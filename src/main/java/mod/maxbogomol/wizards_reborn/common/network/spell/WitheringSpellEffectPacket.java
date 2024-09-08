package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.LightParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.awt.*;
import java.util.Random;
import java.util.function.Supplier;

public class WitheringSpellEffectPacket {
    private final float X, Y, Z;
    private final float colorR, colorG, colorB;

    private static final Random random = new Random();

    public WitheringSpellEffectPacket(float X, float Y, float Z, float colorR, float colorG, float colorB) {
        this.X = X;
        this.Y = Y;
        this.Z = Z;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
    }

    public static WitheringSpellEffectPacket decode(FriendlyByteBuf buf) {
        return new WitheringSpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);
    }

    public static void handle(WitheringSpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level level = WizardsReborn.proxy.getLevel();
                    ParticleBuilder.create(FluffyFurParticles.SMOKE)
                            .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                            .setTransparencyData(GenericParticleData.create(0.15f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 0.5f).build())
                            .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                            .setLifetime(60)
                            .randomVelocity(0.007f)
                            .randomOffset(0.25f)
                            .repeat(level, msg.X, msg.Y, msg.Z, 10, 0.3f);
                    ParticleBuilder.create(FluffyFurParticles.SMOKE)
                            .setRenderType(FluffyFurRenderTypes.DELAYED_PARTICLE)
                            .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.1f, 1.5f).build())
                            .setLightData(LightParticleData.DEFAULT)
                            .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                            .setLifetime(80)
                            .randomVelocity(0.0085f)
                            .randomOffset(0.25f)
                            .repeat(level, msg.X, msg.Y, msg.Z, 10, 0.3f);
                    ParticleBuilder.create(FluffyFurParticles.SMOKE)
                            .setRenderType(FluffyFurRenderTypes.DELAYED_PARTICLE)
                            .setColorData(ColorParticleData.create(Color.BLACK).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0f, 1f).build())
                            .setLightData(LightParticleData.DEFAULT)
                            .setSpinData(SpinParticleData.create().randomSpin(0.1f).build())
                            .setLifetime(80)
                            .randomVelocity(0.0085f)
                            .randomOffset(0.25f)
                            .repeat(level, msg.X, msg.Y, msg.Z, 10, 0.2f);

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}