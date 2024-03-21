package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
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
                    Level world = WizardsReborn.proxy.getWorld();

                    for (int i = 0; i < 20; i++) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(msg.velX + ((random.nextDouble() - 0.5D) / 30), msg.velY + ((random.nextDouble() - 0.5D) / 30), msg.velZ + ((random.nextDouble() - 0.5D) / 30))
                                .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                .setColor(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB())
                                .setLifetime(20)
                                .spawn(world, msg.posX + ((random.nextDouble() - 0.5D) / 3), msg.posY + ((random.nextDouble() - 0.5D) / 3), msg.posZ + ((random.nextDouble() - 0.5D) / 3));
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(msg.velX + ((random.nextDouble() - 0.5D) / 30), msg.velY + ((random.nextDouble() - 0.5D) / 30), msg.velZ + ((random.nextDouble() - 0.5D) / 30))
                                .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                .setColor(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB())
                                .setLifetime(20)
                                .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, msg.posX + ((random.nextDouble() - 0.5D) / 3), msg.posY + ((random.nextDouble() - 0.5D) / 3), msg.posZ + ((random.nextDouble() - 0.5D) / 3));
                    }

                    for (int i = 0; i < 15; i++) {
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                                .setAlpha(0.25f, 0).setScale(0.3f, 0)
                                .setColor(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB())
                                .setLifetime(30)
                                .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, msg.pos.getX() + 0.5F + ((random.nextDouble() - 0.5D) * 1.25), msg.pos.getY() + 0.5F + ((random.nextDouble() - 0.5D) * 1.25), msg.pos.getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 1.25));
                    }
                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}
