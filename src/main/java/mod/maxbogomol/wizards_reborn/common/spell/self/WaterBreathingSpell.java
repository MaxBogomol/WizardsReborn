package mod.maxbogomol.wizards_reborn.common.spell.self;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class WaterBreathingSpell extends SelfSpell {
    public WaterBreathingSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.waterSpellColor;
    }

    @Override
    public int getCooldown() {
        return 150;
    }

    @Override
    public int getWissenCost() {
        return 200;
    }
/*
    @Override
    public void selfSpell(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag stats = getStats(stack);

        int focusLevel = CrystalUtil.getStatLevel(stats, WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);

        player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, (int) (2000 + (450 * (focusLevel + magicModifier))), 0));
        player.clearFire();

        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        PacketHandler.sendToTracking(player.level(), player.getOnPos(), new WaterBreathingSpellEffectPacket((float) player.getX(), (float) player.getY() + (player.getBbHeight() / 2), (float) player.getZ(), r, g, b));
    }*/
}
