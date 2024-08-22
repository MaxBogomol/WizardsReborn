package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class AltarOfDroughtBurstEffectPacket {
    private final BlockPos pos;
    private static final Random random = new Random();

    public AltarOfDroughtBurstEffectPacket(BlockPos pos) {
        this.pos = pos;
    }

    public static AltarOfDroughtBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new AltarOfDroughtBurstEffectPacket(buf.readBlockPos());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public static void handle(AltarOfDroughtBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();
                    ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                            .setScaleData(GenericParticleData.create(0.2f, 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.05f)
                            .repeat(world, msg.pos.getX() + 0.5F, msg.pos.getY() + 0.625F, msg.pos.getZ() + 0.5F, 20);
                    ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0).build())
                            .randomSpin(0.5f)
                            .setLifetime(30)
                            .randomVelocity(0.05f)
                            .repeat(world, msg.pos.getX() + 0.5F, msg.pos.getY() + 0.625F, msg.pos.getZ() + 0.5F, 20);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
