package mod.maxbogomol.wizards_reborn.api.knowledge;

import mod.maxbogomol.wizards_reborn.common.capability.KnowledgeProvider;
import mod.maxbogomol.wizards_reborn.common.network.KnowledgeUpdatePacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.concurrent.atomic.AtomicBoolean;

public class KnowledgeUtils {
    public static boolean isKnowledge(Entity entity, Knowledge knowledge) {
        if (!(entity instanceof PlayerEntity)) return false;
        AtomicBoolean isKnow = new AtomicBoolean(false);
        entity.getCapability(KnowledgeProvider.CAPABILITY, null).ifPresent((k) -> {
            isKnow.set(k.isKnowledge(knowledge));
        });
        return isKnow.get();
    }

    public static void addKnowledge(Entity entity, Knowledge knowledge) {
        if (!(entity instanceof PlayerEntity)) return;
        entity.getCapability(KnowledgeProvider.CAPABILITY, null).ifPresent((k) -> {
            if (k.isKnowledge(knowledge)) return;
            k.addKnowledge(knowledge);

            PacketHandler.sendToServer(new KnowledgeUpdatePacket((PlayerEntity) entity));
        });
    }

    public static void removeKnowledge(Entity entity, Knowledge knowledge) {
        if (!(entity instanceof PlayerEntity)) return;
        entity.getCapability(KnowledgeProvider.CAPABILITY, null).ifPresent((k) -> {
            if (!k.isKnowledge(knowledge)) return;
            k.removeKnowledge(knowledge);

            PacketHandler.sendToServer(new KnowledgeUpdatePacket((PlayerEntity) entity));
        });
    }

    public static void addAllKnowledge(Entity entity) {
        if (!(entity instanceof PlayerEntity)) return;
        entity.getCapability(KnowledgeProvider.CAPABILITY, null).ifPresent((k) -> {
            k.addAllKnowledge();

            PacketHandler.sendToServer(new KnowledgeUpdatePacket((PlayerEntity) entity));
        });
    }

    public static void removeAllKnowledge(Entity entity) {
        if (!(entity instanceof PlayerEntity)) return;
        entity.getCapability(KnowledgeProvider.CAPABILITY, null).ifPresent((k) -> {
            k.removeAllKnowledge();

            PacketHandler.sendToServer(new KnowledgeUpdatePacket((PlayerEntity) entity));
        });
    }
}
