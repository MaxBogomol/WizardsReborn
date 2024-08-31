package mod.maxbogomol.wizards_reborn.api.alchemy;

public interface IHeatBlockEntity {
    int getHeat();
    int getMaxHeat();

    void setHeat(int heat);
    void addHeat(int heat);
    void removeHeat(int heat);
}
