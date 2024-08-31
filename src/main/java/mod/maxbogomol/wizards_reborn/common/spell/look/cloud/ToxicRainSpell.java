package mod.maxbogomol.wizards_reborn.common.spell.look.cloud;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.damage.DamageSourceRegistry;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

import java.awt.*;
import java.util.List;

public class ToxicRainSpell extends CloudSpell {
    public ToxicRainSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.EARTH_CRYSTAL_TYPE);
        addCrystalType(WizardsReborn.WATER_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.poisonSpellColor;
    }

    @Override
    public int getCooldown() {
        return 450;
    }

    @Override
    public int getWissenCost() {
        return 300;
    }

    @Override
    public boolean hasTrails(SpellProjectileEntity entity) {
        return true;
    }

    @Override
    public void rain(SpellProjectileEntity entity, Player player) {
        float size = getCloudSize(entity);

        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getSender());
        float damage = (0.5f + ((focusLevel + magicModifier) * 0.25f));

        List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(entity.getX() - size, entity.getY() - 15, entity.getZ() - size, entity.getX() + size, entity.getY() + 0.5f, entity.getZ() + size));

        for (LivingEntity target : list) {
            if (isValidPos(entity, target.position())) {
                if (target.tickCount % 20 == 0) {
                    target.lastHurtByPlayerTime = target.tickCount;
                    target.hurt(new DamageSource(DamageSourceRegistry.create(target.level(), DamageSourceRegistry.ARCANE_MAGIC).typeHolder()), damage);
                }
                target.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (20 + (10 * (focusLevel + magicModifier))), 1));
                target.addEffect(new MobEffectInstance(MobEffects.HUNGER, (int) (20 + (20 * (focusLevel + magicModifier))), 0));
                target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, (int) (100 + (40 * (focusLevel + magicModifier))), 0));
            }
        }
    }
}
