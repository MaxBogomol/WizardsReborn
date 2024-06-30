package mod.maxbogomol.wizards_reborn.client.model.sniffalo;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import net.minecraft.client.model.SnifferModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public abstract class SniffaloArmorModel<T extends SniffaloEntity> extends SnifferModel {
    final ModelPart root;
    final ModelPart root1;
    final ModelPart bone;
    final ModelPart body;
    final ModelPart rightFrontLeg;
    final ModelPart rightMidLeg;
    final ModelPart rightHindLeg;
    final ModelPart leftFrontLeg;
    final ModelPart leftMidLeg;
    final ModelPart leftHindLeg;
    final ModelPart head;
    final ModelPart leftEar;
    final ModelPart rightEar;
    final ModelPart nose;
    final ModelPart lowerBeak;

    public SniffaloArmorModel(ModelPart root) {
        super(root);
        this.root = root;
        this.root1 = root.getChild("root");
        this.bone = root1.getChild("bone");
        this.body = bone.getChild("body");
        this.rightFrontLeg = bone.getChild("right_front_leg");
        this.rightMidLeg = bone.getChild("right_mid_leg");
        this.rightHindLeg = bone.getChild("right_hind_leg");
        this.leftFrontLeg = bone.getChild("left_front_leg");
        this.leftMidLeg = bone.getChild("left_mid_leg");
        this.leftHindLeg = bone.getChild("left_hind_leg");
        this.head = body.getChild("head");
        this.leftEar = head.getChild("left_ear");
        this.rightEar = head.getChild("right_ear");
        this.nose = head.getChild("nose");
        this.lowerBeak = head.getChild("lower_beak");
    }

    public static PartDefinition createBodyLayer(MeshDefinition mesh) {
        PartDefinition root = mesh.getRoot();
        PartDefinition root1 = root.addOrReplaceChild("root", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition bone = root1.addOrReplaceChild("bone", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition body = bone.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
        bone.addOrReplaceChild("right_front_leg", new CubeListBuilder(), PartPose.ZERO);
        bone.addOrReplaceChild("right_mid_leg", new CubeListBuilder(), PartPose.ZERO);
        bone.addOrReplaceChild("right_hind_leg", new CubeListBuilder(), PartPose.ZERO);
        bone.addOrReplaceChild("left_front_leg", new CubeListBuilder(), PartPose.ZERO);
        bone.addOrReplaceChild("left_mid_leg", new CubeListBuilder(), PartPose.ZERO);
        bone.addOrReplaceChild("left_hind_leg", new CubeListBuilder(), PartPose.ZERO);
        PartDefinition head = body.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
        head.addOrReplaceChild("left_ear", new CubeListBuilder(), PartPose.ZERO);
        head.addOrReplaceChild("right_ear", new CubeListBuilder(), PartPose.ZERO);
        head.addOrReplaceChild("nose", new CubeListBuilder(), PartPose.ZERO);
        head.addOrReplaceChild("lower_beak", new CubeListBuilder(), PartPose.ZERO);

        return root;
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        super.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void copyFromDefault(SnifferModel model) {
        root1.copyFrom(model.root());
        bone.copyFrom(model.root().getChild("bone"));
        body.copyFrom(model.root().getChild("bone").getChild("body"));
        rightFrontLeg.copyFrom(model.root().getChild("bone").getChild("right_front_leg"));
        rightMidLeg.copyFrom(model.root().getChild("bone").getChild("right_mid_leg"));
        rightHindLeg.copyFrom(model.root().getChild("bone").getChild("right_hind_leg"));
        leftFrontLeg.copyFrom(model.root().getChild("bone").getChild("left_front_leg"));
        leftMidLeg.copyFrom(model.root().getChild("bone").getChild("left_mid_leg"));
        leftHindLeg.copyFrom(model.root().getChild("bone").getChild("left_hind_leg"));
        head.copyFrom(model.root().getChild("bone").getChild("body").getChild("head"));
        leftEar.copyFrom(model.root().getChild("bone").getChild("body").getChild("head").getChild("left_ear"));
        rightEar.copyFrom(model.root().getChild("bone").getChild("body").getChild("head").getChild("right_ear"));
        nose.copyFrom(model.root().getChild("bone").getChild("body").getChild("head").getChild("nose"));
        lowerBeak.copyFrom(model.root().getChild("bone").getChild("body").getChild("head").getChild("lower_beak"));
    }
}
