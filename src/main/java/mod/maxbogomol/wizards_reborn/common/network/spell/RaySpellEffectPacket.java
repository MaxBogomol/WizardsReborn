package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.SpinParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class RaySpellEffectPacket {
    private final float posFromX;
    private final float posFromY;
    private final float posFromZ;

    private final float posToX;
    private final float posToY;
    private final float posToZ;

    private final float colorR, colorG, colorB;

    private final boolean burst;

    private static final Random random = new Random();

    public RaySpellEffectPacket(float posFromX, float posFromY, float posFromZ, float posToX, float posToY, float posToZ, float colorR, float colorG, float colorB, boolean burst) {
        this.posFromX = posFromX;
        this.posFromY = posFromY;
        this.posFromZ = posFromZ;

        this.posToX = posToX;
        this.posToY = posToY;
        this.posToZ = posToZ;

        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;

        this.burst = burst;
    }

    public static RaySpellEffectPacket decode(FriendlyByteBuf buf) {
        return new RaySpellEffectPacket(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readBoolean());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(posFromX);
        buf.writeFloat(posFromY);
        buf.writeFloat(posFromZ);

        buf.writeFloat(posToX);
        buf.writeFloat(posToY);
        buf.writeFloat(posToZ);

        buf.writeFloat(colorR);
        buf.writeFloat(colorG);
        buf.writeFloat(colorB);

        buf.writeBoolean(burst);
    }

    public static void handle(RaySpellEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level level = WizardsReborn.proxy.getLevel();

                    if (random.nextFloat() < 0.2f) {
                        ParticleBuilder.create(FluffyFurParticles.WISP)
                                .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                .setTransparencyData(GenericParticleData.create(0.2f, 0).build())
                                .setScaleData(GenericParticleData.create(0.15f, 0).build())
                                .setLifetime(25)
                                .randomVelocity(0.025f)
                                .spawn(level, msg.posFromX, msg.posFromY, msg.posFromZ);
                    }

                    Vec3 pos = new Vec3(msg.posToX, msg.posToY, msg.posToZ);
                    for (int i = 0; i < 10; i++) {
                        if (random.nextFloat() < 0.1f) {
                            double lerp = random.nextDouble();
                            double lerpX = Mth.lerp(lerp, msg.posFromX, pos.x);
                            double lerpY = Mth.lerp(lerp, msg.posFromY, pos.y);
                            double lerpZ = Mth.lerp(lerp, msg.posFromZ, pos.z);

                            ParticleBuilder.create(FluffyFurParticles.WISP)
                                    .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                    .setTransparencyData(GenericParticleData.create(0.2f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.15f, 0).build())
                                    .setLifetime(10)
                                    .randomVelocity(0.015f)
                                    .spawn(level, lerpX, lerpY, lerpZ);

                            if (random.nextFloat() < 0.1f) {
                                boolean square = random.nextBoolean();
                                ParticleBuilder.create(square ? FluffyFurParticles.SQUARE : FluffyFurParticles.SPARKLE)
                                        .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                        .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                                        .setScaleData(GenericParticleData.create(0, square ? 0.1f : 0.2f, 0).setEasing(Easing.SINE_IN_OUT).build())
                                        .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.05f).build())
                                        .setLifetime(10)
                                        .randomVelocity(0.015f)
                                        .spawn(level, lerpX, lerpY, lerpZ);
                            }
                        }
                    }

                    if (msg.burst) {
                        ParticleBuilder.create(FluffyFurParticles.WISP)
                                .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                                .setScaleData(GenericParticleData.create(0.2f, 0).build())
                                .setLifetime(40)
                                .randomVelocity(0.035f)
                                .repeat(level, msg.posToX, msg.posToY, msg.posToZ, 5, 0.1f);
                        ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                                .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                                .setScaleData(GenericParticleData.create(0, 0.075f, 0).setEasing(Easing.SINE_IN_OUT).build())
                                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                                .setLifetime(50)
                                .randomVelocity(0.035f)
                                .repeat(level, msg.posToX, msg.posToY, msg.posToZ, 4, 0.1f);
                        ParticleBuilder.create(FluffyFurParticles.SQUARE)
                                .setColorData(ColorParticleData.create(msg.colorR, msg.colorG, msg.colorB).build())
                                .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                                .setScaleData(GenericParticleData.create(0, 0.05f, 0).setEasing(Easing.SINE_IN_OUT).build())
                                .setSpinData(SpinParticleData.create().randomOffset().randomSpin(0.5f).build())
                                .setLifetime(50)
                                .randomVelocity(0.035f)
                                .repeat(level, msg.posToX, msg.posToY, msg.posToZ, 1, 0.1f);
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}