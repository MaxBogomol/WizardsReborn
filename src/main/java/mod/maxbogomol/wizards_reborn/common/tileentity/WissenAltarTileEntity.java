package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.network.WissenAltarSendEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenAltarRecipe;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import mod.maxbogomol.wizards_reborn.common.network.WissenAltarBurstEffectPacket;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class WissenAltarTileEntity extends TileSimpleInventory implements ITickableTileEntity, IWissenTileEntity {

    public int wissenInItem = 0;
    public int wissenIsCraft = 0;

    public int wissen = 0;

    public Random random = new Random();

    public WissenAltarTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public WissenAltarTileEntity() {
        this(WizardsReborn.WISSEN_ALTAR_TILE_ENTITY.get());
    }

    @Override
    public void tick() {
        if (!world.isRemote()) {
            if ((getItemHandler().getStackInSlot(2).isEmpty()) && (!getItemHandler().getStackInSlot(1).isEmpty())) {
                getItemHandler().setInventorySlotContents(2, getItemHandler().getStackInSlot(1).copy());
                getItemHandler().getStackInSlot(2).setCount(1);
                if (getItemHandler().getStackInSlot(1).getCount()>  1) {
                    getItemHandler().getStackInSlot(1).setCount(getItemHandler().getStackInSlot(1).getCount() - 1);
                } else {
                    getItemHandler().removeStackFromSlot(1);
                }

                PacketUtils.SUpdateTileEntityPacket(this);
            }

            Inventory inv = new Inventory(1);
            inv.setInventorySlotContents(0, getItemHandler().getStackInSlot(2));
            wissenInItem = world.getRecipeManager().getRecipe(WizardsReborn.WISSEN_ALTAR_RECIPE, inv, world)
                    .map(WissenAltarRecipe::getRecipeWissen)
                    .orElse(0);

            if ((wissenInItem > 0) && (wissen < getMaxWissen())) {
                int addRemainCraft = WissenUtils.getAddWissenRemain(wissenIsCraft, getWissenPerTick(), wissenInItem);
                int addRemain = WissenUtils.getAddWissenRemain(getWissen(), getWissenPerTick() - addRemainCraft, getMaxWissen());

                wissenIsCraft = wissenIsCraft + (getWissenPerTick() - addRemainCraft - addRemain);
                addWissen(getWissenPerTick() - addRemainCraft - addRemain);

                PacketUtils.SUpdateTileEntityPacket(this);
            }

            if (wissenInItem > 0) {
                if (wissenInItem <= wissenIsCraft) {
                    getItemHandler().removeStackFromSlot(2);
                    wissenInItem = 0;
                    wissenIsCraft = 0;

                    PacketHandler.sendToTracking(world, getPos(), new WissenAltarBurstEffectPacket(getPos()));
                    PacketUtils.SUpdateTileEntityPacket(this);
                }
            }

            if (wissen > 0) {
                if (!getItemHandler().getStackInSlot(0).isEmpty()) {
                    ItemStack stack = getItemHandler().getStackInSlot(0);
                    if (stack.getItem() instanceof IWissenItem) {
                        IWissenItem item = (IWissenItem) stack.getItem();
                        int wissen_remain = WissenUtils.getRemoveWissenRemain(wissen, 100);
                        wissen_remain = 100 - wissen_remain;
                        int item_wissen_remain = WissenItemUtils.getAddWissenRemain(stack, wissen_remain, item.getMaxWissen());
                        wissen_remain = wissen_remain - item_wissen_remain;
                        if (wissen_remain > 0) {
                            WissenItemUtils.addWissen(stack, wissen_remain, item.getMaxWissen());
                            wissen = wissen - wissen_remain;
                            if (random.nextFloat() < 0.5) {
                                PacketHandler.sendToTracking(world, getPos(), new WissenAltarSendEffectPacket(getPos()));
                            }
                        }
                    }
                }
            }
        }

        if (world.isRemote()) {
            if (getWissen() > 0) {
                if (random.nextFloat() < 0.5) {
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.3f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f)
                            .setLifetime(20)
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.3125F, pos.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.1f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.3125F, pos.getZ() + 0.5F);
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
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.3125F, pos.getZ() + 0.5F);
                }
            }
        }
    }

    @Override
    protected Inventory createItemHandler() {
        return new Inventory(3) {
            @Override
            public int getInventoryStackLimit() {
                return 64;
            }
        };
    }

    @Override
    public final SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = new CompoundNBT();
        write(tag);
        return new SUpdateTileEntityPacket(pos, -999, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet) {
        super.onDataPacket(net, packet);
        read(this.getBlockState(),packet.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
    }

    public float getBlockRotate() {
        switch (this.getBlockState().get(HORIZONTAL_FACING)) {
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
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
        tag.putInt("wissenInItem", wissenInItem);
        tag.putInt("wissenIsCraft", wissenIsCraft);
        tag.putInt("wissen", wissen);
        CompoundNBT ret = super.write(tag);
        return ret;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);
        wissenInItem = tag.getInt("wissenInItem");
        wissenIsCraft = tag.getInt("wissenIsCraft");
        wissen = tag.getInt("wissen");
        super.read(state, tag);
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
}
