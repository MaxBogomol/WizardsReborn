package mod.maxbogomol.wizards_reborn.common.item.equipment.curio;

import mod.maxbogomol.fluffy_fur.integration.common.curios.BaseCurioItem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;

public class MushroomCapItem extends BaseCurioItem {

    public static MushroomCapColor BROWN_MUSHROOM = new MushroomCapColor("brown_mushroom_cap");
    public static MushroomCapColor RED_MUSHROOM = new MushroomCapColor("red_mushroom_cap");
    public static MushroomCapColor CRIMSON_FUNGUS = new MushroomCapColor("crimson_fungus_cap");
    public static MushroomCapColor WARPED_FUNGUS = new MushroomCapColor("warped_fungus_cap");
    public static MushroomCapColor MOR = new MushroomCapColor("mor_cap");
    public static MushroomCapColor ELDER_MOR = new MushroomCapColor("elder_mor_cap");

    public MushroomCapColor color = BROWN_MUSHROOM;

    public MushroomCapItem(Properties properties) {
        super(properties);
    }

    public MushroomCapItem(Properties properties, MushroomCapColor color) {
        super(properties);
        this.color = color;
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 1.0f);
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity) {
        return color.getTexture();
    }

    public static class MushroomCapColor {
        public String modId;
        public String texture;

        public MushroomCapColor(String modId, String texture) {
            this.modId = modId;
            this.texture = texture;
        }

        public MushroomCapColor(String texture) {
            this.modId = WizardsReborn.MOD_ID;
            this.texture = texture;
        }

        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getTexture() {
            return new ResourceLocation(modId, "textures/entity/curio/" + texture + ".png");
        }
    }
}
