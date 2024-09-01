package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornBannerPatterns {
    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registries.BANNER_PATTERN, WizardsReborn.MOD_ID);

    public static final RegistryObject<BannerPattern> VIOLENCE_BANNER_PATTERN = BANNER_PATTERNS.register("violence", () -> new BannerPattern("wrv"));
    public static final RegistryObject<BannerPattern> REPRODUCTION_BANNER_PATTERN = BANNER_PATTERNS.register("reproduction", () -> new BannerPattern("wrr"));
    public static final RegistryObject<BannerPattern> COOPERATION_BANNER_PATTERN = BANNER_PATTERNS.register("cooperation", () -> new BannerPattern("wrc"));
    public static final RegistryObject<BannerPattern> HUNGER_BANNER_PATTERN = BANNER_PATTERNS.register("hunger", () -> new BannerPattern("wrh"));
    public static final RegistryObject<BannerPattern> SURVIVAL_BANNER_PATTERN = BANNER_PATTERNS.register("survival", () -> new BannerPattern("wrs"));
    public static final RegistryObject<BannerPattern> ELEVATION_BANNER_PATTERN = BANNER_PATTERNS.register("elevation", () -> new BannerPattern("wre"));

    public static void register(IEventBus eventBus) {
        BANNER_PATTERNS.register(eventBus);
    }
}
