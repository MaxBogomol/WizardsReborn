package mod.maxbogomol.wizards_reborn.client.event;

import net.minecraft.client.Camera;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class ScreenshakeHandler {
    public static float intensity;
    public static float yawOffset;
    public static float pitchOffset;

    public static void cameraTick(Camera camera, RandomSource random) {
        if (intensity >= 0) {
            yawOffset = randomizeOffset(random);
            pitchOffset = randomizeOffset(random);
            camera.setRotation(camera.getYRot() + yawOffset, camera.getXRot() + pitchOffset);
        }
    }

    public static void clientTick(Camera camera, RandomSource random) {
        if (intensity > 0) {
            intensity = intensity - 0.05f;
            if (intensity < 0) {
                intensity = 0;
            }
        }
    }

    public static void addScreenshake(float newIntensity) {
        if (newIntensity > intensity) {
            intensity = newIntensity;
        }
    }

    public static float randomizeOffset(RandomSource random) {
        return Mth.nextFloat(random, -intensity * 2, intensity * 2);
    }
}
