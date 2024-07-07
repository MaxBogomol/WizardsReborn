package mod.maxbogomol.wizards_reborn.api.arcaneenchantment;

import java.util.ArrayList;
import java.util.List;

public interface IArcaneItem {
    default List<ArcaneEnchantmentType> getArcaneEnchantmentTypes() {
        return new ArrayList<>();
    }
}
