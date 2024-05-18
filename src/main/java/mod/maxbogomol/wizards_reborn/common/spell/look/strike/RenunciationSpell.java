package mod.maxbogomol.wizards_reborn.common.spell.look.strike;

import mod.maxbogomol.wizards_reborn.WizardsReborn;

import java.awt.*;

public class RenunciationSpell extends StrikeSpell {
    public RenunciationSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.FIRE_CRYSTAL_TYPE);
        addCrystalType(WizardsReborn.VOID_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.curseSpellColor;
    }
}
