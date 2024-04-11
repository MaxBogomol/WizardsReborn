package mod.maxbogomol.wizards_reborn.common.crystal;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.awt.*;

public class FireCrystalType extends CrystalType {
    public FireCrystalType(String id) {
        super(id);
    }

    public Color getColor() {
        return WizardsReborn.fireCrystalColor;
    }

    public ResourceLocation getMiniIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/fire_mini_icon.png");
    }

    public ResourceLocation getIcon() {
        return new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/spell/fire_icon.png");
    }

    public ItemStack getFracturedCrystal() {
        return WizardsReborn.FRACTURED_FIRE_CRYSTAL.get().getDefaultInstance();
    }

    public ItemStack getCrystal() {
        return WizardsReborn.FIRE_CRYSTAL.get().getDefaultInstance();
    }
}
