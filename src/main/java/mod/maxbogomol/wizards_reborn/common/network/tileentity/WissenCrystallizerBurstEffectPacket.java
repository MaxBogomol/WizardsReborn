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

public class WissenCrystallizerBurstEffectPacket {
    private final BlockPos pos;
    private static final Random random = new Random();

    public WissenCrystallizerBurstEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static WissenCrystallizerBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenCrystallizerBurstEffectPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(WissenCrystallizerBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();
                    ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                            .setScaleData(GenericParticleData.create(0.3f, 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.025f)
                            .repeat(world, msg.pos.getX() + 0.5F, msg.pos.getY() + 1.125F, msg.pos.getZ() + 0.5F, 20);
                    ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .randomSpin(0.5f)
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .repeat(world, msg.pos.getX() + 0.5F, msg.pos.getY() + 1.125F, msg.pos.getZ() + 0.5F, 10);
                    ParticleBuilder.create(FluffyFur.SQUARE_PARTICLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                            .randomSpin(0.5f)
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .repeat(world, msg.pos.getX() + 0.5F, msg.pos.getY() + 1.125F, msg.pos.getZ() + 0.5F, 10);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
