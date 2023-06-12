package mod.maxbogomol.wizards_reborn.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.util.HashMap;
import java.util.Map;

public class WorldRenderHandler {

    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        if (ClientConfig.BETTER_LAYERING.get()) {
            RenderSystem.pushMatrix();
            RenderSystem.multMatrix(event.getMatrixStack().getLast().getMatrix());
            getDelayedRender().finish(RenderUtils.GLOWING_PARTICLE);
            RenderSystem.popMatrix();
        }
    }

    static IRenderTypeBuffer.Impl DELAYED_RENDER = null;

    public static IRenderTypeBuffer.Impl getDelayedRender() {
        if (DELAYED_RENDER == null) {
            Map<RenderType, BufferBuilder> buffers = new HashMap<>();
            for (RenderType type : new RenderType[]{
                    RenderUtils.GLOWING_PARTICLE}) {
                buffers.put(type, new BufferBuilder(type.getBufferSize()));
            }
            DELAYED_RENDER = IRenderTypeBuffer.getImpl(buffers, new BufferBuilder(256));
        }
        return DELAYED_RENDER;
    }
}
