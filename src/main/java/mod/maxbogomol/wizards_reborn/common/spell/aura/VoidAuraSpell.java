package mod.maxbogomol.wizards_reborn.common.spell.aura;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.FrostAuraSpellBurstPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamage;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.List;

public class VoidAuraSpell extends AuraSpell {

    public VoidAuraSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.voidSpellColor;
    }

    @Override
    public void auraTick(Level level, SpellEntity entity, List<Entity> targets) {
        super.auraTick(level, entity, targets);

        if (entity.tickCount % 20 == 0) {
            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float damage = (1.75f + (focusLevel * 0.5f)) + magicModifier;
            for (Entity target : targets) {
                if (target instanceof LivingEntity livingEntity) {
                    if (!target.equals(entity.getOwner())) {
                        DamageSource damageSource = WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC);
                        livingEntity.lastHurtByPlayerTime = livingEntity.tickCount;
                        livingEntity.hurt(damageSource, damage);
                        WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new FrostAuraSpellBurstPacket(target.position().add(0, target.getBbHeight() / 2f, 0), getColor()));
                    }
                }
            }
        }
    }
}
