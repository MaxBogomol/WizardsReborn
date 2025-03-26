package mod.maxbogomol.wizards_reborn.api.spell;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class SpellContext {
    public Vec3 pos = Vec3.ZERO;
    public Vec3 vec = Vec3.ZERO;
    public Vec3 offset = Vec3.ZERO;
    public BlockPos blockPos = BlockPos.ZERO;
    public Direction direction = Direction.UP;
    public double distance = 0;
    public boolean alternative = false;
    public Level level;
    public Entity entity;
    public boolean mainHand = true;
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

    public SpellContext setBlockPos(BlockPos blockPos) {
        this.blockPos = blockPos;
        return this;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public SpellContext setDirection(Direction direction) {
        this.direction = direction;
        return this;
    }

    public Direction getDirection() {
        return direction;
    }

    public SpellContext setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public double getDistance() {
        return distance;
    }

    public SpellContext setAlternative(boolean alternative) {
        this.alternative = alternative;
        return this;
    }

    public boolean getAlternative() {
        return alternative;
    }

    public SpellContext setLevel(Level level) {
        this.level = level;
        return this;
    }

    public SpellContext setEntity(Entity entity) {
        this.entity = entity;
        return this;
    }

    public SpellContext setMainHand(InteractionHand hand) {
        this.mainHand = hand == InteractionHand.MAIN_HAND;
        return this;
    }

    public SpellContext setMainHand(boolean hand) {
        this.mainHand = hand;
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

    public boolean getMainHand() {
        return mainHand;
    }

    public InteractionHand getMainInteractionHand() {
        return getMainHand() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
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

    public void startUsing(Spell spell) {

    }

    public void stopUsing(Spell spell) {

    }

    public CompoundTag getSpellData() {
        return new CompoundTag();
    }

    public void setSpellData(CompoundTag spellData) {

    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.put("pos", vecToTag(pos));
        tag.put("vec", vecToTag(vec));
        tag.put("offset", vecToTag(offset));
        tag.put("blockPos", blockPosToTag(blockPos));
        tag.putInt("direction", direction.get3DDataValue());
        tag.putDouble("distance", distance);
        tag.putBoolean("alternative", alternative);
        tag.putBoolean("mainHand", mainHand);
        return tag;
    }

    public void fromTag(CompoundTag tag) {
        pos = vecFromTag(tag.getCompound("pos"));
        vec = vecFromTag(tag.getCompound("vec"));
        offset = vecFromTag(tag.getCompound("offset"));
        blockPos = blockPosFromTag(tag.getCompound("blockPos"));
        direction = Direction.from3DDataValue(tag.getInt("distance"));
        distance = tag.getDouble("distance");
        alternative = tag.getBoolean("alternative");
        mainHand = tag.getBoolean("mainHand");
    }

    public void copy(SpellContext context) {
        this.pos = context.pos;
        this.offset = context.offset;
        this.blockPos = context.blockPos;
        this.direction = context.direction;
        this.distance = context.distance;
        this.alternative = context.alternative;
        this.level = context.level;
        this.entity = context.entity;
        this.mainHand = context.mainHand;
        this.itemStack = context.itemStack;
        this.stats = context.stats;
    }

    public static CompoundTag vecToTag(Vec3 vec) {
        CompoundTag tag = new CompoundTag();
        tag.putDouble("x", vec.x());
        tag.putDouble("y", vec.y());
        tag.putDouble("z", vec.z());
        return tag;
    }

    public static Vec3 vecFromTag(CompoundTag tag) {
        double x = tag.getDouble("x");
        double y = tag.getDouble("y");
        double z = tag.getDouble("z");
        return new Vec3(x, y, z);
    }

    public static CompoundTag blockPosToTag(BlockPos blockPos) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("x", blockPos.getX());
        tag.putInt("y", blockPos.getY());
        tag.putInt("z", blockPos.getZ());
        return tag;
    }

    public static BlockPos blockPosFromTag(CompoundTag tag) {
        int x = tag.getInt("x");
        int y = tag.getInt("y");
        int z = tag.getInt("z");
        return new BlockPos(x, y, z);
    }
}
