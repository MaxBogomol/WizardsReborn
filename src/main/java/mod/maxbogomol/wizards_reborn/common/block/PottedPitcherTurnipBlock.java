package mod.maxbogomol.wizards_reborn.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public class PottedPitcherTurnipBlock extends FlowerPotBlock {
    private static final VoxelShape SHAPE = Stream.of(
                    Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D),
                    Block.box(3, 6, 3, 13, 12, 13)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public PottedPitcherTurnipBlock(Block content, Properties pProperties) {
        super(content, pProperties);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }
}
