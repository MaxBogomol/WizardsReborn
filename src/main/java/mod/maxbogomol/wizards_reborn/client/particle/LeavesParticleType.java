package mod.maxbogomol.wizards_reborn.client.particle;

import mod.maxbogomol.fluffy_fur.client.particle.AbstractParticleType;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticleOptions;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.multiplayer.ClientLevel;

public class LeavesParticleType extends AbstractParticleType<GenericParticleOptions> {
    public LeavesParticleType() {
        super();
    }

    public static class Factory implements ParticleProvider<GenericParticleOptions> {
        private final SpriteSet sprite;

        public Factory(SpriteSet sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle createParticle(GenericParticleOptions data, ClientLevel world, double x, double y, double z, double mx, double my, double mz) {
            LeavesParticle ret = new LeavesParticle(world, data, x, y, z, mx, my, mz);
            ret.pickSprite(sprite);
            return ret;
        }
    }
}