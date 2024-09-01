package mod.maxbogomol.wizards_reborn.common.block.casing.steam;

import mod.maxbogomol.wizards_reborn.common.block.casing.CasingBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.steam_pipe.SteamPipeBlock;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;

public class SteamCasingBlock extends SteamPipeBlock {
    public SteamCasingBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, false).setValue(BlockStateProperties.POWERED, false));
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
        return WizardsRebornBlockEntities.STEAM_CASING.get().create(pPos, pState);
    }

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide) {
            InteractionResult interactionResult = super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
            if (interactionResult != InteractionResult.PASS) {
                return InteractionResult.SUCCESS;
            }

            BlockState blockstate = this.pull(pState, pLevel, pPos);
            float f = blockstate.getValue(BlockStateProperties.POWERED) ? 0.6F : 0.5F;
            pLevel.playSound((Player)null, pPos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, f);
            pLevel.gameEvent(pPlayer, blockstate.getValue(BlockStateProperties.POWERED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pPos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    public BlockState pull(BlockState pState, Level pLevel, BlockPos pPos) {
        pState = pState.cycle(BlockStateProperties.POWERED);
        pLevel.setBlock(pPos, pState, 3);
        this.updateNeighbours(pState, pLevel, pPos);
        return pState;
    }


    private void updateNeighbours(BlockState pState, Level pLevel, BlockPos pPos) {
        pLevel.updateNeighborsAt(pPos, this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(BlockStateProperties.WATERLOGGED).add(BlockStateProperties.POWERED);
    }
}
