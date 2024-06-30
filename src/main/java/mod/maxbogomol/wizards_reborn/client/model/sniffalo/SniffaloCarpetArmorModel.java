package mod.maxbogomol.wizards_reborn.client.model.sniffalo;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SniffaloCarpetArmorModel extends SniffaloArmorModel {

    public SniffaloCarpetArmorModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = createBodyLayer(mesh);

        PartDefinition root1 = root.addOrReplaceChild("root", new CubeListBuilder(), PartPose.offset(0.0F, 5.0F, 0.0F));
        PartDefinition bone = root1.addOrReplaceChild("bone", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition body = bone.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition carpet = body.addOrReplaceChild("carpet", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-14.5F, -23.0F, 10.0F, 29.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        PartDefinition carpet_layer = body.addOrReplaceChild("carpet_layer", CubeListBuilder.create().texOffs(0, 16)
                .addBox(-14.5F, -23.0F, 10.0F, 29.0F, 8.0F, 8.0F, new CubeDeformation(0.75F)), PartPose.ZERO);

        PartDefinition right_bottom_carpet = body.addOrReplaceChild("right_bottom_carpet", CubeListBuilder.create().texOffs(0, 32)
                .addBox(-13.5F, -14.0F, 10.0F, 1.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        PartDefinition right_bottom_carpet_layer = body.addOrReplaceChild("right_bottom_carpet_layer", CubeListBuilder.create().texOffs(0, 50)
                .addBox(-13.5F, -14.0F, 10.0F, 1.0F, 10.0F, 8.0F, new CubeDeformation(0.7F)), PartPose.ZERO);

        PartDefinition left_bottom_carpet = body.addOrReplaceChild("left_bottom_carpet", CubeListBuilder.create().texOffs(18, 32)
                .addBox(12.5F, -14.0F, 10.0F, 1.0F, 10.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        PartDefinition left_bottom_carpet_layer = body.addOrReplaceChild("left_bottom_carpet_layer", CubeListBuilder.create().texOffs(18, 50)
                .addBox(12.5F, -14.0F, 10.0F, 1.0F, 10.0F, 8.0F, new CubeDeformation(0.7F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 192, 192);
    }
}
