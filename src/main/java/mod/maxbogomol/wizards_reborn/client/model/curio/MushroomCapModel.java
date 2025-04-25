package mod.maxbogomol.wizards_reborn.client.model.curio;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class MushroomCapModel extends HumanoidModel  {
    public ModelPart root, model;

    public MushroomCapModel(ModelPart root) {
        super(root);
        this.root = root;
        this.model = root.getChild("head").getChild("model");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition body = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition model = head.addOrReplaceChild("model", CubeListBuilder.create().texOffs(0, 18).addBox(-5.0F, -7.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.05F)), PartPose.ZERO);
        PartDefinition model1 = model.addOrReplaceChild("model1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -13.0F, -6.0F, 12.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 64, 32);
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
