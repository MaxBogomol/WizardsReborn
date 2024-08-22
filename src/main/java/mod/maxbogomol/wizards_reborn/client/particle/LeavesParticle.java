package mod.maxbogomol.wizards_reborn.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.GenericParticleOptions;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;

import javax.annotation.Nonnull;

public class LeavesParticle extends GenericParticle {

    private float rotSpeed;
    private final float particleRandom;
    private final float spinAcceleration;

    public LeavesParticle(ClientLevel world, GenericParticleOptions data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
        this.rotSpeed = (float)Math.toRadians(this.random.nextBoolean() ? -30.0D : 30.0D);
        this.particleRandom = this.random.nextFloat();
        this.spinAcceleration = (float)Math.toRadians(this.random.nextBoolean() ? -5.0D : 5.0D);
        this.lifetime = 300;
        this.gravity = 7.5E-4F;
        float f = this.random.nextBoolean() ? 0.05F : 0.075F;
        this.quadSize = f;
        this.setSize(f, f);
        this.friction = 1.0F;
        this.roll = (float) (this.random.nextFloat() * Math.PI);
        this.oRoll = this.roll;
    }

    @Nonnull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        }

        if (!this.removed) {
            float f = (float)(300 - this.lifetime);
            float f1 = Math.min(f / 300.0F, 1.0F);
            double d0 = Math.cos(Math.toRadians((this.particleRandom * 60.0F))) * 2.0D * Math.pow(f1, 1.25D);
            double d1 = Math.sin(Math.toRadians((this.particleRandom * 60.0F))) * 2.0D * Math.pow(f1, 1.25D);
            this.xd += d0 * (double)0.0025F;
            this.zd += d1 * (double)0.0025F;
            this.yd -= this.gravity;
            this.rotSpeed += this.spinAcceleration / 20.0F;
            this.oRoll = this.roll;
            this.roll += this.rotSpeed / 20.0F;
            this.move(this.xd, this.yd, this.zd);
            if (this.onGround || this.lifetime < 299 && (this.xd == 0.0D || this.zd == 0.0D)) {
                this.remove();
            }

            if (!this.removed) {
                this.xd *= this.friction;
                this.yd *= this.friction;
                this.zd *= this.friction;
            }
        }
    }
    @Override
    public void render(VertexConsumer vertexConsumer, Camera camera, float partialTicks) {
        super.render(vertexConsumer, camera, partialTicks);
    }

    protected int getLightColor(float partialTicks) {
        return super.getLightColor(partialTicks);
    }
}