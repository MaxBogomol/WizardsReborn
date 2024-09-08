package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;

public class RainBannerPatternItem extends BannerPatternItem {

    public enum Types {
        VIOLENCE,
        REPRODUCTION,
        COOPERATION,
        HUNGER,
        SURVIVAL,
        ELEVATION
    }

    public Types type;

    public RainBannerPatternItem(Types type, TagKey<BannerPattern> bannerPattern, Item.Properties properties) {
        super(bannerPattern, properties);
        this.type = type;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(stack, level, list, flags);
        list.add(Component.empty());

        Color color = getColor(type);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        list.add(Component.empty().append(getLore(type)).withStyle(ChatFormatting.ITALIC).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, r, g, b))));
    }

    public static Color getColor(Types type) {
        switch (type) {
            case VIOLENCE:
                return new Color(220, 71, 71);
            case REPRODUCTION:
                return new Color(234, 113, 209);
            case COOPERATION:
                return new Color(70, 63, 182);
            case HUNGER:
                return new Color(86, 57, 63);
            case SURVIVAL:
                return new Color(93, 217, 65, 103);
            case ELEVATION:
                return new Color(255, 235, 114);
        }

        return new Color(255, 255, 255);
    }

    public static Component getLore(Types type) {
        switch (type) {
            case VIOLENCE:
                return Component.translatable("lore.wizards_reborn.violence_banner_pattern");
            case REPRODUCTION:
                return Component.translatable("lore.wizards_reborn.reproduction_banner_pattern");
            case COOPERATION:
                return Component.translatable("lore.wizards_reborn.cooperation_banner_pattern");
            case HUNGER:
                return Component.translatable("lore.wizards_reborn.hunger_banner_pattern");
            case SURVIVAL:
                return Component.translatable("lore.wizards_reborn.survival_banner_pattern");
            case ELEVATION:
                return Component.translatable("lore.wizards_reborn.elevation_banner_pattern");
        }

        return Component.empty();
    }
}
