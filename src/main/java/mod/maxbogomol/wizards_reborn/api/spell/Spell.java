package mod.maxbogomol.wizards_reborn.api.spell;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
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
import java.util.Random;

public class Spell {
    public String id;
    public ArrayList<CrystalType> crystalTypes = new ArrayList<CrystalType>();
    public Random random = new Random();

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

    public int getCooldown() {
        return 20;
    }

    public int getWissenCost() {
        return 50;
    }

    public float getCooldownStatModifier() {
        return 0.15f;
    }

    public float getWissenStatModifier() {
        return 0.15f;
    }

    public int getCooldownWithStat(CompoundTag nbt) {
        int absorptionLevel = CrystalUtils.getStatLevel(nbt, WizardsReborn.ABSORPTION_CRYSTAL_STAT);
        return (int) (getCooldown() * (1 - (getCooldownStatModifier() * absorptionLevel)));
    }

    public int getWissenCostWithStat(CompoundTag nbt) {
        int balanceLevel = CrystalUtils.getStatLevel(nbt, WizardsReborn.BALANCE_CRYSTAL_STAT);
        return (int) (getWissenCost() * (1 - (getWissenStatModifier() * balanceLevel)));
    }

    public boolean canSpell(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        CompoundTag nbt = stack.getTag();
        if (nbt.getBoolean("crystal") && nbt.getInt("cooldown") <= 0 && WissenItemUtils.canRemoveWissen(stack, getWissenCost())) {
            return true;
        }

        return false;
    }

    public void setCooldown(ItemStack stack, CompoundTag stats) {
        CompoundTag nbt = stack.getTag();
        nbt.putInt("cooldown", getCooldownWithStat(stats));
    }

    public void removeWissen(ItemStack stack, CompoundTag stats) {
        WissenItemUtils.removeWissen(stack, getWissenCostWithStat(stats));
    }

    public CompoundTag getStats(ItemStack stack) {
        return ArcaneWandItem.getInventory(stack).getItem(0).getOrCreateTag();
    }

    public void useSpell(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        CompoundTag stats = getStats(stack);
        setCooldown(stack, stats);
        removeWissen(stack, stats);
    }

    public void onWandUseFirst(ItemStack stack, UseOnContext context) {

    }

    public void entityTick(SpellProjectileEntity entity) {

    }

    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {

    }

    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player) {

    }

    public void spawnSpellStandart(Level world, Player player, CompoundTag stats) {
        Vec3 pos = player.getEyePosition(0);
        Vec3 vel = player.getEyePosition(0).add(player.getLookAngle().scale(40)).subtract(pos).scale(1.0 / 30);
        world.addFreshEntity(new SpellProjectileEntity(WizardsReborn.SPELL_PROJECTILE.get(), world).shoot(
                pos.x, pos.y - 0.2f, pos.z, vel.x, vel.y, vel.z, player.getUUID(), this.getId(), stats
        ));
    }

    public void onReload(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        world.playSound(WizardsReborn.proxy.getPlayer(), entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ(), WizardsReborn.SPELL_RELOAD_SOUND.get(), SoundSource.BLOCKS, 0.1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
    }

    public void render(SpellProjectileEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {

    }
}
