package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.google.common.base.Preconditions;
import mod.maxbogomol.fluffy_fur.common.item.ICustomBlockEntityDataItem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.common.item.PlacedItem;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

public class AlchemyBottleItem extends PlacedItem implements ICustomBlockEntityDataItem {
    public int maxUses;

    public AlchemyBottleItem(Properties properties, int maxUses) {
        super(properties);
        this.maxUses = maxUses;
    }

    public ItemStack getPotionItem() {
        return new ItemStack(WizardsRebornItems.ALCHEMY_VIAL_POTION.get());
    }

    public static boolean interactWithFluidHandler(@NotNull Player player, @NotNull InteractionHand hand, @NotNull IFluidHandler handler) {
        Preconditions.checkNotNull(player);
        Preconditions.checkNotNull(hand);
        Preconditions.checkNotNull(handler);

        ItemStack stack = player.getItemInHand(hand);

        if (!stack.isEmpty()) {
            if (stack.getItem() instanceof AlchemyBottleItem bottle) {
                AlchemyPotion potion = AlchemyPotionUtil.getPotionFluid(handler.getFluidInTank(0).getFluid());

                if (!AlchemyPotionUtil.isEmpty(potion)) {
                    FluidStack removeFluid = handler.drain(250 * (bottle.maxUses), IFluidHandler.FluidAction.SIMULATE);
                    int remove = removeFluid.getAmount() - (removeFluid.getAmount() % 250);
                    if (remove >= 250) {
                        handler.drain(remove, IFluidHandler.FluidAction.EXECUTE);

                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }

                        ItemStack potionItem = bottle.getPotionItem();
                        AlchemyPotionUtil.setPotion(potionItem, potion);

                        AlchemyPotionItem.setUses(potionItem, bottle.maxUses - (remove / 250));

                        player.level().playSound(WizardsReborn.proxy.getPlayer(), player.getOnPos(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0f, 1.0f);

                        if (stack.isEmpty()) {
                            player.setItemInHand(hand, potionItem);
                        } else {
                            player.getInventory().add(potionItem);
                        }

                        return true;
                    }
                }
            }
        }

        return false;
    }
}