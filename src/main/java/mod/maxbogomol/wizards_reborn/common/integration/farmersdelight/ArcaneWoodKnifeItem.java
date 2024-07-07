package mod.maxbogomol.wizards_reborn.common.integration.farmersdelight;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class ArcaneWoodKnifeItem extends ArcaneKnifeItem {
    @Deprecated
    public final Item repairItem;

    public ArcaneWoodKnifeItem(Tier tier, float attackDamageModifier, float attackSpeedModifier, Properties properties, Item repairItem) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.repairItem = repairItem;
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.WOODEN);
    }

    public int repairTick(ItemStack stack, Level world, Entity entity) {
        return 800;
    }

    public int getRepairTick(ItemStack stack, Level world, Entity entity) {
        int tick = repairTick(stack, world, entity) - (150 * getLifeRoots(stack));
        if (tick < 50) tick = 50;
        return tick;
    }

    public int getLifeRoots(ItemStack stack) {
        return ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.LIFE_ROOTS_ARCANE_ENCHANTMENT);
    }

    public SoundEvent getRepairSound(ItemStack stack, Level world, Entity entity) {
        return WizardsReborn.ARCANE_WOOD_PLACE_SOUND.get();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide) {
            if (entity.tickCount % getRepairTick(stack, world, entity) == 0 && stack.getDamageValue() > 0) {
                stack.setDamageValue(stack.getDamageValue() - 1);
                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), getRepairSound(stack, world, entity), SoundSource.PLAYERS, 0.05f, 2f);
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            if (stack.getDamageValue() > 0) {
                ItemStack offStack = player.getItemInHand(InteractionHand.MAIN_HAND);
                if (hand == InteractionHand.MAIN_HAND) {
                    offStack = player.getItemInHand(InteractionHand.OFF_HAND);
                }

                if (offStack.getItem().equals(repairItem)) {
                    offStack.setCount(offStack.getCount() - 1);
                    stack.setDamageValue(0);
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), getRepairSound(stack, world, player), SoundSource.PLAYERS, 1.0f, 1.5f);
                    return InteractionResultHolder.success(stack);
                }
            }
        }

        return InteractionResultHolder.pass(stack);
    }
}
