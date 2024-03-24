package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import net.minecraft.world.item.ItemStack;

public class RegisterKnowledges {
    //START
    public static ArcanemiconOfferingKnowledge ARCANEMICON_OFFERING = new ArcanemiconOfferingKnowledge(WizardsReborn.MOD_ID+":arcanemicon_offering", false, 0);
    public static ArcanemiconKnowledge ARCANEMICON = new ArcanemiconKnowledge(WizardsReborn.MOD_ID+":arcanemicon", false, 0, WizardsReborn.ARCANEMICON.get());
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

    public static ItemTagKnowledge ARCANE_LUMOS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":arcane_lumos", false, 5, WizardsReborn.ARCANE_LUMOS_ITEM_TAG, new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS.get()));
    public static ItemTagKnowledge CRYSTALS_SEEDS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":crystal_seeds", false, 3, WizardsReborn.CRYSTALS_SEEDS_ITEM_TAG, new ItemStack(WizardsReborn.EARTH_CRYSTAL_SEED.get()));
    public static ItemKnowledge EARTH_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":earth_crystal", false, 5, WizardsReborn.EARTH_CRYSTAL.get());
    public static ItemKnowledge WATER_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":water_crystal", false, 5, WizardsReborn.WATER_CRYSTAL.get());
    public static ItemKnowledge AIR_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":air_crystal", false, 5, WizardsReborn.AIR_CRYSTAL.get());
    public static ItemKnowledge FIRE_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":fire_crystal", false, 5, WizardsReborn.FIRE_CRYSTAL.get());
    public static ItemKnowledge ARCANE_WOOD_CANE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_wood_cane", false, 7, WizardsReborn.ARCANE_WOOD_CANE.get());

    //AUTOMATION
    public static ItemKnowledge ARCANE_LEVER = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_lever", false, 2, WizardsReborn.ARCANE_LEVER_ITEM.get());
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

    //CRYSTAl RITUALS
    public static ItemKnowledge LIGHT_EMITTER = new ItemKnowledge(WizardsReborn.MOD_ID+":light_emitter", true, 10, WizardsReborn.LIGHT_EMITTER_ITEM.get());
    public static ItemKnowledge RUNIC_PEDESTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":runic_pedestal", true, 10, WizardsReborn.RUNIC_PEDESTAL_ITEM.get());
    public static CrystalRitualKnowledge CRYSTAL_INFUSION = new CrystalRitualKnowledge(WizardsReborn.MOD_ID+":crystal_infusion", true, 10, WizardsReborn.CRYSTAL_INFUSION_CRYSTAL_RITUAL);

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
        Knowledges.register(ARCANEMICON_OFFERING);
        Knowledges.register(ARCANEMICON);
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

        //CRYSTAl RITUALS
        Knowledges.register(LIGHT_EMITTER);
        Knowledges.register(RUNIC_PEDESTAL);
        Knowledges.register(CRYSTAL_INFUSION);

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
    }
}