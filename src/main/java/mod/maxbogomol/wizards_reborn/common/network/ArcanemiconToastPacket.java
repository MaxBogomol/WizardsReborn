package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.toast.ArcanemiconToast;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ArcanemiconToastPacket {
    private final UUID uuid;

    public ArcanemiconToastPacket(UUID uuid) {
        this.uuid = uuid;
    }

    public ArcanemiconToastPacket(Player entity) {
        this.uuid = entity.getUUID();
    }

    public static void encode(ArcanemiconToastPacket object, FriendlyByteBuf buffer) {
        buffer.writeUUID(object.uuid);
    }

    public static ArcanemiconToastPacket decode(FriendlyByteBuf buffer) {
       return new ArcanemiconToastPacket(buffer.readUUID());
    }

    public static void handle(ArcanemiconToastPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            assert ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT;

            Level world = WizardsReborn.proxy.getWorld();
            Player player = world.getPlayerByUUID(packet.uuid);
            if (player != null) {
                toast(packet);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public static void toast(ArcanemiconToastPacket packet) {
        Minecraft.getInstance().getToasts().addToast(new ArcanemiconToast());
    }
}