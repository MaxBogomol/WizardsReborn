package mod.maxbogomol.wizards_reborn.common.item.equipment.curio;

import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemType;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.utils.NumericalUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class BaseWissenCurioItem extends BaseCurioItem implements IWissenItem {
    public BaseWissenCurioItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
        return true;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity) {
        return null;
    }

    @Override
    public int getMaxWissen() {
        return 0;
    }

    @Override
    public WissenItemType getWissenItemType() {
        return WissenItemType.OFF;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        if (ClientConfig.NUMERICAL_WISSEN.get()) {
            WissenItemUtils.existWissen(stack);
            list.add(NumericalUtils.getWissenName(WissenItemUtils.getWissen(stack), getMaxWissen()).copy().withStyle(ChatFormatting.GRAY));
        }
    }
}
