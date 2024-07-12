package mod.maxbogomol.wizards_reborn.client.event;

import com.google.common.collect.Multimap;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.client.gui.components.CustomLogoRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {
    public static ResourceLocation panorama = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/title/background/panorama_0");
    public static int attributeModifierTooltip = 0;

    @SubscribeEvent
    public void openMainMenu(ScreenEvent.Opening event) {
        if (event.getScreen() instanceof TitleScreen titleScreen) {
            if (ClientConfig.CUSTOM_PANORAMA.get()) {
                boolean setPanorama = false;
                if (WizardsRebornClient.firstScreen) {
                    setPanorama = true;
                    WizardsRebornClient.firstScreen = false;
                }
                if (!TitleScreen.CUBE_MAP.images[0].equals(panorama)) {
                    setPanorama = true;
                }
                if (setPanorama) {
                    float spin = titleScreen.panorama.spin;
                    float bob = titleScreen.panorama.bob;
                    ResourceLocation base = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/title/background/panorama");
                    TitleScreen.CUBE_MAP = new CubeMap(base);
                    titleScreen.panorama = new PanoramaRenderer(TitleScreen.CUBE_MAP);
                    titleScreen.logoRenderer = new CustomLogoRenderer(false);
                    titleScreen.panorama.spin = spin;
                    titleScreen.panorama.bob = bob;
                }
            }
        }
    }

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
                    if (i > event.getToolTip().size()) attributeModifierTooltip = event.getToolTip().size();
                    event.getToolTip().addAll(i, ArcaneEnchantmentUtils.modifiersAppendHoverText(stack, player.level(), event.getFlags()));
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
