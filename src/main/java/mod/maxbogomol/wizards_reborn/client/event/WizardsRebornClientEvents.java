package mod.maxbogomol.wizards_reborn.client.event;

import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Either;
import mod.maxbogomol.fluffy_fur.client.tooltip.TooltipModifierHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidItem;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamItem;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.gui.tooltip.ValueFrameTooltipComponent;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.EagleShotArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.SplitArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.common.effect.IrritationEffect;
import mod.maxbogomol.wizards_reborn.common.effect.MorSporesEffect;
import mod.maxbogomol.wizards_reborn.common.effect.WissenAuraEffect;
import mod.maxbogomol.wizards_reborn.common.item.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.RunicWisestonePlateItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneBowItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class WizardsRebornClientEvents {

    @SubscribeEvent
    public void loggedPlayer(PlayerEvent.PlayerLoggedInEvent event) {
        ArcanemiconGui.currentChapter = ArcanemiconChapters.ARCANE_NATURE_INDEX;
    }

    @SubscribeEvent
    public void onRenderGuiOverlayEvent(RenderGuiOverlayEvent.Pre event) {
        if (WizardsReborn.proxy.getPlayer().isAlive()) {
            if (event.getOverlay().id() == VanillaGuiOverlay.CROSSHAIR.id()) {
                WissenWandItem.drawWissenGui(event.getGuiGraphics());
                ArcaneWandItem.drawWandGui(event.getGuiGraphics());
            }
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
    public void onTooltipRenderColor(RenderTooltipEvent.Color event) {
        ArcanemiconItem.onTooltipRenderColor(event);
        FracturedCrystalItem.onTooltipRenderColor(event);
        CrystalItem.onTooltipRenderColor(event);
        ArcaneLumosItem.onTooltipRenderColor(event);
        EngravedWisestoneItem.onTooltipRenderColor(event);
        RunicWisestonePlateItem.onTooltipRenderColor(event);
        SkinTrimItem.onTooltipRenderColor(event);
    }

    @SubscribeEvent
    public void onTooltipRenderGatherComponents(RenderTooltipEvent.GatherComponents event) {
        ItemStack stack = event.getItemStack();
        List<Either<FormattedText, TooltipComponent>> tooltipElements = event.getTooltipElements();
        int i = 1;
        if (stack.getItem() instanceof IWissenItem item) {
            WissenItemUtil.existWissen(stack);
            ValueFrameTooltipComponent component = new ValueFrameTooltipComponent(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/wissen_frame.png"), (float) item.getMaxWissen() / WissenItemUtil.getWissen(stack));
            addTooltipComponent(component, i, tooltipElements);
            i++;
        }
        if (stack.getItem() instanceof IFluidItem item) {
            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("fluidTank")) {
                ValueFrameTooltipComponent component = new ValueFrameTooltipComponent(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/fluid_frame.png"), (float) item.getMaxFluid(stack) / nbt.getCompound("fluidTank").getInt("Amount"));
                addTooltipComponent(component, i, tooltipElements);
                i++;
            }
        }
        if (stack.getItem() instanceof ISteamItem item) {
            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("steam")) {
                ValueFrameTooltipComponent component = new ValueFrameTooltipComponent(new ResourceLocation(WizardsReborn.MOD_ID + ":textures/gui/steam_frame.png"), (float) item.getMaxSteam() / nbt.getInt("steam"));
                addTooltipComponent(component, i, tooltipElements);
            }
        }
    }

    public static void addTooltipComponent(TooltipComponent component, int i, List<Either<FormattedText, TooltipComponent>> tooltipElements) {
        if (component != null) {
            if (tooltipElements.size() >= 1) {
                tooltipElements.add(i, Either.right(component));
            } else {
                tooltipElements.add(Either.right(component));
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
    public void onCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        MorSporesEffect.onCameraAngles(event);
    }

    @SubscribeEvent
    public void onFov(ViewportEvent.ComputeFov event) {
        MorSporesEffect.onFov(event);
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent event) {
        WissenLimitHandler.clientTick(event);
        MorSporesEffect.clientTick(event);
        WissenAuraEffect.clientTick(event);
        IrritationEffect.clientTick(event);
    }

    @SubscribeEvent
    public void input(MovementInputUpdateEvent event) {
        if (Minecraft.getInstance().player != null) {
            if (Minecraft.getInstance().player.hasEffect(WizardsRebornMobEffects.TIPSY.get())) {
                //event.getInput().right = true;
                event.getInput().forwardImpulse = 1f;
            }
        }
    }
}
