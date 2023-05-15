package mod.maxbogomol.wizards_reborn.client.render.model.item;

import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WandCrystalsModels {
    public static Map<String, IBakedModel> modelsCrystals = new HashMap<String, IBakedModel>();
    public static Map<String, IBakedModel> models = new HashMap<String, IBakedModel>();
    public static ArrayList<String> crystals = new ArrayList<String>();

    public static void addModelCrystals(String id, IBakedModel model) {
        modelsCrystals.put(id, model);
    }

    public static void addModel(String id, IBakedModel model) {
        models.put(id, model);
    }

    public static void addCrystal(String id) {
        crystals.add(id);
    }

    public static Map<String, IBakedModel> getModelsCrystals() {
        return modelsCrystals;
    }

    public static Map<String, IBakedModel> getModels() {
        return models;
    }

    public static ArrayList<String> getCrystals() {
        return crystals;
    }

    public static IBakedModel getModelCrystals(String id) {
        return modelsCrystals.get(id);
    }

    public static IBakedModel getModel(String id) {
        return models.get(id);
    }

    public static String getCrystal(int id) {
        return crystals.get(id);
    }

    public static ResourceLocation getModelLocationCrystal(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String crystalId = id.substring(i + 1);
        return new ResourceLocation(modId, "item/wand_crystals/" + crystalId);
    }
}
