package mod.maxbogomol.wizards_reborn.common.spell.ray;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.common.network.BlockEntityUpdate;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.LightRayHitResult;
import mod.maxbogomol.wizards_reborn.api.light.LightUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class LightRaySpell extends RaySpell {
    Color color = new Color(0.886f, 0.811f, 0.549f);

    public LightRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.FIRE_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int tickCost() {
        return 1;
    }

    @Override
    public boolean hasBurst(SpellProjectileEntity entity) {
        return false;
    }

    @Override
    public boolean hasSound(SpellProjectileEntity entity) {
        return false;
    }

    @Override
    public void rayTick(SpellProjectileEntity entity, HitResult ray) {
        Vec3 offset = entity.getLookAngle().scale(1f);
        Vec3 from = entity.position().add(offset).add(0, 0.2f, 0);
        Vec3 to = entity.getLookAngle().scale(getRayDistance() + 1).add(from);

        LightRayHitResult hitResult = LightUtils.getLightRayHitResult(entity.level(), entity.getOnPos(), from, to, getRayDistance());
        BlockEntity hitTile = hitResult.getTile();
        if (hitTile != null) {
            if (hitTile instanceof ILightBlockEntity toLight) {
                int max = 10;
                if (max < toLight.getLight()) {
                    max = toLight.getLight();
                }
                toLight.setLight(max);
                BlockEntityUpdate.packet(hitTile);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(SpellProjectileEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
        Vec3 to = entity.getLookAngle().scale(getRayDistance() + 1).add(0, 0.2f, 0).add(entity.position());
        Vec3 offset = entity.getLookAngle().scale(1f);

        float width = 1f;
        if (entity.tickCount < 3) {
            width = (entity.tickCount + partialTicks) / 3;
        } else {
            CompoundTag spellData = entity.getSpellData();
            if (spellData.contains("tick_left")) {
                if (spellData.getInt("tick_left") > 0) {
                    width = 1f - (((entity.tickCount - 1) - spellData.getInt("tick_left") + partialTicks) / 4);
                }
            }
        }
        if (width > 1f) width = 1f;
        if (width < 0f) width = 0f;

        stack.pushPose();
        stack.translate(0, 0.2, 0);
        stack.translate(offset.x(), offset.y(), offset.z());
        stack.scale(width, width, width);
        LightUtils.renderLightRay(entity.level(), entity.getOnPos(), entity.position().add(offset).add(0, 0.2f, 0), to, getRayDistance(), color, partialTicks, stack);
        stack.popPose();
    }
}
