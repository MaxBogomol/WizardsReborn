package mod.maxbogomol.wizards_reborn.common.block.casing.light;

import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.client.sound.LightCasingSoundInstance;
import mod.maxbogomol.wizards_reborn.common.block.light_transfer_lens.LightTransferLensBlockEntity;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class LightCasingBlockEntity extends LightTransferLensBlockEntity {
    public boolean[] connection = new boolean[Direction.values().length];

    public LightCasingSoundInstance sound;

    public LightCasingBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public LightCasingBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.LIGHT_CASING.get(), pos, state);
    }

    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (getLight() > 0) {
                removeLight(1);
                LightUtil.tickLightTypeStack(this, getLightTypes());
                if (getLight() <= 0) {
                    clearLightType();
                }
                update = true;
            } else if (!getLightTypes().isEmpty()) {
                clearLightType();
                update = true;
            }

            if (canWork()) {
                for (Direction direction : Direction.values()) {
                    if (isConnection(direction)) {
                        BlockPos pos = new BlockPos(0, 0, 0).relative(direction);
                        Vec3 from = new Vec3(getBlockPos().getX() + 0.5f + (pos.getX() * getLightLensOffset()), getBlockPos().getY() + 0.5f + (pos.getY() * getLightLensOffset()), getBlockPos().getZ() + 0.5f + (pos.getZ() * getLightLensOffset()));
                        Vec3 to = LightUtil.getLightLensPos(getBlockPos().relative(direction), getLightLensPos());

                        LightRayHitResult hitResult = LightUtil.getLightRayHitResult(level, getBlockPos(), from, to, 25);
                        BlockEntity hitBlock = hitResult.getBlockEntity();
                        LightUtil.transferLight(this, hitBlock);
                        LightUtil.tickHitLightTypeStack(this, getLightTypes(), hitResult);
                    }
                }
            }

            if (update) setChanged();
        }

        if (level.isClientSide()) {
            if (getLight() > 0) {
                for (Direction direction : Direction.values()) {
                    if (isConnection(direction)) {
                        if (sound == null) {
                            sound = LightCasingSoundInstance.getSound(this);
                            sound.playSound();
                        } else if (sound.isStopped()) {
                            sound = LightCasingSoundInstance.getSound(this);
                            sound.playSound();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        for(Direction facing : Direction.values())
            if(nbt.contains("connection"+facing.get3DDataValue()))
                connection[facing.get3DDataValue()] = nbt.getBoolean("connection"+facing.get3DDataValue());
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        for(Direction facing : Direction.values())
            nbt.putBoolean("connection"+facing.get3DDataValue(),connection[facing.get3DDataValue()]);
    }

    @Override
    public float getLightLensSize() {
        return 0.5f;
    }

    public float getLightLensOffset() {
        return 0.4375f;
    }

    public void setConnection(Direction facing, boolean flag) {
        connection[facing.get3DDataValue()] = flag;
    }

    public void resetsConnections() {
        for (Direction facing : Direction.values()) {
            setConnection(facing, false);
        }
    }

    public boolean isConnection(Direction facing) {
        return connection[facing.get3DDataValue()];
    }

    @Override
    public boolean wissenWandReceiveConnect(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
        return false;
    }

    @Override
    public boolean wissenWandSendConnect(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
        return false;
    }

    @Override
    public boolean wissenWandReload(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
        resetsConnections();
        BlockEntityUpdate.packet(this);
        return true;
    }
}
