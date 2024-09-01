package mod.maxbogomol.wizards_reborn.common.block.sensor;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockEntityBase;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;

public class SensorBlockEntity extends BlockEntityBase implements TickableBlockEntity {
    private int output;

    public SensorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SensorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(WizardsRebornBlockEntities.SENSOR.get(), pPos, pBlockState);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            ((SensorBaseBlock) getBlockState().getBlock()).refreshOutputState(level, getBlockPos(), getBlockState());
        }
    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        pTag.putInt("OutputSignal", this.output);
    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.output = pTag.getInt("OutputSignal");
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1f, pos.getY() + 1f, pos.getZ() + 1f);
    }

    public int getOutputSignal() {
        return this.output;
    }

    public void setOutputSignal(int pOutput) {
        this.output = pOutput;
    }

    public float getBlockRotate() {
        switch (this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case NORTH:
                return 0F;
            case SOUTH:
                return 180F;
            case WEST:
                return 90F;
            case EAST:
                return 270F;
            default:
                return 0F;
        }
    }

    public float getBlockUpRotate() {
        switch (this.getBlockState().getValue(SensorBaseBlock.FACE)) {
            case FLOOR:
                return 90F;
            case WALL:
                return 0F;
            case CEILING:
                return -90F;
            default:
                return 0F;
        }
    }
}
