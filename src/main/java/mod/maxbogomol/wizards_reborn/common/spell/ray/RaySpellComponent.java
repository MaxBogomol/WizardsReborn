package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.wizards_reborn.api.spell.SpellComponent;
import net.minecraft.nbt.CompoundTag;

public class RaySpellComponent extends SpellComponent {
    public int fadeTick = 0;
    public int removeTick = 0;

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("fadeTick", removeTick);
        tag.putInt("removeTick", fadeTick);
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        fadeTick = tag.getInt("fadeTick");
        removeTick = tag.getInt("removeTick");
    }
}
