package mod.maxbogomol.wizards_reborn.common.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;

import java.awt.*;

public class EarthProjectileSpell extends Spell {
    public EarthProjectileSpell(String id) {
        super(id);
        addCrystalType(WizardsReborn.EARTH_CRYSTAL_TYPE);
    }

    public Color getColor() {
        return new Color(138, 201, 123);
    }
}
