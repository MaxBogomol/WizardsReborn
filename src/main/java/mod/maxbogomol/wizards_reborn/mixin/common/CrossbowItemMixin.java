package mod.maxbogomol.wizards_reborn.mixin.common;

import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.FireworkJumpArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.MomentArcaneEnchantment;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {

    @Unique
    private static Projectile wizards_reborn$projectile;

    @Inject(at = @At("HEAD"), method = "performShooting")
    private static void wizards_reborn$performShooting(Level level, LivingEntity shooter, InteractionHand usedHand, ItemStack crossbowStack, float velocity, float inaccuracy, CallbackInfo ci) {
        FireworkJumpArcaneEnchantment.entityHit = false;
    }

    @ModifyVariable(method = "shootProjectile", at = @At("STORE"))
    private static Projectile wizards_reborn$shootProjectile(Projectile projectile, Level level, LivingEntity shooter, InteractionHand hand, ItemStack crossbowStack, ItemStack ammoStack, float soundPitch, boolean isCreativeMode, float velocity, float inaccuracy, float projectileAngle) {
        wizards_reborn$projectile = projectile;
        FireworkJumpArcaneEnchantment.crossbowStack = crossbowStack;
        FireworkJumpArcaneEnchantment.onCrossbowShot(projectile, level, shooter, hand, crossbowStack, ammoStack, soundPitch, isCreativeMode, velocity, inaccuracy, projectileAngle);
        return projectile;
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), method = "shootProjectile")
    private static void wizards_reborn$shootProjectile(Level level, LivingEntity shooter, InteractionHand hand, ItemStack crossbowStack, ItemStack ammoStack, float soundPitch, boolean isCreativeMode, float velocity, float inaccuracy, float projectileAngle, CallbackInfo ci) {

    }

    @Inject(at = @At("TAIL"), method = "shootProjectile")
    private static void wizards_reborn$shootProjectileTail(Level level, LivingEntity shooter, InteractionHand hand, ItemStack crossbowStack, ItemStack ammoStack, float soundPitch, boolean isCreativeMode, float velocity, float inaccuracy, float projectileAngle, CallbackInfo ci) {
        MomentArcaneEnchantment.onCrossbowShot(wizards_reborn$projectile, level, shooter, hand, crossbowStack, ammoStack, soundPitch, isCreativeMode, velocity, inaccuracy, projectileAngle);
    }
}
