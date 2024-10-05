package mod.maxbogomol.wizards_reborn.common.item.equipment.curio;

import mod.maxbogomol.fluffy_fur.integration.common.curios.BaseCurioItem;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemType;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.util.NumericalUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class BaseWissenCurioItem extends BaseCurioItem implements IWissenItem {

    public BaseWissenCurioItem(Properties properties) {
        super(properties);
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
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        if (WizardsRebornClientConfig.NUMERICAL_WISSEN.get()) {
            WissenItemUtil.existWissen(stack);
            list.add(NumericalUtil.getWissenName(WissenItemUtil.getWissen(stack), getMaxWissen()).copy().withStyle(ChatFormatting.GRAY));
        }
    }
}
