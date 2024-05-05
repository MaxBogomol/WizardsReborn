package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;

import java.awt.*;

public class LifeRootsArcaneEnchantment extends ArcaneEnchantment {

    public LifeRootsArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    public Color getColor() {
        return new Color(164, 201, 167);
    }
}
