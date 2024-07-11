package mod.maxbogomol.wizards_reborn.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ShrimpItem extends Item {

    public boolean isFried;

    public ShrimpItem(Properties properties, boolean isFried) {
        super(properties);
        this.isFried = isFried;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(stack, world, list, flags);

        Component component = Component.translatable("lore.wizards_reborn.shrimp");
        if (isFried) {
            component = Component.translatable("lore.wizards_reborn.fried_shrimp");
        }

        list.add(Component.empty().append(component).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
    }
}
