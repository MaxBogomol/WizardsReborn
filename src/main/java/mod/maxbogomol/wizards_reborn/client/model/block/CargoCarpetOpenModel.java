package mod.maxbogomol.wizards_reborn.client.model.block;

import mod.maxbogomol.fluffy_fur.client.model.block.CustomBlockModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CargoCarpetOpenModel extends CustomBlockModel {

    public CargoCarpetOpenModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition carpet = root.addOrReplaceChild("carpet", CubeListBuilder.create().texOffs(-33, 0).addBox(-16.5F, 0.0F, -16.5F, 33.0F, 0.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.ZERO);
        PartDefinition carpet_layer = root.addOrReplaceChild("carpet_layer", CubeListBuilder.create().texOffs(-33, 33).addBox(-16.5F, -0.25F, -16.5F, 33.0F, 0.0F, 33.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

        return LayerDefinition.create(mesh, 96, 96);
    }
}
