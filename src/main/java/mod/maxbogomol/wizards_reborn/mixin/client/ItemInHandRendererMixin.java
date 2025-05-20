package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneShearsItem;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @ModifyVariable(method = "renderArmWithItem", at = @At(value = "HEAD"), index = 6, argsOnly = true)
    public ItemStack wizards_reborn$renderArmWithItem(ItemStack stack) {
        if (stack.getItem() instanceof ArcaneShearsItem) {
            if (ArcaneShearsItem.getOpen(stack) == 2) return ItemStack.EMPTY;
        }
        return stack;
    }
}