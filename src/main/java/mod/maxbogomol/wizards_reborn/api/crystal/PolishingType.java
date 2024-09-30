package mod.maxbogomol.wizards_reborn.api.crystal;

import java.awt.*;

public class PolishingType {
    public String id;
    public int level;

    public PolishingType(String id, int level) {
        this.id = id;
        this.level = level;
    }

    public boolean hasParticle() {
        return false;
    }

    public Color getColor() {
        return Color.WHITE;
    }

    public String getId() {
        return id;
    }

    public int getPolishingLevel() {
        return level;
    }
}
