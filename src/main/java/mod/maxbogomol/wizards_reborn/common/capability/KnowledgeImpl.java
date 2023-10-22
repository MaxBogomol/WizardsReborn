package mod.maxbogomol.wizards_reborn.common.capability;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashSet;
import java.util.Set;

public class KnowledgeImpl implements IKnowledge, INBTSerializable<CompoundTag> {
    Set<Knowledge> knowledges = new HashSet<>();
    Set<Spell> spells = new HashSet<>();

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
        knowledges.addAll(Knowledges.getKnowledges());
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
        spells.addAll(Spells.getSpells());
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
    public CompoundTag serializeNBT() {
        ListTag knowledges = new ListTag ();
        for (Knowledge knowledge : getKnowledges()) {
            knowledges.add(StringTag.valueOf(knowledge.getId()));
        }

        ListTag spells = new ListTag ();
        for (Spell spell  : getSpells()) {
            spells.add(StringTag.valueOf(spell.getId()));
        }

        CompoundTag wrapper = new CompoundTag();
        wrapper.put("knowledges", knowledges);
        wrapper.put("spells", spells);

        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        removeAllKnowledge();
        removeAllSpell();

        if ((nbt).contains("knowledges")) {
            ListTag knowledges = nbt.getList("knowledges", Tag.TAG_STRING);
            for (int i = 0; i < knowledges.size(); i++) {
                Knowledge knowledge = Knowledges.getKnowledge(knowledges.getString(i));
                if (knowledge != null) addKnowledge(knowledge);
            }
        }

        if ((nbt).contains("spells")) {
            ListTag spells = nbt.getList("spells", Tag.TAG_STRING);
            for (int i = 0; i < spells.size(); i++) {
                Spell spell = Spells.getSpell(spells.getString(i));
                if (spell != null) addSpell(spell);
            }
        }
    }
}
