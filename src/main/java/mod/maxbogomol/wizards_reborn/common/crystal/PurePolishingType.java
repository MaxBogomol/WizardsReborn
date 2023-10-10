package mod.maxbogomol.wizards_reborn.common.crystal;

import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;

import java.awt.*;

public class PurePolishingType extends PolishingType {
    public PurePolishingType(String id) {
        super(id);
    }

    public boolean hasParticle() {
        return true;
    }

    public Color getColor() {
        return new Color(147, 208, 212);
    }
}
