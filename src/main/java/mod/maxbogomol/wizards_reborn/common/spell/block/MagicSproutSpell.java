package mod.maxbogomol.wizards_reborn.common.spell.block;

import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.MagicSproutSpellEffectPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.level.BlockEvent;

import java.awt.*;

public class MagicSproutSpell extends Spell {
    public MagicSproutSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.EARTH);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.earthSpellColor;
    }

    @Override
    public boolean canSpellAir(Level world, Player player, InteractionHand hand) {
        return false;
    }

    @Override
    public InteractionResult onWandUseOn(ItemStack stack, UseOnContext context) {
        if (canSpell(context.getLevel(), context.getPlayer(), context.getHand()) && !context.getPlayer().level().isClientSide()) {
            Color color = getColor();
            float r = color.getRed() / 255f;
            float g = color.getGreen() / 255f;
            float b = color.getBlue() / 255f;

            if (growCrop(stack, context, context.getClickedPos()) == InteractionResult.SUCCESS) {
                if (!context.getPlayer().isShiftKeyDown()) {
                    useGrow(stack, context, 1f, 1f);
                    PacketHandler.sendToTracking(context.getPlayer().level(), context.getClickedPos(), new MagicSproutSpellEffectPacket((float) context.getClickedPos().getX() + 0.5F, (float) context.getClickedPos().getY() + 0.5F, (float) context.getClickedPos().getZ() + 0.5F, r, g, b));
                } else if (WissenItemUtil.canRemoveWissen(stack, getWissenCostWithStat(getStats(stack), context.getPlayer(), getWissenCost() * 10))) {
                    CompoundTag stats = getStats(stack);
                    int focusLevel = CrystalUtil.getStatLevel(stats, WizardsRebornCrystals.FOCUS);
                    float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(context.getPlayer());
                    int radius = (int) (1 + magicModifier);
                    BlockPos blockPos = context.getClickedPos();
                    for (int x = -radius; x <= radius; x++) {
                        for (int y = -radius; y <= radius; y++) {
                            for (int z = -radius; z <= radius; z++) {
                                if (random.nextFloat() < 0.15f * (focusLevel + magicModifier)) {
                                    if (growCrop(stack, context, new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z)) == InteractionResult.SUCCESS) {
                                        PacketHandler.sendToTracking(context.getPlayer().level(), context.getClickedPos(), new MagicSproutSpellEffectPacket((float) blockPos.getX() + x + 0.5F, (float) blockPos.getY() + y + 0.5F, (float) blockPos.getZ() + z + 0.5F, r, g, b));
                                    }
                                }
                            }
                        }
                    }
                    useGrow(stack, context, 5f, 10f);
                }
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    public static InteractionResult growCrop(ItemStack stack, UseOnContext context, BlockPos blockPos) {
        BlockEvent.EntityPlaceEvent placeEv = new BlockEvent.EntityPlaceEvent(
                BlockSnapshot.create(context.getLevel().dimension(), context.getLevel(), blockPos),
                context.getLevel().getBlockState(blockPos.below()),
                context.getPlayer()
        );
        if (MinecraftForge.EVENT_BUS.post(placeEv)) return InteractionResult.FAIL;

        if (BoneMealItem.growCrop(ItemStack.EMPTY, context.getLevel(), blockPos)) {
            return InteractionResult.SUCCESS;
        } else {
            BlockState blockstate = context.getLevel().getBlockState(blockPos);
            boolean flag = blockstate.isFaceSturdy(context.getLevel(), blockPos, context.getClickedFace());
            if (flag && BoneMealItem.growWaterPlant(ItemStack.EMPTY, context.getLevel(), blockPos.relative(context.getClickedFace()), context.getClickedFace())) {
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    public void useGrow(ItemStack stack, UseOnContext context, float cooldownModifier, float costModifier) {
        Player player = context.getPlayer();
        CompoundTag stats = getStats(stack);
        setCooldown(stack, stats, (int) (getCooldown() * cooldownModifier));
        removeWissen(stack, stats, player, (int) (getWissenCost() * costModifier));
        awardStat(player, stack);
        spellSound(player, context.getLevel());
    }
}
