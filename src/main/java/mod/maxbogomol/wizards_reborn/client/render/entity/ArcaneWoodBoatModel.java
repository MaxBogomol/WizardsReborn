package mod.maxbogomol.wizards_reborn.client.render.entity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.resources.ResourceLocation;

public class ArcaneWoodBoatModel extends BoatRenderer {
    private static final ResourceLocation BOAT_TEXTURE =
            new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/boat/arcane_wood.png");

    public ArcaneWoodBoatModel(EntityRendererProvider.Context pContext, boolean pChestBoat) {
        super(pContext, pChestBoat);
    }

    @Override
    public ResourceLocation getTextureLocation(Boat entity) {
        return BOAT_TEXTURE;
    }
}

