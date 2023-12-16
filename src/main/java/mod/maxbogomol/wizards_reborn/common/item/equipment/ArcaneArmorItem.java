package mod.maxbogomol.wizards_reborn.common.item.equipment;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.UUID;

public class ArcaneArmorItem extends ArmorItem {
    public static final UUID BASE_WISSEN_SALE_UUID = UUID.fromString("0D0AA300-8D31-11EE-B9D1-0242AC120002");
    public static final UUID CHEST_WISSEN_SALE_UUID = UUID.fromString("78537078-9ABB-11EE-b9D1-0242AC120002");
    public static final UUID HEAD_WISSEN_SALE_UUID = UUID.fromString("7BEC8BFC-9ABB-11EE-B9D1-0242AC120002");
    public static final UUID LEGS_WISSEN_SALE_UUID = UUID.fromString("7E429B80-9ABB-11EE-B9D1-0242AC120002");
    public static final UUID FEET_WISSEN_SALE_UUID = UUID.fromString("834D5B42-9ABB-11EE-B9D1-0242AC120002");

    public static final UUID BASE_MAGIC_ARMOR_UUID = UUID.fromString("3001CCC8-9C1B-11EE-8C90-0242AC120002");
    public static final UUID CHEST_MAGIC_ARMOR_UUID = UUID.fromString("3295267E-9C1B-11EE-8C90-0242AC120002");
    public static final UUID HEAD_MAGIC_ARMOR_UUID = UUID.fromString("38000FE8-9C1B-11EE-8C90-0242AC120002");
    public static final UUID LEGS_MAGIC_ARMOR_UUID = UUID.fromString("3A052206-9C1B-11EE-8C90-0242AC120002");
    public static final UUID FEET_MAGIC_ARMOR_UUID = UUID.fromString("3CC51e4C-9C1B-11EE-8C90-0242AC120002");

    public ArcaneArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    public int getWissenSaleForSlot(EquipmentSlot slot) {
        return 0;
    }

    public static UUID getWissenSaleUUIDForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> HEAD_WISSEN_SALE_UUID;
            case CHEST -> CHEST_WISSEN_SALE_UUID;
            case LEGS -> LEGS_WISSEN_SALE_UUID;
            case FEET -> FEET_WISSEN_SALE_UUID;
            default -> BASE_WISSEN_SALE_UUID;
        };
    }

    public int getMagicArmorForSlot(EquipmentSlot slot) {
        return 0;
    }

    public static UUID getMagicArmorUUIDForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> HEAD_MAGIC_ARMOR_UUID;
            case CHEST -> CHEST_MAGIC_ARMOR_UUID;
            case LEGS -> LEGS_MAGIC_ARMOR_UUID;
            case FEET -> FEET_MAGIC_ARMOR_UUID;
            default -> BASE_MAGIC_ARMOR_UUID;
        };
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        if (hasArmorSet()) {
            Player player = Minecraft.getInstance().player;
            list.add(Component.empty());
            list.add(Component.literal("[").append(getArmorSetName()).append(Component.literal("]")).withStyle(hasArmorSetPlayer(player) ? getArmorSetColor() : ChatFormatting.GRAY));

            if (Screen.hasShiftDown()) {
                list.add(getArmorSetItemComponent(EquipmentSlot.HEAD, player));
                list.add(getArmorSetItemComponent(EquipmentSlot.CHEST, player));
                list.add(getArmorSetItemComponent(EquipmentSlot.LEGS, player));
                list.add(getArmorSetItemComponent(EquipmentSlot.FEET, player));
            }
        }
    }

    public boolean hasArmorSet() {
        return false;
    }

    public MutableComponent getArmorSetName() {
        return Component.empty();
    }

    public ItemStack getArmorSetItem(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    public ChatFormatting getArmorSetColor() {
        return ChatFormatting.GREEN;
    }

    public boolean hasArmorSetItem(EquipmentSlot slot, Player player) {
        return player.getItemBySlot(slot).getItem() == getArmorSetItem(slot).getItem();
    }

    public MutableComponent getArmorSetItemComponent(EquipmentSlot slot, Player player) {
        return Component.literal(" ").append(Component.translatable(getArmorSetItem(slot).getDescriptionId()).withStyle(Style.EMPTY.withColor(hasArmorSetItem(slot, player) ? getArmorSetColor() : ChatFormatting.GRAY)));
    }

    public boolean hasArmorSetPlayer(Player player) {
        return hasArmorSetItem(EquipmentSlot.HEAD, player) && hasArmorSetItem(EquipmentSlot.CHEST, player) && hasArmorSetItem(EquipmentSlot.LEGS, player) && hasArmorSetItem(EquipmentSlot.FEET, player);
    }
}
