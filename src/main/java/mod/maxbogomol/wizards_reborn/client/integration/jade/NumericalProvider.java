package mod.maxbogomol.wizards_reborn.client.integration.jade;

import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.IHeatTileEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IExperienceBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_machine.AlchemyMachineBlockEntity;
import mod.maxbogomol.wizards_reborn.utils.NumericalUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum NumericalProvider implements IBlockComponentProvider {
    INSTANCE;

    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockEntity tile = accessor.getLevel().getBlockEntity(accessor.getPosition());

        if (tile instanceof IWissenBlockEntity wissenTile) {
            tooltip.add(NumericalUtils.getWissenName(wissenTile.getWissen(), wissenTile.getMaxWissen()));
        }
        if (tile instanceof ICooldownBlockEntity cooldownTile) {
            tooltip.add(NumericalUtils.getCooldownName(cooldownTile.getCooldown()));
        }
        if (tile instanceof ILightBlockEntity lightTile) {
            if (lightTile.getLight() > 0) {
                tooltip.add(NumericalUtils.getLightName().copy().append(Component.literal(": ").append(Component.translatable("tooltip.jade.state_on"))));
            } else {
                tooltip.add(NumericalUtils.getLightName().copy().append(Component.literal(": ").append(Component.translatable("tooltip.jade.state_off"))));
            }
        }
        if (tile instanceof IFluidBlockEntity fluidTile) {
            tooltip.add(NumericalUtils.getFluidName(fluidTile.getFluidStack(), fluidTile.getFluidMaxAmount()));
        }
        if (tile instanceof IExperienceBlockEntity experienceTile) {
            tooltip.add(NumericalUtils.getExperienceName(experienceTile.getExperience(), experienceTile.getMaxExperience()));
        }
        if (tile instanceof IHeatTileEntity heatTile) {
            tooltip.add(NumericalUtils.getHeatName(heatTile.getHeat(), heatTile.getMaxHeat()));
        }
        if (tile instanceof ISteamBlockEntity steamTile) {
            tooltip.add(NumericalUtils.getSteamName(steamTile.getSteam(), steamTile.getMaxSteam()));
        }
        if (tile instanceof AlchemyMachineBlockEntity machine) {
            for (int i = 0; i <= 2; i++) {
                tooltip.add(NumericalUtils.getFluidName(machine.getFluidStack(i), machine.getMaxCapacity()));
            }
        }
    }

    public ResourceLocation getUid() {
        return WizardsRebornJade.NUMERICAL;
    }
}
