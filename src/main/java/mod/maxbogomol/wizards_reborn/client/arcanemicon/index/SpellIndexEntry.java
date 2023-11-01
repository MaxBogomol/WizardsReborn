package mod.maxbogomol.wizards_reborn.client.arcanemicon.index;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Chapter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpellIndexEntry {
    public Chapter chapter;
    public Spell icon;
    public Knowledge knowledge;

    public SpellIndexEntry(Chapter chapter, Spell icon) {
        this.chapter = chapter;
        this.icon = icon;
    }

    public SpellIndexEntry(Chapter chapter, Spell icon, Knowledge knowledge) {
        this.chapter = chapter;
        this.icon = icon;
        this.knowledge = knowledge;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isUnlocked() {
        if (knowledge == null) {
            return true;
        } else {
            return (KnowledgeUtils.isKnowledge(Minecraft.getInstance().player, knowledge));
        }
    }
}
