package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.WissenDustBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcanumDustTransmutationRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
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
        Level world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();

        if (!world.isClientSide) {
            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, world.getBlockState(blockpos).getBlock().asItem().getDefaultInstance());
            Optional<ArcanumDustTransmutationRecipe> recipe = world.getRecipeManager()
                    .getRecipeFor(WizardsReborn.ARCANUM_DUST_TRANSMUTATION_RECIPE.get(), inv, world);

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
                    world.destroyBlock(blockpos, false);
                    if (place_block.get()) {
                        world.setBlockAndUpdate(blockpos, blockitem.getBlock().defaultBlockState());
                    } else {
                        if (!world.isClientSide()) {
                            world.addFreshEntity(new ItemEntity(world, blockpos.getX() + 0.5F, blockpos.getY() + 0.5F, blockpos.getZ() + 0.5F, item.get()));
                        }
                    }

                    craft = true;
                } else {
                    world.destroyBlock(blockpos, false);
                    if (!world.isClientSide()) {
                        world.addFreshEntity(new ItemEntity(world, blockpos.getX() + 0.5F, blockpos.getY() + 0.5F, blockpos.getZ() + 0.5F, item.get()));
                    }

                    craft = true;
                }

                if (craft) {
                    if (!player.isCreative()) {
                        stack.setCount(stack.getCount() - 1);
                    }

                    Vec3 pos = player.getEyePosition();
                    Vec3 vel = player.getEyePosition().add(player.getLookAngle().scale(40)).subtract(pos).scale(1.0 / 20).normalize().scale(0.2f);

                    PacketHandler.sendToTracking(world, player.getOnPos(), new WissenDustBurstEffectPacket(blockpos, (float) pos.x, (float) pos.y, (float) pos.z, (float) vel.x, (float) vel.y, (float) vel.z));

                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.PASS;
    }
}
