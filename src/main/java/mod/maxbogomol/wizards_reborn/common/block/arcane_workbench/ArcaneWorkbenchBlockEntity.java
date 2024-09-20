package mod.maxbogomol.wizards_reborn.common.block.arcane_workbench;

import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.options.ItemParticleOptions;
import mod.maxbogomol.fluffy_fur.common.block.entity.NameableBlockEntityBase;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.client.gui.container.ArcaneWorkbenchContainer;
import mod.maxbogomol.wizards_reborn.client.sound.ArcaneWorkbenchSoundInstance;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.block.ArcaneWorkbenchBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneWorkbenchRecipe;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornParticles;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
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
import org.joml.Vector3f;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class ArcaneWorkbenchBlockEntity extends NameableBlockEntityBase implements TickableBlockEntity, IWissenBlockEntity, ICooldownBlockEntity, IWissenWandFunctionalBlockEntity, IItemResultBlockEntity {
    public final ItemStackHandler itemHandler = createHandler(13);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final ItemStackHandler itemOutputHandler = createHandler(1);
    public final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() -> itemOutputHandler);

    public int wissenInCraft= 0;
    public int wissenIsCraft = 0;
    public boolean startCraft = false;

    public int wissen = 0;

    public ArcaneWorkbenchSoundInstance sound;

    public ArcaneWorkbenchBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ArcaneWorkbenchBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.ARCANE_WORKBENCH.get(), pos, state);
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

            if (!inv.isEmpty()) {
                Optional<ArcaneWorkbenchRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.ARCANE_WORKBENCH.get(), inv, level);
                wissenInCraft = recipe.map(ArcaneWorkbenchRecipe::getRecipeWissen).orElse(0);

                if (wissenInCraft <= 0 && (wissenIsCraft > 0 || startCraft)) {
                    wissenIsCraft = 0;
                    startCraft = false;

                    update = true;
                }

                if (wissenInCraft > 0 && startCraft) {
                    ItemStack output = recipe.get().getResultItem(RegistryAccess.EMPTY);
                    if (!isCanCraft(inv, output)) {
                        wissenIsCraft = 0;
                        startCraft = false;

                        update = true;
                    }
                }

                if ((wissenInCraft > 0) && (wissen > 0) && (startCraft)) {
                    ItemStack output = recipe.get().getResultItem(RegistryAccess.EMPTY);

                    if (isCanCraft(inv, output)) {
                        if (wissenIsCraft == 0) {
                            level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.ARCANE_WORKBENCH_START.get(), SoundSource.BLOCKS, 1f, 1f);
                        }

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
                            level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.ARCANE_WORKBENCH_END.get(), SoundSource.BLOCKS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                        }
                    }
                }
            } else if (wissenInCraft != 0 || startCraft) {
                wissenInCraft = 0;
                wissenIsCraft = 0;
                startCraft = false;
                update = true;
            }

            if (update) setChanged();
        }

        if (level.isClientSide()) {
            if (getWissen() > 0) {
                if (random.nextFloat() < 0.5) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.3f * getStage(), 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.015f * getStage())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 1.5F, getBlockPos().getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f * getStage(), 0.1f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.015f * getStage())
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 1.5F, getBlockPos().getZ() + 0.5F);
                }
            }

            if (wissenInCraft > 0 && startCraft) {
                if (random.nextFloat() < 0.2) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(random.nextFloat(), random.nextFloat(), random.nextFloat()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f * getStage(), 0).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 1.5F, getBlockPos().getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(random.nextFloat(), random.nextFloat(), random.nextFloat()).build())
                            .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f * getStage(), 0.1f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.1f).build())
                            .setLifetime(65)
                            .randomVelocity(0.0125f)
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 1.5F, getBlockPos().getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.2) {
                    double X = ((random.nextDouble() - 0.5D) * 0.5);
                    double Z = ((random.nextDouble() - 0.5D) * 0.5);
                    ParticleBuilder.create(WizardsRebornParticles.KARMA)
                            .setColorData(ColorParticleData.create(0.733f, 0.564f, 0.937f).build())
                            .setTransparencyData(GenericParticleData.create(0.1f, 0.5f, 0).setEasing(Easing.EXPO_IN, Easing.QUINTIC_IN_OUT).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.EXPO_IN, Easing.QUINTIC_IN_OUT).build())
                            .setLifetime(25)
                            .addVelocity(-(X / 100), (random.nextDouble() / 20), -(Z / 100))
                            .spawn(level, getBlockPos().getX() + 0.5F + X, getBlockPos().getY() + 1F, getBlockPos().getZ() + 0.5F + Z);
                }

                int x = -1;
                int y = -1;
                for (int i = 0; i < 9; i += 1) {
                    ItemStack stack = itemHandler.getStackInSlot(i);

                    if (random.nextFloat() < 0.1) {
                        if (!stack.isEmpty()) {
                            ItemParticleOptions options = new ItemParticleOptions(FluffyFurParticles.ITEM.get(), stack);

                            float rotate = getBlockRotate();

                            Vector3f vector3f = new Vector3f(x, 0, y);
                            vector3f.rotate(Axis.YP.rotationDegrees(rotate));
                            float xx = vector3f.x();
                            float yy = vector3f.z();

                            ParticleBuilder.create(options)
                                    .setRenderType(FluffyFurRenderTypes.ADDITIVE_BLOCK_PARTICLE)
                                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                                    .setTransparencyData(GenericParticleData.create(0.2f, 0.5f, 0).setEasing(Easing.EXPO_IN, Easing.ELASTIC_OUT).build())
                                    .setScaleData(GenericParticleData.create(0.025f, 0.05f, 0).setEasing(Easing.EXPO_IN, Easing.ELASTIC_OUT).build())
                                    .setSpinData(SpinParticleData.create().randomSpin(0.2f).build())
                                    .setLifetime(20)
                                    .addVelocity(xx / 100f, 0.02f, yy / 100f)
                                    .spawn(level, getBlockPos().getX() + 0.5F + (-0.1875F * xx), getBlockPos().getY() + 1.125F, getBlockPos().getZ() + 0.5F + (-0.1875F * yy));
                        }
                    }

                    x = x + 1;
                    if (x > 1) {
                        y = y + 1;
                        x = -1;
                    }
                }

                for (int i = 0; i < 4; i += 1) {
                    ItemStack stack = itemHandler.getStackInSlot(i + 9);

                    if (random.nextFloat() < 0.1) {
                        if (!stack.isEmpty()) {
                            ItemParticleOptions options = new ItemParticleOptions(FluffyFurParticles.ITEM.get(), stack);

                            float rotate = -90F * i + getBlockRotate() - 90F;

                            Vector3f vector3f = new Vector3f(0.375F, 0, 0);
                            vector3f.rotate(Axis.YP.rotationDegrees(rotate));
                            float xx = vector3f.x();
                            float yy = vector3f.z();

                            ParticleBuilder.create(options)
                                    .setRenderType(FluffyFurRenderTypes.ADDITIVE_BLOCK_PARTICLE)
                                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                                    .setTransparencyData(GenericParticleData.create(0.2f, 0.5f, 0).setEasing(Easing.EXPO_IN, Easing.ELASTIC_OUT).build())
                                    .setScaleData(GenericParticleData.create(0.025f, 0.05f, 0).setEasing(Easing.EXPO_IN, Easing.ELASTIC_OUT).build())
                                    .setSpinData(SpinParticleData.create().randomSpin(0.2f).build())
                                    .setLifetime(20)
                                    .addVelocity(-xx / 20f, 0.018f, -yy / 20f)
                                    .spawn(level, getBlockPos().getX() + 0.5F + xx, getBlockPos().getY() + 1.125F, getBlockPos().getZ() + 0.5F + yy);
                        }
                    }
                }

                if (sound == null) {
                    sound = ArcaneWorkbenchSoundInstance.getSound(this);
                    sound.playSound();
                } else if (sound.isStopped()) {
                    sound = ArcaneWorkbenchSoundInstance.getSound(this);
                    sound.playSound();
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
    public Component getDefaultName() {
        return Component.translatable("gui.wizards_reborn.arcane_workbench.title");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ArcaneWorkbenchContainer(i, level, getBlockPos(), inventory, player);
    }

    public float getBlockRotate() {
        return switch (this.getBlockState().getValue(HORIZONTAL_FACING)) {
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

    public boolean isCanCraft(SimpleContainer inv, ItemStack output) {
        if (inv.getItem(13).isEmpty()) return true;
        return (ItemHandlerHelper.canItemStacksStack(output, inv.getItem(13))) && (inv.getItem(13).getCount() + output.getCount() <= output.getMaxStackSize());
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
    public void wissenWandFunction() {
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

        Optional<ArcaneWorkbenchRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.ARCANE_WORKBENCH.get(), inv, level);
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
