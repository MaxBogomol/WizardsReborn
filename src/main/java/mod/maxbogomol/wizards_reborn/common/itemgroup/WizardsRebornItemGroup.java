package mod.maxbogomol.wizards_reborn.common.itemgroup;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class WizardsRebornItemGroup {

    public static final ItemGroup WIZARDS_REBORN_GROUP = new ItemGroup("wizards_reborn_mod_tab") {
        @Override
        public ItemStack createIcon() {
            ItemStack icon = new ItemStack(WizardsReborn.FACETED_EARTH_CRYSTAL.get());
            return icon;
        }
    };
}