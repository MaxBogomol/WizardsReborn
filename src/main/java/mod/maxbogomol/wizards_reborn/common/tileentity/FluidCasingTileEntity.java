package mod.maxbogomol.wizards_reborn.common.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidPipePriority;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipePriorityMap;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.awt.*;
import java.util.ArrayList;

public class FluidCasingTileEntity extends FluidPipeTileEntity {
    public FluidCasingTileEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public FluidCasingTileEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.FLUID_CASING_TILE_ENTITY.get(), pos, state);
    }

    public void tick() {
        if (!level.isClientSide()) {
            if (!level.hasNeighborSignal(getBlockPos())) {
                if (level.getBlockState(getBlockPos()).getValue(BlockStateProperties.POWERED)) {
                    if (!loaded)
                        initConnections();
                    ticksExisted++;
                    boolean fluidMoved = false;

                    FluidStack passStack = tank.drain(MAX_PUSH, IFluidHandler.FluidAction.SIMULATE);
                    if (!passStack.isEmpty()) {
                        PipePriorityMap<Integer, Direction> possibleDirections = new PipePriorityMap<>();
                        IFluidHandler[] fluidHandlers = new IFluidHandler[Direction.values().length];

                        for (Direction facing : Direction.values()) {
                            if (!getConnection(facing).transfer)
                                continue;
                            if (isFrom(facing))
                                continue;
                            BlockEntity tile = level.getBlockEntity(getBlockPos().relative(facing));
                            if (tile != null) {
                                IFluidHandler handler = tile.getCapability(ForgeCapabilities.FLUID_HANDLER, facing.getOpposite()).orElse(null);
                                if (handler != null) {
                                    int priority = PRIORITY_BLOCK;
                                    if (tile instanceof IFluidPipePriority)
                                        priority = ((IFluidPipePriority) tile).getPriority(facing.getOpposite());
                                    possibleDirections.put(priority, facing);
                                    fluidHandlers[facing.get3DDataValue()] = handler;
                                }
                            }
                        }

                        int connectionCount = 0;
                        Direction transfer = lastTransfer;

                        for (int key : possibleDirections.keySet()) {
                            ArrayList<Direction> list = possibleDirections.get(key);
                            for (int i = 0; i < list.size(); i++) {
                                Direction facing = list.get((i + lastRobin) % list.size());
                                if (transfer != facing) {
                                    connectionCount++;
                                    transfer = facing;
                                }
                            }
                        }
                        connectionCount++;
                        passStack = tank.drain((int) Math.floor((double) MAX_PUSH / connectionCount), IFluidHandler.FluidAction.SIMULATE);

                        for (int key : possibleDirections.keySet()) {
                            ArrayList<Direction> list = possibleDirections.get(key);
                            for (int i = 0; i < list.size(); i++) {
                                Direction facing = list.get((i + lastRobin) % list.size());
                                IFluidHandler handler = fluidHandlers[facing.get3DDataValue()];
                                fluidMoved = pushStack(passStack, facing, handler);
                                if (lastTransfer != facing) {
                                    syncTransfer = true;
                                    lastTransfer = facing;
                                    setChanged();
                                }
                                if (fluidMoved) {
                                    lastRobin++;
                                }
                            }
                            if (fluidMoved) {
                                if (random.nextFloat() < 0.005F) {
                                    level.playSound(null, getBlockPos(), SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, 0.6f, 1.0f);
                                }
                            }
                        }
                    }

                    if (tank.getFluidAmount() <= 0) {
                        if (lastTransfer != null && !fluidMoved) {
                            syncTransfer = true;
                            lastTransfer = null;
                            setChanged();
                        }
                        fluidMoved = true;
                        resetFrom();
                    }
                    if (clogged == fluidMoved) {
                        clogged = !fluidMoved;
                        syncCloggedFlag = true;
                        setChanged();
                    }
                } else {
                    super.tick();
                }
            }
        }

        if (level.isClientSide()) {
            if (clogged && isAnySideUnclogged()) {
                cloggedEffect();
            }
            if (!level.getBlockState(getBlockPos()).isAir() && level.getBlockState(getBlockPos()).getValue(BlockStateProperties.POWERED) && WissenUtils.isCanRenderWissenWand()) {
                WissenUtils.connectBlockEffect(level, getBlockPos(), new Color(191, 201, 104));
            }
        }
    }
}
