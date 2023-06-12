package mod.maxbogomol.wizards_reborn.common.spell;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;

import java.awt.*;

public class EarthProjectileSpell extends Spell {
    public EarthProjectileSpell(String id) {
        super(id);
    }

    public Color getColor() {
        return new Color(186, 234, 133);
    }
}
