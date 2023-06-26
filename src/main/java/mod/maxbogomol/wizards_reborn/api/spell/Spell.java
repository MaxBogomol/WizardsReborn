package mod.maxbogomol.wizards_reborn.api.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;

public class Spell {
    public String id;
    public ArrayList<CrystalType> crystalTypes = new ArrayList<CrystalType>();

    public Spell(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return new Color(255, 255, 255);
    }

    public ResourceLocation getIcon() {
        return getIcon(id);
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public static ResourceLocation getIcon(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String spellId = id.substring(i + 1);
        return new ResourceLocation(modId, "textures/spell/" + spellId + ".png");
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String spellId = id.substring(i + 1);
        return "spell."  + modId + "." + spellId;
    }

    public void addCrystalType(CrystalType crystalType) {
        crystalTypes.add(crystalType);
    }

    public ArrayList<CrystalType> getCrystalTypes() {
        return crystalTypes;
    }

    public void useSpell(World world, PlayerEntity player, Hand hand) {

    }

    public void onWandUseFirst(ItemStack stack, ItemUseContext context) {

    }

    public void onImpact(RayTraceResult ray, World world, SpellProjectileEntity projectile, PlayerEntity player, Entity target) {

    }

    public void onImpact(RayTraceResult ray, World world, SpellProjectileEntity projectile, PlayerEntity player) {

    }

    public void spawnSpellStandart(World world, PlayerEntity player) {
        Vector3d pos = player.getEyePosition(0);
        Vector3d vel = player.getEyePosition(0).add(player.getLookVec().scale(40)).subtract(pos).scale(1.0 / 30);
        world.addEntity(new SpellProjectileEntity(WizardsReborn.SPELL_PROJECTILE.get(), world).shoot(
                pos.x, pos.y, pos.z, vel.x, vel.y, vel.z, player.getUniqueID(), this.getId()
        ));
    }
}
