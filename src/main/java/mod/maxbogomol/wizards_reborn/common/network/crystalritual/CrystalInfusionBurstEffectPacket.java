package mod.maxbogomol.wizards_reborn.common.network.crystalritual;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
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
                    ParticleBuilder.create(FluffyFur.SPARKLE_PARTICLE)
                            .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                            .setTransparencyData(GenericParticleData.create(0.4f, 0).build())
                            .setScaleData(GenericParticleData.create(0.2f, 0).build())
                            .setLifetime(60)
                            .setGravity(1)
                            .randomVelocity(0.085f)
                            .addVelocity(0, 0.2f, 0)
                            .repeat(world, msg.pos.getX() + 0.5f, msg.pos.getY() + 1.25f, msg.pos.getZ() + 0.5f, 15);

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}