package mod.maxbogomol.wizards_reborn.common.spell.look.strike;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.damage.DamageSourceRegistry;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class RepentanceSpell extends StrikeSpell {
    public RepentanceSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.EARTH_CRYSTAL_TYPE);
        addCrystalType(WizardsReborn.AIR_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.holySpellColor;
    }

    @Override
    public void strikeDamage(SpellProjectileEntity entity, Player player) {
        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
        float distance = (0.75f + ((focusLevel + magicModifier) * 0.25f));
        float damage = (30 + ((focusLevel + magicModifier) * 5f));
        float heal = (15 + ((focusLevel + magicModifier) * 5f));

        for (Entity target : getTargets(entity, distance)) {
            if (target instanceof LivingEntity livingEntity) {
                if (livingEntity.isInvertedHealAndHarm()) {
                    target.hurt(new DamageSource(DamageSourceRegistry.create(target.level(), DamageSourceRegistry.ARCANE_MAGIC).typeHolder(), entity, player), damage);
                } else {
                    livingEntity.heal(heal);
                }
            }
        }
    }
}
