package mod.maxbogomol.wizards_reborn.client.particle;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
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

        for (int i = 0; i < 4; i++) {
            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                    .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                    .setAlpha(0.25f, 0).setScale(0.4f, 0)
                    .setColor(R, G, B)
                    .setLifetime(30)
                    .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(level, x, y, z);
        }
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
                Particles.create(WizardsReborn.WISP_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 60), ((random.nextDouble() - 0.5D) / 60), ((random.nextDouble() - 0.5D) / 60))
                        .setAlpha(0.25f, 0).setScale(0.3f, 0)
                        .setColor(R, G, B)
                        .setLifetime(20)
                        .spawn(level, X, Y, Z);
            }
            if (random.nextFloat() < 0.05) {
                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 40), ((random.nextDouble() - 0.5D) / 40), ((random.nextDouble() - 0.5D) / 40))
                        .setAlpha(0.25f, 0).setScale(0.1f, 0)
                        .setColor(R, G, B)
                        .setLifetime(30)
                        .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                        .spawn(level, X, Y, Z);
            }
        }
    }
}
