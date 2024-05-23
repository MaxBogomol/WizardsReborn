package mod.maxbogomol.wizards_reborn.common.spell.sound;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
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
        return WizardsReborn.soundSpellColor;
    }

    @Override
    public void useSpell(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);

            CompoundTag stats = getStats(stack);
            setCooldown(stack, stats);
            removeWissen(stack, stats, player);
            awardStat(player, stack);
            playSound(world, player, hand);
        }
    }

    public SoundEvent getSound(Level world, Player player, InteractionHand hand) {
        return WizardsReborn.SPELL_CAST_SOUND.get();
    }

    public void playSound(Level world, Player player, InteractionHand hand) {
        world.playSound(WizardsReborn.proxy.getPlayer(), player.getX(), player.getY(), player.getZ(), getSound(world, player, hand), SoundSource.PLAYERS, 1f, 1f);
    }

    @Override
    public void onReload(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {

    }
}
