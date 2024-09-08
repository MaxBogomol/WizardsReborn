package mod.maxbogomol.wizards_reborn.common.spell.sound;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BoomSoundSpell extends SecretSoundSpell {
    public SoundEvent sound;

    public BoomSoundSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public SoundEvent getSound(Level level, Player player, InteractionHand hand) {
        return WizardsRebornSounds.BOOM.get();
    }
}
