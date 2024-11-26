package mod.maxbogomol.wizards_reborn.integration.common.farmersdelight;

import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.entity.InnocentSparkEntity;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class KnifeInnocentSparkType extends InnocentSparkEntity.SwordSparkType {

    @Override
    public boolean isItem(InnocentSparkEntity entity) {
        return entity.getItem().getItem() == FarmersDelightIntegration.INNOCENT_WOOD_KNIFE.get();
    }

    @Override
    public TextureAtlasSprite getSprite(InnocentSparkEntity entity) {
        return RenderUtil.getSprite(WizardsReborn.MOD_ID, "item/innocent_wood_knife");
    }

    @Override
    public float getDamage(InnocentSparkEntity entity) {
        return 3;
    }
}
