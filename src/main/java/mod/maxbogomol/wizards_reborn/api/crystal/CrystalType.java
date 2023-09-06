package mod.maxbogomol.wizards_reborn.api.crystal;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;

public class CrystalType {
    public ArrayList<CrystalStat> stats = new ArrayList<CrystalStat>();

    public CrystalType() {
        addStat(new CrystalStat(WizardsReborn.MOD_ID+":focus", 3));
        addStat(new CrystalStat(WizardsReborn.MOD_ID+":balance", 3));
        addStat(new CrystalStat(WizardsReborn.MOD_ID+":absorption", 3));
        addStat(new CrystalStat(WizardsReborn.MOD_ID+":resonance", 3));
    }

    public Color getColor() {
        return new Color(255, 255, 255);
    }

    public ResourceLocation getMiniIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/empty_mini_icon.png");
    }

    public ResourceLocation getIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/empty_icon.png");
    }

    public ItemStack getFracturedCrystal() {
        return ItemStack.EMPTY;
    }

    public ItemStack getCrystal() {
        return ItemStack.EMPTY;
    }

    public void addStat(CrystalStat stat) {
        stats.add(stat);
    }

    public ArrayList<CrystalStat> getStats() {
        return stats;
    }
}