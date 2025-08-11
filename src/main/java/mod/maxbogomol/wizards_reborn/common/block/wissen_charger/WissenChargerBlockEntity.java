package mod.maxbogomol.wizards_reborn.common.block.wissen_charger;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.block.WissenChargerReceivePacket;
import mod.maxbogomol.wizards_reborn.common.network.block.WissenChargerSendPacket;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class WissenChargerBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity, IWissenBlockEntity, IWissenWandFunctionalBlockEntity {

    public int wissen = 0;
    public int cooldown = 0;
    public boolean discharge = false;

    public int dischargeTicks = 0;
    public int oldDischargeTicks = 0;
    public int dischargeTicksUp = 0;

    public WissenChargerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public WissenChargerBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.WISSEN_CHARGER.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            Container container = getItemHandler();

            if (cooldown > 0) {
                cooldown--;
                update = true;
            }

            if (cooldown <= 0) {
                if (!container.getItem(0).isEmpty()) {
                    if (!discharge && wissen > 0) {
                        ItemStack stack = container.getItem(0);
                        if (stack.getItem() instanceof IWissenItem item) {
                            int wissenRemain = WissenUtil.getRemoveWissenRemain(wissen, getWissenPerReceive());
                            wissenRemain = getWissenPerReceive() - wissenRemain;
                            WissenItemUtil.existWissen(stack);
                            int itemWissenRemain = WissenItemUtil.getAddWissenRemain(stack, wissenRemain, item.getMaxWissen(stack));
                            wissenRemain = wissenRemain - itemWissenRemain;
                            if (wissenRemain > 0) {
                                WissenItemUtil.addWissen(stack, wissenRemain, item.getMaxWissen(stack));
                                wissen = wissen - wissenRemain;
                                if (random.nextFloat() < 0.5) {
                                    WizardsRebornPacketHandler.sendToTracking(level, getBlockPos(), new WissenChargerSendPacket(getBlockPos()));
                                }
                                if (random.nextFloat() < 0.1) {
                                    level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.BLOCKS, 0.15f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
                                }

                                update = true;
                            }
                        }
                    }
                    if (discharge) {
                        ItemStack stack = container.getItem(0);
                        if (stack.getItem() instanceof IWissenItem item) {
                            WissenItemUtil.existWissen(stack);
                            int itemWissenRemain = WissenItemUtil.getRemoveWissenRemain(stack, getWissenPerReceive());
                            itemWissenRemain = WissenItemUtil.getWissen(stack) - itemWissenRemain;
                            int wissenRemain = WissenUtil.getAddWissenRemain(wissen, itemWissenRemain, getMaxWissen());
                            wissenRemain = itemWissenRemain - wissenRemain;
                            if (wissenRemain > 0) {
                                WissenItemUtil.removeWissen(stack, wissenRemain);
                                wissen = wissen + wissenRemain;
                                if (random.nextFloat() < 0.5) {
                                    WizardsRebornPacketHandler.sendToTracking(level, getBlockPos(), new WissenChargerReceivePacket(getBlockPos()));
                                }
                                if (random.nextFloat() < 0.1) {
                                    level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.BLOCKS, 0.15f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
                                }

                                update = true;
                            }
                        }
                    }
                }
            }

            if (update) setChanged();
        }

        if (level.isClientSide()) {
            oldDischargeTicks = dischargeTicks;
            if (discharge && dischargeTicks == 0) dischargeTicksUp = 0;
            if (discharge && dischargeTicks < 20) dischargeTicks++;
            if (!discharge && dischargeTicks > 0) dischargeTicks--;
            if (dischargeTicks > 0) dischargeTicksUp++;

            if (getWissen() > 0) {
                if (random.nextFloat() < 0.5) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.2f * getStage(), 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.005f * getStage())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.875F, getBlockPos().getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.3) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.0375f * getStage(), 0.075f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.005f * getStage())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.875F, getBlockPos().getZ() + 0.5F);
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
        tag.putInt("cooldown", cooldown);
        tag.putBoolean("discharge", discharge);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        wissen = tag.getInt("wissen");
        cooldown = tag.getInt("cooldown");
        discharge = tag.getBoolean("discharge");
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

    public float getStage() {
        return ((float) getWissen() / (float) getMaxWissen());
    }

    public int getWissenPerReceive() {
        return 200;
    }

    @Override
    public int getWissen() {
        return wissen;
    }

    @Override
    public int getMaxWissen() {
        return 5000;
    }

    @Override
    public boolean canSendWissen() {
        return true;
    }

    @Override
    public boolean canReceiveWissen() {
        return true;
    }

    @Override
    public boolean canConnectSendWissen() {
        return true;
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
    public void wissenWandFunction() {
        if (cooldown <= 0) {
            discharge = !discharge;
            cooldown = 20;
            BlockEntityUpdate.packet(this);
        }
    }
}
