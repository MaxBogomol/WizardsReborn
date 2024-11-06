package mod.maxbogomol.wizards_reborn.common.spell.look;

import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.client.animation.StrikeSpellItemAnimation;
import mod.maxbogomol.wizards_reborn.common.block.crystal.CrystalBlockEntity;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrystalCrushingSpellPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrystalCrushingSpellScreenshakePacket;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornParticles;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import mod.maxbogomol.wizards_reborn.registry.common.damage.WizardsRebornDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;

import java.awt.*;

public class CrystalCrushingSpell extends LookSpell {
    public static StrikeSpellItemAnimation animation = new StrikeSpellItemAnimation();

    public CrystalCrushingSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.voidSpellColor;
    }

    @Override
    public int getCooldown() {
        return 50;
    }

    @Override
    public int getWissenCost() {
        return 300;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public void useSpellTick(Level level, SpellContext spellContext, int time) {
        if (level.isClientSide()) {
            RayHitResult hitResult = getHit(level, spellContext);
            if (level.getBlockEntity(hitResult.getBlockPos()) instanceof CrystalBlockEntity crystal) {
                Color color = getColor();

                if (random.nextFloat() < 0.15f) {
                    ParticleBuilder.create(WizardsRebornParticles.KARMA)
                            .setColorData(ColorParticleData.create(color).build())
                            .setTransparencyData(GenericParticleData.create(0.1f, 0.5f, 0).setEasing(Easing.EXPO_IN, Easing.QUINTIC_IN_OUT).build())
                            .setScaleData(GenericParticleData.create(0.05f, 0.1f, 0).setEasing(Easing.EXPO_IN, Easing.QUINTIC_IN_OUT).build())
                            .setLifetime(30)
                            .randomVelocity(0.025f)
                            .addVelocity(0, 0.03f, 0)
                            .spawn(level, hitResult.getPos());
                }
            }
        }
    }

    @Override
    public void useWand(Level level, Player player, InteractionHand hand, ItemStack stack) {
        if (!level.isClientSide()) {
            player.startUsingItem(hand);
        }
    }

    @Override
    public void finishUseSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            spellContext.setCooldown(this);
            spellContext.removeWissen(this);
            spellContext.awardStat(this);
            spellContext.spellSound(this);
        }
        lookSpell(level, spellContext);
    }

    @Override
    public void lookSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            RayHitResult hitResult = getHit(level, spellContext);
            BlockPos blockPos = hitResult.getBlockPos();
            Player player = null;
            if (spellContext.getEntity() instanceof Player) {
                player = (Player) spellContext.getEntity();
            }
            BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(level, blockPos, level.getBlockState(blockPos), player);

            if (!MinecraftForge.EVENT_BUS.post(breakEvent)) {
                if (level.getBlockEntity(blockPos) instanceof CrystalBlockEntity crystal) {
                    if (crystal.getCrystalItem().getItem() instanceof CrystalItem crystalItem) {
                        for (int i = 0; i < 3; i++) {
                            ItemStack fracturedCrystal = crystalItem.getType().getFracturedCrystal();
                            fracturedCrystal.setTag(crystal.getCrystalItem().getOrCreateTag().copy());
                            level.addFreshEntity(new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, fracturedCrystal));
                        }
                        level.removeBlockEntity(blockPos);
                        level.destroyBlock(blockPos, false);

                        Color color = crystalItem.getType().getColor();
                        WizardsRebornPacketHandler.sendToTracking(level, blockPos, new CrystalCrushingSpellPacket(blockPos.getCenter(), color));
                        if (player != null) WizardsRebornPacketHandler.sendTo(player, new CrystalCrushingSpellScreenshakePacket());
                    }
                } else {
                    if (player != null) {
                        if (spellContext.getItemStack().getItem() instanceof ArcaneWandItem wand) {
                            SimpleContainer inventory = ArcaneWandItem.getInventory(spellContext.getItemStack());
                            ItemStack crystal = inventory.getItem(0);
                            if (crystal.getItem() instanceof CrystalItem crystalItem) {
                                for (int i = 0; i < 3; i++) {
                                    ItemStack fracturedCrystal = crystalItem.getType().getFracturedCrystal();
                                    fracturedCrystal.setTag(crystal.getOrCreateTag().copy());
                                    level.addFreshEntity(new ItemEntity(level, player.getX(), player.getY() + 0.5F, player.getZ(), fracturedCrystal));
                                }
                                CompoundTag nbt = spellContext.getItemStack().getOrCreateTag();
                                inventory.setItem(0, ItemStack.EMPTY);
                                nbt.putBoolean("crystal", false);

                                DamageSource damageSource = getDamage(WizardsRebornDamageTypes.ARCANE_MAGIC, player);
                                player.hurt(damageSource, 10);

                                Vec3 effectPos = player.getLookAngle().scale(1f).add(player.getEyePosition());
                                Color color = crystalItem.getType().getColor();
                                WizardsRebornPacketHandler.sendToTracking(level, player.blockPosition(), new CrystalCrushingSpellPacket(effectPos, color));
                                WizardsRebornPacketHandler.sendTo(player, new CrystalCrushingSpellScreenshakePacket());
                                level.playSound(null, player.getX(), player.getY(), player.getZ(), WizardsRebornSounds.CRYSTAL_BREAK.get(), SoundSource.PLAYERS, 1f, 0.5f);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public boolean hasCustomAnimation(ItemStack stack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemAnimation getAnimation(ItemStack stack) {
        return animation;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 100;
    }
}
