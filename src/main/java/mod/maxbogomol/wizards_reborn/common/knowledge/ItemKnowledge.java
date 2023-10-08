package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class ItemKnowledge extends Knowledge {
    @Deprecated
    public final Item item;

    public ItemKnowledge(String id, Item item) {
        super(id);
        this.item = item;
    }

    public boolean canReceived(List<ItemStack> items) {
        for (ItemStack stack : items) {
            if (stack.getItem().equals(item)) {
                return true;
            }
        }
        return false;
    }

    public Item getItem() {
        return item;
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getIcon() {
        return item.getDefaultInstance();
    }
}
