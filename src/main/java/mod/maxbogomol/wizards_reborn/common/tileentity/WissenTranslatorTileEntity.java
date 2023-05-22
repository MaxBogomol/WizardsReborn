package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.network.*;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import java.util.Random;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

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
                            wissenTileEntity.addWissen(getWissenPerReceive() - removeRemain - addRemain);

                            setCooldown = true;

                            PacketHandler.sendToTracking(world, getPos(), new WissenTranslatorBurstEffectPacket(getPos()));
                            PacketHandler.sendToTracking(world, tileentity.getPos(), new WissenTranslatorSendEffectPacket(tileentity.getPos()));
                            PacketHandler.sendToTracking(world, getPos(), new WissenSendEffectPacket(getPos(), tileentity.getPos()));
                            PacketUtils.SUpdateTileEntityPacket(tileentity);
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
                            addWissen(getWissenPerReceive() - removeRemain - addRemain);
                            wissenTileEntity.removeWissen(getWissenPerReceive() - removeRemain - addRemain);

                            setCooldown = true;

                            PacketHandler.sendToTracking(world, getPos(), new WissenTranslatorSendEffectPacket(getPos()));
                            PacketHandler.sendToTracking(world, getPos(), new WissenSendEffectPacket(tileentity.getPos(), getPos()));
                            PacketUtils.SUpdateTileEntityPacket(tileentity);
                        }
                    } else {
                        isFromBlock = false;
                        PacketUtils.SUpdateTileEntityPacket(this);
                    }
                }
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
        return (new BlockPos(blockFromX, blockFromY, blockFromZ).equals(new BlockPos(blockToX, blockToY, blockToZ)));
    }

    public boolean isSameFromAndTo(BlockPos pos) {
        return (new BlockPos(blockFromX, blockFromY, blockFromZ).equals(pos));
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
        super.read(state, tag);
    }

    public float getStage() {
        return ((float) getWissen() / (float) getMaxWissen());
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

    public float getCraftingStage() {
        return ((float) getWissen() / (float) getMaxWissen());
    }
}
