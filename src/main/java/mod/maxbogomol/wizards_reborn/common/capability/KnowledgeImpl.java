package mod.maxbogomol.wizards_reborn.common.capability;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashSet;
import java.util.Set;

public class KnowledgeImpl implements IKnowledge, INBTSerializable<CompoundTag> {
    Set<Knowledge> knowledges = new HashSet<>();

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
    public CompoundTag serializeNBT() {
        ListTag knowledges = new ListTag ();
        for (Knowledge knowledge : getKnowledges()) {
            knowledges.add(StringTag.valueOf(knowledge.getId()));
        }
        CompoundTag wrapper = new CompoundTag();
        wrapper.put("knowledges", knowledges);
        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        removeAllKnowledge();

        if ((nbt).contains("knowledges")) {
            ListTag signs = nbt.getList("knowledges", Tag.TAG_STRING);
            for (int i = 0; i < signs.size(); i++) {
                Knowledge knowledge = Knowledges.getKnowledge(signs.getString(i));
                if (knowledge != null) addKnowledge(knowledge);
            }
        }
    }
}
