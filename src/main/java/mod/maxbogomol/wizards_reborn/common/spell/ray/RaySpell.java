package mod.maxbogomol.wizards_reborn.common.spell.ray;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.client.animation.ItemAnimation;
import mod.maxbogomol.wizards_reborn.client.animation.SpellHandItemAnimation;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.RaySpellEffectPacket;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.UUID;
import java.util.function.Predicate;

public class RaySpell extends Spell {
    public static SpellHandItemAnimation animation = new SpellHandItemAnimation();

    public RaySpell(String id, int points) {
        super(id, points);
    }

    @Override
    public int getWissenCost() {
        return 20;
    }

    public float getRayDistance() {
        return 25f;
    }

    @Override
    public void useSpell(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);

            CompoundTag stats = getStats(stack);
            CompoundTag spellData = new CompoundTag();
            spellData.putInt("ticks", 1);
            spellData.putInt("ticks_left", 3);
            spellData.putInt("tick_left", 0);

            Vec3 pos = player.getEyePosition();
            SpellProjectileEntity entity = new SpellProjectileEntity(WizardsReborn.SPELL_PROJECTILE.get(), world).shoot(
                    pos.x, pos.y - 0.5, pos.z, 0, 0, 0, player.getUUID(), this.getId(), stats
            ).createSpellData(spellData);
            world.addFreshEntity(entity);

            updatePos(entity);
            updateRot(entity);

            CompoundTag nbt = stack.getOrCreateTag();
            CompoundTag stackSpellData = new CompoundTag();
            nbt.put("spell_data", stackSpellData);
            stackSpellData.putUUID("entity", entity.getUUID());
            stack.setTag(nbt);

