package mod.maxbogomol.wizards_reborn.common.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;

import java.awt.*;

public class FireProjectileSpell extends Spell {
    public FireProjectileSpell(String id) {
        super(id);
        addCrystalType(WizardsReborn.FIRE_CRYSTAL_TYPE);
    }

    public Color getColor() {
        return new Color(225, 127, 103);
    }
}
