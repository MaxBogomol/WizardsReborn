package mod.maxbogomol.wizards_reborn.common.spell.projectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.IcicleRayEffectPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.awt.*;

public class IcicleSpell extends ProjectileSpell {
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
    public void useSpell(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);

            CompoundTag stats = getStats(stack);
            CompoundTag spellData = new CompoundTag();
            spellData.putBoolean("shard", true);

            Vec3 pos = player.getEyePosition(0);
            Vec3 vel = player.getEyePosition(0).add(player.getLookAngle().scale(40)).subtract(pos).scale(1.0 / 20);
            level.addFreshEntity(new SpellProjectileEntity(WizardsRebornEntities.SPELL_PROJECTILE.get(), level).shoot(
                    pos.x, pos.y - 0.2f, pos.z, vel.x, vel.y, vel.z, player.getUUID(), this.getId(), stats
            ).createSpellData(spellData));
            setCooldown(stack, stats);
            removeWissen(stack, stats, player);
            awardStat(player, stack);
            spellSound(player, level);
        }
    }

    @Override
    public void entityTick(SpellProjectileEntity entity) {
        if (!entity.level().isClientSide) {
            boolean hasEffectTrue = true;
            HitResult ray = ProjectileUtil.getHitResultOnMoveVector(entity, (e) -> {
                return !e.isSpectator() && e.isPickable() && (!e.getUUID().equals(entity.getSenderUUID()) || entity.tickCount > 5);
            });
            if (ray.getType() == HitResult.Type.ENTITY) {
                entity.onImpact(ray, ((EntityHitResult) ray).getEntity());
                hasEffectTrue = false;
            } else if (ray.getType() == HitResult.Type.BLOCK) {
                entity.onImpact(ray);
            } else {
                updatePos(entity);
                updateRot(entity);
            }

            if (hasEffectTrue) rayEffect(entity);
        } else {
            updatePos(entity);
            updateRot(entity);
        }
    }

    @Override
    public void updatePos(SpellProjectileEntity entity) {
        Vec3 motion = entity.getDeltaMovement();
        entity.setDeltaMovement(motion.x * 0.98, (motion.y > 0 ? motion.y * 0.98 : motion.y) - 0.011f, motion.z * 0.98);

        Vec3 pos = entity.position();
        entity.xo = pos.x;
        entity.yo = pos.y;
        entity.zo = pos.z;
        entity.setPos(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);
    }

    public void rayEffect(SpellProjectileEntity entity) {
        if (entity.tickCount > 1) {
            Vec3 motion = entity.getDeltaMovement();
            Vec3 pos = entity.position();
            Vec3 norm = motion.normalize().scale(0.025f);

            Color color = getColor();
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            PacketHandler.sendToTracking(entity.level(), new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), new IcicleRayEffectPacket((float) entity.xo, (float) entity.yo + 0.2f, (float) entity.zo, (float) pos.x, (float) pos.y + 0.2f, (float) pos.z, (float) norm.x, (float) norm.y, (float) norm.z, r, g, b));
        }
    }

    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player, Entity target) {
        projectile.burstEffect();
        projectile.remove();
        level.playSound(WizardsReborn.proxy.getPlayer(), projectile.getX(), projectile.getY(), projectile.getZ(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));

        int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);
        float damage = (float) (2f + (focusLevel * 0.75)) + magicModifier;

        CompoundTag spellData = projectile.getSpellData();
        if (spellData.contains("shard") && spellData.getBoolean("shard")) {
            createShards(level, projectile, player, (int) (focusLevel + magicModifier));
        } else {
            damage = damage / 2f;
        }

        DamageSource generic = new DamageSource(target.damageSources().generic().typeHolder(), projectile, player);
        target.hurt(generic, 3);
        if (target instanceof Player targetPlayer) {
            targetPlayer.getInventory().hurtArmor(generic, 3, Inventory.ALL_ARMOR_SLOTS);
        }
        target.invulnerableTime = 0;
        target.hurt(new DamageSource(target.damageSources().freeze().typeHolder(), projectile, player), damage);
        target.clearFire();
        int frost = target.getTicksFrozen() + 500;
        if (frost > 2000) frost = 2000;
        target.setTicksFrozen(frost);
    }

    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player) {
        projectile.setPos(ray.getLocation().x, ray.getLocation().y, ray.getLocation().z);
        projectile.burstEffect();
        projectile.remove();
        level.playSound(WizardsReborn.proxy.getPlayer(), projectile.getX(), projectile.getY(), projectile.getZ(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));

        int focusLevel = CrystalUtil.getStatLevel(projectile.getStats(), WizardsRebornCrystals.FOCUS);
        float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(player);

        CompoundTag spellData = projectile.getSpellData();
        if (spellData.contains("shard") && spellData.getBoolean("shard")) {
            createShards(level, projectile, player, (int) (focusLevel + magicModifier));
        }
    }

    public void createShards(Level level, SpellProjectileEntity projectile, Player player, int count) {
        for (int i = 0; i < count; i++) {
            CompoundTag spellData = new CompoundTag();
            spellData.putBoolean("shard", false);

            Vec3 pos = projectile.getEyePosition(0);
            Vec3 vel = new Vec3((random.nextFloat() - 0.5f) * 0.3f, random.nextFloat() * 0.2f, (random.nextFloat() - 0.5f) * 0.3f);
            level.addFreshEntity(new SpellProjectileEntity(WizardsRebornEntities.SPELL_PROJECTILE.get(), level).shoot(
                    pos.x, pos.y - 0.2f, pos.z, vel.x, vel.y, vel.z, player.getUUID(), this.getId(), projectile.getStats()
            ).createSpellData(spellData));
        }
    }

    public static final ResourceLocation ICICLE_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID,"textures/entity/spell_projectile/icicle.png");

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(SpellProjectileEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
        stack.pushPose();
        stack.translate(0.0F, 0.2F, 0.0F);
        stack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(partialTicks, entity.yRotO, entity.getYRot()) - 90.0F));
        stack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(partialTicks, entity.xRotO, entity.getXRot())));
        stack.mulPose(Axis.XP.rotationDegrees(45.0F));
        stack.scale(0.05625F, 0.05625F, 0.05625F);
        stack.translate(2.0F, 0.0F, 0.0F);
        renderIcicle(ICICLE_TEXTURE, stack, buffer, light);
        stack.popPose();
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

    public void vertex(Matrix4f pMatrix, Matrix3f pNormal, VertexConsumer pConsumer, int pX, int pY, int pZ, float pU, float pV, int pNormalX, int pNormalZ, int pNormalY, int light) {
        pConsumer.vertex(pMatrix, (float)pX, (float)pY, (float)pZ).color(255, 255, 255, 255).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(pNormal, (float)pNormalX, (float)pNormalY, (float)pNormalZ).endVertex();
    }
}