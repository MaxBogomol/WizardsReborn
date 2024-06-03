package mod.maxbogomol.wizards_reborn.common.spell.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.awt.*;

public class IceBlockSpell extends BlockPlaceSpell {
    public IceBlockSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.WATER_CRYSTAL_TYPE);
    }

    @Override
    public Color getColor() {
        return WizardsReborn.frostSpellColor;
    }

    @Override
    public InteractionResult placeBlock(ItemStack stack, UseOnContext context, BlockPos blockPos) {
        Level level = context.getLevel();
        BlockPos blockPos1 = blockPos.relative(context.getClickedFace());
        setBlock(level, blockPos1, Blocks.ICE.defaultBlockState(), context.getPlayer());
        return InteractionResult.SUCCESS;
    }
}
