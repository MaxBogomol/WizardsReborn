package mod.maxbogomol.wizards_reborn.client.shader.postprocess;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.shader.postprocess.PostProcess;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class KnowledgePostProcess extends PostProcess {
    public static final KnowledgePostProcess INSTANCE = new KnowledgePostProcess();
    public EffectInstance effectInstance;
    public ResourceLocation shader = new ResourceLocation("shaders/post/deconverge.json");
    public int tick = 0;
    public int oldTick = 0;
    public boolean canTick = false;

    public KnowledgePostProcess enable() {
        canTick = true;
        setActive(true);
        return this;
    }

    @Override
    public void init() {
        super.init();
        if (postChain != null) {
            effectInstance = effects[0];
        }
    }

    @Override
    public void tick() {
        if (isActive) {
            oldTick = tick;
            if (canTick) {
                if (tick < getMaxTick()) {
                    tick = tick + 1;
                } else {
                    canTick = false;
                }
            } else {
                if (tick > 0) {
                    tick = tick - 1;
                } else {
                    setActive(false);
                }
            }
        }
    }

    @Override
    public ResourceLocation getPostChainLocation() {
        return shader;
    }

    @Override
    public void beforeProcess(PoseStack poseStack) {
        float offset = (float) Math.sin(Math.toRadians((Mth.lerp(ClientTickHandler.partialTicks, oldTick, tick) / getMaxTick()) * 90)) * getIntensity();
        effectInstance.safeGetUniform("ConvergeX").set(new Vector3f(-2f * offset, 0, 1 * offset));
        effectInstance.safeGetUniform("ConvergeY").set(new Vector3f(0, -2f * offset, 1 * offset));
    }

    @Override
    public void afterProcess() {

    }

    @Override
    public boolean isScreen() {
        return true;
    }

    public int getMaxTick() {
        return 50;
    }

    public float getIntensity() {
        return 5;
    }
}
