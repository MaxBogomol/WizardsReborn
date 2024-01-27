package mod.maxbogomol.wizards_reborn.api.light;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class LightRayHitResult {
    public Vec3 posHit;
    public float distance;
    public BlockEntity tile;

    public LightRayHitResult(Vec3 posHit, float distance, BlockEntity tile) {
        this.posHit = posHit;
        this.distance = distance;
        this.tile = tile;
    }

    public Vec3 getPosHit() {
        return posHit;
    }

    public float getDistance() {
        return distance;
    }

    public BlockEntity getTile() {
        return tile;
    }

    public boolean hasTile() {
        return tile != null;
    }
}
