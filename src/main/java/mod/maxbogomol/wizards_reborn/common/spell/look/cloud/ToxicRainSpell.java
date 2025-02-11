package mod.maxbogomol.wizards_reborn.common.spell.look.cloud;

import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import java.awt.*;
import java.util.List;


public class ToxicRainSpell extends CloudSpell {

    public ToxicRainSpell(String id, int points) {
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
        return 450;
    }

    @Override
    public int getWissenCost() {
        return 300;
    }

    @Override
    public boolean hasTrails(SpellEntity entity) {
        return true;
    }

    @Override
    public void rain(SpellEntity entity) {
        if (!entity.level().isClientSide()) {
            float size = getCloudSize(entity);

            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float damage = (0.5f + ((focusLevel + magicModifier) * 0.25f)) + WizardsRebornConfig.TOXIC_RAIN_DAMAGE.get().floatValue();

            List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(entity.getX() - size, entity.getY() - 15, entity.getZ() - size, entity.getX() + size, entity.getY() + 0.5f, entity.getZ() + size));

            for (LivingEntity target : list) {
                if (isValidPos(entity, target.position())) {
                    if (target.tickCount % 20 == 0) {
                        target.lastHurtByPlayerTime = target.tickCount;
                        DamageSource damageSource = DamageHandler.create(entity.level(), WizardsRebornDamageTypes.ARCANE_MAGIC);
                        target.hurt(damageSource, damage);
                    }
                    float power = WizardsRebornConfig.TOXIC_RAIN_POWER.get().floatValue();
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (20 + (10 * (focusLevel + magicModifier)) + power), 1));
                    target.addEffect(new MobEffectInstance(MobEffects.HUNGER, (int) (20 + (20 * (focusLevel + magicModifier)) + power), 0));
                    target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, (int) (100 + (40 * (focusLevel + magicModifier)) + power), 0));
                }
            }
        }
    }
}
