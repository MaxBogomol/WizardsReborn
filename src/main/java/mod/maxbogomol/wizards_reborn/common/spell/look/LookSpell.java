package mod.maxbogomol.wizards_reborn.common.spell.look;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LookSpell extends Spell {

    public LookSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public void useSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            spellContext.setCooldown(this);
            spellContext.removeWissen(this);
            spellContext.awardStat(this);
            spellContext.spellSound(this);
        }
        lookSpell(level, spellContext);
    }

    @Override
    public boolean canSpell(Level level, SpellContext spellContext) {
        if (super.canSpell(level, spellContext)) {
            return canLookSpell(level, spellContext);
        }
        return false;
    }

    public double getLookDistance() {
        return 5f;
    }

    public double getLookAdditionalDistance() {
        return 0f;
    }

    public double getLookDistance(SpellContext spellContext) {
        int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
        return getLookDistance() + (getLookAdditionalDistance() * focusLevel);
    }

    public double getLookDistance(int focusLevel) {
        return getLookDistance() + (getLookAdditionalDistance() * focusLevel);
    }

    public static HitResult getHitPos(Level level, Vec3 start, Vec3 endPos, Predicate<Entity> entityFilter, int entityCount, float size, boolean endE) {
        double distance = Math.sqrt(Math.pow(start.x() - endPos.x(), 2) + Math.pow(start.y() - endPos.y(), 2) + Math.pow(start.z() - endPos.z(), 2));
        double X = start.x();
        double Y = start.y();
        double Z = start.z();
        double oldX = X;
        double oldY = Y;
        double oldZ = Z;
        int count = 0;
        List<Entity> entities = new ArrayList<>();

        for (float i = 0; i < distance * 16; i++) {
            double dst = (distance * 16);

            double dX = start.x() - endPos.x();
            double dY = start.y() - endPos.y();
            double dZ = start.z() - endPos.z();

            double x = -(dX / (dst)) * i;
            double y = -(dY / (dst)) * i;
            double z = -(dZ / (dst)) * i;

            X = (start.x() + x);
            Y = (start.y() + y);
            Z = (start.z() + z);

            boolean canEntity = true;
            if (endE) canEntity = (entityCount > 0);

            if (canEntity) {
                List<Entity> entityList = level.getEntitiesOfClass(Entity.class, new AABB(X - size, Y - size, Z - size, X + size, Y + size, Z + size));
                for (Entity entity : entityList) {
                    if (entityFilter.test(entity) && !entities.contains(entity)) {
                        entities.add(entity);
                        count++;

                        if (endE && count >= entityCount) {
                            return new HitResult(new Vec3(X, Y, Z), false, entities);
                        }
                    }
                }
            }

            BlockPos blockPos = BlockPos.containing(X, Y, Z);

            BlockHitResult blockHitResult = level.getBlockState(blockPos).getVisualShape(level, blockPos, CollisionContext.empty()).clip(start, endPos, blockPos);
            if (blockHitResult != null) {
                boolean isBlock = !level.getBlockState(blockHitResult.getBlockPos()).isAir();
                return new HitResult(new Vec3(oldX, oldY, oldZ), isBlock, entities);
            }

            oldX = X;
            oldY = Y;
            oldZ = Z;
        }
        return new HitResult(new Vec3(X, Y, Z), false, entities);
    }

    public static HitResult getHitPos(Level level, Vec3 start, Vec3 endPos) {
        return getHitPos(level, start, endPos, (e) -> {return false;}, 0, 0, false);
    }

    public HitResult getHitPos(Level level, SpellContext spellContext, Predicate<Entity> entityFilter, int entityCount, float size, boolean endE) {
        double distance = getLookDistance(spellContext);
        return getHitPos(level, spellContext.getPos(), spellContext.getPos().add(spellContext.getVec().scale(distance)), entityFilter, entityCount, size, endE);
    }

    public HitResult getHitPos(Level level, SpellContext spellContext) {
        double distance = getLookDistance(spellContext);
        return getHitPos(level, spellContext.getPos(), spellContext.getPos().add(spellContext.getVec().scale(distance)));
    }

    public boolean canLookSpell(Level level, SpellContext spellContext) {
        return true;
    }

    public void lookSpell(Level level, SpellContext spellContext) {

    }

    public static List<Entity> getHitEntities(Level level, Vec3 start, Vec3 endPos, float distance) {
        List<Entity> list = new ArrayList<>();
        float ds = (float) Math.sqrt(Math.pow(start.x() - endPos.x, 2) + Math.pow(start.y() - endPos.y, 2) + Math.pow(start.z() - endPos.z, 2));
        for (float i = 0; i < ds * 10; i++) {
            float dst = (ds * 10);

            double dX = start.x() - endPos.x();
            double dY = start.y() - endPos.y();
            double dZ = start.z() - endPos.z();

            float x = (float) -(dX / (dst)) * i;
            float y = (float) -(dY / (dst)) * i;
            float z = (float) -(dZ / (dst)) * i;

            float X = (float) (start.x() + x);
            float Y = (float) (start.y() + y);
            float Z = (float) (start.z() + z);

            List<Entity> entityList = level.getEntitiesOfClass(Entity.class, new AABB(X - distance, Y - distance, Z - distance, X + distance, Y + distance, Z + distance));
            for (Entity entity : entityList) {
                if (!list.contains(entity)) {
                    list.add(entity);
                }
            }
        }
        return list;
    }

    public static class HitResult {
        public Vec3 posHit;
        public boolean blockHit;
        public List<Entity> entities;

        public HitResult(Vec3 posHit, boolean blockHit) {
            this.posHit = posHit;
            this.blockHit = blockHit;
        }

        public HitResult(Vec3 posHit, boolean blockHit, List<Entity> entities) {
            this.posHit = posHit;
            this.blockHit = blockHit;
            this.entities = entities;
        }

        public Vec3 getPosHit() {
            return posHit;
        }

        public boolean hasBlockHit() {
            return blockHit;
        }

        public List<Entity> getEntities() {
            return entities;
        }

        public boolean hasEntities() {
            return !entities.isEmpty();
        }
    }
}
