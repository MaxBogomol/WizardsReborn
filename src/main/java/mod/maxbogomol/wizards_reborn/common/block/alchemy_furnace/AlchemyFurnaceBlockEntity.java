package mod.maxbogomol.wizards_reborn.common.block.alchemy_furnace;

import mod.maxbogomol.fluffy_fur.common.block.entity.NameableBlockEntityBase;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.IHeatBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IItemResultBlockEntity;
import mod.maxbogomol.wizards_reborn.client.gui.container.AlchemyFurnaceContainer;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.fluid.WizardsRebornFluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class AlchemyFurnaceBlockEntity extends NameableBlockEntityBase implements TickableBlockEntity, IFluidBlockEntity, ISteamBlockEntity, IHeatBlockEntity, IItemResultBlockEntity {
    public final ItemStackHandler itemHandler = createHandler(1);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final ItemStackHandler itemFuelHandler = createHandler(1);
    public final LazyOptional<IItemHandler> fuelHandler = LazyOptional.of(() -> itemFuelHandler);
    public final ItemStackHandler itemOutputHandler = createHandler(1);
    public final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() -> itemOutputHandler);

    protected FluidTank fluidTank = new FluidTank(10000) {
        @Override
        public void onContentsChanged() {
            AlchemyFurnaceBlockEntity.this.setChanged();
        }
    };
    public LazyOptional<IFluidHandler> fluidHolder = LazyOptional.of(() -> fluidTank);

    public int burnMaxTime = 0;
    public int burnTime = 0;
    public int burnLastTime = 0;
    public int heatLastTime = 0;
    public int cookMaxTime = 0;
    public int cookTime = 0;
    public float exp = 0;

    public int steam = 0;
    public int heat = 0;

    Optional<SmeltingRecipe> lastRecipe;

    public AlchemyFurnaceBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public AlchemyFurnaceBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.ALCHEMY_FURNACE.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            boolean flag = isLit();

            if (burnLastTime > 0) {
                burnLastTime = burnLastTime - 1;
                update = true;
            } else {
                if (burnTime > 0) {
                    burnTime = burnTime - 1;
                    update = true;
                }

                if (burnTime <= 0) {
                    burnMaxTime = 0;
                    update = true;
                }
            }

            if (heatLastTime > 0) {
                heatLastTime = heatLastTime - 1;
                update = true;
            } else {
                if (heat > 0) {
                    heat = heat - 1;
                    update = true;
                }

                if (heat <= 0) {
                    cookMaxTime = 0;
                    cookTime = 0;
                    update = true;
                }
            }

            if (burnTime > 0) {
                for (int i = 0; i < 10; i++) {
                    if (burnTime > 0) {
                        if (heat < getMaxHeat()) {
                            burnTime = burnTime - 1;
                            heat = heat + 1;
                            burnLastTime = 10 * 20;
                            update = true;
                        }
                    }
                }

                if (burnTime <= 0) {
                    burnMaxTime = 0;
                    update = true;
                }
            }

            if (getTank().getFluid().getFluid().is(WizardsRebornFluidTags.STEAM_SOURCE)) {
                for (int i = 0; i < 5; i++) {
                    if (steam < getMaxSteam() && heat > 0 && getFluidAmount() > 0) {
                        getTank().drain(1, IFluidHandler.FluidAction.EXECUTE);
                        heat = heat - 1;
                        steam = steam + 1;
                        heatLastTime = 10 * 20;
                        if (random.nextFloat() < 0.001F) {
                            level.playSound(null, getBlockPos(), WizardsRebornSounds.STEAM_BURST.get(), SoundSource.BLOCKS, 0.1f, 1.0f);
                        }
                        update = true;
                    }
                }

                if (heat <= 0) {
                    cookMaxTime = 0;
                    cookTime = 0;
                    update = true;
                }
            }

            if (getTank().getFluid().getFluid().is(WizardsRebornFluidTags.STEAM_EQUIVALENT)) {
                for (int i = 0; i < 5; i++) {
                    if (steam < getMaxSteam() && getFluidAmount() > 0) {
                        getTank().drain(1, IFluidHandler.FluidAction.EXECUTE);
                        steam = steam + 1;
                        if (random.nextFloat() < 0.001F) {
                            level.playSound(null, getBlockPos(), WizardsRebornSounds.STEAM_BURST.get(), SoundSource.BLOCKS, 0.1f, 1.0f);
                        }
                        update = true;
                    }
                }
            }

            if (getTank().getFluid().getFluid().is(WizardsRebornFluidTags.HEAT_SOURCE)) {
                if (heat - 20 < getMaxHeat() && getFluidAmount() > 0) {
                    getTank().drain(1, IFluidHandler.FluidAction.EXECUTE);
                    heat = heat + 20;
                    heatLastTime = 10 * 20;
                    update = true;
                }
            }

            if (burnTime <= 0) {
                if (isFuel(itemFuelHandler.getStackInSlot(0))) {
                    int burn = getBurnDuration(itemFuelHandler.getStackInSlot(0));
                    burnMaxTime = burn;
                    burnTime = burn;
                    burnLastTime = 10 * 20;
                    heatLastTime = 10 * 20;

                    ItemStack itemstack = itemFuelHandler.getStackInSlot(0);

                    if (itemstack.hasCraftingRemainingItem()) {
                        itemFuelHandler.setStackInSlot(0, itemstack.getCraftingRemainingItem());
                    } else if (!itemFuelHandler.getStackInSlot(0).isEmpty()) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            itemFuelHandler.setStackInSlot(0, itemstack.getCraftingRemainingItem());
                        }
                    }
                    update = true;
                }
            }

            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, itemHandler.getStackInSlot(0));

            if (!inv.isEmpty()) {
                Optional<SmeltingRecipe> recipe = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, inv, level);
                cookMaxTime = recipe.map(SmeltingRecipe::getCookingTime).orElse(200);

                if (recipe.isPresent()) {
                    if (lastRecipe != null) {
                        if (lastRecipe.isPresent()) {
                            if (recipe.get().getId() != lastRecipe.get().getId()) {
                                cookMaxTime = 0;
                                cookTime = 0;
                                update = true;
                            }
                        }
                    }

                    if (canBurn(RegistryAccess.EMPTY, recipe.get(), 64)) {
                        if (cookMaxTime > 0 && heat > 0) {
                            cookTime = cookTime + 1;
                            heat = heat - 1;
                            heatLastTime = 10 * 20;
                            update = true;
                        }

                        if (cookMaxTime > 0) {
                            if (cookTime >= cookMaxTime) {
                                if (burn(RegistryAccess.EMPTY, recipe.get(), 64)) {
                                    cookMaxTime = 0;
                                    cookTime = 1;
                                    exp = exp + recipe.get().getExperience();
                                    update = true;
                                }
                            }
                        }
                    }

                    lastRecipe = recipe;
                } else {
                    cookMaxTime = 0;
                    cookTime = 0;
                    update = true;
                }
            } else if (cookMaxTime != 0 || cookTime != 0) {
                cookMaxTime = 0;
                cookTime = 0;
                update = true;
            }

            if (flag != isLit()) {
                BlockState blockState = getBlockState().setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(isLit()));
                level.setBlock(getBlockPos(), blockState, 3);
                update = true;
            }

            if (update) setChanged();
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
                CombinedInvWrapper item = new CombinedInvWrapper(itemHandler, itemFuelHandler);
                CombinedInvWrapper item1 = new CombinedInvWrapper(item, itemOutputHandler);
                return LazyOptional.of(() -> item1).cast();
            }

            if (side == Direction.DOWN) {
                return outputHandler.cast();
            } else if (getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getClockWise() == side ||
                    getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getClockWise().getOpposite() == side) {
                return fuelHandler.cast();
            } else {
                return handler.cast();
            }
        }

        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            if (side == this.getBlockState().getValue(HORIZONTAL_FACING).getOpposite()) {
                return ForgeCapabilities.FLUID_HANDLER.orEmpty(cap, fluidHolder);
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public Component getDefaultName() {
        return Component.translatable("gui.wizards_reborn.alchemy_furnace.title");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new AlchemyFurnaceContainer(i, level, getBlockPos(), inventory, player);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inv", itemHandler.serializeNBT());
        tag.put("fuel", itemFuelHandler.serializeNBT());
        tag.put("output", itemOutputHandler.serializeNBT());

        tag.putInt("burnLastTime", burnLastTime);
        tag.putInt("heatLastTime", heatLastTime);
        tag.putInt("burnMaxTime", burnMaxTime);
        tag.putInt("burnTime", burnTime);
        tag.putInt("cookMaxTime", cookMaxTime);
        tag.putInt("cookTime", cookTime);
        tag.putFloat("exp", exp);

        tag.putInt("steam", steam);
        tag.putInt("heat", heat);

        tag.put("fluidTank", fluidTank.writeToNBT(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inv"));
        itemFuelHandler.deserializeNBT(tag.getCompound("fuel"));
        itemOutputHandler.deserializeNBT(tag.getCompound("output"));

        burnLastTime = tag.getInt("burnLastTime");
        heatLastTime = tag.getInt("heatLastTime");
        burnMaxTime = tag.getInt("burnMaxTime");
        burnTime = tag.getInt("burnTime");
        cookMaxTime = tag.getInt("cookMaxTime");
        cookTime = tag.getInt("cookTime");
        exp = tag.getFloat("exp");

        steam = tag.getInt("steam");
        heat = tag.getInt("heat");

        fluidTank.readFromNBT(tag.getCompound("fluidTank"));
    }

    public boolean isFuel(ItemStack stack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
    }

    public int getBurnDuration(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        } else {
            Item item = fuel.getItem();
            return net.minecraftforge.common.ForgeHooks.getBurnTime(fuel, RecipeType.SMELTING);
        }
    }

    public boolean canBurn(RegistryAccess registryAccess, SmeltingRecipe recipe, int maxStackSize) {
        if (!itemHandler.getStackInSlot(0).isEmpty() && recipe != null) {
            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, itemHandler.getStackInSlot(0));

            ItemStack itemstack = recipe.assemble(inv, registryAccess);
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = itemOutputHandler.getStackInSlot(0);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!ItemStack.isSameItem(itemstack1, itemstack)) {
                    return false;
                } else if (itemstack1.getCount() + itemstack.getCount() <= maxStackSize && itemstack1.getCount() + itemstack.getCount() <= itemstack1.getMaxStackSize()) {
                    return true;
                } else {
                    return itemstack1.getCount() + itemstack.getCount() <= itemstack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    public boolean burn(RegistryAccess registryAccess, SmeltingRecipe recipe, int maxStackSize) {
        if (recipe != null && this.canBurn(registryAccess, recipe, maxStackSize)) {
            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, itemHandler.getStackInSlot(0));

            ItemStack itemstack = itemHandler.getStackInSlot(0);
            ItemStack itemstack1 = recipe.assemble(inv, registryAccess);
            ItemStack itemstack2 = itemOutputHandler.getStackInSlot(0);
            if (itemstack2.isEmpty()) {
                itemOutputHandler.setStackInSlot(0, itemstack1.copy());
            } else if (itemstack2.is(itemstack1.getItem())) {
                itemstack2.grow(itemstack1.getCount());
            }

            if (itemstack.is(Blocks.WET_SPONGE.asItem()) && !itemFuelHandler.getStackInSlot(0).isEmpty() && itemFuelHandler.getStackInSlot(0).is(Items.BUCKET)) {
                itemFuelHandler.setStackInSlot(0, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.shrink(1);
            return true;
        } else {
            return false;
        }
    }

    public void popExperience(ServerPlayer player) {
        createExperience(player.serverLevel(), player.getPosition(0), (int) exp);
        exp = exp - ((int) exp);
        BlockEntityUpdate.packet(this);
    }

    public void popExperience(ServerLevel level, Vec3 popVec) {
        createExperience(level, popVec, (int) exp);
        exp = exp - ((int) exp);
        BlockEntityUpdate.packet(this);
    }

    public static void createExperience(ServerLevel level, Vec3 popVec, int experience) {
        ExperienceOrb.award(level, popVec, experience);
    }

    public boolean isLit() {
        return cookTime > 0 || burnTime > 0 || burnMaxTime > 0;
    }

    public int getMaxCapacity() {
        return 10000;
    }

    public int getCapacity() {
        return fluidTank.getCapacity();
    }

    @Override
    public FluidStack getFluidStack() {
        return fluidTank.getFluid();
    }

    public FluidTank getTank() {
        return fluidTank;
    }

    @Override
    public int getFluidAmount() {
        return getFluidStack().getAmount();
    }

    @Override
    public int getFluidMaxAmount() {
        return getMaxCapacity();
    }

    @Override
    public int getSteam() {
        return steam;
    }

    @Override
    public int getMaxSteam() {
        return 10000;
    }

    @Override
    public void setSteam(int steam) {
        this.steam = steam;
    }

    @Override
    public void addSteam(int steam) {
        this.steam = this.steam + steam;
        if (this.steam > getMaxSteam()) {
            this.steam = getMaxSteam();
        }
    }

    @Override
    public void removeSteam(int steam) {
        this.steam = this.steam - steam;
        if (this.steam < 0) {
            this.steam = 0;
        }
    }

    @Override
    public boolean canSteamTransfer(Direction side) {
        return true;
    }

    @Override
    public boolean canSteamConnection(Direction side) {
        return (side == Direction.UP);
    }

    @Override
    public int getHeat() {
        return heat;
    }

    @Override
    public int getMaxHeat() {
        return 10000;
    }

    @Override
    public void setHeat(int heat) {
        this.heat = heat;
    }

    @Override
    public void addHeat(int heat) {
        this.heat = this.heat + heat;
        if (this.heat > getMaxHeat()) {
            this.heat = getMaxHeat();
        }
    }

    @Override
    public void removeHeat(int heat) {
        this.heat = this.heat - heat;
        if (this.heat < 0) {
            this.heat = 0;
        }
    }

    @Override
    public List<ItemStack> getItemsResult() {
        List<ItemStack> list = new ArrayList<>();

        SimpleContainer inv = new SimpleContainer(1);
        inv.setItem(0, itemHandler.getStackInSlot(0));

        Optional<SmeltingRecipe> recipe = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, inv, level);
        if (recipe.isPresent()) {
            ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();
            list.add(stack);
        }

        return list;
    }
}
