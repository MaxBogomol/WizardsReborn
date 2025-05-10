package mod.maxbogomol.wizards_reborn.common.item.equipment.arcanewood;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneShovelItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class ArcaneWoodShovelItem extends ArcaneShovelItem {

    public final ArcaneWoodTools tools;

    public ArcaneWoodShovelItem(Tier tier, float attackDamageModifier, float attackSpeedModifier, Properties properties, Supplier<Item> repairItem) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.tools = getTools(repairItem);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentTypes.WOODEN);
    }

    public ArcaneWoodTools getTools(Supplier<Item> repairItem) {
        return new ArcaneWoodTools(repairItem);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        tools.inventoryTick(stack, level, entity, slot, isSelected);
        super.inventoryTick(stack, level, entity, slot, isSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> result = tools.use(level, player, hand);
        if (result.getResult() != InteractionResult.PASS) return result;
        return super.use(level, player, hand);
    }
}
