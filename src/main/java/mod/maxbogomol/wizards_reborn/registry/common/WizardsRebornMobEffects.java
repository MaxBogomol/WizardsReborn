package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.mobeffect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, WizardsReborn.MOD_ID);

    public static final RegistryObject<MobEffect> ARCANE_STRENGTH = MOB_EFFECTS.register("arcane_strength", ArcaneStrengthMobEffect::new);
    public static final RegistryObject<MobEffect> ARCANE_WEAKNESS = MOB_EFFECTS.register("arcane_weakness", ArcaneWeaknessMobEffect::new);
    public static final RegistryObject<MobEffect> MAGIC_RESISTANCE = MOB_EFFECTS.register("magic_resistance", ArcaneStrengthMobEffect::new);
    public static final RegistryObject<MobEffect> MAGIC_SENSIBILITY = MOB_EFFECTS.register("magic_sensibility", ArcaneWeaknessMobEffect::new);
    public static final RegistryObject<MobEffect> MOR_SPORES = MOB_EFFECTS.register("mor_spores", MorSporesMobEffect::new);
    public static final RegistryObject<MobEffect> WISSEN_AURA = MOB_EFFECTS.register("wissen_aura", WissenAuraMobEffect::new);
    public static final RegistryObject<MobEffect> IRRITATION = MOB_EFFECTS.register("irritation", IrritationMobEffect::new);
    public static final RegistryObject<MobEffect> TIPSY = MOB_EFFECTS.register("tipsy", TipsyMobEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
