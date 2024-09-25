package mod.maxbogomol.wizards_reborn.common.alchemypotion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import java.awt.*;

public class MilkAlchemyPotion extends FluidAlchemyPotion {

    public MilkAlchemyPotion(String id, Fluid fluid, Color color) {
        super(id, fluid, color);
    }

    public MilkAlchemyPotion(String id, Fluid fluid, Color color, MobEffectInstance... effects) {
        super(id, fluid, color, effects);
    }

    @Override
    public void apply(ItemStack stack, Level level, LivingEntity livingEntity) {
        livingEntity.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
    }
}
