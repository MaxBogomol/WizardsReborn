package mod.maxbogomol.wizards_reborn.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import mod.maxbogomol.wizards_reborn.common.tileentity.WissenTranslatorTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.profiler.IProfiler;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.DrawHighlightEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WorldRenderHandler {

    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        if (ClientConfig.BETTER_LAYERING.get()) {
            RenderSystem.pushMatrix();
            RenderSystem.multMatrix(event.getMatrixStack().getLast().getMatrix());
            getDelayedRender().finish(RenderUtils.GLOWING_PARTICLE);
            RenderSystem.popMatrix();
        }
    }

    static IRenderTypeBuffer.Impl DELAYED_RENDER = null;

    public static IRenderTypeBuffer.Impl getDelayedRender() {
        if (DELAYED_RENDER == null) {
            Map<RenderType, BufferBuilder> buffers = new HashMap<>();
            for (RenderType type : new RenderType[]{
                    RenderUtils.GLOWING_PARTICLE}) {
                buffers.put(type, new BufferBuilder(type.getBufferSize()));
            }
            DELAYED_RENDER = IRenderTypeBuffer.getImpl(buffers, new BufferBuilder(256));
        }
        return DELAYED_RENDER;
    }

    public static void onDrawBlockHighlight(DrawHighlightEvent event) {
        Minecraft mc = Minecraft.getInstance();
        MatrixStack ms = event.getMatrix();
        IRenderTypeBuffer.Impl buffers = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();

        PlayerEntity player = mc.player;
        ItemStack main = player.getHeldItemMainhand();
        ItemStack offhand = player.getHeldItemOffhand();
        boolean renderWand = false;

        double eyeX = mc.getRenderManager().info.getProjectedView().getX();
        double eyeY = mc.getRenderManager().info.getProjectedView().getY();
        double eyeZ = mc.getRenderManager().info.getProjectedView().getZ();
        ItemStack stack = main;

        if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
            renderWand = true;
        } else {
            if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                renderWand = true;
                stack = offhand;
            }
        }

        CompoundNBT nbt = stack.getTag();
        if (nbt == null) {
            nbt = new CompoundNBT();
            stack.setTag(nbt);
        }

        renderWand = false;

        if (renderWand) {
            if (nbt.getBoolean("block")) {
                BlockPos blockPos = new BlockPos(nbt.getInt("blockX"), nbt.getInt("blockY"), nbt.getInt("blockZ"));

                if (player.world.getTileEntity(blockPos) instanceof IWissenTileEntity) {
                    ms.push();
                    RenderUtils.renderBoxBlockOutline(ms, buffers, mc.world.getBlockState(blockPos).getShape(mc.world, blockPos),
                            blockPos.getX() - eyeX, blockPos.getY() - eyeY, blockPos.getZ() - eyeZ, new Color(255, 255, 255));
                    ms.pop();
                }
            }
        }
    }
}
