package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;

import java.awt.*;

public class WitheringSpell extends EntityLookSpell {
    public WitheringSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.AIR);
        addCrystalType(WizardsRebornCrystals.FIRE);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.witheringSpellColor;
    }

    @Override
    public int getCooldown() {
        return 60;
    }

    @Override
    public int getWissenCost() {
        return 110;
    }

/*    @Override
    public float getLookAdditionalDistance() {
        return 0.5f;
    }*/

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }
/*
    @Override
    public void lookSpell(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag stats = getStats(stack);
        int focusLevel = CrystalUtil.getStatLevel(stats, WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);

        HitResult hit = getEntityHit(level, player, hand);
        Vec3 pos = hit.getPosHit();
        if (hit.hasEntities()) {
            for (Entity entity : hit.getEntities()) {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.WITHER, (int) (200 + (50 * (focusLevel + magicModifier))), 0));

                    Color color = getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    PacketHandler.sendToTracking(player.level(), player.getOnPos(), new WitheringSpellEffectPacket((float) pos.x, (float) pos.y, (float) pos.z, r, g, b));
                }
            }
        }
    }*/
}
