package mod.maxbogomol.wizards_reborn.common.item.equipment.arcane;

import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtil;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.IArcaneItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public class ArcaneEnchantedBookItem extends Item implements IArcaneItem {
    public List<ArcaneEnchantmentType> arcaneEnchantmentTypes = new ArrayList<>();

    public ArcaneEnchantedBookItem(Properties properties) {
        super(properties);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.ALL);
        arcaneEnchantmentTypes.add(ArcaneEnchantmentType.BOOK);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        list.addAll(ArcaneEnchantmentUtil.appendHoverText(stack, world, flags));
    }

    @Override
    public List<ArcaneEnchantmentType> getArcaneEnchantmentTypes() {
        return arcaneEnchantmentTypes;
    }

    public ArcaneEnchantedBookItem addArcaneEnchantmentType(ArcaneEnchantmentType type) {
        arcaneEnchantmentTypes.add(type);
        return this;
    }
}
