package mod.maxbogomol.wizards_reborn.common.spell.charge;

import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPointBuilder;
import mod.maxbogomol.wizards_reborn.api.spell.SpellComponent;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public class ChargeSpellComponent extends SpellComponent {
    public TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(getTrailSize());
    public boolean fade = false;
    public int fadeTick = 0;

    public int tick = 0;
    public int useTick = 0;
    public int endTick = 0;

    public Vec3 vec = Vec3.ZERO;
    public Vec3 vecOld = Vec3.ZERO;

    public double charge = 0;
    public boolean throwed = false;

    public boolean first = true;

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("fade", fade);
        tag.putInt("tick", tick);
        tag.putInt("fadeTick", fadeTick);
        tag.putInt("useTick", useTick);
        tag.putInt("endTick", endTick);
        tag.put("vec", SpellContext.vecToTag(vec));
        tag.put("vecOld", SpellContext.vecToTag(vecOld));
        tag.putDouble("charge", charge);
        tag.putBoolean("throwed", throwed);

        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        fade = tag.getBoolean("fade");
        tick = tag.getInt("tick");
        fadeTick = tag.getInt("fadeTick");
        useTick = tag.getInt("useTick");
        endTick = tag.getInt("endTick");
        charge = tag.getDouble("charge");
        throwed = tag.getBoolean("throwed");
        if (first) {
            vec = SpellContext.vecFromTag(tag.getCompound("vec"));
            vecOld = SpellContext.vecFromTag(tag.getCompound("vecOld"));
            first = false;
        }
    }

    public int getTrailSize() {
        return 20;
    }
}
