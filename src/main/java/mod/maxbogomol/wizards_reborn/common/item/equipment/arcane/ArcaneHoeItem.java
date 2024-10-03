package mod.maxbogomol.wizards_reborn.common.item.equipment.arcane;

import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ArcaneHoeItem extends HoeItem implements IArcaneItem {
    public List<ArcaneEnchantmentType> arcaneEnchantmentTypes = new ArrayList<>();

    public ArcaneHoeItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.BREAKABLE);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.HOE);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.TOOL);
    }

    @Override
    public List<ArcaneEnchantmentType> getArcaneEnchantmentTypes() {
        return arcaneEnchantmentTypes;
    }

    public ArcaneHoeItem addArcaneEnchantmentType(ArcaneEnchantmentType type) {
        arcaneEnchantmentTypes.add(type);
        return this;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if (skin != null) list.add(skin.getSkinComponent());
        list.addAll(ArcaneEnchantmentUtil.appendHoverText(stack, level, flags));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        ArcaneEnchantmentUtil.inventoryTick(stack, level, entity, slot, isSelected);
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
}
