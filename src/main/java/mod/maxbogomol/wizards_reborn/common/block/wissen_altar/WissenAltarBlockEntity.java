package mod.maxbogomol.wizards_reborn.common.block.wissen_altar;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.common.network.block.WissenAltarBurstPacket;
import mod.maxbogomol.wizards_reborn.common.network.block.WissenAltarSendPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenAltarRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
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
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class WissenAltarBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity, IWissenBlockEntity, ICooldownBlockEntity {

    public int wissenInItem = 0;
    public int wissenIsCraft = 0;

    public int wissen = 0;

    public WissenAltarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public WissenAltarBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.WISSEN_ALTAR.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            Container container = getItemHandler();

            if ((container.getItem(2).isEmpty()) && (!container.getItem(1).isEmpty())) {
                container.setItem(2, container.getItem(1).copy());
                container.getItem(2).setCount(1);
                if (container.getItem(1).getCount()>  1) {
                    container.getItem(1).setCount(container.getItem(1).getCount() - 1);
                } else {
                    container.removeItemNoUpdate(1);
                }

                update = true;
            }

            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, container.getItem(2));
            if (!inv.isEmpty()) {
                wissenInItem = 0;
                wissenInItem = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.WISSEN_ALTAR.get(), inv, level).map(WissenAltarRecipe::getRecipeWissen).orElse(0);

                if ((wissenInItem > 0) && (wissen < getMaxWissen())) {
                    int addRemainCraft = WissenUtil.getAddWissenRemain(wissenIsCraft, getWissenPerTick(), wissenInItem);
                    int addRemain = WissenUtil.getAddWissenRemain(getWissen(), getWissenPerTick() - addRemainCraft, getMaxWissen());

                    wissenIsCraft = wissenIsCraft + (getWissenPerTick() - addRemainCraft - addRemain);
                    addWissen(getWissenPerTick() - addRemainCraft - addRemain);
                    if (random.nextFloat() < 0.05) {
                        level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.BLOCKS, 0.15f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
                    }

                    update = true;
                }

                if (wissenInItem > 0) {
                    if (wissenInItem <= wissenIsCraft) {
                        container.removeItemNoUpdate(2);
                        wissenInItem = 0;
                        wissenIsCraft = 0;

                        update = true;

                        WizardsRebornPacketHandler.sendToTracking(level, getBlockPos(), new WissenAltarBurstPacket(getBlockPos()));
                        level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.WISSEN_ALTAR_BURST.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                    }
                }

                if (wissen > 0) {
                    if (!container.getItem(0).isEmpty()) {
                        ItemStack stack = container.getItem(0);
                        if (stack.getItem() instanceof IWissenItem item) {
                            int wissenRemain = WissenUtil.getRemoveWissenRemain(wissen, getWissenPerReceive());
                            wissenRemain = getWissenPerReceive() - wissenRemain;
                            WissenItemUtil.existWissen(stack);
                            int itemWissenRemain = WissenItemUtil.getAddWissenRemain(stack, wissenRemain, item.getMaxWissen());
                            wissenRemain = wissenRemain - itemWissenRemain;
                            if (wissenRemain > 0) {
                                WissenItemUtil.addWissen(stack, wissenRemain, item.getMaxWissen());
                                wissen = wissen - wissenRemain;
                                if (random.nextFloat() < 0.5) {
                                    WizardsRebornPacketHandler.sendToTracking(level, getBlockPos(), new WissenAltarSendPacket(getBlockPos()));
                                }
                                if (random.nextFloat() < 0.1) {
                                    level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.BLOCKS, 0.15f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
                                }

                                update = true;
                            }
                        }
                    }
                }
            } else if (wissenInItem != 0) {
                wissenInItem = 0;
                wissenIsCraft = 0;
                update = true;
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
                            .randomVelocity(0.035f * getStage(), 0.035f * getStage(), 0.035f * getStage())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 1.3125F, getBlockPos().getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColorR(), WizardsRebornConfig.wissenColorG(), WizardsRebornConfig.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f * getStage(), 0.1f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.035f * getStage(), 0.035f * getStage(), 0.035f * getStage())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 1.3125F, getBlockPos().getZ() + 0.5F);
                }
            }

            if (wissenInItem > 0 && getWissen() < getMaxWissen()) {
                if (random.nextFloat() < 0.2) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColorR(), WizardsRebornConfig.wissenColorG(), WizardsRebornConfig.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f * getStage(), 0.1f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(20)
                            .randomVelocity(0.05f)
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 1.3125F, getBlockPos().getZ() + 0.5F);
                }
            }
        }
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(3);
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        if (index == 0) {
            if (stack.getItem() instanceof IWissenItem) {
                return true;
            }
        }

        if (index == 1) {
            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, stack);
            int wissenInItem = getLevel().getRecipeManager().getRecipeFor(WizardsRebornRecipes.WISSEN_ALTAR.get(), inv, getLevel()).map(WissenAltarRecipe::getRecipeWissen).orElse(0);
            if (wissenInItem > 0) {
                if (canPlaceItem(index, stack)) {
                    ItemStack existing = getItem(index);
                    return existing.getCount() < getMaxStackSize();
                }
            }
        }

        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        return index <= 1;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("wissenInItem", wissenInItem);
        tag.putInt("wissenIsCraft", wissenIsCraft);
        tag.putInt("wissen", wissen);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        wissenInItem = tag.getInt("wissenInItem");
        wissenIsCraft = tag.getInt("wissenIsCraft");
        wissen = tag.getInt("wissen");
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
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

    public float getCraftingStage () {
        if (wissenInItem <= 0) {
            return 1F;
        }
        return (1F - ((float) wissenIsCraft / (float) wissenInItem));
    }

    public int getWissenPerReceive() {
        return 100;
    }

    @Override
    public int getWissen() {
        return wissen;
    }

    @Override
    public int getMaxWissen() {
        return 10000;
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

    public int getWissenPerTick() {
        return 10;
    }

    @Override
    public float getCooldown() {
        if (wissenInItem > 0) {
            return (float) wissenInItem / wissenIsCraft;
        }
        return 0;
    }
}
