package mod.maxbogomol.wizards_reborn.common.item.food;

import mod.maxbogomol.fluffy_fur.common.item.ICustomBlockEntityDataItem;
import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.WizardsRebornFarmersDelight;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class PancakeItem extends ItemNameBlockItem implements ICustomBlockEntityDataItem {

    public int nourishmentTick = 0;
    public int nourishmentLevel = 0;
    public int comfortTick = 0;
    public int comfortLevel = 0;

    public PancakeItem(Properties properties) {
        super(WizardsRebornBlocks.PANCAKE.get(), properties);
    }

    public PancakeItem setNourishmentTick(int nourishmentTick) {
        this.nourishmentTick = nourishmentTick;
        return this;
    }

    public PancakeItem setNourishmentLevel(int nourishmentLevel) {
        this.nourishmentLevel = nourishmentLevel;
        return this;
    }

    public PancakeItem setComfortTick(int comfortTick) {
        this.comfortTick = comfortTick;
        return this;
    }

    public PancakeItem setComfortLevel(int comfortLevel) {
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
    public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag nbt) {
        if (!nbt.contains("Items")) {
            ItemStack newStack = stack.copy();
            NonNullList<ItemStack> ret = NonNullList.withSize(1, ItemStack.EMPTY);
            ret.set(0, newStack);
            ContainerHelper.saveAllItems(nbt, ret);
        }

        return nbt;
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        if (canPlaceBlock(context)) {
            InteractionResult result = super.place(context);
            if (result != InteractionResult.FAIL) return result;
        }

        return InteractionResult.PASS;
    }

    public boolean canPlaceBlock(BlockPlaceContext context) {
        return context.getPlayer() != null && context.getPlayer().isShiftKeyDown();
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

    public static ResourceLocation getModelTexture(ItemStack stack) {
        String string = stack.getDescriptionId();
        int i = string.indexOf(".");
        string = string.substring(i + 1);
        i = string.indexOf(".");
        String modId = string.substring(0, i);
        String pancakeId = string.substring(i + 1);
        return new ResourceLocation(modId, "textures/models/pancake/" + pancakeId + ".png");
    }
}
