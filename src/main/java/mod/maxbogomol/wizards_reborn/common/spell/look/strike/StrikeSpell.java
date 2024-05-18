package mod.maxbogomol.wizards_reborn.common.spell.look.strike;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.animation.ItemAnimation;
import mod.maxbogomol.wizards_reborn.client.animation.StrikeSpellItemAnimation;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.client.render.WorldRenderHandler;
import mod.maxbogomol.wizards_reborn.common.entity.SpellProjectileEntity;
import mod.maxbogomol.wizards_reborn.common.network.AddScreenshakePacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.StrikeSpellEffectPacket;
import mod.maxbogomol.wizards_reborn.common.spell.look.block.BlockLookSpell;
import mod.maxbogomol.wizards_reborn.utils.RenderUtils;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;

public class StrikeSpell extends BlockLookSpell {
    public static StrikeSpellItemAnimation animation = new StrikeSpellItemAnimation();

    public StrikeSpell(String id, int points) {
        super(id, points);
    }

    public Color getSecondColor() {
        return getColor();
    }

    @Override
    public float getLookDistance() {
        return 10f;
    }

    @Override
    public float getBlockDistance() {
        return 25f;
    }

    @Override
    public void useSpell(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            player.startUsingItem(hand);
        }
    }

    @Override
    public void onUseTick(Level world, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (livingEntity instanceof Player player) {
            if (!world.isClientSide) {
                if (!super.canSpell(world, player, player.getUsedItemHand())) {
                    player.stopUsingItem();
                }

                if (player.getTicksUsingItem() > getUseTime(world, livingEntity, stack, remainingUseDuration)) {
                    CompoundTag stats = getStats(stack);
                    setCooldown(stack, stats);
                    removeWissen(stack, stats, player);
                    awardStat(player, stack);
                    spellSound(player, world);
                    lookSpell(world, player, player.getUsedItemHand());
                    player.stopUsingItem();
                }
            } else {
                Vec3 pos = getBlockHit(world, player, player.getUsedItemHand()).getPosHit();
                float stage = (float) player.getTicksUsingItem() / getUseTime(world, livingEntity, stack, remainingUseDuration);
                Color color = getColor();
                float r = color.getRed() / 255f;
                float g = color.getGreen() / 255f;
                float b = color.getBlue() / 255f;

                Particles.create(WizardsReborn.WISP_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 20), (((random.nextDouble() - 0.5D) / 20)) + 0.03f, ((random.nextDouble() - 0.5D) / 20))
                        .setAlpha(0.25f, 0).setScale(0.2f * stage, 0)
                        .setColor(r, g, b)
                        .setLifetime(30)
                        .spawn(world, pos.x(), pos.y(), pos.z());
            }
        }
    }

    @Override
    public void lookSpell(Level world, Player player, InteractionHand hand) {
        CompoundTag stats = getStats(player.getItemInHand(hand));
        Vec3 pos = getBlockHit(world, player, hand).getPosHit();

        SpellProjectileEntity entity = new SpellProjectileEntity(WizardsReborn.SPELL_PROJECTILE.get(), world).shoot(
                pos.x, pos.y, pos.z, 0, 0, 0, player.getUUID(), this.getId(), stats
        );
        entity.setYRot(player.getYRot());
        world.addFreshEntity(entity);
    }

    @Override
    public void entityTick(SpellProjectileEntity entity) {
        if (!entity.level().isClientSide) {
            if (entity.tickCount > getLifeTime(entity)) {
                entity.remove();
            }
            if (entity.tickCount == 20) {
                float distance = 40f;
                List<Player> players = entity.level().getEntitiesOfClass(Player.class, new AABB(entity.getX() - distance, entity.getY() - distance, entity.getZ() - distance, entity.getX() + distance, entity.getY() + distance, entity.getZ() + distance));
                for (Player player : players) {
                    float distanceToPlayer = (float) Math.sqrt(Math.pow(entity.getX() - player.getX(), 2) + Math.pow(entity.getY() - player.getY(), 2) + Math.pow(entity.getZ() - player.getZ(), 2));
                    if (40f - distanceToPlayer > 0) PacketHandler.sendToTracking(entity.level(), entity.getOnPos(), new AddScreenshakePacket(1f - (distanceToPlayer / distance / 2)));
                }

                strikeEffect(entity);

                for (int i = 0; i < 8; i++) {
                    BlockPos blockPos = entity.getOnPos();
                    BlockState blockState = entity.level().getBlockState(blockPos);
                    if (!blockState.isAir()) {
                        SoundType soundType = blockState.getBlock().getSoundType(blockState);
                        entity.level().playSound(WizardsReborn.proxy.getPlayer(), entity.getX() + ((random.nextDouble() - 0.5D) * 5f), entity.getY() + ((random.nextDouble() - 0.5D) * 5f), entity.getZ() + ((random.nextDouble() - 0.5D) * 5f), soundType.getBreakSound(), SoundSource.PLAYERS, 1f, 1f);
                    }
                    entity.level().playSound(WizardsReborn.proxy.getPlayer(), entity.getX() + ((random.nextDouble() - 0.5D) * 5f), entity.getY() + ((random.nextDouble() - 0.5D) * 5f), entity.getZ() + ((random.nextDouble() - 0.5D) * 5f), WizardsReborn.SPELL_BURST_SOUND.get(), SoundSource.PLAYERS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                }
            }
        }
    }

    public void strikeEffect(SpellProjectileEntity entity) {
        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        PacketHandler.sendToTracking(entity.level(), entity.getOnPos(), new StrikeSpellEffectPacket((float) entity.getX(), (float) entity.getY(), (float) entity.getZ(), r, g, b));
    }

    public int getUseTime(Level world, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        return 100;
    }

    public int getLifeTime(SpellProjectileEntity entity) {
        return 60;
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
    @OnlyIn(Dist.CLIENT)
    public void render(SpellProjectileEntity entity, float entityYaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int light) {
        MultiBufferSource bufferDelayed = WorldRenderHandler.getDelayedRender();
        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        Color secondColor = getSecondColor();
        float sr = secondColor.getRed() / 255f;
        float sg = secondColor.getGreen() / 255f;
        float sb = secondColor.getBlue() / 255f;

        float ticks = (entity.tickCount + partialTicks);
        float alpha = 1f;
        int lifeTime = getLifeTime(entity);
        float size = 1f;
        float sizeEnd = 1f;

        if (entity.tickCount < 20) {
            alpha = (entity.tickCount + partialTicks) / 20f;
            size = alpha;
            sizeEnd = alpha;
        }
        if (entity.tickCount > 20) {
            alpha = ((lifeTime - entity.tickCount - partialTicks) / (lifeTime - 20));
            sizeEnd = 1f + ((1f - alpha) * 3);
        }
        if (alpha > 1f) alpha = 1f;
        if (alpha < 0f) alpha = 0f;

        float yRot = Mth.lerp(partialTicks, -entity.yRotO, -entity.getYRot()) - 90.0F;

        stack.pushPose();
        stack.translate(0, 100 - (100 * size), 0);
        stack.mulPose(Axis.ZP.rotationDegrees(90f));

        stack.pushPose();
        stack.mulPose(Axis.XP.rotationDegrees(yRot));
        RenderUtils.raySided(stack, bufferDelayed, 0.2f * alpha, 100 * size, 0.4f, r, g, b, 0.4f * alpha, r, g, b, 0F);
        RenderUtils.raySided(stack, bufferDelayed, 0.5f * alpha, 100 * size, 0.4f, r, g, b, 0.2f * alpha, sr, sg, sb, 0F);
        stack.popPose();

        stack.pushPose();
        stack.mulPose(Axis.XP.rotationDegrees(yRot + ticks));
        RenderUtils.raySided(stack, bufferDelayed, 0.38f * sizeEnd, 100 * size, 1f, r, g, b, 0.08f * alpha, r, g, b, 0F);
        RenderUtils.raySided(stack, bufferDelayed, 0.4f * sizeEnd, 100 * size, 1f, r, g, b, 0.05f * alpha, r, g, b, 0F);
        stack.popPose();

        stack.popPose();
    }
}
