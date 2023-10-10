package mod.maxbogomol.wizards_reborn.common.crystal;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class AirCrystalType extends CrystalType {
    public AirCrystalType(String id) {
        super(id);
    }

    public Color getColor() {
        return new Color(230, 173, 134);
    }

    public ResourceLocation getMiniIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/air_mini_icon.png");
    }

    public ResourceLocation getIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/air_icon.png");
    }

    public ItemStack getFracturedCrystal() {
        return WizardsReborn.FRACTURED_AIR_CRYSTAL.get().getDefaultInstance();
    }

    public ItemStack getCrystal() {
        return WizardsReborn.AIR_CRYSTAL.get().getDefaultInstance();
    }
}
