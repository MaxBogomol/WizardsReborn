package mod.maxbogomol.wizards_reborn.common.spell;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;

public class WandSpellContext extends SpellContext {

    @Override
    public void setCooldown(Spell spell) {
        spell.setCooldown(getItemStack(), getStats());
    }

    @Override
    public void setCooldown(Spell spell, int cost) {
        spell.setCooldown(getItemStack(), getStats(), cost);
    }

    @Override
    public void removeWissen(Spell spell) {
        spell.removeWissen(getItemStack(), getStats(), getEntity());
    }

    @Override
    public void removeWissen(Spell spell, int cost) {
        spell.removeWissen(getItemStack(), getStats(), getEntity(), cost);
    }

    @Override
    public void spellSound(Spell spell) {
        getLevel().playSound(null, pos.x(), pos.y(), pos.z(), WizardsRebornSounds.SPELL_CAST.get(), SoundSource.PLAYERS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
    }

    @Override
    public void awardStat(Spell spell) {
        if (getEntity() instanceof Player player && !getItemStack().isEmpty()) {
            player.awardStat(Stats.ITEM_USED.get(getItemStack().getItem()));
        }
    }

    public static WandSpellContext getFromWand(Entity entity, ItemStack stack) {
        WandSpellContext spellContext = new WandSpellContext();
        spellContext.setLevel(entity.level());
        spellContext.setEntity(entity);
        spellContext.setPos(entity.getEyePosition());
        spellContext.setVec(entity.getLookAngle());
        if (entity instanceof Player player) {
            spellContext.setDistance(player.getAttributeValue(ForgeMod.ENTITY_REACH.get()));
        } else {
            spellContext.setDistance(3);
        }
        spellContext.setItemStack(stack);
        spellContext.setStats(Spell.getStats(stack));
        return spellContext;
    }
}
