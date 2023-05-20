package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.data.recipes.ArcanumDustTransmutationRecipe;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ArcanumDustItem extends Item {
    public ArcanumDustItem(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);

        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos blockpos = context.getPos();

        Inventory inv = new Inventory(1);
        inv.setInventorySlotContents(0, world.getBlockState(blockpos).getBlock().asItem().getDefaultInstance());

        Optional<ArcanumDustTransmutationRecipe> recipe = world.getRecipeManager()
                .getRecipe(WizardsReborn.ARCANUM_DUST_TRANSMUTATION_RECIPE, inv, world);

        AtomicBoolean place_block = new AtomicBoolean(true);
        AtomicReference<ItemStack> item = new AtomicReference<>(new ItemStack(null));

        recipe.ifPresent(iRecipe -> {
            place_block.set(iRecipe.getPlaceBlock());
            item.set(iRecipe.getRecipeOutput().copy());
        });

        boolean craft = false;

        if (!(item.get().isEmpty())) {
            if (item.get().getItem() instanceof BlockItem) {
                BlockItem blockitem = (BlockItem) item.get().getItem();
                world.destroyBlock(blockpos, false);
                if (place_block.get()) {
                    world.setBlockState(blockpos, blockitem.getBlock().getDefaultState());
                } else {
                    if (!world.isRemote()) {
                        world.addEntity(new ItemEntity(world, blockpos.getX() + 0.5F, blockpos.getY() + 0.5F, blockpos.getZ() + 0.5F, item.get()));
                    }
                }

                craft = true;
            } else {
                world.destroyBlock(blockpos, false);
                if (!world.isRemote()) {
                    world.addEntity(new ItemEntity(world, blockpos.getX() + 0.5F, blockpos.getY() + 0.5F, blockpos.getZ() + 0.5F, item.get()));
                }

                craft = true;
            }

            if (craft) {
                if (!player.isCreative()) {
                    stack.setCount(stack.getCount()-1);
                }

                if (world.isRemote()) {
                    Vector3d pos = player.getPositionVec().add(player.getLookVec().scale(0.5)).add(0.5 * Math.sin(Math.toRadians(0 - player.rotationYawHead)), player.getHeight() * 2 / 3, 0.5 * Math.cos(Math.toRadians(0 - player.rotationYawHead)));
                    Vector3d vel = player.getEyePosition(0).add(player.getLookVec().scale(40)).subtract(pos).scale(1.0 / 20).normalize().scale(0.2f);

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
