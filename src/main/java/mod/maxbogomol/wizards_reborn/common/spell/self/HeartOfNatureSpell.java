package mod.maxbogomol.wizards_reborn.common.spell.self;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.HeartOfNatureSpellPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.awt.*;

public class HeartOfNatureSpell extends SelfSpell {
    public HeartOfNatureSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.earthSpellColor;
    }

    @Override
    public int getCooldown() {
        return 250;
    }

    @Override
    public int getWissenCost() {
        return 200;
    }

    @Override
    public void selfSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            if (spellContext.getEntity() instanceof LivingEntity entity) {
                int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
                float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity);

                int heal = 0;
                int regen = 0;
                if (magicModifier >= 1) {
                    heal = 1;
                }
                if (magicModifier >= 2) {
                    regen = 1;
                }

                entity.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, heal));
                entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, (int) (250 + (40 * (focusLevel + magicModifier))), regen));
                entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, (int) (680 + (120 * (focusLevel + magicModifier))), 0));
                PacketHandler.sendToTracking(entity.level(), entity.blockPosition(), new HeartOfNatureSpellPacket(entity.position().add(0, entity.getBbHeight() / 2f, 0), getColor()));
            }
        }
    }
}
