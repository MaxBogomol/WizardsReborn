package mod.maxbogomol.wizards_reborn.common.item.equipment.arcane;

import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class ArcaneArmorItem extends ArmorItem implements IArcaneItem {
    public static final UUID BASE_WISSEN_DISCOUNT_UUID = UUID.fromString("0D0AA300-8D31-11EE-B9D1-0242AC120002");
    public static final UUID CHEST_WISSEN_DISCOUNT_UUID = UUID.fromString("78537078-9ABB-11EE-b9D1-0242AC120002");
    public static final UUID HEAD_WISSEN_DISCOUNT_UUID = UUID.fromString("7BEC8BFC-9ABB-11EE-B9D1-0242AC120002");
    public static final UUID LEGS_WISSEN_DISCOUNT_UUID = UUID.fromString("7E429B80-9ABB-11EE-B9D1-0242AC120002");
    public static final UUID FEET_WISSEN_DISCOUNT_UUID = UUID.fromString("834D5B42-9ABB-11EE-B9D1-0242AC120002");

    public static final UUID BASE_MAGIC_ARMOR_UUID = UUID.fromString("3001CCC8-9C1B-11EE-8C90-0242AC120002");
    public static final UUID CHEST_MAGIC_ARMOR_UUID = UUID.fromString("3295267E-9C1B-11EE-8C90-0242AC120002");
    public static final UUID HEAD_MAGIC_ARMOR_UUID = UUID.fromString("38000FE8-9C1B-11EE-8C90-0242AC120002");
    public static final UUID LEGS_MAGIC_ARMOR_UUID = UUID.fromString("3A052206-9C1B-11EE-8C90-0242AC120002");
    public static final UUID FEET_MAGIC_ARMOR_UUID = UUID.fromString("3CC51e4C-9C1B-11EE-8C90-0242AC120002");

    public List<ArcaneEnchantmentType> arcaneEnchantmentTypes = new ArrayList<>();

    public ArcaneArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentTypes.BREAKABLE);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentTypes.ARMOR);
    }

    @Override
    public List<ArcaneEnchantmentType> getArcaneEnchantmentTypes() {
        return arcaneEnchantmentTypes;
    }

    public ArcaneArmorItem addArcaneEnchantmentType(ArcaneEnchantmentType type) {
        arcaneEnchantmentTypes.add(type);
        return this;
    }

    public int getWissenDiscountForSlot(EquipmentSlot slot) {
        return 0;
    }

    public static UUID getWissenDiscountUUIDForSlot(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> HEAD_WISSEN_DISCOUNT_UUID;
            case CHEST -> CHEST_WISSEN_DISCOUNT_UUID;
            case LEGS -> LEGS_WISSEN_DISCOUNT_UUID;
            case FEET -> FEET_WISSEN_DISCOUNT_UUID;
            default -> BASE_WISSEN_DISCOUNT_UUID;
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

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if (skin != null) list.add(skin.getSkinComponent());

        if (hasArmorSet()) {
            Player player = WizardsReborn.proxy.getPlayer();
            if (player != null) {
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

        list.addAll(ArcaneEnchantmentUtil.appendHoverText(stack, level, flags));
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

    public float getMagicModifier() {
        return 0f;
    }

    public static float getPlayerMagicModifier(Entity entity) {
        if (entity instanceof Player player) {
            AttributeInstance attribute = player.getAttribute(WizardsRebornAttributes.MAGIC_MODIFIER.get());
            if (attribute != null) {
                if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArcaneArmorItem armor) {
                    if (armor.hasArmorSet()) {
                        if (armor.hasArmorSetPlayer(player)) {
                            return (float) (armor.getMagicModifier() + attribute.getValue());
                        }
                    }
                }
                return (float) attribute.getValue();
            }
        }

        return 0f;
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

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        ArcaneEnchantmentUtil.inventoryTick(stack, level, entity, slot, isSelected);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return ArcaneEnchantmentUtil.damageItem(stack, amount, entity);
    }

    public boolean hasCustomModel() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isDefaultModel(Entity entity) {
        if (entity instanceof AbstractClientPlayer player) {
            return player.getModelName().equals("default");
        }

        return true;
    }
}
