package mod.maxbogomol.wizards_reborn.common.block.pipe;

import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class TinyPipeBaseBlock extends PipeBaseBlock {
    public static VoxelShape CENTER_AABB = Block.box(6,6,6,10,10,10);
    public static VoxelShape PIPE_DOWN_AABB = Block.box(6,0,6,10,6,10);
    public static VoxelShape END_DOWN_AABB = Shapes.or(Block.box(5,0,5,11,1,11), PIPE_DOWN_AABB);
    public static VoxelShape PIPE_UP_AABB = Block.box(6,10,6,10,16,10);
    public static VoxelShape END_UP_AABB = Shapes.or(Block.box(5,15,5,11,16,11), PIPE_UP_AABB);
    public static VoxelShape PIPE_NORTH_AABB = Block.box(6,6,0,10,10,6);
    public static VoxelShape END_NORTH_AABB = Shapes.or(Block.box(5,5,0,11,11,1), PIPE_NORTH_AABB);
    public static VoxelShape PIPE_SOUTH_AABB = Block.box(6,6,10,10,10,16);
    public static VoxelShape END_SOUTH_AABB = Shapes.or(Block.box(5,5,15,11,11,16), PIPE_SOUTH_AABB);
    public static VoxelShape PIPE_WEST_AABB = Block.box(0,6,6,6,10,10);
    public static VoxelShape END_WEST_AABB = Shapes.or(Block.box(0,5,5,1,11,11), PIPE_WEST_AABB);
    public static VoxelShape PIPE_EAST_AABB = Block.box(10,6,6,16,10,10);
    public static VoxelShape END_EAST_AABB = Shapes.or(Block.box(15,5,5,16,11,11), PIPE_EAST_AABB);
    public static VoxelShape[] PIPE_AABBS = new VoxelShape[] { PIPE_DOWN_AABB, PIPE_UP_AABB, PIPE_NORTH_AABB, PIPE_SOUTH_AABB, PIPE_WEST_AABB, PIPE_EAST_AABB };
    public static VoxelShape[] END_AABBS = new VoxelShape[] { END_DOWN_AABB, END_UP_AABB, END_NORTH_AABB, END_SOUTH_AABB, END_WEST_AABB, END_EAST_AABB };
    public static VoxelShape[] SHAPES = new VoxelShape[729];

    static {
        makeShapes(CENTER_AABB, SHAPES);
    }

    public TinyPipeBaseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public VoxelShape getCenterShape() {
        return CENTER_AABB;
    }

    @Override
    public VoxelShape[] getPipeAabbs() {
        return PIPE_AABBS;
    }

    @Override
    public VoxelShape[] getEndAabbs() {
        return END_AABBS;
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
        BlockEntity BE = level.getBlockEntity(pos);
        if (BE instanceof PipeBaseBlockEntity pipe) {
            return SHAPES[getShapeIndex(pipe.connections[0], pipe.connections[1], pipe.connections[2], pipe.connections[3], pipe.connections[4], pipe.connections[5])];
        }
        return CENTER_AABB;
    }
}
