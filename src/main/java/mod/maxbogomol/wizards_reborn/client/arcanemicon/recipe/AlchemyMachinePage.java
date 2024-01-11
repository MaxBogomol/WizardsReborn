package mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import mod.maxbogomol.wizards_reborn.common.item.FluidStorageBaseItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

public class AlchemyMachinePage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/alchemy_machine_page.png");
    public ItemStack result;
    public ItemStack[] inputs;
    public FluidStack fluidInputs1;
    public FluidStack fluidInputs2;
    public FluidStack fluidInputs3;
    public FluidStack fluidResult;
    public boolean isWissen;
    public boolean isSteam;

    public AlchemyMachinePage(ItemStack result, FluidStack fluidResult, boolean isWissen, boolean isSteam, FluidStack fluidInputs1, FluidStack fluidInputs2, FluidStack fluidInputs3, ItemStack... inputs) {
        super(BACKGROUND);
        this.result = result;
        this.inputs = inputs;
        this.fluidResult = fluidResult;
        this.fluidInputs1 = fluidInputs1;
        this.fluidInputs2 = fluidInputs2;
        this.fluidInputs3 = fluidInputs3;
        this.isWissen = isWissen;
        this.isSteam = isSteam;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        for (int i = 0; i < 2; i ++) {
            for (int j = 0; j < 3; j ++) {
                int index = i * 3 + j;
                if (index < inputs.length && !inputs[index].isEmpty()) {
                    drawItem(book, gui, inputs[index], x + 38 + j * 18, y + 15 + i * 18, mouseX, mouseY);
                }
            }
        }
        if (!result.isEmpty()) {
            drawItem(book, gui, result, x + 34, y + 128, mouseX, mouseY);
        }

        if (!fluidInputs1.isEmpty()) {
            drawItem(book, gui, fluidInputs1.getFluid().getBucket().asItem().getDefaultInstance(), x + 38, y + 55, mouseX, mouseY);

            int width = 32;
            width /= (double) 5000 / (double) fluidInputs1.getAmount();
            gui.blit(BACKGROUND, x + 38 + 4, y + 76 + 32 - width, 128, 32 - width, 8, width, 256, 256);

            if (mouseX >= x + 38 + 4 && mouseY >= y + 76 && mouseX <= x + 38 + 4 + 8 && mouseY <= y + 76 + 32) {
                gui.renderTooltip(Minecraft.getInstance().font, FluidStorageBaseItem.getFluidName(fluidInputs1, 5000), mouseX, mouseY);
            }
        } else {
            gui.blit(BACKGROUND, x + 38, y + 76 + 8, 136, 0, 16, 16, 256, 256);
        }
        if (!fluidInputs2.isEmpty()) {
            drawItem(book, gui, fluidInputs2.getFluid().getBucket().asItem().getDefaultInstance(), x + 56, y + 55, mouseX, mouseY);

            int width = 32;
            width /= (double) 5000 / (double) fluidInputs2.getAmount();
            gui.blit(BACKGROUND, x + 56 + 4, y + 76 + 32 - width, 128, 32 - width, 8, width, 256, 256);

            if (mouseX >= x + 56 + 4 && mouseY >= y + 76 && mouseX <= x + 56 + 4 + 8 && mouseY <= y + 76 + 32) {
                gui.renderTooltip(Minecraft.getInstance().font, FluidStorageBaseItem.getFluidName(fluidInputs2, 5000), mouseX, mouseY);
            }
        } else {
            gui.blit(BACKGROUND, x + 56, y + 76 + 8, 136, 0, 16, 16, 256, 256);
        }
        if (!fluidInputs3.isEmpty()) {
            drawItem(book, gui, fluidInputs3.getFluid().getBucket().asItem().getDefaultInstance(), x + 74, y + 55, mouseX, mouseY);

            int width = 32;
            width /= (double) 5000 / (double) fluidInputs3.getAmount();
            gui.blit(BACKGROUND, x + 74 + 4, y + 76 + 32 - width, 128, 32 - width, 8, width, 256, 256);

            if (mouseX >= x + 74 + 4 && mouseY >= y + 76 && mouseX <= x + 74 + 4 + 8 && mouseY <= y + 76 + 32) {
                gui.renderTooltip(Minecraft.getInstance().font, FluidStorageBaseItem.getFluidName(fluidInputs3, 5000), mouseX, mouseY);
            }
        } else {
            gui.blit(BACKGROUND, x + 74, y + 76 + 8, 136, 0, 16, 16, 256, 256);
        }

        if (!fluidResult.isEmpty()) {
            drawItem(book, gui, fluidResult.getFluid().getBucket().asItem().getDefaultInstance(), x + 75, y + 128, mouseX, mouseY);

            int width = 32;
            width /= (double) 5000 / (double) fluidResult.getAmount();
            gui.blit(BACKGROUND, x + 60, y + 120 + 32 - width, 128, 32 - width, 8, width, 256, 256);

            if (mouseX >= x + 60 && mouseY >= y + 120 && mouseX <= x + 60 + 8 && mouseY <= y + 120 + 32) {
                gui.renderTooltip(Minecraft.getInstance().font, FluidStorageBaseItem.getFluidName(fluidResult, 5000), mouseX, mouseY);
            }
        } else {
            gui.blit(BACKGROUND, x + 56, y + 120 + 8, 136, 0, 16, 16, 256, 256);
        }

        if (!isWissen) {
            gui.blit(BACKGROUND, x + 14, y + 76 + 8, 136, 0, 16, 16, 256, 256);
        }
        if (!isSteam) {
            gui.blit(BACKGROUND, x + 98, y + 76 + 8, 136, 0, 16, 16, 256, 256);
        }
    }
}