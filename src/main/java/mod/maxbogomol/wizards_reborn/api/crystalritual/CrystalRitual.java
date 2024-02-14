package mod.maxbogomol.wizards_reborn.api.crystalritual;

import mod.maxbogomol.wizards_reborn.common.tileentity.CrystalTileEntity;
import mod.maxbogomol.wizards_reborn.utils.PacketUtils;

import java.awt.*;
import java.util.Random;

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

    public int getMaxCooldown(CrystalTileEntity crystal) {
        return 0;
    }

    public void setCooldown(CrystalTileEntity crystal, int cooldown) {
        crystal.cooldown = cooldown;
    }

    public int getCooldown(CrystalTileEntity crystal) {
        return crystal.cooldown;
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
}
