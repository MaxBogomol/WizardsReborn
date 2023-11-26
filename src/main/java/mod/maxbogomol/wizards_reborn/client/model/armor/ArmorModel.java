package mod.maxbogomol.wizards_reborn.client.model.armor;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.EquipmentSlot;

public abstract class ArmorModel extends HumanoidModel {
    public EquipmentSlot slot;
    final ModelPart root;
    final ModelPart head;
    final ModelPart body;
    final ModelPart leftArm;
    final ModelPart rightArm;
    final ModelPart pelvis;
    final ModelPart leftLegging;
    final ModelPart rightLegging;
    final ModelPart leftFoot;
    final ModelPart rightFoot;

    public ArmorModel(ModelPart root) {
        super(root);
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.pelvis = root.getChild("pelvis");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
        this.leftLegging = root.getChild("left_legging");
        this.rightLegging = root.getChild("right_legging");
        this.leftFoot = root.getChild("left_foot");
        this.rightFoot = root.getChild("right_foot");
    }

    public static PartDefinition createHumanoidAlias(MeshDefinition mesh) {
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("pelvis", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("head", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("left_legging", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("left_foot", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("right_legging", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("right_foot", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("left_arm", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("right_arm", new CubeListBuilder(), PartPose.ZERO);

        return root;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return slot == EquipmentSlot.HEAD ? ImmutableList.of(head) : ImmutableList.of();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        if (slot == EquipmentSlot.CHEST) {
            return ImmutableList.of(body, leftArm, rightArm);
        }
        else if (slot == EquipmentSlot.LEGS) {
            return ImmutableList.of(leftLegging, rightLegging, pelvis);
        }
        else if (slot == EquipmentSlot.FEET) {
            return ImmutableList.of(leftFoot, rightFoot);
        }
        else return ImmutableList.of();
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        super.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void copyFromDefault(HumanoidModel model) {
        body.copyFrom(model.body);
        pelvis.copyFrom(model.body);
        leftLegging.copyFrom(model.leftLeg);
        rightLegging.copyFrom(model.rightLeg);
        leftFoot.copyFrom(model.leftLeg);
        rightFoot.copyFrom(model.rightLeg);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}