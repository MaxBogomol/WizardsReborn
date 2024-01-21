package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.ArcaneWorkbenchBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneWorkbenchRecipe;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class ArcaneWorkbenchTileEntity extends BlockEntity implements TickableBlockEntity, IWissenTileEntity, ICooldownTileEntity, IWissenWandFunctionalTileEntity, IItemResultTileEntity {
    public final ItemStackHandler itemHandler = createHandler(13);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final ItemStackHandler itemOutputHandler = createHandler(1);
    public final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() -> itemOutputHandler);

    public int wissenInCraft= 0;
    public int wissenIsCraft = 0;
    public boolean startCraft = false;

    public int wissen = 0;

    public Random random = new Random();

    public ArcaneWorkbenchTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ArcaneWorkbenchTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.ARCANE_WORKBENCH_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            SimpleContainer inv = new SimpleContainer(14);
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                inv.setItem(i, itemHandler.getStackInSlot(i));
            }
            inv.setItem(13, itemOutputHandler.getStackInSlot(0));

            Optional<ArcaneWorkbenchRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.ARCANE_WORKBENCH_RECIPE.get(), inv, level);
            wissenInCraft = recipe.map(ArcaneWorkbenchRecipe::getRecipeWissen).orElse(0);

            if (wissenInCraft <= 0 && (wissenIsCraft > 0 || startCraft)) {
                wissenIsCraft = 0;
                startCraft = false;

                update = true;
            }

            if ((wissenInCraft > 0) && (wissen > 0) && (startCraft)) {
                ItemStack output = recipe.get().getResultItem(RegistryAccess.EMPTY);

                if (isCanCraft(inv, output)) {
                    int addRemainCraft = WissenUtils.getAddWissenRemain(wissenIsCraft, getWissenPerTick(), wissenInCraft);
                    int removeRemain = WissenUtils.getRemoveWissenRemain(getWissen(), getWissenPerTick() - addRemainCraft);

                    wissenIsCraft = wissenIsCraft + (getWissenPerTick() - addRemainCraft - removeRemain);
                    removeWissen(getWissenPerTick() - addRemainCraft - removeRemain);

                    update = true;
                }
            }

            if (wissenInCraft > 0 && startCraft) {
                if (wissenInCraft <= wissenIsCraft) {
                    ItemStack output = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();

                    if (isCanCraft(inv, output)) {
                        wissenInCraft = 0;
                        wissenIsCraft = 0;
                        startCraft = false;

                        CompoundTag tag = new CompoundTag();
                        if (recipe.get().getRecipeSaveNBT() >= 0) {
                            tag = itemHandler.getStackInSlot(recipe.get().getRecipeSaveNBT()).getOrCreateTag();
                        }
                        output.setCount(itemOutputHandler.getStackInSlot(0).getCount() + output.getCount());

                        itemOutputHandler.setStackInSlot(0, output);
                        if (!tag.isEmpty()) {
                            itemOutputHandler.getStackInSlot(0).setTag(tag);
                        }

                        for (int i = 0; i < 13; i++) {
                            if (itemHandler.getStackInSlot(0).hasCraftingRemainingItem()) {
                                itemHandler.setStackInSlot(0, itemHandler.getStackInSlot(0).getCraftingRemainingItem());
                            } else {
                                itemHandler.extractItem(i, 1, false);
                            }
                        }

                        update = true;

                        PacketHandler.sendToTracking(level, getBlockPos(), new ArcaneWorkbenchBurstEffectPacket(getBlockPos()));
                        level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsReborn.WISSEN_BURST_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
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
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.5F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.1f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.5F, worldPosition.getZ() + 0.5F);
                }
            }

            if (wissenInCraft > 0 && startCraft) {
                if (random.nextFloat() < 0.2) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                            .setAlpha(0.25f, 0).setScale(0.1f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.5F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 40), ((random.nextDouble() - 0.5D) / 40), ((random.nextDouble() - 0.5D) / 40))
                            .setAlpha(0.75f, 0).setScale(0.1f * getStage(), 0)
                            .setColor(random.nextFloat(), random.nextFloat(), random.nextFloat())
                            .setLifetime(65)
                            .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 1.5F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.3) {
                    double X = ((random.nextDouble() - 0.5D) * 0.5);
                    double Z = ((random.nextDouble() - 0.5D) * 0.5);
                    Particles.create(WizardsReborn.KARMA_PARTICLE)
                            .addVelocity(-(X / 100), (random.nextDouble() / 20), -(Z / 100))
                            .setAlpha(0.5f, 0).setScale(0.1f, 0.025f)
                            .setColor(0.733f, 0.564f, 0.937f)
                            .setLifetime(15)
                            .spawn(level, worldPosition.getX() + 0.5F + X, worldPosition.getY() + 1F, worldPosition.getZ() + 0.5F + Z);
                }
            }
        }
    }

    private ItemStackHandler createHandler(int size) {
        return new ItemStackHandler(size) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
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
                if (!isItemValid(slot, stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                CombinedInvWrapper item = new CombinedInvWrapper(itemHandler, itemOutputHandler);
                return LazyOptional.of(() -> item).cast();
            }

            if (side == Direction.DOWN) {
                return outputHandler.cast();
            } else {
                return handler.cast();
            }
        }

        return super.getCapability(cap, side);
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

    public float getBlockRotate() {
        switch (this.getBlockState().getValue(HORIZONTAL_FACING)) {
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

    public boolean isCanCraft(SimpleContainer inv, ItemStack output) {
        if (inv.getItem(13).isEmpty()) {
            return true;
        }

        if ((ItemHandlerHelper.canItemStacksStack(output, inv.getItem(13))) && (inv.getItem(13).getCount() + output.getCount() <= output.getMaxStackSize())) {
            return true;
        }

        return false;
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("output", itemOutputHandler.serializeNBT());

        tag.putInt("wissenInCraft", wissenInCraft);
        tag.putInt("wissenIsCraft", wissenIsCraft);
        tag.putBoolean("startCraft", startCraft);
        tag.putInt("wissen", wissen);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        itemOutputHandler.deserializeNBT(tag.getCompound("output"));

        wissenInCraft = tag.getInt("wissenInCraft");
        wissenIsCraft = tag.getInt("wissenIsCraft");
        startCraft = tag.getBoolean("startCraft");
        wissen = tag.getInt("wissen");
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
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
        return 15;
    }

    @Override
    public void wissenWandFuction() {
        startCraft = true;
    }

    @Override
    public float getCooldown() {
        if (wissenInCraft > 0) {
            return (float) wissenInCraft / wissenIsCraft;
        }
        return 0;
    }

    @Override
    public List<ItemStack> getItemsResult() {
        List<ItemStack> list = new ArrayList<>();

        SimpleContainer inv = new SimpleContainer(14);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        inv.setItem(13, itemOutputHandler.getStackInSlot(0));

        Optional<ArcaneWorkbenchRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.ARCANE_WORKBENCH_RECIPE.get(), inv, level);
        wissenInCraft = recipe.map(ArcaneWorkbenchRecipe::getRecipeWissen).orElse(0);

        if (recipe.isPresent() && wissenInCraft > 0) {
            ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();

            CompoundTag tag = new CompoundTag();
            if (recipe.get().getRecipeSaveNBT() >= 0) {
                tag = itemHandler.getStackInSlot(recipe.get().getRecipeSaveNBT()).getOrCreateTag();
            }
            stack.setTag(tag);

            list.add(stack);
        }

        return list;
    }
}
