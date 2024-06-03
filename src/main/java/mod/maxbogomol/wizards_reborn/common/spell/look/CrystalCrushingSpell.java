package mod.maxbogomol.wizards_reborn.common.spell.look;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.animation.ItemAnimation;
import mod.maxbogomol.wizards_reborn.client.animation.StrikeSpellItemAnimation;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.damage.DamageSourceRegistry;
import mod.maxbogomol.wizards_reborn.common.item.CrystalItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.network.AddScreenshakePacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.CrystalCrushingSpellEffectPacket;
import mod.maxbogomol.wizards_reborn.common.tileentity.CrystalTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
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
        addCrystalType(WizardsReborn.VOID_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.voidSpellColor;
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
    public void useSpell(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            player.startUsingItem(hand);
        }
    }

    @Override
    public void onUseTick(Level world, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (world.isClientSide) {
            if (livingEntity instanceof Player player) {
                Vec3 pos = getHitPos(world, player, player.getUsedItemHand()).getPosHit();
                float ds = (float) Math.sqrt(Math.pow(player.getEyePosition().x() - pos.x(), 2) + Math.pow(player.getEyePosition().y() - pos.y(), 2) + Math.pow(player.getEyePosition().z() - pos.z(), 2));

                double dX = player.getEyePosition().x() - pos.x();
                double dY = player.getEyePosition().y() - pos.y();
                double dZ = player.getEyePosition().z() - pos.z();

                float x = (float) -(dX / ds) * (ds + 0.1f);
                float y = (float) -(dY / ds) * (ds + 0.1f);
                float z = (float) -(dZ / ds) * (ds + 0.1f);

                float X = (float) (player.getEyePosition().x() + x);
                float Y = (float) (player.getEyePosition().y() + y);
                float Z = (float) (player.getEyePosition().z() + z);

                BlockPos blockPos = BlockPos.containing(X, Y, Z);

                if (world.getBlockEntity(blockPos) instanceof CrystalTileEntity crystal) {
                    Color color = getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    if (random.nextFloat() < 0.075f) {
                        Particles.create(WizardsReborn.KARMA_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 20), (((random.nextDouble() - 0.5D) / 20)) + 0.03f, ((random.nextDouble() - 0.5D) / 20))
                                .setAlpha(0.25f, 0).setScale(0.1f, 0)
                                .setColor(r, g, b)
                                .setLifetime(30)
                                .spawn(world, pos.x(), pos.y(), pos.z());
                    }
                }
            }
        }
    }

    @Override
    public void finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (!world.isClientSide) {
            if (entityLiving instanceof Player player) {
                CompoundTag stats = getStats(stack);
                removeWissen(stack, stats, player);
                setCooldown(stack, stats);
                awardStat(player, stack);
                spellSound(player, world);
                lookSpell(world, player, player.getUsedItemHand());
                player.stopUsingItem();
            }
        }
    }

    @Override
    public void lookSpell(Level world, Player player, InteractionHand hand) {
        Vec3 pos = getHitPos(world, player, hand).getPosHit();
        float ds = (float) Math.sqrt(Math.pow(player.getEyePosition().x() - pos.x(), 2) + Math.pow(player.getEyePosition().y() - pos.y(), 2) + Math.pow(player.getEyePosition().z() - pos.z(), 2));

        double dX = player.getEyePosition().x() - pos.x();
        double dY = player.getEyePosition().y() - pos.y();
        double dZ = player.getEyePosition().z() - pos.z();

        float x = (float) -(dX / ds) * (ds + 0.1f);
        float y = (float) -(dY / ds) * (ds + 0.1f);
        float z = (float) -(dZ / ds) * (ds + 0.1f);

        float X = (float) (player.getEyePosition().x() + x);
        float Y = (float) (player.getEyePosition().y() + y);
        float Z = (float) (player.getEyePosition().z() + z);

        BlockPos blockPos = BlockPos.containing(X, Y, Z);
        BlockEvent.BreakEvent breakEv = new BlockEvent.BreakEvent(world, blockPos, world.getBlockState(blockPos), player);

        if (!MinecraftForge.EVENT_BUS.post(breakEv)) {
            if (world.getBlockEntity(blockPos) instanceof CrystalTileEntity crystal) {
                if (crystal.getCrystalItem().getItem() instanceof CrystalItem crystalItem) {
                    for (int i = 0; i < 3; i++) {
                        ItemStack fracturedCrystal = crystalItem.getType().getFracturedCrystal();
                        fracturedCrystal.setTag(crystal.getCrystalItem().getOrCreateTag().copy());
                        world.addFreshEntity(new ItemEntity(world, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, fracturedCrystal));
                    }
                    world.removeBlockEntity(blockPos);
                    world.destroyBlock(blockPos, false);

                    Color color = crystalItem.getType().getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;
                    PacketHandler.sendToTracking(world, player.getOnPos(), new CrystalCrushingSpellEffectPacket((float) blockPos.getX() + 0.5f, (float) blockPos.getY() + 0.5f, (float) blockPos.getZ() + 0.5f, r, g, b));
                    PacketHandler.sendTo(player, new AddScreenshakePacket(0.6f));
                }
            } else {
                ItemStack wandItem = player.getItemInHand(hand);
                if (wandItem.getItem() instanceof ArcaneWandItem wand) {
                    SimpleContainer inv = ArcaneWandItem.getInventory(wandItem);
                    ItemStack crystal = inv.getItem(0);
                    if (crystal.getItem() instanceof CrystalItem crystalItem) {
                        for (int i = 0; i < 3; i++) {
                            ItemStack fracturedCrystal = crystalItem.getType().getFracturedCrystal();
                            fracturedCrystal.setTag(crystal.getOrCreateTag().copy());
                            world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY() + 0.5F, player.getZ(), fracturedCrystal));
                        }
                        CompoundTag nbt = wandItem.getOrCreateTag();
                        inv.setItem(0, ItemStack.EMPTY);
                        nbt.putBoolean("crystal", false);

                        player.hurt(new DamageSource(DamageSourceRegistry.create(player.level(), DamageSourceRegistry.ARCANE_MAGIC).typeHolder(), player), 10);

                        Vec3 effectPos = player.getLookAngle().scale(1f).add(player.getEyePosition());
                        Color color = crystalItem.getType().getColor();
                        float r = color.getRed() / 255f;
                        float g = color.getGreen() / 255f;
                        float b = color.getBlue() / 255f;
                        PacketHandler.sendToTracking(world, player.getOnPos(), new CrystalCrushingSpellEffectPacket((float) effectPos.x(), (float) effectPos.y(), (float) effectPos.z(), r, g, b));
                        world.playSound(WizardsReborn.proxy.getPlayer(), player.getX(), player.getY(), player.getZ(), WizardsReborn.CRYSTAL_BREAK_SOUND.get(), SoundSource.PLAYERS, 1f, 0.5f);
                        PacketHandler.sendTo(player, new AddScreenshakePacket(0.6f));
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
