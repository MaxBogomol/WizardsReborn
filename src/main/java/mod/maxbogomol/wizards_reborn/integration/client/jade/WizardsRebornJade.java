package mod.maxbogomol.wizards_reborn.integration.client.jade;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class WizardsRebornJade implements IWailaPlugin {
    public static final ResourceLocation CRYSTAL_GROWTH = new ResourceLocation(WizardsReborn.MOD_ID,"crystal_growth");
    public static final ResourceLocation NUMERICAL = new ResourceLocation(WizardsReborn.MOD_ID,"numerical");
    public static final ResourceLocation SALT_CAMPFIRE = new ResourceLocation(WizardsReborn.MOD_ID,"salt_campfire");

    @Override
    public void register(IWailaCommonRegistration registration) {

    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(CrystalGrowthProvider.INSTANCE, Block.class);
        registration.registerBlockComponent(NumericalProvider.INSTANCE, Block.class);
        registration.registerBlockComponent(SaltCampfireProvider.INSTANCE, Block.class);
    }
}
