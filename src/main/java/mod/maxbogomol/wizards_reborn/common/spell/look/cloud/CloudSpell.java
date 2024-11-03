package mod.maxbogomol.wizards_reborn.common.spell.look.cloud;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.raycast.RayCast;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.spell.look.block.BlockLookSpell;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.entity.WizardsRebornEntities;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class CloudSpell extends BlockLookSpell {

    public CloudSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public double getBlockDistance() {
        return 5f;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public int getCooldown() {
        return 350;
    }

    @Override
    public int getWissenCost() {
        return 200;
    }

    @Override
    public void lookSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            Vec3 pos = getBlockHit(level, spellContext).getPos();
            SpellEntity entity = new SpellEntity(WizardsRebornEntities.SPELL.get(), level);
            entity.setup(pos.x(), pos.y(), pos.z(), spellContext.getEntity(), this.getId(), spellContext.getStats()).setSpellContext(spellContext);
            level.addFreshEntity(entity);
        }
    }

    @Override
    public boolean canLookSpell(Level level, SpellContext spellContext) {
        return !getBlockHit(level, spellContext).hasBlock();
    }

    @Override
    public RayHitResult getBlockHit(Level level, SpellContext spellContext) {
        double distance = getLookDistance(spellContext);
        Vec3 firstPos = spellContext.getVec().scale(distance);
        Vec3 lookPos = RayCast.getHit(level, spellContext.getPos(), spellContext.getPos().add(firstPos.x(), 0, firstPos.z())).getPos();
        return RayCast.getHit(level, lookPos, new Vec3(lookPos.x(), lookPos.y() + getBlockDistance(spellContext), lookPos.z()));
    }

    @Override
    public void entityTick(SpellEntity entity) {
        rain(entity);
        if (!entity.level().isClientSide()) {
            if (entity.tickCount > getLifeTime(entity)) {
                entity.remove();
            }
        } else {
            cloudEffect(entity);
        }
    }

    public void cloudEffect(SpellEntity entity) {
        Color color = getColor();

        float ySize = 1f;
        float size = getCloudSize(entity);

        for (int i = 0; i < 5; i++) {
            if (random.nextFloat() < 0.4f) {
                ParticleBuilder.create(FluffyFurParticles.SMOKE)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.45f, 0).build())
                        .setScaleData(GenericParticleData.create(1.5f).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(20)
                        .randomVelocity(0.007f)
                        .randomOffset(size, ySize, size)
                        .spawn(entity.level(), entity.getX(), entity.getY(), entity.getZ());
            }
            if (random.nextFloat() < 0.8f) {
                ParticleBuilder.create(FluffyFurParticles.SMOKE)
                        .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.45f, 0).build())
                        .setScaleData(GenericParticleData.create(1.5f).build())
                        .setLightData(LightParticleData.DEFAULT)
                        .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                        .setLifetime(20)
                        .randomVelocity(0.007f)
                        .randomOffset(size, ySize, size)
                        .spawn(entity.level(), entity.getX(), entity.getY(), entity.getZ());
            }
        }

        boolean trails = hasTrails(entity);

        if (random.nextFloat() < (trails ? 0.5f : 0.8f)) {
            ParticleBuilder.create(FluffyFurParticles.WISP)
                    .setColorData(ColorParticleData.create(color).build())
                    .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                    .setScaleData(GenericParticleData.create(0.2f, 0).build())
                    .setLifetime(100)
                    .randomOffset(size, ySize, size)
                    .addVelocity(0, -0.2f, 0)
                    .spawn(entity.level(), entity.getX(), entity.getY(), entity.getZ());
        }

        if (trails && random.nextFloat() < 0.4f) {
            ParticleBuilder.create(FluffyFurParticles.SQUARE)
                    .setBehavior(SparkParticleBehavior.create().build())
                    .setColorData(ColorParticleData.create(color).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0.75f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                    .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                    .setLifetime(70)
                    .randomOffset(size, ySize, size)
                    .addVelocity(0, -0.2f, 0)
                    .spawn(entity.level(), entity.getX(), entity.getY(), entity.getZ());
        }
    }

    public int getLifeTime(SpellEntity entity) {
        return 300;
    }

    public boolean hasTrails(SpellEntity entity) {
        return false;
    }

    public void rain(SpellEntity entity) {

    }

    public boolean isValidPos(SpellEntity entity, Vec3 pos) {
        return !RayCast.getHit(entity.level(), pos, new Vec3(pos.x(), entity.getY(), pos.z())).hasBlock();
    }

    public float getCloudSize(SpellEntity entity) {
        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
        return (2.25f + ((focusLevel + magicModifier) * 0.25f));
    }
}
