package mod.maxbogomol.wizards_reborn.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class SpellRenderer<T extends SpellEntity> extends EntityRenderer<T> {

    public SpellRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(SpellEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int light) {
        Spell spell = entity.getSpell();
        if (spell != null) {
            spell.render(entity, entityYaw, partialTicks, poseStack, bufferSource, light);
        }
    }

    @Override
    public boolean shouldRender(T livingEntity, Frustum camera, double camX, double camY, double camZ) {
        return true;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return null;
    }

    @Override
    public Vec3 getRenderOffset(T entity, float partialTicks) {
        Spell spell = entity.getSpell();
        if (spell != null) {
            return spell.getRenderOffset(entity, partialTicks);
        }
        return Vec3.ZERO;
    }
}