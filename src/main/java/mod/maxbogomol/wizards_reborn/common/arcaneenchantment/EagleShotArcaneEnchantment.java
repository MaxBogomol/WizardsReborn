package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.network.AddScreenshakePacket;
import mod.maxbogomol.wizards_reborn.common.network.EagleShotRayEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class EagleShotArcaneEnchantment extends ArcaneEnchantment {
    public static Random random = new Random();

    public EagleShotArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(255, 238, 130);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (stack.getItem() instanceof IArcaneItem item) {
            if (ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.SPLIT_ARCANE_ENCHANTMENT) > 0) return false;
            return item.getArcaneEnchantmentTypes().contains(ArcaneEnchantmentType.BOW);
        }
        return false;
    }

    public static void onBowShot(AbstractArrow abstractarrow, ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        if (entityLiving instanceof Player player && ArcaneEnchantmentUtils.isArcaneItem(stack)) {
            int enchantmentLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.EAGLE_SHOT_ARCANE_ENCHANTMENT);
            if (enchantmentLevel > 0) {
                if (BowItem.getPowerForTime(stack.getUseDuration() - timeLeft) >= 1f) {
                    float costModifier = WissenUtils.getWissenCostModifierWithDiscount(player);
                    List<ItemStack> items = WissenUtils.getWissenItemsNoneAndStorage(WissenUtils.getWissenItemsCurios(player));
                    int wissen = WissenUtils.getWissenInItems(items);
                    int cost = (int) ((30 + (enchantmentLevel * 20)) * (1 - costModifier));
                    if (cost <= 0) {
                        cost = 1;
                    }

                    if (WissenUtils.canRemoveWissen(wissen, cost)) {
                        WissenUtils.removeWissenFromWissenItems(items, cost);

                        float distance = (enchantmentLevel * 10) + 2f;
                        Vec3 movement = abstractarrow.getDeltaMovement();
                        double dX = movement.x();
                        double dY = movement.y();
                        double dZ = movement.z();

                        double yaw = Math.atan2(dZ, dX);
                        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

                        double X = Math.sin(pitch) * Math.cos(yaw) * distance;
                        double Y = Math.cos(pitch) * distance;
                        double Z = Math.sin(pitch) * Math.sin(yaw) * distance;

                        Vec3 vec = new Vec3(-X, -Y, -Z);

                        HitResult ray = getHitResult(abstractarrow.getEyePosition(), abstractarrow, (e) -> {
                            return !e.isSpectator() && e.isPickable() && (!e.equals(player));
                        }, vec, abstractarrow.level());

                        Vec3 loc = ray.getLocation();
                        if (ray.getType() == HitResult.Type.ENTITY) {
                            loc = ((EntityHitResult) ray).getLocation();
                        } else if (ray.getType() == HitResult.Type.BLOCK) {
                            loc = ((BlockHitResult) ray).getLocation();
                        } else {
                            loc = abstractarrow.getEyePosition().add(vec);
                        }
                        Vec3 from = abstractarrow.getEyePosition();
                        distance = (float) Math.sqrt(Math.pow(from.x() - loc.x(), 2) + Math.pow(from.y() - loc.y(), 2) + Math.pow(from.z() - loc.z(), 2));
                        distance = distance - 2f;
                        if (distance < 0) distance = 0;

                        X = Math.sin(pitch) * Math.cos(yaw) * distance;
                        Y = Math.cos(pitch) * distance;
                        Z = Math.sin(pitch) * Math.sin(yaw) * distance;

                        abstractarrow.setPos(from.x() - X, from.y() - Y, from.z() - Z);
                        player.hurtMarked = true;
                        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ANVIL_PLACE, SoundSource.PLAYERS, 0.05f, 2f);
                        if (!level.isClientSide()) {
                            Color color = WizardsReborn.EAGLE_SHOT_ARCANE_ENCHANTMENT.getColor();
                            float r = color.getRed() / 255f;
                            float g = color.getGreen() / 255f;
                            float b = color.getBlue() / 255f;
                            PacketHandler.sendTo(player, new AddScreenshakePacket(0.3f));
                            PacketHandler.sendToTracking(level, player.getOnPos(), new EagleShotRayEffectPacket((float) from.x, (float) from.y, (float) from.z, (float) (from.x() - X), (float) (from.y() - Y), (float) (from.z() - Z), (float) movement.x, (float) movement.y, (float) movement.z, r, g, b));
                        }
                    }
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static float getFOW(Player player, ItemStack stack, float fow) {
        int enchantmentLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.EAGLE_SHOT_ARCANE_ENCHANTMENT);
        if (enchantmentLevel > 0) {
            fow = fow - (BowItem.getPowerForTime(player.getTicksUsingItem()) * 0.4f) - 0.3f;
            if (fow < 0) fow = 0;
        }
        return fow;
    }

    public static HitResult getHitResult(Vec3 pStartVec, Entity pProjectile, Predicate<Entity> pFilter, Vec3 pEndVecOffset, Level pLevel) {
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
}
