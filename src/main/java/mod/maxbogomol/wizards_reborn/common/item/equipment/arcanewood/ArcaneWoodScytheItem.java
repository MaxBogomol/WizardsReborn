package mod.maxbogomol.wizards_reborn.common.item.equipment.arcanewood;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneScytheItem;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class ArcaneWoodScytheItem  extends ArcaneScytheItem {
    @Deprecated
    public final Item repairItem;

    public ArcaneWoodScytheItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties, float distance, int radius, Item repairItem) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties, distance, radius);
        this.repairItem = repairItem;
    }

    public int repairTick(ItemStack stack, Level world, Entity entity) {
        return 800;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide) {
            if (entity.tickCount % repairTick(stack, world, entity) == 0 && stack.getDamageValue() > 0) {
                stack.setDamageValue(stack.getDamageValue() - 1);
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
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), WizardsReborn.ARCANE_WOOD_PLACE_SOUND.get(), SoundSource.PLAYERS, 1.0f, 1.5f);
                    return InteractionResultHolder.success(stack);
                }
            }
        }

        return InteractionResultHolder.pass(stack);
    }
}
