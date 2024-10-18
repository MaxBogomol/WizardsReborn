package mod.maxbogomol.wizards_reborn.api.spell;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class SpellContext {
    public Vec3 pos = Vec3.ZERO;
    public Vec3 vec = Vec3.ZERO;
    public Vec3 offset = Vec3.ZERO;
    public double distance = 0;
    public Level level;
    public Entity entity;
    public ItemStack itemStack = ItemStack.EMPTY;
    public CompoundTag stats = new CompoundTag();

    public static Random random = new Random();

    public SpellContext setPos(Vec3 pos) {
        this.pos = pos;
        return this;
    }

    public Vec3 getPos() {
        return pos;
    }

    public SpellContext setVec(Vec3 vec) {
        this.vec = vec;
        return this;
    }

    public Vec3 getVec() {
        return vec;
    }

    public SpellContext setOffset(Vec3 offset) {
        this.offset = offset;
        return this;
    }

    public Vec3 getOffset() {
        return offset;
    }

    public SpellContext setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public double getDistance() {
        return distance;
    }

    public SpellContext setLevel(Level level) {
        this.level = level;
        return this;
    }

    public SpellContext setEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    public SpellContext setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
        return this;
    }

    public SpellContext setStats(CompoundTag stats) {
        this.stats = stats;
        return this;
    }

    public Level getLevel() {
        return level;
    }

    public Entity getEntity() {
        return entity;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public CompoundTag getStats() {
        return stats;
    }

    public void setCooldown(Spell spell) {

    }

    public void setCooldown(Spell spell, int cost) {

    }

    public void removeWissen(Spell spell) {

    }

    public void removeWissen(Spell spell, int cost) {

    }

    public void removeWissen(int cost) {

    }

    public boolean canRemoveWissen(Spell spell) {
        return true;
    }

    public boolean canRemoveWissen(Spell spell, int cost) {
        return true;
    }

    public boolean canRemoveWissen(int cost) {
        return true;
    }

    public void spellSound(Spell spell) {

    }

    public void awardStat(Spell spell) {

    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("pos", vecToTag(pos));
        tag.put("vec", vecToTag(vec));
        tag.put("offset", vecToTag(offset));
        tag.putDouble("distance", distance);
        return tag;
    }

    public void fromTag(CompoundTag tag) {
        pos = vecFromTag(tag.getCompound("pos"));
        vec = vecFromTag(tag.getCompound("vec"));
        offset = vecFromTag(tag.getCompound("offset"));
        distance = tag.getDouble("distance");
    }

    public CompoundTag vecToTag(Vec3 vec) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("x", vec.x());
        tag.putDouble("y", vec.y());
        tag.putDouble("z", vec.z());
        return tag;
    }

    public Vec3 vecFromTag(CompoundTag tag) {
        double x = tag.getDouble("x");
        double y = tag.getDouble("y");
        double z = tag.getDouble("z");
        return new Vec3(x, y, z);
    }
}
