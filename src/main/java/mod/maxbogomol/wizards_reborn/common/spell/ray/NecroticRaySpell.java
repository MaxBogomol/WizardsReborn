package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.NecroticRaySpellTrailPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class NecroticRaySpell extends RaySpell {

    public NecroticRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.necroticSpellColor;
    }

    @Override
    public int getCooldown() {
        return 60;
    }

    @Override
    public int getWissenCost() {
        return 50;
    }

    @Override
    public float getRayDistance() {
        return 15f;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
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
                    float damage = (1f + ((focusLevel + magicModifier) * 0.5f));
                    DamageSource damageSource = getDamage(WizardsRebornDamageTypes.RITUAL, entity, entity.getOwner());
                    if (target.hurt(damageSource, damage)) {
                        if (entity.getOwner() instanceof LivingEntity livingEntity) {
                            if (random.nextFloat() < 0.4f) livingEntity.heal(1);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void hitEndTick(SpellEntity entity, RayHitResult hitResult) {
        if (!entity.level().isClientSide()) {
            RaySpellComponent spellComponent = getSpellComponent(entity);
            Vec3 vec = spellComponent.vec;

            double distance = Math.sqrt(Math.pow(entity.getX() - hitResult.getPos().x(), 2) + Math.pow(entity.getY() - hitResult.getPos().y(), 2) + Math.pow(entity.getZ() - hitResult.getPos().z(), 2));
            Vec3 pos = entity.position();
            Vec3 posStart = vec.add(entity.position());
            Vec3 posEnd = vec.scale(distance).add(entity.position());

            WizardsRebornPacketHandler.sendToTracking(entity.level(), BlockPos.containing(pos), new NecroticRaySpellTrailPacket(posStart, posEnd, getColor()));
        }
    }
}
