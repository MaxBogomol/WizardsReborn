package mod.maxbogomol.wizards_reborn.api.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

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

    public void useSpell(Level world, Player player, InteractionHand hand) {

    }

    public void onWandUseFirst(ItemStack stack, UseOnContext context) {

    }

    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {

    }

    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player) {

    }

    public void spawnSpellStandart(Level world, Player player) {
        Vec3 pos = player.getEyePosition(0);
        Vec3 vel = player.getEyePosition(0).add(player.getLookAngle().scale(40)).subtract(pos).scale(1.0 / 30);
        world.addFreshEntity(new SpellProjectileEntity(WizardsReborn.SPELL_PROJECTILE.get(), world).shoot(
                pos.x, pos.y - 0.2f, pos.z, vel.x, vel.y, vel.z, player.getUUID(), this.getId()
        ));
    }
}
