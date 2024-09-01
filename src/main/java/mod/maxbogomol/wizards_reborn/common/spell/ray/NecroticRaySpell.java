package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.NecroticRaySpellEffectPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamage;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class NecroticRaySpell extends RaySpell {
    public NecroticRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.necroticSpellColor;
    }

    @Override
    public int getCooldown() {
        return 60;
    }

    @Override
    public int getWissenCost() {
        return 50;
    }

    @Override
    public float getRayDistance() {
        return 15f;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {
        super.onImpact(ray, world, projectile, player, target);

        int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
        float damage = (1f + ((focusLevel + magicModifier) * 0.5f));
        if (target.hurt(new DamageSource(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.RITUAL).typeHolder(), projectile, player), damage)) {
            if (player != null && random.nextFloat() < 0.4f) player.heal(1);
        }
    }

    @Override
    public void rayEndTick(SpellProjectileEntity entity, HitResult ray) {
        CompoundTag spellData = entity.getSpellData();

        if (spellData.getInt("tick_left") <= 0) {
            float distance = (float) Math.sqrt(Math.pow(entity.getX() - ray.getLocation().x, 2) + Math.pow(entity.getY() - ray.getLocation().y, 2) + Math.pow(entity.getZ() - ray.getLocation().z, 2));
            Vec3 pos = entity.position();
            Vec3 posStart = entity.getLookAngle().add(entity.position());
            Vec3 posEnd = entity.getLookAngle().scale(distance).add(entity.position());

            Color color = getColor();
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            PacketHandler.sendToTracking(entity.level(), new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), new NecroticRaySpellEffectPacket((float) posStart.x, (float) posStart.y + 0.2F, (float) posStart.z, (float) posEnd.x, (float) posEnd.y + 0.4F, (float) posEnd.z, r, g, b));
        }
    }
}
