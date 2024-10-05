package mod.maxbogomol.wizards_reborn.common.spell.look;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class BlinkSpell extends LookSpell {
    public boolean isSharp;

    public BlinkSpell(String id, int points, boolean isSharp) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.VOID);
        this.isSharp = isSharp;
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.voidSpellColor;
    }

    @Override
    public int getCooldown() {
        return 100;
    }

    @Override
    public int getWissenCost() {
        return 100;
    }

/*    @Override
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
    }*/
/*
    @Override
    public void lookSpell(Level level, Player player, InteractionHand hand) {
        Vec3 pos = getHitPos(level, player, hand).getPosHit();

        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        PacketHandler.sendToTracking(level, player.getOnPos(), new BlinkSpellEffectPacket((float) player.getX(), (float) player.getY() + player.getEyeHeight(), (float) player.getZ(), (float) pos.x, (float) pos.y, (float) pos.z, r, g, b, isSharp));
        PacketHandler.sendTo(player, new SetAdditionalFovPacket(30f));
        level.playSound(WizardsReborn.proxy.getPlayer(), pos.x(), pos.y(), pos.z(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1f, 0.95f);

        ItemStack stack = player.getItemInHand(hand);
        CompoundTag stats = getStats(stack);
        int focusLevel = CrystalUtil.getStatLevel(stats, WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
        float damage = (3f + (focusLevel)) + magicModifier;

        if (isSharp) {
            for (Entity entity : getHitEntities(level, player.getEyePosition(), pos, 0.5f)) {
                if (!entity.equals(player) && entity instanceof LivingEntity) {
                    entity.hurt(new DamageSource(WizardsRebornDamage.create(entity.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), player), damage);
                }
            }
        }

        player.teleportTo(pos.x(), pos.y(), pos.z());
        player.setDeltaMovement(Vec3.ZERO);
        player.fallDistance = 0;

        if (magicModifier > 0) {
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, (int) (40 * magicModifier), 0));
        }
    }*/
}
