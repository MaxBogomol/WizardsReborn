package mod.maxbogomol.wizards_reborn.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public class FrameBlock extends Block {
    public FrameBlock(Properties pProperties) {
        super(pProperties);
    }

    private static final VoxelShape SHAPE_SHELL = Block.box(0, 0, 0, 16, 16, 16);

    private static final VoxelShape SHAPE_HOLE = Stream.of(
            Block.box(3, 0, 3, 13, 16, 13),
            Block.box(3, 3, 0, 13, 13, 16),
            Block.box(0, 3, 3, 16, 13, 13)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
    private static final VoxelShape SHAPE = Shapes.join(SHAPE_SHELL, SHAPE_HOLE, BooleanOp.ONLY_FIRST);

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }
}
