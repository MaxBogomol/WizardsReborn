package mod.maxbogomol.wizards_reborn.common.spell.look.cloud;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.crystalritual.ArtificialFertilityCrystalRitual;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.List;

public class RainCloudSpell extends CloudSpell {
    public RainCloudSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.WATER_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.waterSpellColor;
    }

    @Override
    public void rain(SpellProjectileEntity entity, Player player) {
        float size = getCloudSize(entity);

        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsReborn.FOCUS_CRYSTAL_STAT);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getSender());
        float chance = (0.1f + ((focusLevel + magicModifier) * 0.025f));

        if (random.nextFloat() < chance) {
            float x = (float) (entity.getX() + ((random.nextFloat() - 0.5F) * 2 * size));
            float z = (float) (entity.getZ() + ((random.nextFloat() - 0.5F) * 2 * size));
            HitResult hit = getHitPos(entity.level(), new Vec3(x, entity.getY(), z), new Vec3(x, entity.getY() - 30, z));

            BlockPos blockPos = BlockPos.containing(hit.getPosHit().x(), hit.getPosHit().y(), hit.getPosHit().z());
            ArtificialFertilityCrystalRitual.growCrop(entity.level(), blockPos);
            ArtificialFertilityCrystalRitual.growCrop(entity.level(), blockPos.below());
        }

        List<LivingEntity> list = entity.level().getEntitiesOfClass(LivingEntity.class, new AABB(entity.getX() - size, entity.getY() - 30, entity.getZ() - size, entity.getX() + size, entity.getY() + 0.5f, entity.getZ() + size));

        for (LivingEntity target : list) {
            if (isValidPos(entity, target.position())) {
                target.clearFire();
            }
        }
    }
}
