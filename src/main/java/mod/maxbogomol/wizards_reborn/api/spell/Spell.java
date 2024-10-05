package mod.maxbogomol.wizards_reborn.api.spell;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.crystal.Crystals;
import mod.maxbogomol.wizards_reborn.api.knowledge.Research;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Spell {
    public String id;
    public ArrayList<CrystalType> crystalTypes = new ArrayList<>();
    public static Random random = new Random();
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
        return Color.WHITE;
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

    public void addAllCrystalType() {
        crystalTypes.addAll(Crystals.getTypes());
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
        return getCooldownWithStat(nbt, getCooldown());
    }

    public int getCooldownWithStat(CompoundTag nbt, int cost) {
        int absorptionLevel = CrystalUtil.getStatLevel(nbt, WizardsRebornCrystals.ABSORPTION);
        return (int) (cost * (1 - (getCooldownStatModifier() * absorptionLevel)));
    }

    public int getWissenCostWithStat(CompoundTag nbt) {
        int balanceLevel = CrystalUtil.getStatLevel(nbt, WizardsRebornCrystals.BALANCE);
        return (int) (getWissenCost() * (1 - (getWissenStatModifier() * balanceLevel)));
    }

    public int getWissenCostWithStat(CompoundTag nbt, Entity entity) {
        return getWissenCostWithStat(nbt, entity, getWissenCost());
    }

    public int getWissenCostWithStat(CompoundTag nbt, Entity entity, int cost) {
        int balanceLevel = CrystalUtil.getStatLevel(nbt, WizardsRebornCrystals.BALANCE);
        float modifier = 1;
        if (entity instanceof Player player) {
            modifier = (1 - (getWissenStatModifier() * balanceLevel) - WissenUtil.getWissenCostModifierWithDiscount(player));
        }
        if (modifier <= 0) {
            return 1;
        }
        return (int) (cost * modifier);
    }

    public boolean isSecret() {
        return false;
    }

    public boolean canWandWithCrystal(ItemStack stack) {
        if (stack.getItem() instanceof ArcaneWandItem) {
            SimpleContainer stack_inv = ArcaneWandItem.getInventory(stack);
            if (stack_inv.getItem(0).getItem() instanceof CrystalItem crystal) {
                for (CrystalType type : getCrystalTypes()) {
                    if (crystal.getType() == type && crystal.getPolishing().getPolishingLevel() >= getMinimumPolishingLevel()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean canSpell(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        CompoundTag nbt = stack.getOrCreateTag();
        return nbt.getBoolean("crystal") && nbt.getInt("cooldown") <= 0 && WissenItemUtil.canRemoveWissen(stack, getWissenCostWithStat(getStats(stack), player));
    }

    public boolean canSpellAir(Level level, Player player, InteractionHand hand) {
        return true;
    }

    public void setCooldown(ItemStack stack, CompoundTag stats) {
        CompoundTag nbt = stack.getOrCreateTag();
        int cooldown = getCooldownWithStat(stats);
        nbt.putInt("cooldown", cooldown);
        nbt.putInt("maxCooldown", cooldown);
    }

    public void setCooldown(ItemStack stack, CompoundTag stats, int cost) {
        CompoundTag nbt = stack.getOrCreateTag();
        int cooldown = getCooldownWithStat(stats, cost);
        nbt.putInt("cooldown", cooldown);
        nbt.putInt("maxCooldown", cooldown);
    }

    public void removeWissen(ItemStack stack, CompoundTag stats) {
        WissenItemUtil.removeWissen(stack, getWissenCostWithStat(stats));
    }

    public void removeWissen(ItemStack stack, CompoundTag stats, Entity entity) {
        WissenItemUtil.removeWissen(stack, getWissenCostWithStat(stats, entity));
    }

    public void removeWissen(ItemStack stack, CompoundTag stats, Entity entity, int cost) {
        WissenItemUtil.removeWissen(stack, getWissenCostWithStat(stats, entity, cost));
    }

    public static CompoundTag getStats(ItemStack stack) {
        return ArcaneWandItem.getInventory(stack).getItem(0).getOrCreateTag();
    }

    public void setResearch(Research research) {
        this.research = research;
    }

    public Research getResearch() {
        return research;
    }

    public int getMinimumPolishingLevel() {
        return 0;
    }

    public void useSpell(Level level, SpellContext spellContext) {

    }

    public void useWand(Level level, Player player, InteractionHand hand, ItemStack stack) {

    }

    public void useSpell(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide()) {
            ItemStack stack = player.getItemInHand(hand);

            CompoundTag stats = getStats(stack);
            setCooldown(stack, stats);
            removeWissen(stack, stats, player);
            //awardStat(player, stack);
        }
    }

    public InteractionResult onWandUseOn(ItemStack stack, UseOnContext context) {
        return InteractionResult.PASS;
    }

    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {

    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {

    }

    public void finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {

    }

    public void entityTick(SpellEntity entity) {

    }

    public void onReload(ItemStack stack, Level level, Entity entity, int slot, boolean isSelected) {
        level.playSound(WizardsReborn.proxy.getPlayer(), entity.getX(), entity.getY(), entity.getZ(), WizardsRebornSounds.SPELL_RELOAD.get(), SoundSource.BLOCKS, 0.1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
    }

    @OnlyIn(Dist.CLIENT)
    public void render(SpellEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {

    }

    @OnlyIn(Dist.CLIENT)
    public Vec3 getRenderOffset(Entity entity, float partialTicks) {
        return Vec3.ZERO;
    }

    public static void spellSound(Vec3 pos, Level level) {
        level.playSound(null, pos.x(), pos.y(), pos.z(), WizardsRebornSounds.SPELL_CAST.get(), SoundSource.PLAYERS, 0.25f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
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
    public ItemAnimation getAnimation(ItemStack stack) {
        return null;
    }

    public static DamageSource getDamage(Holder<DamageType> typeHolder, Entity entity, Entity owner) {
        if (owner != null) {
            return new DamageSource(typeHolder, entity, owner);
        }
        return new DamageSource(typeHolder, entity);
    }

    public SpellComponent getSpellComponent() {
        return new SpellComponent();
    }
}
