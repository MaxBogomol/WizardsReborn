package mod.maxbogomol.wizards_reborn.api.arcaneenchantment;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.*;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArcaneEnchantmentUtil {

    public static ArcaneEnchantment deserializeArcaneEnchantment(JsonObject json) {
        String enchantmentName = GsonHelper.getAsString(json, "arcane_enchantment");
        ArcaneEnchantment enchantment = ArcaneEnchantmentHandler.getArcaneEnchantment(enchantmentName);
        if (enchantment == null) {
            throw new JsonSyntaxException("Unknown arcane enchantment " + enchantmentName);
        }
        return enchantment;
    }

    public static ArcaneEnchantment arcaneEnchantmentFromNetwork(FriendlyByteBuf buffer) {
        return !buffer.readBoolean() ? null : ArcaneEnchantmentHandler.getArcaneEnchantment(buffer.readComponent().getString());
    }

    public static void arcaneEnchantmentToNetwork(ArcaneEnchantment enchantment, FriendlyByteBuf buffer) {
        if (enchantment == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeComponent(Component.literal(enchantment.getId()));
        }
    }

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

        for (ArcaneEnchantment arcaneEnchantment : ArcaneEnchantmentHandler.getArcaneEnchantments()) {
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

    public static void removeArcaneEnchantment(ItemStack stack, ArcaneEnchantment arcaneEnchantment) {
        existArcaneEnchantments(stack);
        CompoundTag nbt = stack.getTag();

        Map<ArcaneEnchantment, Integer> map = getAllArcaneEnchantments(stack);
        map.remove(arcaneEnchantment);
        nbt.put("arcaneEnchantments", new CompoundTag());

        for (ArcaneEnchantment enchantment : map.keySet()) {
            addArcaneEnchantment(stack, enchantment, map.get(enchantment));
        }
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
    public static List<Component> appendHoverText(ItemStack stack, Level level, TooltipFlag flags) {
        Map<ArcaneEnchantment, Integer> arcaneEnchantments = getAllArcaneEnchantments(stack);
        List<Component> list = new ArrayList<>();

        if (!arcaneEnchantments.isEmpty()) {
            list.add(Component.empty());
            list.add(Component.translatable("lore.wizards_reborn.arcane_enchantments").withStyle(ChatFormatting.GOLD));

            for (ArcaneEnchantment arcaneEnchantment : arcaneEnchantments.keySet()) {
                int enchantmentLevel = arcaneEnchantments.get(arcaneEnchantment);
                int maxEnchantmentLevel = arcaneEnchantment.getMaxLevel();

                if (enchantmentLevel > 0) {
                    Color color = arcaneEnchantment.getColor();

                    int R = (int) Mth.lerp(((float) enchantmentLevel / maxEnchantmentLevel), 100, color.getRed());
                    int G = (int) Mth.lerp(((float) enchantmentLevel / maxEnchantmentLevel), 100, color.getGreen());
                    int B = (int) Mth.lerp(((float) enchantmentLevel / maxEnchantmentLevel), 100, color.getBlue());

                    list.add(Component.literal(" ").append(arcaneEnchantment.getFullname(enchantmentLevel)).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, R, G, B)).withBold(arcaneEnchantment.isCurse())));
                }
            }
        }

        return list;
    }

    @OnlyIn(Dist.CLIENT)
    public static List<Component> modifiersAppendHoverText(ItemStack stack, Level level, TooltipFlag flags) {
        Map<ArcaneEnchantment, Integer> arcaneEnchantments = getAllArcaneEnchantments(stack);
        List<Component> list = new ArrayList<>();

        if (!arcaneEnchantments.isEmpty()) {
            for (ArcaneEnchantment arcaneEnchantment : arcaneEnchantments.keySet()) {
                List<Component> newList = arcaneEnchantment.modifierAppendHoverText(stack, level, flags);
                if (newList != null && !newList.isEmpty()) list.addAll(newList);
            }
        }

        return list;
    }

    public static void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        LifeMendingArcaneEnchantment.inventoryTick(stack, level, entity, slot, isSelected);
        WissenMendingArcaneEnchantment.inventoryTick(stack, level, entity, slot, isSelected);
        SonarArcaneEnchantment.inventoryTick(stack, level, entity, slot, isSelected);
    }

    public static int damageItem(ItemStack stack, int amount, LivingEntity entity) {
        amount = LifeMendingArcaneEnchantment.damageItem(stack, amount, entity);
        return WissenMendingArcaneEnchantment.damageItem(stack, amount, entity);
    }

    public static void onLivingDamage(LivingDamageEvent event) {
        WissenChargeArcaneEnchantment.onLivingDamage(event);
    }

    public static void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        MagicBladeArcaneEnchantment.hurtEnemy(stack, target, attacker);
    }

    public static void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        WissenChargeArcaneEnchantment.onUseTick(level, livingEntity, stack, remainingUseDuration);
        ThrowArcaneEnchantment.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }
}
