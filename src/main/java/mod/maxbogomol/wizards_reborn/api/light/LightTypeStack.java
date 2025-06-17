package mod.maxbogomol.wizards_reborn.api.light;

import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornLightTypes;
import net.minecraft.nbt.CompoundTag;

public class LightTypeStack {
    public LightType type;
    public int tick = 0;
    public int tickConcentrated = 0;
    public boolean concentrated = false;
    public CompoundTag tag = new CompoundTag();

    public LightTypeStack(LightType type) {
        this.type = type;
    }

    public LightType getType() {
        return type;
    }

    public int getTick() {
        return tick;
    }

    public int getTickConcentrated() {
        return tickConcentrated;
    }

    public boolean isConcentrated() {
        return concentrated;
    }

    public CompoundTag getTag() {
        return tag;
    }

    public void setType(LightType type) {
        this.type = type;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public void setTickConcentrated(int tick) {
        this.tickConcentrated = tick;
    }

    public void setConcentrated(boolean concentrated) {
        this.concentrated = concentrated;
    }

    public void setTag(CompoundTag tag) {
        this.tag = tag;
    }

    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("id", getType().getId());
        tag.putInt("tick", getTick());
        tag.putInt("tickConcentrated", getTickConcentrated());
        tag.putBoolean("concentrated", isConcentrated());
        tag.put("tag", getTag());

        return tag;
    }

    public static LightTypeStack fromTag(CompoundTag tag) {
        LightType type = LightTypeHandler.getLightType(tag.getString("id"));
        if (type != null) {
            LightTypeStack stack = new LightTypeStack(type);
            stack.setTick(tag.getInt("tick"));
            stack.setTickConcentrated(tag.getInt("tickConcentrated"));
            stack.setConcentrated(tag.getBoolean("concentrated"));
            stack.setTag(tag.getCompound("tag"));
            return stack;
        }

        return new LightTypeStack(WizardsRebornLightTypes.EARTH);
    }

    public LightTypeStack copy() {
        LightTypeStack stack = new LightTypeStack(getType());
        stack.setTick(getTick());
        stack.setTickConcentrated(getTickConcentrated());
        stack.setConcentrated(isConcentrated());
        return stack;
    }
}
