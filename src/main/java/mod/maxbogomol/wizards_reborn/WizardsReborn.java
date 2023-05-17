package mod.maxbogomol.wizards_reborn;

import com.google.common.collect.ImmutableMap;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.particle.SparkleParticleType;
import mod.maxbogomol.wizards_reborn.client.particle.WispParticleType;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.client.render.model.entity.ArcaneWoodBoatModel;
import mod.maxbogomol.wizards_reborn.client.render.model.item.*;
import mod.maxbogomol.wizards_reborn.client.render.model.tileentity.ArcanePedestalTileEntityRenderer;
import mod.maxbogomol.wizards_reborn.common.block.ArcanePedestalBlock;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneWoodStandingSignBlock;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneWoodWallSignBlock;
import mod.maxbogomol.wizards_reborn.common.data.recipes.ArcanumDustTransmutationRecipe;
import mod.maxbogomol.wizards_reborn.common.entity.CustomBoatEntity;
import mod.maxbogomol.wizards_reborn.common.item.ArcanumDustItem;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneGoldTier;
import mod.maxbogomol.wizards_reborn.common.item.CustomBoatItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcanePedestalTileEntity;
import mod.maxbogomol.wizards_reborn.common.tileentity.ArcaneWoodSignTileEntity;
import mod.maxbogomol.wizards_reborn.common.itemgroup.WizardsRebornItemGroup;
import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.SignTileEntityRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.util.stream.Collectors;

@Mod("wizards_reborn")
public class WizardsReborn
{
    public static final String MOD_ID = "wizards_reborn";

    private static final Logger LOGGER = LogManager.getLogger();

