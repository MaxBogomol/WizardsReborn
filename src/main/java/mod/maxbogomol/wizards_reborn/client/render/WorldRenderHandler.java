package mod.maxbogomol.wizards_reborn.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.client.particle.ICustomRenderParticle;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.fml.ModList;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldRenderHandler {
    @OnlyIn(Dist.CLIENT)
    public static Matrix4f particleMVMatrix = null;
    @OnlyIn(Dist.CLIENT)
    public static List<ICustomRenderParticle> particleList = new ArrayList<>();

    public static void onRenderWorldLast(RenderLevelStageEvent event) {
        PoseStack stack = event.getPoseStack();
        float partialTicks = event.getPartialTick();
        MultiBufferSource bufferDelayed = WorldRenderHandler.getDelayedRender();

        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            stack.pushPose();
            Vec3 pos = event.getCamera().getPosition();
            stack.translate(-pos.x, -pos.y, -pos.z);
            for (ICustomRenderParticle particle : particleList) {
                particle.render(stack, bufferDelayed, partialTicks);
            }
            stack.popPose();
            particleList.clear();
        }

        if (ClientConfig.BETTER_LAYERING.get()) {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
                RenderSystem.getModelViewStack().pushPose();
                RenderSystem.getModelViewStack().setIdentity();
                if (particleMVMatrix != null) RenderSystem.getModelViewStack().mulPoseMatrix(particleMVMatrix);
                RenderSystem.applyModelViewMatrix();
                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                getDelayedRender().endBatch(RenderUtils.DELAYED_PARTICLE);
                RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
                getDelayedRender().endBatch(RenderUtils.GLOWING_PARTICLE);
                RenderSystem.getModelViewStack().popPose();
                RenderSystem.applyModelViewMatrix();

                getDelayedRender().endBatch(RenderUtils.GLOWING_SPRITE);
                getDelayedRender().endBatch(RenderUtils.GLOWING);
            }
        }
    }

    static MultiBufferSource.BufferSource DELAYED_RENDER = null;

    public static MultiBufferSource.BufferSource getDelayedRender() {
        if (DELAYED_RENDER == null) {
            Map<RenderType, BufferBuilder> buffers = new HashMap<>();
            for (RenderType type : new RenderType[]{
                    RenderUtils.DELAYED_PARTICLE,
                    RenderUtils.GLOWING_PARTICLE,
                    RenderUtils.GLOWING,
                    RenderUtils.GLOWING_SPRITE}) {
                buffers.put(type, new BufferBuilder(ModList.get().isLoaded("rubidium") ? 32768 : type.bufferSize()));
            }
            DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(buffers, new BufferBuilder(128));
        }
        return DELAYED_RENDER;
    }
}
