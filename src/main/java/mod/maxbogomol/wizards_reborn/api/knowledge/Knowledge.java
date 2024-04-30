package mod.maxbogomol.wizards_reborn.api.knowledge;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Knowledge {
    public String id;
    public int points;
    public boolean articles;
    public Knowledge previous;

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

    public boolean hasScrollAward() {
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

    @OnlyIn(Dist.CLIENT)
    public Component getName() {
        if (I18n.exists(getTranslatedName())) {
            return Component.translatable(getTranslatedName());
        }
        if (!getIcon().isEmpty()) {
            return getIcon().getHoverName();
        }
        return Component.empty();
    }

    public boolean hasPrevious() {
        return previous != null;
    }

    public void addPrevious(Knowledge previous) {
        this.previous = previous;
    }

    public Knowledge getPrevious() {
        return previous;
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String spellId = id.substring(i + 1);
        return "knowledge."  + modId + "." + spellId;
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }
}
