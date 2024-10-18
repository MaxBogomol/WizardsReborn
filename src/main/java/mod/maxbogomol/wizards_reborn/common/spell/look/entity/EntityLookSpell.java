package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.fluffy_fur.common.raycast.RayCast;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.spell.look.LookSpell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class EntityLookSpell extends LookSpell {

    public EntityLookSpell(String id, int points) {
        super(id, points);
    }

    public double getLookDistance() {
        return 2f;
    }

    public double getReachDistance(SpellContext spellContext) {
        if (hasReachDistance(spellContext)) return (spellContext.getDistance() + getLookDistance(spellContext));
        return getLookDistance(spellContext);
    }

    public boolean hasReachDistance(SpellContext spellContext) {
        return true;
    }

    @Override
    public boolean canLookSpell(Level level, SpellContext spellContext) {
        return getEntityHit(level, spellContext).hasEntities();
    }

    public RayHitResult getEntityHit(Level level, SpellContext spellContext, Predicate<Entity> entityFilter, int entityCount, float size, boolean endE) {
        double distance = getReachDistance(spellContext);
        return RayCast.getHit(level, spellContext.getPos(), spellContext.getPos().add(spellContext.getVec().scale(distance)), entityFilter, entityCount, size, endE);
    }

    public RayHitResult getEntityHit(Level level, SpellContext spellContext, Predicate<Entity> entityFilter) {
        double distance = getReachDistance(spellContext);
        return RayCast.getHit(level, spellContext.getPos(), spellContext.getPos().add(spellContext.getVec().scale(distance)), entityFilter, 1, 0.2f, true);
    }

    public RayHitResult getEntityHit(Level level, SpellContext spellContext) {
        double distance = getReachDistance(spellContext);
        return RayCast.getHit(level, spellContext.getPos(), spellContext.getPos().add(spellContext.getVec().scale(distance)), getStandardFilter(spellContext.getEntity()), 1, 0.2f, true);
    }

    public static Predicate<Entity> getStandardFilter() {
        return (e) -> {return e instanceof LivingEntity;};
    }

    public static Predicate<Entity> getStandardFilter(Entity entity) {
        return (e) -> {
            if (!e.equals(entity)) {
                return e instanceof LivingEntity;
            }
            return false;
        };
    }

    public static Predicate<Entity> getAllFilter() {
        return (e) -> {return true;};
    }

    public static Predicate<Entity> getAllFilter(Entity entity) {
        return (e) -> {
            return !e.equals(entity);
        };
    }
}
