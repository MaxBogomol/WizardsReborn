package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.Random;

public class PrecisionCrystalItem extends Item implements IParticleItem {
    private static Random random = new Random();

    public PrecisionCrystalItem(Properties properties) {
        super(properties);
    }

    @Override
    public void addParticles(Level level, ItemEntity entity) {
        if (random.nextFloat() < 0.05) {
            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                    .addVelocity(((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50), ((random.nextDouble() - 0.5D) / 50))
                    .setAlpha(0.5f, 0).setScale(0.1f, 0)
                    .setColor(0.827f, 0.988f, 0.694f)
                    .setLifetime(30)
                    .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(level, entity.getX() + ((random.nextDouble() - 0.5D) * 0.25), entity.getY() + 0.25F + ((random.nextDouble() - 0.5D) * 0.25), entity.getZ() + ((random.nextDouble() - 0.5D) * 0.25));
        }
    }
}
