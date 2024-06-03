package mod.maxbogomol.wizards_reborn.common.spell.block;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.awt.*;

public class LavaBlockSpell extends BlockPlaceSpell {
    public LavaBlockSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.FIRE_CRYSTAL_TYPE);
    }

    @Override
    public int getCooldown() {
        return 100;
    }

    @Override
    public int getWissenCost() {
        return 100;
    }

    @Override
    public Color getColor() {
        return WizardsReborn.fireCrystalColor;
    }

    @Override
    public InteractionResult placeBlock(ItemStack stack, UseOnContext context, BlockPos blockPos) {
        Level level = context.getLevel();
        BlockPos blockPos1 = blockPos.relative(context.getClickedFace());
        setBlock(level, blockPos1, Blocks.LAVA.defaultBlockState(), context.getPlayer());
        return InteractionResult.SUCCESS;
    }
}
