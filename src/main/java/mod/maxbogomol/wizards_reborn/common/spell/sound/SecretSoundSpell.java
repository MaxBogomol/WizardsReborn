package mod.maxbogomol.wizards_reborn.common.spell.sound;

public class SecretSoundSpell extends SoundSpell {
    public SecretSoundSpell(String id, int points) {
        super(id, points);
    }

    @Override
    public boolean isSecret() {
        return true;
    }
}
