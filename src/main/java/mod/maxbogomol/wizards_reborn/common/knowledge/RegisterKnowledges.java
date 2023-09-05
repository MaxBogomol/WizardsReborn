package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;

public class RegisterKnowledges {
    //ITEM KNOWLEDGES
    public static ItemKnowledge ARCANUM_DUST_KNOWLEDGE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcanum_dust", WizardsReborn.ARCANUM_DUST.get());
    public static ItemTagKnowledge ARCANE_WOOD_KNOWLEDGE = new ItemTagKnowledge(WizardsReborn.MOD_ID+":arcane_wood", WizardsReborn.ARCANE_WOOD_LOGS_ITEM_TAG);
    public static ItemKnowledge ARCANE_GOLD_KNOWLEDGE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_gold", WizardsReborn.ARCANE_GOLD_INGOT.get());
    public static ItemKnowledge WISSEN_CRYSTALLIZER = new ItemKnowledge(WizardsReborn.MOD_ID+":wissen_crystallizer", WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get());

    public static void setupKnowledges() {
        Knowledges.register(ARCANUM_DUST_KNOWLEDGE);
        Knowledges.register(ARCANE_WOOD_KNOWLEDGE);
        Knowledges.register(ARCANE_GOLD_KNOWLEDGE);
        Knowledges.register(WISSEN_CRYSTALLIZER);
    }
}
