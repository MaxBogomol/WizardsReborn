package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.recipe.ArcanumDustTransmutationRecipe;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ArcanumDustItem extends Item {
    private static Random random = new Random();

    public ArcanumDustItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();

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
                    stack.setCount(stack.getCount()-1);
                }

                if (world.isClientSide()) {
                    Vec3 pos = player.getEyePosition(0);
                    Vec3 vel = player.getEyePosition(0).add(player.getLookAngle().scale(40)).subtract(pos).scale(1.0 / 20).normalize().scale(0.2f);

                    for (int i = 0; i < 20; i++) {
                        Particles.create(WizardsReborn.WISP_PARTICLE)
                                .addVelocity(vel.x + ((random.nextDouble() - 0.5D) / 30), vel.y + ((random.nextDouble() - 0.5D) / 30), vel.z + ((random.nextDouble() - 0.5D) / 30))
                                .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                                .setLifetime(20)
                                .spawn(world, pos.x + ((random.nextDouble() - 0.5D) / 3), pos.y + ((random.nextDouble() - 0.5D) / 3), pos.z + ((random.nextDouble() - 0.5D) / 3));
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(vel.x + ((random.nextDouble() - 0.5D) / 30), vel.y + ((random.nextDouble() - 0.5D) / 30), vel.z + ((random.nextDouble() - 0.5D) / 30))
                                .setAlpha(0.125f, 0).setScale(0.2f, 0)
                                .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                                .setLifetime(20)
                                .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, pos.x + ((random.nextDouble() - 0.5D) / 3), pos.y + ((random.nextDouble() - 0.5D) / 3), pos.z + ((random.nextDouble() - 0.5D) / 3));
                    }

                    for (int i = 0; i < 15; i++) {
                        Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                                .addVelocity(((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30), ((random.nextDouble() - 0.5D) / 30))
                                .setAlpha(0.25f, 0).setScale(0.3f, 0)
                                .setColor(0.466f, 0.643f, 0.815f, 0.466f, 0.643f, 0.815f)
                                .setLifetime(30)
                                .setSpin((0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                                .spawn(world, blockpos.getX() + 0.5F + ((random.nextDouble() - 0.5D) * 1.25), blockpos.getY() + 0.5F + ((random.nextDouble() - 0.5D) * 1.25), blockpos.getZ() + 0.5F + ((random.nextDouble() - 0.5D) * 1.25));
                    }
                }
            }
        }

        return super.onItemUseFirst(stack, context);
    }
}
