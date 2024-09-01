package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.effect.IrritationEffect;
import mod.maxbogomol.wizards_reborn.common.effect.MorSporesEffect;
import mod.maxbogomol.wizards_reborn.common.effect.TipsyEffect;
import mod.maxbogomol.wizards_reborn.common.effect.WissenAuraEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, WizardsReborn.MOD_ID);

    public static final RegistryObject<MobEffect> MOR_SPORES = MOB_EFFECTS.register("mor_spores", MorSporesEffect::new);
    public static final RegistryObject<MobEffect> WISSEN_AURA = MOB_EFFECTS.register("wissen_aura", WissenAuraEffect::new);
    public static final RegistryObject<MobEffect> IRRITATION = MOB_EFFECTS.register("irritation", IrritationEffect::new);
    public static final RegistryObject<MobEffect> TIPSY = MOB_EFFECTS.register("tipsy", TipsyEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
