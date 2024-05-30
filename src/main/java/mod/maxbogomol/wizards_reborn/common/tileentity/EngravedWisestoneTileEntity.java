package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenWandFunctionalTileEntity;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.block.EngravedWisestoneBlock;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

public class EngravedWisestoneTileEntity extends BlockEntity implements TickableBlockEntity, IWissenWandFunctionalTileEntity {

    public boolean glow = false;
    public int cooldown = 0;

    public int glowTicks = 0;

    public Random random = new Random();

    public EngravedWisestoneTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public EngravedWisestoneTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.ENGRAVED_WISESTONE_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;
            if (cooldown > 0) {
                cooldown--;
                update = true;
            }

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }

        if (level.isClientSide()) {
            if (glow && glowTicks < 20) glowTicks++;
            if (!glow && glowTicks > 0) glowTicks--;

            if (getBlockState().getBlock() instanceof EngravedWisestoneBlock block && block.hasMonogram()) {
                if (glow && glowTicks < 20) {
                    double ticks = (ClientTickHandler.ticksInGame) * 20f;

                    float distance = 0.875f;
                    double yaw = Math.toRadians(getHorizontalBlockRotate() + ticks);
                    double pitch = 90;

                    double X = Math.sin(pitch) * Math.cos(yaw) * distance;
                    double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

                    Monogram monogram = block.getMonogram();
                    Color color = monogram.getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    Particles.create(WizardsReborn.CUBE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 100), (random.nextDouble() / 200) + 0.01f, ((random.nextDouble() - 0.5D) / 100))
                            .setAlpha(0.75f, 0).setScale(0.1f, 0)
                            .setColor(r, g, b, random.nextFloat(), random.nextFloat(), random.nextFloat())
                            .setLifetime(30)
                            .setSpin(0.1f)
                            .spawn(level, getBlockPos().getX() + 0.5F + X, getBlockPos().getY() + (glowTicks / 20f), getBlockPos().getZ() + 0.5F + Z);
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 100), (random.nextDouble() / 200) + 0.01f, ((random.nextDouble() - 0.5D) / 100))
                            .setAlpha(0.75f, 0).setScale(0.1f, 0)
                            .setColor(r, g, b, random.nextFloat(), random.nextFloat(), random.nextFloat())
                            .setLifetime(40)
                            .setSpin(0.1f)
                            .spawn(level, getBlockPos().getX() + 0.5F + X, getBlockPos().getY() + (glowTicks / 20f), getBlockPos().getZ() + 0.5F + Z);
                }
            }
        }
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
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putBoolean("glow", glow);
        tag.putInt("cooldown", cooldown);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        glow = tag.getBoolean("glow");
        cooldown = tag.getInt("cooldown");
    }

    @Override
    public AABB getRenderBoundingBox() {
        return IForgeBlockEntity.INFINITE_EXTENT_AABB;
    }

    public float getHorizontalBlockRotate() {
        switch (this.getBlockState().getValue(EngravedWisestoneBlock.FACING)) {
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

    public float getVerticalBlockRotate() {
        switch (this.getBlockState().getValue(EngravedWisestoneBlock.FACE)) {
            case FLOOR:
                return 90F;
            case WALL:
                return 0F;
            case CEILING:
                return -90F;
            default:
                return 0F;
        }
    }

    @Override
    public void wissenWandFunction() {
        if (getBlockState().getBlock() instanceof EngravedWisestoneBlock block && block.hasMonogram()) {
            if (cooldown <= 0) {
                glow = !glow;
                PacketUtils.SUpdateTileEntityPacket(this);
                level.playSound(WizardsReborn.proxy.getPlayer(), getBlockPos(), WizardsReborn.WISSEN_BURST_SOUND.get(), SoundSource.BLOCKS, 0.5f, glow ? 2f : 0.5f);
                cooldown = 20;
            }
        }
    }
}
