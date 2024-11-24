package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.fluffy_fur.common.fire.FireItemHandler;
import mod.maxbogomol.fluffy_fur.common.fire.FireItemModifier;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.block.salt.campfire.SaltCampfireBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.salt.lantern.SaltLanternBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.salt.torch.SaltTorchBlockEntity;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.item.BlazingWandAttackPacket;
import mod.maxbogomol.wizards_reborn.common.network.item.BlazingWandBurstPacket;
import mod.maxbogomol.wizards_reborn.common.network.item.BlazingWandExtinguishingPacket;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class BlazingWandItem extends Item implements IGuiParticleItem {

    private static final Random random = new Random();

    public BlazingWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> atts = ImmutableMultimap.builder();
        atts.putAll(super.getDefaultAttributeModifiers(slot));
        atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_UUID, "bonus", -2.8f, AttributeModifier.Operation.ADDITION));
        return slot == EquipmentSlot.MAINHAND ? atts.build() : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide()) {
            if (attacker instanceof Player player) {
                if (player.getAttackStrengthScale(0) >= 1) {
                    float costModifier = WissenUtil.getWissenCostModifierWithDiscount(player);
                    List<ItemStack> items = WissenUtil.getWissenItemsNoneAndStorage(WissenUtil.getWissenItemsCurios(player));
                    int wissen = WissenUtil.getWissenInItems(items);
                    int cost = (int) (getEntityWissenCost() * (1 - costModifier));
                    if (cost <= 0) cost = 1;

                    if (WissenUtil.canRemoveWissen(wissen, cost)) {
                        player.sweepAttack();
                        WissenUtil.removeWissenFromWissenItems(items, cost);
                        int fire = target.getRemainingFireTicks() + 25;
                        if (fire > 200) fire = 200;
                        target.setSecondsOnFire(fire);
                        int invulnerableTime = target.invulnerableTime;
                        target.invulnerableTime = 0;
                        target.hurt(DamageHandler.create(player.level(), WizardsRebornDamageTypes.ARCANE_MAGIC, player, player), getAttackDamage());
                        target.invulnerableTime = invulnerableTime;
                        target.level().playSound(WizardsReborn.proxy.getPlayer(), target.blockPosition(), SoundEvents.FIRE_EXTINGUISH, SoundSource.PLAYERS, 0.5f, (float) (1.2f + ((random.nextFloat() - 0.5D) / 2)));
                        WizardsRebornPacketHandler.sendToTracking(target.level(), target.blockPosition(), new BlazingWandAttackPacket(target.position().add(0, target.getBbHeight() / 2f, 0)));
                    }
                }
            }
        }
        return super.hurtEnemy(stack, target, attacker);
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
        int cost = (int) (getBlockWissenCost() * (1 - costModifier));
        if (cost <= 0) cost = 1;

        if (WissenUtil.canRemoveWissen(wissen, cost)) {
            if (isClickable(level, blockPos)) {
                boolean flag = blockState.getValue(BlockStateProperties.LIT);
                level.playSound(null, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                level.setBlock(blockPos, blockState.setValue(BlockStateProperties.LIT, !flag), 11);
                level.gameEvent(player, GameEvent.BLOCK_CHANGE, blockPos);
                WissenUtil.removeWissenFromWissenItems(items, cost);
                player.awardStat(Stats.ITEM_USED.get(this));
                if (!level.isClientSide()) {
                    if (flag) {
                        WizardsRebornPacketHandler.sendToTracking(level, blockPos, new BlazingWandExtinguishingPacket(blockPos));
                    } else {
                        WizardsRebornPacketHandler.sendToTracking(level, blockPos, new BlazingWandBurstPacket(blockPos));
                    }
                }
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

    public float getBlockWissenCost() {
        return 10f;
    }

    public float getEntityWissenCost() {
        return 50f;
    }

    public float getAttackDamage() {
        return 4f;
    }

    public static boolean isClickable(Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        return ((blockEntity instanceof SaltTorchBlockEntity) || (blockEntity instanceof SaltLanternBlockEntity) || (blockEntity instanceof SaltCampfireBlockEntity));
    }

    public static void registerFireItemModifier() {
        FireItemHandler.register(new FireItemModifier() {
            public boolean isCreeperInteract(Entity entity, Player player, InteractionHand hand) {
                return player.getItemInHand(hand).getItem() instanceof BlazingWandItem item && canWissenUse(item, player);
            }

            public boolean isTntUse(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
                return player.getItemInHand(hand).getItem() instanceof BlazingWandItem item && canWissenUse(item, player);
            }

            public void creeperInteract(Entity entity, Player player, InteractionHand hand) {
                if (player.getItemInHand(hand).getItem() instanceof BlazingWandItem item) {
                    wissenUse(item, player);
                    if (!player.level().isClientSide()) WizardsRebornPacketHandler.sendToTracking(player.level(), entity.blockPosition(), new BlazingWandBurstPacket(entity.position().add(0, entity.getBbHeight() / 2f, 0)));
                    super.creeperInteract(entity, player, hand);
                }
            }

            public void tntUse(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
                if (player.getItemInHand(hand).getItem() instanceof BlazingWandItem item) {
                    wissenUse(item, player);
                    if (!level.isClientSide()) WizardsRebornPacketHandler.sendToTracking(level, pos, new BlazingWandBurstPacket(pos));
                    super.tntUse(state, level, pos, player, hand, hit);
                }
            }

            public boolean canWissenUse(BlazingWandItem item, Player player) {
                float costModifier = WissenUtil.getWissenCostModifierWithDiscount(player);
                List<ItemStack> items = WissenUtil.getWissenItemsNoneAndStorage(WissenUtil.getWissenItemsCurios(player));
                int wissen = WissenUtil.getWissenInItems(items);
                int cost = (int) (item.getBlockWissenCost() * (1 - costModifier));
                if (cost <= 0) cost = 1;
                return (WissenUtil.canRemoveWissen(wissen, cost));
            }

            public void wissenUse(BlazingWandItem item, Player player) {
                float costModifier = WissenUtil.getWissenCostModifierWithDiscount(player);
                List<ItemStack> items = WissenUtil.getWissenItemsNoneAndStorage(WissenUtil.getWissenItemsCurios(player));
                int cost = (int) (item.getBlockWissenCost() * (1 - costModifier));
                if (cost <= 0) cost = 1;
                WissenUtil.removeWissenFromWissenItems(items, cost);
            }
        });
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