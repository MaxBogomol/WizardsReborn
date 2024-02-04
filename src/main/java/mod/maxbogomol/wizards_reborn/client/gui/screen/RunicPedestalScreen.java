package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.client.gui.container.RunicPedestalContainer;
import mod.maxbogomol.wizards_reborn.common.tileentity.RunicPedestalTileEntity;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Random;

public class RunicPedestalScreen extends AbstractContainerScreen<RunicPedestalContainer> {
    private final ResourceLocation GUI = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/runic_pedestal.png");

    public RunicPedestalScreen(RunicPedestalContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageHeight = 164;
        this.inventoryLabelY = this.inventoryLabelY - 2;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, ColorUtils.packColor(255, 237, 201, 146), false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTicks, int x, int y) {
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        int i = this.leftPos;
        int j = this.topPos;
        gui.blit(GUI, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if (menu.tileEntity instanceof RunicPedestalTileEntity table) {
            if (menu.tileEntity.getBlockState().getValue(BlockStateProperties.LIT)) {
                gui.blit(GUI, i + 72, j + 22, 176, 0, 32, 32);

                Random random = new Random(table.getBlockPos().asLong());

                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
                RenderSystem.setShaderColor(1F, 1F, 1F, 0.15F);

                for (int ii = 0; ii < 25; ii++) {
                    RenderSystem.setShaderColor(1F, 1F, 1F, (float) (0.05F + (random.nextDouble() / 10)));
                    double dst = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 8;
                    double dstX = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 16;
                    double dstY = (360 * random.nextDouble()) + (ClientTickHandler.ticksInGame + Minecraft.getInstance().getFrameTime()) / 16;
                    int X = (int) (Math.cos(dst) * (4 * Math.sin(Math.toRadians(dstX))));
                    int Y = (int) (Math.sin(dst) * (4 * Math.sin(Math.toRadians(dstY))));

                    gui.blit(GUI, i + X + 72, j + Y + 22, 176, 0, 32, 32);
                }

                RenderSystem.disableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
            }
        }
    }
}
