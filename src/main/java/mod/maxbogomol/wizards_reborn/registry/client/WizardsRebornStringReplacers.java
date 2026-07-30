package mod.maxbogomol.wizards_reborn.registry.client;

import mod.maxbogomol.fluffy_fur.client.string.KeyMappingStringReplacerInstance;
import mod.maxbogomol.fluffy_fur.client.string.StringReplacerHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class WizardsRebornStringReplacers {
    public static KeyMappingStringReplacerInstance SELECTION_MENU = new KeyMappingStringReplacerInstance(WizardsReborn.MOD_ID+":selectionMenu", WizardsRebornKeyMappings.SELECTION_MENU);
    public static KeyMappingStringReplacerInstance BAG_MENU = new KeyMappingStringReplacerInstance(WizardsReborn.MOD_ID+":bagMenu", WizardsRebornKeyMappings.BAG_MENU);
    public static KeyMappingStringReplacerInstance NEXT_SPELL = new KeyMappingStringReplacerInstance(WizardsReborn.MOD_ID+":nextSpell", WizardsRebornKeyMappings.NEXT_SPELL);
    public static KeyMappingStringReplacerInstance PREVIOUS_SPELL = new KeyMappingStringReplacerInstance(WizardsReborn.MOD_ID+":previousSpell", WizardsRebornKeyMappings.PREVIOUS_SPELL);
    public static KeyMappingStringReplacerInstance SPELL_SETS_TOGGLE = new KeyMappingStringReplacerInstance(WizardsReborn.MOD_ID+":spellSetsToggle", WizardsRebornKeyMappings.SPELL_SETS_TOGGLE);

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerStringReplacers(FMLClientSetupEvent event) {
            StringReplacerHandler.register(SELECTION_MENU);
            StringReplacerHandler.register(BAG_MENU);
            StringReplacerHandler.register(NEXT_SPELL);
            StringReplacerHandler.register(PREVIOUS_SPELL);
            StringReplacerHandler.register(SPELL_SETS_TOGGLE);
        }
    }
}
