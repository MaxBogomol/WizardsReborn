package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class ArcaneIteratorBurstEffectPacket {
    private static CompoundTag tag;
    private static Random random = new Random();

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
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < tag.size(); i++) {
                        for (int ii = 0; ii < 20; ii++) {
                            CompoundTag pos = tag.getCompound(String.valueOf(i));

                            Particles.create(WizardsReborn.WISP_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                                    .setAlpha(0.125f, 0).setScale(0.3f, 0)
                                    .setColor(random.nextFloat(), random.nextFloat(), random.nextFloat())
                                    .setLifetime(20)
                                    .spawn(world, pos.getFloat("x") + 0.5F, pos.getFloat("y") + 1.3F, pos.getFloat("z") + 0.5F);
                            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                    .addVelocity(((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20), ((random.nextDouble() - 0.5D) / 20))
                                    .setAlpha(0.25f, 0).setScale(0.1f, 0)
                                    .setColor(random.nextFloat(), random.nextFloat(), random.nextFloat())
                                    .setLifetime(30)
                                    .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                    .spawn(world, pos.getFloat("x") + 0.5F, pos.getFloat("y") + 1.3F, pos.getFloat("z") + 0.5F);
                        }
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
