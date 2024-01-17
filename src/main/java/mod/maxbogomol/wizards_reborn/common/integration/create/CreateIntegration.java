package mod.maxbogomol.wizards_reborn.common.integration.create;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class CreateIntegration {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WizardsReborn.MOD_ID);

    public static void init(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    public static boolean isCreateLoaded() {
        return ModList.get().isLoaded("create");
    }
}
