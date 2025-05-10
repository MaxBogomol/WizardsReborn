package mod.maxbogomol.wizards_reborn.common.item.equipment.innocentwood;

import mod.maxbogomol.wizards_reborn.common.item.equipment.arcanewood.ArcaneWoodSwordItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcanewood.ArcaneWoodTools;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import java.util.function.Supplier;

public class InnocentWoodSwordItem extends ArcaneWoodSwordItem {

    public InnocentWoodSwordItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties, Supplier<Item> repairItem) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties, repairItem);
    }

    @Override
    public ArcaneWoodTools getTools(Supplier<Item> repairItem) {
        return new InnocentWoodTools(repairItem);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (!slotChanged) {
            return false;
        }
        return super.shouldCauseReequipAnimation(oldStack, newStack, true);
    }
}
