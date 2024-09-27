package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.fluffy_fur.common.block.entity.BlockSimpleInventory;
import mod.maxbogomol.fluffy_fur.common.item.FuelItem;
import mod.maxbogomol.wizards_reborn.common.recipe.MortarRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MortarItem extends FuelItem {

    public static List<Item> mortarList = new ArrayList<>();

    public MortarItem(Properties properties, int fuel) {
        super(properties, fuel);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            ItemStack offStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (hand == InteractionHand.MAIN_HAND) {
                offStack = player.getItemInHand(InteractionHand.OFF_HAND);
            }

            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, offStack);
            Optional<MortarRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.MORTAR.get(), inv, level);

            if (recipe.isPresent()) {
                if (recipe.get().getResultItem(RegistryAccess.EMPTY) != null) {
                    if (!player.isCreative()) {
                        offStack.shrink(1);
                    }
                    BlockSimpleInventory.addPlayerItem(level, player, recipe.get().getResultItem(RegistryAccess.EMPTY).copy());
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BONE_MEAL_USE, SoundSource.PLAYERS, 1.0f, 1.0f);
                    player.awardStat(Stats.ITEM_USED.get(this));

                    return InteractionResultHolder.success(stack);
                }
            }
        }

        return InteractionResultHolder.pass(stack);
    }
}