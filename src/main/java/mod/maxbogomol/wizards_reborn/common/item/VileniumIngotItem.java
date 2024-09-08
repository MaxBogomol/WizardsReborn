package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.item.IParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.awt.*;
import java.util.Random;

public class VileniumIngotItem extends Item implements IParticleItem {
    private static Random random = new Random();

    public VileniumIngotItem(Properties properties) {
        super(properties);
    }

    @Override
    public void addParticles(Level level, ItemEntity entity) {
        if (random.nextFloat() < 0.07) {
            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                    .setColorData(ColorParticleData.create(0.921f, 0.792f, 0.607f).build())
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                    .setScaleData(GenericParticleData.create(0.1f, 0).build())
                    .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                    .setLifetime(30)
                    .randomVelocity(0.01f)
                    .randomOffset(0.125f)
                    .spawn(level, entity.getX(), entity.getY() + 0.25F, entity.getZ());
        }
        if (random.nextFloat() < 0.01) {
            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                    .setColorData(ColorParticleData.create(Color.WHITE).build())
                    .setTransparencyData(GenericParticleData.create(0.75f, 0).build())
                    .setScaleData(GenericParticleData.create(0.1f, 0).build())
                    .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                    .setLifetime(30)
                    .randomVelocity(0.01f)
                    .randomOffset(0.125f)
                    .spawn(level, entity.getX(), entity.getY() + 0.25F, entity.getZ());
        }
    }
}
