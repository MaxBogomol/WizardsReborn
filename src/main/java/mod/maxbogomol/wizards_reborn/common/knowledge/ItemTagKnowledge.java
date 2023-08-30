package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ItemTagKnowledge extends Knowledge {
    @Deprecated
    public final TagKey<Item> tag;

    public ItemTagKnowledge(String id, TagKey<Item> tag) {
        super(id);
        this.tag = tag;
    }

    public boolean canReceived(List<ItemStack> items) {
        for (ItemStack stack : items) {
            if (stack.is(tag)) {
                return true;
            }
        }
        return false;
    }

    public TagKey<Item> getTag() {
        return tag;
    }
}
