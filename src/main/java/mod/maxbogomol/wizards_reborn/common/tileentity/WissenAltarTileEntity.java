package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.WissenAltarSendEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenAltarRecipe;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.WissenAltarBurstEffectPacket;
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
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Random;

public class WissenAltarTileEntity extends ExposedTileSimpleInventory implements TickableBlockEntity, IWissenTileEntity, ICooldownTileEntity {

    public int wissenInItem = 0;
    public int wissenIsCraft = 0;

    public int wissen = 0;

    public Random random = new Random();

    public WissenAltarTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public WissenAltarTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.WISSEN_ALTAR_TILE_ENTITY.get(), pos, state);
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
            wissenInItem = 0;
            wissenInItem = level.getRecipeManager().getRecipeFor(WizardsReborn.WISSEN_ALTAR_RECIPE.get(), inv, level)
                    .map(WissenAltarRecipe::getRecipeWissen)
                    .orElse(0);

            if ((wissenInItem > 0) && (wissen < getMaxWissen())) {
                int addRemainCraft = WissenUtils.getAddWissenRemain(wissenIsCraft, getWissenPerTick(), wissenInItem);
                int addRemain = WissenUtils.getAddWissenRemain(getWissen(), getWissenPerTick() - addRemainCraft, getMaxWissen());

                wissenIsCraft = wissenIsCraft + (getWissenPerTick() - addRemainCraft - addRemain);
                addWissen(getWissenPerTick() - addRemainCraft - addRemain);

                update = true;
            }

            if (wissenInItem > 0) {
                if (wissenInItem <= wissenIsCraft) {
                    getItemHandler().removeItemNoUpdate(2);
                    wissenInItem = 0;
                    wissenIsCraft = 0;

                    update = true;

                    PacketHandler.sendToTracking(level, getBlockPos(), new WissenAltarBurstEffectPacket(getBlockPos()));
                    level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsReborn.WISSEN_BURST_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                }
            }

            if (wissen > 0) {
                if (!getItemHandler().getItem(0).isEmpty()) {
                    ItemStack stack = getItemHandler().getItem(0);
                    if (stack.getItem() instanceof IWissenItem) {
                        IWissenItem item = (IWissenItem) stack.getItem();
                        int wissen_remain = WissenUtils.getRemoveWissenRemain(wissen, 100);
                        wissen_remain = 100 - wissen_remain;
                        WissenItemUtils.existWissen(stack);
                        int item_wissen_remain = WissenItemUtils.getAddWissenRemain(stack, wissen_remain, item.getMaxWissen());
                        wissen_remain = wissen_remain - item_wissen_remain;
                        if (wissen_remain > 0) {
                            WissenItemUtils.addWissen(stack, wissen_remain, item.getMaxWissen());
                            wissen = wissen - wissen_remain;
                            if (random.nextFloat() < 0.5) {
                                PacketHandler.sendToTracking(level, getBlockPos(), new WissenAltarSendEffectPacket(getBlockPos()));
                            }

                            update = true;
                        }
                    }
                }
            }

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }

        if (level.isClientSide()) {
            if (getWissen() > 0) {
                if (random.nextFloat() < 0.5) {
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.3f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f)
                            .setLifetime(20)
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.3125F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.1f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.3125F, worldPosition.getZ() + 0.5F);
                }
            }

            if (wissenInItem > 0) {
                if (random.nextFloat() < 0.2) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                            .setAlpha(0.25f, 0).setScale(0.1f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
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
            PacketUtils.SUpdateTileEntityPacket(this);
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
    public int getWissenPerReceive() {
        return 0;
    }

    @Override
    public int getSendWissenCooldown() {
        return 0;
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
