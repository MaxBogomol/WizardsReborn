package mod.maxbogomol.wizards_reborn.client.arcanemicon;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class SpellIndexEntry {
    public Chapter chapter;
    public Spell icon;

    public SpellIndexEntry(Chapter chapter, Spell icon) {
        this.chapter = chapter;
        this.icon = icon;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isUnlocked() {
        return true;
    }
}
