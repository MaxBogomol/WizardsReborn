package mod.maxbogomol.wizards_reborn.api.arcaneenchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArcaneEnchantmentHandler {
    public static Map<String, ArcaneEnchantment> arcaneEnchantments = new HashMap<>();
    public static ArrayList<ArcaneEnchantment> arcaneEnchantmentList = new ArrayList<>();

    public static void addArcaneEnchantment(String id, ArcaneEnchantment arcaneEnchantment) {
        arcaneEnchantments.put(id, arcaneEnchantment);
        arcaneEnchantmentList.add(arcaneEnchantment);
    }

    public static ArcaneEnchantment getArcaneEnchantment(int id) {
        return arcaneEnchantments.get(id);
    }

    public static ArcaneEnchantment getArcaneEnchantment(String id) {
        return arcaneEnchantments.get(id);
    }

    public static void register(ArcaneEnchantment enchantments) {
        arcaneEnchantments.put(enchantments.getId(), enchantments);
        arcaneEnchantmentList.add(enchantments);
    }

    public static int size() {
        return arcaneEnchantments.size();
    }

    public static ArrayList<ArcaneEnchantment> getArcaneEnchantments() {
        return arcaneEnchantmentList;
    }
}
