package mod.maxbogomol.wizards_reborn.api.knowledge;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.common.capability.IKnowledge;
import mod.maxbogomol.wizards_reborn.common.network.KnowledgeToastPacket;
import mod.maxbogomol.wizards_reborn.common.network.KnowledgeUpdatePacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.atomic.AtomicBoolean;

public class KnowledgeUtils {

    public static boolean isKnowledge(Entity entity, Knowledge knowledge) {
        if (!(entity instanceof Player)) return false;
        AtomicBoolean isKnow = new AtomicBoolean(false);
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((k) -> {
            isKnow.set(k.isKnowledge(knowledge));
        });
        return isKnow.get();
    }

    public static void addKnowledge(Entity entity, Knowledge knowledge) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((k) -> {
            if (k.isKnowledge(knowledge)) return;
            k.addKnowledge(knowledge);

            PacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
            PacketHandler.sendTo((Player) entity, new KnowledgeToastPacket((Player) entity, knowledge.getId(), false));
        });
    }

    public static void removeKnowledge(Entity entity, Knowledge knowledge) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((k) -> {
            if (!k.isKnowledge(knowledge)) return;
            k.removeKnowledge(knowledge);

            PacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static void addAllKnowledge(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((k) -> {
            k.addAllKnowledge();

            PacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
            PacketHandler.sendTo((Player) entity, new KnowledgeToastPacket((Player) entity, "", true));
        });
    }

    public static void removeAllKnowledge(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((k) -> {
            k.removeAllKnowledge();

            PacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static boolean isSpell(Entity entity, Spell spell) {
        if (!(entity instanceof Player)) return false;
        AtomicBoolean isKnow = new AtomicBoolean(false);
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            isKnow.set(s.isSpell(spell));
        });
        return isKnow.get();
    }

    public static void addSpell(Entity entity, Spell spell) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            if (s.isSpell(spell)) return;
            s.addSpell(spell);

            PacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static void removeSpell(Entity entity, Spell spell) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            if (!s.isSpell(spell)) return;
            s.removeSpell(spell);

            PacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static void addAllSpell(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            s.addAllSpell();

            PacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static void removeAllSpell(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            s.removeAllSpell();

            PacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static int getKnowledgePoints(Entity entity) {
        int points = 0;

        for (Knowledge knowledge : Knowledges.getKnowledges()) {
            if (isKnowledge(entity, knowledge)) {
                points = points + knowledge.getPoints();
            }
        }

        return points;
    }

    public static int getSpellPoints(Entity entity) {
        int points = 0;

        for (Spell spell : Spells.getSpells()) {
            if (isSpell(entity, spell)) {
                points = points + spell.getPoints();
            }
        }

        return points;
    }
}
