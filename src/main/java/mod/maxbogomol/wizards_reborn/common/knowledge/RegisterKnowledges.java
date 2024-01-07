package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledges;
import net.minecraft.world.item.ItemStack;

public class RegisterKnowledges {
    //ITEM KNOWLEDGES
    public static ItemKnowledge ARCANUM = new ArcanumKnowledge(WizardsReborn.MOD_ID+":arcanum", false, 0, WizardsReborn.ARCANUM.get());
    public static ItemKnowledge ARCANUM_DUST = new ItemKnowledge(WizardsReborn.MOD_ID+":arcanum_dust", true, 10, WizardsReborn.ARCANUM_DUST.get());
    public static ItemTagKnowledge ARCANE_WOOD = new ItemTagKnowledge(WizardsReborn.MOD_ID+":arcane_wood", true, 10, WizardsReborn.ARCANE_WOOD_LOGS_ITEM_TAG, new ItemStack(WizardsReborn.ARCANE_WOOD_LOG_ITEM.get()));
    public static ItemKnowledge ARCANE_GOLD = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_gold", true, 10, WizardsReborn.ARCANE_GOLD_INGOT.get());
    public static ItemKnowledge WISSEN_CRYSTALLIZER = new ItemKnowledge(WizardsReborn.MOD_ID+":wissen_crystallizer", true, 10, WizardsReborn.WISSEN_CRYSTALLIZER_ITEM.get());
    public static ItemKnowledge ARCANE_WORKBENCH = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_workbench", true, 10, WizardsReborn.ARCANE_WORKBENCH_ITEM.get());
    public static ItemTagKnowledge ARCANE_LUMOS = new ItemTagKnowledge(WizardsReborn.MOD_ID+":arcane_lumos", false, 5, WizardsReborn.ARCANE_LUMOS_ITEM_TAG, new ItemStack(WizardsReborn.WHITE_ARCANE_LUMOS.get()));
    public static ItemKnowledge ARCANE_WAND = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_wand", true, 10, WizardsReborn.ARCANE_WAND.get());
    public static ItemKnowledge EARTH_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":earth_crystal", false, 5, WizardsReborn.EARTH_CRYSTAL.get());
    public static ItemKnowledge WATER_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":water_crystal", false, 5, WizardsReborn.WATER_CRYSTAL.get());
    public static ItemKnowledge AIR_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":air_crystal", false, 5, WizardsReborn.AIR_CRYSTAL.get());
    public static ItemKnowledge FIRE_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":fire_crystal", false, 5, WizardsReborn.FIRE_CRYSTAL.get());
    public static ItemKnowledge VOID_CRYSTAL = new ItemKnowledge(WizardsReborn.MOD_ID+":void_crystal", false, 5, WizardsReborn.VOID_CRYSTAL.get());
    public static ItemKnowledge WISESTONE = new ItemKnowledge(WizardsReborn.MOD_ID+":wisestone", true, 10, WizardsReborn.WISESTONE_ITEM.get());
    public static ItemKnowledge ORBITAL_FLUID_RETAINER = new ItemKnowledge(WizardsReborn.MOD_ID+":orbital_fluid_retainer", true, 10, WizardsReborn.ORBITAL_FLUID_RETAINER_ITEM.get());
    public static ItemKnowledge ALCHEMY_FURNACE = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_furnace", true, 10, WizardsReborn.ALCHEMY_FURNACE_ITEM.get());
    public static ItemKnowledge ALCHEMY_MACHINE = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_machine", true, 10, WizardsReborn.ALCHEMY_MACHINE_ITEM.get());
    public static ItemKnowledge ARCACITE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcacite", true, 10, WizardsReborn.ARCACITE.get());
    public static ItemKnowledge ARCANE_ITERATOR = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_iterator", true, 10, WizardsReborn.ARCANE_ITERATOR_ITEM.get());
    public static ItemKnowledge ARCANE_CENSER = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_censer", true, 10, WizardsReborn.ARCANE_CENSER_ITEM.get());
    public static ItemKnowledge REDSTONE_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":redstone_sensor", false, 3, WizardsReborn.REDSTONE_SENSOR_ITEM.get());
    public static ItemKnowledge WISSEN_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":wissen_sensor", false, 3, WizardsReborn.WISSEN_SENSOR_ITEM.get());
    public static ItemKnowledge COOLDOWN_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":cooldown_sensor", false, 3, WizardsReborn.COOLDOWN_SENSOR_ITEM.get());
    public static ItemKnowledge HEAT_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":heat_sensor", false, 3, WizardsReborn.HEAT_SENSOR_ITEM.get());
    public static ItemKnowledge FLUID_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":fluid_sensor", false, 3, WizardsReborn.FLUID_SENSOR_ITEM.get());
    public static ItemKnowledge STEAM_SENSOR = new ItemKnowledge(WizardsReborn.MOD_ID+":steam_sensor", false, 3, WizardsReborn.STEAM_SENSOR_ITEM.get());
    public static ItemKnowledge WISSEN_ACTIVATOR = new ItemKnowledge(WizardsReborn.MOD_ID+":wissen_activator", false, 3, WizardsReborn.WISSEN_ACTIVATOR_ITEM.get());
    public static ItemKnowledge ITEM_SORTER = new ItemKnowledge(WizardsReborn.MOD_ID+":item_sorter", false, 3, WizardsReborn.ITEM_SORTER_ITEM.get());
    public static ItemKnowledge PETALS = new ItemKnowledge(WizardsReborn.MOD_ID+":petals", false, 2, WizardsReborn.PETALS.get());
    public static ItemKnowledge ARCANE_WOOD_SMOKING_PIPE = new ItemKnowledge(WizardsReborn.MOD_ID+":arcane_wood_smoking_pipe", false, 7, WizardsReborn.ARCANE_WOOD_SMOKING_PIPE.get());
    public static ItemKnowledge ALCHEMY_GLASS = new ItemKnowledge(WizardsReborn.MOD_ID+":alchemy_glass", true, 10, WizardsReborn.ALCHEMY_GLASS_ITEM.get());

    public static void init() {
        Knowledges.register(ARCANUM);
        Knowledges.register(ARCANUM_DUST);
        Knowledges.register(ARCANE_WOOD);
        Knowledges.register(ARCANE_GOLD);
        Knowledges.register(WISSEN_CRYSTALLIZER);
        Knowledges.register(ARCANE_WORKBENCH);
        Knowledges.register(ARCANE_LUMOS);
        Knowledges.register(ARCANE_WAND);
        Knowledges.register(EARTH_CRYSTAL);
        Knowledges.register(WATER_CRYSTAL);
        Knowledges.register(AIR_CRYSTAL);
        Knowledges.register(FIRE_CRYSTAL);
        Knowledges.register(VOID_CRYSTAL);
        Knowledges.register(WISESTONE);
        Knowledges.register(ORBITAL_FLUID_RETAINER);
        Knowledges.register(ALCHEMY_FURNACE);
        Knowledges.register(ALCHEMY_MACHINE);
        Knowledges.register(ARCACITE);
        Knowledges.register(ARCANE_ITERATOR);
        Knowledges.register(ARCANE_CENSER);
        Knowledges.register(REDSTONE_SENSOR);
        Knowledges.register(WISSEN_SENSOR);
        Knowledges.register(COOLDOWN_SENSOR);
        Knowledges.register(HEAT_SENSOR);
        Knowledges.register(FLUID_SENSOR);
        Knowledges.register(STEAM_SENSOR);
        Knowledges.register(WISSEN_ACTIVATOR);
        Knowledges.register(ITEM_SORTER);
        Knowledges.register(PETALS);
        Knowledges.register(ARCANE_WOOD_SMOKING_PIPE);
        Knowledges.register(ALCHEMY_GLASS);
    }
}