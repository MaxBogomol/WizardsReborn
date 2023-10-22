package mod.maxbogomol.wizards_reborn.api.knowledge;

import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;

public class ResearchMonogramEntry {
    public Monogram monogram;
    public int count;

    public ResearchMonogramEntry(Monogram monogram, int count) {
        this.monogram = monogram;
        this.count = count;
    }

    public Monogram getMonogram() {
        return monogram;
    }

    public int getCount() {
        return count;
    }
}