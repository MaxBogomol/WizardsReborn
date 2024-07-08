package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import com.google.common.collect.Multimap;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.animation.ItemAnimation;
import mod.maxbogomol.wizards_reborn.client.animation.ScytheThrowItemAnimation;
import mod.maxbogomol.wizards_reborn.common.entity.ThrowedScytheEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class ThrowArcaneEnchantment extends ArcaneEnchantment {
    public static ScytheThrowItemAnimation animation = new ScytheThrowItemAnimation();

    public ThrowArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    public Color getColor() {
        return new Color(234, 129, 210);
    }

    public boolean canEnchantItem(ItemStack stack) {
        if (stack.getItem() instanceof IArcaneItem item) {
            return item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentType.SCYTHE);
        }
        return false;
    }

    public static void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (!level.isClientSide()) {
            if (livingEntity instanceof Player player) {
                if (ArcaneEnchantmentUtils.isArcaneItem(stack)) {
                    if (ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.THROW_ARCANE_ENCHANTMENT) > 0) {
                        float costModifier = WissenUtils.getWissenCostModifierWithDiscount(player);
                        List<ItemStack> items = WissenUtils.getWissenItemsNoneAndStorage(WissenUtils.getWissenItemsCurios(player));
                        int wissen = WissenUtils.getWissenInItems(items);
                        int cost = (int) (150 * (1 - costModifier));

                        int ticks = player.getUseItem().getUseDuration() - player.getUseItemRemainingTicks();
                        if (ticks > 18 && WissenUtils.canRemoveWissen(wissen, cost)) {
                            float baseDamage = 1;
                            float magicDamage = 0;
                            int slot = player.getUsedItemHand() == InteractionHand.OFF_HAND ? player.getInventory().getContainerSize() - 1 : player.getInventory().selected;

                            Multimap<Attribute, AttributeModifier> attributes = stack.getAttributeModifiers(EquipmentSlot.MAINHAND);

                            for(Map.Entry<Attribute, AttributeModifier> entry : attributes.entries()) {
                                AttributeModifier attribute = entry.getValue();
                                if (entry.getKey() == Attributes.ATTACK_DAMAGE) {
                                    baseDamage = (float) (baseDamage + attribute.getAmount());
                                }
                                if (entry.getKey() == WizardsReborn.ARCANE_DAMAGE.get()) {
                                    magicDamage = (float) (magicDamage + attribute.getAmount());
                                }
                            }

                            baseDamage = baseDamage + EnchantmentHelper.getDamageBonus(stack, MobType.UNDEFINED);

                            int bladeLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.MAGIC_BLADE_ARCANE_ENCHANTMENT);
                            if (bladeLevel > 0) {
                                int additionalCost = (int) ((5 * bladeLevel) * (1 - costModifier));
                                if (WissenUtils.canRemoveWissen(wissen, cost + additionalCost)) {
                                    WissenUtils.removeWissenFromWissenItems(items, additionalCost);
                                    magicDamage = magicDamage + (2f * (bladeLevel / 5f));
                                }
                            }

                            ThrowedScytheEntity entity = new ThrowedScytheEntity(level, player.position().x, player.position().y + player.getBbHeight() / 2f, player.position().z);
                            entity.setData(livingEntity, baseDamage, magicDamage, slot, ItemAnimation.isRightHand(player, player.getUsedItemHand()));
                            entity.setItem(stack);

                            entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 0F);
                            level.addFreshEntity(entity);

                            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                            player.setItemInHand(player.getUsedItemHand(), ItemStack.EMPTY);
                            livingEntity.stopUsingItem();
                            WissenUtils.removeWissenFromWissenItems(items, cost);
                        }
                    }
                }
            }
        }
    }
}
