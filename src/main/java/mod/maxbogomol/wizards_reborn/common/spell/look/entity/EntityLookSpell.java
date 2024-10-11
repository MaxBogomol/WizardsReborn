package mod.maxbogomol.wizards_reborn.common.spell.look.entity;

import mod.maxbogomol.wizards_reborn.common.spell.look.LookSpell;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Predicate;

public class EntityLookSpell extends LookSpell {

    public EntityLookSpell(String id, int points) {
        super(id, points);
    }

    public float getLookDistance() {
        return 2f;
    }

/*    public float getReachDistance(Level level, Player player, InteractionHand hand) {
        if (hasReachDistance(level, player, hand)) return (float) (player.getAttributeValue(ForgeMod.ENTITY_REACH.get()) + getLookDistance(level, player, hand));
        return (getLookDistance(level, player, hand));
    }

    public boolean hasReachDistance(Level level, Player player, InteractionHand hand) {
        return true;
    }

    @Override
    public boolean canLookSpell(Level level, Player player, InteractionHand hand) {
        return getEntityHit(level, player, hand).hasEntities();
    }

    public HitResult getEntityHit(Level level, Player player, InteractionHand hand, Predicate<Entity> entityFilter, int entityCount, float size, boolean endE) {
        float distance = getReachDistance(level, player, hand);
        return getHitPos(level, player.getEyePosition(), player.getEyePosition().add(player.getLookAngle().scale(distance)), entityFilter, entityCount, size, endE);
    }

    public HitResult getEntityHit(Level level, Player player, InteractionHand hand, Predicate<Entity> entityFilter) {
        float distance = getReachDistance(level, player, hand);
        return getHitPos(level, player.getEyePosition(), player.getEyePosition().add(player.getLookAngle().scale(distance)), entityFilter, 1, 0.2f, true);
    }

    public HitResult getEntityHit(Level level, Player player, InteractionHand hand) {
        float distance = getReachDistance(level, player, hand);
        return getHitPos(level, player.getEyePosition(), player.getEyePosition().add(player.getLookAngle().scale(distance)), getStandardFilter(player), 1, 0.2f, true);
    }*/

    public static Predicate<Entity> getStandardFilter() {
        return (e) -> {return e instanceof LivingEntity;};
    }

    public static Predicate<Entity> getStandardFilter(Player player) {
        return (e) -> {
            if (!e.equals(player)) {
                return e instanceof LivingEntity;
            }
            return false;
        };
    }

    public static Predicate<Entity> getAllFilter() {
        return (e) -> {return true;};
    }

    public static Predicate<Entity> getAllFilter(Player player) {
        return (e) -> {
            return !e.equals(player);
        };
    }
}
