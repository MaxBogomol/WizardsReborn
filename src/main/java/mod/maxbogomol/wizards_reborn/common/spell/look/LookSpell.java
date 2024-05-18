package mod.maxbogomol.wizards_reborn.common.spell.look;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class LookSpell extends Spell {
    public LookSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public void useSpell(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);

            CompoundTag stats = getStats(stack);
            setCooldown(stack, stats);
            removeWissen(stack, stats, player);
            awardStat(player, stack);
            spellSound(player, world);
            lookSpell(world, player, hand);
        }
    }

    public boolean canSpell(Level world, Player player, InteractionHand hand) {
        if (super.canSpell(world, player, hand)) {
            return canLookSpell(world, player, hand);
        }
        return false;
    }

    public float getLookDistance() {
        return 5f;
    }

    public float getLookAdditionalDistance() {
        return 0f;
    }

    public float getLookDistance(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag stats = getStats(stack);

        int focusLevel = CrystalUtils.getStatLevel(stats, WizardsReborn.FOCUS_CRYSTAL_STAT);
        return getLookDistance() + (getLookAdditionalDistance() * focusLevel);
    }

    public HitResult getHitPos(Level world, Player player, InteractionHand hand, Vec3 start, Vec3 endPos) {
        float distance = (float) Math.sqrt(Math.pow(start.x() - endPos.x, 2) + Math.pow(start.y() - endPos.y, 2) + Math.pow(start.z() - endPos.z, 2));
        float X = (float) start.x();
        float Y = (float) start.y();
        float Z = (float) start.z();
        float oldX = X;
        float oldY = Y;
        float oldZ = Z;
        for (float i = 0; i < distance * 16; i++) {
            float dst = (distance * 16);

            double dX = start.x() - endPos.x();
            double dY = start.y() - endPos.y();
            double dZ = start.z() - endPos.z();

            float x = (float) -(dX / (dst)) * i;
            float y = (float) -(dY / (dst)) * i;
            float z = (float) -(dZ / (dst)) * i;

            X = (float) (start.x() + x);
            Y = (float) (start.y() + y);
            Z = (float) (start.z() + z);

            BlockPos blockPos = new BlockPos(Mth.floor(X), Mth.floor(Y), Mth.floor(Z));

            BlockHitResult blockHitResult = world.getBlockState(blockPos).getShape(world, blockPos).clip(start, endPos, blockPos);
            if (blockHitResult != null) {
                boolean isBlock = !world.getBlockState(blockHitResult.getBlockPos()).isAir();
                if (canHitSpell(world, player, hand, new Vec3(X, Y, Z))) {
                    return new HitResult(blockHitResult.getLocation(), isBlock);
                } else {
                    return new HitResult(new Vec3(oldX, oldY, oldZ), isBlock);
                }
            }

            oldX = X;
            oldY = Y;
            oldZ = Z;
        }
        return new HitResult(new Vec3(X, Y, Z), false);
    }

    public HitResult getHitPos(Level world, Player player, InteractionHand hand) {
        float distance = getLookDistance(world, player, hand);
        return getHitPos(world, player, hand, player.getEyePosition(), player.getEyePosition().add(player.getLookAngle().scale(distance)));
    }

    public boolean canHitSpell(Level world, Player player, InteractionHand hand, Vec3 pos) {
        return true;
    }

    public boolean canLookSpell(Level world, Player player, InteractionHand hand) {
        return true;
    }

    public void lookSpell(Level world, Player player, InteractionHand hand) {

    }

    public List<Entity> getHitEntities(Level world, Vec3 start, Vec3 endPos, float distance) {
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

            List<Entity> entityList = world.getEntitiesOfClass(Entity.class, new AABB(X - distance, Y - distance, Z - distance, X + distance, Y + distance, Z + distance));
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

        public HitResult(Vec3 posHit, boolean blockHit) {
            this.posHit = posHit;
            this.blockHit = blockHit;
        }

        public Vec3 getPosHit() {
            return posHit;
        }

        public boolean hasBlockHit() {
            return blockHit;
        }
    }
}
