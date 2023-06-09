package mod.maxbogomol.wizards_reborn.client.particle;

import com.mojang.serialization.Codec;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.ParticleType;

public class KarmaParticleType extends ParticleType<GenericParticleData> {
    public KarmaParticleType() {
        super(false, GenericParticleData.DESERIALIZER);
    }

    @Override
    public Codec<GenericParticleData> func_230522_e_() {
        return GenericParticleData.codecFor(this);
    }

    public static class Factory implements IParticleFactory<GenericParticleData> {
        private final IAnimatedSprite sprite;

        public Factory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }

        @Override
        public Particle makeParticle(GenericParticleData data, ClientWorld world, double x, double y, double z, double mx, double my, double mz) {
            KarmaParticle ret = new KarmaParticle(world, data, x, y, z, mx, my, mz);
            ret.selectSpriteRandomly(sprite);
            return ret;
        }
    }
}