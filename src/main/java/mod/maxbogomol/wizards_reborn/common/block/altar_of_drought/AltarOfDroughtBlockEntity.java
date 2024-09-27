package mod.maxbogomol.wizards_reborn.common.block.altar_of_drought;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.WissenSendEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.block.AltarOfDroughtBreakPacket;
import mod.maxbogomol.wizards_reborn.common.network.block.AltarOfDroughtBurstPacket;
import mod.maxbogomol.wizards_reborn.common.network.block.AltarOfDroughtSendPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class AltarOfDroughtBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity, IWissenBlockEntity, ICooldownBlockEntity {
    public int wissen = 0;
    public int ticks = 0;
    public int maxTicks = 0;

    public AltarOfDroughtBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public AltarOfDroughtBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.ALTAR_OF_DROUGHT.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            if (wissen > 0) {
                if (!getItemHandler().getItem(0).isEmpty()) {
                    ItemStack stack = getItemHandler().getItem(0);
                    if (stack.getItem() instanceof IWissenItem item) {
                        int wissen_remain = WissenUtil.getRemoveWissenRemain(wissen, 50);
                        wissen_remain = 50 - wissen_remain;
                        WissenItemUtil.existWissen(stack);
                        int item_wissen_remain = WissenItemUtil.getAddWissenRemain(stack, wissen_remain, item.getMaxWissen());
                        wissen_remain = wissen_remain - item_wissen_remain;
                        if (wissen_remain > 0) {
                            WissenItemUtil.addWissen(stack, wissen_remain, item.getMaxWissen());
                            wissen = wissen - wissen_remain;
                            if (random.nextFloat() < 0.5) {
                                PacketHandler.sendToTracking(level, getBlockPos(), new AltarOfDroughtSendPacket(getBlockPos()));
                            }
                            if (random.nextFloat() < 0.1) {
                                level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.BLOCKS, 0.15f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
                            }

                            update = true;
                        }
                    }
                }
            }

            int distance = 15;

            if (ticks > 0) {
                ticks = ticks - 1;
                if (ticks <= 0) {
                    maxTicks = 0;
                }
                update = true;
            }

            if (ticks <= 0 && wissen < getMaxWissen() && canWork()) {
                ArrayList<BlockPos> blockPosList = CrystalRitual.getBlockPosWithArea(level, getBlockPos(), new Vec3(distance, distance, distance), new Vec3(distance, distance, distance), (p) -> {
                    return level.getBlockState(p).is(WizardsRebornBlockTags.ALTAR_OF_DROUGHT_TARGET) && !level.getBlockState(p).getValue(BlockStateProperties.PERSISTENT);
                }, true, true, 1);

                for (BlockPos breakPos : blockPosList) {
                    if (level.getBlockState(breakPos).is(WizardsRebornBlockTags.ALTAR_OF_DROUGHT_TARGET) && !level.getBlockState(breakPos).getValue(BlockStateProperties.PERSISTENT)) {
                        PacketHandler.sendToTracking(level, getBlockPos(), new AltarOfDroughtBurstPacket(getBlockPos()));
                        PacketHandler.sendToTracking(level, breakPos, new AltarOfDroughtBreakPacket(breakPos));
                        PacketHandler.sendToTracking(level, getBlockPos(), new WissenSendEffectPacket(getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F, breakPos.getX() + 0.5F, breakPos.getY() + 0.5F, breakPos.getZ() + 0.5F, WizardsRebornConfig.wissenColorR(), WizardsRebornConfig.wissenColorG(), WizardsRebornConfig.wissenColorB(), 25));
                        level.destroyBlock(breakPos, false);
                        addWissen(25);
                        ticks = 20 + random.nextInt(10);
                        maxTicks = ticks;
                        update = true;
                        level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.ALTAR_OF_DROUGHT.get(), SoundSource.BLOCKS, 0.5f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                    }
                }
            }

            if (update) setChanged();
        }

        if (level.isClientSide()) {
            if (getWissen() > 0) {
                if (random.nextFloat() < 0.5) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColorR(), WizardsRebornConfig.wissenColorG(), WizardsRebornConfig.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.3f * getStage(), 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.015f * getStage())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.625F, getBlockPos().getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColorR(), WizardsRebornConfig.wissenColorG(), WizardsRebornConfig.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.1f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.015f * getStage())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.625F, getBlockPos().getZ() + 0.5F);
                }
            }
        }
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(1);
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        return stack.getItem() instanceof IWissenItem;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        return true;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("wissen", wissen);
        tag.putInt("ticks", ticks);
        tag.putInt("maxTicks", maxTicks);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        wissen = tag.getInt("wissen");
        ticks = tag.getInt("ticks");
        maxTicks = tag.getInt("maxTicks");
    }

    @Override
    public AABB getRenderBoundingBox() {
        return IForgeBlockEntity.INFINITE_EXTENT_AABB;
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

    public boolean canWork() {
        return !level.hasNeighborSignal(getBlockPos());
    }

    public float getStage() {
        return ((float) getWissen() / (float) getMaxWissen());
    }

    @Override
    public int getWissen() {
        return wissen;
    }

    @Override
    public int getMaxWissen() {
        return 7500;
    }

    @Override
    public boolean canSendWissen() {
        return true;
    }

    @Override
    public boolean canReceiveWissen() {
        return false;
    }

    @Override
    public boolean canConnectSendWissen() {
        return false;
    }

    @Override
    public boolean canConnectReceiveWissen() {
        return true;
    }

    @Override
    public void setWissen(int wissen) {
        this.wissen = wissen;
    }

    @Override
    public void addWissen(int wissen) {
        this.wissen = this.wissen + wissen;
        if (this.wissen > getMaxWissen()) {
            this.wissen = getMaxWissen();
        }
    }

    @Override
    public void removeWissen(int wissen) {
        this.wissen = this.wissen - wissen;
        if (this.wissen < 0) {
            this.wissen = 0;
        }
    }

    @Override
    public float getCooldown() {
        if (ticks > 0) {
            return (float) maxTicks / ticks;
        }
        return 0;
    }
}
