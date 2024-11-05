package mod.maxbogomol.wizards_reborn.registry.common.item;

import mod.maxbogomol.fluffy_fur.client.event.BowHandler;
import mod.maxbogomol.fluffy_fur.client.model.item.CustomModel;
import mod.maxbogomol.fluffy_fur.client.render.item.LargeItemRenderer;
import mod.maxbogomol.fluffy_fur.common.item.CustomBoatItem;
import mod.maxbogomol.fluffy_fur.common.item.CustomChestBoatItem;
import mod.maxbogomol.fluffy_fur.common.item.FuelBlockItem;
import mod.maxbogomol.fluffy_fur.common.item.FuelItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurModels;
import mod.maxbogomol.fluffy_fur.registry.common.item.FluffyFurItems;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentTypes;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalHandler;
import mod.maxbogomol.wizards_reborn.client.model.item.CollarItemOverrides;
import mod.maxbogomol.wizards_reborn.client.model.item.DrinksModels;
import mod.maxbogomol.wizards_reborn.client.model.item.WandCrystalsModels;
import mod.maxbogomol.wizards_reborn.client.render.curio.*;
import mod.maxbogomol.wizards_reborn.common.item.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcanewood.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.curio.*;
import mod.maxbogomol.wizards_reborn.common.item.equipment.innocentwood.*;
import mod.maxbogomol.wizards_reborn.registry.common.*;
import mod.maxbogomol.wizards_reborn.registry.common.banner.WizardsRebornBannerPatternTags;
import mod.maxbogomol.wizards_reborn.registry.common.block.WizardsRebornBlocks;
import mod.maxbogomol.wizards_reborn.registry.common.entity.WizardsRebornEntities;
import mod.maxbogomol.wizards_reborn.registry.common.fluid.WizardsRebornFluids;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.awt.*;
import java.util.Map;

