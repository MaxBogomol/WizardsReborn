package mod.maxbogomol.wizards_reborn.client.event;

import com.google.common.collect.Multimap;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {
    public static int attributeModifierTooltip = 0;

    @SubscribeEvent
    public void loggedPlayer(PlayerEvent.PlayerLoggedInEvent event) {
        ArcanemiconGui.currentChapter = ArcanemiconChapters.ARCANE_NATURE_INDEX;
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();

        if (player != null) {
            if (ArcaneEnchantmentUtils.isArcaneItem(stack)) {
                boolean draw = false;
                for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
                    Multimap<Attribute, AttributeModifier> multimap = stack.getAttributeModifiers(equipmentslot);
                    if (!multimap.isEmpty()) {
                        draw = true;
                        break;
                    }
                }

                if (draw) {
                    int i = attributeModifierTooltip + 1;
                    if (i < event.getToolTip().size()) {
                        event.getToolTip().addAll(i, ArcaneEnchantmentUtils.modifiersAppendHoverText(stack, player.level(), event.getFlags()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void input(MovementInputUpdateEvent event) {
        if (Minecraft.getInstance().player != null) {
            if (Minecraft.getInstance().player.hasEffect(WizardsReborn.TIPSY_EFFECT.get())) {
                //event.getInput().right = true;
                event.getInput().forwardImpulse = 1f;
            }
        }
    }
}
