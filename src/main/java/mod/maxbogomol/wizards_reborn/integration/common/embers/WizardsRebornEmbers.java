package mod.maxbogomol.wizards_reborn.integration.common.embers;

import mod.maxbogomol.fluffy_fur.util.IntegrationUtil;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.page.IntegrationPage;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe.CenserPage;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;

import java.util.ArrayList;
import java.util.List;

public class WizardsRebornEmbers {
    public static boolean LOADED;
    public static String MOD_ID = "embers";
    public static String ID = "embers";
    public static String NAME = "Embers Rekindled";

    public static class ClientLoadedOnly {
        public static IntegrationPage INTEGRATION_PAGE;

        public static void arcanemiconChaptersInit() {
            INTEGRATION_PAGE = new IntegrationPage(false, ID, NAME);

            List<MobEffectInstance> emberGritEffects = new ArrayList<>();
            emberGritEffects.add(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 8000, 0));
            emberGritEffects.add(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 0));
            emberGritEffects.add(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));

            ArcanemiconChapters.SMOKING_PIPE.addPage(INTEGRATION_PAGE);
            ArcanemiconChapters.SMOKING_PIPE.addPage(new CenserPage(emberGritEffects, new ItemStack(IntegrationUtil.getItem("embers", "ember_grit"))));
        }
    }

    public static void init(IEventBus eventBus) {
        LOADED = ModList.get().isLoaded(MOD_ID);
    }

    public static void setup() {

    }

    public static boolean isLoaded() {
        return LOADED;
    }
}
