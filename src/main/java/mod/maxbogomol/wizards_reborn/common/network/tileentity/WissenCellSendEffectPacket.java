package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class WissenCellSendEffectPacket {
    private static BlockPos pos;
    private static Random random = new Random();

    public WissenCellSendEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static WissenCellSendEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenCellSendEffectPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(WissenCellSendEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    Particles.create(WizardsReborn.WISP_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 100), -(random.nextDouble() / 40), ((random.nextDouble() - 0.5D) / 100))
                            .setAlpha(0.35f, 0).setScale(0.05f, 0)
                            .setColor(0.466f, 0.643f, 0.815f, 0.5f, 0.5f, 0)
                            .setLifetime(30)
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 0.9375F, pos.getZ() + 0.5F);
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 100), -(random.nextDouble() / 40), ((random.nextDouble() - 0.5D) / 100))
                            .setAlpha(0.35f, 0).setScale(0.05f, 0)
                            .setColor(0.466f, 0.643f, 0.815f)
                            .setLifetime(30)
                            .setSpin((0.2f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(world, pos.getX() + 0.5F, pos.getY() + 0.9375F, pos.getZ() + 0.5F);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
