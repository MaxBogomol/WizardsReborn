package mod.maxbogomol.wizards_reborn.api.light;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class LightRayHitResult {
    public Vec3 posHit;
    public float distance;
    public BlockEntity blockEntity;
    public ArrayList<EntityContext> entities;

    public LightRayHitResult(Vec3 posHit, float distance, BlockEntity blockEntity, ArrayList<EntityContext> entities) {
        this.posHit = posHit;
        this.distance = distance;
        this.blockEntity = blockEntity;
        this.entities = entities;
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

    public ArrayList<EntityContext> getEntities() {
        return entities;
    }

    public static class EntityContext {
        public Vec3 posHit;
        public Entity entity;

        public EntityContext(Vec3 posHit, Entity entity) {
            this.posHit = posHit;
            this.entity = entity;
        }

        public Vec3 getPosHit() {
            return posHit;
        }

        public Entity getEntity() {
            return entity;
        }
    }
}
