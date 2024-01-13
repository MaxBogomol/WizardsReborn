package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandControlledTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.WissenSendEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.WissenTranslatorBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.WissenTranslatorSendEffectPacket;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class WissenTranslatorTileEntity extends ExposedTileSimpleInventory implements TickableBlockEntity, IWissenTileEntity, ICooldownTileEntity, IWissenWandControlledTileEntity {

    public int blockFromX = 0;
    public int blockFromY = 0;
    public int blockFromZ = 0;
    public boolean isFromBlock = false;

    public int blockToX = 0;
    public int blockToY =0 ;
    public int blockToZ = 0;
    public boolean isToBlock = false;

    public int wissen = 0;
    public int cooldown = 0;

    public CompoundTag wissenRays = new CompoundTag();

    public Random random = new Random();

    public WissenTranslatorTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public WissenTranslatorTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.WISSEN_TRANSLATOR_TILE_ENTITY.get(), pos, state);
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

            if (canWork()) {
                if (isToBlock) {
                    if (cooldown <= 0) {
                        if (level.isLoaded(new BlockPos(blockToX, blockToY, blockToZ))) {
                            BlockEntity tileentity = level.getBlockEntity(new BlockPos(blockToX, blockToY, blockToZ));
                            if (tileentity instanceof IWissenTileEntity) {
                                IWissenTileEntity wissenTileEntity = (IWissenTileEntity) tileentity;

                                int removeRemain = WissenUtils.getRemoveWissenRemain(getWissen(), getWissenPerReceive());
                                int addRemain = WissenUtils.getAddWissenRemain(wissenTileEntity.getWissen(), getWissenPerReceive() - removeRemain, wissenTileEntity.getMaxWissen());

                                if ((getWissenPerReceive() - removeRemain - addRemain) > 0) {
                                    removeWissen(getWissenPerReceive() - removeRemain - addRemain);
                                    addWissenRay(getBlockPos(), new BlockPos(blockToX, blockToY, blockToZ), getWissenPerReceive() - removeRemain - addRemain);

                                    setCooldown = true;
                                    update = true;

                                    Color color = getColor();

                                    PacketHandler.sendToTracking(level, getBlockPos(), new WissenTranslatorBurstEffectPacket(getBlockPos().getX() + 0.5f, getBlockPos().getY() + 0.5f, getBlockPos().getZ() + 0.5f, (float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255));
                                    level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsReborn.WISSEN_TRANSFER_SOUND.get(), SoundSource.BLOCKS, 0.1f, (float) (1.1f + ((random.nextFloat() - 0.5D) / 2)));
                                }
                            } else {
                                isToBlock = false;
                                update = true;
                            }
                        }
                    }
                }

                if (isFromBlock) {
                    if (level.isLoaded(new BlockPos(blockFromX, blockFromY, blockFromZ))) {
                        BlockEntity tileentity = level.getBlockEntity(new BlockPos(blockFromX, blockFromY, blockFromZ));
                        if (cooldown <= 0) {
                            if (tileentity instanceof IWissenTileEntity) {
                                IWissenTileEntity wissenTileEntity = (IWissenTileEntity) tileentity;
                                int addRemain = WissenUtils.getAddWissenRemain(getWissen(), getWissenPerReceive(), getMaxWissen());
                                int removeRemain = WissenUtils.getRemoveWissenRemain(wissenTileEntity.getWissen(), getWissenPerReceive() - addRemain);

                                if ((getWissenPerReceive() - removeRemain - addRemain) > 0) {
                                    wissenTileEntity.removeWissen(getWissenPerReceive() - removeRemain - addRemain);
                                    addWissenRay(new BlockPos(blockFromX, blockFromY, blockFromZ), getBlockPos(), getWissenPerReceive() - removeRemain - addRemain);

                                    setCooldown = true;
                                    update = true;

                                    level.playSound(WizardsReborn.proxy.getPlayer(), tileentity.getBlockPos(), WizardsReborn.WISSEN_TRANSFER_SOUND.get(), SoundSource.BLOCKS, 0.1f, (float) (1.1f + ((random.nextFloat() - 0.5D) / 2)));
                                }
                            } else {
                                isFromBlock = false;
                                update = true;
                            }
                        }
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

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }

        if (level.isClientSide()) {
            if (getWissen() > 0) {
                Color color = getColor();

                if (random.nextFloat() < 0.5) {
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.2f * getStage(), 0)
                            .setColor((float) color.getRed() / 255, (float) color.getGreen()/ 255, (float) color.getBlue() / 255)
                            .setLifetime(20)
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.075f * getStage(), 0)
                            .setColor((float) color.getRed() / 255, (float) color.getGreen()/ 255, (float) color.getBlue() / 255)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, worldPosition.getX() + 0.5F, worldPosition.getY() + 0.5F, worldPosition.getZ() + 0.5F);
                }
            }
            wissenWandEffect();
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
        if (stack.is(WizardsReborn.ARCANE_LUMOS_ITEM_TAG)) {
            return true;
        }

        return false;
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

    public float getStage() {
        return ((float) getWissen() / (float) getMaxWissen());
    }

    public float getRaySpeed() {
        return 0.1f;
    }

    public int getRayMitting() {
        return 100;
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
        return ((cooldown <= 0) && (getWissen() < getMaxWissen()));
    }

    @Override
    public boolean canReceiveWissen() {
        return ((cooldown <= 0) && (getWissen() > 0));
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
        return 100;
    }

    @Override
    public int getSendWissenCooldown() {
        return 20;
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

    @OnlyIn(Dist.CLIENT)
    public void wissenWandEffect() {
        Minecraft mc = Minecraft.getInstance();

        Player player = mc.player;
        ItemStack main = player.getMainHandItem();
        ItemStack offhand = player.getOffhandItem();
        boolean renderWand = false;

        if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
            renderWand = true;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                renderWand = true;
            }
        }

        if (renderWand) {
            if (isToBlock) {
                connectEffect(getBlockPos(), new BlockPos(blockToX, blockToY, blockToZ), new Color(118, 184, 214));
                connectBlockEffect(new BlockPos(blockToX, blockToY, blockToZ), new Color(118, 184, 214));
            }

            if (isFromBlock) {
                connectEffect(new BlockPos(blockFromX, blockFromY, blockFromZ), getBlockPos(), new Color(165, 223, 108));
                connectBlockEffect(new BlockPos(blockFromX, blockFromY, blockFromZ), new Color(165, 223, 108));
            }
        }
    }

    public void connectBlockEffect(BlockPos pos, Color color) {
        float colorR = (color.getRed() / 255f);
        float colorG = (color.getGreen() / 255f);
        float colorB = (color.getBlue() / 255f);

        int particlePerBlock = 4;

        for (int i = 0; i < particlePerBlock; i++) {
            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                    .addVelocity(((random.nextDouble() - 0.5D) / 100), ((random.nextDouble() - 0.5D) / 100), ((random.nextDouble() - 0.5D) / 100))
                    .setAlpha(0.25f, 0f).setScale(0.1f, 0f)
                    .setColor(colorR, colorG, colorB)
                    .setLifetime(5)
                    .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(level, pos.getX() + 0.5F + (random.nextDouble() - 0.5D), pos.getY() + 0.5F + (random.nextDouble() - 0.5D), pos.getZ() + 0.5F + (random.nextDouble() - 0.5D));
        }
    }

    public void connectEffect(BlockPos posFrom, BlockPos posTo, Color color) {
        double distance = Math.sqrt((posTo.getX() - posFrom.getX()) * (posTo.getX() - posFrom.getX()) + (posTo.getY() - posFrom.getY()) * (posTo.getY() - posFrom.getY()) + (posTo.getZ() - posFrom.getZ()) * (posTo.getZ() - posFrom.getZ()));
        int blocks = (int) Math.round(distance);

        double dX = posFrom.getX() - posTo.getX();
        double dY = posFrom.getY() - posTo.getY();
        double dZ = posFrom.getZ() - posTo.getZ();

        int particlePerBlock = ClientConfig.WISSEN_TRANSLATOR_PARTICLE_PER_BLOCK.get();

        float x = (float) (dX / (blocks * particlePerBlock));
        float y = (float) (dY / (blocks * particlePerBlock));
        float z = (float) (dZ / (blocks * particlePerBlock));

        float colorR = (color.getRed() / 255f);
        float colorG = (color.getGreen() / 255f);
        float colorB = (color.getBlue() / 255f);

        for (int i = 0; i <= blocks * particlePerBlock; i++) {
            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                    .addVelocity(((random.nextDouble() - 0.5D) / 100), ((random.nextDouble() - 0.5D) / 100), ((random.nextDouble() - 0.5D) / 100))
                    .setAlpha(0.25f, 0f).setScale(0.05f, 0f)
                    .setColor(colorR, colorG, colorB)
                    .setLifetime(3)
                    .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(level, posFrom.getX() + 0.5F - (x * i), posFrom.getY() + 0.5F - (y * i), posFrom.getZ() + 0.5F - (z * i));
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

        tag.putFloat("blockX", posFrom.getX() + 0.5f);
        tag.putFloat("blockY", posFrom.getY() + 0.5f);
        tag.putFloat("blockZ", posFrom.getZ() + 0.5f);

        tag.putFloat("velocityX", (float) -X);
        tag.putFloat("velocityY", (float) -Y);
        tag.putFloat("velocityZ", (float) -Z);

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

            float blockX = tag.getFloat("blockX");
            float blockY = tag.getFloat("blockY");
            float blockZ = tag.getFloat("blockZ");

            float X = tag.getFloat("velocityX") + blockX;
            float Y = tag.getFloat("velocityY") + blockY;
            float Z = tag.getFloat("velocityZ") + blockZ;

            if (level.isOutsideBuildHeight(new BlockPos(Mth.floor(blockX), Mth.floor(blockY), Mth.floor(blockZ)))) {
                deleteRays.add(i);
            }

            if (level.isLoaded(new BlockPos(Mth.floor(blockX), Mth.floor(blockY), Mth.floor(blockZ)))) {

                tag.putFloat("blockX", X);
                tag.putFloat("blockY", Y);
                tag.putFloat("blockZ", Z);

                Color color = getColor();

                PacketHandler.sendToTracking(level, getBlockPos(), new WissenSendEffectPacket(blockX, blockY, blockZ, X, Y, Z, (float) color.getRed() / 255, (float) color.getGreen()/ 255, (float) color.getBlue() / 255));

                if (tag.getInt("wissen") <= 0) {
                    PacketHandler.sendToTracking(level, getBlockPos(), new WissenTranslatorBurstEffectPacket(X, Y, Z, (float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255));
                    deleteRays.add(i);
                } else if ((blockFromX != Mth.floor(blockX)) || blockFromY != Mth.floor(blockY) || (blockFromZ != Mth.floor(blockZ))) {
                    BlockEntity tileentity = level.getBlockEntity(new BlockPos(Mth.floor(blockX), Mth.floor(blockY), Mth.floor(blockZ)));
                    if (tileentity instanceof IWissenTileEntity) {
                        if ((Math.abs(blockX) % 1F > 0.15F && Math.abs(blockX) % 1F <= 0.85F) &&
                                (Math.abs(blockY) % 1F > 0.15F && Math.abs(blockY) % 1F <= 0.85F) &&
                                (Math.abs(blockZ) % 1F > 0.15F && Math.abs(blockZ) % 1F <= 0.85F)) {
                            IWissenTileEntity wissenTileEntity = (IWissenTileEntity) tileentity;

                            PacketUtils.SUpdateTileEntityPacket(tileentity);

                            int addRemain = WissenUtils.getAddWissenRemain(wissenTileEntity.getWissen(), tag.getInt("wissen"), wissenTileEntity.getMaxWissen());
                            wissenTileEntity.addWissen(tag.getInt("wissen") - addRemain);
                            level.playSound(WizardsReborn.proxy.getPlayer(), X, Y, Z, WizardsReborn.WISSEN_TRANSFER_SOUND.get(), SoundSource.BLOCKS, 0.1f, (float) (1f + ((random.nextFloat() - 0.5D) / 2)));

                            PacketHandler.sendToTracking(level, getBlockPos(), new WissenTranslatorBurstEffectPacket(X, Y, Z, (float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255));
                            PacketHandler.sendToTracking(level, getBlockPos(), new WissenTranslatorSendEffectPacket(new BlockPos(Mth.floor(X), Mth.floor(Y), Mth.floor(Z))));

                            deleteRays.add(i);
                        }
                    } else {
                        BlockPos blockpos = new BlockPos(Mth.floor(blockX), Mth.floor(blockY), Mth.floor(blockZ));
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
        Color color = new Color(0.466f, 0.643f, 0.815f);

        if (!getItemHandler().getItem(0).isEmpty()) {
            if (getItemHandler().getItem(0).getItem() instanceof BlockItem) {
                BlockItem blockItem = (BlockItem) getItemHandler().getItem(0).getItem();
                if (blockItem.getBlock() instanceof ArcaneLumosBlock) {
                    ArcaneLumosBlock lumos = (ArcaneLumosBlock) blockItem.getBlock();
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
        if (getSendWissenCooldown() > 0) {
            return (float) getSendWissenCooldown() / cooldown;
        }
        return 0;
    }

    @Override
    public boolean wissenWandReceiveConnect(ItemStack stack, UseOnContext context, BlockEntity tile) {
        BlockPos oldBlockPos = WissenWandItem.getBlockPos(stack);
        BlockEntity oldTile = level.getBlockEntity(oldBlockPos);

        if (oldTile instanceof IWissenTileEntity wissenTile) {
            if ((!isSameFromAndTo(getBlockPos(), oldBlockPos)) && (wissenTile.canConnectReceiveWissen())) {
                blockFromX = oldBlockPos.getX();
                blockFromY = oldBlockPos.getY();
                blockFromZ = oldBlockPos.getZ();
                isFromBlock = true;
                WissenWandItem.setBlock(stack, false);
                PacketUtils.SUpdateTileEntityPacket(this);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean wissenWandSendConnect(ItemStack stack, UseOnContext context, BlockEntity tile) {
        BlockPos oldBlockPos = WissenWandItem.getBlockPos(stack);
        BlockEntity oldTile = level.getBlockEntity(oldBlockPos);

        if (oldTile instanceof IWissenTileEntity wissenTile) {
            if ((!isSameFromAndTo(getBlockPos(), oldBlockPos)) && (wissenTile.canConnectSendWissen())) {
                blockToX = oldBlockPos.getX();
                blockToY = oldBlockPos.getY();
                blockToZ = oldBlockPos.getZ();
                isToBlock = true;
                WissenWandItem.setBlock(stack, false);
                PacketUtils.SUpdateTileEntityPacket(this);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean wissenWandReload(ItemStack stack, UseOnContext context, BlockEntity tile) {
        isFromBlock = false;
        isToBlock = false;
        PacketUtils.SUpdateTileEntityPacket(this);
        return false;
    }
}
