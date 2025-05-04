package mod.maxbogomol.wizards_reborn.client.model.block;

import mod.maxbogomol.fluffy_fur.client.model.block.CustomBlockModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CargoCarpetModel extends CustomBlockModel {

    public CargoCarpetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition carpet = root.addOrReplaceChild("carpet", CubeListBuilder.create().texOffs(0, 0).addBox(-14.5F, -8.0F, -4.0F, 29.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.ZERO);
        PartDefinition carpet_layer = root.addOrReplaceChild("carpet_layer", CubeListBuilder.create().texOffs(0, 16).addBox(-14.5F, -8.0F, -4.0F, 29.0F, 8.0F, 8.0F, new CubeDeformation(0.75F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 96, 96);
    }
}
