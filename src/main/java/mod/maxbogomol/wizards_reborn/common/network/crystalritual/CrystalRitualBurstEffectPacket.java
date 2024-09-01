package mod.maxbogomol.wizards_reborn.common.network.crystalritual;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class CrystalRitualBurstEffectPacket {
    private final CompoundTag tag;
    private static final Random random = new Random();

    public CrystalRitualBurstEffectPacket(CompoundTag tag) {
        this.tag = tag;
    }

    public static CrystalRitualBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new CrystalRitualBurstEffectPacket(buf.readNbt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(tag);
    }

    public static void handle(CrystalRitualBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();

                    for (int i = 0; i < msg.tag.size(); i++) {
                        for (int ii = 0; ii < 20; ii++) {
                            CompoundTag pos = msg.tag.getCompound(String.valueOf(i));
                            ParticleBuilder.create(FluffyFurParticles.SPARKLE)
                                    .setColorData(ColorParticleData.create(random.nextFloat(), random.nextFloat(), random.nextFloat()).build())
                                    .setTransparencyData(GenericParticleData.create(0.3f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.25f, 0).build())
                                    .randomSpin(0.5f)
                                    .setLifetime(40)
                                    .randomVelocity(0.0016f, 0.035f, 0.0016f)
                                    .randomOffset(0.25f)
                                    .spawn(world, pos.getFloat("x") + 0.5F, pos.getFloat("y") + 1.3F, pos.getFloat("z") + 0.5F);
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
