package mod.maxbogomol.wizards_reborn.common.spell.look.block;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.spell.look.LookSpell;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

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
/*
    public float getBlockDistance(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag stats = getStats(stack);

        int focusLevel = CrystalUtil.getStatLevel(stats, WizardsRebornCrystals.FOCUS);
        return getBlockDistance() + (getBlockAdditionalDistance() * focusLevel);
    }

    @Override
    public boolean canLookSpell(Level level, Player player, InteractionHand hand) {
        return getBlockHit(level, player, hand).hasBlockHit();
    }

    public HitResult getBlockHit(Level level, Player player, InteractionHand hand) {
        Vec3 lookPos = getHitPos(level, player, hand).getPosHit();
        return getHitPos(level, lookPos, new Vec3(lookPos.x(), lookPos.y() - getBlockDistance(level, player, hand), lookPos.z()));
    }*/
}
