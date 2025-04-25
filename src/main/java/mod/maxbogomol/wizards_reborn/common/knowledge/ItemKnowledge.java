package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.api.knowledge.IItemKnowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeType;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemKnowledge extends Knowledge implements IItemKnowledge {
    @Deprecated
    public final Item item;

    public ItemKnowledge(String id, boolean articles, int points, Item item) {
        super(id, articles, points);
        this.item = item;
    }

    @Override
    public KnowledgeType getKnowledgeType() {
        return KnowledgeTypes.ITEM;
    }

    @Override
    public boolean canReceived(Player player, ItemStack itemStack) {
        return itemStack.getItem().equals(item);
    }

    public Item getItem() {
        return item;
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getIcon() {
        return item.getDefaultInstance();
    }
}
