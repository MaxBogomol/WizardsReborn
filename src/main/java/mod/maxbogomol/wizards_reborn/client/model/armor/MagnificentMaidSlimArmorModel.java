package mod.maxbogomol.wizards_reborn.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MagnificentMaidSlimArmorModel extends MagnificentMaidArmorModel {

    public MagnificentMaidSlimArmorModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = createHumanoidAlias(mesh);

        rootAdditional(root);
        rootNormalBodyLayer(root);
        rootArmsBodyLayer(root);

        return LayerDefinition.create(mesh, 128, 64);
    }

    public static void rootArmsBodyLayer(PartDefinition root) {
        PartDefinition right_arm = root.getChild("right_arm");
        PartDefinition left_arm = root.getChild("left_arm");

        PartDefinition right_arm_armor = right_arm.addOrReplaceChild("right_arm_armor", CubeListBuilder.create().texOffs(56, 0)
                .addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.52F)), PartPose.ZERO);
        PartDefinition right_arm_layer_armor = right_arm_armor.addOrReplaceChild("right_arm_layer_armor", CubeListBuilder.create().texOffs(72, 0)
                .addBox(-2.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.72F)), PartPose.ZERO);

        PartDefinition left_arm_armor = left_arm.addOrReplaceChild("left_arm_armor", CubeListBuilder.create().texOffs(56, 16)
                .addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.52F)), PartPose.ZERO);
        PartDefinition left_arm_layer_armor = left_arm_armor.addOrReplaceChild("left_arm_layer_armor", CubeListBuilder.create().texOffs(72, 16)
                .addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.72F)), PartPose.ZERO);
    }
}
