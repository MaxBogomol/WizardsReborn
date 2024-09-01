package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.world.tree.ArcaneWoodTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.createOptional(Registries.TRUNK_PLACER_TYPE, WizardsReborn.MOD_ID);

    public static final RegistryObject<TrunkPlacerType<ArcaneWoodTrunkPlacer>> ARCANE_WOOD = TRUNK_PLACER_TYPES.register("arcane_wood", () -> new TrunkPlacerType<>(ArcaneWoodTrunkPlacer.CODEC));

    public static void register(IEventBus eventBus) {
        TRUNK_PLACER_TYPES.register(eventBus);
    }
}
