package mod.maxbogomol.wizards_reborn.api.light;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public class LightRayHitResult {
    public Vec3 posHit;
    public float distance;
    public BlockEntity blockEntity;

    public LightRayHitResult(Vec3 posHit, float distance, BlockEntity blockEntity) {
        this.posHit = posHit;
        this.distance = distance;
        this.blockEntity = blockEntity;
    }

    public Vec3 getPosHit() {
        return posHit;
    }

    public float getDistance() {
        return distance;
    }

    public BlockEntity getBlockEntity() {
        return blockEntity;
    }

    public boolean hasBlockEntity() {
        return blockEntity != null;
    }
}
