package mod.maxbogomol.wizards_reborn;

import com.google.common.collect.ImmutableMap;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantments;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalStat;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.Crystals;
import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRituals;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramRecipe;
import mod.maxbogomol.wizards_reborn.api.monogram.Monograms;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.client.event.ClientEvents;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.event.ClientWorldEvent;
import mod.maxbogomol.wizards_reborn.client.event.KeyBindHandler;
import mod.maxbogomol.wizards_reborn.client.gui.HUDEventHandler;
import mod.maxbogomol.wizards_reborn.client.gui.TooltipEventHandler;
import mod.maxbogomol.wizards_reborn.client.gui.container.*;
import mod.maxbogomol.wizards_reborn.client.gui.screen.*;
import mod.maxbogomol.wizards_reborn.client.particle.*;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.client.render.curio.AmuletRenderer;
import mod.maxbogomol.wizards_reborn.client.render.curio.BeltRenderer;
import mod.maxbogomol.wizards_reborn.client.render.curio.MushroomCapRenderer;
import mod.maxbogomol.wizards_reborn.client.render.item.WandCrystalsModels;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.RegisterAlchemyPotions;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.MagicBladeArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.WissenMendingArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.common.block.*;
import mod.maxbogomol.wizards_reborn.common.capability.IKnowledge;
import mod.maxbogomol.wizards_reborn.common.command.ArcaneEnchantmentArgument;
import mod.maxbogomol.wizards_reborn.common.command.KnowledgeArgument;
import mod.maxbogomol.wizards_reborn.common.command.SpellArgument;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.crystal.*;
import mod.maxbogomol.wizards_reborn.common.crystalritual.ArtificialFertilityCrystalRitual;
import mod.maxbogomol.wizards_reborn.common.crystalritual.CrystalGrowthAccelerationCrystalRitual;
import mod.maxbogomol.wizards_reborn.common.crystalritual.CrystalInfusionCrystalRitual;
import mod.maxbogomol.wizards_reborn.common.crystalritual.RitualBreedingCrystalRitual;
import mod.maxbogomol.wizards_reborn.common.effect.MorSporesEffect;
import mod.maxbogomol.wizards_reborn.common.effect.WissenAuraEffect;
import mod.maxbogomol.wizards_reborn.common.entity.CustomBoatEntity;
import mod.maxbogomol.wizards_reborn.common.entity.CustomChestBoatEntity;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.event.Events;
import mod.maxbogomol.wizards_reborn.common.fluid.CustomFluidType;
import mod.maxbogomol.wizards_reborn.common.integration.create.CreateIntegration;
import mod.maxbogomol.wizards_reborn.common.item.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.*;
import mod.maxbogomol.wizards_reborn.common.itemgroup.WizardsRebornItemGroup;
import mod.maxbogomol.wizards_reborn.common.knowledge.RegisterKnowledges;
import mod.maxbogomol.wizards_reborn.common.knowledge.Researches;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.proxy.ClientProxy;
import mod.maxbogomol.wizards_reborn.common.proxy.ISidedProxy;
import mod.maxbogomol.wizards_reborn.common.proxy.ServerProxy;
import mod.maxbogomol.wizards_reborn.common.recipe.*;
import mod.maxbogomol.wizards_reborn.common.spell.MagicSproutSpell;
import mod.maxbogomol.wizards_reborn.common.spell.charge.*;
import mod.maxbogomol.wizards_reborn.common.spell.projectile.*;
import mod.maxbogomol.wizards_reborn.common.spell.ray.*;
import mod.maxbogomol.wizards_reborn.common.spell.self.AirFlowSpell;
import mod.maxbogomol.wizards_reborn.common.spell.self.FireShieldSpell;
import mod.maxbogomol.wizards_reborn.common.spell.self.HeartOfNatureSpell;
import mod.maxbogomol.wizards_reborn.common.spell.self.WaterBreathingSpell;
import mod.maxbogomol.wizards_reborn.common.tileentity.*;
import mod.maxbogomol.wizards_reborn.common.world.tree.ArcaneWoodTree;
import mod.maxbogomol.wizards_reborn.common.world.tree.ArcaneWoodTrunkPlacer;
import mod.maxbogomol.wizards_reborn.common.world.tree.SupplierBlockStateProvider;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.food.FoodProperties;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.DistExecutor;
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
import org.joml.Vector3f;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.awt.*;

@Mod("wizards_reborn")
public class WizardsReborn {
    public static final String MOD_ID = "wizards_reborn";

