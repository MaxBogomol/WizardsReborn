package mod.maxbogomol.wizards_reborn.common.block;

import net.minecraft.util.StringRepresentable;

public enum PipeConnection implements StringRepresentable {
    NONE("none", 0, 0, false),
    DISABLED("disabled", 1, 0, false),
    PIPE("pipe", 2, 1, true),
    END("end", 3, 2, true),
    LEVER("lever", 4, 2, false);

    private final String name;
    public final int index;
    public final int visualIndex;
    public final boolean transfer;
    public static final PipeConnection[] visualValues = {
            NONE,
            PIPE,
            END
    };

    private PipeConnection(String name, int index, int visualIndex, boolean transfer) {
        this.name = name;
        this.index = index;
        this.visualIndex = visualIndex;
        this.transfer = transfer;
    }

    public static PipeConnection[] visual() {
        return visualValues;
    }

    public String toString() {
        return this.name;
    }

    public String getSerializedName() {
        return this.name;
    }
}