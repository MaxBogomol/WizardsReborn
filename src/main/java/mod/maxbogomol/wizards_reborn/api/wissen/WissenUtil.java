package mod.maxbogomol.wizards_reborn.api.wissen;

import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WissenUtil {

    public static Random random = new Random();

    public static int getAddWissenRemain(int currentWissen, int wissen, int maxWissen) {
        int wissenRemain = 0;
        if (maxWissen < (currentWissen + wissen)) {
            wissenRemain = (currentWissen + wissen) - maxWissen;
        }
        return wissenRemain;
    }

    public static int getRemoveWissenRemain(int currentWissen, int wissen) {
        int wissenRemain = 0;
        if (0 > (currentWissen - wissen)) {
            wissenRemain = -(currentWissen - wissen);
        }
        return wissenRemain;
    }

    public static boolean canAddWissen(int currentWissen, int wissen, int maxWissen) {
        return (maxWissen >= (currentWissen + wissen));
    }

    public static boolean canRemoveWissen(int currentWissen, int wissen) {
        return (0 <= (currentWissen - wissen));
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean isCanRenderWissenWand() {
        Minecraft minecraft = Minecraft.getInstance();

        Player player = minecraft.player;
        ItemStack main = player.getMainHandItem();
        ItemStack offhand = player.getOffhandItem();
        boolean renderWand = false;

        if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
            renderWand = true;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                renderWand = true;
            }
        }

        return renderWand;
    }

    public static float getWissenCostModifierWithDiscount(Player player) {
        if (player != null) {
            AttributeInstance attr = player.getAttribute(WizardsRebornAttributes.WISSEN_DISCOUNT.get());
            return (float) (attr.getValue() / 100d);
        }
        return 0;
    }

    public static List<ItemStack> getWissenItems(Player player) {
        List<ItemStack> items = new ArrayList<>();
        items.addAll(getWissenItemsInventory(player));
        items.addAll(getWissenItemsCurios(player));

        return items;
    }

    public static List<ItemStack> getWissenItemsActive(Player player) {
        List<ItemStack> items = new ArrayList<>();
        items.addAll(getWissenItemsHotbar(player));
        items.addAll(getWissenItemsCurios(player));

        return items;
    }

    public static List<ItemStack> getWissenItemsInventory(Player player) {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            if (player.getInventory().getItem(i).getItem() instanceof IWissenItem) {
                items.add(player.getInventory().getItem(i));
            }
        }

        return items;
    }


    public static List<ItemStack> getWissenItemsHotbar(Player player) {
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (player.getInventory().getItem(i).getItem() instanceof IWissenItem) {
                items.add(player.getInventory().getItem(i));
            }
        }
        if (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof IWissenItem) {
            items.add(player.getItemInHand(InteractionHand.OFF_HAND));
        }

        return items;
    }

    public static List<ItemStack> getWissenItemsCurios(Player player) {
        List<ItemStack> items = new ArrayList<>();
        LazyOptional<ICuriosItemHandler> curiosItemHandler = CuriosApi.getCuriosInventory(player);
        if (curiosItemHandler.isPresent() && curiosItemHandler.resolve().isPresent()) {
            List<SlotResult> curioSlots = curiosItemHandler.resolve().get().findCurios((i) -> true);
            for (SlotResult slot : curioSlots) {
                if (slot.stack().getItem() instanceof IWissenItem) {
                    items.add(slot.stack());
                }
            }
        }

        return items;
    }

    public static List<ItemStack> getWissenItemsOff(List<ItemStack> items) {
        List<ItemStack> filteredItems = new ArrayList<>();
        for (ItemStack stack : items) {
            if (stack.getItem() instanceof IWissenItem wissenItem) {
                if (wissenItem.getWissenItemType() == WissenItemType.OFF) {
                    filteredItems.add(stack);
                }
            }
        }

        return filteredItems;
    }

    public static List<ItemStack> getWissenItemsNone(List<ItemStack> items) {
        List<ItemStack> filteredItems = new ArrayList<>();
        for (ItemStack stack : items) {
            if (stack.getItem() instanceof IWissenItem wissenItem) {
                if (wissenItem.getWissenItemType() == WissenItemType.NONE) {
                    filteredItems.add(stack);
                }
            }
        }

        return filteredItems;
    }

    public static List<ItemStack> getWissenItemsUsing(List<ItemStack> items) {
        List<ItemStack> filteredItems = new ArrayList<>();
        for (ItemStack stack : items) {
            if (stack.getItem() instanceof IWissenItem wissenItem) {
                if (wissenItem.getWissenItemType() == WissenItemType.USING) {
                    filteredItems.add(stack);
                }
            }
        }

        return filteredItems;
    }

    public static List<ItemStack> getWissenItemsStorage(List<ItemStack> items) {
        List<ItemStack> filteredItems = new ArrayList<>();
        for (ItemStack stack : items) {
            if (stack.getItem() instanceof IWissenItem wissenItem) {
                if (wissenItem.getWissenItemType() == WissenItemType.STORAGE) {
                    filteredItems.add(stack);
                }
            }
        }

        return filteredItems;
    }

    public static List<ItemStack> getWissenItemsNoneAndStorage(List<ItemStack> items) {
        List<ItemStack> filteredItems = new ArrayList<>();
        for (ItemStack stack : items) {
            if (stack.getItem() instanceof IWissenItem wissenItem) {
                if (wissenItem.getWissenItemType() == WissenItemType.NONE || wissenItem.getWissenItemType() == WissenItemType.STORAGE) {
                    filteredItems.add(stack);
                }
            }
        }

        return filteredItems;
    }

    public static int getWissenInItems(List<ItemStack> items) {
        int wissen = 0;
        for (ItemStack stack : items) {
            WissenItemUtil.existWissen(stack);
            wissen = wissen + WissenItemUtil.getWissen(stack);
        }

        return wissen;
    }

    public static int getMaxWissenInItems(List<ItemStack> items) {
        int maxWissen = 0;
        for (ItemStack stack : items) {
            if (stack.getItem() instanceof IWissenItem wissenItem) {
                maxWissen = maxWissen + wissenItem.getMaxWissen(stack);
            }
        }

        return maxWissen;
    }

    public static void removeWissenFromWissenItems(List<ItemStack> items, int wissen) {
        for (ItemStack stack : items) {
            if (wissen <= 0) break;
            WissenItemUtil.existWissen(stack);
            int wissenRemain = WissenItemUtil.getRemoveWissenRemain(stack, wissen);
            if (WissenItemUtil.canRemoveWissen(stack, wissen)) {
                WissenItemUtil.removeWissen(stack, wissen);
                break;
            }
            if (wissenRemain > 0) {
                wissen = wissenRemain;
            }
            WissenItemUtil.setWissen(stack, 0);
        }
    }
}