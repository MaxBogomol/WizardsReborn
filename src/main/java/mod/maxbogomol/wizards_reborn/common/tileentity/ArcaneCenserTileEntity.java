package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamTileEntity;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ArcaneCenserTileEntity extends BlockEntity implements TickableBlockEntity, ISteamTileEntity {
    public int steam = 0;
    public int cooldown = 0;

    public Random random = new Random();

    public ArcaneCenserTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public ArcaneCenserTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.ARCANE_CENSER_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
            boolean update = false;

            if (cooldown > 0) {
                cooldown = cooldown - 1;
                update = true;
            }

            if (update) {
                PacketUtils.SUpdateTileEntityPacket(this);
            }
        }

        if (level.isClientSide()) {
            if (cooldown > 0) {
                if (random.nextFloat() < 0.2F) {
                    Particles.create(WizardsReborn.STEAM_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15) + 0.05, ((random.nextDouble() - 0.5D) / 15))
                            .setAlpha(0.1f, 0).setScale(0.1f, 3)
                            .setColor(1f, 1f, 1f)
                            .setLifetime(200 + random.nextInt(100))
                            .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, getBlockPos().getX() + 0.5F, getBlockPos().getY() + 1F, getBlockPos().getZ() + 0.5F);
                }
            }

            float amount = (float) getSteam() / (float) getMaxSteam();

            for (int i = 0; i < 2 * amount; i++) {
                if (random.nextFloat() < amount) {
                    Particles.create(WizardsReborn.STEAM_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                            .setAlpha(0.4f, 0).setScale(0f, 0.3f)
                            .setColor(1f, 1f, 1f)
                            .setLifetime(30)
                            .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, getBlockPos().getX() + 0.5F + ((random.nextDouble() - 0.5D) * 0.05), getBlockPos().getY() + 0.375F + ((random.nextDouble() - 0.5D) * 0.15), getBlockPos().getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 0.05));
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
        tag.putInt("cooldown", cooldown);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        steam = tag.getInt("steam");
        cooldown = tag.getInt("cooldown");
    }

    @Override
    public int getSteam() {
        return steam;
    }

    @Override
    public int getMaxSteam() {
        return 5000;
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
        return side == Direction.DOWN;
    }
}
