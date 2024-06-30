package mod.maxbogomol.wizards_reborn.client.model.sniffalo;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SniffaloSaddleArmorModel extends SniffaloArmorModel {

    public SniffaloSaddleArmorModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = createBodyLayer(mesh);

        PartDefinition root1 = root.addOrReplaceChild("root", new CubeListBuilder(), PartPose.offset(0.0F, 5.0F, 0.0F));
        PartDefinition bone = root1.addOrReplaceChild("bone", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition body = bone.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition saddle = body.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-12.5F, -14.0F, -20.0F, 25.0F, 24.0F, 40.0F, new CubeDeformation(0.75F)), PartPose.ZERO);
        PartDefinition saddle_layer = body.addOrReplaceChild("saddle_layer", CubeListBuilder.create().texOffs(0, 64)
                .addBox(-12.5F, -14.0F, -20.0F, 25.0F, 24.0F, 40.0F, new CubeDeformation(0.85F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 192, 192);
    }
}
