package mod.maxbogomol.wizards_reborn.common.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class KnowledgeProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    KnowledgeImpl impl = new KnowledgeImpl();

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == IKnowledge.INSTANCE) return (LazyOptional<T>) LazyOptional.of(() -> impl);
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