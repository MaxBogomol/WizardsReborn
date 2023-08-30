package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;;

public class WissenAltarBurstEffectPacket {
    private static BlockPos pos;
    private static Random random = new Random();

    public WissenAltarBurstEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static WissenAltarBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenAltarBurstEffectPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(WissenAltarBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    ClientLevel world = Minecraft.getInstance().level;

                    for (int i = 0; i < 20; i++) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                              .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                              .setAlpha(0.125f, 0).setScale(0.3f, 0)
                              .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                              .setLifetime(20)
                              .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.3125F, pos.getZ() + 0.5F);
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                              .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                              .setAlpha(0.25f, 0).setScale(0.1f, 0)
                              .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                              .setLifetime(30)
                              .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                              .spawn(world, pos.getX() + 0.5F, pos.getY() + 1.3125F, pos.getZ() + 0.5F);
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
