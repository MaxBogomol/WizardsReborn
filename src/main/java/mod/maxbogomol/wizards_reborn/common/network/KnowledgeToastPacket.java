package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import mod.maxbogomol.wizards_reborn.client.toast.KnowledgeToast;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class KnowledgeToastPacket {
    UUID uuid;
    Component id;
    boolean all;

    public KnowledgeToastPacket(UUID uuid, String id, boolean all) {
        this.uuid = uuid;
        this.id = Component.literal(id);
        this.all = all;
    }

    public KnowledgeToastPacket(UUID uuid, Component id, boolean all) {
        this.uuid = uuid;
        this.id = id;
        this.all = all;
    }

    public KnowledgeToastPacket(Player entity, String id, boolean all) {
        this.uuid = entity.getUUID();
        this.id = Component.literal(id);
        this.all = all;
    }

    public KnowledgeToastPacket(Player entity, Component id, boolean all) {
        this.uuid = entity.getUUID();
        this.id = id;
        this.all = all;
    }

    public static void encode(KnowledgeToastPacket object, FriendlyByteBuf buffer) {
        buffer.writeUUID(object.uuid);
        buffer.writeComponent(object.id);
        buffer.writeBoolean(object.all);
    }

    public static KnowledgeToastPacket decode(FriendlyByteBuf buffer) {
       return new KnowledgeToastPacket(buffer.readUUID(), buffer.readComponent(), buffer.readBoolean());
    }

    public static void handle(KnowledgeToastPacket packet, Supplier<NetworkEvent.Context> ctx) {
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
    public static void toast(KnowledgeToastPacket packet) {
        if (KnowledgeToast.instance == null) {
            KnowledgeToast.instance = new KnowledgeToast(packet.id);
        }

        KnowledgeToast.instance.id = packet.id;
        if (Minecraft.getInstance().getToasts().getToast(KnowledgeToast.class, KnowledgeToast.instance.getToken()) == null) {
            if (packet.all) {
                KnowledgeToast.instance.all = true;
                KnowledgeToast.instance.articles = true;
                KnowledgeToast.instance.count = Knowledges.getKnowledges().size();
            } else {
                KnowledgeToast.instance.all = false;
                Knowledge knowledge = Knowledges.getKnowledge(packet.id.getString());
                if (knowledge != null) {
                    KnowledgeToast.instance.articles = knowledge.getArticles();
                }
                KnowledgeToast.instance.count = 1;
            }
            Minecraft.getInstance().getToasts().addToast(KnowledgeToast.instance);
        } else {
            if (packet.all) {
                KnowledgeToast.instance.all = true;
                KnowledgeToast.instance.articles = true;
                KnowledgeToast.instance.count = KnowledgeToast.instance.count + Knowledges.getKnowledges().size();
            } else {
                KnowledgeToast.instance.count = KnowledgeToast.instance.count + 1;
            }
        }
    }
}