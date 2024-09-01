package mod.maxbogomol.wizards_reborn.registry.common.banner;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BannerPattern;

public class WizardsRebornBannerPatternTags {
    public static final TagKey<BannerPattern> VIOLENCE_BANNER = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(WizardsReborn.MOD_ID, "pattern_item/violence"));
    public static final TagKey<BannerPattern> REPRODUCTION_BANNER = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(WizardsReborn.MOD_ID, "pattern_item/reproduction"));
    public static final TagKey<BannerPattern> COOPERATION_BANNER = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(WizardsReborn.MOD_ID, "pattern_item/cooperation"));
    public static final TagKey<BannerPattern> HUNGER_BANNER = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(WizardsReborn.MOD_ID, "pattern_item/hunger"));
    public static final TagKey<BannerPattern> SURVIVAL_BANNER = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(WizardsReborn.MOD_ID, "pattern_item/survival"));
    public static final TagKey<BannerPattern> ELEVATION_BANNER = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(WizardsReborn.MOD_ID, "pattern_item/elevation"));
}
