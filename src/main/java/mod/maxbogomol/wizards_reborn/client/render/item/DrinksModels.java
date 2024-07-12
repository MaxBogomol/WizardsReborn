package mod.maxbogomol.wizards_reborn.client.render.item;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class DrinksModels {
    public static Map<Integer, BakedModel> modelsStages = new HashMap<>();
    public static Map<String, Map<Integer, BakedModel>> models = new HashMap<>();

    public static void addModelStage(int stage, BakedModel model) {
        modelsStages.put(stage, model);
    }

    public static void addModel(String drink, int stage, BakedModel model) {
        if (models.get(drink) == null) addDrink(drink);
        models.get(drink).put(stage, model);
    }

    public static Map<String, Map<Integer, BakedModel>> getModels() {
        return models;
    }

    public static BakedModel getModel(String drink, int stage) {
        if (models.get(drink) == null) addDrink(drink);
        return models.get(drink).get(stage);
    }

    public static ModelResourceLocation getModelLocationStage(int stage) {
        return new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "drinks/stage_" + stage), "inventory");
    }

    public static void addDrink(String wand) {
        models.put(wand, new HashMap<>());
    }

    public static void addDrinkItem(Map<ResourceLocation, BakedModel> map, ResourceLocation resourceLocation) {
        BakedModel existingModel = map.get(new ModelResourceLocation(resourceLocation, "inventory"));
        DrinksModels.addModel(resourceLocation.toString(), 0, existingModel);

        for (int i = 0; i < 4; i++) {
            BakedModel model = map.get(DrinksModels.getModelLocationStage(i + 1));
            DrinksModels.addModelStage(i + 1, model);
            model = new CustomFinalisedModel(existingModel, model);
            DrinksModels.addModel(resourceLocation.toString(), i + 1, model);
        }
        CustomModel customModel = new CustomModel(existingModel, new DrinkModelOverrideList());
        map.replace(new ModelResourceLocation(resourceLocation, "inventory"), customModel);
    }
}
