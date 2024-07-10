package mod.maxbogomol.wizards_reborn.common.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public class FuelBlockItem extends BlockItem {
    public int fuel;

    public FuelBlockItem(Block blockIn, Properties properties, int fuel) {
        super(blockIn, properties);
        this.fuel = fuel;
    }

    @Override
    public int getBurnTime(ItemStack stack, @Nullable RecipeType<?> recipeType) {
        return fuel;
    }
}
