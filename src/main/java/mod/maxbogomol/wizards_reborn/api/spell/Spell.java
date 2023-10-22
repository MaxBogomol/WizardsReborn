package mod.maxbogomol.wizards_reborn.api.spell;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtils;
import mod.maxbogomol.wizards_reborn.api.knowledge.Research;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Spell {
    public String id;
    public ArrayList<CrystalType> crystalTypes = new ArrayList<CrystalType>();
    public Random random = new Random();
    public Research research;
    public int points;

    public Spell(String id, int points) {
        this.id = id;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public int getPoints() {
        return points;
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
        return 0.1f;
    }

    public int getCooldownWithStat(CompoundTag nbt) {
        int absorptionLevel = CrystalUtils.getStatLevel(nbt, WizardsReborn.ABSORPTION_CRYSTAL_STAT);
        return (int) (getCooldown() * (1 - (getCooldownStatModifier() * absorptionLevel)));
    }

    public int getWissenCostWithStat(CompoundTag nbt) {
        int balanceLevel = CrystalUtils.getStatLevel(nbt, WizardsReborn.BALANCE_CRYSTAL_STAT);
        return (int) (getWissenCost() * (1 - (getWissenStatModifier() * balanceLevel)));
    }

    public int getWissenCostWithStat(CompoundTag nbt, Player player) {
        int balanceLevel = CrystalUtils.getStatLevel(nbt, WizardsReborn.BALANCE_CRYSTAL_STAT);
        float modifier = (1 - (getWissenStatModifier() * balanceLevel) - WissenUtils.getWissenCostModifierWithSale(player));
        if (modifier <= 0) {
            return 1;
        }
        return (int) (getWissenCost() * modifier);
    }

    public boolean canWandWithCrystal(ItemStack stack) {
        if (stack.getItem() instanceof ArcaneWandItem) {
            SimpleContainer stack_inv = ArcaneWandItem.getInventory(stack);
            if (stack_inv.getItem(0).getItem() instanceof CrystalItem crystal) {
                for (CrystalType type : getCrystalTypes()) {
                    if (crystal.getType() == type) {
                        return true;
                    }
                }
            }
        }

        return false;
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

    public void removeWissen(ItemStack stack, CompoundTag stats, Player player) {
        WissenItemUtils.removeWissen(stack, getWissenCostWithStat(stats, player));
    }

    public CompoundTag getStats(ItemStack stack) {
        return ArcaneWandItem.getInventory(stack).getItem(0).getOrCreateTag();
    }

    public void useSpell(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            ItemStack stack = player.getItemInHand(hand);

            CompoundTag stats = getStats(stack);
            setCooldown(stack, stats);
            removeWissen(stack, stats, player);
        }
    }

    public void onWandUseFirst(ItemStack stack, UseOnContext context) {

    }

    public void onUseTick(Level world, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {

    }

    public void releaseUsing(ItemStack stack, Level world, LivingEntity entityLiving, int timeLeft) {

    }

    public void finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {

    }

    public void entityTick(SpellProjectileEntity entity) {

    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.NONE;
    }

    public boolean hasCustomAnimation(ItemStack stack) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void setupAnim(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @OnlyIn(Dist.CLIENT)
    public void setupAnimRight(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @OnlyIn(Dist.CLIENT)
    public void setupAnimLeft(HumanoidModel model, Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {

    }

    @OnlyIn(Dist.CLIENT)
    public void renderArmWithItem(AbstractClientPlayer player, float partialTicks, float pitch, InteractionHand hand, float swingProgress, ItemStack stack, float equippedProgress, PoseStack poseStack, MultiBufferSource buffer, int combinedLight) {

    }

    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player, Entity target) {

    }

    public void onImpact(HitResult ray, Level world, SpellProjectileEntity projectile, Player player) {

    }

    public void spawnSpellStandart(Level world, Player player, CompoundTag stats) {
        if (!world.isClientSide) {
            Vec3 pos = player.getEyePosition(0);
            Vec3 vel = player.getEyePosition(0).add(player.getLookAngle().scale(40)).subtract(pos).scale(1.0 / 30);
            world.addFreshEntity(new SpellProjectileEntity(WizardsReborn.SPELL_PROJECTILE.get(), world).shoot(
                    pos.x, pos.y - 0.2f, pos.z, vel.x, vel.y, vel.z, player.getUUID(), this.getId(), stats
            ));
        }
    }

    public void onReload(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (world.isClientSide) {
            world.playSound(WizardsReborn.proxy.getPlayer(), entity.getX(), entity.getY() + entity.getEyeHeight(), entity.getZ(), WizardsReborn.SPELL_RELOAD_SOUND.get(), SoundSource.BLOCKS, 0.1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void render(SpellProjectileEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {

    }

    @OnlyIn(Dist.CLIENT)
    public Vec3 getRenderOffset(Entity entity, float partialTicks) {
        return Vec3.ZERO;
    }

    public void setResearch(Research research) {
        this.research = research;
    }

    public Research getResearch() {
        return research;
    }
}
