package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class WizardsRebornSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, WizardsReborn.MOD_ID);

    public static final RegistryObject<SoundEvent> ARCANE_GOLD_BREAK = SOUND_EVENTS.register("arcane_gold_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_gold_break")));
    public static final RegistryObject<SoundEvent> ARCANE_GOLD_STEP = SOUND_EVENTS.register("arcane_gold_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_gold_step")));
    public static final RegistryObject<SoundEvent> ARCANE_GOLD_PLACE = SOUND_EVENTS.register("arcane_gold_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_gold_place")));
    public static final RegistryObject<SoundEvent> ARCANE_GOLD_HIT = SOUND_EVENTS.register("arcane_gold_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_gold_hit")));

    public static final RegistryObject<SoundEvent> ARCANE_GOLD_ORE_BREAK = SOUND_EVENTS.register("arcane_gold_ore_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_gold_ore_break")));
    public static final RegistryObject<SoundEvent> ARCANE_GOLD_ORE_STEP = SOUND_EVENTS.register("arcane_gold_ore_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_gold_ore_step")));
    public static final RegistryObject<SoundEvent> ARCANE_GOLD_ORE_PLACE = SOUND_EVENTS.register("arcane_gold_ore_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_gold_ore_place")));
    public static final RegistryObject<SoundEvent> ARCANE_GOLD_ORE_HIT = SOUND_EVENTS.register("arcane_gold_ore_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_gold_ore_hit")));

    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANE_GOLD_ORE_BREAK = SOUND_EVENTS.register("deepslate_arcane_gold_ore_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "deepslate_arcane_gold_ore_break")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANE_GOLD_ORE_STEP = SOUND_EVENTS.register("deepslate_arcane_gold_ore_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "deepslate_arcane_gold_ore_step")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANE_GOLD_ORE_PLACE = SOUND_EVENTS.register("deepslate_arcane_gold_ore_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "deepslate_arcane_gold_ore_place")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANE_GOLD_ORE_HIT = SOUND_EVENTS.register("deepslate_arcane_gold_ore_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "deepslate_arcane_gold_ore_hit")));

    public static final RegistryObject<SoundEvent> NETHER_ARCANE_GOLD_ORE_BREAK = SOUND_EVENTS.register("nether_arcane_gold_ore_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "nether_arcane_gold_ore_break")));
    public static final RegistryObject<SoundEvent> NETHER_ARCANE_GOLD_ORE_STEP = SOUND_EVENTS.register("nether_arcane_gold_ore_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "nether_arcane_gold_ore_step")));
    public static final RegistryObject<SoundEvent> NETHER_ARCANE_GOLD_ORE_PLACE = SOUND_EVENTS.register("nether_arcane_gold_ore_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "nether_arcane_gold_ore_place")));
    public static final RegistryObject<SoundEvent> NETHER_ARCANE_GOLD_ORE_HIT = SOUND_EVENTS.register("nether_arcane_gold_ore_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "nether_arcane_gold_ore_hit")));

    public static final RegistryObject<SoundEvent> RAW_ARCANE_GOLD_BREAK = SOUND_EVENTS.register("raw_arcane_gold_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "raw_arcane_gold_break")));
    public static final RegistryObject<SoundEvent> RAW_ARCANE_GOLD_STEP = SOUND_EVENTS.register("raw_arcane_gold_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "raw_arcane_gold_step")));
    public static final RegistryObject<SoundEvent> RAW_ARCANE_GOLD_PLACE = SOUND_EVENTS.register("raw_arcane_gold_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "raw_arcane_gold_place")));
    public static final RegistryObject<SoundEvent> RAW_ARCANE_GOLD_HIT = SOUND_EVENTS.register("raw_arcane_gold_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "raw_arcane_gold_hit")));

    public static final RegistryObject<SoundEvent> SARCON_BREAK = SOUND_EVENTS.register("sarcon_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "sarcon_break")));
    public static final RegistryObject<SoundEvent> SARCON_STEP = SOUND_EVENTS.register("sarcon_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "sarcon_step")));
    public static final RegistryObject<SoundEvent> SARCON_PLACE = SOUND_EVENTS.register("sarcon_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "sarcon_place")));
    public static final RegistryObject<SoundEvent> SARCON_HIT = SOUND_EVENTS.register("sarcon_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "sarcon_hit")));

    public static final RegistryObject<SoundEvent> VILENIUM_BREAK = SOUND_EVENTS.register("vilenium_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "vilenium_break")));
    public static final RegistryObject<SoundEvent> VILENIUM_STEP = SOUND_EVENTS.register("vilenium_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "vilenium_step")));
    public static final RegistryObject<SoundEvent> VILENIUM_PLACE = SOUND_EVENTS.register("vilenium_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "vilenium_place")));
    public static final RegistryObject<SoundEvent> VILENIUM_HIT = SOUND_EVENTS.register("vilenium_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "vilenium_hit")));

    public static final RegistryObject<SoundEvent> ARCANUM_BREAK = SOUND_EVENTS.register("arcanum_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_break")));
    public static final RegistryObject<SoundEvent> ARCANUM_STEP = SOUND_EVENTS.register("arcanum_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_step")));
    public static final RegistryObject<SoundEvent> ARCANUM_PLACE = SOUND_EVENTS.register("arcanum_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_place")));
    public static final RegistryObject<SoundEvent> ARCANUM_HIT = SOUND_EVENTS.register("arcanum_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_hit")));

    public static final RegistryObject<SoundEvent> ARCANUM_ORE_BREAK = SOUND_EVENTS.register("arcanum_ore_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_ore_break")));
    public static final RegistryObject<SoundEvent> ARCANUM_ORE_STEP = SOUND_EVENTS.register("arcanum_ore_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_ore_step")));
    public static final RegistryObject<SoundEvent> ARCANUM_ORE_PLACE = SOUND_EVENTS.register("arcanum_ore_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_ore_place")));
    public static final RegistryObject<SoundEvent> ARCANUM_ORE_HIT = SOUND_EVENTS.register("arcanum_ore_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_ore_hit")));

    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANUM_ORE_BREAK = SOUND_EVENTS.register("deepslate_arcanum_ore_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "deepslate_arcanum_ore_break")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANUM_ORE_STEP = SOUND_EVENTS.register("deepslate_arcanum_ore_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "deepslate_arcanum_ore_step")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANUM_ORE_PLACE = SOUND_EVENTS.register("deepslate_arcanum_ore_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "deepslate_arcanum_ore_place")));
    public static final RegistryObject<SoundEvent> DEEPSLATE_ARCANUM_ORE_HIT = SOUND_EVENTS.register("deepslate_arcanum_ore_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "deepslate_arcanum_ore_hit")));

    public static final RegistryObject<SoundEvent> ARCACITE_BREAK = SOUND_EVENTS.register("arcacite_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcacite_break")));
    public static final RegistryObject<SoundEvent> ARCACITE_STEP = SOUND_EVENTS.register("arcacite_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcacite_step")));
    public static final RegistryObject<SoundEvent> ARCACITE_PLACE = SOUND_EVENTS.register("arcacite_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcacite_place")));
    public static final RegistryObject<SoundEvent> ARCACITE_HIT = SOUND_EVENTS.register("arcacite_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcacite_hit")));

    public static final RegistryObject<SoundEvent> PRECISION_CRYSTAL_BREAK = SOUND_EVENTS.register("precision_crystal_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "precision_crystal_break")));
    public static final RegistryObject<SoundEvent> PRECISION_CRYSTAL_STEP = SOUND_EVENTS.register("precision_crystal_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "precision_crystal_step")));
    public static final RegistryObject<SoundEvent> PRECISION_CRYSTAL_PLACE = SOUND_EVENTS.register("precision_crystal_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "precision_crystal_place")));
    public static final RegistryObject<SoundEvent> PRECISION_CRYSTAL_HIT = SOUND_EVENTS.register("precision_crystal_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "precision_crystal_hit")));

    public static final RegistryObject<SoundEvent> NETHER_SALT_BREAK = SOUND_EVENTS.register("nether_salt_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "nether_salt_break")));
    public static final RegistryObject<SoundEvent> NETHER_SALT_STEP = SOUND_EVENTS.register("nether_salt_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "nether_salt_step")));
    public static final RegistryObject<SoundEvent> NETHER_SALT_PLACE = SOUND_EVENTS.register("nether_salt_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "nether_salt_place")));
    public static final RegistryObject<SoundEvent> NETHER_SALT_HIT = SOUND_EVENTS.register("nether_salt_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "nether_salt_hit")));

    public static final RegistryObject<SoundEvent> NETHER_SALT_ORE_BREAK = SOUND_EVENTS.register("nether_salt_ore_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "nether_salt_ore_break")));
    public static final RegistryObject<SoundEvent> NETHER_SALT_ORE_STEP = SOUND_EVENTS.register("nether_salt_ore_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "nether_salt_ore_step")));
    public static final RegistryObject<SoundEvent> NETHER_SALT_ORE_PLACE = SOUND_EVENTS.register("nether_salt_ore_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "nether_salt_ore_place")));
    public static final RegistryObject<SoundEvent> NETHER_SALT_ORE_HIT = SOUND_EVENTS.register("nether_salt_ore_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "nether_salt_ore_hit")));

    public static final RegistryObject<SoundEvent> ARCANE_WOOD_BREAK = SOUND_EVENTS.register("arcane_wood_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_break")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_STEP = SOUND_EVENTS.register("arcane_wood_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_step")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_PLACE = SOUND_EVENTS.register("arcane_wood_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_place")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_HIT = SOUND_EVENTS.register("arcane_wood_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_hit")));

    public static final RegistryObject<SoundEvent> ARCANE_WOOD_HANGING_SIGN_BREAK = SOUND_EVENTS.register("arcane_wood_hanging_sign_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_hanging_sign_break")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_HANGING_SIGN_STEP = SOUND_EVENTS.register("arcane_wood_hanging_sign_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_hanging_sign_step")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_HANGING_SIGN_PLACE = SOUND_EVENTS.register("arcane_wood_hanging_sign_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_hanging_sign_place")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_HANGING_SIGN_HIT = SOUND_EVENTS.register("arcane_wood_hanging_sign_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_hanging_sign_hit")));

    public static final RegistryObject<SoundEvent> ARCANE_WOOD_BUTTON_CLICK_OFF = SOUND_EVENTS.register("arcane_wood_button_click_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_button_click_off")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_BUTTON_CLICK_ON = SOUND_EVENTS.register("arcane_wood_button_click_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_button_click_on")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_PRESSURE_PLATE_CLICK_OFF = SOUND_EVENTS.register("arcane_wood_pressure_plate_click_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_pressure_plate_click_off")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_PRESSURE_PLATE_CLICK_ON = SOUND_EVENTS.register("arcane_wood_pressure_plate_click_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_pressure_plate_click_on")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_FENCE_GATE_CLOSE = SOUND_EVENTS.register("arcane_wood_fence_gate_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_fence_gate_close")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_FENCE_GATE_OPEN = SOUND_EVENTS.register("arcane_wood_fence_gate_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_fence_gate_open")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_DOOR_CLOSE = SOUND_EVENTS.register("arcane_wood_door_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_door_close")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_DOOR_OPEN = SOUND_EVENTS.register("arcane_wood_door_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_door_open")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_TRAPDOOR_CLOSE = SOUND_EVENTS.register("arcane_wood_trapdoor_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_trapdoor_close")));
    public static final RegistryObject<SoundEvent> ARCANE_WOOD_TRAPDOOR_OPEN = SOUND_EVENTS.register("arcane_wood_trapdoor_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_wood_trapdoor_open")));

    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_BREAK = SOUND_EVENTS.register("innocent_wood_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_break")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_STEP = SOUND_EVENTS.register("innocent_wood_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_step")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_PLACE = SOUND_EVENTS.register("innocent_wood_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_place")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_HIT = SOUND_EVENTS.register("innocent_wood_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_hit")));

    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_HANGING_SIGN_BREAK = SOUND_EVENTS.register("innocent_wood_hanging_sign_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_hanging_sign_break")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_HANGING_SIGN_STEP = SOUND_EVENTS.register("innocent_wood_hanging_sign_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_hanging_sign_step")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_HANGING_SIGN_PLACE = SOUND_EVENTS.register("innocent_wood_hanging_sign_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_hanging_sign_place")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_HANGING_SIGN_HIT = SOUND_EVENTS.register("innocent_wood_hanging_sign_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_hanging_sign_hit")));

    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_BUTTON_CLICK_OFF = SOUND_EVENTS.register("innocent_wood_button_click_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_button_click_off")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_BUTTON_CLICK_ON = SOUND_EVENTS.register("innocent_wood_button_click_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_button_click_on")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_PRESSURE_PLATE_CLICK_OFF = SOUND_EVENTS.register("innocent_wood_pressure_plate_click_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_pressure_plate_click_off")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_PRESSURE_PLATE_CLICK_ON = SOUND_EVENTS.register("innocent_wood_pressure_plate_click_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_pressure_plate_click_on")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_FENCE_GATE_CLOSE = SOUND_EVENTS.register("innocent_wood_fence_gate_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_fence_gate_close")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_FENCE_GATE_OPEN = SOUND_EVENTS.register("innocent_wood_fence_gate_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_fence_gate_open")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_DOOR_CLOSE = SOUND_EVENTS.register("innocent_wood_door_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_door_close")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_DOOR_OPEN = SOUND_EVENTS.register("innocent_wood_door_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_door_open")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_TRAPDOOR_CLOSE = SOUND_EVENTS.register("innocent_wood_trapdoor_close", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_trapdoor_close")));
    public static final RegistryObject<SoundEvent> INNOCENT_WOOD_TRAPDOOR_OPEN = SOUND_EVENTS.register("innocent_wood_trapdoor_open", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "innocent_wood_trapdoor_open")));

    public static final RegistryObject<SoundEvent> WISESTONE_BREAK = SOUND_EVENTS.register("wisestone_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_break")));
    public static final RegistryObject<SoundEvent> WISESTONE_STEP = SOUND_EVENTS.register("wisestone_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_step")));
    public static final RegistryObject<SoundEvent> WISESTONE_PLACE = SOUND_EVENTS.register("wisestone_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_place")));
    public static final RegistryObject<SoundEvent> WISESTONE_HIT = SOUND_EVENTS.register("wisestone_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_hit")));

    public static final RegistryObject<SoundEvent> POLISHED_WISESTONE_BREAK = SOUND_EVENTS.register("polished_wisestone_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "polished_wisestone_break")));
    public static final RegistryObject<SoundEvent> POLISHED_WISESTONE_STEP = SOUND_EVENTS.register("polished_wisestone_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "polished_wisestone_step")));
    public static final RegistryObject<SoundEvent> POLISHED_WISESTONE_PLACE = SOUND_EVENTS.register("polished_wisestone_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "polished_wisestone_place")));
    public static final RegistryObject<SoundEvent> POLISHED_WISESTONE_HIT = SOUND_EVENTS.register("polished_wisestone_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "polished_wisestone_hit")));

    public static final RegistryObject<SoundEvent> WISESTONE_BRICKS_BREAK = SOUND_EVENTS.register("wisestone_bricks_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_bricks_break")));
    public static final RegistryObject<SoundEvent> WISESTONE_BRICKS_STEP = SOUND_EVENTS.register("wisestone_bricks_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_bricks_step")));
    public static final RegistryObject<SoundEvent> WISESTONE_BRICKS_PLACE = SOUND_EVENTS.register("wisestone_bricks_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_bricks_place")));
    public static final RegistryObject<SoundEvent> WISESTONE_BRICKS_HIT = SOUND_EVENTS.register("wisestone_bricks_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_bricks_hit")));

    public static final RegistryObject<SoundEvent> WISESTONE_TILE_BREAK = SOUND_EVENTS.register("wisestone_tile_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_tile_break")));
    public static final RegistryObject<SoundEvent> WISESTONE_TILE_STEP = SOUND_EVENTS.register("wisestone_tile_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_tile_step")));
    public static final RegistryObject<SoundEvent> WISESTONE_TILE_PLACE = SOUND_EVENTS.register("wisestone_tile_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_tile_place")));
    public static final RegistryObject<SoundEvent> WISESTONE_TILE_HIT = SOUND_EVENTS.register("wisestone_tile_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_tile_hit")));

    public static final RegistryObject<SoundEvent> CHISELED_WISESTONE_BREAK = SOUND_EVENTS.register("chiseled_wisestone_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "chiseled_wisestone_break")));
    public static final RegistryObject<SoundEvent> CHISELED_WISESTONE_STEP = SOUND_EVENTS.register("chiseled_wisestone_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "chiseled_wisestone_step")));
    public static final RegistryObject<SoundEvent> CHISELED_WISESTONE_PLACE = SOUND_EVENTS.register("chiseled_wisestone_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "chiseled_wisestone_place")));
    public static final RegistryObject<SoundEvent> CHISELED_WISESTONE_HIT = SOUND_EVENTS.register("chiseled_wisestone_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "chiseled_wisestone_hit")));

    public static final RegistryObject<SoundEvent> WISESTONE_BUTTON_CLICK_OFF = SOUND_EVENTS.register("wisestone_button_click_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_button_click_off")));
    public static final RegistryObject<SoundEvent> WISESTONE_BUTTON_CLICK_ON = SOUND_EVENTS.register("wisestone_button_click_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_button_click_on")));
    public static final RegistryObject<SoundEvent> WISESTONE_PRESSURE_PLATE_CLICK_OFF = SOUND_EVENTS.register("wisestone_pressure_plate_click_off", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_pressure_plate_click_off")));
    public static final RegistryObject<SoundEvent> WISESTONE_PRESSURE_PLATE_CLICK_ON = SOUND_EVENTS.register("wisestone_pressure_plate_click_on", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wisestone_pressure_plate_click_on")));

    public static final RegistryObject<SoundEvent> MOR_BREAK = SOUND_EVENTS.register("mor_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mor_break")));
    public static final RegistryObject<SoundEvent> MOR_STEP = SOUND_EVENTS.register("mor_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mor_step")));
    public static final RegistryObject<SoundEvent> MOR_PLACE = SOUND_EVENTS.register("mor_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mor_place")));
    public static final RegistryObject<SoundEvent> MOR_HIT = SOUND_EVENTS.register("mor_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mor_hit")));

    public static final RegistryObject<SoundEvent> ELDER_MOR_BREAK = SOUND_EVENTS.register("elder_mor_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "elder_mor_break")));
    public static final RegistryObject<SoundEvent> ELDER_MOR_STEP = SOUND_EVENTS.register("elder_mor_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "elder_mor_step")));
    public static final RegistryObject<SoundEvent> ELDER_MOR_PLACE = SOUND_EVENTS.register("elder_mor_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "elder_mor_place")));
    public static final RegistryObject<SoundEvent> ELDER_MOR_HIT = SOUND_EVENTS.register("elder_mor_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "elder_mor_hit")));

    public static final RegistryObject<SoundEvent> MOR_BLOCK_BREAK = SOUND_EVENTS.register("mor_block_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mor_block_break")));
    public static final RegistryObject<SoundEvent> MOR_BLOCK_STEP = SOUND_EVENTS.register("mor_block_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mor_block_step")));
    public static final RegistryObject<SoundEvent> MOR_BLOCK_PLACE = SOUND_EVENTS.register("mor_block_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mor_block_place")));
    public static final RegistryObject<SoundEvent> MOR_BLOCK_HIT = SOUND_EVENTS.register("mor_block_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mor_block_hit")));

    public static final RegistryObject<SoundEvent> ELDER_MOR_BLOCK_BREAK = SOUND_EVENTS.register("elder_mor_block_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "elder_mor_block_break")));
    public static final RegistryObject<SoundEvent> ELDER_MOR_BLOCK_STEP = SOUND_EVENTS.register("elder_mor_block_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "elder_mor_block_step")));
    public static final RegistryObject<SoundEvent> ELDER_MOR_BLOCK_PLACE = SOUND_EVENTS.register("elder_mor_block_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "elder_mor_block_place")));
    public static final RegistryObject<SoundEvent> ELDER_MOR_BLOCK_HIT = SOUND_EVENTS.register("elder_mor_block_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "elder_mor_block_hit")));

    public static final RegistryObject<SoundEvent> MIXTURE_BREAK = SOUND_EVENTS.register("mixture_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mixture_break")));
    public static final RegistryObject<SoundEvent> MIXTURE_STEP = SOUND_EVENTS.register("mixture_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mixture_step")));
    public static final RegistryObject<SoundEvent> MIXTURE_PLACE = SOUND_EVENTS.register("mixture_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mixture_place")));
    public static final RegistryObject<SoundEvent> MIXTURE_HIT = SOUND_EVENTS.register("mixture_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mixture_hit")));

    public static final RegistryObject<SoundEvent> CRYSTAL_BREAK = SOUND_EVENTS.register("crystal_break", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "crystal_break")));
    public static final RegistryObject<SoundEvent> CRYSTAL_STEP = SOUND_EVENTS.register("crystal_step", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "crystal_step")));
    public static final RegistryObject<SoundEvent> CRYSTAL_PLACE = SOUND_EVENTS.register("crystal_place", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "crystal_place")));
    public static final RegistryObject<SoundEvent> CRYSTAL_HIT = SOUND_EVENTS.register("crystal_hit", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "crystal_hit")));

    public static final RegistryObject<SoundEvent> CRYSTAL_RESONATE = SOUND_EVENTS.register("crystal_resonate", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "crystal_resonate")));
    public static final RegistryObject<SoundEvent> CRYSTAL_SHIMMER = SOUND_EVENTS.register("crystal_shimmer", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "crystal_shimmer")));

    public static final RegistryObject<SoundEvent> PEDESTAL_INSERT = SOUND_EVENTS.register("pedestal_insert", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "pedestal_insert")));
    public static final RegistryObject<SoundEvent> PEDESTAL_REMOVE = SOUND_EVENTS.register("pedestal_remove", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "pedestal_remove")));

    public static final ForgeSoundType ARCANE_GOLD = new ForgeSoundType(1f, 1f, ARCANE_GOLD_BREAK, ARCANE_GOLD_STEP, ARCANE_GOLD_PLACE, ARCANE_GOLD_HIT, () -> SoundEvents.NETHERITE_BLOCK_FALL);
    public static final ForgeSoundType ARCANE_GOLD_ORE = new ForgeSoundType(1f, 1f, ARCANE_GOLD_ORE_BREAK, ARCANE_GOLD_ORE_STEP, ARCANE_GOLD_ORE_PLACE, ARCANE_GOLD_ORE_HIT, () -> SoundEvents.STONE_FALL);
    public static final ForgeSoundType DEEPSLATE_ARCANE_GOLD_ORE = new ForgeSoundType(1f, 1f, DEEPSLATE_ARCANE_GOLD_ORE_BREAK, DEEPSLATE_ARCANE_GOLD_ORE_STEP, DEEPSLATE_ARCANE_GOLD_ORE_PLACE, DEEPSLATE_ARCANE_GOLD_ORE_HIT, () -> SoundEvents.DEEPSLATE_FALL);
    public static final ForgeSoundType NETHER_ARCANE_GOLD_ORE = new ForgeSoundType(1f, 1f, NETHER_ARCANE_GOLD_ORE_BREAK, NETHER_ARCANE_GOLD_ORE_STEP, NETHER_ARCANE_GOLD_ORE_PLACE, NETHER_ARCANE_GOLD_ORE_HIT, () -> SoundEvents.NETHER_ORE_FALL);
    public static final ForgeSoundType RAW_ARCANE_GOLD = new ForgeSoundType(1f, 1f, RAW_ARCANE_GOLD_BREAK, RAW_ARCANE_GOLD_STEP, RAW_ARCANE_GOLD_PLACE, RAW_ARCANE_GOLD_HIT, () -> SoundEvents.STONE_FALL);
    public static final ForgeSoundType SARCON = new ForgeSoundType(1f, 1f, SARCON_BREAK, SARCON_STEP, SARCON_PLACE, SARCON_HIT, () -> SoundEvents.NETHERITE_BLOCK_FALL);
    public static final ForgeSoundType VILENIUM = new ForgeSoundType(1f, 1f, VILENIUM_BREAK, VILENIUM_STEP, VILENIUM_PLACE, VILENIUM_HIT, () -> SoundEvents.NETHERITE_BLOCK_FALL);
    public static final ForgeSoundType ARCANUM = new ForgeSoundType(1f, 1f, ARCANUM_BREAK, ARCANUM_STEP, ARCANUM_PLACE, ARCANUM_HIT, () -> SoundEvents.METAL_FALL);
    public static final ForgeSoundType ARCANUM_ORE = new ForgeSoundType(1f, 1f, ARCANUM_ORE_BREAK, ARCANUM_ORE_STEP, ARCANUM_ORE_PLACE, ARCANUM_ORE_HIT, () -> SoundEvents.STONE_FALL);
    public static final ForgeSoundType DEEPSLATE_ARCANUM_ORE = new ForgeSoundType(1f, 1f, DEEPSLATE_ARCANUM_ORE_BREAK, DEEPSLATE_ARCANUM_ORE_STEP, DEEPSLATE_ARCANUM_ORE_PLACE, DEEPSLATE_ARCANUM_ORE_HIT, () -> SoundEvents.DEEPSLATE_FALL);
    public static final ForgeSoundType ARCACITE = new ForgeSoundType(1f, 1f, ARCACITE_BREAK, ARCACITE_STEP, ARCACITE_PLACE, ARCACITE_HIT, () -> SoundEvents.METAL_FALL);
    public static final ForgeSoundType PRECISION_CRYSTAL = new ForgeSoundType(1f, 1f, PRECISION_CRYSTAL_BREAK, PRECISION_CRYSTAL_STEP, PRECISION_CRYSTAL_PLACE, PRECISION_CRYSTAL_HIT, () -> SoundEvents.COPPER_FALL);
    public static final ForgeSoundType NETHER_SALT = new ForgeSoundType(1f, 1f, NETHER_SALT_BREAK, NETHER_SALT_STEP, NETHER_SALT_PLACE, NETHER_SALT_HIT, () -> SoundEvents.STONE_FALL);
    public static final ForgeSoundType NETHER_SALT_ORE = new ForgeSoundType(1f, 1f, NETHER_SALT_ORE_BREAK, NETHER_SALT_ORE_STEP, NETHER_SALT_ORE_PLACE, NETHER_SALT_ORE_HIT, () -> SoundEvents.NETHER_ORE_FALL);
    public static final ForgeSoundType ARCANE_WOOD = new ForgeSoundType(1f, 1f, ARCANE_WOOD_BREAK, ARCANE_WOOD_STEP, ARCANE_WOOD_PLACE, ARCANE_WOOD_HIT, () -> SoundEvents.BAMBOO_WOOD_FALL);
    public static final ForgeSoundType ARCANE_WOOD_HANGING_SIGN = new ForgeSoundType(1f, 1f, ARCANE_WOOD_HANGING_SIGN_BREAK, ARCANE_WOOD_HANGING_SIGN_STEP, ARCANE_WOOD_HANGING_SIGN_PLACE, ARCANE_WOOD_HANGING_SIGN_HIT, () -> SoundEvents.BAMBOO_WOOD_FALL);
    public static final ForgeSoundType INNOCENT_WOOD = new ForgeSoundType(1f, 1f, INNOCENT_WOOD_BREAK, INNOCENT_WOOD_STEP, INNOCENT_WOOD_PLACE, INNOCENT_WOOD_HIT, () -> SoundEvents.CHERRY_WOOD_FALL);
    public static final ForgeSoundType INNOCENT_WOOD_HANGING_SIGN = new ForgeSoundType(1f, 1f, INNOCENT_WOOD_HANGING_SIGN_BREAK, INNOCENT_WOOD_HANGING_SIGN_STEP, INNOCENT_WOOD_HANGING_SIGN_PLACE, INNOCENT_WOOD_HANGING_SIGN_HIT, () -> SoundEvents.CHERRY_WOOD_FALL);
    public static final ForgeSoundType WISESTONE = new ForgeSoundType(1f, 1f, WISESTONE_BREAK, WISESTONE_STEP, WISESTONE_PLACE, WISESTONE_HIT, () -> SoundEvents.DEEPSLATE_FALL);
    public static final ForgeSoundType POLISHED_WISESTONE = new ForgeSoundType(1f, 1f, POLISHED_WISESTONE_BREAK, POLISHED_WISESTONE_STEP, POLISHED_WISESTONE_PLACE, POLISHED_WISESTONE_HIT, () -> SoundEvents.POLISHED_DEEPSLATE_FALL);
    public static final ForgeSoundType WISESTONE_BRICKS = new ForgeSoundType(1f, 1f, WISESTONE_BRICKS_BREAK, WISESTONE_BRICKS_STEP, WISESTONE_BRICKS_PLACE, WISESTONE_BRICKS_HIT, () -> SoundEvents.DEEPSLATE_BRICKS_FALL);
    public static final ForgeSoundType WISESTONE_TILE = new ForgeSoundType(1f, 1f, WISESTONE_TILE_BREAK, WISESTONE_TILE_STEP, WISESTONE_TILE_PLACE, WISESTONE_TILE_HIT, () -> SoundEvents.DEEPSLATE_TILES_FALL);
    public static final ForgeSoundType CHISELED_WISESTONE = new ForgeSoundType(1f, 1f, CHISELED_WISESTONE_BREAK, CHISELED_WISESTONE_STEP, CHISELED_WISESTONE_PLACE, CHISELED_WISESTONE_HIT, () -> SoundEvents.POLISHED_DEEPSLATE_FALL);
    public static final ForgeSoundType MOR = new ForgeSoundType(1f, 1f, MOR_BREAK, MOR_STEP, MOR_PLACE, MOR_HIT, () -> SoundEvents.FUNGUS_FALL);
    public static final ForgeSoundType ELDER_MOR = new ForgeSoundType(1f, 1f, ELDER_MOR_BREAK, ELDER_MOR_STEP, ELDER_MOR_PLACE, ELDER_MOR_HIT, () -> SoundEvents.FUNGUS_FALL);
    public static final ForgeSoundType MOR_BLOCK = new ForgeSoundType(1f, 1f, MOR_BLOCK_BREAK, MOR_BLOCK_STEP, MOR_BLOCK_PLACE, MOR_BLOCK_HIT, () -> SoundEvents.MUDDY_MANGROVE_ROOTS_FALL);
    public static final ForgeSoundType ELDER_MOR_BLOCK = new ForgeSoundType(1f, 1f, ELDER_MOR_BLOCK_BREAK, ELDER_MOR_BLOCK_STEP, ELDER_MOR_BLOCK_PLACE, ELDER_MOR_BLOCK_HIT, () -> SoundEvents.MUDDY_MANGROVE_ROOTS_FALL);
    public static final ForgeSoundType MIXTURE = new ForgeSoundType(1f, 1f, MIXTURE_BREAK, MIXTURE_STEP, MIXTURE_PLACE, MIXTURE_HIT, () -> SoundEvents.BONE_BLOCK_FALL);
    public static final ForgeSoundType CRYSTAL = new ForgeSoundType(1f, 1f, CRYSTAL_BREAK, CRYSTAL_STEP, CRYSTAL_PLACE, CRYSTAL_HIT, () -> SoundEvents.AMETHYST_CLUSTER_FALL);

    public static final RegistryObject<SoundEvent> MUSIC_DISC_ARCANUM = SOUND_EVENTS.register("arcanum_swinging", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_swinging")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_MOR = SOUND_EVENTS.register("mor_marsh", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "mor_marsh")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_REBORN = SOUND_EVENTS.register("reborn", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "reborn")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_SHIMMER = SOUND_EVENTS.register("blue_shimmer", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "blue_shimmer")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_CAPITALISM = SOUND_EVENTS.register("battle_against_a_true_capitalist", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "battle_against_a_true_capitalist")));
    public static final RegistryObject<SoundEvent> MUSIC_DISC_PANACHE = SOUND_EVENTS.register("magical_panache", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "magical_panache")));

    public static final RegistryObject<SoundEvent> WISSEN_BURST = SOUND_EVENTS.register("wissen_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wissen_burst")));
    public static final RegistryObject<SoundEvent> WISSEN_TRANSFER = SOUND_EVENTS.register("wissen_transfer", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wissen_transfer")));

    public static final RegistryObject<SoundEvent> ARCANUM_LENS_RESONATE = SOUND_EVENTS.register("arcanum_lens_resonate", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_lens_resonate")));

    public static final RegistryObject<SoundEvent> STEAM_BURST = SOUND_EVENTS.register("steam_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "steam_burst")));

    public static final RegistryObject<SoundEvent> SPELL_CAST = SOUND_EVENTS.register("spell_cast", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "spell_cast")));
    public static final RegistryObject<SoundEvent> SPELL_BURST = SOUND_EVENTS.register("spell_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "spell_burst")));
    public static final RegistryObject<SoundEvent> SPELL_RELOAD = SOUND_EVENTS.register("spell_reload", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "spell_reload")));

    public static final RegistryObject<SoundEvent> ARCANEMICON_OFFERING = SOUND_EVENTS.register("arcanemicon_offering", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcanemicon_offering")));

    public static final RegistryObject<SoundEvent> ARCANUM_DUST_TRANSMUTATION = SOUND_EVENTS.register("arcanum_dust_transmutation", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcanum_dust_transmutation")));

    public static final RegistryObject<SoundEvent> WISSEN_ALTAR_BURST = SOUND_EVENTS.register("wissen_altar_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wissen_altar_burst")));

    public static final RegistryObject<SoundEvent> WISSEN_CRYSTALLIZER_START = SOUND_EVENTS.register("wissen_crystallizer_start", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wissen_crystallizer_start")));
    public static final RegistryObject<SoundEvent> WISSEN_CRYSTALLIZER_END = SOUND_EVENTS.register("wissen_crystallizer_end", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wissen_crystallizer_end")));
    public static final RegistryObject<SoundEvent> WISSEN_CRYSTALLIZER_LOOP = SOUND_EVENTS.register("wissen_crystallizer_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "wissen_crystallizer_loop")));

    public static final RegistryObject<SoundEvent> ARCANE_WORKBENCH_START = SOUND_EVENTS.register("arcane_workbench_start", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_workbench_start")));
    public static final RegistryObject<SoundEvent> ARCANE_WORKBENCH_END = SOUND_EVENTS.register("arcane_workbench_end", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_workbench_end")));
    public static final RegistryObject<SoundEvent> ARCANE_WORKBENCH_LOOP = SOUND_EVENTS.register("arcane_workbench_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_workbench_loop")));

    public static final RegistryObject<SoundEvent> TOTEM_OF_EXPERIENCE_ABSORPTION_LOOP = SOUND_EVENTS.register("totem_of_experience_absorption_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "totem_of_experience_absorption_loop")));

    public static final RegistryObject<SoundEvent> TOTEM_OF_DISENCHANT_START = SOUND_EVENTS.register("totem_of_disenchant_start", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "totem_of_disenchant_start")));
    public static final RegistryObject<SoundEvent> TOTEM_OF_DISENCHANT_END = SOUND_EVENTS.register("totem_of_disenchant_end", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "totem_of_disenchant_end")));
    public static final RegistryObject<SoundEvent> TOTEM_OF_DISENCHANT_LOOP = SOUND_EVENTS.register("totem_of_disenchant_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "totem_of_disenchant_loop")));

    public static final RegistryObject<SoundEvent> ALTAR_OF_DROUGHT = SOUND_EVENTS.register("altar_of_drought_burst", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "altar_of_drought_burst")));

    public static final RegistryObject<SoundEvent> ARCANE_ITERATOR_START = SOUND_EVENTS.register("arcane_iterator_start", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_iterator_start")));
    public static final RegistryObject<SoundEvent> ARCANE_ITERATOR_END = SOUND_EVENTS.register("arcane_iterator_end", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_iterator_end")));
    public static final RegistryObject<SoundEvent> ARCANE_ITERATOR_LOOP = SOUND_EVENTS.register("arcane_iterator_loop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "arcane_iterator_loop")));

    public static final RegistryObject<SoundEvent> CRYSTAL_RITUAL_START = SOUND_EVENTS.register("crystal_ritual_start", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "crystal_ritual_start")));
    public static final RegistryObject<SoundEvent> CRYSTAL_RITUAL_END = SOUND_EVENTS.register("crystal_ritual_end", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "crystal_ritual_end")));

    public static final RegistryObject<SoundEvent> PIPE = SOUND_EVENTS.register("pipe", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "pipe")));
    public static final RegistryObject<SoundEvent> BOOM = SOUND_EVENTS.register("boom", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "boom")));
    public static final RegistryObject<SoundEvent> MOAI = SOUND_EVENTS.register("moai", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(WizardsReborn.MOD_ID, "moai")));

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
