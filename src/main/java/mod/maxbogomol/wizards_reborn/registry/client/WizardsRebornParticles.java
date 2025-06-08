package mod.maxbogomol.wizards_reborn.registry.client;

import mod.maxbogomol.fluffy_fur.client.particle.type.GenericParticleType;
import mod.maxbogomol.fluffy_fur.client.particle.type.LeavesParticleType;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, WizardsReborn.MOD_ID);
    
    public static RegistryObject<GenericParticleType> KARMA = PARTICLES.register("karma", GenericParticleType::new);
    public static RegistryObject<LeavesParticleType> ARCANE_WOOD_LEAVES = PARTICLES.register("arcane_wood_leaves", LeavesParticleType::new);
    public static RegistryObject<LeavesParticleType> INNOCENT_WOOD_LEAVES= PARTICLES.register("innocence_wood_leaves", LeavesParticleType::new);
    public static RegistryObject<GenericParticleType> THROWN_SHEARS_CUT = PARTICLES.register("thrown_shears_cut", GenericParticleType::new);

    public static RegistryObject<SimpleParticleType> MUNDANE_BREW_SPLASH = PARTICLES.register("mundane_brew_splash", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> ALCHEMY_OIL_SPLASH = PARTICLES.register("alchemy_oil_splash", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> OIL_TEA_SPLASH = PARTICLES.register("oil_tea_splash", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> WISSEN_TEA_SPLASH = PARTICLES.register("wissen_tea_splash", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> MILK_TEA_SPLASH = PARTICLES.register("milk_tea_splash", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> MUSHROOM_BREW_SPLASH = PARTICLES.register("mushroom_brew_splash", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> HELLISH_MUSHROOM_BREW_SPLASH = PARTICLES.register("hellish_mushroom_brew_splash", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> MOR_BREW_SPLASH = PARTICLES.register("mor_brew_splash", () -> new SimpleParticleType(false));
    public static RegistryObject<SimpleParticleType> FLOWER_BREW_SPLASH = PARTICLES.register("flower_brew_splash", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLES.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerParticles(RegisterParticleProvidersEvent event) {
            ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
            particleEngine.register(KARMA.get(), GenericParticleType.Factory::new);
            particleEngine.register(ARCANE_WOOD_LEAVES.get(), LeavesParticleType.Factory::new);
            particleEngine.register(INNOCENT_WOOD_LEAVES.get(), LeavesParticleType.Factory::new);
            particleEngine.register(THROWN_SHEARS_CUT.get(), GenericParticleType.Factory::new);

            particleEngine.register(MUNDANE_BREW_SPLASH.get(), SplashParticle.Provider::new);
            particleEngine.register(ALCHEMY_OIL_SPLASH.get(), SplashParticle.Provider::new);
            particleEngine.register(OIL_TEA_SPLASH.get(), SplashParticle.Provider::new);
            particleEngine.register(WISSEN_TEA_SPLASH.get(), SplashParticle.Provider::new);
            particleEngine.register(MILK_TEA_SPLASH.get(), SplashParticle.Provider::new);
            particleEngine.register(MUSHROOM_BREW_SPLASH.get(), SplashParticle.Provider::new);
            particleEngine.register(HELLISH_MUSHROOM_BREW_SPLASH.get(), SplashParticle.Provider::new);
            particleEngine.register(MOR_BREW_SPLASH.get(), SplashParticle.Provider::new);
            particleEngine.register(FLOWER_BREW_SPLASH.get(), SplashParticle.Provider::new);
        }
    }
}
