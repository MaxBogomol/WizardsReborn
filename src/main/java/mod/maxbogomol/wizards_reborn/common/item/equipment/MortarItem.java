package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.recipe.MortarRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MortarItem extends Item {

    public static List<Item> mortarList = new ArrayList<>();

    public MortarItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            ItemStack offStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (hand == InteractionHand.MAIN_HAND) {
                offStack = player.getItemInHand(InteractionHand.OFF_HAND);
            }

            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, offStack);
            Optional<MortarRecipe> recipe = world.getRecipeManager()
                    .getRecipeFor(WizardsReborn.MORTAR_RECIPE.get(), inv, world);

            if (recipe.isPresent()) {
                if (recipe.get().getResultItem(RegistryAccess.EMPTY) != null) {
                    if (!player.isCreative()) {
                        offStack.setCount(offStack.getCount() - 1);
                    }

                    if (player.getInventory().getSlotWithRemainingSpace(recipe.get().getResultItem(RegistryAccess.EMPTY).copy()) != -1 || player.getInventory().getFreeSlot() > -1) {
                        player.getInventory().add(recipe.get().getResultItem(RegistryAccess.EMPTY).copy());
                    } else {
                        world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY() + 0.5F, player.getZ(), recipe.get().getResultItem(RegistryAccess.EMPTY).copy()));
                    }
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BONE_MEAL_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
                    player.awardStat(Stats.ITEM_USED.get(this));

                    return InteractionResultHolder.success(stack);
                }
            }
        }

        return InteractionResultHolder.pass(stack);
    }
}