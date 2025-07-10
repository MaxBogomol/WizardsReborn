package mod.maxbogomol.wizards_reborn.common.arcaneenchantment;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.capability.IFireworkModifier;
import mod.maxbogomol.wizards_reborn.common.capability.IPlayerModifier;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.entity.PlayerModifierUpdatePacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;

import java.awt.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class FireworkJumpArcaneEnchantment extends ArcaneEnchantment {

    public static ItemStack crossbowStack;
    public static boolean entityHit = false;

    public FireworkJumpArcaneEnchantment(String id, int maxLevel) {
        super(id, maxLevel);
    }

    @Override
    public Color getColor() {
        return new Color(255, 113, 113);
    }

    @Override
    public boolean canEnchantItem(ItemStack stack) {
        if (ArcaneEnchantmentUtil.isArcaneItem(stack)) {
            return ArcaneEnchantmentUtil.getArcaneEnchantmentTypes(stack).contains(ArcaneEnchantmentTypes.CROSSBOW);
        }
        return false;
    }

    public static void onCrossbowShot(Projectile projectile, Level level, LivingEntity shooter, InteractionHand hand, ItemStack crossbowStack, ItemStack ammoStack, float soundPitch, boolean isCreativeMode, float velocity, float inaccuracy, float projectileAngle) {
        if (shooter instanceof Player player && ArcaneEnchantmentUtil.isArcaneItem(crossbowStack)) {
            int enchantmentLevel = ArcaneEnchantmentUtil.getArcaneEnchantment(crossbowStack, WizardsRebornArcaneEnchantments.FIREWORK_JUMP);
            if (enchantmentLevel > 0) {
                if (projectile instanceof FireworkRocketEntity rocket) {
                    rocket.getCapability(IFireworkModifier.INSTANCE, null).ifPresent((w) -> {
                        float costModifier = WissenUtil.getWissenCostModifierWithDiscount(player);
                        List<ItemStack> items = WissenUtil.getWissenItemsNoneAndStorage(WissenUtil.getWissenItemsCurios(player));
                        int wissen = WissenUtil.getWissenInItems(items);
                        int cost = (int) ((50 + (20 * enchantmentLevel)) * (1 - costModifier));
                        if (cost <= 0) {
                            cost = 1;
                        }

                        if (WissenUtil.canRemoveWissen(wissen, cost)) {
                            WissenUtil.removeWissenFromWissenItems(items, cost);
                            w.setJump(enchantmentLevel / 3f);
                        }
                    });
                }
            }
        }
    }

    public static void onHitFirst(FireworkRocketEntity rocket, LivingEntity livingEntity) {
        if (!rocket.level().isClientSide()) {
            if (isJump(rocket)) {
                livingEntity.invulnerableTime = 0;
            }
        }
    }

    public static void onHit(FireworkRocketEntity rocket, LivingEntity livingEntity) {
        if (!rocket.level().isClientSide()) {
            if (isJump(rocket)) {
                int momentEnchantmentLevel = ArcaneEnchantmentUtil.getArcaneEnchantment(crossbowStack, WizardsRebornArcaneEnchantments.MOMENT);

                float jump = getJump(rocket);
                float f = 0.0F;
                ItemStack itemstack = rocket.getItem();
                CompoundTag compoundtag = itemstack.isEmpty() ? null : itemstack.getTagElement("Fireworks");
                ListTag listtag = compoundtag != null ? compoundtag.getList("Explosions", 10) : null;
                if (listtag != null && !listtag.isEmpty()) {
                    f = 5.0F + (float) (listtag.size() * 2);
                }

                float f1 = f * (float) Math.sqrt((5.0D - (double) rocket.distanceTo(livingEntity)) / 5.0D);
                if (momentEnchantmentLevel > 0 && rocket.getOwner() == livingEntity)  f1 = f;
                float p = (f1 / 6f);
                Vec3 vec = livingEntity.position().subtract(rocket.position()).normalize().scale(p).scale(jump);
                if (momentEnchantmentLevel > 0 && rocket.getOwner() == livingEntity) {
                    vec = rocket.getOwner().getLookAngle().reverse().normalize().scale(p).scale(jump);
                    if (!entityHit) {
                        livingEntity.setDeltaMovement(Vec3.ZERO);
                        entityHit = true;
                    }
                }
                livingEntity.push(vec.x(), vec.y(), vec.z());
                livingEntity.hurtMarked = true;
                if (livingEntity instanceof Player player) {
                    if (getFireworkJump(player) < jump) setFireworkJump(player, jump);
                }
            }
        }
    }

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (!player.level().isClientSide()) {
            if (isFireworkJump(player)) removeFireworkJump(player, 0.015f);
        }
    }

    public static boolean isJump(Entity entity) {
        if (!(entity instanceof FireworkRocketEntity)) return false;
        AtomicBoolean isJump = new AtomicBoolean(false);
        entity.getCapability(IFireworkModifier.INSTANCE, null).ifPresent((w) -> {
            isJump.set(w.isJump());
        });
        return isJump.get();
    }

    public static float getJump(Entity entity) {
        if (!(entity instanceof FireworkRocketEntity)) return 0;
        AtomicReference<Float> jump = new AtomicReference<>((float) 0);
        entity.getCapability(IFireworkModifier.INSTANCE, null).ifPresent((w) -> {
            jump.set(w.getJump());
        });
        return jump.get();
    }

    public static boolean isFireworkJump(Entity entity) {
        if (!(entity instanceof Player)) return false;
        AtomicBoolean isJump = new AtomicBoolean(false);
        entity.getCapability(IPlayerModifier.INSTANCE, null).ifPresent((w) -> {
            isJump.set(w.isFireworkJump());
        });
        return isJump.get();
    }

    public static float getFireworkJump(Player entity) {
        if (!(entity instanceof Player)) return 0;
        AtomicReference<Float> jump = new AtomicReference<>((float) 0);
        entity.getCapability(IPlayerModifier.INSTANCE, null).ifPresent((w) -> {
            jump.set(w.getFireworkJump());
        });
        return jump.get();
    }

    public static void removeFireworkJump(Entity entity, float jump) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IPlayerModifier.INSTANCE, null).ifPresent((w) -> {
            w.removeFireworkJump(jump);

            WizardsRebornPacketHandler.sendTo((Player) entity, new PlayerModifierUpdatePacket((Player) entity));
        });
    }

    public static void setFireworkJump(Entity entity, float jump) {
        if (!(entity instanceof Player)) return;
        entity.getCapability(IPlayerModifier.INSTANCE, null).ifPresent((w) -> {
            w.setFireworkJump(jump);

            WizardsRebornPacketHandler.sendTo((Player) entity, new PlayerModifierUpdatePacket((Player) entity));
        });
    }
}
