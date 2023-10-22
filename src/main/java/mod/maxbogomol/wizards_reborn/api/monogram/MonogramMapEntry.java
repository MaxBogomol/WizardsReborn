package mod.maxbogomol.wizards_reborn.api.monogram;

public class MonogramMapEntry {
    public boolean isStart = false;
    public boolean isActive = false;
    public Monogram monogram;

    public MonogramMapEntry(Monogram monogram) {
        this.monogram = monogram;
    }

    public void setStart(boolean isStart) {
        this.isStart = isStart;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setMonogram(Monogram monogram) {
        this.monogram = monogram;
    }

    public Monogram getMonogram() {
        return monogram;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }
}
