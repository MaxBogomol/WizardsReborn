package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.common.item.ICustomAnimationItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin<T extends LivingEntity>  {

    @Inject(at = @At("RETURN"), method = "setupAnim")
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch, CallbackInfo ci) {
        HumanoidModel self = (HumanoidModel) ((Object) this);
        if (pEntity instanceof Player player) {
            if (pEntity.isUsingItem() && pEntity.getUseItemRemainingTicks() > 0) {
                boolean isWand = false;
                if (ClientConfig.SPELLS_ANIMATIONS.get()) {
                    if (player.getItemInHand(player.getUsedItemHand()).getItem() instanceof ArcaneWandItem) {
                        isWand = true;
                        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
                        CompoundTag nbt = stack.getTag();
                        if (nbt.getBoolean("crystal")) {
                            if (nbt.getString("spell") != "") {
                                Spell spell = Spells.getSpell(nbt.getString("spell"));
                                if (spell.hasCustomAnimation(stack) && spell.getAnimation(stack) != null) {
                                    if (player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                                        spell.getAnimation(stack).setupAnimRight(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                                    } else {
                                        spell.getAnimation(stack).setupAnimLeft(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                                    }
                                    spell.getAnimation(stack).setupAnim(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                                }
                            }
                        }
                    }
                }
                if (!isWand) {
                    if (player.getItemInHand(player.getUsedItemHand()).getItem() instanceof ICustomAnimationItem item) {
                        ItemStack stack = player.getItemInHand(player.getUsedItemHand());
                        if (item.getAnimation(stack) != null) {
                            if (player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                                item.getAnimation(stack).setupAnimRight(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                            } else {
                                item.getAnimation(stack).setupAnimLeft(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                            }
                            item.getAnimation(stack).setupAnim(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                        }
                    }
                }
            }
        }
    }
}
