package mod.maxbogomol.wizards_reborn.integration.common.create.common.alchemypotion;

import mod.maxbogomol.wizards_reborn.common.alchemypotion.FluidAlchemyPotion;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import java.awt.*;

public class HoneyAlchemyPotion extends FluidAlchemyPotion {

    public HoneyAlchemyPotion(String id, Fluid fluid, Color color) {
        super(id, fluid, color);
    }

    public HoneyAlchemyPotion(String id, Fluid fluid, Color color, MobEffectInstance... effects) {
        super(id, fluid, color, effects);
    }

    @Override
    public void apply(ItemStack stack, Level level, LivingEntity livingEntity) {
        livingEntity.removeEffect(MobEffects.POISON);
    }
}
