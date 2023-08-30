package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtils;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.common.item.ItemBackedInventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class ArcaneWandItem extends Item implements IWissenItem {
    public ArcaneWandItem(Properties properties) {
        super(properties);
    }

    public static SimpleContainer getInventory(ItemStack stack) {
        return new ItemBackedInventory(stack, 1);
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag oldCapNbt) {
        return new InvProvider(stack);
    }

    private static class InvProvider implements ICapabilityProvider {
        private final LazyOptional<IItemHandler> opt;

        public InvProvider(ItemStack stack) {
            opt = LazyOptional.of(() -> new InvWrapper(getInventory(stack)));
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
            return ForgeCapabilities.ITEM_HANDLER.orEmpty(capability, opt);
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean isSelected) {
        if (!world.isClientSide()) {
            CompoundTag nbt = stack.getTag();
            if (nbt == null) {
                nbt = new CompoundTag();
                nbt.putBoolean("crystal", false);
                nbt.putString("spell", "");
                SimpleContainer item = getInventory(stack);
                stack.setTag(nbt);
            }

            WissenItemUtils.existWissen(stack);
        }
    }

    @Override
    public int getMaxWissen() {
        return 10000;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (!slotChanged) {
            return false;
        }
        return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            KnowledgeUtils.removeAllKnowledge(player);
            CompoundTag nbt = stack.getTag();
            if (nbt.getBoolean("crystal")) {
                Spell spell = Spells.getSpell(nbt.getString("spell"));
                spell.spawnSpellStandart(world, player);
            }
        }

        return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, stack);
    }
}
