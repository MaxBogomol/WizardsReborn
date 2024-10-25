package mod.maxbogomol.wizards_reborn.registry.client;

import mod.maxbogomol.fluffy_fur.client.sound.MusicHandler;
import mod.maxbogomol.fluffy_fur.client.sound.MusicModifier;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.Music;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.Tags;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class WizardsRebornMusics {
    public static final Music REBORN = new Music(WizardsRebornSounds.MUSIC_DISC_REBORN.getHolder().get(), 20, 600, true);
    public static final Music MOR = new Music(WizardsRebornSounds.MUSIC_DISC_MOR.getHolder().get(), 3600, 9600, false);
    public static final Music SHIMMER = new Music(WizardsRebornSounds.MUSIC_DISC_SHIMMER.getHolder().get(), 3600, 9600, false);

    @Mod.EventBusSubscriber(modid = WizardsReborn.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientRegistryEvents {
        @SubscribeEvent
        public static void registerMusicModifiers(FMLClientSetupEvent event) {
            MusicHandler.register(new MusicModifier.Panorama(REBORN, WizardsRebornClient.MAGICAL_ORIGINS_PANORAMA));
            MusicHandler.register(new MusicModifier() {
                public boolean isCanPlay(Music defaultMisic, Minecraft minecraft) {
                    if (isBiome(Tags.Biomes.IS_SWAMP, minecraft)) {
                        return (WizardsRebornClient.random.nextFloat() < 0.8f);
                    }
                    return false;
                }

                public Music play(Music defaultMisic, Minecraft minecraft) {
                    return MOR;
                }
            });
            MusicHandler.register(new MusicModifier() {
                public boolean isCanPlay(Music defaultMisic, Minecraft minecraft) {
                    if (isBiome(Tags.Biomes.IS_CAVE, minecraft)) {
                        if (minecraft.player.getY() >= -40 && minecraft.player.getY() <= 30) {
                            return (WizardsRebornClient.random.nextFloat() < 0.6f);
                        }
                    }
                    return false;
                }

                public Music play(Music defaultMisic, Minecraft minecraft) {
                    return SHIMMER;
                }
            });
        }
    }
}
