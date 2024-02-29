package mod.maxbogomol.wizards_reborn.mixin;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerMixin {
    @Inject(method = "createAttributes", at = @At("RETURN"))
    private static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> ci) {
        ci.getReturnValue().add(WizardsReborn.WISSEN_DISCOUNT.get());
        ci.getReturnValue().add(WizardsReborn.MAGIC_ARMOR.get());
    }
}