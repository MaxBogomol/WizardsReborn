package mod.maxbogomol.wizards_reborn.common.block.wissen_translator;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.ExposedBlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandControlledBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.WissenSendEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.block.WissenTranslatorBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.block.WissenTranslatorSendEffectPacket;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;

public class WissenTranslatorBlockEntity extends ExposedBlockSimpleInventory implements TickableBlockEntity, IWissenBlockEntity, ICooldownBlockEntity, IWissenWandControlledBlockEntity {

    public int blockFromX = 0;
    public int blockFromY = 0;
    public int blockFromZ = 0;
    public boolean isFromBlock = false;

    public int blockToX = 0;
    public int blockToY = 0;
    public int blockToZ = 0;
    public boolean isToBlock = false;

    public int wissen = 0;
    public int cooldown = 0;

    public CompoundTag wissenRays = new CompoundTag();

    public WissenTranslatorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public WissenTranslatorBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsRebornBlockEntities.WISSEN_TRANSLATOR.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            if (cooldown > 0) {
                cooldown = cooldown - 1;
                update = true;
            }

            boolean setCooldown = false;

            if (isToBlock && isFromBlock) {
                if (isSameFromAndTo()) {
                    isFromBlock = false;
                    isToBlock = false;
                    update = true;
                }
            }

            if (isToBlock) {
                if (level.isLoaded(new BlockPos(blockToX, blockToY, blockToZ))) {
                    BlockEntity blockEntity = level.getBlockEntity(new BlockPos(blockToX, blockToY, blockToZ));
                    if (blockEntity instanceof IWissenBlockEntity wissenBlockEntity) {
                        if (wissenBlockEntity.canReceiveWissen() && (cooldown <= 0) && canWork()) {
                            int removeRemain = WissenUtils.getRemoveWissenRemain(getWissen(), getWissenPerReceive());
                            int addRemain = WissenUtils.getAddWissenRemain(wissenBlockEntity.getWissen(), getWissenPerReceive() - removeRemain, wissenBlockEntity.getMaxWissen());

                            if ((getWissenPerReceive() - removeRemain - addRemain) > 0) {
                                removeWissen(getWissenPerReceive() - removeRemain - addRemain);
                                addWissenRay(getBlockPos(), new BlockPos(blockToX, blockToY, blockToZ), getWissenPerReceive() - removeRemain - addRemain);

                                setCooldown = true;
                                update = true;

                                Color color = getColor();

                                PacketHandler.sendToTracking(level, getBlockPos(), new WissenTranslatorBurstEffectPacket(getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, (float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255));
                                level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsRebornSounds.WISSEN_TRANSFER.get(), SoundSource.BLOCKS, 0.1f, (float) (1.1f + ((random.nextFloat() - 0.5D) / 2)));
                            }
                        }
                    } else {
                        isToBlock = false;
                        update = true;
                    }
                }
            }

            if (isFromBlock) {
                if (level.isLoaded(new BlockPos(blockFromX, blockFromY, blockFromZ))) {
                    BlockEntity blockEntity = level.getBlockEntity(new BlockPos(blockFromX, blockFromY, blockFromZ));
                    if (blockEntity instanceof IWissenBlockEntity wissenBlockEntity) {
                        if (wissenBlockEntity.canSendWissen() && (cooldown <= 0) && canWork()) {
                            int addRemain = WissenUtils.getAddWissenRemain(getWissen(), getWissenPerReceive(), getMaxWissen());
                            int removeRemain = WissenUtils.getRemoveWissenRemain(wissenBlockEntity.getWissen(), getWissenPerReceive() - addRemain);

                            if ((getWissenPerReceive() - removeRemain - addRemain) > 0) {
                                wissenBlockEntity.removeWissen(getWissenPerReceive() - removeRemain - addRemain);
                                addWissenRay(new BlockPos(blockFromX, blockFromY, blockFromZ), getBlockPos(), getWissenPerReceive() - removeRemain - addRemain);
                                BlockEntityUpdate.packet(blockEntity);

                                setCooldown = true;
                                update = true;

                                level.playSound(WizardsReborn.proxy.getPlayer(), blockEntity.getBlockPos(), WizardsRebornSounds.WISSEN_TRANSFER.get(), SoundSource.BLOCKS, 0.1f, (float) (1.1f + ((random.nextFloat() - 0.5D) / 2)));
                            }
                        }
                    } else {
                        isFromBlock = false;
                        update = true;
                    }
                }
            }

