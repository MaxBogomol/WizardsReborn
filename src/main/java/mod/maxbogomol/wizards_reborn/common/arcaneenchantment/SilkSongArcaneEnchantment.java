package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import com.google.common.collect.Multimap;
import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.entity.ThrownShearsEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneShearsItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
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
import java.util.UUID;

public class SilkSongArcaneEnchantment extends ArcaneEnchantment {

    public SilkSongArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(255, 47, 123);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (stack.getItem() instanceof IArcaneItem item) {
            return item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentTypes.SHEARS);
        }
        return false;
    }

    public static InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsRebornArcaneEnchantments.SILK_SONG) > 0) {
            float costModifier = WissenUtil.getWissenCostModifierWithDiscount(player);
            List<ItemStack> items = WissenUtil.getWissenItemsNoneAndStorage(WissenUtil.getWissenItemsCurios(player));
            int wissen = WissenUtil.getWissenInItems(items);

            int open = ArcaneShearsItem.getOpen(stack);

            if (open == 0) {
                int cost = (int) (15 * (1 - costModifier));
                if (WissenUtil.canRemoveWissen(wissen, cost)) {
                    WissenUtil.removeWissenFromWissenItems(items, cost);
                    ArcaneShearsItem.setOpen(stack, 1);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), WizardsRebornSounds.THROWN_SHEARS_OPEN.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
                    player.getCooldowns().addCooldown(stack.getItem(), 10);
                    return InteractionResultHolder.success(stack);
                }
            } else if (open == 1) {
                int cost = (int) (80 * (1 - costModifier));
                if (WissenUtil.canRemoveWissen(wissen, cost)) {
                    WissenUtil.removeWissenFromWissenItems(items, cost);
                    ArcaneShearsItem.setOpen(stack, 2);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), WizardsRebornSounds.THROWN_SHEARS_THROW.get(), SoundSource.BLOCKS, 1.0f, 1.0f);

                    float baseDamage = 1;
                    float magicDamage = 0;
                    int slot = player.getUsedItemHand() == InteractionHand.OFF_HAND ? player.getInventory().getContainerSize() - 1 : player.getInventory().selected;

                    Multimap<Attribute, AttributeModifier> attributes = stack.getAttributeModifiers(EquipmentSlot.MAINHAND);

                    for (Map.Entry<Attribute, AttributeModifier> entry : attributes.entries()) {
                        AttributeModifier attribute = entry.getValue();
                        if (entry.getKey() == Attributes.ATTACK_DAMAGE) {
                            baseDamage = (float) (baseDamage + attribute.getAmount());
                        }
                        if (entry.getKey() == WizardsRebornAttributes.ARCANE_DAMAGE.get()) {
                            magicDamage = (float) (magicDamage + attribute.getAmount());
                        }
                    }

                    baseDamage = baseDamage + EnchantmentHelper.getDamageBonus(stack, MobType.UNDEFINED);
                    baseDamage = baseDamage + 3;

                    ThrownShearsEntity entity = new ThrownShearsEntity(level, player.position().x, player.position().y + player.getEyeHeight(), player.position().z);
                    entity.setData(player, baseDamage, magicDamage, slot, ItemAnimation.isRightHand(player, hand));
                    entity.setItem(stack);

                    entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 0.1F);
                    level.addFreshEntity(entity);

                    player.awardStat(Stats.ITEM_USED.get(stack.getItem()));

                    setThrownShearsEntity(stack, entity);
                }
            } else if (open == 2) {
                ThrownShearsEntity thrownShearsEntity = getThrownShearsEntity(level, stack);
                if (thrownShearsEntity != null) {
                    if (thrownShearsEntity.getCutTick() <= 10) thrownShearsEntity.setCutTick(10);
                    if (thrownShearsEntity.getIsCut() && player.isShiftKeyDown()) {
                        thrownShearsEntity.setIsThrown(true);
                        thrownShearsEntity.setCutTick(2);
                        player.getCooldowns().addCooldown(stack.getItem(), 10);
                    }
                    if (!thrownShearsEntity.getSender().equals(player)) thrownShearsEntity.setSender(player);
                    int slot = player.getUsedItemHand() == InteractionHand.OFF_HAND ? player.getInventory().getContainerSize() - 1 : player.getInventory().selected;
                    thrownShearsEntity.setSlot(slot);
                    thrownShearsEntity.setIsRight(ItemAnimation.isRightHand(player, hand));
                }
                return InteractionResultHolder.success(stack);
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    public static void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsRebornArcaneEnchantments.SILK_SONG) > 0) {
            if (!level.isClientSide()) {
                int open = ArcaneShearsItem.getOpen(stack);
                if (open == 2) {
                    ThrownShearsEntity thrownShearsEntity = getThrownShearsEntity(level, stack);
                    if (thrownShearsEntity == null) {
                        ArcaneShearsItem.setOpen(stack, 0);
                    } else {
                        if (thrownShearsEntity.getFade()) {
                            ArcaneShearsItem.setOpen(stack, 0);
                        }
                    }
                }
            }
        }
    }

    public static ThrownShearsEntity getThrownShearsEntity(Level level, ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains(WizardsReborn.MOD_ID+":thrownShearsEntity") && !level.isClientSide()) {
            UUID uuid = nbt.getUUID(WizardsReborn.MOD_ID+":thrownShearsEntity");
            Entity entity = ((ServerLevel) level).getEntity(uuid);
            if (entity instanceof ThrownShearsEntity thrownShearsEntity) {
                return thrownShearsEntity;
            }
        }

        return null;
    }

    public static void setThrownShearsEntity(ItemStack stack, ThrownShearsEntity entity) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putUUID(WizardsReborn.MOD_ID+":thrownShearsEntity", entity.getUUID());
    }
}
