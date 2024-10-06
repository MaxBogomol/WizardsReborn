package mod.maxbogomol.wizards_reborn.common.spell.self;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.FireShieldSpellPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.awt.*;

public class FireShieldSpell extends SelfSpell {

    public FireShieldSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.fireSpellColor;
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
    public void selfSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            if (spellContext.getEntity() instanceof LivingEntity entity) {
                int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
                float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity);

                entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, (int) (2000 + (450 * (focusLevel + magicModifier))), 0));
                entity.setTicksFrozen(0);
                PacketHandler.sendToTracking(entity.level(), entity.blockPosition(), new FireShieldSpellPacket(entity.position().add(0, entity.getBbHeight() / 2f, 0), getColor()));
            }
        }
    }
}
