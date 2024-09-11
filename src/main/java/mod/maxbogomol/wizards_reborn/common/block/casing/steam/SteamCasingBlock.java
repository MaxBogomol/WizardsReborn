package mod.maxbogomol.wizards_reborn.common.block.casing.steam;

import mod.maxbogomol.wizards_reborn.common.block.casing.CasingBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.steam.SteamPipeBlock;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
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
    
    public SteamCasingBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, false).setValue(BlockStateProperties.POWERED, false));
    }

    public static VoxelShape[] SHAPES = new VoxelShape[729];

    static {
        makeShapes(CasingBlock.SHAPE, SHAPES);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return PipeBaseBlock.getShapeWithConnection(state, level, pos, context, SHAPES);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return WizardsRebornBlockEntities.STEAM_CASING.get().create(pos, state);
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            InteractionResult interactionResult = super.use(state, level, pos, player, hand, hit);
            if (interactionResult != InteractionResult.PASS) {
                return InteractionResult.SUCCESS;
            }

            BlockState blockstate = this.pull(state, level, pos);
            float f = blockstate.getValue(BlockStateProperties.POWERED) ? 0.6F : 0.5F;
            level.playSound((Player)null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, f);
            level.gameEvent(player, blockstate.getValue(BlockStateProperties.POWERED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    public BlockState pull(BlockState state, Level level, BlockPos pos) {
        state = state.cycle(BlockStateProperties.POWERED);
        level.setBlock(pos, state, 3);
        this.updateNeighbours(state, level, pos);
        return state;
    }


    private void updateNeighbours(BlockState state, Level level, BlockPos pos) {
        level.updateNeighborsAt(pos, this);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED).add(BlockStateProperties.POWERED);
    }
}
