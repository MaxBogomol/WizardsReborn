package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.api.knowledge.IItemKnowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeType;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeTypes;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemTagKnowledge extends Knowledge implements IItemKnowledge {
    @Deprecated
    public final TagKey<Item> tag;

    public ItemStack item;

    public ItemTagKnowledge(String id, boolean articles, int points, TagKey<Item> tag, ItemStack item) {
        super(id, articles, points);
        this.tag = tag;
        this.item = item;
    }

    @Override
    public KnowledgeType getKnowledgeType() {
        return KnowledgeTypes.ITEM;
    }

    @Override
    public boolean canReceived(Player player, ItemStack itemStack) {
        return itemStack.is(tag);
    }

    public TagKey<Item> getTag() {
        return tag;
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getIcon() {
        return item;
    }
}
