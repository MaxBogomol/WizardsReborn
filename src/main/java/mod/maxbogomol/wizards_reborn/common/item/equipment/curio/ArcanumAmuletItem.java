package mod.maxbogomol.wizards_reborn.common.item.equipment.curio;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ArcanumAmuletItem extends Item implements ICurioItem, ICurioItemTexture, IWissenItem {

    private static final ResourceLocation AMULET_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID,"textures/entity/curio/arcanum_amulet.png");

    public ArcanumAmuletItem(Item.Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_GOLD, 1.0f, 1.0f);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
        return true;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext,
                                                                        UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        return atts;
    }

    @Override
    public int getMaxWissen() {
        return 1000;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide()) {
            WissenItemUtils.existWissen(stack);
        }
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity) {
        return AMULET_TEXTURE;
    }
}
