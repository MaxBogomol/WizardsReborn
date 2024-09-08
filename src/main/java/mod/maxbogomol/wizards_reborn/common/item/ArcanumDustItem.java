package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.WissenDustBurstEffectPacket;
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
    private static Random random = new Random();

    public ArcanumDustItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();

        if (!level.isClientSide) {
            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, level.getBlockState(blockpos).getBlock().asItem().getDefaultInstance());
            Optional<ArcanumDustTransmutationRecipe> recipe = level.getRecipeManager()
                    .getRecipeFor(WizardsRebornRecipes.ARCANUM_DUST_TRANSMUTATION.get(), inv, level);

            AtomicBoolean place_block = new AtomicBoolean(true);
            AtomicReference<ItemStack> item = new AtomicReference<>(ItemStack.EMPTY);

            recipe.ifPresent(iRecipe -> {
                place_block.set(iRecipe.getPlaceBlock());
                item.set(iRecipe.getResultItem(RegistryAccess.EMPTY).copy());
            });

            boolean craft = false;

            if (!(item.get().isEmpty())) {
                if (item.get().getItem() instanceof BlockItem) {
                    BlockItem blockitem = (BlockItem) item.get().getItem();
                    level.destroyBlock(blockpos, false);
                    if (place_block.get()) {
                        level.setBlockAndUpdate(blockpos, blockitem.getBlock().defaultBlockState());
                    } else {
                        if (!level.isClientSide()) {
                            level.addFreshEntity(new ItemEntity(level, blockpos.getX() + 0.5F, blockpos.getY() + 0.5F, blockpos.getZ() + 0.5F, item.get()));
                        }
                    }

                    craft = true;
                } else {
                    level.destroyBlock(blockpos, false);
                    if (!level.isClientSide()) {
                        level.addFreshEntity(new ItemEntity(level, blockpos.getX() + 0.5F, blockpos.getY() + 0.5F, blockpos.getZ() + 0.5F, item.get()));
                    }

                    craft = true;
                }

                if (craft) {
                    if (!player.isCreative()) {
                        stack.setCount(stack.getCount() - 1);
                    }

                    Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(0.75f));
                    Vec3 vel = player.getEyePosition().add(player.getLookAngle().scale(40)).subtract(pos).scale(1.0 / 20).normalize().scale(0.2f);

                    PacketHandler.sendToTracking(level, player.getOnPos(), new WissenDustBurstEffectPacket(blockpos, (float) pos.x, (float) pos.y, (float) pos.z, (float) vel.x, (float) vel.y, (float) vel.z));

                    player.awardStat(Stats.ITEM_USED.get(this));
                    level.playSound(WizardsReborn.proxy.getPlayer(), blockpos, WizardsRebornSounds.ARCANUM_DUST_TRANSMUTATION.get(), SoundSource.PLAYERS, 1f, (float) (1.0f + ((random.nextFloat() - 0.5D) / 2)));

                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }

    public static boolean executeTransmutation(ItemStack stack, Level level, BlockPos blockpos) {
        if (!level.isClientSide) {
            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, level.getBlockState(blockpos).getBlock().asItem().getDefaultInstance());
            Optional<ArcanumDustTransmutationRecipe> recipe = level.getRecipeManager()
                    .getRecipeFor(WizardsRebornRecipes.ARCANUM_DUST_TRANSMUTATION.get(), inv, level);

            AtomicBoolean place_block = new AtomicBoolean(true);
            AtomicReference<ItemStack> item = new AtomicReference<>(ItemStack.EMPTY);

            recipe.ifPresent(iRecipe -> {
                place_block.set(iRecipe.getPlaceBlock());
                item.set(iRecipe.getResultItem(RegistryAccess.EMPTY).copy());
            });

            boolean craft = false;

            if (!(item.get().isEmpty())) {
                if (item.get().getItem() instanceof BlockItem) {
                    BlockItem blockitem = (BlockItem) item.get().getItem();
                    level.destroyBlock(blockpos, false);
                    if (place_block.get()) {
                        level.setBlockAndUpdate(blockpos, blockitem.getBlock().defaultBlockState());
                    } else {
                        if (!level.isClientSide()) {
                            level.addFreshEntity(new ItemEntity(level, blockpos.getX() + 0.5F, blockpos.getY() + 0.5F, blockpos.getZ() + 0.5F, item.get()));
                        }
                    }

                    craft = true;
                } else {
                    level.destroyBlock(blockpos, false);
                    if (!level.isClientSide()) {
                        level.addFreshEntity(new ItemEntity(level, blockpos.getX() + 0.5F, blockpos.getY() + 0.5F, blockpos.getZ() + 0.5F, item.get()));
                    }

                    craft = true;
                }
            }

            if (craft) {
                stack.setCount(stack.getCount() - 1);
                PacketHandler.sendToTracking(level, blockpos, new WissenDustBurstEffectPacket(blockpos, blockpos.getX() + 0.5F,  blockpos.getY() + 0.5F,  blockpos.getZ() + 0.5F, 0, 0, 0));
                level.playSound(WizardsReborn.proxy.getPlayer(), blockpos, WizardsRebornSounds.WISSEN_TRANSFER.get(), SoundSource.PLAYERS, 0.5f, (float) (1.1f + ((random.nextFloat() - 0.5D) / 2)));
                level.playSound(WizardsReborn.proxy.getPlayer(), blockpos, WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.PLAYERS, 0.5f, (float) (1.3f + ((random.nextFloat() - 0.5D) / 2)));

                return true;
            }
        }

        return false;
    }
}
