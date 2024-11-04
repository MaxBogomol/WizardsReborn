package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.item.FuelItem;
import mod.maxbogomol.fluffy_fur.common.item.IParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.Random;

public class NetherSaltItem extends FuelItem implements IParticleItem {
    private static final Random random = new Random();

    public NetherSaltItem(Properties properties, int fuel) {
        super(properties, fuel);
    }

    @Override
    public void addParticles(Level level, ItemEntity entity) {
        if (random.nextFloat() < 0.1) {
            ParticleBuilder.create(FluffyFurParticles.SMOKE)
                    .setRenderType(FluffyFurRenderTypes.TRANSLUCENT_PARTICLE)
                    .setColorData(ColorParticleData.create(Color.BLACK).build())
                    .setTransparencyData(GenericParticleData.create(1f, 0).build())
                    .setScaleData(GenericParticleData.create(0.1f, 0.15f, 0).setEasing(Easing.SINE_IN_OUT).build())
                    .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.1f).build())
                    .setLightData(LightParticleData.DEFAULT)
                    .setLifetime(100)
                    .randomVelocity(0.005f)
                    .randomOffset(0.125f)
                    .spawn(level, entity.getX(), entity.getY() + 0.25F, entity.getZ());
        }
    }
}
