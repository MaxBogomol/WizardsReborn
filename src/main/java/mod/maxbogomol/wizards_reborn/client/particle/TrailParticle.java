package mod.maxbogomol.wizards_reborn.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;

public class TrailParticle extends GenericParticle implements ICustomRenderParticle {
    public float sX = 0;
    public float sY = 0;
    public float sZ = 0;

    public TrailParticle(ClientLevel world, GenericParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
        sX = (float) x;
        sY = (float) y;
        sZ = (float) z;
        hasPhysics = false;
    }

    @Override
    protected int getLightColor(float partialTicks) {
        return 0xF000F0;
    }

    @Override
    public void render(VertexConsumer b, Camera info, float pticks) {
        WorldRenderHandler.particleList.add(this);
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource buffer, float partialTicks) {
        VertexConsumer builder = buffer.getBuffer(RenderUtils.GLOWING);

        double dX = Mth.lerp(partialTicks, this.xo, this.x) - this.sX;
        double dY = Mth.lerp(partialTicks, this.yo, this.y) - this.sY;
        double dZ = Mth.lerp(partialTicks, this.zo, this.z) - this.sZ;

        float distance = (float) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2) + Math.pow(dZ, 2));
        distance = distance * 0.25f;

        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Mth.PI;

        float width = quadSize / 4f;

        double X = Math.sin(pitch) * Math.cos(yaw) * distance;
        double Y = Math.cos(pitch) * distance;
        double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

        stack.pushPose();
        stack.translate(Mth.lerp(partialTicks, this.xo, this.x), Mth.lerp(partialTicks, this.yo, this.y), Mth.lerp(partialTicks, this.zo, this.z));

        float endU = Mth.PI * 2;
        float stepU = (endU) / 8;

        if (distance > 0) {
            for (int i = 0; i < 8; i++) {
                float u = i * stepU;
                float un = (i + 1 == 8) ? endU : (i + 1) * stepU;

                Matrix4f mat = stack.last().pose();

                stack.pushPose();
                stack.translate(X, Y, Z);
                stack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
                stack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 90f));
                stack.mulPose(Axis.ZP.rotationDegrees(90));
                stack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(u)));
                stack.translate(width, 0, 0);
                mat = stack.last().pose();
                builder.vertex(mat, 0, 0, 0).color(rCol, gCol, bCol, alpha).endVertex();
                stack.popPose();

                stack.pushPose();
                stack.translate(-(Mth.lerp(partialTicks, this.xo, this.x) - sX), -(Mth.lerp(partialTicks, this.yo, this.y) - sY), -(Mth.lerp(partialTicks, this.zo, this.z) - sZ));
                mat = stack.last().pose();
                builder.vertex(mat, 0, 0, 0).color(rCol, gCol, bCol, alpha).endVertex();
                builder.vertex(mat, 0, 0, 0).color(rCol, gCol, bCol, alpha).endVertex();
                stack.popPose();

                stack.pushPose();
                stack.translate(X, Y, Z);
                stack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
                stack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 90f));
                stack.mulPose(Axis.ZP.rotationDegrees(90));
                stack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(un)));
                stack.translate(width, 0, 0);
                mat = stack.last().pose();
                builder.vertex(mat, 0, 0, 0).color(rCol, gCol, bCol, alpha).endVertex();
                stack.popPose();

                stack.pushPose();
                stack.translate(X, Y, Z);
                stack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
                stack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 90f));
                stack.mulPose(Axis.ZP.rotationDegrees(90));
                stack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(u)));
                stack.translate(width, 0, 0);
                mat = stack.last().pose();
                builder.vertex(mat, 0, 0, 0).color(rCol, gCol, bCol, alpha).endVertex();
                stack.popPose();

                stack.pushPose();
                stack.translate(X, Y, Z);
                stack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(-yaw)));
                stack.mulPose(Axis.ZP.rotationDegrees((float) Math.toDegrees(-pitch) - 90f));
                stack.mulPose(Axis.ZP.rotationDegrees(90));
                stack.mulPose(Axis.YP.rotationDegrees((float) Math.toDegrees(un)));
                stack.translate(width, 0, 0);
                mat = stack.last().pose();
                builder.vertex(mat, 0, 0, 0).color(rCol, gCol, bCol, alpha).endVertex();
                stack.popPose();

                stack.pushPose();
                mat = stack.last().pose();
                builder.vertex(mat, 0, 0, 0).color(rCol, gCol, bCol, alpha).endVertex();
                builder.vertex(mat, 0, 0, 0).color(rCol, gCol, bCol, alpha).endVertex();
                stack.popPose();
            }
        }

        stack.popPose();
    }
}