package mod.maxbogomol.wizards_reborn.registry.common.fluid;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class WizardsRebornFluidTags {
    public static final TagKey<Fluid> STEAM_SOURCE = TagKey.create(Registries.FLUID, new ResourceLocation(WizardsReborn.MOD_ID, "steam_source"));
    public static final TagKey<Fluid> HEAT_SOURCE = TagKey.create(Registries.FLUID, new ResourceLocation(WizardsReborn.MOD_ID, "heat_source"));
    public static final TagKey<Fluid> STEAM_EQUIVALENT = TagKey.create(Registries.FLUID, new ResourceLocation(WizardsReborn.MOD_ID, "steam_equivalent"));
    public static final TagKey<Fluid> COBBLESTONE_INTERACTION = TagKey.create(Registries.FLUID, new ResourceLocation(WizardsReborn.MOD_ID, "cobblestone_interaction"));
}
