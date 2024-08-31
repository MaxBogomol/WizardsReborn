package mod.maxbogomol.wizards_reborn.common.block.creative.fluid_storage;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.api.alchemy.IPipeConnection;
import mod.maxbogomol.wizards_reborn.api.alchemy.PipeConnection;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.FluidAlchemyPotion;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlock;
import mod.maxbogomol.wizards_reborn.common.block.pipe.PipeBaseBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyPotionItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
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
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CreativeFluidStorageBlock extends Block implements EntityBlock, SimpleWaterloggedBlock, IPipeConnection {

    public static final VoxelShape SHAPE = Block.box(3,3,3,13,13,13);
    public static final VoxelShape[] SHAPES = new VoxelShape[729];

    static {
        PipeBaseBlock.makeShapes(SHAPE, SHAPES);
    }

    public CreativeFluidStorageBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext ctx) {
        return PipeBaseBlock.getShapeWithConnection(state, world, pos, ctx, SHAPES);
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
        if (world.getBlockEntity(pos) instanceof CreativeFluidStorageBlockEntity storage) {
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.isEmpty()) {
                IFluidHandler cap = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
                if (cap != null) {
                    storage.getTank().setFluid(new FluidStack(cap.getFluidInTank(0).getFluid(), Integer.MAX_VALUE));
                    SoundEvent soundevent = cap.getFluidInTank(0).getFluid().getFluidType().getSound(cap.getFluidInTank(0), SoundActions.BUCKET_FILL);
                    world.playSound(player, pos, soundevent, SoundSource.BLOCKS, 1F, 1f);

                    return InteractionResult.SUCCESS;
                }
                if (stack.getItem() instanceof AlchemyPotionItem vial) {
                    AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
                    if (!AlchemyPotionUtil.isEmpty(potion)) {
                        if (potion instanceof FluidAlchemyPotion fluidPotion) {
                            FluidStack fluid = new FluidStack(fluidPotion.fluid, Integer.MAX_VALUE);
                            storage.getTank().setFluid(fluid);
                            world.playSound(player, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 0.3F, 0.7f);

                            return InteractionResult.SUCCESS;
                        }
                    }
                }
                if (stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent()) {
                    return InteractionResult.CONSUME_PARTIAL;
                }
            }
        }
        return InteractionResult.PASS;
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

        if (pLevel.getBlockEntity(pCurrentPos) instanceof PipeBaseBlockEntity pipe) {
            BlockEntity facingBE = pLevel.getBlockEntity(pCurrentPos);
            if (pNeighborState.is(WizardsReborn.FLUID_PIPE_CONNECTION_BLOCK_TAG)) {
                if (facingBE instanceof PipeBaseBlockEntity && ((PipeBaseBlockEntity) facingBE).getConnection(pDirection.getOpposite()) == PipeConnection.DISABLED) {
                    pipe.setConnection(pDirection, PipeConnection.NONE);
                } else {
                    pipe.setConnection(pDirection, PipeConnection.PIPE);
                }
            } else {
                pipe.setConnection(pDirection, PipeConnection.NONE);
            }
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
        return new CreativeFluidStorageBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return TickableBlockEntity.getTickerHelper();
    }

    @Override
    public PipeConnection getPipeConnection(BlockState state, Direction direction) {
        return PipeConnection.END;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        CreativeFluidStorageBlockEntity tile = (CreativeFluidStorageBlockEntity) level.getBlockEntity(pos);
        return Mth.floor(((float) tile.getTank().getFluidAmount() / tile.getMaxCapacity()) * 14.0F);
    }
}
