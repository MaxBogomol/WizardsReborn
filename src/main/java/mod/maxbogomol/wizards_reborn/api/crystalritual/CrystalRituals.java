package mod.maxbogomol.wizards_reborn.api.crystalritual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrystalRituals {
    public static Map<String, CrystalRitual> crystalRituals = new HashMap<String, CrystalRitual>();
    public static ArrayList<CrystalRitual> crystalRitualsList = new ArrayList<CrystalRitual>();

    public static void addCrystalRitual(String id, CrystalRitual crystalRitual) {
        crystalRituals.put(id, crystalRitual);
        crystalRitualsList.add(crystalRitual);
    }

    public static CrystalRitual getCrystalRitual(int id) {
        return crystalRituals.get(id);
    }

    public static CrystalRitual getCrystalRitual(String id) {
        return crystalRituals.get(id);
    }

    public static void register(CrystalRitual crystalRitual) {
        crystalRituals.put(crystalRitual.getId(), crystalRitual);
        crystalRitualsList.add(crystalRitual);
    }

    public static int size() {
        return crystalRituals.size();
    }

    public static ArrayList<CrystalRitual> getCrystalRituals() {
        return crystalRitualsList;
    }
}
