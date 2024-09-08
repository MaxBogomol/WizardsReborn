package mod.maxbogomol.wizards_reborn.common.spell.sound;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
    public void useSpell(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);

            CompoundTag stats = getStats(stack);
            setCooldown(stack, stats);
            removeWissen(stack, stats, player);
            awardStat(player, stack);
            playSound(level, player, hand);
        }
    }

    public SoundEvent getSound(Level level, Player player, InteractionHand hand) {
        return WizardsRebornSounds.SPELL_CAST.get();
    }

    public void playSound(Level level, Player player, InteractionHand hand) {
        level.playSound(WizardsReborn.proxy.getPlayer(), player.getX(), player.getY(), player.getZ(), getSound(level, player, hand), SoundSource.PLAYERS, 1f, 1f);
    }

    @Override
    public void onReload(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {

    }
}
