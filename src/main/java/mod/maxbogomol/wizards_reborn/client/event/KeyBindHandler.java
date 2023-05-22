package mod.maxbogomol.wizards_reborn.client.event;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.render.gui.CrystalChooseScreen;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.network.DeleteCrystalPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
@OnlyIn(Dist.CLIENT)
public class KeyBindHandler {

    private KeyBindHandler() {}

    @SubscribeEvent
    public static void onKeyPress (InputEvent.KeyInputEvent event) {
        if (WizardsReborn.OPEN_WAND_SELECTION_KEY.isKeyDown()) {
            Minecraft mc = Minecraft.getInstance();
            PlayerEntity player = mc.player;
            ItemStack main = mc.player.getHeldItemMainhand();
            ItemStack offhand = mc.player.getHeldItemOffhand();
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

            if (open && !player.isSneaking()) {
                Minecraft.getInstance().displayGuiScreen(new CrystalChooseScreen(new StringTextComponent("")));
            } else if (open && player.isSneaking()) {
                PacketHandler.sendToServer(new DeleteCrystalPacket(hand));
            }
        }
    }
}
