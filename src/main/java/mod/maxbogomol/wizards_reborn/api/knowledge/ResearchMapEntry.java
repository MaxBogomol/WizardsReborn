package mod.maxbogomol.wizards_reborn.api.knowledge;

import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramMapEntry;

import java.util.ArrayList;

public class ResearchMapEntry {
    public ArrayList<MonogramMapEntry> map = new ArrayList<>();

    public ResearchMapEntry(Monogram... entries) {
        for (Monogram entry : entries) {
            MonogramMapEntry monogramMapEntry = new MonogramMapEntry(entry);
            monogramMapEntry.setStart(true);
            map.add(monogramMapEntry);
        }
    }

    public ArrayList<MonogramMapEntry> getMap() {
        return map;
    }
}