package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public abstract class GuiGraphicsMixin {
    @Inject(at = @At(value = "TAIL"), method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V")
    private void wizards_reborn$renderItem(LivingEntity pEntity, Level pLevel, ItemStack pStack, int pX, int pY, int pSeed, int pGuiOffset, CallbackInfo ci) {
        if (ClientConfig.ITEM_GUI_PARTICLE.get()) {
            if (ArcaneEnchantmentUtils.isArcaneItem(pStack)) {
                int i = 0;
                for (ArcaneEnchantment enchantment : ArcaneEnchantments.getArcaneEnchantments()) {
                    int levelEnchantment = ArcaneEnchantmentUtils.getArcaneEnchantment(pStack, enchantment);
                    if (levelEnchantment > 0) {
                        GuiGraphics self = (GuiGraphics) ((Object) this);
                        enchantment.renderParticle(self.pose(), pEntity, pLevel, pStack, pX, pY, pSeed, pGuiOffset, i);
                        i++;
                    }
                }
            }
        }
    }
}
