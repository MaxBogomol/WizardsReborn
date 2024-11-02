package mod.maxbogomol.wizards_reborn.common.spell.block;

import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.BlockPlaceSpellPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;

import java.util.List;

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
    public boolean canUseSpell(Level level, SpellContext spellContext) {
        return false;
    }

    @Override
    public boolean useSpellOn(Level level, SpellContext spellContext) {
        BlockPos blockPos = spellContext.getBlockPos().relative(spellContext.getDirection());
        if (canPlaceBlock(level, spellContext, blockPos) && placeBlock(level, spellContext, blockPos)) {
            if (!level.isClientSide()) {
                spellContext.setCooldown(this);
                spellContext.removeWissen(this);
                spellContext.awardStat(this);
                spellContext.spellSound(this);
            }
            return true;
        }
        return false;
    }

    public boolean canPlaceBlock(Level level, SpellContext spellContext, BlockPos blockPos) {
        BlockState blockState = level.getBlockState(blockPos);
        return (blockState.isAir() || !blockState.getFluidState().isEmpty()) && isNoEntity(level, spellContext, blockPos);
    }

    public boolean placeBlock(Level level, SpellContext spellContext, BlockPos blockPos) {
        return true;
    }

    public void setBlock(Level level, SpellContext spellContext, BlockPos blockPos, BlockState blockState) {
        if (!level.isClientSide()) {
            level.setBlockAndUpdate(blockPos, blockState);
            level.gameEvent(null, GameEvent.BLOCK_PLACE, blockPos);
            SoundType soundType = blockState.getSoundType(level, blockPos, spellContext.getEntity());
            level.playSound(null, blockPos, soundType.getPlaceSound(), SoundSource.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
            WizardsRebornPacketHandler.sendToTracking(level, blockPos, new BlockPlaceSpellPacket(blockPos, getColor()));
        }
    }

    public boolean isNoEntity(Level level, SpellContext spellContext, BlockPos blockPos) {
        List<LivingEntity> entityList =  level.getEntitiesOfClass(LivingEntity.class, new AABB(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 1, blockPos.getZ() + 1));
        return entityList.isEmpty();
    }
}
