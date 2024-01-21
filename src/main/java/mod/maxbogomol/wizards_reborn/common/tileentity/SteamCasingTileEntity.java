package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamPipePriority;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamTileEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipePriorityMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class SteamCasingTileEntity extends SteamPipeTileEntity {
    public SteamCasingTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public SteamCasingTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.STEAM_CASING_TILE_ENTITY.get(), pos, state);
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
                        if (tile instanceof ISteamTileEntity steamTileEntity) {
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
                        steamMoved = pushSteam((int) Math.floor((double) MAX_PUSH / possibleDirections.keySet().size()), facing);
                        if (lastTransfer != facing) {
                            syncTransfer = true;
                            lastTransfer = facing;
                            setChanged();
                        }
                        if (steamMoved) {
                            lastRobin++;
                        }
                    }
                    if (steamMoved) {
                        if (random.nextFloat() < 0.005F) {
                            level.playSound(null, getBlockPos(), WizardsReborn.STEAM_BURST_SOUND.get(), SoundSource.BLOCKS, 0.1f, 1.0f);
                        }
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
            if (clogged && isAnySideUnclogged()) {
                cloggedEffect();
            }
        }
    }
}
