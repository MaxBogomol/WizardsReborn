package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Inject(at = @At("HEAD"), method = "evaluateWhichHandsToRender", cancellable = true)
    private static void wizards_reborn$evaluateWhichHandsToRender(LocalPlayer pPlayer, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        ItemStack itemstack = pPlayer.getUseItem();
        InteractionHand interactionhand = pPlayer.getUsedItemHand();
        if (itemstack.is(WizardsReborn.ARCANE_WOOD_BOW.get())) {
            cir.setReturnValue(ItemInHandRenderer.HandRenderSelection.onlyForHand(interactionhand));
        }
    }
}
