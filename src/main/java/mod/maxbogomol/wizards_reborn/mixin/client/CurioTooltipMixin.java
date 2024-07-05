package mod.maxbogomol.wizards_reborn.mixin.client;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ScytheItem;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import top.theillusivec4.curios.client.ClientEventHandler;

import java.util.Map;

@Mixin(ClientEventHandler.class)
public class CurioTooltipMixin {

    @ModifyVariable(method = "onTooltip", at = @At("STORE"), remap = false)
    public Multimap<Attribute, AttributeModifier> wizards_reborn$getTooltip(Multimap<Attribute, AttributeModifier> multimap, ItemTooltipEvent event) {
        if (event != null && multimap != null) {
            Multimap<Attribute, AttributeModifier> copied = LinkedHashMultimap.create();
            for (Map.Entry<Attribute, AttributeModifier> entry : multimap.entries()) {
                Attribute key = entry.getKey();
                if (key != null) {
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
            }

            return copied;
        }
        return multimap;
    }
}