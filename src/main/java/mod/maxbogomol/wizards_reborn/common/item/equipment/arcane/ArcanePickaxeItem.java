package mod.maxbogomol.wizards_reborn.common.item.equipment.arcane;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.SonarArcaneEnchantment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ArcanePickaxeItem extends PickaxeItem implements IArcaneItem {
    public List<ArcaneEnchantmentType> arcaneEnchantmentTypes = new ArrayList<>();

    public ArcanePickaxeItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.BREAKABLE);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.PICKAXE);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.TOOL);
    }

    @Override
    public List<ArcaneEnchantmentType> getArcaneEnchantmentTypes() {
        return arcaneEnchantmentTypes;
    }

    public ArcanePickaxeItem addArcaneEnchantmentType(ArcaneEnchantmentType type) {
        arcaneEnchantmentTypes.add(type);
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        Skin skin = Skin.getSkinFromItem(stack);
        if (skin != null) list.add(skin.getSkinComponent());
        list.addAll(ArcaneEnchantmentUtils.appendHoverText(stack, world, flags));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        int enchantmentLevel = ArcaneEnchantmentUtils.getArcaneEnchantment(stack, WizardsReborn.SONAR_ARCANE_ENCHANTMENT);
        if (enchantmentLevel > 0) {
            float costModifier = WissenUtils.getWissenCostModifierWithDiscount(player);
            List<ItemStack> items = WissenUtils.getWissenItemsNoneAndStorage(WissenUtils.getWissenItemsCurios(player));
            int wissen = WissenUtils.getWissenInItems(items);
            int cost = (int) (60 * (1 - costModifier));

            if (WissenUtils.canRemoveWissen(wissen, cost)) {
                if (SonarArcaneEnchantment.getCooldown(stack) <= 0 && SonarArcaneEnchantment.getOres(stack).isEmpty()) {
                    SonarArcaneEnchantment.writeOres(player, stack, enchantmentLevel);
                    WissenUtils.removeWissenFromWissenItems(items, cost);
                    return InteractionResultHolder.success(stack);
                }
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        ArcaneEnchantmentUtils.inventoryTick(stack, world, entity, slot, isSelected);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ArcaneEnchantmentUtils.damageItem(stack, amount, entity);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ArcaneEnchantmentUtils.hurtEnemy(stack, target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }
}
