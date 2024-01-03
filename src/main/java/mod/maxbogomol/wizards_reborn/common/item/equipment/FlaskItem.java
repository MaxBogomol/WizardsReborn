package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.world.item.ItemStack;

public class FlaskItem extends AlchemyBottleItem {

    public FlaskItem(Properties properties, int maxUses) {
        super(properties, maxUses);
    }

    @Override
    public ItemStack getPotionItem() {
        return new ItemStack(WizardsReborn.ALCHEMY_FLASK_POTION.get());
    }
}