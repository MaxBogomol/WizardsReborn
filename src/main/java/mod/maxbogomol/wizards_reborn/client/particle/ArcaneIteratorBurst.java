package mod.maxbogomol.wizards_reborn.client.particle;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;

import java.util.Random;

public class ArcaneIteratorBurst {
    public Level level;
    public float x, y, z;
    public float endX, endY, endZ;
    public float velX, velY, velZ;
    public float speed;
    public float upTicks, endTick;
    public float R, G, B;
    public float tick;
    public boolean up;
    public boolean end;

    public Random random = new Random();

    public ArcaneIteratorBurst(Level level, float x, float y, float z, float endX, float endY, float endZ, float speed, float upTicks, float endTick, float R, float G, float B) {
        this.level = level;
        this.x = x;
        this.y = y;
        this.z = z;
        this.endX = endX;
        this.endY = endY;
        this.endZ = endZ;
        this.speed = speed;
        this.upTicks = upTicks;
        this.endTick = endTick;
        this.R = R;
        this.G = G;
        this.B = B;

        double yaw = random.nextDouble() * Math.PI * 2;
        double pitch = random.nextDouble() * Math.PI * 2;

        this.velX = (float) (Math.sin(pitch) * Math.cos(yaw) * speed);
        this.velY = (float) (Math.cos(pitch) * speed);
        this.velZ = (float) (Math.sin(pitch) * Math.sin(yaw) * speed);

        this.end = false;

        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                .setColorData(ColorParticleData.create(R, G, B).build())
                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                .setScaleData(GenericParticleData.create(0.4f, 0).build())
                .setSpinData(SpinParticleData.create().randomSpin(0.35f).build())
                .setLifetime(20)
                .randomVelocity(0.025f)
                .repeat(level, x, y, z, 4);
    }

    public void tick() {
        x = x + velX;
        y = y + velY;
        z = z + velZ;
        tick++;

        if (tick > upTicks && !up) {
            speed = speed * 2F;
            up = true;

            double dX = x - endX;
            double dY = y - endY;
            double dZ = z - endZ;

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            velX = (float) (Math.sin(pitch) * Math.cos(yaw) * speed);
            velY = (float) (Math.cos(pitch) * speed);
            velZ = (float) (Math.sin(pitch) * Math.sin(yaw) * speed);
        }

        float distance = (float) Math.sqrt(Math.pow(x - endX, 2) + Math.pow(y - endY, 2) + Math.pow(z - endZ, 2));
        if (distance < 0.25F || tick > endTick) {
            end = true;
        }

        for (int i = 0; i < 4; i++) {
            float X = Mth.lerp(i / 4F, x, x + velX);
            float Y = Mth.lerp(i / 4F, y, y + velY);
            float Z = Mth.lerp(i / 4F, z, z + velZ);
            if (random.nextFloat() < 0.25) {
                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create(R, G, B).build())
                        .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                        .setScaleData(GenericParticleData.create(0.3f, 0).build())
                        .setLifetime(20)
                        .randomVelocity(0.0085f)
                        .spawn(level, X, Y, Z);
            }
            if (random.nextFloat() < 0.05) {
                ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                        .setColorData(ColorParticleData.create(R, G, B).build())
                        .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                        .setScaleData(GenericParticleData.create(0.1f, 0).build())
                        .setSpinData(SpinParticleData.create().randomSpin(0.5f).build())
                        .setLifetime(30)
                        .randomVelocity(0.0125f)
                        .spawn(level, X, Y, Z);
            }
        }
    }
}
