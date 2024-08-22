package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.fluffy_fur.common.item.ICustomBlockEntityDataItem;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamItem;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
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

public class SteamStorageBaseItem extends BlockItem implements ISteamItem, ICustomBlockEntityDataItem {

    public SteamStorageBaseItem(Block blockIn, Properties properties) {
        super(blockIn, properties);
    }

    @Override
    public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag tileNbt) {
        if (!tileNbt.contains("steam")) {
            CompoundTag nbt = stack.getOrCreateTag();
            tileNbt.putInt("steam", nbt.getInt("steam"));
        }

        return tileNbt;
    }

    @Override
    public int getMaxSteam() {
        if (getBlock() instanceof EntityBlock tileBlock) {
            BlockEntity tile = tileBlock.newBlockEntity(new BlockPos(0, 0, 0), getBlock().defaultBlockState());
            if (tile instanceof ISteamBlockEntity steamTile) {
                return steamTile.getMaxSteam();
            }
        }
        return 0;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        if (ClientConfig.NUMERICAL_STEAM.get()) {
            CompoundTag nbt = stack.getTag();
            if (nbt != null && nbt.contains("steam")) {
                list.add(NumericalUtils.getSteamName(nbt.getInt("steam"), getMaxSteam()).copy().withStyle(ChatFormatting.GRAY));
            }
        }
    }
}