package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;

import java.awt.*;

public class WissenMendingArcaneEnchantment extends ArcaneEnchantment {

    public WissenMendingArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    public Color getColor() {
        return new Color(87, 127, 184);
    }
}
