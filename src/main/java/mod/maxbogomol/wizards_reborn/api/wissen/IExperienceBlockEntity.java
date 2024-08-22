package mod.maxbogomol.wizards_reborn.api.wissen;

public interface IExperienceBlockEntity {
    int getExperience();
    int getMaxExperience();

    void setExperience(int experience);
    void addExperience(int experience);
    void removeExperience(int experience);
}
