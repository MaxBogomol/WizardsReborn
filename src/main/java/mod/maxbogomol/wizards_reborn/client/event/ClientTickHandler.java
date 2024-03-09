package mod.maxbogomol.wizards_reborn.client.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;

public class ClientTickHandler {

    private ClientTickHandler() {}

    public static int ticksInGame = 0;
    public static float partialTicks = 0;
    public static int wissenCount = 0;
    public static int wissenCountOld = 0;
    public static int wissenCountMax = 0;
    public static int wissenTick = 0;

    public static void clientTickEnd(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            if (!Minecraft.getInstance().isPaused()) {
                if (wissenCount > wissenCountMax) {
                    wissenCountMax = wissenCount;
                    wissenTick = 10;
                }
                wissenCountOld = wissenCountMax;
                wissenCount = 0;
            }
        }

        if (event.phase == TickEvent.Phase.END) {

            if (!Minecraft.getInstance().isPaused()) {
                ticksInGame++;
                partialTicks = 0;

                if (wissenTick < 0) {
                    wissenCountMax = 0;
                } else {
                    wissenTick--;
                }
            }
        }
    }
}
