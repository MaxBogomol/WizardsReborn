package mod.maxbogomol.wizards_reborn.api.crystalritual;

import net.minecraft.world.phys.Vec3;

public class CrystalRitualArea {
    public Vec3 sizeFrom;
    public Vec3 sizeTo;

    public CrystalRitualArea(Vec3 sizeFrom, Vec3 sizeTo) {
        this.sizeFrom = sizeFrom;
        this.sizeTo = sizeTo;
    }

    public CrystalRitualArea(float x1, float y1, float z1, float x2, float y2, float z2) {
        this.sizeFrom = new Vec3(x1, y1, z1);
        this.sizeTo = new Vec3(x2, y2, z2);
    }

    public CrystalRitualArea(Vec3 size) {
        this.sizeFrom = size;
        this.sizeTo = size;
    }

    public Vec3 getSize() {
        return sizeFrom;
    }

    public Vec3 getSizeFrom() {
        return sizeFrom;
    }

    public Vec3 getSizeTo() {
        return sizeTo;
    }
}
