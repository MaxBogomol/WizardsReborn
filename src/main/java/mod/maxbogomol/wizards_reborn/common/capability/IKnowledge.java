package mod.maxbogomol.wizards_reborn.common.capability;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import java.util.ArrayList;
import java.util.Set;

public interface IKnowledge {
    Capability<IKnowledge> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    boolean isKnowledge(Knowledge knowledge);
    void addKnowledge(Knowledge knowledge);
    void removeKnowledge(Knowledge knowledge);
    void addAllKnowledge();
    void removeAllKnowledge();
    Set<Knowledge> getKnowledges();

    boolean isSpell(Spell spell);
    void addSpell(Spell spell);
    void removeSpell(Spell spell);
    void addAllSpell();
    void removeAllSpell();
    Set<Spell> getSpells();

    ArrayList<ArrayList<Spell>> getSpellSets();
    ArrayList<Spell> getSpellSet(int id);
    void removeSpellSet(int id);
    void removeAllSpellSets();
    boolean isSpellSetCustomName(int id);
    void setSpellSetCustomName(int id, String name);
    boolean isSpellInSet(int id, int spellId);
    Spell getSpellFromSet(int id, int spellId);
    void addSpellInSet(int id, int spellId, Spell spell);
    void removeSpellFromSet(int id, int spellId);
    int getCurrentSpellSet();
    void setCurrentSpellSet(int id);
}
