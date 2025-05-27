package mod.maxbogomol.wizards_reborn.common.item.equipment.arcane;

import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ArcaneCrossbowItem extends CrossbowItem implements IArcaneItem {
    public List<ArcaneEnchantmentType> arcaneEnchantmentTypes = new ArrayList<>();
    public Supplier<Ingredient> repairMaterial;

    public ArcaneCrossbowItem(Properties properties) {
        super(properties);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentTypes.BREAKABLE);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentTypes.CROSSBOW);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentTypes.WEAPON);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentTypes.RANGED_WEAPON);
    }

    @Override
    public List<ArcaneEnchantmentType> getArcaneEnchantmentTypes() {
        return arcaneEnchantmentTypes;
    }

    public ArcaneCrossbowItem addArcaneEnchantmentType(ArcaneEnchantmentType type) {
        arcaneEnchantmentTypes.add(type);
        return this;
    }

    public ArcaneCrossbowItem setRepairMaterial(Supplier<Ingredient> repairMaterial) {
        this.repairMaterial = repairMaterial;
        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if (skin != null) list.add(skin.getSkinComponent());

        List<Component> crossbowList = new ArrayList<>();
        super.appendHoverText(stack, level, crossbowList, flags);
        if (crossbowList.size() > 0) {
            list.add(crossbowList.get(0));
            if (crossbowList.size() > 1) {
                if (Screen.hasShiftDown()) {
                    crossbowList.remove(0);
                    list.addAll(crossbowList);
                } else {
                    list.add(Component.empty().append(CommonComponents.SPACE).append(Component.literal("[...]").withStyle(ChatFormatting.GRAY)));
                }
            }
        }

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

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
        ArcaneEnchantmentUtil.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        if (repairMaterial != null) {
            return repairMaterial.get().test(repair);
        }
        return super.isValidRepairItem(toRepair, repair);
    }
}
