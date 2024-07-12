package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.ArrayList;
import java.util.List;

public class DrinkBottleItem extends Item {

    public DrinkBottleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        super.finishUsingItem(stack, level, livingEntity);

        if (!level.isClientSide()) {
            if (livingEntity instanceof Player player) {
                player.awardStat(Stats.ITEM_USED.get(this));

                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                if (stack.isEmpty()) {
                    return new ItemStack(WizardsReborn.ALCHEMY_BOTTLE.get());
                }
            }
        }

        livingEntity.gameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 40;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }

    public static int getStage(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("drinkStage")) {
            return nbt.getInt("drinkStage");
        }

        return 0;
    }

    public static void setStage(ItemStack stack, int stage) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putInt("drinkStage", stage);
    }

    public static List<ItemStack> getItemsAllStages(Item item) {
        List<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(item));
        for (int i = 0; i < 4; i++) {
            ItemStack stack = new ItemStack(item);
            setStage(stack, i + 1);
            list.add(stack);
        }

        return list;
    }

    public static List<ItemStack> getItemsAllStages(ItemStack item) {
        return getItemsAllStages(item.getItem());
    }

    public static List<ItemStack> getItemsForTab(Item item) {
        List<ItemStack> list = new ArrayList<>();

        list.add(new ItemStack(item));
        ItemStack stack = new ItemStack(item);
        setStage(stack, 3);
        list.add(stack);

        return list;
    }

    public static List<ItemStack> getItemsForTab(ItemStack item) {
        return getItemsForTab(item.getItem());
    }
}
