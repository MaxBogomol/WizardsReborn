package mod.maxbogomol.wizards_reborn.common.item.equipment.innocentwood;

import mod.maxbogomol.wizards_reborn.common.item.equipment.arcanewood.ArcaneWoodTools;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InnocentWoodTools extends ArcaneWoodTools {

    public InnocentWoodTools(Item repairItem) {
        super(repairItem);
    }

    @Override
    public int repairTick(ItemStack stack, Level level, Entity entity) {
        return 500;
    }

    @Override
    public SoundEvent getRepairSound(ItemStack stack, Level level, Entity entity) {
        return WizardsRebornSounds.INNOCENT_WOOD_PLACE.get();
    }
}
