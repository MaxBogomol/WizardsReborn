package mod.maxbogomol.wizards_reborn;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramRecipe;
import mod.maxbogomol.wizards_reborn.api.monogram.Monograms;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.event.ClientWorldEvent;
import mod.maxbogomol.wizards_reborn.client.gui.HUDEventHandler;
import mod.maxbogomol.wizards_reborn.client.gui.TooltipEventHandler;
import mod.maxbogomol.wizards_reborn.client.gui.container.ArcaneWorkbenchContainer;
import mod.maxbogomol.wizards_reborn.client.gui.screen.ArcaneWorkbenchScreen;
import mod.maxbogomol.wizards_reborn.client.model.curio.AmuletModel;
import mod.maxbogomol.wizards_reborn.client.model.curio.BeltModel;
import mod.maxbogomol.wizards_reborn.client.particle.ArcaneWoodLeafParticleType;
import mod.maxbogomol.wizards_reborn.client.particle.KarmaParticleType;
import mod.maxbogomol.wizards_reborn.client.particle.SparkleParticleType;
import mod.maxbogomol.wizards_reborn.client.particle.WispParticleType;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.client.render.curio.AmuletRenderer;
import mod.maxbogomol.wizards_reborn.client.render.curio.BeltRenderer;
import mod.maxbogomol.wizards_reborn.client.render.entity.ArcaneWoodBoatModel;
import mod.maxbogomol.wizards_reborn.client.render.entity.EmptyRenderer;
import mod.maxbogomol.wizards_reborn.client.render.item.*;
import mod.maxbogomol.wizards_reborn.client.render.tileentity.*;
import mod.maxbogomol.wizards_reborn.common.block.*;
import mod.maxbogomol.wizards_reborn.common.block.flammable.*;
import mod.maxbogomol.wizards_reborn.common.capability.IKnowledge;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.crystal.*;
import mod.maxbogomol.wizards_reborn.common.entity.CustomBoatEntity;
import mod.maxbogomol.wizards_reborn.common.entity.CustomChestBoatEntity;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.event.Events;
import mod.maxbogomol.wizards_reborn.common.item.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.CustomItemTier;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ScytheItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.ArcanumAmuletItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.ArcanumRingItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.LeatherBeltItem;
import mod.maxbogomol.wizards_reborn.common.itemgroup.WizardsRebornItemGroup;
import mod.maxbogomol.wizards_reborn.common.knowledge.RegisterKnowledges;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcaneWorkbenchRecipe;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcanumDustTransmutationRecipe;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenAltarRecipe;
import mod.maxbogomol.wizards_reborn.common.recipe.WissenCrystallizerRecipe;
import mod.maxbogomol.wizards_reborn.common.spell.*;
import mod.maxbogomol.wizards_reborn.common.tileentity.*;
import mod.maxbogomol.wizards_reborn.common.world.tree.ArcaneWoodTree;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.io.IOException;
import java.util.Map;

@Mod("wizards_reborn")
public class WizardsReborn
{
    public static final String MOD_ID = "wizards_reborn";

