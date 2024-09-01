package mod.maxbogomol.wizards_reborn.integration.client.jade;

import mod.maxbogomol.wizards_reborn.common.block.salt_campfire.SaltCampfireBlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

public enum SaltCampfireProvider implements IBlockComponentProvider {
    INSTANCE;

    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockEntity tile = accessor.getLevel().getBlockEntity(accessor.getPosition());

        if (tile instanceof SaltCampfireBlockEntity campfire) {
            IElementHelper elements = tooltip.getElementHelper();

            for(int i = 0; i < campfire.cookingTime.length; ++i) {
                ItemStack stack = campfire.getItems().get(i);
                if (!stack.isEmpty() && (campfire.cookingTime[i] - campfire.cookingProgress[i] > 0)) {
                    IElement icon = elements.item(stack, 0.5f).size(new Vec2(11, 10)).translate(new Vec2(0, -1));
                    IElement text = elements.text(stack.getHoverName().copy().append(Component.literal(" ").append(IThemeHelper.get().seconds(campfire.cookingTime[i] - campfire.cookingProgress[i]))));
                    icon.message(null);
                    tooltip.add(icon);
                    tooltip.add(text);
                }
            }
        }
    }

    public ResourceLocation getUid() {
        return WizardsRebornJade.SALT_CAMPFIRE;
    }
}