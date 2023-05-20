package mod.maxbogomol.wizards_reborn.client.render.model.curio;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import javax.annotation.Nonnull;

public class BeltModel<T extends LivingEntity> extends EntityModel<T> {
    public ModelRenderer model;

    public BeltModel() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.model = new ModelRenderer(this, 0, 0);
        this.model.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.model.setTextureOffset(0, 0).addBox(-4.0F, 9.0F, -2.0F, 8, 2, 4, 0.3F);
        this.model.setTextureOffset(0, 6).addBox(-4.0F, 8.0F, -2.0F, 8, 4, 4, 0.5F);
    }

    @Override
    public void setRotationAngles(@Nonnull T entity, float limbSwing, float limbSwingAmount,
                                  float ageInTicks, float netHeadYaw, float netHeadPitch) {

    }

    @Override
    public void render(@Nonnull MatrixStack matrixStack, @Nonnull IVertexBuilder vertexBuilder,
                       int light, int overlay, float red, float green, float blue, float alpha) {
        this.model.render(matrixStack, vertexBuilder, light, overlay);
    }
}
