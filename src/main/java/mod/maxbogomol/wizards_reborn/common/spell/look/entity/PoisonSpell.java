package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.PoisonSpellEffectPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class PoisonSpell extends EntityLookSpell {
    public PoisonSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.poisonSpellColor;
    }

    @Override
    public int getCooldown() {
        return 60;
    }

    @Override
    public int getWissenCost() {
        return 70;
    }

    @Override
    public float getLookAdditionalDistance() {
        return 0.5f;
    }

    @Override
    public void lookSpell(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag stats = getStats(stack);
        int focusLevel = CrystalUtil.getStatLevel(stats, WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);

        HitResult hit = getEntityHit(world, player, hand);
        Vec3 pos = hit.getPosHit();
        if (hit.hasEntities()) {
            for (Entity entity : hit.getEntities()) {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (200 + (50 * (focusLevel + magicModifier))), 0));

                    Color color = getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    PacketHandler.sendToTracking(player.level(), player.getOnPos(), new PoisonSpellEffectPacket((float) pos.x, (float) pos.y, (float) pos.z, r, g, b));
                }
            }
        }
    }
}
