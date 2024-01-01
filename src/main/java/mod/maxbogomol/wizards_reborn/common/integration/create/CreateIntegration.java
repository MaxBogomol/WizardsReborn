package mod.maxbogomol.wizards_reborn.common.integration.create;

import net.minecraftforge.fml.ModList;

public class CreateIntegration {
    public static boolean isCreateLoaded() {
        return ModList.get().isLoaded("create");
    }
}
