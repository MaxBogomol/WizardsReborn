package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.awt.*;

public class MomentArcaneEnchantment extends ArcaneEnchantment {

    public MomentArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(54, 255, 75);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (stack.getItem() instanceof IArcaneItem item) {
            return item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentTypes.CROSSBOW);
        }
        return false;
    }

    public static void onCrossbowShot(Projectile projectile, Level level, LivingEntity shooter, InteractionHand hand, ItemStack crossbowStack, ItemStack ammoStack, float soundPitch, boolean isCreativeMode, float velocity, float inaccuracy, float projectileAngle) {
        if (shooter instanceof Player player && ArcaneEnchantmentUtil.isArcaneItem(crossbowStack)) {
            if (ArcaneEnchantmentUtil.getArcaneEnchantment(crossbowStack, WizardsRebornArcaneEnchantments.MOMENT) > 0) {
                if (projectile instanceof FireworkRocketEntity rocket) {
                    int cooldown = 60;
                    if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, crossbowStack) > 0) cooldown = cooldown * 2;
                    player.getCooldowns().addCooldown(crossbowStack.getItem(), cooldown);
                    if (!level.isClientSide()) rocket.explode();
                }
            }
        }
    }
}
