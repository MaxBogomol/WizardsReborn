package mod.maxbogomol.wizards_reborn.registry.client;

import mod.maxbogomol.fluffy_fur.client.particle.type.GenericParticleType;
import mod.maxbogomol.fluffy_fur.client.particle.type.LeavesParticleType;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
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
        }
    }
}
