package mod.maxbogomol.wizards_reborn.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class FireworkModifierImpl implements IFireworkModifier, INBTSerializable<CompoundTag> {
    float jump = 0;

    @Override
    public boolean isJump() {
        return jump > 0;
    }

    @Override
    public void addJump(float jump) {
        this.jump = this.jump + jump;
    }

    @Override
    public void addJump(float jump, float maxJump) {
        this.jump = this.jump + jump;
        if (this.jump > maxJump) {
            this.jump = maxJump;
        }
    }

    @Override
    public void removeJump(float jump) {
        this.jump = this.jump - jump;
        if (this.jump < 0) {
            this.jump = 0;
        }
    }

    @Override
    public void setJump(float jump) {
        this.jump = jump;
    }

    @Override
    public float getJump() {
        return jump;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag wrapper = new CompoundTag();
        wrapper.putFloat("jump", getJump());

        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if ((nbt).contains("jump")) {
            setJump(nbt.getFloat("jump"));
        }
    }
}
