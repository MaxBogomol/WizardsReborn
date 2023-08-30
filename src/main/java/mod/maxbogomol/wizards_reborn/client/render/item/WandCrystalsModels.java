package mod.maxbogomol.wizards_reborn.client.render.item;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WandCrystalsModels {
    public static Map<String, BakedModel> modelsCrystals = new HashMap<String, BakedModel>();
    public static Map<String, BakedModel> models = new HashMap<String, BakedModel>();
    public static ArrayList<String> crystals = new ArrayList<String>();

    public static void addModelCrystals(String id, BakedModel model) {
        modelsCrystals.put(id, model);
    }

    public static void addModel(String id, BakedModel model) {
        models.put(id, model);
    }

    public static void addCrystal(String id) {
        crystals.add(id);
    }

    public static Map<String, BakedModel> getModelsCrystals() {
        return modelsCrystals;
    }

    public static Map<String, BakedModel> getModels() {
        return models;
    }

    public static ArrayList<String> getCrystals() {
        return crystals;
    }

    public static BakedModel getModelCrystals(String id) {
        return modelsCrystals.get(id);
    }

    public static BakedModel getModel(String id) {
        return models.get(id);
    }

    public static String getCrystal(int id) {
        return crystals.get(id);
    }

    public static ModelResourceLocation getModelLocationCrystal(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String crystalId = id.substring(i + 1);
        return new ModelResourceLocation(new ResourceLocation(modId, "wand_crystals/" + crystalId), "inventory");
    }
}
