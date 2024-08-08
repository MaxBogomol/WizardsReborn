package mod.maxbogomol.wizards_reborn;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentType;
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
import mod.maxbogomol.wizards_reborn.api.skin.Skin;
import mod.maxbogomol.wizards_reborn.api.skin.Skins;
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
import mod.maxbogomol.wizards_reborn.client.render.curio.*;
import mod.maxbogomol.wizards_reborn.client.render.item.WandCrystalsModels;
import mod.maxbogomol.wizards_reborn.common.alchemypotion.RegisterAlchemyPotions;
import mod.maxbogomol.wizards_reborn.common.arcaneenchantment.*;
import mod.maxbogomol.wizards_reborn.common.block.*;
import mod.maxbogomol.wizards_reborn.common.capability.IKnowledge;
import mod.maxbogomol.wizards_reborn.common.command.ArcaneEnchantmentArgument;
import mod.maxbogomol.wizards_reborn.common.command.KnowledgeArgument;
import mod.maxbogomol.wizards_reborn.common.command.SpellArgument;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.config.ServerConfig;
import mod.maxbogomol.wizards_reborn.common.crystal.*;
import mod.maxbogomol.wizards_reborn.common.crystalritual.ArtificialFertilityCrystalRitual;
import mod.maxbogomol.wizards_reborn.common.crystalritual.CrystalGrowthAccelerationCrystalRitual;
import mod.maxbogomol.wizards_reborn.common.crystalritual.CrystalInfusionCrystalRitual;
import mod.maxbogomol.wizards_reborn.common.crystalritual.RitualBreedingCrystalRitual;
import mod.maxbogomol.wizards_reborn.common.effect.IrritationEffect;
import mod.maxbogomol.wizards_reborn.common.effect.MorSporesEffect;
import mod.maxbogomol.wizards_reborn.common.effect.TipsyEffect;
import mod.maxbogomol.wizards_reborn.common.effect.WissenAuraEffect;
import mod.maxbogomol.wizards_reborn.common.entity.*;
import mod.maxbogomol.wizards_reborn.common.event.Events;
import mod.maxbogomol.wizards_reborn.common.fluid.CustomFluidType;
import mod.maxbogomol.wizards_reborn.common.integration.create.CreateIntegration;
import mod.maxbogomol.wizards_reborn.common.integration.farmersdelight.FarmersDelightIntegration;
import mod.maxbogomol.wizards_reborn.common.item.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcanewood.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.innocentwood.*;
import mod.maxbogomol.wizards_reborn.common.itemgroup.WizardsRebornItemGroup;
import mod.maxbogomol.wizards_reborn.common.knowledge.RegisterKnowledges;
import mod.maxbogomol.wizards_reborn.common.knowledge.Researches;
import mod.maxbogomol.wizards_reborn.common.loot.AddItemListLootModifier;
import mod.maxbogomol.wizards_reborn.common.loot.AddItemLootModifier;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.proxy.ClientProxy;
import mod.maxbogomol.wizards_reborn.common.proxy.ISidedProxy;
import mod.maxbogomol.wizards_reborn.common.proxy.ServerProxy;
import mod.maxbogomol.wizards_reborn.common.recipe.*;
import mod.maxbogomol.wizards_reborn.common.skin.*;
import mod.maxbogomol.wizards_reborn.common.spell.aura.*;
import mod.maxbogomol.wizards_reborn.common.spell.block.*;
import mod.maxbogomol.wizards_reborn.common.spell.charge.*;
import mod.maxbogomol.wizards_reborn.common.spell.fog.MorSwarmSpell;
import mod.maxbogomol.wizards_reborn.common.spell.look.BlinkSpell;
import mod.maxbogomol.wizards_reborn.common.spell.look.CrystalCrushingSpell;
import mod.maxbogomol.wizards_reborn.common.spell.look.WisdomSpell;
import mod.maxbogomol.wizards_reborn.common.spell.look.cloud.RainCloudSpell;
import mod.maxbogomol.wizards_reborn.common.spell.look.cloud.ToxicRainSpell;
import mod.maxbogomol.wizards_reborn.common.spell.look.entity.*;
import mod.maxbogomol.wizards_reborn.common.spell.look.strike.IncinerationSpell;
import mod.maxbogomol.wizards_reborn.common.spell.look.strike.RenunciationSpell;
import mod.maxbogomol.wizards_reborn.common.spell.look.strike.RepentanceSpell;
import mod.maxbogomol.wizards_reborn.common.spell.projectile.*;
import mod.maxbogomol.wizards_reborn.common.spell.ray.*;
import mod.maxbogomol.wizards_reborn.common.spell.self.AirFlowSpell;
import mod.maxbogomol.wizards_reborn.common.spell.self.FireShieldSpell;
import mod.maxbogomol.wizards_reborn.common.spell.self.HeartOfNatureSpell;
import mod.maxbogomol.wizards_reborn.common.spell.self.WaterBreathingSpell;
import mod.maxbogomol.wizards_reborn.common.spell.sound.BoomSoundSpell;
import mod.maxbogomol.wizards_reborn.common.spell.sound.MoaiSoundSpell;
import mod.maxbogomol.wizards_reborn.common.spell.sound.PipeSoundSpell;
import mod.maxbogomol.wizards_reborn.common.tileentity.*;
import mod.maxbogomol.wizards_reborn.common.world.tree.ArcaneWoodTree;
import mod.maxbogomol.wizards_reborn.common.world.tree.ArcaneWoodTrunkPlacer;
import mod.maxbogomol.wizards_reborn.common.world.tree.InnocentWoodTree;
import mod.maxbogomol.wizards_reborn.common.world.tree.SupplierBlockStateProvider;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
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
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.DistExecutor;
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
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.awt.*;

