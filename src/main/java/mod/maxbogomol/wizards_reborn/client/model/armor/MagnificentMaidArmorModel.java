package mod.maxbogomol.wizards_reborn.client.model.armor;

import com.google.common.collect.ImmutableList;
import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.EquipmentSlot;

public class MagnificentMaidArmorModel extends ArmorModel {
    final ModelPart leftSkirt;
    final ModelPart rightSkirt;
    final ModelPart leftStocking;
    final ModelPart rightStocking;

    public MagnificentMaidArmorModel(ModelPart root) {
        super(root);
        this.leftSkirt = root.getChild("left_skirt");
        this.rightSkirt = root.getChild("right_skirt");
        this.leftStocking = root.getChild("left_stocking");
        this.rightStocking = root.getChild("right_stocking");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(new CubeDeformation(0), 0);
        PartDefinition root = createHumanoidAlias(mesh);

        rootAdditional(root);
        rootNormalBodyLayer(root);
        rootArmsBodyLayer(root);

        return LayerDefinition.create(mesh, 128, 64);
    }

    public static void rootAdditional(PartDefinition root) {
        root.addOrReplaceChild("left_skirt", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("right_skirt", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("left_stocking", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("right_stocking", new CubeListBuilder(), PartPose.ZERO);
    }

    public static void rootNormalBodyLayer(PartDefinition root) {
        PartDefinition body = root.getChild("body");
        PartDefinition right_skirt = root.getChild("right_skirt");
        PartDefinition left_skirt = root.getChild("left_skirt");
        PartDefinition right_legging = root.getChild("right_stocking");
        PartDefinition left_legging = root.getChild("left_stocking");
        PartDefinition right_foot = root.getChild("right_foot");
        PartDefinition left_foot = root.getChild("left_foot");
        PartDefinition head = root.getChild("head");

        PartDefinition head_armor = head.addOrReplaceChild("head_armor", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.52F)), PartPose.ZERO);
        PartDefinition head_layer_armor = head_armor.addOrReplaceChild("head_layer_armor", CubeListBuilder.create().texOffs(0, 16)
                .addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.72F)), PartPose.ZERO);

        PartDefinition body_armor = body.addOrReplaceChild("body_armor", CubeListBuilder.create().texOffs(32, 0)
                .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.62F)), PartPose.ZERO);
        PartDefinition body_layer_armor = body_armor.addOrReplaceChild("body_layer_armor", CubeListBuilder.create().texOffs(32, 16)
                .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.82F)), PartPose.ZERO);

        PartDefinition right_skirt_armor = right_skirt.addOrReplaceChild("right_skirt_armor", CubeListBuilder.create().texOffs(32, 32)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.ZERO);
        PartDefinition right_skirt_layer_armor = right_skirt_armor.addOrReplaceChild("right_skirt_layer_armor", CubeListBuilder.create().texOffs(48, 32)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.02F)), PartPose.ZERO);

        PartDefinition left_skirt_armor = left_skirt.addOrReplaceChild("left_skirt_armor", CubeListBuilder.create().texOffs(32, 48)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.ZERO);
        PartDefinition left_skirt_layer_armor = left_skirt_armor.addOrReplaceChild("left_skirt_layer_armor", CubeListBuilder.create().texOffs(48, 48)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.02F)), PartPose.ZERO);

        PartDefinition right_legging_armor = right_legging.addOrReplaceChild("right_legging_armor", CubeListBuilder.create().texOffs(64, 32)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.52F)), PartPose.ZERO);
        PartDefinition right_legging_layer_armor = right_legging_armor.addOrReplaceChild("right_legging_layer_armor", CubeListBuilder.create().texOffs(80, 32)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.72F)), PartPose.ZERO);

        PartDefinition left_legging_armor = left_legging.addOrReplaceChild("left_legging_armor", CubeListBuilder.create().texOffs(64, 48)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.52F)), PartPose.ZERO);
        PartDefinition left_legging_layer_armor = left_legging_armor.addOrReplaceChild("left_legging_layer_armor", CubeListBuilder.create().texOffs(80, 48)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.72F)), PartPose.ZERO);

        PartDefinition right_foot_armor = right_foot.addOrReplaceChild("right_foot_armor", CubeListBuilder.create().texOffs(88, 0)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.55F)), PartPose.ZERO);
        PartDefinition right_foot_layer_armor = right_foot_armor.addOrReplaceChild("right_foot_armor", CubeListBuilder.create().texOffs(88, 16)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.ZERO);

        PartDefinition left_foot_armor = left_foot.addOrReplaceChild("left_foot_layer_armor", CubeListBuilder.create().texOffs(104, 0)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.55F)), PartPose.ZERO);
        PartDefinition left_foot_layer_armor = left_foot_armor.addOrReplaceChild("left_foot_layer_armor", CubeListBuilder.create().texOffs(104, 16)
                .addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.75F)), PartPose.ZERO);
    }

    public static void rootArmsBodyLayer(PartDefinition root) {
        PartDefinition right_arm = root.getChild("right_arm");
        PartDefinition left_arm = root.getChild("left_arm");

        PartDefinition right_arm_armor = right_arm.addOrReplaceChild("right_arm_armor", CubeListBuilder.create().texOffs(56, 0)
                .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.52F)), PartPose.ZERO);
        PartDefinition right_arm_layer_armor = right_arm_armor.addOrReplaceChild("right_arm_layer_armor", CubeListBuilder.create().texOffs(72, 0)
                .addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.72F)), PartPose.ZERO);

        PartDefinition left_arm_armor = left_arm.addOrReplaceChild("left_arm_armor", CubeListBuilder.create().texOffs(56, 16)
                .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.52F)), PartPose.ZERO);
        PartDefinition left_arm_layer_armor = left_arm_armor.addOrReplaceChild("left_arm_layer_armor", CubeListBuilder.create().texOffs(72, 16)
                .addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.72F)), PartPose.ZERO);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        if (slot == EquipmentSlot.CHEST) {
            return ImmutableList.of(body, leftArm, rightArm, leftSkirt, rightSkirt);
        }
        else if (slot == EquipmentSlot.LEGS) {
            return ImmutableList.of(leftStocking, rightStocking);
        }
        else if (slot == EquipmentSlot.FEET) {
            return ImmutableList.of(leftFoot, rightFoot);
        }
        else return ImmutableList.of();
    }

    @Override
    public void copyFromDefault(HumanoidModel model) {
        super.copyFromDefault(model);
        leftSkirt.copyFrom(model.leftLeg);
        rightSkirt.copyFrom(model.rightLeg);
        leftStocking.copyFrom(model.leftLeg);
        rightStocking.copyFrom(model.rightLeg);
    }
}
