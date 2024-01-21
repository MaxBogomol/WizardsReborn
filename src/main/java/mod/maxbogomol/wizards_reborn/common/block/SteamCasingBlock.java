package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

public class SteamCasingBlock extends SteamPipeBlock {
    public SteamCasingBlock(Properties pProperties) {
        super(pProperties);
    }

    public static VoxelShape[] SHAPES = new VoxelShape[729];

    static {
        makeShapes(CasingBlock.SHAPE, SHAPES);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return PipeBaseBlock.getShapeWithConnection(state, world, pos, ctx, SHAPES);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return WizardsReborn.STEAM_CASING_TILE_ENTITY.get().create(pPos, pState);
    }
}
