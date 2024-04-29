package mod.maxbogomol.wizards_reborn.client.arcanemicon.index;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Chapter;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IndexEntry {
    public Chapter chapter;
    public ItemStack icon;
    public Knowledge knowledge;

    public IndexEntry(Chapter chapter, ItemStack icon) {
        this.chapter = chapter;
        this.icon = icon;
    }

    public IndexEntry(Chapter chapter, ItemStack icon, Knowledge knowledge) {
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

    @OnlyIn(Dist.CLIENT)
    public boolean hasKnowledge() {
        return knowledge != null;
    }

    @OnlyIn(Dist.CLIENT)
    public Knowledge getKnowledge() {
        return knowledge;
    }

    @OnlyIn(Dist.CLIENT)
    public Component getKnowledgeName() {
        if (knowledge == null) {
            return Component.empty();
        }
        return knowledge.getName();
    }
}
