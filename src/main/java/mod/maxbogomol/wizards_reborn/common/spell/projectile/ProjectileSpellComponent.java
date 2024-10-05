package mod.maxbogomol.wizards_reborn.common.spell.projectile;

import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPointBuilder;
import mod.maxbogomol.wizards_reborn.api.spell.SpellComponent;
import net.minecraft.nbt.CompoundTag;

public class ProjectileSpellComponent extends SpellComponent {
    public TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(getTrailSize());
    public boolean fade = false;
    public int fadeTick = 0;

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("fade", fade);
        tag.putInt("fadeTick", fadeTick);
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        fade = tag.getBoolean("fade");
        fadeTick = tag.getInt("fadeTick");
    }

    public int getTrailSize() {
        return 20;
    }
}
