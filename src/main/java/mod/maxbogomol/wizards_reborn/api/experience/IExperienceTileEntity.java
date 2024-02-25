package mod.maxbogomol.wizards_reborn.api.experience;

public interface IExperienceTileEntity {
    int getExperience();
    int getMaxExperience();

    void setExperience(int experience);
    void addExperience(int experience);
    void removeExperience(int experience);
}
