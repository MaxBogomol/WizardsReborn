package mod.maxbogomol.wizards_reborn.common.spell.charge;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.client.animation.ChargeSpellHandItemAnimation;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.ChargeSpellProjectileRayEffectPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ChargeSpell extends Spell {
    public static ChargeSpellHandItemAnimation animation = new ChargeSpellHandItemAnimation();

    public ChargeSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public int getWissenCost() {
        return 35;
    }

    public int getCharge() {
        return 30;
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
            spellData.putInt("ticks", 0);
            spellData.putInt("charge", 0);
            spellData.putBoolean("throw", false);
            spellData.putInt("ticks_left", 1);

            Vec3 pos = player.getEyePosition();
            SpellProjectileEntity entity = new SpellProjectileEntity(WizardsRebornEntities.SPELL_PROJECTILE.get(), level).shoot(
                    pos.x, pos.y - 0.2f, pos.z, 0, 0, 0, player.getUUID(), this.getId(), stats
            ).createSpellData(spellData);
            level.addFreshEntity(entity);

            updatePos(entity);
            updateRot(entity);

            CompoundTag nbt = stack.getOrCreateTag();
            CompoundTag stackSpellData = new CompoundTag();
            nbt.put("spell_data", stackSpellData);
            stackSpellData.putUUID("entity", entity.getUUID());
            stack.setTag(nbt);

            player.startUsingItem(hand);
            awardStat(player, stack);
            level.playSound(WizardsReborn.proxy.getPlayer(), player.getX(), player.getY(), player.getZ(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.PLAYERS, 0.5f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (!level.isClientSide) {
            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("spell_data")) {
                CompoundTag stackSpellData = nbt.getCompound("spell_data");
                if (stackSpellData.contains("entity")) {
                    UUID entityUUID = stackSpellData.getUUID("entity");
                    Entity entity = ((ServerLevel) level).getEntity(entityUUID);
                    if (entity instanceof SpellProjectileEntity projectile) {
                        CompoundTag spellData = projectile.getSpellData();
                        spellData.putInt("ticks", 0);
                        spellData.putInt("charge", spellData.getInt("charge") + 1);
                        if (spellData.getInt("charge") > getCharge()) {
                            spellData.putInt("charge", getCharge());
                        }
                        spellData.putInt("ticks_left", 1);
                        projectile.setSpellData(spellData);
                        projectile.updateSpellData();

                        if (random.nextFloat() < 0.5) {
                            entity.level().playSound(WizardsReborn.proxy.getPlayer(), entity.getX(), entity.getY(), entity.getZ(), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.PLAYERS, 0.25f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
                        }
                    }
                }
            }
        }
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (!level.isClientSide) {
            CompoundTag stats = getStats(stack);

            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("spell_data")) {
                CompoundTag stackSpellData = nbt.getCompound("spell_data");
                if (stackSpellData.contains("entity")) {
                    UUID entityUUID = stackSpellData.getUUID("entity");
                    Entity entity = ((ServerLevel) level).getEntity(entityUUID);
                    if (entity instanceof SpellProjectileEntity projectile) {
                        setCooldown(stack, stats);

                        CompoundTag spellData = projectile.getSpellData();
                        spellData.putBoolean("throw", true);
                        projectile.setSpellData(spellData);
                        projectile.updateSpellData();

                        Vec3 pos = projectile.getSender().getEyePosition(0);
                        Vec3 vel = projectile.getSender().getEyePosition(0).add(projectile.getSender().getLookAngle().scale(40)).subtract(pos).scale(1.0 / 25);
                        projectile.setDeltaMovement(vel);

                        level.playSound(WizardsReborn.proxy.getPlayer(), projectile.getSender().getX(), projectile.getSender().getY(), projectile.getSender().getZ(), WizardsRebornSounds.SPELL_CAST.get(), SoundSource.PLAYERS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                    }

                    nbt.put("spell_data", new CompoundTag());
                }
            }
        }
    }

    @Override
    public void entityTick(SpellProjectileEntity entity) {
        if (!entity.level().isClientSide) {
            if (!entity.getFade()) {
                boolean hasEffectTrue = true;
                CompoundTag spellData = entity.getSpellData();
                if (spellData.getBoolean("throw")) {
                    HitResult ray = ProjectileUtil.getHitResultOnMoveVector(entity, (e) -> {
                        return !e.isSpectator() && e.isPickable() && (!e.getUUID().equals(entity.getSenderUUID()) || (spellData.getInt("ticks") > 5));
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
                } else {
                    updatePos(entity);
                    updateRot(entity);
                }

                if (spellData.getInt("ticks") <= 500) {
                    spellData.putInt("ticks", spellData.getInt("ticks") + 1);
                } else {
                    entity.setFade(true);
                    entity.setFadeTick(20);
                    entity.burstEffect();
                }

                if (spellData.getInt("ticks_left") <= 0) {
                    entity.setFade(true);
                    entity.setFadeTick(20);
                }

                if (!spellData.getBoolean("throw")) {
                    if (spellData.getInt("ticks_left") > 0) {
                        spellData.putInt("ticks_left", spellData.getInt("ticks_left") - 1);
                    }
                }

                entity.setSpellData(spellData);
                entity.updateSpellData();

                if (hasEffectTrue) rayEffect(entity);
                if (entity.getSender() != null) {
                    Vec3 posE = entity.getSender().getEyePosition(0);
                    Vec3 vel = entity.getSender().getEyePosition(0).add(entity.getSender().getLookAngle().scale(40)).subtract(posE).scale(1.0 / 25);
                    Vec3 oldPos = entity.position().add(vel);

                    spellData.putFloat("oldX", (float) oldPos.x);
                    spellData.putFloat("oldY", (float) oldPos.y);
                    spellData.putFloat("oldZ", (float) oldPos.z);
                }
            }
        } else {
            if (!entity.getFade()) {
                updatePos(entity);
                updateRot(entity);

                if (entity.tickCount > 1) {
                    CompoundTag spellData = entity.getSpellData();
                    if (spellData.getBoolean("throw")) {
                        entity.addTrail(new Vec3(entity.position().toVector3f()));
                    }
                }
            }
        }
    }

    public void updatePos(SpellProjectileEntity entity) {
        CompoundTag spellData = entity.getSpellData();

        if (spellData.getBoolean("throw")) {
            Vec3 motion = entity.getDeltaMovement();
            entity.setDeltaMovement(motion.x * 0.99, motion.y * 0.99, motion.z * 0.99);

            Vec3 pos = entity.position();
            entity.xo = pos.x;
            entity.yo = pos.y;
            entity.zo = pos.z;
            entity.setPos(pos.x + motion.x, pos.y + motion.y, pos.z + motion.z);
        } else {
            if (entity.getSender() != null) {
                Vec3 pos = entity.position();
                entity.xo = pos.x;
                entity.yo = pos.y;
                entity.zo = pos.z;
                Player player = entity.getSender();
                entity.copyPosition(player);
                entity.setPos(entity.getX(), entity.getY() + ((player.getEyeHeight() - 0.2F)), entity.getZ());
                entity.xOld = player.xOld;
                entity.yOld = player.yOld + ((player.getEyeHeight() - 0.2F));
                entity.zOld = player.zOld;
            }
        }
    }

    public void updateRot(SpellProjectileEntity entity) {
        CompoundTag spellData = entity.getSpellData();

        if (spellData.getBoolean("throw")) {
            Vec3 vec3 = entity.getDeltaMovement();
            double d0 = vec3.horizontalDistance();
            entity.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * (double) (180F / (float) Math.PI)));
            entity.setXRot((float) (Mth.atan2(vec3.y, d0) * (double) (180F / (float) Math.PI)));
            entity.yRotO = entity.getYRot();
            entity.xRotO = entity.getXRot();
        } else {
            if (entity.getSender() != null) {
                entity.setYRot(entity.getSender().getYRot());
                entity.setXRot(entity.getSender().getXRot());
                entity.yRotO = entity.getSender().yRotO;
                entity.xRotO = entity.getSender().xRotO;
            }
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
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player, Entity target) {
        projectile.setFade(true);
        projectile.setFadeTick(20);
        projectile.burstEffect();
        level.playSound(WizardsReborn.proxy.getPlayer(), projectile.getX(), projectile.getY(), projectile.getZ(), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.PLAYERS, 0.35f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
    }

    @Override
    public void onImpact(HitResult ray, Level level, SpellProjectileEntity projectile, Player player) {
        projectile.setFade(true);
        projectile.setFadeTick(20);
        projectile.setPos(ray.getLocation().x, ray.getLocation().y, ray.getLocation().z);
        projectile.burstEffect();
        level.playSound(WizardsReborn.proxy.getPlayer(), projectile.getX(), projectile.getY(), projectile.getZ(), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.PLAYERS, 0.35f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
    }

    public void rayEffect(SpellProjectileEntity projectile) {
        CompoundTag spellData = projectile.getSpellData();

        float charge = 0.5f + (((float) spellData.getInt("charge") / getCharge()) / 2f);

        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        if (!spellData.getBoolean("throw")) {
            if (projectile.tickCount > 1 && (projectile.getSender() != null)) {
                Vec3 posE = projectile.getSender().getEyePosition(0);
                Vec3 vel = projectile.getSender().getEyePosition(0).add(projectile.getSender().getLookAngle().scale(40)).subtract(posE).scale(1.0 / 25);
                projectile.setDeltaMovement(vel);

                Vec3 motion = projectile.getDeltaMovement();
                Vec3 pos = projectile.position().add(vel);
                Vec3 norm = motion.normalize().scale(0.025f);

                PacketHandler.sendToTracking(projectile.level(), new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), new ChargeSpellProjectileRayEffectPacket(spellData.getFloat("oldX"), spellData.getFloat("oldY") + 0.2f, spellData.getFloat("oldZ"), (float) pos.x, (float) pos.y + 0.2f, (float) pos.z, (float) norm.x, (float) norm.y, (float) norm.z, r, g, b, charge));
            }
        } else {
            if (spellData.getInt("ticks") > 1) {
                Vec3 motion = projectile.getDeltaMovement();
                Vec3 pos = projectile.position();
                Vec3 norm = motion.normalize().scale(0.025f);

                PacketHandler.sendToTracking(projectile.level(), new BlockPos((int) pos.x, (int) pos.y, (int) pos.z), new ChargeSpellProjectileRayEffectPacket((float) projectile.xo, (float) projectile.yo + 0.2f, (float) projectile.zo, (float) pos.x, (float) pos.y + 0.2f, (float) pos.z, (float) norm.x, (float) norm.y, (float) norm.z, r, g, b, charge));
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(SpellProjectileEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
        CompoundTag spellData = entity.getSpellData();
        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();
        VertexConsumer builder = bufferDelayed.getBuffer(FluffyFurRenderTypes.ADDITIVE);
        Color color = getColor();

        List<Vec3> trailList = new ArrayList<>(entity.trail);
        if (trailList.size() > 1 && spellData.getInt("ticks") >= 20) {
            Vec3 position = trailList.get(0);
            Vec3 nextPosition = trailList.get(1);
            float x = (float) Mth.lerp(partialTicks, position.x, nextPosition.x);
            float y = (float) Mth.lerp(partialTicks, position.y, nextPosition.y);
            float z = (float) Mth.lerp(partialTicks, position.z, nextPosition.z);
            trailList.set(0, new Vec3(x, y, z));
        }

        float x = (float) Mth.lerp(partialTicks, entity.xOld, entity.getX());
        float y = (float) Mth.lerp(partialTicks, entity.yOld, entity.getY());
        float z = (float) Mth.lerp(partialTicks, entity.zOld, entity.getZ());

        if (trailList.size() > 1) {
            trailList.set(trailList.size() - 1, new Vec3(x, y, z));
        }

        float charge = 0.5f + (((float) spellData.getInt("charge") / getCharge()) / 2f);

        stack.pushPose();
        stack.translate(0, 0.2f, 0);
        stack.translate(entity.getX() - x, entity.getY() - y,  entity.getZ() - z);
        //WizardsRebornRenderUtil.renderTrail(stack, builder, entity.position(), trailList, 0, 0.15f * charge, 0, 1.0f,  1.0f, color, 8, true);
        //WizardsRebornRenderUtil.renderTrail(stack, builder, entity.position(), trailList, 0, 0.15f * charge, 0, 0.75f, 0.75f, color, 8, true);
        stack.popPose();
    }
}
