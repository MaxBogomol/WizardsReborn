package mod.maxbogomol.wizards_reborn.client.shader.postprocess;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.PostProcess;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.PostProcessInstanceData;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;

public class LightGlowPostProcess extends PostProcess {
    public static final LightGlowPostProcess INSTANCE = new LightGlowPostProcess();
    public final PostProcessInstanceData data = new LightGlowPostProcess.LightGlowPostProcessInstanceData();
    public EffectInstance effectInstance;
    public ResourceLocation shader = new ResourceLocation(WizardsReborn.MOD_ID, "shaders/post/light_glow.json");

    public LightGlowPostProcess addInstance(LightGlowPostProcessInstance instance) {
        data.addInstance(instance);
        setActive(true);
        return this;
    }

    @Override
    public void init() {
        super.init();
        if (postChain != null) {
            effectInstance = effects[0];
        }
        data.init();
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return shader;
    }

    @Override
    public void beforeProcess(PoseStack viewModelStack) {
        data.beforeProcess(viewModelStack);
        if (data.isEmpty()) {
            setActive(false);
        } else {
            data.setDataBufferUniform(effectInstance, "DataBuffer", "InstanceCount");
        }
    }

    @Override
    public void afterProcess() {
        data.instances.clear();
    }

    public static class LightGlowPostProcessInstanceData extends PostProcessInstanceData {

        @Override
        public int getMaxInstances() {
            return 1024;
        }

        @Override
        public int getDataSizePerInstance() {
            return 12;
        }
    }
}
