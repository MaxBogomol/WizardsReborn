package mod.maxbogomol.wizards_reborn.client.shader.postprocess;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.PostProcess;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.effect.MorSporesEffect;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class MorSporesPostProcess extends PostProcess {
    public static final MorSporesPostProcess INSTANCE = new MorSporesPostProcess();
    public EffectInstance effectInstance;
    public ResourceLocation shader = new ResourceLocation(WizardsReborn.MOD_ID, "shaders/post/mor_spores.json");
    public int tick = 0;
    public int oldTick = 0;

    @Override
    public void init() {
        super.init();
        if (postChain != null) {
            effectInstance = effects[0];
        }
    }

    public void tickEffect() {
        oldTick = tick;
        if (MorSporesEffect.hasEffect()) {
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
        double intensity =  WizardsRebornClientConfig.MOR_SPORES_SHADER_INTENSITY.get();
        float fade = (Mth.lerp(ClientTickHandler.partialTicks, oldTick, tick) / getMaxTick()) * (float) intensity;
        effectInstance.safeGetUniform("fade").set(fade);
    }

    @Override
    public void afterProcess() {

    }

    @Override
    public boolean isScreen() {
        return true;
    }

    @Override
    public float getPriority() {
        return 1000;
    }

    public int getMaxTick() {
        return 80;
    }
}
