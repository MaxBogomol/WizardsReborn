package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class SpellProjectileRayEffectPacket {
    private static float posFromX;
    private static float posFromY;
    private static float posFromZ;

    private static float posToX;
    private static float posToY;
    private static float posToZ;

    private static float motionX;
    private static float motionY;
    private static float motionZ;

    private static float r;
    private static float g;
    private static float b;

    private static Random random = new Random();

    public SpellProjectileRayEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ, float motionX, float motionY, float motionZ, float r, float g, float b) {
        this.posFromX = posFromX;
        this.posFromY = posFromY;
        this.posFromZ = posFromZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;

        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static SpellProjectileRayEffectPacket decode(PacketBuffer buf) {
        return new SpellProjectileRayEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(PacketBuffer buf) {
        buf.writeFloat(posFromX);
        buf.writeFloat(posFromY);
        buf.writeFloat(posFromZ);

        buf.writeFloat(posToX);
        buf.writeFloat(posToY);
        buf.writeFloat(posToZ);

        buf.writeFloat(motionX);
        buf.writeFloat(motionY);
        buf.writeFloat(motionZ);

        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
    }

    public static void handle(SpellProjectileRayEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                ClientWorld world = Minecraft.getInstance().world;

                Vector3d pos = new Vector3d(posToX, posToY, posToZ);
                Vector3d norm = new Vector3d(motionX, motionY, motionZ).normalize().scale(0.025f);

                for (int i = 0; i < 10; i ++) {
                    double lerpX = MathHelper.lerp(i / 10.0f, posFromX, pos.x);
                    double lerpY = MathHelper.lerp(i / 10.0f, posFromY, pos.y);
                    double lerpZ = MathHelper.lerp(i / 10.0f, posFromZ, pos.z);

                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(-norm.x + ((random.nextDouble() - 0.5D) / 500), -norm.y + ((random.nextDouble() - 0.5D) / 500), -norm.z + ((random.nextDouble() - 0.5D) / 500))
                            .setAlpha(0.2f, 0).setScale(0.15f, 0)
                            .setColor(r, g, b)
                            .setLifetime(20)
                            .spawn(world, lerpX, lerpY, lerpZ);

                    if (random.nextFloat() < 0.1f) {
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(-norm.x + ((random.nextDouble() - 0.5D) / 250), -norm.y + ((random.nextDouble() - 0.5D) / 250), -norm.z + ((random.nextDouble() - 0.5D) / 250))
                                .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                .setColor(r, g, b)
                                .setLifetime(30)
                                .spawn(world, lerpX, lerpY, lerpZ);
                    }
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
