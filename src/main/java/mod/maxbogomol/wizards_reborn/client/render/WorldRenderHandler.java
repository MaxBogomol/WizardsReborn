package mod.maxbogomol.wizards_reborn.client.render;

import com.mojang.blaze3d.systems.RenderSystem;;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class WorldRenderHandler {
    @OnlyIn(Dist.CLIENT)
    public static Matrix4f particleMVMatrix = null;

    public static void onRenderWorldLast(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            if (ClientConfig.BETTER_LAYERING.get()) {
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
            }
        }
    }

    static MultiBufferSource.BufferSource DELAYED_RENDER = null;

    public static MultiBufferSource.BufferSource getDelayedRender() {
        if (DELAYED_RENDER == null) {
            Map<RenderType, BufferBuilder> buffers = new HashMap<>();
            for (RenderType type : new RenderType[]{
                    RenderUtils.DELAYED_PARTICLE,
                    RenderUtils.GLOWING_PARTICLE}) {
                buffers.put(type, new BufferBuilder(type.bufferSize()));
            }
            DELAYED_RENDER = MultiBufferSource.immediateWithBuffers(buffers, new BufferBuilder(256));
        }
        return DELAYED_RENDER;
    }
}
