package mod.maxbogomol.wizards_reborn.api.knowledge;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Knowledge {
    public String id;
    public int points;
    public boolean articles;

    public Knowledge(String id, boolean articles, int points) {
        this.id = id;
        this.points = points;
        this.articles = articles;
    }

    public boolean canReceived(Player player) {
        return false;
    }

    public String getId() {
        return id;
    }

    public int getPoints() {
        return points;
    }

    public boolean getArticles() {
        return articles;
    }

    public boolean hasToast() {
        return true;
    }

    public boolean hasAllAward() {
        return true;
    }

    public void award(Player player) {

    }

    public boolean addTick(Player player) {
        if (!KnowledgeUtils.isKnowledge(player, this) && canReceived(player)) {
            KnowledgeUtils.addKnowledge(player, this);
            return true;
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getIcon() {
        return ItemStack.EMPTY;
    }
}
