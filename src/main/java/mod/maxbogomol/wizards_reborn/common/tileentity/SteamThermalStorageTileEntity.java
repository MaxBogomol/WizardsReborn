package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamTileEntity;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.block.SteamThermalStorageBlock;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class SteamThermalStorageTileEntity extends BlockEntity implements TickableBlockEntity, ISteamTileEntity {
    public int steam = 0;

    public Random random = new Random();

    public SteamThermalStorageTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SteamThermalStorageTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.STEAM_THERMAL_STORAGE_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level.isClientSide()) {
            float amount = (float) getSteam() / (float) getMaxSteam();

            Vec3 posSteam = new Vec3(0F, 0F, 0F);
            switch (getBlockState().getValue(SteamThermalStorageBlock.AXIS)) {
                case X:
                    posSteam = new Vec3(0.25F, 0.15F, 0.15F);
                    break;
                case Y:
                    posSteam = new Vec3(0.15F, 0.25F, 0.15F);
                    break;
                case Z:
                    posSteam = new Vec3(0.15F, 0.15F, 0.25F);
                    break;
                default:
                    posSteam = new Vec3(0F, 0F, 0F);
                    break;
            }

            for (int i = 0; i < 2 * amount; i++) {
                if (random.nextFloat() < amount) {
                    Particles.create(WizardsReborn.STEAM_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                            .setAlpha(0.4f, 0).setScale(0f, 0.3f)
                            .setColor(1f, 1f, 1f)
                            .setLifetime(30)
                            .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, getBlockPos().getX() + 0.5F + ((random.nextDouble() - 0.5D) * posSteam.x()), getBlockPos().getY() + 0.5F + ((random.nextDouble() - 0.5D) * posSteam.y()), getBlockPos().getZ() + 0.5F + ((random.nextDouble() - 0.5D) * posSteam.z()));
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
        tag.putInt("steam", steam);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        steam = tag.getInt("steam");
    }

    @Override
    public int getSteam() {
        return steam;
    }

    @Override
    public int getMaxSteam() {
        return 25000;
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
        switch (getBlockState().getValue(SteamThermalStorageBlock.AXIS)) {
            case X:
                return (side == Direction.EAST || side == Direction.WEST);
            case Y:
                return (side == Direction.UP || side == Direction.DOWN);
            case Z:
                return (side == Direction.NORTH || side == Direction.SOUTH);
            default:
                return false;
        }
    }
}
