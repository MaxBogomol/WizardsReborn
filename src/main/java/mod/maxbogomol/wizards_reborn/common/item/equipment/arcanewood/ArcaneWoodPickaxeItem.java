package mod.maxbogomol.wizards_reborn.common.item.equipment.arcanewood;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcanePickaxeItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class ArcaneWoodPickaxeItem extends ArcanePickaxeItem {

    public final ArcaneWoodTools tools;

    public ArcaneWoodPickaxeItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties, Item repairItem) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.tools = getTools(repairItem);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentTypes.WOODEN);
    }

    public ArcaneWoodTools getTools(Item repairItem) {
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
