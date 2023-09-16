package mod.maxbogomol.wizards_reborn.client.event;

import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.gui.screen.CrystalChooseScreen;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.network.DeleteCrystalPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class KeyBindHandler {

    private KeyBindHandler() {}

    @SubscribeEvent
    public static void onKeyPress(InputEvent event) {
        if (WizardsRebornClient.OPEN_WAND_SELECTION_KEY.isDown()) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            ItemStack main = mc.player.getMainHandItem();
            ItemStack offhand = mc.player.getOffhandItem();
            boolean open = false;
            boolean hand = true;

            if (!main.isEmpty() && main.getItem() instanceof ArcaneWandItem) {
                open=true;
            } else {
                if (!offhand.isEmpty() && offhand.getItem() instanceof ArcaneWandItem) {
                    open=true;
                    hand=false;
                }
            }

            if (open && !player.isShiftKeyDown()) {
                Minecraft.getInstance().setScreen(new CrystalChooseScreen(Component.empty()));
            } else if (open && player.isShiftKeyDown()) {
                PacketHandler.sendToServer(new DeleteCrystalPacket(hand));
            }
        }
    }
}
