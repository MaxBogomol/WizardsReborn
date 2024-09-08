package mod.maxbogomol.wizards_reborn.common.spell.self;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SelfSpell extends Spell {
    public SelfSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public void useSpell(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);

            CompoundTag stats = getStats(stack);
            setCooldown(stack, stats);
            removeWissen(stack, stats, player);
            awardStat(player, stack);
            spellSound(player, level);
            selfSpell(level, player, hand);
        }
    }

    public boolean canSpell(Level level, Player player, InteractionHand hand) {
        if (super.canSpell(level, player, hand)) {
            return canSelfSpell(level, player, hand);
        }
        return false;
    }

    public boolean canSelfSpell(Level level, Player player, InteractionHand hand) {
        return true;
    }

    public void selfSpell(Level level, Player player, InteractionHand hand) {

    }
}
