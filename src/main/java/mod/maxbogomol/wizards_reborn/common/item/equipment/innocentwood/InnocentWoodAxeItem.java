package mod.maxbogomol.wizards_reborn.common.item.equipment.innocentwood;

import mod.maxbogomol.wizards_reborn.common.item.equipment.arcanewood.ArcaneWoodAxeItem;
import mod.maxbogomol.wizards_reborn.common.network.InnocentWoodToolsEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import java.util.Random;
import java.util.function.Consumer;

public class InnocentWoodAxeItem extends ArcaneWoodAxeItem {
    private static Random random = new Random();

    public InnocentWoodAxeItem(Tier tier, float attackDamageModifier, float attackSpeedModifier, Properties properties, Item repairItem) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties, repairItem);
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
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        if (entity != null) {
            if (!entity.level().isClientSide) {
                if (entity.getHealth() != entity.getMaxHealth() && random.nextFloat() < 0.35f + (0.15f * getLifeRoots(stack))) {
                    entity.heal(1f);
                    entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), getRepairSound(stack, entity.level(), entity), SoundSource.PLAYERS, 0.25f, 2f);
                    PacketHandler.sendToTracking(entity.level(), entity.getOnPos(), new InnocentWoodToolsEffectPacket((float) entity.getX(), (float) entity.getY() + (entity.getBbHeight() / 2), (float) entity.getZ()));
                }
            }
        }
        return super.damageItem(stack, amount, entity, onBroken);
    }
}
