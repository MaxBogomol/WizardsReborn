package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class WissenAltarSendEffectPacket {
    private final BlockPos pos;
    private static final Random random = new Random();

    public WissenAltarSendEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static WissenAltarSendEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenAltarSendEffectPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(WissenAltarSendEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();
                    ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0).build())
                            .setLifetime(20)
                            .addVelocity(((random.nextDouble() - 0.5D) / 100), -(random.nextDouble() / 40), ((random.nextDouble() - 0.5D) / 100))
                            .spawn(world, msg.pos.getX() + 0.5F, msg.pos.getY() + 1.3125F, msg.pos.getZ() + 0.5F);
                    boolean square = random.nextFloat() < 0.3f;
                    float i = square ? 0.5f : 1f;
                    ParticleBuilder.create(square ? FluffyFur.SQUARE_PARTICLE : FluffyFur.SPARKLE_PARTICLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.35f, 0).build())
                            .setScaleData(GenericParticleData.create(0.025f * i, 0.05f * i, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .randomSpin(0.2f)
                            .setLifetime(30)
                            .addVelocity(((random.nextDouble() - 0.5D) / 100), -(random.nextDouble() / 40), ((random.nextDouble() - 0.5D) / 100))
                            .spawn(world, msg.pos.getX() + 0.5F, msg.pos.getY() + 1.3125F, msg.pos.getZ() + 0.5F);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
