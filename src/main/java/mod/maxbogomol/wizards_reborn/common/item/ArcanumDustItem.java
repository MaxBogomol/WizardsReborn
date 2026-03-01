package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.item.WissenDustBurstPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcanumDustTransmutationRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ArcanumDustItem extends ArcanumItem {

    private static final Random random = new Random();

    public ArcanumDustItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos blockPos = context.getClickedPos();

        if (!level.isClientSide) {
            Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(0.75f));
            Vec3 vel = player.getLookAngle().scale(40).scale(1.0 / 20).normalize().scale(0.2f);

            if (executeTransmutation(stack, level, blockPos, pos, vel, true, player.isCreative())) {
                player.awardStat(Stats.ITEM_USED.get(this));
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    public static boolean executeTransmutation(ItemStack stack, Level level, BlockPos blockPos, Vec3 pos, Vec3 vel, boolean isPlayer, boolean isCreative) {
        if (!level.isClientSide) {
            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, level.getBlockState(blockPos).getBlock().asItem().getDefaultInstance());
            Optional<ArcanumDustTransmutationRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.ARCANUM_DUST_TRANSMUTATION.get(), inv, level);

            AtomicBoolean placeBlock = new AtomicBoolean(true);
            AtomicReference<ItemStack> item = new AtomicReference<>(ItemStack.EMPTY);

            recipe.ifPresent(iRecipe -> {
                placeBlock.set(iRecipe.getPlaceBlock());
                item.set(iRecipe.getResultItem(RegistryAccess.EMPTY).copy());
            });

            boolean craft = false;

            if (!(item.get().isEmpty())) {
                if (item.get().getItem() instanceof BlockItem blockitem) {
                    level.destroyBlock(blockPos, false);
                    if (placeBlock.get()) {
                        level.setBlockAndUpdate(blockPos, blockitem.getBlock().defaultBlockState());
                    } else {
                        level.addFreshEntity(new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, item.get()));
                    }

                    craft = true;
                } else {
                    level.destroyBlock(blockPos, false);
                    level.addFreshEntity(new ItemEntity(level, blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F, item.get()));

                    craft = true;
                }
            }

            if (craft) {
                if (!isPlayer) {
                    pos = blockPos.getCenter();
                    vel = Vec3.ZERO;
                }
                if (!isCreative) stack.shrink(1);
                WizardsRebornPacketHandler.sendToTracking(level, blockPos, new WissenDustBurstPacket(blockPos, pos, vel));
                level.playSound(null, blockPos, WizardsRebornSounds.ARCANUM_DUST_TRANSMUTATION.get(), SoundSource.PLAYERS, 1f, (float) (1.0f + ((random.nextFloat() - 0.5D) / 2)));

                return true;
            }
        }

        return false;
    }
}
