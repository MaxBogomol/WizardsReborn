package mod.maxbogomol.wizards_reborn.client.event;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.client.gui.screen.CrystalChooseScreen;
import mod.maxbogomol.wizards_reborn.common.item.equipment.ArcaneWandItem;
import mod.maxbogomol.wizards_reborn.common.network.DeleteCrystalPacket;
import mod.maxbogomol.wizards_reborn.common.network.OpenAlchemyBagPacket;
import mod.maxbogomol.wizards_reborn.common.network.OpenCrystalBagPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
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

@OnlyIn(Dist.CLIENT)
public class KeyBindHandler {

    private KeyBindHandler() {}

    @SubscribeEvent
    public static void onKeyPress(InputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        if (WizardsRebornClient.OPEN_WAND_SELECTION_KEY.isDown()) {
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
                Minecraft.getInstance().setScreen(new CrystalChooseScreen(Component.empty()));
            } else if (open && player.isShiftKeyDown()) {
                CompoundTag nbt = stack.getOrCreateTag();
                if (nbt.contains("crystal")) {
                    if (nbt.getBoolean("crystal")) {
                        PacketHandler.sendToServer(new DeleteCrystalPacket(hand));
                        Minecraft.getInstance().player.playNotifySound(WizardsReborn.CRYSTAL_RESONATE_SOUND.get(), SoundSource.NEUTRAL, 1.0f, 1.0f);
                    }
                }
            }
        }

        if (WizardsRebornClient.OPEN_CRYSTAL_BAG_KEY.isDown()) {
            PacketHandler.sendToServer(new OpenCrystalBagPacket());
        }

        if (WizardsRebornClient.OPEN_ALCHEMY_BAG_KEY.isDown()) {
            PacketHandler.sendToServer(new OpenAlchemyBagPacket());
        }
    }
}
