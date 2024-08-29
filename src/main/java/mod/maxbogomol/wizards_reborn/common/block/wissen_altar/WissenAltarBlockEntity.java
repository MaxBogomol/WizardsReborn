package mod.maxbogomol.wizards_reborn.common.block.wissen_altar;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.WissenAltarBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.WissenAltarSendEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenAltarRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class WissenAltarBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity, IWissenBlockEntity, ICooldownBlockEntity {

    public int wissenInItem = 0;
    public int wissenIsCraft = 0;

    public int wissen = 0;

    public Random random = new Random();

    public WissenAltarBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public WissenAltarBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.WISSEN_ALTAR_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            if ((getItemHandler().getItem(2).isEmpty()) && (!getItemHandler().getItem(1).isEmpty())) {
                getItemHandler().setItem(2, getItemHandler().getItem(1).copy());
                getItemHandler().getItem(2).setCount(1);
                if (getItemHandler().getItem(1).getCount()>  1) {
                    getItemHandler().getItem(1).setCount(getItemHandler().getItem(1).getCount() - 1);
                } else {
                    getItemHandler().removeItemNoUpdate(1);
                }

                update = true;
            }

            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, getItemHandler().getItem(2));
            if (!inv.isEmpty()) {
                wissenInItem = 0;
                wissenInItem = level.getRecipeManager().getRecipeFor(WizardsReborn.WISSEN_ALTAR_RECIPE.get(), inv, level)
                        .map(WissenAltarRecipe::getRecipeWissen)
                        .orElse(0);

                if ((wissenInItem > 0) && (wissen < getMaxWissen())) {
                    int addRemainCraft = WissenUtils.getAddWissenRemain(wissenIsCraft, getWissenPerTick(), wissenInItem);
                    int addRemain = WissenUtils.getAddWissenRemain(getWissen(), getWissenPerTick() - addRemainCraft, getMaxWissen());

                    wissenIsCraft = wissenIsCraft + (getWissenPerTick() - addRemainCraft - addRemain);
                    addWissen(getWissenPerTick() - addRemainCraft - addRemain);
                    if (random.nextFloat() < 0.05) {
                        level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsReborn.WISSEN_BURST_SOUND.get(), SoundSource.BLOCKS, 0.15f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
                    }

                    update = true;
                }

                if (wissenInItem > 0) {
                    if (wissenInItem <= wissenIsCraft) {
                        getItemHandler().removeItemNoUpdate(2);
                        wissenInItem = 0;
                        wissenIsCraft = 0;

                        update = true;

                        PacketHandler.sendToTracking(level, getBlockPos(), new WissenAltarBurstEffectPacket(getBlockPos()));
                        level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsReborn.WISSEN_ALTAR_BURST_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                    }
                }

                if (wissen > 0) {
                    if (!getItemHandler().getItem(0).isEmpty()) {
                        ItemStack stack = getItemHandler().getItem(0);
                        if (stack.getItem() instanceof IWissenItem) {
                            IWissenItem item = (IWissenItem) stack.getItem();
                            int wissenRemain = WissenUtils.getRemoveWissenRemain(wissen, getWissenPerReceive());
                            wissenRemain = getWissenPerReceive() - wissenRemain;
                            WissenItemUtils.existWissen(stack);
                            int itemWissenRemain = WissenItemUtils.getAddWissenRemain(stack, wissenRemain, item.getMaxWissen());
                            wissenRemain = wissenRemain - itemWissenRemain;
                            if (wissenRemain > 0) {
                                WissenItemUtils.addWissen(stack, wissenRemain, item.getMaxWissen());
                                wissen = wissen - wissenRemain;
                                if (random.nextFloat() < 0.5) {
                                    PacketHandler.sendToTracking(level, getBlockPos(), new WissenAltarSendEffectPacket(getBlockPos()));
                                }
                                if (random.nextFloat() < 0.1) {
                                    level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsReborn.WISSEN_BURST_SOUND.get(), SoundSource.BLOCKS, 0.15f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
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
                    ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.3f * getStage(), 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.035f * getStage(), 0.035f * getStage(), 0.035f * getStage())
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.3125F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFur.SQUARE_PARTICLE : FluffyFur.SPARKLE_PARTICLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f * getStage(), 0.1f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .randomSpin(0.5f)
                            .setLifetime(30)
                            .randomVelocity(0.035f * getStage(), 0.035f * getStage(), 0.035f * getStage())
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.3125F, worldPosition.getZ() + 0.5F);
                }
            }

            if (wissenInItem > 0 && getWissen() < getMaxWissen()) {
                if (random.nextFloat() < 0.2) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFur.SQUARE_PARTICLE : FluffyFur.SPARKLE_PARTICLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f * getStage(), 0.1f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .randomSpin(0.5f)
                            .setLifetime(20)
                            .randomVelocity(0.05f)
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.3125F, worldPosition.getZ() + 0.5F);
                }
            }
        }
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(3) {
            @Override
            public int getMaxStackSize() {
                return 64;
            }
        };
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, (e) -> e.getUpdateTag());
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getTag());
    }

    @NotNull
    @Override
    public final CompoundTag getUpdateTag() {
        var tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            BlockEntityUpdate.packet(this);
        }
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
            int wissenInItem = getLevel().getRecipeManager().getRecipeFor(WizardsReborn.WISSEN_ALTAR_RECIPE.get(), inv, getLevel())
                    .map(WissenAltarRecipe::getRecipeWissen)
                    .orElse(0);
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
        if (index <= 1) {
            return true;
        }

        return false;
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
