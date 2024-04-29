package mod.maxbogomol.wizards_reborn.common.spell.self;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AirFlowSpellEffectPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class AirFlowSpell extends SelfSpell {
    public AirFlowSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.AIR_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.airSpellColor;
    }

    public int getCooldown() {
        return 150;
    }

    public int getWissenCost() {
        return 80;
    }

    @Override
    public void selfSpell(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag stats = getStats(stack);

        int focusLevel = CrystalUtils.getStatLevel(stats, WizardsReborn.FOCUS_CRYSTAL_STAT);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);

        float scale = 0.55f + (focusLevel * 0.15f);

        Vec3 vel = player.getViewVector(0).scale(scale);
        if (player.isFallFlying()) {
            vel = vel.scale(0.65f);
        }

        player.push(vel.x(), vel.y(), vel.z());
        player.hurtMarked = true;
        if (magicModifier > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, (int) (50 * magicModifier), 0));
        }

        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        PacketHandler.sendToTracking(world, player.getOnPos(), new AirFlowSpellEffectPacket((float) player.getX(), (float) player.getY() + 0.2f, (float) player.getZ(), (float) vel.x() / 4, (float) vel.y() / 4, (float) vel.z() / 4, r, g, b));
    }
}
