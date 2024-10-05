package mod.maxbogomol.wizards_reborn.common.spell.self;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.HeartOfNatureSpellEffectPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.awt.*;

public class HeartOfNatureSpell extends SelfSpell {
    public HeartOfNatureSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.earthSpellColor;
    }

    @Override
    public int getCooldown() {
        return 250;
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

        int heal = 0;
        int regen = 0;
        if (magicModifier >= 1) {
            heal = 1;
        }
        if (magicModifier >= 2) {
            regen = 1;
        }

        player.addEffect(new MobEffectInstance(MobEffects.HEAL, 1, heal));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, (int) (250 + (40 * (focusLevel + magicModifier))), regen));
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, (int) (680 + (120 * (focusLevel + magicModifier))), 0));

        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        PacketHandler.sendToTracking(player.level(), player.getOnPos(), new HeartOfNatureSpellEffectPacket((float) player.getX(), (float) player.getY() + (player.getBbHeight() / 2), (float) player.getZ(), r, g, b));
    }*/
}
