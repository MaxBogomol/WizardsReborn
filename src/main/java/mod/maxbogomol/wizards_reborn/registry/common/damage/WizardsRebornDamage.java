package mod.maxbogomol.wizards_reborn.registry.common.damage;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class WizardsRebornDamage {
    public static final ResourceKey<DamageType> ARCANE_MAGIC = register("arcane_magic");
    public static final ResourceKey<DamageType> RITUAL = register("ritual");

    private static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(WizardsReborn.MOD_ID, name));
    }

    public static DamageSource create(Level level, ResourceKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), source, attacker);
    }

    public static DamageSource create(Level level, ResourceKey<DamageType> key, @Nullable Entity source) {
        return create(level, key, source, null);
    }

    public static DamageSource create(Level level, ResourceKey<DamageType> key) {
        return create(level, key, null, null);
    }
}