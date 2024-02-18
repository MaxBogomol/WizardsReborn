package mod.maxbogomol.wizards_reborn.api.crystalritual;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalStat;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.PolishingType;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.CrystalTileEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;

public class CrystalRitual {
    public String id;
    public Random random = new Random();

    public CrystalRitual(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return new Color(200, 200, 200);
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String spellId = id.substring(i + 1);
        return "crystal_ritual."  + modId + "." + spellId;
    }

    public CrystalRitualArea getArea(CrystalTileEntity crystal) {
        return new CrystalRitualArea(3, 3, 3, 3, 3, 3);
    }

    public int getStatLevel(CrystalTileEntity crystal, CrystalStat stat) {
        ItemStack item = getCrystalItem(crystal);
        if (!item.isEmpty()) {
            return CrystalItem.getStatLevel(item, stat);
        }
        return 0;
    }

    public int getMaxRitualCooldown(CrystalTileEntity crystal) {
        return 0;
    }

    public int getMaxRitualCooldown(int cooldown, ItemStack item) {
        int resonanceLevel = CrystalItem.getStatLevel(item, WizardsReborn.RESONANCE_CRYSTAL_STAT);
        return (int) (cooldown * (1 - (getCooldownStatModifier() * resonanceLevel)));
    }

    public int getMaxRitualCooldownWithStat(CrystalTileEntity crystal) {
        ItemStack item = getCrystalItem(crystal);
        if (!item.isEmpty()) {
            return getMaxRitualCooldown(getMaxRitualCooldown(crystal), item);
        }
        return getMaxRitualCooldown(crystal);
    }

    public float getCooldownStatModifier() {
        return 0.15f;
    }

    public void setCooldown(CrystalTileEntity crystal, int cooldown) {
        crystal.cooldown = cooldown;
    }

    public int getCooldown(CrystalTileEntity crystal) {
        return crystal.cooldown;
    }

    public void setMaxCooldown(CrystalTileEntity crystal, int cooldown) {
        crystal.maxCooldown = cooldown;
    }

    public int getMaxCooldown(CrystalTileEntity crystal) {
        return crystal.maxCooldown;
    }

    public float getCrystalCooldown(CrystalTileEntity crystal) {
        if (crystal.cooldown > 0) {
            return (float) crystal.maxCooldown / (float) crystal.cooldown;
        }
        return 0;
    }

    public void updateCrystal(CrystalTileEntity crystal) {
        PacketUtils.SUpdateTileEntityPacket(crystal);
    }

    public boolean canStart(CrystalTileEntity crystal) {
        return true;
    }

    public boolean canTick(CrystalTileEntity crystal) {
        return true;
    }

    public boolean canEnd(CrystalTileEntity crystal) {
        return false;
    }

    public void start(CrystalTileEntity crystal) {

    }

    public void tick(CrystalTileEntity crystal) {

    }

    public void end(CrystalTileEntity crystal) {

    }

    public ItemStack getCrystalItem(CrystalTileEntity crystal) {
        return crystal.getCrystalItem();
    }

    public CrystalType getCrystalType(ItemStack crystal) {
        if (crystal.getItem() instanceof CrystalItem crystalItem) {
            return crystalItem.getType();
        }
        return null;
    }

    public PolishingType getCrystalPolishingType(ItemStack crystal) {
        if (crystal.getItem() instanceof CrystalItem crystalItem) {
            return crystalItem.getPolishing();
        }
        return null;
    }

    public Color getCrystalColor(ItemStack crystal) {
        CrystalType type = getCrystalType(crystal);
        if (type != null) {
            return type.getColor();
        }
        return new Color(255, 255, 255);
    }

    public static ArrayList<BlockPos> getBlockPosWithArea(Level level, BlockPos startPos, Vec3 sizeFrom, Vec3 sizeTo, Predicate<BlockPos> filter, boolean hasRandomOffset, boolean hasLimit, int limit) {
        ArrayList<BlockPos> blockPosList = new ArrayList<>();
        int count = 0;
        boolean reachLimit = false;

        float xOffset = 0;
        float yOffset = 0;
        float zOffset = 0;

        if (hasRandomOffset) {
            Random random = new Random();
            xOffset = (float) (random.nextFloat() * (sizeFrom.x() + sizeTo.x()));
            yOffset = (float) (random.nextFloat() * (sizeFrom.y() + sizeTo.y()));
            zOffset = (float) (random.nextFloat() * (sizeFrom.z() + sizeTo.z()));
        }

        for (double x = -sizeFrom.x(); x <= sizeTo.x(); x++) {
            for (double y = -sizeFrom.y(); y <= sizeTo.y(); y++) {
                for (double z = -sizeFrom.z(); z <= sizeTo.z(); z++) {
                    BlockPos pos = new BlockPos(new BlockPos(startPos.getX() + Mth.floor(x), startPos.getY() + Mth.floor(y), startPos.getZ() + Mth.floor(z)));
                    if (hasRandomOffset) {
                        float X = (float) (((xOffset + x + sizeFrom.x()) % (sizeFrom.x() + sizeTo.x() + 1)) - sizeFrom.x());
                        float Y = (float) (((yOffset + y + sizeFrom.y()) % (sizeFrom.y() + sizeTo.y() + 1)) - sizeFrom.y());
                        float Z = (float) (((zOffset + z + sizeFrom.z()) % (sizeFrom.z() + sizeTo.z() + 1)) - sizeFrom.z());
                        pos = new BlockPos(new BlockPos(startPos.getX() + Mth.floor(X), startPos.getY() + Mth.floor(Y), startPos.getZ() + Mth.floor(Z)));
                    }
                    if (level.isLoaded(pos)) {
                        if (filter.test(pos)) {
                            blockPosList.add(pos);
                            count++;
                            if (limit >= count && hasLimit) {
                                reachLimit = true;
                                break;
                            }
                        }
                    }
                }
                if (reachLimit) break;
            }
            if (reachLimit) break;
        }
        return blockPosList;
    }

    public static ArrayList<BlockPos> getBlockPosWithArea(Level level, BlockPos startPos, CrystalRitualArea area, Predicate<BlockPos> filter, boolean hasRandomOffset, boolean hasLimit, int limit) {
        return getBlockPosWithArea(level, startPos, area.getSizeFrom(), area.getSizeTo(), filter, hasRandomOffset, hasLimit, limit);
    }
}
