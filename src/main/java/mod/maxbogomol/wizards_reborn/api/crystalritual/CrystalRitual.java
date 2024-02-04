package mod.maxbogomol.wizards_reborn.api.crystalritual;

import java.awt.*;
import java.util.Random;

public class CrystalRitual {
    public String id;
    public Random random = new Random();

    public CrystalRitual(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return new Color(200, 200, 200);
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String spellId = id.substring(i + 1);
        return "crystal_ritual."  + modId + "." + spellId;
    }
}
