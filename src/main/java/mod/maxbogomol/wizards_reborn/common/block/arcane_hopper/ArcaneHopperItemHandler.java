package mod.maxbogomol.wizards_reborn.common.block.arcane_hopper;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.InvWrapper;
import org.jetbrains.annotations.NotNull;

public class ArcaneHopperItemHandler extends InvWrapper
{
    private final ArcaneHopperBlockEntity hopper;

    public ArcaneHopperItemHandler(ArcaneHopperBlockEntity hopper)
    {
        super(hopper);
        this.hopper = hopper;
    }

    @Override
    @NotNull
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
    {
        if (simulate)
        {
            return super.insertItem(slot, stack, simulate);
        }
        else
        {
            boolean wasEmpty = getInv().isEmpty();

            int originalStackSize = stack.getCount();
            stack = super.insertItem(slot, stack, simulate);

            if (wasEmpty && originalStackSize > stack.getCount())
            {
                if (!hopper.isOnCustomCooldown())
                {
                    hopper.setCooldown(8);
                }
            }

            return stack;
        }
    }
}
