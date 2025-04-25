package mod.maxbogomol.wizards_reborn.mixin.common;

import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeHandler;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryChangeTrigger.class)
public class InventoryChangeTriggerMixin {

    @Inject(method = "trigger*", at = @At("HEAD"))
    private void wizards_reborn$trigger(ServerPlayer player, Inventory inventory, ItemStack stack, CallbackInfo ci) {
        KnowledgeHandler.itemKnowledgeListTrigger(player, stack);
    }
}