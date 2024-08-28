package mod.maxbogomol.wizards_reborn.client.model.item;

import mod.maxbogomol.fluffy_fur.client.model.item.CustomFinalisedModel;
import mod.maxbogomol.fluffy_fur.client.model.item.CustomModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WandCrystalsModels {
    public static Map<String, BakedModel> modelsCrystals = new HashMap<>();
    public static Map<String, Map<String, BakedModel>> models = new HashMap<>();
    public static ArrayList<String> crystals = new ArrayList<>();

    public static void addModelCrystals(String id, BakedModel model) {
        modelsCrystals.put(id, model);
    }

    public static void addModel(String wand, String id, BakedModel model) {
        if (models.get(wand) == null) addWand(wand);
        models.get(wand).put(id, model);
    }

    public static void addCrystal(String id) {
        crystals.add(id);
    }

    public static Map<String, BakedModel> getModelsCrystals() {
        return modelsCrystals;
    }

    public static Map<String, Map<String, BakedModel>> getModels() {
        return models;
    }

    public static ArrayList<String> getCrystals() {
        return crystals;
    }

    public static BakedModel getModelCrystals(String id) {
        return modelsCrystals.get(id);
    }

    public static BakedModel getModel(String wand, String id) {
        if (models.get(wand) == null) addWand(wand);
        return models.get(wand).get(id);
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

    public static void addWand(String wand) {
        models.put(wand, new HashMap<>());
    }

    public static void addWandItem(Map<ResourceLocation, BakedModel> map, ResourceLocation resourceLocation) {
        BakedModel existingModel = map.get(new ModelResourceLocation(resourceLocation, "inventory"));
        WandCrystalsModels.addModel(resourceLocation.toString(), "", existingModel);

        for (String crystal : WandCrystalsModels.getCrystals()) {
            BakedModel model = map.get(WandCrystalsModels.getModelLocationCrystal(crystal));
            WandCrystalsModels.addModelCrystals(crystal, model);
            model = new CustomFinalisedModel(existingModel, WandCrystalsModels.getModelCrystals(crystal));
            WandCrystalsModels.addModel(resourceLocation.toString(), crystal, model);
        }
        CustomModel customModel = new CustomModel(existingModel, new WandItemOverrides());
        map.replace(new ModelResourceLocation(resourceLocation, "inventory"), customModel);
    }
}
