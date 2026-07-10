package mod.maxbogomol.wizards_reborn.integration.client.athena;

import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class WizardsRebornAthena {
    public static boolean LOADED;

    public static class ClientLoadedOnly {
        public static void init() {
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.ALCHEMY_GLASS.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.WHITE_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.LIGHT_GRAY_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.GRAY_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.BLACK_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.BROWN_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.RED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.ORANGE_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.YELLOW_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.LIME_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.GREEN_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.CYAN_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.LIGHT_BLUE_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.BLUE_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.PURPLE_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.MAGENTA_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.PINK_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.RAINBOW_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.COSMIC_LUMINAL_GLASS.get(), RenderType.translucent());

            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.WHITE_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.LIGHT_GRAY_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.GRAY_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.BLACK_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.BROWN_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.RED_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.ORANGE_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.YELLOW_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.LIME_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.GREEN_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.CYAN_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.LIGHT_BLUE_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.BLUE_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.PURPLE_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.MAGENTA_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.PINK_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.RAINBOW_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WizardsRebornBlocks.COSMIC_FRAMED_LUMINAL_GLASS.get(), RenderType.translucent());
        }
    }

    public static void init(IEventBus eventBus) {
        LOADED = ModList.get().isLoaded("athena");
    }

    public static void setup() {
        if (isLoaded()) {
            WizardsRebornAthena.ClientLoadedOnly.init();
        }
    }

    public static boolean isLoaded() {
        return LOADED;
    }
}
