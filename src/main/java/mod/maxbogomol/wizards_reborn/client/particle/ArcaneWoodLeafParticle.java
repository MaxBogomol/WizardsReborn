package mod.maxbogomol.wizards_reborn.client.particle;

import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.world.ClientWorld;

import javax.annotation.Nonnull;

public class ArcaneWoodLeafParticle extends GenericParticle {
    public ArcaneWoodLeafParticle(ClientWorld world, GenericParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    @Override
    protected int getBrightnessForRender(float partialTicks) {
        return 0xF000F0;
    }

    @Nonnull
    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
}