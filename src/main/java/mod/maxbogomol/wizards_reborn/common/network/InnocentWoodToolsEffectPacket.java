package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class InnocentWoodToolsEffectPacket {
    private final float posX;
    private final float posY;
    private final float posZ;

    private static final Random random = new Random();

    public InnocentWoodToolsEffectPacket(float posX, float posY, float posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
    }

    public static InnocentWoodToolsEffectPacket decode(FriendlyByteBuf buf) {
        return new InnocentWoodToolsEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);
    }

    public static void handle(InnocentWoodToolsEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 3; i++) {
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 10), ((random.nextDouble() - 0.5D) / 10), ((random.nextDouble() - 0.5D) / 10))
                                .setAlpha(0f, 0.5f).setScale(0.5f, 0f)
                                .setColor(0.694F, 0.274F, 0.309F)
                                .setLifetime(30)
                                .spawn(world, msg.posX + ((random.nextDouble() - 0.5D) / 4), msg.posY + ((random.nextDouble() - 0.5D) / 4), msg.posZ + ((random.nextDouble() - 0.5D) / 4));
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
