package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.client.gui.container.SniffaloContainer;
import mod.maxbogomol.wizards_reborn.client.gui.screen.SniffaloScreen;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SniffaloScreenPacket {
    private final int containerId;
    private final int size;
    private final int entityId;

    public SniffaloScreenPacket(int pContainerId, int pSize, int pEntityId) {
        this.containerId = pContainerId;
        this.size = pSize;
        this.entityId = pEntityId;
    }

    public SniffaloScreenPacket(FriendlyByteBuf pBuffer) {
        this.containerId = pBuffer.readUnsignedByte();
        this.size = pBuffer.readVarInt();
        this.entityId = pBuffer.readInt();
    }

    public static SniffaloScreenPacket decode(FriendlyByteBuf buf) {
        return new SniffaloScreenPacket(buf.readByte(), buf.readVarInt(), buf.readInt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(this.containerId);
        buf.writeVarInt(this.size);
        buf.writeInt(this.entityId);
    }

    public static void handle(SniffaloScreenPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Minecraft mc = Minecraft.getInstance();

                    Entity entity = mc.level.getEntity(msg.getEntityId());
                    if (entity instanceof SniffaloEntity sniffalo) {
                        LocalPlayer localplayer = mc.player;
                        SimpleContainer simplecontainer = new SimpleContainer(msg.getSize());
                        SniffaloContainer sniffaloContainer = new SniffaloContainer(msg.getContainerId(), localplayer.getInventory(), simplecontainer, mc.player, sniffalo);
                        localplayer.containerMenu = sniffaloContainer;
                        mc.setScreen(new SniffaloScreen(sniffaloContainer, localplayer.getInventory(), sniffalo));
                    }
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }

    public int getContainerId() {
        return this.containerId;
    }

    public int getSize() {
        return this.size;
    }

    public int getEntityId() {
        return this.entityId;
    }
}
