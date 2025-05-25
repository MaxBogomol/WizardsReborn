package mod.maxbogomol.wizards_reborn.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneFishingRodItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FishingHookRenderer.class)
public abstract class FishingHookRendererMixin {

    @ModifyVariable(method = "render*", at = @At("STORE"))
    public VertexConsumer wizards_reborn$render(VertexConsumer vertexConsumer, FishingHook entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        Player player = entity.getPlayerOwner();
        if (player != null) {
            ItemStack itemstack = player.getMainHandItem();
            if (!itemstack.canPerformAction(net.minecraftforge.common.ToolActions.FISHING_ROD_CAST)) {
                itemstack = player.getOffhandItem();
            }
            if (itemstack.getItem() instanceof ArcaneFishingRodItem fishingRod) {
                ResourceLocation resourceLocation = fishingRod.getTexture(itemstack, entity);
                return buffer.getBuffer(RenderType.entityCutout(resourceLocation));
            }
        }
        return vertexConsumer;
    }
}
