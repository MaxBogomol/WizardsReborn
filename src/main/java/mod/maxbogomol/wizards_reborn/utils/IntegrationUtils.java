package mod.maxbogomol.wizards_reborn.utils;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class IntegrationUtils {
    public static Item getItem(String modId, String id) {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(modId, id));
    }
}
