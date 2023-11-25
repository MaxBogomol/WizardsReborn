package mod.maxbogomol.wizards_reborn.mixin.client;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.WizardsRebornClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(Minecraft.class)
public class ClientMusicChoiceMixin {
    private static Random random = new Random();

    @Shadow
    @Nullable
    public Screen screen;

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Shadow
    @Final
    public Gui gui;

    @Inject(method = "getSituationalMusic", at = @At("HEAD"), cancellable = true)
    private void biomemusic$musicChoice(final CallbackInfoReturnable<Music> cir) {
        if (screen instanceof WinScreen) {
            return;
        }

        List<Music> possibleTracks = new ArrayList<>();

        if (this.player != null) {
            Holder<Biome> holder = this.player.level().getBiome(this.player.blockPosition());
            final Music biomeMusic = holder.value().getBackgroundMusic().orElse(null);
            if (holder.is(Tags.Biomes.IS_SWAMP)) {
                if (random.nextFloat() < 0.8) {
                    cir.setReturnValue(WizardsRebornClient.MOR_MUSIC);
                }
            }
        }
    }
}