package mod.maxbogomol.wizards_reborn.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;

public class CubeParticle extends GenericParticle {
    public CubeParticle(ClientLevel world, GenericParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    @Override
    protected int getLightColor(float partialTicks) {
        return 0xF000F0;
    }

    @Override
    public void render(VertexConsumer b, Camera info, float pticks) {
        VertexConsumer builder = ClientConfig.BETTER_LAYERING.get() ? WorldRenderHandler.getDelayedRender().getBuffer(RenderUtils.GLOWING_PARTICLE) : b;

        float x = (float) (Mth.lerp(pticks, this.xo, this.x) - info.getPosition().x());
        float y = (float) (Mth.lerp(pticks, this.yo, this.y) - info.getPosition().y());
        float z = (float) (Mth.lerp(pticks, this.zo, this.z) - info.getPosition().z());

        float u0 = this.getU0();
        float u1 = this.getU1();
        float v0 = this.getV0();
        float v1 = this.getV1();
        int lmap = getLightColor(pticks);

        float x1 = x - (quadSize / 2f);
        float y1 = y - (quadSize / 2f);
        float z1 = z - (quadSize / 2f);

        float x2 = (quadSize);
        float y2 = (quadSize);
        float z2 = (quadSize);

        decoVert(builder.vertex(x1, y1 + y2, z1), u1, v1, alpha, lmap);
        decoVert(builder.vertex(x1 + x2, y1 + y2, z1), u1, v0, alpha, lmap);
        decoVert(builder.vertex(x1 + x2, y1, z1), u0, v0, alpha, lmap);
        decoVert(builder.vertex(x1, y1, z1), u0, v1, alpha, lmap);

        decoVert(builder.vertex(x1 + x2, y1 + y2, z1 + z2), u1, v1, alpha, lmap);
        decoVert(builder.vertex(x1, y1 + y2, z1 + z2), u1, v0, alpha, lmap);
        decoVert(builder.vertex(x1, y1, z1 + z2), u0, v0, alpha, lmap);
        decoVert(builder.vertex(x1 + x2, y1, z1 + z2), u0, v1, alpha, lmap);

        decoVert(builder.vertex(x1 + x2, y1, z1 + z2), u1, v1, alpha, lmap);
        decoVert(builder.vertex(x1 + x2, y1, z1), u1, v0, alpha, lmap);
        decoVert(builder.vertex(x1 + x2, y1 + y2, z1), u0, v0, alpha, lmap);
        decoVert(builder.vertex(x1 + x2, y1 + y2, z1 + z2), u0, v1, alpha, lmap);

        decoVert(builder.vertex(x1, y1, z1), u1, v1, alpha, lmap);
        decoVert(builder.vertex(x1, y1, z1 + z2), u1, v0, alpha, lmap);
        decoVert(builder.vertex(x1, y1 + y2, z1 + z2), u0, v0, alpha, lmap);
        decoVert(builder.vertex(x1, y1 + y2, z1), u0, v1, alpha, lmap);

        decoVert(builder.vertex(x1, y1 + y2, z1 + z2), u1, v1, alpha, lmap);
        decoVert(builder.vertex(x1 + x2, y1 + y2, z1 + z2), u1, v0, alpha, lmap);
        decoVert(builder.vertex(x1 + x2, y1 + y2, z1), u0, v0, alpha, lmap);
        decoVert(builder.vertex(x1, y1 + y2, z1), u0, v1, alpha, lmap);

        decoVert(builder.vertex(x1, y1, z1), u1, v1, alpha, lmap);
        decoVert(builder.vertex(x1 + x2, y1, z1), u1, v0, alpha, lmap);
        decoVert(builder.vertex(x1 + x2, y1, z1 + z2), u0, v0, alpha, lmap);
        decoVert(builder.vertex(x1, y1, z1 + z2), u0, v1, alpha, lmap);
    }

    private void decoVert(VertexConsumer vc, float u, float v, float alpha, int lmap) {
        vc.uv(u, v).color(rCol, gCol, bCol, alpha).uv2(lmap).endVertex();
    }
}