    private static final Logger LOGGER = LogManager.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MOD_ID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);
    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registries.BANNER_PATTERN, MOD_ID);

    public static final WoodType ARCANE_WOOD_TYPE = WoodType.register(new WoodType(new ResourceLocation(MOD_ID, "arcane_wood").toString(), BlockSetType.OAK));

    public static final TagKey<Item> ARCANE_WOOD_LOGS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "arcane_wood_logs"));
    public static final TagKey<Item> ARCANE_LUMOS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "arcane_lumos"));

    public static final TagKey<BannerPattern> VIOLENCE_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/violence"));
    public static final TagKey<BannerPattern> REPRODUCTION_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/reproduction"));
    public static final TagKey<BannerPattern> COOPERATION_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/cooperation"));
    public static final TagKey<BannerPattern> HUNGER_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/hunger"));
    public static final TagKey<BannerPattern> SURVIVAL_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/survival"));
    public static final TagKey<BannerPattern> ELEVATION_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/elevation"));

    //POLISHING_TYPES
    public static final PolishingType CRYSTAL_POLISHING_TYPE  = new CrystalPolishingType();
    public static final PolishingType FACETED_POLISHING_TYPE  = new FacetedPolishingType();
    public static final PolishingType ADVANCED_POLISHING_TYPE  = new AdvancedPolishingType();
    public static final PolishingType MASTERFUL_POLISHING_TYPE  = new MasterfulPolishingType();
    public static final PolishingType PURE_POLISHING_TYPE  = new PurePolishingType();

    //CRYSTAL_TYPES
    public static final CrystalType EARTH_CRYSTAL_TYPE  = new EarthCrystalType();
    public static final CrystalType WATER_CRYSTAL_TYPE  = new WaterCrystalType();
    public static final CrystalType AIR_CRYSTAL_TYPE  = new AirCrystalType();
    public static final CrystalType FIRE_CRYSTAL_TYPE  = new FireCrystalType();
    public static final CrystalType VOID_CRYSTAL_TYPE  = new VoidCrystalType();

    //MONOGRAMS
    public static Monogram TEST1_MONOGRAM = new Monogram(MOD_ID+":test1");
    public static Monogram TEST2_MONOGRAM = new Monogram(MOD_ID+":test2");
    public static Monogram TEST3_MONOGRAM = new Monogram(MOD_ID+":test3");
    public static Monogram TEST4_MONOGRAM = new Monogram(MOD_ID+":test4");
    public static Monogram TEST5_MONOGRAM = new Monogram(MOD_ID+":test5");
    public static Monogram TEST6_MONOGRAM = new Monogram(MOD_ID+":test6");
    public static Monogram TEST7_MONOGRAM = new Monogram(MOD_ID+":test7");
    public static Monogram TEST8_MONOGRAM = new Monogram(MOD_ID+":test8");
    public static Monogram TEST9_MONOGRAM = new Monogram(MOD_ID+":test9");
    public static Monogram TEST10_MONOGRAM = new Monogram(MOD_ID+":test10");
    public static Monogram TEST11_MONOGRAM = new Monogram(MOD_ID+":test11");
    public static Monogram TEST12_MONOGRAM = new Monogram(MOD_ID+":test12");
    public static Monogram TEST13_MONOGRAM = new Monogram(MOD_ID+":test13");
    public static Monogram TEST14_MONOGRAM = new Monogram(MOD_ID+":test14");
    public static Monogram TEST15_MONOGRAM = new Monogram(MOD_ID+":test15");
    public static Monogram TEST16_MONOGRAM = new Monogram(MOD_ID+":test16");
    public static Monogram TEST17_MONOGRAM = new Monogram(MOD_ID+":test17");
    public static Monogram TEST18_MONOGRAM = new Monogram(MOD_ID+":test18");
    public static Monogram TEST19_MONOGRAM = new Monogram(MOD_ID+":test19");
    public static Monogram TEST20_MONOGRAM = new Monogram(MOD_ID+":test20");

    //SPELLS
    public static Spell EARTH_PROJECTILE_SPELL = new EarthProjectileSpell(MOD_ID+":earth_projectile");
    public static Spell WATER_PROJECTILE_SPELL = new WaterProjectileSpell(MOD_ID+":water_projectile");
    public static Spell AIR_PROJECTILE_SPELL = new AirProjectileSpell(MOD_ID+":air_projectile");
    public static Spell FIRE_PROJECTILE_SPELL = new FireProjectileSpell(MOD_ID+":fire_projectile");
    public static Spell VOID_PROJECTILE_SPELL = new VoidProjectileSpell(MOD_ID+":void_projectile");

    //BLOCKS
    public static final RegistryObject<Block> ARCANE_GOLD_BLOCK = BLOCKS.register("arcane_gold_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> ARCANE_GOLD_ORE = BLOCKS.register("arcane_gold_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_ARCANE_GOLD_ORE = BLOCKS.register("deepslate_arcane_gold_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE)));
    public static final RegistryObject<Block> NETHER_ARCANE_GOLD_ORE = BLOCKS.register("nether_arcane_gold_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHER_GOLD_ORE)));
    public static final RegistryObject<Block> RAW_ARCANE_GOLD_BLOCK = BLOCKS.register("raw_arcane_gold_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK)));
    public static final RegistryObject<Block> ARCANUM_BLOCK = BLOCKS.register("arcanum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> ARCANUM_ORE = BLOCKS.register("arcanum_ore", () -> new ArcanumOreBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_ARCANUM_ORE = BLOCKS.register("deepslate_arcanum_ore", () -> new ArcanumOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE)));

    public static final RegistryObject<Block> ARCANE_WOOD_LOG = BLOCKS.register("arcane_wood_log", () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG), 5, 5));
    public static final RegistryObject<Block> ARCANE_WOOD = BLOCKS.register("arcane_wood", () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD), 5, 5));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD_LOG = BLOCKS.register("stripped_arcane_wood_log", () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG), 5, 5));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD = BLOCKS.register("stripped_arcane_wood", () -> new FlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD), 5, 5));
    public static final RegistryObject<Block> ARCANE_WOOD_PLANKS = BLOCKS.register("arcane_wood_planks", () -> new FlammableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS), 5, 20));
    public static final RegistryObject<Block> ARCANE_WOOD_STAIRS = BLOCKS.register("arcane_wood_stairs", () -> new FlammableStairsBlock(() -> ARCANE_WOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS),  5, 20));
    public static final RegistryObject<Block> ARCANE_WOOD_SLAB = BLOCKS.register("arcane_wood_slab", () -> new FlammableSlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS),  5, 20));
    public static final RegistryObject<Block> ARCANE_WOOD_FENCE = BLOCKS.register("arcane_wood_fence", () -> new FlammableFenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS),  5, 20));
    public static final RegistryObject<Block> ARCANE_WOOD_FENCE_GATE = BLOCKS.register("arcane_wood_fence_gate", () -> new FlammableFenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS), WoodType.OAK,  5, 20));
    public static final RegistryObject<Block> ARCANE_WOOD_DOOR = BLOCKS.register("arcane_wood_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> ARCANE_WOOD_TRAPDOOR = BLOCKS.register("arcane_wood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> ARCANE_WOOD_PRESSURE_PLATE = BLOCKS.register("arcane_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.AMETHYST), BlockSetType.OAK));
    public static final RegistryObject<Block> ARCANE_WOOD_BUTTON = BLOCKS.register("arcane_wood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(SoundType.WOOD), BlockSetType.OAK, 30, true));
    public static final RegistryObject<Block> ARCANE_WOOD_SIGN = BLOCKS.register("arcane_wood_sign", () -> new ArcaneWoodStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ARCANE_WOOD_TYPE));
    public static final RegistryObject<Block> ARCANE_WOOD_WALL_SIGN = BLOCKS.register("arcane_wood_wall_sign", () -> new ArcaneWoodWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ARCANE_WOOD_TYPE));
    public static final RegistryObject<Block> ARCANE_WOOD_HANGING_SIGN = BLOCKS.register("arcane_wood_hanging_sign", () -> new ArcaneWoodCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ARCANE_WOOD_TYPE));
    public static final RegistryObject<Block> ARCANE_WOOD_WALL_HANGING_SIGN = BLOCKS.register("arcane_wood_wall_hanging_sign", () -> new ArcaneWoodWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ARCANE_WOOD_TYPE));
    public static final RegistryObject<Block> ARCANE_WOOD_LEAVES = BLOCKS.register("arcane_wood_leaves", () -> new ArcaneWoodLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).lightLevel((state) -> 5), 30, 60));
    public static final RegistryObject<Block> ARCANE_WOOD_SAPLING = BLOCKS.register("arcane_wood_sapling", () -> new SaplingBlock(new ArcaneWoodTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> POTTED_ARCANE_WOOD_SAPLING = BLOCKS.register("potted_arcane_wood_sapling", () -> new FlowerPotBlock(ARCANE_WOOD_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));

    public static final RegistryObject<Block> ARCANE_LINEN = BLOCKS.register("arcane_linen", () -> new ArcaneLinenBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> ARCANE_LINEN_HAY = BLOCKS.register("arcane_linen_hay", () -> new FlammableHayBlock(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK), 60, 20));

    public static final RegistryObject<Block> MOR = BLOCKS.register("mor", () -> new MorBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM)));
    public static final RegistryObject<Block> POTTED_MOR = BLOCKS.register("potted_mor", () -> new FlowerPotBlock(MOR.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> MOR_BLOCK = BLOCKS.register("mor_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK)));
    public static final RegistryObject<Block> ELDER_MOR = BLOCKS.register("elder_mor", () -> new MorBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM)));
    public static final RegistryObject<Block> POTTED_ELDER_MOR = BLOCKS.register("potted_elder_mor", () -> new FlowerPotBlock(ELDER_MOR.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> ELDER_MOR_BLOCK = BLOCKS.register("elder_mor_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK)));

    public static final RegistryObject<Block> EARTH_CRYSTAL_SEED_BLOCK = BLOCKS.register("earth_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> WATER_CRYSTAL_SEED_BLOCK = BLOCKS.register("water_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> AIR_CRYSTAL_SEED_BLOCK = BLOCKS.register("air_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> FIRE_CRYSTAL_SEED_BLOCK = BLOCKS.register("fire_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> VOID_CRYSTAL_SEED_BLOCK = BLOCKS.register("void_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));

    public static final RegistryObject<Block> EARTH_CRYSTAL_GROWTH = BLOCKS.register("earth_crystal_growth", () -> new CrystalGrowthBlock(EARTH_CRYSTAL_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> WATER_CRYSTAL_GROWTH = BLOCKS.register("water_crystal_growth", () -> new CrystalGrowthBlock(WATER_CRYSTAL_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> AIR_CRYSTAL_GROWTH = BLOCKS.register("air_crystal_growth", () -> new CrystalGrowthBlock(AIR_CRYSTAL_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> FIRE_CRYSTAL_GROWTH = BLOCKS.register("fire_crystal_growth", () -> new CrystalGrowthBlock(FIRE_CRYSTAL_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> VOID_CRYSTAL_GROWTH = BLOCKS.register("void_crystal_growth", () -> new CrystalGrowthBlock(VOID_CRYSTAL_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));

    public static final RegistryObject<Block> EARTH_CRYSTAL_BLOCK = BLOCKS.register("earth_crystal", () -> new CrystalBlock(EARTH_CRYSTAL_TYPE, CRYSTAL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> WATER_CRYSTAL_BLOCK = BLOCKS.register("water_crystal", () -> new CrystalBlock(WATER_CRYSTAL_TYPE, CRYSTAL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> AIR_CRYSTAL_BLOCK = BLOCKS.register("air_crystal", () -> new CrystalBlock(AIR_CRYSTAL_TYPE, CRYSTAL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> FIRE_CRYSTAL_BLOCK = BLOCKS.register("fire_crystal", () -> new CrystalBlock(FIRE_CRYSTAL_TYPE, CRYSTAL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> VOID_CRYSTAL_BLOCK = BLOCKS.register("void_crystal", () -> new CrystalBlock(VOID_CRYSTAL_TYPE, CRYSTAL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));

    public static final RegistryObject<Block> FACETED_EARTH_CRYSTAL_BLOCK = BLOCKS.register("faceted_earth_crystal", () -> new CrystalBlock(EARTH_CRYSTAL_TYPE, FACETED_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> FACETED_WATER_CRYSTAL_BLOCK = BLOCKS.register("faceted_water_crystal", () -> new CrystalBlock(WATER_CRYSTAL_TYPE, FACETED_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> FACETED_AIR_CRYSTAL_BLOCK = BLOCKS.register("faceted_air_crystal", () -> new CrystalBlock(AIR_CRYSTAL_TYPE, FACETED_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> FACETED_FIRE_CRYSTAL_BLOCK = BLOCKS.register("faceted_fire_crystal", () -> new CrystalBlock(FIRE_CRYSTAL_TYPE, FACETED_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> FACETED_VOID_CRYSTAL_BLOCK = BLOCKS.register("faceted_void_crystal", () -> new CrystalBlock(VOID_CRYSTAL_TYPE, FACETED_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));

    public static final RegistryObject<Block> ADVANCED_EARTH_CRYSTAL_BLOCK = BLOCKS.register("advanced_earth_crystal", () -> new CrystalBlock(EARTH_CRYSTAL_TYPE, ADVANCED_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> ADVANCED_WATER_CRYSTAL_BLOCK = BLOCKS.register("advanced_water_crystal", () -> new CrystalBlock(WATER_CRYSTAL_TYPE, ADVANCED_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> ADVANCED_AIR_CRYSTAL_BLOCK = BLOCKS.register("advanced_air_crystal", () -> new CrystalBlock(AIR_CRYSTAL_TYPE, ADVANCED_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> ADVANCED_FIRE_CRYSTAL_BLOCK = BLOCKS.register("advanced_fire_crystal", () -> new CrystalBlock(FIRE_CRYSTAL_TYPE, ADVANCED_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> ADVANCED_VOID_CRYSTAL_BLOCK = BLOCKS.register("advanced_void_crystal", () -> new CrystalBlock(VOID_CRYSTAL_TYPE, ADVANCED_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));

    public static final RegistryObject<Block> MASTERFUL_EARTH_CRYSTAL_BLOCK = BLOCKS.register("masterful_earth_crystal", () -> new CrystalBlock(EARTH_CRYSTAL_TYPE, MASTERFUL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> MASTERFUL_WATER_CRYSTAL_BLOCK = BLOCKS.register("masterful_water_crystal", () -> new CrystalBlock(WATER_CRYSTAL_TYPE, MASTERFUL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> MASTERFUL_AIR_CRYSTAL_BLOCK = BLOCKS.register("masterful_air_crystal", () -> new CrystalBlock(AIR_CRYSTAL_TYPE, MASTERFUL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> MASTERFUL_FIRE_CRYSTAL_BLOCK = BLOCKS.register("masterful_fire_crystal", () -> new CrystalBlock(FIRE_CRYSTAL_TYPE, MASTERFUL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> MASTERFUL_VOID_CRYSTAL_BLOCK = BLOCKS.register("masterful_void_crystal", () -> new CrystalBlock(VOID_CRYSTAL_TYPE, MASTERFUL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));

    public static final RegistryObject<Block> PURE_EARTH_CRYSTAL_BLOCK = BLOCKS.register("pure_earth_crystal", () -> new CrystalBlock(EARTH_CRYSTAL_TYPE, PURE_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> PURE_WATER_CRYSTAL_BLOCK = BLOCKS.register("pure_water_crystal", () -> new CrystalBlock(WATER_CRYSTAL_TYPE, PURE_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> PURE_AIR_CRYSTAL_BLOCK = BLOCKS.register("pure_air_crystal", () -> new CrystalBlock(AIR_CRYSTAL_TYPE, PURE_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> PURE_FIRE_CRYSTAL_BLOCK = BLOCKS.register("pure_fire_crystal", () -> new CrystalBlock(FIRE_CRYSTAL_TYPE, PURE_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> PURE_VOID_CRYSTAL_BLOCK = BLOCKS.register("pure_void_crystal", () -> new CrystalBlock(VOID_CRYSTAL_TYPE, PURE_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));

    public static final RegistryObject<Block> ARCANE_PEDESTAL = BLOCKS.register("arcane_pedestal", () -> new ArcanePedestalBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> WISSEN_ALTAR = BLOCKS.register("wissen_altar", () -> new WissenAltarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> WISSEN_TRANSLATOR = BLOCKS.register("wissen_translator", () -> new WissenTranslatorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> WISSEN_CRYSTALLIZER = BLOCKS.register("wissen_crystallizer", () -> new WissenCrystallizerBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_WORKBENCH = BLOCKS.register("arcane_workbench", () -> new ArcaneWorkbenchBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    public static final RegistryObject<Block> WHITE_ARCANE_LUMOS = BLOCKS.register("white_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.WHITE, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> ORANGE_ARCANE_LUMOS = BLOCKS.register("orange_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.ORANGE, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> MAGENTA_ARCANE_LUMOS = BLOCKS.register("magenta_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.MAGENTA, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> LIGHT_BLUE_ARCANE_LUMOS = BLOCKS.register("light_blue_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.LIGHT_BLUE, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> YELLOW_ARCANE_LUMOS = BLOCKS.register("yellow_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.YELLOW, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> LIME_ARCANE_LUMOS = BLOCKS.register("lime_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.LIME, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> PINK_ARCANE_LUMOS = BLOCKS.register("pink_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.PINK, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> GRAY_ARCANE_LUMOS = BLOCKS.register("gray_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.GRAY, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> LIGHT_GRAY_ARCANE_LUMOS = BLOCKS.register("light_gray_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.LIGHT_GRAY, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> CYAN_ARCANE_LUMOS = BLOCKS.register("cyan_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.CYAN, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> PURPLE_ARCANE_LUMOS = BLOCKS.register("purple_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.PURPLE, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> BLUE_ARCANE_LUMOS = BLOCKS.register("blue_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.BLUE, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> BROWN_ARCANE_LUMOS = BLOCKS.register("brown_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.BROWN, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> GREEN_ARCANE_LUMOS = BLOCKS.register("green_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.GREEN, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> RED_ARCANE_LUMOS = BLOCKS.register("red_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.RED, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> BLACK_ARCANE_LUMOS = BLOCKS.register("black_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.BLACK, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> RAINBOW_ARCANE_LUMOS = BLOCKS.register("rainbow_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.RAINBOW, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> COSMIC_ARCANE_LUMOS = BLOCKS.register("cosmic_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.COSMIC, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));

    //ITEMS
    public static final RegistryObject<Item> ARCANE_GOLD_INGOT = ITEMS.register("arcane_gold_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_NUGGET = ITEMS.register("arcane_gold_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_ARCANE_GOLD = ITEMS.register("raw_arcane_gold", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_BLOCK_ITEM = ITEMS.register("arcane_gold_block", () -> new BlockItem(ARCANE_GOLD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_ORE_ITEM = ITEMS.register("arcane_gold_ore", () -> new BlockItem(ARCANE_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DEEPSLATE_ARCANE_GOLD_ORE_ITEM = ITEMS.register("deepslate_arcane_gold_ore", () -> new BlockItem(DEEPSLATE_ARCANE_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> NETHER_ARCANE_GOLD_ORE_ITEM = ITEMS.register("nether_arcane_gold_ore", () -> new BlockItem(NETHER_ARCANE_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAW_ARCANE_GOLD_BLOCK_ITEM = ITEMS.register("raw_arcane_gold_block", () -> new BlockItem(RAW_ARCANE_GOLD_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_GOLD_SWORD = ITEMS.register("arcane_gold_sword", () -> new SwordItem(CustomItemTier.ARCANE_GOLD, 3, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_PICKAXE = ITEMS.register("arcane_gold_pickaxe", () -> new PickaxeItem(CustomItemTier.ARCANE_GOLD, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_AXE = ITEMS.register("arcane_gold_axe", () -> new AxeItem(CustomItemTier.ARCANE_GOLD, 6, -3.1f, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_SHOVEL = ITEMS.register("arcane_gold_shovel", () -> new ShovelItem(CustomItemTier.ARCANE_GOLD, 1.5f, -3f, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_HOE = ITEMS.register("arcane_gold_hoe", () -> new HoeItem(CustomItemTier.ARCANE_GOLD, -2, -1f, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_SCYTHE = ITEMS.register("arcane_gold_scythe", () -> new ScytheItem(CustomItemTier.ARCANE_GOLD, 4, -2.8f, new Item.Properties(), 1));

    public static final RegistryObject<Item> ARCANUM = ITEMS.register("arcanum", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_DUST = ITEMS.register("arcanum_dust", () -> new ArcanumDustItem(new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_BLOCK_ITEM = ITEMS.register("arcanum_block", () -> new BlockItem(ARCANUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_ORE_ITEM = ITEMS.register("arcanum_ore", () -> new BlockItem(ARCANUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DEEPSLATE_ARCANUM_ORE_ITEM = ITEMS.register("deepslate_arcanum_ore", () -> new BlockItem(DEEPSLATE_ARCANUM_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_WOOD_LOG_ITEM = ITEMS.register("arcane_wood_log", () -> new BlockItem(ARCANE_WOOD_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_ITEM = ITEMS.register("arcane_wood", () -> new BlockItem(ARCANE_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_ARCANE_WOOD_LOG_ITEM = ITEMS.register("stripped_arcane_wood_log", () -> new BlockItem(STRIPPED_ARCANE_WOOD_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_ARCANE_WOOD_ITEM = ITEMS.register("stripped_arcane_wood", () -> new BlockItem(STRIPPED_ARCANE_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_ITEM = ITEMS.register("arcane_wood_planks", () -> new BlockItem(ARCANE_WOOD_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_STAIRS_ITEM = ITEMS.register("arcane_wood_stairs", () -> new BlockItem(ARCANE_WOOD_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_SLAB_ITEM = ITEMS.register("arcane_wood_slab", () -> new BlockItem(ARCANE_WOOD_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_FENCE_ITEM = ITEMS.register("arcane_wood_fence", () -> new BlockItem(ARCANE_WOOD_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_FENCE_GATE_ITEM = ITEMS.register("arcane_wood_fence_gate", () -> new BlockItem(ARCANE_WOOD_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_DOOR_ITEM = ITEMS.register("arcane_wood_door", () -> new BlockItem(ARCANE_WOOD_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_TRAPDOOR_ITEM = ITEMS.register("arcane_wood_trapdoor", () -> new BlockItem(ARCANE_WOOD_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_PRESSURE_PLATE_ITEM = ITEMS.register("arcane_wood_pressure_plate", () -> new BlockItem(ARCANE_WOOD_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_BUTTON_ITEM = ITEMS.register("arcane_wood_button", () -> new BlockItem(ARCANE_WOOD_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_SIGN_ITEM = ITEMS.register("arcane_wood_sign", () -> new SignItem(new Item.Properties().stacksTo(16),ARCANE_WOOD_SIGN.get(), ARCANE_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> ARCANE_WOOD_HANGING_SIGN_ITEM = ITEMS.register("arcane_wood_hanging_sign", () -> new HangingSignItem(ARCANE_WOOD_HANGING_SIGN.get(), ARCANE_WOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> ARCANE_WOOD_BOAT_ITEM = ITEMS.register("arcane_wood_boat", () -> new CustomBoatItem(false, CustomBoatEntity.Type.ARCANE_WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANE_WOOD_CHEST_BOAT_ITEM = ITEMS.register("arcane_wood_chest_boat", () -> new CustomBoatItem(true, CustomBoatEntity.Type.ARCANE_WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANE_WOOD_BRANCH = ITEMS.register("arcane_wood_branch", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_LEAVES_ITEM = ITEMS.register("arcane_wood_leaves", () -> new BlockItem(ARCANE_WOOD_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_SAPLING_ITEM = ITEMS.register("arcane_wood_sapling", () -> new BlockItem(ARCANE_WOOD_SAPLING.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_LINEN_SEEDS = ITEMS.register("arcane_linen_seeds", () -> new BlockItem(ARCANE_LINEN.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_LINEN_ITEM = ITEMS.register("arcane_linen", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_LINEN_HAY_ITEM = ITEMS.register("arcane_linen_hay", () -> new BlockItem(ARCANE_LINEN_HAY.get(), new Item.Properties()));

    public static final RegistryObject<Item> MOR_ITEM = ITEMS.register("mor", () -> new BlockItem(MOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> MOR_BLOCK_ITEM = ITEMS.register("mor_block", () -> new BlockItem(MOR_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ELDER_MOR_ITEM = ITEMS.register("elder_mor", () -> new BlockItem(ELDER_MOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ELDER_MOR_BLOCK_ITEM = ITEMS.register("elder_mor_block", () -> new BlockItem(ELDER_MOR_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_WAND = ITEMS.register("arcane_wand", () -> new ArcaneWandItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WISSEN_WAND = ITEMS.register("wissen_wand", () -> new WissenWandItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> EARTH_CRYSTAL_SEED = ITEMS.register("earth_crystal_seed", () -> new BlockItem(EARTH_CRYSTAL_SEED_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> WATER_CRYSTAL_SEED = ITEMS.register("water_crystal_seed", () -> new BlockItem(WATER_CRYSTAL_SEED_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> AIR_CRYSTAL_SEED = ITEMS.register("air_crystal_seed", () -> new BlockItem(AIR_CRYSTAL_SEED_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> FIRE_CRYSTAL_SEED = ITEMS.register("fire_crystal_seed", () -> new BlockItem(FIRE_CRYSTAL_SEED_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOID_CRYSTAL_SEED = ITEMS.register("void_crystal_seed", () -> new BlockItem(VOID_CRYSTAL_SEED_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> EARTH_CRYSTAL_GROWTH_ITEM = ITEMS.register("earth_crystal_growth", () -> new BlockItem(EARTH_CRYSTAL_GROWTH.get(), new Item.Properties()));
    public static final RegistryObject<Item> WATER_CRYSTAL_GROWTH_ITEM = ITEMS.register("water_crystal_growth", () -> new BlockItem(WATER_CRYSTAL_GROWTH.get(), new Item.Properties()));
    public static final RegistryObject<Item> AIR_CRYSTAL_GROWTH_ITEM = ITEMS.register("air_crystal_growth", () -> new BlockItem(AIR_CRYSTAL_GROWTH.get(), new Item.Properties()));
    public static final RegistryObject<Item> FIRE_CRYSTAL_GROWTH_ITEM = ITEMS.register("fire_crystal_growth", () -> new BlockItem(FIRE_CRYSTAL_GROWTH.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOID_CRYSTAL_GROWTH_ITEM = ITEMS.register("void_crystal_growth", () -> new BlockItem(VOID_CRYSTAL_GROWTH.get(), new Item.Properties()));

    public static final RegistryObject<Item> FRACTURED_EARTH_CRYSTAL = ITEMS.register("fractured_earth_crystal", () -> new FracturedCrystalItem(EARTH_CRYSTAL_TYPE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FRACTURED_WATER_CRYSTAL = ITEMS.register("fractured_water_crystal", () -> new FracturedCrystalItem(WATER_CRYSTAL_TYPE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FRACTURED_AIR_CRYSTAL = ITEMS.register("fractured_air_crystal", () -> new FracturedCrystalItem(AIR_CRYSTAL_TYPE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FRACTURED_FIRE_CRYSTAL = ITEMS.register("fractured_fire_crystal", () -> new FracturedCrystalItem(FIRE_CRYSTAL_TYPE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FRACTURED_VOID_CRYSTAL = ITEMS.register("fractured_void_crystal", () -> new FracturedCrystalItem(VOID_CRYSTAL_TYPE, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> EARTH_CRYSTAL = ITEMS.register("earth_crystal", () -> new CrystalItem(EARTH_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WATER_CRYSTAL = ITEMS.register("water_crystal", () -> new CrystalItem(WATER_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> AIR_CRYSTAL = ITEMS.register("air_crystal", () -> new CrystalItem(AIR_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FIRE_CRYSTAL = ITEMS.register("fire_crystal", () -> new CrystalItem(FIRE_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> VOID_CRYSTAL = ITEMS.register("void_crystal", () -> new CrystalItem(VOID_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FACETED_EARTH_CRYSTAL = ITEMS.register("faceted_earth_crystal", () -> new CrystalItem(FACETED_EARTH_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FACETED_WATER_CRYSTAL = ITEMS.register("faceted_water_crystal", () -> new CrystalItem(FACETED_WATER_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FACETED_AIR_CRYSTAL = ITEMS.register("faceted_air_crystal", () -> new CrystalItem(FACETED_AIR_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FACETED_FIRE_CRYSTAL = ITEMS.register("faceted_fire_crystal", () -> new CrystalItem(FACETED_FIRE_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FACETED_VOID_CRYSTAL = ITEMS.register("faceted_void_crystal", () -> new CrystalItem(FACETED_VOID_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ADVANCED_EARTH_CRYSTAL = ITEMS.register("advanced_earth_crystal", () -> new CrystalItem(ADVANCED_EARTH_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ADVANCED_WATER_CRYSTAL = ITEMS.register("advanced_water_crystal", () -> new CrystalItem(ADVANCED_WATER_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ADVANCED_AIR_CRYSTAL = ITEMS.register("advanced_air_crystal", () -> new CrystalItem(ADVANCED_AIR_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ADVANCED_FIRE_CRYSTAL = ITEMS.register("advanced_fire_crystal", () -> new CrystalItem(ADVANCED_FIRE_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ADVANCED_VOID_CRYSTAL = ITEMS.register("advanced_void_crystal", () -> new CrystalItem(ADVANCED_VOID_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MASTERFUL_EARTH_CRYSTAL = ITEMS.register("masterful_earth_crystal", () -> new CrystalItem(MASTERFUL_EARTH_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MASTERFUL_WATER_CRYSTAL = ITEMS.register("masterful_water_crystal", () -> new CrystalItem(MASTERFUL_WATER_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MASTERFUL_AIR_CRYSTAL = ITEMS.register("masterful_air_crystal", () -> new CrystalItem(MASTERFUL_AIR_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MASTERFUL_FIRE_CRYSTAL = ITEMS.register("masterful_fire_crystal", () -> new CrystalItem(MASTERFUL_FIRE_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MASTERFUL_VOID_CRYSTAL = ITEMS.register("masterful_void_crystal", () -> new CrystalItem(MASTERFUL_VOID_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PURE_EARTH_CRYSTAL = ITEMS.register("pure_earth_crystal", () -> new CrystalItem(PURE_EARTH_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PURE_WATER_CRYSTAL = ITEMS.register("pure_water_crystal", () -> new CrystalItem(PURE_WATER_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PURE_AIR_CRYSTAL = ITEMS.register("pure_air_crystal", () -> new CrystalItem(PURE_AIR_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PURE_FIRE_CRYSTAL = ITEMS.register("pure_fire_crystal", () -> new CrystalItem(PURE_FIRE_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PURE_VOID_CRYSTAL = ITEMS.register("pure_void_crystal", () -> new CrystalItem(PURE_VOID_CRYSTAL_BLOCK.get(), new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARCANE_PEDESTAL_ITEM = ITEMS.register("arcane_pedestal", () -> new BlockItem(ARCANE_PEDESTAL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_ALTAR_ITEM = ITEMS.register("wissen_altar", () -> new BlockItem(WISSEN_ALTAR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_TRANSLATOR_ITEM = ITEMS.register("wissen_translator", () -> new BlockItem(WISSEN_TRANSLATOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_CRYSTALLIZER_ITEM = ITEMS.register("wissen_crystallizer", () -> new BlockItem(WISSEN_CRYSTALLIZER.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WORKBENCH_ITEM = ITEMS.register("arcane_workbench", () -> new BlockItem(ARCANE_WORKBENCH.get(), new Item.Properties()));

    public static final RegistryObject<Item> WHITE_ARCANE_LUMOS_ITEM = ITEMS.register("white_arcane_lumos", () -> new BlockItem(WHITE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_ARCANE_LUMOS_ITEM = ITEMS.register("orange_arcane_lumos", () -> new BlockItem(ORANGE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAGENTA_ARCANE_LUMOS_ITEM = ITEMS.register("magenta_arcane_lumos", () -> new BlockItem(MAGENTA_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BLUE_ARCANE_LUMOS_ITEM = ITEMS.register("light_blue_arcane_lumos", () -> new BlockItem(LIGHT_BLUE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_ARCANE_LUMOS_ITEM = ITEMS.register("yellow_arcane_lumos", () -> new BlockItem(YELLOW_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIME_ARCANE_LUMOS_ITEM = ITEMS.register("lime_arcane_lumos", () -> new BlockItem(LIME_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_ARCANE_LUMOS_ITEM = ITEMS.register("pink_arcane_lumos", () -> new BlockItem(PINK_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_ARCANE_LUMOS_ITEM = ITEMS.register("gray_arcane_lumos", () -> new BlockItem(GRAY_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_GRAY_ARCANE_LUMOS_ITEM = ITEMS.register("light_gray_arcane_lumos", () -> new BlockItem(LIGHT_GRAY_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CYAN_ARCANE_LUMOS_ITEM = ITEMS.register("cyan_arcane_lumos", () -> new BlockItem(CYAN_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_ARCANE_LUMOS_ITEM = ITEMS.register("purple_arcane_lumos", () -> new BlockItem(PURPLE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_ARCANE_LUMOS_ITEM = ITEMS.register("blue_arcane_lumos", () -> new BlockItem(BLUE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BROWN_ARCANE_LUMOS_ITEM = ITEMS.register("brown_arcane_lumos", () -> new BlockItem(BROWN_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_ARCANE_LUMOS_ITEM = ITEMS.register("green_arcane_lumos", () -> new BlockItem(GREEN_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_ARCANE_LUMOS_ITEM = ITEMS.register("red_arcane_lumos", () -> new BlockItem(RED_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_ARCANE_LUMOS_ITEM = ITEMS.register("black_arcane_lumos", () -> new BlockItem(BLACK_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAINBOW_ARCANE_LUMOS_ITEM = ITEMS.register("rainbow_arcane_lumos", () -> new BlockItem(RAINBOW_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_ARCANE_LUMOS_ITEM = ITEMS.register("cosmic_arcane_lumos", () -> new BlockItem(COSMIC_ARCANE_LUMOS.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANUM_AMULET = ITEMS.register("arcanum_amulet", () -> new ArcanumAmuletItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANUM_RING = ITEMS.register("arcanum_ring", () -> new ArcanumRingItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LEATHER_BELT = ITEMS.register("leather_belt", () -> new LeatherBeltItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARCANEMICON = ITEMS.register("arcanemicon", () -> new ArcanemiconItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> VIOLENCE_BANNER_PATTERN_ITEM = ITEMS.register("violence_banner_pattern", () -> new BannerPatternItem(VIOLENCE_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> REPRODUCTION_BANNER_PATTERN_ITEM = ITEMS.register("reproduction_banner_pattern", () -> new BannerPatternItem(REPRODUCTION_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> COOPERATION_BANNER_PATTERN_ITEM = ITEMS.register("cooperation_banner_pattern", () -> new BannerPatternItem(COOPERATION_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> HUNGER_BANNER_PATTERN_ITEM = ITEMS.register("hunger_banner_pattern", () -> new BannerPatternItem(HUNGER_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SURVIVAL_BANNER_PATTERN_ITEM = ITEMS.register("survival_banner_pattern", () -> new BannerPatternItem(SURVIVAL_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ELEVATION_BANNER_PATTERN_ITEM = ITEMS.register("elevation_banner_pattern", () -> new BannerPatternItem(ELEVATION_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));

    //TILE_ENTITIES
    public static final RegistryObject<BlockEntityType<CustomSignTileEntity>> SIGN_TILE_ENTITY = TILE_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(CustomSignTileEntity::new, ARCANE_WOOD_SIGN.get(), ARCANE_WOOD_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomHangingSignTileEntity>> HANGING_SIGN_TILE_ENTITY = TILE_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(CustomHangingSignTileEntity::new, ARCANE_WOOD_HANGING_SIGN.get(), ARCANE_WOOD_WALL_HANGING_SIGN.get()).build(null));

    public static RegistryObject<BlockEntityType<CrystalTileEntity>> CRYSTAL_TILE_ENTITY = TILE_ENTITIES.register("crystal", () -> BlockEntityType.Builder.of(CrystalTileEntity::new,
            EARTH_CRYSTAL_BLOCK.get(), WATER_CRYSTAL_BLOCK.get(), AIR_CRYSTAL_BLOCK.get(), FIRE_CRYSTAL_BLOCK.get(), VOID_CRYSTAL_BLOCK.get(),
            FACETED_EARTH_CRYSTAL_BLOCK.get(), FACETED_WATER_CRYSTAL_BLOCK.get(), FACETED_AIR_CRYSTAL_BLOCK.get(), FACETED_FIRE_CRYSTAL_BLOCK.get(), FACETED_VOID_CRYSTAL_BLOCK.get(),
            ADVANCED_EARTH_CRYSTAL_BLOCK.get(), ADVANCED_WATER_CRYSTAL_BLOCK.get(), ADVANCED_AIR_CRYSTAL_BLOCK.get(), ADVANCED_FIRE_CRYSTAL_BLOCK.get(), ADVANCED_VOID_CRYSTAL_BLOCK.get(),
            MASTERFUL_EARTH_CRYSTAL_BLOCK.get(), MASTERFUL_WATER_CRYSTAL_BLOCK.get(), MASTERFUL_AIR_CRYSTAL_BLOCK.get(), MASTERFUL_FIRE_CRYSTAL_BLOCK.get(), MASTERFUL_VOID_CRYSTAL_BLOCK.get(),
            PURE_EARTH_CRYSTAL_BLOCK.get(), PURE_WATER_CRYSTAL_BLOCK.get(), PURE_AIR_CRYSTAL_BLOCK.get(), PURE_FIRE_CRYSTAL_BLOCK.get(), PURE_VOID_CRYSTAL_BLOCK.get()
            ).build(null));
    public static RegistryObject<BlockEntityType<CrystalGrowthTileEntity>> CRYSTAL_GROWTH_TILE_ENTITY = TILE_ENTITIES.register("crystal_growth", () -> BlockEntityType.Builder.of(CrystalGrowthTileEntity::new,
            EARTH_CRYSTAL_GROWTH.get(), WATER_CRYSTAL_GROWTH.get(), AIR_CRYSTAL_GROWTH.get(), FIRE_CRYSTAL_GROWTH.get(), VOID_CRYSTAL_GROWTH.get()
            ).build(null));

    public static RegistryObject<BlockEntityType<ArcanePedestalTileEntity>> ARCANE_PEDESTAL_TILE_ENTITY = TILE_ENTITIES.register("arcane_pedestal", () -> BlockEntityType.Builder.of(ArcanePedestalTileEntity::new, ARCANE_PEDESTAL.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenAltarTileEntity>> WISSEN_ALTAR_TILE_ENTITY = TILE_ENTITIES.register("wissen_altar", () -> BlockEntityType.Builder.of(WissenAltarTileEntity::new, WISSEN_ALTAR.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenTranslatorTileEntity>> WISSEN_TRANSLATOR_TILE_ENTITY = TILE_ENTITIES.register("wissen_translator", () -> BlockEntityType.Builder.of(WissenTranslatorTileEntity::new, WISSEN_TRANSLATOR.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenCrystallizerTileEntity>> WISSEN_CRYSTALLIZER_TILE_ENTITY = TILE_ENTITIES.register("wissen_crystallizer", () -> BlockEntityType.Builder.of(WissenCrystallizerTileEntity::new, WISSEN_CRYSTALLIZER.get()).build(null));
    public static RegistryObject<BlockEntityType<ArcaneWorkbenchTileEntity>> ARCANE_WORKBENCH_TILE_ENTITY = TILE_ENTITIES.register("arcane_workbench", () -> BlockEntityType.Builder.of(ArcaneWorkbenchTileEntity::new, ARCANE_WORKBENCH.get()).build(null));

    //ENTITIES
    public static final RegistryObject<EntityType<CustomBoatEntity>> BOAT = ENTITIES.register("boat", () -> EntityType.Builder.<CustomBoatEntity>of(CustomBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(MOD_ID, "arcane_wood_boat").toString()));
    public static final RegistryObject<EntityType<CustomChestBoatEntity>> CHEST_BOAT = ENTITIES.register("chest_boat", () -> EntityType.Builder.<CustomChestBoatEntity>of(CustomChestBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(MOD_ID, "arcane_wood_chest_boat").toString()));
    public static final RegistryObject<EntityType<SpellProjectileEntity>> SPELL_PROJECTILE = ENTITIES.register("spell_projectile", () -> EntityType.Builder.<SpellProjectileEntity>of(SpellProjectileEntity::new, MobCategory.MISC).sized(0.4f, 0.4f).build(new ResourceLocation(MOD_ID, "spell_projectile").toString()));

    private static final String CATEGORY_KEY = "key.category."+MOD_ID+".general";
    public static final KeyMapping OPEN_WAND_SELECTION_KEY = new KeyMapping("key."+MOD_ID+".selection_hud", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY_KEY);

    //PARTICLES
    public static RegistryObject<WispParticleType> WISP_PARTICLE = PARTICLES.register("wisp", WispParticleType::new);
    public static RegistryObject<SparkleParticleType> SPARKLE_PARTICLE = PARTICLES.register("sparkle", SparkleParticleType::new);
    public static RegistryObject<KarmaParticleType> KARMA_PARTICLE = PARTICLES.register("karma", KarmaParticleType::new);
    public static RegistryObject<ArcaneWoodLeafParticleType> ARCANE_WOOD_LEAF_PARTICLE = PARTICLES.register("arcane_wood_leaf", ArcaneWoodLeafParticleType::new);

    //RECIPES
    public static final RegistryObject<ArcanumDustTransmutationRecipe.Serializer> ARCANUM_DUST_TRANSMUTATION_SERIALIZER = RECIPE_SERIALIZERS.register("arcanum_dust_transmutation", ArcanumDustTransmutationRecipe.Serializer::new);
    public static RegistryObject<RecipeType<ArcanumDustTransmutationRecipe>> ARCANUM_DUST_TRANSMUTATION_RECIPE = RECIPES.register("arcanum_dust_transmutation", () -> RecipeType.simple(ArcanumDustTransmutationRecipe.TYPE_ID));

    public static final RegistryObject<WissenAltarRecipe.Serializer> WISSEN_ALTAR_SERIALIZER = RECIPE_SERIALIZERS.register("wissen_altar", WissenAltarRecipe.Serializer::new);
    public static RegistryObject<RecipeType<WissenAltarRecipe>> WISSEN_ALTAR_RECIPE = RECIPES.register("wissen_altar", () -> RecipeType.simple(WissenAltarRecipe.TYPE_ID));

    public static final RegistryObject<WissenCrystallizerRecipe.Serializer> WISSEN_CRYSTALLIZER_SERIALIZER = RECIPE_SERIALIZERS.register("wissen_crystallizer", WissenCrystallizerRecipe.Serializer::new);
    public static RegistryObject<RecipeType<WissenCrystallizerRecipe>> WISSEN_CRYSTALLIZER_RECIPE = RECIPES.register("wissen_crystallizer", () -> RecipeType.simple(WissenCrystallizerRecipe.TYPE_ID));

    public static final RegistryObject<ArcaneWorkbenchRecipe.Serializer> ARCANE_WORKBENCH_SERIALIZER = RECIPE_SERIALIZERS.register("arcane_workbench", ArcaneWorkbenchRecipe.Serializer::new);
    public static RegistryObject<RecipeType<ArcaneWorkbenchRecipe>> ARCANE_WORKBENCH_RECIPE = RECIPES.register("arcane_workbench", () -> RecipeType.simple(ArcaneWorkbenchRecipe.TYPE_ID));

    //CONTAINERS
    public static final RegistryObject<MenuType<ArcaneWorkbenchContainer>> ARCANE_WORKBENCH_CONTAINER
            = CONTAINERS.register("arcane_workbench_container",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new ArcaneWorkbenchContainer(windowId, world, pos, inv, inv.player);
            })));

    public static ModelLayerLocation BELT_LAYER = new ModelLayerLocation(new ResourceLocation(MOD_ID, "belt"), "main");
    public static ModelLayerLocation AMULET_LAYER = new ModelLayerLocation(new ResourceLocation(MOD_ID, "amulet"), "main");

    public static final RegistryObject<BannerPattern> VIOLENCE_BANNER_PATTERN = BANNER_PATTERNS.register("violence", () -> new BannerPattern("wrv"));
    public static final RegistryObject<BannerPattern> REPRODUCTION_BANNER_PATTERN = BANNER_PATTERNS.register("reproduction", () -> new BannerPattern("wrr"));
    public static final RegistryObject<BannerPattern> COOPERATION_BANNER_PATTERN = BANNER_PATTERNS.register("cooperation", () -> new BannerPattern("wrc"));
    public static final RegistryObject<BannerPattern> HUNGER_BANNER_PATTERN = BANNER_PATTERNS.register("hunger", () -> new BannerPattern("wrh"));
    public static final RegistryObject<BannerPattern> SURVIVAL_BANNER_PATTERN = BANNER_PATTERNS.register("survival", () -> new BannerPattern("wrs"));
    public static final RegistryObject<BannerPattern> ELEVATION_BANNER_PATTERN = BANNER_PATTERNS.register("elevation", () -> new BannerPattern("wre"));

    public WizardsReborn() {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BODY.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.RING.getMessageBuilder().size(2).build());

        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(eventBus);
        BLOCKS.register(eventBus);
        TILE_ENTITIES.register(eventBus);
        ENTITIES.register(eventBus);
        PARTICLES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPES.register(eventBus);
        CONTAINERS.register(eventBus);
        BANNER_PATTERNS.register(eventBus);

        setupMonograms();
        setupSpells();
        setupWandCrystalsModels();

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        forgeBus.addListener(ClientTickHandler::clientTickEnd);
        forgeBus.addListener(WorldRenderHandler::onRenderWorldLast);
        forgeBus.addListener(ClientWorldEvent::onTick);
        forgeBus.addListener(HUDEventHandler::onDrawScreenPost);
        forgeBus.addListener(TooltipEventHandler::onPostTooltipEvent);

        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);

        WizardsRebornItemGroup.register(eventBus);
        eventBus.addListener(WizardsRebornItemGroup::addCreative);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Events());
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        PacketHandler.init();
        ArcanemiconChapters.init();
        RegisterKnowledges.setupKnowledges();

        event.enqueueWork(() -> {
            AxeItem.STRIPPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPPABLES)
                    .put(ARCANE_WOOD_LOG.get(), STRIPPED_ARCANE_WOOD_LOG.get())
                    .put(ARCANE_WOOD.get(), STRIPPED_ARCANE_WOOD.get()).build();
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ARCANE_WORKBENCH_CONTAINER.get(), ArcaneWorkbenchScreen::new);

            CuriosRendererRegistry.register(LEATHER_BELT.get(), BeltRenderer::new);
            CuriosRendererRegistry.register(ARCANUM_AMULET.get(), AmuletRenderer::new);
        });
    }

    public static ShaderInstance GLOWING_PARTICLE_SHADER, SPRITE_PARTICLE_SHADER;

    public static ShaderInstance getGlowingParticleShader() { return GLOWING_PARTICLE_SHADER; }
    public static ShaderInstance getSpriteParticleShader() { return SPRITE_PARTICLE_SHADER; }

    public static void setupMonograms() {
        Monograms.register(TEST1_MONOGRAM);
        Monograms.register(TEST2_MONOGRAM);
        Monograms.register(TEST3_MONOGRAM);
        Monograms.register(TEST4_MONOGRAM);
        Monograms.register(TEST5_MONOGRAM);
        Monograms.register(TEST6_MONOGRAM);
        Monograms.register(TEST7_MONOGRAM);
        Monograms.register(TEST8_MONOGRAM);
        Monograms.register(TEST9_MONOGRAM);
        Monograms.register(TEST10_MONOGRAM);
        Monograms.register(TEST11_MONOGRAM);
        Monograms.register(TEST12_MONOGRAM);
        Monograms.register(TEST13_MONOGRAM);
        Monograms.register(TEST14_MONOGRAM);
        Monograms.register(TEST15_MONOGRAM);
        Monograms.register(TEST16_MONOGRAM);
        Monograms.register(TEST17_MONOGRAM);
        Monograms.register(TEST18_MONOGRAM);
        Monograms.register(TEST19_MONOGRAM);
        Monograms.register(TEST20_MONOGRAM);

        Monograms.addRecipe(new MonogramRecipe(TEST5_MONOGRAM, TEST1_MONOGRAM, TEST2_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST6_MONOGRAM, TEST1_MONOGRAM, TEST3_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST7_MONOGRAM, TEST4_MONOGRAM, TEST2_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST8_MONOGRAM, TEST4_MONOGRAM, TEST1_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST9_MONOGRAM, TEST3_MONOGRAM, TEST4_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST10_MONOGRAM, TEST3_MONOGRAM, TEST2_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST11_MONOGRAM, TEST6_MONOGRAM, TEST9_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST12_MONOGRAM, TEST6_MONOGRAM, TEST4_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST13_MONOGRAM, TEST10_MONOGRAM, TEST4_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST14_MONOGRAM, TEST2_MONOGRAM, TEST4_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST15_MONOGRAM, TEST9_MONOGRAM, TEST7_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST16_MONOGRAM, TEST2_MONOGRAM, TEST8_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST17_MONOGRAM, TEST10_MONOGRAM, TEST5_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST18_MONOGRAM, TEST2_MONOGRAM, TEST7_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST19_MONOGRAM, TEST3_MONOGRAM, TEST8_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEST20_MONOGRAM, TEST7_MONOGRAM, TEST6_MONOGRAM));
    }

    public static void setupSpells() {
        Spells.register(EARTH_PROJECTILE_SPELL);
        Spells.register(WATER_PROJECTILE_SPELL);
        Spells.register(AIR_PROJECTILE_SPELL);
        Spells.register(FIRE_PROJECTILE_SPELL);
        Spells.register(VOID_PROJECTILE_SPELL);
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

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onRenderTypeSetup(FMLClientSetupEvent event) {
            Sheets.addWoodType(ARCANE_WOOD_TYPE);

            ItemBlockRenderTypes.setRenderLayer(ARCANE_WOOD_DOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ARCANE_WOOD_TRAPDOOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ARCANE_WOOD_LEAVES.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ARCANE_WOOD_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(POTTED_ARCANE_WOOD_SAPLING.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ARCANE_LINEN.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(POTTED_MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(ELDER_MOR.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(POTTED_ELDER_MOR.get(), RenderType.cutout());

            BlockEntityRenderers.register(SIGN_TILE_ENTITY.get(), SignRenderer::new);
            BlockEntityRenderers.register(HANGING_SIGN_TILE_ENTITY.get(), HangingSignRenderer::new);
            BlockEntityRenderers.register(ARCANE_PEDESTAL_TILE_ENTITY.get(), (trd) -> new ArcanePedestalTileEntityRenderer());
            BlockEntityRenderers.register(WISSEN_ALTAR_TILE_ENTITY.get(), (trd) -> new WissenAltarTileEntityRenderer());
            BlockEntityRenderers.register(WISSEN_TRANSLATOR_TILE_ENTITY.get(), (trd) -> new WissenTranslatorTileEntityRenderer());
            BlockEntityRenderers.register(WISSEN_CRYSTALLIZER_TILE_ENTITY.get(), (trd) -> new WissenCrystallizerTileEntityRenderer());
            BlockEntityRenderers.register(ARCANE_WORKBENCH_TILE_ENTITY.get(), (trd) -> new ArcaneWorkbenchTileEntityRenderer());

            BlockEntityRenderers.register(CRYSTAL_TILE_ENTITY.get(), (trd) -> new CrystalTileEntityRenderer());

            EntityRenderers.register(BOAT.get(), m -> new ArcaneWoodBoatModel(m, false));
            EntityRenderers.register(CHEST_BOAT.get(), m -> new ArcaneWoodBoatModel(m, true));
            EntityRenderers.register(SPELL_PROJECTILE.get(), EmptyRenderer::new);
        }

        @SubscribeEvent
        public static void onModelRegistryEvent(ModelEvent.RegisterAdditional event) {
            for (String crystal : WandCrystalsModels.getCrystals()) {
                event.register(WandCrystalsModels.getModelLocationCrystal(crystal));
            }

            if (ClientConfig.LARGE_ITEM_MODEL.get()) {
                for (String item : Item2DRenderer.HAND_MODEL_ITEMS) {
                    event.register(new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, item + "_in_hand"), "inventory"));
                }
            }
        }

        @SubscribeEvent
        public static void onModelBakeEvent(ModelEvent.ModifyBakingResult event) {
            Map<ResourceLocation, BakedModel> map = event.getModels();
            BakedModel existingModel = map.get(new ModelResourceLocation(ARCANE_WAND.getId(), "inventory"));

            for (String crystal : WandCrystalsModels.getCrystals()) {
                BakedModel model = map.get(WandCrystalsModels.getModelLocationCrystal(crystal));
                WandCrystalsModels.addModelCrystals(crystal, model);
                model = new CustomFinalisedModel(existingModel, WandCrystalsModels.getModelCrystals(crystal));
                WandCrystalsModels.addModel(crystal, model);
            }
            CustomModel customModel = new CustomModel(existingModel, new WandModelOverrideList());
            map.replace(new ModelResourceLocation(ARCANE_WAND.getId(), "inventory"), customModel);

            if (ClientConfig.LARGE_ITEM_MODEL.get()) {
                Item2DRenderer.onModelBakeEvent(event);
            }
        }

        @SubscribeEvent
        public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
            event.register(OPEN_WAND_SELECTION_KEY);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void registerFactories(RegisterParticleProvidersEvent event) {
            Minecraft.getInstance().particleEngine.register(WISP_PARTICLE.get(), WispParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(SPARKLE_PARTICLE.get(), SparkleParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(KARMA_PARTICLE.get(), KarmaParticleType.Factory::new);
            Minecraft.getInstance().particleEngine.register(ARCANE_WOOD_LEAF_PARTICLE.get(), ArcaneWoodLeafParticleType.Factory::new);
        }

        @OnlyIn(Dist.CLIENT)
        @SubscribeEvent
        public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("wizards_reborn:glowing_particle"), DefaultVertexFormat.PARTICLE),
                    shader -> { GLOWING_PARTICLE_SHADER = shader; });
            event.registerShader(new ShaderInstance(event.getResourceProvider(), new ResourceLocation("wizards_reborn:sprite_particle"), DefaultVertexFormat.PARTICLE),
                    shader -> { SPRITE_PARTICLE_SHADER = shader; });
        }

        @SubscribeEvent
        public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            for(CustomBoatEntity.Type boatType : CustomBoatEntity.Type.values()) {
                event.registerLayerDefinition(ArcaneWoodBoatModel.createBoatModelName(boatType), BoatModel::createBodyModel);
                event.registerLayerDefinition(ArcaneWoodBoatModel.createChestBoatModelName(boatType), ChestBoatModel::createBodyModel);
            }
        }

        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(BELT_LAYER, BeltModel::createBodyLayer);
            event.registerLayerDefinition(AMULET_LAYER, AmuletModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void registerCaps(RegisterCapabilitiesEvent event) {
            event.register(IKnowledge.class);
        }
    }
}
