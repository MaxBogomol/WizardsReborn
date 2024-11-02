package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.awt.*;

public class WaterRaySpell extends RaySpell {

    public WaterRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.waterSpellColor;
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
                    float damage = (1.0f + (focusLevel * 0.5f)) + magicModifier;

                    target.clearFire();
                    int frost = target.getTicksFrozen() + 10;
                    if (frost > 250) frost = 250;
                    target.setTicksFrozen(frost);

                    DamageSource damageSource = getDamage(target.damageSources().drown().typeHolder(), entity, entity.getOwner());
                    target.hurt(damageSource, damage);
                }
            }
        }
    }

/*    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult) {
        super.onImpact(level, entity, hitResult);

        if (entity.getSpellContext().getAlternative()) {
            Vec3 vec = getBlockHitOffset(ray, projectile, -0.1f);
            BlockPos blockPos = BlockPos.containing(vec.x(), vec.y(), vec.z());

            int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
            int radius = focusLevel + 1;*/


/*            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        if (WissenItemUtil.canRemoveWissen(stack, 5)) {
                            BlockPos pos = blockPos.relative(Direction.Axis.X, x).relative(Direction.Axis.Y, y).relative(Direction.Axis.Z, z);

                            BlockEvent.BreakEvent breakEv = new BlockEvent.BreakEvent(level, blockPos, level.getBlockState(pos), player);

                            if (!level.getBlockState(pos).isAir() && !MinecraftForge.EVENT_BUS.post(breakEv)) {
                                if (level.getBlockState(pos).getBlock() instanceof FireBlock) {
                                    level.destroyBlock(pos, false);
                                    removeWissen(stack, projectile.getStats(), player, 5);
                                    PacketHandler.sendToTracking(level, player.getOnPos(), new WaterRaySpellEffectPacket((float) pos.getX() + 0.5f, (float) pos.getY() + 0.5f, (float) pos.getZ() + 0.5f, r, g, b));
                                }
                            }
                        }
                    }
                }
            }*/
        //}
    //}
}
