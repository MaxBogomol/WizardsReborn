package mod.maxbogomol.wizards_reborn.common.crystal;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class WaterCrystalType extends CrystalType {
    public WaterCrystalType(String id) {
        super(id);
    }

    public Color getColor() {
        return WizardsRebornCrystals.waterCrystalColor;
    }

    public ResourceLocation getMiniIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/water_mini_icon.png");
    }

    public ResourceLocation getIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/water_icon.png");
    }

    public ItemStack getFracturedCrystal() {
        return WizardsRebornItems.FRACTURED_WATER_CRYSTAL.get().getDefaultInstance();
    }

    public ItemStack getCrystal() {
        return WizardsRebornItems.WATER_CRYSTAL.get().getDefaultInstance();
    }
}
