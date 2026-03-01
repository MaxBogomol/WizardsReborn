package mod.maxbogomol.wizards_reborn.common.network.entity;

import mod.maxbogomol.fluffy_fur.common.network.ClientPacket;
import mod.maxbogomol.wizards_reborn.common.gui.menu.SniffaloMenu;
import mod.maxbogomol.wizards_reborn.client.gui.screen.SniffaloScreen;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SniffaloScreenPacket extends ClientPacket {
    protected final int containerId;
    protected final int size;
    protected final int entityId;

    public SniffaloScreenPacket(int containerId, int size, int entityId) {
        this.containerId = containerId;
        this.size = size;
        this.entityId = entityId;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Minecraft minecraft = Minecraft.getInstance();

        Entity entity = minecraft.level.getEntity(entityId);
        if (entity instanceof SniffaloEntity sniffalo) {
            LocalPlayer localplayer = minecraft.player;
            SimpleContainer simplecontainer = new SimpleContainer(size);
            SniffaloMenu sniffaloMenu = new SniffaloMenu(containerId, localplayer.getInventory(), simplecontainer, minecraft.player, sniffalo);
            localplayer.containerMenu = sniffaloMenu;
            minecraft.setScreen(new SniffaloScreen(sniffaloMenu, localplayer.getInventory(), sniffalo));
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SniffaloScreenPacket.class, SniffaloScreenPacket::encode, SniffaloScreenPacket::decode, SniffaloScreenPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeByte(containerId);
        buf.writeVarInt(size);
        buf.writeInt(entityId);
    }

    public static SniffaloScreenPacket decode(FriendlyByteBuf buf) {
        return new SniffaloScreenPacket(buf.readByte(), buf.readVarInt(), buf.readInt());
    }
}
