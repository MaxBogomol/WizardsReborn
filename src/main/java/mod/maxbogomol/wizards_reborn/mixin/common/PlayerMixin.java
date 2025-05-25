package mod.maxbogomol.wizards_reborn.mixin.common;

import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.FireworkJumpArcaneEnchantment;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "getFlyingSpeed", at = @At("TAIL"), cancellable = true)
    private void wizards_reborn$getFlyingSpeed(CallbackInfoReturnable<Float> cir) {
        Player self = (Player) ((Object) this);
        if (FireworkJumpArcaneEnchantment.isFireworkJump(self)) {
            cir.setReturnValue(cir.getReturnValue() * ((FireworkJumpArcaneEnchantment.getFireworkJump(self) + 1f) * 3f));
        }
    }
}