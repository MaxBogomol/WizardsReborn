package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

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

        Vector3d pos = player.getPositionVec().add(player.getLookVec().scale(0.5)).add(0.5 * Math.sin(Math.toRadians(225 - player.rotationYawHead)), player.getHeight() * 2 / 3, 0.5 * Math.cos(Math.toRadians(225 - player.rotationYawHead)));
        Vector3d vel = player.getEyePosition(0).add(player.getLookVec().scale(40)).subtract(pos).scale(1.0 / 20).normalize().scale(0.2f);

        for (int i = 0; i < 20; i ++) {
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
                    .setSpin( (0.5f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(world, pos.x + ((random.nextDouble() - 0.5D) / 3), pos.y + ((random.nextDouble() - 0.5D) / 3), pos.z + ((random.nextDouble() - 0.5D) / 3));
        }

        return super.onItemUseFirst(stack, context);
    }
}
