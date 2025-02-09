package mod.maxbogomol.wizards_reborn.client.shader.postprocess;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.PostProcess;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.effect.IrritationEffect;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.Random;

public class IrritationPostProcess extends PostProcess {
    public static final IrritationPostProcess INSTANCE = new IrritationPostProcess();
    public EffectInstance effectInstance;
    public ResourceLocation shader = new ResourceLocation(WizardsReborn.MOD_ID, "shaders/post/irritation.json");
    public static Random random = new Random();
    public int tick = 0;
    public int oldTick = 0;
    public float startTime = 0;

    @Override
    public void init() {
        super.init();
        if (postChain != null) {
            effectInstance = effects[0];
        }
        startTime = random.nextFloat() * 1000;
    }

    public void tickEffect() {
        oldTick = tick;
        if (IrritationEffect.hasEffect()) {
            setActive(true);
            if (tick < getMaxTick()) {
                tick = tick + 1;
            }
        } else {
            if (tick > 0) {
                tick = tick - 1;
            } else {
                setActive(false);
            }
        }
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return shader;
    }

    @Override
    public void beforeProcess(PoseStack poseStack) {
        double intensity =  WizardsRebornClientConfig.IRRITATION_SHADER_INTENSITY.get();
        float fade = (Mth.lerp(ClientTickHandler.partialTicks, oldTick, tick) / getMaxTick()) * (float) intensity;
        effectInstance.safeGetUniform("fade").set(fade);
        effectInstance.safeGetUniform("startTime").set(startTime);
    }

    @Override
    public void afterProcess() {

    }

    @Override
    public boolean isScreen() {
        return true;
    }

    public int getMaxTick() {
        return 40;
    }
}
