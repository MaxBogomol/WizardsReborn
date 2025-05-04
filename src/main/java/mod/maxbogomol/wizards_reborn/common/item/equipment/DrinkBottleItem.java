package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.placed_items.PlacedItemsBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.PlacedItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class DrinkBottleItem extends PlacedItem {
    boolean isAlcoholic = true;
    boolean isOverexpose = true;
    int stageForAlc = -1;
    int stageOverexpose = 4;

    int ticksAged = 24000;
    int ticksAgedOverexpose = 24000;

    public List<EffectInstance> effects = new ArrayList<>();

    public DrinkBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        super.finishUsingItem(stack, level, livingEntity);

        if (!level.isClientSide()) {
            int ticks = getTicks(stack);

            if (livingEntity instanceof Player player) {
                for (MobEffectInstance mobeffectinstance : getMobEffects(ticks)) {
                    if (mobeffectinstance.getEffect().isInstantenous()) {
                        mobeffectinstance.getEffect().applyInstantenousEffect(player, player, livingEntity, mobeffectinstance.getAmplifier(), 1.0D);
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(mobeffectinstance));
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(this));

                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                if (stack.isEmpty()) {
                    return new ItemStack(WizardsRebornItems.ALCHEMY_BOTTLE.get());
                }
            }
        }

        livingEntity.gameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        int ticks = getTicks(stack);

        if (getAlcoholicWithItem(stack)) {
            list.add(Component.translatable("lore.wizards_reborn.drinks.alcoholic").withStyle(ChatFormatting.GRAY));
        } else {
            list.add(Component.translatable("lore.wizards_reborn.drinks.non_alcoholic").withStyle(ChatFormatting.GRAY));
        }
        list.add(Component.literal(dayFromTick(ticks)).append(" ").append(Component.translatable("lore.wizards_reborn.drinks.days_aged")).withStyle(ChatFormatting.GRAY));

        list.add(Component.empty());

        List<MobEffectInstance> effectInstances = getMobEffects(ticks);
        PotionUtils.addPotionTooltip(effectInstances, list, 1.0f);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 40;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public static int getTicks(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("ticksAged")) {
            return nbt.getInt("ticksAged");
        }

        return 0;
    }

    public static void setTicks(ItemStack stack, int ticks) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("ticksAged", ticks);
    }

    public int getStage(ItemStack stack) {
        return getStageFromTicks(getTicks(stack));
    }

    public void setStage(ItemStack stack, int stage) {
        setTicks(stack, getTicksFromStage(stage));
    }

    public static int getStageFromItem(ItemStack stack) {
        if (stack.getItem() instanceof DrinkBottleItem drink) {
            return drink.getStage(stack);
        }

        return 0;
    }

    public static void setStageS(ItemStack stack, int stage) {
        if (stack.getItem() instanceof DrinkBottleItem drink) {
            drink.setStage(stack, stage);
        }
    }

    public static List<ItemStack> getItemsAllStages(Item item) {
        List<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(item));
        for (int i = 0; i < 4; i++) {
            ItemStack stack = new ItemStack(item);
            setStageS(stack, i + 1);
            list.add(stack);
        }

        return list;
    }

    public static List<ItemStack> getItemsAllStages(ItemStack item) {
        return getItemsAllStages(item.getItem());
    }

    public static List<ItemStack> getItemsForTab(Item item) {
        List<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(item));
        ItemStack stack = new ItemStack(item);
        setStageS(stack, 3);
        list.add(stack);

        return list;
    }

    public static List<ItemStack> getItemsForTab(ItemStack item) {
        return getItemsForTab(item.getItem());
    }

    public DrinkBottleItem setAlcoholic(boolean isAlcoholic) {
        this.isAlcoholic = isAlcoholic;
        return this;
    }

    public boolean getAlcoholic() {
        return isAlcoholic;
    }

    public DrinkBottleItem setStageForAcl(int stage) {
        this.stageForAlc = stage;
        return this;
    }

    public int getStageForAcl() {
        return stageForAlc;
    }

    public DrinkBottleItem setIsOverexpose(boolean isOverexpose) {
        this.isOverexpose = isOverexpose;
        return this;
    }

    public boolean getIsOverexpose() {
        return isOverexpose;
    }

    public DrinkBottleItem setAged(int ticksAged, int ticksAgedOverexpose) {
        this.ticksAged = ticksAged;
        this.ticksAgedOverexpose = ticksAgedOverexpose;
        return this;
    }

    public DrinkBottleItem addEffect(EffectInstance instance) {
        effects.add(instance);
        return this;
    }

    public List<EffectInstance> getEffects() {
        return effects;
    }

    public List<MobEffectInstance> getMobEffects(int ticks) {
        List<MobEffectInstance> mobEffects = new ArrayList<>();
        for (EffectInstance effectInstance : getEffects()) {
            mobEffects.add(effectInstance.getEffectPerTick(ticks, ticksAged, ticksAgedOverexpose));
        }

        return mobEffects;
    }

    public boolean getAlcoholicWithItem(ItemStack stack) {
        if (getStageForAcl() >= 0) {
            if (getStage(stack) >= getStageForAcl()) {
                return true;
            }
        }

        return getAlcoholic();
    }

    public int getTicksFromStage(int stage) {
        if (stage > 0) {
            if (stage >= stageOverexpose) {
                return ticksAged + ticksAgedOverexpose;
            }
            return Mth.lerpInt((float) stage / (stageOverexpose - 1), 0, ticksAged);
        }

        return 0;
    }

    public int getStageFromTicks(int ticks) {
        if (getIsOverexpose()) {
            if (ticks >= ticksAged + ticksAgedOverexpose) {
                return stageOverexpose;
            }
        }

        return Mth.lerpInt((float) ticks / ticksAged, 0, stageOverexpose - 1);
    }

    public static String dayFromTick(int ticks) {
        return String.valueOf((Math.round((ticks / 24000f) * 100)) / 100f);
    }

    public static class EffectInstance {
        public MobEffect effect;
        public int minDuration;
        public int maxDuration;
        public int minAmplifier;
        public int maxAmplifier;

        public EffectInstance(MobEffect effect, int minDuration, int maxDuration, int minAmplifier, int maxAmplifier) {
            this.effect = effect;
            this.minDuration = minDuration;
            this.maxDuration = maxDuration;
            this.minAmplifier = minAmplifier;
            this.maxAmplifier = maxAmplifier;
        }

        public MobEffectInstance getEffectPerTick(int ticks, int ticksAged, int ticksAgedOverexpose) {
            float lerp = (float) ticks / ticksAged;
            if (lerp > 1f) lerp = 1f;
            int duration = Mth.lerpInt(lerp, minDuration, maxDuration);
            int amplifier = Mth.lerpInt(lerp, minAmplifier, maxAmplifier);

            if (ticks >= ticksAged + ticksAgedOverexpose) {

            }

            return new MobEffectInstance(effect, duration, amplifier);
        }
    }

    public static ResourceLocation getModelTexture(ItemStack stack) {
        String string = stack.getDescriptionId();
        int i = string.indexOf(".");
        string = string.substring(i + 1);
        i = string.indexOf(".");
        String modId = string.substring(0, i);
        String drinkId = string.substring(i + 1);
        return new ResourceLocation(modId, "textures/models/drink/" + drinkId + ".png");
    }

    public static ResourceLocation getStageModelTexture(int stage) {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/models/drink/stage_" + stage + ".png");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderPlacedItem(ItemStack stack, int rotation, float rotate, PlacedItemsBlockEntity items, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light, int overlay) {
        poseStack.pushPose();
        poseStack.translate(0F, 0.001F, 0F);
        poseStack.mulPose(Axis.YP.rotationDegrees((rotation * -22.5f) + rotate));
        poseStack.mulPose(Axis.XP.rotationDegrees(180f));
        WizardsRebornModels.ALCHEMY_BOTTLE.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(getModelTexture(stack))), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        int stage = getStage(stack);
        if (stage > 0) {
            poseStack.pushPose();
            poseStack.scale(1.001f, 1.001f, 1.001f);
            WizardsRebornModels.ALCHEMY_BOTTLE.renderToBuffer(poseStack, bufferSource.getBuffer(RenderType.entityCutoutNoCull(getStageModelTexture(stage))), light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
            poseStack.popPose();
        }
        poseStack.popPose();
    }
}
