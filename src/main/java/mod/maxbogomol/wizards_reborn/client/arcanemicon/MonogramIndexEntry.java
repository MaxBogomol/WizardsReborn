package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MonogramIndexEntry {
    public Chapter chapter;
    public Monogram icon;

    public MonogramIndexEntry(Chapter chapter, Monogram icon) {
        this.chapter = chapter;
        this.icon = icon;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isUnlocked() {
        return true;
    }
}
