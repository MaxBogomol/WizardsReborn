package mod.maxbogomol.wizards_reborn.common.spell.look.strike;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornDamage;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class IncinerationSpell extends StrikeSpell {
    public Color secondColor = new Color(0.979f, 0.912f, 0.585f);

    public IncinerationSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.fireSpellColor;
    }

    @Override
    public Color getSecondColor() {
        return secondColor;
    }

    @Override
    public int getCooldown() {
        return 350;
    }

    @Override
    public int getWissenCost() {
        return 600;
    }

    @Override
    public void strikeDamage(SpellProjectileEntity entity, Player player) {
        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
        float distance = (0.75f + ((focusLevel + magicModifier) * 0.25f));
        float damage = (10 + ((focusLevel + magicModifier) * 5f));

        for (Entity target : getTargets(entity, distance)) {
            if (target instanceof LivingEntity livingEntity) {
                int fire = target.getRemainingFireTicks() + 250;
                if (fire > 1000) fire = 1000;
                target.setSecondsOnFire(fire);
                target.setTicksFrozen(0);

                target.hurt(new DamageSource(target.damageSources().onFire().typeHolder(), entity, player), damage);
                target.invulnerableTime = 0;
                target.hurt(new DamageSource(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), entity, player), 10);
            }
        }
    }
}
