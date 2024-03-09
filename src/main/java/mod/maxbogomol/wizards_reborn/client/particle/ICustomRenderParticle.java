package mod.maxbogomol.wizards_reborn.client.particle;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;

public interface ICustomRenderParticle {
    void render(PoseStack stack, MultiBufferSource buffer, float partialTicks);
}
