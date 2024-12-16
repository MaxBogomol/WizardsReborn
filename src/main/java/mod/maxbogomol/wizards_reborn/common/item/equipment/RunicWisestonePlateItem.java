package mod.maxbogomol.wizards_reborn.common.item.equipment;

import com.mojang.blaze3d.vertex.PoseStack;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualHandler;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtil;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystalRituals;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;

import java.awt.*;
import java.util.List;

public class RunicWisestonePlateItem extends Item implements IGuiParticleItem {

    public RunicWisestonePlateItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        CrystalRitualUtil.setCrystalRitual(stack, WizardsRebornCrystalRituals.EMPTY);
        return stack;
    }

    @OnlyIn(Dist.CLIENT)
    public static class ColorHandler implements ItemColor {
        @Override
        public int getColor(ItemStack stack, int tintIndex) {
            if (tintIndex == 1) {
                CrystalRitual ritual = CrystalRitualUtil.getCrystalRitual(stack);
                if (!CrystalRitualUtil.isEmpty(ritual)) {
                    Color color = ritual.getColor();

                    int i = CrystalRitualHandler.getCrystalRituals().indexOf(ritual);

                    float a = (float) Math.abs(Math.sin((ClientTickHandler.getTotal() + (i * 10)) / 15));

                    int r = Mth.lerpInt(a, 173, color.getRed());
                    int g = Mth.lerpInt(a, 237, color.getGreen());
                    int b = Mth.lerpInt(a, 205, color.getBlue());

                    return ColorUtil.packColor(255, r, g, b);
                }
            }
            return 0xFFFFFF;
        }
    }

    @Override
    public Component getHighlightTip(ItemStack stack, Component displayName) {
        CrystalRitual ritual = CrystalRitualUtil.getCrystalRitual(stack);
        if (!CrystalRitualUtil.isEmpty(ritual)) {
            return displayName.copy().append(Component.literal(" (")).append(getRitualName(ritual)).append(Component.literal(")"));
        }

        return displayName;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        CrystalRitual ritual = CrystalRitualUtil.getCrystalRitual(stack);
        if (!CrystalRitualUtil.isEmpty(ritual)) {
            list.add(getRitualName(ritual));
        }
    }

    public static Component getRitualName(CrystalRitual ritual) {
        Color color = ritual.getColor();

        return Component.translatable(ritual.getTranslatedName()).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, color.getRed(), color.getGreen(), color.getBlue())));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        CrystalRitual ritual = CrystalRitualUtil.getCrystalRitual(stack);
        if (!CrystalRitualUtil.isEmpty(ritual)) {
            Color color = ritual.getColor();
            int ii = CrystalRitualHandler.getCrystalRituals().indexOf(ritual);

            float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) + (ii * 10);
            float alpha = (float) (0.1f + Math.abs(Math.sin(Math.toRadians(ticks)) * 0.15f));

            for (int i = 0; i < 45; i++) {
                poseStack.pushPose();
                float offset = (float) (Math.abs(Math.sin(Math.toRadians(i * 4 + (ticks * 2f)))));
                offset = (offset - 0.25f) * (1 / 0.75f);
                if (offset < 0) offset = 0;
                poseStack.translate(x + 8.5 + (Math.sin(Math.toRadians(i * 8)) * 7.5), y + 8 + (Math.cos(Math.toRadians(i * 8)) * 2) + (Math.sin(Math.toRadians(i * 8 * 2 + ticks)) * 2), 100 + (100 * Math.cos(Math.toRadians(i * 8))));
                RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                        .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/wisp"))
                        .setColor(color).setAlpha(alpha)
                        .renderCenteredQuad(poseStack, 2f * offset)
                        .endBatch();
                poseStack.popPose();
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void onTooltipRenderColor(RenderTooltipEvent.Color event) {
        if (event.getItemStack().getItem() instanceof RunicWisestonePlateItem item) {
            CrystalRitual ritual = CrystalRitualUtil.getCrystalRitual(event.getItemStack());
            if (!CrystalRitualUtil.isEmpty(ritual)) {
                Color color = ritual.getColor();
                int packColorStart = ColorUtil.packColor(255 / 10, color.getRed(), color.getGreen(), color.getBlue());
                int packColorEnd = ColorUtil.packColor(color);
                event.setBorderStart(packColorStart);
                event.setBorderEnd(packColorEnd);
            }
        }
    }
}