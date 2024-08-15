package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.fluffy_fur.common.item.ICustomBlockEntityDataItem;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemType;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtils;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.utils.NumericalUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class WissenStorageBaseItem extends BlockItem implements IWissenItem, ICustomBlockEntityDataItem {

    public WissenStorageBaseItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    @Override
    public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag tileNbt) {
        if (!tileNbt.contains("wissen")) {
            WissenItemUtils.existWissen(stack);
            tileNbt.putInt("wissen", WissenItemUtils.getWissen(stack));
        }

        return tileNbt;
    }

    @Override
    public int getMaxWissen() {
        if (getBlock() instanceof EntityBlock tileBlock) {
            BlockEntity tile = tileBlock.newBlockEntity(new BlockPos(0, 0, 0), getBlock().defaultBlockState());
            if (tile instanceof IWissenTileEntity wissenTile) {
                return wissenTile.getMaxWissen();
            }
        }
        return 0;
    }

    @Override
    public WissenItemType getWissenItemType() {
        return WissenItemType.OFF;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        if (ClientConfig.NUMERICAL_WISSEN.get()) {
            WissenItemUtils.existWissen(stack);
            list.add(NumericalUtils.getWissenName(WissenItemUtils.getWissen(stack), getMaxWissen()).copy().withStyle(ChatFormatting.GRAY));
        }
    }
}