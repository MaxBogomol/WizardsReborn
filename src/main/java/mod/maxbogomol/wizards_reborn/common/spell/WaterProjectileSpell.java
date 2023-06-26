package mod.maxbogomol.wizards_reborn.common.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;

import java.awt.*;

public class WaterProjectileSpell extends Spell {
    public WaterProjectileSpell(String id) {
        super(id);
        addCrystalType(WizardsReborn.WATER_CRYSTAL_TYPE);
    }

    public Color getColor() {
        return new Color(152, 180, 223);
    }
}
