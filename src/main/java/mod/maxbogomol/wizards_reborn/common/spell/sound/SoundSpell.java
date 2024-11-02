package mod.maxbogomol.wizards_reborn.common.spell.sound;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class SoundSpell extends Spell {

    public SoundSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.soundSpellColor;
    }

    @Override
    public void useSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            spellContext.setCooldown(this);
            spellContext.removeWissen(this);
            spellContext.awardStat(this);
        }
        playSound(level, spellContext);
    }

    public SoundEvent getSound(Level level, SpellContext spellContext) {
        return WizardsRebornSounds.SPELL_CAST.get();
    }

    public void playSound(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            Vec3 pos = spellContext.getPos();
            level.playSound(WizardsReborn.proxy.getPlayer(), pos.x(), pos.y(), pos.z(), getSound(level, spellContext), SoundSource.PLAYERS, 1f, 1f);
        }
    }

    @Override
    public void onReload(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {

    }
}
