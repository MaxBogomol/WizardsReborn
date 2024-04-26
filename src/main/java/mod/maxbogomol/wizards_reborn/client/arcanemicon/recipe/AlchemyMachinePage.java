package mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtils;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyBottleItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyPotionItem;
import mod.maxbogomol.wizards_reborn.common.recipe.AlchemyMachineContext;
import mod.maxbogomol.wizards_reborn.common.recipe.AlchemyMachineRecipe;
import mod.maxbogomol.wizards_reborn.utils.NumericalUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlchemyMachinePage extends RecipePage {
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
                Component component = NumericalUtils.getFluidName(fluidInputs1, 5000);
                if (!ClientConfig.NUMERICAL_FLUID.get()) {
                    component = NumericalUtils.getFluidName(fluidInputs1);
                }
                gui.renderTooltip(Minecraft.getInstance().font, component, mouseX, mouseY);
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
                Component component = NumericalUtils.getFluidName(fluidInputs2, 5000);
                if (!ClientConfig.NUMERICAL_FLUID.get()) {
                    component = NumericalUtils.getFluidName(fluidInputs2);
                }
                gui.renderTooltip(Minecraft.getInstance().font, component, mouseX, mouseY);
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
                Component component = NumericalUtils.getFluidName(fluidInputs3, 5000);
                if (!ClientConfig.NUMERICAL_FLUID.get()) {
                    component = NumericalUtils.getFluidName(fluidInputs3);
                }
                gui.renderTooltip(Minecraft.getInstance().font, component, mouseX, mouseY);
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
                gui.renderTooltip(Minecraft.getInstance().font, NumericalUtils.getFluidName(fluidResult, 5000), mouseX, mouseY);
            }
        } else {
            gui.blit(BACKGROUND, x + 56, y + 120 + 8, 136, 0, 16, 16, 256, 256);
        }

        if (!isWissen) {
            gui.blit(BACKGROUND, x + 14, y + 76 + 8, 136, 0, 16, 16, 256, 256);
        } else {
            if (mouseX >= x + 14 && mouseY >= y + 76 + 8 && mouseX <= x + 14 + 16 && mouseY <= y + 76 + 8 + 16) {
                gui.renderTooltip(Minecraft.getInstance().font, NumericalUtils.getWissenName(), mouseX, mouseY);
            }
        }
        if (!isSteam) {
            gui.blit(BACKGROUND, x + 98, y + 76 + 8, 136, 0, 16, 16, 256, 256);
        } else {
            if (mouseX >= x + 98 && mouseY >= y + 76 + 8 && mouseX <= x + 98 + 16 && mouseY <= y + 76 + 8 + 16) {
                gui.renderTooltip(Minecraft.getInstance().font, NumericalUtils.getSteamName(), mouseX, mouseY);
            }
        }

        renderChanged(book, gui, x, y, mouseX, mouseY);
    }

    protected FluidTank fluidTank1 = new FluidTank(Integer.MAX_VALUE);
    protected FluidTank fluidTank2 = new FluidTank(Integer.MAX_VALUE);
    protected FluidTank fluidTank3 = new FluidTank(Integer.MAX_VALUE);

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isChanged(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            SimpleContainer inv = new SimpleContainer(7);
            for (int i = 0; i < inputs.length; i++) {
                inv.setItem(i, inputs[i]);
            }
            inv.setItem(6, ItemStack.EMPTY);
            fluidTank1.setFluid(fluidInputs1);
            fluidTank2.setFluid(fluidInputs2);
            fluidTank3.setFluid(fluidInputs3);
            AlchemyMachineContext context = new AlchemyMachineContext(inv, fluidTank1, fluidTank2, fluidTank3);

            List<ItemStack> list = new ArrayList<>();

            Optional<AlchemyMachineRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsReborn.ALCHEMY_MACHINE_RECIPE.get(), context, level);
            if (recipe.isPresent()) {
                ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();

                if (!stack.isEmpty()) {
                    list.add(stack);
                } else {
                    if (!AlchemyPotionUtils.isEmpty(recipe.get().getRecipeAlchemyPotion())) {
                        ItemStack bottle = getAlchemyBottle();
                        AlchemyPotionUtils.setPotion(bottle, recipe.get().getRecipeAlchemyPotion());
                        list.add(bottle);
                    }
                }

                if (!recipe.get().getResultFluid().isEmpty()) {
                    list.add(recipe.get().getResultFluid().getFluid().getBucket().getDefaultInstance().copy());
                }

                boolean ft1 = false;
                boolean ft2 = false;
                boolean ft3 = false;

                for (int i = 0; i < recipe.get().getFluidIngredients().size(); i++) {
                    for (FluidStack fluidStack : recipe.get().getFluidIngredients().get(i).getFluids()) {
                        if (fluidInputs1.isFluidEqual(fluidStack) & !ft1) ft1 = true;
                        if (fluidInputs2.isFluidEqual(fluidStack) & !ft2) ft2 = true;
                        if (fluidInputs3.isFluidEqual(fluidStack) & !ft3) ft3 = true;

                    }
                }

                if (!ft1 && !fluidInputs1.isEmpty()) return true;
                if (!ft2 && !fluidInputs2.isEmpty()) return true;
                if (!ft3 && !fluidInputs3.isEmpty()) return true;

                if (!fluidResult.isFluidEqual(recipe.get().getResultFluid())) return true;
            } else {
                return true;
            }

            return list.isEmpty();
        }

        return false;
    }

    public ItemStack getAlchemyBottle() {
        ItemStack stack = ItemStack.EMPTY;

        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i].getItem() instanceof AlchemyBottleItem item) {
                stack = item.getPotionItem();
            }
            if (inputs[i].getItem() instanceof AlchemyPotionItem item) {
                stack = inputs[i].copy();
            }
        }

        return stack;
    }
}