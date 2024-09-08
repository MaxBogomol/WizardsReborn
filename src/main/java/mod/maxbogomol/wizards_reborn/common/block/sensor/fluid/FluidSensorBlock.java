package mod.maxbogomol.wizards_reborn.common.block.sensor.fluid;

import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidBlockEntity;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.FluidAlchemyPotion;
import mod.maxbogomol.wizards_reborn.common.block.sensor.SensorBaseBlock;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyPotionItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import net.minecraft.client.resources.model.ModelResourceLocation;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class FluidSensorBlock extends SensorBaseBlock {

    public FluidSensorBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected int getInputSignal(Level level, BlockPos pos, BlockState state) {
        int i = super.getInputSignal(level, pos, state);
        Direction direction = state.getValue(FACING);
        BlockPos blockpos = pos.relative(direction);

        switch (state.getValue(FACE)) {
            case FLOOR:
                blockpos = pos.above();
                direction = Direction.UP;
                break;
            case WALL:
                direction = direction.getOpposite();
                break;
            case CEILING:
                blockpos = pos.below();
                direction = Direction.DOWN;
                break;
        }

        if (level.getBlockEntity(pos) instanceof FluidSensorBlockEntity sensor) {
            BlockEntity tile = level.getBlockEntity(blockpos);
            boolean active = ((!state.getValue(BlockStateProperties.LIT) || level.hasNeighborSignal(pos)));
            if (tile instanceof IFluidBlockEntity fluidTile) {
                if (!sensor.getTank().isEmpty()) {
                    if ((active && fluidTile.getFluidStack().getFluid().isSame(sensor.getTank().getFluid().getFluid())) ||
                            (!active && fluidTile.getFluidStack().getFluid() != sensor.getTank().getFluid().getFluid())) {
                        i = Mth.floor(((float) fluidTile.getFluidAmount() / fluidTile.getFluidMaxAmount()) * 14.0F);
                    }
                } else {
                    i = Mth.floor(((float) fluidTile.getFluidAmount() / fluidTile.getFluidMaxAmount()) * 14.0F);
                }
            }

            if (tile != null) {
                IFluidHandler cap = tile.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).orElse(null);
                if (cap != null) {
                    if (!sensor.getTank().isEmpty()) {
                        if ((active && cap.getFluidInTank(0).getFluid() == sensor.getTank().getFluid().getFluid()) ||
                                (!active && !cap.getFluidInTank(0).getFluid().isSame(sensor.getTank().getFluid().getFluid()))) {
                            i = Mth.floor(((float) cap.getFluidInTank(0).getAmount() / cap.getTankCapacity(0)) * 14.0F);
                        }
                    } else {
                        i = Mth.floor(((float) cap.getFluidInTank(0).getAmount() / cap.getTankCapacity(0)) * 14.0F);
                    }
                }
            }
        }

        return i;
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            if (level.getBlockEntity(pos) instanceof FluidSensorBlockEntity sensorTile) {
                ItemStack stack = player.getItemInHand(hand);
                if (!stack.isEmpty()) {
                    IFluidHandler cap = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
                    if (cap != null) {
                        sensorTile.getTank().setFluid(new FluidStack(cap.getFluidInTank(0).getFluid(), 1));
                        SoundEvent soundevent = cap.getFluidInTank(0).getFluid().getFluidType().getSound(cap.getFluidInTank(0), SoundActions.BUCKET_FILL);
                        level.playSound(player, pos, soundevent, SoundSource.BLOCKS, 1F, 1f);

                        return InteractionResult.SUCCESS;
                    }
                    if (stack.getItem() instanceof AlchemyPotionItem vial) {
                        AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
                        if (!AlchemyPotionUtil.isEmpty(potion)) {
                            if (potion instanceof FluidAlchemyPotion fluidPotion) {
                                FluidStack fluid = new FluidStack(fluidPotion.fluid, 1);
                                sensorTile.getTank().setFluid(fluid);
                                level.playSound(player, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1F, 1f);

                                return InteractionResult.SUCCESS;
                            }
                        }
                    }
                }

                if (player.isShiftKeyDown()) {
                    sensorTile.getTank().setFluid(FluidStack.EMPTY);
                    level.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, 0.7f);
                    return InteractionResult.SUCCESS;
                }
            }

            state = state.setValue(BlockStateProperties.LIT, !state.getValue(BlockStateProperties.LIT));
            level.playSound(player, pos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, 0.5f);
            level.setBlock(pos, state, 2);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FluidSensorBlockEntity(pos, state);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ModelResourceLocation getModel(BlockState state) {
        if (state.getValue(BlockStateProperties.LIT)) {
            return WizardsRebornModels.FLUID_SENSOR_PIECE_ON;
        }
        return WizardsRebornModels.FLUID_SENSOR_PIECE;
    }
}
