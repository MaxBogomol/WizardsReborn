package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.awt.*;

public class VoidRaySpell extends RaySpell {

    public VoidRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.voidSpellColor;
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {
        super.onImpact(level, entity, hitResult, target);

        if (!entity.level().isClientSide()) {
            if (target.tickCount % 10 == 0) {
                if (entity.getSpellContext().canRemoveWissen(this)) {
                    entity.getSpellContext().removeWissen(this);
                    int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
                    float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
                    float damage = (2.5f + (focusLevel)) + magicModifier + WizardsRebornConfig.SPELL_RAY_DAMAGE.get().floatValue() + WizardsRebornConfig.VOID_RAY_DAMAGE.get().floatValue();
                    DamageSource damageSource = getDamage(WizardsRebornDamageTypes.ARCANE_MAGIC, entity, entity.getOwner());
                    target.hurt(damageSource, damage);
                }
            }
        }
    }
}
