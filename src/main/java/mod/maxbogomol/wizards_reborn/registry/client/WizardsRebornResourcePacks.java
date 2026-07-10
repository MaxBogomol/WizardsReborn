package mod.maxbogomol.wizards_reborn.registry.client;

import mod.maxbogomol.fluffy_fur.common.pack.PackHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class WizardsRebornResourcePacks {

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {

        @SubscribeEvent
        public static void addPackFinders(AddPackFindersEvent event) {
            if (event.getPackType() == PackType.CLIENT_RESOURCES) {
                addPack(event, "fusion");
                addPack(event, "athena");
            }
        }
    }

    public static void addPack(AddPackFindersEvent event, String name) {
        String id = WizardsReborn.MOD_ID + ":" + name;
        PackHandler.addPack(event, WizardsReborn.MOD_ID, id, Component.literal(id), "resourcepacks/" + name, PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.DEFAULT);
    }
}
