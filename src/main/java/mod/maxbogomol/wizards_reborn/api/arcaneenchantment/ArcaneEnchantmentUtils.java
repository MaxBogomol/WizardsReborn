package mod.maxbogomol.wizards_reborn.api.arcaneenchantment;

import com.google.common.collect.Maps;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.WissenMendingArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArcaneEnchantmentUtils {
    public static int getArcaneEnchantment(ItemStack stack, ArcaneEnchantment arcaneEnchantment) {
        existArcaneEnchantments(stack);
        CompoundTag nbt = stack.getTag();
        CompoundTag nbtEnchantments = nbt.getCompound("arcaneEnchantments");
        if (nbtEnchantments.contains(arcaneEnchantment.getId())) {
            return nbtEnchantments.getInt(arcaneEnchantment.getId());
        }

        return 0;
    }

    public static Map<ArcaneEnchantment, Integer> getAllArcaneEnchantments(ItemStack stack) {
        Map<ArcaneEnchantment, Integer> map = Maps.newLinkedHashMap();

        existArcaneEnchantments(stack);
        CompoundTag nbt = stack.getTag();
        CompoundTag nbtEnchantments = nbt.getCompound("arcaneEnchantments");

        for (ArcaneEnchantment arcaneEnchantment : ArcaneEnchantments.getArcaneEnchantments()) {
            if (nbtEnchantments.contains(arcaneEnchantment.getId())) {
                map.put(arcaneEnchantment, nbtEnchantments.getInt(arcaneEnchantment.getId()));
            }
        }

        return map;
    }

    public static void addArcaneEnchantment(ItemStack stack, ArcaneEnchantment arcaneEnchantment) {
        existArcaneEnchantments(stack);

        if (getArcaneEnchantment(stack, arcaneEnchantment) == 0) {
            addArcaneEnchantment(stack, arcaneEnchantment, 1);
        }
    }

    public static void addArcaneEnchantment(ItemStack stack, ArcaneEnchantment arcaneEnchantment, int enchantmentLevel) {
        existArcaneEnchantments(stack);
        CompoundTag nbt = stack.getTag();
        CompoundTag nbtEnchantments = nbt.getCompound("arcaneEnchantments");

        nbtEnchantments.putInt(arcaneEnchantment.getId(), enchantmentLevel);
    }

    public static void addItemArcaneEnchantment(ItemStack stack, ArcaneEnchantment arcaneEnchantment) {
        existArcaneEnchantments(stack);

        int enchantmentLevel = getArcaneEnchantment(stack, arcaneEnchantment);

        if (enchantmentLevel + 1 <= arcaneEnchantment.getMaxLevel()) {
            addArcaneEnchantment(stack, arcaneEnchantment, enchantmentLevel + 1);
        }
    }

    public static boolean canAddArcaneEnchantment(ItemStack stack, ArcaneEnchantment arcaneEnchantment, int enchantmentLevel) {
        if (checkCompatibility(stack, arcaneEnchantment)) {
            existArcaneEnchantments(stack);

            return enchantmentLevel <= arcaneEnchantment.getMaxLevel();
        }

        return false;
    }

    public static boolean canAddItemArcaneEnchantment(ItemStack stack, ArcaneEnchantment arcaneEnchantment) {
        if (checkCompatibility(stack, arcaneEnchantment)) {
            existArcaneEnchantments(stack);

            int enchantmentLevel = getArcaneEnchantment(stack, arcaneEnchantment);

            return enchantmentLevel + 1 <= arcaneEnchantment.getMaxLevel();
        }

        return false;
    }

    public static void existArcaneEnchantments(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("arcaneEnchantments")) {
            CompoundTag nbtEnchantments = new CompoundTag();
            nbt.put("arcaneEnchantments", nbtEnchantments);
        }
    }

    public static boolean isArcaneItem(ItemStack stack) {
        return stack.getItem() instanceof IArcaneItem;
    }

    public static boolean checkCompatibility(ItemStack stack, ArcaneEnchantment arcaneEnchantment) {
        if (isArcaneItem(stack) && arcaneEnchantment.canEnchantItem(stack)) {
            existArcaneEnchantments(stack);
            Map<ArcaneEnchantment, Integer> arcaneEnchantments = getAllArcaneEnchantments(stack);

            for (ArcaneEnchantment enchantment : arcaneEnchantments.keySet()) {
                if (!enchantment.checkCompatibility(arcaneEnchantment)) {
                    break;
                }
            }

            return true;
        }

        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public static List<Component> appendHoverText(ItemStack stack, Level world, TooltipFlag flags) {
        Map<ArcaneEnchantment, Integer> arcaneEnchantments = getAllArcaneEnchantments(stack);
        List<Component> list = new ArrayList<>();

        if (!arcaneEnchantments.isEmpty()) {
            list.add(Component.empty());
            list.add(Component.translatable("lore.wizards_reborn.arcane_enchantments").withStyle(ChatFormatting.GOLD));

            for (ArcaneEnchantment arcaneEnchantment : arcaneEnchantments.keySet()) {
                int enchantmentLevel = arcaneEnchantments.get(arcaneEnchantment);
                int maxEnchantmentLevel = arcaneEnchantment.getMaxLevel();
                Color color = arcaneEnchantment.getColor();

                int R = (int) Mth.lerp(((float) enchantmentLevel / maxEnchantmentLevel), 100, color.getRed());
                int G = (int) Mth.lerp(((float) enchantmentLevel / maxEnchantmentLevel), 100, color.getGreen());
                int B = (int) Mth.lerp(((float) enchantmentLevel / maxEnchantmentLevel), 100, color.getBlue());

                list.add(Component.literal(" ").append(arcaneEnchantment.getFullname(enchantmentLevel)).withStyle(Style.EMPTY.withColor(ColorUtils.packColor(255, R, G, B))));
            }
        }

        return list;
    }

    public static void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        WissenMendingArcaneEnchantment.inventoryTick(stack, world, entity, slot, isSelected);
    }

    public static int damageItem(ItemStack stack, int amount, LivingEntity entity) {
        return WissenMendingArcaneEnchantment.damageItem(stack, amount, entity);
    }
}
