package mod.maxbogomol.wizards_reborn.client.model.item;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemSkinsModels {
    public static Map<String, BakedModel> modelsSkins = new HashMap<String, BakedModel>();
    public static ArrayList<String> skins = new ArrayList<String>();

    public static void addModelSkins(String id, BakedModel model) {
        modelsSkins.put(id, model);
    }

    public static void addSkin(String id) {
        skins.add(id);
    }

    public static Map<String, BakedModel> getModelsSkins() {
        return modelsSkins;
    }

    public static ArrayList<String> getSkins() {
        return skins;
    }

    public static BakedModel getModelSkins(String id) {
        return modelsSkins.get(id);
    }

    public static String getSkin(int id) {
        return skins.get(id);
    }

    public static ModelResourceLocation getModelLocationSkin(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String skinId = id.substring(i + 1);
        return new ModelResourceLocation(new ResourceLocation(modId, "skin/" + skinId), "inventory");
    }
}
