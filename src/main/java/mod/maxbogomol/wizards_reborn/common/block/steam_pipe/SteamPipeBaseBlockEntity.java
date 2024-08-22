package mod.maxbogomol.wizards_reborn.common.block.steam_pipe;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamPipePriority;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipePriorityMap;
import mod.maxbogomol.wizards_reborn.api.alchemy.SteamUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Random;

public abstract class SteamPipeBaseBlockEntity extends PipeBaseBlockEntity implements ISteamPipePriority, TickableBlockEntity, ISteamBlockEntity {

    public static final int PRIORITY_BLOCK = 0;
    public static final int PRIORITY_PIPE = PRIORITY_BLOCK;
    public static final int MAX_PUSH = 150;

    public static Random random = new Random();
    public boolean[] from = new boolean[Direction.values().length];
    public boolean clogged = false;
    public Direction lastTransfer;
    public boolean syncCloggedFlag = true;
    public boolean syncTransfer = true;
    public int ticksExisted;
    public int lastRobin;

    public int steam = 0;

    public SteamPipeBaseBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public void onLoad() {
        syncTransfer = true;
        syncCloggedFlag = true;
        if (level instanceof ServerLevel serverLevel) {
            for (ServerPlayer serverplayer : serverLevel.getServer().getPlayerList().getPlayers()) {
                serverplayer.connection.send(this.getUpdatePacket());
            }
            this.resetSync();
        }
    }

    public abstract int getCapacity();

    @Override
    public int getPriority(Direction facing) {
        return PRIORITY_PIPE;
    }

    public void setFrom(Direction facing, boolean flag) {
        from[facing.get3DDataValue()] = flag;
    }

    public void resetFrom() {
        for (Direction facing : Direction.values()) {
            setFrom(facing, false);
        }
    }

    protected boolean isFrom(Direction facing) {
        return from[facing.get3DDataValue()];
    }

