package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class WissenSendEffectPacket {
    private static BlockPos posFrom;
    private static BlockPos posTo;
    private static Random random = new Random();

    public WissenSendEffectPacket(BlockPos posFrom, BlockPos posTo) {
        this.posFrom = posFrom;
        this.posTo = posTo;
    }

    public static WissenSendEffectPacket decode(PacketBuffer buf) {
        return new WissenSendEffectPacket(buf.readBlockPos(), buf.readBlockPos());
    }

    public void encode(PacketBuffer buf) {
        buf.writeBlockPos(posFrom);
        buf.writeBlockPos(posTo);
    }

    public static void handle(WissenSendEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                ClientWorld world = Minecraft.getInstance().world;

                int particlePerBlock = 10;
                double distance = Math.sqrt((posTo.getX() - posFrom.getX()) * (posTo.getX() - posFrom.getX()) + (posTo.getY() - posFrom.getY()) * (posTo.getY() - posFrom.getY()) + (posTo.getZ() - posFrom.getZ()) * (posTo.getZ() - posFrom.getZ()));
                int blocks = (int) Math.floor(distance);

                double dX = posFrom.getX() - posTo.getX();
                double dY = posFrom.getY() - posTo.getY();
                double dZ = posFrom.getZ() - posTo.getZ();

                double yaw = Math.atan2(dZ, dX);
                double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                double X = Math.sin(pitch) * Math.cos(yaw) * 0.1f;
                double Y = Math.sin(pitch) * Math.sin(yaw) * 0.1f;
                double Z = Math.cos(pitch) * 0.1f;

                float x = (float) (dX / (particlePerBlock * blocks));
                float y = (float) (dY / (particlePerBlock * blocks));
                float z = (float) (dZ / (particlePerBlock * blocks));

                Vector3d vector = new Vector3d(X, Z, Y);

                blocks = blocks - 1;
                if (blocks <= 0) {
                    blocks = 1;
                }

                for (int i = 0; i < particlePerBlock * blocks; i++) {
                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 50) + vector.getX(), ((random.nextDouble() - 0.5D) / 50)  + vector.getY(), ((random.nextDouble() - 0.5D) / 50)  + vector.getZ())
                            .setAlpha(0.3f, 0).setScale(0.25f, 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(20)
                            .spawn(world, posFrom.getX() + 0.5F - (x * i), posFrom.getY() + 0.5F - (y * i), posFrom.getZ() + 0.5F - (z * i));
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 50) + vector.getX(), ((random.nextDouble() - 0.5D) / 50)  + vector.getY(), ((random.nextDouble() - 0.5D) / 50)  + vector.getZ())
                            .setAlpha(0.25f, 0).setScale(0.3f, 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(world, posFrom.getX() + 0.5F - (x * i), posFrom.getY() + 0.5F - (y * i), posFrom.getZ() + 0.5F - (z * i));
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
