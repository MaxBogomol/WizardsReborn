package mod.maxbogomol.wizards_reborn.common.spell.ray;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellComponent;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.client.animation.SpellHandItemAnimation;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.RaySpellEffectPacket;
import mod.maxbogomol.wizards_reborn.common.spell.WandSpellContext;
import mod.maxbogomol.wizards_reborn.common.spell.projectile.ProjectileSpellComponent;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.function.Predicate;

public class RaySpell extends Spell {
    public static SpellHandItemAnimation animation = new SpellHandItemAnimation();

    public RaySpell(String id, int points) {
        super(id, points);
    }

    @Override
    public SpellComponent getSpellComponent() {
        return new ProjectileSpellComponent();
    }

    @Override
    public int getWissenCost() {
        return 20;
    }

    public float getRayDistance() {
        return 25f;
    }

    public Color getSecondColor() {
        return getColor();
    }

    @Override
    public void useSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            Vec3 pos = spellContext.getPos();
            Vec3 vec = spellContext.getVec();
            SpellEntity entity = new SpellEntity(WizardsRebornEntities.SPELL.get(), level);
            entity.setup(pos.x(), pos.y() - 0.2f, pos.z(), spellContext.getEntity(), this.getId(), spellContext.getStats());
            entity.lookAt(EntityAnchorArgument.Anchor.FEET, entity.position().add(vec));

            level.addFreshEntity(entity);
            spellContext.setCooldown(this);
            spellContext.removeWissen(this);
            spellContext.awardStat(this);
            spellContext.spellSound(this);
        }
    }

    @Override
    public void useWand(Level level, Player player, InteractionHand hand, ItemStack stack) {
        if (!level.isClientSide()) {
            useSpell(level, getWandContext(player, stack));
            player.startUsingItem(hand);
        }
    }

    @Override
    public SpellContext getWandContext(Player player, ItemStack stack) {
        WandSpellContext spellContext = WandSpellContext.getFromWand(player, stack);
        //pellContext.setVec(new Vec3(0, 0.3f, 0));
        return spellContext;
    }

/*    @Override
    public void useSpell(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            ItemStack stack = player.getItemInHand(hand);

            CompoundTag stats = getStats(stack);
            CompoundTag spellData = new CompoundTag();
            spellData.putInt("ticks", 1);
            spellData.putInt("ticks_left", 3);
            spellData.putInt("tick_left", 0);

            Vec3 pos = player.getEyePosition();
            SpellProjectileEntity entity = new SpellProjectileEntity(WizardsRebornEntities.SPELL_PROJECTILE.get(), level).shoot(
                    pos.x, pos.y - 0.5, pos.z, 0, 0, 0, player.getUUID(), this.getId(), stats
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
            spellSound(player, level);
        }
    }*/

/*    @Override
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
                        spellData.putInt("ticks", 1);
                        spellData.putInt("ticks_left", 3);
                        projectile.setSpellData(spellData);
                        projectile.updateSpellData();
                        projectile.hurtMarked = true;

                        if (WissenItemUtil.canRemoveWissen(stack, 1)) {
                            if (projectile.tickCount % tickCost() == 0) {
                                WissenItemUtil.removeWissen(stack, 1);
                            }
                        } else {
                            livingEntity.stopUsingItem();
                        }
                    }
                }
            }
        }
    }*/

