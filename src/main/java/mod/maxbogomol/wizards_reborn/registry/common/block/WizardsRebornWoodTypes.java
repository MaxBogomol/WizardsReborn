package mod.maxbogomol.wizards_reborn.registry.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class WizardsRebornWoodTypes {
    public static final WoodType ARCANE_WOOD = WoodType.register(new WoodType(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood").toString(), WizardsRebornBlockSetTypes.ARCANE_WOOD,
            WizardsRebornSounds.ARCANE_WOOD, WizardsRebornSounds.ARCANE_WOOD_HANGING_SIGN,
            WizardsRebornSounds.ARCANE_WOOD_FENCE_GATE_CLOSE.get(), WizardsRebornSounds.ARCANE_WOOD_FENCE_GATE_OPEN.get()));

    public static final WoodType INNOCENT_WOOD = WoodType.register(new WoodType(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood").toString(), WizardsRebornBlockSetTypes.INNOCENT_WOOD,
            WizardsRebornSounds.INNOCENT_WOOD, WizardsRebornSounds.INNOCENT_WOOD_HANGING_SIGN,
            WizardsRebornSounds.INNOCENT_WOOD_FENCE_GATE_CLOSE.get(), WizardsRebornSounds.INNOCENT_WOOD_FENCE_GATE_OPEN.get()));

    public static final WoodType CORK_BAMBOO = WoodType.register(new WoodType(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo").toString(), WizardsRebornBlockSetTypes.CORK_BAMBOO,
            WizardsRebornSounds.CORK_BAMBOO_WOOD, WizardsRebornSounds.ARCANE_WOOD_HANGING_SIGN,
            WizardsRebornSounds.CORK_BAMBOO_FENCE_GATE_CLOSE.get(), WizardsRebornSounds.CORK_BAMBOO_FENCE_GATE_OPEN.get()));

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerWoodTypes(FMLClientSetupEvent event) {
            Sheets.addWoodType(WizardsRebornWoodTypes.ARCANE_WOOD);
            Sheets.addWoodType(WizardsRebornWoodTypes.INNOCENT_WOOD);
            Sheets.addWoodType(WizardsRebornWoodTypes.CORK_BAMBOO);
        }
    }
}
