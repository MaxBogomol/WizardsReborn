package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class WissenDustBurstEffectPacket {
    private final BlockPos pos;

    private final float posX;
    private final float posY;
    private final float posZ;

    private final float velX;
    private final float velY;
    private final float velZ;

    private static final Random random = new Random();

    public WissenDustBurstEffectPacket(BlockPos pos, float posX, float posY, float posZ, float velX, float velY, float velZ) {
        this.pos = pos;

        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;

        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
    }

    public static WissenDustBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new WissenDustBurstEffectPacket(buf.readBlockPos(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);

        buf.writeFloat(posX);
        buf.writeFloat(posY);
        buf.writeFloat(posZ);

        buf.writeFloat(velX);
        buf.writeFloat(velY);
        buf.writeFloat(velZ);
    }

    public static void handle(WissenDustBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();
                    ParticleBuilder.create(FluffyFurParticles.WISP)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                            .setScaleData(GenericParticleData.create(0.2f, 0).build())
                            .setLifetime(20)
                            .randomVelocity(0.015f)
                            .randomOffset(0.15f)
                            .repeat(world, msg.posX, msg.posY, msg.posZ, 20);
                    ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                            .setScaleData(GenericParticleData.create(0.2f, 0).build())
                            .randomSpin(0.5f)
                            .setLifetime(20)
                            .randomVelocity(0.015f)
                            .randomOffset(0.15f)
                            .repeat(world, msg.posX, msg.posY, msg.posZ, 20);
                    ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                            .setColorData(ColorParticleData.create(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).build())
                            .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                            .setScaleData(GenericParticleData.create(0.3f, 0).build())
                            .randomSpin(0.5f)
                            .setLifetime(30)
                            .randomVelocity(0.015f)
                            .randomOffset(0.625f)
                            .repeat(world, msg.posX, msg.posY, msg.posZ, 15);
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
