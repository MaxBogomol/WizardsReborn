package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.fluffy_fur.common.item.FuelItem;
import mod.maxbogomol.fluffy_fur.common.item.IParticleItem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;

import java.util.Random;

public class NetherSaltItem extends FuelItem implements IParticleItem {
    private static Random random = new Random();

    public NetherSaltItem(Properties properties, int fuel) {
        super(properties, fuel);
    }

    @Override
    public void addParticles(Level level, ItemEntity entity) {
        if (random.nextFloat() < 0.1) {
            Particles.create(WizardsReborn.SMOKE_PARTICLE)
                    .addVelocity(((random.nextDouble() - 0.5D) / 80), ((random.nextDouble() - 0.5D) / 50) + 0.02f, ((random.nextDouble() - 0.5D) / 80))
                    .setAlpha(1f, 0).setScale(0.15f, 0)
                    .setColor(0, 0, 0)
                    .setLifetime(100)
                    .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(level, entity.getX() + ((random.nextDouble() - 0.5D) * 0.125), entity.getY() + 0.25F + ((random.nextDouble() - 0.5D) * 0.125), entity.getZ() + ((random.nextDouble() - 0.5D) * 0.125));
        }
    }
}
