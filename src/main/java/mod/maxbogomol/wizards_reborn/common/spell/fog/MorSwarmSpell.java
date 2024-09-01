package mod.maxbogomol.wizards_reborn.common.spell.fog;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornDamage;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMobEffects;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.awt.*;
import java.util.List;

public class MorSwarmSpell extends FogSpell {
    public MorSwarmSpell(String id, int points) {
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
        return 300;
    }

    @Override
    public int getWissenCost() {
        return 200;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public void fog(SpellProjectileEntity entity, Player player) {
        float alpha = 1;
        int lifeTime = getLifeTime(entity);

        if (entity.tickCount < 20) {
            alpha = (entity.tickCount) / 20f;
        }
        if (entity.tickCount > lifeTime - 20) {
            alpha = ((lifeTime - entity.tickCount) / 20f);
        }
        if (alpha > 1f) alpha = 1f;
        if (alpha < 0f) alpha = 0f;

        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
        int size = getSize(entity) + (getSize(entity) * focusLevel);
        List<BlockPos> blocks = getBlocks(entity.level(), entity.getOnPos(), (int) (size * alpha), 4, isCircle(entity));
        List<Entity> entities = getEntities(entity.level(), blocks);

        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getSender());
        float damage = (0.25f + ((focusLevel + magicModifier) * 0.15f));

        for (Entity e : entities) {
            if (e instanceof LivingEntity target) {
                if (target.tickCount % 20 == 0) {
                    target.lastHurtByPlayerTime = target.tickCount;
                    target.hurt(new DamageSource(WizardsRebornDamage.create(target.level(), WizardsRebornDamage.ARCANE_MAGIC).typeHolder()), damage);
                }
                target.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (60 + (20 * (focusLevel + magicModifier))), 1));
                target.addEffect(new MobEffectInstance(MobEffects.HUNGER, (int) (20 + (20 * (focusLevel + magicModifier))), 0));
                target.addEffect(new MobEffectInstance(MobEffects.CONFUSION, (int) (100 + (40 * (focusLevel + magicModifier))), 0));
                target.addEffect(new MobEffectInstance(WizardsRebornMobEffects.MOR_SPORES.get(), (int) (100 + (40 * (focusLevel + magicModifier))), 0));
            }
        }
    }
}
