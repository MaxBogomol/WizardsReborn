package mod.maxbogomol.wizards_reborn.client.model.curio;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.LivingEntity;

public class BeltModel extends HumanoidModel  {
    public ModelPart root, model;

    public BeltModel(ModelPart root) {
        super(root);
        this.root = root;
        this.model = root.getChild("body").getChild("model");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = mesh.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition model = body.addOrReplaceChild("model", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 9.0F, -2.0F, 8, 2, 4, new CubeDeformation(0.3F)), PartPose.ZERO);
        PartDefinition model1 = model.addOrReplaceChild("model1", CubeListBuilder.create().texOffs(0, 6).addBox(-4.0F, 8.0F, -2.0F, 8, 4, 4, new CubeDeformation(0.5F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 32, 32);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.model.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
