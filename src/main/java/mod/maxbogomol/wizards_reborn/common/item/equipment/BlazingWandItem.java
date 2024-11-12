package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.salt.campfire.SaltCampfireBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.salt.lantern.SaltLanternBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.salt.torch.SaltTorchBlockEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.item.BlazingWandBurstPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.CandleCakeBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;

public class BlazingWandItem extends Item implements IGuiParticleItem {

    public BlazingWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);

        float costModifier = WissenUtil.getWissenCostModifierWithDiscount(player);
        List<ItemStack> items = WissenUtil.getWissenItemsNoneAndStorage(WissenUtil.getWissenItemsCurios(player));
        int wissen = WissenUtil.getWissenInItems(items);
        int cost = (int) (getWissenCost() * (1 - costModifier));
        if (cost <= 0) {
            cost = 1;
        }

        if (WissenUtil.canRemoveWissen(wissen, cost)) {
            if (isClickable(level, blockPos)) {
                level.playSound(null, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                level.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, !blockState.getValue(BlockStateProperties.LIT)), 11);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                WissenUtil.removeWissenFromWissenItems(items, cost);
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!level.isClientSide()) WizardsRebornPacketHandler.sendToTracking(level, blockPos, new BlazingWandBurstPacket(blockPos));
                return InteractionResult.SUCCESS;
            }

            if (!CampfireBlock.canLight(blockState) && !CandleBlock.canLight(blockState) && !CandleCakeBlock.canLight(blockState)) {
                BlockPos blockPos1 = blockPos.relative(context.getClickedFace());
                if (BaseFireBlock.canBePlacedAt(level, blockPos1, Direction.UP)) {
                    level.playSound(null, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                    BlockState blockstate1 = BaseFireBlock.getState(level, blockPos1);
                    level.setBlock(blockPos1, blockstate1, 11);
                    WissenUtil.removeWissenFromWissenItems(items, cost);
                    player.awardStat(Stats.ITEM_USED.get(this));
                    if (!level.isClientSide()) WizardsRebornPacketHandler.sendToTracking(level, blockPos1, new BlazingWandBurstPacket(blockPos1));
                    return InteractionResult.SUCCESS;
                }
            } else {
                level.playSound(null, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                level.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                WissenUtil.removeWissenFromWissenItems(items, cost);
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!level.isClientSide()) WizardsRebornPacketHandler.sendToTracking(level, blockPos, new BlazingWandBurstPacket(blockPos));
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    public float getWissenCost() {
        return 10f;
    }

    public static boolean isClickable(Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        return ((blockEntity instanceof SaltTorchBlockEntity) || (blockEntity instanceof SaltLanternBlockEntity) || (blockEntity instanceof SaltCampfireBlockEntity));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        float ticks = ClientTickHandler.getTotal();
        Color color1 = SaltTorchBlockEntity.colorFirst;
        Color color2 = SaltTorchBlockEntity.colorSecond;

        poseStack.pushPose();
        poseStack.translate(x + 10.5f, y + 5.5f, 100);
        poseStack.mulPose(Axis.ZP.rotationDegrees(ticks));
        RenderBuilder sparkleBuilder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/sparkle"))
                .setColor(color1).setAlpha(0.5f)
                .renderCenteredQuad(poseStack, 7f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(45));
        sparkleBuilder.renderCenteredQuad(poseStack, 7f);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(x + 10.5f, y + 5.5f, 100);
        poseStack.mulPose(Axis.ZP.rotationDegrees(ticks));
        RenderBuilder wispBuilder = RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/wisp"))
                .setColor(color2).setAlpha(0.15f)
                .renderCenteredQuad(poseStack, 6f);
        poseStack.mulPose(Axis.ZP.rotationDegrees(45));
        wispBuilder.renderCenteredQuad(poseStack, 6f)
                .endBatch();
        poseStack.popPose();
    }
}