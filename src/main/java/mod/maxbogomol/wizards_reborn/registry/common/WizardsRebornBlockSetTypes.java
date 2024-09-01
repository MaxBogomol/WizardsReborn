package mod.maxbogomol.wizards_reborn.registry.common;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.common.util.LazyOptional;

public class WizardsRebornBlockSetTypes {
    public static final LazyOptional<BlockSetType> ARCANE_WOOD = LazyOptional.of(() -> BlockSetType.register(
            new BlockSetType("arcane_wood", true,
                    WizardsRebornSounds.ARCANE_WOOD,
                    WizardsRebornSounds.ARCANE_WOOD_DOOR_CLOSE.get(), WizardsRebornSounds.ARCANE_WOOD_DOOR_OPEN.get(),
                    WizardsRebornSounds.ARCANE_WOOD_TRAPDOOR_CLOSE.get(), WizardsRebornSounds.ARCANE_WOOD_TRAPDOOR_OPEN.get(),
                    WizardsRebornSounds.ARCANE_WOOD_PRESSURE_PLATE_CLICK_OFF.get(), WizardsRebornSounds.ARCANE_WOOD_PRESSURE_PLATE_CLICK_ON.get(),
                    WizardsRebornSounds.ARCANE_WOOD_BUTTON_CLICK_OFF.get(), WizardsRebornSounds.ARCANE_WOOD_BUTTON_CLICK_ON.get())));

    public static final LazyOptional<BlockSetType> INNOCENT_WOOD = LazyOptional.of(() -> BlockSetType.register(
            new BlockSetType("innocent_wood", true,
                    WizardsRebornSounds.INNOCENT_WOOD,
                    WizardsRebornSounds.INNOCENT_WOOD_DOOR_CLOSE.get(), WizardsRebornSounds.INNOCENT_WOOD_DOOR_OPEN.get(),
                    WizardsRebornSounds.INNOCENT_WOOD_TRAPDOOR_CLOSE.get(), WizardsRebornSounds.INNOCENT_WOOD_TRAPDOOR_OPEN.get(),
                    WizardsRebornSounds.INNOCENT_WOOD_PRESSURE_PLATE_CLICK_OFF.get(), WizardsRebornSounds.INNOCENT_WOOD_PRESSURE_PLATE_CLICK_ON.get(),
                    WizardsRebornSounds.INNOCENT_WOOD_BUTTON_CLICK_OFF.get(), WizardsRebornSounds.INNOCENT_WOOD_BUTTON_CLICK_ON.get())));
    public static final LazyOptional<BlockSetType> CORK_BAMBOO = LazyOptional.of(() -> BlockSetType.register(
            new BlockSetType("cork_bamboo", true,
                    WizardsRebornSounds.ARCANE_WOOD,
                    WizardsRebornSounds.ARCANE_WOOD_DOOR_CLOSE.get(), WizardsRebornSounds.ARCANE_WOOD_DOOR_OPEN.get(),
                    WizardsRebornSounds.ARCANE_WOOD_TRAPDOOR_CLOSE.get(), WizardsRebornSounds.ARCANE_WOOD_TRAPDOOR_OPEN.get(),
                    WizardsRebornSounds.ARCANE_WOOD_PRESSURE_PLATE_CLICK_OFF.get(), WizardsRebornSounds.ARCANE_WOOD_PRESSURE_PLATE_CLICK_ON.get(),
                    WizardsRebornSounds.ARCANE_WOOD_BUTTON_CLICK_OFF.get(), WizardsRebornSounds.ARCANE_WOOD_BUTTON_CLICK_ON.get())));

    public static final LazyOptional<BlockSetType> WISESTONE = LazyOptional.of(() -> BlockSetType.register(
            new BlockSetType("wisestone", true,
                    WizardsRebornSounds.POLISHED_WISESTONE,
                    SoundEvents.IRON_DOOR_CLOSE, SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE, SoundEvents.IRON_TRAPDOOR_OPEN,
                    WizardsRebornSounds.WISESTONE_PRESSURE_PLATE_CLICK_OFF.get(), WizardsRebornSounds.WISESTONE_PRESSURE_PLATE_CLICK_ON.get(),
                    WizardsRebornSounds.WISESTONE_BUTTON_CLICK_OFF.get(), WizardsRebornSounds.WISESTONE_BUTTON_CLICK_ON.get())));
}
