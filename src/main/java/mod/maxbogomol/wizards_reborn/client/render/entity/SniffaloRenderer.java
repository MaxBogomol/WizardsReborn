package mod.maxbogomol.wizards_reborn.client.render.entity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.render.entity.layer.SniffaloArmorLayer;
import mod.maxbogomol.wizards_reborn.client.render.entity.layer.SniffaloCarpetLayer;
import mod.maxbogomol.wizards_reborn.client.render.entity.layer.SniffaloSaddleLayer;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import net.minecraft.client.model.SnifferModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class SniffaloRenderer extends MobRenderer<SniffaloEntity, SnifferModel<SniffaloEntity>> {
    public static final ResourceLocation SNIFFALO_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/sniffalo.png");

    public SniffaloRenderer(EntityRendererProvider.Context context) {
        super(context, new SnifferModel<>(context.bakeLayer(ModelLayers.SNIFFER)), 1.1F);
        addLayer(new SniffaloSaddleLayer(this));
        addLayer(new SniffaloCarpetLayer(this));
        addLayer(new SniffaloArmorLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(SniffaloEntity entity) {
        return SNIFFALO_TEXTURE;
    }
}