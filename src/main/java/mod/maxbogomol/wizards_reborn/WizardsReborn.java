package mod.maxbogomol.wizards_reborn;

import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.common.proxy.ClientProxy;
import mod.maxbogomol.fluffy_fur.common.proxy.ISidedProxy;
import mod.maxbogomol.fluffy_fur.common.proxy.ServerProxy;
import mod.maxbogomol.wizards_reborn.common.event.WizardsRebornEvents;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.knowledge.Researches;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.integration.common.create.WizardsRebornCreate;
import mod.maxbogomol.wizards_reborn.integration.common.farmers_delight.WizardsRebornFarmersDelight;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornParticles;
import mod.maxbogomol.wizards_reborn.registry.common.*;
import mod.maxbogomol.wizards_reborn.registry.common.banner.WizardsRebornBannerPatterns;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlockEntities;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.entity.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.fluid.WizardsRebornFluids;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornCreativeTabs;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItemSkins;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import mod.maxbogomol.wizards_reborn.registry.common.levelgen.WizardsRebornTrunkPlacerTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("wizards_reborn")
public class WizardsReborn {
    public static final String MOD_ID = "wizards_reborn";
    public static final String NAME = "Wizard's Reborn";
    public static final String VERSION = "0.2.7";
    public static final int VERSION_NUMBER = 27;

    public static final ISidedProxy proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static final Logger LOGGER = LogManager.getLogger();

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
        WizardsRebornMobEffects.register(eventBus);
        WizardsRebornFluids.register(eventBus);

        WizardsRebornCreate.init(eventBus);
        WizardsRebornFarmersDelight.init(eventBus);

        WizardsRebornCrystals.register();
        WizardsRebornMonograms.register();
        WizardsRebornSpells.register();
        WizardsRebornItemSkins.register();
        WizardsRebornArcaneEnchantments.register();
        WizardsRebornCrystalRituals.register();
        WizardsRebornLightTypes.register();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, WizardsRebornClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WizardsRebornConfig.SPEC);

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            WizardsRebornClient.ClientOnly.clientInit();
            return new Object();
        });

        eventBus.addListener(this::setup);
        eventBus.addListener(WizardsRebornClient::clientSetup);

        WizardsRebornCreativeTabs.register(eventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new WizardsRebornEvents());
        ForgeMod.enableMilkFluid();
    }

    private void setup(final FMLCommonSetupEvent event) {
        hi();
        WizardsRebornPacketHandler.init();
        WizardsRebornAlchemyPotions.init();
        WizardsRebornKnowledges.init();

        Researches.init();

        WissenWandItem.setupControlTypes();

        WizardsRebornItems.setupBook();
        WizardsRebornItems.setupItems();
        WizardsRebornItems.setupCrystalsItems();
        WizardsRebornItems.setupDrinksItems();
        WizardsRebornBlocks.setupBlocks();
    }

    public static void hi() {
        FluffyFur.LOGGER.info("Hi Wizard's Reborn :3");
        LOGGER.info("Hi Fluffy Fur :3");
    }
}