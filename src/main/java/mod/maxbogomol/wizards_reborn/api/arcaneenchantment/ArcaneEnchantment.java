package mod.maxbogomol.wizards_reborn.api.arcaneenchantment;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
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
        return new Color(255, 255, 255);
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
    public void renderParticle(PoseStack pose, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset, int index) {
        int levelEnchantment = ArcaneEnchantmentUtil.getArcaneEnchantment(stack, this);
        if (levelEnchantment > getMaxLevel()) {
            levelEnchantment = getMaxLevel();
        }
        float size = 0.5f + (((float) levelEnchantment / getMaxLevel()) * 0.5f);

        float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.4f + (index * 35);
        float r = getColor().getRed() / 255f;
        float g = getColor().getGreen() / 255f;
        float b = getColor().getBlue() / 255f;

        if (isCurse()) ticks = -ticks;

        RenderUtils.startGuiParticle();
        MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();

        TextureAtlasSprite sparkle = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(FluffyFur.MOD_ID, "particle/sparkle"));

        pose.pushPose();
        pose.translate(x + 8, y + 8, 100);
        pose.mulPose(Axis.ZP.rotationDegrees(ticks));
        RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 18f * size, 18f * size, sparkle.getU0(), sparkle.getU1(), sparkle.getV0(), sparkle.getV1(), r, g, b, 0.25f + (0.5f * size));
        buffersource.endBatch();
        pose.popPose();

        RenderUtils.endGuiParticle();
    }
}
