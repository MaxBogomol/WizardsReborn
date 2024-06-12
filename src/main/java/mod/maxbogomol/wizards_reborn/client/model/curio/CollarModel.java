package mod.maxbogomol.wizards_reborn.client.model.curio;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class CollarModel extends HumanoidModel {
    public ModelPart root, model;

    public CollarModel(ModelPart root) {
        super(root);
        this.root = root;
        this.model = root.getChild("body").getChild("model");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 1);
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition body = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition model = body.addOrReplaceChild("model", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 0.0F, -2.0F, 8, 4, 4, new CubeDeformation(0.3F)), PartPose.ZERO);
        PartDefinition model1 = body.addOrReplaceChild("model1", CubeListBuilder.create().texOffs(0, 8).addBox(-4.0F, 0.0F, -2.0F, 8, 4, 4, new CubeDeformation(0.4F)), PartPose.ZERO);
        PartDefinition model2 = model.addOrReplaceChild("model2", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, 2F, -2.75F, 1, 1, 1, new CubeDeformation(0.15F)), PartPose.ZERO);
        PartDefinition model3 = model.addOrReplaceChild("model3", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, 2F, -2F, 3, 3, 1, new CubeDeformation(0.35F)), PartPose.ZERO);
        PartDefinition model4 = model.addOrReplaceChild("model4", CubeListBuilder.create().texOffs(8, 18).addBox(-2F, 2F, -2F, 4, 5, 1, new CubeDeformation(0.35F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(root.getChild("head"));
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(root.getChild("body"));
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.renderToBuffer(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
