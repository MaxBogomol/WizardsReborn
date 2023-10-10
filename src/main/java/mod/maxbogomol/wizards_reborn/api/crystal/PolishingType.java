package mod.maxbogomol.wizards_reborn.api.crystal;

import java.awt.*;

public class PolishingType {
    public String id;

    public PolishingType(String id) {
        this.id = id;
    }

    public boolean hasParticle() {
        return false;
    }

    public Color getColor() {
        return new Color(255, 255, 255);
    }

    public String getId() {
        return id;
    }
}
