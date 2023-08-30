package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import net.minecraft.client.Minecraft;
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
}
