package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornDamage;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.awt.*;

public class CurseChargeSpell extends ChargeSpell {
    public CurseChargeSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.curseSpellColor;
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

        if (target instanceof LivingEntity livingEntity) {
            int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
            float damage = (2.5f + (focusLevel * 1.5f)) + magicModifier;

            if (projectile.getSpellData() != null) {
                float charge = 0.75f + (((float) projectile.getSpellData().getInt("charge") / getCharge()) / 4f);
                damage = damage * charge;
            }

            if (livingEntity.getMobType() != MobType.UNDEAD) {
                target.hurt(new DamageSource(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), projectile, player), damage);
            } else {
                livingEntity.heal(damage);
            }
        }
    }
}