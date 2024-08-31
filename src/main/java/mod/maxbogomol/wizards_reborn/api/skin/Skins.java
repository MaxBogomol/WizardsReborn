package mod.maxbogomol.wizards_reborn.api.skin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Skins {
    public static Map<String, Skin> skins = new HashMap<>();
    public static ArrayList<Skin> skinList = new ArrayList<>();

    public static void addSkin(String id, Skin skin) {
        skins.put(id, skin);
        skinList.add(skin);
    }

    public static Skin getSkin(int id) {
        return skins.get(id);
    }

    public static Skin getSkin(String id) {
        return skins.get(id);
    }

    public static void register(Skin skin) {
        skins.put(skin.getId(), skin);
        skinList.add(skin);
    }

    public static int size() {
        return skins.size();
    }

    public static ArrayList<Skin> getSkins() {
        return skinList;
    }
}
