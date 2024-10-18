package mod.maxbogomol.wizards_reborn.common.spell.ray;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class LightRaySpell extends RaySpell {
    Color color = new Color(0.886f, 0.811f, 0.549f);

    public LightRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.FIRE);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int tickCost() {
        return 10;
    }

    @Override
    public boolean hasBurst(SpellEntity entity) {
        return false;
    }

    @Override
    public boolean hasSound(SpellEntity entity) {
        return false;
    }

    @Override
    public void hitTick(SpellEntity entity, RayHitResult hitResult) {
        RaySpellComponent spellComponent = (RaySpellComponent) entity.getSpellComponent();
        Vec3 vec = spellComponent.vec;

        Vec3 from = entity.position().add(vec);
        Vec3 to = entity.position().add(vec.scale(getRayDistance() + 1));

        LightRayHitResult lightHitResult = LightUtil.getLightRayHitResult(entity.level(), entity.getOnPos(), from, to, getRayDistance());
        BlockEntity hitBlock = lightHitResult.getBlockEntity();
        if (hitBlock != null) {
            if (hitBlock instanceof ILightBlockEntity toLight) {
                int max = 10;
                if (max < toLight.getLight()) {
                    max = toLight.getLight();
                }
                toLight.setLight(max);
                BlockEntityUpdate.packet(hitBlock);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(SpellEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        RaySpellComponent spellComponent = (RaySpellComponent) entity.getSpellComponent();
        Vec3 vec = spellComponent.vec;
        Vec3 vecOld = spellComponent.vecOld;
        double vecX = Mth.lerp(partialTicks, vecOld.x(), vec.x());
        double vecY = Mth.lerp(partialTicks, vecOld.y(), vec.y());
        double vecZ = Mth.lerp(partialTicks, vecOld.z(), vec.z());
        Vec3 lookVec = new Vec3(vecX, vecY, vecZ);

        Color color = getColor();
        float offset = 1f;

        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());

        poseStack.pushPose();
        Vec3 pos1 = entity.getPosition(partialTicks).add(lookVec.scale(offset));
        Vec3 pos2 = entity.getPosition(partialTicks).add(lookVec.scale(getRayDistance() + 1));
        poseStack.translate(-x, -y, -z);
        poseStack.translate(pos1.x(), pos1.y(), pos1.z());
        LightRayHitResult hitResult = LightUtil.getLightRayHitResult(entity.level(), entity.getOnPos(), pos1, pos2, getRayDistance());
        LightUtil.renderLightRay(pos1, hitResult.getPosHit(), color, Color.WHITE, Color.WHITE, false, partialTicks, poseStack);
        poseStack.popPose();
    }
}