    public static final ISidedProxy proxy = DistExecutor.unsafeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MOD_ID);
    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MOD_ID);
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MOD_ID);
    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registries.BANNER_PATTERN, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MOD_ID);
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARG_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, MOD_ID);
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MOD_ID);
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.createOptional(Registries.TRUNK_PLACER_TYPE, MOD_ID);
    public static final DeferredRegister<BlockStateProviderType<?>> BLOCK_STATE_PROVIDER_TYPE = DeferredRegister.createOptional(Registries.BLOCK_STATE_PROVIDER_TYPE, MOD_ID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MOD_ID);

    public static final WoodType ARCANE_WOOD_TYPE = WoodType.register(new WoodType(new ResourceLocation(MOD_ID, "arcane_wood").toString(), BlockSetType.OAK));
    public static final WoodType INNOCENT_WOOD_TYPE = WoodType.register(new WoodType(new ResourceLocation(MOD_ID, "innocent_wood").toString(), BlockSetType.CHERRY));

    public static final TagKey<Item> ARCANE_WOOD_LOGS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "arcane_wood_logs"));
    public static final TagKey<Item> ARCANE_LUMOS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "arcane_lumos"));
    public static final TagKey<Item> CRYSTALS_SEEDS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "crystal_seeds"));
    public static final TagKey<Item> FRACTURED_CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "fractured_crystals"));
    public static final TagKey<Item> CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "crystals"));
    public static final TagKey<Item> FACETED_CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "faceted_crystals"));
    public static final TagKey<Item> ADVANCED_CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "advanced_crystals"));
    public static final TagKey<Item> MASTERFUL_CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "masterful_crystals"));
    public static final TagKey<Item> PURE_CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "pure_crystals"));
    public static final TagKey<Item> ALL_CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "all_crystals"));

    public static final TagKey<Block> FLUID_PIPE_CONNECTION_BLOCK_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "fluid_pipe_connection"));
    public static final TagKey<Block> FLUID_PIPE_CONNECTION_TOGGLE_BLOCK_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "fluid_pipe_connection_toggle"));
    public static final TagKey<Block> STEAM_PIPE_CONNECTION_BLOCK_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "steam_pipe_connection"));
    public static final TagKey<Block> STEAM_PIPE_CONNECTION_TOGGLE_BLOCK_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "steam_pipe_connection_toggle"));
    public static final TagKey<Block> EXTRACTOR_LEAVER_CONNECTION_BLOCK_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "extractor_leaver_connection"));
    public static final TagKey<Block> ALTAR_OF_DROUGHT_TARGET_BLOCK_TAG  = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "altar_of_drought_target"));

    public static final TagKey<BannerPattern> VIOLENCE_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/violence"));
    public static final TagKey<BannerPattern> REPRODUCTION_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/reproduction"));
    public static final TagKey<BannerPattern> COOPERATION_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/cooperation"));
    public static final TagKey<BannerPattern> HUNGER_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/hunger"));
    public static final TagKey<BannerPattern> SURVIVAL_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/survival"));
    public static final TagKey<BannerPattern> ELEVATION_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/elevation"));

    public static final TagKey<DamageType> MAGIC_DAMAGE_TYPE_TAG = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MOD_ID, "magic"));

    public static final RegistryObject<SoundEvent> MUSIC_DISC_ARCANUM_SOUND = SOUND_EVENTS.register("arcanum_swinging", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanum_swinging")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_MOR_SOUND = SOUND_EVENTS.register("mor_marsh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mor_marsh")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_REBORN_SOUND = SOUND_EVENTS.register("reborn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "reborn")));

    //CRYSTAL_STATS
    public static CrystalStat FOCUS_CRYSTAL_STAT = new CrystalStat(MOD_ID+":focus", 3);
    public static CrystalStat BALANCE_CRYSTAL_STAT = new CrystalStat(MOD_ID+":balance", 3);
    public static CrystalStat ABSORPTION_CRYSTAL_STAT = new CrystalStat(MOD_ID+":absorption", 3);
    public static CrystalStat RESONANCE_CRYSTAL_STAT = new CrystalStat(MOD_ID+":resonance", 3);

    //POLISHING_TYPES
    public static final PolishingType CRYSTAL_POLISHING_TYPE  = new CrystalPolishingType(MOD_ID+":crystal");
    public static final PolishingType FACETED_POLISHING_TYPE  = new FacetedPolishingType(MOD_ID+":faceted");
    public static final PolishingType ADVANCED_POLISHING_TYPE  = new AdvancedPolishingType(MOD_ID+":advanced");
    public static final PolishingType MASTERFUL_POLISHING_TYPE  = new MasterfulPolishingType(MOD_ID+":masterful");
    public static final PolishingType PURE_POLISHING_TYPE  = new PurePolishingType(MOD_ID+":pure");

    //CRYSTAL_TYPES
    public static final CrystalType EARTH_CRYSTAL_TYPE  = new EarthCrystalType(MOD_ID+":earth");
    public static final CrystalType WATER_CRYSTAL_TYPE  = new WaterCrystalType(MOD_ID+":water");
    public static final CrystalType AIR_CRYSTAL_TYPE  = new AirCrystalType(MOD_ID+":air");
    public static final CrystalType FIRE_CRYSTAL_TYPE  = new FireCrystalType(MOD_ID+":fire");
    public static final CrystalType VOID_CRYSTAL_TYPE  = new VoidCrystalType(MOD_ID+":void");

    //MONOGRAMS
    public static Monogram LUNAM_MONOGRAM = new Monogram(MOD_ID+":lunam", new Color(177, 211, 251));
    public static Monogram VITA_MONOGRAM = new Monogram(MOD_ID+":vita", new Color(245, 77, 127));
    public static Monogram SOLEM_MONOGRAM = new Monogram(MOD_ID+":solem", new Color(245, 251, 123));
    public static Monogram MORS_MONOGRAM = new Monogram(MOD_ID+":mors", new Color(122, 103, 128));
    public static Monogram MIRACULUM_MONOGRAM = new Monogram(MOD_ID+":miraculum", new Color(67, 148, 114));
    public static Monogram TEMPUS_MONOGRAM = new Monogram(MOD_ID+":tempus", new Color(214, 152, 137));
    public static Monogram STATERA_MONOGRAM = new Monogram(MOD_ID+":statera", new Color(85, 132, 227));
    public static Monogram ECLIPSIS_MONOGRAM = new Monogram(MOD_ID+":eclipsis", new Color(229, 249, 179));
    public static Monogram SICCITAS_MONOGRAM = new Monogram(MOD_ID+":siccitas", new Color(139, 223, 133));
    public static Monogram SOLSTITIUM_MONOGRAM = new Monogram(MOD_ID+":solstitium", new Color(228, 188, 67));
    public static Monogram FAMES_MONOGRAM = new Monogram(MOD_ID+":fames", new Color(151, 103, 135));
    public static Monogram RENAISSANCE_MONOGRAM = new Monogram(MOD_ID+":renaissance", new Color(124, 236, 197));
    public static Monogram BELLUM_MONOGRAM = new Monogram(MOD_ID+":bellum", new Color(138, 64, 76));
    public static Monogram LUX_MONOGRAM = new Monogram(MOD_ID+":lux", new Color(215, 235, 192));
    public static Monogram KARA_MONOGRAM = new Monogram(MOD_ID+":kara", new Color(133, 56, 89));
    public static Monogram DEGRADATIO_MONOGRAM = new Monogram(MOD_ID+":degradatio", new Color(134, 130, 93));
    public static Monogram PRAEDICTIONEM_MONOGRAM = new Monogram(MOD_ID+":praedictionem", new Color(255, 142, 94));
    public static Monogram EVOLUTIONIS_MONOGRAM = new Monogram(MOD_ID+":evolutionis", new Color(208, 132, 214));
    public static Monogram TENEBRIS_MONOGRAM = new Monogram(MOD_ID+":tenebris", new Color(62, 77, 111));
    public static Monogram UNIVERSUM_MONOGRAM = new Monogram(MOD_ID+":universum", new Color(120, 14, 212));

    //SPELLS
    public static Spell EARTH_PROJECTILE_SPELL = new EarthProjectileSpell(MOD_ID+":earth_projectile", 5);
    public static Spell WATER_PROJECTILE_SPELL = new WaterProjectileSpell(MOD_ID+":water_projectile", 5);
    public static Spell AIR_PROJECTILE_SPELL = new AirProjectileSpell(MOD_ID+":air_projectile", 5);
    public static Spell FIRE_PROJECTILE_SPELL = new FireProjectileSpell(MOD_ID+":fire_projectile", 5);
    public static Spell VOID_PROJECTILE_SPELL = new VoidProjectileSpell(MOD_ID+":void_projectile", 5);
    public static Spell FROST_PROJECTILE_SPELL = new FrostProjectileSpell(MOD_ID+":frost_projectile", 5);
    public static Spell HOLY_PROJECTILE_SPELL = new HolyProjectileSpell(MOD_ID+":holy_projectile", 5);
    public static Spell CURSE_PROJECTILE_SPELL = new CurseProjectileSpell(MOD_ID+":curse_projectile", 5);
    public static Spell EARTH_RAY_SPELL = new EarthRaySpell(MOD_ID+":earth_ray", 7);
    public static Spell WATER_RAY_SPELL = new WaterRaySpell(MOD_ID+":water_ray", 7);
    public static Spell AIR_RAY_SPELL = new AirRaySpell(MOD_ID+":air_ray", 7);
    public static Spell FIRE_RAY_SPELL = new FireRaySpell(MOD_ID+":fire_ray", 7);
    public static Spell VOID_RAY_SPELL = new VoidRaySpell(MOD_ID+":void_ray", 7);
    public static Spell FROST_RAY_SPELL = new FrostRaySpell(MOD_ID+":frost_ray", 7);
    public static Spell HOLY_RAY_SPELL = new HolyRaySpell(MOD_ID+":holy_ray", 7);
    public static Spell CURSE_RAY_SPELL = new CurseRaySpell(MOD_ID+":curse_ray", 7);
    public static Spell EARTH_CHARGE_SPELL = new EarthChargeSpell(MOD_ID+":earth_charge", 10);
    public static Spell WATER_CHARGE_SPELL = new WaterChargeSpell(MOD_ID+":water_charge", 10);
    public static Spell AIR_CHARGE_SPELL = new AirChargeSpell(MOD_ID+":air_charge", 10);
    public static Spell FIRE_CHARGE_SPELL = new FireChargeSpell(MOD_ID+":fire_charge", 10);
    public static Spell VOID_CHARGE_SPELL = new VoidChargeSpell(MOD_ID+":void_charge", 10);
    public static Spell FROST_CHARGE_SPELL = new FrostChargeSpell(MOD_ID+":frost_charge", 10);
    public static Spell HOLY_CHARGE_SPELL = new HolyChargeSpell(MOD_ID+":holy_charge", 10);
    public static Spell CURSE_CHARGE_SPELL = new CurseChargeSpell(MOD_ID+":curse_charge", 10);
    public static Spell HEART_OF_NATURE_SPELL = new HeartOfNatureSpell(MOD_ID+":heart_of_nature", 15);
    public static Spell WATER_BREATHING_SPELL = new WaterBreathingSpell(MOD_ID+":water_breathing", 15);
    public static Spell AIR_FLOW_SPELL = new AirFlowSpell(MOD_ID+":air_flow", 15);
    public static Spell FIRE_SHIELD_SPELL = new FireShieldSpell(MOD_ID+":fire_shield", 15);
    public static Spell MAGIC_SPROUT_SPELL = new MagicSproutSpell(MOD_ID+":magic_sprout", 15);

    //ARCANE ENCHANTMENT
    public static ArcaneEnchantment WISSEN_MENDING_ARCANE_ENCHANTMENT = new WissenMendingArcaneEnchantment(MOD_ID+":wissen_mending", 3);
    public static ArcaneEnchantment MAGIC_BLADE_ARCANE_ENCHANTMENT = new MagicBladeArcaneEnchantment(MOD_ID+":magic_blade", 5);

    //CRYSTAL RITUALS
    public static CrystalRitual EMPTY_CRYSTAL_RITUAL = new CrystalRitual(MOD_ID+":empty");
    public static CrystalRitual ARTIFICIAL_FERTILITY_CRYSTAL_RITUAL = new ArtificialFertilityCrystalRitual(MOD_ID+":artificial_fertility");
    public static CrystalRitual RITUAL_BREEDING_CRYSTAL_RITUAL = new RitualBreedingCrystalRitual(MOD_ID+":ritual_breeding");
    public static CrystalRitual CRYSTAL_GROWTH_ACCELERATION_CRYSTAL_RITUAL = new CrystalGrowthAccelerationCrystalRitual(MOD_ID+":crystal_growth_acceleration");
    public static CrystalRitual CRYSTAL_INFUSION_CRYSTAL_RITUAL = new CrystalInfusionCrystalRitual(MOD_ID+":crystal_infusion");
    //public static CrystalRitual STONE_CALENDAR_CRYSTAL_RITUAL = new CrystalRitual(MOD_ID+":stone_calendar");

    public static final FoodProperties MOR_FOOD = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.6F).effect(new MobEffectInstance(MobEffects.POISON, 450, 0), 1.0F).effect(new MobEffectInstance(MobEffects.CONFUSION, 350, 0), 1.0F).effect(new MobEffectInstance(MobEffects.BLINDNESS, 250, 0), 1.0F).effect(new MobEffectInstance(MobEffects.WEAKNESS, 550, 1), 1.0F).build();

    //BLOCKS
    public static final RegistryObject<Block> ARCANE_GOLD_BLOCK = BLOCKS.register("arcane_gold_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK)));
    public static final RegistryObject<Block> ARCANE_GOLD_ORE = BLOCKS.register("arcane_gold_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_ARCANE_GOLD_ORE = BLOCKS.register("deepslate_arcane_gold_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE)));
    public static final RegistryObject<Block> NETHER_ARCANE_GOLD_ORE = BLOCKS.register("nether_arcane_gold_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_GOLD_ORE), UniformInt.of(0, 1)));
    public static final RegistryObject<Block> RAW_ARCANE_GOLD_BLOCK = BLOCKS.register("raw_arcane_gold_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK)));
    public static final RegistryObject<Block> ARCANUM_BLOCK = BLOCKS.register("arcanum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));
    public static final RegistryObject<Block> ARCANUM_ORE = BLOCKS.register("arcanum_ore", () -> new ArcanumOreBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE)));
    public static final RegistryObject<Block> DEEPSLATE_ARCANUM_ORE = BLOCKS.register("deepslate_arcanum_ore", () -> new ArcanumOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE)));
    public static final RegistryObject<Block> ARCACITE_BLOCK = BLOCKS.register("arcacite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK)));

    public static final RegistryObject<Block> ARCANE_WOOD_LOG = BLOCKS.register("arcane_wood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> ARCANE_WOOD = BLOCKS.register("arcane_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD_LOG = BLOCKS.register("stripped_arcane_wood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD = BLOCKS.register("stripped_arcane_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistryObject<Block> ARCANE_WOOD_PLANKS = BLOCKS.register("arcane_wood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_WOOD_STAIRS = BLOCKS.register("arcane_wood_stairs", () -> new StairBlock(() -> ARCANE_WOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_WOOD_SLAB = BLOCKS.register("arcane_wood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_WOOD_FENCE = BLOCKS.register("arcane_wood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_WOOD_FENCE_GATE = BLOCKS.register("arcane_wood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS), WoodType.OAK));
    public static final RegistryObject<Block> ARCANE_WOOD_DOOR = BLOCKS.register("arcane_wood_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> ARCANE_WOOD_TRAPDOOR = BLOCKS.register("arcane_wood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion(), BlockSetType.OAK));
    public static final RegistryObject<Block> ARCANE_WOOD_PRESSURE_PLATE = BLOCKS.register("arcane_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), BlockSetType.OAK));
    public static final RegistryObject<Block> ARCANE_WOOD_BUTTON = BLOCKS.register("arcane_wood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(SoundType.WOOD), BlockSetType.OAK, 30, true));
    public static final RegistryObject<Block> ARCANE_WOOD_SIGN = BLOCKS.register("arcane_wood_sign", () -> new CustomStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ARCANE_WOOD_TYPE));
    public static final RegistryObject<Block> ARCANE_WOOD_WALL_SIGN = BLOCKS.register("arcane_wood_wall_sign", () -> new CustomWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ARCANE_WOOD_TYPE));
    public static final RegistryObject<Block> ARCANE_WOOD_HANGING_SIGN = BLOCKS.register("arcane_wood_hanging_sign", () -> new CustomCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ARCANE_WOOD_TYPE));
    public static final RegistryObject<Block> ARCANE_WOOD_WALL_HANGING_SIGN = BLOCKS.register("arcane_wood_wall_hanging_sign", () -> new CustomWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion().noCollission(), ARCANE_WOOD_TYPE));
    public static final RegistryObject<Block> ARCANE_WOOD_LEAVES = BLOCKS.register("arcane_wood_leaves", () -> new ArcaneWoodLeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES).lightLevel((state) -> 5)));
    public static final RegistryObject<Block> ARCANE_WOOD_SAPLING = BLOCKS.register("arcane_wood_sapling", () -> new SaplingBlock(new ArcaneWoodTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> POTTED_ARCANE_WOOD_SAPLING = BLOCKS.register("potted_arcane_wood_sapling", () -> new FlowerPotBlock(ARCANE_WOOD_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));

    public static final RegistryObject<Block> INNOCENT_WOOD_LOG = BLOCKS.register("innocent_wood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_WOOD)));
    public static final RegistryObject<Block> INNOCENT_WOOD = BLOCKS.register("innocent_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_WOOD)));
    public static final RegistryObject<Block> STRIPPED_INNOCENT_WOOD_LOG = BLOCKS.register("stripped_innocent_wood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_CHERRY_LOG)));
    public static final RegistryObject<Block> STRIPPED_INNOCENT_WOOD = BLOCKS.register("stripped_innocent_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_CHERRY_WOOD)));
    public static final RegistryObject<Block> INNOCENT_WOOD_PLANKS = BLOCKS.register("innocent_wood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS)));
    public static final RegistryObject<Block> INNOCENT_WOOD_STAIRS = BLOCKS.register("innocent_wood_stairs", () -> new StairBlock(() -> INNOCENT_WOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS)));
    public static final RegistryObject<Block> INNOCENT_WOOD_SLAB = BLOCKS.register("innocent_wood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS)));
    public static final RegistryObject<Block> INNOCENT_WOOD_FENCE = BLOCKS.register("innocent_wood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS)));
    public static final RegistryObject<Block> INNOCENT_WOOD_FENCE_GATE = BLOCKS.register("innocent_wood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS), WoodType.CHERRY));
    public static final RegistryObject<Block> INNOCENT_WOOD_DOOR = BLOCKS.register("innocent_wood_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion(), BlockSetType.CHERRY));
    public static final RegistryObject<Block> INNOCENT_WOOD_TRAPDOOR = BLOCKS.register("innocent_wood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion(), BlockSetType.CHERRY));
    public static final RegistryObject<Block> INNOCENT_WOOD_PRESSURE_PLATE = BLOCKS.register("innocent_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion().noCollission(), BlockSetType.OAK));
    public static final RegistryObject<Block> INNOCENT_WOOD_BUTTON = BLOCKS.register("innocent_wood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_BUTTON).sound(SoundType.WOOD), BlockSetType.CHERRY, 30, true));
    public static final RegistryObject<Block> INNOCENT_WOOD_SIGN = BLOCKS.register("innocent_wood_sign", () -> new CustomStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion().noCollission(), INNOCENT_WOOD_TYPE));
    public static final RegistryObject<Block> INNOCENT_WOOD_WALL_SIGN = BLOCKS.register("innocent_wood_wall_sign", () -> new CustomWallSignBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion().noCollission(), INNOCENT_WOOD_TYPE));
    public static final RegistryObject<Block> INNOCENT_WOOD_HANGING_SIGN = BLOCKS.register("innocent_wood_hanging_sign", () -> new CustomCeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion().noCollission(), INNOCENT_WOOD_TYPE));
    public static final RegistryObject<Block> INNOCENT_WOOD_WALL_HANGING_SIGN = BLOCKS.register("innocent_wood_wall_hanging_sign", () -> new CustomWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion().noCollission(), INNOCENT_WOOD_TYPE));
    public static final RegistryObject<Block> INNOCENT_WOOD_LEAVES = BLOCKS.register("innocent_wood_leaves", () -> new InnocentWoodLeavesBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_LEAVES)));
    public static final RegistryObject<Block> INNOCENT_WOOD_SAPLING = BLOCKS.register("innocent_wood_sapling", () -> new SaplingBlock(new ArcaneWoodTree(), BlockBehaviour.Properties.copy(Blocks.CHERRY_SAPLING)));
    public static final RegistryObject<Block> POTTED_INNOCENT_WOOD_SAPLING = BLOCKS.register("potted_innocent_wood_sapling", () -> new FlowerPotBlock(INNOCENT_WOOD_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> PETALS_OF_INNOCENCE = BLOCKS.register("petals_of_innocence", () -> new PetalsOfInnocenceBlock(BlockBehaviour.Properties.copy(Blocks.PINK_PETALS)));
    public static final RegistryObject<Block> POTTED_PETALS_OF_INNOCENCE = BLOCKS.register("potted_petals_of_innocence", () -> new FlowerPotBlock(PETALS_OF_INNOCENCE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> POTTED_PINK_PETALS = BLOCKS.register("potted_pink_petals", () -> new FlowerPotBlock(Blocks.PINK_PETALS, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));

    public static final RegistryObject<Block> WISESTONE = BLOCKS.register("wisestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_STAIRS = BLOCKS.register("wisestone_stairs", () -> new StairBlock(() -> WISESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_SLAB = BLOCKS.register("wisestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_WALL = BLOCKS.register("wisestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> POLISHED_WISESTONE = BLOCKS.register("polished_wisestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> POLISHED_WISESTONE_STAIRS = BLOCKS.register("polished_wisestone_stairs", () -> new StairBlock(() -> POLISHED_WISESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> POLISHED_WISESTONE_SLAB = BLOCKS.register("polished_wisestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> POLISHED_WISESTONE_WALL = BLOCKS.register("polished_wisestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_BRICKS = BLOCKS.register("wisestone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_BRICKS_STAIRS = BLOCKS.register("wisestone_bricks_stairs", () -> new StairBlock(() -> WISESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_BRICKS_SLAB = BLOCKS.register("wisestone_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_BRICKS_WALL = BLOCKS.register("wisestone_bricks_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_TILE = BLOCKS.register("wisestone_tile", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_TILE_STAIRS = BLOCKS.register("wisestone_tile_stairs", () -> new StairBlock(() -> WISESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_TILE_SLAB = BLOCKS.register("wisestone_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_TILE_WALL = BLOCKS.register("wisestone_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> CHISELED_WISESTONE = BLOCKS.register("chiseled_wisestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> CHISELED_WISESTONE_STAIRS = BLOCKS.register("chiseled_wisestone_stairs", () -> new StairBlock(() -> POLISHED_WISESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> CHISELED_WISESTONE_SLAB = BLOCKS.register("chiseled_wisestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> CHISELED_WISESTONE_WALL = BLOCKS.register("chiseled_wisestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_PILLAR = BLOCKS.register("wisestone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> POLISHED_WISESTONE_PRESSURE_PLATE = BLOCKS.register("polished_wisestone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).noOcclusion().noCollission(), BlockSetType.POLISHED_BLACKSTONE));
    public static final RegistryObject<Block> POLISHED_WISESTONE_BUTTON = BLOCKS.register("polished_wisestone_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BUTTON).sound(SoundType.POLISHED_DEEPSLATE), BlockSetType.POLISHED_BLACKSTONE, 20, false));

    public static final RegistryObject<Block> ARCANE_LINEN = BLOCKS.register("arcane_linen", () -> new ArcaneLinenBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> ARCANE_LINEN_HAY = BLOCKS.register("arcane_linen_hay", () -> new HayBlock(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));

    public static final RegistryObject<Block> MOR = BLOCKS.register("mor", () -> new MorBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM)));
    public static final RegistryObject<Block> POTTED_MOR = BLOCKS.register("potted_mor", () -> new FlowerPotBlock(MOR.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> MOR_BLOCK = BLOCKS.register("mor_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK)));
    public static final RegistryObject<Block> ELDER_MOR = BLOCKS.register("elder_mor", () -> new MorBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM)));
    public static final RegistryObject<Block> POTTED_ELDER_MOR = BLOCKS.register("potted_elder_mor", () -> new FlowerPotBlock(ELDER_MOR.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> ELDER_MOR_BLOCK = BLOCKS.register("elder_mor_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK)));

    public static final RegistryObject<Block> ARCANUM_SEED_BLOCK = BLOCKS.register("arcanum_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));
    public static final RegistryObject<Block> ARCANUM_GROWTH = BLOCKS.register("arcanum_growth", () -> new ArcanumGrowthBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER)));

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

    public static final RegistryObject<Block> ARCANE_PEDESTAL = BLOCKS.register("arcane_pedestal", () -> new ArcanePedestalBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> HOVERING_TOME_STAND = BLOCKS.register("hovering_tome_stand", () -> new HoveringTomeStandBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> WISSEN_ALTAR = BLOCKS.register("wissen_altar", () -> new WissenAltarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> WISSEN_TRANSLATOR = BLOCKS.register("wissen_translator", () -> new WissenTranslatorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> WISSEN_CRYSTALLIZER = BLOCKS.register("wissen_crystallizer", () -> new WissenCrystallizerBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_WORKBENCH = BLOCKS.register("arcane_workbench", () -> new ArcaneWorkbenchBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> WISSEN_CELL = BLOCKS.register("wissen_cell", () -> new WissenCellBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> JEWELER_TABLE = BLOCKS.register("jeweler_table", () -> new JewelerTableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ALTAR_OF_DROUGHT = BLOCKS.register("altar_of_drought", () -> new AltarOfDroughtBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> TOTEM_BASE = BLOCKS.register("totem_base", () -> new TotemBaseBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> TOTEM_OF_FLAMES = BLOCKS.register("totem_of_flames", () -> new TotemOfFlamesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).lightLevel(TotemOfFlamesBlock::getLightLevel)));
    public static final RegistryObject<Block> EXPERIENCE_TOTEM = BLOCKS.register("experience_totem", () -> new ExperienceTotemBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> TOTEM_OF_EXPERIENCE_ABSORPTION = BLOCKS.register("totem_of_experience_absorption", () -> new TotemOfExperienceAbsorptionBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> TOTEM_OF_DISENCHANT = BLOCKS.register("totem_of_disenchant", () -> new TotemOfExperienceAbsorptionBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ARCANE_ITERATOR = BLOCKS.register("arcane_iterator", () -> new ArcaneIteratorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    public static final RegistryObject<Block> WISESTONE_PEDESTAL = BLOCKS.register("wisestone_pedestal", () -> new ArcanePedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> WISESTONE_HOVERING_TOME_STAND = BLOCKS.register("wisestone_hovering_tome_stand", () -> new HoveringTomeStandBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> FLUID_PIPE = BLOCKS.register("fluid_pipe", () -> new FluidPipeBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> FLUID_EXTRACTOR = BLOCKS.register("fluid_extractor", () -> new FluidExtractorBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> STEAM_PIPE = BLOCKS.register("steam_pipe", () -> new SteamPipeBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> STEAM_EXTRACTOR = BLOCKS.register("steam_extractor", () -> new SteamExtractorBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> ALCHEMY_FURNACE = BLOCKS.register("alchemy_furnace", () -> new AlchemyFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 13 : 0)));
    public static final RegistryObject<Block> ORBITAL_FLUID_RETAINER = BLOCKS.register("orbital_fluid_retainer", () -> new OrbitalFluidRetainerBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> STEAM_THERMAL_STORAGE = BLOCKS.register("steam_thermal_storage", () -> new SteamThermalStorageBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> ALCHEMY_MACHINE = BLOCKS.register("alchemy_machine", () -> new AlchemyMachineBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> ALCHEMY_BOILER = BLOCKS.register("alchemy_boiler", () -> new AlchemyBoilerBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> ARCANE_CENSER = BLOCKS.register("arcane_censer", () -> new ArcaneCenserBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> INNOCENT_PEDESTAL = BLOCKS.register("innocent_pedestal", () -> new ArcanePedestalBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS)));
    public static final RegistryObject<Block> INNOCENT_HOVERING_TOME_STAND = BLOCKS.register("innocent_hovering_tome_stand", () -> new HoveringTomeStandBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS)));

    public static final RegistryObject<Block> LIGHT_EMITTER = BLOCKS.register("light_emitter", () -> new LightEmitterBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> LIGHT_TRANSFER_LENS = BLOCKS.register("light_transfer_lens", () -> new LightTransferLensBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> RUNIC_PEDESTAL = BLOCKS.register("runic_pedestal", () -> new RunicPedestalBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));

    public static final RegistryObject<Block> ARCANE_LEVER = BLOCKS.register("arcane_lever", () -> new WaterloggableLeverBlock(BlockBehaviour.Properties.copy(Blocks.LEVER)));
    public static final RegistryObject<Block> REDSTONE_SENSOR = BLOCKS.register("redstone_sensor", () -> new RedstoneSensorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> WISSEN_SENSOR = BLOCKS.register("wissen_sensor", () -> new WissenSensorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> COOLDOWN_SENSOR = BLOCKS.register("cooldown_sensor", () -> new CooldownSensorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> LIGHT_SENSOR = BLOCKS.register("light_sensor", () -> new LightSensorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> EXPERIENCE_SENSOR = BLOCKS.register("experience_sensor", () -> new ExperienceSensorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> HEAT_SENSOR = BLOCKS.register("heat_sensor", () -> new HeatSensorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> FLUID_SENSOR = BLOCKS.register("fluid_sensor", () -> new FluidSensorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> STEAM_SENSOR = BLOCKS.register("steam_sensor", () -> new SteamSensorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> WISSEN_ACTIVATOR = BLOCKS.register("wissen_activator", () -> new WissenActivatorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> ITEM_SORTER = BLOCKS.register("item_sorter", () -> new ItemSorterBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));

    public static final RegistryObject<Block> ARCANE_WOOD_FRAME = BLOCKS.register("arcane_wood_frame", () -> new FrameBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_GLASS_FRAME = BLOCKS.register("arcane_wood_glass_frame", () -> new GlassFrameBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_CASING = BLOCKS.register("arcane_wood_casing", () -> new CasingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_WISSEN_CASING = BLOCKS.register("arcane_wood_wissen_casing", () -> new WissenCasingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_LIGHT_CASING = BLOCKS.register("arcane_wood_light_casing", () -> new LightCasingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_FLUID_CASING = BLOCKS.register("arcane_wood_fluid_casing", () -> new FluidCasingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_STEAM_CASING = BLOCKS.register("arcane_wood_steam_casing", () -> new SteamCasingBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_FRAME = BLOCKS.register("innocent_wood_frame", () -> new FrameBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_GLASS_FRAME = BLOCKS.register("innocent_wood_glass_frame", () -> new GlassFrameBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_CASING = BLOCKS.register("innocent_wood_casing", () -> new CasingBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_WISSEN_CASING = BLOCKS.register("innocent_wood_wissen_casing", () -> new WissenCasingBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_LIGHT_CASING = BLOCKS.register("innocent_wood_light_casing", () -> new LightCasingBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_FLUID_CASING = BLOCKS.register("innocent_wood_fluid_casing", () -> new FluidCasingBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_STEAM_CASING = BLOCKS.register("innocent_wood_steam_casing", () -> new SteamCasingBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_FRAME = BLOCKS.register("wisestone_frame", () -> new FrameBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_GLASS_FRAME = BLOCKS.register("wisestone_glass_frame", () -> new GlassFrameBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_CASING = BLOCKS.register("wisestone_casing", () -> new CasingBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_WISSEN_CASING = BLOCKS.register("wisestone_wissen_casing", () -> new WissenCasingBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_LIGHT_CASING = BLOCKS.register("wisestone_light_casing", () -> new LightCasingBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_FLUID_CASING = BLOCKS.register("wisestone_fluid_casing", () -> new FluidCasingBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_STEAM_CASING = BLOCKS.register("wisestone_steam_casing", () -> new SteamCasingBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).noOcclusion()));

    public static final RegistryObject<Block> CREATIVE_WISSEN_STORAGE = BLOCKS.register("creative_wissen_storage", () -> new CreativeWissenStorageBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> CREATIVE_LIGHT_STORAGE = BLOCKS.register("creative_light_storage", () -> new CreativeLightStorageBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryObject<Block> CREATIVE_FLUID_STORAGE = BLOCKS.register("creative_fluid_storage", () -> new CreativeFluidStorageBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));
    public static final RegistryObject<Block> CREATIVE_STEAM_STORAGE = BLOCKS.register("creative_steam_storage", () -> new CreativeSteamStorageBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE)));

    public static final RegistryObject<Block> ALCHEMY_GLASS = BLOCKS.register("alchemy_glass", () -> new TintedGlassBlock(BlockBehaviour.Properties.copy(Blocks.TINTED_GLASS).noOcclusion()));

    //ITEMS
    public static final RegistryObject<Item> ARCANE_GOLD_INGOT = ITEMS.register("arcane_gold_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_NUGGET = ITEMS.register("arcane_gold_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_ARCANE_GOLD = ITEMS.register("raw_arcane_gold", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_BLOCK_ITEM = ITEMS.register("arcane_gold_block", () -> new BlockItem(ARCANE_GOLD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_ORE_ITEM = ITEMS.register("arcane_gold_ore", () -> new BlockItem(ARCANE_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DEEPSLATE_ARCANE_GOLD_ORE_ITEM = ITEMS.register("deepslate_arcane_gold_ore", () -> new BlockItem(DEEPSLATE_ARCANE_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> NETHER_ARCANE_GOLD_ORE_ITEM = ITEMS.register("nether_arcane_gold_ore", () -> new BlockItem(NETHER_ARCANE_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAW_ARCANE_GOLD_BLOCK_ITEM = ITEMS.register("raw_arcane_gold_block", () -> new BlockItem(RAW_ARCANE_GOLD_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_GOLD_SWORD = ITEMS.register("arcane_gold_sword", () -> new ArcaneSwordItem(CustomItemTier.ARCANE_GOLD, 3, -2.4f, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_PICKAXE = ITEMS.register("arcane_gold_pickaxe", () -> new ArcanePickaxeItem(CustomItemTier.ARCANE_GOLD, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_AXE = ITEMS.register("arcane_gold_axe", () -> new ArcaneAxeItem(CustomItemTier.ARCANE_GOLD, 6, -3.1f, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_SHOVEL = ITEMS.register("arcane_gold_shovel", () -> new ArcaneShovelItem(CustomItemTier.ARCANE_GOLD, 1.5f, -3f, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_HOE = ITEMS.register("arcane_gold_hoe", () -> new ArcaneHoeItem(CustomItemTier.ARCANE_GOLD, -2, -1f, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_SCYTHE = ITEMS.register("arcane_gold_scythe", () -> new ArcaneScytheItem(CustomItemTier.ARCANE_GOLD, 4, -2.8f, new Item.Properties(), 1));

    public static final RegistryObject<Item> ARCANE_GOLD_HELMET = ITEMS.register("arcane_gold_helmet", () -> new ArcaneArmorItem(CustomArmorMaterial.ARCANE_GOLD, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_CHESTPLATE = ITEMS.register("arcane_gold_chestplate", () -> new ArcaneArmorItem(CustomArmorMaterial.ARCANE_GOLD, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_LEGGINGS = ITEMS.register("arcane_gold_leggings", () -> new ArcaneArmorItem(CustomArmorMaterial.ARCANE_GOLD, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_BOOTS = ITEMS.register("arcane_gold_boots", () -> new ArcaneArmorItem(CustomArmorMaterial.ARCANE_GOLD, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> ARCANUM = ITEMS.register("arcanum", () -> new ArcanumItem(new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_DUST = ITEMS.register("arcanum_dust", () -> new ArcanumDustItem(new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_BLOCK_ITEM = ITEMS.register("arcanum_block", () -> new BlockItem(ARCANUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_ORE_ITEM = ITEMS.register("arcanum_ore", () -> new BlockItem(ARCANUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DEEPSLATE_ARCANUM_ORE_ITEM = ITEMS.register("deepslate_arcanum_ore", () -> new BlockItem(DEEPSLATE_ARCANUM_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCACITE = ITEMS.register("arcacite", () -> new ArcaciteItem(new Item.Properties()));
    public static final RegistryObject<Item> ARCACITE_BLOCK_ITEM = ITEMS.register("arcacite_block", () -> new BlockItem(ARCACITE_BLOCK.get(), new Item.Properties()));

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
    public static final RegistryObject<Item> ARCANE_WOOD_MORTAR = ITEMS.register("arcane_wood_mortar", () -> new MortarItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANE_WOOD_LEAVES_ITEM = ITEMS.register("arcane_wood_leaves", () -> new BlockItem(ARCANE_WOOD_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_SAPLING_ITEM = ITEMS.register("arcane_wood_sapling", () -> new BlockItem(ARCANE_WOOD_SAPLING.get(), new Item.Properties()));

    public static final RegistryObject<Item> INNOCENT_WOOD_LOG_ITEM = ITEMS.register("innocent_wood_log", () -> new BlockItem(INNOCENT_WOOD_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_ITEM = ITEMS.register("innocent_wood", () -> new BlockItem(INNOCENT_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_INNOCENT_WOOD_LOG_ITEM = ITEMS.register("stripped_innocent_wood_log", () -> new BlockItem(STRIPPED_INNOCENT_WOOD_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_INNOCENT_WOOD_ITEM = ITEMS.register("stripped_innocent_wood", () -> new BlockItem(STRIPPED_INNOCENT_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_PLANKS_ITEM = ITEMS.register("innocent_wood_planks", () -> new BlockItem(INNOCENT_WOOD_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_STAIRS_ITEM = ITEMS.register("innocent_wood_stairs", () -> new BlockItem(INNOCENT_WOOD_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_SLAB_ITEM = ITEMS.register("innocent_wood_slab", () -> new BlockItem(INNOCENT_WOOD_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_FENCE_ITEM = ITEMS.register("innocent_wood_fence", () -> new BlockItem(INNOCENT_WOOD_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_FENCE_GATE_ITEM = ITEMS.register("innocent_wood_fence_gate", () -> new BlockItem(INNOCENT_WOOD_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_DOOR_ITEM = ITEMS.register("innocent_wood_door", () -> new BlockItem(INNOCENT_WOOD_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_TRAPDOOR_ITEM = ITEMS.register("innocent_wood_trapdoor", () -> new BlockItem(INNOCENT_WOOD_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_PRESSURE_PLATE_ITEM = ITEMS.register("innocent_wood_pressure_plate", () -> new BlockItem(INNOCENT_WOOD_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_BUTTON_ITEM = ITEMS.register("innocent_wood_button", () -> new BlockItem(INNOCENT_WOOD_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_SIGN_ITEM = ITEMS.register("innocent_wood_sign", () -> new SignItem(new Item.Properties().stacksTo(16),INNOCENT_WOOD_SIGN.get(), INNOCENT_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> INNOCENT_WOOD_HANGING_SIGN_ITEM = ITEMS.register("innocent_wood_hanging_sign", () -> new HangingSignItem(INNOCENT_WOOD_HANGING_SIGN.get(), INNOCENT_WOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> INNOCENT_WOOD_BOAT_ITEM = ITEMS.register("innocent_wood_boat", () -> new CustomBoatItem(false, CustomBoatEntity.Type.INNOCENT_WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> INNOCENT_WOOD_CHEST_BOAT_ITEM = ITEMS.register("innocent_wood_chest_boat", () -> new CustomBoatItem(true, CustomBoatEntity.Type.INNOCENT_WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> INNOCENT_WOOD_BRANCH = ITEMS.register("innocent_wood_branch", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_MORTAR = ITEMS.register("innocent_wood_mortar", () -> new MortarItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> INNOCENT_WOOD_LEAVES_ITEM = ITEMS.register("innocent_wood_leaves", () -> new BlockItem(INNOCENT_WOOD_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_SAPLING_ITEM = ITEMS.register("innocent_wood_sapling", () -> new BlockItem(INNOCENT_WOOD_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> PETALS_OF_INNOCENCE_ITEM = ITEMS.register("petals_of_innocence", () -> new BlockItem(PETALS_OF_INNOCENCE.get(), new Item.Properties()));

    public static final RegistryObject<Item> WISESTONE_ITEM = ITEMS.register("wisestone", () -> new BlockItem(WISESTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_STAIRS_ITEM = ITEMS.register("wisestone_stairs", () -> new BlockItem(WISESTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_SLAB_ITEM = ITEMS.register("wisestone_slab", () -> new BlockItem(WISESTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_WALL_ITEM = ITEMS.register("wisestone_wall", () -> new BlockItem(WISESTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_WISESTONE_ITEM = ITEMS.register("polished_wisestone", () -> new BlockItem(POLISHED_WISESTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_WISESTONE_STAIRS_ITEM = ITEMS.register("polished_wisestone_stairs", () -> new BlockItem(POLISHED_WISESTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_WISESTONE_SLAB_ITEM = ITEMS.register("polished_wisestone_slab", () -> new BlockItem(POLISHED_WISESTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_WISESTONE_WALL_ITEM = ITEMS.register("polished_wisestone_wall", () -> new BlockItem(POLISHED_WISESTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_BRICKS_ITEM = ITEMS.register("wisestone_bricks", () -> new BlockItem(WISESTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_BRICKS_STAIRS_ITEM = ITEMS.register("wisestone_bricks_stairs", () -> new BlockItem(WISESTONE_BRICKS_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_BRICKS_SLAB_ITEM = ITEMS.register("wisestone_bricks_slab", () -> new BlockItem(WISESTONE_BRICKS_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_BRICKS_WALL_ITEM = ITEMS.register("wisestone_bricks_wall", () -> new BlockItem(WISESTONE_BRICKS_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_TILE_ITEM = ITEMS.register("wisestone_tile", () -> new BlockItem(WISESTONE_TILE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_TILE_STAIRS_ITEM = ITEMS.register("wisestone_tile_stairs", () -> new BlockItem(WISESTONE_TILE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_TILE_SLAB_ITEM = ITEMS.register("wisestone_tile_slab", () -> new BlockItem(WISESTONE_TILE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_TILE_WALL_ITEM = ITEMS.register("wisestone_tile_wall", () -> new BlockItem(WISESTONE_TILE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_WISESTONE_ITEM = ITEMS.register("chiseled_wisestone", () -> new BlockItem(CHISELED_WISESTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_WISESTONE_STAIRS_ITEM = ITEMS.register("chiseled_wisestone_stairs", () -> new BlockItem(CHISELED_WISESTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_WISESTONE_SLAB_ITEM = ITEMS.register("chiseled_wisestone_slab", () -> new BlockItem(CHISELED_WISESTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_WISESTONE_WALL_ITEM = ITEMS.register("chiseled_wisestone_wall", () -> new BlockItem(CHISELED_WISESTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_PILLAR_ITEM = ITEMS.register("wisestone_pillar", () -> new BlockItem(WISESTONE_PILLAR.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_WISESTONE_PRESSURE_PLATE_ITEM = ITEMS.register("polished_wisestone_pressure_plate", () -> new BlockItem(POLISHED_WISESTONE_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_WISESTONE_BUTTON_ITEM = ITEMS.register("polished_wisestone_button", () -> new BlockItem(POLISHED_WISESTONE_BUTTON.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_LINEN_SEEDS = ITEMS.register("arcane_linen_seeds", () -> new BlockItem(ARCANE_LINEN.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_LINEN_ITEM = ITEMS.register("arcane_linen", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_LINEN_HAY_ITEM = ITEMS.register("arcane_linen_hay", () -> new BlockItem(ARCANE_LINEN_HAY.get(), new Item.Properties()));

    public static final RegistryObject<Item> MOR_ITEM = ITEMS.register("mor", () -> new MorItem(MOR.get(), new Item.Properties().food(MOR_FOOD), 1500, 1800));
    public static final RegistryObject<Item> MOR_BLOCK_ITEM = ITEMS.register("mor_block", () -> new BlockItem(MOR_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ELDER_MOR_ITEM = ITEMS.register("elder_mor", () -> new BlockItem(ELDER_MOR.get(), new Item.Properties().food(MOR_FOOD)));
    public static final RegistryObject<Item> ELDER_MOR_BLOCK_ITEM = ITEMS.register("elder_mor_block", () -> new MorItem(ELDER_MOR_BLOCK.get(), new Item.Properties(), 1700, 2100));

    public static final RegistryObject<Item> PETALS = ITEMS.register("petals", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLOWER_FERTILIZER = ITEMS.register("flower_fertilizer", () -> new BoneMealItem(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_BROWN_MUSHROOM = ITEMS.register("ground_brown_mushroom", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_RED_MUSHROOM = ITEMS.register("ground_red_mushroom", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_CRIMSON_FUNGUS = ITEMS.register("ground_crimson_fungus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_WARPED_FUNGUS = ITEMS.register("ground_warped_fungus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_MOR = ITEMS.register("ground_mor", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_ELDER_MOR = ITEMS.register("ground_elder_mor", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ARCANUM_SEED = ITEMS.register("arcanum_seed", () -> new BlockItem(ARCANUM_SEED_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_GROWTH_ITEM = ITEMS.register("arcanum_growth", () -> new BlockItem(ARCANUM_GROWTH.get(), new Item.Properties()));

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

    public static final RegistryObject<Item> ARCANE_PEDESTAL_ITEM = ITEMS.register("arcane_pedestal", () -> new BlockItem(ARCANE_PEDESTAL.get(), new Item.Properties()));
    public static final RegistryObject<Item> HOVERING_TOME_STAND_ITEM = ITEMS.register("hovering_tome_stand", () -> new BlockItem(HOVERING_TOME_STAND.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_ALTAR_ITEM = ITEMS.register("wissen_altar", () -> new BlockItem(WISSEN_ALTAR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_TRANSLATOR_ITEM = ITEMS.register("wissen_translator", () -> new BlockItem(WISSEN_TRANSLATOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_CRYSTALLIZER_ITEM = ITEMS.register("wissen_crystallizer", () -> new BlockItem(WISSEN_CRYSTALLIZER.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WORKBENCH_ITEM = ITEMS.register("arcane_workbench", () -> new BlockItem(ARCANE_WORKBENCH.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_CELL_ITEM = ITEMS.register("wissen_cell", () -> new WissenStorageBaseItem(WISSEN_CELL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> JEWELER_TABLE_ITEM = ITEMS.register("jeweler_table", () -> new BlockItem(JEWELER_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALTAR_OF_DROUGHT_ITEM = ITEMS.register("altar_of_drought", () -> new BlockItem(ALTAR_OF_DROUGHT.get(), new Item.Properties()));
    public static final RegistryObject<Item> TOTEM_BASE_ITEM = ITEMS.register("totem_base", () -> new BlockItem(TOTEM_BASE.get(), new Item.Properties()));
    public static final RegistryObject<Item> TOTEM_OF_FLAMES_ITEM = ITEMS.register("totem_of_flames", () -> new BlockItem(TOTEM_OF_FLAMES.get(), new Item.Properties()));
    public static final RegistryObject<Item> EXPERIENCE_TOTEM_ITEM = ITEMS.register("experience_totem", () -> new BlockItem(EXPERIENCE_TOTEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> TOTEM_OF_EXPERIENCE_ABSORPTION_ITEM = ITEMS.register("totem_of_experience_absorption", () -> new BlockItem(TOTEM_OF_EXPERIENCE_ABSORPTION.get(), new Item.Properties()));
    public static final RegistryObject<Item> TOTEM_OF_DISENCHANT_ITEM = ITEMS.register("totem_of_disenchant", () -> new BlockItem(TOTEM_OF_DISENCHANT.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_ITERATOR_ITEM = ITEMS.register("arcane_iterator", () -> new BlockItem(ARCANE_ITERATOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> WISESTONE_PEDESTAL_ITEM = ITEMS.register("wisestone_pedestal", () -> new BlockItem(WISESTONE_PEDESTAL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_HOVERING_TOME_STAND_ITEM = ITEMS.register("wisestone_hovering_tome_stand", () -> new BlockItem(WISESTONE_HOVERING_TOME_STAND.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLUID_PIPE_ITEM = ITEMS.register("fluid_pipe", () -> new BlockItem(FLUID_PIPE.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLUID_EXTRACTOR_ITEM = ITEMS.register("fluid_extractor", () -> new BlockItem(FLUID_EXTRACTOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> STEAM_PIPE_ITEM = ITEMS.register("steam_pipe", () -> new BlockItem(STEAM_PIPE.get(), new Item.Properties()));
    public static final RegistryObject<Item> STEAM_EXTRACTOR_ITEM = ITEMS.register("steam_extractor", () -> new BlockItem(STEAM_EXTRACTOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_FURNACE_ITEM = ITEMS.register("alchemy_furnace", () -> new BlockItem(ALCHEMY_FURNACE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ORBITAL_FLUID_RETAINER_ITEM = ITEMS.register("orbital_fluid_retainer", () -> new FluidStorageBaseItem(ORBITAL_FLUID_RETAINER.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STEAM_THERMAL_STORAGE_ITEM = ITEMS.register("steam_thermal_storage", () -> new SteamStorageBaseItem(STEAM_THERMAL_STORAGE.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ALCHEMY_MACHINE_ITEM = ITEMS.register("alchemy_machine", () -> new BlockItem(ALCHEMY_MACHINE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_BOILER_ITEM = ITEMS.register("alchemy_boiler", () -> new BlockItem(ALCHEMY_BOILER.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_CENSER_ITEM = ITEMS.register("arcane_censer", () -> new BlockItem(ARCANE_CENSER.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_PEDESTAL_ITEM = ITEMS.register("innocent_pedestal", () -> new BlockItem(INNOCENT_PEDESTAL.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_HOVERING_TOME_STAND_ITEM = ITEMS.register("innocent_hovering_tome_stand", () -> new BlockItem(INNOCENT_HOVERING_TOME_STAND.get(), new Item.Properties()));

    public static final RegistryObject<Item> LIGHT_EMITTER_ITEM = ITEMS.register("light_emitter", () -> new BlockItem(LIGHT_EMITTER.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_TRANSFER_LENS_ITEM = ITEMS.register("light_transfer_lens", () -> new BlockItem(LIGHT_TRANSFER_LENS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RUNIC_PEDESTAL_ITEM = ITEMS.register("runic_pedestal", () -> new BlockItem(RUNIC_PEDESTAL.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_LEVER_ITEM = ITEMS.register("arcane_lever", () -> new BlockItem(ARCANE_LEVER.get(), new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_SENSOR_ITEM = ITEMS.register("redstone_sensor", () -> new BlockItem(REDSTONE_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_SENSOR_ITEM = ITEMS.register("wissen_sensor", () -> new BlockItem(WISSEN_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> COOLDOWN_SENSOR_ITEM = ITEMS.register("cooldown_sensor", () -> new BlockItem(COOLDOWN_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> EXPERIENCE_SENSOR_ITEM = ITEMS.register("experience_sensor", () -> new BlockItem(EXPERIENCE_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> HEAT_SENSOR_ITEM = ITEMS.register("heat_sensor", () -> new BlockItem(HEAT_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_SENSOR_ITEM = ITEMS.register("light_sensor", () -> new BlockItem(LIGHT_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLUID_SENSOR_ITEM = ITEMS.register("fluid_sensor", () -> new BlockItem(FLUID_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> STEAM_SENSOR_ITEM = ITEMS.register("steam_sensor", () -> new BlockItem(STEAM_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_ACTIVATOR_ITEM = ITEMS.register("wissen_activator", () -> new BlockItem(WISSEN_ACTIVATOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ITEM_SORTER_ITEM = ITEMS.register("item_sorter", () -> new BlockItem(ITEM_SORTER.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_WOOD_FRAME_ITEM = ITEMS.register("arcane_wood_frame", () -> new BlockItem(ARCANE_WOOD_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_GLASS_FRAME_ITEM = ITEMS.register("arcane_wood_glass_frame", () -> new BlockItem(ARCANE_WOOD_GLASS_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_CASING_ITEM = ITEMS.register("arcane_wood_casing", () -> new BlockItem(ARCANE_WOOD_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_WISSEN_CASING_ITEM = ITEMS.register("arcane_wood_wissen_casing", () -> new BlockItem(ARCANE_WOOD_WISSEN_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_LIGHT_CASING_ITEM = ITEMS.register("arcane_wood_light_casing", () -> new BlockItem(ARCANE_WOOD_LIGHT_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_FLUID_CASING_ITEM = ITEMS.register("arcane_wood_fluid_casing", () -> new BlockItem(ARCANE_WOOD_FLUID_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_STEAM_CASING_ITEM = ITEMS.register("arcane_wood_steam_casing", () -> new BlockItem(ARCANE_WOOD_STEAM_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_FRAME_ITEM = ITEMS.register("innocent_wood_frame", () -> new BlockItem(INNOCENT_WOOD_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_GLASS_FRAME_ITEM = ITEMS.register("innocent_wood_glass_frame", () -> new BlockItem(INNOCENT_WOOD_GLASS_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_CASING_ITEM = ITEMS.register("innocent_wood_casing", () -> new BlockItem(INNOCENT_WOOD_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_WISSEN_CASING_ITEM = ITEMS.register("innocent_wood_wissen_casing", () -> new BlockItem(INNOCENT_WOOD_WISSEN_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_LIGHT_CASING_ITEM = ITEMS.register("innocent_wood_light_casing", () -> new BlockItem(INNOCENT_WOOD_LIGHT_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_FLUID_CASING_ITEM = ITEMS.register("innocent_wood_fluid_casing", () -> new BlockItem(INNOCENT_WOOD_FLUID_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_STEAM_CASING_ITEM = ITEMS.register("innocent_wood_steam_casing", () -> new BlockItem(INNOCENT_WOOD_STEAM_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_FRAME_ITEM = ITEMS.register("wisestone_frame", () -> new BlockItem(WISESTONE_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_GLASS_FRAME_ITEM = ITEMS.register("wisestone_glass_frame", () -> new BlockItem(WISESTONE_GLASS_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_CASING_ITEM = ITEMS.register("wisestone_casing", () -> new BlockItem(WISESTONE_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_WISSEN_CASING_ITEM = ITEMS.register("wisestone_wissen_casing", () -> new BlockItem(WISESTONE_WISSEN_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_LIGHT_CASING_ITEM = ITEMS.register("wisestone_light_casing", () -> new BlockItem(WISESTONE_LIGHT_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_FLUID_CASING_ITEM = ITEMS.register("wisestone_fluid_casing", () -> new BlockItem(WISESTONE_FLUID_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_STEAM_CASING_ITEM = ITEMS.register("wisestone_steam_casing", () -> new BlockItem(WISESTONE_STEAM_CASING.get(), new Item.Properties()));

    public static final RegistryObject<Item> CREATIVE_WISSEN_STORAGE_ITEM = ITEMS.register("creative_wissen_storage", () -> new BlockItem(CREATIVE_WISSEN_STORAGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CREATIVE_LIGHT_STORAGE_ITEM = ITEMS.register("creative_light_storage", () -> new BlockItem(CREATIVE_LIGHT_STORAGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CREATIVE_FLUID_STORAGE_ITEM = ITEMS.register("creative_fluid_storage", () -> new BlockItem(CREATIVE_FLUID_STORAGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CREATIVE_STEAM_STORAGE_ITEM = ITEMS.register("creative_steam_storage", () -> new BlockItem(CREATIVE_STEAM_STORAGE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ALCHEMY_GLASS_ITEM = ITEMS.register("alchemy_glass", () -> new BlockItem(ALCHEMY_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_VIAL = ITEMS.register("alchemy_vial", () -> new VialItem(new Item.Properties(), 3));
    public static final RegistryObject<Item> ALCHEMY_FLASK = ITEMS.register("alchemy_flask", () -> new FlaskItem(new Item.Properties(), 6));
    public static final RegistryObject<Item> ALCHEMY_VIAL_POTION = ITEMS.register("alchemy_vial_potion", () -> new AlchemyPotionItem(new Item.Properties().stacksTo(1), 3, ALCHEMY_VIAL.get()));
    public static final RegistryObject<Item> ALCHEMY_FLASK_POTION = ITEMS.register("alchemy_flask_potion", () -> new AlchemyPotionItem(new Item.Properties().stacksTo(1), 6, ALCHEMY_FLASK.get()));
    public static final RegistryObject<Item> ARCACITE_POLISHING_MIXTURE = ITEMS.register("arcacite_polishing_mixture", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WISESTONE_PLATE = ITEMS.register("wisestone_plate", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RUNIC_WISESTONE_PLATE = ITEMS.register("runic_wisestone_plate", () -> new RunicWisestonePlateItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARCANE_WAND = ITEMS.register("arcane_wand", () -> new ArcaneWandItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WISSEN_WAND = ITEMS.register("wissen_wand", () -> new WissenWandItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARCANUM_AMULET = ITEMS.register("arcanum_amulet", () -> new ArcanumAmuletItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANUM_RING = ITEMS.register("arcanum_ring", () -> new ArcanumRingItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCACITE_AMULET = ITEMS.register("arcacite_amulet", () -> new ArcaciteAmuletItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCACITE_RING = ITEMS.register("arcacite_ring", () -> new ArcaciteRingItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WISSEN_KEYCHAIN = ITEMS.register("wissen_keychain", () -> new WissenKeychainItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LEATHER_BELT = ITEMS.register("leather_belt", () -> new LeatherBeltItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BROWN_MUSHROOM_CAP = ITEMS.register("brown_mushroom_cap", () -> new MushroomCapItem(new Item.Properties().stacksTo(1), "brown_mushroom_cap"));
    public static final RegistryObject<Item> RED_MUSHROOM_CAP = ITEMS.register("red_mushroom_cap", () -> new MushroomCapItem(new Item.Properties().stacksTo(1), "red_mushroom_cap"));
    public static final RegistryObject<Item> CRIMSON_FUNGUS_CAP = ITEMS.register("crimson_fungus_cap", () -> new MushroomCapItem(new Item.Properties().stacksTo(1), "crimson_fungus_cap"));
    public static final RegistryObject<Item> WARPED_FUNGUS_CAP = ITEMS.register("warped_fungus_cap", () -> new MushroomCapItem(new Item.Properties().stacksTo(1), "warped_fungus_cap"));
    public static final RegistryObject<Item> MOR_CAP = ITEMS.register("mor_cap", () -> new MushroomCapItem(new Item.Properties().stacksTo(1), "mor_cap"));
    public static final RegistryObject<Item> ELDER_MOR_CAP = ITEMS.register("elder_mor_cap", () -> new MushroomCapItem(new Item.Properties().stacksTo(1), "elder_mor_cap"));

    public static final RegistryObject<Item> ARCANE_FORTRESS_HELMET = ITEMS.register("arcane_fortress_helmet", () -> new ArcaneFortressArmorItem(CustomArmorMaterial.ARCANE_FORTRESS, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_FORTRESS_CHESTPLATE = ITEMS.register("arcane_fortress_chestplate", () -> new ArcaneFortressArmorItem(CustomArmorMaterial.ARCANE_FORTRESS, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_FORTRESS_LEGGINGS = ITEMS.register("arcane_fortress_leggings", () -> new ArcaneFortressArmorItem(CustomArmorMaterial.ARCANE_FORTRESS, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_FORTRESS_BOOTS = ITEMS.register("arcane_fortress_boots", () -> new ArcaneFortressArmorItem(CustomArmorMaterial.ARCANE_FORTRESS, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> INVENTOR_WIZARD_HAT = ITEMS.register("inventor_wizard_hat", () -> new InventorWizardArmorItem(CustomArmorMaterial.INVENTOR_WIZARD, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> INVENTOR_WIZARD_COSTUME = ITEMS.register("inventor_wizard_costume", () -> new InventorWizardArmorItem(CustomArmorMaterial.INVENTOR_WIZARD, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> INVENTOR_WIZARD_TROUSERS = ITEMS.register("inventor_wizard_trousers", () -> new InventorWizardArmorItem(CustomArmorMaterial.INVENTOR_WIZARD, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> INVENTOR_WIZARD_BOOTS = ITEMS.register("inventor_wizard_boots", () -> new InventorWizardArmorItem(CustomArmorMaterial.INVENTOR_WIZARD, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_WOOD_SMOKING_PIPE = ITEMS.register("arcane_wood_smoking_pipe", () -> new SmokingPipeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANE_WOOD_CANE = ITEMS.register("arcane_wood_cane", () -> new CaneItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANE_WOOD_BOW = ITEMS.register("arcane_wood_bow", () -> new ArcaneBowItem(new Item.Properties().durability(576)));

    public static final RegistryObject<Item> BLAZE_REAP = ITEMS.register("blaze_reap", () -> new ArcanePickaxeItem(CustomItemTier.ARCANE_GOLD, 1, -2.8f, new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> ARCANE_ENCHANTED_BOOK = ITEMS.register("arcane_enchanted_book", () -> new ArcaneEnchantedBookItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANEMICON = ITEMS.register("arcanemicon", () -> new ArcanemiconItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> VIOLENCE_BANNER_PATTERN_ITEM = ITEMS.register("violence_banner_pattern", () -> new BannerPatternItem(VIOLENCE_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> REPRODUCTION_BANNER_PATTERN_ITEM = ITEMS.register("reproduction_banner_pattern", () -> new BannerPatternItem(REPRODUCTION_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> COOPERATION_BANNER_PATTERN_ITEM = ITEMS.register("cooperation_banner_pattern", () -> new BannerPatternItem(COOPERATION_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> HUNGER_BANNER_PATTERN_ITEM = ITEMS.register("hunger_banner_pattern", () -> new BannerPatternItem(HUNGER_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SURVIVAL_BANNER_PATTERN_ITEM = ITEMS.register("survival_banner_pattern", () -> new BannerPatternItem(SURVIVAL_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ELEVATION_BANNER_PATTERN_ITEM = ITEMS.register("elevation_banner_pattern", () -> new BannerPatternItem(ELEVATION_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MUSIC_DISC_ARCANUM = ITEMS.register("music_disc_arcanum", () -> new RecordItem(6, MUSIC_DISC_ARCANUM_SOUND.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 122));
    public static final RegistryObject<Item> MUSIC_DISC_MOR = ITEMS.register("music_disc_mor", () -> new RecordItem(6, MUSIC_DISC_MOR_SOUND.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 305));
    public static final RegistryObject<Item> MUSIC_DISC_REBORN = ITEMS.register("music_disc_reborn", () -> new RecordItem(6, MUSIC_DISC_REBORN_SOUND.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 211));

    //TILE_ENTITIES
    public static final RegistryObject<BlockEntityType<CustomSignTileEntity>> SIGN_TILE_ENTITY = TILE_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(CustomSignTileEntity::new,
            ARCANE_WOOD_SIGN.get(), ARCANE_WOOD_WALL_SIGN.get(),
            INNOCENT_WOOD_SIGN.get(), INNOCENT_WOOD_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<CustomHangingSignTileEntity>> HANGING_SIGN_TILE_ENTITY = TILE_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(CustomHangingSignTileEntity::new,
            ARCANE_WOOD_HANGING_SIGN.get(), ARCANE_WOOD_WALL_HANGING_SIGN.get(),
            INNOCENT_WOOD_HANGING_SIGN.get(), INNOCENT_WOOD_WALL_HANGING_SIGN.get())
            .build(null));

    public static RegistryObject<BlockEntityType<ArcanumGrowthTileEntity>> ARCANUM_GROWTH_TILE_ENTITY = TILE_ENTITIES.register("arcanum_growth", () -> BlockEntityType.Builder.of(ArcanumGrowthTileEntity::new, ARCANUM_GROWTH.get()).build(null));
    public static RegistryObject<BlockEntityType<CrystalTileEntity>> CRYSTAL_TILE_ENTITY = TILE_ENTITIES.register("crystal", () -> BlockEntityType.Builder.of(CrystalTileEntity::new,
            EARTH_CRYSTAL_BLOCK.get(), WATER_CRYSTAL_BLOCK.get(), AIR_CRYSTAL_BLOCK.get(), FIRE_CRYSTAL_BLOCK.get(), VOID_CRYSTAL_BLOCK.get(),
            FACETED_EARTH_CRYSTAL_BLOCK.get(), FACETED_WATER_CRYSTAL_BLOCK.get(), FACETED_AIR_CRYSTAL_BLOCK.get(), FACETED_FIRE_CRYSTAL_BLOCK.get(), FACETED_VOID_CRYSTAL_BLOCK.get(),
            ADVANCED_EARTH_CRYSTAL_BLOCK.get(), ADVANCED_WATER_CRYSTAL_BLOCK.get(), ADVANCED_AIR_CRYSTAL_BLOCK.get(), ADVANCED_FIRE_CRYSTAL_BLOCK.get(), ADVANCED_VOID_CRYSTAL_BLOCK.get(),
            MASTERFUL_EARTH_CRYSTAL_BLOCK.get(), MASTERFUL_WATER_CRYSTAL_BLOCK.get(), MASTERFUL_AIR_CRYSTAL_BLOCK.get(), MASTERFUL_FIRE_CRYSTAL_BLOCK.get(), MASTERFUL_VOID_CRYSTAL_BLOCK.get(),
            PURE_EARTH_CRYSTAL_BLOCK.get(), PURE_WATER_CRYSTAL_BLOCK.get(), PURE_AIR_CRYSTAL_BLOCK.get(), PURE_FIRE_CRYSTAL_BLOCK.get(), PURE_VOID_CRYSTAL_BLOCK.get())
            .build(null));
    public static RegistryObject<BlockEntityType<CrystalGrowthTileEntity>> CRYSTAL_GROWTH_TILE_ENTITY = TILE_ENTITIES.register("crystal_growth", () -> BlockEntityType.Builder.of(CrystalGrowthTileEntity::new,
            EARTH_CRYSTAL_GROWTH.get(), WATER_CRYSTAL_GROWTH.get(), AIR_CRYSTAL_GROWTH.get(), FIRE_CRYSTAL_GROWTH.get(), VOID_CRYSTAL_GROWTH.get())
            .build(null));

    public static RegistryObject<BlockEntityType<ArcanePedestalTileEntity>> ARCANE_PEDESTAL_TILE_ENTITY = TILE_ENTITIES.register("arcane_pedestal", () -> BlockEntityType.Builder.of(ArcanePedestalTileEntity::new, ARCANE_PEDESTAL.get(), WISESTONE_PEDESTAL.get(), INNOCENT_PEDESTAL.get()).build(null));
    public static RegistryObject<BlockEntityType<HoveringTomeStandTileEntity>> HOVERING_TOME_STAND_TILE_ENTITY = TILE_ENTITIES.register("hovering_tome_stand", () -> BlockEntityType.Builder.of(HoveringTomeStandTileEntity::new, HOVERING_TOME_STAND.get(), WISESTONE_HOVERING_TOME_STAND.get(), INNOCENT_HOVERING_TOME_STAND.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenAltarTileEntity>> WISSEN_ALTAR_TILE_ENTITY = TILE_ENTITIES.register("wissen_altar", () -> BlockEntityType.Builder.of(WissenAltarTileEntity::new, WISSEN_ALTAR.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenTranslatorTileEntity>> WISSEN_TRANSLATOR_TILE_ENTITY = TILE_ENTITIES.register("wissen_translator", () -> BlockEntityType.Builder.of(WissenTranslatorTileEntity::new, WISSEN_TRANSLATOR.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenCrystallizerTileEntity>> WISSEN_CRYSTALLIZER_TILE_ENTITY = TILE_ENTITIES.register("wissen_crystallizer", () -> BlockEntityType.Builder.of(WissenCrystallizerTileEntity::new, WISSEN_CRYSTALLIZER.get()).build(null));
    public static RegistryObject<BlockEntityType<ArcaneWorkbenchTileEntity>> ARCANE_WORKBENCH_TILE_ENTITY = TILE_ENTITIES.register("arcane_workbench", () -> BlockEntityType.Builder.of(ArcaneWorkbenchTileEntity::new, ARCANE_WORKBENCH.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenCellTileEntity>> WISSEN_CELL_TILE_ENTITY = TILE_ENTITIES.register("wissen_cell", () -> BlockEntityType.Builder.of(WissenCellTileEntity::new, WISSEN_CELL.get()).build(null));
    public static RegistryObject<BlockEntityType<JewelerTableTileEntity>> JEWELER_TABLE_TILE_ENTITY = TILE_ENTITIES.register("jeweler_table", () -> BlockEntityType.Builder.of(JewelerTableTileEntity::new, JEWELER_TABLE.get()).build(null));
    public static RegistryObject<BlockEntityType<AltarOfDroughtTileEntity>> ALTAR_OF_DROUGHT_TILE_ENTITY = TILE_ENTITIES.register("altar_of_drought", () -> BlockEntityType.Builder.of(AltarOfDroughtTileEntity::new, ALTAR_OF_DROUGHT.get()).build(null));
    public static RegistryObject<BlockEntityType<TotemOfFlamesTileEntity>> TOTEM_OF_FLAMES_TILE_ENTITY = TILE_ENTITIES.register("totem_of_flames", () -> BlockEntityType.Builder.of(TotemOfFlamesTileEntity::new, TOTEM_OF_FLAMES.get()).build(null));
    public static RegistryObject<BlockEntityType<ExperienceTotemTileEntity>> EXPERIENCE_TOTEM_TILE_ENTITY = TILE_ENTITIES.register("experience_totem", () -> BlockEntityType.Builder.of(ExperienceTotemTileEntity::new, EXPERIENCE_TOTEM.get()).build(null));
    public static RegistryObject<BlockEntityType<TotemOfExperienceAbsorptionTileEntity>> TOTEM_OF_EXPERIENCE_ABSORPTION_TILE_ENTITY = TILE_ENTITIES.register("totem_of_experience_absorption", () -> BlockEntityType.Builder.of(TotemOfExperienceAbsorptionTileEntity::new, TOTEM_OF_EXPERIENCE_ABSORPTION.get()).build(null));
    public static RegistryObject<BlockEntityType<ArcaneIteratorTileEntity>> ARCANE_ITERATOR_TILE_ENTITY = TILE_ENTITIES.register("arcane_iterator", () -> BlockEntityType.Builder.of(ArcaneIteratorTileEntity::new, ARCANE_ITERATOR.get()).build(null));

    public static RegistryObject<BlockEntityType<FluidPipeTileEntity>> FLUID_PIPE_TILE_ENTITY = TILE_ENTITIES.register("fluid_pipe", () -> BlockEntityType.Builder.of(FluidPipeTileEntity::new, FLUID_PIPE.get()).build(null));
    public static RegistryObject<BlockEntityType<FluidExtractorTileEntity>> FLUID_EXTRACTOR_TILE_ENTITY = TILE_ENTITIES.register("fluid_extractor", () -> BlockEntityType.Builder.of(FluidExtractorTileEntity::new, FLUID_EXTRACTOR.get()).build(null));
    public static RegistryObject<BlockEntityType<SteamPipeTileEntity>> STEAM_PIPE_TILE_ENTITY = TILE_ENTITIES.register("steam_pipe", () -> BlockEntityType.Builder.of(SteamPipeTileEntity::new, STEAM_PIPE.get()).build(null));
    public static RegistryObject<BlockEntityType<SteamExtractorTileEntity>> STEAM_EXTRACTOR_TILE_ENTITY = TILE_ENTITIES.register("steam_extractor", () -> BlockEntityType.Builder.of(SteamExtractorTileEntity::new, STEAM_EXTRACTOR.get()).build(null));
    public static RegistryObject<BlockEntityType<AlchemyFurnaceTileEntity>> ALCHEMY_FURNACE_TILE_ENTITY = TILE_ENTITIES.register("alchemy_furnace", () -> BlockEntityType.Builder.of(AlchemyFurnaceTileEntity::new, ALCHEMY_FURNACE.get()).build(null));
    public static RegistryObject<BlockEntityType<OrbitalFluidRetainerTileEntity>> ORBITAL_FLUID_RETAINER_TILE_ENTITY = TILE_ENTITIES.register("orbital_fluid_retainer", () -> BlockEntityType.Builder.of(OrbitalFluidRetainerTileEntity::new, ORBITAL_FLUID_RETAINER.get()).build(null));
    public static RegistryObject<BlockEntityType<SteamThermalStorageTileEntity>> STEAM_THERMAL_STORAGE_TILE_ENTITY = TILE_ENTITIES.register("steam_thermal_storage", () -> BlockEntityType.Builder.of(SteamThermalStorageTileEntity::new, STEAM_THERMAL_STORAGE.get()).build(null));
    public static RegistryObject<BlockEntityType<AlchemyMachineTileEntity>> ALCHEMY_MACHINE_TILE_ENTITY = TILE_ENTITIES.register("alchemy_machine", () -> BlockEntityType.Builder.of(AlchemyMachineTileEntity::new, ALCHEMY_MACHINE.get()).build(null));
    public static RegistryObject<BlockEntityType<AlchemyBoilerTileEntity>> ALCHEMY_BOILER_TILE_ENTITY = TILE_ENTITIES.register("alchemy_boiler", () -> BlockEntityType.Builder.of(AlchemyBoilerTileEntity::new, ALCHEMY_BOILER.get()).build(null));
    public static RegistryObject<BlockEntityType<ArcaneCenserTileEntity>> ARCANE_CENSER_TILE_ENTITY = TILE_ENTITIES.register("arcane_censer", () -> BlockEntityType.Builder.of(ArcaneCenserTileEntity::new, ARCANE_CENSER.get()).build(null));

    public static RegistryObject<BlockEntityType<LightEmitterTileEntity>> LIGHT_EMITTER_TILE_ENTITY = TILE_ENTITIES.register("light_emitter", () -> BlockEntityType.Builder.of(LightEmitterTileEntity::new, LIGHT_EMITTER.get()).build(null));
    public static RegistryObject<BlockEntityType<LightTransferLensTileEntity>> LIGHT_TRANSFER_LENS_TILE_ENTITY = TILE_ENTITIES.register("light_transfer_lens", () -> BlockEntityType.Builder.of(LightTransferLensTileEntity::new, LIGHT_TRANSFER_LENS.get()).build(null));
    public static RegistryObject<BlockEntityType<RunicPedestalTileEntity>> RUNIC_PEDESTAL_TILE_ENTITY = TILE_ENTITIES.register("runic_pedestal", () -> BlockEntityType.Builder.of(RunicPedestalTileEntity::new, RUNIC_PEDESTAL.get()).build(null));

    public static RegistryObject<BlockEntityType<SensorTileEntity>> SENSOR_TILE_ENTITY = TILE_ENTITIES.register("sensor", () -> BlockEntityType.Builder.of(SensorTileEntity::new, REDSTONE_SENSOR.get(), WISSEN_SENSOR.get(), COOLDOWN_SENSOR.get(), LIGHT_SENSOR.get(), EXPERIENCE_SENSOR.get(), HEAT_SENSOR.get(), STEAM_SENSOR.get()).build(null));
    public static RegistryObject<BlockEntityType<FluidSensorTileEntity>> FLUID_SENSOR_TILE_ENTITY = TILE_ENTITIES.register("fluid_sensor", () -> BlockEntityType.Builder.of(FluidSensorTileEntity::new, FLUID_SENSOR.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenActivatorTileEntity>> WISSEN_ACTIVATOR_TILE_ENTITY = TILE_ENTITIES.register("wissen_activator", () -> BlockEntityType.Builder.of(WissenActivatorTileEntity::new, WISSEN_ACTIVATOR.get()).build(null));
    public static RegistryObject<BlockEntityType<ItemSorterTileEntity>> ITEM_SORTER_TILE_ENTITY = TILE_ENTITIES.register("item_sorter", () -> BlockEntityType.Builder.of(ItemSorterTileEntity::new, ITEM_SORTER.get()).build(null));

    public static RegistryObject<BlockEntityType<WissenCasingTileEntity>> WISSEN_CASING_TILE_ENTITY = TILE_ENTITIES.register("wissen_casing", () -> BlockEntityType.Builder.of(WissenCasingTileEntity::new, ARCANE_WOOD_WISSEN_CASING.get(), INNOCENT_WOOD_WISSEN_CASING.get(), WISESTONE_WISSEN_CASING.get()).build(null));
    public static RegistryObject<BlockEntityType<LightCasingTileEntity>> LIGHT_CASING_TILE_ENTITY = TILE_ENTITIES.register("light_casing", () -> BlockEntityType.Builder.of(LightCasingTileEntity::new, ARCANE_WOOD_LIGHT_CASING.get(), INNOCENT_WOOD_LIGHT_CASING.get(), WISESTONE_LIGHT_CASING.get()).build(null));
    public static RegistryObject<BlockEntityType<FluidCasingTileEntity>> FLUID_CASING_TILE_ENTITY = TILE_ENTITIES.register("fluid_casing", () -> BlockEntityType.Builder.of(FluidCasingTileEntity::new, ARCANE_WOOD_FLUID_CASING.get(), INNOCENT_WOOD_FLUID_CASING.get(), WISESTONE_FLUID_CASING.get()).build(null));
    public static RegistryObject<BlockEntityType<SteamCasingTileEntity>> STEAM_CASING_TILE_ENTITY = TILE_ENTITIES.register("steam_casing", () -> BlockEntityType.Builder.of(SteamCasingTileEntity::new, ARCANE_WOOD_STEAM_CASING.get(), INNOCENT_WOOD_STEAM_CASING.get(), WISESTONE_STEAM_CASING.get()).build(null));

    public static RegistryObject<BlockEntityType<CreativeWissenStorageTileEntity>> CREATIVE_WISSEN_STORAGE_TILE_ENTITY = TILE_ENTITIES.register("creative_wissen_storage", () -> BlockEntityType.Builder.of(CreativeWissenStorageTileEntity::new, CREATIVE_WISSEN_STORAGE.get()).build(null));
    public static RegistryObject<BlockEntityType<CreativeLightStorageTileEntity>> CREATIVE_LIGHT_STORAGE_TILE_ENTITY = TILE_ENTITIES.register("creative_light_storage", () -> BlockEntityType.Builder.of(CreativeLightStorageTileEntity::new, CREATIVE_LIGHT_STORAGE.get()).build(null));
    public static RegistryObject<BlockEntityType<CreativeFluidStorageTileEntity>> CREATIVE_FLUID_STORAGE_TILE_ENTITY = TILE_ENTITIES.register("creative_fluid_storage", () -> BlockEntityType.Builder.of(CreativeFluidStorageTileEntity::new, CREATIVE_FLUID_STORAGE.get()).build(null));
    public static RegistryObject<BlockEntityType<CreativeSteamStorageTileEntity>> CREATIVE_STEAM_STORAGE_TILE_ENTITY = TILE_ENTITIES.register("creative_steam_storage", () -> BlockEntityType.Builder.of(CreativeSteamStorageTileEntity::new, CREATIVE_STEAM_STORAGE.get()).build(null));

    //ENTITIES
    public static final RegistryObject<EntityType<CustomBoatEntity>> BOAT = ENTITIES.register("boat", () -> EntityType.Builder.<CustomBoatEntity>of(CustomBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(MOD_ID, "arcane_wood_boat").toString()));
    public static final RegistryObject<EntityType<CustomChestBoatEntity>> CHEST_BOAT = ENTITIES.register("chest_boat", () -> EntityType.Builder.<CustomChestBoatEntity>of(CustomChestBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(MOD_ID, "arcane_wood_chest_boat").toString()));
    public static final RegistryObject<EntityType<SpellProjectileEntity>> SPELL_PROJECTILE = ENTITIES.register("spell_projectile", () -> EntityType.Builder.<SpellProjectileEntity>of(SpellProjectileEntity::new, MobCategory.MISC).sized(0.4f, 0.4f).build(new ResourceLocation(MOD_ID, "spell_projectile").toString()));

    //PARTICLES
    public static RegistryObject<WispParticleType> WISP_PARTICLE = PARTICLES.register("wisp", WispParticleType::new);
    public static RegistryObject<SparkleParticleType> SPARKLE_PARTICLE = PARTICLES.register("sparkle", SparkleParticleType::new);
    public static RegistryObject<KarmaParticleType> KARMA_PARTICLE = PARTICLES.register("karma", KarmaParticleType::new);
    public static RegistryObject<ArcaneWoodLeafParticleType> ARCANE_WOOD_LEAF_PARTICLE = PARTICLES.register("arcane_wood_leaf", ArcaneWoodLeafParticleType::new);
    public static RegistryObject<InnocenceWoodLeafParticleType> INNOCENT_WOOD_LEAF_PARTICLE = PARTICLES.register("innocence_wood_leaf", InnocenceWoodLeafParticleType::new);
    public static RegistryObject<SteamParticleType> STEAM_PARTICLE = PARTICLES.register("steam", SteamParticleType::new);
    public static RegistryObject<SmokeParticleType> SMOKE_PARTICLE = PARTICLES.register("smoke", SmokeParticleType::new);

    //RECIPES
    public static final RegistryObject<ArcanumDustTransmutationRecipe.Serializer> ARCANUM_DUST_TRANSMUTATION_SERIALIZER = RECIPE_SERIALIZERS.register("arcanum_dust_transmutation", ArcanumDustTransmutationRecipe.Serializer::new);
    public static RegistryObject<RecipeType<ArcanumDustTransmutationRecipe>> ARCANUM_DUST_TRANSMUTATION_RECIPE = RECIPES.register("arcanum_dust_transmutation", () -> RecipeType.simple(ArcanumDustTransmutationRecipe.TYPE_ID));

    public static final RegistryObject<WissenAltarRecipe.Serializer> WISSEN_ALTAR_SERIALIZER = RECIPE_SERIALIZERS.register("wissen_altar", WissenAltarRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<WissenAltarRecipe>> WISSEN_ALTAR_RECIPE = RECIPES.register("wissen_altar", () -> RecipeType.simple(WissenAltarRecipe.TYPE_ID));

    public static final RegistryObject<WissenCrystallizerRecipe.Serializer> WISSEN_CRYSTALLIZER_SERIALIZER = RECIPE_SERIALIZERS.register("wissen_crystallizer", WissenCrystallizerRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<WissenCrystallizerRecipe>> WISSEN_CRYSTALLIZER_RECIPE = RECIPES.register("wissen_crystallizer", () -> RecipeType.simple(WissenCrystallizerRecipe.TYPE_ID));

    public static final RegistryObject<ArcaneWorkbenchRecipe.Serializer> ARCANE_WORKBENCH_SERIALIZER = RECIPE_SERIALIZERS.register("arcane_workbench", ArcaneWorkbenchRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<ArcaneWorkbenchRecipe>> ARCANE_WORKBENCH_RECIPE = RECIPES.register("arcane_workbench", () -> RecipeType.simple(ArcaneWorkbenchRecipe.TYPE_ID));

    public static final RegistryObject<MortarRecipe.Serializer> MORTAR_SERIALIZER = RECIPE_SERIALIZERS.register("mortar", MortarRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<MortarRecipe>> MORTAR_RECIPE = RECIPES.register("mortar", () -> RecipeType.simple(MortarRecipe.TYPE_ID));

    public static final RegistryObject<JewelerTableRecipe.Serializer> JEWELER_TABLE_SERIALIZER = RECIPE_SERIALIZERS.register("jeweler_table", JewelerTableRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<JewelerTableRecipe>> JEWELER_TABLE_RECIPE = RECIPES.register("jeweler_table", () -> RecipeType.simple(JewelerTableRecipe.TYPE_ID));

    public static final RegistryObject<AlchemyMachineRecipe.Serializer> ALCHEMY_MACHINE_SERIALIZER = RECIPE_SERIALIZERS.register("alchemy_machine", AlchemyMachineRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<AlchemyMachineRecipe>> ALCHEMY_MACHINE_RECIPE = RECIPES.register("alchemy_machine", () -> RecipeType.simple(AlchemyMachineRecipe.TYPE_ID));

    public static final RegistryObject<CenserRecipe.Serializer> CENSER_SERIALIZER = RECIPE_SERIALIZERS.register("censer", CenserRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<CenserRecipe>> CENSER_RECIPE = RECIPES.register("censer", () -> RecipeType.simple(CenserRecipe.TYPE_ID));

    public static final RegistryObject<ArcaneIteratorRecipe.Serializer> ARCANE_ITERATOR_SERIALIZER = RECIPE_SERIALIZERS.register("arcane_iterator", ArcaneIteratorRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<ArcaneIteratorRecipe>> ARCANE_ITERATOR_RECIPE = RECIPES.register("arcane_iterator", () -> RecipeType.simple(ArcaneIteratorRecipe.TYPE_ID));

    public static final RegistryObject<CrystalRitualRecipe.Serializer> CRYSTAL_RITUAL_SERIALIZER = RECIPE_SERIALIZERS.register("crystal_ritual", CrystalRitualRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<CrystalRitualRecipe>> CRYSTAL_RITUAL_RECIPE = RECIPES.register("crystal_ritual", () -> RecipeType.simple(CrystalRitualRecipe.TYPE_ID));

    public static final RegistryObject<CrystalInfusionRecipe.Serializer> CRYSTAL_INFUSION_SERIALIZER = RECIPE_SERIALIZERS.register("crystal_infusion", CrystalInfusionRecipe.Serializer::new);
    public static final RegistryObject<RecipeType<CrystalInfusionRecipe>> CRYSTAL_INFUSION_RECIPE = RECIPES.register("crystal_infusion", () -> RecipeType.simple(CrystalInfusionRecipe.TYPE_ID));

    //CONTAINERS
    public static final RegistryObject<MenuType<ArcaneWorkbenchContainer>> ARCANE_WORKBENCH_CONTAINER
            = CONTAINERS.register("arcane_workbench",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new ArcaneWorkbenchContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<MenuType<JewelerTableContainer>> JEWELER_TABLE_CONTAINER
            = CONTAINERS.register("jeweler_table",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new JewelerTableContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<MenuType<AlchemyFurnaceContainer>> ALCHEMY_FURNACE_CONTAINER
            = CONTAINERS.register("alchemy_furnace",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new AlchemyFurnaceContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<MenuType<AlchemyMachineContainer>> ALCHEMY_MACHINE_CONTAINER
            = CONTAINERS.register("alchemy_machine",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new AlchemyMachineContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<MenuType<ItemSorterContainer>> ITEM_SORTER_CONTAINER
            = CONTAINERS.register("item_sorter",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new ItemSorterContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<MenuType<RunicPedestalContainer>> RUNIC_PEDESTAL_CONTAINER
            = CONTAINERS.register("runic_pedestal",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new RunicPedestalContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<BannerPattern> VIOLENCE_BANNER_PATTERN = BANNER_PATTERNS.register("violence", () -> new BannerPattern("wrv"));
    public static final RegistryObject<BannerPattern> REPRODUCTION_BANNER_PATTERN = BANNER_PATTERNS.register("reproduction", () -> new BannerPattern("wrr"));
    public static final RegistryObject<BannerPattern> COOPERATION_BANNER_PATTERN = BANNER_PATTERNS.register("cooperation", () -> new BannerPattern("wrc"));
    public static final RegistryObject<BannerPattern> HUNGER_BANNER_PATTERN = BANNER_PATTERNS.register("hunger", () -> new BannerPattern("wrh"));
    public static final RegistryObject<BannerPattern> SURVIVAL_BANNER_PATTERN = BANNER_PATTERNS.register("survival", () -> new BannerPattern("wrs"));
    public static final RegistryObject<BannerPattern> ELEVATION_BANNER_PATTERN = BANNER_PATTERNS.register("elevation", () -> new BannerPattern("wre"));

    public static final RegistryObject<SoundEvent> WISSEN_BURST_SOUND = SOUND_EVENTS.register("wissen_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wissen_burst")));
    public static final RegistryObject<SoundEvent> WISSEN_TRANSFER_SOUND = SOUND_EVENTS.register("wissen_transfer", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wissen_transfer")));
    public static final RegistryObject<SoundEvent> SPELL_CAST_SOUND = SOUND_EVENTS.register("spell_cast", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "spell_cast")));
    public static final RegistryObject<SoundEvent> SPELL_BURST_SOUND = SOUND_EVENTS.register("spell_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "spell_burst")));
    public static final RegistryObject<SoundEvent> SPELL_RELOAD_SOUND = SOUND_EVENTS.register("spell_reload", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "spell_reload")));
    public static final RegistryObject<SoundEvent> STEAM_BURST_SOUND = SOUND_EVENTS.register("steam_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "steam_burst")));

    public static final RegistryObject<ArgumentTypeInfo<?, ?>> KNOWLEDGE_ARG = ARG_TYPES.register("knowledge", () -> ArgumentTypeInfos.registerByClass(KnowledgeArgument.class, SingletonArgumentInfo.contextFree(KnowledgeArgument::knowledges)));
    public static final RegistryObject<ArgumentTypeInfo<?, ?>> SPELLS_ARG = ARG_TYPES.register("spell", () -> ArgumentTypeInfos.registerByClass(SpellArgument.class, SingletonArgumentInfo.contextFree(SpellArgument::spells)));
    public static final RegistryObject<ArgumentTypeInfo<?, ?>> ARCANE_ENCHANTMENT_ARG = ARG_TYPES.register("arcane_enchantment", () -> ArgumentTypeInfos.registerByClass(ArcaneEnchantmentArgument.class, SingletonArgumentInfo.contextFree(ArcaneEnchantmentArgument::arcaneEnchantments)));

    public static final RegistryObject<Attribute> WISSEN_DISCOUNT = ATTRIBUTES.register("wissen_discount", () -> new RangedAttribute("attribute.name.wizards_reborn.wissen_discount", 0, 0, 75).setSyncable(true));
    public static final RegistryObject<Attribute> MAGIC_ARMOR = ATTRIBUTES.register("magic_armor", () -> new RangedAttribute("attribute.name.wizards_reborn.magic_armor", 0, 0, 100).setSyncable(true));

    public static final RegistryObject<MobEffect> MOR_SPORES_EFFECT = EFFECTS.register("mor_spores", MorSporesEffect::new);
    public static final RegistryObject<MobEffect> WISSEN_AURA_EFFECT = EFFECTS.register("wissen_aura", WissenAuraEffect::new);

    public static final RegistryObject<TrunkPlacerType<ArcaneWoodTrunkPlacer>> ARCANE_WOOD_TRUNK_PLACER = TRUNK_PLACER_TYPES.register("arcane_wood_trunk_placer", () -> new TrunkPlacerType<>(ArcaneWoodTrunkPlacer.CODEC));

    public static final RegistryObject<BlockStateProviderType<?>> AN_STATEPROVIDER = BLOCK_STATE_PROVIDER_TYPE.register("an_stateprovider", () -> new BlockStateProviderType<>(SupplierBlockStateProvider.CODEC));

    //FLUIDS
    public static final RegistryObject<FlowingFluid> MUNDANE_BREW_FLUID = FLUIDS.register("mundane_brew", () -> new ForgeFlowingFluid.Source(WizardsReborn.MUNDANE_BREW_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MUNDANE_BREW_FLUID = FLUIDS.register("flowing_mundane_brew", () -> new ForgeFlowingFluid.Flowing(WizardsReborn.MUNDANE_BREW_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> ALCHEMY_OIL_FLUID = FLUIDS.register("alchemy_oil", () -> new ForgeFlowingFluid.Source(WizardsReborn.ALCHEMY_OIL_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_ALCHEMY_OIL_FLUID = FLUIDS.register("flowing_alchemy_oil", () -> new ForgeFlowingFluid.Flowing(WizardsReborn.ALCHEMY_OIL_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> OIL_TEA_FLUID = FLUIDS.register("oil_tea", () -> new ForgeFlowingFluid.Source(WizardsReborn.OIL_TEA_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_OIL_TEA_FLUID = FLUIDS.register("flowing_oil_tea", () -> new ForgeFlowingFluid.Flowing(WizardsReborn.OIL_TEA_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> WISSEN_TEA_FLUID = FLUIDS.register("wissen_tea", () -> new ForgeFlowingFluid.Source(WizardsReborn.WISSEN_TEA_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_WISSEN_TEA_FLUID = FLUIDS.register("flowing_wissen_tea", () -> new ForgeFlowingFluid.Flowing(WizardsReborn.WISSEN_TEA_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MUSHROOM_BREW_FLUID  = FLUIDS.register("mushroom_brew", () -> new ForgeFlowingFluid.Source(WizardsReborn.MUSHROOM_BREW_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MUSHROOM_BREW_FLUID = FLUIDS.register("flowing_mushroom_brew", () -> new ForgeFlowingFluid.Flowing(WizardsReborn.MUSHROOM_BREW_PROPERTIES));

    public static final RegistryObject<FlowingFluid> HELLISH_MUSHROOM_BREW_FLUID = FLUIDS.register("hellish_mushroom_brew", () -> new ForgeFlowingFluid.Source(WizardsReborn.HELLISH_MUSHROOM_BREW_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_HELLISH_MUSHROOM_BREW_FLUID = FLUIDS.register("flowing_hellish_mushroom_brew", () -> new ForgeFlowingFluid.Flowing(WizardsReborn.HELLISH_MUSHROOM_BREW_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> MOR_BREW_FLUID = FLUIDS.register("mor_brew", () -> new ForgeFlowingFluid.Source(WizardsReborn.MOR_BREW_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_MOR_BREW_FLUID = FLUIDS.register("flowing_mor_brew", () -> new ForgeFlowingFluid.Flowing(WizardsReborn.MOR_BREW_FLUID_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWER_BREW_FLUID = FLUIDS.register("flower_brew", () -> new ForgeFlowingFluid.Source(WizardsReborn.FLOWER_BREW_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> FLOWING_FLOWER_BREW_FLUID = FLUIDS.register("flowing_flower_brew", () -> new ForgeFlowingFluid.Flowing(WizardsReborn.FLOWER_BREW_FLUID_PROPERTIES));

    public static final RegistryObject<LiquidBlock> MUNDANE_BREW_FLUID_BLOCK = BLOCKS.register("mundane_brew_block", () -> new LiquidBlock(WizardsReborn.MUNDANE_BREW_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> ALCHEMY_OIL_FLUID_BLOCK = BLOCKS.register("alchemy_oil_block", () -> new LiquidBlock(WizardsReborn.ALCHEMY_OIL_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> OIL_TEA_FLUID_BLOCK = BLOCKS.register("oil_tea_block", () -> new LiquidBlock(WizardsReborn.OIL_TEA_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> WISSEN_TEA_FLUID_BLOCK = BLOCKS.register("wissen_tea_block", () -> new LiquidBlock(WizardsReborn.MUNDANE_BREW_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> MUSHROOM_BREW_FLUID_BLOCK = BLOCKS.register("mushroom_brew_block", () -> new LiquidBlock(WizardsReborn.WISSEN_TEA_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> HELLISH_MUSHROOM_BREW_FLUID_BLOCK = BLOCKS.register("hellish_mushroom_brew_block", () -> new LiquidBlock(WizardsReborn.HELLISH_MUSHROOM_BREW_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> MOR_BREW_FLUID_BLOCK = BLOCKS.register("mor_brew_block", () -> new LiquidBlock(WizardsReborn.MOR_BREW_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> FLOWER_BREW_FLUID_BLOCK = BLOCKS.register("flower_brew_block", () -> new LiquidBlock(WizardsReborn.FLOWER_BREW_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));

    public static final RegistryObject<Item> MUNDANE_BREW_BUCKET = ITEMS.register("mundane_brew_bucket", () -> new BucketItem(MUNDANE_BREW_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> ALCHEMY_OIL_BUCKET = ITEMS.register("alchemy_oil_bucket", () -> new BucketItem(ALCHEMY_OIL_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> OIL_TEA_BUCKET = ITEMS.register("oil_tea_bucket", () -> new BucketItem(OIL_TEA_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> WISSEN_TEA_BUCKET = ITEMS.register("wissen_tea_bucket", () -> new BucketItem(WISSEN_TEA_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MUSHROOM_BREW_BUCKET = ITEMS.register("mushroom_brew_bucket", () -> new BucketItem(MUSHROOM_BREW_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> HELLISH_MUSHROOM_BREW_BUCKET = ITEMS.register("hellish_mushroom_brew_bucket", () -> new BucketItem(HELLISH_MUSHROOM_BREW_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MOR_BREW_BUCKET = ITEMS.register("mor_brew_bucket", () -> new BucketItem(MOR_BREW_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> FLOWER_BREW_BUCKET = ITEMS.register("flower_brew_bucket", () -> new BucketItem(FLOWER_BREW_FLUID, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<FluidType> MUNDANE_BREW_FLUID_TYPE =  FLUID_TYPES.register("mundane_brew", () -> new CustomFluidType( new ResourceLocation("block/water_still"),  new ResourceLocation("block/water_flow"), new ResourceLocation("misc/underwater"),
            0xFF324B8D, new Vector3f(50f / 255f, 75f / 255f, 141f / 255f), FluidType.Properties.create().density(1).viscosity(15).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));
    public static final RegistryObject<FluidType> ALCHEMY_OIL_FLUID_TYPE =  FLUID_TYPES.register("alchemy_oil", () -> new CustomFluidType( new ResourceLocation("block/water_still"),  new ResourceLocation("block/water_flow"), new ResourceLocation("misc/underwater"),
            0xFF602F3B, new Vector3f(96f / 255f, 47f / 255f, 59f / 255f), FluidType.Properties.create().density(1).viscosity(15).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));
    public static final RegistryObject<FluidType> OIL_TEA_FLUID_TYPE =  FLUID_TYPES.register("oil_tea", () -> new CustomFluidType( new ResourceLocation("block/water_still"),  new ResourceLocation("block/water_flow"), new ResourceLocation("misc/underwater"),
            0xFFBD7C81, new Vector3f(189f / 255f, 124f / 255f, 129f / 255f), FluidType.Properties.create().density(1).viscosity(15).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));
    public static final RegistryObject<FluidType> WISSEN_TEA_FLUID_TYPE =  FLUID_TYPES.register("wissen_tea", () -> new CustomFluidType( new ResourceLocation("block/water_still"),  new ResourceLocation("block/water_flow"), new ResourceLocation("misc/underwater"),
            0xFF77A4D0, new Vector3f(119f / 255f, 164f / 255f, 208f / 255f), FluidType.Properties.create().density(1).viscosity(15).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));
    public static final RegistryObject<FluidType> MUSHROOM_BREW_FLUID_TYPE =  FLUID_TYPES.register("mushroom_brew", () -> new CustomFluidType( new ResourceLocation("block/water_still"),  new ResourceLocation("block/water_flow"), new ResourceLocation("misc/underwater"),
            0xFF8D6B53, new Vector3f(141f / 255f, 107f / 255f, 83f / 255f), FluidType.Properties.create().density(1).viscosity(15).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));
    public static final RegistryObject<FluidType> HELLISH_MUSHROOM_BREW_FLUID_TYPE =  FLUID_TYPES.register("hellish_mushroom_brew", () -> new CustomFluidType( new ResourceLocation("block/water_still"),  new ResourceLocation("block/water_flow"), new ResourceLocation("misc/underwater"),
            0xFF4E1B1B, new Vector3f(78f / 255f, 27f / 255f, 27f / 255f), FluidType.Properties.create().density(1).viscosity(15).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));
    public static final RegistryObject<FluidType> MOR_BREW_FLUID_TYPE =  FLUID_TYPES.register("mor_brew", () -> new CustomFluidType( new ResourceLocation("block/water_still"),  new ResourceLocation("block/water_flow"), new ResourceLocation("misc/underwater"),
            0xFF4D5474, new Vector3f(77f / 255f, 84f / 255f, 116f / 255f), FluidType.Properties.create().density(1).viscosity(15).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));
    public static final RegistryObject<FluidType> FLOWER_BREW_FLUID_TYPE =  FLUID_TYPES.register("flower_brew", () -> new CustomFluidType( new ResourceLocation("block/water_still"),  new ResourceLocation("block/water_flow"), new ResourceLocation("misc/underwater"),
            0xFF204426, new Vector3f(32f / 255f, 68f / 255f, 38f / 255f), FluidType.Properties.create().density(1).viscosity(15).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)));

    public static final ForgeFlowingFluid.Properties MUNDANE_BREW_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            MUNDANE_BREW_FLUID_TYPE, MUNDANE_BREW_FLUID, FLOWING_MUNDANE_BREW_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(1).block(MUNDANE_BREW_FLUID_BLOCK)
            .bucket(MUNDANE_BREW_BUCKET);
    public static final ForgeFlowingFluid.Properties ALCHEMY_OIL_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            ALCHEMY_OIL_FLUID_TYPE, ALCHEMY_OIL_FLUID, FLOWING_ALCHEMY_OIL_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(1).block(ALCHEMY_OIL_FLUID_BLOCK)
            .bucket(ALCHEMY_OIL_BUCKET);
    public static final ForgeFlowingFluid.Properties OIL_TEA_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            OIL_TEA_FLUID_TYPE, OIL_TEA_FLUID, FLOWING_OIL_TEA_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(1).block(OIL_TEA_FLUID_BLOCK)
            .bucket(OIL_TEA_BUCKET);
    public static final ForgeFlowingFluid.Properties WISSEN_TEA_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            WISSEN_TEA_FLUID_TYPE, WISSEN_TEA_FLUID, FLOWING_WISSEN_TEA_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(1).block(WISSEN_TEA_FLUID_BLOCK)
            .bucket(WISSEN_TEA_BUCKET);
    public static final ForgeFlowingFluid.Properties MUSHROOM_BREW_PROPERTIES = new ForgeFlowingFluid.Properties(
            MUSHROOM_BREW_FLUID_TYPE, MUSHROOM_BREW_FLUID, FLOWING_MUSHROOM_BREW_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(1).block(MUSHROOM_BREW_FLUID_BLOCK)
            .bucket(MUSHROOM_BREW_BUCKET);
    public static final ForgeFlowingFluid.Properties HELLISH_MUSHROOM_BREW_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            HELLISH_MUSHROOM_BREW_FLUID_TYPE, HELLISH_MUSHROOM_BREW_FLUID, FLOWING_HELLISH_MUSHROOM_BREW_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(1).block(HELLISH_MUSHROOM_BREW_FLUID_BLOCK)
            .bucket(HELLISH_MUSHROOM_BREW_BUCKET);
    public static final ForgeFlowingFluid.Properties MOR_BREW_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            MOR_BREW_FLUID_TYPE, MOR_BREW_FLUID, FLOWING_MOR_BREW_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(1).block(MOR_BREW_FLUID_BLOCK)
            .bucket(MOR_BREW_BUCKET);
    public static final ForgeFlowingFluid.Properties FLOWER_BREW_FLUID_PROPERTIES = new ForgeFlowingFluid.Properties(
            FLOWER_BREW_FLUID_TYPE,FLOWER_BREW_FLUID, FLOWING_FLOWER_BREW_FLUID)
            .slopeFindDistance(1).levelDecreasePerBlock(1).block(FLOWER_BREW_FLUID_BLOCK)
            .bucket(FLOWER_BREW_BUCKET);

    public WizardsReborn() {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BELT.getMessageBuilder().cosmetic().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.BODY.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.CHARM.getMessageBuilder().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.NECKLACE.getMessageBuilder().cosmetic().build());
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().cosmetic().build());
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
        SOUND_EVENTS.register(eventBus);
        ARG_TYPES.register(eventBus);
        ATTRIBUTES.register(eventBus);
        TRUNK_PLACER_TYPES.register(eventBus);
        BLOCK_STATE_PROVIDER_TYPE.register(eventBus);
        EFFECTS.register(eventBus);
        FLUID_TYPES.register(eventBus);
        FLUIDS.register(eventBus);

        if (CreateIntegration.isCreateLoaded()) CreateIntegration.init(eventBus);

        setupCrystals();
        setupMonograms();
        setupSpells();
        setupArcaneEnchantments();
        setupCrystalRituals();

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            setupWandCrystalsModels();

            forgeBus.addListener(ClientTickHandler::clientTickEnd);
            forgeBus.addListener(WorldRenderHandler::onRenderWorldLast);
            forgeBus.addListener(ClientWorldEvent::onTick);
            forgeBus.addListener(ClientWorldEvent::onRender);
            forgeBus.addListener(HUDEventHandler::onDrawScreenPost);
            forgeBus.addListener(TooltipEventHandler::onPostTooltipEvent);
            forgeBus.addListener(KeyBindHandler::onKeyPress);
            MinecraftForge.EVENT_BUS.register(new ClientEvents());
            return new Object();
        });

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
        RegisterAlchemyPotions.init();
        RegisterKnowledges.init();
        Researches.init();

        setupCrystalsItems();

        event.enqueueWork(() -> {
            AxeItem.STRIPPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPPABLES)
                    .put(ARCANE_WOOD_LOG.get(), STRIPPED_ARCANE_WOOD_LOG.get())
                    .put(ARCANE_WOOD.get(), STRIPPED_ARCANE_WOOD.get())
                    .put(INNOCENT_WOOD_LOG.get(), STRIPPED_INNOCENT_WOOD_LOG.get())
                    .put(INNOCENT_WOOD.get(), STRIPPED_INNOCENT_WOOD.get()).build();

            ArcanePedestalBlock.blocksList.put(ARCANE_PEDESTAL.get(), HOVERING_TOME_STAND.get());
            ArcanePedestalBlock.blocksList.put(WISESTONE_PEDESTAL.get(), WISESTONE_HOVERING_TOME_STAND.get());
            ArcanePedestalBlock.blocksList.put(INNOCENT_PEDESTAL.get(), INNOCENT_HOVERING_TOME_STAND.get());
            HoveringTomeStandBlock.blocksList.put(HOVERING_TOME_STAND.get(), ARCANE_PEDESTAL.get());
            HoveringTomeStandBlock.blocksList.put(WISESTONE_HOVERING_TOME_STAND.get(), WISESTONE_PEDESTAL.get());
            HoveringTomeStandBlock.blocksList.put(INNOCENT_HOVERING_TOME_STAND.get(), INNOCENT_PEDESTAL.get());

            MortarItem.mortarList.add(WizardsReborn.ARCANE_WOOD_MORTAR.get());
            MortarItem.mortarList.add(WizardsReborn.INNOCENT_WOOD_MORTAR.get());

            AlchemyPotionItem.potionList.add(WizardsReborn.ALCHEMY_VIAL_POTION.get());
            AlchemyPotionItem.potionList.add(WizardsReborn.ALCHEMY_FLASK_POTION.get());

            FireBlock fireblock = (FireBlock) Blocks.FIRE;
            fireblock.setFlammable(ARCANE_WOOD_LOG.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD.get(), 5, 20);
            fireblock.setFlammable(STRIPPED_ARCANE_WOOD_LOG.get(), 5, 20);
            fireblock.setFlammable(STRIPPED_ARCANE_WOOD.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD_STAIRS.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD_SLAB.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD_FENCE.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD_FENCE_GATE.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ARCANE_LINEN_HAY.get(), 60, 20);
            fireblock.setFlammable(PETALS_OF_INNOCENCE.get(), 60, 100);

            ComposterBlock.add(0.3F, ARCANE_WOOD_LEAVES_ITEM.get());
            ComposterBlock.add(0.3F, ARCANE_WOOD_SAPLING_ITEM.get());
            ComposterBlock.add(0.3F, ARCANE_LINEN_SEEDS.get());
            ComposterBlock.add(0.65F, ARCANE_LINEN_ITEM.get());
            ComposterBlock.add(0.85F, ARCANE_LINEN_HAY_ITEM.get());
            ComposterBlock.add(0.65F, MOR_ITEM.get());
            ComposterBlock.add(0.65F, ELDER_MOR_ITEM.get());
            ComposterBlock.add(0.85F, MOR_BLOCK_ITEM.get());
            ComposterBlock.add(0.85F, ELDER_MOR_BLOCK_ITEM.get());
            ComposterBlock.add(0.2F, PETALS.get());
            ComposterBlock.add(0.2F, GROUND_BROWN_MUSHROOM.get());
            ComposterBlock.add(0.2F, GROUND_RED_MUSHROOM.get());
            ComposterBlock.add(0.2F, GROUND_CRIMSON_FUNGUS.get());
            ComposterBlock.add(0.2F, GROUND_WARPED_FUNGUS.get());
            ComposterBlock.add(0.2F, GROUND_MOR.get());
            ComposterBlock.add(0.2F, GROUND_ELDER_MOR.get());
            ComposterBlock.add(0.3F, PETALS_OF_INNOCENCE.get());

            DispenserBlock.registerBehavior(ARCANUM_DUST.get(), new OptionalDispenseItemBehavior() {
                protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
                    this.setSuccess(true);
                    Level level = blockSource.getLevel();
                    BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
                    if (!ArcanumDustItem.executeTransmutation(itemStack, level, blockpos)) {
                        this.setSuccess(false);
                    }

                    return itemStack;
                }
            });
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        try {
            Class.forName("net.optifine.Config");
            WizardsRebornClient.optifinePresent = true;
        } catch (ClassNotFoundException e) {
            WizardsRebornClient.optifinePresent = false;
        }

        event.enqueueWork(() -> {
            ArcanemiconChapters.init();
            CrystalChooseScreen.initSpells();

            MenuScreens.register(ARCANE_WORKBENCH_CONTAINER.get(), ArcaneWorkbenchScreen::new);
            MenuScreens.register(JEWELER_TABLE_CONTAINER.get(), JewelerTableScreen::new);
            MenuScreens.register(ALCHEMY_FURNACE_CONTAINER.get(), AlchemyFurnaceScreen::new);
            MenuScreens.register(ALCHEMY_MACHINE_CONTAINER.get(), AlchemyMachineScreen::new);
            MenuScreens.register(ITEM_SORTER_CONTAINER.get(), ItemSorterScreen::new);
            MenuScreens.register(RUNIC_PEDESTAL_CONTAINER.get(), RunicPedestalScreen::new);

            CuriosRendererRegistry.register(LEATHER_BELT.get(), BeltRenderer::new);
            CuriosRendererRegistry.register(ARCANUM_AMULET.get(), AmuletRenderer::new);
            CuriosRendererRegistry.register(ARCACITE_AMULET.get(), AmuletRenderer::new);
            CuriosRendererRegistry.register(BROWN_MUSHROOM_CAP.get(), MushroomCapRenderer::new);
            CuriosRendererRegistry.register(RED_MUSHROOM_CAP.get(), MushroomCapRenderer::new);
            CuriosRendererRegistry.register(CRIMSON_FUNGUS_CAP.get(), MushroomCapRenderer::new);
            CuriosRendererRegistry.register(WARPED_FUNGUS_CAP.get(), MushroomCapRenderer::new);
            CuriosRendererRegistry.register(MOR_CAP.get(), MushroomCapRenderer::new);
            CuriosRendererRegistry.register(ELDER_MOR_CAP.get(), MushroomCapRenderer::new);
        });
    }

    public static void setupCrystals() {
        Crystals.registerPolishing(CRYSTAL_POLISHING_TYPE);
        Crystals.registerPolishing(FACETED_POLISHING_TYPE);
        Crystals.registerPolishing(ADVANCED_POLISHING_TYPE);
        Crystals.registerPolishing(MASTERFUL_POLISHING_TYPE);
        Crystals.registerPolishing(PURE_POLISHING_TYPE);

        Crystals.registerType(EARTH_CRYSTAL_TYPE);
        Crystals.registerType(WATER_CRYSTAL_TYPE);
        Crystals.registerType(AIR_CRYSTAL_TYPE);
        Crystals.registerType(FIRE_CRYSTAL_TYPE);
        Crystals.registerType(VOID_CRYSTAL_TYPE);
    }

    public static void setupMonograms() {
        Monograms.register(LUNAM_MONOGRAM);
        Monograms.register(VITA_MONOGRAM);
        Monograms.register(SOLEM_MONOGRAM);
        Monograms.register(MORS_MONOGRAM);
        Monograms.register(MIRACULUM_MONOGRAM);
        Monograms.register(TEMPUS_MONOGRAM);
        Monograms.register(STATERA_MONOGRAM);
        Monograms.register(ECLIPSIS_MONOGRAM);
        Monograms.register(SICCITAS_MONOGRAM);
        Monograms.register(SOLSTITIUM_MONOGRAM);
        Monograms.register(FAMES_MONOGRAM);
        Monograms.register(RENAISSANCE_MONOGRAM);
        Monograms.register(BELLUM_MONOGRAM);
        Monograms.register(LUX_MONOGRAM);
        Monograms.register(KARA_MONOGRAM);
        Monograms.register(DEGRADATIO_MONOGRAM);
        Monograms.register(PRAEDICTIONEM_MONOGRAM);
        Monograms.register(EVOLUTIONIS_MONOGRAM);
        Monograms.register(TENEBRIS_MONOGRAM);
        Monograms.register(UNIVERSUM_MONOGRAM);

        Monograms.addRecipe(new MonogramRecipe(MIRACULUM_MONOGRAM, LUNAM_MONOGRAM, VITA_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TEMPUS_MONOGRAM, LUNAM_MONOGRAM, SOLEM_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(STATERA_MONOGRAM, MORS_MONOGRAM, VITA_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(ECLIPSIS_MONOGRAM, MORS_MONOGRAM, LUNAM_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(SICCITAS_MONOGRAM, SOLEM_MONOGRAM, MORS_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(SOLSTITIUM_MONOGRAM, SOLEM_MONOGRAM, VITA_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(FAMES_MONOGRAM, TEMPUS_MONOGRAM, SICCITAS_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(RENAISSANCE_MONOGRAM, TEMPUS_MONOGRAM, MORS_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(BELLUM_MONOGRAM, SOLSTITIUM_MONOGRAM, MORS_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(LUX_MONOGRAM, VITA_MONOGRAM, SOLSTITIUM_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(KARA_MONOGRAM, SICCITAS_MONOGRAM, STATERA_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(DEGRADATIO_MONOGRAM, VITA_MONOGRAM, ECLIPSIS_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(PRAEDICTIONEM_MONOGRAM, SOLSTITIUM_MONOGRAM, MIRACULUM_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(EVOLUTIONIS_MONOGRAM, VITA_MONOGRAM, STATERA_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(TENEBRIS_MONOGRAM, SOLEM_MONOGRAM, ECLIPSIS_MONOGRAM));
        Monograms.addRecipe(new MonogramRecipe(UNIVERSUM_MONOGRAM, STATERA_MONOGRAM, TEMPUS_MONOGRAM));
    }

    public static void setupSpells() {
        Spells.register(EARTH_PROJECTILE_SPELL);
        Spells.register(WATER_PROJECTILE_SPELL);
        Spells.register(AIR_PROJECTILE_SPELL);
        Spells.register(FIRE_PROJECTILE_SPELL);
        Spells.register(VOID_PROJECTILE_SPELL);
        Spells.register(FROST_PROJECTILE_SPELL);
        Spells.register(HOLY_PROJECTILE_SPELL);
        Spells.register(CURSE_PROJECTILE_SPELL);
        Spells.register(EARTH_RAY_SPELL);
        Spells.register(WATER_RAY_SPELL);
        Spells.register(AIR_RAY_SPELL);
        Spells.register(FIRE_RAY_SPELL);
        Spells.register(VOID_RAY_SPELL);
        Spells.register(FROST_RAY_SPELL);
        Spells.register(HOLY_RAY_SPELL);
        Spells.register(CURSE_RAY_SPELL);
        Spells.register(EARTH_CHARGE_SPELL);
        Spells.register(WATER_CHARGE_SPELL);
        Spells.register(AIR_CHARGE_SPELL);
        Spells.register(FIRE_CHARGE_SPELL);
        Spells.register(VOID_CHARGE_SPELL);
        Spells.register(FROST_CHARGE_SPELL);
        Spells.register(HOLY_CHARGE_SPELL);
        Spells.register(CURSE_CHARGE_SPELL);
        Spells.register(HEART_OF_NATURE_SPELL);
        Spells.register(WATER_BREATHING_SPELL);
        Spells.register(AIR_FLOW_SPELL);
        Spells.register(FIRE_SHIELD_SPELL);
        Spells.register(MAGIC_SPROUT_SPELL);
    }

    public static void setupArcaneEnchantments() {
        ArcaneEnchantments.register(WISSEN_MENDING_ARCANE_ENCHANTMENT);
        ArcaneEnchantments.register(MAGIC_BLADE_ARCANE_ENCHANTMENT);
    }

    public static void setupCrystalRituals() {
        CrystalRituals.register(EMPTY_CRYSTAL_RITUAL);
        CrystalRituals.register(ARTIFICIAL_FERTILITY_CRYSTAL_RITUAL);
        CrystalRituals.register(RITUAL_BREEDING_CRYSTAL_RITUAL);
        CrystalRituals.register(CRYSTAL_GROWTH_ACCELERATION_CRYSTAL_RITUAL);
        CrystalRituals.register(CRYSTAL_INFUSION_CRYSTAL_RITUAL);
        //CrystalRituals.register(STONE_CALENDAR_CRYSTAL_RITUAL);
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

    public static void setupCrystalsItems() {
        Crystals.addItem(EARTH_CRYSTAL.get());
        Crystals.addItem(WATER_CRYSTAL.get());
        Crystals.addItem(AIR_CRYSTAL.get());
        Crystals.addItem(FIRE_CRYSTAL.get());
        Crystals.addItem(VOID_CRYSTAL.get());

        Crystals.addItem(FACETED_EARTH_CRYSTAL.get());
        Crystals.addItem(FACETED_WATER_CRYSTAL.get());
        Crystals.addItem(FACETED_AIR_CRYSTAL.get());
        Crystals.addItem(FACETED_FIRE_CRYSTAL.get());
        Crystals.addItem(FACETED_VOID_CRYSTAL.get());

        Crystals.addItem(ADVANCED_EARTH_CRYSTAL.get());
        Crystals.addItem(ADVANCED_WATER_CRYSTAL.get());
        Crystals.addItem(ADVANCED_AIR_CRYSTAL.get());
        Crystals.addItem(ADVANCED_FIRE_CRYSTAL.get());
        Crystals.addItem(ADVANCED_VOID_CRYSTAL.get());

        Crystals.addItem(MASTERFUL_EARTH_CRYSTAL.get());
        Crystals.addItem(MASTERFUL_WATER_CRYSTAL.get());
        Crystals.addItem(MASTERFUL_AIR_CRYSTAL.get());
        Crystals.addItem(MASTERFUL_FIRE_CRYSTAL.get());
        Crystals.addItem(MASTERFUL_VOID_CRYSTAL.get());

        Crystals.addItem(PURE_EARTH_CRYSTAL.get());
        Crystals.addItem(PURE_WATER_CRYSTAL.get());
        Crystals.addItem(PURE_AIR_CRYSTAL.get());
        Crystals.addItem(PURE_FIRE_CRYSTAL.get());
        Crystals.addItem(PURE_VOID_CRYSTAL.get());
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerCaps(RegisterCapabilitiesEvent event) {
            event.register(IKnowledge.class);
        }
    }
}