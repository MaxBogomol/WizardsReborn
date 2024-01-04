package mod.maxbogomol.wizards_reborn.api.alchemy;

import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AlchemyPotion {
    public String id;
    public ArrayList<MobEffectInstance> effects = new ArrayList<>();

    public AlchemyPotion(String id) {
        this.id = id;
    }

    public AlchemyPotion(String id, MobEffectInstance... effects) {
        this.id = id;
        this.effects = new ArrayList<>(Arrays.asList(effects));;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        int color = PotionUtils.getColor(effects);
        return new Color(ColorUtils.getRed(color), ColorUtils.getGreen(color), ColorUtils.getBlue(color));
    }

    public ArrayList<MobEffectInstance> getEffects() {
        return effects;
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String spellId = id.substring(i + 1);
        return "alchemy_potion."  + modId + "." + spellId;
    }
}
