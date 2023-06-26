package mod.maxbogomol.wizards_reborn.client.render.entity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.util.ResourceLocation;

public class ArcaneWoodBoatModel extends BoatRenderer {
    private static final ResourceLocation BOAT_TEXTURE =
            new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/boat/arcane_wood.png");

    public ArcaneWoodBoatModel(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public ResourceLocation getEntityTexture(BoatEntity entity) {
        return BOAT_TEXTURE;
    }
}

