package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.awt.*;

public class AirRaySpell extends RaySpell {

    public AirRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.AIR);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.airSpellColor;
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
                    float damage = (1.5f + (focusLevel * 0.5f)) + magicModifier;
                    DamageSource damageSource = getDamage(target.damageSources().fall().typeHolder(), entity, entity.getOwner());
                    target.hurt(damageSource, damage);


                    if (entity.getSpellContext().getAlternative()) {
                        if (target instanceof LivingEntity livingEntity) {
                            RaySpellComponent spellComponent = (RaySpellComponent) entity.getSpellComponent();
                            livingEntity.knockback(((focusLevel + magicModifier) * 0.5F), -spellComponent.vec.x(), -spellComponent.vec.z());

                            Color color = getColor();
                            float r = color.getRed() / 255f;
                            float g = color.getGreen() / 255f;
                            float b = color.getBlue() / 255f;

                            //Vec3 pos = ray.getLocation().add(projectile.getLookAngle());
                            //Vec3 vel = player.getEyePosition().add(player.getLookAngle().scale(40)).subtract(pos).scale(1.0 / 10).normalize().scale(0.2f);

                            //PacketHandler.sendToTracking(level, player.getOnPos(), new AirRaySpellEffectPacket((float) pos.x(), (float) pos.y() + (target.getBbHeight() / 2), (float) pos.z(), (float) vel.x(), (float) vel.y(), (float) vel.z(), r, g, b));
                        }
                    }
                }
            }
        }
    }
}
