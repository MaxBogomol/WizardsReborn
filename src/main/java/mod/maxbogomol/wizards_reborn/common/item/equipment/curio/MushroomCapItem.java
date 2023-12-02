package mod.maxbogomol.wizards_reborn.common.item.equipment.curio;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;

public class MushroomCapItem extends BaseCurioItem implements ICurioItemTexture {
    public String name;

    public MushroomCapItem(Properties properties, String name) {
        super(properties);
        this.name = name;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 1.0f);
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity) {
        return new ResourceLocation(WizardsReborn.MOD_ID,"textures/entity/curio/" + name + ".png");
    }
}
