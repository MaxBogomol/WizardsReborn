package mod.maxbogomol.wizards_reborn.registry.common;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.EchoHandler;
import mod.maxbogomol.wizards_reborn.common.echo.EggEcho;

public class WizardsRebornEchoes {
    public static EggEcho EGG = new EggEcho(WizardsReborn.MOD_ID+":egg");

    public static void register() {
        EchoHandler.register(EGG);
    }
}
