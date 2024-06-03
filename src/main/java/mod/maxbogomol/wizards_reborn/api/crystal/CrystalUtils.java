package mod.maxbogomol.wizards_reborn.api.crystal;

import mod.maxbogomol.wizards_reborn.common.item.FracturedCrystalItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrystalUtils {
    public static void createCrystalItemStats(ItemStack stack, CrystalType type, Level level, int count) {
        int maxCount = 0;
        Map<CrystalStat, Integer> intStats = new HashMap<CrystalStat, Integer>();
        ArrayList<CrystalStat> stats = new ArrayList<CrystalStat>();

        for (CrystalStat stat : type.getStats()) {
            maxCount = maxCount + stat.getMaxLevel();
            intStats.put(stat, 0);
            stats.add(stat);
        }
        if (count > maxCount) {
            count = maxCount;
        }

        for (int i = 0; i < count; i++) {
            int id = Mth.nextInt(level.random, 0, stats.size() - 1);
            CrystalStat stat = stats.get(id);
            intStats.put(stat, intStats.get(stat) + 1);
            if (intStats.get(stat) >= stat.getMaxLevel()) {
                stats.remove(stat);
            }
        }

        CompoundTag nbt = stack.getOrCreateTag();
        for (CrystalStat stat : type.getStats()) {
            nbt.putInt(stat.getId(), intStats.get(stat));
        }
        stack.setTag(nbt);
    }

    public static void createCrystalFromFractured(ItemStack stack, Container container) {
        int fracturedCount = 0;
        Map<CrystalStat, Integer> intStats = new HashMap<CrystalStat, Integer>();
        for (int i = 0; i < container.getContainerSize(); i++) {
            if (container.getItem(i).getItem() instanceof FracturedCrystalItem) {
                FracturedCrystalItem fractured = (FracturedCrystalItem) container.getItem(i).getItem();
                fracturedCount++;

                for (CrystalStat stat : fractured.getType().getStats()) {
                    if (intStats.containsKey(stat)) {
                        intStats.put(stat, intStats.get(stat) + FracturedCrystalItem.getStatLevel(container.getItem(i), stat));
                    } else {
                        intStats.put(stat, FracturedCrystalItem.getStatLevel(container.getItem(i), stat));
                    }
                }
            }
        }

        CompoundTag nbt = stack.getOrCreateTag();
        for (CrystalStat stat : intStats.keySet()) {
            int statLevel = Mth.ceil(intStats.get(stat) / ((float) fracturedCount));
            if (statLevel > stat.getMaxLevel()) {
                statLevel = stat.getMaxLevel();
            }
            nbt.putInt(stat.getId(), statLevel);
        }
        stack.setTag(nbt);
    }

    public static int getStatLevel(CompoundTag nbt, CrystalStat stat) {
        int statlevel = 0;
        if (nbt.contains(stat.getId())) {
            statlevel = nbt.getInt(stat.getId());
        }
        return statlevel;
    }
}
