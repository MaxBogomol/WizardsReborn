package mod.maxbogomol.wizards_reborn.api.alchemy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlchemyPotions {
    public static Map<String, AlchemyPotion> alchemyPotions = new HashMap<>();
    public static ArrayList<AlchemyPotion> alchemyPotionList = new ArrayList<>();

    public static void addAlchemyPotion(String id, AlchemyPotion potion) {
        alchemyPotions.put(id, potion);
        alchemyPotionList.add(potion);
    }

    public static AlchemyPotion getAlchemyPotion(int id) {
        return alchemyPotions.get(id);
    }

    public static AlchemyPotion getAlchemyPotion(String id) {
        return alchemyPotions.get(id);
    }

    public static void register(AlchemyPotion potion) {
        alchemyPotions.put(potion.getId(), potion);
        alchemyPotionList.add(potion);
    }

    public static int size() {
        return alchemyPotions.size();
    }

    public static ArrayList<AlchemyPotion> getAlchemyPotions() {
        return alchemyPotionList;
    }
}
