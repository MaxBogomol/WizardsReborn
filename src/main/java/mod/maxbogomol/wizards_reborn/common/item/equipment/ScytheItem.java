package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.level.BlockEvent;

import java.util.UUID;

public class ScytheItem extends SwordItem {
    public static final UUID BASE_ENTITY_REACH_UUID = UUID.fromString("DB0F1F0B-7DF7-4D45-BA75-9BA60DABCCCD");
    public final int radius;
    public final float distance;

    public ScytheItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties, float distance, int radius) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
        this.distance = distance;
        this.radius = radius;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> atts = ImmutableMultimap.builder();
        atts.putAll(super.getDefaultAttributeModifiers(slot));
        atts.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(BASE_ENTITY_REACH_UUID, "bonus", distance, AttributeModifier.Operation.ADDITION));
        return slot == EquipmentSlot.MAINHAND ? atts.build() : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        InteractionHand hand = context.getHand();
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        InteractionResult result = onBlockUse(player, level, hand, blockpos, true);

        return result;
    }

    public InteractionResult onBlockUse(Player player, Level level, InteractionHand hand, BlockPos blockPos, boolean initialCall) {
        if (player.isSpectator() || hand == InteractionHand.OFF_HAND) {
            return InteractionResult.PASS;
        }

        BlockState state = level.getBlockState(blockPos);
        ItemStack stack = player.getItemInHand(hand);

        if (state.getBlock() instanceof CropBlock || state.getBlock() instanceof NetherWartBlock) {
            if (isMature(state)) {
                if (radius != 0 && initialCall) {
                    for (int x = -radius; x <= radius; x++) {
                        for (int z = -radius; z <= radius; z++) {
                            BlockPos pos = blockPos.relative(Direction.Axis.X, x).relative(Direction.Axis.Z, z);
                            onBlockUse(player, level, hand, pos, false);
                        }
                    }
                }

                if (!level.isClientSide()) {
                    BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(level, blockPos, state, player);
                    if (MinecraftForge.EVENT_BUS.post(breakEvent)) return InteractionResult.FAIL;
                    BlockState replantState = getReplantState(state);
                    BlockEvent.EntityPlaceEvent placeEvent = new BlockEvent.EntityPlaceEvent(BlockSnapshot.create(level.dimension(), level, blockPos), level.getBlockState(blockPos.below()), player);
                    if (MinecraftForge.EVENT_BUS.post(placeEvent)) return InteractionResult.FAIL;
                    level.setBlockAndUpdate(blockPos, replantState);
                    dropStacks(state, (ServerLevel) level, blockPos, player, player.getItemInHand(hand));

                    stack.hurtAndBreak(1, player, (entity) -> entity.broadcastBreakEvent(hand));
                } else {
                    player.playSound(SoundEvents.CROP_BREAK, 1.0f, 1.0f);
                }

                player.awardStat(Stats.ITEM_USED.get(this));

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    public static boolean isMature(BlockState state) {
        if (state.getBlock() instanceof CropBlock cropBlock) {
            return cropBlock.isMaxAge(state);
        }

        if (state.getBlock() instanceof NetherWartBlock wartBlock) {
            return state.getValue(NetherWartBlock.AGE) >= NetherWartBlock.MAX_AGE;
        }

        return false;
    }

    public static BlockState getReplantState(BlockState state) {
        if (state.getBlock() instanceof CropBlock cropBlock) {
            return cropBlock.getStateForAge(0);
        }

        if (state.getBlock() instanceof NetherWartBlock wartBlock) {
            return wartBlock.defaultBlockState();
        }

        return state;
    }

    public static void dropStacks(BlockState state, ServerLevel level, BlockPos pos, Entity entity, ItemStack toolStack) {
        Item replant = state.getBlock().getCloneItemStack(level, pos, state).getItem();
        final boolean[] removedReplant = {false};

        Block.getDrops(state, level, pos, null, entity, toolStack).forEach(stack -> {
            if (!removedReplant[0] && stack.getItem() == replant) {
                stack.shrink(1);
                removedReplant[0] = true;
            }

            Block.popResource(level, pos, stack);
        });

        state.spawnAfterBreak(level, pos, toolStack, true);
    }
}
