package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class AltarOfDroughtBreakEffectPacket {
    private final BlockPos pos;
    private static final Random random = new Random();

    public AltarOfDroughtBreakEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static AltarOfDroughtBreakEffectPacket decode(FriendlyByteBuf buf) {
        return new AltarOfDroughtBreakEffectPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(AltarOfDroughtBreakEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 5; i++) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 5), ((random.nextDouble() - 0.5D) / 5), ((random.nextDouble() - 0.5D) / 5))
                                .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                .setColor(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB())
                                .setLifetime(20)
                                .spawn(world, msg.pos.getX() + 0.5F + ((random.nextDouble() - 0.5D) * 0.1), msg.pos.getY() + 0.5F + ((random.nextDouble() - 0.5D) * 0.1), msg.pos.getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 0.1));
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15), ((random.nextDouble() - 0.5D) / 15))
                                .setAlpha(0.25f, 0).setScale(0.2f, 0)
                                .setColor(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB())
                                .setLifetime(30)
                                .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, msg.pos.getX() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5), msg.pos.getY() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5), msg.pos.getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 0.5));
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
