package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.api.spell.SpellComponent;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
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
        tag.put("vec", SpellContext.vecToTag(vec));
        tag.put("vecOld", SpellContext.vecToTag(vecOld));

        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        fadeTick = tag.getInt("fadeTick");
        useTick = tag.getInt("useTick");
        endTick = tag.getInt("endTick");
        if (first) {
            vec = SpellContext.vecFromTag(tag.getCompound("vec"));
            vecOld = SpellContext.vecFromTag(tag.getCompound("vecOld"));
            first = false;
        }
    }
}
