package mod.maxbogomol.wizards_reborn.api.light;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LightTypeHandler {
    public static Map<String, LightType> lightTypes = new HashMap<>();
    public static ArrayList<LightType> lightTypesList = new ArrayList<>();

    public static void addLightType(String id, LightType lightType) {
        lightTypes.put(id, lightType);
        lightTypesList.add(lightType);
    }

    public static LightType getLightType(int id) {
        return lightTypes.get(id);
    }

    public static LightType getLightType(String id) {
        return lightTypes.get(id);
    }

    public static void register(LightType lightType) {
        lightTypes.put(lightType.getId(), lightType);
        lightTypesList.add(lightType);
    }

    public static int size() {
        return lightTypes.size();
    }

    public static ArrayList<LightType> getLightTypes() {
        return lightTypesList;
    }
}
