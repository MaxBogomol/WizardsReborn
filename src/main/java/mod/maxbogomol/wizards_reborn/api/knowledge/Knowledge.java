package mod.maxbogomol.wizards_reborn.api.knowledge;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Knowledge {
    public String id;

    public Knowledge(String id) {
        this.id = id;
    }

    public boolean canReceived() {
        return false;
    }

    public String getId() {
        return id;
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getIcon() {
        return ItemStack.EMPTY;
    }
}
