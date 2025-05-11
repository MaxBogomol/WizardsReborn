package mod.maxbogomol.wizards_reborn.common.item.food;

import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.WizardsRebornFarmersDelight;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class ArcaneFoodItem extends Item {

    public int nourishmentTick = 0;
    public int nourishmentLevel = 0;
    public int comfortTick = 0;
    public int comfortLevel = 0;

    public ArcaneFoodItem(Properties properties) {
        super(properties);
    }

    public ArcaneFoodItem setNourishmentTick(int nourishmentTick) {
        this.nourishmentTick = nourishmentTick;
        return this;
    }

    public ArcaneFoodItem setNourishmentLevel(int nourishmentLevel) {
        this.nourishmentLevel = nourishmentLevel;
        return this;
    }

    public ArcaneFoodItem setComfortTick(int comfortTick) {
        this.comfortTick = comfortTick;
        return this;
    }

    public ArcaneFoodItem setComfortLevel(int comfortLevel) {
        this.comfortLevel = comfortLevel;
        return this;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        applyEffect(stack, level, livingEntity);
        return super.finishUsingItem(stack, level, livingEntity);
    }

    public void applyEffect(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (WizardsRebornFarmersDelight.isLoaded()) {
            if (nourishmentTick > 0) {
                WizardsRebornFarmersDelight.LoadedOnly.addNourishmentEffect(livingEntity, nourishmentTick, nourishmentLevel);
            }
            if (comfortTick > 0) {
                WizardsRebornFarmersDelight.LoadedOnly.addComfortEffect(livingEntity, comfortTick, comfortLevel);
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        if (WizardsRebornFarmersDelight.isLoaded()) {
            ArrayList<MobEffectInstance> effects = new ArrayList<>();
            if (nourishmentTick > 0) {
                effects.add(WizardsRebornFarmersDelight.LoadedOnly.getNourishmentEffect(nourishmentTick, nourishmentLevel));
            }
            if (comfortTick > 0) {
                effects.add(WizardsRebornFarmersDelight.LoadedOnly.getComfortEffect(comfortTick, comfortLevel));
            }
            PotionUtils.addPotionTooltip(effects, list, 1.0f);
        }
    }
}
