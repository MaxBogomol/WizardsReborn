package mod.maxbogomol.wizards_reborn.registry.common.fluid;

import mod.maxbogomol.fluffy_fur.common.fluid.CustomFluidType;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornParticles;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

import java.util.function.Supplier;

public class WizardsRebornFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, WizardsReborn.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, WizardsReborn.MOD_ID);

    public static final RegistryObject<FlowingFluid> MUNDANE_BREW = FLUIDS.register("mundane_brew", () -> new ForgeFlowingFluid.Source(WizardsRebornFluids.MUNDANE_BREW_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MUNDANE_BREW = FLUIDS.register("flowing_mundane_brew", () -> new ForgeFlowingFluid.Flowing(WizardsRebornFluids.MUNDANE_BREW_PROPERTIES));

    public static final RegistryObject<FlowingFluid> ALCHEMY_OIL = FLUIDS.register("alchemy_oil", () -> new ForgeFlowingFluid.Source(WizardsRebornFluids.ALCHEMY_OIL_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_ALCHEMY_OIL = FLUIDS.register("flowing_alchemy_oil", () -> new ForgeFlowingFluid.Flowing(WizardsRebornFluids.ALCHEMY_OIL_PROPERTIES));

    public static final RegistryObject<FlowingFluid> OIL_TEA = FLUIDS.register("oil_tea", () -> new ForgeFlowingFluid.Source(WizardsRebornFluids.OIL_TEA_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_OIL_TEA = FLUIDS.register("flowing_oil_tea", () -> new ForgeFlowingFluid.Flowing(WizardsRebornFluids.OIL_TEA_PROPERTIES));

    public static final RegistryObject<FlowingFluid> WISSEN_TEA = FLUIDS.register("wissen_tea", () -> new ForgeFlowingFluid.Source(WizardsRebornFluids.WISSEN_TEA_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_WISSEN_TEA = FLUIDS.register("flowing_wissen_tea", () -> new ForgeFlowingFluid.Flowing(WizardsRebornFluids.WISSEN_TEA_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MILK_TEA = FLUIDS.register("milk_tea", () -> new ForgeFlowingFluid.Source(WizardsRebornFluids.MILK_TEA_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MILK_TEA = FLUIDS.register("flowing_milk_tea", () -> new ForgeFlowingFluid.Flowing(WizardsRebornFluids.MILK_TEA_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MUSHROOM_BREW  = FLUIDS.register("mushroom_brew", () -> new ForgeFlowingFluid.Source(WizardsRebornFluids.MUSHROOM_BREW_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MUSHROOM_BREW = FLUIDS.register("flowing_mushroom_brew", () -> new ForgeFlowingFluid.Flowing(WizardsRebornFluids.MUSHROOM_BREW_PROPERTIES));

    public static final RegistryObject<FlowingFluid> HELLISH_MUSHROOM_BREW = FLUIDS.register("hellish_mushroom_brew", () -> new ForgeFlowingFluid.Source(WizardsRebornFluids.HELLISH_MUSHROOM_BREW_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_HELLISH_MUSHROOM_BREW = FLUIDS.register("flowing_hellish_mushroom_brew", () -> new ForgeFlowingFluid.Flowing(WizardsRebornFluids.HELLISH_MUSHROOM_BREW_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MOR_BREW = FLUIDS.register("mor_brew", () -> new ForgeFlowingFluid.Source(WizardsRebornFluids.MOR_BREW_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MOR_BREW = FLUIDS.register("flowing_mor_brew", () -> new ForgeFlowingFluid.Flowing(WizardsRebornFluids.MOR_BREW_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWER_BREW = FLUIDS.register("flower_brew", () -> new ForgeFlowingFluid.Source(WizardsRebornFluids.FLOWER_BREW_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_FLOWER_BREW = FLUIDS.register("flowing_flower_brew", () -> new ForgeFlowingFluid.Flowing(WizardsRebornFluids.FLOWER_BREW_PROPERTIES));

    public static final RegistryObject<FluidType> MUNDANE_BREW_TYPE =  FLUID_TYPES.register("mundane_brew", () -> new CustomFluidType(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"), new ResourceLocation(WizardsReborn.MOD_ID, "textures/misc/mundane_brew_overlay.png"),
            0xFF324B8D, new Vector3f(50f / 255f, 75f / 255f, 141f / 255f), FluidType.Properties.create().fallDistanceModifier(0).canExtinguish(true).supportsBoating(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)).setBubleParticle(ParticleTypes.BUBBLE).setSplashParticle(WizardsRebornParticles.MUNDANE_BREW_SPLASH.get()));
    public static final RegistryObject<FluidType> ALCHEMY_OIL_TYPE =  FLUID_TYPES.register("alchemy_oil", () -> new CustomFluidType(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"), new ResourceLocation(WizardsReborn.MOD_ID, "textures/misc/alchemy_oil_overlay.png"),
            0xFF602F3B, new Vector3f(96f / 255f, 47f / 255f, 59f / 255f), FluidType.Properties.create().fallDistanceModifier(0).motionScale(0.007D).canExtinguish(true).supportsBoating(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)).setSplashParticle(WizardsRebornParticles.ALCHEMY_OIL_SPLASH.get()));
    public static final RegistryObject<FluidType> OIL_TEA_TYPE =  FLUID_TYPES.register("oil_tea", () -> new CustomFluidType(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"), new ResourceLocation(WizardsReborn.MOD_ID, "textures/misc/oil_tea_overlay.png"),
            0xFFBD7C81, new Vector3f(189f / 255f, 124f / 255f, 129f / 255f), FluidType.Properties.create().fallDistanceModifier(0).canExtinguish(true).supportsBoating(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)).setSplashParticle(WizardsRebornParticles.OIL_TEA_SPLASH.get()));
    public static final RegistryObject<FluidType> WISSEN_TEA_TYPE =  FLUID_TYPES.register("wissen_tea", () -> new CustomFluidType(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"), new ResourceLocation(WizardsReborn.MOD_ID, "textures/misc/wissen_tea_overlay.png"),
            0xFF77A4D0, new Vector3f(119f / 255f, 164f / 255f, 208f / 255f), FluidType.Properties.create().fallDistanceModifier(0).canExtinguish(true).supportsBoating(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)).setSplashParticle(WizardsRebornParticles.WISSEN_TEA_SPLASH.get()));
    public static final RegistryObject<FluidType> MILK_TEA_TYPE =  FLUID_TYPES.register("milk_tea", () -> new CustomFluidType(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"), new ResourceLocation(WizardsReborn.MOD_ID, "textures/misc/milk_tea_overlay.png"),
            0xFFC2DDEE, new Vector3f(194f / 255f, 221f / 255f, 238f / 255f), FluidType.Properties.create().fallDistanceModifier(0).canExtinguish(true).supportsBoating(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)).setSplashParticle(WizardsRebornParticles.MILK_TEA_SPLASH.get()));
    public static final RegistryObject<FluidType> MUSHROOM_BREW_TYPE =  FLUID_TYPES.register("mushroom_brew", () -> new CustomFluidType(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"), new ResourceLocation(WizardsReborn.MOD_ID, "textures/misc/mushroom_brew_overlay.png"),
            0xFF8D6B53, new Vector3f(141f / 255f, 107f / 255f, 83f / 255f), FluidType.Properties.create().fallDistanceModifier(0).canExtinguish(true).supportsBoating(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)).setBubleParticle(ParticleTypes.BUBBLE).setSplashParticle(WizardsRebornParticles.MUSHROOM_BREW_SPLASH.get()));
    public static final RegistryObject<FluidType> HELLISH_MUSHROOM_BREW_TYPE =  FLUID_TYPES.register("hellish_mushroom_brew", () -> new CustomFluidType(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"), new ResourceLocation(WizardsReborn.MOD_ID, "textures/misc/hellish_mushroom_brew_overlay.png"),
            0xFF4E1B1B, new Vector3f(78f / 255f, 27f / 255f, 27f / 255f), FluidType.Properties.create().fallDistanceModifier(0).canExtinguish(true).supportsBoating(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)).setBubleParticle(ParticleTypes.BUBBLE).setSplashParticle(WizardsRebornParticles.HELLISH_MUSHROOM_BREW_SPLASH.get()));
    public static final RegistryObject<FluidType> MOR_BREW_TYPE =  FLUID_TYPES.register("mor_brew", () -> new CustomFluidType(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"), new ResourceLocation(WizardsReborn.MOD_ID, "textures/misc/mor_brew_overlay.png"),
            0xFF4D5474, new Vector3f(77f / 255f, 84f / 255f, 116f / 255f), FluidType.Properties.create().fallDistanceModifier(0).canExtinguish(true).supportsBoating(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)).setBubleParticle(ParticleTypes.BUBBLE).setSplashParticle(WizardsRebornParticles.MOR_BREW_SPLASH.get()));
    public static final RegistryObject<FluidType> FLOWER_BREW_TYPE =  FLUID_TYPES.register("flower_brew", () -> new CustomFluidType(new ResourceLocation("block/water_still"), new ResourceLocation("block/water_flow"), new ResourceLocation(WizardsReborn.MOD_ID, "textures/misc/flower_brew_overlay.png"),
            0xFF204426, new Vector3f(32f / 255f, 68f / 255f, 38f / 255f), FluidType.Properties.create().fallDistanceModifier(0).canExtinguish(true).supportsBoating(true).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)).setBubleParticle(ParticleTypes.BUBBLE).setSplashParticle(WizardsRebornParticles.FLOWER_BREW_SPLASH.get()));

    public static final ForgeFlowingFluid.Properties MUNDANE_BREW_PROPERTIES = new ForgeFlowingFluid.Properties(
            MUNDANE_BREW_TYPE, MUNDANE_BREW, FLOWING_MUNDANE_BREW)
            .explosionResistance(100f)
            .block(WizardsRebornBlocks.MUNDANE_BREW).bucket(WizardsRebornItems.MUNDANE_BREW_BUCKET);
    public static final ForgeFlowingFluid.Properties ALCHEMY_OIL_PROPERTIES = new ForgeFlowingFluid.Properties(
            ALCHEMY_OIL_TYPE, ALCHEMY_OIL, FLOWING_ALCHEMY_OIL)
            .levelDecreasePerBlock(2).explosionResistance(100f)
            .block(WizardsRebornBlocks.ALCHEMY_OIL).bucket(WizardsRebornItems.ALCHEMY_OIL_BUCKET);
    public static final ForgeFlowingFluid.Properties OIL_TEA_PROPERTIES = new ForgeFlowingFluid.Properties(
            OIL_TEA_TYPE, OIL_TEA, FLOWING_OIL_TEA)
            .explosionResistance(100f)
            .block(WizardsRebornBlocks.OIL_TEA).bucket(WizardsRebornItems.OIL_TEA_BUCKET);
    public static final ForgeFlowingFluid.Properties WISSEN_TEA_PROPERTIES = new ForgeFlowingFluid.Properties(
            WISSEN_TEA_TYPE, WISSEN_TEA, FLOWING_WISSEN_TEA)
            .explosionResistance(100f)
            .block(WizardsRebornBlocks.WISSEN_TEA).bucket(WizardsRebornItems.WISSEN_TEA_BUCKET);
    public static final ForgeFlowingFluid.Properties MILK_TEA_PROPERTIES = new ForgeFlowingFluid.Properties(
            MILK_TEA_TYPE, MILK_TEA, FLOWING_MILK_TEA)
            .explosionResistance(100f)
            .block(WizardsRebornBlocks.MILK_TEA).bucket(WizardsRebornItems.MILK_TEA_BUCKET);
    public static final ForgeFlowingFluid.Properties MUSHROOM_BREW_PROPERTIES = new ForgeFlowingFluid.Properties(
            MUSHROOM_BREW_TYPE, MUSHROOM_BREW, FLOWING_MUSHROOM_BREW)
            .explosionResistance(100f)
            .block(WizardsRebornBlocks.MUSHROOM_BREW).bucket(WizardsRebornItems.MUSHROOM_BREW_BUCKET);
    public static final ForgeFlowingFluid.Properties HELLISH_MUSHROOM_BREW_PROPERTIES = new ForgeFlowingFluid.Properties(
            HELLISH_MUSHROOM_BREW_TYPE, HELLISH_MUSHROOM_BREW, FLOWING_HELLISH_MUSHROOM_BREW)
            .explosionResistance(100f)
            .block(WizardsRebornBlocks.HELLISH_MUSHROOM_BREW).bucket(WizardsRebornItems.HELLISH_MUSHROOM_BREW_BUCKET);
    public static final ForgeFlowingFluid.Properties MOR_BREW_PROPERTIES = new ForgeFlowingFluid.Properties(
            MOR_BREW_TYPE, MOR_BREW, FLOWING_MOR_BREW)
            .explosionResistance(100f)
            .block(WizardsRebornBlocks.MOR_BREW).bucket(WizardsRebornItems.MOR_BREW_BUCKET);
    public static final ForgeFlowingFluid.Properties FLOWER_BREW_PROPERTIES = new ForgeFlowingFluid.Properties(
            FLOWER_BREW_TYPE,FLOWER_BREW, FLOWING_FLOWER_BREW)
            .explosionResistance(100f)
            .block(WizardsRebornBlocks.FLOWER_BREW).bucket(WizardsRebornItems.FLOWER_BREW_BUCKET);

    public static void register(IEventBus eventBus) {
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerFluids(FMLCommonSetupEvent event) {
            Supplier<IForgeRegistry<FluidType>> fluidTypes = ForgeRegistries.FLUID_TYPES;
            for (FluidType fluidType : fluidTypes.get()) {
                addCobblestoneInteraction(MUNDANE_BREW_TYPE.get(), fluidType);
                addCobblestoneInteraction(ALCHEMY_OIL_TYPE.get(), fluidType);
                addCobblestoneInteraction(OIL_TEA_TYPE.get(), fluidType);
                addCobblestoneInteraction(WISSEN_TEA_TYPE.get(), fluidType);
                addCobblestoneInteraction(MILK_TEA_TYPE.get(), fluidType);
                addCobblestoneInteraction(MUSHROOM_BREW_TYPE.get(), fluidType);
                addCobblestoneInteraction(HELLISH_MUSHROOM_BREW_TYPE.get(), fluidType);
                addCobblestoneInteraction(MOR_BREW_TYPE.get(), fluidType);
                addCobblestoneInteraction(FLOWER_BREW_TYPE.get(), fluidType);
            }
        }

        @SubscribeEvent
        public static void registerFluidsClient(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(MUNDANE_BREW.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_MUNDANE_BREW.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ALCHEMY_OIL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_ALCHEMY_OIL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(OIL_TEA.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_OIL_TEA.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(WISSEN_TEA.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_WISSEN_TEA.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(MILK_TEA.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_MILK_TEA.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(MUSHROOM_BREW.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_MUSHROOM_BREW.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(HELLISH_MUSHROOM_BREW.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_HELLISH_MUSHROOM_BREW.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(MOR_BREW.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_MOR_BREW.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWER_BREW.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_FLOWER_BREW.get(), RenderType.translucent());
        }
    }

    public static void addCobblestoneInteraction(FluidType source, FluidType fluidType) {
        if (!fluidType.equals(source)) {
            FluidInteractionRegistry.addInteraction(fluidType, new FluidInteractionRegistry.InteractionInformation(
                    source,
                    fluidState -> Blocks.COBBLESTONE.defaultBlockState()
            ));
        }
    }
}
