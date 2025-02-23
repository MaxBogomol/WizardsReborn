package mod.maxbogomol.wizards_reborn.integration.common.malum;

import com.sammy.malum.registry.common.MobEffectRegistry;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotion;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionHandler;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

public class WizardsRebornMalum {
    public static boolean LOADED;

    public static class AlchemyPotionsLoadedOnly {
        public static AlchemyPotion GLUTTONY;

        public static void init() {
            GLUTTONY = new AlchemyPotion(WizardsReborn.MOD_ID+":gluttony", new MobEffectInstance(MobEffectRegistry.GLUTTONY.get(), 1200, 2));

            AlchemyPotionHandler.register(GLUTTONY);
        }
    }

    public static void init(IEventBus eventBus) {
        LOADED = ModList.get().isLoaded("malum");
    }

    public static void setup() {
        if (isLoaded()) {
            AlchemyPotionsLoadedOnly.init();
        }
    }

    public static boolean isLoaded() {
        return LOADED;
    }
}
