package mod.maxbogomol.wizards_reborn.common.spell.fog;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class FogSpell extends Spell {
    public FogSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public boolean canSpellAir(Level world, Player player, InteractionHand hand) {
        return false;
    }

    @Override
    public InteractionResult onWandUseOn(ItemStack stack, UseOnContext context) {
        if (canSpell(context.getLevel(), context.getPlayer(), context.getHand()) && !context.getPlayer().level().isClientSide()) {

        }

        return InteractionResult.PASS;
    }
}
