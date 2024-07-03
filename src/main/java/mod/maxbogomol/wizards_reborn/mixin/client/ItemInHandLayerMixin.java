package mod.maxbogomol.wizards_reborn.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.common.item.ICustomAnimationItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public abstract class ItemInHandLayerMixin {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"), method = "renderArmWithItem")
    public void wizards_reborn$renderArmWithItem(LivingEntity pLivingEntity, ItemStack pItemStack, ItemDisplayContext pDisplayContext, HumanoidArm pArm, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, CallbackInfo ci) {
        if (pLivingEntity.isUsingItem() && pLivingEntity.getUseItemRemainingTicks() > 0) {
            boolean isWand = false;
            if (ClientConfig.SPELLS_ITEM_ANIMATIONS.get()) {
                if (pItemStack.getItem() instanceof ArcaneWandItem) {
                    isWand = true;
                    CompoundTag nbt = pItemStack.getTag();
                    if (nbt.getBoolean("crystal")) {
                        if (nbt.getString("spell") != "") {
                            Spell spell = Spells.getSpell(nbt.getString("spell"));
                            if (spell.hasCustomAnimation(pItemStack) && spell.getAnimation(pItemStack) != null) {
                                spell.getAnimation(pItemStack).renderArmWithItem(pLivingEntity, pItemStack, pDisplayContext, pArm, pPoseStack, pBuffer, pPackedLight);
                            }
                        }
                    }
                }
            }
            if (!isWand) {
                if (pItemStack.getItem() instanceof ICustomAnimationItem item) {
                    if (item.getAnimation(pItemStack) != null) {
                        item.getAnimation(pItemStack).renderArmWithItem(pLivingEntity, pItemStack, pDisplayContext, pArm, pPoseStack, pBuffer, pPackedLight);
                    }
                }
            }
        }
    }
}
