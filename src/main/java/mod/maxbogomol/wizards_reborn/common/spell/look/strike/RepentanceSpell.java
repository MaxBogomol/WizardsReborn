package mod.maxbogomol.wizards_reborn.common.spell.look.strike;

import mod.maxbogomol.wizards_reborn.WizardsReborn;

import java.awt.*;

public class RepentanceSpell extends StrikeSpell {
    public RepentanceSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.EARTH_CRYSTAL_TYPE);
        addCrystalType(WizardsReborn.AIR_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.holySpellColor;
    }
}
