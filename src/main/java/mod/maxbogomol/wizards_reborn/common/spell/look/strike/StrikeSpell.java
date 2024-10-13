package mod.maxbogomol.wizards_reborn.common.spell.look.strike;

import mod.maxbogomol.wizards_reborn.client.animation.StrikeSpellItemAnimation;
import mod.maxbogomol.wizards_reborn.common.spell.look.block.BlockLookSpell;

import java.awt.*;

public class StrikeSpell extends BlockLookSpell {
    public static StrikeSpellItemAnimation animation = new StrikeSpellItemAnimation();

    public StrikeSpell(String id, int points) {
        super(id, points);
    }

    public Color getSecondColor() {
        return getColor();
    }

/*    @Override
    public float getLookDistance() {
        return 10f;
    }*/

    @Override
    public float getBlockDistance() {
        return 25f;
    }

    @Override
    public int getCooldown() {
        return 300;
    }

    @Override
    public int getWissenCost() {
        return 500;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }
/*
    @Override
    public void useSpell(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            player.startUsingItem(hand);
        }
    }*/
/*
    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if (livingEntity instanceof Player player) {
            if (!level.isClientSide) {
                if (!super.canSpell(level, player, player.getUsedItemHand())) {
                    player.stopUsingItem();
                }

                if (player.getTicksUsingItem() > getUseTime(level, livingEntity, stack, remainingUseDuration)) {
                    CompoundTag stats = getStats(stack);
                    setCooldown(stack, stats);
                    removeWissen(stack, stats, player);
                    awardStat(player, stack);
                    spellSound(player, level);
                    lookSpell(level, player, player.getUsedItemHand());
                    player.stopUsingItem();
                }
            } else {
                Vec3 pos = getBlockHit(level, player, player.getUsedItemHand()).getPosHit();
                float stage = (float) player.getTicksUsingItem() / getUseTime(level, livingEntity, stack, remainingUseDuration);
                Color color = getColor();

                ParticleBuilder.create(FluffyFurParticles.WISP)
                        .setColorData(ColorParticleData.create(color).build())
                        .setTransparencyData(GenericParticleData.create(0.25f, 0).build())
                        .setScaleData(GenericParticleData.create(0.2f * stage, 0).build())
                        .setLifetime(30)
                        .randomVelocity(0.025f)
                        .addVelocity(0, 0.03f, 0)
                        .spawn(level, pos.x(), pos.y(), pos.z());
            }
        }
    }

    @Override
    public void lookSpell(Level level, Player player, InteractionHand hand) {
        CompoundTag stats = getStats(player.getItemInHand(hand));
        Vec3 pos = getBlockHit(level, player, hand).getPosHit();

        SpellProjectileEntity entity = new SpellProjectileEntity(WizardsRebornEntities.SPELL_PROJECTILE.get(), level).shoot(
                pos.x, pos.y, pos.z, 0, 0, 0, player.getUUID(), this.getId(), stats
        );
        entity.setYRot(player.getYRot());
        level.addFreshEntity(entity);
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
                    //if (40f - distanceToPlayer > 0) PacketHandler.sendTo(player, new AddScreenshakePacket(1f - (distanceToPlayer / distance / 2)));
                }

                strikeDamage(entity, entity.getSender());
                strikeEffect(entity);

                for (int i = 0; i < 8; i++) {
                    BlockPos blockPos = entity.getOnPos();
                    BlockState blockState = entity.level().getBlockState(blockPos);
                    boolean blockSound = false;
                    if (!blockState.isAir()) {
                        blockSound = true;
                    } else {
                        blockPos = entity.getOnPos().below();
                        blockState = entity.level().getBlockState(blockPos);
                        if (!blockState.isAir()) blockSound = true;
                    }

                    if (blockSound) {
                        SoundType soundType = blockState.getBlock().getSoundType(blockState);
                        entity.level().playSound(WizardsReborn.proxy.getPlayer(), entity.getX() + ((random.nextDouble() - 0.5D) * 5f), entity.getY() + ((random.nextDouble() - 0.5D) * 5f), entity.getZ() + ((random.nextDouble() - 0.5D) * 5f), soundType.getBreakSound(), SoundSource.PLAYERS, 1f, 1f);
                    }

                    entity.level().playSound(WizardsReborn.proxy.getPlayer(), entity.getX() + ((random.nextDouble() - 0.5D) * 5f), entity.getY() + ((random.nextDouble() - 0.5D) * 5f), entity.getZ() + ((random.nextDouble() - 0.5D) * 5f), WizardsRebornSounds.SPELL_BURST.get(), SoundSource.PLAYERS, 1f, (float) (1f + ((random.nextFloat() - 0.5D) / 4)));
                }
            }
        }
    }

    public List<Entity> getTargets(SpellProjectileEntity entity, float distance) {
        return entity.level().getEntitiesOfClass(Entity.class, new AABB(entity.getX() - distance, entity.getY() - 1, entity.getZ() - distance, entity.getX() + distance, entity.getY() + 3, entity.getZ() + distance));
    }

    public void strikeDamage(SpellProjectileEntity entity, Player player) {

    }

    public void strikeEffect(SpellProjectileEntity entity) {
        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;

        PacketHandler.sendToTracking(entity.level(), entity.getOnPos(), new StrikeSpellEffectPacket((float) entity.getX(), (float) entity.getY(), (float) entity.getZ(), r, g, b));
    }

    public int getUseTime(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
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
        MultiBufferSource bufferDelayed = FluffyFurRenderTypes.getDelayedRender();
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
        //WizardsRebornRenderUtil.raySided(stack, bufferDelayed, 0.2f * alpha, 100 * size, 0.4f, r, g, b, 0.4f * alpha, r, g, b, 0F);
        //WizardsRebornRenderUtil.raySided(stack, bufferDelayed, 0.5f * alpha, 100 * size, 0.4f, sr, sg, sb, 0.2f * alpha, sr, sg, sb, 0F);
        stack.popPose();

        stack.pushPose();
        stack.mulPose(Axis.XP.rotationDegrees(yRot + ticks));
        //WizardsRebornRenderUtil.raySided(stack, bufferDelayed, 0.38f * sizeEnd, 100 * size, 1f, r, g, b, 0.08f * alpha, r, g, b, 0F);
        //WizardsRebornRenderUtil.raySided(stack, bufferDelayed, 0.4f * sizeEnd, 100 * size, 1f, r, g, b, 0.05f * alpha, r, g, b, 0F);
        stack.popPose();

        stack.popPose();
    }*/
}
