package mod.maxbogomol.wizards_reborn.common.block;

import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtils;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidTileEntity;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.FluidAlchemyPotion;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyPotionItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.FluidSensorTileEntity;
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
    protected int getInputSignal(Level pLevel, BlockPos pPos, BlockState pState) {
        int i = super.getInputSignal(pLevel, pPos, pState);
        Direction direction = pState.getValue(FACING);
        BlockPos blockpos = pPos.relative(direction);

        switch (pState.getValue(FACE)) {
            case FLOOR:
                blockpos = pPos.above();
                direction = Direction.UP;
                break;
            case WALL:
                direction = direction.getOpposite();
                break;
            case CEILING:
                blockpos = pPos.below();
                direction = Direction.DOWN;
                break;
        }

        if (pLevel.getBlockEntity(pPos) instanceof FluidSensorTileEntity sensor) {
            BlockEntity tile = pLevel.getBlockEntity(blockpos);
            boolean active = ((!pState.getValue(BlockStateProperties.LIT) || pLevel.hasNeighborSignal(pPos)));
            if (tile instanceof IFluidTileEntity fluidTile) {
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

    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pPlayer.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            if (pLevel.getBlockEntity(pPos) instanceof FluidSensorTileEntity sensorTile) {
                ItemStack stack = pPlayer.getItemInHand(pHand);
                if (!stack.isEmpty()) {
                    IFluidHandler cap = stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).orElse(null);
                    if (cap != null) {
                        sensorTile.getTank().setFluid(new FluidStack(cap.getFluidInTank(0).getFluid(), 1));
                        SoundEvent soundevent = cap.getFluidInTank(0).getFluid().getFluidType().getSound(cap.getFluidInTank(0), SoundActions.BUCKET_FILL);
                        pLevel.playSound(pPlayer, pPos, soundevent, SoundSource.BLOCKS, 1F, 1f);

                        return InteractionResult.SUCCESS;
                    }
                    if (stack.getItem() instanceof AlchemyPotionItem vial) {
                        AlchemyPotion potion = AlchemyPotionUtils.getPotion(stack);
                        if (!AlchemyPotionUtils.isEmpty(potion)) {
                            if (potion instanceof FluidAlchemyPotion fluidPotion) {
                                FluidStack fluid = new FluidStack(fluidPotion.fluid, 1);
                                sensorTile.getTank().setFluid(fluid);
                                pLevel.playSound(pPlayer, pPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1F, 1f);

                                return InteractionResult.SUCCESS;
                            }
                        }
                    }
                }

                if (pPlayer.isShiftKeyDown()) {
                    sensorTile.getTank().setFluid(FluidStack.EMPTY);
                    pLevel.playSound(pPlayer, pPos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, 0.7f);
                    return InteractionResult.SUCCESS;
                }
            }

            pState = pState.setValue(BlockStateProperties.LIT, !pState.getValue(BlockStateProperties.LIT));
            pLevel.playSound(pPlayer, pPos, SoundEvents.COMPARATOR_CLICK, SoundSource.BLOCKS, 0.3F, 0.5f);
            pLevel.setBlock(pPos, pState, 2);
            return InteractionResult.sidedSuccess(pLevel.isClientSide);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FluidSensorTileEntity(pPos, pState);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ModelResourceLocation getModel(BlockState state) {
        if (state.getValue(BlockStateProperties.LIT)) {
            return WizardsRebornClient.FLUID_SENSOR_PIECE_ON_MODEl;
        }
        return WizardsRebornClient.FLUID_SENSOR_PIECE_MODEl;
    }
}
