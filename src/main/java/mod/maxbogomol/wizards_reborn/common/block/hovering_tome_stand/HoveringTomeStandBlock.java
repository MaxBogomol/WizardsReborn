package mod.maxbogomol.wizards_reborn.common.block.hovering_tome_stand;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class HoveringTomeStandBlock extends Block implements EntityBlock, SimpleWaterloggedBlock {

    public static Map<Block, Block> blocksList = new HashMap<>();

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(5, 13, 5, 11, 14, 11),
            Block.box(2, 11, 2, 14, 13, 14),
            Block.box(4, 2, 4, 12, 11, 12),
            Block.box(2, 0, 2, 14, 2, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public HoveringTomeStandBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand).copy();

        if (player.isShiftKeyDown()) {
            Block block = blocksList.get(world.getBlockState(pos).getBlock());
            if (block != null) {
                world.setBlockAndUpdate(pos, block.defaultBlockState());
                ItemStack arcanemicon = new ItemStack(WizardsReborn.ARCANEMICON.get());
                if (player.getInventory().getSlotWithRemainingSpace(arcanemicon) != -1 || player.getInventory().getFreeSlot() > -1) {
                    player.getInventory().add(arcanemicon);
                } else {
                    world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5F, pos.getY() + 1.0F, pos.getZ() + 0.5F, arcanemicon));
                }
                world.playSound(null, pos, WizardsReborn.PEDESTAL_REMOVE_SOUND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                world.playSound(null, pos, SoundEvents.BOOK_PAGE_TURN, SoundSource.BLOCKS, 1.0f, 1.0f);
                return InteractionResult.SUCCESS;
            }
        }

        if (world.isClientSide) {
            openGui(pos);
        }

        return InteractionResult.SUCCESS;
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(BlockPos blockPos) {
        Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
        Minecraft.getInstance().setScreen(new ArcanemiconGui());
        ArcanemiconGui.blockPos = blockPos;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pState.getValue(BlockStateProperties.WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int id, int param) {
        super.triggerEvent(state, world, pos, id, param);
        BlockEntity tileentity = world.getBlockEntity(pos);
        return tileentity != null && tileentity.triggerEvent(id, param);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new HoveringTomeStandBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }
}
