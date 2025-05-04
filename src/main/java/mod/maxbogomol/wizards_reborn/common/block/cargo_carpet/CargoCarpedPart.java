package mod.maxbogomol.wizards_reborn.common.block.cargo_carpet;

import net.minecraft.util.StringRepresentable;

public enum CargoCarpedPart implements StringRepresentable {
    UP("up"),
    CENTER("center"),
    DOWN("down");

    private final String name;

    private CargoCarpedPart(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}
