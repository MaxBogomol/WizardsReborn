package mod.maxbogomol.wizards_reborn.common.alchemypotion;

import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.material.Fluid;

import java.awt.*;

public class ColorAlchemyPotion extends AlchemyPotion {
    public Color color;

    public ColorAlchemyPotion(String id, Color color) {
        super(id);
        this.color = color;
    }

    public ColorAlchemyPotion(String id, Color color, MobEffectInstance... effects) {
        super(id, effects);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
