package mod.maxbogomol.wizards_reborn.common.crystal;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class WaterCrystalType extends CrystalType {
    public WaterCrystalType() {

    }

    public Color getColor() {
        return new Color(152, 180, 223);
    }

    public ResourceLocation getMiniIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/water_mini_icon.png");
    }

    public ResourceLocation getIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/water_icon.png");
    }

    public ItemStack getFracturedCrystal() {
        return WizardsReborn.FRACTURED_WATER_CRYSTAL.get().getDefaultInstance();
    }

    public ItemStack getCrystal() {
        return WizardsReborn.WATER_CRYSTAL.get().getDefaultInstance();
    }
}
