package mod.maxbogomol.wizards_reborn.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public interface IPlayerModifier {
    Capability<IPlayerModifier> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    boolean isFireworkJump();
    void addFireworkJump(float jump);
    void addFireworkJump(float jump, float maxJump);
    void removeFireworkJump(float jump);
    void setFireworkJump(float jump);
    float getFireworkJump();
}
