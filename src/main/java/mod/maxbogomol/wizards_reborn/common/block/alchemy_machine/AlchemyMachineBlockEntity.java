package mod.maxbogomol.wizards_reborn.common.block.alchemy_machine;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.api.alchemy.SteamUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IItemResultBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.client.gui.container.AlchemyMachineContainer;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_boiler.AlchemyBoilerBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyBottleItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyPotionItem;
import mod.maxbogomol.wizards_reborn.common.recipe.AlchemyMachineContext;
import mod.maxbogomol.wizards_reborn.common.recipe.AlchemyMachineRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlchemyMachineBlockEntity extends PipeBaseBlockEntity implements TickableBlockEntity, IWissenWandFunctionalBlockEntity, ICooldownBlockEntity, IItemResultBlockEntity, MenuProvider, Nameable {
    protected FluidTank fluidTank1 = new FluidTank(getMaxCapacity()) {
        @Override
        public void onContentsChanged() {
            AlchemyMachineBlockEntity.this.setChanged();
        }
    };
    protected FluidTank fluidTank2 = new FluidTank(getMaxCapacity()) {
        @Override
        public void onContentsChanged() {
            AlchemyMachineBlockEntity.this.setChanged();
        }
    };
    protected FluidTank fluidTank3 = new FluidTank(getMaxCapacity()) {
        @Override
        public void onContentsChanged() {
            AlchemyMachineBlockEntity.this.setChanged();
        }
    };
    public LazyOptional<IFluidHandler> fluidHolder1 = LazyOptional.of(() -> fluidTank1);
    public LazyOptional<IFluidHandler> fluidHolder2 = LazyOptional.of(() -> fluidTank2);
    public LazyOptional<IFluidHandler> fluidHolder3 = LazyOptional.of(() -> fluidTank3);

    public final ItemStackHandler itemHandler = createHandler(6);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final ItemStackHandler itemOutputHandler = createHandler(1);
    public final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() -> itemOutputHandler);

    public static Direction[] directions = {
            Direction.NORTH,
            Direction.SOUTH,
            Direction.WEST,
            Direction.EAST
    };

    public int wissenInCraft = 0;
    public int wissenIsCraft = 0;
    public int steamInCraft = 0;
    public int steamIsCraft = 0;
    public boolean startCraft = false;

    @Nullable
    public Component name;

    public AlchemyMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public AlchemyMachineBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.ALCHEMY_MACHINE.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            initConnections();

            boolean update = false;

            if (level.getBlockEntity(getBlockPos().above()) instanceof AlchemyBoilerBlockEntity boiler) {
                SimpleContainer inv = new SimpleContainer(7);
                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    inv.setItem(i, itemHandler.getStackInSlot(i));
                }
                inv.setItem(6, itemOutputHandler.getStackInSlot(0));

                if (!inv.isEmpty()) {
                    AlchemyMachineContext context = new AlchemyMachineContext(inv, fluidTank1, fluidTank2, fluidTank3);
                    Optional<AlchemyMachineRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.ALCHEMY_MACHINE.get(), context, level);
                    wissenInCraft = recipe.map(AlchemyMachineRecipe::getWissen).orElse(0);
                    steamInCraft = recipe.map(AlchemyMachineRecipe::getSteam).orElse(0);

                    if ((wissenInCraft <= 0 && (wissenIsCraft > 0 || startCraft)) && (steamInCraft <= 0 && (steamIsCraft > 0 || startCraft))) {
                        wissenIsCraft = 0;
                        steamIsCraft = 0;
                        startCraft = false;

                        update = true;
                    }

                    if (recipe.isPresent()) {
                        int filled = boiler.getTank().fill(recipe.get().getFluidResult(), IFluidHandler.FluidAction.SIMULATE);
                        boolean isCanFluid = filled == recipe.get().getFluidResult().getAmount();
                        if (startCraft) {
                            ItemStack output = recipe.get().getResultItem(RegistryAccess.EMPTY);

                            if (((wissenInCraft > 0) && (boiler.wissen > 0)) && isCanCraft(inv, output, recipe.get(), isCanFluid)) {
                                int addRemainCraft = WissenUtil.getAddWissenRemain(wissenIsCraft, 6, wissenInCraft);
                                int removeRemain = WissenUtil.getRemoveWissenRemain(boiler.getWissen(), 6 - addRemainCraft);

                                wissenIsCraft = wissenIsCraft + (6 - addRemainCraft - removeRemain);
                                boiler.removeWissen(6 - addRemainCraft - removeRemain);

                                update = true;
                            }

                            if (random.nextFloat() < 0.01F) {
                                level.playSound(null, getBlockPos(), SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 0.6f, 1.0f);
                            }
                        } else {
                            wissenIsCraft = 0;
                            startCraft = false;

                            update = true;
                        }

                        if ((steamInCraft > 0) && (boiler.steam > 0) && (startCraft)) {
                            ItemStack output = recipe.get().getResultItem(RegistryAccess.EMPTY);

                            if (isCanCraft(inv, output, recipe.get(), isCanFluid)) {
                                int addRemainCraft = SteamUtil.getAddSteamRemain(steamIsCraft, 3, steamInCraft);
                                int removeRemain = SteamUtil.getRemoveSteamRemain(boiler.getSteam(), 3 - addRemainCraft);

                                steamIsCraft = steamIsCraft + (3 - addRemainCraft - removeRemain);
                                boiler.removeSteam(3 - addRemainCraft - removeRemain);

                                update = true;
                            }

                            if (random.nextFloat() < 0.01F) {
                                level.playSound(null, getBlockPos(), WizardsRebornSounds.STEAM_BURST.get(), SoundSource.BLOCKS, 0.1f, 1.0f);
                            }
                        }

                        if ((wissenInCraft > 0 || steamInCraft > 0) && startCraft) {
                            if (wissenInCraft <= wissenIsCraft && steamInCraft <= steamIsCraft) {
                                ItemStack output = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();

                                if (isCanCraft(inv, output, recipe.get(), isCanFluid)) {
                                    wissenInCraft = 0;
                                    wissenIsCraft = 0;
                                    steamInCraft = 0;
                                    steamIsCraft = 0;
                                    startCraft = false;

                                    output.setCount(itemOutputHandler.getStackInSlot(0).getCount() + output.getCount());
                                    if (!AlchemyPotionUtil.isEmpty(recipe.get().getAlchemyPotion())) {
                                        output = getAlchemyBottle();
                                        AlchemyPotionUtil.setPotion(output, recipe.get().getAlchemyPotion());
                                    }

                                    itemOutputHandler.setStackInSlot(0, output);

                                    for (int i = 0; i < 6; i++) {
                                        if (itemHandler.getStackInSlot(0).hasCraftingRemainingItem()) {
                                            itemHandler.setStackInSlot(0, itemHandler.getStackInSlot(0).getCraftingRemainingItem());
                                        } else {
                                            itemHandler.extractItem(i, 1, false);
                                        }
                                    }

                                    boolean ft1 = false;
                                    boolean ft2 = false;
                                    boolean ft3 = false;

                                    for (int i = 0; i < recipe.get().getFluidInputs().size(); i++) {
                                        for (FluidStack fluidStack : recipe.get().getFluidInputs().get(i).getFluids()) {
                                            if (fluidTank1.getFluid().isFluidEqual(fluidStack) & !ft1) {
                                                FluidStack extracted = fluidTank1.drain(fluidStack, IFluidHandler.FluidAction.SIMULATE);
                                                fluidTank1.drain(extracted, IFluidHandler.FluidAction.EXECUTE);
                                                ft1 = true;
                                            }

                                            if (fluidTank2.getFluid().isFluidEqual(fluidStack) & !ft2) {
                                                FluidStack extracted = fluidTank2.drain(fluidStack, IFluidHandler.FluidAction.SIMULATE);
                                                fluidTank2.drain(extracted, IFluidHandler.FluidAction.EXECUTE);
                                                ft2 = true;
                                            }

                                            if (fluidTank3.getFluid().isFluidEqual(fluidStack) & !ft3) {
                                                FluidStack extracted = fluidTank3.drain(fluidStack, IFluidHandler.FluidAction.SIMULATE);
                                                fluidTank3.drain(extracted, IFluidHandler.FluidAction.EXECUTE);
                                                ft3 = true;
                                            }
                                        }
                                    }

                                    if (!recipe.get().getFluidResult().isEmpty()) {
                                        if (boiler.getTank().getFluid().isFluidEqual(recipe.get().getFluidResult()) || boiler.getTank().isEmpty()) {
                                            boiler.getTank().fill(recipe.get().getFluidResult(), IFluidHandler.FluidAction.EXECUTE);
                                        }
                                    }

                                    level.playSound(null, getBlockPos(), WizardsRebornSounds.STEAM_BURST.get(), SoundSource.BLOCKS, 0.1f, 1.0f);

                                    update = true;
                                }
                            }
                        }
                    }
                } else {
                    if ((wissenInCraft <= 0 && (wissenIsCraft > 0 || startCraft)) && (steamInCraft <= 0 && (steamIsCraft > 0 || startCraft))) {
                        wissenIsCraft = 0;
                        steamIsCraft = 0;
                        startCraft = false;

                        update = true;
                    }
                }
            }

            if (update) {
                BlockEntityUpdate.packet(this);
                if (level.getBlockEntity(getBlockPos().above()) instanceof AlchemyBoilerBlockEntity boiler) {
                    BlockEntityUpdate.packet(boiler);
                }
            }
        }

        if (level.isClientSide()) {
            if (level.getBlockEntity(getBlockPos().above()) instanceof AlchemyBoilerBlockEntity boiler) {
                if (wissenIsCraft > 0 || steamIsCraft > 0) {
                    if (random.nextFloat() < 0.6F) {
                        ParticleBuilder.create(FluffyFurParticles.SMOKE)
                                .setColorData(ColorParticleData.create(Color.WHITE).build())
                                .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                                .setScaleData(GenericParticleData.create(0.1f, 0.5f).build())
                                .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                                .setLifetime(30)
                                .randomVelocity(0.0125f, 0, 0.0125f)
                                .flatRandomVelocity(0, 0.025f, 0)
                                .addVelocity(0,  0.005, 0)
                                .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 2F, getBlockPos().getZ() + 0.5F);
                    }
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
        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            Direction newSide = getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING).getClockWise();
            if (newSide == side) {
                return ForgeCapabilities.FLUID_HANDLER.orEmpty(cap, fluidHolder1);
            }
            newSide = newSide.getClockWise();
            if (newSide ==side) {
                return ForgeCapabilities.FLUID_HANDLER.orEmpty(cap, fluidHolder2);
            }
            newSide = newSide.getClockWise();
            if (newSide == side) {
                return ForgeCapabilities.FLUID_HANDLER.orEmpty(cap, fluidHolder3);
            }
        }

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
    public Component getName() {
        return name;
    }

    public void setCustomName(Component pName) {
        this.name = pName;
    }

    @Override
    public Component getDisplayName() {
        return this.name != null ? this.name : this.getDefaultName();
    }

    public Component getDefaultName() {
        return Component.translatable("gui.wizards_reborn.alchemy_machine.title");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new AlchemyMachineContainer(i, level, getBlockPos(), inventory, player);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("fluidTank1", fluidTank1.writeToNBT(new CompoundTag()));
        tag.put("fluidTank2", fluidTank2.writeToNBT(new CompoundTag()));
        tag.put("fluidTank3", fluidTank3.writeToNBT(new CompoundTag()));

        tag.put("inv", itemHandler.serializeNBT());
        tag.put("output", itemOutputHandler.serializeNBT());

        tag.putInt("wissenInCraft", wissenInCraft);
        tag.putInt("wissenIsCraft", wissenIsCraft);
        tag.putInt("steamInCraft", steamInCraft);
        tag.putInt("steamIsCraft", steamIsCraft);
        tag.putBoolean("startCraft", startCraft);

        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name));
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        fluidTank1.readFromNBT(tag.getCompound("fluidTank1"));
        fluidTank2.readFromNBT(tag.getCompound("fluidTank2"));
        fluidTank3.readFromNBT(tag.getCompound("fluidTank3"));

        itemHandler.deserializeNBT(tag.getCompound("inv"));
        itemOutputHandler.deserializeNBT(tag.getCompound("output"));

        wissenInCraft = tag.getInt("wissenInCraft");
        wissenIsCraft = tag.getInt("wissenIsCraft");
        steamInCraft = tag.getInt("steamInCraft");
        steamIsCraft = tag.getInt("steamIsCraft");
        startCraft = tag.getBoolean("startCraft");

        if (tag.contains("CustomName", 8)) {
            this.name = Component.Serializer.fromJson(tag.getString("CustomName"));
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 2.5f, pos.getZ() + 1.5f);
    }

    public void initConnections() {
        Block block = level.getBlockState(getBlockPos()).getBlock();
        for (Direction direction : directions) {
            BlockState facingState = level.getBlockState(getBlockPos().relative(direction));
            BlockEntity facingBE = level.getBlockEntity(getBlockPos().relative(direction));
            if (facingState.is(WizardsRebornBlockTags.FLUID_PIPE_CONNECTION)) {
                if (facingBE instanceof PipeBaseBlockEntity && !((PipeBaseBlockEntity) facingBE).getConnection(direction.getOpposite()).transfer) {
                    connections[direction.get3DDataValue()] = PipeConnection.NONE;
                } else {
                    connections[direction.get3DDataValue()] = PipeConnection.PIPE;
                }
            } else {
                connections[direction.get3DDataValue()] = PipeConnection.NONE;
            }
        }
        loaded = true;
        setChanged();
        level.getChunkAt(getBlockPos()).setUnsaved(true);
        level.updateNeighbourForOutputSignal(getBlockPos(), block);
    }

    public int getMaxCapacity() {
        return 5000;
    }

    public int getCapacity(int index) {
        return switch (index) {
            case 0 -> fluidTank1.getCapacity();
            case 1 -> fluidTank2.getCapacity();
            case 2 -> fluidTank3.getCapacity();
            default -> fluidTank1.getCapacity();
        };
    }

    public FluidStack getFluidStack(int index) {
        return switch (index) {
            case 0 -> fluidTank1.getFluid();
            case 1 -> fluidTank2.getFluid();
            case 2 -> fluidTank3.getFluid();
            default -> fluidTank1.getFluid();
        };
    }

    public FluidTank getTank(int index) {
        return switch (index) {
            case 0 -> fluidTank1;
            case 1 -> fluidTank2;
            case 2 -> fluidTank3;
            default -> fluidTank1;
        };
    }

    @Override
    public void wissenWandFunction() {
        startCraft = true;
    }

    public boolean isCanCraft(SimpleContainer inv, ItemStack output, AlchemyMachineRecipe recipe, boolean isCanFluid) {
        if (!AlchemyPotionUtil.isEmpty(recipe.getAlchemyPotionIngredient())) {
            ItemStack bottle = getAlchemyBottle();
            if (bottle.getItem() instanceof AlchemyPotionItem) {
                if (AlchemyPotionUtil.getPotion(bottle) != recipe.getAlchemyPotionIngredient()) {
                    return false;
                }
            } else {
               return false;
            }
        }

        if (recipe.getFluidInputs().size() == 0) {
            for (int i = 0; i < 3; i++) {
                if (!getTank(i).isEmpty()) {
                    return false;
                }
            }
        }

        if (!AlchemyPotionUtil.isEmpty(recipe.getAlchemyPotion())) {
            return true;
        }

        if (!recipe.getFluidInputs().isEmpty()) {
            return isCanFluid;
        }

        if (!recipe.getFluidResult().isEmpty() && !recipe.getResultItem(RegistryAccess.EMPTY).isEmpty()) {
            if (isCanFluid) {
                return isCanCraftItem(inv, output);
            }
        }

        if (!recipe.getFluidResult().isEmpty()) {
            return isCanFluid;
        }

        if (!recipe.getResultItem(RegistryAccess.EMPTY).isEmpty()) {
            return isCanCraftItem(inv, output);
        }

        return false;
    }

    public boolean isCanCraftItem(SimpleContainer inv, ItemStack output) {
        if (inv.getItem(6).isEmpty()) return true;
        return (ItemHandlerHelper.canItemStacksStack(output, inv.getItem(6))) && (inv.getItem(6).getCount() + output.getCount() <= output.getMaxStackSize());
    }

    @Override
    public List<ItemStack> getItemsResult() {
        List<ItemStack> list = new ArrayList<>();

        SimpleContainer inv = new SimpleContainer(7);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        inv.setItem(6, itemOutputHandler.getStackInSlot(0));

        AlchemyMachineContext context = new AlchemyMachineContext(inv, fluidTank1, fluidTank2, fluidTank3);
        Optional<AlchemyMachineRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.ALCHEMY_MACHINE.get(), context, level);
        if (recipe.isPresent()) {
            ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();

            if (level.getBlockEntity(getBlockPos().above()) instanceof AlchemyBoilerBlockEntity boiler) {
                int filled = boiler.getTank().fill(recipe.get().getFluidResult(), IFluidHandler.FluidAction.SIMULATE);
                boolean isCanFluid = filled == recipe.get().getFluidResult().getAmount();

                if (isCanCraft(inv, stack, recipe.get(), isCanFluid)) {
                    if (!stack.isEmpty()) {
                        list.add(stack);
                    } else {
                        if (!AlchemyPotionUtil.isEmpty(recipe.get().getAlchemyPotion())) {
                            ItemStack bottle = getAlchemyBottle();
                            AlchemyPotionUtil.setPotion(bottle, recipe.get().getAlchemyPotion());
                            list.add(bottle);
                        }
                    }

                    if (!recipe.get().getFluidResult().isEmpty()) {
                        list.add(recipe.get().getFluidResult().getFluid().getBucket().getDefaultInstance().copy());
                    }
                }
            }
        }

        return list;
    }

    public ItemStack getAlchemyBottle() {
        ItemStack stack = ItemStack.EMPTY;

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            if (itemHandler.getStackInSlot(i).getItem() instanceof AlchemyBottleItem item) {
                stack = item.getPotionItem();
            }
            if (itemHandler.getStackInSlot(i).getItem() instanceof AlchemyPotionItem item) {
                stack = itemHandler.getStackInSlot(i).copy();
            }
        }

        return stack;
    }

    @Override
    public float getCooldown() {
        if (wissenInCraft > 0 || steamInCraft > 0) {
            return (float) (wissenInCraft + steamInCraft) / (wissenIsCraft + steamIsCraft);
        }
        return 0;
    }
}
