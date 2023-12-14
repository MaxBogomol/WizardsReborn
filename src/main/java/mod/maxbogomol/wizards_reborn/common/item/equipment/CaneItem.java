package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;

import java.util.UUID;

public class CaneItem extends Item {
    public static final UUID BASE_MOVEMENT_SPEED_UUID = UUID.fromString("1EF77B34-978A-11EE-B9B1-0242AC120002");

    public CaneItem(Properties properties) {
        super(properties);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> atts = ImmutableMultimap.builder();
        atts.putAll(super.getDefaultAttributeModifiers(slot));
        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(BASE_MOVEMENT_SPEED_UUID, "bonus", 0.15f, AttributeModifier.Operation.MULTIPLY_BASE));
        return (slot == EquipmentSlot.MAINHAND || slot == EquipmentSlot.OFFHAND) ? atts.build() : super.getDefaultAttributeModifiers(slot);
    }
}
