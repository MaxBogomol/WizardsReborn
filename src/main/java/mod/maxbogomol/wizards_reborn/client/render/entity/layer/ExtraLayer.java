package mod.maxbogomol.wizards_reborn.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExtraLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    public ExtraLayer(RenderLayerParent<T, M> pRenderer) {
        super(pRenderer);
    }

    public void render(PoseStack ms, MultiBufferSource buffer, int pPackedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float betHeadYaw, float headPitch) {
        Minecraft mc = Minecraft.getInstance();
    }
}