/*    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (!level.isClientSide) {
            CompoundTag stats = getStats(stack);

            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("spell_data")) {
                CompoundTag stackSpellData = nbt.getCompound("spell_data");
                if (stackSpellData.contains("entity")) {
                    UUID entityUUID = stackSpellData.getUUID("entity");
                    Entity entity = ((ClientLevel) level).getEntities().getEntity(entityUUID);
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
    }*/

    @Override
    public void entityTick(SpellEntity entity) {
        updatePos(entity);
        updateRot(entity);
        if (!entity.level().isClientSide()) {
            boolean burst = false;
            HitResult ray = getHitResult(entity, entity.position(), entity.getLookAngle().scale(getRayDistance()).add(0, 0.3F, 0), entity.level(), (e) -> {
                return !e.isSpectator() && e.isPickable() && !e.equals(entity.getOwner());
            });
/*            if (spellData.getInt("tick_left") <= 0) {
                if (ray.getType() == HitResult.Type.ENTITY) {
                    entity.onImpact(ray, ((EntityHitResult) ray).getEntity());
                    burst = true;
                } else if (ray.getType() == HitResult.Type.BLOCK) {
                    entity.onImpact(ray);
                    burst = true;
                }
            }

            rayTick(entity, ray);

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
            }*/
            entity.remove();

            //entity.setSpellData(spellData);
            //entity.updateSpellData();

            //if (spellData.getInt("tick_left") <= 0) {
                //updatePos(entity);
                //updateRot(entity);

                if (hasBurst(entity)) {
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
            //}

            if (random.nextFloat() < 0.5 && hasSound(entity)) {
                entity.level().playSound(WizardsReborn.proxy.getPlayer(), entity.getX(), entity.getY(), entity.getZ(), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.PLAYERS, 0.25f, (float) (0.5f + ((random.nextFloat() - 0.5D) / 4)));
            }

            rayEndTick(entity, ray);
        }
    }

    public void rayTick(SpellEntity entity, HitResult ray) {

    }

    public void rayEndTick(SpellEntity entity, HitResult ray) {

    }

    public boolean hasBurst(SpellEntity entity) {
        return true;
    }

    public boolean hasSound(SpellEntity entity) {
        return true;
    }

    public void updatePos(SpellEntity entity) {
        if (entity.getOwner() != null) {
/*            Entity player = entity.getOwner();
            //entity.copyPosition(player);
            entity.setPos(player.getX(), player.getY() + ((player.getEyeHeight() - 0.5F)), player.getZ());
            entity.xo = player.xo;
            entity.yo = player.yo + ((player.getEyeHeight() - 0.5F));
            entity.zo = player.zo;
            //entity.xOld = player.xo;
            //entity.yOld = player.yo + ((player.getEyeHeight() - 0.5F));
            //entity.zOld = player.zo;
            entity.hurtMarked = true;*/
            Entity player = entity.getOwner();
            Vec3 pos = entity.position();
            entity.xo = pos.x;
            entity.yo = pos.y;
            entity.zo = pos.z;
            entity.setPos(player.getX(), player.getY(), player.getZ());
        }
    }

    public void updateRot(SpellEntity entity) {
        if (entity.getOwner() != null) {
            entity.setYRot(entity.getOwner().getYRot());
            entity.setXRot(entity.getOwner().getXRot());
            entity.yRotO = entity.getOwner().yRotO;
            entity.xRotO = entity.getOwner().xRotO;
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
/*        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("spell_data")) {
            CompoundTag stackSpellData = nbt.getCompound("spell_data");
            if (stackSpellData.contains("entity")) {
                return UseAnim.CUSTOM;
            }
        }*/
        return UseAnim.CUSTOM;
    }

    @Override
    public boolean hasCustomAnimation(ItemStack stack) {
/*        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("spell_data")) {
            CompoundTag stackSpellData = nbt.getCompound("spell_data");
            if (stackSpellData.contains("entity")) {
                return true;
            }
        }*/
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemAnimation getAnimation(ItemStack stack) {
        return animation;
    }

    public int tickCost() {
        return 7;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(SpellEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
        HitResult ray = getHitResult(entity, entity.position(), entity.getLookAngle().scale(getRayDistance()).add(0, 0.3F, 0), entity.level(), (e) -> {
            return !e.isSpectator() && e.isPickable() && !e.equals(entity.getOwner());
        });

        Color color = getColor();
        Color secondColor = getSecondColor();

        float offset = 1.0f;
        updateRot(entity);

        float width = 1f;
/*        if (entity.tickCount < 3) {
            width = (entity.tickCount + partialTicks) / 3;
        } else {
            CompoundTag spellData = entity.getSpellData();
            if (spellData.contains("tick_left")) {
                if (spellData.getInt("tick_left") > 0) {
                    width = 1f - (((entity.tickCount - 1) - spellData.getInt("tick_left") + partialTicks) / 4);
                }
            }
        }*/
        if (width > 1f) width = 1f;
        if (width < 0f) width = 0f;

        poseStack.pushPose();
        float distance = (float) Math.sqrt(Math.pow(entity.getX() - ray.getLocation().x(), 2) + Math.pow(entity.getY() - ray.getLocation().y(), 2) + Math.pow(entity.getZ() - ray.getLocation().z(), 2));
        Vec3 pos1 = entity.position().add(entity.getViewVector(partialTicks).scale(offset)).add(0, 0.2f, 0);
        Vec3 pos2 = entity.position().add(entity.getViewVector(partialTicks).scale(distance)).add(0, 0.3f, 0);;
        poseStack.translate(-entity.getX(), -entity.getY(), -entity.getZ());
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/trail"))
                .setColor(color)
                .renderBeam(poseStack.last().pose(), pos1, pos2, 0.15f * width, 0.15f * Mth.lerp(distance / getRayDistance(), 1f, 0.5f));
        poseStack.popPose();
    }

    public HitResult getHitResult(Entity projectile, Vec3 startVec, Vec3 endVecOffset, Level level, Predicate<Entity> filter) {
        return getHitResultStandard(projectile, startVec, endVecOffset, level, filter);
    }

    public static HitResult getHitResultStandard(Entity projectile, Vec3 startVec, Vec3 endVecOffset, Level level, Predicate<Entity> filter) {
        Vec3 vec3 = startVec.add(endVecOffset);
        HitResult hitresult = level.clip(new ClipContext(startVec, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, projectile));
        if (hitresult.getType() != HitResult.Type.MISS) {
            vec3 = hitresult.getLocation();
        }

        HitResult hitresult1 = ProjectileUtil.getEntityHitResult(level, projectile, startVec, vec3, projectile.getBoundingBox().expandTowards(endVecOffset).inflate(1.0D), filter);
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
