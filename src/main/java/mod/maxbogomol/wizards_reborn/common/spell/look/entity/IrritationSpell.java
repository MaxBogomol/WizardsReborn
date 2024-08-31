package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.IrritationSpellEffectPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class IrritationSpell extends EntityLookSpell {
    public IrritationSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.VOID_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.necroticSpellColor;
    }

    @Override
    public int getCooldown() {
        return 150;
    }

    @Override
    public int getWissenCost() {
        return 200;
    }

    @Override
    public float getLookAdditionalDistance() {
        return 0.5f;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public void lookSpell(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag stats = getStats(stack);
        int focusLevel = CrystalUtil.getStatLevel(stats, WizardsReborn.FOCUS_CRYSTAL_STAT);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);

        HitResult hit = getEntityHit(world, player, hand);
        Vec3 pos = hit.getPosHit();
        if (hit.hasEntities()) {
            for (Entity entity : hit.getEntities()) {
                if (entity instanceof LivingEntity livingEntity) {
                    livingEntity.addEffect(new MobEffectInstance(WizardsReborn.IRRITATION_EFFECT.get(), (int) (400 + (100 * (focusLevel + magicModifier))), 0));

                    Color color = getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    PacketHandler.sendToTracking(player.level(), player.getOnPos(), new IrritationSpellEffectPacket((float) pos.x, (float) pos.y, (float) pos.z, r, g, b));
                }
            }
        }
    }
}
