package mod.maxbogomol.wizards_reborn.common.spell.projectile;

import mod.maxbogomol.fluffy_fur.client.render.trail.TrailPointBuilder;
import mod.maxbogomol.wizards_reborn.api.spell.SpellComponent;
import net.minecraft.nbt.CompoundTag;

public class ProjectileSpellComponent extends SpellComponent {
    public TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(20);
    public boolean fade = false;
    public int fadeTick = 0;

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("fade", fade);
        tag.putInt("fadeTick", fadeTick);
        return tag;
    }

    public void fromTag(CompoundTag tag) {
        fade = tag.getBoolean("fade");
        fadeTick = tag.getInt("fadeTick");
    }
}
