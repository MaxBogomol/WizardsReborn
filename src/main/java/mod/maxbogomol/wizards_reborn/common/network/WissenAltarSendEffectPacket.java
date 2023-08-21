package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class WissenAltarSendEffectPacket {
    private static BlockPos pos;
    private static Random random = new Random();

    public WissenAltarSendEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static WissenAltarSendEffectPacket decode(PacketBuffer buf) {
        return new WissenAltarSendEffectPacket(buf.readBlockPos());
    }

    public void encode(PacketBuffer buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(WissenAltarSendEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(() -> {
                ClientWorld world = Minecraft.getInstance().world;

                Particles.create(WizardsReborn.WISP_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 100), -(random.nextDouble() / 40), ((random.nextDouble() - 0.5D) / 100))
                        .setAlpha(0.35f, 0).setScale(0.05f, 0)
                        .setColor(0.466f, 0.643f, 0.815f)
                        .setLifetime(30)
                        .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.3125F, pos.getZ() + 0.5F);
                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 100), -(random.nextDouble() / 40), ((random.nextDouble() - 0.5D) / 100))
                        .setAlpha(0.35f, 0).setScale(0.05f, 0)
                        .setColor(0.466f, 0.643f, 0.815f)
                        .setLifetime(30)
                        .setSpin((0.2f * (float) ((random.nextDouble() - 0.5D) * 2)))
                        .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.3125F, pos.getZ() + 0.5F);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
