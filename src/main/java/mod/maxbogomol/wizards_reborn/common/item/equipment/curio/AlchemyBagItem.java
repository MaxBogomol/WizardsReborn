package mod.maxbogomol.wizards_reborn.common.item.equipment.curio;

import mod.maxbogomol.fluffy_fur.common.item.ItemBackedInventory;
import mod.maxbogomol.fluffy_fur.integration.common.curios.BaseCurioItem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.gui.container.AlchemyBagContainer;
import mod.maxbogomol.wizards_reborn.common.item.equipment.IBagItem;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.NetworkHooks;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.Iterator;
import java.util.stream.Stream;

public class AlchemyBagItem extends BaseCurioItem implements IBagItem {
    
    public static Color color = new Color(108, 170, 231);

    private static final ResourceLocation BELT_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID,"textures/entity/curio/alchemy_bag.png");

    public AlchemyBagItem(Properties properties) {
        super(properties);
    }

    public static SimpleContainer getInventory(ItemStack stack) {
        return new ItemBackedInventory(stack, 15);
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag oldCapNbt) {
        return new AlchemyBagItem.InvProvider(stack);
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
    public boolean canEquipFromUse(SlotContext slot, ItemStack stack) {
        return !slot.entity().isShiftKeyDown();
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 1.0f);
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity) {
        return BELT_TEXTURE;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.awardStat(Stats.ITEM_USED.get(this));
        openBag(level, player, stack);

        return InteractionResultHolder.success(stack);
    }

    public MenuProvider createContainerProvider(Level level, ItemStack stack) {
        return new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return stack.getHoverName();
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                return new AlchemyBagContainer(i, level, stack, playerInventory, playerEntity);
            }
        };
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        Iterator<ItemStack> iter = new Iterator<>() {
            private int i = 0;
            private final SimpleContainer inventory = getInventory(itemEntity.getItem());

            @Override
            public boolean hasNext() {
                return i < inventory.getContainerSize();
            }

            @Override
            public ItemStack next() {
                return inventory.getItem(i++);
            }
        };

        ItemUtils.onContainerDestroyed(itemEntity, Stream.iterate(iter.next(), t -> iter.hasNext(), t -> iter.next()));
    }

    @Override
    public Color getColor(ItemStack stack) {
        return color;
    }

    @Override
    public void openBag(Level level, Player player, ItemStack stack) {
        if (!level.isClientSide) {
            MenuProvider containerProvider = createContainerProvider(level, stack);
            NetworkHooks.openScreen(((ServerPlayer) player), containerProvider, b -> b.writeItem(stack));
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_LEATHER, SoundSource.PLAYERS, 1f, 1f);
        }
    }
}
