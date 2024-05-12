package mod.maxbogomol.wizards_reborn.common.spell.look.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.common.spell.look.LookSpell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BlockLookSpell extends LookSpell {
    public BlockLookSpell(String id, int points) {
        super(id, points);
    }

    public float getBlockDistance() {
        return 10f;
    }

    public float getBlockAdditionalDistance() {
        return 0f;
    }

    public float getBlockDistance(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag stats = getStats(stack);

        int focusLevel = CrystalUtils.getStatLevel(stats, WizardsReborn.FOCUS_CRYSTAL_STAT);
        return getLookDistance() + (getLookAdditionalDistance() * focusLevel);
    }
}
