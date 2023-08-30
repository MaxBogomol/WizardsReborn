package mod.maxbogomol.wizards_reborn.common.capability;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

import java.util.Set;

public interface IKnowledge {
    static Capability<IKnowledge> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    boolean isKnowledge(Knowledge knowledge);
    void addKnowledge(Knowledge knowledge);
    void removeKnowledge(Knowledge knowledge);
    void addAllKnowledge();
    void removeAllKnowledge();
    Set<Knowledge> getKnowledges();
}
