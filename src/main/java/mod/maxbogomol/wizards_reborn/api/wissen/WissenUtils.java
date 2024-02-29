package mod.maxbogomol.wizards_reborn.api.wissen;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WissenUtils {

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
        Minecraft mc = Minecraft.getInstance();

        Player player = mc.player;
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

    public static void connectBlockEffect(Level level, BlockPos pos, Color color, float particlePerBlock) {
        connectBlockEffect(level, new Vec3(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F), color, particlePerBlock);
    }

    public static void connectEffect(Level level, BlockPos posFrom, BlockPos posTo, Color color, int particlePerBlock) {
        connectEffect(level, new Vec3(posFrom.getX() + 0.5F, posFrom.getY() + 0.5F, posFrom.getZ() + 0.5F), new Vec3(posTo.getX() + 0.5F, posTo.getY() + 0.5F, posTo.getZ() + 0.5F), color, particlePerBlock);
    }

    public static void connectBlockEffect(Level level, Vec3 pos, Color color, float particlePerBlock) {
        float colorR = (color.getRed() / 255f);
        float colorG = (color.getGreen() / 255f);
        float colorB = (color.getBlue() / 255f);

        for (int i = 0; i < particlePerBlock; i++) {
            if (random.nextFloat() < 0.25f) {
                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 100), ((random.nextDouble() - 0.5D) / 100), ((random.nextDouble() - 0.5D) / 100))
                        .setAlpha(0.45f, 0f).setScale(0.1f, 0f)
                        .setColor(colorR, colorG, colorB)
                        .setLifetime(5)
                        .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                        .spawn(level, pos.x() + (random.nextDouble() - 0.5D), pos.y() + (random.nextDouble() - 0.5D), pos.z() + (random.nextDouble() - 0.5D));
            }
        }
    }

    public static void connectEffect(Level level, Vec3 posFrom, Vec3 posTo, Color color, float particlePerBlock) {
        double distance = Math.sqrt((posTo.x() - posFrom.x()) * (posTo.x() - posFrom.x()) + (posTo.y() - posFrom.y()) * (posTo.y() - posFrom.y()) + (posTo.z() - posFrom.z()) * (posTo.z() - posFrom.z()));
        int blocks = (int) Math.round(distance);

        double dX = posFrom.x() - posTo.x();
        double dY = posFrom.y() - posTo.y();
        double dZ = posFrom.z() - posTo.z();

        float x = (float) (dX / (blocks * particlePerBlock));
        float y = (float) (dY / (blocks * particlePerBlock));
        float z = (float) (dZ / (blocks * particlePerBlock));

        float colorR = (color.getRed() / 255f);
        float colorG = (color.getGreen() / 255f);
        float colorB = (color.getBlue() / 255f);

        for (int i = 0; i <= blocks * particlePerBlock; i++) {
            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                    .addVelocity(((random.nextDouble() - 0.5D) / 100), ((random.nextDouble() - 0.5D) / 100), ((random.nextDouble() - 0.5D) / 100))
                    .setAlpha(0.45f, 0f).setScale(0.05f, 0f)
                    .setColor(colorR, colorG, colorB)
                    .setLifetime(1)
                    .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(level, posFrom.x() - (x * i), posFrom.y() - (y * i), posFrom.z() - (z * i));
        }
    }

    public static void connectBlockEffect(Level level, BlockPos pos, Color color) {
        connectBlockEffect(level, pos, color, 4);
    }

    public static void connectEffect(Level level, BlockPos posFrom, BlockPos posTo, Color color) {
        connectEffect(level, posFrom, posTo, color, 4);
    }

    public static void connectBlockEffect(Level level, Vec3 pos, Color color) {
        connectBlockEffect(level, pos, color, 4);
    }

    public static void connectEffect(Level level, Vec3 posFrom, Vec3 posTo, Color color) {
        connectEffect(level, posFrom, posTo, color, 4);
    }

    public static void connectBoxEffect(Level level, Vec3 posFrom, Vec3 posTo, Color color, float particlePerBlock) {
        WissenUtils.connectEffect(level, new Vec3(posFrom.x(), posFrom.y(), posFrom.z()), new Vec3(posTo.x() , posFrom.y(), posFrom.z()), color, particlePerBlock);
        WissenUtils.connectEffect(level, new Vec3(posTo.x(), posFrom.y(), posFrom.z()), new Vec3(posTo.x(), posFrom.y(), posTo.z()), color, particlePerBlock);
        WissenUtils.connectEffect(level, new Vec3(posTo.x(), posFrom.y(), posTo.z()), new Vec3(posFrom.x(), posFrom.y(), posTo.z()), color, particlePerBlock);
        WissenUtils.connectEffect(level, new Vec3(posFrom.x(), posFrom.y(), posTo.z()), new Vec3(posFrom.x(), posFrom.y(), posFrom.z()), color, particlePerBlock);

        WissenUtils.connectEffect(level, new Vec3(posFrom.x(), posFrom.y(), posFrom.z()), new Vec3(posFrom.x(), posTo.y(), posFrom.z()), color, particlePerBlock);
        WissenUtils.connectEffect(level, new Vec3(posTo.x(), posFrom.y(), posFrom.z()), new Vec3(posTo.x(), posTo.y(), posFrom.z()), color, particlePerBlock);
        WissenUtils.connectEffect(level, new Vec3(posTo.x(), posFrom.y(), posTo.z()), new Vec3(posTo.x(), posTo.y(), posTo.z()), color, particlePerBlock);
        WissenUtils.connectEffect(level, new Vec3(posFrom.x(), posFrom.y(), posTo.z()), new Vec3(posFrom.x(), posTo.y(), posTo.z()), color, particlePerBlock);

        WissenUtils.connectEffect(level, new Vec3(posFrom.x(), posTo.y(), posFrom.z()), new Vec3(posTo.x(), posTo.y(), posFrom.z()), color, particlePerBlock);
        WissenUtils.connectEffect(level, new Vec3(posTo.x(), posTo.y(), posFrom.z()), new Vec3(posTo.x() , posTo.y(), posTo.z()), color, particlePerBlock);
        WissenUtils.connectEffect(level, new Vec3(posTo.x(), posTo.y(), posTo.z()), new Vec3(posFrom.x(), posTo.y(), posTo.z()), color, particlePerBlock);
        WissenUtils.connectEffect(level, new Vec3(posFrom.x(), posTo.y(), posTo.z()), new Vec3(posFrom.x(), posTo.y(), posFrom.z()), color, particlePerBlock);
    }

    public static void connectBoxEffect(Level level, Vec3 posFrom, Vec3 posTo, Color color) {
        connectBoxEffect(level, posFrom, posTo, color, 1);
    }

    public static float getWissenCostModifierWithDiscount(Player player) {
        AttributeInstance attr = player.getAttribute(WizardsReborn.WISSEN_DISCOUNT.get());
        return (float) (attr.getValue() / 100d);
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
        List<SlotResult> curioSlots = CuriosApi.getCuriosHelper().findCurios(player, (i) -> {return true;});
        for (SlotResult slot : curioSlots) {
            if (slot.stack().getItem() instanceof IWissenItem) {
                items.add(slot.stack());
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
            WissenItemUtils.existWissen(stack);
            wissen = wissen + WissenItemUtils.getWissen(stack);
        }

        return wissen;
    }

    public static int getMaxWissenInItems(List<ItemStack> items) {
        int maxWissen = 0;
        for (ItemStack stack : items) {
            if (stack.getItem() instanceof IWissenItem wissenItem) {
                maxWissen = maxWissen + wissenItem.getMaxWissen();
            }
        }

        return maxWissen;
    }

    public static void removeWissenFromWissenItems(List<ItemStack> items, int wissen) {
        for (ItemStack stack : items) {
            WissenItemUtils.existWissen(stack);
            int wissenRemain = WissenItemUtils.getRemoveWissenRemain(stack, wissen);
            if (WissenItemUtils.canRemoveWissen(stack, wissen)) {
                WissenItemUtils.removeWissen(stack, wissen);
            }
            if (wissenRemain > 0) {
                wissen = wissenRemain;
                WissenItemUtils.setWissen(stack, 0);
            }
        }
    }
}