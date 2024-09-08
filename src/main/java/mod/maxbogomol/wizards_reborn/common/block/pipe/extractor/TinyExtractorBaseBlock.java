package mod.maxbogomol.wizards_reborn.common.block.pipe.extractor;

import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.pipe.TinyPipeBaseBlock;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class TinyExtractorBaseBlock extends TinyPipeBaseBlock {

    public static final VoxelShape EXTRACTOR_AABB = Block.box(5,5,5,11,11,11);
    public static final VoxelShape[] EXTRACTOR_SHAPES = new VoxelShape[729];

    static {
        makeShapes(EXTRACTOR_AABB, EXTRACTOR_SHAPES);
    }

    public TinyExtractorBaseBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, false).setValue(BlockStateProperties.LIT, false).setValue(BlockStateProperties.POWERED, false));
    }

    @Override
    public VoxelShape getCenterShape() {
        return EXTRACTOR_AABB;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        BlockEntity BE = level.getBlockEntity(pos);
        if (BE instanceof PipeBaseBlockEntity pipe) {
            return EXTRACTOR_SHAPES[getShapeIndex(pipe.connections[0], pipe.connections[1], pipe.connections[2], pipe.connections[3], pipe.connections[4], pipe.connections[5])];
        }
        return CENTER_AABB;
    }

    @Override
    public boolean connected(Direction direction, BlockState state) {
        if (!state.is(WizardsRebornBlockTags.EXTRACTOR_LEVER_CONNECTION)) {
            return false;
        }

        if (state.hasProperty(BlockStateProperties.AXIS)) {
            return state.getValue(BlockStateProperties.AXIS) == direction.getAxis();
        }

        if (state.hasProperty(BlockStateProperties.ATTACH_FACE) && state.getValue(BlockStateProperties.ATTACH_FACE) != AttachFace.WALL) {
            if (direction == Direction.DOWN && state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.CEILING)
                return true;
            if (direction == Direction.UP && state.getValue(BlockStateProperties.ATTACH_FACE) == AttachFace.FLOOR)
                return true;
            return false;
        }

        return facingConnected(direction, state, BlockStateProperties.HORIZONTAL_FACING)
                && facingConnected(direction, state, BlockStateProperties.FACING);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (!level.isClientSide) {
            boolean flag = state.getValue(BlockStateProperties.LIT);
            if (flag != level.hasNeighborSignal(pos)) {
                if (flag) {
                    level.scheduleTick(pos, this, 4);
                } else {
                    level.setBlock(pos, state.cycle(BlockStateProperties.LIT), 2);
                }
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(BlockStateProperties.LIT) && !level.hasNeighborSignal(pos)) {
            level.setBlock(pos, state.cycle(BlockStateProperties.LIT), 2);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            InteractionResult interactionResult = super.use(state, level, pos, player, hand, hit);
            if (interactionResult != InteractionResult.PASS) {
                return InteractionResult.SUCCESS;
            }

            BlockState blockstate = this.pull(state, level, pos);
            float f = blockstate.getValue(BlockStateProperties.POWERED) ? 0.6F : 0.5F;
            level.playSound(null, pos, SoundEvents.LEVER_CLICK, SoundSource.BLOCKS, 0.3F, f);
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
        builder.add(BlockStateProperties.WATERLOGGED).add(BlockStateProperties.LIT).add(BlockStateProperties.POWERED);
    }
}