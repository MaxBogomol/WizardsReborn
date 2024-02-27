package mod.maxbogomol.wizards_reborn.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.common.item.ICustomAnimationItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"), method = "renderArmWithItem")
    public void renderArmWithItem(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        if (pPlayer.isUsingItem() && pPlayer.getUseItemRemainingTicks() > 0 && pPlayer.getUsedItemHand() == pHand) {
            boolean isWand = false;
            if (ClientConfig.SPELLS_FIRST_PERSON_ITEM_ANIMATIONS.get()) {
                if (pStack.getItem() instanceof ArcaneWandItem) {
                    isWand = true;
                    CompoundTag nbt = pStack.getTag();
                    if (nbt.getBoolean("crystal")) {
                        if (nbt.getString("spell") != "") {
                            Spell spell = Spells.getSpell(nbt.getString("spell"));
                            if (spell.hasCustomAnimation(pStack) && spell.getAnimation(pStack) != null) {
                                spell.getAnimation(pStack).renderArmWithItem(pPlayer, pPartialTicks, pPitch, pHand, pSwingProgress, pStack, pEquippedProgress, pPoseStack, pBuffer, pCombinedLight);
                            }
                        }
                    }
                }
            }
            if (!isWand) {
                if (pStack.getItem() instanceof ICustomAnimationItem item) {
                    if (item.getAnimation(pStack) != null) {
                        item.getAnimation(pStack).renderArmWithItem(pPlayer, pPartialTicks, pPitch, pHand, pSwingProgress, pStack, pEquippedProgress, pPoseStack, pBuffer, pCombinedLight);
                    }
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "evaluateWhichHandsToRender", cancellable = true)
    private static void evaluateWhichHandsToRender(LocalPlayer pPlayer, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
        ItemStack itemstack = pPlayer.getUseItem();
        InteractionHand interactionhand = pPlayer.getUsedItemHand();
        if (itemstack.is(WizardsReborn.ARCANE_WOOD_BOW.get())) {
            cir.setReturnValue(ItemInHandRenderer.HandRenderSelection.onlyForHand(interactionhand));
        }
    }
}
