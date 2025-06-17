package mod.maxbogomol.wizards_reborn.api.knowledge;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornEchoes;
import net.minecraft.nbt.CompoundTag;

public class EchoStack {
    public Echo echo;
    public int tick = 0;
    public CompoundTag tag = new CompoundTag();

    public EchoStack(Echo echo) {
        this.echo = echo;
    }

    public Echo getEcho() {
        return echo;
    }

    public int getTick() {
        return tick;
    }

    public CompoundTag getTag() {
        return tag;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public void setTag(CompoundTag tag) {
        this.tag = tag;
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", getEcho().getId());
        tag.putInt("tick", getTick());
        tag.put("tag", getTag());

        return tag;
    }

    public static EchoStack fromTag(CompoundTag tag) {
        Echo echo = EchoHandler.getEcho(tag.getString("id"));
        if (echo != null) {
            EchoStack stack = new EchoStack(echo);
            stack.setTick(tag.getInt("tick"));
            stack.setTag(tag.getCompound("tag"));
            return stack;
        }
        return new EchoStack(WizardsRebornEchoes.EGG);
    }
}
