package mod.maxbogomol.wizards_reborn.common.item.equipment.arcanewood;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class ArcaneWoodTools {

    public final Supplier<Item> repairItem;

    public ArcaneWoodTools(Supplier<Item> repairItem) {
        this.repairItem = repairItem;
    }

    public int repairTick(ItemStack stack, Level level, Entity entity) {
        return 800;
    }

    public int getRepairTick(ItemStack stack, Level level, Entity entity) {
        int tick = repairTick(stack, level, entity) - (150 * getLifeRoots(stack));
        if (tick < 50) tick = 50;
        return tick;
    }

    public static int getLifeRoots(ItemStack stack) {
        return ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsRebornArcaneEnchantments.LIFE_ROOTS);
    }

    public SoundEvent getRepairSound(ItemStack stack, Level level, Entity entity) {
        return WizardsRebornSounds.ARCANE_WOOD_PLACE.get();
    }

    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        if (!level.isClientSide()) {
            if (entity.tickCount % getRepairTick(stack, level, entity) == 0 && stack.getDamageValue() > 0) {
                stack.setDamageValue(stack.getDamageValue() - 1);
                level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), getRepairSound(stack, level, entity), SoundSource.PLAYERS, 0.05f, 2f);
            }
        }
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getDamageValue() > 0) {
            ItemStack offStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (hand == InteractionHand.MAIN_HAND) {
                offStack = player.getItemInHand(InteractionHand.OFF_HAND);
            }

            if (offStack.getItem().equals(repairItem.get())) {
                if (!player.isCreative()) offStack.shrink(1);
                stack.setDamageValue(0);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), getRepairSound(stack, level, player), SoundSource.PLAYERS, 1.0f, 1.5f);
                return InteractionResultHolder.success(stack);
            }
        }

        return InteractionResultHolder.pass(stack);
    }
}