    protected boolean isAnySideUnclogged() {
        for (Direction facing : Direction.values()) {
            if (!getConnection(facing).transfer)
                continue;
            BlockEntity tile = level.getBlockEntity(worldPosition.relative(facing));
            if (tile instanceof SteamPipeBaseBlockEntity && !((SteamPipeBaseBlockEntity) tile).clogged)
                return true;
        }
        return false;
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this, (e) -> e.getUpdateTag());
    }

    public void tick() {
        if (!level.isClientSide()) {
            if (!loaded)
                initConnections();
            ticksExisted++;
            boolean steamMoved = false;

            if (steam > 0) {
                PipePriorityMap<Integer, Direction> possibleDirections = new PipePriorityMap<>();

                for (Direction facing : Direction.values()) {
                    if (!getConnection(facing).transfer)
                        continue;
                    if (isFrom(facing))
                        continue;
                    BlockEntity tile = level.getBlockEntity(getBlockPos().relative(facing));
                    if (tile != null) {
                        if (tile instanceof ISteamBlockEntity steamTileEntity) {
                            if (steamTileEntity.canSteamTransfer(facing.getOpposite())) {
                                int priority = PRIORITY_BLOCK;
                                if (tile instanceof ISteamPipePriority)
                                    priority = ((ISteamPipePriority) tile).getPriority(facing.getOpposite());
                                if (isFrom(facing.getOpposite()))
                                    priority -= 5;
                                possibleDirections.put(priority, facing);
                            }
                        }
                    }
                }

                for (int key : possibleDirections.keySet()) {
                    ArrayList<Direction> list = possibleDirections.get(key);
                    for (int i = 0; i < list.size(); i++) {
                        Direction facing = list.get((i + lastRobin) % list.size());
                        steamMoved = pushSteam(MAX_PUSH, facing);
                        if (lastTransfer != facing) {
                            syncTransfer = true;
                            lastTransfer = facing;
                            setChanged();
                        }
                        if (steamMoved) {
                            lastRobin++;
                            break;
                        }
                    }
                    if (steamMoved) {
                        if (random.nextFloat() < 0.005F) {
                            level.playSound(null, getBlockPos(), WizardsReborn.STEAM_BURST_SOUND.get(), SoundSource.BLOCKS, 0.1f, 1.0f);
                        }
                        break;
                    }
                }
            }

            if (getSteam() <= 0) {
                if (lastTransfer != null && !steamMoved) {
                    syncTransfer = true;
                    lastTransfer = null;
                    setChanged();
                }
                steamMoved = true;
                resetFrom();
            }

            if (clogged == steamMoved) {
                clogged = !steamMoved;
                syncCloggedFlag = true;
                setChanged();
            }
        }

        if (level.isClientSide()) {
            wissenWandEffect();
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

        if (lastTransfer != null && renderWand) {
            float vx = lastTransfer.getStepX();
            float vy = lastTransfer.getStepY();
            float vz = lastTransfer.getStepZ() ;
            double x = getBlockPos().getX() + 0.2f + random.nextFloat() * 0.6f;
            double y = getBlockPos().getY() + 0.2f + random.nextFloat() * 0.6f;
            double z = getBlockPos().getZ() + 0.2f + random.nextFloat() * 0.6f;
            float r = clogged ? 255f : 16f;
            float g = clogged ? 16f : 255f;
            float b = 16f;
            ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                    .setColorData(ColorParticleData.create(r / 255f, g / 255f, b / 255f).build())
                    .setTransparencyData(GenericParticleData.create(0.3f, 0f).build())
                    .setScaleData(GenericParticleData.create(0.1f, 0f).build())
                    .randomSpin(0.1f)
                    .setLifetime(10)
                    .addVelocity(vx / 10f, vy / 10f, vz / 10f)
                    .repeat(level, x, y, z, 2);
        }
    }

    public boolean pushSteam(int amount, Direction facing) {
        BlockEntity tile = level.getBlockEntity(getBlockPos().relative(facing));
        if (tile instanceof ISteamBlockEntity steamTileEntity) {
            int steam_remain = WissenUtils.getRemoveWissenRemain(steam, amount);
            steam_remain = amount - steam_remain;
            int addRemain = SteamUtils.getAddSteamRemain(steamTileEntity.getSteam(), steam_remain, steamTileEntity.getMaxSteam());
            steam_remain = steam_remain - addRemain;
            if (steam_remain > 0) {
                steamTileEntity.addSteam(steam_remain);
                removeSteam(steam_remain);
                PacketUtils.SUpdateTileEntityPacket(this);
                PacketUtils.SUpdateTileEntityPacket(tile);
                if (tile instanceof SteamPipeBaseBlockEntity steamPipe) {
                    steamPipe.setFrom(facing.getOpposite(), true);
                }
                return steam <= 0;
            }
        }

        if (isFrom(facing))
            setFrom(facing, false);
        return false;
    }

    protected void resetSync() {
        syncCloggedFlag = false;
        syncTransfer = false;
    }

    protected boolean requiresSync() {
        return true;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("clogged"))
            clogged = nbt.getBoolean("clogged");
        if (nbt.contains("lastTransfer"))
            lastTransfer = readNullableFacing(nbt.getInt("lastTransfer"));
        for(Direction facing : Direction.values())
            if(nbt.contains("from"+facing.get3DDataValue()))
                from[facing.get3DDataValue()] = nbt.getBoolean("from"+facing.get3DDataValue());
        if (nbt.contains("lastRobin"))
            lastRobin = nbt.getInt("lastRobin");
        if (nbt.contains("steam"))
            steam = nbt.getInt("steam");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        writeCloggedFlag(nbt);
        writeLastTransfer(nbt);
        for(Direction facing : Direction.values())
            nbt.putBoolean("from"+facing.get3DDataValue(),from[facing.get3DDataValue()]);
        nbt.putInt("lastRobin",lastRobin);
        nbt.putInt("steam",steam);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        nbt.putInt("steam",steam);
        if (syncCloggedFlag)
            writeCloggedFlag(nbt);
        if (syncTransfer)
            writeLastTransfer(nbt);
        return nbt;
    }

    private void writeCloggedFlag(CompoundTag nbt) {
        nbt.putBoolean("clogged", clogged);
    }

    private void writeLastTransfer(CompoundTag nbt) {
        nbt.putInt("lastTransfer", writeNullableFacing(lastTransfer));
    }

    @Override
    public void setChanged() {
        super.setChanged();
        if (level != null && !level.isClientSide) {
            if (requiresSync() && level instanceof ServerLevel serverLevel) {
                for (ServerPlayer serverplayer : serverLevel.getServer().getPlayerList().getPlayers()) {
                    serverplayer.connection.send(this.getUpdatePacket());
                }
                this.resetSync();
            }
        }
    }

    public static Direction readNullableFacing(int index) {
        return index > 0 ? Direction.from3DDataValue(index) : null;
    }

    public static int writeNullableFacing(Direction facing) {
        return facing != null ? facing.get3DDataValue() : -1;
    }
}
