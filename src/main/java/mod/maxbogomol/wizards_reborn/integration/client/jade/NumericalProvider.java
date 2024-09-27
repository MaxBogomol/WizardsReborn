package mod.maxbogomol.wizards_reborn.integration.client.jade;

import mod.maxbogomol.wizards_reborn.api.alchemy.IFluidBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.IHeatBlockEntity;
import mod.maxbogomol.wizards_reborn.api.alchemy.ISteamBlockEntity;
import mod.maxbogomol.wizards_reborn.api.light.ILightBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.ICooldownBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IExperienceBlockEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.alchemy_machine.AlchemyMachineBlockEntity;
import mod.maxbogomol.wizards_reborn.util.NumericalUtil;
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
        BlockEntity blockEntity = accessor.getLevel().getBlockEntity(accessor.getPosition());

        if (blockEntity instanceof IWissenBlockEntity wissenBlockEntity) {
            tooltip.add(NumericalUtil.getWissenName(wissenBlockEntity.getWissen(), wissenBlockEntity.getMaxWissen()));
        }
        if (blockEntity instanceof ICooldownBlockEntity cooldownBlockEntity) {
            tooltip.add(NumericalUtil.getCooldownName(cooldownBlockEntity.getCooldown()));
        }
        if (blockEntity instanceof ILightBlockEntity lightBlockEntity) {
            if (lightBlockEntity.getLight() > 0) {
                tooltip.add(NumericalUtil.getLightName().copy().append(Component.literal(": ").append(Component.translatable("tooltip.jade.state_on"))));
            } else {
                tooltip.add(NumericalUtil.getLightName().copy().append(Component.literal(": ").append(Component.translatable("tooltip.jade.state_off"))));
            }
        }
        if (blockEntity instanceof IFluidBlockEntity fluidBlockEntity) {
            tooltip.add(NumericalUtil.getFluidName(fluidBlockEntity.getFluidStack(), fluidBlockEntity.getFluidMaxAmount()));
        }
        if (blockEntity instanceof IExperienceBlockEntity experienceBlockEntity) {
            tooltip.add(NumericalUtil.getExperienceName(experienceBlockEntity.getExperience(), experienceBlockEntity.getMaxExperience()));
        }
        if (blockEntity instanceof IHeatBlockEntity heatBlockEntity) {
            tooltip.add(NumericalUtil.getHeatName(heatBlockEntity.getHeat(), heatBlockEntity.getMaxHeat()));
        }
        if (blockEntity instanceof ISteamBlockEntity steamBlockEntity) {
            tooltip.add(NumericalUtil.getSteamName(steamBlockEntity.getSteam(), steamBlockEntity.getMaxSteam()));
        }
        if (blockEntity instanceof AlchemyMachineBlockEntity machine) {
            for (int i = 0; i <= 2; i++) {
                tooltip.add(NumericalUtil.getFluidName(machine.getFluidStack(i), machine.getMaxCapacity()));
            }
        }
    }

    public ResourceLocation getUid() {
        return WizardsRebornJade.NUMERICAL;
    }
}
