package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class WizardsRebornWoodTypes {
    public static final LazyOptional<WoodType> ARCANE_WOOD = LazyOptional.of(() -> WoodType.register(new WoodType(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood").toString(), WizardsRebornBlockSetTypes.ARCANE_WOOD.resolve().get(),
            WizardsRebornSounds.ARCANE_WOOD, WizardsRebornSounds.ARCANE_WOOD_HANGING_SIGN,
            WizardsRebornSounds.ARCANE_WOOD_FENCE_GATE_CLOSE.get(), WizardsRebornSounds.ARCANE_WOOD_FENCE_GATE_OPEN.get())));

    public static final LazyOptional<WoodType> INNOCENT_WOOD = LazyOptional.of(() -> WoodType.register(new WoodType(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood").toString(), WizardsRebornBlockSetTypes.INNOCENT_WOOD.resolve().get(),
            WizardsRebornSounds.INNOCENT_WOOD, WizardsRebornSounds.INNOCENT_WOOD_HANGING_SIGN,
            WizardsRebornSounds.INNOCENT_WOOD_FENCE_GATE_CLOSE.get(), WizardsRebornSounds.INNOCENT_WOOD_FENCE_GATE_OPEN.get())));

    public static final LazyOptional<WoodType> CORK_BAMBOO = LazyOptional.of(() -> WoodType.register(new WoodType(new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo").toString(), WizardsRebornBlockSetTypes.CORK_BAMBOO.resolve().get(),
            WizardsRebornSounds.ARCANE_WOOD, WizardsRebornSounds.ARCANE_WOOD_HANGING_SIGN,
            WizardsRebornSounds.ARCANE_WOOD_FENCE_GATE_CLOSE.get(), WizardsRebornSounds.ARCANE_WOOD_FENCE_GATE_OPEN.get())));

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerWoodTypes(FMLClientSetupEvent event) {
            Sheets.addWoodType(WizardsRebornWoodTypes.ARCANE_WOOD.resolve().get());
            Sheets.addWoodType(WizardsRebornWoodTypes.INNOCENT_WOOD.resolve().get());
            Sheets.addWoodType(WizardsRebornWoodTypes.CORK_BAMBOO.resolve().get());
        }
    }
}
