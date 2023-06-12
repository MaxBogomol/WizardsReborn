package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class IndexEntry {
    public Chapter chapter;
    public ItemStack icon;

    public IndexEntry(Chapter chapter, ItemStack icon) {
        this.chapter = chapter;
        this.icon = icon;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isUnlocked() {
        return true;
    }
}
