package mod.maxbogomol.wizards_reborn.api.crystal;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Crystals {
    public static Map<String, CrystalType> crystalTypes = new HashMap<String, CrystalType>();
    public static ArrayList<CrystalType> crystalTypesList = new ArrayList<CrystalType>();
    public static Map<String, PolishingType> crystalPolishing = new HashMap<String, PolishingType>();
    public static ArrayList<PolishingType> crystalPolishingList = new ArrayList<PolishingType>();
    public static ArrayList<Item> crystalItems = new ArrayList<Item>();

    public static void addType(String id, CrystalType type) {
        crystalTypes.put(id, type);
        crystalTypesList.add(type);
    }

    public static CrystalType getType(int id) {
        return crystalTypes.get(id);
    }

    public static CrystalType getType(String id) {
        return crystalTypes.get(id);
    }

    public static void registerType(CrystalType type) {
        crystalTypes.put(type.getId(), type);
        crystalTypesList.add(type);
    }

    public static int sizeType() {
        return crystalTypes.size();
    }

    public static ArrayList<CrystalType> getTypes() {
        return crystalTypesList;
    }

    public static void addPolishing(String id, PolishingType type) {
        crystalPolishing.put(id, type);
        crystalPolishingList.add(type);
    }

    public static PolishingType getPolishing(int id) {
        return crystalPolishing.get(id);
    }

    public static PolishingType getPolishing(String id) {
        return crystalPolishing.get(id);
    }

    public static void registerPolishing(PolishingType type) {
        crystalPolishing.put(type.getId(), type);
        crystalPolishingList.add(type);
    }

    public static int sizePolishing() {
        return crystalPolishing.size();
    }

    public static ArrayList<PolishingType> getPolishings() {
        return crystalPolishingList;
    }

    public static void addItem(Item item) {
        crystalItems.add(item);
    }

    public static ArrayList<Item> getItems() {
        return crystalItems;
    }
}