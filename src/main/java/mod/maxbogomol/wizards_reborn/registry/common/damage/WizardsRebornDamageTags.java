package mod.maxbogomol.wizards_reborn.registry.common.damage;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public class WizardsRebornDamageTags {
    public static final TagKey<DamageType> MAGIC = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge", "is_magic"));
    public static final TagKey<DamageType> ARCANE_MAGIC = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(WizardsReborn.MOD_ID, "is_arcane_magic"));
}
