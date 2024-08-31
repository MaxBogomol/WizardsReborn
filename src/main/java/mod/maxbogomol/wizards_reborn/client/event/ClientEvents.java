package mod.maxbogomol.wizards_reborn.client.event;

import com.google.common.collect.Multimap;
import mod.maxbogomol.fluffy_fur.client.tooltip.TooltipModifierHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.EagleShotArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.SplitArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneBowItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {

    @SubscribeEvent
    public void loggedPlayer(PlayerEvent.PlayerLoggedInEvent event) {
        ArcanemiconGui.currentChapter = ArcanemiconChapters.ARCANE_NATURE_INDEX;
    }

    @SubscribeEvent
    public void onDrawScreenPost(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay().id() == VanillaGuiOverlay.CROSSHAIR.id()) {
            WissenWandItem.drawWissenGui(event.getGuiGraphics());
            ArcaneWandItem.drawWandGui(event.getGuiGraphics());
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();

        if (player != null) {
            if (ArcaneEnchantmentUtil.isArcaneItem(stack)) {
                boolean draw = false;
                for (EquipmentSlot equipmentslot : EquipmentSlot.values()) {
                    Multimap<Attribute, AttributeModifier> multimap = stack.getAttributeModifiers(equipmentslot);
                    if (!multimap.isEmpty()) {
                        draw = true;
                        break;
                    }
                }

                if (draw) {
                    int i = TooltipModifierHandler.getAttributeTooltipSize() + 1;
                    if (i < event.getToolTip().size()) {
                        event.getToolTip().addAll(i, ArcaneEnchantmentUtil.modifiersAppendHoverText(stack, player.level(), event.getFlags()));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void getFovModifier(ComputeFovModifierEvent event) {
        Player player = event.getPlayer();
        ItemStack itemStack = player.getUseItem();
        if (player.isUsingItem()) {
            if (itemStack.getItem() instanceof ArcaneBowItem) {
                event.setNewFovModifier(EagleShotArcaneEnchantment.getFOW(player, itemStack, event.getNewFovModifier()));
                event.setNewFovModifier(SplitArcaneEnchantment.getFOW(player, itemStack, event.getNewFovModifier()));
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
