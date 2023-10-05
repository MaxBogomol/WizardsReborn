package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
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
            if (player.getItemInHand(player.getUsedItemHand()).getItem() instanceof ArcaneWandItem) {
                if (pEntity.isUsingItem() && pEntity.getUseItemRemainingTicks() > 0) {
                    ItemStack stack = player.getItemInHand(player.getUsedItemHand());
                    CompoundTag nbt = stack.getTag();
                    if (nbt.getBoolean("crystal")) {
                        if (nbt.getString("spell") != "") {
                            Spell spell = Spells.getSpell(nbt.getString("spell"));
                            if (spell.hasCustomAnimation(stack)) {
                                if (player.getUsedItemHand() == InteractionHand.MAIN_HAND) {
                                    spell.setupAnimRight(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                                } else {
                                    spell.setupAnimLeft(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                                }
                                spell.setupAnim(self, pEntity, pLimbSwing, pLimbSwingAmount, pAgeInTicks, pNetHeadYaw, pHeadPitch);
                            }
                        }
                    }
                }
            }
        }
    }
}
