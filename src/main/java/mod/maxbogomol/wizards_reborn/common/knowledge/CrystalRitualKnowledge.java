package mod.maxbogomol.wizards_reborn.common.knowledge;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.common.item.equipment.RunicWisestonePlateItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class CrystalRitualKnowledge extends Knowledge {
    public CrystalRitual ritual;

    public CrystalRitualKnowledge(String id, boolean articles, int points, CrystalRitual ritual) {
        super(id, articles, points);
        this.ritual = ritual;
    }

    @Override
    public boolean canReceived(Player player) {
        List<ItemStack> items = player.inventoryMenu.getItems();
        for (ItemStack stack : items) {
            if (stack.getItem() instanceof RunicWisestonePlateItem plate) {
                if (CrystalRitualUtil.getCrystalRitual(stack) == ritual) {
                    return true;
                }
            }
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public ItemStack getIcon() {
        ItemStack stack = WizardsReborn.RUNIC_WISESTONE_PLATE.get().getDefaultInstance();
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