            player.startUsingItem(hand);
            awardStat(player, stack);
            world.playSound(WizardsReborn.proxy.getPlayer(), player.getX(), player.getY(), player.getZ(), WizardsReborn.SPELL_CAST_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
        }
    }

    @Override
    public void onUseTick(Level world, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (!world.isClientSide) {
            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("spell_data")) {
                CompoundTag stackSpellData = nbt.getCompound("spell_data");
                if (stackSpellData.contains("entity")) {
                    UUID entityUUID = stackSpellData.getUUID("entity");
                    Entity entity = ((ServerLevel) world).getEntity(entityUUID);
                    if (entity instanceof SpellProjectileEntity projectile) {
                        CompoundTag spellData = projectile.getSpellData();
                        spellData.putInt("ticks", 1);
                        spellData.putInt("ticks_left", 3);
                        projectile.setSpellData(spellData);
                        projectile.updateSpellData();
                        projectile.hurtMarked = true;

                        if (projectile.tickCount % 7 == 0) {
                            WissenItemUtils.removeWissen(stack, 1);
                        }
                    }
                }
            }
        }
    }

    public void releaseUsing(ItemStack stack, Level world, LivingEntity entityLiving, int timeLeft) {
        if (!world.isClientSide) {
            CompoundTag stats = getStats(stack);

            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("spell_data")) {
                CompoundTag stackSpellData = nbt.getCompound("spell_data");
                if (stackSpellData.contains("entity")) {
                    UUID entityUUID = stackSpellData.getUUID("entity");
                    Entity entity = ((ServerLevel) world).getEntity(entityUUID);
                    if (entity instanceof SpellProjectileEntity projectile) {
                        setCooldown(stack, stats);

                        CompoundTag spellData = projectile.getSpellData();
                        spellData.putInt("ticks", 0);
                        spellData.putInt("tick_left", projectile.tickCount);
                        projectile.setSpellData(spellData);
                        projectile.updateSpellData();
                    }

                    nbt.put("spell_data", new CompoundTag());
                }
            }
        }
    }

    @Override
    public void entityTick(SpellProjectileEntity entity) {
        if (!entity.level().isClientSide) {
            boolean burst = false;
            CompoundTag spellData = entity.getSpellData();
            HitResult ray = getHitResult(entity, entity.position().add(0, 0.3F, 0), entity.getLookAngle().scale(getRayDistance()), entity.level(), (e) -> {
                return !e.isSpectator() && e.isPickable() && !e.getUUID().equals(entity.getEntityData().get(entity.casterId).get());
            });
            if (spellData.getInt("tick_left") <= 0) {
                if (ray.getType() == HitResult.Type.ENTITY) {
                    entity.onImpact(ray, ((EntityHitResult) ray).getEntity());
                    burst = true;
                } else if (ray.getType() == HitResult.Type.BLOCK) {
                    entity.onImpact(ray);
                    burst = true;
                }
            }

            if (spellData.getInt("ticks") <= 0) {
                if (spellData.getInt("ticks_left") <= 0) {
                    entity.remove();
                }
                if (spellData.getInt("ticks_left") > 0) {
                    spellData.putInt("ticks_left", spellData.getInt("ticks_left")  - 1);
                }
            }

            if (spellData.getInt("ticks") >= 0) {
                spellData.putInt("ticks", spellData.getInt("ticks")  - 1);
            }

            entity.setSpellData(spellData);
            entity.updateSpellData();

            if (spellData.getInt("tick_left") <= 0) {
                updatePos(entity);
                updateRot(entity);

                float distance = (float) Math.sqrt(Math.pow(entity.getX() - ray.getLocation().x, 2) + Math.pow(entity.getY() - ray.getLocation().y, 2) + Math.pow(entity.getZ() - ray.getLocation().z, 2));
                Vec3 pos = entity.position();
                Vec3 posStart = entity.getLookAngle().add(entity.position());
                Vec3 posEnd = entity.getLookAngle().scale(distance).add(entity.position());

                Color color = getColor();
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                PacketHandler.sendToTracking(entity.level(), new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), new RaySpellEffectPacket((float) posStart.x, (float) posStart.y + 0.2F, (float) posStart.z, (float) posEnd.x, (float) posEnd.y + 0.4F, (float) posEnd.z, r, g, b, burst));
            }

            if (random.nextFloat() < 0.5) {
                entity.level().playSound(WizardsReborn.proxy.getPlayer(), entity.getX(), entity.getY(), entity.getZ(), WizardsReborn.SPELL_BURST_SOUND.get(), SoundSource.BLOCKS, 0.25f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
            }
        }
    }

    public void updatePos(SpellProjectileEntity entity) {
        if (entity.getSender() != null) {
            Player player = entity.getSender();
            entity.copyPosition(player);
            entity.setPos(entity.getX(), entity.getY() + ((player.getEyeHeight() - 0.5F)), entity.getZ());
            entity.xOld = player.xOld;
            entity.yOld = player.yOld + ((player.getEyeHeight() - 0.5F));
            entity.zOld = player.zOld;
        }
    }

    public void updateRot(SpellProjectileEntity entity) {
        if (entity.getSender() != null) {
            entity.setYRot(entity.getSender().getYRot());
            entity.setXRot(entity.getSender().getXRot());
            entity.yRotO = entity.getSender().yRotO;
            entity.xRotO = entity.getSender().xRotO;
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("spell_data")) {
            CompoundTag stackSpellData = nbt.getCompound("spell_data");
            if (stackSpellData.contains("entity")) {
                return UseAnim.CUSTOM;
            }
        }
        return UseAnim.NONE;
    }

    @Override
    public boolean hasCustomAnimation(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("spell_data")) {
            CompoundTag stackSpellData = nbt.getCompound("spell_data");
            if (stackSpellData.contains("entity")) {
                return true;
            }
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemAnimation getAnimation(ItemStack stack) {
        return animation;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(SpellProjectileEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
        HitResult ray = getHitResult(entity, entity.position().add(0, 0.3F, 0), entity.getLookAngle().scale(getRayDistance()), entity.level(), (e) -> {
            return !e.isSpectator() && e.isPickable() && !e.getUUID().equals(entity.getEntityData().get(entity.casterId).get());
        });

        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        float offset = 1.0f;
        updateRot(entity);

        float yRot = Mth.lerp(partialTicks, -entity.yRotO, -entity.getYRot()) - 90.0F;
        float xRot = Mth.lerp(partialTicks, -entity.xRotO, -entity.getXRot());

        stack.pushPose();
        stack.translate(0, 0.2, 0);
        stack.mulPose(Axis.YP.rotationDegrees(yRot));
        stack.mulPose(Axis.ZP.rotationDegrees(xRot));
        stack.mulPose(Axis.XP.rotationDegrees((entity.tickCount + partialTicks) * 5f));

        stack.translate(offset, 0, 0);

        MultiBufferSource bufferDelayed = WorldRenderHandler.getDelayedRender();

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

        float distance = (float) Math.sqrt(Math.pow(entity.getX() - ray.getLocation().x, 2) + Math.pow(entity.getY() - ray.getLocation().y, 2) + Math.pow(entity.getZ() - ray.getLocation().z, 2));
        RenderUtils.ray(stack, bufferDelayed, 0.1f * width, (distance - offset) * width, Mth.lerp(distance / 25f, 1f, 0.5f), r, g, b, 1, r, g, b, 0.1F);
        stack.translate(-0.05f, 0, 0);
        stack.mulPose(Axis.XP.rotationDegrees(-(entity.tickCount + partialTicks) * 10f));
        RenderUtils.ray(stack, bufferDelayed, 0.15f * width, (distance - offset + 0.1f) * width, Mth.lerp(distance / 25f, 1f, 0.5f), r, g, b, 0.5F, r, g, b, 0.05F);

        stack.popPose();
    }

    public HitResult getHitResult(SpellProjectileEntity pProjectile, Vec3 pStartVec, Vec3 pEndVecOffset, Level pLevel, Predicate<Entity> pFilter) {
        return getHitResultStandart(pProjectile, pStartVec, pEndVecOffset, pLevel, pFilter);
    }

    public static HitResult getHitResultStandart(Entity pProjectile, Vec3 pStartVec, Vec3 pEndVecOffset, Level pLevel, Predicate<Entity> pFilter) {
        Vec3 vec3 = pStartVec.add(pEndVecOffset);
        HitResult hitresult = pLevel.clip(new ClipContext(pStartVec, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, pProjectile));
        if (hitresult.getType() != HitResult.Type.MISS) {
            vec3 = hitresult.getLocation();
        }

        HitResult hitresult1 = ProjectileUtil.getEntityHitResult(pLevel, pProjectile, pStartVec, vec3, pProjectile.getBoundingBox().expandTowards(pEndVecOffset).inflate(1.0D), pFilter);
        if (hitresult1 != null) {
            hitresult = hitresult1;
        }

        return hitresult;
    }

    public static Vec3 getBlockHitOffset(HitResult ray, Entity projectile, float offset) {
        Vec3 vec = new Vec3(ray.getLocation().x() + (projectile.getViewVector(0).x() * offset), ray.getLocation().y() + (projectile.getViewVector(0).y() * offset), ray.getLocation().z() + (projectile.getViewVector(0).z() * offset));

        return vec;
    }
}
