package mod.maxbogomol.wizards_reborn.common.block.sensor;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockEntityBase;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
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

    public SensorBlockEntity(BlockPos pos, BlockState blockState) {
        super(WizardsRebornBlockEntities.SENSOR.get(), pos, blockState);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            ((SensorBaseBlock) getBlockState().getBlock()).refreshOutputState(level, getBlockPos(), getBlockState());
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("OutputSignal", this.output);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.output = tag.getInt("OutputSignal");
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
        return switch (this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case NORTH -> 0F;
            case SOUTH -> 180F;
            case WEST -> 90F;
            case EAST -> 270F;
            default -> 0F;
        };
    }

    public float getBlockUpRotate() {
        return switch (this.getBlockState().getValue(SensorBaseBlock.FACE)) {
            case FLOOR -> 90F;
            case WALL -> 0F;
            case CEILING -> -90F;
            default -> 0F;
        };
    }
}
