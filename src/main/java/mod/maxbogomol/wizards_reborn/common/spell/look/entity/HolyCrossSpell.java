package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class HolyCrossSpell extends EntityLookSpell {
    public HolyCrossSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
        addCrystalType(WizardsRebornCrystals.AIR);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.holySpellColor;
    }

    @Override
    public int getCooldown() {
        return 20;
    }

    @Override
    public int getWissenCost() {
        return 10;
    }

/*    @Override
    public float getLookAdditionalDistance() {
        return 0.5f;
    }*/
/*
    @Override
    public void lookSpell(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag stats = getStats(stack);
        int focusLevel = CrystalUtil.getStatLevel(stats, WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
        float damage = (float) (1.0f + ((focusLevel + magicModifier) * 0.5));

        HitResult hit = getEntityHit(level, player, hand);
        Vec3 pos = hit.getPosHit();
        if (hit.hasEntities()) {
            for (Entity entity : hit.getEntities()) {
                if (entity instanceof LivingEntity livingEntity) {
                    if (livingEntity.isInvertedHealAndHarm()) {
                        entity.hurt(new DamageSource(WizardsRebornDamage.create(entity.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder(), player), damage);
                    } else {
                        if (livingEntity.getHealth() != livingEntity.getMaxHealth()) {
                            livingEntity.heal(damage);
                        }
                    }

                    Color color = getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    PacketHandler.sendToTracking(player.level(), player.getOnPos(), new HolyCrossSpellEffectPacket((float) pos.x, (float) pos.y, (float) pos.z, r, g, b));
                }
            }
        }
    }*/
}
