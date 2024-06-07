package mod.maxbogomol.wizards_reborn.common.knowledge;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MusicDiscKnowledge extends ItemKnowledge {

    public MusicDiscKnowledge(String id, boolean articles, int points, Item item) {
        super(id, articles, points, item);
    }

    @OnlyIn(Dist.CLIENT)
    public Component getName() {
        if (getItem() instanceof RecordItem disc) {
            return getIcon().getHoverName().copy()
                    .append(" (")
                    .append(disc.getDisplayName())
                    .append(")");
        }
        return Component.empty();
    }
}
