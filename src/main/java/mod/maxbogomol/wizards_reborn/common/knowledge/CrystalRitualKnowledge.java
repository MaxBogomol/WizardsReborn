package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.api.knowledge.IItemKnowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeType;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeTypes;
import mod.maxbogomol.wizards_reborn.common.item.equipment.RunicWisestonePlateItem;
import mod.maxbogomol.wizards_reborn.registry.common.item.WizardsRebornItems;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrystalRitualKnowledge extends Knowledge implements IItemKnowledge {
    public CrystalRitual ritual;

    public CrystalRitualKnowledge(String id, boolean articles, int points, CrystalRitual ritual) {
        super(id, articles, points);
        this.ritual = ritual;
    }

    @Override
    public KnowledgeType getKnowledgeType() {
        return KnowledgeTypes.ITEM;
    }

    @Override
    public boolean canReceived(Player player, ItemStack itemStack) {
        if (itemStack.getItem() instanceof RunicWisestonePlateItem plate) {
            return CrystalRitualUtil.getCrystalRitual(itemStack) == ritual;
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getIcon() {
        ItemStack stack = WizardsRebornItems.RUNIC_WISESTONE_PLATE.get().getDefaultInstance();
        CrystalRitualUtil.setCrystalRitual(stack, ritual);
        return stack;
    }

    @OnlyIn(Dist.CLIENT)
    public Component getName() {
        CrystalRitual ritual = CrystalRitualUtil.getCrystalRitual(getIcon());
        if (!CrystalRitualUtil.isEmpty(ritual)) {
            return RunicWisestonePlateItem.getRitualName(ritual);
        }
        return Component.empty();
    }
}
