package mod.maxbogomol.wizards_reborn.common.alchemypotion;

import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import net.minecraft.world.level.material.Fluid;

import java.awt.*;

public class FluidAlchemyPotion extends AlchemyPotion {
    public Fluid fluid;
    public Color color;

    public FluidAlchemyPotion(String id, Fluid fluid, Color color) {
        super(id);
        this.fluid = fluid;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getTranslatedName() {
        return fluid.getFluidType().getDescriptionId();
    }


    public Fluid getFluid() {
        return fluid;
    }

}
