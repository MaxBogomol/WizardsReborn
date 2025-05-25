package mod.maxbogomol.wizards_reborn.common.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public interface IFireworkModifier {
    Capability<IFireworkModifier> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {});

    boolean isJump();
    void addJump(float jump);
    void addJump(float jump, float maxJump);
    void removeJump(float jump);
    void setJump(float jump);
    float getJump();
}