public class WizardsRebornItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, WizardsReborn.MOD_ID);

    public static final RegistryObject<Item> ARCANE_GOLD_INGOT = ITEMS.register("arcane_gold_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_NUGGET = ITEMS.register("arcane_gold_nugget", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_ARCANE_GOLD = ITEMS.register("raw_arcane_gold", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_BLOCK = ITEMS.register("arcane_gold_block", () -> new BlockItem(WizardsRebornBlocks.ARCANE_GOLD_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_ORE = ITEMS.register("arcane_gold_ore", () -> new BlockItem(WizardsRebornBlocks.ARCANE_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DEEPSLATE_ARCANE_GOLD_ORE = ITEMS.register("deepslate_arcane_gold_ore", () -> new BlockItem(WizardsRebornBlocks.DEEPSLATE_ARCANE_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> NETHER_ARCANE_GOLD_ORE = ITEMS.register("nether_arcane_gold_ore", () -> new BlockItem(WizardsRebornBlocks.NETHER_ARCANE_GOLD_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAW_ARCANE_GOLD_BLOCK = ITEMS.register("raw_arcane_gold_block", () -> new BlockItem(WizardsRebornBlocks.RAW_ARCANE_GOLD_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_GOLD_SWORD = ITEMS.register("arcane_gold_sword", () -> new ArcaneSwordItem(WizardsRebornItemTiers.ARCANE_GOLD, 3, -2.4f, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_COLD));
    public static final RegistryObject<Item> ARCANE_GOLD_PICKAXE = ITEMS.register("arcane_gold_pickaxe", () -> new ArcanePickaxeItem(WizardsRebornItemTiers.ARCANE_GOLD, 1, -2.8f, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_COLD));
    public static final RegistryObject<Item> ARCANE_GOLD_AXE = ITEMS.register("arcane_gold_axe", () -> new ArcaneAxeItem(WizardsRebornItemTiers.ARCANE_GOLD, 6, -3.1f, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_COLD));
    public static final RegistryObject<Item> ARCANE_GOLD_SHOVEL = ITEMS.register("arcane_gold_shovel", () -> new ArcaneShovelItem(WizardsRebornItemTiers.ARCANE_GOLD, 1.5f, -3f, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_COLD));
    public static final RegistryObject<Item> ARCANE_GOLD_HOE = ITEMS.register("arcane_gold_hoe", () -> new ArcaneHoeItem(WizardsRebornItemTiers.ARCANE_GOLD, -2, -1f, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_COLD));
    public static final RegistryObject<Item> ARCANE_GOLD_SCYTHE = ITEMS.register("arcane_gold_scythe", () -> new ArcaneScytheItem(WizardsRebornItemTiers.ARCANE_GOLD, 4, -2.8f, new Item.Properties(), 1, 1).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_COLD));

    public static final RegistryObject<Item> ARCANE_GOLD_HELMET = ITEMS.register("arcane_gold_helmet", () -> new ArcaneArmorItem(WizardsRebornArmorMaterials.ARCANE_GOLD, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_CHESTPLATE = ITEMS.register("arcane_gold_chestplate", () -> new ArcaneArmorItem(WizardsRebornArmorMaterials.ARCANE_GOLD, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_LEGGINGS = ITEMS.register("arcane_gold_leggings", () -> new ArcaneArmorItem(WizardsRebornArmorMaterials.ARCANE_GOLD, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_GOLD_BOOTS = ITEMS.register("arcane_gold_boots", () -> new ArcaneArmorItem(WizardsRebornArmorMaterials.ARCANE_GOLD, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> SARCON_INGOT = ITEMS.register("sarcon_ingot", () -> new SarconIngotItem(new Item.Properties()));
    public static final RegistryObject<Item> SARCON_NUGGET = ITEMS.register("sarcon_nugget", () -> new SarconIngotItem(new Item.Properties()));
    public static final RegistryObject<Item> SARCON_BLOCK = ITEMS.register("sarcon_block", () -> new BlockItem(WizardsRebornBlocks.SARCON_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> VILENIUM_INGOT = ITEMS.register("vilenium_ingot", () -> new VileniumIngotItem(new Item.Properties()));
    public static final RegistryObject<Item> VILENIUM_NUGGET = ITEMS.register("vilenium_nugget", () -> new VileniumIngotItem(new Item.Properties()));
    public static final RegistryObject<Item> VILENIUM_BLOCK = ITEMS.register("vilenium_block", () -> new BlockItem(WizardsRebornBlocks.VILENIUM_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANUM = ITEMS.register("arcanum", () -> new ArcanumItem(new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_DUST = ITEMS.register("arcanum_dust", () -> new ArcanumDustItem(new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_BLOCK = ITEMS.register("arcanum_block", () -> new BlockItem(WizardsRebornBlocks.ARCANUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_ORE = ITEMS.register("arcanum_ore", () -> new BlockItem(WizardsRebornBlocks.ARCANUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> DEEPSLATE_ARCANUM_ORE = ITEMS.register("deepslate_arcanum_ore", () -> new BlockItem(WizardsRebornBlocks.DEEPSLATE_ARCANUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_DUST_BLOCK = ITEMS.register("arcanum_dust_block", () -> new BlockItem(WizardsRebornBlocks.ARCANUM_DUST_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCACITE = ITEMS.register("arcacite", () -> new ArcaciteItem(new Item.Properties()));
    public static final RegistryObject<Item> ARCACITE_BLOCK = ITEMS.register("arcacite_block", () -> new BlockItem(WizardsRebornBlocks.ARCACITE_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> PRECISION_CRYSTAL = ITEMS.register("precision_crystal", () -> new PrecisionCrystalItem(new Item.Properties()));
    public static final RegistryObject<Item> PRECISION_CRYSTAL_BLOCK = ITEMS.register("precision_crystal_block", () -> new BlockItem(WizardsRebornBlocks.PRECISION_CRYSTAL_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> NETHER_SALT = ITEMS.register("nether_salt", () -> new NetherSaltItem(new Item.Properties(), 3200));
    public static final RegistryObject<Item> NETHER_SALT_BLOCK = ITEMS.register("nether_salt_block", () -> new FuelBlockItem(WizardsRebornBlocks.NETHER_SALT_BLOCK.get(), new Item.Properties(), 32000));
    public static final RegistryObject<Item> NETHER_SALT_ORE = ITEMS.register("nether_salt_ore", () -> new BlockItem(WizardsRebornBlocks.NETHER_SALT_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_WOOD_LOG = ITEMS.register("arcane_wood_log", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD = ITEMS.register("arcane_wood", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_ARCANE_WOOD_LOG = ITEMS.register("stripped_arcane_wood_log", () -> new BlockItem(WizardsRebornBlocks.STRIPPED_ARCANE_WOOD_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_ARCANE_WOOD = ITEMS.register("stripped_arcane_wood", () -> new BlockItem(WizardsRebornBlocks.STRIPPED_ARCANE_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS = ITEMS.register("arcane_wood_planks", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_STAIRS = ITEMS.register("arcane_wood_stairs", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_SLAB = ITEMS.register("arcane_wood_slab", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_BAULK = ITEMS.register("arcane_wood_baulk", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_CROSS_BAULK = ITEMS.register("arcane_wood_cross_baulk", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_ARCANE_WOOD_BAULK = ITEMS.register("stripped_arcane_wood_baulk", () -> new BlockItem(WizardsRebornBlocks.STRIPPED_ARCANE_WOOD_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_ARCANE_WOOD_CROSS_BAULK = ITEMS.register("stripped_arcane_wood_cross_baulk", () -> new BlockItem(WizardsRebornBlocks.STRIPPED_ARCANE_WOOD_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_BAULK = ITEMS.register("arcane_wood_planks_baulk", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_PLANKS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_PLANKS_CROSS_BAULK = ITEMS.register("arcane_wood_planks_cross_baulk", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_PLANKS_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_FENCE = ITEMS.register("arcane_wood_fence", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_FENCE_GATE = ITEMS.register("arcane_wood_fence_gate", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_DOOR = ITEMS.register("arcane_wood_door", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_TRAPDOOR = ITEMS.register("arcane_wood_trapdoor", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_PRESSURE_PLATE = ITEMS.register("arcane_wood_pressure_plate", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_BUTTON = ITEMS.register("arcane_wood_button", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_SIGN = ITEMS.register("arcane_wood_sign", () -> new SignItem(new Item.Properties().stacksTo(16), WizardsRebornBlocks.ARCANE_WOOD_SIGN.get(), WizardsRebornBlocks.ARCANE_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> ARCANE_WOOD_HANGING_SIGN = ITEMS.register("arcane_wood_hanging_sign", () -> new HangingSignItem(WizardsRebornBlocks.ARCANE_WOOD_HANGING_SIGN.get(), WizardsRebornBlocks.ARCANE_WOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> ARCANE_WOOD_BOAT = ITEMS.register("arcane_wood_boat", () -> new CustomBoatItem(new Item.Properties().stacksTo(1), WizardsRebornEntities.ARCANE_WOOD_BOAT));
    public static final RegistryObject<Item> ARCANE_WOOD_CHEST_BOAT = ITEMS.register("arcane_wood_chest_boat", () -> new CustomChestBoatItem(new Item.Properties().stacksTo(1), WizardsRebornEntities.ARCANE_WOOD_CHEST_BOAT));
    public static final RegistryObject<Item> ARCANE_WOOD_BRANCH = ITEMS.register("arcane_wood_branch", () -> new FuelItem(new Item.Properties(), 200));
    public static final RegistryObject<Item> ARCANE_WOOD_SWORD = ITEMS.register("arcane_wood_sword", () -> new ArcaneWoodSwordItem(WizardsRebornItemTiers.ARCANE_WOOD, 3, -2.4f, new Item.Properties(), ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_WOOD));
    public static final RegistryObject<Item> ARCANE_WOOD_PICKAXE = ITEMS.register("arcane_wood_pickaxe", () -> new ArcaneWoodPickaxeItem(WizardsRebornItemTiers.ARCANE_WOOD, 1, -2.8f, new Item.Properties(), ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_WOOD));
    public static final RegistryObject<Item> ARCANE_WOOD_AXE = ITEMS.register("arcane_wood_axe", () -> new ArcaneWoodAxeItem(WizardsRebornItemTiers.ARCANE_WOOD, 6, -3.1f, new Item.Properties(), ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_WOOD));
    public static final RegistryObject<Item> ARCANE_WOOD_SHOVEL = ITEMS.register("arcane_wood_shovel", () -> new ArcaneWoodShovelItem(WizardsRebornItemTiers.ARCANE_WOOD, 1.5f, -3f, new Item.Properties(), ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_WOOD));
    public static final RegistryObject<Item> ARCANE_WOOD_HOE = ITEMS.register("arcane_wood_hoe", () -> new ArcaneWoodHoeItem(WizardsRebornItemTiers.ARCANE_WOOD, -2, -1f, new Item.Properties(), ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_WOOD));
    public static final RegistryObject<Item> ARCANE_WOOD_SCYTHE = ITEMS.register("arcane_wood_scythe", () -> new ArcaneWoodScytheItem(WizardsRebornItemTiers.ARCANE_WOOD, 4, -2.8f, new Item.Properties(), 0.5f, 0, ARCANE_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_WOOD));
    public static final RegistryObject<Item> ARCANE_WOOD_MORTAR = ITEMS.register("arcane_wood_mortar", () -> new MortarItem(new Item.Properties().stacksTo(1), 400));
    public static final RegistryObject<Item> ARCANE_WOOD_LEAVES = ITEMS.register("arcane_wood_leaves", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_SAPLING = ITEMS.register("arcane_wood_sapling", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_SAPLING.get(), new Item.Properties()));

    public static final RegistryObject<Item> INNOCENT_WOOD_LOG = ITEMS.register("innocent_wood_log", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD = ITEMS.register("innocent_wood", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_INNOCENT_WOOD_LOG = ITEMS.register("stripped_innocent_wood_log", () -> new BlockItem(WizardsRebornBlocks.STRIPPED_INNOCENT_WOOD_LOG.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_INNOCENT_WOOD = ITEMS.register("stripped_innocent_wood", () -> new BlockItem(WizardsRebornBlocks.STRIPPED_INNOCENT_WOOD.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_PLANKS = ITEMS.register("innocent_wood_planks", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_STAIRS = ITEMS.register("innocent_wood_stairs", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_SLAB = ITEMS.register("innocent_wood_slab", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_BAULK = ITEMS.register("innocent_wood_baulk", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_CROSS_BAULK = ITEMS.register("innocent_wood_cross_baulk", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_INNOCENT_WOOD_BAULK = ITEMS.register("stripped_innocent_wood_baulk", () -> new BlockItem(WizardsRebornBlocks.STRIPPED_INNOCENT_WOOD_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> STRIPPED_INNOCENT_WOOD_CROSS_BAULK = ITEMS.register("stripped_innocent_wood_cross_baulk", () -> new BlockItem(WizardsRebornBlocks.STRIPPED_INNOCENT_WOOD_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_PLANKS_BAULK = ITEMS.register("innocent_wood_planks_baulk", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_PLANKS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_PLANKS_CROSS_BAULK = ITEMS.register("innocent_wood_planks_cross_baulk", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_PLANKS_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_FENCE = ITEMS.register("innocent_wood_fence", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_FENCE_GATE = ITEMS.register("innocent_wood_fence_gate", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_DOOR = ITEMS.register("innocent_wood_door", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_TRAPDOOR = ITEMS.register("innocent_wood_trapdoor", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_PRESSURE_PLATE = ITEMS.register("innocent_wood_pressure_plate", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_BUTTON = ITEMS.register("innocent_wood_button", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_SIGN = ITEMS.register("innocent_wood_sign", () -> new SignItem(new Item.Properties().stacksTo(16), WizardsRebornBlocks.INNOCENT_WOOD_SIGN.get(), WizardsRebornBlocks.INNOCENT_WOOD_WALL_SIGN.get()));
    public static final RegistryObject<Item> INNOCENT_WOOD_HANGING_SIGN = ITEMS.register("innocent_wood_hanging_sign", () -> new HangingSignItem(WizardsRebornBlocks.INNOCENT_WOOD_HANGING_SIGN.get(), WizardsRebornBlocks.INNOCENT_WOOD_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> INNOCENT_WOOD_BOAT = ITEMS.register("innocent_wood_boat", () -> new CustomBoatItem(new Item.Properties().stacksTo(1), WizardsRebornEntities.INNOCENT_WOOD_BOAT));
    public static final RegistryObject<Item> INNOCENT_WOOD_CHEST_BOAT = ITEMS.register("innocent_wood_chest_boat", () -> new CustomChestBoatItem(new Item.Properties().stacksTo(1), WizardsRebornEntities.INNOCENT_WOOD_CHEST_BOAT));
    public static final RegistryObject<Item> INNOCENT_WOOD_BRANCH = ITEMS.register("innocent_wood_branch", () -> new FuelItem(new Item.Properties(), 200));
    public static final RegistryObject<Item> INNOCENT_WOOD_SWORD = ITEMS.register("innocent_wood_sword", () -> new InnocentWoodSwordItem(WizardsRebornItemTiers.INNOCENT_WOOD, 3, -2.4f, new Item.Properties(), INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.INNOCENT_WOOD));
    public static final RegistryObject<Item> INNOCENT_WOOD_PICKAXE = ITEMS.register("innocent_wood_pickaxe", () -> new InnocentWoodPickaxeItem(WizardsRebornItemTiers.INNOCENT_WOOD, 1, -2.8f, new Item.Properties(), INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.INNOCENT_WOOD));
    public static final RegistryObject<Item> INNOCENT_WOOD_AXE = ITEMS.register("innocent_wood_axe", () -> new InnocentWoodAxeItem(WizardsRebornItemTiers.INNOCENT_WOOD, 6, -3.1f, new Item.Properties(), INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.INNOCENT_WOOD));
    public static final RegistryObject<Item> INNOCENT_WOOD_SHOVEL = ITEMS.register("innocent_wood_shovel", () -> new InnocentWoodShovelItem(WizardsRebornItemTiers.INNOCENT_WOOD, 1.5f, -3f, new Item.Properties(), INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.INNOCENT_WOOD));
    public static final RegistryObject<Item> INNOCENT_WOOD_HOE = ITEMS.register("innocent_wood_hoe", () -> new InnocentWoodHoeItem(WizardsRebornItemTiers.INNOCENT_WOOD, -2, -1f, new Item.Properties(), INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.INNOCENT_WOOD));
    public static final RegistryObject<Item> INNOCENT_WOOD_SCYTHE = ITEMS.register("innocent_wood_scythe", () -> new InnocentWoodScytheItem(WizardsRebornItemTiers.INNOCENT_WOOD, 4, -2.8f, new Item.Properties(), 0.5f, 0, INNOCENT_WOOD_BRANCH.get()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.INNOCENT_WOOD));
    public static final RegistryObject<Item> INNOCENT_WOOD_MORTAR = ITEMS.register("innocent_wood_mortar", () -> new MortarItem(new Item.Properties().stacksTo(1), 400));
    public static final RegistryObject<Item> INNOCENT_WOOD_LEAVES = ITEMS.register("innocent_wood_leaves", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_LEAVES.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_SAPLING = ITEMS.register("innocent_wood_sapling", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> PETALS_OF_INNOCENCE = ITEMS.register("petals_of_innocence", () -> new BlockItem(WizardsRebornBlocks.PETALS_OF_INNOCENCE.get(), new Item.Properties()));

    public static final RegistryObject<Item> WISESTONE = ITEMS.register("wisestone", () -> new BlockItem(WizardsRebornBlocks.WISESTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_STAIRS = ITEMS.register("wisestone_stairs", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_SLAB = ITEMS.register("wisestone_slab", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_WALL = ITEMS.register("wisestone_wall", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_WISESTONE = ITEMS.register("polished_wisestone", () -> new BlockItem(WizardsRebornBlocks.POLISHED_WISESTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_WISESTONE_STAIRS = ITEMS.register("polished_wisestone_stairs", () -> new BlockItem(WizardsRebornBlocks.POLISHED_WISESTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_WISESTONE_SLAB = ITEMS.register("polished_wisestone_slab", () -> new BlockItem(WizardsRebornBlocks.POLISHED_WISESTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_WISESTONE_WALL = ITEMS.register("polished_wisestone_wall", () -> new BlockItem(WizardsRebornBlocks.POLISHED_WISESTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_BRICKS = ITEMS.register("wisestone_bricks", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_BRICKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_BRICKS_STAIRS = ITEMS.register("wisestone_bricks_stairs", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_BRICKS_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_BRICKS_SLAB = ITEMS.register("wisestone_bricks_slab", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_BRICKS_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_BRICKS_WALL = ITEMS.register("wisestone_bricks_wall", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_BRICKS_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_TILE = ITEMS.register("wisestone_tile", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_TILE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_TILE_STAIRS = ITEMS.register("wisestone_tile_stairs", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_TILE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_TILE_SLAB = ITEMS.register("wisestone_tile_slab", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_TILE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_TILE_WALL = ITEMS.register("wisestone_tile_wall", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_TILE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_WISESTONE = ITEMS.register("chiseled_wisestone", () -> new BlockItem(WizardsRebornBlocks.CHISELED_WISESTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_WISESTONE_STAIRS = ITEMS.register("chiseled_wisestone_stairs", () -> new BlockItem(WizardsRebornBlocks.CHISELED_WISESTONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_WISESTONE_SLAB = ITEMS.register("chiseled_wisestone_slab", () -> new BlockItem(WizardsRebornBlocks.CHISELED_WISESTONE_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> CHISELED_WISESTONE_WALL = ITEMS.register("chiseled_wisestone_wall", () -> new BlockItem(WizardsRebornBlocks.CHISELED_WISESTONE_WALL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_PILLAR = ITEMS.register("wisestone_pillar", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_PILLAR.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_WISESTONE_PRESSURE_PLATE = ITEMS.register("polished_wisestone_pressure_plate", () -> new BlockItem(WizardsRebornBlocks.POLISHED_WISESTONE_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> POLISHED_WISESTONE_BUTTON = ITEMS.register("polished_wisestone_button", () -> new BlockItem(WizardsRebornBlocks.POLISHED_WISESTONE_BUTTON.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_LINEN_SEEDS = ITEMS.register("arcane_linen_seeds", () -> new ItemNameBlockItem(WizardsRebornBlocks.ARCANE_LINEN.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_LINEN = ITEMS.register("arcane_linen", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_LINEN_HAY = ITEMS.register("arcane_linen_hay", () -> new BlockItem(WizardsRebornBlocks.ARCANE_LINEN_HAY.get(), new Item.Properties()));

    public static final RegistryObject<Item> MOR = ITEMS.register("mor", () -> new MorItem(WizardsRebornBlocks.MOR.get(), new Item.Properties().food(WizardsRebornFoods.MOR), 1500, 1800));
    public static final RegistryObject<Item> MOR_BLOCK = ITEMS.register("mor_block", () -> new BlockItem(WizardsRebornBlocks.MOR_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ELDER_MOR = ITEMS.register("elder_mor", () -> new MorItem(WizardsRebornBlocks.ELDER_MOR.get(), new Item.Properties().food(WizardsRebornFoods.MOR), 1700, 2100));
    public static final RegistryObject<Item> ELDER_MOR_BLOCK = ITEMS.register("elder_mor_block", () -> new BlockItem(WizardsRebornBlocks.ELDER_MOR_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> PITCHER_TURNIP = ITEMS.register("pitcher_turnip", () -> new BlockItem(WizardsRebornBlocks.PITCHER_TURNIP.get(), new Item.Properties().food(WizardsRebornFoods.PITCHER_TURNIP)));
    public static final RegistryObject<Item> PITCHER_TURNIP_BLOCK = ITEMS.register("pitcher_turnip_block", () -> new BlockItem(WizardsRebornBlocks.PITCHER_TURNIP_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHINY_CLOVER_SEED = ITEMS.register("shiny_clover_seed", () -> new ItemNameBlockItem(WizardsRebornBlocks.SHINY_CLOVER_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> SHINY_CLOVER = ITEMS.register("shiny_clover", () -> new BlockItem(WizardsRebornBlocks.SHINY_CLOVER.get(), new Item.Properties()));
    public static final RegistryObject<Item> UNDERGROUND_GRAPE_VINE = ITEMS.register("underground_grape_vine", () -> new ItemNameBlockItem(WizardsRebornBlocks.UNDERGROUND_GRAPE_VINES.get(), new Item.Properties()));
    public static final RegistryObject<Item> UNDERGROUND_GRAPE = ITEMS.register("underground_grape", () -> new Item(new Item.Properties().food(WizardsRebornFoods.UNDERGROUND_GRAPE)));

    public static final RegistryObject<Item> CORK_BAMBOO_SEED = ITEMS.register("cork_bamboo_seed", () -> new ItemNameBlockItem(WizardsRebornBlocks.CORK_BAMBOO_SAPLING.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO = ITEMS.register("cork_bamboo", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_BLOCK = ITEMS.register("cork_bamboo_block", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_PLANKS = ITEMS.register("cork_bamboo_planks", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CHISELED_PLANKS = ITEMS.register("cork_bamboo_chiseled_planks", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_CHISELED_PLANKS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_STAIRS = ITEMS.register("cork_bamboo_stairs", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CHISELED_STAIRS = ITEMS.register("cork_bamboo_chiseled_stairs", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_CHISELED_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_SLAB = ITEMS.register("cork_bamboo_slab", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CHISELED_SLAB = ITEMS.register("cork_bamboo_chiseled_slab", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_CHISELED_SLAB.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_BAULK = ITEMS.register("cork_bamboo_baulk", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CROSS_BAULK = ITEMS.register("cork_bamboo_cross_baulk", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_PLANKS_BAULK = ITEMS.register("cork_bamboo_planks_baulk", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_PLANKS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_PLANKS_CROSS_BAULK = ITEMS.register("cork_bamboo_planks_cross_baulk", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_PLANKS_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CHISELED_PLANKS_BAULK = ITEMS.register("cork_bamboo_chiseled_planks_baulk", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_CHISELED_PLANKS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK = ITEMS.register("cork_bamboo_chiseled_planks_cross_baulk", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_CHISELED_PLANKS_CROSS_BAULK.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_FENCE = ITEMS.register("cork_bamboo_fence", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_FENCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_FENCE_GATE = ITEMS.register("cork_bamboo_fence_gate", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_FENCE_GATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_DOOR = ITEMS.register("cork_bamboo_door", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_DOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_TRAPDOOR = ITEMS.register("cork_bamboo_trapdoor", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_TRAPDOOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_PRESSURE_PLATE = ITEMS.register("cork_bamboo_pressure_plate", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_PRESSURE_PLATE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_BUTTON = ITEMS.register("cork_bamboo_button", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_SIGN = ITEMS.register("cork_bamboo_sign", () -> new SignItem(new Item.Properties().stacksTo(16), WizardsRebornBlocks.CORK_BAMBOO_SIGN.get(), WizardsRebornBlocks.CORK_BAMBOO_WALL_SIGN.get()));
    public static final RegistryObject<Item> CORK_BAMBOO_HANGING_SIGN = ITEMS.register("cork_bamboo_hanging_sign", () -> new HangingSignItem(WizardsRebornBlocks.CORK_BAMBOO_HANGING_SIGN.get(), WizardsRebornBlocks.CORK_BAMBOO_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> CORK_BAMBOO_RAFT = ITEMS.register("cork_bamboo_raft", () -> new CustomBoatItem(new Item.Properties().stacksTo(1), WizardsRebornEntities.CORK_BAMBOO_RAFT));
    public static final RegistryObject<Item> CORK_BAMBOO_CHEST_RAFT = ITEMS.register("cork_bamboo_chest_raft", () -> new CustomChestBoatItem(new Item.Properties().stacksTo(1), WizardsRebornEntities.CORK_BAMBOO_CHEST_RAFT));

    public static final RegistryObject<Item> PETALS = ITEMS.register("petals", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLOWER_FERTILIZER = ITEMS.register("flower_fertilizer", () -> new FlowerFertilizerItem(new Item.Properties()));
    public static final RegistryObject<Item> BUNCH_OF_THINGS = ITEMS.register("bunch_of_things", () -> new BunchOfThingsItem(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_BROWN_MUSHROOM = ITEMS.register("ground_brown_mushroom", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_RED_MUSHROOM = ITEMS.register("ground_red_mushroom", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_CRIMSON_FUNGUS = ITEMS.register("ground_crimson_fungus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_WARPED_FUNGUS = ITEMS.register("ground_warped_fungus", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_MOR = ITEMS.register("ground_mor", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GROUND_ELDER_MOR = ITEMS.register("ground_elder_mor", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ARCANUM_SEED = ITEMS.register("arcanum_seed", () -> new BlockItem(WizardsRebornBlocks.ARCANUM_SEED.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANUM_GROWTH = ITEMS.register("arcanum_growth", () -> new BlockItem(WizardsRebornBlocks.ARCANUM_GROWTH.get(), new Item.Properties()));

    public static final RegistryObject<Item> EARTH_CRYSTAL_SEED = ITEMS.register("earth_crystal_seed", () -> new BlockItem(WizardsRebornBlocks.EARTH_CRYSTAL_SEED.get(), new Item.Properties()));
    public static final RegistryObject<Item> WATER_CRYSTAL_SEED = ITEMS.register("water_crystal_seed", () -> new BlockItem(WizardsRebornBlocks.WATER_CRYSTAL_SEED.get(), new Item.Properties()));
    public static final RegistryObject<Item> AIR_CRYSTAL_SEED = ITEMS.register("air_crystal_seed", () -> new BlockItem(WizardsRebornBlocks.AIR_CRYSTAL_SEED.get(), new Item.Properties()));
    public static final RegistryObject<Item> FIRE_CRYSTAL_SEED = ITEMS.register("fire_crystal_seed", () -> new BlockItem(WizardsRebornBlocks.FIRE_CRYSTAL_SEED.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOID_CRYSTAL_SEED = ITEMS.register("void_crystal_seed", () -> new BlockItem(WizardsRebornBlocks.VOID_CRYSTAL_SEED.get(), new Item.Properties()));

    public static final RegistryObject<Item> EARTH_CRYSTAL_GROWTH = ITEMS.register("earth_crystal_growth", () -> new BlockItem(WizardsRebornBlocks.EARTH_CRYSTAL_GROWTH.get(), new Item.Properties()));
    public static final RegistryObject<Item> WATER_CRYSTAL_GROWTH = ITEMS.register("water_crystal_growth", () -> new BlockItem(WizardsRebornBlocks.WATER_CRYSTAL_GROWTH.get(), new Item.Properties()));
    public static final RegistryObject<Item> AIR_CRYSTAL_GROWTH = ITEMS.register("air_crystal_growth", () -> new BlockItem(WizardsRebornBlocks.AIR_CRYSTAL_GROWTH.get(), new Item.Properties()));
    public static final RegistryObject<Item> FIRE_CRYSTAL_GROWTH = ITEMS.register("fire_crystal_growth", () -> new BlockItem(WizardsRebornBlocks.FIRE_CRYSTAL_GROWTH.get(), new Item.Properties()));
    public static final RegistryObject<Item> VOID_CRYSTAL_GROWTH = ITEMS.register("void_crystal_growth", () -> new BlockItem(WizardsRebornBlocks.VOID_CRYSTAL_GROWTH.get(), new Item.Properties()));

    public static final RegistryObject<Item> FRACTURED_EARTH_CRYSTAL = ITEMS.register("fractured_earth_crystal", () -> new FracturedCrystalItem(WizardsRebornCrystals.EARTH, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FRACTURED_WATER_CRYSTAL = ITEMS.register("fractured_water_crystal", () -> new FracturedCrystalItem(WizardsRebornCrystals.WATER, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FRACTURED_AIR_CRYSTAL = ITEMS.register("fractured_air_crystal", () -> new FracturedCrystalItem(WizardsRebornCrystals.AIR, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FRACTURED_FIRE_CRYSTAL = ITEMS.register("fractured_fire_crystal", () -> new FracturedCrystalItem(WizardsRebornCrystals.FIRE, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FRACTURED_VOID_CRYSTAL = ITEMS.register("fractured_void_crystal", () -> new FracturedCrystalItem(WizardsRebornCrystals.VOID, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> EARTH_CRYSTAL = ITEMS.register("earth_crystal", () -> new CrystalItem(WizardsRebornBlocks.EARTH_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WATER_CRYSTAL = ITEMS.register("water_crystal", () -> new CrystalItem(WizardsRebornBlocks.WATER_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> AIR_CRYSTAL = ITEMS.register("air_crystal", () -> new CrystalItem(WizardsRebornBlocks.AIR_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FIRE_CRYSTAL = ITEMS.register("fire_crystal", () -> new CrystalItem(WizardsRebornBlocks.FIRE_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> VOID_CRYSTAL = ITEMS.register("void_crystal", () -> new CrystalItem(WizardsRebornBlocks.VOID_CRYSTAL.get(), new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> FACETED_EARTH_CRYSTAL = ITEMS.register("faceted_earth_crystal", () -> new CrystalItem(WizardsRebornBlocks.FACETED_EARTH_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FACETED_WATER_CRYSTAL = ITEMS.register("faceted_water_crystal", () -> new CrystalItem(WizardsRebornBlocks.FACETED_WATER_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FACETED_AIR_CRYSTAL = ITEMS.register("faceted_air_crystal", () -> new CrystalItem(WizardsRebornBlocks.FACETED_AIR_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FACETED_FIRE_CRYSTAL = ITEMS.register("faceted_fire_crystal", () -> new CrystalItem(WizardsRebornBlocks.FACETED_FIRE_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> FACETED_VOID_CRYSTAL = ITEMS.register("faceted_void_crystal", () -> new CrystalItem(WizardsRebornBlocks.FACETED_VOID_CRYSTAL.get(), new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ADVANCED_EARTH_CRYSTAL = ITEMS.register("advanced_earth_crystal", () -> new CrystalItem(WizardsRebornBlocks.ADVANCED_EARTH_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ADVANCED_WATER_CRYSTAL = ITEMS.register("advanced_water_crystal", () -> new CrystalItem(WizardsRebornBlocks.ADVANCED_WATER_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ADVANCED_AIR_CRYSTAL = ITEMS.register("advanced_air_crystal", () -> new CrystalItem(WizardsRebornBlocks.ADVANCED_AIR_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ADVANCED_FIRE_CRYSTAL = ITEMS.register("advanced_fire_crystal", () -> new CrystalItem(WizardsRebornBlocks.ADVANCED_FIRE_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ADVANCED_VOID_CRYSTAL = ITEMS.register("advanced_void_crystal", () -> new CrystalItem(WizardsRebornBlocks.ADVANCED_VOID_CRYSTAL.get(), new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MASTERFUL_EARTH_CRYSTAL = ITEMS.register("masterful_earth_crystal", () -> new CrystalItem(WizardsRebornBlocks.MASTERFUL_EARTH_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MASTERFUL_WATER_CRYSTAL = ITEMS.register("masterful_water_crystal", () -> new CrystalItem(WizardsRebornBlocks.MASTERFUL_WATER_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MASTERFUL_AIR_CRYSTAL = ITEMS.register("masterful_air_crystal", () -> new CrystalItem(WizardsRebornBlocks.MASTERFUL_AIR_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MASTERFUL_FIRE_CRYSTAL = ITEMS.register("masterful_fire_crystal", () -> new CrystalItem(WizardsRebornBlocks.MASTERFUL_FIRE_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> MASTERFUL_VOID_CRYSTAL = ITEMS.register("masterful_void_crystal", () -> new CrystalItem(WizardsRebornBlocks.MASTERFUL_VOID_CRYSTAL.get(), new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> PURE_EARTH_CRYSTAL = ITEMS.register("pure_earth_crystal", () -> new CrystalItem(WizardsRebornBlocks.PURE_EARTH_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PURE_WATER_CRYSTAL = ITEMS.register("pure_water_crystal", () -> new CrystalItem(WizardsRebornBlocks.PURE_WATER_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PURE_AIR_CRYSTAL = ITEMS.register("pure_air_crystal", () -> new CrystalItem(WizardsRebornBlocks.PURE_AIR_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PURE_FIRE_CRYSTAL = ITEMS.register("pure_fire_crystal", () -> new CrystalItem(WizardsRebornBlocks.PURE_FIRE_CRYSTAL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> PURE_VOID_CRYSTAL = ITEMS.register("pure_void_crystal", () -> new CrystalItem(WizardsRebornBlocks.PURE_VOID_CRYSTAL.get(), new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> WHITE_ARCANE_LUMOS = ITEMS.register("white_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.WHITE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_GRAY_ARCANE_LUMOS = ITEMS.register("light_gray_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.LIGHT_GRAY_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_ARCANE_LUMOS = ITEMS.register("gray_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.GRAY_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_ARCANE_LUMOS = ITEMS.register("black_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.BLACK_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BROWN_ARCANE_LUMOS = ITEMS.register("brown_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.BROWN_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_ARCANE_LUMOS = ITEMS.register("red_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.RED_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_ARCANE_LUMOS = ITEMS.register("orange_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.ORANGE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_ARCANE_LUMOS = ITEMS.register("yellow_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.YELLOW_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIME_ARCANE_LUMOS = ITEMS.register("lime_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.LIME_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_ARCANE_LUMOS = ITEMS.register("green_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.GREEN_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CYAN_ARCANE_LUMOS = ITEMS.register("cyan_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.CYAN_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BLUE_ARCANE_LUMOS = ITEMS.register("light_blue_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.LIGHT_BLUE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_ARCANE_LUMOS = ITEMS.register("blue_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.BLUE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_ARCANE_LUMOS = ITEMS.register("purple_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.PURPLE_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAGENTA_ARCANE_LUMOS = ITEMS.register("magenta_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.MAGENTA_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_ARCANE_LUMOS = ITEMS.register("pink_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.PINK_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAINBOW_ARCANE_LUMOS = ITEMS.register("rainbow_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.RAINBOW_ARCANE_LUMOS.get(), new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_ARCANE_LUMOS = ITEMS.register("cosmic_arcane_lumos", () -> new ArcaneLumosItem(WizardsRebornBlocks.COSMIC_ARCANE_LUMOS.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_PEDESTAL = ITEMS.register("arcane_pedestal", () -> new BlockItem(WizardsRebornBlocks.ARCANE_PEDESTAL.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_ALTAR = ITEMS.register("wissen_altar", () -> new BlockItem(WizardsRebornBlocks.WISSEN_ALTAR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_TRANSLATOR = ITEMS.register("wissen_translator", () -> new BlockItem(WizardsRebornBlocks.WISSEN_TRANSLATOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_CRYSTALLIZER = ITEMS.register("wissen_crystallizer", () -> new BlockItem(WizardsRebornBlocks.WISSEN_CRYSTALLIZER.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WORKBENCH = ITEMS.register("arcane_workbench", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WORKBENCH.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_CELL = ITEMS.register("wissen_cell", () -> new WissenStorageBaseItem(WizardsRebornBlocks.WISSEN_CELL.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> JEWELER_TABLE = ITEMS.register("jeweler_table", () -> new BlockItem(WizardsRebornBlocks.JEWELER_TABLE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALTAR_OF_DROUGHT = ITEMS.register("altar_of_drought", () -> new BlockItem(WizardsRebornBlocks.ALTAR_OF_DROUGHT.get(), new Item.Properties()));
    public static final RegistryObject<Item> TOTEM_BASE = ITEMS.register("totem_base", () -> new BlockItem(WizardsRebornBlocks.TOTEM_BASE.get(), new Item.Properties()));
    public static final RegistryObject<Item> TOTEM_OF_FLAMES = ITEMS.register("totem_of_flames", () -> new BlockItem(WizardsRebornBlocks.TOTEM_OF_FLAMES.get(), new Item.Properties()));
    public static final RegistryObject<Item> EXPERIENCE_TOTEM = ITEMS.register("experience_totem", () -> new BlockItem(WizardsRebornBlocks.EXPERIENCE_TOTEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> TOTEM_OF_EXPERIENCE_ABSORPTION = ITEMS.register("totem_of_experience_absorption", () -> new BlockItem(WizardsRebornBlocks.TOTEM_OF_EXPERIENCE_ABSORPTION.get(), new Item.Properties()));
    public static final RegistryObject<Item> TOTEM_OF_DISENCHANT = ITEMS.register("totem_of_disenchant", () -> new BlockItem(WizardsRebornBlocks.TOTEM_OF_DISENCHANT.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_ITERATOR = ITEMS.register("arcane_iterator", () -> new BlockItem(WizardsRebornBlocks.ARCANE_ITERATOR.get(), new Item.Properties()));

    public static final RegistryObject<Item> WISESTONE_PEDESTAL = ITEMS.register("wisestone_pedestal", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_PEDESTAL.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLUID_PIPE = ITEMS.register("fluid_pipe", () -> new BlockItem(WizardsRebornBlocks.FLUID_PIPE.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLUID_EXTRACTOR = ITEMS.register("fluid_extractor", () -> new BlockItem(WizardsRebornBlocks.FLUID_EXTRACTOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> STEAM_PIPE = ITEMS.register("steam_pipe", () -> new BlockItem(WizardsRebornBlocks.STEAM_PIPE.get(), new Item.Properties()));
    public static final RegistryObject<Item> STEAM_EXTRACTOR = ITEMS.register("steam_extractor", () -> new BlockItem(WizardsRebornBlocks.STEAM_EXTRACTOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_FURNACE = ITEMS.register("alchemy_furnace", () -> new BlockItem(WizardsRebornBlocks.ALCHEMY_FURNACE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ORBITAL_FLUID_RETAINER = ITEMS.register("orbital_fluid_retainer", () -> new FluidStorageBaseItem(WizardsRebornBlocks.ORBITAL_FLUID_RETAINER.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> STEAM_THERMAL_STORAGE = ITEMS.register("steam_thermal_storage", () -> new SteamStorageBaseItem(WizardsRebornBlocks.STEAM_THERMAL_STORAGE.get(), new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ALCHEMY_MACHINE = ITEMS.register("alchemy_machine", () -> new BlockItem(WizardsRebornBlocks.ALCHEMY_MACHINE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_BOILER = ITEMS.register("alchemy_boiler", () -> new BlockItem(WizardsRebornBlocks.ALCHEMY_BOILER.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_CENSER = ITEMS.register("arcane_censer", () -> new BlockItem(WizardsRebornBlocks.ARCANE_CENSER.get(), new Item.Properties()));

    public static final RegistryObject<Item> CORK_BAMBOO_PEDESTAL = ITEMS.register("cork_bamboo_pedestal", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_PEDESTAL.get(), new Item.Properties()));

    public static final RegistryObject<Item> LIGHT_EMITTER = ITEMS.register("light_emitter", () -> new WizardsRebornRenderBlockItem(WizardsRebornBlocks.LIGHT_EMITTER.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_TRANSFER_LENS = ITEMS.register("light_transfer_lens", () -> new WizardsRebornRenderBlockItem(WizardsRebornBlocks.LIGHT_TRANSFER_LENS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RUNIC_PEDESTAL = ITEMS.register("runic_pedestal", () -> new BlockItem(WizardsRebornBlocks.RUNIC_PEDESTAL.get(), new Item.Properties()));

    public static final RegistryObject<Item> ENGRAVED_WISESTONE = ITEMS.register("engraved_wisestone", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_LUNAM = ITEMS.register("engraved_wisestone_lunam", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_LUNAM.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_VITA = ITEMS.register("engraved_wisestone_vita", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_VITA.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_SOLEM = ITEMS.register("engraved_wisestone_solem", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_SOLEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_MORS = ITEMS.register("engraved_wisestone_mors", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_MORS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_MIRACULUM = ITEMS.register("engraved_wisestone_miraculum", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_MIRACULUM.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_TEMPUS = ITEMS.register("engraved_wisestone_tempus", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_TEMPUS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_STATERA = ITEMS.register("engraved_wisestone_statera", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_STATERA.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_ECLIPSIS = ITEMS.register("engraved_wisestone_eclipsis", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_ECLIPSIS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_SICCITAS = ITEMS.register("engraved_wisestone_siccitas", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_SICCITAS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_SOLSTITIUM = ITEMS.register("engraved_wisestone_solstitium", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_SOLSTITIUM.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_FAMES = ITEMS.register("engraved_wisestone_fames", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_FAMES.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_RENAISSANCE = ITEMS.register("engraved_wisestone_renaissance", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_RENAISSANCE.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_BELLUM = ITEMS.register("engraved_wisestone_bellum", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_BELLUM.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_LUX = ITEMS.register("engraved_wisestone_lux", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_LUX.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_KARA = ITEMS.register("engraved_wisestone_kara", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_KARA.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_DEGRADATIO = ITEMS.register("engraved_wisestone_degradatio", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_DEGRADATIO.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_PRAEDICTIONEM = ITEMS.register("engraved_wisestone_praedictionem", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_PRAEDICTIONEM.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_EVOLUTIONIS = ITEMS.register("engraved_wisestone_evolutionis", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_EVOLUTIONIS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_TENEBRIS = ITEMS.register("engraved_wisestone_tenebris", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_TENEBRIS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENGRAVED_WISESTONE_UNIVERSUM = ITEMS.register("engraved_wisestone_universum", () -> new EngravedWisestoneItem(WizardsRebornBlocks.ENGRAVED_WISESTONE_UNIVERSUM.get(), new Item.Properties()));

    public static final RegistryObject<Item> INNOCENT_PEDESTAL = ITEMS.register("innocent_pedestal", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_PEDESTAL.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_LEVER = ITEMS.register("arcane_lever", () -> new BlockItem(WizardsRebornBlocks.ARCANE_LEVER.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_HOPPER = ITEMS.register("arcane_hopper", () -> new BlockItem(WizardsRebornBlocks.ARCANE_HOPPER.get(), new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_SENSOR = ITEMS.register("redstone_sensor", () -> new BlockItem(WizardsRebornBlocks.REDSTONE_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_SENSOR = ITEMS.register("wissen_sensor", () -> new BlockItem(WizardsRebornBlocks.WISSEN_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> COOLDOWN_SENSOR = ITEMS.register("cooldown_sensor", () -> new BlockItem(WizardsRebornBlocks.COOLDOWN_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> EXPERIENCE_SENSOR = ITEMS.register("experience_sensor", () -> new BlockItem(WizardsRebornBlocks.EXPERIENCE_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_SENSOR = ITEMS.register("light_sensor", () -> new BlockItem(WizardsRebornBlocks.LIGHT_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> HEAT_SENSOR = ITEMS.register("heat_sensor", () -> new BlockItem(WizardsRebornBlocks.HEAT_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> FLUID_SENSOR = ITEMS.register("fluid_sensor", () -> new BlockItem(WizardsRebornBlocks.FLUID_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> STEAM_SENSOR = ITEMS.register("steam_sensor", () -> new BlockItem(WizardsRebornBlocks.STEAM_SENSOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISSEN_ACTIVATOR = ITEMS.register("wissen_activator", () -> new BlockItem(WizardsRebornBlocks.WISSEN_ACTIVATOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> ITEM_SORTER = ITEMS.register("item_sorter", () -> new BlockItem(WizardsRebornBlocks.ITEM_SORTER.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_WOOD_FRAME = ITEMS.register("arcane_wood_frame", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_GLASS_FRAME = ITEMS.register("arcane_wood_glass_frame", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_GLASS_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_CASING = ITEMS.register("arcane_wood_casing", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_WISSEN_CASING = ITEMS.register("arcane_wood_wissen_casing", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_WISSEN_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_LIGHT_CASING = ITEMS.register("arcane_wood_light_casing", () -> new WizardsRebornRenderBlockItem(WizardsRebornBlocks.ARCANE_WOOD_LIGHT_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_FLUID_CASING = ITEMS.register("arcane_wood_fluid_casing", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_FLUID_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_WOOD_STEAM_CASING = ITEMS.register("arcane_wood_steam_casing", () -> new BlockItem(WizardsRebornBlocks.ARCANE_WOOD_STEAM_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_FRAME = ITEMS.register("innocent_wood_frame", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_GLASS_FRAME = ITEMS.register("innocent_wood_glass_frame", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_GLASS_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_CASING = ITEMS.register("innocent_wood_casing", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_WISSEN_CASING = ITEMS.register("innocent_wood_wissen_casing", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_WISSEN_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_LIGHT_CASING = ITEMS.register("innocent_wood_light_casing", () -> new WizardsRebornRenderBlockItem(WizardsRebornBlocks.INNOCENT_WOOD_LIGHT_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_FLUID_CASING = ITEMS.register("innocent_wood_fluid_casing", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_FLUID_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_STEAM_CASING = ITEMS.register("innocent_wood_steam_casing", () -> new BlockItem(WizardsRebornBlocks.INNOCENT_WOOD_STEAM_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_FRAME = ITEMS.register("cork_bamboo_frame", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_GLASS_FRAME = ITEMS.register("cork_bamboo_glass_frame", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_GLASS_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_CASING = ITEMS.register("cork_bamboo_casing", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_WISSEN_CASING = ITEMS.register("cork_bamboo_wissen_casing", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_WISSEN_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_LIGHT_CASING = ITEMS.register("cork_bamboo_light_casing", () -> new WizardsRebornRenderBlockItem(WizardsRebornBlocks.CORK_BAMBOO_LIGHT_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_FLUID_CASING = ITEMS.register("cork_bamboo_fluid_casing", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_FLUID_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_STEAM_CASING = ITEMS.register("cork_bamboo_steam_casing", () -> new BlockItem(WizardsRebornBlocks.CORK_BAMBOO_STEAM_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_FRAME = ITEMS.register("wisestone_frame", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_GLASS_FRAME = ITEMS.register("wisestone_glass_frame", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_GLASS_FRAME.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_CASING = ITEMS.register("wisestone_casing", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_WISSEN_CASING = ITEMS.register("wisestone_wissen_casing", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_WISSEN_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_LIGHT_CASING = ITEMS.register("wisestone_light_casing", () -> new WizardsRebornRenderBlockItem(WizardsRebornBlocks.WISESTONE_LIGHT_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_FLUID_CASING = ITEMS.register("wisestone_fluid_casing", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_FLUID_CASING.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_STEAM_CASING = ITEMS.register("wisestone_steam_casing", () -> new BlockItem(WizardsRebornBlocks.WISESTONE_STEAM_CASING.get(), new Item.Properties()));

    public static final RegistryObject<Item> CREATIVE_WISSEN_STORAGE = ITEMS.register("creative_wissen_storage", () -> new BlockItem(WizardsRebornBlocks.CREATIVE_WISSEN_STORAGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CREATIVE_LIGHT_STORAGE = ITEMS.register("creative_light_storage", () -> new WizardsRebornRenderBlockItem(WizardsRebornBlocks.CREATIVE_LIGHT_STORAGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CREATIVE_FLUID_STORAGE = ITEMS.register("creative_fluid_storage", () -> new BlockItem(WizardsRebornBlocks.CREATIVE_FLUID_STORAGE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CREATIVE_STEAM_STORAGE = ITEMS.register("creative_steam_storage", () -> new BlockItem(WizardsRebornBlocks.CREATIVE_STEAM_STORAGE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_SALT_TORCH = ITEMS.register("arcane_salt_torch", () -> new SaltTorchItem(WizardsRebornBlocks.ARCANE_SALT_TORCH.get(), WizardsRebornBlocks.ARCANE_SALT_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> INNOCENT_SALT_TORCH = ITEMS.register("innocent_salt_torch", () -> new SaltTorchItem(WizardsRebornBlocks.INNOCENT_SALT_TORCH.get(), WizardsRebornBlocks.INNOCENT_SALT_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> CORK_BAMBOO_SALT_TORCH = ITEMS.register("cork_bamboo_salt_torch", () -> new SaltTorchItem(WizardsRebornBlocks.CORK_BAMBOO_SALT_TORCH.get(), WizardsRebornBlocks.CORK_BAMBOO_SALT_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> WISESTONE_SALT_TORCH = ITEMS.register("wisestone_salt_torch", () -> new SaltTorchItem(WizardsRebornBlocks.WISESTONE_SALT_TORCH.get(), WizardsRebornBlocks.WISESTONE_SALT_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> ARCANE_SALT_LANTERN = ITEMS.register("arcane_salt_lantern", () -> new SaltLanternItem(WizardsRebornBlocks.ARCANE_SALT_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_SALT_LANTERN = ITEMS.register("innocent_salt_lantern", () -> new SaltLanternItem(WizardsRebornBlocks.INNOCENT_SALT_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_SALT_LANTERN = ITEMS.register("cork_bamboo_salt_lantern", () -> new SaltLanternItem(WizardsRebornBlocks.CORK_BAMBOO_SALT_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_SALT_LANTERN = ITEMS.register("wisestone_salt_lantern", () -> new SaltLanternItem(WizardsRebornBlocks.WISESTONE_SALT_LANTERN.get(), new Item.Properties()));
    public static final RegistryObject<Item> ARCANE_SALT_CAMPFIRE = ITEMS.register("arcane_salt_campfire", () -> new SaltCampfireItem(WizardsRebornBlocks.ARCANE_SALT_CAMPFIRE.get(), new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_SALT_CAMPFIRE = ITEMS.register("innocent_salt_campfire", () -> new SaltCampfireItem(WizardsRebornBlocks.INNOCENT_SALT_CAMPFIRE.get(), new Item.Properties()));
    public static final RegistryObject<Item> CORK_BAMBOO_SALT_CAMPFIRE = ITEMS.register("cork_bamboo_salt_campfire", () -> new SaltCampfireItem(WizardsRebornBlocks.CORK_BAMBOO_SALT_CAMPFIRE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_SALT_CAMPFIRE = ITEMS.register("wisestone_salt_campfire", () -> new SaltCampfireItem(WizardsRebornBlocks.WISESTONE_SALT_CAMPFIRE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ALCHEMY_GLASS = ITEMS.register("alchemy_glass", () -> new BlockItem(WizardsRebornBlocks.ALCHEMY_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_VIAL = ITEMS.register("alchemy_vial", () -> new VialItem(new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_FLASK = ITEMS.register("alchemy_flask", () -> new FlaskItem(new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_BOTTLE = ITEMS.register("alchemy_bottle", () -> new AlchemyDrinkBottleItem(new Item.Properties()));
    public static final RegistryObject<Item> ALCHEMY_VIAL_POTION = ITEMS.register("alchemy_vial_potion", () -> new VialPotionItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ALCHEMY_FLASK_POTION = ITEMS.register("alchemy_flask_potion", () -> new FlaskPotionItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> WHITE_LUMINAL_GLASS = ITEMS.register("white_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.WHITE_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_GRAY_LUMINAL_GLASS = ITEMS.register("light_gray_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.LIGHT_GRAY_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_LUMINAL_GLASS = ITEMS.register("gray_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.GRAY_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_LUMINAL_GLASS = ITEMS.register("black_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.BLACK_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BROWN_LUMINAL_GLASS = ITEMS.register("brown_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.BROWN_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_LUMINAL_GLASS = ITEMS.register("red_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.RED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_LUMINAL_GLASS = ITEMS.register("orange_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.ORANGE_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_LUMINAL_GLASS = ITEMS.register("yellow_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.YELLOW_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIME_LUMINAL_GLASS = ITEMS.register("lime_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.LIME_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_LUMINAL_GLASS = ITEMS.register("green_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.GREEN_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CYAN_LUMINAL_GLASS = ITEMS.register("cyan_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.CYAN_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BLUE_LUMINAL_GLASS = ITEMS.register("light_blue_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.LIGHT_BLUE_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_LUMINAL_GLASS = ITEMS.register("blue_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.BLUE_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_LUMINAL_GLASS = ITEMS.register("purple_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.PURPLE_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAGENTA_LUMINAL_GLASS = ITEMS.register("magenta_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.MAGENTA_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_LUMINAL_GLASS = ITEMS.register("pink_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.PINK_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAINBOW_LUMINAL_GLASS = ITEMS.register("rainbow_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.RAINBOW_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_LUMINAL_GLASS = ITEMS.register("cosmic_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.COSMIC_LUMINAL_GLASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> WHITE_FRAMED_LUMINAL_GLASS = ITEMS.register("white_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.WHITE_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_GRAY_FRAMED_LUMINAL_GLASS = ITEMS.register("light_gray_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.LIGHT_GRAY_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAY_FRAMED_LUMINAL_GLASS = ITEMS.register("gray_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.GRAY_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLACK_FRAMED_LUMINAL_GLASS = ITEMS.register("black_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.BLACK_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BROWN_FRAMED_LUMINAL_GLASS = ITEMS.register("brown_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.BROWN_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_FRAMED_LUMINAL_GLASS = ITEMS.register("red_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.RED_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_FRAMED_LUMINAL_GLASS = ITEMS.register("orange_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.ORANGE_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_FRAMED_LUMINAL_GLASS = ITEMS.register("yellow_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.YELLOW_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIME_FRAMED_LUMINAL_GLASS = ITEMS.register("lime_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.LIME_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> GREEN_FRAMED_LUMINAL_GLASS = ITEMS.register("green_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.GREEN_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> CYAN_FRAMED_LUMINAL_GLASS = ITEMS.register("cyan_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.CYAN_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BLUE_FRAMED_LUMINAL_GLASS = ITEMS.register("light_blue_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.LIGHT_BLUE_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> BLUE_FRAMED_LUMINAL_GLASS = ITEMS.register("blue_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.BLUE_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_FRAMED_LUMINAL_GLASS = ITEMS.register("purple_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.PURPLE_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> MAGENTA_FRAMED_LUMINAL_GLASS = ITEMS.register("magenta_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.MAGENTA_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> PINK_FRAMED_LUMINAL_GLASS = ITEMS.register("pink_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.PINK_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAINBOW_FRAMED_LUMINAL_GLASS = ITEMS.register("rainbow_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.RAINBOW_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> COSMIC_FRAMED_LUMINAL_GLASS = ITEMS.register("cosmic_framed_luminal_glass", () -> new BlockItem(WizardsRebornBlocks.COSMIC_FRAMED_LUMINAL_GLASS.get(), new Item.Properties()));

    public static final RegistryObject<Item> ALCHEMY_CALX = ITEMS.register("alchemy_calx", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NATURAL_CALX = ITEMS.register("natural_calx", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SCORCHED_CALX = ITEMS.register("scorched_calx", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DISTANT_CALX = ITEMS.register("distant_calx", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENCHANTED_CALX = ITEMS.register("enchanted_calx", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ALCHEMY_CALX_BLOCK = ITEMS.register("alchemy_calx_block", () -> new BlockItem(WizardsRebornBlocks.ALCHEMY_CALX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> NATURAL_CALX_BLOCK = ITEMS.register("natural_calx_block", () -> new BlockItem(WizardsRebornBlocks.NATURAL_CALX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> SCORCHED_CALX_BLOCK = ITEMS.register("scorched_calx_block", () -> new BlockItem(WizardsRebornBlocks.SCORCHED_CALX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> DISTANT_CALX_BLOCK = ITEMS.register("distant_calx_block", () -> new BlockItem(WizardsRebornBlocks.DISTANT_CALX_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> ENCHANTED_CALX_BLOCK = ITEMS.register("enchanted_calx_block", () -> new BlockItem(WizardsRebornBlocks.ENCHANTED_CALX_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> ARCACITE_POLISHING_MIXTURE = ITEMS.register("arcacite_polishing_mixture", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ARCACITE_POLISHING_MIXTURE_BLOCK = ITEMS.register("arcacite_polishing_mixture_block", () -> new BlockItem(WizardsRebornBlocks.ARCACITE_POLISHING_MIXTURE_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Item> SNIFFALO_EGG = ITEMS.register("sniffalo_egg", () -> new BlockItem(WizardsRebornBlocks.SNIFFALO_EGG.get(), new Item.Properties()));

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

    public static final RegistryObject<Item> ARCANE_FORTRESS_HELMET = ITEMS.register("arcane_fortress_helmet", () -> new ArcaneFortressArmorItem(WizardsRebornArmorMaterials.ARCANE_FORTRESS, ArmorItem.Type.HELMET, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_FORTRESS_ARMOR));
    public static final RegistryObject<Item> ARCANE_FORTRESS_CHESTPLATE = ITEMS.register("arcane_fortress_chestplate", () -> new ArcaneFortressArmorItem(WizardsRebornArmorMaterials.ARCANE_FORTRESS, ArmorItem.Type.CHESTPLATE, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_FORTRESS_ARMOR));
    public static final RegistryObject<Item> ARCANE_FORTRESS_LEGGINGS = ITEMS.register("arcane_fortress_leggings", () -> new ArcaneFortressArmorItem(WizardsRebornArmorMaterials.ARCANE_FORTRESS, ArmorItem.Type.LEGGINGS, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_FORTRESS_ARMOR));
    public static final RegistryObject<Item> ARCANE_FORTRESS_BOOTS = ITEMS.register("arcane_fortress_boots", () -> new ArcaneFortressArmorItem(WizardsRebornArmorMaterials.ARCANE_FORTRESS, ArmorItem.Type.BOOTS, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.ARCANE_FORTRESS_ARMOR));

    public static final RegistryObject<Item> INVENTOR_WIZARD_HAT = ITEMS.register("inventor_wizard_hat", () -> new InventorWizardArmorItem(WizardsRebornArmorMaterials.INVENTOR_WIZARD, ArmorItem.Type.HELMET, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.INVENTOR_WIZARD_ARMOR));
    public static final RegistryObject<Item> INVENTOR_WIZARD_COSTUME = ITEMS.register("inventor_wizard_costume", () -> new InventorWizardArmorItem(WizardsRebornArmorMaterials.INVENTOR_WIZARD, ArmorItem.Type.CHESTPLATE, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.INVENTOR_WIZARD_ARMOR));
    public static final RegistryObject<Item> INVENTOR_WIZARD_TROUSERS = ITEMS.register("inventor_wizard_trousers", () -> new InventorWizardArmorItem(WizardsRebornArmorMaterials.INVENTOR_WIZARD, ArmorItem.Type.LEGGINGS, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.INVENTOR_WIZARD_ARMOR));
    public static final RegistryObject<Item> INVENTOR_WIZARD_BOOTS = ITEMS.register("inventor_wizard_boots", () -> new InventorWizardArmorItem(WizardsRebornArmorMaterials.INVENTOR_WIZARD, ArmorItem.Type.BOOTS, new Item.Properties()).addArcaneEnchantmentType(ArcaneEnchantmentTypes.INVENTOR_WIZARD_ARMOR));

    public static final RegistryObject<Item> ARCANE_WAND = ITEMS.register("arcane_wand", () -> new ArcaneWandItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> WISSEN_WAND = ITEMS.register("wissen_wand", () -> new WissenWandItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARCANE_WOOD_SMOKING_PIPE = ITEMS.register("arcane_wood_smoking_pipe", () -> new SmokingPipeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> INNOCENT_WOOD_SMOKING_PIPE = ITEMS.register("innocent_wood_smoking_pipe", () -> new SmokingPipeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> BAMBOO_SMOKING_PIPE = ITEMS.register("bamboo_smoking_pipe", () -> new SmokingPipeItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> CORK_BAMBOO_SMOKING_PIPE = ITEMS.register("cork_bamboo_smoking_pipe", () -> new SmokingPipeItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ARCANE_WOOD_CANE = ITEMS.register("arcane_wood_cane", () -> new CaneItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ARCANE_WOOD_BOW = ITEMS.register("arcane_wood_bow", () -> new ArcaneBowItem(new Item.Properties().durability(576)));

    public static final RegistryObject<Item> BLAZE_REAP = ITEMS.register("blaze_reap", () -> new ArcanePickaxeItem(WizardsRebornItemTiers.ARCANE_GOLD, 1, -2.8f, new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> CARGO_CARPET = ITEMS.register("cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.CARPET, new Item.Properties()));
    public static final RegistryObject<Item> WHITE_CARGO_CARPET = ITEMS.register("white_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.WHITE, new Item.Properties()));
    public static final RegistryObject<Item> ORANGE_CARGO_CARPET = ITEMS.register("orange_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.ORANGE, new Item.Properties()));
    public static final RegistryObject<Item> MAGENTA_CARGO_CARPET = ITEMS.register("magenta_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.MAGENTA, new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_BLUE_CARGO_CARPET = ITEMS.register("light_blue_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.LIGHT_BLUE, new Item.Properties()));
    public static final RegistryObject<Item> YELLOW_CARGO_CARPET = ITEMS.register("yellow_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.YELLOW, new Item.Properties()));
    public static final RegistryObject<Item> LIME_CARGO_CARPET = ITEMS.register("lime_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.LIME, new Item.Properties()));
    public static final RegistryObject<Item> PINK_CARGO_CARPET = ITEMS.register("pink_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.PINK, new Item.Properties()));
    public static final RegistryObject<Item> GRAY_CARGO_CARPET = ITEMS.register("gray_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.GRAY, new Item.Properties()));
    public static final RegistryObject<Item> LIGHT_GRAY_CARGO_CARPET = ITEMS.register("light_gray_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.LIGHT_GRAY, new Item.Properties()));
    public static final RegistryObject<Item> CYAN_CARGO_CARPET = ITEMS.register("cyan_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.CYAN, new Item.Properties()));
    public static final RegistryObject<Item> PURPLE_CARGO_CARPET = ITEMS.register("purple_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.PURPLE, new Item.Properties()));
    public static final RegistryObject<Item> BLUE_CARGO_CARPET = ITEMS.register("blue_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.BLUE, new Item.Properties()));
    public static final RegistryObject<Item> BROWN_CARGO_CARPET = ITEMS.register("brown_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.BROWN, new Item.Properties()));
    public static final RegistryObject<Item> GREEN_CARGO_CARPET = ITEMS.register("green_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.GREEN, new Item.Properties()));
    public static final RegistryObject<Item> RED_CARGO_CARPET = ITEMS.register("red_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.RED, new Item.Properties()));
    public static final RegistryObject<Item> BLACK_CARGO_CARPET = ITEMS.register("black_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.BLACK, new Item.Properties()));
    public static final RegistryObject<Item> RAINBOW_CARGO_CARPET = ITEMS.register("rainbow_cargo_carpet", () -> new CargoCarpetItem(CargoCarpetItem.RAINBOW, new Item.Properties()));

    public static final RegistryObject<Item> ARCANE_ENCHANTED_BOOK = ITEMS.register("arcane_enchanted_book", () -> new ArcaneEnchantedBookItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ARCANEMICON = ITEMS.register("arcanemicon", () -> new ArcanemiconItem(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> KNOWLEDGE_SCROLL = ITEMS.register("knowledge_scroll", () -> new KnowledgeSrollItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> CREATIVE_KNOWLEDGE_SCROLL = ITEMS.register("creative_knowledge_scroll", () -> new CreativeKnowledgeSrollItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), false));
    public static final RegistryObject<Item> CREATIVE_KNOWLEDGE_ANCIENT_SCROLL = ITEMS.register("creative_knowledge_ancient_scroll", () -> new CreativeKnowledgeSrollItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), true));
    public static final RegistryObject<Item> CREATIVE_SPELL_SCROLL = ITEMS.register("creative_spell_scroll", () -> new CreativeSpellSrollItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), false));
    public static final RegistryObject<Item> CREATIVE_SPELL_ANCIENT_SCROLL = ITEMS.register("creative_spell_ancient_scroll", () -> new CreativeSpellSrollItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC), true));

    public static final RegistryObject<Item> VIOLENCE_BANNER_PATTERN = ITEMS.register("violence_banner_pattern", () -> new RainBannerPatternItem(RainBannerPatternItem.Types.VIOLENCE, WizardsRebornBannerPatternTags.VIOLENCE_BANNER, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> REPRODUCTION_BANNER_PATTERN = ITEMS.register("reproduction_banner_pattern", () -> new RainBannerPatternItem(RainBannerPatternItem.Types.REPRODUCTION, WizardsRebornBannerPatternTags.REPRODUCTION_BANNER, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> COOPERATION_BANNER_PATTERN = ITEMS.register("cooperation_banner_pattern", () -> new RainBannerPatternItem(RainBannerPatternItem.Types.COOPERATION, WizardsRebornBannerPatternTags.COOPERATION_BANNER, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> HUNGER_BANNER_PATTERN = ITEMS.register("hunger_banner_pattern", () -> new RainBannerPatternItem(RainBannerPatternItem.Types.HUNGER, WizardsRebornBannerPatternTags.HUNGER_BANNER, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SURVIVAL_BANNER_PATTERN = ITEMS.register("survival_banner_pattern", () -> new RainBannerPatternItem(RainBannerPatternItem.Types.SURVIVAL, WizardsRebornBannerPatternTags.SURVIVAL_BANNER, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> ELEVATION_BANNER_PATTERN = ITEMS.register("elevation_banner_pattern", () -> new RainBannerPatternItem(RainBannerPatternItem.Types.ELEVATION, WizardsRebornBannerPatternTags.ELEVATION_BANNER, (new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<Item> MUSIC_DISC_ARCANUM = ITEMS.register("music_disc_arcanum", () -> new ArcaneRecordItem(6, WizardsRebornSounds.MUSIC_DISC_ARCANUM.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 122, new Color(203, 234, 251)));
    public static final RegistryObject<Item> MUSIC_DISC_MOR = ITEMS.register("music_disc_mor", () -> new ArcaneRecordItem(6, WizardsRebornSounds.MUSIC_DISC_MOR.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 305, new Color(244, 245, 230)));
    public static final RegistryObject<Item> MUSIC_DISC_REBORN = ITEMS.register("music_disc_reborn", () -> new ArcaneRecordItem(6, WizardsRebornSounds.MUSIC_DISC_REBORN.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 211, new Color(252, 240, 175)));
    public static final RegistryObject<Item> MUSIC_DISC_SHIMMER = ITEMS.register("music_disc_shimmer", () -> new ArcaneRecordItem(6, WizardsRebornSounds.MUSIC_DISC_SHIMMER.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 234, new Color(203, 234, 251)));
    public static final RegistryObject<Item> MUSIC_DISC_CAPITALISM = ITEMS.register("music_disc_capitalism", () -> new ArcaneRecordItem(6, WizardsRebornSounds. MUSIC_DISC_CAPITALISM.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 96, new Color(252, 240, 175)));
    public static final RegistryObject<Item> MUSIC_DISC_PANACHE = ITEMS.register("music_disc_panache", () -> new ArcaneRecordItem(6, WizardsRebornSounds.MUSIC_DISC_PANACHE.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 91, new Color(252, 240, 175)).setCassette());

    public static final RegistryObject<Item> ARCANE_WOOD_TRIM = ITEMS.register("arcane_wood_trim", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WISESTONE_TRIM = ITEMS.register("wisestone_trim", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INNOCENT_WOOD_TRIM = ITEMS.register("innocent_wood_trim", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TOP_HAT_TRIM = ITEMS.register("top_hat_trim", () -> new SkinTrimItem(new Item.Properties(), WizardsRebornItemSkins.TOP_HAT));
    public static final RegistryObject<Item> MAGNIFICENT_MAID_TRIM = ITEMS.register("magnificent_maid_trim", () -> new SkinTrimItem(new Item.Properties(), WizardsRebornItemSkins.MAGNIFICENT_MAID));
    public static final RegistryObject<Item> SUMMER_LOVE_TRIM = ITEMS.register("summer_love_trim", () -> new SkinTrimItem(new Item.Properties(), WizardsRebornItemSkins.SUMMER_LOVE));
    public static final RegistryObject<Item> SOUL_HUNTER_TRIM = ITEMS.register("soul_hunter_trim", () -> new SkinTrimItem(new Item.Properties(), WizardsRebornItemSkins.SOUL_HUNTER));
    public static final RegistryObject<Item> IMPLOSION_TRIM = ITEMS.register("implosion_trim", () -> new SkinTrimItem(new Item.Properties(), WizardsRebornItemSkins.IMPLOSION));
    public static final RegistryObject<Item> PHANTOM_INK_TRIM = ITEMS.register("phantom_ink_trim", () -> new SkinTrimItem(new Item.Properties(), WizardsRebornItemSkins.PHANTOM_INK));

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

    public static final RegistryObject<Item> SHRIMP = ITEMS.register("shrimp", () -> new ShrimpItem(new Item.Properties().food(WizardsRebornFoods.SHRIMP), false));
    public static final RegistryObject<Item> FRIED_SHRIMP = ITEMS.register("fried_shrimp", () -> new ShrimpItem(new Item.Properties().food(WizardsRebornFoods.FRIED_SHRIMP), true));

    public static final RegistryObject<Item> MUNDANE_BREW_BUCKET = ITEMS.register("mundane_brew_bucket", () -> new BucketItem(WizardsRebornFluids.MUNDANE_BREW, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> ALCHEMY_OIL_BUCKET = ITEMS.register("alchemy_oil_bucket", () -> new BucketItem(WizardsRebornFluids.ALCHEMY_OIL, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> OIL_TEA_BUCKET = ITEMS.register("oil_tea_bucket", () -> new BucketItem(WizardsRebornFluids.OIL_TEA, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> WISSEN_TEA_BUCKET = ITEMS.register("wissen_tea_bucket", () -> new BucketItem(WizardsRebornFluids.WISSEN_TEA, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MILK_TEA_BUCKET = ITEMS.register("milk_tea_bucket", () -> new BucketItem(WizardsRebornFluids.MILK_TEA, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MUSHROOM_BREW_BUCKET = ITEMS.register("mushroom_brew_bucket", () -> new BucketItem(WizardsRebornFluids.MUSHROOM_BREW, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> HELLISH_MUSHROOM_BREW_BUCKET = ITEMS.register("hellish_mushroom_brew_bucket", () -> new BucketItem(WizardsRebornFluids.HELLISH_MUSHROOM_BREW, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> MOR_BREW_BUCKET = ITEMS.register("mor_brew_bucket", () -> new BucketItem(WizardsRebornFluids.MOR_BREW, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final RegistryObject<Item> FLOWER_BREW_BUCKET = ITEMS.register("flower_brew_bucket", () -> new BucketItem(WizardsRebornFluids.FLOWER_BREW, new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final RegistryObject<ForgeSpawnEggItem> SNIFFALO_SPAWN_EGG = ITEMS.register("sniffalo_spawn_egg", () -> new ForgeSpawnEggItem(WizardsRebornEntities.SNIFFALO, ColorUtil.packColor(255, 96, 58, 62), ColorUtil.packColor(255, 181, 139, 117), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerItems(FMLClientSetupEvent event) {
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

            FluffyFurItems.makeBow(ARCANE_WOOD_BOW.get());

            ItemProperties.register(ALCHEMY_VIAL_POTION.get(), new ResourceLocation("uses"), (stack, level, entity, seed) -> AlchemyPotionItem.getUses(stack));
            ItemProperties.register(ALCHEMY_FLASK_POTION.get(), new ResourceLocation("uses"), (stack, level, entity, seed) -> AlchemyPotionItem.getUses(stack));

            ItemProperties.register(KNOWLEDGE_SCROLL.get(), new ResourceLocation("knowledge"), (stack, level, entity, seed) -> KnowledgeSrollItem.hasKnowledge(stack) ? 1 : 0);

            BowHandler.addBow(ARCANE_WOOD_BOW.get());
        }

        @SubscribeEvent
        public static void modelRegistryItems(ModelEvent.RegisterAdditional event) {
            FluffyFurModels.addBowItemModel(event, WizardsReborn.MOD_ID, "arcane_wood_bow");
            event.register(LargeItemRenderer.getModelResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_scythe"));
            event.register(LargeItemRenderer.getModelResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_scythe"));
            event.register(LargeItemRenderer.getModelResourceLocation(WizardsReborn.MOD_ID, "arcane_gold_scythe"));
            event.register(LargeItemRenderer.getModelResourceLocation(WizardsReborn.MOD_ID, "blaze_reap"));

            for (String skin : LeatherCollarItem.skins.values()) {
                event.register(new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "collar/" + skin), "inventory"));
            }

            for (int i = 0; i < 4; i++) {
                event.register(DrinksModels.getModelLocationStage(i + 1));
            }
        }

        @SubscribeEvent
        public static void modelBakeItems(ModelEvent.ModifyBakingResult event) {
            Map<ResourceLocation, BakedModel> map = event.getModels();

            WandCrystalsModels.addWandItem(map, ARCANE_WAND.getId());

            LargeItemRenderer.bakeModel(map, WizardsReborn.MOD_ID, "blaze_reap");

            for (String skin : LeatherCollarItem.skins.keySet()) {
                BakedModel model = map.get(new ModelResourceLocation(new ResourceLocation(WizardsReborn.MOD_ID, "collar/" + LeatherCollarItem.skins.get(skin)), "inventory"));
                CollarItemOverrides.skins.put(skin, model);
            }
            BakedModel collarModel = map.get(new ModelResourceLocation(LEATHER_COLLAR.getId(), "inventory"));
            CustomModel collarNewModel = new CustomModel(collarModel, new CollarItemOverrides());
            map.replace(new ModelResourceLocation(LEATHER_COLLAR.getId(), "inventory"), collarNewModel);

            DrinksModels.addDrinkItem(map, VODKA_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, BOURBON_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, WHISKEY_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, WHITE_WINE_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, RED_WINE_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, PORT_WINE_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, PALM_LIQUEUR_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, MEAD_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, SBITEN_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, SLIVOVITZ_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, SAKE_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, SOJU_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, CHICHA_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, CHACHA_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, APPLEJACK_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, RAKIA_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, KIRSCH_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, BOROVICHKA_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, PALINKA_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, TEQUILA_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, PULQUE_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, ARKHI_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, TEJ_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, WISSEN_BEER_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, MOR_TINCTURE_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, INNOCENT_WINE_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, TARKHUNA_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, BAIKAL_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, KVASS_BOTTLE.getId());
            DrinksModels.addDrinkItem(map, KISSEL_BOTTLE.getId());

            FluffyFurModels.addCustomRenderItemModel(map, LIGHT_EMITTER.getId());
            FluffyFurModels.addCustomRenderItemModel(map, LIGHT_TRANSFER_LENS.getId());
            FluffyFurModels.addCustomRenderItemModel(map, ARCANE_WOOD_LIGHT_CASING.getId());
            FluffyFurModels.addCustomRenderItemModel(map, INNOCENT_WOOD_LIGHT_CASING.getId());
            FluffyFurModels.addCustomRenderItemModel(map, CORK_BAMBOO_LIGHT_CASING.getId());
            FluffyFurModels.addCustomRenderItemModel(map, WISESTONE_LIGHT_CASING.getId());
            FluffyFurModels.addCustomRenderItemModel(map, CREATIVE_LIGHT_STORAGE.getId());
        }

        @SubscribeEvent
        public static void registerItemColorHandlers(RegisterColorHandlersEvent.Item event) {
            event.register(new AlchemyPotionItem.ColorHandler(), ALCHEMY_VIAL_POTION.get(), ALCHEMY_FLASK_POTION.get());
            event.register(new RunicWisestonePlateItem.ColorHandler(), RUNIC_WISESTONE_PLATE.get());
        }
    }

    public static void setupItems() {
        FluffyFurItems.composterItem(0.3F, ARCANE_WOOD_LEAVES.get());
        FluffyFurItems.composterItem(0.3F, ARCANE_WOOD_SAPLING.get());
        FluffyFurItems.composterItem(0.3F, INNOCENT_WOOD_LEAVES.get());
        FluffyFurItems.composterItem(0.3F, INNOCENT_WOOD_SAPLING.get());
        FluffyFurItems.composterItem(0.3F, PETALS_OF_INNOCENCE.get());
        FluffyFurItems.composterItem(0.3F, ARCANE_LINEN_SEEDS.get());
        FluffyFurItems.composterItem(0.65F, ARCANE_LINEN.get());
        FluffyFurItems.composterItem(0.85F, ARCANE_LINEN_HAY.get());
        FluffyFurItems.composterItem(0.65F, MOR.get());
        FluffyFurItems.composterItem(0.65F, ELDER_MOR.get());
        FluffyFurItems.composterItem(0.85F, MOR_BLOCK.get());
        FluffyFurItems.composterItem(0.85F, ELDER_MOR_BLOCK.get());
        FluffyFurItems.composterItem(0.9F, PITCHER_TURNIP.get());
        FluffyFurItems.composterItem(0.9F, SHINY_CLOVER.get());
        FluffyFurItems.composterItem(0.2F, PETALS.get());
        FluffyFurItems.composterItem(0.2F, GROUND_BROWN_MUSHROOM.get());
        FluffyFurItems.composterItem(0.2F, GROUND_RED_MUSHROOM.get());
        FluffyFurItems.composterItem(0.2F, GROUND_CRIMSON_FUNGUS.get());
        FluffyFurItems.composterItem(0.2F, GROUND_WARPED_FUNGUS.get());
        FluffyFurItems.composterItem(0.2F, GROUND_MOR.get());
        FluffyFurItems.composterItem(0.2F, GROUND_ELDER_MOR.get());

        MortarItem.mortarList.add(ARCANE_WOOD_MORTAR.get());
        MortarItem.mortarList.add(INNOCENT_WOOD_MORTAR.get());

        AlchemyPotionItem.potionList.add(ALCHEMY_VIAL_POTION.get());
        AlchemyPotionItem.potionList.add(ALCHEMY_FLASK_POTION.get());

        DispenserBlock.registerBehavior(ARCANUM_DUST.get(), new OptionalDispenseItemBehavior() {
            protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
                this.setSuccess(true);
                Level level = blockSource.getLevel();
                BlockPos blockpos = blockSource.getPos().relative(blockSource.getBlockState().getValue(DispenserBlock.FACING));
                if (!ArcanumDustItem.executeTransmutation(itemStack, level, blockpos, Vec3.ZERO, Vec3.ZERO, false, false)) {
                    this.setSuccess(false);
                }

                return itemStack;
            }
        });
    }

    public static void setupCrystalsItems() {
        CrystalHandler.addItem(EARTH_CRYSTAL.get());
        CrystalHandler.addItem(WATER_CRYSTAL.get());
        CrystalHandler.addItem(AIR_CRYSTAL.get());
        CrystalHandler.addItem(FIRE_CRYSTAL.get());
        CrystalHandler.addItem(VOID_CRYSTAL.get());

        CrystalHandler.addItem(FACETED_EARTH_CRYSTAL.get());
        CrystalHandler.addItem(FACETED_WATER_CRYSTAL.get());
        CrystalHandler.addItem(FACETED_AIR_CRYSTAL.get());
        CrystalHandler.addItem(FACETED_FIRE_CRYSTAL.get());
        CrystalHandler.addItem(FACETED_VOID_CRYSTAL.get());

        CrystalHandler.addItem(ADVANCED_EARTH_CRYSTAL.get());
        CrystalHandler.addItem(ADVANCED_WATER_CRYSTAL.get());
        CrystalHandler.addItem(ADVANCED_AIR_CRYSTAL.get());
        CrystalHandler.addItem(ADVANCED_FIRE_CRYSTAL.get());
        CrystalHandler.addItem(ADVANCED_VOID_CRYSTAL.get());

        CrystalHandler.addItem(MASTERFUL_EARTH_CRYSTAL.get());
        CrystalHandler.addItem(MASTERFUL_WATER_CRYSTAL.get());
        CrystalHandler.addItem(MASTERFUL_AIR_CRYSTAL.get());
        CrystalHandler.addItem(MASTERFUL_FIRE_CRYSTAL.get());
        CrystalHandler.addItem(MASTERFUL_VOID_CRYSTAL.get());

        CrystalHandler.addItem(PURE_EARTH_CRYSTAL.get());
        CrystalHandler.addItem(PURE_WATER_CRYSTAL.get());
        CrystalHandler.addItem(PURE_AIR_CRYSTAL.get());
        CrystalHandler.addItem(PURE_FIRE_CRYSTAL.get());
        CrystalHandler.addItem(PURE_VOID_CRYSTAL.get());
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
}
