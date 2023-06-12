package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.tileentity.CrystalTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.awt.*;
import java.util.Random;
import java.util.stream.Stream;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class CrystalBlock extends Block implements ITileEntityProvider, IWaterLoggable {

    public PolishingType polishing;
    public CrystalType type;

    private static final VoxelShape FACETED_SHAPE = Block.makeCuboidShape(5, 0, 5, 11, 9, 11);
    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(5, 0, 5, 11, 9, 11),
            Block.makeCuboidShape(3, 0, 4, 6, 3, 7),
            Block.makeCuboidShape(9, 0, 3, 12, 3, 6),
            Block.makeCuboidShape(9, 0, 10, 12, 3, 13),
            Block.makeCuboidShape(4, 0, 9, 7, 3, 12)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public CrystalBlock(CrystalType type, PolishingType polishing, Properties properties) {
        super(properties);
        this.type = type;
        this.polishing = polishing;
        setDefaultState(getDefaultState().with(WATERLOGGED, false));
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        if (polishing == WizardsReborn.CRYSTAL_POLISHING_TYPE) {
            return SHAPE;
        }

        return FACETED_SHAPE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidState = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    @Nonnull
    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader world) {
        return new CrystalTileEntity();
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : Fluids.EMPTY.getDefaultState();
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction side, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }

        return getBlockConnected(stateIn).getOpposite() == side && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, side, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean eventReceived(BlockState state, World world, BlockPos pos, int id, int param) {
        super.eventReceived(state, world, pos, id, param);
        TileEntity tileentity = world.getTileEntity(pos);
        return tileentity != null && tileentity.receiveClientEvent(id, param);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = getBlockConnected(state).getOpposite();
        return Block.hasEnoughSolidSide(worldIn, pos.offset(direction), direction.getOpposite());
    }

    protected static Direction getBlockConnected(BlockState state) {
        return Direction.UP;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (polishing.hasParticle()) {
            Color color = polishing.getColor();
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                    .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                    .setAlpha(0.5f, 0).setScale(0.1f, 0)
                    .setColor(r, g, b)
                    .setLifetime(30)
                    .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(world, pos.getX() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5), pos.getY() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5), pos.getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5));
        }
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (world.isRemote()) {
            if (!player.isCreative()) {
                Color color = type.getColor();
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                for (int i = 0; i < 25; i++) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((RANDOM.nextDouble() - 0.5D) / 15), ((RANDOM.nextDouble() - 0.5D) / 15), ((RANDOM.nextDouble() - 0.5D) / 15))
                            .setAlpha(0.25f, 0).setScale(0.35f, 0)
                            .setColor(r, g, b)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((RANDOM.nextDouble() - 0.5D) * 2)))
                            .spawn(world, pos.getX() + 0.5F + ((RANDOM.nextDouble() - 0.5D) * 0.5), pos.getY() + 0.5F + ((RANDOM.nextDouble() - 0.5D) * 0.5), pos.getZ() + 0.5F + ((RANDOM.nextDouble() - 0.5D) * 0.5));
                }
            }
        }

        super.onBlockHarvested(world, pos, state, player);
    }
}
