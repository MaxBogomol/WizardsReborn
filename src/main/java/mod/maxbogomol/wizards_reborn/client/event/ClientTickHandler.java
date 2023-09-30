package mod.maxbogomol.wizards_reborn.client.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;

public class ClientTickHandler {

    private ClientTickHandler() {}

    public static int ticksInGame = 0;
    public static float partialTicks = 0;

    public static void clientTickEnd(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {

            if (!Minecraft.getInstance().isPaused()) {
                ticksInGame++;
                partialTicks = 0;
            }
        }
    }
}
