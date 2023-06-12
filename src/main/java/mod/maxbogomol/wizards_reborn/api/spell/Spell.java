package mod.maxbogomol.wizards_reborn.api.spell;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.Hand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.awt.*;

public class Spell {
    public String id;

    public Spell(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return new Color(255, 255, 255);
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
