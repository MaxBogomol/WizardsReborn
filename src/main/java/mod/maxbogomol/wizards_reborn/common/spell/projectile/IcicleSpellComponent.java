package mod.maxbogomol.wizards_reborn.common.spell.projectile;

import net.minecraft.nbt.CompoundTag;

public class IcicleSpellComponent extends ProjectileSpellComponent {
    public boolean shard = false;

    @Override
    public CompoundTag toTag() {
        CompoundTag tag = super.toTag();
        tag.putBoolean("shard", shard);
        return tag;
    }

    @Override
    public void fromTag(CompoundTag tag) {
        super.fromTag(tag);
        shard = tag.getBoolean("shard");
    }

    @Override
    public int getTrailSize() {
        return 10;
    }
}
