package mod.maxbogomol.wizards_reborn.api.knowledge;

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

    public boolean canReceived() {
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

    @OnlyIn(Dist.CLIENT)
    public ItemStack getIcon() {
        return ItemStack.EMPTY;
    }
}
