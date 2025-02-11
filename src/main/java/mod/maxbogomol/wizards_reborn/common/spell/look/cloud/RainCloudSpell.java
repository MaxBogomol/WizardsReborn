package mod.maxbogomol.wizards_reborn.common.spell.look.cloud;

import mod.maxbogomol.fluffy_fur.common.raycast.RayCast;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.fluffy_fur.util.BlockUtil;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.List;

public class RainCloudSpell extends CloudSpell {

    public RainCloudSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.waterSpellColor;
    }

    @Override
    public void rain(SpellEntity entity) {
        if (!entity.level().isClientSide()) {
            float size = getCloudSize(entity);

            int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
            float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
            float chance = (0.1f + ((focusLevel + magicModifier) * 0.025f)) + WizardsRebornConfig.RAIN_CLOUD_POWER.get().floatValue();

            if (random.nextFloat() < chance) {
                float x = (float) (entity.getX() + ((random.nextFloat() - 0.5F) * 2 * size));
                float z = (float) (entity.getZ() + ((random.nextFloat() - 0.5F) * 2 * size));
                RayHitResult hitResult = RayCast.getHit(entity.level(), new Vec3(x, entity.getY(), z), new Vec3(x, entity.getY() - 30, z));

                BlockPos blockPos = BlockPos.containing(hitResult.getPos());
                BlockUtil.growCrop(entity.level(), blockPos);
                BlockUtil.growCrop(entity.level(), blockPos.below());
            }

            List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(entity.getX() - size, entity.getY() - 30, entity.getZ() - size, entity.getX() + size, entity.getY() + 0.5f, entity.getZ() + size));

            for (LivingEntity target : list) {
                if (isValidPos(entity, target.position())) {
                    target.clearFire();
                }
            }
        }
    }
}
