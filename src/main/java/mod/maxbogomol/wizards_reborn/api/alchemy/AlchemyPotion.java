package mod.maxbogomol.wizards_reborn.api.alchemy;

import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;

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
        return new Color(ColorUtil.getRed(color), ColorUtil.getGreen(color), ColorUtil.getBlue(color));
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
        String alchemyPotionId = id.substring(i + 1);
        return "alchemy_potion." + modId + "." + alchemyPotionId;
    }

    public void apply(ItemStack stack, Level level, LivingEntity livingEntity) {

    }
}
