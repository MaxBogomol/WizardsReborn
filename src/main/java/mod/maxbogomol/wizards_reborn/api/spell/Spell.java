package mod.maxbogomol.wizards_reborn.api.spell;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.fluffy_fur.common.damage.DamageHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalHandler;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalType;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.knowledge.Research;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.spell.WandSpellContext;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
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
import java.util.Objects;
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
        crystalTypes.addAll(CrystalHandler.getTypes());
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
        if (stack.getItem() instanceof CrystalItem crystal) {
            for (CrystalType type : getCrystalTypes()) {
                if (crystal.getType() == type && crystal.getPolishing().getPolishingLevel() >= getMinimumPolishingLevel()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean canSpell(Level level, SpellContext spellContext) {
        return true;
    }

    public boolean canUseSpell(Level level, SpellContext spellContext) {
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

    public boolean useSpellOn(Level level, SpellContext spellContext) {
        return false;
    }

    public void useSpellTick(Level level, SpellContext spellContext, int remainingUseDuration) {

    }

    public void stopUseSpell(Level level, SpellContext spellContext, int timeLeft) {

    }

    public void finishUseSpell(Level level, SpellContext spellContext) {

    }

    public SpellContext getWandContext(Entity entity, ItemStack stack, InteractionHand hand) {
        return WandSpellContext.getFromWand(entity, stack, hand);
    }

    public SpellContext getWandContext(Entity entity, ItemStack stack) {
        return getWandContext(entity, stack, null);
    }

    public void useWand(Level level, Player player, InteractionHand hand, ItemStack stack) {
        useSpell(level, getWandContext(player, stack, hand));
    }

    public boolean useWandOn(ItemStack stack, UseOnContext context) {
        SpellContext spellContext = getWandContext(context.getPlayer(), stack, context.getHand());
        spellContext.setBlockPos(context.getClickedPos());
        spellContext.setDirection(context.getClickedFace());
        return useSpellOn(context.getLevel(), spellContext);
    }

    public void useWandTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        useSpellTick(level, getWandContext(livingEntity, stack), remainingUseDuration);
    }

    public void stopUseWand(ItemStack stack, Level level, LivingEntity entityLiving, int timeLeft) {
        stopUseSpell(level, getWandContext(entityLiving, stack), timeLeft);
    }

    public void finishUseWand(ItemStack stack, Level level, LivingEntity entityLiving) {
        finishUseSpell(level, getWandContext(entityLiving, stack));
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

    public static DamageSource getDamage(ResourceKey<DamageType> key, Entity entity, Entity owner) {
        return DamageHandler.create(entity.level(), key, entity, Objects.requireNonNullElse(owner, entity));
    }

    public static DamageSource getDamage(ResourceKey<DamageType> key, Entity entity) {
        return DamageHandler.create(entity.level(), key, entity, entity);
    }

    public SpellComponent getSpellComponent() {
        return new SpellComponent();
    }
}
