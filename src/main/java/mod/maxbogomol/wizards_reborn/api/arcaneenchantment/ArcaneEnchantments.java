package mod.maxbogomol.wizards_reborn.api.arcaneenchantment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArcaneEnchantments {
    public static Map<String, ArcaneEnchantment> arcaneEnchantments = new HashMap<String, ArcaneEnchantment>();
    public static ArrayList<ArcaneEnchantment> arcaneEnchantmentList = new ArrayList<ArcaneEnchantment>();

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

    public static void register(ArcaneEnchantment spell) {
        arcaneEnchantments.put(spell.getId(), spell);
        arcaneEnchantmentList.add(spell);
    }

    public static int size() {
        return arcaneEnchantments.size();
    }

    public static ArrayList<ArcaneEnchantment> getArcaneEnchantments() {
        return arcaneEnchantmentList;
    }
}
