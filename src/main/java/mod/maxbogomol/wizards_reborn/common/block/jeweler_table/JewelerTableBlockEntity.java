package mod.maxbogomol.wizards_reborn.common.block.jeweler_table;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.BlockEntityBase;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.SkinTrimItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.JewelerTableBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.JewelerTableRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
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

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class JewelerTableBlockEntity extends BlockEntityBase implements TickableBlockEntity, IWissenBlockEntity, ICooldownBlockEntity, IWissenWandFunctionalBlockEntity, IItemResultBlockEntity {
    public final ItemStackHandler itemHandler = createHandler(2);
    public final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    public final ItemStackHandler itemOutputHandler = createHandler(1);
    public final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() -> itemOutputHandler);

    public int wissenInCraft= 0;
    public int wissenIsCraft = 0;
    public boolean startCraft = false;
    public int stoneRotate = 0;
    public int stoneSpeed = 0;

    public int wissen = 0;

    public JewelerTableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public JewelerTableBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.JEWELER_TABLE.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            SimpleContainer inv = new SimpleContainer(3);
            for (int i = 0; i < itemHandler.getSlots(); i++) {
                inv.setItem(i, itemHandler.getStackInSlot(i));
            }
            inv.setItem(2, itemOutputHandler.getStackInSlot(0));

            if (!inv.isEmpty()) {
                Optional<JewelerTableRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.JEWELER_TABLE.get(), inv, level);
                wissenInCraft = recipe.map(JewelerTableRecipe::getRecipeWissen).orElse(0);

                Skin skin = getSkin();
                if (skin != null) wissenInCraft = 250;

                if (wissenInCraft <= 0 && (wissenIsCraft > 0 || startCraft)) {
                    wissenIsCraft = 0;
                    startCraft = false;

                    update = true;
                }

                if ((wissenInCraft > 0) && (wissen > 0) && (startCraft)) {
                    ItemStack output = ItemStack.EMPTY;
                    if (recipe.isPresent()) output = recipe.get().getResultItem(RegistryAccess.EMPTY);

                    if (isCanCraft(inv, output) || skin != null) {
                        int addRemainCraft = WissenUtils.getAddWissenRemain(wissenIsCraft, getWissenPerTick(), wissenInCraft);
                        int removeRemain = WissenUtils.getRemoveWissenRemain(getWissen(), getWissenPerTick() - addRemainCraft);

                        wissenIsCraft = wissenIsCraft + (getWissenPerTick() - addRemainCraft - removeRemain);
                        removeWissen(getWissenPerTick() - addRemainCraft - removeRemain);

                        update = true;

                        if (random.nextFloat() < 0.05) {
                            level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 0.3f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
                        }
                    }
                }

                if (wissenInCraft > 0 && startCraft) {
                    if (wissenInCraft <= wissenIsCraft) {
                        boolean particles = false;
                        if (recipe.isPresent()) {
                            ItemStack output = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();

                            if (isCanCraft(inv, output)) {
                                wissenInCraft = 0;
                                wissenIsCraft = 0;
                                startCraft = false;

                                output.setCount(itemOutputHandler.getStackInSlot(0).getCount() + output.getCount());
                                if (recipe.get().getRecipeIsSaveNBT()) {
                                    output.setTag(itemHandler.getStackInSlot(0).getOrCreateTag());
                                }

                                itemOutputHandler.setStackInSlot(0, output);

                                for (int i = 0; i < 2; i++) {
                                    itemHandler.extractItem(i, 1, false);
                                }

                                update = true;
                                particles = true;
                            }
                        } else {
                            if (skin != null) {
                                wissenInCraft = 0;
                                wissenIsCraft = 0;
                                startCraft = false;

                                ItemStack output = itemHandler.getStackInSlot(0).copy();

                                output.setCount(itemOutputHandler.getStackInSlot(0).getCount() + output.getCount());
                                output.setTag(itemHandler.getStackInSlot(0).getOrCreateTag());
                                skin.applyOnItem(output);

                                itemOutputHandler.setStackInSlot(0, output);

                                for (int i = 0; i < 2; i++) {
                                    itemHandler.extractItem(i, 1, false);
                                }

                                update = true;
                                particles = true;
                            }
                        }

                        if (particles) {
                            Vec3 pos = getBlockRotatePos();
                            Vec2 vel = getBlockRotateParticle();

                            Color color = Color.WHITE;
                            boolean isColor = false;

                            if (skin != null) {
                                color = skin.getColor();
                                isColor = true;
                            } else if (itemOutputHandler.getStackInSlot(0).getItem() instanceof CrystalItem crystalItem) {
                                color = crystalItem.getType().getColor();
                                isColor = true;
                            }

                            if (isColor) {
                                float r = color.getRed() / 255f;
                                float g = color.getGreen() / 255f;
                                float b = color.getBlue() / 255f;

                                PacketHandler.sendToTracking(level, getBlockPos(), new JewelerTableBurstEffectPacket(getBlockPos(), (float) pos.x(), (float) pos.y(), (float) pos.z(), vel.x, vel.y, r, g, b));
                            } else {
                                PacketHandler.sendToTracking(level, getBlockPos(), new JewelerTableBurstEffectPacket(getBlockPos(), (float) pos.x(), (float) pos.y(), (float) pos.z()));
                            }

                            level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 0.5f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                            level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.CRYSTAL_BREAK.get(), SoundSource.BLOCKS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
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
                Vec3 pos = getBlockRotatePos();
                pos = new Vec3(worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y() + 0.1875F, worldPosition.getZ() + pos.z());

                if (random.nextFloat() < 0.5) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.2f * getStage(), 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.015f)
                            .spawn(level, pos.x(), pos.y(), pos.z());
                }
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0, 0.05f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .randomSpin(0.5f)
                            .setLifetime(30)
                            .randomVelocity(0.015f)
                            .spawn(level, pos.x(), pos.y(), pos.z());
                }
            }

            if (wissenInCraft > 0 && startCraft) {
                Vec3 pos = getBlockRotatePos();
                Vec2 vel = getBlockRotateParticle();
                pos = new Vec3(worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y() - 0.125F, worldPosition.getZ() + pos.z());

                if (stoneSpeed < 35) {
                    stoneSpeed = stoneSpeed + 1;
                }

                stoneRotate = stoneRotate + stoneSpeed;
                stoneRotate = stoneRotate % 360;

                Color color = Color.WHITE;
                boolean isColor = false;

                Skin skin = getSkin();
                if (skin != null) {
                    color = skin.getColor();
                    isColor = true;
                } else if (itemOutputHandler.getStackInSlot(0).getItem() instanceof CrystalItem crystalItem) {
                    color = crystalItem.getType().getColor();
                    isColor = true;
                }

                if (isColor) {
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    if (random.nextFloat() < 0.6) {
                        float x = 0F;
                        float y = 0F;

                        if (vel.x == 0) {
                            x = (float) ((random.nextDouble() - 0.5D) / 20);
                        } else {
                            x = (float) ((random.nextDouble() / 20) * vel.x);
                        }

                        if (vel.y == 0) {
                            y = (float) ((random.nextDouble() - 0.5D) / 20);
                        } else {
                            y = (float) ((random.nextDouble() / 20) * vel.y);
                        }

                        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                                .setColorData(ColorParticleData.create(r, g, b).build())
                                .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                                .setScaleData(GenericParticleData.create(0.2f, 0).build())
                                .randomSpin(0.5f)
                                .setLifetime(30)
                                .addVelocity(x, (random.nextDouble() / 30), y)
                                .spawn(level, pos.x(), pos.y(), pos.z());
                    }
                }
            } else {
                if (stoneSpeed > 0) {
                    stoneSpeed = stoneSpeed - 1;

                    stoneRotate = stoneRotate + stoneSpeed;
                    stoneRotate = stoneRotate % 360;
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

    public Vec3 getBlockRotatePos() {
        switch (this.getBlockState().getValue(HORIZONTAL_FACING)) {
            case NORTH:
                return new Vec3(0.5F, 0.84375F, 0.71875F);
            case SOUTH:
                return new Vec3(0.5F, 0.84375F, 0.28125F);
            case WEST:
                return new Vec3(0.71875F, 0.84375F, 0.5F);
            case EAST:
                return new Vec3(0.28125F, 0.84375F, 0.5F);
            default:
                return new Vec3(0.5F, 0.84375F, 0.5F);
        }
    }

    public Vec2 getBlockRotateParticle() {
        switch (this.getBlockState().getValue(HORIZONTAL_FACING)) {
            case NORTH:
                return new Vec2(0F, -1F);
            case SOUTH:
                return new Vec2(0F, 1F);
            case WEST:
                return new Vec2(-1F, 0F);
            case EAST:
                return new Vec2(1F, 0F);
            default:
                return new Vec2(0F, 0F);
        }
    }

    public float getStage() {
        return ((float) getWissen() / (float) getMaxWissen());
    }

    public boolean isCanCraft(SimpleContainer inv, ItemStack output) {
        if (inv.getItem(2).isEmpty()) {
            return true;
        }

        if ((ItemHandlerHelper.canItemStacksStack(output, inv.getItem(2))) && (inv.getItem(2).getCount() + output.getCount() <= output.getMaxStackSize())) {
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

    public int getWissenPerTick() {
        return 10;
    }

    @Override
    public void wissenWandFunction() {
        startCraft = true;
    }

    @Override
    public float getCooldown() {
        Skin skin = getSkin();
        if (skin != null) wissenInCraft = 250;
        if (wissenInCraft > 0) {
            return (float) wissenInCraft / wissenIsCraft;
        }
        return 0;
    }

    @Override
    public List<ItemStack> getItemsResult() {
        List<ItemStack> list = new ArrayList<>();

        SimpleContainer inv = new SimpleContainer(3);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        inv.setItem(2, itemOutputHandler.getStackInSlot(0));

        Optional<JewelerTableRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.JEWELER_TABLE.get(), inv, level);
        wissenInCraft = recipe.map(JewelerTableRecipe::getRecipeWissen).orElse(0);

        if (recipe.isPresent() && wissenInCraft > 0) {
            ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();
            if (recipe.get().getRecipeIsSaveNBT()) {
                stack.setTag(itemHandler.getStackInSlot(0).copy().getOrCreateTag());
            }
            list.add(stack);
        }

        Skin skin = getSkin();
        if (skin != null) list.add(skin.applyOnItem(itemHandler.getStackInSlot(0).copy()));

        return list;
    }

    public Skin getSkin() {
        if (itemOutputHandler.getStackInSlot(0).isEmpty() && !itemHandler.getStackInSlot(0).isEmpty() && !itemHandler.getStackInSlot(1).isEmpty()) {
            if (itemHandler.getStackInSlot(1).getItem() instanceof SkinTrimItem trim) {
                if (trim.getSkin().canApplyOnItem(itemHandler.getStackInSlot(0))) {
                    Skin skin = Skin.getSkinFromItem(itemHandler.getStackInSlot(0));
                    if (skin != null) {
                        if (skin == trim.getSkin()) return null;
                    }

                    return trim.getSkin();
                }
            }
        }
        return null;
    }
}