package mod.maxbogomol.wizards_reborn.common.capability;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import net.minecraft.nbt.*;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

public class KnowledgeStorage implements Capability.IStorage<IKnowledge> {
    @Override
    public INBT writeNBT(Capability<IKnowledge> capability, IKnowledge instance, Direction side) {
        ListNBT knowledges = new ListNBT();
        for (Knowledge knowledge : instance.getKnowledges()) {
            knowledges.add(StringNBT.valueOf(knowledge.getId()));
        }
        CompoundNBT wrapper = new CompoundNBT();
        wrapper.put("knowledges", knowledges);
        return wrapper;
    }

    @Override
    public void readNBT(Capability<IKnowledge> capability, IKnowledge instance, Direction side, INBT nbt) {
        instance.removeAllKnowledge();

        if (((CompoundNBT)nbt).contains("knowledges")) {
            ListNBT signs = ((CompoundNBT) nbt).getList("knowledges", Constants.NBT.TAG_STRING);
            for (int i = 0; i < signs.size(); i++) {
                Knowledge knowledge = Knowledges.getSpell(signs.getString(i));
                if (knowledge != null) instance.addKnowledge(knowledge);
            }
        }
    }
}