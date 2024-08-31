package mod.maxbogomol.wizards_reborn.common.item.equipment.arcane;

import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.fluffy_fur.common.item.ICustomAnimationItem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.ThrowArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ScytheItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ArcaneScytheItem extends ScytheItem implements IArcaneItem, ICustomAnimationItem {
    public List<ArcaneEnchantmentType> arcaneEnchantmentTypes = new ArrayList<>();

    public ArcaneScytheItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties, float distance, int radius) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties, distance, radius);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.BREAKABLE);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.SCYTHE);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.WEAPON);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.MELEE_WEAPON);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.TOOL);
    }

    @Override
    public List<ArcaneEnchantmentType> getArcaneEnchantmentTypes() {
        return arcaneEnchantmentTypes;
    }

    public ArcaneScytheItem addArcaneEnchantmentType(ArcaneEnchantmentType type) {
        arcaneEnchantmentTypes.add(type);
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        Skin skin = Skin.getSkinFromItem(stack);
        if (skin != null) list.add(skin.getSkinComponent());
        list.addAll(ArcaneEnchantmentUtil.appendHoverText(stack, world, flags));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        ArcaneEnchantmentUtil.inventoryTick(stack, world, entity, slot, isSelected);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ArcaneEnchantmentUtil.damageItem(stack, amount, entity);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        ArcaneEnchantmentUtil.hurtEnemy(stack, target, attacker);
        return super.hurtEnemy(stack, target, attacker);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsReborn.THROW_ARCANE_ENCHANTMENT) > 0) {
            float costModifier = WissenUtils.getWissenCostModifierWithDiscount(player);
            List<ItemStack> items = WissenUtils.getWissenItemsNoneAndStorage(WissenUtils.getWissenItemsCurios(player));
            int wissen = WissenUtils.getWissenInItems(items);
            int cost = (int) (150 * (1 - costModifier));

            if (WissenUtils.canRemoveWissen(wissen, cost)) {
                player.startUsingItem(hand);
                return InteractionResultHolder.pass(stack);
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        ArcaneEnchantmentUtil.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public ItemAnimation getAnimation(ItemStack stack) {
        if (ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsReborn.THROW_ARCANE_ENCHANTMENT) > 0) {
            return ThrowArcaneEnchantment.animation;
        }
        return null;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        if (ArcaneEnchantmentUtil.getArcaneEnchantment(stack, WizardsReborn.THROW_ARCANE_ENCHANTMENT) > 0) {
            return UseAnim.CUSTOM;
        }
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }
}
