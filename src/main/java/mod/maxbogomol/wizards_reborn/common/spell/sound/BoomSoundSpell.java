package mod.maxbogomol.wizards_reborn.common.spell.sound;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BoomSoundSpell extends SoundSpell {
    public SoundEvent sound;

    public BoomSoundSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsReborn.VOID_CRYSTAL_TYPE);
    }

    @Override
    public SoundEvent getSound(Level world, Player player, InteractionHand hand) {
        return WizardsReborn.BOOM_SOUND.get();
    }
}
