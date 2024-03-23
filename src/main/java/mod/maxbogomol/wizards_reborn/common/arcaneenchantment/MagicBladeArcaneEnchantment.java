package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.integration.farmersdelight.FarmersDelightIntegration;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneAxeItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneScytheItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneSwordItem;
import mod.maxbogomol.wizards_reborn.common.network.MagicBladeEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class MagicBladeArcaneEnchantment extends ArcaneEnchantment {
    private static Random random = new Random();

    public MagicBladeArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    public Color getColor() {
        return new Color(110, 78, 169);
    }

    public boolean canEnchantItem(ItemStack stack) {
        return ((stack.getItem() instanceof ArcaneSwordItem) || (stack.getItem() instanceof ArcaneAxeItem) || (stack.getItem() instanceof ArcaneScytheItem) ||
                (FarmersDelightIntegration.canMagicBladeEnchant(stack.getItem())));
    }

    public static void onLivingDamage(LivingDamageEvent event) {
        if (!event.getEntity().level().isClientSide) {
            if (event.getSource().getEntity() instanceof Player player) {
                ItemStack stack = player.getItemBySlot(EquipmentSlot.MAINHAND);

                if (ArcaneEnchantmentUtils.isArcaneItem(stack)) {
                    if (!event.getSource().is(WizardsReborn.MAGIC_DAMAGE_TYPE_TAG)) {
                        int enchantmentLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.MAGIC_BLADE_ARCANE_ENCHANTMENT);

                        if (enchantmentLevel > 0 && random.nextFloat() < (0.35F + ((enchantmentLevel - 1) * 0.025F))) {
                            float costModifier = WissenUtils.getWissenCostModifierWithDiscount(player);
                            List<ItemStack> items = WissenUtils.getWissenItemsNoneAndStorage(WissenUtils.getWissenItemsCurios(player));
                            int wissen = WissenUtils.getWissenInItems(items);
                            int cost = (int) (30 * (1 - costModifier));
                            if (cost <= 0) {
                                cost = 1;
                            }

                            if (WissenUtils.canRemoveWissen(wissen, cost)) {
                                WissenUtils.removeWissenFromWissenItems(items, cost);
                                event.getEntity().invulnerableTime = 0;
                                event.getEntity().hurt(new DamageSource(event.getEntity().damageSources().magic().typeHolder(), player), (1.0f * enchantmentLevel));
                                event.getEntity().level().playSound(WizardsReborn.proxy.getPlayer(), event.getEntity().getOnPos(), SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.PLAYERS, 1.0f, (float) (0.8f + ((random.nextFloat() - 0.5D) / 2)));
                                PacketHandler.sendToTracking( event.getEntity().level(), event.getEntity().getOnPos(), new MagicBladeEffectPacket((float) event.getEntity().getX(), (float) event.getEntity().getY() + (event.getEntity().getBbHeight() / 2), (float) event.getEntity().getZ()));
                            }
                        }
                    }
                }
            }
        }
    }
}
