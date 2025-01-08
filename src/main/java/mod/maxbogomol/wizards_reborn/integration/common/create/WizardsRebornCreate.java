package mod.maxbogomol.wizards_reborn.integration.common.create;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornCreate {
    public static boolean LOADED;

    public static class ItemsLoadedOnly {
        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WizardsReborn.MOD_ID);

        public static final RegistryObject<Item> CRUSHED_RAW_ARCANE_GOLD = ITEMS.register("crushed_raw_arcane_gold", () -> new Item(new Item.Properties()));
        public static final RegistryObject<Item> ARCANE_GOLD_SHEET = ITEMS.register("arcane_gold_sheet", () -> new Item(new Item.Properties()));

        public static final RegistryObject<Item> SARCON_SHEET = ITEMS.register("sarcon_sheet", () -> new Item(new Item.Properties()));

        public static final RegistryObject<Item> VILENIUM_SHEET = ITEMS.register("vilenium_sheet", () -> new Item(new Item.Properties()));
    }

    public static void init(IEventBus eventBus) {
        LOADED = ModList.get().isLoaded("create");

        if (isLoaded()) {
            ItemsLoadedOnly.ITEMS.register(eventBus);
        }
    }

    public static boolean isLoaded() {
        return LOADED;
    }
}
