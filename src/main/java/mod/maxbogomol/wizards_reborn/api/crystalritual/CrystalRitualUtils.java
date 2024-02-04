package mod.maxbogomol.wizards_reborn.api.crystalritual;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class CrystalRitualUtils {
    public static CrystalRitual getCrystalRitual(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("crystalRitual")) {
            CrystalRitual ritual = CrystalRituals.getCrystalRitual(nbt.getString("crystalRitual"));
            if (ritual != null) {
                return ritual;
            }
        }

        return WizardsReborn.EMPTY_CRYSTAL_RITUAL;
    }

    public static void setCrystalRitual(ItemStack stack, CrystalRitual ritual) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putString("crystalRitual", ritual.getId());
    }

    public static boolean isEmpty(CrystalRitual ritual) {
        return ritual == null;
    }
}
