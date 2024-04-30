package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class RegisterKnowledges {
    //PROGRESSION
    public static List<Knowledge> progression = new ArrayList<>();
    public static List<Knowledge> additionalProgression = new ArrayList<>();

    //START
    public static ArcanemiconKnowledge ARCANEMICON = new ArcanemiconKnowledge(WizardsReborn.MOD_ID+":arcanemicon", false, 0, WizardsReborn.ARCANEMICON.get());
    public static ArcanemiconOfferingKnowledge ARCANEMICON_OFFERING = new ArcanemiconOfferingKnowledge(WizardsReborn.MOD_ID+":arcanemicon_offering", false, 0);
    public static ArcanumKnowledge ARCANUM = new ArcanumKnowledge(WizardsReborn.MOD_ID+":arcanum", false, 0, WizardsReborn.ARCANUM.get());

    //ARCANE NATURE
    public static ItemKnowledge ARCANUM_DUST = new ItemKnowledge(WizardsReborn.MOD_ID+":arcanum_dust", true, 10, WizardsReborn.ARCANUM_DUST.get());
    public static ItemTagKnowledge ARCANE_WOOD = new ItemTagKnowledge(WizardsReborn.MOD_ID+":arcane_wood", true, 10, WizardsReborn.ARCANE_WOOD_LOGS_ITEM_TAG, new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get()));
    public static ItemKnowledge ARCANE_GOLD = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_gold", true, 10, WizardsReborn.ARCANE_GOLD_INGOT.get());
    public static ItemKnowledge WISSEN_CRYSTALLIZER = new ItemKnowledge(WizardsReborn.MOD_ID+":wissen_crystallizer", true, 10, WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get());
    public static ItemKnowledge ARCANE_WORKBENCH = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_workbench", true, 10, WizardsReborn.ARCANE_WORKBENCH_ITEM.get());
    public static ItemKnowledge ARCANE_WAND = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_wand", true, 10, WizardsReborn.ARCANE_WAND.get());
    public static ItemKnowledge VOID_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":void_crystal", true, 10, WizardsReborn.VOID_CRYSTAL.get());
    public static ItemKnowledge ARCANE_ITERATOR = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_iterator", true, 10, WizardsReborn.ARCANE_ITERATOR_ITEM.get());
    public static ItemKnowledge ARCANUM_LENS = new ItemKnowledge(WizardsReborn.MOD_ID+":arcanum_lens", true, 10, WizardsReborn.ARCANUM_LENS.get());
    public static ItemKnowledge JEWELER_TABLE = new ItemKnowledge(WizardsReborn.MOD_ID+":jeweler_table", false, 8, WizardsReborn.JEWELER_TABLE_ITEM.get());
    public static ItemTagKnowledge FACETED_CRYSTALS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":faceted_crystals", true, 10, WizardsReborn.FACETED_CRYSTALS_ITEM_TAG, new ItemStack(WizardsReborn.FACETED_EARTH_CRYSTAL.get()));

    public static ItemTagKnowledge ARCANE_WOOD_TOOLS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":arcane_wood_tools", false, 2, WizardsReborn.ARCANE_WOOD_TOOLS_ITEM_TAG, new ItemStack(WizardsReborn.ARCANE_WOOD_PICKAXE.get()));
    public static ItemTagKnowledge ARCANE_GOLD_TOOLS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":arcane_gold_tools", false, 5, WizardsReborn.ARCANE_GOLD_TOOLS_ITEM_TAG, new ItemStack(WizardsReborn.ARCANE_GOLD_PICKAXE.get()));
    public static ItemTagKnowledge SCYTHES = new ItemTagKnowledge(WizardsReborn.MOD_ID+":scythes", false, 5, WizardsReborn.SCYTHES_ITEM_TAG, new ItemStack(WizardsReborn.ARCANE_GOLD_SCYTHE.get()));
    public static ItemTagKnowledge ARCANE_LUMOS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":arcane_lumos", false, 5, WizardsReborn.ARCANE_LUMOS_ITEM_TAG, new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS.get()));
    public static ItemTagKnowledge CRYSTALS_SEEDS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":crystal_seeds", false, 3, WizardsReborn.CRYSTALS_SEEDS_ITEM_TAG, new ItemStack(WizardsReborn.EARTH_CRYSTAL_SEED.get()));
    public static ItemKnowledge EARTH_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":earth_crystal", false, 5, WizardsReborn.EARTH_CRYSTAL.get());
    public static ItemKnowledge WATER_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":water_crystal", false, 5, WizardsReborn.WATER_CRYSTAL.get());
    public static ItemKnowledge AIR_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":air_crystal", false, 5, WizardsReborn.AIR_CRYSTAL.get());
    public static ItemKnowledge FIRE_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":fire_crystal", false, 5, WizardsReborn.FIRE_CRYSTAL.get());
    public static ItemKnowledge ARCANE_WOOD_CANE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_wood_cane", false, 7, WizardsReborn.ARCANE_WOOD_CANE.get());

    //AUTOMATION
    public static ItemKnowledge ARCANE_LEVER = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_lever", false, 2, WizardsReborn.ARCANE_LEVER_ITEM.get());
    public static ItemKnowledge ARCANE_HOPPER = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_hopper", false, 5, WizardsReborn.ARCANE_HOPPER_ITEM.get());
    public static ItemKnowledge REDSTONE_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":redstone_sensor", false, 6, WizardsReborn.REDSTONE_SENSOR_ITEM.get());
    public static ItemKnowledge WISSEN_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":wissen_sensor", false, 4, WizardsReborn.WISSEN_SENSOR_ITEM.get());
    public static ItemKnowledge COOLDOWN_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":cooldown_sensor", false, 4, WizardsReborn.COOLDOWN_SENSOR_ITEM.get());
    public static ItemKnowledge EXPERIENCE_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":experience_sensor", false, 4, WizardsReborn.EXPERIENCE_SENSOR_ITEM.get());
    public static ItemKnowledge LIGHT_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":light_sensor", false, 4, WizardsReborn.LIGHT_SENSOR_ITEM.get());
    public static ItemKnowledge HEAT_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":heat_sensor", false, 4, WizardsReborn.HEAT_SENSOR_ITEM.get());
    public static ItemKnowledge FLUID_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":fluid_sensor", false, 4, WizardsReborn.FLUID_SENSOR_ITEM.get());
    public static ItemKnowledge STEAM_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":steam_sensor", false, 4, WizardsReborn.STEAM_SENSOR_ITEM.get());
    public static ItemKnowledge WISSEN_ACTIVATOR = new ItemKnowledge(WizardsReborn.MOD_ID+":wissen_activator", false, 4, WizardsReborn.WISSEN_ACTIVATOR_ITEM.get());
    public static ItemKnowledge ITEM_SORTER = new ItemKnowledge(WizardsReborn.MOD_ID+":item_sorter", false, 4, WizardsReborn.ITEM_SORTER_ITEM.get());
    public static ItemTagKnowledge WISSEN_CASING = new ItemTagKnowledge(WizardsReborn.MOD_ID+":wissen_casing", false, 5, WizardsReborn.WISSEN_CASINGS_ITEM_TAG, new ItemStack(WizardsReborn.ARCANE_WOOD_WISSEN_CASING_ITEM.get()));
    public static ItemTagKnowledge LIGHT_CASING = new ItemTagKnowledge(WizardsReborn.MOD_ID+":light_casing", false, 5, WizardsReborn.LIGHT_CASINGS_ITEM_TAG, new ItemStack(WizardsReborn.ARCANE_WOOD_LIGHT_CASING_ITEM.get()));
    public static ItemTagKnowledge FLUID_CASING = new ItemTagKnowledge(WizardsReborn.MOD_ID+":fluid_casing", false, 5, WizardsReborn.FLUID_CASINGS_ITEM_TAG, new ItemStack(WizardsReborn.ARCANE_WOOD_FLUID_CASING_ITEM.get()));
    public static ItemTagKnowledge STEAM_CASING = new ItemTagKnowledge(WizardsReborn.MOD_ID+":steam_casing", false, 5, WizardsReborn.STEAM_CASINGS_ITEM_TAG, new ItemStack(WizardsReborn.ARCANE_WOOD_STEAM_CASING_ITEM.get()));

    //CRYSTAL RITUALS
    public static ItemKnowledge LIGHT_EMITTER = new ItemKnowledge(WizardsReborn.MOD_ID+":light_emitter", true, 10, WizardsReborn.LIGHT_EMITTER_ITEM.get());
    public static ItemKnowledge RUNIC_PEDESTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":runic_pedestal", true, 10, WizardsReborn.RUNIC_PEDESTAL_ITEM.get());
    public static CrystalRitualKnowledge CRYSTAL_INFUSION = new CrystalRitualKnowledge(WizardsReborn.MOD_ID+":crystal_infusion", true, 10, WizardsReborn.CRYSTAL_INFUSION_CRYSTAL_RITUAL);
    public static ItemTagKnowledge INNOCENT_WOOD = new ItemTagKnowledge(WizardsReborn.MOD_ID+":innocent_wood", true, 10, WizardsReborn.INNOCENT_WOOD_LOGS_ITEM_TAG, new ItemStack(WizardsReborn.INNOCENT_WOOD_LOG_ITEM.get()));
    public static ItemTagKnowledge INNOCENT_WOOD_TOOLS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":innocent_wood_tools", false, 5, WizardsReborn.INNOCENT_WOOD_TOOLS_ITEM_TAG, new ItemStack(WizardsReborn.INNOCENT_WOOD_PICKAXE.get()));

    //ALCHEMY
    public static ItemKnowledge PETALS = new ItemKnowledge(WizardsReborn.MOD_ID+":petals", false, 3, WizardsReborn.PETALS.get());
    public static ItemKnowledge WISESTONE = new ItemKnowledge(WizardsReborn.MOD_ID+":wisestone", true, 10, WizardsReborn.WISESTONE_ITEM.get());
    public static ItemKnowledge FLUID_PIPE = new ItemKnowledge(WizardsReborn.MOD_ID+":fluid_pipe", false, 5, WizardsReborn.FLUID_PIPE_ITEM.get());
    public static ItemKnowledge STEAM_PIPE = new ItemKnowledge(WizardsReborn.MOD_ID+":steam_pipe", false, 5, WizardsReborn.STEAM_PIPE_ITEM.get());
    public static ItemKnowledge ORBITAL_FLUID_RETAINER = new ItemKnowledge(WizardsReborn.MOD_ID+":orbital_fluid_retainer", true, 10, WizardsReborn.ORBITAL_FLUID_RETAINER_ITEM.get());
    public static ItemKnowledge ALCHEMY_FURNACE = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_furnace", true, 10, WizardsReborn.ALCHEMY_FURNACE_ITEM.get());
    public static ItemKnowledge ALCHEMY_MACHINE = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_machine", true, 10, WizardsReborn.ALCHEMY_MACHINE_ITEM.get());
    public static ItemKnowledge ALCHEMY_OIL = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_oil", true, 10, WizardsReborn.ALCHEMY_OIL_BUCKET.get());
    public static ItemKnowledge ALCHEMY_CALX = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_calx", true, 10, WizardsReborn.ALCHEMY_CALX.get());
    public static ItemKnowledge ALCHEMY_GLASS = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_glass", true, 10, WizardsReborn.ALCHEMY_GLASS_ITEM.get());
    public static ItemKnowledge ARCANE_CENSER = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_censer", true, 10, WizardsReborn.ARCANE_CENSER_ITEM.get());
    public static ItemKnowledge ARCANE_WOOD_SMOKING_PIPE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_wood_smoking_pipe", false, 7, WizardsReborn.ARCANE_WOOD_SMOKING_PIPE.get());
    public static ItemKnowledge ARCACITE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcacite", true, 10, WizardsReborn.ARCACITE.get());
    public static ItemKnowledge ARCACITE_POLISHING_MIXTURE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcacite_polishing_mixture", true, 10, WizardsReborn.ARCACITE_POLISHING_MIXTURE.get());

    public static void init() {
        //START
        Knowledges.register(ARCANEMICON);
        Knowledges.register(ARCANEMICON_OFFERING);
        Knowledges.register(ARCANUM);

        //ARCANE NATURE
        Knowledges.register(ARCANUM_DUST);
        Knowledges.register(ARCANE_WOOD);
        Knowledges.register(ARCANE_GOLD);
        Knowledges.register(WISSEN_CRYSTALLIZER);
        Knowledges.register(ARCANE_WORKBENCH);
        Knowledges.register(ARCANE_WAND);
        Knowledges.register(ARCANE_ITERATOR);
        Knowledges.register(ARCANUM_LENS);
        Knowledges.register(JEWELER_TABLE);
        Knowledges.register(FACETED_CRYSTALS);

        Knowledges.register(ARCANE_WOOD_TOOLS);
        Knowledges.register(ARCANE_GOLD_TOOLS);
        Knowledges.register(SCYTHES);
        Knowledges.register(ARCANE_LUMOS);
        Knowledges.register(CRYSTALS_SEEDS);
        Knowledges.register(EARTH_CRYSTAL);
        Knowledges.register(WATER_CRYSTAL);
        Knowledges.register(AIR_CRYSTAL);
        Knowledges.register(FIRE_CRYSTAL);
        Knowledges.register(VOID_CRYSTAL);
        Knowledges.register(ARCANE_WOOD_CANE);

        //AUTOMATION
        Knowledges.register(ARCANE_LEVER);
        Knowledges.register(ARCANE_HOPPER);
        Knowledges.register(REDSTONE_SENSOR);
        Knowledges.register(WISSEN_SENSOR);
        Knowledges.register(COOLDOWN_SENSOR);
        Knowledges.register(EXPERIENCE_SENSOR);
        Knowledges.register(LIGHT_SENSOR);
        Knowledges.register(HEAT_SENSOR);
        Knowledges.register(FLUID_SENSOR);
        Knowledges.register(STEAM_SENSOR);
        Knowledges.register(WISSEN_ACTIVATOR);
        Knowledges.register(ITEM_SORTER);
        Knowledges.register(WISSEN_CASING);
        Knowledges.register(LIGHT_CASING);
        Knowledges.register(FLUID_CASING);
        Knowledges.register(STEAM_CASING);

        //CRYSTAL RITUALS
        Knowledges.register(LIGHT_EMITTER);
        Knowledges.register(RUNIC_PEDESTAL);
        Knowledges.register(CRYSTAL_INFUSION);
        Knowledges.register(INNOCENT_WOOD);

        Knowledges.register(INNOCENT_WOOD_TOOLS);

        //ALCHEMY
        Knowledges.register(PETALS);
        Knowledges.register(WISESTONE);
        Knowledges.register(FLUID_PIPE);
        Knowledges.register(STEAM_PIPE);
        Knowledges.register(ORBITAL_FLUID_RETAINER);
        Knowledges.register(ALCHEMY_FURNACE);
        Knowledges.register(ALCHEMY_MACHINE);
        Knowledges.register(ALCHEMY_OIL);
        Knowledges.register(ALCHEMY_CALX);
        Knowledges.register(ALCHEMY_GLASS);
        Knowledges.register(ARCANE_CENSER);
        Knowledges.register(ARCANE_WOOD_SMOKING_PIPE);
        Knowledges.register(ARCACITE);
        Knowledges.register(ARCACITE_POLISHING_MIXTURE);

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
        addProgression(ARCANUM_LENS, ARCANE_ITERATOR);
        addProgression(LIGHT_EMITTER, ARCANUM_LENS);
        addProgression(RUNIC_PEDESTAL, ARCANUM_LENS);
        addProgression(CRYSTAL_INFUSION, RUNIC_PEDESTAL);
        addProgression(ARCANE_WOOD, CRYSTAL_INFUSION);
    }

    public static void initAdditionalProgression() {
        addAdditionalProgression(PETALS, ARCANE_WOOD);
        addAdditionalProgression(ARCANE_WOOD_TOOLS, ARCANE_WOOD);
        addAdditionalProgression(ARCANE_GOLD_TOOLS, ARCANE_GOLD);
        addAdditionalProgression(SCYTHES, ARCANE_GOLD);
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
        addAdditionalProgression(ARCANE_WOOD_SMOKING_PIPE, ARCANE_CENSER);
        addAdditionalProgression(ARCANE_WOOD_CANE, ARCACITE);
        addAdditionalProgression(JEWELER_TABLE, ARCACITE_POLISHING_MIXTURE);
        addAdditionalProgression(LIGHT_SENSOR, LIGHT_EMITTER);
        addAdditionalProgression(LIGHT_CASING, LIGHT_EMITTER);
        addAdditionalProgression(INNOCENT_WOOD_TOOLS, INNOCENT_WOOD);
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