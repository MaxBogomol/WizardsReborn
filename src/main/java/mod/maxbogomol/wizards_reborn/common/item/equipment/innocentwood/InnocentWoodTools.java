package mod.maxbogomol.wizards_reborn.common.item.equipment.innocentwood;

import mod.maxbogomol.wizards_reborn.common.entity.InnocentSparkEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcanewood.ArcaneWoodTools;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InnocentWoodTools extends ArcaneWoodTools {

    public InnocentWoodTools(Item repairItem) {
        super(repairItem);
    }

    @Override
    public int repairTick(ItemStack stack, Level level, Entity entity) {
        return 500;
    }

    @Override
    public SoundEvent getRepairSound(ItemStack stack, Level level, Entity entity) {
        return WizardsRebornSounds.INNOCENT_WOOD_PLACE.get();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> result = super.use(level, player, hand);
        if (result.getResult() != InteractionResult.PASS) return result;
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getDamageValue() < stack.getMaxDamage() - 27) {
            if (!level.isClientSide()) {
                int slot = hand == InteractionHand.OFF_HAND ? player.getInventory().getContainerSize() - 1 : player.getInventory().selected;
                InnocentSparkEntity entity = new InnocentSparkEntity(level, player.getEyePosition().x(), player.getEyePosition().y(), player.getEyePosition().z());
                entity.setData(player, slot, player.getEyePosition());
                entity.setItem(stack);

                entity.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 0.5F, 0F);
                level.addFreshEntity(entity);

                stack.hurtAndBreak(25, player, (e) -> {});
                int cooldown = 500 - (150 * getLifeRoots(stack));
                if (cooldown < 50) cooldown = 50;
                player.getCooldowns().addCooldown(stack.getItem(), cooldown);
            }
            return InteractionResultHolder.success(stack);
        }
        return InteractionResultHolder.pass(stack);
    }
}
