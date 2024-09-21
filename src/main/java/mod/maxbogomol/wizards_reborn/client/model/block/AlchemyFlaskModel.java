package mod.maxbogomol.wizards_reborn.client.model.block;

import mod.maxbogomol.fluffy_fur.client.model.block.CustomBlockModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class AlchemyFlaskModel extends CustomBlockModel {

    public AlchemyFlaskModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition model = root.addOrReplaceChild("model", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -7.0F, -3.0F, 6, 7, 6, new CubeDeformation(0F)), PartPose.ZERO);
        PartDefinition model1 = model.addOrReplaceChild("model1", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, -8.0F, -2.0F, 4, 2, 4, new CubeDeformation(0F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 32, 32);
    }
}
