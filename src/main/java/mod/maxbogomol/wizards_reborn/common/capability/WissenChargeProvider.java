package mod.maxbogomol.wizards_reborn.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class WissenChargeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    WissenChargeImpl impl = new WissenChargeImpl();

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == WissenChargeImpl.INSTANCE) return (LazyOptional<T>) LazyOptional.of(() -> impl);
        else return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return impl.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        impl.deserializeNBT(nbt);
    }
}