package mod.maxbogomol.wizards_reborn.common.network.crystalritual;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class CrystalInfusionBurstEffectPacket {
    private final BlockPos pos;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public CrystalInfusionBurstEffectPacket(BlockPos pos, float colorR, float colorG, float colorB) {
        this.pos = pos;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static CrystalInfusionBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new CrystalInfusionBurstEffectPacket(buf.readBlockPos(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(CrystalInfusionBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 15; i++) {
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 6), ((random.nextDouble() - 0.5D) / 8) + 0.2D, ((random.nextDouble() - 0.5D) / 6))
                                .setAlpha(0.4f, 0).setScale(0.2f, 0)
                                .setColor(msg.colorR, msg.colorG, msg.colorB)
                                .setLifetime(60)
                                .enableGravity()
                                .spawn(world, msg.pos.getX() + 0.5f, msg.pos.getY() + 1.25f, msg.pos.getZ() + 0.5f);
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}