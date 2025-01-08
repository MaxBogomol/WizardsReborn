package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.item.FlowerFertilizerPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.FakePlayerFactory;

public class FlowerFertilizerItem extends BoneMealItem {

    public FlowerFertilizerItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result1 = useFertilizer(context);
        InteractionResult result2 = useFertilizer(context);
        if (result1 != InteractionResult.PASS || result2 != InteractionResult.PASS) {
            if (!context.getLevel().isClientSide()) {
                Vec3 pos = context.getClickedPos().getCenter();
                context.getItemInHand().shrink(1);
                WizardsRebornPacketHandler.sendToTracking(context.getLevel(), context.getClickedPos(), new FlowerFertilizerPacket(pos.add(0, -0.4f, 0)));
            }
            return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
        }

        return InteractionResult.PASS;
    }

    public InteractionResult useFertilizer(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(context.getClickedFace());
        if (applyBonemeal(ItemStack.EMPTY, level, blockpos, context.getPlayer())) {
            if (!level.isClientSide) {
                level.levelEvent(1505, blockpos, 0);
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            BlockState blockstate = level.getBlockState(blockpos);
            boolean flag = blockstate.isFaceSturdy(level, blockpos, context.getClickedFace());
            if (flag && growWaterPlant(ItemStack.EMPTY, level, blockpos1, context.getClickedFace())) {
                if (!level.isClientSide) {
                    level.levelEvent(1505, blockpos1, 0);
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.PASS;
            }
        }
    }

    public static boolean growCropFertilizer(ItemStack stack, Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            boolean result1 = (applyBonemeal(ItemStack.EMPTY, level, pos, FakePlayerFactory.getMinecraft(serverLevel)) || growWaterPlant(ItemStack.EMPTY, level, pos, null));
            boolean result2 = (applyBonemeal(ItemStack.EMPTY, level, pos, FakePlayerFactory.getMinecraft(serverLevel)) || growWaterPlant(ItemStack.EMPTY, level, pos, null));
            if (result1 || result2) {
                stack.shrink(1);
                WizardsRebornPacketHandler.sendToTracking(level, pos, new FlowerFertilizerPacket(pos.getCenter().add(0, -0.4f, 0)));
                return true;
            }
        }
        return false;
    }
}