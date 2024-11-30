package mod.maxbogomol.wizards_reborn.common.effect;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.shader.postprocess.MorSporesPostProcess;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;

public class MorSporesEffect extends MobEffect {
    public static float roll;
    public static float rollOld;
    public static float fov;
    public static float fovOld;

    public MorSporesEffect() {
        super(MobEffectCategory.HARMFUL, 0x6b668c);
    }

    @OnlyIn(Dist.CLIENT)
    public static void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        float angle = (float) Mth.lerp(event.getPartialTick(), Math.sin(Math.toRadians(rollOld)), Math.sin(Math.toRadians(roll)));
        if (hasEffect() || angle != 0) {
            event.setRoll(event.getRoll() + (angle * -10F));
            event.setYaw(event.getYaw() + (angle * 5F));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void onFov(ViewportEvent.ComputeFov event) {
        float angle = (float) Mth.lerp(event.getPartialTick(), fovOld / 100f, fov / 100f);
        double fov = event.getFOV();
        boolean changed = false;
        if (hasEffect() || angle > 0) {
            fov = (float) (fov - (20f * angle));
            if (fov < 5f) {
                fov = 5f;
            }
            changed = true;
        }
        if (changed) event.setFOV(fov);
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!Minecraft.getInstance().isPaused()) {
                rollOld = roll;
                fovOld = fov;
                if (hasEffect()) {
                    roll++;
                    roll = roll % 360;
                    if (fov < 100) {
                        fov++;
                    }
                } else {
                    if (roll != 0) {
                        if (roll > 180) {
                            roll++;
                        } else {
                            roll--;
                        }
                        roll = roll % 360;
                    }
                    if (fov > 0) {
                        fov--;
                    }
                }
                MorSporesPostProcess.INSTANCE.tickEffect();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean hasEffect() {
        if (WizardsReborn.proxy.getPlayer() != null) {
            return WizardsReborn.proxy.getPlayer().hasEffect(WizardsRebornMobEffects.MOR_SPORES.get());
        }
        return false;
    }
}
