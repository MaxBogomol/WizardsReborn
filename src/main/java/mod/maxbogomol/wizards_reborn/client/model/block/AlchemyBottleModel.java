package mod.maxbogomol.wizards_reborn.client.model.block;

import mod.maxbogomol.fluffy_fur.client.model.block.CustomBlockModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class AlchemyBottleModel extends CustomBlockModel {

    public AlchemyBottleModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition model = root.addOrReplaceChild("model", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -8.0F, -2.0F, 4, 8, 4, new CubeDeformation(0F)), PartPose.ZERO);
        PartDefinition model1 = model.addOrReplaceChild("model1", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -10.0F, -1.0F, 2, 3, 2, new CubeDeformation(0F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 32, 32);
    }
}
