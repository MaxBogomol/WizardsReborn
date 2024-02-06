package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.animation.ItemAnimation;
import mod.maxbogomol.wizards_reborn.client.animation.SmokingPipeItemAnimation;
import mod.maxbogomol.wizards_reborn.common.item.ICustomAnimationItem;
import mod.maxbogomol.wizards_reborn.common.item.ItemBackedInventory;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.SmokeEffectPacket;
import mod.maxbogomol.wizards_reborn.common.recipe.CenserRecipe;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SmokingPipeItem extends Item implements ICustomAnimationItem {
    public static SmokingPipeItemAnimation animation = new SmokingPipeItemAnimation();

    public SmokingPipeItem(Properties properties) {
        super(properties);
    }

    public static SimpleContainer getInventory(ItemStack stack) {
        return new ItemBackedInventory(stack, 6);
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag oldCapNbt) {
        return new SmokingPipeItem.InvProvider(stack);
    }

    private static class InvProvider implements ICapabilityProvider {
        private final LazyOptional<IItemHandler> opt;

        public InvProvider(ItemStack stack) {
            opt = LazyOptional.of(() -> new InvWrapper(getInventory(stack)));
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
            return ForgeCapabilities.ITEM_HANDLER.orEmpty(capability, opt);
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity entityLiving, int pTimeLeft) {
        if (!world.isClientSide) {
            if (entityLiving instanceof Player player) {
                CompoundTag nbt = stack.getOrCreateTag();
                int invSize = getInventorySize(stack);

                if (getUseDuration(stack) - player.getUseItemRemainingTicks() >= 20 && invSize > 0) {
                    List<MobEffectInstance> effects = new ArrayList<>();
                    List<Item> usedItems = new ArrayList<>();

                    SimpleContainer inv = new SimpleContainer(1);
                    for (int i = 0; i < getInventorySize(stack); i++) {
                        inv.setItem(0, getInventory(stack).getItem(i).copy());
                        Optional<CenserRecipe> recipe = world.getRecipeManager().getRecipeFor(WizardsReborn.CENSER_RECIPE.get(), inv, world);
                        if (recipe.isPresent()) {
                            for (MobEffectInstance effectInstance : recipe.get().getEffects()) {
                                effects.add(new MobEffectInstance(effectInstance.getEffect(), (int) Math.ceil(effectInstance.getDuration() / 4F), effectInstance.getAmplifier()));
                            }
                            if (!usedItems.contains(getInventory(stack).getItem(i).getItem())) {
                                usedItems.add(getInventory(stack).getItem(i).getItem());
                            }
                        }
                    }

                    float R = 1f;
                    float G = 1f;
                    float B = 1f;


                    for (int i = 0; i < getInventorySize(stack); i++) {
                        ItemStack item = getInventory(stack).getItem(i);
                        if (usedItems.contains(item.getItem())) {
                            setItemBurnCenser(item, getItemBurnCenser(item) + 1);
                            getInventory(stack).setItem(i, item);
                            if (getItemBurnCenser(item) >= 5) {
                                world.playSound(null, player.getOnPos(), SoundEvents.BONE_MEAL_USE, SoundSource.BLOCKS, 1.0f, 1.0f);
                            }
                            usedItems.remove(item.getItem());
                        }
                    }

                    sortItems(stack);

                    if (effects.size() > 0) {
                        for (MobEffectInstance effectInstance : effects) {
                            player.addEffect(new MobEffectInstance(effectInstance));
                        }

                        int color = PotionUtils.getColor(effects);
                        R = ColorUtils.getRed(color) / 255F;
                        G = ColorUtils.getGreen(color) / 255F;
                        B = ColorUtils.getBlue(color) / 255F;
                    }

                    Vec3 posSmoke = player.getEyePosition().add(player.getLookAngle().scale(0.75f));
                    Vec3 vel = player.getEyePosition().add(player.getLookAngle().scale(40)).subtract(posSmoke).scale(1.0 / 20).normalize().scale(0.05f);
                    PacketHandler.sendToTracking(world, player.getOnPos(), new SmokeEffectPacket((float) posSmoke.x, (float) posSmoke.y, (float) posSmoke.z, (float) vel.x, (float) vel.y, (float) vel.z, R, G, B));
                    world.playSound(null, player.getOnPos(), WizardsReborn.STEAM_BURST_SOUND.get(), SoundSource.PLAYERS, 0.1f, 2.0f);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            ItemStack offStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            InteractionHand offHand = InteractionHand.MAIN_HAND;
            if (hand == InteractionHand.MAIN_HAND) {
                offStack = player.getItemInHand(InteractionHand.OFF_HAND);
                offHand = InteractionHand.OFF_HAND;
            }

            SimpleContainer inv = new SimpleContainer(1);
            inv.setItem(0, offStack);
            Optional<CenserRecipe> recipe = world.getRecipeManager().getRecipeFor(WizardsReborn.CENSER_RECIPE.get(), inv, world);

            CompoundTag nbt = stack.getOrCreateTag();
            int invSize = getInventorySize(stack);

            if (!player.isShiftKeyDown()) {
                if (recipe.isPresent()) {
                    if (invSize < 6) {
                        int slot = invSize;
                        if (offStack.getCount() > 1) {
                            player.getItemInHand(offHand).setCount(offStack.getCount() - 1);
                            getInventory(stack).setItem(slot, offStack);
                            ItemStack itemStack = getInventory(stack).getItem(slot);
                            itemStack.setCount(1);
                            getInventory(stack).setItem(slot, itemStack);
                            return InteractionResultHolder.success(stack);
                        } else {
                            getInventory(stack).setItem(slot, offStack);
                            player.getInventory().removeItem(player.getItemInHand(offHand));
                            return InteractionResultHolder.success(stack);
                        }
                    }
                }
            } else {
                if (invSize > 0) {
                    for (int i = 0; i < invSize; i++) {
                        int slot = invSize - i - 1;
                        if (!getInventory(stack).getItem(slot).isEmpty()) {
                            if (getItemBurnCenser(getInventory(stack).getItem(slot)) <= 0) {
                                if (player.getInventory().getSlotWithRemainingSpace(getInventory(stack).getItem(slot)) > 0) {
                                    player.getInventory().add(getInventory(stack).getItem(slot).copy());
                                } else {
                                    world.addFreshEntity(new ItemEntity(world, player.getX(), player.getY() + 0.5F, player.getZ(), getInventory(stack).getItem(slot).copy()));
                                }
                                getInventory(stack).removeItem(slot, 1);
                                sortItems(stack);
                                return InteractionResultHolder.success(stack);
                            }
                        }
                    }
                }
            }
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    public void sortItems(ItemStack stack) {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (!getInventory(stack).getItem(i).isEmpty() && getItemBurnCenser(getInventory(stack).getItem(i)) < 5) {
                stacks.add(getInventory(stack).getItem(i).copy());
            }
        }
        for (int i = 0; i < 8; i++) {
            getInventory(stack).removeItem(i, 1);
        }

        for (int i = 0; i < stacks.size(); i++) {
            getInventory(stack).setItem(i, stacks.get(i));
        }
    }

    public int getInventorySize(ItemStack stack) {
        int size = 0;

        for (int i = 0; i < getInventory(stack).getContainerSize(); i++) {
            if (!getInventory(stack).getItem(i).isEmpty()) {
                size++;
            } else {
                break;
            }
        }

        return size;
    }

    public static void setItemBurnCenser(ItemStack itemStack, int burn) {
        CompoundTag nbt = itemStack.getOrCreateTag();
        nbt.putInt("burnCenser", burn);
    }

    public static int getItemBurnCenser(ItemStack itemStack) {
        CompoundTag nbt = itemStack.getTag();
        if (nbt != null) {
            if (nbt.contains("burnCenser")) {
                return nbt.getInt("burnCenser");
            }
        }

        return 0;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        CompoundTag nbt = stack.getOrCreateTag();
        int invSize = getInventorySize(stack);

        if (invSize > 0) list.add(Component.empty());

        for (int i = 0; i < invSize; i++) {
            int burn = getItemBurnCenser(getInventory(stack).getItem(i));
            int R = (int) Mth.lerp(((float) burn / 5), 255, 0);
            int G = (int) Mth.lerp(((float) burn / 5), 255, 0);
            int B = (int) Mth.lerp(((float) burn / 5), 255, 0);
            list.add(Component.translatable(getInventory(stack).getItem(i).getDescriptionId()).withStyle(Style.EMPTY.withColor(ColorUtils.packColor(255, R, G, B))));
        }

        if (invSize > 0) list.add(Component.empty());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ItemAnimation getAnimation(ItemStack stack) {
        return animation;
    }
}