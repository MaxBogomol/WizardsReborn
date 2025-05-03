package mod.maxbogomol.wizards_reborn.client.model.sniffalo;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SniffaloArcaneArmorModel extends SniffaloArmorModel {

    public SniffaloArcaneArmorModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = createBodyLayer(mesh);

        PartDefinition root1 = root.addOrReplaceChild("root", new CubeListBuilder(), PartPose.offset(0.0F, 5.0F, 0.0F));
        PartDefinition bone = root1.addOrReplaceChild("bone", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition body = bone.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition head = body.addOrReplaceChild("head", new CubeListBuilder(), PartPose.offset(0.0F, 6.5F, -19.48F));

        PartDefinition right_front_leg = bone.addOrReplaceChild("right_front_leg", new CubeListBuilder(), PartPose.offset(-7.5F, 10.0F, -15.0F));
        PartDefinition right_mid_leg = bone.addOrReplaceChild("right_mid_leg", new CubeListBuilder(), PartPose.offset(-7.5F, 10.0F, 0.0F));
        PartDefinition right_hind_leg = bone.addOrReplaceChild("right_hind_leg", new CubeListBuilder(),PartPose.offset(-7.5F, 10.0F, 15.0F));
        PartDefinition left_front_leg = bone.addOrReplaceChild("left_front_leg", new CubeListBuilder(), PartPose.offset(7.5F, 10.0F, -15.0F));
        PartDefinition left_mid_leg = bone.addOrReplaceChild("left_mid_leg", new CubeListBuilder(), PartPose.offset(7.5F, 10.0F, 0.0F));
        PartDefinition left_hind_leg = bone.addOrReplaceChild("left_hind_leg", new CubeListBuilder(), PartPose.offset(7.5F, 10.0F, 15.0F));

        PartDefinition hat = head.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.55F, -10F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
        PartDefinition hat_edge = hat.addOrReplaceChild("hat_edge", CubeListBuilder.create().texOffs(-10, 11).addBox(-5.0F, -7.55F, -11F, 10.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        PartDefinition armor = body.addOrReplaceChild("armor", CubeListBuilder.create().texOffs(32, 0).addBox(-12.5F, -14.0F, -20.0F, 25.0F, 24.0F, 40.0F, new CubeDeformation(0.65F)), PartPose.ZERO);
        PartDefinition armor_layer = body.addOrReplaceChild("armor_layer", CubeListBuilder.create().texOffs(32, 64).addBox(-12.5F, -14.0F, -20.0F, 25.0F, 24.0F, 40.0F, new CubeDeformation(0.9F)), PartPose.ZERO);

        PartDefinition right_front_leg_layer = right_front_leg.addOrReplaceChild("right_front_leg_layer", CubeListBuilder.create().texOffs(0, 21).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        PartDefinition right_mid_leg_layer = right_mid_leg.addOrReplaceChild("right_mid_leg_layer", CubeListBuilder.create().texOffs(0, 21).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        PartDefinition right_hind_leg_layer = right_hind_leg.addOrReplaceChild("right_hind_leg_layer", CubeListBuilder.create().texOffs(0, 21).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        PartDefinition left_front_leg_layer = left_front_leg.addOrReplaceChild("left_front_leg_layer", CubeListBuilder.create().texOffs(0, 21).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        PartDefinition left_mid_leg_layer = left_mid_leg.addOrReplaceChild("left_mid_leg_layer", CubeListBuilder.create().texOffs(0, 21).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        PartDefinition left_hind_leg_layer = left_hind_leg.addOrReplaceChild("left_hind_leg_layer", CubeListBuilder.create().texOffs(0, 21).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 192, 192);
    }
}
