package mod.maxbogomol.wizards_reborn.common.item.equipment;

import mod.maxbogomol.fluffy_fur.common.item.ICustomBlockEntityDataItem;
import mod.maxbogomol.fluffy_fur.common.item.ItemBackedInventory;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
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
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class CargoCarpetItem extends BlockItem implements ICustomBlockEntityDataItem  {

    public static CarpetColor WHITE = CarpetColor.create(WizardsReborn.MOD_ID, "white_carpet");
    public static CarpetColor LIGHT_GRAY = CarpetColor.create(WizardsReborn.MOD_ID, "light_gray_carpet");
    public static CarpetColor GRAY = CarpetColor.create(WizardsReborn.MOD_ID, "gray_carpet");
    public static CarpetColor BLACK = CarpetColor.create(WizardsReborn.MOD_ID, "black_carpet");
    public static CarpetColor BROWN = CarpetColor.create(WizardsReborn.MOD_ID, "brown_carpet");
    public static CarpetColor RED = CarpetColor.create(WizardsReborn.MOD_ID, "red_carpet");
    public static CarpetColor ORANGE = CarpetColor.create(WizardsReborn.MOD_ID, "orange_carpet");
    public static CarpetColor YELLOW = CarpetColor.create(WizardsReborn.MOD_ID, "yellow_carpet");
    public static CarpetColor LIME = CarpetColor.create(WizardsReborn.MOD_ID, "lime_carpet");
    public static CarpetColor GREEN = CarpetColor.create(WizardsReborn.MOD_ID, "green_carpet");
    public static CarpetColor CYAN = CarpetColor.create(WizardsReborn.MOD_ID, "cyan_carpet");
    public static CarpetColor LIGHT_BLUE = CarpetColor.create(WizardsReborn.MOD_ID, "light_blue_carpet");
    public static CarpetColor BLUE = CarpetColor.create(WizardsReborn.MOD_ID, "blue_carpet");
    public static CarpetColor PURPLE = CarpetColor.create(WizardsReborn.MOD_ID, "purple_carpet");
    public static CarpetColor MAGENTA = CarpetColor.create(WizardsReborn.MOD_ID, "magenta_carpet");
    public static CarpetColor PINK = CarpetColor.create(WizardsReborn.MOD_ID, "pink_carpet");
    public static CarpetColor RAINBOW = CarpetColor.create(WizardsReborn.MOD_ID, "rainbow_carpet");

    public CarpetColor color;

    public CargoCarpetItem(Block block, CarpetColor color, Properties properties) {
        super(block, properties);
        this.color = color;
    }

    public static SimpleContainer getInventory(ItemStack stack) {
        return new ItemBackedInventory(stack, 20);
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag oldCapNbt) {
        return new CargoCarpetItem.InvProvider(stack);
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
    public CompoundTag getCustomBlockEntityData(ItemStack stack, CompoundTag nbt) {
        if (!nbt.contains("Items")) {
            ItemStack newStack = stack.copy();
            NonNullList<ItemStack> ret = NonNullList.withSize(1, ItemStack.EMPTY);
            ret.set(0, newStack);
            ContainerHelper.saveAllItems(nbt, ret);
        }

        return nbt;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (target instanceof SniffaloEntity sniffalo && target.isAlive()) {
            if (sniffalo.isCarpeted() && !sniffalo.isBannered()) {
                sniffalo.setCarpet(stack.copy());
                target.level().gameEvent(target, GameEvent.EQUIP, target.position());
                stack.shrink(1);

                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
        }

        return InteractionResult.PASS;
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
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(20, ItemStack.EMPTY);
        SimpleContainer container = CargoCarpetItem.getInventory(stack);
        for (int i = 0; i < 20; i++) {
            nonnulllist.set(i, container.getItem(i));
        }
        int i = 0;
        int j = 0;

        for (ItemStack itemstack : nonnulllist) {
            if (!itemstack.isEmpty()) {
                ++j;
                if (i <= 4) {
                    ++i;
                    MutableComponent mutableComponent = itemstack.getHoverName().copy();
                    mutableComponent.append(" x").append(String.valueOf(itemstack.getCount()));
                    list.add(mutableComponent);
                }
            }
        }

        if (j - i > 0) {
            list.add(Component.translatable("container.shulkerBox.more", j - i).withStyle(ChatFormatting.ITALIC));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getSniffaloCarpetTexture(ItemStack stack, SniffaloEntity entity) {
        return color.getTexture();
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getCarpetTexture(ItemStack stack) {
        return color.getTexture();
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getCarpetOpenTexture(ItemStack stack) {
        return color.getOpenTexture();
    }

    public static class CarpetColor {
        public ResourceLocation texture;
        public ResourceLocation textureOpen;
        
        public CarpetColor(ResourceLocation texture, ResourceLocation textureOpen) {
            this.texture = texture;
            this.textureOpen = textureOpen;
        }

        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getTexture() {
            return texture;
        }

        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getOpenTexture() {
            return textureOpen;
        }

        public static CarpetColor create(String modId, String carpet) {
            return new CarpetColor(new ResourceLocation(modId, "textures/models/cargo_carpet/" + carpet + ".png"), new ResourceLocation(modId, "textures/models/cargo_carpet/" + carpet + "_open.png"));
        }
    }
}
