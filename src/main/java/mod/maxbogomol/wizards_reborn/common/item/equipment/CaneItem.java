package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeMod;

import java.util.UUID;

public class CaneItem extends Item {
    public static final UUID BASE_MOVEMENT_SPEED_MAINHAND_UUID = UUID.fromString("1EF77B34-978A-11EE-B9B1-0242AC120002");
    public static final UUID BASE_MOVEMENT_SPEED_OFFHAND_UUID = UUID.fromString("BB9F719D-A476-498C-A645-118BD98A5228");
    public static final UUID BASE_STEP_HEIGHT_ADDITION_MAINHAND_UUID = UUID.fromString("61271427-8CCB-4A94-85C9-A6E3E4222054");
    public static final UUID BASE_STEP_HEIGHT_ADDITION_OFFHAND_UUID = UUID.fromString("91301CF8-062F-4C22-B9CF-F11B54F24074");

    public CaneItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> atts = ImmutableMultimap.builder();
        atts.putAll(super.getDefaultAttributeModifiers(slot));
        if (slot == EquipmentSlot.MAINHAND) {
            atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(BASE_MOVEMENT_SPEED_MAINHAND_UUID, "bonus", 0.15f, AttributeModifier.Operation.MULTIPLY_BASE));
            atts.put(ForgeMod.STEP_HEIGHT_ADDITION.get(), new AttributeModifier(BASE_STEP_HEIGHT_ADDITION_MAINHAND_UUID, "bonus", 0.5f, AttributeModifier.Operation.ADDITION));
        }
        if (slot == EquipmentSlot.OFFHAND) {
            atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(BASE_MOVEMENT_SPEED_OFFHAND_UUID, "bonus", 0.15f, AttributeModifier.Operation.MULTIPLY_BASE));
            atts.put(ForgeMod.STEP_HEIGHT_ADDITION.get(), new AttributeModifier(BASE_STEP_HEIGHT_ADDITION_OFFHAND_UUID, "bonus", 0.5f, AttributeModifier.Operation.ADDITION));
        }
        return (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) ? atts.build() : super.getDefaultAttributeModifiers(slot);
    }
}
