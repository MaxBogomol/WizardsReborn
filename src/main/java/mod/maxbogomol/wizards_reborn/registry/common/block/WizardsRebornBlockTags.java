package mod.maxbogomol.wizards_reborn.registry.common.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class WizardsRebornBlockTags {
    public static final TagKey<Block> FLUID_PIPE_CONNECTION = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_connection"));
    public static final TagKey<Block> FLUID_PIPE_CONNECTION_TOGGLE = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "fluid_pipe_connection_toggle"));
    public static final TagKey<Block> STEAM_PIPE_CONNECTION = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_connection"));
    public static final TagKey<Block> STEAM_PIPE_CONNECTION_TOGGLE = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "steam_pipe_connection_toggle"));
    public static final TagKey<Block> EXTRACTOR_LEVER_CONNECTION = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "extractor_lever_connection"));
    public static final TagKey<Block> ALTAR_OF_DROUGHT_TARGET  = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "altar_of_drought_target"));
    public static final TagKey<Block> ORES  = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", "ores"));
    public static final TagKey<Block> CORK_BAMBOO_PLANTABLE_ON  = TagKey.create(Registries.BLOCK, new ResourceLocation(WizardsReborn.MOD_ID, "cork_bamboo_plantable_on"));
}
