package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.api.spell.SpellComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public class RaySpellComponent extends SpellComponent {
    public int fadeTick = 3;
    public int useTick = 0;
    public int endTick = 0;

    public Vec3 vec = Vec3.ZERO;
    public Vec3 vecOld = Vec3.ZERO;

    public boolean first = true;

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("fadeTick", fadeTick);
        tag.putInt("useTick", useTick);
        tag.putInt("endTick", endTick);
        tag.putDouble("xv", vec.x());
        tag.putDouble("yv", vec.y());
        tag.putDouble("zv", vec.z());
        tag.putDouble("xvo", vecOld.x());
        tag.putDouble("yvo", vecOld.y());
        tag.putDouble("zvo", vecOld.z());

        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        fadeTick = tag.getInt("fadeTick");
        useTick = tag.getInt("useTick");
        endTick = tag.getInt("endTick");
        if (first) {
            double xv = tag.getDouble("xv");
            double yv = tag.getDouble("yv");
            double zv = tag.getDouble("zv");
            vec = new Vec3(xv, yv, zv);
            double xvo = tag.getDouble("xvo");
            double yvo = tag.getDouble("yvo");
            double zvo = tag.getDouble("zvo");
            vecOld = new Vec3(xvo, yvo, zvo);
            first = false;
        }
    }
}
