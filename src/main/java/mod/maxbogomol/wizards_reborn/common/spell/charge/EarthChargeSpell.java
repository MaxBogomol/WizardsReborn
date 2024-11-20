package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.awt.*;

public class EarthChargeSpell extends ChargeSpell {

    public EarthChargeSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.earthSpellColor;
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {
        super.onImpact(level, entity, hitResult, target);

        if (!entity.level().isClientSide()) {
            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float damage = (6.0f + (focusLevel * 1.5f)) + (magicModifier * 2f) + WizardsRebornConfig.SPELL_CHARGE_DAMAGE.get().floatValue() + WizardsRebornConfig.EARTH_CHARGE_DAMAGE.get().floatValue();
            ChargeSpellComponent spellComponent = getSpellComponent(entity);

            float charge = (float) (0.5f + ((spellComponent.charge / getCharge()) / 2f));
            damage = damage * charge;

            DamageSource damageSource = getDamage(DamageTypes.GENERIC, entity, entity.getOwner());
            target.hurt(damageSource, damage);
            if (target instanceof Player player) {
                player.getInventory().hurtArmor(damageSource, damage, Inventory.ALL_ARMOR_SLOTS);
            }
        }
    }
}
