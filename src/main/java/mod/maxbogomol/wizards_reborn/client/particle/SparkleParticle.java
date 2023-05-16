package mod.maxbogomol.wizards_reborn.client.particle;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import mod.maxbogomol.wizards_reborn.client.render.RenderUtils;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;

public class SparkleParticle extends GenericParticle {
    public SparkleParticle(ClientWorld world, GenericParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    @Override
    protected int getBrightnessForRender(float partialTicks) {
        return 0xF000F0;
    }

    @Override
    public void renderParticle(IVertexBuilder b, ActiveRenderInfo info, float pticks) {
        super.renderParticle(true ? WorldRenderHandler.getDelayedRender().getBuffer(RenderUtils.GLOWING_PARTICLE) : b, info, pticks);
    }
}