            if (wissenRays.size() > 0) {
                updateWissenRays();
                update = true;
            }

            if (setCooldown) {
                cooldown = getSendWissenCooldown();
            }

            if (update) setChanged();
        }

        if (level.isClientSide()) {
            if (getWissen() > 0) {
                Color color = getColor();

                if (random.nextFloat() < 0.5) {
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.2f * getStage(), 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.015f)
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    ParticleBuilder.create(random.nextBoolean() ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.0375f * getStage(), 0.075f * getStage(), 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                            .setLifetime(30)
                            .randomVelocity(0.015f)
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 0.5F, getBlockPos().getZ() + 0.5F);
                }
            }
        }
    }

    @Override
    protected SimpleContainer createItemHandler() {
        return new SimpleContainer(1) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }
        };
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        return stack.is(WizardsRebornItemTags.ARCANE_LUMOS);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, @NotNull ItemStack stack, @Nullable Direction direction) {
        return true;
    }

    public boolean isSameFromAndTo() {
        return ((blockFromX == blockToX) && (blockFromY == blockToY) && (blockFromZ == blockToZ));
    }

    public static boolean isSameFromAndTo(BlockPos posFrom, BlockPos posTo) {
        return ((posFrom.getX() == posTo.getX()) && (posFrom.getY() == posTo.getY()) && (posFrom.getZ() == posTo.getZ()));
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("blockFromX", blockFromX);
        tag.putInt("blockFromY", blockFromY);
        tag.putInt("blockFromZ", blockFromZ);
        tag.putBoolean("isFromBlock", isFromBlock);

        tag.putInt("blockToX", blockToX);
        tag.putInt("blockToY", blockToY);
        tag.putInt("blockToZ", blockToZ);
        tag.putBoolean("isToBlock", isToBlock);

        tag.putInt("wissen", wissen);
        tag.putInt("cooldown", cooldown);

        tag.put("wissenRays", wissenRays);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        blockFromX = tag.getInt("blockFromX");
        blockFromY = tag.getInt("blockFromY");
        blockFromZ = tag.getInt("blockFromZ");
        isFromBlock = tag.getBoolean("isFromBlock");

        blockToX = tag.getInt("blockToX");
        blockToY = tag.getInt("blockToY");
        blockToZ = tag.getInt("blockToZ");
        isToBlock = tag.getBoolean("isToBlock");

        wissen = tag.getInt("wissen");
        cooldown = tag.getInt("cooldown");

        wissenRays = tag.getCompound("wissenRays");
    }

    @Override
    public AABB getRenderBoundingBox() {
        return IForgeBlockEntity.INFINITE_EXTENT_AABB;
    }

    public float getStage() {
        return ((float) getWissen() / (float) getMaxWissen());
    }

    public double getRaySpeed() {
        return 0.1f;
    }

    public int getRayMitting() {
        return 100;
    }

    public int getWissenPerReceive() {
        return 100;
    }

    public int getSendWissenCooldown() {
        return 20;
    }

    @Override
    public int getWissen() {
        return wissen;
    }

    @Override
    public int getMaxWissen() {
        return 1000;
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

    public void addWissenRay(BlockPos posFrom, BlockPos posTo, int wissenCount) {
        CompoundTag tag = new CompoundTag();

        double dX = posTo.getX() - posFrom.getX();
        double dY = posTo.getY() - posFrom.getY();
        double dZ = posTo.getZ() - posFrom.getZ();

        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

        double X = Math.sin(pitch) * Math.cos(yaw) * getRaySpeed();
        double Y = Math.cos(pitch) * getRaySpeed();
        double Z = Math.sin(pitch) * Math.sin(yaw) * getRaySpeed();

        tag.putInt("blockFromX", posFrom.getX());
        tag.putInt("blockFromY", posFrom.getY());
        tag.putInt("blockFromZ", posFrom.getZ());

        tag.putDouble("blockX", posFrom.getX() + 0.5f);
        tag.putDouble("blockY", posFrom.getY() + 0.5f);
        tag.putDouble("blockZ", posFrom.getZ() + 0.5f);

        tag.putDouble("velocityX", -X);
        tag.putDouble("velocityY", -Y);
        tag.putDouble("velocityZ", -Z);

        tag.putInt("wissen", wissenCount);
        tag.putInt("mitting", 0);

        wissenRays.put(String.valueOf(wissenRays.size()), tag);
    }

    public void deleteWissenRay(ArrayList<Integer> indexs) {
        CompoundTag tag = new CompoundTag();

        int ii = 0;

        for (int i = 0; i < wissenRays.size(); i++) {
            if (!indexs.contains(i)) {
                tag.put(String.valueOf(ii), wissenRays.getCompound(String.valueOf(i)));
                ii = ii + 1;
            }
        }
        wissenRays = tag;
    }

    public void updateWissenRays() {
        ArrayList<Integer> deleteRays = new ArrayList<Integer>();

        for (int i = 0; i < wissenRays.size(); i++) {
            CompoundTag tag = wissenRays.getCompound(String.valueOf(i));

            int blockFromX = tag.getInt("blockFromX");
            int blockFromY = tag.getInt("blockFromY");
            int blockFromZ = tag.getInt("blockFromZ");

            double blockX = tag.getDouble("blockX");
            double blockY = tag.getDouble("blockY");
            double blockZ = tag.getDouble("blockZ");

            double X = tag.getDouble("velocityX") + blockX;
            double Y = tag.getDouble("velocityY") + blockY;
            double Z = tag.getDouble("velocityZ") + blockZ;

            if (level.isOutsideBuildHeight(BlockPos.containing(blockX, blockY, blockZ))) {
                deleteRays.add(i);
            }

            if (level.isLoaded(BlockPos.containing(blockX, blockY, blockZ))) {
                tag.putDouble("blockX", X);
                tag.putDouble("blockY", Y);
                tag.putDouble("blockZ", Z);

                Color color = getColor();

                PacketHandler.sendToTracking(level, getBlockPos(), new WissenSendEffectPacket(blockX, blockY, blockZ, X, Y, Z, (float) color.getRed() / 255, (float) color.getGreen()/ 255, (float) color.getBlue() / 255));

                if (tag.getInt("wissen") <= 0) {
                    PacketHandler.sendToTracking(level, getBlockPos(), new WissenTranslatorBurstEffectPacket((float) X, (float) Y, (float) Z, (float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255));
                    deleteRays.add(i);
                } else if ((blockFromX != Mth.floor(blockX)) || blockFromY != Mth.floor(blockY) || (blockFromZ != Mth.floor(blockZ))) {
                    BlockEntity blockEntity = level.getBlockEntity(BlockPos.containing(blockX, blockY, blockZ));
                    if (blockEntity instanceof IWissenBlockEntity) {
                        if ((Math.abs(blockX) % 1F > 0.15F && Math.abs(blockX) % 1F <= 0.85F) &&
                                (Math.abs(blockY) % 1F > 0.15F && Math.abs(blockY) % 1F <= 0.85F) &&
                                (Math.abs(blockZ) % 1F > 0.15F && Math.abs(blockZ) % 1F <= 0.85F)) {
                            IWissenBlockEntity wissenBlockEntity = (IWissenBlockEntity) blockEntity;

                            int addRemain = WissenUtils.getAddWissenRemain(wissenBlockEntity.getWissen(), tag.getInt("wissen"), wissenBlockEntity.getMaxWissen());
                            wissenBlockEntity.addWissen(tag.getInt("wissen") - addRemain);
                            level.playSound(WizardsReborn.proxy.getPlayer(), X, Y, Z, WizardsRebornSounds.WISSEN_TRANSFER.get(), SoundSource.BLOCKS, 0.1f, (float) (1f + ((random.nextFloat() - 0.5D) / 2)));

                            BlockEntityUpdate.packet(blockEntity);

                            PacketHandler.sendToTracking(level, getBlockPos(), new WissenTranslatorBurstEffectPacket((float) X, (float) Y, (float) Z, (float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255));
                            PacketHandler.sendToTracking(level, getBlockPos(), new WissenTranslatorSendEffectPacket(BlockPos.containing(X, Y, Z)));

                            deleteRays.add(i);
                        }
                    } else {
                        BlockPos blockpos = BlockPos.containing(blockX, blockY, blockZ);
                        if (level.getBlockState(blockpos).isCollisionShapeFullBlock(level, blockpos)) {
                            tag.putInt("wissen", tag.getInt("wissen") - 1);
                        }

                        if (tag.getInt("mitting") >= getRayMitting()) {
                            tag.putInt("wissen", tag.getInt("wissen") - 1);
                        }
                        tag.putInt("mitting", tag.getInt("mitting") + 1);
                    }
                }
            }
        }

        if (deleteRays.size() > 0) {
            deleteWissenRay(deleteRays);
        }
    }

    public Color getColor() {
        Color color = new Color(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB());

        if (!getItemHandler().getItem(0).isEmpty()) {
            if (getItemHandler().getItem(0).getItem() instanceof BlockItem blockItem) {
                if (blockItem.getBlock() instanceof ArcaneLumosBlock lumos) {
                    color = ArcaneLumosBlock.getColor(lumos.color);
                }
            }
        }

        return color;
    }

    public boolean canWork() {
        return !level.hasNeighborSignal(getBlockPos());
    }

    @Override
    public float getCooldown() {
        if (cooldown > 0) {
            return (float) getSendWissenCooldown() / cooldown;
        }
        return 0;
    }

    @Override
    public boolean wissenWandReceiveConnect(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
        BlockPos oldBlockPos = WissenWandItem.getBlockPos(stack);
        BlockEntity oldBlockEntity = level.getBlockEntity(oldBlockPos);

        if (oldBlockEntity instanceof IWissenBlockEntity wissenBlockEntity) {
            if ((!isSameFromAndTo(getBlockPos(), oldBlockPos)) && (wissenBlockEntity.canConnectReceiveWissen())) {
                blockFromX = oldBlockPos.getX();
                blockFromY = oldBlockPos.getY();
                blockFromZ = oldBlockPos.getZ();
                isFromBlock = true;
                WissenWandItem.setBlock(stack, false);
                BlockEntityUpdate.packet(this);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean wissenWandSendConnect(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
        BlockPos oldBlockPos = WissenWandItem.getBlockPos(stack);
        BlockEntity oldBlockEntity = level.getBlockEntity(oldBlockPos);

        if (oldBlockEntity instanceof IWissenBlockEntity wissenBlockEntity) {
            if ((!isSameFromAndTo(getBlockPos(), oldBlockPos)) && (wissenBlockEntity.canConnectSendWissen())) {
                blockToX = oldBlockPos.getX();
                blockToY = oldBlockPos.getY();
                blockToZ = oldBlockPos.getZ();
                isToBlock = true;
                WissenWandItem.setBlock(stack, false);
                BlockEntityUpdate.packet(this);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean wissenWandReload(ItemStack stack, UseOnContext context, BlockEntity blockEntity) {
        isFromBlock = false;
        isToBlock = false;
        BlockEntityUpdate.packet(this);
        return true;
    }
}
