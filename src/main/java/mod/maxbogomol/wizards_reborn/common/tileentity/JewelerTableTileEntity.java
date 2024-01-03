package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.*;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.JewelerTableBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.JewelerTableRecipe;
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
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
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
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class JewelerTableTileEntity extends BlockEntity implements TickableBlockEntity, IWissenTileEntity, ICooldownTileEntity, IWissenWandFunctionalTileEntity, IItemResultTileEntity {
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

    public Random random = new Random();

    public JewelerTableTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public JewelerTableTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.JEWELER_TABLE_TILE_ENTITY.get(), pos, state);
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

            Optional<JewelerTableRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.JEWELER_TABLE_RECIPE.get(), inv, level);
            wissenInCraft = recipe.map(JewelerTableRecipe::getRecipeWissen).orElse(0);

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

                        output.setCount(itemOutputHandler.getStackInSlot(0).getCount() + output.getCount());
                        if (recipe.get().getRecipeIsSaveNBT()) {
                            output.setTag(itemHandler.getStackInSlot(0).getOrCreateTag());
                        }

                        itemOutputHandler.setStackInSlot(0, output);

                        for (int i = 0; i < 2; i++) {
                            itemHandler.extractItem(i, 1, false);
                        }

                        update = true;

                        Vec3 pos = getBlockRotatePos();
                        Vec2 vel = getBlockRotateParticle();

                        if (itemOutputHandler.getStackInSlot(0).getItem() instanceof CrystalItem crystalItem) {
                            Color color = crystalItem.getType().getColor();
                            float r = color.getRed() / 255f;
                            float g = color.getGreen() / 255f;
                            float b = color.getBlue() / 255f;

                            PacketHandler.sendToTracking(level, getBlockPos(), new JewelerTableBurstEffectPacket(getBlockPos(), (float) pos.x(), (float) pos.y(), (float) pos.z(), vel.x , vel.y, r, g, b));
                        } else {
                            PacketHandler.sendToTracking(level, getBlockPos(), new JewelerTableBurstEffectPacket(getBlockPos(), (float) pos.x(), (float) pos.y(), (float) pos.z()));
                        }
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
                Vec3 pos = getBlockRotatePos();
                pos = new Vec3(worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y() + 0.1875F, worldPosition.getZ() + pos.z());

                if (random.nextFloat() < 0.5) {
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.2f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f)
                            .setLifetime(20)
                            .spawn(level, pos.x(), pos.y(), pos.z());
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.05f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
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

                if (itemHandler.getStackInSlot(0).getItem() instanceof CrystalItem crystalItem) {

                    Color color = crystalItem.getType().getColor();
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

                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(x, (random.nextDouble() / 30), y)
                                .setAlpha(0.35f, 0).setScale(0.2f, 0)
                                .setColor(r, g, b)
                                .setLifetime(30)
                                .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
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

        SimpleContainer inv = new SimpleContainer(3);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }
        inv.setItem(2, itemOutputHandler.getStackInSlot(0));

        Optional<JewelerTableRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.JEWELER_TABLE_RECIPE.get(), inv, level);
        wissenInCraft = recipe.map(JewelerTableRecipe::getRecipeWissen).orElse(0);

        if (recipe.isPresent() && wissenInCraft > 0) {
            ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();
            if (recipe.get().getRecipeIsSaveNBT()) {
                stack.setTag(itemHandler.getStackInSlot(0).copy().getOrCreateTag());
            }
            list.add(stack);
        }

        return list;
    }
}
