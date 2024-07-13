package mod.maxbogomol.wizards_reborn.client.model.curio;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BagModel extends BeltModel {
    public ModelPart root, model;

    public BagModel(ModelPart root) {
        super(root);
        this.root = root;
        this.model = root.getChild("body").getChild("model");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = mesh.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition body = root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);

        PartDefinition model = body.addOrReplaceChild("model", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, 9.0F, -2.0F, 8, 2, 4, new CubeDeformation(0.3F)), PartPose.ZERO);
        PartDefinition model1 = model.addOrReplaceChild("model1", CubeListBuilder.create().texOffs(0, 6).addBox(-4.0F, 7.0F, -2.0F, 8, 6, 4, new CubeDeformation(0.5F)), PartPose.ZERO);

        PartDefinition model2 = model.addOrReplaceChild("model2", CubeListBuilder.create().texOffs(0, 16).addBox(-4.1F, 8.0F, -4.0F, 4, 3, 3, new CubeDeformation(0F)), PartPose.rotation(0.043625F, 0.02F, 0.08725F));
        PartDefinition model3 = model.addOrReplaceChild("model3", CubeListBuilder.create().texOffs(0, 22).addBox(-4.1F, 8.0F, -4.0F, 4, 3, 3, new CubeDeformation(0.15F)), PartPose.rotation(0.043625F, 0.02F, 0.08725F));

        PartDefinition model4 = model.addOrReplaceChild("model4", CubeListBuilder.create().texOffs(15, 19).addBox(1.5F, 8.0F, -3.1F, 1, 1, 1, new CubeDeformation(0.25F)), PartPose.rotation(-0.043625F, -0.1F, -0.08725F));
        PartDefinition model5 = model.addOrReplaceChild("model5", CubeListBuilder.create().texOffs(15, 21).addBox(1.0F, 9.0F, -3.6F, 2, 2, 2, new CubeDeformation(0F)), PartPose.rotation(-0.043625F, -0.1F, -0.08725F));

        return LayerDefinition.create(mesh, 32, 32);
    }
}
