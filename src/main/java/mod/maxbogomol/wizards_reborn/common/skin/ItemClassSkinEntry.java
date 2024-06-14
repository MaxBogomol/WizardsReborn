package mod.maxbogomol.wizards_reborn.common.skin;

import mod.maxbogomol.wizards_reborn.api.skin.SkinEntry;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemClassSkinEntry extends SkinEntry {
    public final Class item;
    public String skin;

    public ItemClassSkinEntry(Class item, String skin) {
        this.item = item;
        this.skin = skin;
    }

    @Override
    public boolean canApplyOnItem(ItemStack itemStack) {
        return item.isInstance(itemStack.getItem());
    }

    @OnlyIn(Dist.CLIENT)
    public String getItemModelName(ItemStack stack) {
        return skin;
    }
}
