package mod.maxbogomol.wizards_reborn.common.capability;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeHandler;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class KnowledgeImpl implements IKnowledge, INBTSerializable<CompoundTag> {
    Set<Knowledge> knowledges = new HashSet<>();
    Set<Spell> spells = new HashSet<>();
    ArrayList<ArrayList<Spell>> spellSets = new ArrayList<>();
    int currentSpellSet = 0;
    int currentSpellInSet = 0;

    @Override
    public boolean isKnowledge(Knowledge knowledge) {
        return knowledges.contains(knowledge);
    }

    @Override
    public void addKnowledge(Knowledge knowledge) {
        knowledges.add(knowledge);
    }

    @Override
    public void removeKnowledge(Knowledge knowledge) {
        knowledges.remove(knowledge);
    }

    @Override
    public void addAllKnowledge() {
        knowledges.clear();
        knowledges.addAll(KnowledgeHandler.getKnowledges());
    }

    @Override
    public void removeAllKnowledge() {
        knowledges.clear();
    }

    @Override
    public Set<Knowledge> getKnowledges() {
        return knowledges;
    }

    @Override
    public boolean isSpell(Spell spell) {
        return spells.contains(spell);
    }

    @Override
    public void addSpell(Spell spell) {
        spells.add(spell);
    }

    @Override
    public void removeSpell(Spell spell) {
        spells.remove(spell);
    }

    @Override
    public void addAllSpell() {
        spells.clear();
        spells.addAll(SpellHandler.getSpells());
    }

    @Override
    public void removeAllSpell() {
        spells.clear();
    }

    @Override
    public Set<Spell> getSpells() {
        return spells;
    }

    @Override
    public ArrayList<ArrayList<Spell>> getSpellSets() {
        return spellSets;
    }

    @Override
    public ArrayList<Spell> getSpellSet(int id) {
        return spellSets.get(id);
    }

    @Override
    public void removeSpellSet(int id) {
        spellSets.set(id, new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            spellSets.get(id).set(i, null);
        }
    }

    @Override
    public void removeAllSpellSets() {
        for (int i = 0; i < 10; i++) {
            spellSets.add(i, new ArrayList<>());
            for (int ii = 0; ii < 10; ii++) {
                spellSets.get(i).add(ii, null);
            }
        }
    }

    @Override
    public boolean isSpellSetCustomName(int id) {
        return false;
    }

    @Override
    public void setSpellSetCustomName(int id, String name) {

    }

    @Override
    public boolean isSpellInSet(int id, int spellId) {
        return spellSets.get(id).get(spellId) != null;
    }

    @Override
    public Spell getSpellFromSet(int id, int spellId) {
        return spellSets.get(id).get(spellId);
    }

    @Override
    public void addSpellInSet(int id, int spellId, Spell spell) {
        if (id < 10 && spellId < 10) spellSets.get(id).set(spellId, spell);
    }

    @Override
    public void removeSpellFromSet(int id, int spellId) {
        if (id < 10 && spellId < 10) spellSets.get(id).set(spellId, null);
    }

    @Override
    public int getCurrentSpellSet() {
        return currentSpellSet;
    }

    @Override
    public void setCurrentSpellSet(int id) {
        currentSpellSet = id;
    }

    @Override
    public int getCurrentSpellInSet() {
        return currentSpellInSet;
    }

    @Override
    public void setCurrentSpellInSet(int id) {
        currentSpellInSet = id;
    }

    @Override
    public CompoundTag serializeNBT() {
        ListTag knowledges = new ListTag();
        for (Knowledge knowledge : getKnowledges()) {
            knowledges.add(StringTag.valueOf(knowledge.getId()));
        }

        ListTag spells = new ListTag();
        for (Spell spell : getSpells()) {
            spells.add(StringTag.valueOf(spell.getId()));
        }

        ListTag spellSets = new ListTag();
        for (ArrayList<Spell> spellSet : getSpellSets()) {
            ListTag spellSetTag = new ListTag();
            for (Spell spell : spellSet) {
                if (spell != null) {
                    spellSetTag.add(StringTag.valueOf(spell.getId()));
                } else {
                    spellSetTag.add(StringTag.valueOf(""));
                }
            }
            spellSets.add(spellSetTag);
        }

        CompoundTag wrapper = new CompoundTag();
        wrapper.put("knowledges", knowledges);
        wrapper.put("spells", spells);
        wrapper.put("spellsSets", spellSets);
        wrapper.putInt("currentSpellSet", getCurrentSpellSet());
        wrapper.putInt("currentSpellInSet", getCurrentSpellInSet());

        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        removeAllKnowledge();
        removeAllSpell();
        removeAllSpellSets();
        setCurrentSpellSet(0);

        if ((nbt).contains("knowledges")) {
            ListTag knowledges = nbt.getList("knowledges", Tag.TAG_STRING);
            for (int i = 0; i < knowledges.size(); i++) {
                Knowledge knowledge = KnowledgeHandler.getKnowledge(knowledges.getString(i));
                if (knowledge != null) addKnowledge(knowledge);
            }
        }

        if ((nbt).contains("spells")) {
            ListTag spells = nbt.getList("spells", Tag.TAG_STRING);
            for (int i = 0; i < spells.size(); i++) {
                Spell spell = SpellHandler.getSpell(spells.getString(i));
                if (spell != null) addSpell(spell);
            }
        }

        if ((nbt).contains("spellsSets")) {
            ListTag spellSets = nbt.getList("spellsSets", Tag.TAG_LIST);
            for (int i = 0; i < spellSets.size(); i++) {
                ListTag spellSet = spellSets.getList(i);
                for (int ii = 0; ii < spellSet.size(); ii++) {
                    Spell spell = SpellHandler.getSpell(spellSet.getString(ii));
                    addSpellInSet(i, ii, spell);
                }
                spellSets.set(i, spellSet);
            }
        }

        if ((nbt).contains("currentSpellSet")) {
            setCurrentSpellSet(nbt.getInt("currentSpellSet"));
        }
        if ((nbt).contains("currentSpellInSet")) {
            setCurrentSpellInSet(nbt.getInt("currentSpellInSet"));
        }
    }
}
