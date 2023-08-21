package mod.maxbogomol.wizards_reborn.api.knowledge;

public class Knowledge {
    public String id;

    public Knowledge(String id) {
        this.id = id;
    }

    public boolean canReceived() {
        return false;
    }

    public String getId() {
        return id;
    }
}
