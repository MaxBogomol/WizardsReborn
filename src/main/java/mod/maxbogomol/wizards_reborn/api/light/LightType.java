package mod.maxbogomol.wizards_reborn.api.light;

import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import java.awt.*;

public class LightType {
    public String id;

    public LightType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return Color.WHITE;
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String lightId = id.substring(i + 1);
        return "light_type."  + modId + "." + lightId;
    }

    public Component getColoredName() {
        return Component.translatable(getTranslatedName(id)).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(getColor())));
    }

    public static Component getColoredName(String id) {
        LightType lightType = LightTypes.getLightType(id);
        if (lightType != null) {
            return lightType.getColoredName();
        }
        return Component.empty();
    }

    public boolean tick(LightTypeStack stack) {
        return false;
    }

    public boolean hitTick(LightTypeStack stack, LightRayHitResult hitResult) {
        return false;
    }

    public boolean transferToNew(LightTypeStack oldStack, LightTypeStack newStack) {
        return false;
    }
}
