package mod.maxbogomol.wizards_reborn.common.spell.look;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.damage.DamageSourceRegistry;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SetAdditionalFovPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.BlinkSpellEffectPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class BlinkSpell extends LookSpell {
    public boolean isSharp;

    public BlinkSpell(String id, int points, boolean isSharp) {
        super(id, points);
        addCrystalType(WizardsReborn.VOID_CRYSTAL_TYPE);
        this.isSharp = isSharp;
    }

    @Override
    public Color getColor() {
        return WizardsReborn.voidSpellColor;
    }

    @Override
    public int getCooldown() {
        return 100;
    }

    @Override
    public int getWissenCost() {
        return 100;
    }

    @Override
    public float getLookDistance() {
        if (isSharp) return 6f;
        return 5f;
    }

    @Override
    public int getMinimumPolishingLevel() {
        if (isSharp) return 1;
        return 0;
    }

    @Override
    public float getLookAdditionalDistance() {
        return 0.5f;
    }

    @Override
    public void lookSpell(Level world, Player player, InteractionHand hand) {
        Vec3 pos = getHitPos(world, player, hand).getPosHit();

        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        PacketHandler.sendToTracking(world, player.getOnPos(), new BlinkSpellEffectPacket((float) player.getX(), (float) player.getY() + player.getEyeHeight(), (float) player.getZ(), (float) pos.x, (float) pos.y, (float) pos.z, r, g, b, isSharp));
        PacketHandler.sendTo(player, new SetAdditionalFovPacket(30f));
        world.playSound(WizardsReborn.proxy.getPlayer(), pos.x(), pos.y(), pos.z(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1f, 0.95f);

        ItemStack stack = player.getItemInHand(hand);
        CompoundTag stats = getStats(stack);
        int focusLevel = CrystalUtil.getStatLevel(stats, WizardsReborn.FOCUS_CRYSTAL_STAT);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
        float damage = (3f + (focusLevel)) + magicModifier;

        if (isSharp) {
            for (Entity entity : getHitEntities(world, player.getEyePosition(), pos, 0.5f)) {
                if (!entity.equals(player) && entity instanceof LivingEntity) {
                    entity.hurt(new DamageSource(DamageSourceRegistry.create(entity.level(), DamageSourceRegistry.ARCANE_MAGIC).typeHolder(), player), damage);
                }
            }
        }

        player.teleportTo(pos.x(), pos.y(), pos.z());
        player.setDeltaMovement(Vec3.ZERO);
        player.fallDistance = 0;

        if (magicModifier > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, (int) (40 * magicModifier), 0));
        }
    }
}
