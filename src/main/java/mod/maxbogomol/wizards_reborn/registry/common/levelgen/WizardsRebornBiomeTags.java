package mod.maxbogomol.wizards_reborn.registry.common.levelgen;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class WizardsRebornBiomeTags {
    public static final TagKey<Biome> MOR_MUSIC = TagKey.create(Registries.BIOME, new ResourceLocation(WizardsReborn.MOD_ID, "mor_music"));
    public static final TagKey<Biome> SHIMMER_MUSIC = TagKey.create(Registries.BIOME, new ResourceLocation(WizardsReborn.MOD_ID, "shimmer_music"));
}
