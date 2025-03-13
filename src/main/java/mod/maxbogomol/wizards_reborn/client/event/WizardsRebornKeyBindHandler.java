package mod.maxbogomol.wizards_reborn.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.client.gui.screen.ArcaneWandScreen;
import mod.maxbogomol.wizards_reborn.client.gui.screen.BagMenuScreen;
import mod.maxbogomol.wizards_reborn.client.gui.screen.WissenWandScreen;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.IBagItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.item.ArcaneWandRemoveCrystalPacket;
import mod.maxbogomol.wizards_reborn.common.network.item.ArcaneWandSpellSetPacket;
import mod.maxbogomol.wizards_reborn.common.network.item.BagOpenPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.SpellSetSetCurrentPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.SpellSetSetCurrentSpellPacket;
import mod.maxbogomol.wizards_reborn.registry.client.WizardsRebornKeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class WizardsRebornKeyBindHandler {

    @SubscribeEvent
    public static void onInput(InputEvent event) {
        if (WizardsRebornKeyMappings.OPEN_SELECTION_MENU.isDown()) {
            chooseMenus();
        }

        if (WizardsRebornKeyMappings.OPEN_BAG_MENU.isDown()) {
            bagMenu();
        }
    }

    @SubscribeEvent
    public static void onKey(InputEvent.Key event) {
        if (event.getAction() == InputConstants.PRESS) {
            if (!Minecraft.getInstance().isPaused()) {
                spellsToggle(event.getKey());
            }
        }
    }

    @SubscribeEvent
    public static void onMouseKey(InputEvent.MouseButton event) {
        if (event.getAction() == InputConstants.PRESS) {
            if (!Minecraft.getInstance().isPaused()) {
                spellsToggle(event.getButton());
            }
        }
    }

    public static void chooseMenus() {
        boolean arcaneWand = arcaneWandMenu();
        if (arcaneWand) return;
        boolean wissenWand = wissenWandMenu();
        if (wissenWand) return;
    }

    public static boolean arcaneWandMenu() {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        ItemStack main = minecraft.player.getMainHandItem();
        ItemStack offhand = minecraft.player.getOffhandItem();
        boolean open = false;
        boolean hand = true;
        ItemStack stack = minecraft.player.getMainHandItem();

        if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
            open=true;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                open=true;
                hand=false;
                stack = offhand;
            }
        }

        if (open && !player.isShiftKeyDown()) {
            Minecraft.getInstance().setScreen(new ArcaneWandScreen(Component.empty()));
            return true;
        } else if (open && player.isShiftKeyDown()) {
            CompoundTag nbt = stack.getOrCreateTag();
            if (nbt.contains("crystal")) {
                if (nbt.getBoolean("crystal")) {
                    WizardsRebornPacketHandler.sendToServer(new ArcaneWandRemoveCrystalPacket(hand));
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean wissenWandMenu() {
        Minecraft minecraft = Minecraft.getInstance();

        ItemStack main = minecraft.player.getMainHandItem();
        ItemStack offhand = minecraft.player.getOffhandItem();
        boolean open = false;

        if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
            open=true;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                open=true;
            }
        }

        if (open) {
            Minecraft.getInstance().setScreen(new WissenWandScreen(Component.empty()));
            return true;
        }

        return false;
    }

    public static void bagMenu() {
        Minecraft minecraft = Minecraft.getInstance();
        List<ItemStack> items = minecraft.player.inventoryMenu.getItems();
        LazyOptional<ICuriosItemHandler> curiosItemHandler = CuriosApi.getCuriosInventory(minecraft.player);
        if (curiosItemHandler.isPresent() && curiosItemHandler.resolve().isPresent()) {
            List<SlotResult> curioSlots = curiosItemHandler.resolve().get().findCurios((i) -> true);
            for (SlotResult slot : curioSlots) {
                if (slot.stack() != null) {
                    items.add(slot.stack());
                }
            }
        }

        ArrayList<ItemStack> bags = new ArrayList<ItemStack>();

        for (ItemStack stack : items) {
            if (stack.getItem() instanceof IBagItem) {
                bags.add(stack);
            }
        }

        if (!bags.isEmpty()) {
            if (bags.size() == 1) {
                WizardsRebornPacketHandler.sendToServer(new BagOpenPacket(bags.get(0)));
            } else {
                Minecraft.getInstance().setScreen(new BagMenuScreen(Component.empty()));
            }
        }
    }

    public static void spellsToggle(int key) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;

        if (player != null) {
            ItemStack main = minecraft.player.getMainHandItem();
            ItemStack offhand = minecraft.player.getOffhandItem();
            boolean open = false;
            boolean hand = true;
            ItemStack stack = minecraft.player.getMainHandItem();

            if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                open = true;
            } else {
                if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                    open = true;
                    hand = false;
                    stack = offhand;
                }
            }

            if (open) {
                int current = 0;
                int currentSet = 0;
                boolean set = false;
                boolean spellSet = false;

                if (WizardsRebornKeyMappings.SPELL_SETS_TOGGLE.isDown()) {
                    if (key == WizardsRebornKeyMappings.NEXT_SPELL.getKey().getValue()) {
                        current = (KnowledgeUtil.getCurrentSpellSet(player) + 1) % 10;
                        if (current < 0) current = 9;
                        WizardsRebornPacketHandler.sendToServer(new SpellSetSetCurrentPacket(current));
                        WizardsRebornPacketHandler.sendToServer(new SpellSetSetCurrentSpellPacket(0));
                        set = true;
                        currentSet = current;
                        current = 0;
                    }
                    if (key == WizardsRebornKeyMappings.PREVIOUS_SPELL.getKey().getValue()) {
                        current = (KnowledgeUtil.getCurrentSpellSet(player) - 1) % 10;
                        if (current < 0) current = 9;
                        WizardsRebornPacketHandler.sendToServer(new SpellSetSetCurrentPacket(current));
                        WizardsRebornPacketHandler.sendToServer(new SpellSetSetCurrentSpellPacket(0));
                        set = true;
                        currentSet = current;
                        current = 0;
                    }
                } else {
                    if (key == WizardsRebornKeyMappings.NEXT_SPELL.getKey().getValue()) {
                        current = (KnowledgeUtil.getCurrentSpellInSet(player) + 1) % 10;
                        if (current < 0) current = 9;
                        WizardsRebornPacketHandler.sendToServer(new SpellSetSetCurrentSpellPacket(current));
                        set = true;
                        spellSet = true;
                        currentSet = KnowledgeUtil.getCurrentSpellSet(player);
                    }
                    if (key == WizardsRebornKeyMappings.PREVIOUS_SPELL.getKey().getValue()) {
                        current = (KnowledgeUtil.getCurrentSpellInSet(player) - 1) % 10;
                        if (current < 0) current = 9;
                        WizardsRebornPacketHandler.sendToServer(new SpellSetSetCurrentSpellPacket(current));
                        set = true;
                        spellSet = true;
                        currentSet = KnowledgeUtil.getCurrentSpellSet(player);
                    }
                }

                if (set) {
                    if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                        Spell spell = KnowledgeUtil.getSpellFromSet(player, currentSet, current);
                        String string = "";
                        if (spell != null) string = spell.getId();
                        if (spellSet) WizardsRebornPacketHandler.sendToServer(new ArcaneWandSpellSetPacket(true, string));
                    } else {
                        if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                            Spell spell = KnowledgeUtil.getSpellFromSet(player, KnowledgeUtil.getCurrentSpellSet(player), current);
                            String string = "";
                            if (spell != null) string = spell.getId();
                            if (spellSet) WizardsRebornPacketHandler.sendToServer(new ArcaneWandSpellSetPacket(false, string));
                        }
                    }
                }
            }
        }
    }
}
