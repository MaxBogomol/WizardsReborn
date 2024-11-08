package mod.maxbogomol.wizards_reborn.api.arcaneenchantment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.List;

public class ArcaneEnchantment {
    public String id;
    public int maxLevel;

    public ArcaneEnchantment(String id, int maxLevel) {
        this.id = id;
        this.maxLevel = maxLevel;
    }

    public String getId() {
        return id;
    }

    public Color getColor() {
        return Color.WHITE;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public boolean isCurse() {
        return false;
    }

    public String getTranslatedName() {
        return getTranslatedName(id);
    }

    public static String getTranslatedName(String id) {
        int i = id.indexOf(":");
        String modId = id.substring(0, i);
        String spellId = id.substring(i + 1);
        return "arcane_enchantment."  + modId + "." + spellId;
    }

    public boolean canEnchantItem(ItemStack stack) {
        return true;
    }

    public boolean checkCompatibility(ArcaneEnchantment arcaneEnchantment) {
        return true;
    }

    public Component getFullname(int level) {
        MutableComponent component = Component.translatable(getTranslatedName());
        if (level != 1 || this.getMaxLevel() != 1) {
            component.append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + level));
        }

        return component;
    }

    @OnlyIn(Dist.CLIENT)
    public List<Component> modifierAppendHoverText(ItemStack stack, Level level, TooltipFlag flags) {
        return null;
    }

    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset, int index) {
        int levelEnchantment = ArcaneEnchantmentUtil.getArcaneEnchantment(stack, this);
        if (levelEnchantment > getMaxLevel()) levelEnchantment = getMaxLevel();
        float size = 0.5f + (((float) levelEnchantment / getMaxLevel()) * 0.5f);

        float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.4f * (1f + ((index / 25f) * 0.1f)) + (index * 35);
        if (isCurse()) ticks = -ticks;

        poseStack.pushPose();
        poseStack.translate(x + 8, y + 8, 100);
        poseStack.mulPose(Axis.ZP.rotationDegrees(ticks));
        RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/sparkle"))
                .setColor(getColor()).setAlpha(0.25f + (0.5f * size))
                .renderCenteredQuad(poseStack, 9f * size)
                .endBatch();
        poseStack.popPose();
    }
}
