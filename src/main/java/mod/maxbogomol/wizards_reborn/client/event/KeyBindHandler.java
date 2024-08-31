package mod.maxbogomol.wizards_reborn.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.client.gui.screen.ArcaneWandScreen;
import mod.maxbogomol.wizards_reborn.client.gui.screen.BagMenuScreen;
import mod.maxbogomol.wizards_reborn.client.gui.screen.WissenWandChooseScreen;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.IBagItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.network.*;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class KeyBindHandler {

    private KeyBindHandler() {}

    @SubscribeEvent
    public static void onInput(InputEvent event) {
        if (WizardsRebornClient.OPEN_SELECTION_MENU_KEY.isDown()) {
            chooseMenus();
        }

        if (WizardsRebornClient.OPEN_BAG_MENU_KEY.isDown()) {
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
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        ItemStack main = mc.player.getMainHandItem();
        ItemStack offhand = mc.player.getOffhandItem();
        boolean open = false;
        boolean hand = true;
        ItemStack stack = mc.player.getMainHandItem();

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
                    PacketHandler.sendToServer(new DeleteCrystalPacket(hand));
                    Minecraft.getInstance().player.playNotifySound(WizardsReborn.CRYSTAL_RESONATE_SOUND.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean wissenWandMenu() {
        Minecraft mc = Minecraft.getInstance();

        ItemStack main = mc.player.getMainHandItem();
        ItemStack offhand = mc.player.getOffhandItem();
        boolean open = false;

        if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
            open=true;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                open=true;
            }
        }

        if (open) {
            Minecraft.getInstance().setScreen(new WissenWandChooseScreen(Component.empty()));
            return true;
        }

        return false;
    }

    public static void bagMenu() {
        Minecraft mc = Minecraft.getInstance();
        List<ItemStack> items = mc.player.inventoryMenu.getItems();
        List<SlotResult> curioSlots = CuriosApi.getCuriosInventory(mc.player).resolve().get().findCurios();
        for (SlotResult slot : curioSlots) {
            if (slot.stack() != null) {
                items.add(slot.stack());
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
                PacketHandler.sendToServer(new OpenBagPacket(bags.get(0)));
            } else {
                Minecraft.getInstance().setScreen(new BagMenuScreen(Component.empty()));
            }
        }
    }

    public static void spellsToggle(int key) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (player != null) {
            ItemStack main = mc.player.getMainHandItem();
            ItemStack offhand = mc.player.getOffhandItem();
            boolean open = false;
            boolean hand = true;
            ItemStack stack = mc.player.getMainHandItem();

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

                if (WizardsRebornClient.SPELL_SETS_TOGGLE_KEY.isDown()) {
                    if (key == WizardsRebornClient.NEXT_SPELL_KEY.getKey().getValue()) {
                        current = (KnowledgeUtil.getCurrentSpellSet(player) + 1) % 10;
                        if (current < 0) current = 9;
                        PacketHandler.sendToServer(new SetCurrentSpellSetPacket(current));
                        PacketHandler.sendToServer(new SetCurrentSpellInSetPacket(0));
                        set = true;
                        currentSet = current;
                        current = 0;
                    }
                    if (key == WizardsRebornClient.PREVIOUS_SPELL_KEY.getKey().getValue()) {
                        current = (KnowledgeUtil.getCurrentSpellSet(player) - 1) % 10;
                        if (current < 0) current = 9;
                        PacketHandler.sendToServer(new SetCurrentSpellSetPacket(current));
                        PacketHandler.sendToServer(new SetCurrentSpellInSetPacket(0));
                        set = true;
                        currentSet = current;
                        current = 0;
                    }
                } else {
                    if (key == WizardsRebornClient.NEXT_SPELL_KEY.getKey().getValue()) {
                        current = (KnowledgeUtil.getCurrentSpellInSet(player) + 1) % 10;
                        if (current < 0) current = 9;
                        PacketHandler.sendToServer(new SetCurrentSpellInSetPacket(current));
                        set = true;
                        spellSet = true;
                        currentSet = KnowledgeUtil.getCurrentSpellSet(player);
                    }
                    if (key == WizardsRebornClient.PREVIOUS_SPELL_KEY.getKey().getValue()) {
                        current = (KnowledgeUtil.getCurrentSpellInSet(player) - 1) % 10;
                        if (current < 0) current = 9;
                        PacketHandler.sendToServer(new SetCurrentSpellInSetPacket(current));
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
                        if (spellSet) PacketHandler.sendToServer(new SetSpellPacket(true, string));
                        Minecraft.getInstance().player.playNotifySound(WizardsReborn.CRYSTAL_RESONATE_SOUND.get(), SoundSource.NEUTRAL, 1.0f, 1.5f);
                    } else {
                        if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                            Spell spell = KnowledgeUtil.getSpellFromSet(player, KnowledgeUtil.getCurrentSpellSet(player), current);
                            String string = "";
                            if (spell != null) string = spell.getId();
                            if (spellSet) PacketHandler.sendToServer(new SetSpellPacket(false, string));
                            Minecraft.getInstance().player.playNotifySound(WizardsReborn.CRYSTAL_RESONATE_SOUND.get(), SoundSource.NEUTRAL, 1.0f, 1.5f);
                        }
                    }
                }
            }
        }
    }
}
