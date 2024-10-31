package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.fluffy_fur.config.FluffyFurClientConfig;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentHandler;
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
    private void wizards_reborn$renderItem(LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset, CallbackInfo ci) {
        if (FluffyFurClientConfig.ITEM_GUI_PARTICLE.get()) {
            if (ArcaneEnchantmentUtil.isArcaneItem(stack)) {
                int i = 0;
                for (ArcaneEnchantment enchantment : ArcaneEnchantmentHandler.getArcaneEnchantments()) {
                    int levelEnchantment = ArcaneEnchantmentUtil.getArcaneEnchantment(stack, enchantment);
                    if (levelEnchantment > 0) {
                        GuiGraphics self = (GuiGraphics) ((Object) this);
                        enchantment.renderParticle(self.pose(), entity, level, stack, x, y, seed, guiOffset, i);
                        i++;
                    }
                }
            }
        }
    }
}
