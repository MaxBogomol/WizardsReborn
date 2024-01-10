package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AirFlowEffectSpellPuchPacket {
    private static float velX, velY, velZ;
    private static float factor;

    public AirFlowEffectSpellPuchPacket(float velX, float velY, float velZ) {
        this.velX = velX;
        this.velY = velY;
        this.velZ = velZ;
        this.factor = factor;
    }

    public static AirFlowEffectSpellPuchPacket decode(FriendlyByteBuf buf) {
        return new AirFlowEffectSpellPuchPacket(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeFloat(velX);
        buf.writeFloat(velY);
        buf.writeFloat(velZ);
    }

    public static void handle(AirFlowEffectSpellPuchPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Player player = WizardsReborn.proxy.getPlayer();
                    Vec3 vel = new Vec3(velX, velY, velZ);
                    if (player.isFallFlying()) {
                        vel = vel.scale(0.65f);
                    }
                    player.push(vel.x(), vel.y(), vel.z());

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}