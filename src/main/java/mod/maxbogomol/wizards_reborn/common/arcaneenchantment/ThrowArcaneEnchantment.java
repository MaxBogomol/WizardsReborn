package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import com.google.common.collect.Multimap;
import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.client.animation.ScytheThrowItemAnimation;
import mod.maxbogomol.wizards_reborn.common.entity.ThrowedScytheEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
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

    @Override
    public Color getColor() {
        return new Color(234, 129, 210);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (stack.getItem() instanceof IArcaneItem item) {
            return item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentTypes.SCYTHE);
        }
        return false;
    }

    public static void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (!level.isClientSide()) {
            if (livingEntity instanceof Player player) {
                if (ArcaneEnchantmentUtil.isArcaneItem(stack)) {
                    if (ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsRebornArcaneEnchantments.THROW) > 0) {
                        float costModifier = WissenUtil.getWissenCostModifierWithDiscount(player);
                        List<ItemStack> items = WissenUtil.getWissenItemsNoneAndStorage(WissenUtil.getWissenItemsCurios(player));
                        int wissen = WissenUtil.getWissenInItems(items);
                        int cost = (int) (150 * (1 - costModifier));

                        int ticks = player.getUseItem().getUseDuration() - player.getUseItemRemainingTicks();
                        if (ticks > 18 && WissenUtil.canRemoveWissen(wissen, cost)) {
                            float baseDamage = 1;
                            float magicDamage = 0;
                            int slot = player.getUsedItemHand() == InteractionHand.OFF_HAND ? player.getInventory().getContainerSize() - 1 : player.getInventory().selected;

                            Multimap<Attribute, AttributeModifier> attributes = stack.getAttributeModifiers(EquipmentSlot.MAINHAND);

                            for(Map.Entry<Attribute, AttributeModifier> entry : attributes.entries()) {
                                AttributeModifier attribute = entry.getValue();
                                if (entry.getKey() == Attributes.ATTACK_DAMAGE) {
                                    baseDamage = (float) (baseDamage + attribute.getAmount());
                                }
                                if (entry.getKey() == WizardsRebornAttributes.ARCANE_DAMAGE.get()) {
                                    magicDamage = (float) (magicDamage + attribute.getAmount());
                                }
                            }

                            baseDamage = baseDamage + EnchantmentHelper.getDamageBonus(stack, MobType.UNDEFINED);

                            int bladeLevel = ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsRebornArcaneEnchantments.MAGIC_BLADE);
                            if (bladeLevel > 0) {
                                int additionalCost = (int) ((5 * bladeLevel) * (1 - costModifier));
                                if (WissenUtil.canRemoveWissen(wissen, cost + additionalCost)) {
                                    WissenUtil.removeWissenFromWissenItems(items, additionalCost);
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
                            WissenUtil.removeWissenFromWissenItems(items, cost);
                        }
                    }
                }
            }
        }
    }
}
