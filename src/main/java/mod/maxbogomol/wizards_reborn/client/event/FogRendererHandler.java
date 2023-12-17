package mod.maxbogomol.wizards_reborn.client.event;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class FogRendererHandler {
    public static int morEffectRoll = 0;
    public static float morEffectFov = 0;

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onFogRender(ViewportEvent.RenderFog event) {
            if (hasMorEffect() || morEffectRoll > 0) {
                event.setCanceled(true);
                event.setNearPlaneDistance(Mth.lerp(gerMorEffectFovTicks((float) event.getPartialTick()), event.getNearPlaneDistance(), 0f));
                event.setFarPlaneDistance(Mth.lerp(gerMorEffectFovTicks((float) event.getPartialTick()), event.getFarPlaneDistance(), 15f));
            }
        }

        @SubscribeEvent
        public static void onFogColor(ViewportEvent.ComputeFogColor event) {

        }

        @SubscribeEvent
        public static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
            if (hasMorEffect() || morEffectRoll > 0) {
                double ticks = gerMorEffectRollTicks((float) event.getPartialTick());
                event.setRoll(event.getRoll() + (float) (Math.sin(Math.toRadians(ticks)) * -10F));
                event.setYaw(event.getYaw() + (float) (Math.sin(Math.toRadians(ticks)) * 5F));
            }
        }

        @SubscribeEvent
        public static void onFov(ViewportEvent.ComputeFov event) {
            if (hasMorEffect() || morEffectFov > 0) {
                float fov = (float) (event.getFOV() - (20f * gerMorEffectFovTicks((float) event.getPartialTick())));
                if (fov < 5f) {
                    fov = 5f;
                }
                event.setFOV(fov);
            }
        }

        @SubscribeEvent
        public static void clientTickEnd(TickEvent.ClientTickEvent event) {
            if (!Minecraft.getInstance().isPaused()) {
                if (hasMorEffect()) {
                    morEffectRoll++;
                    morEffectRoll = morEffectRoll % 360;
                    if (morEffectFov < 1) {
                        morEffectFov = morEffectFov + (1f / 100f);
                    }
                    if (morEffectFov > 1) {
                        morEffectFov = 1;
                    }
                } else {
                    if (morEffectRoll > 0) {
                        morEffectRoll++;
                        morEffectRoll = morEffectRoll % 360;
                        if (morEffectRoll == 180) {
                            morEffectRoll = 0;
                        }
                    }
                    if (morEffectFov > 0) {
                        morEffectFov = morEffectFov - (1f / 100f);
                    }
                    if (morEffectFov < 0) {
                        morEffectFov = 0;
                    }
                }
            }
        }
    }

    public static boolean hasMorEffect() {
        if (Minecraft.getInstance().player != null) {
            return Minecraft.getInstance().player.hasEffect(WizardsReborn.MOR_EFFECT.get());
        }

        return false;
    }

    public static float gerMorEffectFovTicks(float partialTick) {
        float tick = (hasMorEffect() ? morEffectFov + (1f/100f * partialTick) : morEffectFov - (1f/100f * partialTick));

        if (tick > 1) {
            tick = 1;
        }
        if (tick < 0) {
            tick = 0;
        }

        return tick;
    }

    public static float gerMorEffectRollTicks(float partialTick) {
        float tick = (morEffectRoll + partialTick) * 2;
        tick = tick % 360;

        return tick;
    }
}