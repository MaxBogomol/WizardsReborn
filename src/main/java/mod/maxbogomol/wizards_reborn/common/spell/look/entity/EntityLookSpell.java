package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.fluffy_fur.common.raycast.RayCast;
import mod.maxbogomol.fluffy_fur.common.raycast.RayCastContext;
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

    public RayHitResult getEntityHit(Level level, SpellContext spellContext, RayCastContext context, Predicate<Entity> entityFilter) {
        double distance = getReachDistance(spellContext);
        context.setStartPos(spellContext.getPos()).setEndPos(spellContext.getPos().add(spellContext.getVec().scale(distance)))
                .setEntityFilter(entityFilter);
        return RayCast.getHit(level, context);
    }

    public RayHitResult getEntityHit(Level level, SpellContext spellContext, Predicate<Entity> entityFilter) {
        double distance = getReachDistance(spellContext);
        RayCastContext context = new RayCastContext(spellContext.getPos(), spellContext.getPos().add(spellContext.getVec().scale(distance)))
                .setEntityFilter(entityFilter).setEntityCount(1).setEntitySize(0.2f).setEntityEnd(true);
        return RayCast.getHit(level, context);
    }

    public RayHitResult getEntityHit(Level level, SpellContext spellContext) {
        double distance = getReachDistance(spellContext);
        RayCastContext context = new RayCastContext(spellContext.getPos(), spellContext.getPos().add(spellContext.getVec().scale(distance)))
                .setEntityFilter(getStandardFilter(spellContext.getEntity())).setEntityCount(1).setEntitySize(0.2f).setEntityEnd(true);
        return RayCast.getHit(level, context);
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
