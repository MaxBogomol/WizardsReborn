package mod.maxbogomol.wizards_reborn.registry.common.damage;

import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class WizardsRebornDamageTypes {
    public static final ResourceKey<DamageType> ARCANE_MAGIC = DamageHandler.register(WizardsReborn.MOD_ID, "arcane_magic");
    public static final ResourceKey<DamageType> RITUAL = DamageHandler.register(WizardsReborn.MOD_ID, "ritual");
}