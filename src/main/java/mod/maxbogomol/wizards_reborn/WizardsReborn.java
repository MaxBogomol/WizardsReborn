package mod.maxbogomol.wizards_reborn;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.proxy.ClientProxy;
import mod.maxbogomol.fluffy_fur.common.proxy.ISidedProxy;
import mod.maxbogomol.fluffy_fur.common.proxy.ServerProxy;
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.api.skin.Skins;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.common.capability.IArrowModifier;
import mod.maxbogomol.wizards_reborn.common.capability.IKnowledge;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.config.ServerConfig;
import mod.maxbogomol.wizards_reborn.common.creativetab.WizardsRebornCreativeTab;
import mod.maxbogomol.wizards_reborn.common.event.Events;
import mod.maxbogomol.wizards_reborn.integration.common.create.CreateIntegration;
import mod.maxbogomol.wizards_reborn.integration.common.farmersdelight.FarmersDelightIntegration;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.knowledge.Researches;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.world.tree.SupplierBlockStateProvider;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornParticles;
import mod.maxbogomol.wizards_reborn.registry.common.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("wizards_reborn")
public class WizardsReborn {
    public static final String MOD_ID = "wizards_reborn";
    public static final String NAME = "Wizard's Reborn";
    public static final String VERSION = "0.2";
    public static final int VERSION_NUMBER = 20;

    public static final ISidedProxy proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPE = DeferredRegister.createOptional(Registries.BLOCK_STATE_PROVIDER_TYPE, MOD_ID);

    public static final RegistryObject<BlockStateProviderType<?>> AN_STATEPROVIDER = BLOCK_STATE_PROVIDER_TYPE.register("an_stateprovider", () -> new BlockStateProviderType<>(SupplierBlockStateProvider.CODEC));

    public WizardsReborn() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        WizardsRebornItems.register(eventBus);
        WizardsRebornBlocks.register(eventBus);
        WizardsRebornBlockEntities.register(eventBus);
        WizardsRebornEntities.register(eventBus);
        WizardsRebornParticles.register(eventBus);
        WizardsRebornRecipes.register(eventBus);
        WizardsRebornMenuTypes.register(eventBus);
        WizardsRebornBannerPatterns.register(eventBus);
        WizardsRebornSounds.register(eventBus);
        WizardsRebornArgumentTypes.register(eventBus);
        WizardsRebornAttributes.register(eventBus);
        WizardsRebornTrunkPlacerTypes.register(eventBus);
        BLOCK_STATE_PROVIDER_TYPE.register(eventBus);
        WizardsRebornMobEffects.register(eventBus);
        WizardsRebornFluids.register(eventBus);

        CreateIntegration.init(eventBus);
        FarmersDelightIntegration.init(eventBus);

        WizardsRebornCrystals.register();
        WizardsRebornMonograms.register();
        WizardsRebornSpells.register();
        WizardsRebornSkins.register();
        WizardsRebornArcaneEnchantments.register();
        WizardsRebornCrystalRituals.register();

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            WizardsRebornClient.ClientOnly.clientInit();
            return new Object();
        });

        eventBus.addListener(this::setup);
        eventBus.addListener(WizardsRebornClient::clientSetup);

        WizardsRebornCreativeTab.register(eventBus);
        eventBus.addListener(WizardsRebornCreativeTab::addCreative);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Events());
    }

    private void setup(final FMLCommonSetupEvent event) {
        hi();
        PacketHandler.init();
        WizardsRebornAlchemyPotions.init();
        WizardsRebornKnowledges.init();
        Researches.init();
        WissenWandItem.setupControlTypes();

        WizardsRebornItems.setupCrystalsItems();
        WizardsRebornItems.setupDrinksItems();

        for (Skin skin : Skins.getSkins()) {
            skin.setupSkinEntries();
        }

        WizardsRebornBlocks.setupBlocks();
        WizardsRebornItems.setupItems();
    }

    public static void hi() {
        FluffyFur.LOGGER.info("Hi Wizard's Reborn :3");
        LOGGER.info("Hi Fluffy Fur :3");
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerCaps(RegisterCapabilitiesEvent event) {
            event.register(IKnowledge.class);
            event.register(IArrowModifier.class);
        }
    }
}