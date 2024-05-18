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
    public void useSpell(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);

            CompoundTag stats = getStats(stack);
            setCooldown(stack, stats);
            removeWissen(stack, stats, player);
            awardStat(player, stack);
            spellSound(player, world);
            selfSpell(world, player, hand);
        }
    }

    public boolean canSpell(Level world, Player player, InteractionHand hand) {
        if (super.canSpell(world, player, hand)) {
            return canSelfSpell(world, player, hand);
        }
        return false;
    }

    public boolean canSelfSpell(Level world, Player player, InteractionHand hand) {
        return true;
    }

    public void selfSpell(Level world, Player player, InteractionHand hand) {

    }
}
