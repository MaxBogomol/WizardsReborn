package mod.maxbogomol.wizards_reborn.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneShearsItem;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Unique
    public boolean wizards_reborn$renderShears;

    @ModifyVariable(method = "renderArmWithItem", at = @At(value = "HEAD"), index = 6, argsOnly = true)
    public ItemStack wizards_reborn$renderArmWithItem(ItemStack stack) {
        wizards_reborn$renderShears = false;
        if (stack.getItem() instanceof ArcaneShearsItem) {
            wizards_reborn$renderShears = true;
            if (ArcaneShearsItem.getOpen(stack) == 2) return ItemStack.EMPTY;
        }
        return stack;
    }

    @ModifyVariable(method = "renderArmWithItem", at = @At(value = "HEAD"), index = 4, argsOnly = true)
    public InteractionHand wizards_reborn$renderArmWithItem(InteractionHand hand) {
        if (!(wizards_reborn$renderShears && hand == InteractionHand.OFF_HAND)) {
            wizards_reborn$renderShears = false;
        }
        return hand;
    }

    @ModifyVariable(method = "renderArmWithItem", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 0))
    public boolean wizards_reborn$renderArmWithItem(boolean value, AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {
        if (wizards_reborn$renderShears) return true;
        return value;
    }
}