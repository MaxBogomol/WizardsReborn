package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.fluffy_fur.common.raycast.RayCastContext;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.client.animation.StrikeSpellItemAnimation;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AirImpactSpellPacket;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class AirImpactSpell extends EntityLookSpell {

    public static StrikeSpellItemAnimation animation = new StrikeSpellItemAnimation();

    public AirImpactSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.AIR);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.airSpellColor;
    }

    @Override
    public int getCooldown() {
        return 50;
    }

    @Override
    public int getWissenCost() {
        return 40;
    }

    @Override
    public double getLookDistance() {
        return 2.5f;
    }

    @Override
    public double getLookAdditionalDistance() {
        return 0.25f;
    }

    @Override
    public boolean hasReachDistance(SpellContext spellContext) {
        return false;
    }

    @Override
    public boolean canLookSpell(Level level, SpellContext spellContext) {
        RayCastContext context = new RayCastContext().setEntitySize(1.5f);
        return getEntityHit(level, spellContext, context, getAllFilter(spellContext.getEntity())).hasEntities();
    }

    @Override
    public void lookSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(spellContext.getEntity());
            float power = 1.1f + ((focusLevel + magicModifier) * 0.1f) + WizardsRebornConfig.AIR_IMPACT_POWER.get().floatValue();

            Vec3 vec = spellContext.getVec().scale(power);

            RayCastContext context = new RayCastContext().setEntitySize(1.5f);
            RayHitResult hit = getEntityHit(level, spellContext, context, getAllFilter(spellContext.getEntity()));
            if (hit.hasEntities()) {
                for (Entity entity : hit.getEntities()) {
                    entity.push(vec.x(), vec.y() / 2, vec.z());
                    entity.hurtMarked = true;
                }
            }

            Vec3 pos = spellContext.getPos().add(spellContext.getVec().scale(0.5f));
            WizardsRebornPacketHandler.sendToTracking(level, BlockPos.containing(pos), new AirImpactSpellPacket(pos, vec.scale(0.35f), getColor()));
        }
    }
}
