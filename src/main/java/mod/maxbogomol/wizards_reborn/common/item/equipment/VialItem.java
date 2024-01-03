package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.world.item.ItemStack;

public class VialItem extends AlchemyBottleItem {

    public VialItem(Properties properties, int maxUses) {
        super(properties, maxUses);
    }

    @Override
    public ItemStack getPotionItem() {
        return new ItemStack(WizardsReborn.ALCHEMY_VIAL_POTION.get());
    }
}