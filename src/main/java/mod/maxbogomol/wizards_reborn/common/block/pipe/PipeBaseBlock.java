package mod.maxbogomol.wizards_reborn.common.block.pipe;

import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.api.alchemy.IPipeConnection;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public abstract class PipeBaseBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {
    public static VoxelShape CENTER_AABB = Block.box(5,5,5,11,11,11);
    public static VoxelShape PIPE_DOWN_AABB = Block.box(5,0,5,11,5,11);
    public static VoxelShape END_DOWN_AABB = Shapes.or(Block.box(4,0,4,12,2,12), PIPE_DOWN_AABB);
    public static VoxelShape PIPE_UP_AABB = Block.box(5,11,5,11,16,11);
    public static VoxelShape END_UP_AABB = Shapes.or(Block.box(4,14,4,12,16,12), PIPE_UP_AABB);
    public static VoxelShape PIPE_NORTH_AABB = Block.box(5,5,0,11,11,5);
    public static VoxelShape END_NORTH_AABB = Shapes.or(Block.box(4,4,0,12,12,2), PIPE_NORTH_AABB);
    public static VoxelShape PIPE_SOUTH_AABB = Block.box(5,5,11,11,11,16);
    public static VoxelShape END_SOUTH_AABB = Shapes.or(Block.box(4,4,14,12,12,16), PIPE_SOUTH_AABB);
    public static VoxelShape PIPE_WEST_AABB = Block.box(0,5,5,5,11,11);
    public static VoxelShape END_WEST_AABB = Shapes.or(Block.box(0,4,4,2,12,12), PIPE_WEST_AABB);
    public static VoxelShape PIPE_EAST_AABB = Block.box(11,5,5,16,11,11);
    public static VoxelShape END_EAST_AABB = Shapes.or(Block.box(14,4,4,16,12,12), PIPE_EAST_AABB);
    public static VoxelShape[] PIPE_AABBS = new VoxelShape[] { PIPE_DOWN_AABB, PIPE_UP_AABB, PIPE_NORTH_AABB, PIPE_SOUTH_AABB, PIPE_WEST_AABB, PIPE_EAST_AABB };
    public static VoxelShape[] END_AABBS = new VoxelShape[] { END_DOWN_AABB, END_UP_AABB, END_NORTH_AABB, END_SOUTH_AABB, END_WEST_AABB, END_EAST_AABB };
    public static VoxelShape[] SHAPES = new VoxelShape[729];

    static {
        makeShapes(CENTER_AABB, SHAPES);
    }

    public abstract TagKey<Block> getConnectionTag();

    public abstract TagKey<Block> getToggleConnectionTag();

    public abstract boolean connectToTile(BlockEntity blockEntity, Direction face);

    public abstract boolean unclog(BlockEntity blockEntity, Level level, BlockPos pos);

    public PipeBaseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    public VoxelShape[] getPipeAabbs() {
        return PIPE_AABBS;
    }

    public VoxelShape[] getEndAabbs() {
        return END_AABBS;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            ItemStack stack = player.getItemInHand(hand).copy();
            boolean isWand = false;

            if (stack.getItem() instanceof WissenWandItem) {
                if (WissenWandItem.getMode(stack) == 0) {
                    isWand = true;
                }
                if (WissenWandItem.getMode(stack) == 3) {
                    if (unclog(level.getBlockEntity(pos), level, pos)) {
                        return InteractionResult.SUCCESS;
                    }
                }
            }

            if (!isWand) {
                return InteractionResult.PASS;
            }

            BlockEntity BE = level.getBlockEntity(pos);
            if (BE instanceof PipeBaseBlockEntity pipe) {
                double reach = player.getBlockReach();
                Vec3 eyePosition = player.getEyePosition();
                Vec3 lookVector = player.getLookAngle().multiply(reach, reach, reach).add(eyePosition);

                Vec3[] hitPositions = new Vec3[6];
                BlockHitResult centerHit = getCenterShape().clip(eyePosition, lookVector, pos);

                for (int i = 0; i < 6; i++) {
                    BlockHitResult partHit = null;
                    if (pipe.connections[i] == PipeConnection.END) {
                        partHit = getEndAabbs()[i].clip(eyePosition, lookVector, pos);
                    } else if (pipe.connections[i] == PipeConnection.PIPE) {
                        partHit = getPipeAabbs()[i].clip(eyePosition, lookVector, pos);
                    }
                    if (partHit != null) {
                        hitPositions[i] = partHit.getLocation();
                    }
                }
                int closestHit = -1;
                double closestDistance = reach;
                if (centerHit != null)
                    closestDistance = eyePosition.distanceTo(centerHit.getLocation());
                for (int i = 0; i < 6; i++) {
                    if (hitPositions[i] != null) {
                        double dist = eyePosition.distanceTo(hitPositions[i]);
                        if (dist < closestDistance) {
                            closestDistance = dist;
                            closestHit = i;
                        }
                    }
                }
                if (closestHit == -1) {
                    Direction face = hit.getDirection();
                    if (pipe.getConnection(face) != PipeConnection.DISABLED)
                        return InteractionResult.PASS;
                    BlockPos facingPos = pos.relative(face);
                    BlockState facingState = level.getBlockState(facingPos);

                    if (facingState.is(getToggleConnectionTag()) && level.getBlockEntity(facingPos) instanceof PipeBaseBlockEntity facingPipe) {
                        pipe.setConnection(face, PipeConnection.PIPE);
                        facingPipe.setConnection(face.getOpposite(), PipeConnection.PIPE);
                        level.updateNeighbourForOutputSignal(pos, this);
                        level.updateNeighbourForOutputSignal(facingPos, this);
                        level.playSound(null, pos.getX() + 0.5 + face.getStepX() * 0.5, pos.getY() + 0.5 + face.getStepY() * 0.5, pos.getZ() + 0.5 + face.getStepZ() * 0.5, SoundEvents.DEEPSLATE_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                        BlockEntityUpdate.packet(pipe);
                        return InteractionResult.SUCCESS;
                    }
                    BlockEntity blockEntity = level.getBlockEntity(facingPos);
                    if (connectToTile(blockEntity, face)) {
                        if (facingState.getBlock() instanceof IPipeConnection) {
                            pipe.setConnection(face, ((IPipeConnection) facingState.getBlock()).getPipeConnection(facingState, face.getOpposite()));
                        } else {
                            pipe.setConnection(face, PipeConnection.END);
                        }
                        level.updateNeighbourForOutputSignal(pos, this);
                        facingState.updateShape(face.getOpposite(), state, level, facingPos, pos);
                        level.playSound(null, pos.getX() + 0.5 + face.getStepX() * 0.4, pos.getY() + 0.5 + face.getStepY() * 0.4, pos.getZ() + 0.5 + face.getStepZ() * 0.4, SoundEvents.DEEPSLATE_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                        BlockEntityUpdate.packet(pipe);
                        return InteractionResult.SUCCESS;
                    }
                } else {
                    Direction direction = Direction.from3DDataValue(closestHit);
                    if (!pipe.getConnection(direction).transfer)
                        return InteractionResult.PASS;
                    BlockPos facingPos = pos.relative(direction);
                    BlockState facingState = level.getBlockState(facingPos);

                    if (pipe.getConnection(direction) == PipeConnection.PIPE && facingState.is(getToggleConnectionTag()) && level.getBlockEntity(facingPos) instanceof PipeBaseBlockEntity facingPipe) {
                        pipe.setConnection(direction, PipeConnection.DISABLED);
                        facingPipe.setConnection(direction.getOpposite(), PipeConnection.DISABLED);
                        level.updateNeighbourForOutputSignal(pos, this);
                        level.updateNeighbourForOutputSignal(facingPos, this);
                        level.playSound(null, pos.getX() + 0.5 + direction.getStepX() * 0.5, pos.getY() + 0.5 + direction.getStepY() * 0.5, pos.getZ() + 0.5 + direction.getStepZ() * 0.5, SoundEvents.DEEPSLATE_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                        BlockEntityUpdate.packet(pipe);
                        return InteractionResult.SUCCESS;
                    }
                    if (pipe.getConnection(direction).transfer && !facingState.is(getConnectionTag()) && !connected(direction, facingState)) {
                        pipe.setConnection(direction, PipeConnection.DISABLED);
                        level.updateNeighbourForOutputSignal(pos, this);
                        facingState.updateShape(direction.getOpposite(), state, level, facingPos, pos);
                        level.playSound(null, pos.getX() + 0.5 + direction.getStepX() * 0.4, pos.getY() + 0.5 + direction.getStepY() * 0.4, pos.getZ() + 0.5 + direction.getStepZ() * 0.4, SoundEvents.DEEPSLATE_HIT, SoundSource.BLOCKS, 1.0f, 1.0f);
                        BlockEntityUpdate.packet(pipe);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    public static int getShapeIndex(PipeConnection down, PipeConnection up, PipeConnection north, PipeConnection south, PipeConnection west, PipeConnection east) {
        return (((((down.visualIndex * 3 + up.visualIndex) * 3 + north.visualIndex) * 3 + south.visualIndex) * 3 + west.visualIndex) * 3) + east.visualIndex;
    }

    public VoxelShape getCenterShape() {
        return CENTER_AABB;
    }

    public static void makeShapes(VoxelShape center, VoxelShape[] shapes) {
        for (PipeConnection down : PipeConnection.visual()) {
            for (PipeConnection up : PipeConnection.visual()) {
                for (PipeConnection north : PipeConnection.visual()) {
                    for (PipeConnection south : PipeConnection.visual()) {
                        for (PipeConnection west : PipeConnection.visual()) {
                            for (PipeConnection east : PipeConnection.visual()) {
                                VoxelShape shape = center;
                                if (down == PipeConnection.PIPE)
                                    shape = Shapes.joinUnoptimized(shape, PIPE_DOWN_AABB, BooleanOp.OR);
                                else if (down == PipeConnection.END)
                                    shape = Shapes.joinUnoptimized(shape, END_DOWN_AABB, BooleanOp.OR);
                                if (up == PipeConnection.PIPE)
                                    shape = Shapes.joinUnoptimized(shape, PIPE_UP_AABB, BooleanOp.OR);
                                else if (up == PipeConnection.END)
                                    shape = Shapes.joinUnoptimized(shape, END_UP_AABB, BooleanOp.OR);
                                if (north == PipeConnection.PIPE)
                                    shape = Shapes.joinUnoptimized(shape, PIPE_NORTH_AABB, BooleanOp.OR);
                                else if (north == PipeConnection.END)
                                    shape = Shapes.joinUnoptimized(shape, END_NORTH_AABB, BooleanOp.OR);
                                if (south == PipeConnection.PIPE)
                                    shape = Shapes.joinUnoptimized(shape, PIPE_SOUTH_AABB, BooleanOp.OR);
                                else if (south == PipeConnection.END)
                                    shape = Shapes.joinUnoptimized(shape, END_SOUTH_AABB, BooleanOp.OR);
                                if (west == PipeConnection.PIPE)
                                    shape = Shapes.joinUnoptimized(shape, PIPE_WEST_AABB, BooleanOp.OR);
                                else if (west == PipeConnection.END)
                                    shape = Shapes.joinUnoptimized(shape, END_WEST_AABB, BooleanOp.OR);
                                if (east == PipeConnection.PIPE)
                                    shape = Shapes.joinUnoptimized(shape, PIPE_EAST_AABB, BooleanOp.OR);
                                else if (east == PipeConnection.END)
                                    shape = Shapes.joinUnoptimized(shape, END_EAST_AABB, BooleanOp.OR);
                                shapes[getShapeIndex(down, up, north, south, west, east)] = shape.optimize();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShapeWithConnection(state, level, pos, context, SHAPES);
    }

    public static VoxelShape getShapeWithConnection(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context, VoxelShape[] shapes) {
        BlockEntity BE = level.getBlockEntity(pos);
        if (BE instanceof PipeBaseBlockEntity pipe) {
            return shapes[getShapeIndex(pipe.connections[0], pipe.connections[1], pipe.connections[2], pipe.connections[3], pipe.connections[4], pipe.connections[5])];
        }
        return CENTER_AABB;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        BlockEntity BE = level.getBlockEntity(currentPos);
        if (BE instanceof PipeBaseBlockEntity pipe) {
            BlockEntity facingBE = level.getBlockEntity(facingPos);
            if (!(facingBE instanceof PipeBaseBlockEntity) || ((PipeBaseBlockEntity) facingBE).getConnection(facing.getOpposite()) != PipeConnection.DISABLED) {
                boolean enabled = pipe.getConnection(facing) != PipeConnection.DISABLED;
                if (facingState.is(getConnectionTag()) && enabled) {
                    if (facingBE instanceof PipeBaseBlockEntity && ((PipeBaseBlockEntity) facingBE).getConnection(facing.getOpposite()) == PipeConnection.DISABLED) {
                        pipe.setConnection(facing, PipeConnection.DISABLED);
                    } else {
                        pipe.setConnection(facing, PipeConnection.PIPE);
                    }
                } else {
                    BlockEntity blockEntity = level.getBlockEntity(facingPos);
                    if (connected(facing, facingState)) {
                        pipe.setConnection(facing, PipeConnection.LEVER);
                    } else if ((connectToTile(blockEntity, facing) && enabled)) {
                        if (facingState.getBlock() instanceof IPipeConnection) {
                            pipe.setConnection(facing, ((IPipeConnection) facingState.getBlock()).getPipeConnection(facingState, facing.getOpposite()));
                        } else {
                            pipe.setConnection(facing, PipeConnection.END);
                        }
                    } else if (enabled) {
                        pipe.setConnection(facing, PipeConnection.NONE);
                    }
                }
            }
        }
        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    public static boolean facingConnected(Direction facing, BlockState state, DirectionProperty property) {
        return !state.hasProperty(property) || state.getValue(property) == facing;
    }

    public abstract boolean connected(Direction direction, BlockState state);

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
