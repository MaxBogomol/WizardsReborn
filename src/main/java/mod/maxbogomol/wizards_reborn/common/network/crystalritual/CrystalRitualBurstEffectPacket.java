package mod.maxbogomol.wizards_reborn.common.network.crystalritual;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
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
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < msg.tag.size(); i++) {
                        for (int ii = 0; ii < 20; ii++) {
                            CompoundTag pos = msg.tag.getCompound(String.valueOf(i));

                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 300), (random.nextDouble() / 15), ((random.nextDouble() - 0.5D) / 300))
                                    .setAlpha(0.3f, 0).setScale(0.25f, 0)
                                    .setColor(random.nextFloat(), random.nextFloat(), random.nextFloat())
                                    .setLifetime(40)
                                    .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(world, pos.getFloat("x") + 0.5F + ((random.nextDouble() - 0.5D) / 2), pos.getFloat("y") + 1.3F + ((random.nextDouble() - 0.5D) / 2), pos.getFloat("z") + 0.5F + ((random.nextDouble() - 0.5D) / 2));
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
