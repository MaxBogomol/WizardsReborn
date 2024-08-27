package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class ArcaneIteratorBurstEffectPacket {
    private final CompoundTag tag;
    private static final Random random = new Random();

    public ArcaneIteratorBurstEffectPacket(CompoundTag tag) {
        this.tag = tag;
    }

    public static ArcaneIteratorBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new ArcaneIteratorBurstEffectPacket(buf.readNbt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(tag);
    }

    public static void handle(ArcaneIteratorBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getLevel();

                    for (int i = 0; i < msg.tag.size(); i++) {
                        for (int ii = 0; ii < 20; ii++) {
                            CompoundTag pos = msg.tag.getCompound(String.valueOf(i));
                            ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                                    .setColorData(ColorParticleData.create(random.nextFloat(), random.nextFloat(), random.nextFloat()).build())
                                    .setTransparencyData(GenericParticleData.create(0.125f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.3f, 0).build())
                                    .setLifetime(20)
                                    .randomVelocity(0.025f)
                                    .spawn(world, pos.getFloat("x") + 0.5F, pos.getFloat("y") + 1.3F, pos.getFloat("z") + 0.5F);
                            ParticleBuilder.create(FluffyFur.WISP_PARTICLE)
                                    .setColorData(ColorParticleData.create(random.nextFloat(), random.nextFloat(), random.nextFloat()).build())
                                    .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                                    .setScaleData(GenericParticleData.create(0.1f, 0).build())
                                    .randomSpin(0.5f)
                                    .setLifetime(30)
                                    .randomVelocity(0.025f)
                                    .spawn(world, pos.getFloat("x") + 0.5F, pos.getFloat("y") + 1.3F, pos.getFloat("z") + 0.5F);
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
