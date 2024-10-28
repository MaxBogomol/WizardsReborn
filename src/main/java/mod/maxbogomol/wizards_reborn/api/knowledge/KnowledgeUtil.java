package mod.maxbogomol.wizards_reborn.api.knowledge;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellHandler;
import mod.maxbogomol.wizards_reborn.common.capability.IKnowledge;
import mod.maxbogomol.wizards_reborn.common.network.KnowledgeToastPacket;
import mod.maxbogomol.wizards_reborn.common.network.KnowledgeUpdatePacket;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class KnowledgeUtil {

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

            knowledge.award((Player) entity);

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
            if (knowledge.hasToast()) {
                WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeToastPacket(knowledge.getId(), false));
            }
        });
    }

    public static void removeKnowledge(Entity entity, Knowledge knowledge) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((k) -> {
            if (!k.isKnowledge(knowledge)) return;
            k.removeKnowledge(knowledge);

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static void addAllKnowledge(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((k) -> {
            k.addAllKnowledge();

            for (Knowledge knowledge : KnowledgeHandler.getKnowledges()) {
                if (knowledge.hasAllAward()) {
                    knowledge.award((Player) entity);
                }
            }

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeToastPacket("", true));
        });
    }

    public static void removeAllKnowledge(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((k) -> {
            k.removeAllKnowledge();

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static void addKnowledgeFromScroll(Entity entity, Knowledge knowledge) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((k) -> {
            if (k.isKnowledge(knowledge)) return;
            k.addKnowledge(knowledge);

            if (knowledge.hasScrollAward()) {
                knowledge.award((Player) entity);
            }

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
            if (knowledge.hasToast()) {
                WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeToastPacket(knowledge.getId(), false));
            }
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

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static void removeSpell(Entity entity, Spell spell) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            if (!s.isSpell(spell)) return;
            s.removeSpell(spell);

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static void addAllSpell(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            s.addAllSpell();

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static void removeAllSpell(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            s.removeAllSpell();

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static ArrayList<ArrayList<Spell>> getSpellSets(Entity entity) {
        if (!(entity instanceof Player)) return null;
        AtomicReference<ArrayList<ArrayList<Spell>>> sets = new AtomicReference<>();
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            sets.set(s.getSpellSets());
        });
        return sets.get();
    }

    public static ArrayList<Spell> getSpellSet(Entity entity, int id) {
        if (!(entity instanceof Player)) return null;
        AtomicReference<ArrayList<Spell>> set = new AtomicReference<>();
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            set.set(s.getSpellSet(id));
        });
        return set.get();
    }

    public static void removeSpellSet(Entity entity, int id) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            s.removeSpellSet(id);

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static void removeSpellSets(Entity entity) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            s.removeAllSpellSets();

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static boolean isSpellInSet(Entity entity, int id, int spellId) {
        if (!(entity instanceof Player)) return false;
        AtomicBoolean isSpell = new AtomicBoolean(false);
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            isSpell.set(s.isSpellInSet(id, spellId));
        });
        return isSpell.get();
    }

    public static Spell getSpellFromSet(Entity entity, int id, int spellId) {
        if (!(entity instanceof Player)) return null;
        AtomicReference<Spell> spell = new AtomicReference<>();
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            spell.set(s.getSpellFromSet(id, spellId));
        });
        return spell.get();
    }

    public static void addSpellInSet(Entity entity, int id, int spellId, Spell spell) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            s.addSpellInSet(id, spellId, spell);

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static void removeSpellFromSet(Entity entity, int id, int spellId) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            s.removeSpellFromSet(id, spellId);

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static int getCurrentSpellSet(Entity entity) {
        if (!(entity instanceof Player)) return 0;
        AtomicInteger current = new AtomicInteger();
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            current.set(s.getCurrentSpellSet());
        });
        return current.get();
    }

    public static void setCurrentSpellSet(Entity entity, int id) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            s.setCurrentSpellSet(id);

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static int getCurrentSpellInSet(Entity entity) {
        if (!(entity instanceof Player)) return 0;
        AtomicInteger current = new AtomicInteger();
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            current.set(s.getCurrentSpellInSet());
        });
        return current.get();
    }

    public static void setCurrentSpellInSet(Entity entity, int id) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IKnowledge.INSTANCE, null).ifPresent((s) -> {
            s.setCurrentSpellInSet(id);

            WizardsRebornPacketHandler.sendTo((Player) entity, new KnowledgeUpdatePacket((Player) entity));
        });
    }

    public static int getKnowledgePoints(Entity entity) {
        int points = 0;

        for (Knowledge knowledge : KnowledgeHandler.getKnowledges()) {
            if (isKnowledge(entity, knowledge)) {
                points = points + knowledge.getPoints();
            }
        }

        return points;
    }

    public static int getSpellPoints(Entity entity) {
        int points = 0;

        for (Spell spell : SpellHandler.getSpells()) {
            if (isSpell(entity, spell)) {
                points = points + spell.getPoints();
            }
        }

        return points;
    }

    public static int getAllKnowledgePoints() {
        int points = 0;

        for (Knowledge knowledge : KnowledgeHandler.getKnowledges()) {
            points = points + knowledge.getPoints();
        }

        return points;
    }

    public static int getAllSpellPoints() {
        int points = 0;

        for (Spell spell : SpellHandler.getSpells()) {
            points = points + spell.getPoints();
        }

        return points;
    }
}
