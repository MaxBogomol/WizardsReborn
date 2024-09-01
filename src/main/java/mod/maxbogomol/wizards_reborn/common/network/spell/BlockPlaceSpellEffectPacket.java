package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class BlockPlaceSpellEffectPacket {
    private final float X, Y, Z;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public BlockPlaceSpellEffectPacket(float X, float Y, float Z, float colorR, float colorG, float colorB) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static BlockPlaceSpellEffectPacket decode(FriendlyByteBuf buf) {
        return new BlockPlaceSpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(BlockPlaceSpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                            .setTransparencyData(GenericParticleData.create(0.2f, 0).build())
                            .setScaleData(GenericParticleData.create(0.2f, 0).build())
                            .randomSpin(0.25f)
                            .setLifetime(30)
                            .randomVelocity(0.015f)
                            .addVelocity(0, 0.06f, 0)
                            .flatRandomOffset(0.55f, 0.55f, 0.55f)
                            .repeat(world, msg.X, msg.Y, msg.Z, 20, 0.6f);

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}