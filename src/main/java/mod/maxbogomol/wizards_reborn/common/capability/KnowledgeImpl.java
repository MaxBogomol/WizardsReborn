package mod.maxbogomol.wizards_reborn.common.capability;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;

import java.util.HashSet;
import java.util.Set;

public class KnowledgeImpl implements IKnowledge {
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
        knowledges.addAll(Knowledges.getSpells());
    }

    @Override
    public void removeAllKnowledge() {
        knowledges.clear();
    }

    @Override
    public Set<Knowledge> getKnowledges() {
        return knowledges;
    }
}
