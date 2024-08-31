package mod.maxbogomol.wizards_reborn.api.monogram;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Monograms {
    public static Map<String, Monogram> monograms = new HashMap<>();
    public static ArrayList<Monogram> monogramList = new ArrayList<>();
    public static Map<String, MonogramRecipe> recipes = new HashMap<>();

    public static void addMonogram(String id, Monogram monogram) {
        monograms.put(id, monogram);
        monogramList.add(monogram);
    }

    public static Monogram getMonogram(int id) {
        return monograms.get(id);
    }

    public static Monogram getMonogram(String id) {
        return monograms.get(id);
    }

    public static void register(Monogram monogram) {
        monograms.put(monogram.getId(), monogram);
        monogramList.add(monogram);
    }

    public static int size() {
        return monograms.size();
    }

    public static ArrayList<Monogram> getMonograms() {
        return monogramList;
    }

    public static void addRecipe(MonogramRecipe recipe) {
        recipes.put(recipe.getOutput().getId(), recipe);
    }

    @Nonnull
    public static MonogramRecipe getRecipe(String id) {
        return recipes.get(id);
    }

    public static Map<String, MonogramRecipe> getRecipes() {
        return recipes;
    }
}
