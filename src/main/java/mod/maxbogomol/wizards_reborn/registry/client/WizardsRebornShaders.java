package mod.maxbogomol.wizards_reborn.registry.client;

import mod.maxbogomol.fluffy_fur.client.shader.postprocess.PostProcessHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.shader.postprocess.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class WizardsRebornShaders {

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerShaders(FMLClientSetupEvent event) {
            PostProcessHandler.addInstance(KnowledgePostProcess.INSTANCE);
            PostProcessHandler.addInstance(MorSporesPostProcess.INSTANCE);
            PostProcessHandler.addInstance(WissenAuraPostProcess.INSTANCE);
            PostProcessHandler.addInstance(IrritationPostProcess.INSTANCE);
            PostProcessHandler.addInstance(LightGlowPostProcess.INSTANCE);
        }
    }
}
