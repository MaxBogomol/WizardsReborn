package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.network.*;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class WissenTranslatorTileEntity extends TileEntity implements ITickableTileEntity, IWissenTileEntity {

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

    public CompoundNBT wissenRays = new CompoundNBT();
    public int wissenRaysCount = 0;

    public Random random = new Random();

    public WissenTranslatorTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public WissenTranslatorTileEntity() {
        this(WizardsReborn.WISSEN_TRANSLATOR_TILE_ENTITY.get());
    }

    @Override
    public void tick() {
        if (!world.isRemote()) {
            if (cooldown > 0) {
                cooldown = cooldown - 1;
                PacketUtils.SUpdateTileEntityPacket(this);
            }

            boolean setCooldown = false;

            if (isToBlock && isFromBlock) {
                if (isSameFromAndTo()) {
                    isFromBlock = false;
                    isToBlock = false;
                    PacketUtils.SUpdateTileEntityPacket(this);
                }
            }

            if (isToBlock) {
                if (cooldown <= 0) {
                    TileEntity tileentity = world.getTileEntity(new BlockPos(blockToX, blockToY, blockToZ));
                    if (tileentity instanceof IWissenTileEntity) {
                        IWissenTileEntity wissenTileEntity = (IWissenTileEntity) tileentity;

                        int removeRemain = WissenUtils.getRemoveWissenRemain(getWissen(), getWissenPerReceive());
                        int addRemain = WissenUtils.getAddWissenRemain(wissenTileEntity.getWissen(), getWissenPerReceive() - removeRemain, wissenTileEntity.getMaxWissen());

                        if ((getWissenPerReceive() - removeRemain - addRemain) > 0) {
                            removeWissen(getWissenPerReceive() - removeRemain - addRemain);
                            addWissenRay(getPos(), new BlockPos(blockToX, blockToY, blockToZ), getWissenPerReceive() - removeRemain - addRemain);

                            setCooldown = true;

                            PacketHandler.sendToTracking(world, getPos(), new WissenTranslatorBurstEffectPacket(getPos().getX() + 0.5f, getPos().getY() + 0.5f, getPos().getZ() + 0.5f));
                        }
                    } else {
                        isToBlock = false;
                        PacketUtils.SUpdateTileEntityPacket(this);
                    }
                }
            }

            if (isFromBlock) {
                TileEntity tileentity = world.getTileEntity(new BlockPos(blockFromX, blockFromY, blockFromZ));
                if (cooldown <= 0) {
                    if (tileentity instanceof IWissenTileEntity) {
                        IWissenTileEntity wissenTileEntity = (IWissenTileEntity) tileentity;
                        int addRemain = WissenUtils.getAddWissenRemain(getWissen(), getWissenPerReceive(), getMaxWissen());
                        int removeRemain = WissenUtils.getRemoveWissenRemain(wissenTileEntity.getWissen(), getWissenPerReceive() - addRemain);

                        if ((getWissenPerReceive() - removeRemain - addRemain) > 0) {
                            wissenTileEntity.removeWissen(getWissenPerReceive() - removeRemain - addRemain);
                            addWissenRay(new BlockPos(blockFromX, blockFromY, blockFromZ), getPos(), getWissenPerReceive() - removeRemain - addRemain);

                            setCooldown = true;

                            PacketUtils.SUpdateTileEntityPacket(tileentity);
                        }
                    } else {
                        isFromBlock = false;
                        PacketUtils.SUpdateTileEntityPacket(this);
                    }
                }
            }

            if (wissenRaysCount > 0) {
                updateWissenRays();
            }

            if (setCooldown) {
                cooldown = getSendWissenCooldown();
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }

        if (world.isRemote()) {
            if (getWissen() > 0) {
                if (random.nextFloat() < 0.5) {
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.2f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(20)
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage(), ((random.nextDouble() - 0.5D) / 30) * getStage())
                            .setAlpha(0.25f, 0).setScale(0.075f * getStage(), 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F);
                }
            }
            wissenWandEffect();
        }
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

    public boolean isSameFromAndTo() {
        return ((blockFromX == blockToX) && (blockFromY == blockToY) && (blockFromZ == blockToZ));
    }

    public boolean isSameFromAndTo(BlockPos posFrom, BlockPos posTo) {
        return ((posFrom.getX() == posTo.getX()) && (posFrom.getY() == posTo.getY()) && (posFrom.getZ() == posTo.getZ()));
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        super.write(tag);
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
        tag.putInt("wissenRaysCount", wissenRaysCount);

        CompoundNBT ret = super.write(tag);
        return ret;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);
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
        wissenRaysCount = tag.getInt("wissenRaysCount");

        super.read(state, tag);
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

    public void wissenWandEffect() {
        Minecraft mc = Minecraft.getInstance();

        PlayerEntity player = mc.player;
        ItemStack main = player.getHeldItemMainhand();
        ItemStack offhand = player.getHeldItemOffhand();
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
                connectEffect(getPos(), new BlockPos(blockToX, blockToY, blockToZ), new Color(118, 184, 214));
                connectBlockEffect(new BlockPos(blockToX, blockToY, blockToZ), new Color(118, 184, 214));
            }

            if (isFromBlock) {
                connectEffect(new BlockPos(blockFromX, blockFromY, blockFromZ), getPos(), new Color(165, 223, 108));
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
                    .setColor(colorR, colorG, colorB, colorR, colorG, colorB)
                    .setLifetime(5)
                    .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(world, pos.getX() + 0.5F + (random.nextDouble() - 0.5D), pos.getY() + 0.5F + (random.nextDouble() - 0.5D), pos.getZ() + 0.5F + (random.nextDouble() - 0.5D));
        }
    }

    public void connectEffect(BlockPos posFrom, BlockPos posTo, Color color) {
        double distance = Math.sqrt((posTo.getX() - posFrom.getX()) * (posTo.getX() - posFrom.getX()) + (posTo.getY() - posFrom.getY()) * (posTo.getY() - posFrom.getY()) + (posTo.getZ() - posFrom.getZ()) * (posTo.getZ() - posFrom.getZ()));
        int blocks = (int) Math.round(distance);

        double dX = posFrom.getX() - posTo.getX();
        double dY = posFrom.getY() - posTo.getY();
        double dZ = posFrom.getZ() - posTo.getZ();

        int particlePerBlock = ClientConfig.TEST.get();

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
                    .setColor(colorR, colorG, colorB, colorR, colorG, colorB)
                    .setLifetime(3)
                    .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(world, posFrom.getX() + 0.5F - (x * i), posFrom.getY() + 0.5F - (y * i), posFrom.getZ() + 0.5F - (z * i));
        }
    }

    public void addWissenRay(BlockPos posFrom, BlockPos posTo, int wissenCount) {
        CompoundNBT tag = new CompoundNBT();

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

        wissenRaysCount = wissenRaysCount + 1;
        wissenRays.put(String.valueOf(wissenRaysCount - 1), tag);
    }

    public void deleteWissenRay(ArrayList<Integer> indexs) {
        CompoundNBT tag = new CompoundNBT();

        int ii = 0;

        for (int i = 0; i < wissenRaysCount; i++) {
            if (!indexs.contains(i)) {
                tag.put(String.valueOf(ii), wissenRays.getCompound(String.valueOf(i)));
                ii = ii + 1;
            }
        }
        wissenRaysCount = wissenRaysCount - 1;
        wissenRays = tag;
    }

    public void updateWissenRays() {
        int preWissenRaysCount = wissenRaysCount;
        ArrayList<Integer> deleteRays = new ArrayList<Integer>();

        for (int i = 0; i < wissenRaysCount; i++) {
            CompoundNBT tag = wissenRays.getCompound(String.valueOf(i));

            int blockFromX = tag.getInt("blockFromX");
            int blockFromY = tag.getInt("blockFromY");
            int blockFromZ = tag.getInt("blockFromZ");

            float blockX = tag.getFloat("blockX");
            float blockY = tag.getFloat("blockY");
            float blockZ = tag.getFloat("blockZ");

            float X = tag.getFloat("velocityX") + blockX;
            float Y = tag.getFloat("velocityY") + blockY;
            float Z = tag.getFloat("velocityZ") + blockZ;

            tag.putFloat("blockX", X);
            tag.putFloat("blockY", Y);
            tag.putFloat("blockZ", Z);

            PacketHandler.sendToTracking(world, getPos(), new WissenSendEffectPacket(blockX, blockY, blockZ, X, Y, Z));

            if (tag.getInt("wissen") <= 0) {
                PacketHandler.sendToTracking(world, getPos(), new WissenTranslatorBurstEffectPacket(X, Y, Z));
                deleteRays.add(i);
            } else if ((blockFromX != MathHelper.floor(blockX)) || blockFromY != MathHelper.floor(blockY) || (blockFromZ != MathHelper.floor(blockZ))) {
                TileEntity tileentity = world.getTileEntity(new BlockPos(blockX, blockY, blockZ));
                if (tileentity instanceof IWissenTileEntity) {
                    if ((Math.abs(blockX) % 1F > 0.25F && Math.abs(blockX) % 1F <= 0.75F) &&
                            (Math.abs(blockY) % 1F > 0.25F && Math.abs(blockY) % 1F <= 0.75F) &&
                            (Math.abs(blockZ) % 1F > 0.25F && Math.abs(blockZ) % 1F <= 0.75F)) {
                        IWissenTileEntity wissenTileEntity = (IWissenTileEntity) tileentity;

                        int addRemain = WissenUtils.getAddWissenRemain(wissenTileEntity.getWissen(), tag.getInt("wissen"), wissenTileEntity.getMaxWissen());
                        wissenTileEntity.addWissen(tag.getInt("wissen") - addRemain);
                        PacketUtils.SUpdateTileEntityPacket(tileentity);

                        PacketHandler.sendToTracking(world, getPos(), new WissenTranslatorBurstEffectPacket(X, Y, Z));
                        PacketHandler.sendToTracking(world, getPos(), new WissenTranslatorSendEffectPacket(new BlockPos(X, Y, Z)));

                        deleteRays.add(i);
                    }
                } else {
                    BlockPos blockpos = new BlockPos(blockX, blockY, blockZ);
                    if (world.getBlockState(blockpos).hasOpaqueCollisionShape(world, blockpos)) {
                        tag.putInt("wissen", tag.getInt("wissen") - 1);
                    }

                    if (tag.getInt("mitting") >= getRayMitting()) {
                        tag.putInt("wissen", tag.getInt("wissen") - 1);
                    }
                    tag.putInt("mitting", tag.getInt("mitting") + 1);
                }
            }
        }

        if (deleteRays.size() > 0) {
            deleteWissenRay(deleteRays);
        }

        if (preWissenRaysCount > wissenRaysCount) {
            PacketUtils.SUpdateTileEntityPacket(this);
        }
    }
}
