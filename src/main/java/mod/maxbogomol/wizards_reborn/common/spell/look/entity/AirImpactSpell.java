package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.client.animation.StrikeSpellItemAnimation;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.AirImpactSpellEffectPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class AirImpactSpell extends EntityLookSpell {
    public static StrikeSpellItemAnimation animation = new StrikeSpellItemAnimation();

    public AirImpactSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.AIR_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.airSpellColor;
    }

    @Override
    public int getCooldown() {
        return 50;
    }

    @Override
    public int getWissenCost() {
        return 40;
    }

    @Override
    public float getLookDistance() {
        return 2.5f;
    }

    @Override
    public float getLookAdditionalDistance() {
        return 0.25f;
    }

    @Override
    public boolean hasReachDistance(Level world, Player player, InteractionHand hand) {
        return false;
    }

    @Override
    public boolean canLookSpell(Level world, Player player, InteractionHand hand) {
        return getEntityHit(world, player, hand, getAllFilter(player), 0, 1.5f, false).hasEntities();
    }

    @Override
    public void lookSpell(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag stats = getStats(stack);
        int focusLevel = CrystalUtils.getStatLevel(stats, WizardsReborn.FOCUS_CRYSTAL_STAT);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
        float power = 1.1f + ((focusLevel + magicModifier) * 0.1f);

        Vec3 vec = player.getLookAngle().scale(power);

        HitResult hit = getEntityHit(world, player, hand, getAllFilter(player), 0, 1.5f, false);
        if (hit.hasEntities()) {
            for (Entity entity : hit.getEntities()) {
                entity.push(vec.x(), vec.y() / 2, vec.z());
                entity.hurtMarked = true;
            }
        }

        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(0.5f));
        PacketHandler.sendToTracking(world, player.getOnPos(), new AirImpactSpellEffectPacket((float) pos.x(), (float) pos.y(), (float) pos.z(), (float) vec.x() / 3, (float) vec.y() / 6, (float) vec.z() / 3, r, g, b));
    }
}
