package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.fluffy_fur.common.item.ICustomBlockEntityDataItem;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamItem;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.util.NumericalUtil;
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

    public SteamStorageBaseItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag nbt) {
        if (!nbt.contains("steam")) {
            CompoundTag stackNbt = stack.getOrCreateTag();
            nbt.putInt("steam", stackNbt.getInt("steam"));
        }

        return nbt;
    }

    @Override
    public int getMaxSteam(ItemStack stack) {
        if (getBlock() instanceof EntityBlock block) {
            BlockEntity blockEntity = block.newBlockEntity(new BlockPos(0, 0, 0), getBlock().defaultBlockState());
            if (blockEntity instanceof ISteamBlockEntity steamBlockEntity) {
                return steamBlockEntity.getMaxSteam();
            }
        }
        return 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        if (WizardsRebornClientConfig.NUMERICAL_STEAM.get()) {
            CompoundTag nbt = stack.getTag();
            if (nbt != null && nbt.contains("steam")) {
                list.add(NumericalUtil.getSteamName(nbt.getInt("steam"), getMaxSteam(stack)).copy().withStyle(ChatFormatting.GRAY));
            }
        }
    }
}