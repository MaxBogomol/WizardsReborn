package mod.maxbogomol.wizards_reborn.mixin;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ScytheItem;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Unique
    public AttributeModifier wizards_reborn$attributeModifier;

    @ModifyVariable(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getId()Ljava/util/UUID;", ordinal = 0), index = 13)
    public AttributeModifier wizards_reborn$getTooltip(AttributeModifier value) {
        this.wizards_reborn$attributeModifier = value;
        return value;
    }

    @ModifyVariable(method = "getTooltipLines", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getOperation()Lnet/minecraft/world/entity/ai/attributes/AttributeModifier$Operation;", ordinal = 0), index = 16)
    public boolean wizards_reborn$getTooltip(boolean value, @Nullable Player player, TooltipFlag flag) {
        if (player != null) {
            if (wizards_reborn$attributeModifier.getId().equals(ScytheItem.BASE_ENTITY_REACH_UUID)) {
                return true;
            }
        }
        return value;
    }

    @ModifyVariable(method = "getTooltipLines", at = @At("STORE"))
    public Multimap<Attribute, AttributeModifier> wizards_reborn$getTooltip(Multimap<Attribute, AttributeModifier> map, @Nullable Player player, TooltipFlag flag) {
        if (player != null) {
            Multimap<Attribute, AttributeModifier> copied = LinkedHashMultimap.create();
            for (Map.Entry<Attribute, AttributeModifier> entry : map.entries()) {
                Attribute key = entry.getKey();
                AttributeModifier modifier = entry.getValue();
                double amount = modifier.getAmount();
                boolean flagAdd = false;
                AttributeModifier.Operation operation = modifier.getOperation();

                if (modifier.getId().equals(ScytheItem.BASE_ENTITY_REACH_UUID)) flagAdd = true;
                if (key.equals(WizardsReborn.WISSEN_DISCOUNT.get())) {
                    operation = AttributeModifier.Operation.MULTIPLY_BASE;
                    amount = amount / 100f;
                    flagAdd = true;
                }
                if (key.equals(WizardsReborn.MAGIC_ARMOR.get())) {
                    operation = AttributeModifier.Operation.MULTIPLY_BASE;
                    amount = amount / 100f;
                    flagAdd = true;
                }

                if (flagAdd) {
                    copied.put(key, new AttributeModifier(
                            modifier.getId(), modifier.getName(), amount, operation
                    ));
                } else {
                    copied.put(key, modifier);
                }
            }

            return copied;
        }
        return map;
    }
}