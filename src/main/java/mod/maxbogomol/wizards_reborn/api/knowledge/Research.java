package mod.maxbogomol.wizards_reborn.api.knowledge;

import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramMapEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Research {
    public ArrayList<MonogramMapEntry> map = new ArrayList<MonogramMapEntry>();
    public Map<Monogram, Integer> monograms = new HashMap<Monogram, Integer>();
    public int mapSize;

    public Research(int mapSize, ResearchMapEntry mapEntry, ResearchMonogramEntry... monograms) {
        this.mapSize = mapSize;
        this.map = (ArrayList<MonogramMapEntry>) mapEntry.getMap().clone();
        for (ResearchMonogramEntry monogram : monograms) {
            this.monograms.put(monogram.getMonogram(), monogram.getCount());
        }
    }

    public ArrayList<MonogramMapEntry> getMap() {
        return map;
    }

    public Map<Monogram, Integer> getMonograms() {
        return monograms;
    }

    public int getMapSize() {
        return mapSize;
    }
}