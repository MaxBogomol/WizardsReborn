package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

;

public class WissenTranslatorSendEffectPacket {
    private static BlockPos pos;
    private static Random random = new Random();

    public WissenTranslatorSendEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static WissenTranslatorSendEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenTranslatorSendEffectPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(WissenTranslatorSendEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 15; i++) {
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                                .setAlpha(0.25f, 0).setScale(0.15f, 0)
                                .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                                .setLifetime(30)
                                .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, pos.getX() + 0.5F + ((random.nextDouble() - 0.5D) * 0.75), pos.getY() + 0.5F + ((random.nextDouble() - 0.5D) * 0.75), pos.getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 0.75));
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
