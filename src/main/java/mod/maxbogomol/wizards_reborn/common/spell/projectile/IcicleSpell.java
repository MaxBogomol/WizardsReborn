package mod.maxbogomol.wizards_reborn.common.spell.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellComponent;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.IcicleSpellTrailPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.awt.*;

public class IcicleSpell extends ProjectileSpell {
    public static final ResourceLocation ICICLE_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID,"textures/entity/spell_projectile/icicle.png");

    public IcicleSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.frostSpellColor;
    }

    @Override
    public int getCooldown() {
        return 45;
    }

    @Override
    public int getWissenCost() {
        return 80;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public SpellComponent getSpellComponent() {
        return new IcicleSpellComponent();
    }

    @Override
    public void useSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            Vec3 pos = spellContext.getPos();
            Vec3 vel = spellContext.getPos().add(spellContext.getVec().scale(40)).subtract(pos).scale(1.0 / 20);
            SpellEntity entity = new SpellEntity(WizardsRebornEntities.SPELL.get(), level);
            entity.setup(pos.x, pos.y, pos.z, spellContext.getEntity(), this.getId(), spellContext.getStats()).setSpellContext(spellContext);
            entity.setDeltaMovement(vel);
            IcicleSpellComponent spellComponent = (IcicleSpellComponent) entity.getSpellComponent();
            spellComponent.shard = true;
            entity.updateSpellComponent(spellComponent);
            level.addFreshEntity(entity);
            spellContext.setCooldown(this);
            spellContext.removeWissen(this);
            spellContext.awardStat(this);
            spellContext.spellSound(this);
        }
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {
        super.onImpact(level, entity, hitResult, target);
        IcicleSpellComponent spellComponent = (IcicleSpellComponent) entity.getSpellComponent();

        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
        float damage = (2f + (focusLevel * 0.75f)) + magicModifier;

        if (spellComponent.shard) {
            createShards(level, entity, (int) (focusLevel + magicModifier));
        } else {
            damage = damage / 2f;
        }

        DamageSource generic = getDamage(target.damageSources().generic().typeHolder(), entity, entity.getOwner());
        target.hurt(generic, 3);
        if (target instanceof Player targetPlayer) {
            targetPlayer.getInventory().hurtArmor(generic, 3, Inventory.ALL_ARMOR_SLOTS);
        }
        target.invulnerableTime = 0;
        DamageSource damageSource = getDamage(target.damageSources().freeze().typeHolder(), entity, entity.getOwner());
        target.hurt(damageSource, damage);
        target.clearFire();
        int frost = target.getTicksFrozen() + 500;
        if (frost <= 2000) target.setTicksFrozen(frost);
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult) {
        super.onImpact(level, entity, hitResult);
        IcicleSpellComponent spellComponent = (IcicleSpellComponent) entity.getSpellComponent();
        int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
        if (spellComponent.shard) {
            createShards(level, entity, (int) (focusLevel + magicModifier));
        }
    }

    @Override
    public void trailEffect(Level level, SpellEntity entity) {
        if (!entity.level().isClientSide()) {
            Vec3 motion = entity.getDeltaMovement();
            Vec3 pos = entity.position();
            Vec3 norm = motion.normalize().scale(0.005f);
            PacketHandler.sendToTracking(level, entity.blockPosition(), new IcicleSpellTrailPacket(new Vec3(entity.xo, entity.yo + 0.2f, entity.zo), pos.add(0, 0.2f, 0), norm, getColor()));
        }
    }

    @Override
    public void burstSound(Level level, SpellEntity entity) {
        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
    }

    public void createShards(Level level, SpellEntity entity, int count) {
        for (int i = 0; i < count; i++) {
            Vec3 pos = entity.getEyePosition();
            Vec3 vel = new Vec3((random.nextFloat() - 0.5f) * 0.3f, random.nextFloat() * 0.2f, (random.nextFloat() - 0.5f) * 0.3f);
            SpellEntity newEntity = new SpellEntity(WizardsRebornEntities.SPELL.get(), level);
            newEntity.setup(pos.x, pos.y - 0.2f, pos.z, entity.getOwner(), this.getId(), entity.getStats()).setSpellContext(entity.getSpellContext());
            newEntity.setDeltaMovement(vel);
            level.addFreshEntity(newEntity);
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(SpellEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, light);
        ProjectileSpellComponent spellComponent = (ProjectileSpellComponent) entity.getSpellComponent();

        if (!spellComponent.fade) {
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.2F, 0.0F);
            poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
            poseStack.mulPose(Axis.XP.rotationDegrees(45.0F));
            poseStack.scale(0.05625F, 0.05625F, 0.05625F);
            poseStack.translate(2.0F, 0.0F, 0.0F);
            renderIcicle(ICICLE_TEXTURE, poseStack, buffer, light);
            poseStack.popPose();
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void renderIcicle(ResourceLocation texture, PoseStack stack, MultiBufferSource buffer, int light) {
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entityCutout(texture));
        PoseStack.Pose posestack$pose = stack.last();
        Matrix4f matrix4f = posestack$pose.pose();
        Matrix3f matrix3f = posestack$pose.normal();
        this.vertex(matrix4f, matrix3f, vertexconsumer, 0, -2, -2, 0.0F, 0.15625F, -1, 0, 0, light);
        this.vertex(matrix4f, matrix3f, vertexconsumer, 0, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, light);
        this.vertex(matrix4f, matrix3f, vertexconsumer, 0, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, light);
        this.vertex(matrix4f, matrix3f, vertexconsumer, 0, 2, -2, 0.0F, 0.3125F, -1, 0, 0, light);
        this.vertex(matrix4f, matrix3f, vertexconsumer, 0, 2, -2, 0.0F, 0.15625F, 1, 0, 0, light);
        this.vertex(matrix4f, matrix3f, vertexconsumer, 0, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, light);
        this.vertex(matrix4f, matrix3f, vertexconsumer, 0, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, light);
        this.vertex(matrix4f, matrix3f, vertexconsumer, 0, -2, -2, 0.0F, 0.3125F, 1, 0, 0, light);

        for(int j = 0; j < 4; ++j) {
            stack.mulPose(Axis.XP.rotationDegrees(90.0F));
            this.vertex(matrix4f, matrix3f, vertexconsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, light);
            this.vertex(matrix4f, matrix3f, vertexconsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, light);
            this.vertex(matrix4f, matrix3f, vertexconsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, light);
            this.vertex(matrix4f, matrix3f, vertexconsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, light);
        }
    }

    public void vertex(Matrix4f matrix, Matrix3f normal, VertexConsumer pConsumer, int x, int y, int z, float u, float v, int normalX, int normalZ, int normalY, int light) {
        pConsumer.vertex(matrix, (float) x, (float) y, (float) z).color(255, 255, 255, 255).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, (float) normalX, (float) normalY, (float) normalZ).endVertex();
    }
}