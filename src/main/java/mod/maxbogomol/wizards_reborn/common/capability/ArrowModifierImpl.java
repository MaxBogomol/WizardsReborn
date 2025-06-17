package mod.maxbogomol.wizards_reborn.common.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class ArrowModifierImpl implements IArrowModifier, INBTSerializable<CompoundTag> {
    int charge = 0;

    @Override
    public boolean isCharged() {
        return charge > 0;
    }

    @Override
    public void addCharge(int charge) {
        this.charge = this.charge + charge;
    }

    @Override
    public void addCharge(int charge, int maxCharge) {
        this.charge = this.charge + charge;
        if (this.charge > maxCharge) {
            this.charge = maxCharge;
        }
    }

    @Override
    public void removeCharge(int charge) {
        this.charge = this.charge - charge;
        if (this.charge < 0) {
            this.charge = 0;
        }
    }

    @Override
    public void setCharge(int charge) {
        this.charge = charge;
    }

    @Override
    public int getCharge() {
        return charge;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag wrapper = new CompoundTag();
        wrapper.putInt("charge", getCharge());

        return wrapper;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("charge")) {
            setCharge(nbt.getInt("charge"));
        }
    }
}
