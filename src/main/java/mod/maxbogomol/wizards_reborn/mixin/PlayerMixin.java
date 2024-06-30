package mod.maxbogomol.wizards_reborn.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public class PlayerMixin {
    /*
    @Inject(method = "createAttributes", at = @At("RETURN"))
    private static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> ci) {
        ci.getReturnValue().add(WizardsReborn.WISSEN_DISCOUNT.get());
        ci.getReturnValue().add(WizardsReborn.MAGIC_ARMOR.get());
        ci.getReturnValue().add(WizardsReborn.MAGIC_MODOFIER.get());
    }*/
}