@Mod("wizards_reborn")
public class WizardsReborn {
    public static final String MOD_ID = "wizards_reborn";
    public static final int VERSION_NUMBER = 19;

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
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);

    public static final TagKey<Item> SCYTHES_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "scythes"));
    public static final TagKey<Item> ARCANE_GOLD_TOOLS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "arcane_gold_tools"));
    public static final TagKey<Item> ARCANE_WOOD_LOGS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "arcane_wood_logs"));
    public static final TagKey<Item> ARCANE_WOOD_TOOLS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "arcane_wood_tools"));
    public static final TagKey<Item> INNOCENT_WOOD_LOGS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "innocent_wood_logs"));
    public static final TagKey<Item> INNOCENT_WOOD_TOOLS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "innocent_wood_tools"));
    public static final TagKey<Item> ARCANE_LUMOS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "arcane_lumos"));
    public static final TagKey<Item> CRYSTALS_SEEDS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "crystal_seeds"));
    public static final TagKey<Item> FRACTURED_CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "fractured_crystals"));
    public static final TagKey<Item> CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "crystals"));
    public static final TagKey<Item> FACETED_CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "faceted_crystals"));
    public static final TagKey<Item> ADVANCED_CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "advanced_crystals"));
    public static final TagKey<Item> MASTERFUL_CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "masterful_crystals"));
    public static final TagKey<Item> PURE_CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "pure_crystals"));
    public static final TagKey<Item> ALL_CRYSTALS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "all_crystals"));
    public static final TagKey<Item> CRYSTAL_BAG_SLOTS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "crystal_bag_slots"));
    public static final TagKey<Item> ALCHEMY_BAG_SLOTS_ITEM_TAG = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "alchemy_bag_slots"));
    public static final TagKey<Item> WISSEN_CASINGS_ITEM_TAG  = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "wissen_casings"));
    public static final TagKey<Item> LIGHT_CASINGS_ITEM_TAG  = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "light_casings"));
    public static final TagKey<Item> FLUID_CASINGS_ITEM_TAG  = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "fluid_casings"));
    public static final TagKey<Item> STEAM_CASINGS_ITEM_TAG  = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "steam_casings"));
    public static final TagKey<Item> SNIFFALO_FOOD_ITEM_TAG  = TagKey.create(Registries.ITEM, new ResourceLocation(MOD_ID, "sniffalo_food"));

    public static final TagKey<Block> FLUID_PIPE_CONNECTION_BLOCK_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "fluid_pipe_connection"));
    public static final TagKey<Block> FLUID_PIPE_CONNECTION_TOGGLE_BLOCK_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "fluid_pipe_connection_toggle"));
    public static final TagKey<Block> STEAM_PIPE_CONNECTION_BLOCK_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "steam_pipe_connection"));
    public static final TagKey<Block> STEAM_PIPE_CONNECTION_TOGGLE_BLOCK_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "steam_pipe_connection_toggle"));
    public static final TagKey<Block> EXTRACTOR_LEVER_CONNECTION_BLOCK_TAG = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "extractor_lever_connection"));
    public static final TagKey<Block> ALTAR_OF_DROUGHT_TARGET_BLOCK_TAG  = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "altar_of_drought_target"));
    public static final TagKey<Block> ORES_BLOCK_TAG  = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", "ores"));
    public static final TagKey<Block> CORK_BAMBOO_PLANTABLE_ON_BLOCK_TAG  = TagKey.create(Registries.BLOCK, new ResourceLocation(MOD_ID, "cork_bamboo_plantable_on"));

    public static final TagKey<Fluid> STEAM_SOURCE_FLUID_TAG = TagKey.create(Registries.FLUID, new ResourceLocation(MOD_ID, "steam_source"));
    public static final TagKey<Fluid> HEAT_SOURCE_FLUID_TAG = TagKey.create(Registries.FLUID, new ResourceLocation(MOD_ID, "heat_source"));

    public static final TagKey<BannerPattern> VIOLENCE_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/violence"));
    public static final TagKey<BannerPattern> REPRODUCTION_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/reproduction"));
    public static final TagKey<BannerPattern> COOPERATION_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/cooperation"));
    public static final TagKey<BannerPattern> HUNGER_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/hunger"));
    public static final TagKey<BannerPattern> SURVIVAL_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/survival"));
    public static final TagKey<BannerPattern> ELEVATION_BANNER_PATTERN_TAG = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(MOD_ID, "pattern_item/elevation"));

    public static final TagKey<DamageType> MAGIC_DAMAGE_TYPE_TAG = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("forge", "is_magic"));
    public static final TagKey<DamageType> ARCANE_MAGIC_DAMAGE_TYPE_TAG = TagKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(MOD_ID, "is_arcane_magic"));

    public static final ResourceLocation SNIFFALO_DIGGING_LOOT_TABLE = new ResourceLocation(MOD_ID, "gameplay/sniffalo_digging");

    public static final RegistryObject<SoundEvent> MUSIC_DISC_ARCANUM_SOUND = SOUND_EVENTS.register("arcanum_swinging", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanum_swinging")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_MOR_SOUND = SOUND_EVENTS.register("mor_marsh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mor_marsh")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_REBORN_SOUND = SOUND_EVENTS.register("reborn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "reborn")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_SHIMMER_SOUND = SOUND_EVENTS.register("blue_shimmer", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "blue_shimmer")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_CAPITALISM_SOUND = SOUND_EVENTS.register("battle_against_a_true_capitalist", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "battle_against_a_true_capitalist")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_PANACHE_SOUND = SOUND_EVENTS.register("magical_panache", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "magical_panache")));

    public static final RegistryObject<SoundEvent> ARCANE_GOLD_BREAK_SOUND = SOUND_EVENTS.register("arcane_gold_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_gold_break")));
    public static final RegistryObject<SoundEvent> ARCANE_GOLD_STEP_SOUND = SOUND_EVENTS.register("arcane_gold_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_gold_step")));
    public static final RegistryObject<SoundEvent> ARCANE_GOLD_PLACE_SOUND = SOUND_EVENTS.register("arcane_gold_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_gold_place")));
    public static final RegistryObject<SoundEvent> ARCANE_GOLD_HIT_SOUND = SOUND_EVENTS.register("arcane_gold_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_gold_hit")));

    public static final RegistryObject<SoundEvent> ARCANE_GOLD_ORE_BREAK_SOUND = SOUND_EVENTS.register("arcane_gold_ore_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_gold_ore_break")));
    public static final RegistryObject<SoundEvent> ARCANE_GOLD_ORE_STEP_SOUND = SOUND_EVENTS.register("arcane_gold_ore_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_gold_ore_step")));
    public static final RegistryObject<SoundEvent> ARCANE_GOLD_ORE_PLACE_SOUND = SOUND_EVENTS.register("arcane_gold_ore_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_gold_ore_place")));
    public static final RegistryObject<SoundEvent> ARCANE_GOLD_ORE_HIT_SOUND = SOUND_EVENTS.register("arcane_gold_ore_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_gold_ore_hit")));

    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANE_GOLD_ORE_BREAK_SOUND = SOUND_EVENTS.register("deepslate_arcane_gold_ore_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "deepslate_arcane_gold_ore_break")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANE_GOLD_ORE_STEP_SOUND = SOUND_EVENTS.register("deepslate_arcane_gold_ore_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "deepslate_arcane_gold_ore_step")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANE_GOLD_ORE_PLACE_SOUND = SOUND_EVENTS.register("deepslate_arcane_gold_ore_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "deepslate_arcane_gold_ore_place")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANE_GOLD_ORE_HIT_SOUND = SOUND_EVENTS.register("deepslate_arcane_gold_ore_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "deepslate_arcane_gold_ore_hit")));

    public static final RegistryObject<SoundEvent> NETHER_ARCANE_GOLD_ORE_BREAK_SOUND = SOUND_EVENTS.register("nether_arcane_gold_ore_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "nether_arcane_gold_ore_break")));
    public static final RegistryObject<SoundEvent> NETHER_ARCANE_GOLD_ORE_STEP_SOUND = SOUND_EVENTS.register("nether_arcane_gold_ore_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "nether_arcane_gold_ore_step")));
    public static final RegistryObject<SoundEvent> NETHER_ARCANE_GOLD_ORE_PLACE_SOUND = SOUND_EVENTS.register("nether_arcane_gold_ore_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "nether_arcane_gold_ore_place")));
    public static final RegistryObject<SoundEvent> NETHER_ARCANE_GOLD_ORE_HIT_SOUND = SOUND_EVENTS.register("nether_arcane_gold_ore_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "nether_arcane_gold_ore_hit")));

    public static final RegistryObject<SoundEvent> RAW_ARCANE_GOLD_BREAK_SOUND = SOUND_EVENTS.register("raw_arcane_gold_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "raw_arcane_gold_break")));
    public static final RegistryObject<SoundEvent> RAW_ARCANE_GOLD_STEP_SOUND = SOUND_EVENTS.register("raw_arcane_gold_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "raw_arcane_gold_step")));
    public static final RegistryObject<SoundEvent> RAW_ARCANE_GOLD_PLACE_SOUND = SOUND_EVENTS.register("raw_arcane_gold_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "raw_arcane_gold_place")));
    public static final RegistryObject<SoundEvent> RAW_ARCANE_GOLD_HIT_SOUND = SOUND_EVENTS.register("raw_arcane_gold_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "raw_arcane_gold_hit")));

    public static final RegistryObject<SoundEvent> SARCON_BREAK_SOUND = SOUND_EVENTS.register("sarcon_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "sarcon_break")));
    public static final RegistryObject<SoundEvent> SARCON_STEP_SOUND = SOUND_EVENTS.register("sarcon_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "sarcon_step")));
    public static final RegistryObject<SoundEvent> SARCON_PLACE_SOUND = SOUND_EVENTS.register("sarcon_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "sarcon_place")));
    public static final RegistryObject<SoundEvent> SARCON_HIT_SOUND = SOUND_EVENTS.register("sarcon_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "sarcon_hit")));

    public static final RegistryObject<SoundEvent> VILENIUM_BREAK_SOUND = SOUND_EVENTS.register("vilenium_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "vilenium_break")));
    public static final RegistryObject<SoundEvent> VILENIUM_STEP_SOUND = SOUND_EVENTS.register("vilenium_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "vilenium_step")));
    public static final RegistryObject<SoundEvent> VILENIUM_PLACE_SOUND = SOUND_EVENTS.register("vilenium_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "vilenium_place")));
    public static final RegistryObject<SoundEvent> VILENIUM_HIT_SOUND = SOUND_EVENTS.register("vilenium_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "vilenium_hit")));

    public static final RegistryObject<SoundEvent> ARCANUM_BREAK_SOUND = SOUND_EVENTS.register("arcanum_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanum_break")));
    public static final RegistryObject<SoundEvent> ARCANUM_STEP_SOUND = SOUND_EVENTS.register("arcanum_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanum_step")));
    public static final RegistryObject<SoundEvent> ARCANUM_PLACE_SOUND = SOUND_EVENTS.register("arcanum_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanum_place")));
    public static final RegistryObject<SoundEvent> ARCANUM_HIT_SOUND = SOUND_EVENTS.register("arcanum_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanum_hit")));

    public static final RegistryObject<SoundEvent> ARCANUM_ORE_BREAK_SOUND = SOUND_EVENTS.register("arcanum_ore_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanum_ore_break")));
    public static final RegistryObject<SoundEvent> ARCANUM_ORE_STEP_SOUND = SOUND_EVENTS.register("arcanum_ore_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanum_ore_step")));
    public static final RegistryObject<SoundEvent> ARCANUM_ORE_PLACE_SOUND = SOUND_EVENTS.register("arcanum_ore_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanum_ore_place")));
    public static final RegistryObject<SoundEvent> ARCANUM_ORE_HIT_SOUND = SOUND_EVENTS.register("arcanum_ore_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanum_ore_hit")));

    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANUM_ORE_BREAK_SOUND = SOUND_EVENTS.register("deepslate_arcanum_ore_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "deepslate_arcanum_ore_break")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANUM_ORE_STEP_SOUND = SOUND_EVENTS.register("deepslate_arcanum_ore_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "deepslate_arcanum_ore_step")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANUM_ORE_PLACE_SOUND = SOUND_EVENTS.register("deepslate_arcanum_ore_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "deepslate_arcanum_ore_place")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANUM_ORE_HIT_SOUND = SOUND_EVENTS.register("deepslate_arcanum_ore_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "deepslate_arcanum_ore_hit")));

    public static final RegistryObject<SoundEvent> ARCACITE_BREAK_SOUND = SOUND_EVENTS.register("arcacite_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcacite_break")));
    public static final RegistryObject<SoundEvent> ARCACITE_STEP_SOUND = SOUND_EVENTS.register("arcacite_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcacite_step")));
    public static final RegistryObject<SoundEvent> ARCACITE_PLACE_SOUND = SOUND_EVENTS.register("arcacite_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcacite_place")));
    public static final RegistryObject<SoundEvent> ARCACITE_HIT_SOUND = SOUND_EVENTS.register("arcacite_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcacite_hit")));

    public static final RegistryObject<SoundEvent> PRECISION_CRYSTAL_BREAK_SOUND = SOUND_EVENTS.register("precision_crystal_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "precision_crystal_break")));
    public static final RegistryObject<SoundEvent> PRECISION_CRYSTAL_STEP_SOUND = SOUND_EVENTS.register("precision_crystal_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "precision_crystal_step")));
    public static final RegistryObject<SoundEvent> PRECISION_CRYSTAL_PLACE_SOUND = SOUND_EVENTS.register("precision_crystal_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "precision_crystal_place")));
    public static final RegistryObject<SoundEvent> PRECISION_CRYSTAL_HIT_SOUND = SOUND_EVENTS.register("precision_crystal_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "precision_crystal_hit")));

    public static final RegistryObject<SoundEvent> NETHER_SALT_BREAK_SOUND = SOUND_EVENTS.register("nether_salt_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "nether_salt_break")));
    public static final RegistryObject<SoundEvent> NETHER_SALT_STEP_SOUND = SOUND_EVENTS.register("nether_salt_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "nether_salt_step")));
    public static final RegistryObject<SoundEvent> NETHER_SALT_PLACE_SOUND = SOUND_EVENTS.register("nether_salt_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "nether_salt_place")));
    public static final RegistryObject<SoundEvent> NETHER_SALT_HIT_SOUND = SOUND_EVENTS.register("nether_salt_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "nether_salt_hit")));

    public static final RegistryObject<SoundEvent> NETHER_SALT_ORE_BREAK_SOUND = SOUND_EVENTS.register("nether_salt_ore_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "nether_salt_ore_break")));
    public static final RegistryObject<SoundEvent> NETHER_SALT_ORE_STEP_SOUND = SOUND_EVENTS.register("nether_salt_ore_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "nether_salt_ore_step")));
    public static final RegistryObject<SoundEvent> NETHER_SALT_ORE_PLACE_SOUND = SOUND_EVENTS.register("nether_salt_ore_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "nether_salt_ore_place")));
    public static final RegistryObject<SoundEvent> NETHER_SALT_ORE_HIT_SOUND = SOUND_EVENTS.register("nether_salt_ore_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "nether_salt_ore_hit")));

    public static final RegistryObject<SoundEvent> ARCANE_WOOD_BREAK_SOUND = SOUND_EVENTS.register("arcane_wood_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_break")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_STEP_SOUND = SOUND_EVENTS.register("arcane_wood_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_step")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_PLACE_SOUND = SOUND_EVENTS.register("arcane_wood_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_place")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_HIT_SOUND = SOUND_EVENTS.register("arcane_wood_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_hit")));

    public static final RegistryObject<SoundEvent> ARCANE_WOOD_HANGING_SIGN_BREAK_SOUND = SOUND_EVENTS.register("arcane_wood_hanging_sign_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_hanging_sign_break")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_HANGING_SIGN_STEP_SOUND = SOUND_EVENTS.register("arcane_wood_hanging_sign_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_hanging_sign_step")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_HANGING_SIGN_PLACE_SOUND = SOUND_EVENTS.register("arcane_wood_hanging_sign_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_hanging_sign_place")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_HANGING_SIGN_HIT_SOUND = SOUND_EVENTS.register("arcane_wood_hanging_sign_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_hanging_sign_hit")));

    public static final RegistryObject<SoundEvent> ARCANE_WOOD_BUTTON_CLICK_OFF_SOUND = SOUND_EVENTS.register("arcane_wood_button_click_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_button_click_off")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_BUTTON_CLICK_ON_SOUND = SOUND_EVENTS.register("arcane_wood_button_click_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_button_click_on")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_PRESSURE_PLATE_CLICK_OFF_SOUND = SOUND_EVENTS.register("arcane_wood_pressure_plate_click_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_pressure_plate_click_off")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_PRESSURE_PLATE_CLICK_ON_SOUND = SOUND_EVENTS.register("arcane_wood_pressure_plate_click_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_pressure_plate_click_on")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_FENCE_GATE_CLOSE_SOUND = SOUND_EVENTS.register("arcane_wood_fence_gate_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_fence_gate_close")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_FENCE_GATE_OPEN_SOUND = SOUND_EVENTS.register("arcane_wood_fence_gate_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_fence_gate_open")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_DOOR_CLOSE_SOUND = SOUND_EVENTS.register("arcane_wood_door_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_door_close")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_DOOR_OPEN_SOUND = SOUND_EVENTS.register("arcane_wood_door_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_door_open")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_TRAPDOOR_CLOSE_SOUND = SOUND_EVENTS.register("arcane_wood_trapdoor_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_trapdoor_close")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_TRAPDOOR_OPEN_SOUND = SOUND_EVENTS.register("arcane_wood_trapdoor_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_wood_trapdoor_open")));

    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_BREAK_SOUND = SOUND_EVENTS.register("innocent_wood_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_break")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_STEP_SOUND = SOUND_EVENTS.register("innocent_wood_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_step")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_PLACE_SOUND = SOUND_EVENTS.register("innocent_wood_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_place")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_HIT_SOUND = SOUND_EVENTS.register("innocent_wood_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_hit")));

    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_HANGING_SIGN_BREAK_SOUND = SOUND_EVENTS.register("innocent_wood_hanging_sign_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_hanging_sign_break")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_HANGING_SIGN_STEP_SOUND = SOUND_EVENTS.register("innocent_wood_hanging_sign_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_hanging_sign_step")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_HANGING_SIGN_PLACE_SOUND = SOUND_EVENTS.register("innocent_wood_hanging_sign_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_hanging_sign_place")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_HANGING_SIGN_HIT_SOUND = SOUND_EVENTS.register("innocent_wood_hanging_sign_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_hanging_sign_hit")));

    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_BUTTON_CLICK_OFF_SOUND = SOUND_EVENTS.register("innocent_wood_button_click_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_button_click_off")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_BUTTON_CLICK_ON_SOUND = SOUND_EVENTS.register("innocent_wood_button_click_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_button_click_on")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_PRESSURE_PLATE_CLICK_OFF_SOUND = SOUND_EVENTS.register("innocent_wood_pressure_plate_click_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_pressure_plate_click_off")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_PRESSURE_PLATE_CLICK_ON_SOUND = SOUND_EVENTS.register("innocent_wood_pressure_plate_click_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_pressure_plate_click_on")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_FENCE_GATE_CLOSE_SOUND = SOUND_EVENTS.register("innocent_wood_fence_gate_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_fence_gate_close")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_FENCE_GATE_OPEN_SOUND = SOUND_EVENTS.register("innocent_wood_fence_gate_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_fence_gate_open")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_DOOR_CLOSE_SOUND = SOUND_EVENTS.register("innocent_wood_door_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_door_close")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_DOOR_OPEN_SOUND = SOUND_EVENTS.register("innocent_wood_door_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_door_open")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_TRAPDOOR_CLOSE_SOUND = SOUND_EVENTS.register("innocent_wood_trapdoor_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_trapdoor_close")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_TRAPDOOR_OPEN_SOUND = SOUND_EVENTS.register("innocent_wood_trapdoor_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "innocent_wood_trapdoor_open")));

    public static final RegistryObject<SoundEvent> WISESTONE_BREAK_SOUND = SOUND_EVENTS.register("wisestone_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_break")));
    public static final RegistryObject<SoundEvent> WISESTONE_STEP_SOUND = SOUND_EVENTS.register("wisestone_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_step")));
    public static final RegistryObject<SoundEvent> WISESTONE_PLACE_SOUND = SOUND_EVENTS.register("wisestone_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_place")));
    public static final RegistryObject<SoundEvent> WISESTONE_HIT_SOUND = SOUND_EVENTS.register("wisestone_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_hit")));

    public static final RegistryObject<SoundEvent> POLISHED_WISESTONE_BREAK_SOUND = SOUND_EVENTS.register("polished_wisestone_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "polished_wisestone_break")));
    public static final RegistryObject<SoundEvent> POLISHED_WISESTONE_STEP_SOUND = SOUND_EVENTS.register("polished_wisestone_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "polished_wisestone_step")));
    public static final RegistryObject<SoundEvent> POLISHED_WISESTONE_PLACE_SOUND = SOUND_EVENTS.register("polished_wisestone_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "polished_wisestone_place")));
    public static final RegistryObject<SoundEvent> POLISHED_WISESTONE_HIT_SOUND = SOUND_EVENTS.register("polished_wisestone_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "polished_wisestone_hit")));

    public static final RegistryObject<SoundEvent> WISESTONE_BRICKS_BREAK_SOUND = SOUND_EVENTS.register("wisestone_bricks_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_bricks_break")));
    public static final RegistryObject<SoundEvent> WISESTONE_BRICKS_STEP_SOUND = SOUND_EVENTS.register("wisestone_bricks_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_bricks_step")));
    public static final RegistryObject<SoundEvent> WISESTONE_BRICKS_PLACE_SOUND = SOUND_EVENTS.register("wisestone_bricks_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_bricks_place")));
    public static final RegistryObject<SoundEvent> WISESTONE_BRICKS_HIT_SOUND = SOUND_EVENTS.register("wisestone_bricks_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_bricks_hit")));

    public static final RegistryObject<SoundEvent> WISESTONE_TILE_BREAK_SOUND = SOUND_EVENTS.register("wisestone_tile_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_tile_break")));
    public static final RegistryObject<SoundEvent> WISESTONE_TILE_STEP_SOUND = SOUND_EVENTS.register("wisestone_tile_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_tile_step")));
    public static final RegistryObject<SoundEvent> WISESTONE_TILE_PLACE_SOUND = SOUND_EVENTS.register("wisestone_tile_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_tile_place")));
    public static final RegistryObject<SoundEvent> WISESTONE_TILE_HIT_SOUND = SOUND_EVENTS.register("wisestone_tile_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_tile_hit")));

    public static final RegistryObject<SoundEvent> CHISELED_WISESTONE_BREAK_SOUND = SOUND_EVENTS.register("chiseled_wisestone_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "chiseled_wisestone_break")));
    public static final RegistryObject<SoundEvent> CHISELED_WISESTONE_STEP_SOUND = SOUND_EVENTS.register("chiseled_wisestone_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "chiseled_wisestone_step")));
    public static final RegistryObject<SoundEvent> CHISELED_WISESTONE_PLACE_SOUND = SOUND_EVENTS.register("chiseled_wisestone_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "chiseled_wisestone_place")));
    public static final RegistryObject<SoundEvent> CHISELED_WISESTONE_HIT_SOUND = SOUND_EVENTS.register("chiseled_wisestone_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "chiseled_wisestone_hit")));

    public static final RegistryObject<SoundEvent> WISESTONE_BUTTON_CLICK_OFF_SOUND = SOUND_EVENTS.register("wisestone_button_click_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_button_click_off")));
    public static final RegistryObject<SoundEvent> WISESTONE_BUTTON_CLICK_ON_SOUND = SOUND_EVENTS.register("wisestone_button_click_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_button_click_on")));
    public static final RegistryObject<SoundEvent> WISESTONE_PRESSURE_PLATE_CLICK_OFF_SOUND = SOUND_EVENTS.register("wisestone_pressure_plate_click_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_pressure_plate_click_off")));
    public static final RegistryObject<SoundEvent> WISESTONE_PRESSURE_PLATE_CLICK_ON_SOUND = SOUND_EVENTS.register("wisestone_pressure_plate_click_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wisestone_pressure_plate_click_on")));

    public static final RegistryObject<SoundEvent> MOR_BREAK_SOUND = SOUND_EVENTS.register("mor_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mor_break")));
    public static final RegistryObject<SoundEvent> MOR_STEP_SOUND = SOUND_EVENTS.register("mor_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mor_step")));
    public static final RegistryObject<SoundEvent> MOR_PLACE_SOUND = SOUND_EVENTS.register("mor_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mor_place")));
    public static final RegistryObject<SoundEvent> MOR_HIT_SOUND = SOUND_EVENTS.register("mor_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mor_hit")));

    public static final RegistryObject<SoundEvent> ELDER_MOR_BREAK_SOUND = SOUND_EVENTS.register("elder_mor_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "elder_mor_break")));
    public static final RegistryObject<SoundEvent> ELDER_MOR_STEP_SOUND = SOUND_EVENTS.register("elder_mor_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "elder_mor_step")));
    public static final RegistryObject<SoundEvent> ELDER_MOR_PLACE_SOUND = SOUND_EVENTS.register("elder_mor_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "elder_mor_place")));
    public static final RegistryObject<SoundEvent> ELDER_MOR_HIT_SOUND = SOUND_EVENTS.register("elder_mor_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "elder_mor_hit")));

    public static final RegistryObject<SoundEvent> MOR_BLOCK_BREAK_SOUND = SOUND_EVENTS.register("mor_block_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mor_block_break")));
    public static final RegistryObject<SoundEvent> MOR_BLOCK_STEP_SOUND = SOUND_EVENTS.register("mor_block_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mor_block_step")));
    public static final RegistryObject<SoundEvent> MOR_BLOCK_PLACE_SOUND = SOUND_EVENTS.register("mor_block_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mor_block_place")));
    public static final RegistryObject<SoundEvent> MOR_BLOCK_HIT_SOUND = SOUND_EVENTS.register("mor_block_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mor_block_hit")));

    public static final RegistryObject<SoundEvent> ELDER_MOR_BLOCK_BREAK_SOUND = SOUND_EVENTS.register("elder_mor_block_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "elder_mor_block_break")));
    public static final RegistryObject<SoundEvent> ELDER_MOR_BLOCK_STEP_SOUND = SOUND_EVENTS.register("elder_mor_block_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "elder_mor_block_step")));
    public static final RegistryObject<SoundEvent> ELDER_MOR_BLOCK_PLACE_SOUND = SOUND_EVENTS.register("elder_mor_block_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "elder_mor_block_place")));
    public static final RegistryObject<SoundEvent> ELDER_MOR_BLOCK_HIT_SOUND = SOUND_EVENTS.register("elder_mor_block_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "elder_mor_block_hit")));

    public static final RegistryObject<SoundEvent> MIXTURE_BREAK_SOUND = SOUND_EVENTS.register("mixture_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mixture_break")));
    public static final RegistryObject<SoundEvent> MIXTURE_STEP_SOUND = SOUND_EVENTS.register("mixture_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mixture_step")));
    public static final RegistryObject<SoundEvent> MIXTURE_PLACE_SOUND = SOUND_EVENTS.register("mixture_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mixture_place")));
    public static final RegistryObject<SoundEvent> MIXTURE_HIT_SOUND = SOUND_EVENTS.register("mixture_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "mixture_hit")));

    public static final RegistryObject<SoundEvent> CRYSTAL_BREAK_SOUND = SOUND_EVENTS.register("crystal_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "crystal_break")));
    public static final RegistryObject<SoundEvent> CRYSTAL_STEP_SOUND = SOUND_EVENTS.register("crystal_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "crystal_step")));
    public static final RegistryObject<SoundEvent> CRYSTAL_PLACE_SOUND = SOUND_EVENTS.register("crystal_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "crystal_place")));
    public static final RegistryObject<SoundEvent> CRYSTAL_HIT_SOUND = SOUND_EVENTS.register("crystal_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "crystal_hit")));

    public static final RegistryObject<SoundEvent> CRYSTAL_RESONATE_SOUND = SOUND_EVENTS.register("crystal_resonate", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "crystal_resonate")));
    public static final RegistryObject<SoundEvent> CRYSTAL_SHIMMER_SOUND = SOUND_EVENTS.register("crystal_shimmer", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "crystal_shimmer")));

    public static final RegistryObject<SoundEvent> PEDESTAL_INSERT_SOUND = SOUND_EVENTS.register("pedestal_insert", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "pedestal_insert")));
    public static final RegistryObject<SoundEvent> PEDESTAL_REMOVE_SOUND = SOUND_EVENTS.register("pedestal_remove", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "pedestal_remove")));

    public static final ForgeSoundType ARCANE_GOLD_SOUNDS = new ForgeSoundType(1f, 1f, ARCANE_GOLD_BREAK_SOUND, ARCANE_GOLD_STEP_SOUND, ARCANE_GOLD_PLACE_SOUND, ARCANE_GOLD_HIT_SOUND, () -> SoundEvents.NETHERITE_BLOCK_FALL);
    public static final ForgeSoundType ARCANE_GOLD_ORE_SOUNDS = new ForgeSoundType(1f, 1f, ARCANE_GOLD_ORE_BREAK_SOUND, ARCANE_GOLD_ORE_STEP_SOUND, ARCANE_GOLD_ORE_PLACE_SOUND, ARCANE_GOLD_ORE_HIT_SOUND, () -> SoundEvents.STONE_FALL);
    public static final ForgeSoundType DEEPSLATE_ARCANE_GOLD_ORE_SOUNDS = new ForgeSoundType(1f, 1f, DEEPSLATE_ARCANE_GOLD_ORE_BREAK_SOUND, DEEPSLATE_ARCANE_GOLD_ORE_STEP_SOUND, DEEPSLATE_ARCANE_GOLD_ORE_PLACE_SOUND, DEEPSLATE_ARCANE_GOLD_ORE_HIT_SOUND, () -> SoundEvents.DEEPSLATE_FALL);
    public static final ForgeSoundType NETHER_ARCANE_GOLD_ORE_SOUNDS = new ForgeSoundType(1f, 1f, NETHER_ARCANE_GOLD_ORE_BREAK_SOUND, NETHER_ARCANE_GOLD_ORE_STEP_SOUND, NETHER_ARCANE_GOLD_ORE_PLACE_SOUND, NETHER_ARCANE_GOLD_ORE_HIT_SOUND, () -> SoundEvents.NETHER_ORE_FALL);
    public static final ForgeSoundType RAW_ARCANE_GOLD_SOUNDS = new ForgeSoundType(1f, 1f, RAW_ARCANE_GOLD_BREAK_SOUND, RAW_ARCANE_GOLD_STEP_SOUND, RAW_ARCANE_GOLD_PLACE_SOUND, RAW_ARCANE_GOLD_HIT_SOUND, () -> SoundEvents.STONE_FALL);
    public static final ForgeSoundType SARCON_SOUNDS = new ForgeSoundType(1f, 1f, SARCON_BREAK_SOUND, SARCON_STEP_SOUND, SARCON_PLACE_SOUND, SARCON_HIT_SOUND, () -> SoundEvents.NETHERITE_BLOCK_FALL);
    public static final ForgeSoundType VILENIUM_SOUNDS = new ForgeSoundType(1f, 1f, VILENIUM_BREAK_SOUND, VILENIUM_STEP_SOUND, VILENIUM_PLACE_SOUND, VILENIUM_HIT_SOUND, () -> SoundEvents.NETHERITE_BLOCK_FALL);
    public static final ForgeSoundType ARCANUM_SOUNDS = new ForgeSoundType(1f, 1f, ARCANUM_BREAK_SOUND, ARCANUM_STEP_SOUND, ARCANUM_PLACE_SOUND, ARCANUM_HIT_SOUND, () -> SoundEvents.METAL_FALL);
    public static final ForgeSoundType ARCANUM_ORE_SOUNDS = new ForgeSoundType(1f, 1f, ARCANUM_ORE_BREAK_SOUND, ARCANUM_ORE_STEP_SOUND, ARCANUM_ORE_PLACE_SOUND, ARCANUM_ORE_HIT_SOUND, () -> SoundEvents.STONE_FALL);
    public static final ForgeSoundType DEEPSLATE_ARCANUM_ORE_SOUNDS = new ForgeSoundType(1f, 1f, DEEPSLATE_ARCANUM_ORE_BREAK_SOUND, DEEPSLATE_ARCANUM_ORE_STEP_SOUND, DEEPSLATE_ARCANUM_ORE_PLACE_SOUND, DEEPSLATE_ARCANUM_ORE_HIT_SOUND, () -> SoundEvents.DEEPSLATE_FALL);
    public static final ForgeSoundType ARCACITE_SOUNDS = new ForgeSoundType(1f, 1f, ARCACITE_BREAK_SOUND, ARCACITE_STEP_SOUND, ARCACITE_PLACE_SOUND, ARCACITE_HIT_SOUND, () -> SoundEvents.METAL_FALL);
    public static final ForgeSoundType PRECISION_CRYSTAL_SOUNDS = new ForgeSoundType(1f, 1f, PRECISION_CRYSTAL_BREAK_SOUND, PRECISION_CRYSTAL_STEP_SOUND, PRECISION_CRYSTAL_PLACE_SOUND, PRECISION_CRYSTAL_HIT_SOUND, () -> SoundEvents.COPPER_FALL);
    public static final ForgeSoundType NETHER_SALT_SOUNDS = new ForgeSoundType(1f, 1f, NETHER_SALT_BREAK_SOUND, NETHER_SALT_STEP_SOUND, NETHER_SALT_PLACE_SOUND, NETHER_SALT_HIT_SOUND, () -> SoundEvents.STONE_FALL);
    public static final ForgeSoundType NETHER_SALT_ORE_SOUNDS = new ForgeSoundType(1f, 1f, NETHER_SALT_ORE_BREAK_SOUND, NETHER_SALT_ORE_STEP_SOUND, NETHER_SALT_ORE_PLACE_SOUND, NETHER_SALT_ORE_HIT_SOUND, () -> SoundEvents.NETHER_ORE_FALL);
    public static final ForgeSoundType ARCANE_WOOD_SOUNDS = new ForgeSoundType(1f, 1f, ARCANE_WOOD_BREAK_SOUND, ARCANE_WOOD_STEP_SOUND, ARCANE_WOOD_PLACE_SOUND, ARCANE_WOOD_HIT_SOUND, () -> SoundEvents.BAMBOO_WOOD_FALL);
    public static final ForgeSoundType ARCANE_WOOD_HANGING_SIGN_SOUNDS = new ForgeSoundType(1f, 1f, ARCANE_WOOD_HANGING_SIGN_BREAK_SOUND, ARCANE_WOOD_HANGING_SIGN_STEP_SOUND, ARCANE_WOOD_HANGING_SIGN_PLACE_SOUND, ARCANE_WOOD_HANGING_SIGN_HIT_SOUND, () -> SoundEvents.BAMBOO_WOOD_FALL);
    public static final ForgeSoundType INNOCENT_WOOD_SOUNDS = new ForgeSoundType(1f, 1f, INNOCENT_WOOD_BREAK_SOUND, INNOCENT_WOOD_STEP_SOUND, INNOCENT_WOOD_PLACE_SOUND, INNOCENT_WOOD_HIT_SOUND, () -> SoundEvents.CHERRY_WOOD_FALL);
    public static final ForgeSoundType INNOCENT_WOOD_HANGING_SIGN_SOUNDS = new ForgeSoundType(1f, 1f, INNOCENT_WOOD_HANGING_SIGN_BREAK_SOUND, INNOCENT_WOOD_HANGING_SIGN_STEP_SOUND, INNOCENT_WOOD_HANGING_SIGN_PLACE_SOUND, INNOCENT_WOOD_HANGING_SIGN_HIT_SOUND, () -> SoundEvents.CHERRY_WOOD_FALL);
    public static final ForgeSoundType WISESTONE_SOUNDS = new ForgeSoundType(1f, 1f, WISESTONE_BREAK_SOUND, WISESTONE_STEP_SOUND, WISESTONE_PLACE_SOUND, WISESTONE_HIT_SOUND, () -> SoundEvents.DEEPSLATE_FALL);
    public static final ForgeSoundType POLISHED_WISESTONE_SOUNDS = new ForgeSoundType(1f, 1f, POLISHED_WISESTONE_BREAK_SOUND, POLISHED_WISESTONE_STEP_SOUND, POLISHED_WISESTONE_PLACE_SOUND, POLISHED_WISESTONE_HIT_SOUND, () -> SoundEvents.POLISHED_DEEPSLATE_FALL);
    public static final ForgeSoundType WISESTONE_BRICKS_SOUNDS = new ForgeSoundType(1f, 1f, WISESTONE_BRICKS_BREAK_SOUND, WISESTONE_BRICKS_STEP_SOUND, WISESTONE_BRICKS_PLACE_SOUND, WISESTONE_BRICKS_HIT_SOUND, () -> SoundEvents.DEEPSLATE_BRICKS_FALL);
    public static final ForgeSoundType WISESTONE_TILE_SOUNDS = new ForgeSoundType(1f, 1f, WISESTONE_TILE_BREAK_SOUND, WISESTONE_TILE_STEP_SOUND, WISESTONE_TILE_PLACE_SOUND, WISESTONE_TILE_HIT_SOUND, () -> SoundEvents.DEEPSLATE_TILES_FALL);
    public static final ForgeSoundType CHISELED_WISESTONE_SOUNDS = new ForgeSoundType(1f, 1f, CHISELED_WISESTONE_BREAK_SOUND, CHISELED_WISESTONE_STEP_SOUND, CHISELED_WISESTONE_PLACE_SOUND, CHISELED_WISESTONE_HIT_SOUND, () -> SoundEvents.POLISHED_DEEPSLATE_FALL);
    public static final ForgeSoundType MOR_SOUNDS = new ForgeSoundType(1f, 1f, MOR_BREAK_SOUND, MOR_STEP_SOUND, MOR_PLACE_SOUND, MOR_HIT_SOUND, () -> SoundEvents.FUNGUS_FALL);
    public static final ForgeSoundType ELDER_MOR_SOUNDS = new ForgeSoundType(1f, 1f, ELDER_MOR_BREAK_SOUND, ELDER_MOR_STEP_SOUND, ELDER_MOR_PLACE_SOUND, ELDER_MOR_HIT_SOUND, () -> SoundEvents.FUNGUS_FALL);
    public static final ForgeSoundType MOR_BLOCK_SOUNDS = new ForgeSoundType(1f, 1f, MOR_BLOCK_BREAK_SOUND, MOR_BLOCK_STEP_SOUND, MOR_BLOCK_PLACE_SOUND, MOR_BLOCK_HIT_SOUND, () -> SoundEvents.MUDDY_MANGROVE_ROOTS_FALL);
    public static final ForgeSoundType ELDER_MOR_BLOCK_SOUNDS = new ForgeSoundType(1f, 1f, ELDER_MOR_BLOCK_BREAK_SOUND, ELDER_MOR_BLOCK_STEP_SOUND, ELDER_MOR_BLOCK_PLACE_SOUND, ELDER_MOR_BLOCK_HIT_SOUND, () -> SoundEvents.MUDDY_MANGROVE_ROOTS_FALL);
    public static final ForgeSoundType MIXTURE_SOUNDS = new ForgeSoundType(1f, 1f, MIXTURE_BREAK_SOUND, MIXTURE_STEP_SOUND, MIXTURE_PLACE_SOUND, MIXTURE_HIT_SOUND, () -> SoundEvents.BONE_BLOCK_FALL);
    public static final ForgeSoundType CRYSTAL_SOUNDS = new ForgeSoundType(1f, 1f, CRYSTAL_BREAK_SOUND, CRYSTAL_STEP_SOUND, CRYSTAL_PLACE_SOUND, CRYSTAL_HIT_SOUND, () -> SoundEvents.AMETHYST_CLUSTER_FALL);

    public static final LazyOptional<BlockSetType> ARCANE_WOOD_BLOCK_SET = LazyOptional.of(() -> BlockSetType.register(
            new BlockSetType("arcane_wood", true,
                    ARCANE_WOOD_SOUNDS,
                    ARCANE_WOOD_DOOR_CLOSE_SOUND.get(), ARCANE_WOOD_DOOR_OPEN_SOUND.get(),
                    ARCANE_WOOD_TRAPDOOR_CLOSE_SOUND.get(), ARCANE_WOOD_TRAPDOOR_OPEN_SOUND.get(),
                    ARCANE_WOOD_PRESSURE_PLATE_CLICK_OFF_SOUND.get(), ARCANE_WOOD_PRESSURE_PLATE_CLICK_ON_SOUND.get(),
                    ARCANE_WOOD_BUTTON_CLICK_OFF_SOUND.get(), ARCANE_WOOD_BUTTON_CLICK_ON_SOUND.get())));
    public static final LazyOptional<BlockSetType> INNOCENT_WOOD_BLOCK_SET = LazyOptional.of(() -> BlockSetType.register(
            new BlockSetType("innocent_wood", true,
                    INNOCENT_WOOD_SOUNDS,
                    INNOCENT_WOOD_DOOR_CLOSE_SOUND.get(), INNOCENT_WOOD_DOOR_OPEN_SOUND.get(),
                    INNOCENT_WOOD_TRAPDOOR_CLOSE_SOUND.get(), INNOCENT_WOOD_TRAPDOOR_OPEN_SOUND.get(),
                    INNOCENT_WOOD_PRESSURE_PLATE_CLICK_OFF_SOUND.get(), INNOCENT_WOOD_PRESSURE_PLATE_CLICK_ON_SOUND.get(),
                    INNOCENT_WOOD_BUTTON_CLICK_OFF_SOUND.get(), INNOCENT_WOOD_BUTTON_CLICK_ON_SOUND.get())));
    public static final LazyOptional<BlockSetType> CORK_BAMBOO_BLOCK_SET = LazyOptional.of(() -> BlockSetType.register(
            new BlockSetType("cork_bamboo", true,
                    ARCANE_WOOD_SOUNDS,
                    ARCANE_WOOD_DOOR_CLOSE_SOUND.get(), ARCANE_WOOD_DOOR_OPEN_SOUND.get(),
                    ARCANE_WOOD_TRAPDOOR_CLOSE_SOUND.get(), ARCANE_WOOD_TRAPDOOR_OPEN_SOUND.get(),
                    ARCANE_WOOD_PRESSURE_PLATE_CLICK_OFF_SOUND.get(), ARCANE_WOOD_PRESSURE_PLATE_CLICK_ON_SOUND.get(),
                    ARCANE_WOOD_BUTTON_CLICK_OFF_SOUND.get(), ARCANE_WOOD_BUTTON_CLICK_ON_SOUND.get())));
    public static final LazyOptional<BlockSetType> WISESTONE_BLOCK_SET = LazyOptional.of(() -> BlockSetType.register(
            new BlockSetType("wisestone", true,
                    POLISHED_WISESTONE_SOUNDS,
                    SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN,
                    WISESTONE_PRESSURE_PLATE_CLICK_OFF_SOUND.get(), WISESTONE_PRESSURE_PLATE_CLICK_ON_SOUND.get(),
                    WISESTONE_BUTTON_CLICK_OFF_SOUND.get(), WISESTONE_BUTTON_CLICK_ON_SOUND.get())));

    public static final LazyOptional<WoodType> ARCANE_WOOD_TYPE = LazyOptional.of(() -> WoodType.register(new WoodType(new ResourceLocation(MOD_ID, "arcane_wood").toString(), ARCANE_WOOD_BLOCK_SET.resolve().get(),
            ARCANE_WOOD_SOUNDS, ARCANE_WOOD_HANGING_SIGN_SOUNDS,
            ARCANE_WOOD_FENCE_GATE_CLOSE_SOUND.get(), ARCANE_WOOD_FENCE_GATE_OPEN_SOUND.get())));
    public static final LazyOptional<WoodType> INNOCENT_WOOD_TYPE = LazyOptional.of(() -> WoodType.register(new WoodType(new ResourceLocation(MOD_ID, "innocent_wood").toString(), INNOCENT_WOOD_BLOCK_SET.resolve().get(),
            INNOCENT_WOOD_SOUNDS, INNOCENT_WOOD_HANGING_SIGN_SOUNDS,
            INNOCENT_WOOD_FENCE_GATE_CLOSE_SOUND.get(), INNOCENT_WOOD_FENCE_GATE_OPEN_SOUND.get())));
    public static final LazyOptional<WoodType> CORK_BAMBOO_TYPE = LazyOptional.of(() -> WoodType.register(new WoodType(new ResourceLocation(MOD_ID, "cork_bamboo").toString(), CORK_BAMBOO_BLOCK_SET.resolve().get(),
            ARCANE_WOOD_SOUNDS, ARCANE_WOOD_HANGING_SIGN_SOUNDS,
            ARCANE_WOOD_FENCE_GATE_CLOSE_SOUND.get(), ARCANE_WOOD_FENCE_GATE_OPEN_SOUND.get())));

    //COLORS
    public static Color earthCrystalColor = new Color(138, 201, 123);
    public static Color waterCrystalColor = new Color(152, 180, 223);
    public static Color airCrystalColor = new Color(230, 173, 134);
    public static Color fireCrystalColor = new Color(225, 127, 103);
    public static Color voidCrystalColor = new Color(175, 140, 194);

    public static Color earthSpellColor = new Color(138, 201, 123);
    public static Color waterSpellColor = new Color(152, 180, 223);
    public static Color airSpellColor = new Color(230, 173, 134);
    public static Color fireSpellColor = new Color(225, 127, 103);
    public static Color voidSpellColor = new Color(175, 140, 194);
    public static Color frostSpellColor = new Color(221, 243, 254);
    public static Color holySpellColor = new Color(255, 248, 194);
    public static Color curseSpellColor = new Color(203, 194, 255);
    public static Color poisonSpellColor = new Color(149, 255, 115);
    public static Color witheringSpellColor = new Color(64, 50, 41);
    public static Color necroticSpellColor = new Color(255, 119, 167);
    public static Color experienceSpellColor = new Color(245, 255, 143);
    public static Color soundSpellColor = new Color(250, 242, 242);

    //CRYSTAL_STATS
    public static CrystalStat FOCUS_CRYSTAL_STAT = new CrystalStat(MOD_ID+":focus", 3);
    public static CrystalStat BALANCE_CRYSTAL_STAT = new CrystalStat(MOD_ID+":balance", 3);
    public static CrystalStat ABSORPTION_CRYSTAL_STAT = new CrystalStat(MOD_ID+":absorption", 3);
    public static CrystalStat RESONANCE_CRYSTAL_STAT = new CrystalStat(MOD_ID+":resonance", 3);

    //POLISHING_TYPES
    public static final PolishingType CRYSTAL_POLISHING_TYPE = new CrystalPolishingType(MOD_ID+":crystal");
    public static final PolishingType FACETED_POLISHING_TYPE = new FacetedPolishingType(MOD_ID+":faceted");
    public static final PolishingType ADVANCED_POLISHING_TYPE = new AdvancedPolishingType(MOD_ID+":advanced");
    public static final PolishingType MASTERFUL_POLISHING_TYPE = new MasterfulPolishingType(MOD_ID+":masterful");
    public static final PolishingType PURE_POLISHING_TYPE = new PurePolishingType(MOD_ID+":pure");

    //CRYSTAL_TYPES
    public static final CrystalType EARTH_CRYSTAL_TYPE = new EarthCrystalType(MOD_ID+":earth");
    public static final CrystalType WATER_CRYSTAL_TYPE = new WaterCrystalType(MOD_ID+":water");
    public static final CrystalType AIR_CRYSTAL_TYPE = new AirCrystalType(MOD_ID+":air");
    public static final CrystalType FIRE_CRYSTAL_TYPE = new FireCrystalType(MOD_ID+":fire");
    public static final CrystalType VOID_CRYSTAL_TYPE = new VoidCrystalType(MOD_ID+":void");

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
    public static Spell HEART_OF_NATURE_SPELL = new HeartOfNatureSpell(MOD_ID+":heart_of_nature", 8);
    public static Spell WATER_BREATHING_SPELL = new WaterBreathingSpell(MOD_ID+":water_breathing", 8);
    public static Spell AIR_FLOW_SPELL = new AirFlowSpell(MOD_ID+":air_flow", 8);
    public static Spell FIRE_SHIELD_SPELL = new FireShieldSpell(MOD_ID+":fire_shield", 8);
    public static Spell BLINK_SPELL = new BlinkSpell(MOD_ID+":blink", 8, false);
    public static Spell SNOWFLAKE_SPELL = new SnowflakeSpell(MOD_ID+":snowflake", 8);
    public static Spell HOLY_CROSS_SPELL = new HolyCrossSpell(MOD_ID+":holy_cross", 8);
    public static Spell CURSE_CROSS_SPELL = new CurseCrossSpell(MOD_ID+":curse_cross", 8);
    public static Spell POISON_SPELL = new PoisonSpell(MOD_ID+":poison", 8);
    public static Spell MAGIC_SPROUT_SPELL = new MagicSproutSpell(MOD_ID+":magic_sprout", 10);
    public static Spell DIRT_BLOCK_SPELL = new DirtBlockSpell(MOD_ID+":dirt_block", 4);
    public static Spell WATER_BLOCK_SPELL = new WaterBlockSpell(MOD_ID+":water_block", 4);
    public static Spell AIR_IMPACT_SPELL = new AirImpactSpell(MOD_ID+":air_impact", 4);
    public static Spell ICE_BLOCK_SPELL = new IceBlockSpell(MOD_ID+":ice_block", 4);
    public static Spell EARTH_CHARGE_SPELL = new EarthChargeSpell(MOD_ID+":earth_charge", 10);
    public static Spell WATER_CHARGE_SPELL = new WaterChargeSpell(MOD_ID+":water_charge", 10);
    public static Spell AIR_CHARGE_SPELL = new AirChargeSpell(MOD_ID+":air_charge", 10);
    public static Spell FIRE_CHARGE_SPELL = new FireChargeSpell(MOD_ID+":fire_charge", 10);
    public static Spell VOID_CHARGE_SPELL = new VoidChargeSpell(MOD_ID+":void_charge", 10);
    public static Spell FROST_CHARGE_SPELL = new FrostChargeSpell(MOD_ID+":frost_charge", 10);
    public static Spell HOLY_CHARGE_SPELL = new HolyChargeSpell(MOD_ID+":holy_charge", 10);
    public static Spell CURSE_CHARGE_SPELL = new CurseChargeSpell(MOD_ID+":curse_charge", 10);
    public static Spell EARTH_AURA_SPELL = new EarthAuraSpell(MOD_ID+":earth_aura", 12);
    public static Spell WATER_AURA_SPELL = new WaterAuraSpell(MOD_ID+":water_aura", 12);
    public static Spell AIR_AURA_SPELL = new AirAuraSpell(MOD_ID+":air_aura", 12);
    public static Spell FIRE_AURA_SPELL = new FireAuraSpell(MOD_ID+":fire_aura", 12);
    public static Spell VOID_AURA_SPELL = new VoidAuraSpell(MOD_ID+":void_aura", 12);
    public static Spell FROST_AURA_SPELL = new FrostAuraSpell(MOD_ID+":frost_aura", 12);
    public static Spell HOLY_AURA_SPELL = new HolyAuraSpell(MOD_ID+":holy_aura", 12);
    public static Spell CURSE_AURA_SPELL = new CurseAuraSpell(MOD_ID+":curse_aura", 12);
    public static Spell RAIN_CLOUD_SPELL = new RainCloudSpell(MOD_ID+":rain_cloud", 15);
    public static Spell LAVA_BLOCK_SPELL = new LavaBlockSpell(MOD_ID+":lava_block", 12);
    public static Spell ICICLE_SPELL = new IcicleSpell(MOD_ID+":icicle", 10);
    public static Spell SHARP_BLINK_SPELL = new BlinkSpell(MOD_ID+":sharp_blink", 10, true);
    public static Spell CRYSTAL_CRUSHING_SPELL = new CrystalCrushingSpell(MOD_ID+":crystal_crushing", 8);
    public static Spell TOXIC_RAIN_SPELL = new ToxicRainSpell(MOD_ID+":toxic_rain", 8);
    public static Spell MOR_SWARM_SPELL = new MorSwarmSpell(MOD_ID+":mor_swarm", 8);
    public static Spell WITHERING_SPELL = new WitheringSpell(MOD_ID+":withering", 8);
    public static Spell IRRITATION_SPELL = new IrritationSpell(MOD_ID+":irritation", 8);
    public static Spell NECROTIC_RAY_SPELL = new NecroticRaySpell(MOD_ID+":necrotic_ray", 8);
    public static Spell LIGHT_RAY_SPELL = new LightRaySpell(MOD_ID+":light_ray", 8);
    public static Spell INCINERATION_SPELL = new IncinerationSpell(MOD_ID+":incineration", 15);
    public static Spell REPENTANCE_SPELL = new RepentanceSpell(MOD_ID+":repentance", 15);
    public static Spell RENUNCIATION_SPELL = new RenunciationSpell(MOD_ID+":renunciation", 15);
    public static Spell EMBER_RAY_SPELL = new EmberRaySpell(MOD_ID+":ember_ray", 15);
    public static Spell WISDOM_SPELL = new WisdomSpell(MOD_ID+":wisdom", 20);

    public static Spell PIPE_SOUND_SPELL = new PipeSoundSpell(MOD_ID+":pipe_sound", 0);
    public static Spell BOOM_SOUND_SPELL = new BoomSoundSpell(MOD_ID+":boom_sound", 0);
    public static Spell MOAI_SOUND_SPELL = new MoaiSoundSpell(MOD_ID+":moai_sound", 0);

    //SKINS
    public static Skin TOP_HAT_SKIN = new TopHatSkin(MOD_ID+":top_hat", new Color(54, 60, 81));
    public static Skin SOUL_HUNTER_SKIN = new SoulHunterSkin(MOD_ID+":soul_hunter", new Color(225, 99, 226));
    public static Skin IMPLOSION_SKIN = new ImplosionSkin(MOD_ID+":implosion", new Color(149, 237, 255));
    public static Skin PHANTOM_INK_SKIN = new PhantomInkSkin(MOD_ID+":phantom_ink", new Color(189, 237, 255));
    public static Skin MAGNIFICENT_MAID_SKIN = new MagnificentMaidSkin(MOD_ID+":magnificent_maid", new Color(153, 168, 184));
    public static Skin SUMMER_LOVE_SKIN = new SummerLoveSkin(MOD_ID+":summer_love", new Color(243, 181, 127));

    //ARCANE ENCHANTMENTS
    public static ArcaneEnchantment WISSEN_MENDING_ARCANE_ENCHANTMENT = new WissenMendingArcaneEnchantment(MOD_ID+":wissen_mending", 3);
    public static ArcaneEnchantment LIFE_MENDING_ARCANE_ENCHANTMENT = new LifeMendingArcaneEnchantment(MOD_ID+":life_mending", 3);
    public static ArcaneEnchantment MAGIC_BLADE_ARCANE_ENCHANTMENT = new MagicBladeArcaneEnchantment(MOD_ID+":magic_blade", 5);
    public static ArcaneEnchantment THROW_ARCANE_ENCHANTMENT = new ThrowArcaneEnchantment(MOD_ID+":throw", 1);
    public static ArcaneEnchantment LIFE_ROOTS_ARCANE_ENCHANTMENT = new LifeRootsArcaneEnchantment(MOD_ID+":life_roots", 2);
    public static ArcaneEnchantment WISSEN_CHARGE_ARCANE_ENCHANTMENT = new WissenChargeArcaneEnchantment(MOD_ID+":wissen_charge", 2);
    public static ArcaneEnchantment EAGLE_SHOT_ARCANE_ENCHANTMENT = new EagleShotArcaneEnchantment(MOD_ID+":eagle_shot", 4);
    public static ArcaneEnchantment SPLIT_ARCANE_ENCHANTMENT = new SplitArcaneEnchantment(MOD_ID+":split", 4);
    public static ArcaneEnchantment SONAR_ARCANE_ENCHANTMENT = new SonarArcaneEnchantment(MOD_ID+":sonar", 3);

    //CRYSTAL RITUALS
    public static CrystalRitual EMPTY_CRYSTAL_RITUAL = new CrystalRitual(MOD_ID+":empty");
    public static CrystalRitual ARTIFICIAL_FERTILITY_CRYSTAL_RITUAL = new ArtificialFertilityCrystalRitual(MOD_ID+":artificial_fertility");
    public static CrystalRitual RITUAL_BREEDING_CRYSTAL_RITUAL = new RitualBreedingCrystalRitual(MOD_ID+":ritual_breeding");
    public static CrystalRitual CRYSTAL_GROWTH_ACCELERATION_CRYSTAL_RITUAL = new CrystalGrowthAccelerationCrystalRitual(MOD_ID+":crystal_growth_acceleration");
    public static CrystalRitual CRYSTAL_INFUSION_CRYSTAL_RITUAL = new CrystalInfusionCrystalRitual(MOD_ID+":crystal_infusion");
    //public static CrystalRitual STONE_CALENDAR_CRYSTAL_RITUAL = new CrystalRitual(MOD_ID+":stone_calendar");

    public static final FoodProperties MOR_FOOD = (new FoodProperties.Builder()).nutrition(1).saturationMod(0.2F).effect(new MobEffectInstance(MobEffects.POISON, 450, 0), 1.0F).effect(new MobEffectInstance(MobEffects.CONFUSION, 350, 0), 1.0F).effect(new MobEffectInstance(MobEffects.BLINDNESS, 250, 0), 1.0F).effect(new MobEffectInstance(MobEffects.WEAKNESS, 550, 1), 1.0F).build();
    public static final FoodProperties PITCHER_TURNIP_FOOD = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.4F).build();
    public static final FoodProperties UNDERGROUND_GRAPE_FOOD = (new FoodProperties.Builder()).nutrition(2).saturationMod(0.2F).build();
    public static final FoodProperties SHRIMP_FOOD = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.25F).build();
    public static final FoodProperties FRIED_SHRIMP_FOOD = (new FoodProperties.Builder()).nutrition(8).saturationMod(0.6F).build();

    //BLOCKS
    public static final RegistryObject<Block> ARCANE_GOLD_BLOCK = BLOCKS.register("arcane_gold_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).sound(ARCANE_GOLD_SOUNDS)));
    public static final RegistryObject<Block> ARCANE_GOLD_ORE = BLOCKS.register("arcane_gold_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE).sound(ARCANE_GOLD_ORE_SOUNDS)));
    public static final RegistryObject<Block> DEEPSLATE_ARCANE_GOLD_ORE = BLOCKS.register("deepslate_arcane_gold_ore", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_GOLD_ORE).sound(DEEPSLATE_ARCANE_GOLD_ORE_SOUNDS)));
    public static final RegistryObject<Block> NETHER_ARCANE_GOLD_ORE = BLOCKS.register("nether_arcane_gold_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_GOLD_ORE).sound(NETHER_ARCANE_GOLD_ORE_SOUNDS), UniformInt.of(0, 1)));
    public static final RegistryObject<Block> RAW_ARCANE_GOLD_BLOCK = BLOCKS.register("raw_arcane_gold_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).sound(RAW_ARCANE_GOLD_SOUNDS)));
    public static final RegistryObject<Block> SARCON_BLOCK = BLOCKS.register("sarcon_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).mapColor(MapColor.COLOR_PURPLE).sound(SARCON_SOUNDS)));
    public static final RegistryObject<Block> VILENIUM_BLOCK = BLOCKS.register("vilenium_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.NETHERITE_BLOCK).mapColor(MapColor.COLOR_YELLOW).sound(VILENIUM_SOUNDS)));
    public static final RegistryObject<Block> ARCANUM_BLOCK = BLOCKS.register("arcanum_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).sound(ARCANUM_SOUNDS)));
    public static final RegistryObject<Block> ARCANUM_ORE = BLOCKS.register("arcanum_ore", () -> new ArcanumOreBlock(BlockBehaviour.Properties.copy(Blocks.DIAMOND_ORE).sound(ARCANUM_ORE_SOUNDS)));
    public static final RegistryObject<Block> DEEPSLATE_ARCANUM_ORE = BLOCKS.register("deepslate_arcanum_ore", () -> new ArcanumOreBlock(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_DIAMOND_ORE).sound(DEEPSLATE_ARCANUM_ORE_SOUNDS)));
    public static final RegistryObject<Block> ARCANUM_DUST_BLOCK = BLOCKS.register("arcanum_dust_block", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_CONCRETE_POWDER)));
    public static final RegistryObject<Block> ARCACITE_BLOCK = BLOCKS.register("arcacite_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_RED).sound(ARCACITE_SOUNDS)));
    public static final RegistryObject<Block> PRECISION_CRYSTAL_BLOCK = BLOCKS.register("precision_crystal_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).mapColor(MapColor.COLOR_LIGHT_GREEN).sound(PRECISION_CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> NETHER_SALT_BLOCK = BLOCKS.register("nether_salt_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_BLOCK).sound(NETHER_SALT_SOUNDS)));
    public static final RegistryObject<Block> NETHER_SALT_ORE = BLOCKS.register("nether_salt_ore", () -> new NetherSaltOreBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_GOLD_ORE).sound(NETHER_SALT_ORE_SOUNDS)));

    public static final RegistryObject<Block> ARCANE_WOOD_LOG = BLOCKS.register("arcane_wood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> ARCANE_WOOD = BLOCKS.register("arcane_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD_LOG = BLOCKS.register("stripped_arcane_wood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD = BLOCKS.register("stripped_arcane_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> ARCANE_WOOD_PLANKS = BLOCKS.register("arcane_wood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> ARCANE_WOOD_STAIRS = BLOCKS.register("arcane_wood_stairs", () -> new StairBlock(() -> ARCANE_WOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_SLAB = BLOCKS.register("arcane_wood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_BAULK = BLOCKS.register("arcane_wood_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_LOG.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_CROSS_BAULK = BLOCKS.register("arcane_wood_cross_baulk", () -> new CrossBalkBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_LOG.get())));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD_BAULK = BLOCKS.register("stripped_arcane_wood_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(STRIPPED_ARCANE_WOOD_LOG.get())));
    public static final RegistryObject<Block> STRIPPED_ARCANE_WOOD_CROSS_BAULK = BLOCKS.register("stripped_arcane_wood_cross_baulk", () -> new CrossBalkBlock(BlockBehaviour.Properties.copy(STRIPPED_ARCANE_WOOD_LOG.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_PLANKS_BAULK = BLOCKS.register("arcane_wood_planks_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_PLANKS_CROSS_BAULK = BLOCKS.register("arcane_wood_planks_cross_baulk", () -> new CrossBalkBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_FENCE = BLOCKS.register("arcane_wood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_WOOD_FENCE_GATE = BLOCKS.register("arcane_wood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()), ARCANE_WOOD_TYPE.resolve().get()));
    public static final RegistryObject<Block> ARCANE_WOOD_DOOR = BLOCKS.register("arcane_wood_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion(), ARCANE_WOOD_BLOCK_SET.resolve().get()));
    public static final RegistryObject<Block> ARCANE_WOOD_TRAPDOOR = BLOCKS.register("arcane_wood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion(), ARCANE_WOOD_BLOCK_SET.resolve().get()));
    public static final RegistryObject<Block> ARCANE_WOOD_PRESSURE_PLATE = BLOCKS.register("arcane_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion().noCollission(), ARCANE_WOOD_BLOCK_SET.resolve().get()));
    public static final RegistryObject<Block> ARCANE_WOOD_BUTTON = BLOCKS.register("arcane_wood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(ARCANE_WOOD_SOUNDS), ARCANE_WOOD_BLOCK_SET.resolve().get(), 30, true));
    public static final RegistryObject<Block> ARCANE_WOOD_SIGN = BLOCKS.register("arcane_wood_sign", () -> new CustomStandingSignBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion().noCollission(), ARCANE_WOOD_TYPE.resolve().get()));
    public static final RegistryObject<Block> ARCANE_WOOD_WALL_SIGN = BLOCKS.register("arcane_wood_wall_sign", () -> new CustomWallSignBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion().noCollission(), ARCANE_WOOD_TYPE.resolve().get()));
    public static final RegistryObject<Block> ARCANE_WOOD_HANGING_SIGN = BLOCKS.register("arcane_wood_hanging_sign", () -> new CustomCeilingHangingSignBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).sound(ARCANE_WOOD_HANGING_SIGN_SOUNDS).noOcclusion().noCollission(), ARCANE_WOOD_TYPE.resolve().get()));
    public static final RegistryObject<Block> ARCANE_WOOD_WALL_HANGING_SIGN = BLOCKS.register("arcane_wood_wall_hanging_sign", () -> new CustomWallHangingSignBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).sound(ARCANE_WOOD_HANGING_SIGN_SOUNDS).noOcclusion().noCollission(), ARCANE_WOOD_TYPE.resolve().get()));
    public static final RegistryObject<Block> ARCANE_WOOD_LEAVES = BLOCKS.register("arcane_wood_leaves", () -> new ArcaneWoodLeavesBlock(BlockBehaviour.Properties.copy(Blocks.AZALEA_LEAVES).lightLevel((state) -> 5)));
    public static final RegistryObject<Block> ARCANE_WOOD_SAPLING = BLOCKS.register("arcane_wood_sapling", () -> new SaplingBlock(new ArcaneWoodTree(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING).sound(SoundType.AZALEA)));
    public static final RegistryObject<Block> POTTED_ARCANE_WOOD_SAPLING = BLOCKS.register("potted_arcane_wood_sapling", () -> new FlowerPotBlock(ARCANE_WOOD_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));

    public static final RegistryObject<Block> INNOCENT_WOOD_LOG = BLOCKS.register("innocent_wood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_LOG).mapColor(MapColor.TERRACOTTA_GRAY).sound(INNOCENT_WOOD_SOUNDS)));
    public static final RegistryObject<Block> INNOCENT_WOOD = BLOCKS.register("innocent_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_LOG).mapColor(MapColor.TERRACOTTA_GRAY).sound(INNOCENT_WOOD_SOUNDS)));
    public static final RegistryObject<Block> STRIPPED_INNOCENT_WOOD_LOG = BLOCKS.register("stripped_innocent_wood_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_LOG).mapColor(MapColor.TERRACOTTA_GRAY).sound(INNOCENT_WOOD_SOUNDS)));
    public static final RegistryObject<Block> STRIPPED_INNOCENT_WOOD = BLOCKS.register("stripped_innocent_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_LOG).mapColor(MapColor.TERRACOTTA_GRAY).sound(INNOCENT_WOOD_SOUNDS)));
    public static final RegistryObject<Block> INNOCENT_WOOD_PLANKS = BLOCKS.register("innocent_wood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.CHERRY_PLANKS).mapColor(MapColor.TERRACOTTA_GRAY).sound(INNOCENT_WOOD_SOUNDS)));
    public static final RegistryObject<Block> INNOCENT_WOOD_STAIRS = BLOCKS.register("innocent_wood_stairs", () -> new StairBlock(() -> INNOCENT_WOOD_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_SLAB = BLOCKS.register("innocent_wood_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_BAULK = BLOCKS.register("innocent_wood_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_LOG.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_CROSS_BAULK = BLOCKS.register("innocent_wood_cross_baulk", () -> new CrossBalkBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_LOG.get())));
    public static final RegistryObject<Block> STRIPPED_INNOCENT_WOOD_BAULK = BLOCKS.register("stripped_innocent_wood_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(STRIPPED_INNOCENT_WOOD_LOG.get())));
    public static final RegistryObject<Block> STRIPPED_INNOCENT_WOOD_CROSS_BAULK = BLOCKS.register("stripped_innocent_wood_cross_baulk", () -> new CrossBalkBlock(BlockBehaviour.Properties.copy(STRIPPED_INNOCENT_WOOD_LOG.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_PLANKS_BAULK = BLOCKS.register("innocent_wood_planks_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_PLANKS_CROSS_BAULK = BLOCKS.register("innocent_wood_planks_cross_baulk", () -> new CrossBalkBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_FENCE = BLOCKS.register("innocent_wood_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> INNOCENT_WOOD_FENCE_GATE = BLOCKS.register("innocent_wood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()), INNOCENT_WOOD_TYPE.resolve().get()));
    public static final RegistryObject<Block> INNOCENT_WOOD_DOOR = BLOCKS.register("innocent_wood_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion(), ARCANE_WOOD_BLOCK_SET.resolve().get()));
    public static final RegistryObject<Block> INNOCENT_WOOD_TRAPDOOR = BLOCKS.register("innocent_wood_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion(), ARCANE_WOOD_BLOCK_SET.resolve().get()));
    public static final RegistryObject<Block> INNOCENT_WOOD_PRESSURE_PLATE = BLOCKS.register("innocent_wood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion().noCollission(), BlockSetType.OAK));
    public static final RegistryObject<Block> INNOCENT_WOOD_BUTTON = BLOCKS.register("innocent_wood_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_BUTTON).sound(INNOCENT_WOOD_SOUNDS), ARCANE_WOOD_BLOCK_SET.resolve().get(), 30, true));
    public static final RegistryObject<Block> INNOCENT_WOOD_SIGN = BLOCKS.register("innocent_wood_sign", () -> new CustomStandingSignBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion().noCollission(), INNOCENT_WOOD_TYPE.resolve().get()));
    public static final RegistryObject<Block> INNOCENT_WOOD_WALL_SIGN = BLOCKS.register("innocent_wood_wall_sign", () -> new CustomWallSignBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion().noCollission(), INNOCENT_WOOD_TYPE.resolve().get()));
    public static final RegistryObject<Block> INNOCENT_WOOD_HANGING_SIGN = BLOCKS.register("innocent_wood_hanging_sign", () -> new CustomCeilingHangingSignBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).sound(INNOCENT_WOOD_HANGING_SIGN_SOUNDS).noOcclusion().noCollission(), INNOCENT_WOOD_TYPE.resolve().get()));
    public static final RegistryObject<Block> INNOCENT_WOOD_WALL_HANGING_SIGN = BLOCKS.register("innocent_wood_wall_hanging_sign", () -> new CustomWallHangingSignBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).sound(INNOCENT_WOOD_HANGING_SIGN_SOUNDS).noOcclusion().noCollission(), INNOCENT_WOOD_TYPE.resolve().get()));
    public static final RegistryObject<Block> INNOCENT_WOOD_LEAVES = BLOCKS.register("innocent_wood_leaves", () -> new InnocentWoodLeavesBlock(BlockBehaviour.Properties.copy(Blocks.CHERRY_LEAVES)));
    public static final RegistryObject<Block> INNOCENT_WOOD_SAPLING = BLOCKS.register("innocent_wood_sapling", () -> new SaplingBlock(new InnocentWoodTree(), BlockBehaviour.Properties.copy(Blocks.CHERRY_SAPLING)));
    public static final RegistryObject<Block> POTTED_INNOCENT_WOOD_SAPLING = BLOCKS.register("potted_innocent_wood_sapling", () -> new FlowerPotBlock(INNOCENT_WOOD_SAPLING.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> PETALS_OF_INNOCENCE = BLOCKS.register("petals_of_innocence", () -> new PetalsOfInnocenceBlock(BlockBehaviour.Properties.copy(Blocks.PINK_PETALS)));
    public static final RegistryObject<Block> POTTED_PETALS_OF_INNOCENCE = BLOCKS.register("potted_petals_of_innocence", () -> new FlowerPotBlock(PETALS_OF_INNOCENCE.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> POTTED_PINK_PETALS = BLOCKS.register("potted_pink_petals", () -> new FlowerPotBlock(Blocks.PINK_PETALS, BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));

    public static final RegistryObject<Block> WISESTONE = BLOCKS.register("wisestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).mapColor(MapColor.TERRACOTTA_BLACK).sound(WISESTONE_SOUNDS)));
    public static final RegistryObject<Block> WISESTONE_STAIRS = BLOCKS.register("wisestone_stairs", () -> new StairBlock(() -> WISESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(WISESTONE.get())));
    public static final RegistryObject<Block> WISESTONE_SLAB = BLOCKS.register("wisestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WISESTONE.get())));
    public static final RegistryObject<Block> WISESTONE_WALL = BLOCKS.register("wisestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(WISESTONE.get())));
    public static final RegistryObject<Block> POLISHED_WISESTONE = BLOCKS.register("polished_wisestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).mapColor(MapColor.TERRACOTTA_BLACK).sound(POLISHED_WISESTONE_SOUNDS)));
    public static final RegistryObject<Block> POLISHED_WISESTONE_STAIRS = BLOCKS.register("polished_wisestone_stairs", () -> new StairBlock(() -> POLISHED_WISESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> POLISHED_WISESTONE_SLAB = BLOCKS.register("polished_wisestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> POLISHED_WISESTONE_WALL = BLOCKS.register("polished_wisestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> WISESTONE_BRICKS = BLOCKS.register("wisestone_bricks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_BRICKS).mapColor(MapColor.TERRACOTTA_BLACK).sound(WISESTONE_BRICKS_SOUNDS)));
    public static final RegistryObject<Block> WISESTONE_BRICKS_STAIRS = BLOCKS.register("wisestone_bricks_stairs", () -> new StairBlock(() -> WISESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(WISESTONE_BRICKS.get())));
    public static final RegistryObject<Block> WISESTONE_BRICKS_SLAB = BLOCKS.register("wisestone_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WISESTONE_BRICKS.get())));
    public static final RegistryObject<Block> WISESTONE_BRICKS_WALL = BLOCKS.register("wisestone_bricks_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(WISESTONE_BRICKS.get())));
    public static final RegistryObject<Block> WISESTONE_TILE = BLOCKS.register("wisestone_tile", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE_TILES).mapColor(MapColor.TERRACOTTA_BLACK).sound(WISESTONE_TILE_SOUNDS)));
    public static final RegistryObject<Block> WISESTONE_TILE_STAIRS = BLOCKS.register("wisestone_tile_stairs", () -> new StairBlock(() -> WISESTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(WISESTONE_TILE.get())));
    public static final RegistryObject<Block> WISESTONE_TILE_SLAB = BLOCKS.register("wisestone_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(WISESTONE_TILE.get())));
    public static final RegistryObject<Block> WISESTONE_TILE_WALL = BLOCKS.register("wisestone_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(WISESTONE_TILE.get())));
    public static final RegistryObject<Block> CHISELED_WISESTONE = BLOCKS.register("chiseled_wisestone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.POLISHED_DEEPSLATE).mapColor(MapColor.TERRACOTTA_BLACK).sound(CHISELED_WISESTONE_SOUNDS)));
    public static final RegistryObject<Block> CHISELED_WISESTONE_STAIRS = BLOCKS.register("chiseled_wisestone_stairs", () -> new StairBlock(() -> POLISHED_WISESTONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(CHISELED_WISESTONE.get())));
    public static final RegistryObject<Block> CHISELED_WISESTONE_SLAB = BLOCKS.register("chiseled_wisestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CHISELED_WISESTONE.get())));
    public static final RegistryObject<Block> CHISELED_WISESTONE_WALL = BLOCKS.register("chiseled_wisestone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(CHISELED_WISESTONE.get())));
    public static final RegistryObject<Block> WISESTONE_PILLAR = BLOCKS.register("wisestone_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> POLISHED_WISESTONE_PRESSURE_PLATE = BLOCKS.register("polished_wisestone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).mapColor(MapColor.TERRACOTTA_BLACK).sound(POLISHED_WISESTONE_SOUNDS).noOcclusion().noCollission(), WISESTONE_BLOCK_SET.resolve().get()));
    public static final RegistryObject<Block> POLISHED_WISESTONE_BUTTON = BLOCKS.register("polished_wisestone_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.POLISHED_BLACKSTONE_BUTTON).mapColor(MapColor.TERRACOTTA_BLACK).sound(POLISHED_WISESTONE_SOUNDS), WISESTONE_BLOCK_SET.resolve().get(), 20, false));

    public static final RegistryObject<Block> ARCANE_LINEN = BLOCKS.register("arcane_linen", () -> new ArcaneLinenBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));
    public static final RegistryObject<Block> ARCANE_LINEN_HAY = BLOCKS.register("arcane_linen_hay", () -> new HayBlock(BlockBehaviour.Properties.copy(Blocks.HAY_BLOCK)));

    public static final RegistryObject<Block> MOR = BLOCKS.register("mor", () -> new MorBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM).mapColor(MapColor.COLOR_BLUE).sound(MOR_SOUNDS)));
    public static final RegistryObject<Block> POTTED_MOR = BLOCKS.register("potted_mor", () -> new FlowerPotBlock(MOR.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> MOR_BLOCK = BLOCKS.register("mor_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK).mapColor(MapColor.COLOR_BLUE).sound(MOR_BLOCK_SOUNDS)));
    public static final RegistryObject<Block> ELDER_MOR = BLOCKS.register("elder_mor", () -> new MorBlock(BlockBehaviour.Properties.copy(Blocks.RED_MUSHROOM).mapColor(MapColor.COLOR_BLACK).sound(ELDER_MOR_SOUNDS)));
    public static final RegistryObject<Block> POTTED_ELDER_MOR = BLOCKS.register("potted_elder_mor", () -> new FlowerPotBlock(ELDER_MOR.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> ELDER_MOR_BLOCK = BLOCKS.register("elder_mor_block", () -> new HugeMushroomBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_MUSHROOM_BLOCK).mapColor(MapColor.COLOR_BLACK).sound(ELDER_MOR_BLOCK_SOUNDS)));

    public static final RegistryObject<Block> PITCHER_TURNIP = BLOCKS.register("pitcher_turnip", () -> new PitcherTurnipBlock(BlockBehaviour.Properties.of().strength(0.5F).mapColor(MapColor.COLOR_ORANGE).sound(SoundType.CROP).noOcclusion()));
    public static final RegistryObject<Block> PITCHER_TURNIP_BLOCK = BLOCKS.register("pitcher_turnip_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.PUMPKIN)));
    public static final RegistryObject<Block> POTTED_PITCHER_TURNIP = BLOCKS.register("potted_pitcher_turnip", () -> new PottedPitcherTurnipBlock(PITCHER_TURNIP.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> SHINY_CLOVER_CROP = BLOCKS.register("shiny_clover_crop", () -> new ShinyCloverCropBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> SHINY_CLOVER = BLOCKS.register("shiny_clover", () -> new ShinyCloverBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().sound(SoundType.PINK_PETALS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> POTTED_SHINY_CLOVER = BLOCKS.register("potted_shiny_clover", () -> new FlowerPotBlock(SHINY_CLOVER.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> UNDERGROUND_GRAPE_VINES = BLOCKS.register("underground_grape_vines", () -> new UndergroundGrapeVinesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().noCollission().instabreak().sound(SoundType.CAVE_VINES).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> UNDERGROUND_GRAPE_VINES_PLANT = BLOCKS.register("underground_grape_vines_plant", () -> new UndergroundGrapeVinesPlantBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).randomTicks().noCollission().instabreak().sound(SoundType.CAVE_VINES).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> CORK_BAMBOO_SAPLING = BLOCKS.register("cork_bamboo_sapling", () -> new CorkBambooSaplingBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).forceSolidOn().randomTicks().instabreak().noCollission().strength(1.0F).sound(SoundType.BAMBOO_SAPLING).offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> CORK_BAMBOO = BLOCKS.register("cork_bamboo", () -> new CorkBambooStalkBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).forceSolidOn().randomTicks().instabreak().strength(1.0F).sound(SoundType.BAMBOO).noOcclusion().dynamicShape().offsetType(BlockBehaviour.OffsetType.XZ).ignitedByLava().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> POTTED_CORK_BAMBOO = BLOCKS.register("potted_cork_bamboo", () -> new FlowerPotBlock(CORK_BAMBOO.get(), BlockBehaviour.Properties.copy(Blocks.FLOWER_POT).instabreak().noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_BLOCK = BLOCKS.register("cork_bamboo_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> CORK_BAMBOO_PLANKS = BLOCKS.register("cork_bamboo_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BAMBOO_BLOCK).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> CORK_BAMBOO_CHISELED_PLANKS = BLOCKS.register("cork_bamboo_chiseled_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BAMBOO_BLOCK).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> CORK_BAMBOO_STAIRS = BLOCKS.register("cork_bamboo_stairs", () -> new StairBlock(() -> CORK_BAMBOO_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_CHISELED_STAIRS = BLOCKS.register("cork_bamboo_chiseled_stairs", () -> new StairBlock(() -> CORK_BAMBOO_CHISELED_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(CORK_BAMBOO_CHISELED_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_SLAB = BLOCKS.register("cork_bamboo_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_CHISELED_SLAB = BLOCKS.register("cork_bamboo_chiseled_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_CHISELED_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_BAULK = BLOCKS.register("cork_bamboo_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_BLOCK.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_CROSS_BAULK = BLOCKS.register("cork_bamboo_cross_baulk", () -> new CrossBalkBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_BLOCK.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_PLANKS_BAULK = BLOCKS.register("cork_bamboo_planks_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_PLANKS_CROSS_BAULK = BLOCKS.register("cork_bamboo_planks_cross_baulk", () -> new CrossBalkBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_CHISELED_PLANKS_BAULK = BLOCKS.register("cork_bamboo_chiseled_planks_baulk", () -> new BaulkBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_CHISELED_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK = BLOCKS.register("cork_bamboo_chiseled_planks_cross_baulk", () -> new CrossBalkBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_CHISELED_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_FENCE = BLOCKS.register("cork_bamboo_fence", () -> new FenceBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_FENCE_GATE = BLOCKS.register("cork_bamboo_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()), CORK_BAMBOO_TYPE.resolve().get()));
    public static final RegistryObject<Block> CORK_BAMBOO_DOOR = BLOCKS.register("cork_bamboo_door", () -> new DoorBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion(), ARCANE_WOOD_BLOCK_SET.resolve().get()));
    public static final RegistryObject<Block> CORK_BAMBOO_TRAPDOOR = BLOCKS.register("cork_bamboo_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion(), ARCANE_WOOD_BLOCK_SET.resolve().get()));
    public static final RegistryObject<Block> CORK_BAMBOO_PRESSURE_PLATE = BLOCKS.register("cork_bamboo_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion().noCollission(), ARCANE_WOOD_BLOCK_SET.resolve().get()));
    public static final RegistryObject<Block> CORK_BAMBOO_BUTTON = BLOCKS.register("cork_bamboo_button", () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON).sound(ARCANE_WOOD_SOUNDS), ARCANE_WOOD_BLOCK_SET.resolve().get(), 30, true));
    public static final RegistryObject<Block> CORK_BAMBOO_SIGN = BLOCKS.register("cork_bamboo_sign", () -> new CustomStandingSignBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion().noCollission(), CORK_BAMBOO_TYPE.resolve().get()));
    public static final RegistryObject<Block> CORK_BAMBOO_WALL_SIGN = BLOCKS.register("cork_bamboo_wall_sign", () -> new CustomWallSignBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion().noCollission(), CORK_BAMBOO_TYPE.resolve().get()));
    public static final RegistryObject<Block> CORK_BAMBOO_HANGING_SIGN = BLOCKS.register("cork_bamboo_hanging_sign", () -> new CustomCeilingHangingSignBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).sound(ARCANE_WOOD_HANGING_SIGN_SOUNDS).noOcclusion().noCollission(), CORK_BAMBOO_TYPE.resolve().get()));
    public static final RegistryObject<Block> CORK_BAMBOO_WALL_HANGING_SIGN = BLOCKS.register("cork_bamboo_wall_hanging_sign", () -> new CustomWallHangingSignBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).sound(ARCANE_WOOD_HANGING_SIGN_SOUNDS).noOcclusion().noCollission(), CORK_BAMBOO_TYPE.resolve().get()));

    public static final RegistryObject<Block> PLACED_ITEMS_BLOCK = BLOCKS.register("placed_items", () -> new PlacedItemsBlock(BlockBehaviour.Properties.of().strength(0.25F).mapColor(MapColor.COLOR_BROWN).sound(SoundType.CROP).noOcclusion()));

    public static final RegistryObject<Block> ARCANUM_SEED_BLOCK = BLOCKS.register("arcanum_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_LIGHT_BLUE).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> ARCANUM_GROWTH = BLOCKS.register("arcanum_growth", () -> new ArcanumGrowthBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_LIGHT_BLUE).sound(CRYSTAL_SOUNDS)));

    public static final RegistryObject<Block> EARTH_CRYSTAL_SEED_BLOCK = BLOCKS.register("earth_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_GREEN).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> WATER_CRYSTAL_SEED_BLOCK = BLOCKS.register("water_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_BLUE).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> AIR_CRYSTAL_SEED_BLOCK = BLOCKS.register("air_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_YELLOW).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> FIRE_CRYSTAL_SEED_BLOCK = BLOCKS.register("fire_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_RED).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> VOID_CRYSTAL_SEED_BLOCK = BLOCKS.register("void_crystal_seed", () -> new CrystalSeedBlock(BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_PURPLE).sound(CRYSTAL_SOUNDS)));

    public static final RegistryObject<Block> EARTH_CRYSTAL_GROWTH = BLOCKS.register("earth_crystal_growth", () -> new CrystalGrowthBlock(EARTH_CRYSTAL_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_GREEN).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> WATER_CRYSTAL_GROWTH = BLOCKS.register("water_crystal_growth", () -> new CrystalGrowthBlock(WATER_CRYSTAL_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_BLUE).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> AIR_CRYSTAL_GROWTH = BLOCKS.register("air_crystal_growth", () -> new CrystalGrowthBlock(AIR_CRYSTAL_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_YELLOW).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> FIRE_CRYSTAL_GROWTH = BLOCKS.register("fire_crystal_growth", () -> new CrystalGrowthBlock(FIRE_CRYSTAL_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_RED).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> VOID_CRYSTAL_GROWTH = BLOCKS.register("void_crystal_growth", () -> new CrystalGrowthBlock(VOID_CRYSTAL_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_PURPLE).sound(CRYSTAL_SOUNDS)));

    public static final RegistryObject<Block> EARTH_CRYSTAL_BLOCK = BLOCKS.register("earth_crystal", () -> new CrystalBlock(EARTH_CRYSTAL_TYPE, CRYSTAL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_GREEN).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> WATER_CRYSTAL_BLOCK = BLOCKS.register("water_crystal", () -> new CrystalBlock(WATER_CRYSTAL_TYPE, CRYSTAL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_BLUE).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> AIR_CRYSTAL_BLOCK = BLOCKS.register("air_crystal", () -> new CrystalBlock(AIR_CRYSTAL_TYPE, CRYSTAL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_YELLOW).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> FIRE_CRYSTAL_BLOCK = BLOCKS.register("fire_crystal", () -> new CrystalBlock(FIRE_CRYSTAL_TYPE, CRYSTAL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_RED).sound(CRYSTAL_SOUNDS)));
    public static final RegistryObject<Block> VOID_CRYSTAL_BLOCK = BLOCKS.register("void_crystal", () -> new CrystalBlock(VOID_CRYSTAL_TYPE, CRYSTAL_POLISHING_TYPE, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).mapColor(MapColor.COLOR_PURPLE).sound(CRYSTAL_SOUNDS)));

    public static final RegistryObject<Block> FACETED_EARTH_CRYSTAL_BLOCK = BLOCKS.register("faceted_earth_crystal", () -> new CrystalBlock(EARTH_CRYSTAL_TYPE, FACETED_POLISHING_TYPE, BlockBehaviour.Properties.copy(EARTH_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> FACETED_WATER_CRYSTAL_BLOCK = BLOCKS.register("faceted_water_crystal", () -> new CrystalBlock(WATER_CRYSTAL_TYPE, FACETED_POLISHING_TYPE, BlockBehaviour.Properties.copy(WATER_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> FACETED_AIR_CRYSTAL_BLOCK = BLOCKS.register("faceted_air_crystal", () -> new CrystalBlock(AIR_CRYSTAL_TYPE, FACETED_POLISHING_TYPE, BlockBehaviour.Properties.copy(AIR_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> FACETED_FIRE_CRYSTAL_BLOCK = BLOCKS.register("faceted_fire_crystal", () -> new CrystalBlock(FIRE_CRYSTAL_TYPE, FACETED_POLISHING_TYPE, BlockBehaviour.Properties.copy(FIRE_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> FACETED_VOID_CRYSTAL_BLOCK = BLOCKS.register("faceted_void_crystal", () -> new CrystalBlock(VOID_CRYSTAL_TYPE, FACETED_POLISHING_TYPE, BlockBehaviour.Properties.copy(VOID_CRYSTAL_BLOCK.get())));

    public static final RegistryObject<Block> ADVANCED_EARTH_CRYSTAL_BLOCK = BLOCKS.register("advanced_earth_crystal", () -> new CrystalBlock(EARTH_CRYSTAL_TYPE, ADVANCED_POLISHING_TYPE, BlockBehaviour.Properties.copy(EARTH_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> ADVANCED_WATER_CRYSTAL_BLOCK = BLOCKS.register("advanced_water_crystal", () -> new CrystalBlock(WATER_CRYSTAL_TYPE, ADVANCED_POLISHING_TYPE, BlockBehaviour.Properties.copy(WATER_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> ADVANCED_AIR_CRYSTAL_BLOCK = BLOCKS.register("advanced_air_crystal", () -> new CrystalBlock(AIR_CRYSTAL_TYPE, ADVANCED_POLISHING_TYPE, BlockBehaviour.Properties.copy(AIR_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> ADVANCED_FIRE_CRYSTAL_BLOCK = BLOCKS.register("advanced_fire_crystal", () -> new CrystalBlock(FIRE_CRYSTAL_TYPE, ADVANCED_POLISHING_TYPE, BlockBehaviour.Properties.copy(FIRE_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> ADVANCED_VOID_CRYSTAL_BLOCK = BLOCKS.register("advanced_void_crystal", () -> new CrystalBlock(VOID_CRYSTAL_TYPE, ADVANCED_POLISHING_TYPE, BlockBehaviour.Properties.copy(VOID_CRYSTAL_BLOCK.get())));

    public static final RegistryObject<Block> MASTERFUL_EARTH_CRYSTAL_BLOCK = BLOCKS.register("masterful_earth_crystal", () -> new CrystalBlock(EARTH_CRYSTAL_TYPE, MASTERFUL_POLISHING_TYPE, BlockBehaviour.Properties.copy(EARTH_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> MASTERFUL_WATER_CRYSTAL_BLOCK = BLOCKS.register("masterful_water_crystal", () -> new CrystalBlock(WATER_CRYSTAL_TYPE, MASTERFUL_POLISHING_TYPE, BlockBehaviour.Properties.copy(WATER_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> MASTERFUL_AIR_CRYSTAL_BLOCK = BLOCKS.register("masterful_air_crystal", () -> new CrystalBlock(AIR_CRYSTAL_TYPE, MASTERFUL_POLISHING_TYPE, BlockBehaviour.Properties.copy(AIR_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> MASTERFUL_FIRE_CRYSTAL_BLOCK = BLOCKS.register("masterful_fire_crystal", () -> new CrystalBlock(FIRE_CRYSTAL_TYPE, MASTERFUL_POLISHING_TYPE, BlockBehaviour.Properties.copy(FIRE_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> MASTERFUL_VOID_CRYSTAL_BLOCK = BLOCKS.register("masterful_void_crystal", () -> new CrystalBlock(VOID_CRYSTAL_TYPE, MASTERFUL_POLISHING_TYPE, BlockBehaviour.Properties.copy(VOID_CRYSTAL_BLOCK.get())));

    public static final RegistryObject<Block> PURE_EARTH_CRYSTAL_BLOCK = BLOCKS.register("pure_earth_crystal", () -> new CrystalBlock(EARTH_CRYSTAL_TYPE, PURE_POLISHING_TYPE, BlockBehaviour.Properties.copy(EARTH_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> PURE_WATER_CRYSTAL_BLOCK = BLOCKS.register("pure_water_crystal", () -> new CrystalBlock(WATER_CRYSTAL_TYPE, PURE_POLISHING_TYPE, BlockBehaviour.Properties.copy(WATER_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> PURE_AIR_CRYSTAL_BLOCK = BLOCKS.register("pure_air_crystal", () -> new CrystalBlock(AIR_CRYSTAL_TYPE, PURE_POLISHING_TYPE, BlockBehaviour.Properties.copy(AIR_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> PURE_FIRE_CRYSTAL_BLOCK = BLOCKS.register("pure_fire_crystal", () -> new CrystalBlock(FIRE_CRYSTAL_TYPE, PURE_POLISHING_TYPE, BlockBehaviour.Properties.copy(FIRE_CRYSTAL_BLOCK.get())));
    public static final RegistryObject<Block> PURE_VOID_CRYSTAL_BLOCK = BLOCKS.register("pure_void_crystal", () -> new CrystalBlock(VOID_CRYSTAL_TYPE, PURE_POLISHING_TYPE, BlockBehaviour.Properties.copy(VOID_CRYSTAL_BLOCK.get())));

    public static final RegistryObject<Block> WHITE_ARCANE_LUMOS = BLOCKS.register("white_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.WHITE, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> LIGHT_GRAY_ARCANE_LUMOS = BLOCKS.register("light_gray_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.LIGHT_GRAY, BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> GRAY_ARCANE_LUMOS = BLOCKS.register("gray_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.GRAY, BlockBehaviour.Properties.copy(Blocks.GRAY_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> BLACK_ARCANE_LUMOS = BLOCKS.register("black_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.BLACK, BlockBehaviour.Properties.copy(Blocks.BLACK_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> BROWN_ARCANE_LUMOS = BLOCKS.register("brown_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.BROWN, BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> RED_ARCANE_LUMOS = BLOCKS.register("red_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.RED, BlockBehaviour.Properties.copy(Blocks.RED_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> ORANGE_ARCANE_LUMOS = BLOCKS.register("orange_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.ORANGE, BlockBehaviour.Properties.copy(Blocks.ORANGE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> YELLOW_ARCANE_LUMOS = BLOCKS.register("yellow_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.YELLOW, BlockBehaviour.Properties.copy(Blocks.YELLOW_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> LIME_ARCANE_LUMOS = BLOCKS.register("lime_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.LIME, BlockBehaviour.Properties.copy(Blocks.LIME_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> GREEN_ARCANE_LUMOS = BLOCKS.register("green_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.GREEN, BlockBehaviour.Properties.copy(Blocks.GREEN_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> CYAN_ARCANE_LUMOS = BLOCKS.register("cyan_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.CYAN, BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> LIGHT_BLUE_ARCANE_LUMOS = BLOCKS.register("light_blue_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.LIGHT_BLUE, BlockBehaviour.Properties.copy(Blocks.LIGHT_BLUE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> BLUE_ARCANE_LUMOS = BLOCKS.register("blue_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.BLUE, BlockBehaviour.Properties.copy(Blocks.BLUE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> PURPLE_ARCANE_LUMOS = BLOCKS.register("purple_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.PURPLE, BlockBehaviour.Properties.copy(Blocks.PURPLE_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> MAGENTA_ARCANE_LUMOS = BLOCKS.register("magenta_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.MAGENTA, BlockBehaviour.Properties.copy(Blocks.MAGENTA_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> PINK_ARCANE_LUMOS = BLOCKS.register("pink_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.PINK, BlockBehaviour.Properties.copy(Blocks.PINK_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> RAINBOW_ARCANE_LUMOS = BLOCKS.register("rainbow_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.RAINBOW, BlockBehaviour.Properties.copy(Blocks.PINK_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));
    public static final RegistryObject<Block> COSMIC_ARCANE_LUMOS = BLOCKS.register("cosmic_arcane_lumos", () -> new ArcaneLumosBlock(ArcaneLumosBlock.Colors.COSMIC, BlockBehaviour.Properties.copy(Blocks.MAGENTA_WOOL).lightLevel((state) -> 15).noOcclusion().noCollission().instabreak()));

    public static final RegistryObject<Block> ARCANE_PEDESTAL = BLOCKS.register("arcane_pedestal", () -> new ArcanePedestalBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> HOVERING_TOME_STAND = BLOCKS.register("hovering_tome_stand", () -> new HoveringTomeStandBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> WISSEN_ALTAR = BLOCKS.register("wissen_altar", () -> new WissenAltarBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> WISSEN_TRANSLATOR = BLOCKS.register("wissen_translator", () -> new WissenTranslatorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> WISSEN_CRYSTALLIZER = BLOCKS.register("wissen_crystallizer", () -> new WissenCrystallizerBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_WORKBENCH = BLOCKS.register("arcane_workbench", () -> new ArcaneWorkbenchBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> WISSEN_CELL = BLOCKS.register("wissen_cell", () -> new WissenCellBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> JEWELER_TABLE = BLOCKS.register("jeweler_table", () -> new JewelerTableBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ALTAR_OF_DROUGHT = BLOCKS.register("altar_of_drought", () -> new AltarOfDroughtBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> TOTEM_BASE = BLOCKS.register("totem_base", () -> new TotemBaseBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> TOTEM_OF_FLAMES = BLOCKS.register("totem_of_flames", () -> new TotemOfFlamesBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).lightLevel(TotemOfFlamesBlock::getLightLevel)));
    public static final RegistryObject<Block> EXPERIENCE_TOTEM = BLOCKS.register("experience_totem", () -> new ExperienceTotemBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> TOTEM_OF_EXPERIENCE_ABSORPTION = BLOCKS.register("totem_of_experience_absorption", () -> new TotemOfExperienceAbsorptionBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> TOTEM_OF_DISENCHANT = BLOCKS.register("totem_of_disenchant", () -> new TotemOfDisenchantBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ARCANE_ITERATOR = BLOCKS.register("arcane_iterator", () -> new ArcaneIteratorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));

    public static final RegistryObject<Block> WISESTONE_PEDESTAL = BLOCKS.register("wisestone_pedestal", () -> new ArcanePedestalBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> WISESTONE_HOVERING_TOME_STAND = BLOCKS.register("wisestone_hovering_tome_stand", () -> new HoveringTomeStandBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> FLUID_PIPE = BLOCKS.register("fluid_pipe", () -> new FluidPipeBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> FLUID_EXTRACTOR = BLOCKS.register("fluid_extractor", () -> new FluidExtractorBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> STEAM_PIPE = BLOCKS.register("steam_pipe", () -> new SteamPipeBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> STEAM_EXTRACTOR = BLOCKS.register("steam_extractor", () -> new SteamExtractorBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> ALCHEMY_FURNACE = BLOCKS.register("alchemy_furnace", () -> new AlchemyFurnaceBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).lightLevel((state) -> state.getValue(BlockStateProperties.LIT) ? 13 : 0)));
    public static final RegistryObject<Block> ORBITAL_FLUID_RETAINER = BLOCKS.register("orbital_fluid_retainer", () -> new OrbitalFluidRetainerBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> STEAM_THERMAL_STORAGE = BLOCKS.register("steam_thermal_storage", () -> new SteamThermalStorageBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> ALCHEMY_MACHINE = BLOCKS.register("alchemy_machine", () -> new AlchemyMachineBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> ALCHEMY_BOILER = BLOCKS.register("alchemy_boiler", () -> new AlchemyBoilerBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> ARCANE_CENSER = BLOCKS.register("arcane_censer", () -> new ArcaneCenserBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));

    public static final RegistryObject<Block> CORK_BAMBOO_PEDESTAL = BLOCKS.register("cork_bamboo_pedestal", () -> new ArcanePedestalBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));
    public static final RegistryObject<Block> CORK_BAMBOO_HOVERING_TOME_STAND = BLOCKS.register("cork_bamboo_hovering_tome_stand", () -> new HoveringTomeStandBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get())));

    public static final RegistryObject<Block> LIGHT_EMITTER = BLOCKS.register("light_emitter", () -> new LightEmitterBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> LIGHT_TRANSFER_LENS = BLOCKS.register("light_transfer_lens", () -> new LightTransferLensBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> RUNIC_PEDESTAL = BLOCKS.register("runic_pedestal", () -> new RunicPedestalBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));

    public static final RegistryObject<Block> ENGRAVED_WISESTONE = BLOCKS.register("engraved_wisestone", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_LUNAM = BLOCKS.register("engraved_wisestone_lunam", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), LUNAM_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_VITA = BLOCKS.register("engraved_wisestone_vita", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), VITA_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_SOLEM = BLOCKS.register("engraved_wisestone_solem", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), SOLEM_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_MORS = BLOCKS.register("engraved_wisestone_mors", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), MORS_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_MIRACULUM = BLOCKS.register("engraved_wisestone_miraculum", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), MIRACULUM_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_TEMPUS = BLOCKS.register("engraved_wisestone_tempus", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), TEMPUS_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_STATERA = BLOCKS.register("engraved_wisestone_statera", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), STATERA_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_ECLIPSIS = BLOCKS.register("engraved_wisestone_eclipsis", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), ECLIPSIS_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_SICCITAS = BLOCKS.register("engraved_wisestone_siccitas", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), SICCITAS_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_SOLSTITIUM = BLOCKS.register("engraved_wisestone_solstitium", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), SOLSTITIUM_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_FAMES = BLOCKS.register("engraved_wisestone_fames", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), FAMES_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_RENAISSANCE = BLOCKS.register("engraved_wisestone_renaissance", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), RENAISSANCE_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_BELLUM = BLOCKS.register("engraved_wisestone_bellum", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), BELLUM_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_LUX = BLOCKS.register("engraved_wisestone_lux", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), LUX_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_KARA = BLOCKS.register("engraved_wisestone_kara", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), KARA_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_DEGRADATIO = BLOCKS.register("engraved_wisestone_degradatio", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), DEGRADATIO_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_PRAEDICTIONEM = BLOCKS.register("engraved_wisestone_praedictionem", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), PRAEDICTIONEM_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_EVOLUTIONIS = BLOCKS.register("engraved_wisestone_evolutionis", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), EVOLUTIONIS_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_TENEBRIS = BLOCKS.register("engraved_wisestone_tenebris", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), TENEBRIS_MONOGRAM));
    public static final RegistryObject<Block> ENGRAVED_WISESTONE_UNIVERSUM = BLOCKS.register("engraved_wisestone_universum", () -> new EngravedWisestoneBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()), UNIVERSUM_MONOGRAM));

    public static final RegistryObject<Block> INNOCENT_PEDESTAL = BLOCKS.register("innocent_pedestal", () -> new ArcanePedestalBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> INNOCENT_HOVERING_TOME_STAND = BLOCKS.register("innocent_hovering_tome_stand", () -> new HoveringTomeStandBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get())));

    public static final RegistryObject<Block> ARCANE_LEVER = BLOCKS.register("arcane_lever", () -> new WaterloggableLeverBlock(BlockBehaviour.Properties.copy(Blocks.LEVER).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> ARCANE_HOPPER = BLOCKS.register("arcane_hopper", () -> new ArcaneHopperBlock(BlockBehaviour.Properties.copy(Blocks.HOPPER).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> REDSTONE_SENSOR = BLOCKS.register("redstone_sensor", () -> new RedstoneSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> WISSEN_SENSOR = BLOCKS.register("wissen_sensor", () -> new WissenSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> COOLDOWN_SENSOR = BLOCKS.register("cooldown_sensor", () -> new CooldownSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> EXPERIENCE_SENSOR = BLOCKS.register("experience_sensor", () -> new ExperienceSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> LIGHT_SENSOR = BLOCKS.register("light_sensor", () -> new LightSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> HEAT_SENSOR = BLOCKS.register("heat_sensor", () -> new HeatSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> FLUID_SENSOR = BLOCKS.register("fluid_sensor", () -> new FluidSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> STEAM_SENSOR = BLOCKS.register("steam_sensor", () -> new SteamSensorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> WISSEN_ACTIVATOR = BLOCKS.register("wissen_activator", () -> new WissenActivatorBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> ITEM_SORTER = BLOCKS.register("item_sorter", () -> new ItemSorterBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));

    public static final RegistryObject<Block> ARCANE_WOOD_FRAME = BLOCKS.register("arcane_wood_frame", () -> new FrameBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_GLASS_FRAME = BLOCKS.register("arcane_wood_glass_frame", () -> new GlassFrameBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_CASING = BLOCKS.register("arcane_wood_casing", () -> new CasingBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_WISSEN_CASING = BLOCKS.register("arcane_wood_wissen_casing", () -> new WissenCasingBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_LIGHT_CASING = BLOCKS.register("arcane_wood_light_casing", () -> new LightCasingBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_FLUID_CASING = BLOCKS.register("arcane_wood_fluid_casing", () -> new FluidCasingBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> ARCANE_WOOD_STEAM_CASING = BLOCKS.register("arcane_wood_steam_casing", () -> new SteamCasingBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_FRAME = BLOCKS.register("innocent_wood_frame", () -> new FrameBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_GLASS_FRAME = BLOCKS.register("innocent_wood_glass_frame", () -> new GlassFrameBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_CASING = BLOCKS.register("innocent_wood_casing", () -> new CasingBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_WISSEN_CASING = BLOCKS.register("innocent_wood_wissen_casing", () -> new WissenCasingBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_LIGHT_CASING = BLOCKS.register("innocent_wood_light_casing", () -> new LightCasingBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_FLUID_CASING = BLOCKS.register("innocent_wood_fluid_casing", () -> new FluidCasingBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> INNOCENT_WOOD_STEAM_CASING = BLOCKS.register("innocent_wood_steam_casing", () -> new SteamCasingBlock(BlockBehaviour.Properties.copy(INNOCENT_WOOD_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_FRAME = BLOCKS.register("cork_bamboo_frame", () -> new FrameBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_GLASS_FRAME = BLOCKS.register("cork_bamboo_glass_frame", () -> new GlassFrameBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_CASING = BLOCKS.register("cork_bamboo_casing", () -> new CasingBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_WISSEN_CASING = BLOCKS.register("cork_bamboo_wissen_casing", () -> new WissenCasingBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_LIGHT_CASING = BLOCKS.register("cork_bamboo_light_casing", () -> new LightCasingBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_FLUID_CASING = BLOCKS.register("cork_bamboo_fluid_casing", () -> new FluidCasingBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> CORK_BAMBOO_STEAM_CASING = BLOCKS.register("cork_bamboo_steam_casing", () -> new SteamCasingBlock(BlockBehaviour.Properties.copy(CORK_BAMBOO_PLANKS.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_FRAME = BLOCKS.register("wisestone_frame", () -> new FrameBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_GLASS_FRAME = BLOCKS.register("wisestone_glass_frame", () -> new GlassFrameBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_CASING = BLOCKS.register("wisestone_casing", () -> new CasingBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_WISSEN_CASING = BLOCKS.register("wisestone_wissen_casing", () -> new WissenCasingBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_LIGHT_CASING = BLOCKS.register("wisestone_light_casing", () -> new LightCasingBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_FLUID_CASING = BLOCKS.register("wisestone_fluid_casing", () -> new FluidCasingBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));
    public static final RegistryObject<Block> WISESTONE_STEAM_CASING = BLOCKS.register("wisestone_steam_casing", () -> new SteamCasingBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get()).noOcclusion()));

    public static final RegistryObject<Block> CREATIVE_WISSEN_STORAGE = BLOCKS.register("creative_wissen_storage", () -> new CreativeWissenStorageBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> CREATIVE_LIGHT_STORAGE = BLOCKS.register("creative_light_storage", () -> new CreativeLightStorageBlock(BlockBehaviour.Properties.copy(ARCANE_WOOD_PLANKS.get())));
    public static final RegistryObject<Block> CREATIVE_FLUID_STORAGE = BLOCKS.register("creative_fluid_storage", () -> new CreativeFluidStorageBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));
    public static final RegistryObject<Block> CREATIVE_STEAM_STORAGE = BLOCKS.register("creative_steam_storage", () -> new CreativeSteamStorageBlock(BlockBehaviour.Properties.copy(POLISHED_WISESTONE.get())));

    public static final RegistryObject<Block> ARCANE_SALT_TORCH = BLOCKS.register("arcane_salt_torch", () -> new SaltTorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel((state) -> 15).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> ARCANE_SALT_WALL_TORCH = BLOCKS.register("arcane_salt_wall_torch", () -> new SaltWallTorchBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel((state) -> 15).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> INNOCENT_SALT_TORCH = BLOCKS.register("innocent_salt_torch", () -> new SaltTorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel((state) -> 15).mapColor(MapColor.TERRACOTTA_GRAY).sound(INNOCENT_WOOD_SOUNDS)));
    public static final RegistryObject<Block> INNOCENT_SALT_WALL_TORCH = BLOCKS.register("innocent_salt_wall_torch", () -> new SaltWallTorchBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel((state) -> 15).mapColor(MapColor.TERRACOTTA_GRAY).sound(INNOCENT_WOOD_SOUNDS)));
    public static final RegistryObject<Block> CORK_BAMBOO_SALT_TORCH = BLOCKS.register("cork_bamboo_salt_torch", () -> new SaltTorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel((state) -> 15).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> CORK_BAMBOO_SALT_WALL_TORCH = BLOCKS.register("cork_bamboo_salt_wall_torch", () -> new SaltWallTorchBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel((state) -> 15).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> WISESTONE_SALT_TORCH = BLOCKS.register("wisestone_salt_torch", () -> new SaltTorchBlock(BlockBehaviour.Properties.copy(Blocks.TORCH).lightLevel((state) -> 15).mapColor(MapColor.TERRACOTTA_BLACK).sound(POLISHED_WISESTONE_SOUNDS)));
    public static final RegistryObject<Block> WISESTONE_SALT_WALL_TORCH = BLOCKS.register("wisestone_salt_wall_torch", () -> new SaltWallTorchBlock(BlockBehaviour.Properties.copy(Blocks.WALL_TORCH).lightLevel((state) -> 15).mapColor(MapColor.TERRACOTTA_BLACK).sound(POLISHED_WISESTONE_SOUNDS)));
    public static final RegistryObject<Block> ARCANE_SALT_LANTERN = BLOCKS.register("arcane_salt_lantern", () -> new SaltLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel((state) -> 15).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> INNOCENT_SALT_LANTERN = BLOCKS.register("innocent_salt_lantern", () -> new SaltLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel((state) -> 15).mapColor(MapColor.TERRACOTTA_GRAY).sound(INNOCENT_WOOD_SOUNDS)));
    public static final RegistryObject<Block> CORK_BAMBOO_SALT_LANTERN = BLOCKS.register("cork_bamboo_salt_lantern", () -> new SaltLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel((state) -> 15).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> WISESTONE_SALT_LANTERN = BLOCKS.register("wisestone_salt_lantern", () -> new SaltLanternBlock(BlockBehaviour.Properties.copy(Blocks.LANTERN).lightLevel((state) -> 15).mapColor(MapColor.TERRACOTTA_BLACK).sound(POLISHED_WISESTONE_SOUNDS)));
    public static final RegistryObject<Block> ARCANE_SALT_CAMPFIRE = BLOCKS.register("arcane_salt_campfire", () -> new SaltCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE).lightLevel((state) -> 15).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> INNOCENT_SALT_CAMPFIRE = BLOCKS.register("innocent_salt_campfire", () -> new SaltCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE).lightLevel((state) -> 15).mapColor(MapColor.TERRACOTTA_GRAY).sound(INNOCENT_WOOD_SOUNDS)));
    public static final RegistryObject<Block> CORK_BAMBOO_SALT_CAMPFIRE = BLOCKS.register("cork_bamboo_salt_campfire", () -> new SaltCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE).lightLevel((state) -> 15).mapColor(MapColor.PODZOL).sound(ARCANE_WOOD_SOUNDS)));
    public static final RegistryObject<Block> WISESTONE_SALT_CAMPFIRE = BLOCKS.register("wisestone_salt_campfire", () -> new SaltCampfireBlock(BlockBehaviour.Properties.copy(Blocks.CAMPFIRE).lightLevel((state) -> 15).mapColor(MapColor.TERRACOTTA_BLACK).sound(POLISHED_WISESTONE_SOUNDS)));

    public static final RegistryObject<Block> ALCHEMY_GLASS = BLOCKS.register("alchemy_glass", () -> new TintedGlassBlock(BlockBehaviour.Properties.copy(Blocks.TINTED_GLASS).mapColor(MapColor.COLOR_LIGHT_GRAY).noOcclusion()));

    public static final RegistryObject<Block> WHITE_LUMINAL_GLASS = BLOCKS.register("white_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> LIGHT_GRAY_LUMINAL_GLASS = BLOCKS.register("light_gray_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GRAY_LUMINAL_GLASS = BLOCKS.register("gray_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_GRAY).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BLACK_LUMINAL_GLASS = BLOCKS.register("black_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BLACK).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BROWN_LUMINAL_GLASS = BLOCKS.register("brown_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BROWN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> RED_LUMINAL_GLASS = BLOCKS.register("red_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_RED).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> ORANGE_LUMINAL_GLASS = BLOCKS.register("orange_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_ORANGE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> YELLOW_LUMINAL_GLASS = BLOCKS.register("yellow_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_YELLOW).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> LIME_LUMINAL_GLASS = BLOCKS.register("lime_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GREEN_LUMINAL_GLASS = BLOCKS.register("green_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_GREEN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> CYAN_LUMINAL_GLASS = BLOCKS.register("cyan_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_CYAN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> LIGHT_BLUE_LUMINAL_GLASS = BLOCKS.register("light_blue_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BLUE_LUMINAL_GLASS = BLOCKS.register("blue_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BLUE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> PURPLE_LUMINAL_GLASS = BLOCKS.register("purple_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_PURPLE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> MAGENTA_LUMINAL_GLASS = BLOCKS.register("magenta_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_MAGENTA).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> PINK_LUMINAL_GLASS = BLOCKS.register("pink_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_PINK).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> RAINBOW_LUMINAL_GLASS = BLOCKS.register("rainbow_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_PINK).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> COSMIC_LUMINAL_GLASS = BLOCKS.register("cosmic_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_MAGENTA).noOcclusion().lightLevel((state) -> 8)));

    public static final RegistryObject<Block> WHITE_FRAMED_LUMINAL_GLASS = BLOCKS.register("white_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_WHITE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> LIGHT_GRAY_FRAMED_LUMINAL_GLASS = BLOCKS.register("light_gray_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GRAY_FRAMED_LUMINAL_GLASS = BLOCKS.register("gray_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_GRAY).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BLACK_FRAMED_LUMINAL_GLASS = BLOCKS.register("black_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BLACK).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BROWN_FRAMED_LUMINAL_GLASS = BLOCKS.register("brown_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BROWN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> RED_FRAMED_LUMINAL_GLASS = BLOCKS.register("red_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_RED).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> ORANGE_FRAMED_LUMINAL_GLASS = BLOCKS.register("orange_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_ORANGE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> YELLOW_FRAMED_LUMINAL_GLASS = BLOCKS.register("yellow_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_YELLOW).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> LIME_FRAMED_LUMINAL_GLASS = BLOCKS.register("lime_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> GREEN_FRAMED_LUMINAL_GLASS = BLOCKS.register("green_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_GREEN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> CYAN_FRAMED_LUMINAL_GLASS = BLOCKS.register("cyan_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_CYAN).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> LIGHT_BLUE_FRAMED_LUMINAL_GLASS = BLOCKS.register("light_blue_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> BLUE_FRAMED_LUMINAL_GLASS = BLOCKS.register("blue_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_BLUE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> PURPLE_FRAMED_LUMINAL_GLASS = BLOCKS.register("purple_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_PURPLE).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> MAGENTA_FRAMED_LUMINAL_GLASS = BLOCKS.register("magenta_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_MAGENTA).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> PINK_FRAMED_LUMINAL_GLASS = BLOCKS.register("pink_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_PINK).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> RAINBOW_FRAMED_LUMINAL_GLASS = BLOCKS.register("rainbow_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_PINK).noOcclusion().lightLevel((state) -> 8)));
    public static final RegistryObject<Block> COSMIC_FRAMED_LUMINAL_GLASS = BLOCKS.register("cosmic_framed_luminal_glass", () -> new LuminalGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).mapColor(MapColor.TERRACOTTA_MAGENTA).noOcclusion().lightLevel((state) -> 8)));

    public static final RegistryObject<Block> ALCHEMY_CALX_BLOCK = BLOCKS.register("alchemy_calx_block", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_CONCRETE_POWDER)));
    public static final RegistryObject<Block> NATURAL_CALX_BLOCK = BLOCKS.register("natural_calx_block", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.GREEN_CONCRETE_POWDER)));
    public static final RegistryObject<Block> SCORCHED_CALX_BLOCK = BLOCKS.register("scorched_calx_block", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.RED_CONCRETE_POWDER)));
    public static final RegistryObject<Block> DISTANT_CALX_BLOCK = BLOCKS.register("distant_calx_block", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.CYAN_CONCRETE_POWDER)));
    public static final RegistryObject<Block> ENCHANTED_CALX_BLOCK = BLOCKS.register("enchanted_calx_block", () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.LIME_CONCRETE_POWDER)));

    public static final RegistryObject<Block> ARCACITE_POLISHING_MIXTURE_BLOCK = BLOCKS.register("arcacite_polishing_mixture_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_GOLD_BLOCK).mapColor(MapColor.TERRACOTTA_RED).sound(MIXTURE_SOUNDS)));

    public static final RegistryObject<Block> SNIFFALO_EGG = BLOCKS.register("sniffalo_egg", () -> new SniffaloEggBlock(BlockBehaviour.Properties.copy(Blocks.SNIFFER_EGG).mapColor(MapColor.COLOR_BROWN)));

    //ITEMS
    public static final RegistryObject<Item> ARCANE_GOLD_INGOT = ITEMS.register("arcane_gold_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_NUGGET = ITEMS.register("arcane_gold_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_ARCANE_GOLD = ITEMS.register("raw_arcane_gold", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_BLOCK_ITEM = ITEMS.register("arcane_gold_block", () -> new BlockItem(ARCANE_GOLD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_ORE_ITEM = ITEMS.register("arcane_gold_ore", () -> new BlockItem(ARCANE_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DEEPSLATE_ARCANE_GOLD_ORE_ITEM = ITEMS.register("deepslate_arcane_gold_ore", () -> new BlockItem(DEEPSLATE_ARCANE_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> NETHER_ARCANE_GOLD_ORE_ITEM = ITEMS.register("nether_arcane_gold_ore", () -> new BlockItem(NETHER_ARCANE_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAW_ARCANE_GOLD_BLOCK_ITEM = ITEMS.register("raw_arcane_gold_block", () -> new BlockItem(RAW_ARCANE_GOLD_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_GOLD_SWORD = ITEMS.register("arcane_gold_sword", () -> new ArcaneSwordItem(CustomItemTier.ARCANE_GOLD, 3, -2.4f, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_COLD));
    public static final RegistryObject<Item> ARCANE_GOLD_PICKAXE = ITEMS.register("arcane_gold_pickaxe", () -> new ArcanePickaxeItem(CustomItemTier.ARCANE_GOLD, 1, -2.8f, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_COLD));
    public static final RegistryObject<Item> ARCANE_GOLD_AXE = ITEMS.register("arcane_gold_axe", () -> new ArcaneAxeItem(CustomItemTier.ARCANE_GOLD, 6, -3.1f, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_COLD));
    public static final RegistryObject<Item> ARCANE_GOLD_SHOVEL = ITEMS.register("arcane_gold_shovel", () -> new ArcaneShovelItem(CustomItemTier.ARCANE_GOLD, 1.5f, -3f, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_COLD));
    public static final RegistryObject<Item> ARCANE_GOLD_HOE = ITEMS.register("arcane_gold_hoe", () -> new ArcaneHoeItem(CustomItemTier.ARCANE_GOLD, -2, -1f, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_COLD));
    public static final RegistryObject<Item> ARCANE_GOLD_SCYTHE = ITEMS.register("arcane_gold_scythe", () -> new ArcaneScytheItem(CustomItemTier.ARCANE_GOLD, 4, -2.8f, new Item.Properties(), 1, 1).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_COLD));

    public static final RegistryObject<Item> ARCANE_GOLD_HELMET = ITEMS.register("arcane_gold_helmet", () -> new ArcaneArmorItem(CustomArmorMaterial.ARCANE_GOLD, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_CHESTPLATE = ITEMS.register("arcane_gold_chestplate", () -> new ArcaneArmorItem(CustomArmorMaterial.ARCANE_GOLD, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_LEGGINGS = ITEMS.register("arcane_gold_leggings", () -> new ArcaneArmorItem(CustomArmorMaterial.ARCANE_GOLD, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_BOOTS = ITEMS.register("arcane_gold_boots", () -> new ArcaneArmorItem(CustomArmorMaterial.ARCANE_GOLD, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> SARCON_INGOT = ITEMS.register("sarcon_ingot", () -> new SarconIngotItem(new Item.Properties()));
    public static final RegistryObject<Item> SARCON_NUGGET = ITEMS.register("sarcon_nugget", () -> new SarconIngotItem(new Item.Properties()));
    public static final RegistryObject<Item> SARCON_BLOCK_ITEM = ITEMS.register("sarcon_block", () -> new BlockItem(SARCON_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> VILENIUM_INGOT = ITEMS.register("vilenium_ingot", () -> new VileniumIngotItem(new Item.Properties()));
    public static final RegistryObject<Item> VILENIUM_NUGGET = ITEMS.register("vilenium_nugget", () -> new VileniumIngotItem(new Item.Properties()));
    public static final RegistryObject<Item> VILENIUM_BLOCK_ITEM = ITEMS.register("vilenium_block", () -> new BlockItem(VILENIUM_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANUM = ITEMS.register("arcanum", () -> new ArcanumItem(new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_DUST = ITEMS.register("arcanum_dust", () -> new ArcanumDustItem(new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_BLOCK_ITEM = ITEMS.register("arcanum_block", () -> new BlockItem(ARCANUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_ORE_ITEM = ITEMS.register("arcanum_ore", () -> new BlockItem(ARCANUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DEEPSLATE_ARCANUM_ORE_ITEM = ITEMS.register("deepslate_arcanum_ore", () -> new BlockItem(DEEPSLATE_ARCANUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_DUST_BLOCK_ITEM = ITEMS.register("arcanum_dust_block", () -> new BlockItem(ARCANUM_DUST_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCACITE = ITEMS.register("arcacite", () -> new ArcaciteItem(new Item.Properties()));
    public static final RegistryObject<Item> ARCACITE_BLOCK_ITEM = ITEMS.register("arcacite_block", () -> new BlockItem(ARCACITE_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> PRECISION_CRYSTAL = ITEMS.register("precision_crystal", () -> new PrecisionCrystalItem(new Item.Properties()));
    public static final RegistryObject<Item> PRECISION_CRYSTAL_BLOCK_ITEM = ITEMS.register("precision_crystal_block", () -> new BlockItem(PRECISION_CRYSTAL_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> NETHER_SALT = ITEMS.register("nether_salt", () -> new NetherSaltItem(new Item.Properties(), 3200));
    public static final RegistryObject<Item> NETHER_SALT_BLOCK_ITEM = ITEMS.register("nether_salt_block", () -> new FuelBlockItem(NETHER_SALT_BLOCK.get(), new Item.Properties(), 32000));
    public static final RegistryObject<Item> NETHER_SALT_ORE_ITEM = ITEMS.register("nether_salt_ore", () -> new BlockItem(NETHER_SALT_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_WOOD_LOG_ITEM = ITEMS.register("arcane_wood_log", () -> new BlockItem(ARCANE_WOOD_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_ITEM = ITEMS.register("arcane_wood", () -> new BlockItem(ARCANE_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_ARCANE_WOOD_LOG_ITEM = ITEMS.register("stripped_arcane_wood_log", () -> new BlockItem(STRIPPED_ARCANE_WOOD_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_ARCANE_WOOD_ITEM = ITEMS.register("stripped_arcane_wood", () -> new BlockItem(STRIPPED_ARCANE_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_ITEM = ITEMS.register("arcane_wood_planks", () -> new BlockItem(ARCANE_WOOD_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_STAIRS_ITEM = ITEMS.register("arcane_wood_stairs", () -> new BlockItem(ARCANE_WOOD_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_SLAB_ITEM = ITEMS.register("arcane_wood_slab", () -> new BlockItem(ARCANE_WOOD_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_BAULK_ITEM = ITEMS.register("arcane_wood_baulk", () -> new BlockItem(ARCANE_WOOD_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_CROSS_BAULK_ITEM = ITEMS.register("arcane_wood_cross_baulk", () -> new BlockItem(ARCANE_WOOD_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_ARCANE_WOOD_BAULK_ITEM = ITEMS.register("stripped_arcane_wood_baulk", () -> new BlockItem(STRIPPED_ARCANE_WOOD_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_ARCANE_WOOD_CROSS_BAULK_ITEM = ITEMS.register("stripped_arcane_wood_cross_baulk", () -> new BlockItem(STRIPPED_ARCANE_WOOD_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_BAULK_ITEM = ITEMS.register("arcane_wood_planks_baulk", () -> new BlockItem(ARCANE_WOOD_PLANKS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_CROSS_BAULK_ITEM = ITEMS.register("arcane_wood_planks_cross_baulk", () -> new BlockItem(ARCANE_WOOD_PLANKS_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_FENCE_ITEM = ITEMS.register("arcane_wood_fence", () -> new BlockItem(ARCANE_WOOD_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_FENCE_GATE_ITEM = ITEMS.register("arcane_wood_fence_gate", () -> new BlockItem(ARCANE_WOOD_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_DOOR_ITEM = ITEMS.register("arcane_wood_door", () -> new BlockItem(ARCANE_WOOD_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_TRAPDOOR_ITEM = ITEMS.register("arcane_wood_trapdoor", () -> new BlockItem(ARCANE_WOOD_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_PRESSURE_PLATE_ITEM = ITEMS.register("arcane_wood_pressure_plate", () -> new BlockItem(ARCANE_WOOD_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_BUTTON_ITEM = ITEMS.register("arcane_wood_button", () -> new BlockItem(ARCANE_WOOD_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_SIGN_ITEM = ITEMS.register("arcane_wood_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ARCANE_WOOD_SIGN.get(), ARCANE_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> ARCANE_WOOD_HANGING_SIGN_ITEM = ITEMS.register("arcane_wood_hanging_sign", () -> new HangingSignItem(ARCANE_WOOD_HANGING_SIGN.get(), ARCANE_WOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> ARCANE_WOOD_BOAT_ITEM = ITEMS.register("arcane_wood_boat", () -> new CustomBoatItem(false, CustomBoatEntity.Type.ARCANE_WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANE_WOOD_CHEST_BOAT_ITEM = ITEMS.register("arcane_wood_chest_boat", () -> new CustomBoatItem(true, CustomBoatEntity.Type.ARCANE_WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANE_WOOD_BRANCH = ITEMS.register("arcane_wood_branch", () -> new FuelItem(new Item.Properties(), 200));
    public static final RegistryObject<Item> ARCANE_WOOD_SWORD = ITEMS.register("arcane_wood_sword", () -> new ArcaneWoodSwordItem(CustomItemTier.ARCANE_WOOD, 3, -2.4f, new Item.Properties(), ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_WOOD));
    public static final RegistryObject<Item> ARCANE_WOOD_PICKAXE = ITEMS.register("arcane_wood_pickaxe", () -> new ArcaneWoodPickaxeItem(CustomItemTier.ARCANE_WOOD, 1, -2.8f, new Item.Properties(), ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_WOOD));
    public static final RegistryObject<Item> ARCANE_WOOD_AXE = ITEMS.register("arcane_wood_axe", () -> new ArcaneWoodAxeItem(CustomItemTier.ARCANE_WOOD, 6, -3.1f, new Item.Properties(), ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_WOOD));
    public static final RegistryObject<Item> ARCANE_WOOD_SHOVEL = ITEMS.register("arcane_wood_shovel", () -> new ArcaneWoodShovelItem(CustomItemTier.ARCANE_WOOD, 1.5f, -3f, new Item.Properties(), ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_WOOD));
    public static final RegistryObject<Item> ARCANE_WOOD_HOE = ITEMS.register("arcane_wood_hoe", () -> new ArcaneWoodHoeItem(CustomItemTier.ARCANE_WOOD, -2, -1f, new Item.Properties(), ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_WOOD));
    public static final RegistryObject<Item> ARCANE_WOOD_SCYTHE = ITEMS.register("arcane_wood_scythe", () -> new ArcaneWoodScytheItem(CustomItemTier.ARCANE_WOOD, 4, -2.8f, new Item.Properties(), 0.5f, 0, ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_WOOD));
    public static final RegistryObject<Item> ARCANE_WOOD_MORTAR = ITEMS.register("arcane_wood_mortar", () -> new MortarItem(new Item.Properties().stacksTo(1), 400));
    public static final RegistryObject<Item> ARCANE_WOOD_LEAVES_ITEM = ITEMS.register("arcane_wood_leaves", () -> new BlockItem(ARCANE_WOOD_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_SAPLING_ITEM = ITEMS.register("arcane_wood_sapling", () -> new BlockItem(ARCANE_WOOD_SAPLING.get(), new Item.Properties()));

    public static final RegistryObject<Item> INNOCENT_WOOD_LOG_ITEM = ITEMS.register("innocent_wood_log", () -> new BlockItem(INNOCENT_WOOD_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_ITEM = ITEMS.register("innocent_wood", () -> new BlockItem(INNOCENT_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_INNOCENT_WOOD_LOG_ITEM = ITEMS.register("stripped_innocent_wood_log", () -> new BlockItem(STRIPPED_INNOCENT_WOOD_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_INNOCENT_WOOD_ITEM = ITEMS.register("stripped_innocent_wood", () -> new BlockItem(STRIPPED_INNOCENT_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_PLANKS_ITEM = ITEMS.register("innocent_wood_planks", () -> new BlockItem(INNOCENT_WOOD_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_STAIRS_ITEM = ITEMS.register("innocent_wood_stairs", () -> new BlockItem(INNOCENT_WOOD_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_SLAB_ITEM = ITEMS.register("innocent_wood_slab", () -> new BlockItem(INNOCENT_WOOD_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_BAULK_ITEM = ITEMS.register("innocent_wood_baulk", () -> new BlockItem(INNOCENT_WOOD_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_CROSS_BAULK_ITEM = ITEMS.register("innocent_wood_cross_baulk", () -> new BlockItem(INNOCENT_WOOD_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_INNOCENT_WOOD_BAULK_ITEM = ITEMS.register("stripped_innocent_wood_baulk", () -> new BlockItem(STRIPPED_INNOCENT_WOOD_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_INNOCENT_WOOD_CROSS_BAULK_ITEM = ITEMS.register("stripped_innocent_wood_cross_baulk", () -> new BlockItem(STRIPPED_INNOCENT_WOOD_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_PLANKS_BAULK_ITEM = ITEMS.register("innocent_wood_planks_baulk", () -> new BlockItem(INNOCENT_WOOD_PLANKS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_PLANKS_CROSS_BAULK_ITEM = ITEMS.register("innocent_wood_planks_cross_baulk", () -> new BlockItem(INNOCENT_WOOD_PLANKS_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_FENCE_ITEM = ITEMS.register("innocent_wood_fence", () -> new BlockItem(INNOCENT_WOOD_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_FENCE_GATE_ITEM = ITEMS.register("innocent_wood_fence_gate", () -> new BlockItem(INNOCENT_WOOD_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_DOOR_ITEM = ITEMS.register("innocent_wood_door", () -> new BlockItem(INNOCENT_WOOD_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_TRAPDOOR_ITEM = ITEMS.register("innocent_wood_trapdoor", () -> new BlockItem(INNOCENT_WOOD_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_PRESSURE_PLATE_ITEM = ITEMS.register("innocent_wood_pressure_plate", () -> new BlockItem(INNOCENT_WOOD_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_BUTTON_ITEM = ITEMS.register("innocent_wood_button", () -> new BlockItem(INNOCENT_WOOD_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_SIGN_ITEM = ITEMS.register("innocent_wood_sign", () -> new SignItem(new Item.Properties().stacksTo(16), INNOCENT_WOOD_SIGN.get(), INNOCENT_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> INNOCENT_WOOD_HANGING_SIGN_ITEM = ITEMS.register("innocent_wood_hanging_sign", () -> new HangingSignItem(INNOCENT_WOOD_HANGING_SIGN.get(), INNOCENT_WOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> INNOCENT_WOOD_BOAT_ITEM = ITEMS.register("innocent_wood_boat", () -> new CustomBoatItem(false, CustomBoatEntity.Type.INNOCENT_WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> INNOCENT_WOOD_CHEST_BOAT_ITEM = ITEMS.register("innocent_wood_chest_boat", () -> new CustomBoatItem(true, CustomBoatEntity.Type.INNOCENT_WOOD, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> INNOCENT_WOOD_BRANCH = ITEMS.register("innocent_wood_branch", () -> new FuelItem(new Item.Properties(), 200));
    public static final RegistryObject<Item> INNOCENT_WOOD_SWORD = ITEMS.register("innocent_wood_sword", () -> new InnocentWoodSwordItem(CustomItemTier.INNOCENT_WOOD, 3, -2.4f, new Item.Properties(), INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.INNOCENT_WOOD));
    public static final RegistryObject<Item> INNOCENT_WOOD_PICKAXE = ITEMS.register("innocent_wood_pickaxe", () -> new InnocentWoodPickaxeItem(CustomItemTier.INNOCENT_WOOD, 1, -2.8f, new Item.Properties(), INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.INNOCENT_WOOD));
    public static final RegistryObject<Item> INNOCENT_WOOD_AXE = ITEMS.register("innocent_wood_axe", () -> new InnocentWoodAxeItem(CustomItemTier.INNOCENT_WOOD, 6, -3.1f, new Item.Properties(), INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.INNOCENT_WOOD));
    public static final RegistryObject<Item> INNOCENT_WOOD_SHOVEL = ITEMS.register("innocent_wood_shovel", () -> new InnocentWoodShovelItem(CustomItemTier.INNOCENT_WOOD, 1.5f, -3f, new Item.Properties(), INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.INNOCENT_WOOD));
    public static final RegistryObject<Item> INNOCENT_WOOD_HOE = ITEMS.register("innocent_wood_hoe", () -> new InnocentWoodHoeItem(CustomItemTier.INNOCENT_WOOD, -2, -1f, new Item.Properties(), INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.INNOCENT_WOOD));
    public static final RegistryObject<Item> INNOCENT_WOOD_SCYTHE = ITEMS.register("innocent_wood_scythe", () -> new InnocentWoodScytheItem(CustomItemTier.INNOCENT_WOOD, 4, -2.8f, new Item.Properties(), 0.5f, 0, INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentType.INNOCENT_WOOD));
    public static final RegistryObject<Item> INNOCENT_WOOD_MORTAR = ITEMS.register("innocent_wood_mortar", () -> new MortarItem(new Item.Properties().stacksTo(1), 400));
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

    public static final RegistryObject<Item> ARCANE_LINEN_SEEDS = ITEMS.register("arcane_linen_seeds", () -> new ItemNameBlockItem(ARCANE_LINEN.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_LINEN_ITEM = ITEMS.register("arcane_linen", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_LINEN_HAY_ITEM = ITEMS.register("arcane_linen_hay", () -> new BlockItem(ARCANE_LINEN_HAY.get(), new Item.Properties()));

    public static final RegistryObject<Item> MOR_ITEM = ITEMS.register("mor", () -> new MorItem(MOR.get(), new Item.Properties().food(MOR_FOOD), 1500, 1800));
    public static final RegistryObject<Item> MOR_BLOCK_ITEM = ITEMS.register("mor_block", () -> new BlockItem(MOR_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ELDER_MOR_ITEM = ITEMS.register("elder_mor", () -> new MorItem(ELDER_MOR.get(), new Item.Properties().food(MOR_FOOD), 1700, 2100));
    public static final RegistryObject<Item> ELDER_MOR_BLOCK_ITEM = ITEMS.register("elder_mor_block", () -> new BlockItem(ELDER_MOR_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> PITCHER_TURNIP_ITEM = ITEMS.register("pitcher_turnip", () -> new BlockItem(PITCHER_TURNIP.get(), new Item.Properties().food(PITCHER_TURNIP_FOOD)));
    public static final RegistryObject<Item> PITCHER_TURNIP_BLOCK_ITEM = ITEMS.register("pitcher_turnip_block", () -> new BlockItem(PITCHER_TURNIP_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHINY_CLOVER_SEED = ITEMS.register("shiny_clover_seed", () -> new ItemNameBlockItem(SHINY_CLOVER_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHINY_CLOVER_ITEM = ITEMS.register("shiny_clover", () -> new BlockItem(SHINY_CLOVER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UNDERGROUND_GRAPE_VINE = ITEMS.register("underground_grape_vine", () -> new ItemNameBlockItem(UNDERGROUND_GRAPE_VINES.get(), new Item.Properties()));
    public static final RegistryObject<Item> UNDERGROUND_GRAPE = ITEMS.register("underground_grape", () -> new Item(new Item.Properties().food(UNDERGROUND_GRAPE_FOOD)));

    public static final RegistryObject<Item> CORK_BAMBOO_SEED = ITEMS.register("cork_bamboo_seed", () -> new ItemNameBlockItem(CORK_BAMBOO_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_ITEM = ITEMS.register("cork_bamboo", () -> new BlockItem(CORK_BAMBOO.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_BLOCK_ITEM = ITEMS.register("cork_bamboo_block", () -> new BlockItem(CORK_BAMBOO_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_PLANKS_ITEM = ITEMS.register("cork_bamboo_planks", () -> new BlockItem(CORK_BAMBOO_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CHISELED_PLANKS_ITEM = ITEMS.register("cork_bamboo_chiseled_planks", () -> new BlockItem(CORK_BAMBOO_CHISELED_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_STAIRS_ITEM = ITEMS.register("cork_bamboo_stairs", () -> new BlockItem(CORK_BAMBOO_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CHISELED_STAIRS_ITEM = ITEMS.register("cork_bamboo_chiseled_stairs", () -> new BlockItem(CORK_BAMBOO_CHISELED_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_SLAB_ITEM = ITEMS.register("cork_bamboo_slab", () -> new BlockItem(CORK_BAMBOO_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CHISELED_SLAB_ITEM = ITEMS.register("cork_bamboo_chiseled_slab", () -> new BlockItem(CORK_BAMBOO_CHISELED_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_BAULK_ITEM = ITEMS.register("cork_bamboo_baulk", () -> new BlockItem(CORK_BAMBOO_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CROSS_BAULK_ITEM = ITEMS.register("cork_bamboo_cross_baulk", () -> new BlockItem(CORK_BAMBOO_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_PLANKS_BAULK_ITEM = ITEMS.register("cork_bamboo_planks_baulk", () -> new BlockItem(CORK_BAMBOO_PLANKS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_PLANKS_CROSS_BAULK_ITEM = ITEMS.register("cork_bamboo_planks_cross_baulk", () -> new BlockItem(CORK_BAMBOO_PLANKS_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CHISELED_PLANKS_BAULK_ITEM = ITEMS.register("cork_bamboo_chiseled_planks_baulk", () -> new BlockItem(CORK_BAMBOO_CHISELED_PLANKS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK_ITEM = ITEMS.register("cork_bamboo_chiseled_planks_cross_baulk", () -> new BlockItem(CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_FENCE_ITEM = ITEMS.register("cork_bamboo_fence", () -> new BlockItem(CORK_BAMBOO_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_FENCE_GATE_ITEM = ITEMS.register("cork_bamboo_fence_gate", () -> new BlockItem(CORK_BAMBOO_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_DOOR_ITEM = ITEMS.register("cork_bamboo_door", () -> new BlockItem(CORK_BAMBOO_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_TRAPDOOR_ITEM = ITEMS.register("cork_bamboo_trapdoor", () -> new BlockItem(CORK_BAMBOO_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_PRESSURE_PLATE_ITEM = ITEMS.register("cork_bamboo_pressure_plate", () -> new BlockItem(CORK_BAMBOO_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_BUTTON_ITEM = ITEMS.register("cork_bamboo_button", () -> new BlockItem(CORK_BAMBOO_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_SIGN_ITEM = ITEMS.register("cork_bamboo_sign", () -> new SignItem(new Item.Properties().stacksTo(16), CORK_BAMBOO_SIGN.get(), CORK_BAMBOO_WALL_SIGN.get()));
    public static final RegistryObject<Item> CORK_BAMBOO_HANGING_SIGN_ITEM = ITEMS.register("cork_bamboo_hanging_sign", () -> new HangingSignItem(CORK_BAMBOO_HANGING_SIGN.get(), CORK_BAMBOO_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> CORK_BAMBOO_RAFT_ITEM = ITEMS.register("cork_bamboo_raft", () -> new CustomBoatItem(false, CustomBoatEntity.Type.CORK_BAMBOO, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CORK_BAMBOO_CHEST_RAFT_ITEM = ITEMS.register("cork_bamboo_chest_raft", () -> new CustomBoatItem(true, CustomBoatEntity.Type.CORK_BAMBOO, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PETALS = ITEMS.register("petals", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLOWER_FERTILIZER = ITEMS.register("flower_fertilizer", () -> new FlowerFertilizerItem(new Item.Properties()));
    public static final RegistryObject<Item> BUNCH_OF_THINGS = ITEMS.register("bunch_of_things", () -> new BunchOfThingsItem(new Item.Properties()));
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

    public static final RegistryObject<Item> WHITE_ARCANE_LUMOS_ITEM = ITEMS.register("white_arcane_lumos", () -> new ArcaneLumosItem(WHITE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_GRAY_ARCANE_LUMOS_ITEM = ITEMS.register("light_gray_arcane_lumos", () -> new ArcaneLumosItem(LIGHT_GRAY_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_ARCANE_LUMOS_ITEM = ITEMS.register("gray_arcane_lumos", () -> new ArcaneLumosItem(GRAY_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_ARCANE_LUMOS_ITEM = ITEMS.register("black_arcane_lumos", () -> new ArcaneLumosItem(BLACK_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BROWN_ARCANE_LUMOS_ITEM = ITEMS.register("brown_arcane_lumos", () -> new ArcaneLumosItem(BROWN_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_ARCANE_LUMOS_ITEM = ITEMS.register("red_arcane_lumos", () -> new ArcaneLumosItem(RED_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_ARCANE_LUMOS_ITEM = ITEMS.register("orange_arcane_lumos", () -> new ArcaneLumosItem(ORANGE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_ARCANE_LUMOS_ITEM = ITEMS.register("yellow_arcane_lumos", () -> new ArcaneLumosItem(YELLOW_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIME_ARCANE_LUMOS_ITEM = ITEMS.register("lime_arcane_lumos", () -> new ArcaneLumosItem(LIME_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_ARCANE_LUMOS_ITEM = ITEMS.register("green_arcane_lumos", () -> new ArcaneLumosItem(GREEN_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CYAN_ARCANE_LUMOS_ITEM = ITEMS.register("cyan_arcane_lumos", () -> new ArcaneLumosItem(CYAN_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BLUE_ARCANE_LUMOS_ITEM = ITEMS.register("light_blue_arcane_lumos", () -> new ArcaneLumosItem(LIGHT_BLUE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_ARCANE_LUMOS_ITEM = ITEMS.register("blue_arcane_lumos", () -> new ArcaneLumosItem(BLUE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_ARCANE_LUMOS_ITEM = ITEMS.register("purple_arcane_lumos", () -> new ArcaneLumosItem(PURPLE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAGENTA_ARCANE_LUMOS_ITEM = ITEMS.register("magenta_arcane_lumos", () -> new ArcaneLumosItem(MAGENTA_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_ARCANE_LUMOS_ITEM = ITEMS.register("pink_arcane_lumos", () -> new ArcaneLumosItem(PINK_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAINBOW_ARCANE_LUMOS_ITEM = ITEMS.register("rainbow_arcane_lumos", () -> new ArcaneLumosItem(RAINBOW_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_ARCANE_LUMOS_ITEM = ITEMS.register("cosmic_arcane_lumos", () -> new ArcaneLumosItem(COSMIC_ARCANE_LUMOS.get(), new Item.Properties()));

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

    public static final RegistryObject<Item> CORK_BAMBOO_PEDESTAL_ITEM = ITEMS.register("cork_bamboo_pedestal", () -> new BlockItem(CORK_BAMBOO_PEDESTAL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_HOVERING_TOME_STAND_ITEM = ITEMS.register("cork_bamboo_hovering_tome_stand", () -> new BlockItem(CORK_BAMBOO_HOVERING_TOME_STAND.get(), new Item.Properties()));

    public static final RegistryObject<Item> LIGHT_EMITTER_ITEM = ITEMS.register("light_emitter", () -> new BlockItem(LIGHT_EMITTER.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_TRANSFER_LENS_ITEM = ITEMS.register("light_transfer_lens", () -> new BlockItem(LIGHT_TRANSFER_LENS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RUNIC_PEDESTAL_ITEM = ITEMS.register("runic_pedestal", () -> new BlockItem(RUNIC_PEDESTAL.get(), new Item.Properties()));

    public static final RegistryObject<Item> ENGRAVED_WISESTONE_ITEM = ITEMS.register("engraved_wisestone", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_LUNAM_ITEM = ITEMS.register("engraved_wisestone_lunam", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_LUNAM.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_VITA_ITEM = ITEMS.register("engraved_wisestone_vita", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_VITA.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_SOLEM_ITEM = ITEMS.register("engraved_wisestone_solem", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_SOLEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_MORS_ITEM = ITEMS.register("engraved_wisestone_mors", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_MORS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_MIRACULUM_ITEM = ITEMS.register("engraved_wisestone_miraculum", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_MIRACULUM.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_TEMPUS_ITEM = ITEMS.register("engraved_wisestone_tempus", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_TEMPUS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_STATERA_ITEM = ITEMS.register("engraved_wisestone_statera", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_STATERA.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_ECLIPSIS_ITEM = ITEMS.register("engraved_wisestone_eclipsis", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_ECLIPSIS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_SICCITAS_ITEM = ITEMS.register("engraved_wisestone_siccitas", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_SICCITAS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_SOLSTITIUM_ITEM = ITEMS.register("engraved_wisestone_solstitium", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_SOLSTITIUM.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_FAMES_ITEM = ITEMS.register("engraved_wisestone_fames", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_FAMES.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_RENAISSANCE_ITEM = ITEMS.register("engraved_wisestone_renaissance", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_RENAISSANCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_BELLUM_ITEM = ITEMS.register("engraved_wisestone_bellum", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_BELLUM.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_LUX_ITEM = ITEMS.register("engraved_wisestone_lux", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_LUX.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_KARA_ITEM = ITEMS.register("engraved_wisestone_kara", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_KARA.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_DEGRADATIO_ITEM = ITEMS.register("engraved_wisestone_degradatio", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_DEGRADATIO.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_PRAEDICTIONEM_ITEM = ITEMS.register("engraved_wisestone_praedictionem", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_PRAEDICTIONEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_EVOLUTIONIS_ITEM = ITEMS.register("engraved_wisestone_evolutionis", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_EVOLUTIONIS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_TENEBRIS_ITEM = ITEMS.register("engraved_wisestone_tenebris", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_TENEBRIS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_UNIVERSUM_ITEM = ITEMS.register("engraved_wisestone_universum", () -> new EngravedWisestoneItem(ENGRAVED_WISESTONE_UNIVERSUM.get(), new Item.Properties()));

    public static final RegistryObject<Item> INNOCENT_PEDESTAL_ITEM = ITEMS.register("innocent_pedestal", () -> new BlockItem(INNOCENT_PEDESTAL.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_HOVERING_TOME_STAND_ITEM = ITEMS.register("innocent_hovering_tome_stand", () -> new BlockItem(INNOCENT_HOVERING_TOME_STAND.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_LEVER_ITEM = ITEMS.register("arcane_lever", () -> new BlockItem(ARCANE_LEVER.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_HOPPER_ITEM = ITEMS.register("arcane_hopper", () -> new BlockItem(ARCANE_HOPPER.get(), new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_SENSOR_ITEM = ITEMS.register("redstone_sensor", () -> new BlockItem(REDSTONE_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_SENSOR_ITEM = ITEMS.register("wissen_sensor", () -> new BlockItem(WISSEN_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> COOLDOWN_SENSOR_ITEM = ITEMS.register("cooldown_sensor", () -> new BlockItem(COOLDOWN_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> EXPERIENCE_SENSOR_ITEM = ITEMS.register("experience_sensor", () -> new BlockItem(EXPERIENCE_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_SENSOR_ITEM = ITEMS.register("light_sensor", () -> new BlockItem(LIGHT_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> HEAT_SENSOR_ITEM = ITEMS.register("heat_sensor", () -> new BlockItem(HEAT_SENSOR.get(), new Item.Properties()));
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
    public static final RegistryObject<Item> CORK_BAMBOO_FRAME_ITEM = ITEMS.register("cork_bamboo_frame", () -> new BlockItem(CORK_BAMBOO_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_GLASS_FRAME_ITEM = ITEMS.register("cork_bamboo_glass_frame", () -> new BlockItem(CORK_BAMBOO_GLASS_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CASING_ITEM = ITEMS.register("cork_bamboo_casing", () -> new BlockItem(CORK_BAMBOO_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_WISSEN_CASING_ITEM = ITEMS.register("cork_bamboo_wissen_casing", () -> new BlockItem(CORK_BAMBOO_WISSEN_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_LIGHT_CASING_ITEM = ITEMS.register("cork_bamboo_light_casing", () -> new BlockItem(CORK_BAMBOO_LIGHT_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_FLUID_CASING_ITEM = ITEMS.register("cork_bamboo_fluid_casing", () -> new BlockItem(CORK_BAMBOO_FLUID_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_STEAM_CASING_ITEM = ITEMS.register("cork_bamboo_steam_casing", () -> new BlockItem(CORK_BAMBOO_STEAM_CASING.get(), new Item.Properties()));
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

    public static final RegistryObject<Item> ARCANE_SALT_TORCH_ITEM = ITEMS.register("arcane_salt_torch", () -> new SaltTorchItem(ARCANE_SALT_TORCH.get(), ARCANE_SALT_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> INNOCENT_SALT_TORCH_ITEM = ITEMS.register("innocent_salt_torch", () -> new SaltTorchItem(INNOCENT_SALT_TORCH.get(), INNOCENT_SALT_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> CORK_BAMBOO_SALT_TORCH_ITEM = ITEMS.register("cork_bamboo_salt_torch", () -> new SaltTorchItem(CORK_BAMBOO_SALT_TORCH.get(), CORK_BAMBOO_SALT_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> WISESTONE_SALT_TORCH_ITEM = ITEMS.register("wisestone_salt_torch", () -> new SaltTorchItem(WISESTONE_SALT_TORCH.get(), WISESTONE_SALT_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> ARCANE_SALT_LANTERN_ITEM = ITEMS.register("arcane_salt_lantern", () -> new SaltLanternItem(ARCANE_SALT_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_SALT_LANTERN_ITEM = ITEMS.register("innocent_salt_lantern", () -> new SaltLanternItem(INNOCENT_SALT_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_SALT_LANTERN_ITEM = ITEMS.register("cork_bamboo_salt_lantern", () -> new SaltLanternItem(CORK_BAMBOO_SALT_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_SALT_LANTERN_ITEM = ITEMS.register("wisestone_salt_lantern", () -> new SaltLanternItem(WISESTONE_SALT_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_SALT_CAMPFIRE_ITEM = ITEMS.register("arcane_salt_campfire", () -> new SaltCampfireItem(ARCANE_SALT_CAMPFIRE.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_SALT_CAMPFIRE_ITEM = ITEMS.register("innocent_salt_campfire", () -> new SaltCampfireItem(INNOCENT_SALT_CAMPFIRE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_SALT_CAMPFIRE_ITEM = ITEMS.register("cork_bamboo_salt_campfire", () -> new SaltCampfireItem(CORK_BAMBOO_SALT_CAMPFIRE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_SALT_CAMPFIRE_ITEM = ITEMS.register("wisestone_salt_campfire", () -> new SaltCampfireItem(WISESTONE_SALT_CAMPFIRE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ALCHEMY_GLASS_ITEM = ITEMS.register("alchemy_glass", () -> new BlockItem(ALCHEMY_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_VIAL = ITEMS.register("alchemy_vial", () -> new VialItem(new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_FLASK = ITEMS.register("alchemy_flask", () -> new FlaskItem(new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_BOTTLE = ITEMS.register("alchemy_bottle", () -> new AlchemyDrinkBottleItem(new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_VIAL_POTION = ITEMS.register("alchemy_vial_potion", () -> new VialPotionItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ALCHEMY_FLASK_POTION = ITEMS.register("alchemy_flask_potion", () -> new FlaskPotionItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> WHITE_LUMINAL_GLASS_ITEM = ITEMS.register("white_luminal_glass", () -> new BlockItem(WHITE_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_GRAY_LUMINAL_GLASS_ITEM = ITEMS.register("light_gray_luminal_glass", () -> new BlockItem(LIGHT_GRAY_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_LUMINAL_GLASS_ITEM = ITEMS.register("gray_luminal_glass", () -> new BlockItem(GRAY_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_LUMINAL_GLASS_ITEM = ITEMS.register("black_luminal_glass", () -> new BlockItem(BLACK_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BROWN_LUMINAL_GLASS_ITEM = ITEMS.register("brown_luminal_glass", () -> new BlockItem(BROWN_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_LUMINAL_GLASS_ITEM = ITEMS.register("red_luminal_glass", () -> new BlockItem(RED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_LUMINAL_GLASS_ITEM = ITEMS.register("orange_luminal_glass", () -> new BlockItem(ORANGE_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_LUMINAL_GLASS_ITEM = ITEMS.register("yellow_luminal_glass", () -> new BlockItem(YELLOW_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIME_LUMINAL_GLASS_ITEM = ITEMS.register("lime_luminal_glass", () -> new BlockItem(LIME_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_LUMINAL_GLASS_ITEM = ITEMS.register("green_luminal_glass", () -> new BlockItem(GREEN_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CYAN_LUMINAL_GLASS_ITEM = ITEMS.register("cyan_luminal_glass", () -> new BlockItem(CYAN_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BLUE_LUMINAL_GLASS_ITEM = ITEMS.register("light_blue_luminal_glass", () -> new BlockItem(LIGHT_BLUE_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_LUMINAL_GLASS_ITEM = ITEMS.register("blue_luminal_glass", () -> new BlockItem(BLUE_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_LUMINAL_GLASS_ITEM = ITEMS.register("purple_luminal_glass", () -> new BlockItem(PURPLE_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAGENTA_LUMINAL_GLASS_ITEM = ITEMS.register("magenta_luminal_glass", () -> new BlockItem(MAGENTA_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_LUMINAL_GLASS_ITEM = ITEMS.register("pink_luminal_glass", () -> new BlockItem(PINK_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAINBOW_LUMINAL_GLASS_ITEM = ITEMS.register("rainbow_luminal_glass", () -> new BlockItem(RAINBOW_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_LUMINAL_GLASS_ITEM = ITEMS.register("cosmic_luminal_glass", () -> new BlockItem(COSMIC_LUMINAL_GLASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> WHITE_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("white_framed_luminal_glass", () -> new BlockItem(WHITE_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_GRAY_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("light_gray_framed_luminal_glass", () -> new BlockItem(LIGHT_GRAY_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("gray_framed_luminal_glass", () -> new BlockItem(GRAY_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("black_framed_luminal_glass", () -> new BlockItem(BLACK_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BROWN_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("brown_framed_luminal_glass", () -> new BlockItem(BROWN_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("red_framed_luminal_glass", () -> new BlockItem(RED_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("orange_framed_luminal_glass", () -> new BlockItem(ORANGE_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("yellow_framed_luminal_glass", () -> new BlockItem(YELLOW_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIME_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("lime_framed_luminal_glass", () -> new BlockItem(LIME_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("green_framed_luminal_glass", () -> new BlockItem(GREEN_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CYAN_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("cyan_framed_luminal_glass", () -> new BlockItem(CYAN_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BLUE_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("light_blue_framed_luminal_glass", () -> new BlockItem(LIGHT_BLUE_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("blue_framed_luminal_glass", () -> new BlockItem(BLUE_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("purple_framed_luminal_glass", () -> new BlockItem(PURPLE_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAGENTA_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("magenta_framed_luminal_glass", () -> new BlockItem(MAGENTA_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("pink_framed_luminal_glass", () -> new BlockItem(PINK_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAINBOW_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("rainbow_framed_luminal_glass", () -> new BlockItem(RAINBOW_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_FRAMED_LUMINAL_GLASS_ITEM = ITEMS.register("cosmic_framed_luminal_glass", () -> new BlockItem(COSMIC_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> ALCHEMY_CALX = ITEMS.register("alchemy_calx", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NATURAL_CALX = ITEMS.register("natural_calx", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SCORCHED_CALX = ITEMS.register("scorched_calx", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DISTANT_CALX = ITEMS.register("distant_calx", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENCHANTED_CALX = ITEMS.register("enchanted_calx", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ALCHEMY_CALX_BLOCK_ITEM = ITEMS.register("alchemy_calx_block", () -> new BlockItem(ALCHEMY_CALX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> NATURAL_CALX_BLOCK_ITEM = ITEMS.register("natural_calx_block", () -> new BlockItem(NATURAL_CALX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SCORCHED_CALX_BLOCK_ITEM = ITEMS.register("scorched_calx_block", () -> new BlockItem(SCORCHED_CALX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DISTANT_CALX_BLOCK_ITEM = ITEMS.register("distant_calx_block", () -> new BlockItem(DISTANT_CALX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENCHANTED_CALX_BLOCK_ITEM = ITEMS.register("enchanted_calx_block", () -> new BlockItem(ENCHANTED_CALX_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCACITE_POLISHING_MIXTURE = ITEMS.register("arcacite_polishing_mixture", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCACITE_POLISHING_MIXTURE_BLOCK_ITEM = ITEMS.register("arcacite_polishing_mixture_block", () -> new BlockItem(ARCACITE_POLISHING_MIXTURE_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> SNIFFALO_EGG_ITEM = ITEMS.register("sniffalo_egg", () -> new BlockItem(SNIFFALO_EGG.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANUM_LENS = ITEMS.register("arcanum_lens", () -> new ArcanumLensItem(new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_PLATE = ITEMS.register("wisestone_plate", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RUNIC_WISESTONE_PLATE = ITEMS.register("runic_wisestone_plate", () -> new RunicWisestonePlateItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARCANUM_AMULET = ITEMS.register("arcanum_amulet", () -> new ArcanumAmuletItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANUM_RING = ITEMS.register("arcanum_ring", () -> new ArcanumRingItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCACITE_AMULET = ITEMS.register("arcacite_amulet", () -> new ArcaciteAmuletItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCACITE_RING = ITEMS.register("arcacite_ring", () -> new ArcaciteRingItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WISSEN_KEYCHAIN = ITEMS.register("wissen_keychain", () -> new WissenKeychainItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WISSEN_RING = ITEMS.register("wissen_ring", () -> new WissenRingItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CREATIVE_WISSEN_KEYCHAIN = ITEMS.register("creative_wissen_keychain", () -> new CreativeWissenKeychainItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> LEATHER_BELT = ITEMS.register("leather_belt", () -> new LeatherBeltItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANE_FORTRESS_BELT = ITEMS.register("arcane_fortress_belt", () -> new ArcaneFortressBeltItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> INVENTOR_WIZARD_BELT = ITEMS.register("inventor_wizard_belt", () -> new InventorWizardBeltItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CRYSTAL_BAG = ITEMS.register("crystal_bag", () -> new CrystalBagItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ALCHEMY_BAG = ITEMS.register("alchemy_bag", () -> new AlchemyBagItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> LEATHER_COLLAR = ITEMS.register("leather_collar", () -> new LeatherCollarItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BROWN_MUSHROOM_CAP = ITEMS.register("brown_mushroom_cap", () -> new MushroomCapItem(new Item.Properties().stacksTo(1), "brown_mushroom_cap"));
    public static final RegistryObject<Item> RED_MUSHROOM_CAP = ITEMS.register("red_mushroom_cap", () -> new MushroomCapItem(new Item.Properties().stacksTo(1), "red_mushroom_cap"));
    public static final RegistryObject<Item> CRIMSON_FUNGUS_CAP = ITEMS.register("crimson_fungus_cap", () -> new MushroomCapItem(new Item.Properties().stacksTo(1), "crimson_fungus_cap"));
    public static final RegistryObject<Item> WARPED_FUNGUS_CAP = ITEMS.register("warped_fungus_cap", () -> new MushroomCapItem(new Item.Properties().stacksTo(1), "warped_fungus_cap"));
    public static final RegistryObject<Item> MOR_CAP = ITEMS.register("mor_cap", () -> new MushroomCapItem(new Item.Properties().stacksTo(1), "mor_cap"));
    public static final RegistryObject<Item> ELDER_MOR_CAP = ITEMS.register("elder_mor_cap", () -> new MushroomCapItem(new Item.Properties().stacksTo(1), "elder_mor_cap"));

    public static final RegistryObject<Item> ARCANE_FORTRESS_HELMET = ITEMS.register("arcane_fortress_helmet", () -> new ArcaneFortressArmorItem(CustomArmorMaterial.ARCANE_FORTRESS, ArmorItem.Type.HELMET, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_FORTRESS_ARMOR));
    public static final RegistryObject<Item> ARCANE_FORTRESS_CHESTPLATE = ITEMS.register("arcane_fortress_chestplate", () -> new ArcaneFortressArmorItem(CustomArmorMaterial.ARCANE_FORTRESS, ArmorItem.Type.CHESTPLATE, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_FORTRESS_ARMOR));
    public static final RegistryObject<Item> ARCANE_FORTRESS_LEGGINGS = ITEMS.register("arcane_fortress_leggings", () -> new ArcaneFortressArmorItem(CustomArmorMaterial.ARCANE_FORTRESS, ArmorItem.Type.LEGGINGS, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_FORTRESS_ARMOR));
    public static final RegistryObject<Item> ARCANE_FORTRESS_BOOTS = ITEMS.register("arcane_fortress_boots", () -> new ArcaneFortressArmorItem(CustomArmorMaterial.ARCANE_FORTRESS, ArmorItem.Type.BOOTS, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.ARCANE_FORTRESS_ARMOR));

    public static final RegistryObject<Item> INVENTOR_WIZARD_HAT = ITEMS.register("inventor_wizard_hat", () -> new InventorWizardArmorItem(CustomArmorMaterial.INVENTOR_WIZARD, ArmorItem.Type.HELMET, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.INVENTOR_WIZARD_ARMOR));
    public static final RegistryObject<Item> INVENTOR_WIZARD_COSTUME = ITEMS.register("inventor_wizard_costume", () -> new InventorWizardArmorItem(CustomArmorMaterial.INVENTOR_WIZARD, ArmorItem.Type.CHESTPLATE, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.INVENTOR_WIZARD_ARMOR));
    public static final RegistryObject<Item> INVENTOR_WIZARD_TROUSERS = ITEMS.register("inventor_wizard_trousers", () -> new InventorWizardArmorItem(CustomArmorMaterial.INVENTOR_WIZARD, ArmorItem.Type.LEGGINGS, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.INVENTOR_WIZARD_ARMOR));
    public static final RegistryObject<Item> INVENTOR_WIZARD_BOOTS = ITEMS.register("inventor_wizard_boots", () -> new InventorWizardArmorItem(CustomArmorMaterial.INVENTOR_WIZARD, ArmorItem.Type.BOOTS, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentType.INVENTOR_WIZARD_ARMOR));

    public static final RegistryObject<Item> ARCANE_WAND = ITEMS.register("arcane_wand", () -> new ArcaneWandItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WISSEN_WAND = ITEMS.register("wissen_wand", () -> new WissenWandItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARCANE_WOOD_SMOKING_PIPE = ITEMS.register("arcane_wood_smoking_pipe", () -> new SmokingPipeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> INNOCENT_WOOD_SMOKING_PIPE = ITEMS.register("innocent_wood_smoking_pipe", () -> new SmokingPipeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BAMBOO_SMOKING_PIPE = ITEMS.register("bamboo_smoking_pipe", () -> new SmokingPipeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CORK_BAMBOO_SMOKING_PIPE = ITEMS.register("cork_bamboo_smoking_pipe", () -> new SmokingPipeItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARCANE_WOOD_CANE = ITEMS.register("arcane_wood_cane", () -> new CaneItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANE_WOOD_BOW = ITEMS.register("arcane_wood_bow", () -> new ArcaneBowItem(new Item.Properties().durability(576)));

    public static final RegistryObject<Item> BLAZE_REAP = ITEMS.register("blaze_reap", () -> new ArcanePickaxeItem(CustomItemTier.ARCANE_GOLD, 1, -2.8f, new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> CARGO_CARPET = ITEMS.register("cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.CARPET, new Item.Properties()));
    public static final RegistryObject<Item> WHITE_CARGO_CARPET = ITEMS.register("white_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.WHITE, new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_CARGO_CARPET = ITEMS.register("orange_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.ORANGE, new Item.Properties()));
    public static final RegistryObject<Item> MAGENTA_CARGO_CARPET = ITEMS.register("magenta_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.MAGENTA, new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BLUE_CARGO_CARPET = ITEMS.register("light_blue_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.LIGHT_BLUE, new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_CARGO_CARPET = ITEMS.register("yellow_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.YELLOW, new Item.Properties()));
    public static final RegistryObject<Item> LIME_CARGO_CARPET = ITEMS.register("lime_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.LIME, new Item.Properties()));
    public static final RegistryObject<Item> PINK_CARGO_CARPET = ITEMS.register("pink_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.PINK, new Item.Properties()));
    public static final RegistryObject<Item> GRAY_CARGO_CARPET = ITEMS.register("gray_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.GRAY, new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_GRAY_CARGO_CARPET = ITEMS.register("light_gray_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.LIGHT_GRAY, new Item.Properties()));
    public static final RegistryObject<Item> CYAN_CARGO_CARPET = ITEMS.register("cyan_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.CYAN, new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_CARGO_CARPET = ITEMS.register("purple_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.PURPLE, new Item.Properties()));
    public static final RegistryObject<Item> BLUE_CARGO_CARPET = ITEMS.register("blue_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.BLUE, new Item.Properties()));
    public static final RegistryObject<Item> BROWN_CARGO_CARPET = ITEMS.register("brown_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.BROWN, new Item.Properties()));
    public static final RegistryObject<Item> GREEN_CARGO_CARPET = ITEMS.register("green_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.GREEN, new Item.Properties()));
    public static final RegistryObject<Item> RED_CARGO_CARPET = ITEMS.register("red_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.RED, new Item.Properties()));
    public static final RegistryObject<Item> BLACK_CARGO_CARPET = ITEMS.register("black_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.BLACK, new Item.Properties()));
    public static final RegistryObject<Item> RAINBOW_CARGO_CARPET = ITEMS.register("rainbow_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.Colors.RAINBOW, new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_ENCHANTED_BOOK = ITEMS.register("arcane_enchanted_book", () -> new ArcaneEnchantedBookItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ARCANEMICON = ITEMS.register("arcanemicon", () -> new ArcanemiconItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> KNOWLEDGE_SCROLL = ITEMS.register("knowledge_scroll", () -> new KnowledgeSrollItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CREATIVE_KNOWLEDGE_SCROLL = ITEMS.register("creative_knowledge_scroll", () -> new CreativeKnowledgeSrollItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), false));
    public static final RegistryObject<Item> CREATIVE_KNOWLEDGE_ANCIENT_SCROLL = ITEMS.register("creative_knowledge_ancient_scroll", () -> new CreativeKnowledgeSrollItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), true));
    public static final RegistryObject<Item> CREATIVE_SPELL_SCROLL = ITEMS.register("creative_spell_scroll", () -> new CreativeSpellSrollItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), false));
    public static final RegistryObject<Item> CREATIVE_SPELL_ANCIENT_SCROLL = ITEMS.register("creative_spell_ancient_scroll", () -> new CreativeSpellSrollItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), true));

    public static final RegistryObject<Item> VIOLENCE_BANNER_PATTERN_ITEM = ITEMS.register("violence_banner_pattern", () -> new RainBannerPatternItem(RainBannerPatternItem.Types.VIOLENCE, VIOLENCE_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> REPRODUCTION_BANNER_PATTERN_ITEM = ITEMS.register("reproduction_banner_pattern", () -> new RainBannerPatternItem(RainBannerPatternItem.Types.REPRODUCTION, REPRODUCTION_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> COOPERATION_BANNER_PATTERN_ITEM = ITEMS.register("cooperation_banner_pattern", () -> new RainBannerPatternItem(RainBannerPatternItem.Types.COOPERATION, COOPERATION_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> HUNGER_BANNER_PATTERN_ITEM = ITEMS.register("hunger_banner_pattern", () -> new RainBannerPatternItem(RainBannerPatternItem.Types.HUNGER, HUNGER_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SURVIVAL_BANNER_PATTERN_ITEM = ITEMS.register("survival_banner_pattern", () -> new RainBannerPatternItem(RainBannerPatternItem.Types.SURVIVAL, SURVIVAL_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ELEVATION_BANNER_PATTERN_ITEM = ITEMS.register("elevation_banner_pattern", () -> new RainBannerPatternItem(RainBannerPatternItem.Types.ELEVATION, ELEVATION_BANNER_PATTERN_TAG, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MUSIC_DISC_ARCANUM = ITEMS.register("music_disc_arcanum", () -> new ArcaneRecordItem(6, MUSIC_DISC_ARCANUM_SOUND.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 122, new Color(203, 234, 251)));
    public static final RegistryObject<Item> MUSIC_DISC_MOR = ITEMS.register("music_disc_mor", () -> new ArcaneRecordItem(6, MUSIC_DISC_MOR_SOUND.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 305, new Color(244, 245, 230)));
    public static final RegistryObject<Item> MUSIC_DISC_REBORN = ITEMS.register("music_disc_reborn", () -> new ArcaneRecordItem(6, MUSIC_DISC_REBORN_SOUND.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 211, new Color(252, 240, 175)));
    public static final RegistryObject<Item> MUSIC_DISC_SHIMMER = ITEMS.register("music_disc_shimmer", () -> new ArcaneRecordItem(6, MUSIC_DISC_SHIMMER_SOUND.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 234, new Color(203, 234, 251)));
    public static final RegistryObject<Item> MUSIC_DISC_CAPITALISM = ITEMS.register("music_disc_capitalism", () -> new ArcaneRecordItem(6, MUSIC_DISC_CAPITALISM_SOUND.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 96, new Color(252, 240, 175)));
    public static final RegistryObject<Item> MUSIC_DISC_PANACHE = ITEMS.register("music_disc_panache", () -> new ArcaneRecordItem(6, MUSIC_DISC_PANACHE_SOUND.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 91, new Color(252, 240, 175)).setCassette());

    public static final RegistryObject<Item> ARCANE_WOOD_TRIM = ITEMS.register("arcane_wood_trim", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_TRIM = ITEMS.register("wisestone_trim", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_TRIM = ITEMS.register("innocent_wood_trim", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TOP_HAT_TRIM = ITEMS.register("top_hat_trim", () -> new SkinTrimItem(new Item.Properties(), TOP_HAT_SKIN));
    public static final RegistryObject<Item> MAGNIFICENT_MAID_TRIM = ITEMS.register("magnificent_maid_trim", () -> new SkinTrimItem(new Item.Properties(), MAGNIFICENT_MAID_SKIN));
    public static final RegistryObject<Item> SUMMER_LOVE_TRIM = ITEMS.register("summer_love_trim", () -> new SkinTrimItem(new Item.Properties(), SUMMER_LOVE_SKIN));
    public static final RegistryObject<Item> SOUL_HUNTER_TRIM = ITEMS.register("soul_hunter_trim", () -> new SkinTrimItem(new Item.Properties(), SOUL_HUNTER_SKIN));
    public static final RegistryObject<Item> IMPLOSION_TRIM = ITEMS.register("implosion_trim", () -> new SkinTrimItem(new Item.Properties(), IMPLOSION_SKIN));
    public static final RegistryObject<Item> PHANTOM_INK_TRIM = ITEMS.register("phantom_ink_trim", () -> new SkinTrimItem(new Item.Properties(), PHANTOM_INK_SKIN));

    public static final RegistryObject<DrinkBottleItem> VODKA_BOTTLE = ITEMS.register("vodka_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> BOURBON_BOTTLE = ITEMS.register("bourbon_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> WHISKEY_BOTTLE = ITEMS.register("whiskey_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> WHITE_WINE_BOTTLE = ITEMS.register("white_wine_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> RED_WINE_BOTTLE = ITEMS.register("red_wine_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> PORT_WINE_BOTTLE = ITEMS.register("port_wine_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> PALM_LIQUEUR_BOTTLE = ITEMS.register("palm_liqueur_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> MEAD_BOTTLE = ITEMS.register("mead_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> SBITEN_BOTTLE = ITEMS.register("sbiten_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> SLIVOVITZ_BOTTLE = ITEMS.register("slivovitz_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> SAKE_BOTTLE = ITEMS.register("sake_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> SOJU_BOTTLE = ITEMS.register("soju_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> CHICHA_BOTTLE = ITEMS.register("chicha_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> CHACHA_BOTTLE = ITEMS.register("chacha_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> APPLEJACK_BOTTLE = ITEMS.register("applejack_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> RAKIA_BOTTLE = ITEMS.register("rakia_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> KIRSCH_BOTTLE = ITEMS.register("kirsch_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> BOROVICHKA_BOTTLE = ITEMS.register("borovichka_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> PALINKA_BOTTLE = ITEMS.register("palinka_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> TEQUILA_BOTTLE = ITEMS.register("tequila_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> PULQUE_BOTTLE = ITEMS.register("pulque_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> ARKHI_BOTTLE = ITEMS.register("arkhi_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> TEJ_BOTTLE = ITEMS.register("tej_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> WISSEN_BEER_BOTTLE = ITEMS.register("wissen_beer_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> MOR_TINCTURE_BOTTLE = ITEMS.register("mor_tincture_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> INNOCENT_WINE_BOTTLE = ITEMS.register("innocent_wine_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> TARKHUNA_BOTTLE = ITEMS.register("tarkhuna_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> BAIKAL_BOTTLE = ITEMS.register("baikal_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> KVASS_BOTTLE = ITEMS.register("kvass_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<DrinkBottleItem> KISSEL_BOTTLE = ITEMS.register("kissel_bottle", () -> new DrinkBottleItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SHRIMP = ITEMS.register("shrimp", () -> new ShrimpItem(new Item.Properties().food(SHRIMP_FOOD), false));
    public static final RegistryObject<Item> FRIED_SHRIMP = ITEMS.register("fried_shrimp", () -> new ShrimpItem(new Item.Properties().food(FRIED_SHRIMP_FOOD), true));

    //TILE_ENTITIES
    public static final RegistryObject<BlockEntityType<CustomSignTileEntity>> SIGN_TILE_ENTITY = TILE_ENTITIES.register("sign", () -> BlockEntityType.Builder.of(CustomSignTileEntity::new,
            ARCANE_WOOD_SIGN.get(), ARCANE_WOOD_WALL_SIGN.get(),
            INNOCENT_WOOD_SIGN.get(), INNOCENT_WOOD_WALL_SIGN.get(),
            CORK_BAMBOO_SIGN.get(), CORK_BAMBOO_WALL_SIGN.get())
            .build(null));
    public static final RegistryObject<BlockEntityType<CustomHangingSignTileEntity>> HANGING_SIGN_TILE_ENTITY = TILE_ENTITIES.register("hanging_sign", () -> BlockEntityType.Builder.of(CustomHangingSignTileEntity::new,
            ARCANE_WOOD_HANGING_SIGN.get(), ARCANE_WOOD_WALL_HANGING_SIGN.get(),
            INNOCENT_WOOD_HANGING_SIGN.get(), INNOCENT_WOOD_WALL_HANGING_SIGN.get(),
            CORK_BAMBOO_HANGING_SIGN.get(), CORK_BAMBOO_WALL_HANGING_SIGN.get())
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

    public static RegistryObject<BlockEntityType<ArcanePedestalTileEntity>> ARCANE_PEDESTAL_TILE_ENTITY = TILE_ENTITIES.register("arcane_pedestal", () -> BlockEntityType.Builder.of(ArcanePedestalTileEntity::new, ARCANE_PEDESTAL.get(), INNOCENT_PEDESTAL.get(), CORK_BAMBOO_PEDESTAL.get(), WISESTONE_PEDESTAL.get()).build(null));
    public static RegistryObject<BlockEntityType<HoveringTomeStandTileEntity>> HOVERING_TOME_STAND_TILE_ENTITY = TILE_ENTITIES.register("hovering_tome_stand", () -> BlockEntityType.Builder.of(HoveringTomeStandTileEntity::new, HOVERING_TOME_STAND.get(), INNOCENT_HOVERING_TOME_STAND.get(), CORK_BAMBOO_HOVERING_TOME_STAND.get(), WISESTONE_HOVERING_TOME_STAND.get()).build(null));
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
    public static RegistryObject<BlockEntityType<TotemOfDisenchantTileEntity>> TOTEM_OF_DISENCHANT_TILE_ENTITY = TILE_ENTITIES.register("totem_of_disenchant", () -> BlockEntityType.Builder.of(TotemOfDisenchantTileEntity::new, TOTEM_OF_DISENCHANT.get()).build(null));
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
    public static RegistryObject<BlockEntityType<EngravedWisestoneTileEntity>> ENGRAVED_WISESTONE_TILE_ENTITY = TILE_ENTITIES.register("engraved_wisestone", () -> BlockEntityType.Builder.of(EngravedWisestoneTileEntity::new, ENGRAVED_WISESTONE.get(),
                    ENGRAVED_WISESTONE_LUNAM.get(), ENGRAVED_WISESTONE_VITA.get(), ENGRAVED_WISESTONE_SOLEM.get(), ENGRAVED_WISESTONE_MORS.get(), ENGRAVED_WISESTONE_MIRACULUM.get(),
                    ENGRAVED_WISESTONE_TEMPUS.get(), ENGRAVED_WISESTONE_STATERA.get(), ENGRAVED_WISESTONE_ECLIPSIS.get(), ENGRAVED_WISESTONE_SICCITAS.get(), ENGRAVED_WISESTONE_SOLSTITIUM.get(),
                    ENGRAVED_WISESTONE_FAMES.get(), ENGRAVED_WISESTONE_RENAISSANCE.get(), ENGRAVED_WISESTONE_BELLUM.get(), ENGRAVED_WISESTONE_LUX.get(), ENGRAVED_WISESTONE_KARA.get(),
                    ENGRAVED_WISESTONE_DEGRADATIO.get(), ENGRAVED_WISESTONE_PRAEDICTIONEM.get(), ENGRAVED_WISESTONE_EVOLUTIONIS.get(), ENGRAVED_WISESTONE_TENEBRIS.get(), ENGRAVED_WISESTONE_UNIVERSUM.get())
            .build(null));

    public static RegistryObject<BlockEntityType<ArcaneHopperTileEntity>> ARCANE_HOPPER_TILE_ENTITY = TILE_ENTITIES.register("arcane_hopper", () -> BlockEntityType.Builder.of(ArcaneHopperTileEntity::new, ARCANE_HOPPER.get()).build(null));
    public static RegistryObject<BlockEntityType<SensorTileEntity>> SENSOR_TILE_ENTITY = TILE_ENTITIES.register("sensor", () -> BlockEntityType.Builder.of(SensorTileEntity::new, REDSTONE_SENSOR.get(), WISSEN_SENSOR.get(), COOLDOWN_SENSOR.get(), LIGHT_SENSOR.get(), EXPERIENCE_SENSOR.get(), HEAT_SENSOR.get(), STEAM_SENSOR.get()).build(null));
    public static RegistryObject<BlockEntityType<FluidSensorTileEntity>> FLUID_SENSOR_TILE_ENTITY = TILE_ENTITIES.register("fluid_sensor", () -> BlockEntityType.Builder.of(FluidSensorTileEntity::new, FLUID_SENSOR.get()).build(null));
    public static RegistryObject<BlockEntityType<WissenActivatorTileEntity>> WISSEN_ACTIVATOR_TILE_ENTITY = TILE_ENTITIES.register("wissen_activator", () -> BlockEntityType.Builder.of(WissenActivatorTileEntity::new, WISSEN_ACTIVATOR.get()).build(null));
    public static RegistryObject<BlockEntityType<ItemSorterTileEntity>> ITEM_SORTER_TILE_ENTITY = TILE_ENTITIES.register("item_sorter", () -> BlockEntityType.Builder.of(ItemSorterTileEntity::new, ITEM_SORTER.get()).build(null));

    public static RegistryObject<BlockEntityType<WissenCasingTileEntity>> WISSEN_CASING_TILE_ENTITY = TILE_ENTITIES.register("wissen_casing", () -> BlockEntityType.Builder.of(WissenCasingTileEntity::new, ARCANE_WOOD_WISSEN_CASING.get(), INNOCENT_WOOD_WISSEN_CASING.get(), CORK_BAMBOO_WISSEN_CASING.get(), WISESTONE_WISSEN_CASING.get()).build(null));
    public static RegistryObject<BlockEntityType<LightCasingTileEntity>> LIGHT_CASING_TILE_ENTITY = TILE_ENTITIES.register("light_casing", () -> BlockEntityType.Builder.of(LightCasingTileEntity::new, ARCANE_WOOD_LIGHT_CASING.get(), INNOCENT_WOOD_LIGHT_CASING.get(), CORK_BAMBOO_LIGHT_CASING.get(), WISESTONE_LIGHT_CASING.get()).build(null));
    public static RegistryObject<BlockEntityType<FluidCasingTileEntity>> FLUID_CASING_TILE_ENTITY = TILE_ENTITIES.register("fluid_casing", () -> BlockEntityType.Builder.of(FluidCasingTileEntity::new, ARCANE_WOOD_FLUID_CASING.get(), INNOCENT_WOOD_FLUID_CASING.get(), CORK_BAMBOO_FLUID_CASING.get(), WISESTONE_FLUID_CASING.get()).build(null));
    public static RegistryObject<BlockEntityType<SteamCasingTileEntity>> STEAM_CASING_TILE_ENTITY = TILE_ENTITIES.register("steam_casing", () -> BlockEntityType.Builder.of(SteamCasingTileEntity::new, ARCANE_WOOD_STEAM_CASING.get(), INNOCENT_WOOD_STEAM_CASING.get(), CORK_BAMBOO_STEAM_CASING.get(), WISESTONE_STEAM_CASING.get()).build(null));

    public static RegistryObject<BlockEntityType<CreativeWissenStorageTileEntity>> CREATIVE_WISSEN_STORAGE_TILE_ENTITY = TILE_ENTITIES.register("creative_wissen_storage", () -> BlockEntityType.Builder.of(CreativeWissenStorageTileEntity::new, CREATIVE_WISSEN_STORAGE.get()).build(null));
    public static RegistryObject<BlockEntityType<CreativeLightStorageTileEntity>> CREATIVE_LIGHT_STORAGE_TILE_ENTITY = TILE_ENTITIES.register("creative_light_storage", () -> BlockEntityType.Builder.of(CreativeLightStorageTileEntity::new, CREATIVE_LIGHT_STORAGE.get()).build(null));
    public static RegistryObject<BlockEntityType<CreativeFluidStorageTileEntity>> CREATIVE_FLUID_STORAGE_TILE_ENTITY = TILE_ENTITIES.register("creative_fluid_storage", () -> BlockEntityType.Builder.of(CreativeFluidStorageTileEntity::new, CREATIVE_FLUID_STORAGE.get()).build(null));
    public static RegistryObject<BlockEntityType<CreativeSteamStorageTileEntity>> CREATIVE_STEAM_STORAGE_TILE_ENTITY = TILE_ENTITIES.register("creative_steam_storage", () -> BlockEntityType.Builder.of(CreativeSteamStorageTileEntity::new, CREATIVE_STEAM_STORAGE.get()).build(null));

    public static RegistryObject<BlockEntityType<SaltTorchTileEntity>> SALT_TORCH_TILE_ENTITY = TILE_ENTITIES.register("salt_torch", () -> BlockEntityType.Builder.of(SaltTorchTileEntity::new, ARCANE_SALT_TORCH.get(), ARCANE_SALT_WALL_TORCH.get(), INNOCENT_SALT_TORCH.get(), INNOCENT_SALT_WALL_TORCH.get(), CORK_BAMBOO_SALT_TORCH.get(), CORK_BAMBOO_SALT_WALL_TORCH.get(), WISESTONE_SALT_TORCH.get(), WISESTONE_SALT_WALL_TORCH.get()).build(null));
    public static RegistryObject<BlockEntityType<SaltLanternTileEntity>> SALT_LANTERN_TILE_ENTITY = TILE_ENTITIES.register("salt_lantern", () -> BlockEntityType.Builder.of(SaltLanternTileEntity::new, ARCANE_SALT_LANTERN.get(), INNOCENT_SALT_LANTERN.get(), CORK_BAMBOO_SALT_LANTERN.get(), WISESTONE_SALT_LANTERN.get()).build(null));
    public static RegistryObject<BlockEntityType<SaltCampfireTileEntity>> SALT_CAMPFIRE_TILE_ENTITY = TILE_ENTITIES.register("salt_campfire", () -> BlockEntityType.Builder.of(SaltCampfireTileEntity::new, ARCANE_SALT_CAMPFIRE.get(), INNOCENT_SALT_CAMPFIRE.get(), CORK_BAMBOO_SALT_CAMPFIRE.get(), WISESTONE_SALT_CAMPFIRE.get()).build(null));

    public static RegistryObject<BlockEntityType<CrossBalkTileEntity>> CROSS_BAULK_TILE_ENTITY = TILE_ENTITIES.register("cross_baulk", () -> BlockEntityType.Builder.of(CrossBalkTileEntity::new, ARCANE_WOOD_CROSS_BAULK.get(), STRIPPED_ARCANE_WOOD_CROSS_BAULK.get(), ARCANE_WOOD_PLANKS_CROSS_BAULK.get(), INNOCENT_WOOD_CROSS_BAULK.get(), STRIPPED_INNOCENT_WOOD_CROSS_BAULK.get(), INNOCENT_WOOD_PLANKS_CROSS_BAULK.get(), CORK_BAMBOO_CROSS_BAULK.get(), CORK_BAMBOO_PLANKS_CROSS_BAULK.get(), CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK.get()).build(null));
    public static RegistryObject<BlockEntityType<PlacedItemsTileEntity>> PLACED_ITEMS_TILE_ENTITY = TILE_ENTITIES.register("placed_items", () -> BlockEntityType.Builder.of(PlacedItemsTileEntity::new, PLACED_ITEMS_BLOCK.get()).build(null));

    //ENTITIES
    public static final RegistryObject<EntityType<CustomBoatEntity>> BOAT = ENTITIES.register("boat", () -> EntityType.Builder.<CustomBoatEntity>of(CustomBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(MOD_ID, "boat").toString()));
    public static final RegistryObject<EntityType<CustomChestBoatEntity>> CHEST_BOAT = ENTITIES.register("chest_boat", () -> EntityType.Builder.<CustomChestBoatEntity>of(CustomChestBoatEntity::new, MobCategory.MISC).sized(1.375f, 0.5625f).build(new ResourceLocation(MOD_ID, "chest_boat").toString()));
    public static final RegistryObject<EntityType<SpellProjectileEntity>> SPELL_PROJECTILE = ENTITIES.register("spell_projectile", () -> EntityType.Builder.<SpellProjectileEntity>of(SpellProjectileEntity::new, MobCategory.MISC).sized(0.4f, 0.4f).build(new ResourceLocation(MOD_ID, "spell_projectile").toString()));
    public static final RegistryObject<EntityType<ThrowedScytheEntity>> THROWED_SCYTHE_PROJECTILE = ENTITIES.register("throwed_scythe", () -> EntityType.Builder.<ThrowedScytheEntity>of(ThrowedScytheEntity::new, MobCategory.MISC).sized(1.75f, 0.2f).build(new ResourceLocation(MOD_ID, "throwed_scythe").toString()));
    public static final RegistryObject<EntityType<SplitArrowEntity>> SPLIT_ARROW_PROJECTILE = ENTITIES.register("split_arrow", () -> EntityType.Builder.<SplitArrowEntity>of(SplitArrowEntity::new, MobCategory.MISC).sized(0.2f, 0.2f).build(new ResourceLocation(MOD_ID, "split_arrow").toString()));
    public static final RegistryObject<EntityType<SniffaloEntity>> SNIFFALO = ENTITIES.register("sniffalo", () -> EntityType.Builder.<SniffaloEntity>of(SniffaloEntity::new, MobCategory.CREATURE).sized(1.9F, 1.75F).clientTrackingRange(10).build(new ResourceLocation(MOD_ID, "sniffalo").toString()));

    public static final RegistryObject<ForgeSpawnEggItem> SNIFFALO_SPAWN_EGG = ITEMS.register("sniffalo_spawn_egg", () -> new ForgeSpawnEggItem(SNIFFALO, ColorUtils.packColor(255, 96, 58, 62), ColorUtils.packColor(255, 181, 139, 117), new Item.Properties()));

    //PARTICLES
    public static RegistryObject<WispParticleType> WISP_PARTICLE = PARTICLES.register("wisp", WispParticleType::new);
    public static RegistryObject<SparkleParticleType> SPARKLE_PARTICLE = PARTICLES.register("sparkle", SparkleParticleType::new);
    public static RegistryObject<KarmaParticleType> KARMA_PARTICLE = PARTICLES.register("karma", KarmaParticleType::new);
    public static RegistryObject<ArcaneWoodLeafParticleType> ARCANE_WOOD_LEAF_PARTICLE = PARTICLES.register("arcane_wood_leaf", ArcaneWoodLeafParticleType::new);
    public static RegistryObject<InnocenceWoodLeafParticleType> INNOCENT_WOOD_LEAF_PARTICLE = PARTICLES.register("innocence_wood_leaf", InnocenceWoodLeafParticleType::new);
    public static RegistryObject<SteamParticleType> STEAM_PARTICLE = PARTICLES.register("steam", SteamParticleType::new);
    public static RegistryObject<SmokeParticleType> SMOKE_PARTICLE = PARTICLES.register("smoke", SmokeParticleType::new);
    public static RegistryObject<CubeParticleType> CUBE_PARTICLE = PARTICLES.register("cube", CubeParticleType::new);
    public static RegistryObject<TrailParticleType> TRAIL_PARTICLE = PARTICLES.register("trail", TrailParticleType::new);

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

    public static final RegistryObject<MenuType<ArcaneHopperContainer>> ARCANE_HOPPER_CONTAINER
            = CONTAINERS.register("arcane_hopper",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new ArcaneHopperContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<MenuType<ItemSorterContainer>> ITEM_SORTER_CONTAINER
            = CONTAINERS.register("item_sorter",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new ItemSorterContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<MenuType<TotemOfDisenchantContainer>> TOTEM_OF_DISENCHANT_CONTAINER
            = CONTAINERS.register("totem_of_disenchant",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new TotemOfDisenchantContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<MenuType<RunicPedestalContainer>> RUNIC_PEDESTAL_CONTAINER
            = CONTAINERS.register("runic_pedestal",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                Level world = inv.player.getCommandSenderWorld();
                return new RunicPedestalContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<MenuType<CrystalBagContainer>> CRYSTAL_BAG_CONTAINER
            = CONTAINERS.register("crystal_bag",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                Level world = inv.player.getCommandSenderWorld();
                return new CrystalBagContainer(windowId, world, data.readItem(), inv, inv.player);
            })));

    public static final RegistryObject<MenuType<AlchemyBagContainer>> ALCHEMY_BAG_CONTAINER
            = CONTAINERS.register("alchemy_bag",
            () -> IForgeMenuType.create(((windowId, inv, data) -> {
                Level world = inv.player.getCommandSenderWorld();
                return new AlchemyBagContainer(windowId, world, data.readItem(), inv, inv.player);
            })));

    public static final RegistryObject<BannerPattern> VIOLENCE_BANNER_PATTERN = BANNER_PATTERNS.register("violence", () -> new BannerPattern("wrv"));
    public static final RegistryObject<BannerPattern> REPRODUCTION_BANNER_PATTERN = BANNER_PATTERNS.register("reproduction", () -> new BannerPattern("wrr"));
    public static final RegistryObject<BannerPattern> COOPERATION_BANNER_PATTERN = BANNER_PATTERNS.register("cooperation", () -> new BannerPattern("wrc"));
    public static final RegistryObject<BannerPattern> HUNGER_BANNER_PATTERN = BANNER_PATTERNS.register("hunger", () -> new BannerPattern("wrh"));
    public static final RegistryObject<BannerPattern> SURVIVAL_BANNER_PATTERN = BANNER_PATTERNS.register("survival", () -> new BannerPattern("wrs"));
    public static final RegistryObject<BannerPattern> ELEVATION_BANNER_PATTERN = BANNER_PATTERNS.register("elevation", () -> new BannerPattern("wre"));

    public static final RegistryObject<SoundEvent> WISSEN_BURST_SOUND = SOUND_EVENTS.register("wissen_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wissen_burst")));
    public static final RegistryObject<SoundEvent> WISSEN_TRANSFER_SOUND = SOUND_EVENTS.register("wissen_transfer", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wissen_transfer")));

    public static final RegistryObject<SoundEvent> ARCANUM_LENS_RESONATE_SOUND = SOUND_EVENTS.register("arcanum_lens_resonate", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanum_lens_resonate")));

    public static final RegistryObject<SoundEvent> STEAM_BURST_SOUND = SOUND_EVENTS.register("steam_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "steam_burst")));

    public static final RegistryObject<SoundEvent> SPELL_CAST_SOUND = SOUND_EVENTS.register("spell_cast", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "spell_cast")));
    public static final RegistryObject<SoundEvent> SPELL_BURST_SOUND = SOUND_EVENTS.register("spell_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "spell_burst")));
    public static final RegistryObject<SoundEvent> SPELL_RELOAD_SOUND = SOUND_EVENTS.register("spell_reload", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "spell_reload")));

    public static final RegistryObject<SoundEvent> ARCANEMICON_OFFERING_SOUND = SOUND_EVENTS.register("arcanemicon_offering", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanemicon_offering")));

    public static final RegistryObject<SoundEvent> ARCANUM_DUST_TRANSMUTATION_SOUND = SOUND_EVENTS.register("arcanum_dust_transmutation", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcanum_dust_transmutation")));

    public static final RegistryObject<SoundEvent> WISSEN_ALTAR_BURST_SOUND = SOUND_EVENTS.register("wissen_altar_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wissen_altar_burst")));

    public static final RegistryObject<SoundEvent> WISSEN_CRYSTALLIZER_START_SOUND = SOUND_EVENTS.register("wissen_crystallizer_start", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wissen_crystallizer_start")));
    public static final RegistryObject<SoundEvent> WISSEN_CRYSTALLIZER_END_SOUND = SOUND_EVENTS.register("wissen_crystallizer_end", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wissen_crystallizer_end")));
    public static final RegistryObject<SoundEvent> WISSEN_CRYSTALLIZER_LOOP_SOUND = SOUND_EVENTS.register("wissen_crystallizer_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "wissen_crystallizer_loop")));

    public static final RegistryObject<SoundEvent> ARCANE_WORKBENCH_START_SOUND = SOUND_EVENTS.register("arcane_workbench_start", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_workbench_start")));
    public static final RegistryObject<SoundEvent> ARCANE_WORKBENCH_END_SOUND = SOUND_EVENTS.register("arcane_workbench_end", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_workbench_end")));
    public static final RegistryObject<SoundEvent> ARCANE_WORKBENCH_LOOP_SOUND = SOUND_EVENTS.register("arcane_workbench_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_workbench_loop")));

    public static final RegistryObject<SoundEvent> TOTEM_OF_EXPERIENCE_ABSORPTION_LOOP_SOUND = SOUND_EVENTS.register("totem_of_experience_absorption_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "totem_of_experience_absorption_loop")));

    public static final RegistryObject<SoundEvent> TOTEM_OF_DISENCHANT_START_SOUND = SOUND_EVENTS.register("totem_of_disenchant_start", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "totem_of_disenchant_start")));
    public static final RegistryObject<SoundEvent> TOTEM_OF_DISENCHANT_END_SOUND = SOUND_EVENTS.register("totem_of_disenchant_end", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "totem_of_disenchant_end")));
    public static final RegistryObject<SoundEvent> TOTEM_OF_DISENCHANT_LOOP_SOUND = SOUND_EVENTS.register("totem_of_disenchant_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "totem_of_disenchant_loop")));

    public static final RegistryObject<SoundEvent> ALTAR_OF_DROUGHT_SOUND = SOUND_EVENTS.register("altar_of_drought_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "altar_of_drought_burst")));

    public static final RegistryObject<SoundEvent> ARCANE_ITERATOR_START_SOUND = SOUND_EVENTS.register("arcane_iterator_start", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_iterator_start")));
    public static final RegistryObject<SoundEvent> ARCANE_ITERATOR_END_SOUND = SOUND_EVENTS.register("arcane_iterator_end", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_iterator_end")));
    public static final RegistryObject<SoundEvent> ARCANE_ITERATOR_LOOP_SOUND = SOUND_EVENTS.register("arcane_iterator_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "arcane_iterator_loop")));

    public static final RegistryObject<SoundEvent> CRYSTAL_RITUAL_START_SOUND = SOUND_EVENTS.register("crystal_ritual_start", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "crystal_ritual_start")));
    public static final RegistryObject<SoundEvent> CRYSTAL_RITUAL_END_SOUND = SOUND_EVENTS.register("crystal_ritual_end", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "crystal_ritual_end")));

    public static final RegistryObject<SoundEvent> PIPE_SOUND = SOUND_EVENTS.register("pipe", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "pipe")));
    public static final RegistryObject<SoundEvent> BOOM_SOUND = SOUND_EVENTS.register("boom", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "boom")));
    public static final RegistryObject<SoundEvent> MOAI_SOUND = SOUND_EVENTS.register("moai", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "moai")));

    public static final RegistryObject<ArgumentTypeInfo<?, ?>> KNOWLEDGE_ARG = ARG_TYPES.register("knowledge", () -> ArgumentTypeInfos.registerByClass(KnowledgeArgument.class, SingletonArgumentInfo.contextFree(KnowledgeArgument::knowledges)));
    public static final RegistryObject<ArgumentTypeInfo<?, ?>> SPELLS_ARG = ARG_TYPES.register("spell", () -> ArgumentTypeInfos.registerByClass(SpellArgument.class, SingletonArgumentInfo.contextFree(SpellArgument::spells)));
    public static final RegistryObject<ArgumentTypeInfo<?, ?>> ARCANE_ENCHANTMENT_ARG = ARG_TYPES.register("arcane_enchantment", () -> ArgumentTypeInfos.registerByClass(ArcaneEnchantmentArgument.class, SingletonArgumentInfo.contextFree(ArcaneEnchantmentArgument::arcaneEnchantments)));

    public static final RegistryObject<Attribute> WISSEN_DISCOUNT = ATTRIBUTES.register("wissen_discount", () -> new RangedAttribute("attribute.name.wizards_reborn.wissen_discount", 0, 0, 75).setSyncable(true));
    public static final RegistryObject<Attribute> MAGIC_ARMOR = ATTRIBUTES.register("magic_armor", () -> new RangedAttribute("attribute.name.wizards_reborn.magic_armor", 0, 0, 100).setSyncable(true));
    public static final RegistryObject<Attribute> MAGIC_MODIFIER = ATTRIBUTES.register("magic_modifier", () -> new RangedAttribute("attribute.name.wizards_reborn.magic_modifier", 0, 0, 100).setSyncable(true));
    public static final RegistryObject<Attribute> ARCANE_DAMAGE = ATTRIBUTES.register("arcane_damage", () -> new RangedAttribute("attribute.name.wizards_reborn.arcane_damage", 0, 0, 1000).setSyncable(true));

    public static final RegistryObject<MobEffect> MOR_SPORES_EFFECT = EFFECTS.register("mor_spores", MorSporesEffect::new);
    public static final RegistryObject<MobEffect> WISSEN_AURA_EFFECT = EFFECTS.register("wissen_aura", WissenAuraEffect::new);
    public static final RegistryObject<MobEffect> IRRITATION_EFFECT = EFFECTS.register("irritation", IrritationEffect::new);
    public static final RegistryObject<MobEffect> TIPSY_EFFECT = EFFECTS.register("tipsy", TipsyEffect::new);

    public static final RegistryObject<TrunkPlacerType<ArcaneWoodTrunkPlacer>> ARCANE_WOOD_TRUNK_PLACER = TRUNK_PLACER_TYPES.register("arcane_wood_trunk_placer", () -> new TrunkPlacerType<>(ArcaneWoodTrunkPlacer.CODEC));

    public static final RegistryObject<BlockStateProviderType<?>> AN_STATEPROVIDER = BLOCK_STATE_PROVIDER_TYPE.register("an_stateprovider", () -> new BlockStateProviderType<>(SupplierBlockStateProvider.CODEC));

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM = LOOT_MODIFIERS.register("add_item", AddItemLootModifier.CODEC);
    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_ITEM_LIST = LOOT_MODIFIERS.register("add_item_list", AddItemListLootModifier.CODEC);

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
    public static final RegistryObject<LiquidBlock> WISSEN_TEA_FLUID_BLOCK = BLOCKS.register("wissen_tea_block", () -> new LiquidBlock(WizardsReborn.WISSEN_TEA_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
    public static final RegistryObject<LiquidBlock> MUSHROOM_BREW_FLUID_BLOCK = BLOCKS.register("mushroom_brew_block", () -> new LiquidBlock(WizardsReborn.MUSHROOM_BREW_FLUID, BlockBehaviour.Properties.copy(Blocks.WATER)));
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
        LOOT_MODIFIERS.register(eventBus);

        CreateIntegration.init(eventBus);
        FarmersDelightIntegration.init(eventBus);

        setupCrystals();
        setupMonograms();
        setupSpells();
        setupSkins();
        setupArcaneEnchantments();
        setupCrystalRituals();

        for (Skin skin : Skins.getSkins()) {
            skin.setupSkinEntries();
        }

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);

        DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            setupWandCrystalsModels();
            WissenWandItem.setupTooltips();

            forgeBus.addListener(ClientTickHandler::clientTickEnd);
            forgeBus.addListener(WorldRenderHandler::onRenderWorldLast);
            forgeBus.addListener(ClientWorldEvent::onTick);
            forgeBus.addListener(ClientWorldEvent::onRender);
            forgeBus.addListener(HUDEventHandler::onDrawScreenPost);
            forgeBus.addListener(TooltipEventHandler::onPostTooltipEvent);
            forgeBus.addListener(KeyBindHandler::onInput);
            forgeBus.addListener(KeyBindHandler::onKey);
            forgeBus.addListener(KeyBindHandler::onMouseKey);
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

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
        RegisterAlchemyPotions.init();
        RegisterKnowledges.init();
        Researches.init();
        WissenWandItem.setupControlTypes();

        setupCrystalsItems();
        setupDrinksItems();

        event.enqueueWork(() -> {
            AxeItem.STRIPPABLES = new ImmutableMap.Builder<Block, Block>().putAll(AxeItem.STRIPPABLES)
                    .put(ARCANE_WOOD_LOG.get(), STRIPPED_ARCANE_WOOD_LOG.get())
                    .put(ARCANE_WOOD.get(), STRIPPED_ARCANE_WOOD.get())
                    .put(ARCANE_WOOD_BAULK.get(), STRIPPED_ARCANE_WOOD_BAULK.get())
                    .put(INNOCENT_WOOD_LOG.get(), STRIPPED_INNOCENT_WOOD_LOG.get())
                    .put(INNOCENT_WOOD.get(), STRIPPED_INNOCENT_WOOD.get())
                    .put(INNOCENT_WOOD_BAULK.get(), STRIPPED_INNOCENT_WOOD_BAULK.get()).build();

            ArcanePedestalBlock.blocksList.put(ARCANE_PEDESTAL.get(), HOVERING_TOME_STAND.get());
            ArcanePedestalBlock.blocksList.put(INNOCENT_PEDESTAL.get(), INNOCENT_HOVERING_TOME_STAND.get());
            ArcanePedestalBlock.blocksList.put(CORK_BAMBOO_PEDESTAL.get(), CORK_BAMBOO_HOVERING_TOME_STAND.get());
            ArcanePedestalBlock.blocksList.put(WISESTONE_PEDESTAL.get(), WISESTONE_HOVERING_TOME_STAND.get());
            HoveringTomeStandBlock.blocksList.put(HOVERING_TOME_STAND.get(), ARCANE_PEDESTAL.get());
            HoveringTomeStandBlock.blocksList.put(INNOCENT_HOVERING_TOME_STAND.get(), INNOCENT_PEDESTAL.get());
            HoveringTomeStandBlock.blocksList.put(CORK_BAMBOO_HOVERING_TOME_STAND.get(), CORK_BAMBOO_PEDESTAL.get());
            HoveringTomeStandBlock.blocksList.put(WISESTONE_HOVERING_TOME_STAND.get(), WISESTONE_PEDESTAL.get());

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
            fireblock.setFlammable(ARCANE_WOOD_BAULK.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD_CROSS_BAULK.get(), 5, 20);
            fireblock.setFlammable(STRIPPED_ARCANE_WOOD_BAULK.get(), 5, 20);
            fireblock.setFlammable(STRIPPED_ARCANE_WOOD_CROSS_BAULK.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD_PLANKS_BAULK.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD_PLANKS_CROSS_BAULK.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD_FENCE.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD_FENCE_GATE.get(), 5, 20);
            fireblock.setFlammable(ARCANE_WOOD_LEAVES.get(), 30, 60);
            fireblock.setFlammable(INNOCENT_WOOD_LOG.get(), 5, 20);
            fireblock.setFlammable(INNOCENT_WOOD.get(), 5, 20);
            fireblock.setFlammable(STRIPPED_INNOCENT_WOOD_LOG.get(), 5, 20);
            fireblock.setFlammable(STRIPPED_INNOCENT_WOOD.get(), 5, 20);
            fireblock.setFlammable(INNOCENT_WOOD_PLANKS.get(), 5, 20);
            fireblock.setFlammable(INNOCENT_WOOD_STAIRS.get(), 5, 20);
            fireblock.setFlammable(INNOCENT_WOOD_SLAB.get(), 5, 20);
            fireblock.setFlammable(INNOCENT_WOOD_BAULK.get(), 5, 20);
            fireblock.setFlammable(INNOCENT_WOOD_CROSS_BAULK.get(), 5, 20);
            fireblock.setFlammable(STRIPPED_INNOCENT_WOOD_BAULK.get(), 5, 20);
            fireblock.setFlammable(STRIPPED_INNOCENT_WOOD_CROSS_BAULK.get(), 5, 20);
            fireblock.setFlammable(INNOCENT_WOOD_PLANKS_BAULK.get(), 5, 20);
            fireblock.setFlammable(INNOCENT_WOOD_PLANKS_CROSS_BAULK.get(), 5, 20);
            fireblock.setFlammable(INNOCENT_WOOD_FENCE.get(), 5, 20);
            fireblock.setFlammable(INNOCENT_WOOD_FENCE_GATE.get(), 5, 20);
            fireblock.setFlammable(INNOCENT_WOOD_LEAVES.get(), 30, 60);
            fireblock.setFlammable(PETALS_OF_INNOCENCE.get(), 60, 100);
            fireblock.setFlammable(ARCANE_LINEN_HAY.get(), 60, 20);
            fireblock.setFlammable(SHINY_CLOVER_CROP.get(), 60, 100);
            fireblock.setFlammable(SHINY_CLOVER.get(), 60, 100);

            ComposterBlock.add(0.3F, ARCANE_WOOD_LEAVES_ITEM.get());
            ComposterBlock.add(0.3F, ARCANE_WOOD_SAPLING_ITEM.get());
            ComposterBlock.add(0.3F, INNOCENT_WOOD_LEAVES_ITEM.get());
            ComposterBlock.add(0.3F, INNOCENT_WOOD_SAPLING_ITEM.get());
            ComposterBlock.add(0.3F, PETALS_OF_INNOCENCE.get());
            ComposterBlock.add(0.3F, ARCANE_LINEN_SEEDS.get());
            ComposterBlock.add(0.65F, ARCANE_LINEN_ITEM.get());
            ComposterBlock.add(0.85F, ARCANE_LINEN_HAY_ITEM.get());
            ComposterBlock.add(0.65F, MOR_ITEM.get());
            ComposterBlock.add(0.65F, ELDER_MOR_ITEM.get());
            ComposterBlock.add(0.85F, MOR_BLOCK_ITEM.get());
            ComposterBlock.add(0.85F, ELDER_MOR_BLOCK_ITEM.get());
            ComposterBlock.add(0.9F, PITCHER_TURNIP_ITEM.get());
            ComposterBlock.add(0.9F, SHINY_CLOVER_ITEM.get());
            ComposterBlock.add(0.2F, PETALS.get());
            ComposterBlock.add(0.2F, GROUND_BROWN_MUSHROOM.get());
            ComposterBlock.add(0.2F, GROUND_RED_MUSHROOM.get());
            ComposterBlock.add(0.2F, GROUND_CRIMSON_FUNGUS.get());
            ComposterBlock.add(0.2F, GROUND_WARPED_FUNGUS.get());
            ComposterBlock.add(0.2F, GROUND_MOR.get());
            ComposterBlock.add(0.2F, GROUND_ELDER_MOR.get());

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

            MenuScreens.register(ARCANE_WORKBENCH_CONTAINER.get(), ArcaneWorkbenchScreen::new);
            MenuScreens.register(JEWELER_TABLE_CONTAINER.get(), JewelerTableScreen::new);
            MenuScreens.register(ALCHEMY_FURNACE_CONTAINER.get(), AlchemyFurnaceScreen::new);
            MenuScreens.register(ALCHEMY_MACHINE_CONTAINER.get(), AlchemyMachineScreen::new);
            MenuScreens.register(ARCANE_HOPPER_CONTAINER.get(), ArcaneHopperScreen::new);
            MenuScreens.register(ITEM_SORTER_CONTAINER.get(), ItemSorterScreen::new);
            MenuScreens.register(TOTEM_OF_DISENCHANT_CONTAINER.get(), TotemOfDisenchantScreen::new);
            MenuScreens.register(RUNIC_PEDESTAL_CONTAINER.get(), RunicPedestalScreen::new);
            MenuScreens.register(CRYSTAL_BAG_CONTAINER.get(), CrystalBagScreen::new);
            MenuScreens.register(ALCHEMY_BAG_CONTAINER.get(), AlchemyBagScreen::new);

            CuriosRendererRegistry.register(ARCANUM_AMULET.get(), AmuletRenderer::new);
            CuriosRendererRegistry.register(ARCACITE_AMULET.get(), AmuletRenderer::new);
            CuriosRendererRegistry.register(LEATHER_BELT.get(), BeltRenderer::new);
            CuriosRendererRegistry.register(ARCANE_FORTRESS_BELT.get(), BeltRenderer::new);
            CuriosRendererRegistry.register(INVENTOR_WIZARD_BELT.get(), BeltRenderer::new);
            CuriosRendererRegistry.register(CRYSTAL_BAG.get(), BagRenderer::new);
            CuriosRendererRegistry.register(ALCHEMY_BAG.get(), BagRenderer::new);
            CuriosRendererRegistry.register(LEATHER_COLLAR.get(), CollarRenderer::new);
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
        Spells.register(HEART_OF_NATURE_SPELL);
        Spells.register(WATER_BREATHING_SPELL);
        Spells.register(AIR_FLOW_SPELL);
        Spells.register(FIRE_SHIELD_SPELL);
        Spells.register(BLINK_SPELL);
        Spells.register(SNOWFLAKE_SPELL);
        Spells.register(HOLY_CROSS_SPELL);
        Spells.register(CURSE_CROSS_SPELL);
        Spells.register(POISON_SPELL);
        Spells.register(MAGIC_SPROUT_SPELL);
        Spells.register(DIRT_BLOCK_SPELL);
        Spells.register(WATER_BLOCK_SPELL);
        Spells.register(AIR_IMPACT_SPELL);
        Spells.register(ICE_BLOCK_SPELL);
        Spells.register(EARTH_CHARGE_SPELL);
        Spells.register(WATER_CHARGE_SPELL);
        Spells.register(AIR_CHARGE_SPELL);
        Spells.register(FIRE_CHARGE_SPELL);
        Spells.register(VOID_CHARGE_SPELL);
        Spells.register(FROST_CHARGE_SPELL);
        Spells.register(HOLY_CHARGE_SPELL);
        Spells.register(CURSE_CHARGE_SPELL);
        Spells.register(EARTH_AURA_SPELL);
        Spells.register(WATER_AURA_SPELL);
        Spells.register(AIR_AURA_SPELL);
        Spells.register(FIRE_AURA_SPELL);
        Spells.register(VOID_AURA_SPELL);
        Spells.register(FROST_AURA_SPELL);
        Spells.register(HOLY_AURA_SPELL);
        Spells.register(CURSE_AURA_SPELL);
        Spells.register(RAIN_CLOUD_SPELL);
        Spells.register(LAVA_BLOCK_SPELL);
        Spells.register(ICICLE_SPELL);
        Spells.register(SHARP_BLINK_SPELL);
        Spells.register(CRYSTAL_CRUSHING_SPELL);
        Spells.register(TOXIC_RAIN_SPELL);
        Spells.register(MOR_SWARM_SPELL);
        Spells.register(WITHERING_SPELL);
        Spells.register(IRRITATION_SPELL);
        Spells.register(NECROTIC_RAY_SPELL);
        Spells.register(LIGHT_RAY_SPELL);
        Spells.register(INCINERATION_SPELL);
        Spells.register(REPENTANCE_SPELL);
        Spells.register(RENUNCIATION_SPELL);
        Spells.register(EMBER_RAY_SPELL);
        Spells.register(WISDOM_SPELL);

        Spells.register(PIPE_SOUND_SPELL);
        Spells.register(BOOM_SOUND_SPELL);
        Spells.register(MOAI_SOUND_SPELL);
        PIPE_SOUND_SPELL.addAllCrystalType();
        BOOM_SOUND_SPELL.addAllCrystalType();
        MOAI_SOUND_SPELL.addAllCrystalType();
    }

    public static void setupSkins() {
        Skins.register(TOP_HAT_SKIN);
        Skins.register(SOUL_HUNTER_SKIN);
        Skins.register(IMPLOSION_SKIN);
        Skins.register(PHANTOM_INK_SKIN);
        Skins.register(MAGNIFICENT_MAID_SKIN);
        Skins.register(SUMMER_LOVE_SKIN);
    }

    public static void setupArcaneEnchantments() {
        ArcaneEnchantments.register(WISSEN_MENDING_ARCANE_ENCHANTMENT);
        ArcaneEnchantments.register(LIFE_MENDING_ARCANE_ENCHANTMENT);
        ArcaneEnchantments.register(MAGIC_BLADE_ARCANE_ENCHANTMENT);
        ArcaneEnchantments.register(THROW_ARCANE_ENCHANTMENT);
        ArcaneEnchantments.register(LIFE_ROOTS_ARCANE_ENCHANTMENT);
        ArcaneEnchantments.register(WISSEN_CHARGE_ARCANE_ENCHANTMENT);
        ArcaneEnchantments.register(EAGLE_SHOT_ARCANE_ENCHANTMENT);
        ArcaneEnchantments.register(SPLIT_ARCANE_ENCHANTMENT);
        ArcaneEnchantments.register(SONAR_ARCANE_ENCHANTMENT);
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

    public static void setupDrinksItems() {
        int second = 20;
        int minute = 1200;
        int day = 24000;
        VODKA_BOTTLE.get().setAged(day * 24000, 24000);
        RED_WINE_BOTTLE.get().setAged(day * 10, day).addEffect(new DrinkBottleItem.EffectInstance(MobEffects.REGENERATION, minute, minute * 10, 0, 2));
        INNOCENT_WINE_BOTTLE.get().setAlcoholic(false);
        TARKHUNA_BOTTLE.get().setAlcoholic(false);
        BAIKAL_BOTTLE.get().setAlcoholic(false);
        KVASS_BOTTLE.get().setAlcoholic(false).setStageForAcl(4);
        KISSEL_BOTTLE.get().setAlcoholic(false);
    }

    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerCaps(RegisterCapabilitiesEvent event) {
            event.register(IKnowledge.class);
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeCreationEvent event) {
            event.put(WizardsReborn.SNIFFALO.get(), SniffaloEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void registerAttributes(EntityAttributeModificationEvent event) {
            for (EntityType<? extends LivingEntity> livingEntity : event.getTypes()) {
                event.add(livingEntity, MAGIC_ARMOR.get());
                event.add(EntityType.PLAYER, ARCANE_DAMAGE.get());
            }
            event.add(EntityType.PLAYER, WISSEN_DISCOUNT.get());
            event.add(EntityType.PLAYER, MAGIC_MODIFIER.get());
        }
    }
}