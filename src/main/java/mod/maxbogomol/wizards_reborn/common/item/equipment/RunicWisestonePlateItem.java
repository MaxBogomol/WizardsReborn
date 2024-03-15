package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtils;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRituals;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;

public class RunicWisestonePlateItem extends Item {

    public RunicWisestonePlateItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        CrystalRitualUtils.setCrystalRitual(stack, WizardsReborn.EMPTY_CRYSTAL_RITUAL);
        return stack;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ColorHandler implements ItemColor {
        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            if (tintIndex == 1) {
                CrystalRitual ritual = CrystalRitualUtils.getCrystalRitual(stack);
                if (!CrystalRitualUtils.isEmpty(ritual)) {
                    Color color = ritual.getColor();

                    int i = CrystalRituals.getCrystalRituals().indexOf(ritual);

                    float a = (float) Math.abs(Math.sin((ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick() + (i * 10)) / 15));

                    int r = Mth.lerpInt(a, 173, color.getRed());
                    int g = Mth.lerpInt(a, 237, color.getGreen());
                    int b = Mth.lerpInt(a, 205, color.getBlue());

                    return ColorUtils.packColor(255, r, g, b);
                }
            }
            return 0xFFFFFF;
        }
    }

    @Override
    public Component getHighlightTip(ItemStack stack, Component displayName) {
        CrystalRitual ritual = CrystalRitualUtils.getCrystalRitual(stack);
        if (!CrystalRitualUtils.isEmpty(ritual)) {
            return displayName.copy().append(Component.literal(" (")).append(getRitualName(ritual)).append(Component.literal(")"));
        }

        return displayName;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        CrystalRitual ritual = CrystalRitualUtils.getCrystalRitual(stack);
        if (!CrystalRitualUtils.isEmpty(ritual)) {
            list.add(getRitualName(ritual));
        }
    }

    public Component getRitualName(CrystalRitual ritual) {
        Color color = ritual.getColor();

        return Component.translatable(ritual.getTranslatedName()).withStyle(Style.EMPTY.withColor(ColorUtils.packColor(255, color.getRed(), color.getGreen(), color.getBlue())));
    }
}