package mod.maxbogomol.wizards_reborn.api.light;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public interface ILightBlockEntity {
    int getLight();
    int getMaxLight();

    boolean canSendLight();
    boolean canReceiveLight();

    boolean canConnectSendLight();
    boolean canConnectReceiveLight();

    void setLight(int light);
    void addLight(int light);
    void removeLight(int light);

    Vec3 getLightLensPos();
    float getLightLensSize();

    ArrayList<LightTypeStack> getLightTypes();
    void setLightTypes(ArrayList<LightTypeStack> lightTypes);
    void addLightType(LightTypeStack lightType);
    void removeLightType(LightTypeStack lightType);
    void clearLightType();
}
