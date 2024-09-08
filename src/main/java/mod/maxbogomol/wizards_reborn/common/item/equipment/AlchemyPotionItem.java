package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.google.common.base.Preconditions;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.FluidAlchemyPotion;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAlchemyPotions;
import mod.maxbogomol.wizards_reborn.common.item.PlacedItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AlchemyPotionItem extends PlacedItem {
    public int maxUses;
    public static List<Item> potionList = new ArrayList<>();

    @Deprecated
    public final Item bottle;

    public AlchemyPotionItem(Properties properties, int maxUses, Item bottle) {
        super(properties);
        this.maxUses = maxUses;
        this.bottle = bottle;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ColorHandler implements ItemColor {
        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            if (tintIndex == 1) {
                AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
                if (!AlchemyPotionUtil.isEmpty(potion)) {
                    Color color = potion.getColor();

                    return ColorUtil.packColor(255, color.getRed(), color.getGreen(), color.getBlue());
                }
            }
            return 0xFFFFFF;
        }
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();;
        AlchemyPotionUtil.setPotion(stack, WizardsRebornAlchemyPotions.WATER);
        return stack;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        Player player = entityLiving instanceof Player ? (Player)entityLiving : null;
        if (player instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)player, stack);
        }

        if (!level.isClientSide) {
            AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
            if (!AlchemyPotionUtil.isEmpty(potion)) {
                for (MobEffectInstance mobeffectinstance : potion.getEffects()) {
                    if (mobeffectinstance.getEffect().isInstantenous()) {
                        mobeffectinstance.getEffect().applyInstantenousEffect(player, player, entityLiving, mobeffectinstance.getAmplifier(), 1.0D);
                    } else {
                        entityLiving.addEffect(new MobEffectInstance(mobeffectinstance));
                    }
                }
            }
        }

        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                setUses(stack, getUses(stack) + 1);
                if (getUses(stack) >= maxUses) {
                    if (player == null || !player.getAbilities().instabuild) {
                        stack.shrink(1);

                        if (stack.isEmpty()) {
                            return new ItemStack(bottle);
                        }

                        if (player != null) {
                            player.getInventory().add(new ItemStack(bottle));
                        }
                    }
                }
            }
        }

        entityLiving.gameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public Component getHighlightTip(ItemStack stack, Component displayName) {
        AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
        if (!AlchemyPotionUtil.isEmpty(potion)) {
            return displayName.copy().append(Component.literal(" (")).append(getPotionName(potion)).append(Component.literal(")"));
        }

        return displayName;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
        if (!AlchemyPotionUtil.isEmpty(potion)) {
            list.add(getPotionName(potion));
            PotionUtils.addPotionTooltip(potion.getEffects(), list, 1.0f);
        }
    }

    public Component getPotionName(AlchemyPotion potion) {
        Color color = potion.getColor();

        return Component.translatable(potion.getTranslatedName()).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, color.getRed(), color.getGreen(), color.getBlue())));
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 32;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
        if (!AlchemyPotionUtil.isEmpty(potion)) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }

        return InteractionResultHolder.pass(stack);
    }

    public static int getUses(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("uses")) {
            nbt.putInt("uses", 0);
        }

        return nbt.getInt("uses");
    }

    public static void setUses(ItemStack stack, int uses) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("uses", uses);
    }

    public static boolean hasPotion(ItemStack stack) {
        AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
        return (!AlchemyPotionUtil.isEmpty(potion));
    }

    public static boolean interactWithFluidHandler(@NotNull Player player, @NotNull InteractionHand hand, @NotNull IFluidHandler handler)
    {
        Preconditions.checkNotNull(player);
        Preconditions.checkNotNull(hand);
        Preconditions.checkNotNull(handler);

        ItemStack stack = player.getItemInHand(hand);

        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof AlchemyPotionItem vial) {
                AlchemyPotion potion = AlchemyPotionUtil.getPotion(stack);
                if (!AlchemyPotionUtil.isEmpty(potion)) {
                    if (potion instanceof FluidAlchemyPotion fluidPotion) {
                        FluidStack fluid = new FluidStack(fluidPotion.fluid, 250 * (vial.maxUses - getUses(stack)));
                        int added = handler.fill(fluid, IFluidHandler.FluidAction.SIMULATE);
                        added = added - (added % 250);
                        if (added >= 250) {
                            handler.fill(fluid, IFluidHandler.FluidAction.EXECUTE);
                            player.level().playSound(WizardsReborn.proxy.getPlayer(), player.getOnPos(), SoundEvents.BOTTLE_EMPTY, SoundSource.PLAYERS, 1.0f, 1.0f);

                            if (!player.getAbilities().instabuild) {
                                setUses(stack, getUses(stack) + (added / 250));
                                if (getUses(stack) >= vial.maxUses) {
                                    if (player == null || !player.getAbilities().instabuild) {
                                        stack.shrink(1);

                                        if (stack.isEmpty()) {
                                            player.setItemInHand(hand, new ItemStack(vial.bottle));
                                        }
                                    }
                                }
                            }

                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }
}