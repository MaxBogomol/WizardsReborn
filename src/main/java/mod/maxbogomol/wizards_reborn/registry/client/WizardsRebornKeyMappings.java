package mod.maxbogomol.wizards_reborn.registry.client;

import com.mojang.blaze3d.platform.InputConstants;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

public class WizardsRebornKeyMappings {
    private static final String CATEGORY = "key.category."+ WizardsReborn.MOD_ID+".general";
    public static final KeyMapping OPEN_SELECTION_MENU = new KeyMapping("key."+WizardsReborn.MOD_ID+".selection_menu", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY);
    public static final KeyMapping OPEN_BAG_MENU = new KeyMapping("key."+WizardsReborn.MOD_ID+".bag_menu", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, CATEGORY);
    public static final KeyMapping NEXT_SPELL = new KeyMapping("key."+WizardsReborn.MOD_ID+".next_spell", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, CATEGORY);
    public static final KeyMapping PREVIOUS_SPELL = new KeyMapping("key."+WizardsReborn.MOD_ID+".previous_spell", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_Z, CATEGORY);
    public static final KeyMapping SPELL_SETS_TOGGLE = new KeyMapping("key."+WizardsReborn.MOD_ID+".spell_sets_toggle", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_LEFT_ALT, CATEGORY);

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(OPEN_SELECTION_MENU);
            event.register(OPEN_BAG_MENU);
            event.register(NEXT_SPELL);
            event.register(PREVIOUS_SPELL);
            event.register(SPELL_SETS_TOGGLE);
        }
    }
}
