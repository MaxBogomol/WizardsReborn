package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.fluffy_fur.client.model.armor.ArmorModel;
import mod.maxbogomol.fluffy_fur.common.itemskin.ItemSkin;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornModels;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.event.TickEvent;

import java.util.UUID;
import java.util.function.Consumer;

public class ArcaneFortressArmorItem extends ArcaneArmorItem implements IForgeItem {
    public static final UUID BASE_MAX_HEALTH_UUID = UUID.fromString("11864F7D-2608-4A27-A7AA-7483A4fAA5A1");

    public ArcaneFortressArmorItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentTypes.FORTRESS_ARMOR);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public ArmorModel getHumanoidArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel original) {
                float partialTicks = Minecraft.getInstance().getFrameTime();
                float f = Mth.rotLerp(partialTicks, entity.yBodyRotO, entity.yBodyRot);
                float f1 = Mth.rotLerp(partialTicks, entity.yHeadRotO, entity.yHeadRot);
                float netHeadYaw = f1 - f;
                float netHeadPitch = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());

                ArmorModel model = WizardsRebornModels.ARCANE_FORTRESS_ARMOR;
                if (!isDefaultModel(entity)) {
                    model = WizardsRebornModels.ARCANE_FORTRESS_SLIM_ARMOR;
                }

                ItemSkin skin = ItemSkin.getSkinFromItem(itemStack);
                if (skin != null) model = skin.getArmorModel(entity, itemStack, equipmentSlot, original);

                model.slot = type.getSlot();
                model.copyFromDefault(original);
                model.setupAnim(entity, entity.walkAnimation.position(partialTicks), entity.walkAnimation.speed(partialTicks), entity.tickCount + partialTicks, netHeadYaw, netHeadPitch);
                return model;
            }
        });
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        ItemSkin skin = ItemSkin.getSkinFromItem(stack);
        if (skin != null) return skin.getArmorTexture(stack, entity, slot, type);
        if (!isDefaultModel(entity)) {
            return WizardsReborn.MOD_ID + ":textures/models/armor/arcane_fortress_slim.png";
        }
        return WizardsReborn.MOD_ID + ":textures/models/armor/arcane_fortress.png";
    }

    @Override
    public boolean hasCustomModel() {
        return true;
    }

    @Override
    public boolean hasArmorSet() {
        return true;
    }

    @Override
    public MutableComponent getArmorSetName() {
        return Component.translatable("lore.wizards_reborn.arcane_fortress_armor");
    }

    @Override
    public ChatFormatting getArmorSetColor() {
        return ChatFormatting.GOLD;
    }

    @Override
    public ItemStack getArmorSetItem(EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> WizardsRebornItems.ARCANE_FORTRESS_HELMET.get().getDefaultInstance();
            case CHEST -> WizardsRebornItems.ARCANE_FORTRESS_CHESTPLATE.get().getDefaultInstance();
            case LEGS -> WizardsRebornItems.ARCANE_FORTRESS_LEGGINGS.get().getDefaultInstance();
            case FEET -> WizardsRebornItems.ARCANE_FORTRESS_BOOTS.get().getDefaultInstance();
            default -> ItemStack.EMPTY;
        };
    }

    public int getHealthForSet() {
        return 4;
    }

    public static void playerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        AttributeInstance attr = player.getAttribute(Attributes.MAX_HEALTH);
        boolean remove = false;

        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof ArcaneFortressArmorItem armorItem) {
            if (armorItem.hasArmorSetPlayer(player)) {
                if (attr.getModifier(BASE_MAX_HEALTH_UUID) == null) {
                    attr.addPermanentModifier(new AttributeModifier(BASE_MAX_HEALTH_UUID, "bonus", armorItem.getHealthForSet(), AttributeModifier.Operation.ADDITION));
                } else {
                    if (attr.getModifier(BASE_MAX_HEALTH_UUID).getAmount() != armorItem.getHealthForSet()) {
                        attr.removeModifier(BASE_MAX_HEALTH_UUID);
                        attr.addPermanentModifier(new AttributeModifier(BASE_MAX_HEALTH_UUID, "bonus", armorItem.getHealthForSet(), AttributeModifier.Operation.ADDITION));
                    }
                }
            } else {
                remove = true;
            }
        } else {
            remove = true;
        }

        if (remove) {
            if (attr.getModifier(BASE_MAX_HEALTH_UUID) != null) {
                if (player.getHealth() > player.getHealth() - attr.getModifier(BASE_MAX_HEALTH_UUID).getAmount()) {
                    player.setHealth((float) (player.getMaxHealth() - attr.getModifier(BASE_MAX_HEALTH_UUID).getAmount()));
                }
                attr.removeModifier(BASE_MAX_HEALTH_UUID);
            }
        }
    }
}
