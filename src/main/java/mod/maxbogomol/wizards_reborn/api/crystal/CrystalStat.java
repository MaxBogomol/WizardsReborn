package mod.maxbogomol.wizards_reborn.api.crystal;

public class CrystalStat {
    public String id;
    public int maxLevel;

    public CrystalStat(String id, int maxLevel) {
        this.id = id;
        this.maxLevel = maxLevel;
    }

    public String getId() {
        return id;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getTranslatedName() {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String statId = id.substring(i + 1);
        return "crystal_stat."  + modId + "." + statId;
    }
}
