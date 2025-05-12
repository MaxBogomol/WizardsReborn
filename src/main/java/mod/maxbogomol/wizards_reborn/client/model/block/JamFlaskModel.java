package mod.maxbogomol.wizards_reborn.client.model.block;

import mod.maxbogomol.fluffy_fur.client.model.block.CustomBlockModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class JamFlaskModel extends CustomBlockModel {

    public JamFlaskModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer(int uses) {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        if (uses == 0) {
            PartDefinition model = root.addOrReplaceChild("model", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -7.0F, -3.0F, 6, 7, 6, new CubeDeformation(0F)), PartPose.ZERO);
        }

        if (uses == 1) {
            PartDefinition model = root.addOrReplaceChild("model", CubeListBuilder.create().texOffs(0, 13).addBox(-3.0F, -6.0F, -3.0F, 6, 6, 6, new CubeDeformation(0F)), PartPose.ZERO);
        }

        if (uses == 2) {
            PartDefinition model = root.addOrReplaceChild("model", CubeListBuilder.create().texOffs(0, 25).addBox(-3.0F, -5.0F, -3.0F, 6, 5, 6, new CubeDeformation(0F)), PartPose.ZERO);
        }

        if (uses == 3) {
            PartDefinition model = root.addOrReplaceChild("model", CubeListBuilder.create().texOffs(24, 0).addBox(-3.0F, -4.0F, -3.0F, 6, 4, 6, new CubeDeformation(0F)), PartPose.ZERO);
        }

        if (uses == 4) {
            PartDefinition model = root.addOrReplaceChild("model", CubeListBuilder.create().texOffs(24, 10).addBox(-3.0F, -3.0F, -3.0F, 6, 3, 6, new CubeDeformation(0F)), PartPose.ZERO);
        }

        if (uses == 5) {
            PartDefinition model = root.addOrReplaceChild("model", CubeListBuilder.create().texOffs(24, 19).addBox(-3.0F, -2.0F, -3.0F, 6, 2, 6, new CubeDeformation(0F)), PartPose.ZERO);
        }

        return LayerDefinition.create(mesh, 48, 48);
    }
}
