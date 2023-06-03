package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.data.recipes.ArcaneWorkbenchRecipe;
import mod.maxbogomol.wizards_reborn.common.network.ArcaneWorkbenchBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.PacketUtils;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Optional;
import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class ArcaneWorkbenchTileEntity extends TileEntity implements ITickableTileEntity, IWissenTileEntity, IWissenWandFunctionalTileEntity {
    public final ItemStackHandler itemHandler = createHandler();
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public int wissenInCraft= 0;
    public int wissenIsCraft = 0;
    public boolean startCraft = false;

    public int wissen = 0;

    public Random random = new Random();

    public ArcaneWorkbenchTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public ArcaneWorkbenchTileEntity() {
        this(WizardsReborn.ARCANE_WORKBENCH_TILE_ENTITY.get());
    }

    @Override
    public void tick() {
        if (!world.isRemote()) {
            Inventory inv = new Inventory(itemHandler.getSlots());
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                inv.setInventorySlotContents(i, itemHandler.getStackInSlot(i));
            }

            Optional<ArcaneWorkbenchRecipe> recipe = world.getRecipeManager().getRecipe(WizardsReborn.ARCANE_WORKBENCH_RECIPE, inv, world);
            wissenInCraft =  recipe.map(ArcaneWorkbenchRecipe::getRecipeWissen).orElse(0);

            if (wissenInCraft <= 0) {
                wissenIsCraft = 0;
                startCraft = false;
            }

            if ((wissenInCraft > 0) && (wissen > 0) && (startCraft)) {
                ItemStack output = recipe.get().getRecipeOutput();

                if (isCanCraft(inv, output)) {
                    int addRemainCraft = WissenUtils.getAddWissenRemain(wissenIsCraft, getWissenPerTick(), wissenInCraft);
                    int removeRemain = WissenUtils.getRemoveWissenRemain(getWissen(), getWissenPerTick() - addRemainCraft);

                    wissenIsCraft = wissenIsCraft + (getWissenPerTick() - addRemainCraft - removeRemain);
                    removeWissen(getWissenPerTick() - addRemainCraft - removeRemain);

                    PacketUtils.SUpdateTileEntityPacket(this);
                }
            }

            if (wissenInCraft > 0 && startCraft) {
                if (wissenInCraft <= wissenIsCraft) {
                    ItemStack output = recipe.get().getRecipeOutput().copy();

                    if (isCanCraft(inv, output)) {
                        wissenInCraft = 0;
                        wissenIsCraft = 0;
                        startCraft = false;

                        output.setCount(itemHandler.getStackInSlot(13).getCount() + output.getCount());

                        itemHandler.setStackInSlot(13, output);

                        for (int i = 0; i < 13; i++) {
                            itemHandler.extractItem(i, 1, false);
                        }

                        PacketHandler.sendToTracking(world, getPos(), new ArcaneWorkbenchBurstEffectPacket(getPos()));
                        PacketUtils.SUpdateTileEntityPacket(this);
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
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(20)
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.5F, pos.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.1f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.5F, pos.getZ() + 0.5F);
                }
            }

            if (wissenInCraft > 0 && startCraft) {
                if (random.nextFloat() < 0.2) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                            .setAlpha(0.25f, 0).setScale(0.1f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.5F, pos.getZ() + 0.5F);
                }

                if (random.nextFloat() < 0.3) {
                    double X = ((random.nextDouble() - 0.5D) * 0.5);
                    double Z = ((random.nextDouble() - 0.5D) * 0.5);
                    Particles.create(WizardsReborn.KARMA_PARTICLE)
                            .addVelocity(-(X / 100), (random.nextDouble() / 20), -(Z / 100))
                            .setAlpha(0.5f, 0).setScale(0.1f, 0.025f)
                            .setColor(0.733f, 0.564f, 0.937f, 0.733f, 0.564f, 0.937f)
                            .setLifetime(15)
                            .spawn(world, pos.getX() + 0.5F + X, pos.getY() + 1F, pos.getZ() + 0.5F + Z);
                }
            }
        }
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(14) {
            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(!isItemValid(slot, stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }

        return super.getCapability(cap, side);
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

    public boolean isCanCraft(Inventory inv, ItemStack output) {
        if (inv.getStackInSlot(13).isEmpty()) {
            return true;
        }

        if ((ItemHandlerHelper.canItemStacksStack(output, inv.getStackInSlot(13))) && (inv.getStackInSlot(13).getCount() + output.getCount() <= output.getMaxStackSize())) {
            return true;
        }

        return false;
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
        tag.put("inv", itemHandler.serializeNBT());

        tag.putInt("wissenInCraft", wissenInCraft);
        tag.putInt("wissenIsCraft", wissenIsCraft);
        tag.putBoolean("startCraft", startCraft);
        tag.putInt("wissen", wissen);
        CompoundNBT ret = super.write(tag);
        return ret;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);
        itemHandler.deserializeNBT(tag.getCompound("inv"));

        wissenInCraft = tag.getInt("wissenInCraft");
        wissenIsCraft = tag.getInt("wissenIsCraft");
        startCraft = tag.getBoolean("startCraft");
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
    public void wissenWandFuction() {
        startCraft = true;
    }
}
