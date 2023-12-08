package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SmokeEffectPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SmokingPipeItem extends Item {
    public SmokingPipeItem(Properties properties) {
        super(properties);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entityLiving, int pTimeLeft) {
        if (!world.isClientSide) {
            if (entityLiving instanceof Player player) {
                if (getUseDuration(stack) - player.getUseItemRemainingTicks() >= 20) {
                    Vec3 posSmoke = player.getEyePosition().add(player.getLookAngle().scale(0.75f));
                    Vec3 vel = player.getEyePosition().add(player.getLookAngle().scale(40)).subtract(posSmoke).scale(1.0 / 20).normalize().scale(0.05f);
                    PacketHandler.sendToTracking(world, player.getOnPos(), new SmokeEffectPacket((float) posSmoke.x, (float) posSmoke.y, (float) posSmoke.z, (float) vel.x, (float) vel.y, (float) vel.z, 1f, 1f, 1f));
                    world.playSound(null, player.getOnPos(), WizardsReborn.STEAM_BURST_SOUND.get(), SoundSource.PLAYERS, 0.1f, 2.0f);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.NONE;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }
}