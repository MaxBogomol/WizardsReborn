package mod.maxbogomol.wizards_reborn.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public interface IWissenCharge {
    Capability<IWissenCharge> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    boolean isCharged();
    void addCharge(int charge);
    void addCharge(int charge, int maxCharge);
    void removeCharge(int charge);
    void setCharge(int charge);
    int getCharge();
}
