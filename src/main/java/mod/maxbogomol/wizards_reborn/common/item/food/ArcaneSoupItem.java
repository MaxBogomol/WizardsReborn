package mod.maxbogomol.wizards_reborn.common.item.food;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ArcaneSoupItem extends ArcaneFoodItem {

    public ArcaneSoupItem(Properties properties) {
        super(properties);
    }

    public ArcaneSoupItem setNourishmentTick(int nourishmentTick) {
        super.setNourishmentTick(nourishmentTick);
        return this;
    }

    public ArcaneSoupItem setNourishmentLevel(int nourishmentLevel) {
        super.setNourishmentLevel(nourishmentLevel);
        return this;
    }

    public ArcaneSoupItem setComfortTick(int comfortTick) {
        super.setComfortTick(comfortTick);
        return this;
    }

    public ArcaneSoupItem setComfortLevel(int comfortLevel) {
        super.setComfortLevel(comfortLevel);
        return this;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        stack = super.finishUsingItem(stack, level, livingEntity);
        Player player = livingEntity instanceof Player ? (Player) livingEntity : null;
        if (player != null) {
            if (!player.getAbilities().instabuild) {
                if (stack.isEmpty()) {
                    return new ItemStack(Items.BOWL);
                }

                player.getInventory().add(new ItemStack(Items.BOWL));
            }
        }
        return stack;
    }
}
