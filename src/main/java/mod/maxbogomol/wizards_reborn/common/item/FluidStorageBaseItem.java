package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidItem;
import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidTileEntity;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.utils.NumericalUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class FluidStorageBaseItem extends BlockItem implements IFluidItem {

    public FluidStorageBaseItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    @Override
    protected boolean updateCustomBlockEntityTag(BlockPos pos, Level level, @Nullable Player player, ItemStack stack, BlockState state) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (!nbt.contains("BlockEntityTag")) {
            CompoundTag tileNbt = new CompoundTag();
            tileNbt.put("fluidTank", nbt.getCompound("fluidTank"));
            nbt.put("BlockEntityTag", tileNbt);
        }

        return BlockItem.updateCustomBlockEntityTag(level, player, pos, stack);
    }

    @Override
    public int getMaxFluid(ItemStack stack) {
        if (getBlock() instanceof EntityBlock tileBlock) {
            BlockEntity tile = tileBlock.newBlockEntity(new BlockPos(0, 0, 0), getBlock().defaultBlockState());
            if (tile instanceof IFluidTileEntity fluidTile) {
                return fluidTile.getFluidMaxAmount();
            }
        }
        return 0;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        if (getBlock() instanceof EntityBlock tileBlock) {
            BlockEntity tile = tileBlock.newBlockEntity(new BlockPos(0, 0, 0), getBlock().defaultBlockState());
            if (tile instanceof IFluidTileEntity fluidTile) {
                CompoundTag nbt = stack.getOrCreateTag();
                if (nbt.contains("fluidTank")) {
                    FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt.getCompound("fluidTank"));
                    Component fluidName = NumericalUtils.getFluidName(fluid, fluidTile.getFluidMaxAmount());
                    if (!ClientConfig.NUMERICAL_FLUID.get()) {
                        fluidName = NumericalUtils.getFluidName(fluid);
                    }
                    list.add(Component.empty().append(fluidName).withStyle(ChatFormatting.GRAY));
                }
            }
        }
    }
}