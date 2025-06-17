package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeHandler;
import mod.maxbogomol.wizards_reborn.common.knowledge.*;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItemTags;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WizardsRebornKnowledges {
    //PROGRESSION
    public static List<Knowledge> progression = new ArrayList<>();
    public static List<Knowledge> additionalProgression = new ArrayList<>();

    //START
    public static ArcanemiconKnowledge ARCANEMICON = new ArcanemiconKnowledge(WizardsReborn.MOD_ID+":arcanemicon", false, 0, WizardsRebornItems.ARCANEMICON.get());
    public static ArcanemiconOfferingKnowledge ARCANEMICON_OFFERING = new ArcanemiconOfferingKnowledge(WizardsReborn.MOD_ID+":arcanemicon_offering", false, 0);
    public static ArcanumKnowledge ARCANUM = new ArcanumKnowledge(WizardsReborn.MOD_ID+":arcanum", false, 0, WizardsRebornItems.ARCANUM.get());

    //ARCANE NATURE
    public static ItemKnowledge ARCANUM_DUST = new ItemKnowledge(WizardsReborn.MOD_ID+":arcanum_dust", true, 10, WizardsRebornItems.ARCANUM_DUST.get());
    public static ItemTagKnowledge ARCANE_WOOD = new ItemTagKnowledge(WizardsReborn.MOD_ID+":arcane_wood", true, 10, WizardsRebornItemTags.ARCANE_WOOD_LOGS, new ItemStack(WizardsRebornItems.ARCANE_WOOD_LOG.get()));
    public static ItemKnowledge ARCANE_GOLD = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_gold", true, 10, WizardsRebornItems.ARCANE_GOLD_INGOT.get());
    public static ItemKnowledge WISSEN_CRYSTALLIZER = new ItemKnowledge(WizardsReborn.MOD_ID+":wissen_crystallizer", true, 10, WizardsRebornItems.WISSEN_CRYSTALLIZER.get());
    public static ItemKnowledge ARCANE_WORKBENCH = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_workbench", true, 10, WizardsRebornItems.ARCANE_WORKBENCH.get());
    public static ItemKnowledge ARCANE_WAND = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_wand", true, 20, WizardsRebornItems.ARCANE_WAND.get());
    public static ItemKnowledge VOID_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":void_crystal", true, 20, WizardsRebornItems.VOID_CRYSTAL.get());
    public static ItemKnowledge ARCANE_ITERATOR = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_iterator", true, 10, WizardsRebornItems.ARCANE_ITERATOR.get());
    public static ItemKnowledge ARCANUM_LENS = new ItemKnowledge(WizardsReborn.MOD_ID+":arcanum_lens", true, 10, WizardsRebornItems.ARCANUM_LENS.get());
    public static ItemKnowledge JEWELER_TABLE = new ItemKnowledge(WizardsReborn.MOD_ID+":jeweler_table", true, 10, WizardsRebornItems.JEWELER_TABLE.get());
    public static ItemTagKnowledge FACETED_CRYSTALS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":faceted_crystals", true, 20, WizardsRebornItemTags.FACETED_CRYSTALS, new ItemStack(WizardsRebornItems.FACETED_EARTH_CRYSTAL.get()));

    public static ItemTagKnowledge ARCANE_WOOD_TOOLS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":arcane_wood_tools", false, 5, WizardsRebornItemTags.ARCANE_WOOD_TOOLS, new ItemStack(WizardsRebornItems.ARCANE_WOOD_PICKAXE.get()));
    public static ItemTagKnowledge ARCANE_GOLD_TOOLS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":arcane_gold_tools", false, 5, WizardsRebornItemTags.ARCANE_GOLD_TOOLS, new ItemStack(WizardsRebornItems.ARCANE_GOLD_PICKAXE.get()));
    public static ItemTagKnowledge SCYTHES = new ItemTagKnowledge(WizardsReborn.MOD_ID+":scythes", false, 5, WizardsRebornItemTags.SCYTHES, new ItemStack(WizardsRebornItems.ARCANE_GOLD_SCYTHE.get()));
    public static ItemKnowledge MUSIC_DISC_SHIMMER = new MusicDiscKnowledge(WizardsReborn.MOD_ID+":music_disc_shimmer", false, 8, WizardsRebornItems.MUSIC_DISC_SHIMMER.get());
    public static ItemTagKnowledge ARCANE_LUMOS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":arcane_lumos", false, 5, WizardsRebornItemTags.ARCANE_LUMOS, new ItemStack(WizardsRebornItems.WHITE_ARCANE_LUMOS.get()));
    public static ItemTagKnowledge CRYSTALS_SEEDS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":crystal_seeds", false, 3, WizardsRebornItemTags.CRYSTALS_SEEDS, new ItemStack(WizardsRebornItems.EARTH_CRYSTAL_SEED.get()));
    public static ItemKnowledge EARTH_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":earth_crystal", false, 5, WizardsRebornItems.EARTH_CRYSTAL.get());
    public static ItemKnowledge WATER_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":water_crystal", false, 5, WizardsRebornItems.WATER_CRYSTAL.get());
    public static ItemKnowledge AIR_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":air_crystal", false, 5, WizardsRebornItems.AIR_CRYSTAL.get());
    public static ItemKnowledge FIRE_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":fire_crystal", false, 5, WizardsRebornItems.FIRE_CRYSTAL.get());
    public static ItemKnowledge CRYSTAL_BAG = new ItemKnowledge(WizardsReborn.MOD_ID+":crystal_bag", false, 9, WizardsRebornItems.CRYSTAL_BAG.get());
    public static ItemKnowledge ARCANE_FORTRESS_BELT = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_fortress_belt", false, 8, WizardsRebornItems.ARCANE_FORTRESS_BELT.get());
    public static ItemKnowledge ARCANE_WOOD_CANE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_wood_cane", false, 7, WizardsRebornItems.ARCANE_WOOD_CANE.get());
    public static ItemKnowledge KNOWLEDGE_SCROLL = new ItemKnowledge(WizardsReborn.MOD_ID+":knowledge_scroll", false, 20, WizardsRebornItems.KNOWLEDGE_SCROLL.get());
    public static ItemKnowledge WISSEN_KEYCHAIN = new ItemKnowledge(WizardsReborn.MOD_ID+":wissen_keychain", false, 7, WizardsRebornItems.WISSEN_KEYCHAIN.get());
    public static ItemKnowledge WISSEN_RING = new ItemKnowledge(WizardsReborn.MOD_ID+":wissen_ring", false, 5, WizardsRebornItems.WISSEN_RING.get());
    public static ItemKnowledge MUSIC_DISC_REBORN = new MusicDiscKnowledge(WizardsReborn.MOD_ID+":music_disc_reborn", false, 8, WizardsRebornItems.MUSIC_DISC_REBORN.get());
    public static ItemKnowledge MUSIC_DISC_PANACHE = new MusicDiscKnowledge(WizardsReborn.MOD_ID+":music_disc_panache", false, 8, WizardsRebornItems.MUSIC_DISC_PANACHE.get());
    public static ItemKnowledge MUSIC_DISC_CAPITALISM = new MusicDiscKnowledge(WizardsReborn.MOD_ID+":music_disc_capitalism", false, 8, WizardsRebornItems.MUSIC_DISC_CAPITALISM.get());

    //AUTOMATION
    public static ItemKnowledge ARCANE_LEVER = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_lever", false, 4, WizardsRebornItems.ARCANE_LEVER.get());
    public static ItemKnowledge ARCANE_HOPPER = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_hopper", false, 5, WizardsRebornItems.ARCANE_HOPPER.get());
    public static ItemKnowledge REDSTONE_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":redstone_sensor", false, 8, WizardsRebornItems.REDSTONE_SENSOR.get());
    public static ItemKnowledge WISSEN_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":wissen_sensor", false, 5, WizardsRebornItems.WISSEN_SENSOR.get());
    public static ItemKnowledge COOLDOWN_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":cooldown_sensor", false, 5, WizardsRebornItems.COOLDOWN_SENSOR.get());
    public static ItemKnowledge EXPERIENCE_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":experience_sensor", false, 5, WizardsRebornItems.EXPERIENCE_SENSOR.get());
    public static ItemKnowledge LIGHT_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":light_sensor", false, 5, WizardsRebornItems.LIGHT_SENSOR.get());
    public static ItemKnowledge HEAT_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":heat_sensor", false, 5, WizardsRebornItems.HEAT_SENSOR.get());
    public static ItemKnowledge FLUID_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":fluid_sensor", false, 5, WizardsRebornItems.FLUID_SENSOR.get());
    public static ItemKnowledge STEAM_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":steam_sensor", false, 5, WizardsRebornItems.STEAM_SENSOR.get());
    public static ItemKnowledge WISSEN_ACTIVATOR = new ItemKnowledge(WizardsReborn.MOD_ID+":wissen_activator", false, 5, WizardsRebornItems.WISSEN_ACTIVATOR.get());
    public static ItemKnowledge ITEM_SORTER = new ItemKnowledge(WizardsReborn.MOD_ID+":item_sorter", false, 5, WizardsRebornItems.ITEM_SORTER.get());
    public static ItemTagKnowledge WISSEN_CASING = new ItemTagKnowledge(WizardsReborn.MOD_ID+":wissen_casing", false, 6, WizardsRebornItemTags.WISSEN_CASINGS, new ItemStack(WizardsRebornItems.ARCANE_WOOD_WISSEN_CASING.get()));
    public static ItemTagKnowledge LIGHT_CASING = new ItemTagKnowledge(WizardsReborn.MOD_ID+":light_casing", false, 6, WizardsRebornItemTags.LIGHT_CASINGS, new ItemStack(WizardsRebornItems.ARCANE_WOOD_LIGHT_CASING.get()));
    public static ItemTagKnowledge FLUID_CASING = new ItemTagKnowledge(WizardsReborn.MOD_ID+":fluid_casing", false, 6, WizardsRebornItemTags.FLUID_CASINGS, new ItemStack(WizardsRebornItems.ARCANE_WOOD_FLUID_CASING.get()));
    public static ItemTagKnowledge STEAM_CASING = new ItemTagKnowledge(WizardsReborn.MOD_ID+":steam_casing", false, 6, WizardsRebornItemTags.STEAM_CASINGS, new ItemStack(WizardsRebornItems.ARCANE_WOOD_STEAM_CASING.get()));

    //CRYSTAL RITUALS
    public static ItemKnowledge LIGHT_EMITTER = new ItemKnowledge(WizardsReborn.MOD_ID+":light_emitter", true, 10, WizardsRebornItems.LIGHT_EMITTER.get());
    public static ItemKnowledge RUNIC_PEDESTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":runic_pedestal", true, 10, WizardsRebornItems.RUNIC_PEDESTAL.get());
    public static CrystalRitualKnowledge CRYSTAL_INFUSION = new CrystalRitualKnowledge(WizardsReborn.MOD_ID+":crystal_infusion", true, 15, WizardsRebornCrystalRituals.CRYSTAL_INFUSION);
    public static ItemTagKnowledge INNOCENT_WOOD = new ItemTagKnowledge(WizardsReborn.MOD_ID+":innocent_wood", true, 10, WizardsRebornItemTags.INNOCENT_WOOD_LOGS, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_LOG.get()));

    public static ItemTagKnowledge INNOCENT_WOOD_TOOLS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":innocent_wood_tools", false, 5, WizardsRebornItemTags.INNOCENT_WOOD_TOOLS, new ItemStack(WizardsRebornItems.INNOCENT_WOOD_PICKAXE.get()));
    public static ItemKnowledge MUSIC_DISC_DISCO = new MusicDiscKnowledge(WizardsReborn.MOD_ID+":music_disc_disco", false, 8, WizardsRebornItems.MUSIC_DISC_DISCO.get());

    //ALCHEMY
    public static ItemKnowledge PETALS = new ItemKnowledge(WizardsReborn.MOD_ID+":petals", false, 3, WizardsRebornItems.PETALS.get());
    public static ItemKnowledge WISESTONE = new ItemKnowledge(WizardsReborn.MOD_ID+":wisestone", true, 10, WizardsRebornItems.WISESTONE.get());
    public static ItemKnowledge FLUID_PIPE = new ItemKnowledge(WizardsReborn.MOD_ID+":fluid_pipe", false, 5, WizardsRebornItems.FLUID_PIPE.get());
    public static ItemKnowledge STEAM_PIPE = new ItemKnowledge(WizardsReborn.MOD_ID+":steam_pipe", false, 5, WizardsRebornItems.STEAM_PIPE.get());
    public static ItemKnowledge ORBITAL_FLUID_RETAINER = new ItemKnowledge(WizardsReborn.MOD_ID+":orbital_fluid_retainer", true, 10, WizardsRebornItems.ORBITAL_FLUID_RETAINER.get());
    public static ItemKnowledge ALCHEMY_FURNACE = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_furnace", true, 10, WizardsRebornItems.ALCHEMY_FURNACE.get());
    public static ItemKnowledge ALCHEMY_MACHINE = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_machine", true, 10, WizardsRebornItems.ALCHEMY_MACHINE.get());
    public static ItemKnowledge ALCHEMY_OIL = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_oil", true, 10, WizardsRebornItems.ALCHEMY_OIL_BUCKET.get());
    public static ItemKnowledge ALCHEMY_CALX = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_calx", true, 10, WizardsRebornItems.ALCHEMY_CALX.get());
    public static ItemKnowledge ALCHEMY_GLASS = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_glass", true, 10, WizardsRebornItems.ALCHEMY_GLASS.get());
    public static ItemKnowledge ARCANE_CENSER = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_censer", true, 10, WizardsRebornItems.ARCANE_CENSER.get());
    public static ItemKnowledge ARCANE_WOOD_SMOKING_PIPE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_wood_smoking_pipe", false, 7, WizardsRebornItems.ARCANE_WOOD_SMOKING_PIPE.get());
    public static ItemKnowledge ARCACITE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcacite", true, 10, WizardsRebornItems.ARCACITE.get());
    public static ItemKnowledge ARCACITE_POLISHING_MIXTURE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcacite_polishing_mixture", true, 10, WizardsRebornItems.ARCACITE_POLISHING_MIXTURE.get());
    public static ItemKnowledge SNIFFALO_EGG = new EchoEggKnowledge(WizardsReborn.MOD_ID+":sniffalo_egg", true, 10, WizardsRebornItems.SNIFFALO_EGG.get());

    public static ItemKnowledge MOR = new ItemKnowledge(WizardsReborn.MOD_ID+":mor", false, 5, WizardsRebornItems.MOR.get());
    public static ItemKnowledge ELDER_MOR = new ItemKnowledge(WizardsReborn.MOD_ID+":elder_mor", false, 5, WizardsRebornItems.ELDER_MOR.get());
    public static ItemKnowledge MUSIC_DISC_ARCANUM = new MusicDiscKnowledge(WizardsReborn.MOD_ID+":music_disc_arcanum", false, 8, WizardsRebornItems.MUSIC_DISC_ARCANUM.get());
    public static ItemKnowledge MUSIC_DISC_MOR = new MusicDiscKnowledge(WizardsReborn.MOD_ID+":music_disc_mor", false, 8, WizardsRebornItems.MUSIC_DISC_MOR.get());
    public static ItemKnowledge NETHER_SALT = new ItemKnowledge(WizardsReborn.MOD_ID+":nether_salt", false, 10, WizardsRebornItems.NETHER_SALT.get());
    public static ItemKnowledge NATURAL_CALX = new ItemKnowledge(WizardsReborn.MOD_ID+":natural_calx", false, 5, WizardsRebornItems.NATURAL_CALX.get());
    public static ItemKnowledge SCORCHED_CALX = new ItemKnowledge(WizardsReborn.MOD_ID+":scorched_calx", false, 5, WizardsRebornItems.SCORCHED_CALX.get());
    public static ItemKnowledge DISTANT_CALX = new ItemKnowledge(WizardsReborn.MOD_ID+":distant_calx", false, 5, WizardsRebornItems.DISTANT_CALX.get());
    public static ItemKnowledge ENCHANTED_CALX = new ItemKnowledge(WizardsReborn.MOD_ID+":enchanted_calx", false, 5, WizardsRebornItems.ENCHANTED_CALX.get());
    public static ItemKnowledge ALCHEMY_VIAL = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_vial", false, 8, WizardsRebornItems.ALCHEMY_VIAL.get());
    public static ItemKnowledge ALCHEMY_BAG = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_bag", false, 8, WizardsRebornItems.ALCHEMY_BAG.get());

    public static void init() {
        //START
        KnowledgeHandler.register(ARCANEMICON);
        KnowledgeHandler.register(ARCANEMICON_OFFERING);
        KnowledgeHandler.register(ARCANUM);

        //ARCANE NATURE
        KnowledgeHandler.register(ARCANUM_DUST);
        KnowledgeHandler.register(ARCANE_WOOD);
        KnowledgeHandler.register(ARCANE_GOLD);
        KnowledgeHandler.register(WISSEN_CRYSTALLIZER);
        KnowledgeHandler.register(ARCANE_WORKBENCH);
        KnowledgeHandler.register(ARCANE_WAND);
        KnowledgeHandler.register(ARCANE_ITERATOR);
        KnowledgeHandler.register(ARCANUM_LENS);
        KnowledgeHandler.register(JEWELER_TABLE);
        KnowledgeHandler.register(FACETED_CRYSTALS);

        KnowledgeHandler.register(ARCANE_WOOD_TOOLS);
        KnowledgeHandler.register(ARCANE_GOLD_TOOLS);
        KnowledgeHandler.register(SCYTHES);
        KnowledgeHandler.register(MUSIC_DISC_SHIMMER);
        KnowledgeHandler.register(ARCANE_LUMOS);
        KnowledgeHandler.register(CRYSTALS_SEEDS);
        KnowledgeHandler.register(EARTH_CRYSTAL);
        KnowledgeHandler.register(WATER_CRYSTAL);
        KnowledgeHandler.register(AIR_CRYSTAL);
        KnowledgeHandler.register(FIRE_CRYSTAL);
        KnowledgeHandler.register(VOID_CRYSTAL);
        KnowledgeHandler.register(CRYSTAL_BAG);
        KnowledgeHandler.register(ARCANE_FORTRESS_BELT);
        KnowledgeHandler.register(ARCANE_WOOD_CANE);
        KnowledgeHandler.register(KNOWLEDGE_SCROLL);
        KnowledgeHandler.register(WISSEN_KEYCHAIN);
        KnowledgeHandler.register(WISSEN_RING);
        KnowledgeHandler.register(MUSIC_DISC_REBORN);
        KnowledgeHandler.register(MUSIC_DISC_CAPITALISM);
        KnowledgeHandler.register(MUSIC_DISC_PANACHE);

        //AUTOMATION
        KnowledgeHandler.register(ARCANE_LEVER);
        KnowledgeHandler.register(ARCANE_HOPPER);
        KnowledgeHandler.register(REDSTONE_SENSOR);
        KnowledgeHandler.register(WISSEN_SENSOR);
        KnowledgeHandler.register(COOLDOWN_SENSOR);
        KnowledgeHandler.register(EXPERIENCE_SENSOR);
        KnowledgeHandler.register(LIGHT_SENSOR);
        KnowledgeHandler.register(HEAT_SENSOR);
        KnowledgeHandler.register(FLUID_SENSOR);
        KnowledgeHandler.register(STEAM_SENSOR);
        KnowledgeHandler.register(WISSEN_ACTIVATOR);
        KnowledgeHandler.register(ITEM_SORTER);
        KnowledgeHandler.register(WISSEN_CASING);
        KnowledgeHandler.register(LIGHT_CASING);
        KnowledgeHandler.register(FLUID_CASING);
        KnowledgeHandler.register(STEAM_CASING);

        //CRYSTAL RITUALS
        KnowledgeHandler.register(LIGHT_EMITTER);
        KnowledgeHandler.register(RUNIC_PEDESTAL);
        KnowledgeHandler.register(CRYSTAL_INFUSION);
        KnowledgeHandler.register(INNOCENT_WOOD);

        KnowledgeHandler.register(INNOCENT_WOOD_TOOLS);
        KnowledgeHandler.register(MUSIC_DISC_DISCO);

        //ALCHEMY
        KnowledgeHandler.register(PETALS);
        KnowledgeHandler.register(WISESTONE);
        KnowledgeHandler.register(FLUID_PIPE);
        KnowledgeHandler.register(STEAM_PIPE);
        KnowledgeHandler.register(ORBITAL_FLUID_RETAINER);
        KnowledgeHandler.register(ALCHEMY_FURNACE);
        KnowledgeHandler.register(ALCHEMY_MACHINE);
        KnowledgeHandler.register(ALCHEMY_OIL);
        KnowledgeHandler.register(ALCHEMY_CALX);
        KnowledgeHandler.register(ALCHEMY_GLASS);
        KnowledgeHandler.register(ARCANE_CENSER);
        KnowledgeHandler.register(ARCANE_WOOD_SMOKING_PIPE);
        KnowledgeHandler.register(ARCACITE);
        KnowledgeHandler.register(ARCACITE_POLISHING_MIXTURE);
        KnowledgeHandler.register(SNIFFALO_EGG);

        KnowledgeHandler.register(MOR);
        KnowledgeHandler.register(ELDER_MOR);
        KnowledgeHandler.register(MUSIC_DISC_ARCANUM);
        KnowledgeHandler.register(MUSIC_DISC_MOR);
        KnowledgeHandler.register(NETHER_SALT);
        KnowledgeHandler.register(NATURAL_CALX);
        KnowledgeHandler.register(SCORCHED_CALX);
        KnowledgeHandler.register(DISTANT_CALX);
        KnowledgeHandler.register(ENCHANTED_CALX);
        KnowledgeHandler.register(ALCHEMY_VIAL);
        KnowledgeHandler.register(ALCHEMY_BAG);

        initProgression();
        initAdditionalProgression();
    }

    public static void initProgression() {
        addProgression(ARCANEMICON);
        addProgression(ARCANUM_DUST);
        addProgression(ARCANE_WOOD, ARCANUM_DUST);
        addProgression(ARCANE_GOLD, ARCANUM_DUST);
        addProgression(WISSEN_CRYSTALLIZER, ARCANE_GOLD);
        addProgression(ARCANE_WORKBENCH, ARCANE_GOLD);
        addProgression(ARCANE_WAND, ARCANE_WORKBENCH);
        addProgression(WISESTONE, ARCANE_WORKBENCH);
        addProgression(ORBITAL_FLUID_RETAINER, WISESTONE);
        addProgression(ALCHEMY_FURNACE, ORBITAL_FLUID_RETAINER);
        addProgression(ALCHEMY_MACHINE, ALCHEMY_FURNACE);
        addProgression(ALCHEMY_OIL, ALCHEMY_MACHINE);
        addProgression(ALCHEMY_CALX, ALCHEMY_OIL);
        addProgression(ALCHEMY_GLASS, ALCHEMY_CALX);
        addProgression(ARCANE_CENSER, ALCHEMY_GLASS);
        addProgression(ARCACITE, ALCHEMY_GLASS);
        addProgression(VOID_CRYSTAL, ARCACITE);
        addProgression(ARCACITE_POLISHING_MIXTURE, ARCACITE);
        addProgression(FACETED_CRYSTALS, ARCACITE_POLISHING_MIXTURE);
        addProgression(ARCANE_ITERATOR, ARCACITE);
        addProgression(JEWELER_TABLE, ARCANE_ITERATOR);
        addProgression(ARCANUM_LENS, ARCANE_ITERATOR);
        addProgression(LIGHT_EMITTER, ARCANUM_LENS);
        addProgression(RUNIC_PEDESTAL, ARCANUM_LENS);
        addProgression(CRYSTAL_INFUSION, RUNIC_PEDESTAL);
        addProgression(INNOCENT_WOOD, CRYSTAL_INFUSION);
    }

    public static void initAdditionalProgression() {
        addAdditionalProgression(MOR);
        addAdditionalProgression(ELDER_MOR);
        addAdditionalProgression(PETALS, ARCANE_WOOD);
        addAdditionalProgression(ARCANE_WOOD_TOOLS, ARCANE_WOOD);
        addAdditionalProgression(ARCANE_GOLD_TOOLS, ARCANE_GOLD);
        addAdditionalProgression(SCYTHES, ARCANE_GOLD);
        addAdditionalProgression(MUSIC_DISC_SHIMMER, WISSEN_CRYSTALLIZER);
        addAdditionalProgression(ARCANE_LUMOS, WISSEN_CRYSTALLIZER);
        addAdditionalProgression(CRYSTALS_SEEDS, WISSEN_CRYSTALLIZER);
        addAdditionalProgression(EARTH_CRYSTAL, WISSEN_CRYSTALLIZER);
        addAdditionalProgression(WATER_CRYSTAL, WISSEN_CRYSTALLIZER);
        addAdditionalProgression(AIR_CRYSTAL, WISSEN_CRYSTALLIZER);
        addAdditionalProgression(FIRE_CRYSTAL, WISSEN_CRYSTALLIZER);
        addAdditionalProgression(ARCANE_HOPPER, ARCANE_WORKBENCH);
        addAdditionalProgression(REDSTONE_SENSOR, ARCANE_WORKBENCH);
        addAdditionalProgression(WISSEN_SENSOR, ARCANE_WORKBENCH);
        addAdditionalProgression(COOLDOWN_SENSOR, ARCANE_WORKBENCH);
        addAdditionalProgression(WISSEN_ACTIVATOR, ARCANE_WORKBENCH);
        addAdditionalProgression(WISSEN_CASING, ARCANE_WORKBENCH);
        addAdditionalProgression(ARCANE_LEVER, WISESTONE);
        addAdditionalProgression(FLUID_PIPE, WISESTONE);
        addAdditionalProgression(FLUID_SENSOR, FLUID_PIPE);
        addAdditionalProgression(FLUID_CASING, FLUID_PIPE);
        addAdditionalProgression(STEAM_PIPE, WISESTONE);
        addAdditionalProgression(STEAM_SENSOR, STEAM_PIPE);
        addAdditionalProgression(STEAM_CASING, STEAM_PIPE);
        addAdditionalProgression(HEAT_SENSOR, ALCHEMY_FURNACE);
        addAdditionalProgression(EXPERIENCE_SENSOR, ALCHEMY_GLASS);
        addAdditionalProgression(ITEM_SORTER, ALCHEMY_GLASS);
        addAdditionalProgression(MUSIC_DISC_ARCANUM, ALCHEMY_OIL);
        addAdditionalProgression(MUSIC_DISC_MOR, ALCHEMY_OIL);
        addAdditionalProgression(NETHER_SALT, ALCHEMY_OIL);
        addAdditionalProgression(NATURAL_CALX, ALCHEMY_CALX);
        addAdditionalProgression(SCORCHED_CALX, ALCHEMY_CALX);
        addAdditionalProgression(DISTANT_CALX, ALCHEMY_CALX);
        addAdditionalProgression(ENCHANTED_CALX, ALCHEMY_CALX);
        addAdditionalProgression(ALCHEMY_VIAL, ALCHEMY_CALX);
        addAdditionalProgression(CRYSTAL_BAG, ALCHEMY_CALX);
        addAdditionalProgression(ALCHEMY_BAG, ALCHEMY_CALX);
        addAdditionalProgression(ARCANE_WOOD_SMOKING_PIPE, ARCANE_CENSER);
        addAdditionalProgression(ARCANE_FORTRESS_BELT, ARCACITE);
        addAdditionalProgression(ARCANE_WOOD_CANE, ARCACITE);
        addAdditionalProgression(KNOWLEDGE_SCROLL, ARCANE_ITERATOR);
        addAdditionalProgression(WISSEN_KEYCHAIN, ARCANE_ITERATOR);
        addAdditionalProgression(WISSEN_RING, ARCANE_ITERATOR);
        addAdditionalProgression(MUSIC_DISC_REBORN, ARCANE_ITERATOR);
        addAdditionalProgression(MUSIC_DISC_PANACHE, ARCANE_ITERATOR);
        addAdditionalProgression(MUSIC_DISC_CAPITALISM, ARCANE_ITERATOR);
        addAdditionalProgression(LIGHT_SENSOR, LIGHT_EMITTER);
        addAdditionalProgression(LIGHT_CASING, LIGHT_EMITTER);
        addAdditionalProgression(INNOCENT_WOOD_TOOLS, INNOCENT_WOOD);
        addAdditionalProgression(MUSIC_DISC_DISCO, INNOCENT_WOOD);
    }

    public static void addProgression(Knowledge knowledge) {
        progression.add(knowledge);
    }

    public static void addProgression(Knowledge knowledge, Knowledge knowledgePrevious) {
        addProgression(knowledge);
        knowledge.addPrevious(knowledgePrevious);
    }

    public static void addAdditionalProgression(Knowledge knowledge) {
        additionalProgression.add(knowledge);
    }

    public static void addAdditionalProgression(Knowledge knowledge, Knowledge knowledgePrevious) {
        addAdditionalProgression(knowledge);
        knowledge.addPrevious(knowledgePrevious);
    }
}