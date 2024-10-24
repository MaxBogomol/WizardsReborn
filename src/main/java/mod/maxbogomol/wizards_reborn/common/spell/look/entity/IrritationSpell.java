package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.IrritationSpellPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMobEffects;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class IrritationSpell extends EntityLookSpell {

    public IrritationSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.necroticSpellColor;
    }

    @Override
    public int getCooldown() {
        return 150;
    }

    @Override
    public int getWissenCost() {
        return 200;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public double getLookAdditionalDistance() {
        return 0.5f;
    }

    @Override
    public void lookSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(spellContext.getEntity());

            RayHitResult hit = getEntityHit(level, spellContext);
            Vec3 pos = hit.getPos();
            if (hit.hasEntities()) {
                for (Entity entity : hit.getEntities()) {
                    if (entity instanceof LivingEntity livingEntity) {
                        livingEntity.addEffect(new MobEffectInstance(WizardsRebornMobEffects.IRRITATION.get(), (int) (400 + (100 * (focusLevel + magicModifier))), 0));
                        WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new IrritationSpellPacket(pos, getColor()));
                    }
                }
            }
        }
    }
}
