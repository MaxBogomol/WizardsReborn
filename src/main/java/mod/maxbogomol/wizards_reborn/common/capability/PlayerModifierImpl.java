package mod.maxbogomol.wizards_reborn.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerModifierImpl implements IPlayerModifier, INBTSerializable<CompoundTag> {
    float jump = 0;

    @Override
    public boolean isFireworkJump() {
        return jump > 0;
    }

    @Override
    public void addFireworkJump(float jump) {
        this.jump = this.jump + jump;
    }

    @Override
    public void addFireworkJump(float jump, float maxJump) {
        this.jump = this.jump + jump;
        if (this.jump > maxJump) {
            this.jump = maxJump;
        }
    }

    @Override
    public void removeFireworkJump(float jump) {
        this.jump = this.jump - jump;
        if (this.jump < 0) {
            this.jump = 0;
        }
    }

    @Override
    public void setFireworkJump(float jump) {
        this.jump = jump;
    }

    @Override
    public float getFireworkJump() {
        return jump;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag wrapper = new CompoundTag();
        wrapper.putFloat("jump", getFireworkJump());

        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("jump")) {
            setFireworkJump(nbt.getFloat("jump"));
        }
    }
}
