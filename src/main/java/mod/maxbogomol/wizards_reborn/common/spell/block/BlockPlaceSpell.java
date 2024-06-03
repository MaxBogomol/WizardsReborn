package mod.maxbogomol.wizards_reborn.common.spell.block;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.BlockPlaceSpellEffectPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.awt.*;

public class BlockPlaceSpell extends Spell {
    public BlockPlaceSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public int getCooldown() {
        return 10;
    }

    @Override
    public int getWissenCost() {
        return 15;
    }

    @Override
    public boolean canSpellAir(Level world, Player player, InteractionHand hand) {
        return false;
    }

    @Override
    public InteractionResult onWandUseOn(ItemStack stack, UseOnContext context) {
        if (canSpell(context.getLevel(), context.getPlayer(), context.getHand()) && !context.getPlayer().level().isClientSide()) {
            if (canPlaceBlock(stack, context, context.getClickedPos()) && placeBlock(stack, context, context.getClickedPos()) == InteractionResult.SUCCESS) {
                Player player = context.getPlayer();
                CompoundTag stats = getStats(stack);
                setCooldown(stack, stats);
                removeWissen(stack, stats, player);
                awardStat(player, stack);
                spellSound(player, context.getLevel());
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    public boolean canPlaceBlock(ItemStack stack, UseOnContext context, BlockPos blockPos) {
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos().relative(context.getClickedFace()));
        return blockState.isAir() || !blockState.getFluidState().isEmpty();
    }

    public InteractionResult placeBlock(ItemStack stack, UseOnContext context, BlockPos blockPos) {
        return InteractionResult.SUCCESS;
    }

    public void setBlock(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        level.setBlockAndUpdate(blockPos, blockState);
        level.gameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
        SoundType soundtype = blockState.getSoundType(level, blockPos, player);
        level.playSound(null, blockPos, soundtype.getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);

        Color color = getColor();
        float r = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        PacketHandler.sendToTracking(level, player.getOnPos(), new BlockPlaceSpellEffectPacket((float) blockPos.getX() + 0.5f, (float) blockPos.getY() + 0.5f, (float) blockPos.getZ() + 0.5f, r, g, b));
    }
}
