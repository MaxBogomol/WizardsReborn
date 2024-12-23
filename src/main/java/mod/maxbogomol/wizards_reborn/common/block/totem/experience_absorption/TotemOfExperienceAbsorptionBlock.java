package mod.maxbogomol.wizards_reborn.common.block.totem.experience_absorption;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;

import mod.maxbogomol.wizards_reborn.api.wissen.ITotemBlock;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.util.TotemOfExperienceAbsorptionBlockUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

import net.minecraft.util.Mth;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.stream.Stream;

public class TotemOfExperienceAbsorptionBlock extends Block implements EntityBlock, SimpleWaterloggedBlock, ITotemBlock {

    private static final VoxelShape SHAPE = Stream.of(
            Block.box(4, 0, 4, 12, 4, 12),
            Block.box(6, 4, 6, 10, 6, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public TotemOfExperienceAbsorptionBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Nonnull

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
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
    public void onRemove(@Nonnull BlockState state, @Nonnull Level level, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof TotemOfExperienceAbsorptionBlockEntity totem) {
                if (totem.getExperience() > 0) {
                    level.addFreshEntity(new ExperienceOrb(level, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, totem.getExperience()));
                }
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        TotemOfExperienceAbsorptionBlockEntity blockEntity = (TotemOfExperienceAbsorptionBlockEntity) level.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand).copy();

        if (!WissenWandItem.isClickable(stack)) {
            level.updateNeighbourForOutputSignal(pos, this);
            BlockEntityUpdate.packet(blockEntity);
            return InteractionResult.SUCCESS;
        }

        if (!player.isShiftKeyDown()) {
            if (blockEntity.getExperience() < blockEntity.getMaxExperience()) {
                int remain = WissenUtil.getRemoveWissenRemain(getPlayerXP(player), 100);
                remain = 100 - remain;
                int remainAdd = WissenUtil.getAddWissenRemain(blockEntity.getExperience(), remain, blockEntity.getMaxExperience());
                remainAdd = remain - remainAdd;
                if (remainAdd > 0 && remain > 0) {
                    blockEntity.addExperience(remainAdd);
                    player.giveExperiencePoints(-remainAdd);
                    level.playSound(null, player.getOnPos(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.5f, 1.2f);
                    if (player.level().isClientSide()) {
                        TotemOfExperienceAbsorptionBlockUtil.transferExperienceToPlayer(level, pos, player, blockEntity);
                    }
                    level.updateNeighbourForOutputSignal(pos, this);
                    BlockEntityUpdate.packet(blockEntity);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResult useTotem(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return use(state, level, pos, player, hand, hit);
    }

    public static int getPlayerXP(Player player) {
        return (int)(getExperienceForLevel(player.experienceLevel) + (player.experienceProgress * player.getXpNeededForNextLevel()));
    }

    public static int getExperienceForLevel(int level) {
        if (level == 0) return 0;
        if (level <= 15) return sum(level, 7, 2);
        if (level <= 30) return 315 + sum(level - 15, 37, 5);
        return 1395 + sum(level - 30, 112, 9);
    }

    private static int sum(int n, int a0, int d) {
        return n * (2 * a0 + (n - 1) * d) / 2;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = level.getBlockState(blockpos);
        if (blockstate.getBlock() == WizardsRebornBlocks.ARCANE_PEDESTAL.get() || blockstate.getBlock() == WizardsRebornBlocks.TOTEM_BASE.get()) {
            return true;
        }

        return false;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (level.getBlockState(pos.below()).getBlock() == WizardsRebornBlocks.ARCANE_PEDESTAL.get()) {
            level.setBlockAndUpdate(pos.below(), WizardsRebornBlocks.TOTEM_BASE.get().defaultBlockState());
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TotemOfExperienceAbsorptionBlockEntity(pos, state);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        TotemOfExperienceAbsorptionBlockEntity blockEntity = (TotemOfExperienceAbsorptionBlockEntity) level.getBlockEntity(pos);
        return Mth.floor(((float) blockEntity.getWissen() / blockEntity.getMaxWissen()) * 14.0F);
    }
}