    public static final ItemGroup WIZARDS_REBORN_GROUP = WizardsRebornItemGroup.WIZARDS_REBORN_GROUP;

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);

    public static final WoodType ARCANE_WOOD_TYPE = WoodType.create(new ResourceLocation(MOD_ID, "arcane_wood").toString());

    //BLOCKS
    public static final RegistryObject<Block> ARCANE_GOLD_BLOCK = BLOCKS.register("arcane_gold_block", () -> new Block(AbstractBlock.Properties.from(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> ARCANE_GOLD_ORE = BLOCKS.register("arcane_gold_ore", () -> new Block(AbstractBlock.Properties.from(Blocks.GOLD_ORE)));
    public static final RegistryObject<Block> NETHER_ARCANE_GOLD_ORE = BLOCKS.register("nether_arcane_gold_ore", () -> new Block(AbstractBlock.Properties.from(Blocks.GOLD_ORE)));
    public static final RegistryObject<Block> ARCANUM_BLOCK = BLOCKS.register("arcanum_block", () -> new Block(AbstractBlock.Properties.from(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> ARCANUM_ORE = BLOCKS.register("arcanum_ore", () -> new Block(AbstractBlock.Properties.from(Blocks.DIAMOND_BLOCK)));

    public static final RegistryObject<Block> ARCANE_WOOD_LOG = BLOCKS.register("arcane_wood_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> ARCANE_WOOD = BLOCKS.register("arcane_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD_LOG = BLOCKS.register("stripped_arcane_wood_log", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD = BLOCKS.register("stripped_arcane_wood", () -> new RotatedPillarBlock(AbstractBlock.Properties.from(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistryObject<Block> ARCANE_WOOD_PLANKS = BLOCKS.register("arcane_wood_planks", () -> new Block(AbstractBlock.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_WOOD_STAIRS = BLOCKS.register("arcane_wood_stairs", () -> new StairsBlock(() -> ARCANE_WOOD_PLANKS.get().getDefaultState(),AbstractBlock.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_WOOD_SLAB = BLOCKS.register("arcane_wood_slab", () -> new SlabBlock(AbstractBlock.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_WOOD_FENCE = BLOCKS.register("arcane_wood_fence", () -> new FenceBlock(AbstractBlock.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_WOOD_FENCE_GATE = BLOCKS.register("arcane_wood_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.from(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_WOOD_DOOR = BLOCKS.register("arcane_wood_door", () -> new DoorBlock(AbstractBlock.Properties.from(Blocks.OAK_PLANKS).notSolid()));
    public static final RegistryObject<Block> ARCANE_WOOD_TRAPDOOR = BLOCKS.register("arcane_wood_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.from(Blocks.OAK_PLANKS).notSolid()));
    public static final RegistryObject<Block> ARCANE_WOOD_PRESSURE_PLATE = BLOCKS.register("arcane_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.from(Blocks.OAK_PLANKS).notSolid().doesNotBlockMovement()));
    public static final RegistryObject<Block> ARCANE_WOOD_BUTTON = BLOCKS.register("arcane_wood_button", () -> new WoodButtonBlock(AbstractBlock.Properties.from(Blocks.OAK_PLANKS).doesNotBlockMovement()));
    public static final RegistryObject<Block> ARCANE_WOOD_SIGN = BLOCKS.register("arcane_wood_sign", () -> new ArcaneWoodStandingSignBlock(AbstractBlock.Properties.from(Blocks.OAK_PLANKS).notSolid().doesNotBlockMovement(), ARCANE_WOOD_TYPE));
    public static final RegistryObject<Block> ARCANE_WOOD_WALL_SIGN = BLOCKS.register("arcane_wood_wall_sign", () -> new ArcaneWoodWallSignBlock(AbstractBlock.Properties.from(Blocks.OAK_PLANKS).notSolid().doesNotBlockMovement(), ARCANE_WOOD_TYPE));

    public static final RegistryObject<Block> ARCANE_PEDESTAL = BLOCKS.register("arcane_pedestal", () -> new ArcanePedestalBlock(AbstractBlock.Properties.from(Blocks.OAK_PLANKS)));

    //ITEMS
    public static final RegistryObject<Item> ARCANE_GOLD_INGOT = ITEMS.register("arcane_gold_ingot", () -> new Item(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_GOLD_NUGGET = ITEMS.register("arcane_gold_nugget", () -> new Item(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_GOLD_BLOCK_ITEM = ITEMS.register("arcane_gold_block", () -> new BlockItem(ARCANE_GOLD_BLOCK.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_GOLD_ORE_ITEM = ITEMS.register("arcane_gold_ore", () -> new BlockItem(ARCANE_GOLD_ORE.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> NETHER_ARCANE_GOLD_ORE_ITEM = ITEMS.register("nether_arcane_gold_ore", () -> new BlockItem(NETHER_ARCANE_GOLD_ORE.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));

    public static final RegistryObject<Item> ARCANE_GOLD_SWORD = ITEMS.register("arcane_gold_sword", () -> new SwordItem(ArcaneGoldTier.ARCANE_GOLD, 3, -2.4f, new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_GOLD_PICKAXE = ITEMS.register("arcane_gold_pickaxe", () -> new PickaxeItem(ArcaneGoldTier.ARCANE_GOLD, 1, -2.8f, new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_GOLD_AXE = ITEMS.register("arcane_gold_axe", () -> new AxeItem(ArcaneGoldTier.ARCANE_GOLD, 6, -3.1f, new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_GOLD_SHOVEL = ITEMS.register("arcane_gold_shovel", () -> new ShovelItem(ArcaneGoldTier.ARCANE_GOLD, 1.5f, -3f, new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_GOLD_HOE = ITEMS.register("arcane_gold_hoe", () -> new HoeItem(ArcaneGoldTier.ARCANE_GOLD, -2, -1f, new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_GOLD_SCYTHE = ITEMS.register("arcane_gold_scythe", () -> new SwordItem(ArcaneGoldTier.ARCANE_GOLD, 3, -2.4f, new Item.Properties().group(WIZARDS_REBORN_GROUP)));

    public static final RegistryObject<Item> ARCANUM = ITEMS.register("arcanum", () -> new Item(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANUM_DUST = ITEMS.register("arcanum_dust", () -> new ArcanumDustItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANUM_BLOCK_ITEM = ITEMS.register("arcanum_block", () -> new BlockItem(ARCANUM_BLOCK.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANUM_ORE_ITEM = ITEMS.register("arcanum_ore", () -> new BlockItem(ARCANUM_ORE.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));

    public static final RegistryObject<Item> ARCANE_WOOD_LOG_ITEM = ITEMS.register("arcane_wood_log", () -> new BlockItem(ARCANE_WOOD_LOG.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_WOOD_ITEM = ITEMS.register("arcane_wood", () -> new BlockItem(ARCANE_WOOD.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> STRIPPED_ARCANE_WOOD_LOG_ITEM = ITEMS.register("stripped_arcane_wood_log", () -> new BlockItem(STRIPPED_ARCANE_WOOD_LOG.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> STRIPPED_ARCANE_WOOD_ITEM = ITEMS.register("stripped_arcane_wood", () -> new BlockItem(STRIPPED_ARCANE_WOOD.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_ITEM = ITEMS.register("arcane_wood_planks", () -> new BlockItem(ARCANE_WOOD_PLANKS.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_STAIRS_ITEM = ITEMS.register("arcane_wood_stairs", () -> new BlockItem(ARCANE_WOOD_STAIRS.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_SLAB_ITEM = ITEMS.register("arcane_wood_slab", () -> new BlockItem(ARCANE_WOOD_SLAB.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_FENCE_ITEM = ITEMS.register("arcane_wood_fence", () -> new BlockItem(ARCANE_WOOD_FENCE.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_FENCE_GATE_ITEM = ITEMS.register("arcane_wood_fence_gate", () -> new BlockItem(ARCANE_WOOD_FENCE_GATE.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_DOOR_ITEM = ITEMS.register("arcane_wood_door", () -> new BlockItem(ARCANE_WOOD_DOOR.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_TRAPDOOR_ITEM = ITEMS.register("arcane_wood_trapdoor", () -> new BlockItem(ARCANE_WOOD_TRAPDOOR.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_PRESSURE_PLATE_ITEM = ITEMS.register("arcane_wood_pressure_plate", () -> new BlockItem(ARCANE_WOOD_PRESSURE_PLATE.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_BUTTON_ITEM = ITEMS.register("arcane_wood_button", () -> new BlockItem(ARCANE_WOOD_BUTTON.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ARCANE_WOOD_SIGN_ITEM = ITEMS.register("arcane_wood_sign", () -> new SignItem(new Item.Properties().maxStackSize(16).group(WIZARDS_REBORN_GROUP),ARCANE_WOOD_SIGN.get(), ARCANE_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> ARCANE_WOOD_BOAT_ITEM = ITEMS.register("arcane_wood_boat", () -> new CustomBoatItem(new Item.Properties().maxStackSize(1).group(WIZARDS_REBORN_GROUP), "arcane_wood"));
    public static final RegistryObject<Item> ARCANE_WOOD_BRANCH = ITEMS.register("arcane_wood_branch", () -> new Item(new Item.Properties().group(WIZARDS_REBORN_GROUP)));

    public static final RegistryObject<Item> ARCANE_WAND = ITEMS.register("arcane_wand", () -> new ArcaneWandItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));

    public static final RegistryObject<Item> EARTH_CRYSTAL = ITEMS.register("earth_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> WATER_CRYSTAL = ITEMS.register("water_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> AIR_CRYSTAL = ITEMS.register("air_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> FIRE_CRYSTAL = ITEMS.register("fire_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> VOID_CRYSTAL = ITEMS.register("void_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));

    public static final RegistryObject<Item> FACETED_EARTH_CRYSTAL = ITEMS.register("faceted_earth_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> FACETED_WATER_CRYSTAL = ITEMS.register("faceted_water_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> FACETED_AIR_CRYSTAL = ITEMS.register("faceted_air_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> FACETED_FIRE_CRYSTAL = ITEMS.register("faceted_fire_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> FACETED_VOID_CRYSTAL = ITEMS.register("faceted_void_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));

    public static final RegistryObject<Item> ADVANCED_EARTH_CRYSTAL = ITEMS.register("advanced_earth_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ADVANCED_WATER_CRYSTAL = ITEMS.register("advanced_water_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ADVANCED_AIR_CRYSTAL = ITEMS.register("advanced_air_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ADVANCED_FIRE_CRYSTAL = ITEMS.register("advanced_fire_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> ADVANCED_VOID_CRYSTAL = ITEMS.register("advanced_void_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));

    public static final RegistryObject<Item> MASTERFUL_EARTH_CRYSTAL = ITEMS.register("masterful_earth_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> MASTERFUL_WATER_CRYSTAL = ITEMS.register("masterful_water_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> MASTERFUL_AIR_CRYSTAL = ITEMS.register("masterful_air_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> MASTERFUL_FIRE_CRYSTAL = ITEMS.register("masterful_fire_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> MASTERFUL_VOID_CRYSTAL = ITEMS.register("masterful_void_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));

    public static final RegistryObject<Item> PURE_EARTH_CRYSTAL = ITEMS.register("pure_earth_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> PURE_WATER_CRYSTAL = ITEMS.register("pure_water_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> PURE_AIR_CRYSTAL = ITEMS.register("pure_air_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> PURE_FIRE_CRYSTAL = ITEMS.register("pure_fire_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));
    public static final RegistryObject<Item> PURE_VOID_CRYSTAL = ITEMS.register("pure_void_crystal", () -> new CrystalItem(new Item.Properties().group(WIZARDS_REBORN_GROUP)));

    public static final RegistryObject<Item> ARCANE_PEDESTAL_ITEM = ITEMS.register("arcane_pedestal", () -> new BlockItem(ARCANE_PEDESTAL.get(), new Item.Properties().group(WIZARDS_REBORN_GROUP)));

    //TILE_ENTITIES
    public static final RegistryObject<TileEntityType<ArcaneWoodSignTileEntity>> ARCANE_WOOD_SIGN_TILE_ENTITY = TILE_ENTITIES.register("arcane_wood_sign", () -> TileEntityType.Builder.create(ArcaneWoodSignTileEntity::new, ARCANE_WOOD_SIGN.get(), ARCANE_WOOD_WALL_SIGN.get()).build(null));

    public static RegistryObject<TileEntityType<ArcanePedestalTileEntity>> ARCANE_PEDESTAL_TILE_ENTITY = TILE_ENTITIES.register("arcane_pedestal", () -> TileEntityType.Builder.create(ArcanePedestalTileEntity::new, ARCANE_PEDESTAL.get()).build(null));

    //ENTITIES
    public static final RegistryObject<EntityType<CustomBoatEntity>> ARCANE_WOOD_BOAT = ENTITIES.register("arcane_wood_boat", () -> EntityType.Builder.<CustomBoatEntity>create(CustomBoatEntity::new, EntityClassification.MISC).size(1.375f, 0.5625f).build(new ResourceLocation(MOD_ID, "arcane_wood_boat").toString()));

    private static final String CATEGORY_KEY = "key.category."+MOD_ID+".general";
    public static final KeyBinding OPEN_WAND_SELECTION_KEY = new KeyBinding("key."+MOD_ID+".selection_hud", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY_KEY);

    //PARTICLES
    public static RegistryObject<WispParticleType> WISP_PARTICLE = PARTICLES.register("wisp", WispParticleType::new);
    public static RegistryObject<SparkleParticleType> SPARKLE_PARTICLE = PARTICLES.register("sparkle", SparkleParticleType::new);

    //RECIPES
    public static final RegistryObject<ArcanumDustTransmutationRecipe.Serializer> ARCANUM_DUST_TRANSMUTATION_SERIALIZER = RECIPES.register("arcanum_dust_transmutation", ArcanumDustTransmutationRecipe.Serializer::new);
    public static IRecipeType<ArcanumDustTransmutationRecipe> ARCANUM_DUST_TRANSMUTATION_RECIPE = new ArcanumDustTransmutationRecipe.ArcanumDustTransmutationRecipeType();

    public WizardsReborn() {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BELT.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.BODY.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.CHARM.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.HEAD.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID ,SlotTypeMessage.REGISTER_TYPE,
                () -> SlotTypePreset.RING.getMessageBuilder().size(2).build());

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
        TILE_ENTITIES.register(eventBus);
        ENTITIES.register(eventBus);
        PARTICLES.register(eventBus);
        RECIPES.register(eventBus);

        Registry.register(Registry.RECIPE_TYPE, ArcanumDustTransmutationRecipe.TYPE_ID, ARCANUM_DUST_TRANSMUTATION_RECIPE);

        setupWandCrystalsModels();

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        forgeBus.addListener(ClientTickHandler::clientTickEnd);
        forgeBus.addListener(WorldRenderHandler::onRenderWorldLast);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::processIMC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        PacketHandler.init();

        event.enqueueWork(() -> {
            AxeItem.BLOCK_STRIPPING_MAP = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.BLOCK_STRIPPING_MAP)
                    .put(ARCANE_WOOD_LOG.get(), STRIPPED_ARCANE_WOOD_LOG.get())
                    .put(ARCANE_WOOD.get(), STRIPPED_ARCANE_WOOD.get()).build();
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("wizards_reborn", "wizards_reborn", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    public static void setupWandCrystalsModels() {
        WandCrystalsModels.addCrystal(MOD_ID+":earth_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":water_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":air_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":fire_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":void_crystal");

        WandCrystalsModels.addCrystal(MOD_ID+":faceted_earth_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":faceted_water_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":faceted_air_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":faceted_fire_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":faceted_void_crystal");

        WandCrystalsModels.addCrystal(MOD_ID+":advanced_earth_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":advanced_water_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":advanced_air_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":advanced_fire_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":advanced_void_crystal");

        WandCrystalsModels.addCrystal(MOD_ID+":masterful_earth_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":masterful_water_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":masterful_air_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":masterful_fire_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":masterful_void_crystal");

        WandCrystalsModels.addCrystal(MOD_ID+":pure_earth_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":pure_water_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":pure_air_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":pure_fire_crystal");
        WandCrystalsModels.addCrystal(MOD_ID+":pure_void_crystal");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            LOGGER.info("HELLO from Register Block");
        }
        @SubscribeEvent
        public static void onRenderTypeSetup(FMLClientSetupEvent event) {
            Atlases.addWoodType(ARCANE_WOOD_TYPE);

            RenderTypeLookup.setRenderLayer(ARCANE_WOOD_DOOR.get(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(ARCANE_WOOD_TRAPDOOR.get(), RenderType.getCutout());

            ClientRegistry.bindTileEntityRenderer(ARCANE_WOOD_SIGN_TILE_ENTITY.get(), SignTileEntityRenderer::new);
            ClientRegistry.bindTileEntityRenderer(ARCANE_PEDESTAL_TILE_ENTITY.get(), ArcanePedestalTileEntityRenderer::new);

            RenderingRegistry.registerEntityRenderingHandler(ARCANE_WOOD_BOAT.get(), ArcaneWoodBoatModel::new);
        }

        @SubscribeEvent
        public static void onModelRegistryEvent(ModelRegistryEvent event) {
            for (String crystal : WandCrystalsModels.getCrystals()) {
                ModelLoader.addSpecialModel(WandCrystalsModels.getModelLocationCrystal(crystal));
            }
            for (String item : Item2DRenderer.HAND_MODEL_ITEMS) {
                ModelLoader.addSpecialModel(new ModelResourceLocation(MOD_ID+":" + item + "_in_hand", "inventory"));
            }
        }
        @SubscribeEvent
        public static void onModelBakeEvent(ModelBakeEvent event)
        {
            ResourceLocation wandResourceLocation =  new ModelResourceLocation("wizards_reborn:arcane_wand", "inventory");
            ModelManager manager = Minecraft.getInstance().getModelManager();
            IBakedModel existingModel = manager.getModel(wandResourceLocation);

            for (String crystal : WandCrystalsModels.getCrystals()) {
                IBakedModel model = manager.getModel(WandCrystalsModels.getModelLocationCrystal(crystal));
                WandCrystalsModels.addModelCrystals(crystal, model);
                model = new CustomFinalisedModel(existingModel, WandCrystalsModels.getModelCrystals(crystal));
                WandCrystalsModels.addModel(crystal, model);
            }
            CustomModel customModel = new CustomModel(existingModel, new WandModelOverrideList());
            event.getModelRegistry().put(wandResourceLocation, customModel);

            Item2DRenderer.onModelBakeEvent(event);
        }

        @SubscribeEvent
        public static void registerKeyBindings(final FMLClientSetupEvent event) {
            ClientRegistry.registerKeyBinding(OPEN_WAND_SELECTION_KEY);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void registerFactories(ParticleFactoryRegisterEvent event) {
            Minecraft.getInstance().particles.registerFactory(WISP_PARTICLE.get(), WispParticleType.Factory::new);
            Minecraft.getInstance().particles.registerFactory(SPARKLE_PARTICLE.get(), SparkleParticleType.Factory::new);
        }
    }
}
