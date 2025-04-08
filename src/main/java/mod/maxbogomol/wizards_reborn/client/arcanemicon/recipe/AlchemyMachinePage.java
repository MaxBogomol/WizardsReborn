package mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.alchemy.AlchemyPotionUtil;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyBottleItem;
import mod.maxbogomol.wizards_reborn.common.item.equipment.AlchemyPotionItem;
import mod.maxbogomol.wizards_reborn.common.recipe.AlchemyMachineContext;
import mod.maxbogomol.wizards_reborn.common.recipe.AlchemyMachineRecipe;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornRecipes;
import mod.maxbogomol.wizards_reborn.util.NumericalUtil;
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
    public ItemStack result = ItemStack.EMPTY;
    public ItemStack[] inputs;
    public FluidStack fluidInput1 = FluidStack.EMPTY;
    public FluidStack fluidInput2 = FluidStack.EMPTY;
    public FluidStack fluidInput3 = FluidStack.EMPTY;
    public FluidStack fluidResult = FluidStack.EMPTY;
    public boolean isWissen = false;
    public boolean isSteam = false;

    public AlchemyMachinePage() {
        super(BACKGROUND);
    }

    public AlchemyMachinePage setResult(ItemStack result) {
        this.result = result;
        return this;
    }

    public AlchemyMachinePage setInputs(ItemStack... inputs) {
        this.inputs = inputs;
        return this;
    }

    public AlchemyMachinePage setFluidResult(FluidStack fluidResult) {
        this.fluidResult = fluidResult;
        return this;
    }

    public AlchemyMachinePage setFluidInputs(FluidStack fluidInput1, FluidStack fluidInput2, FluidStack fluidInput3) {
        this.fluidInput1 = fluidInput1;
        this.fluidInput2 = fluidInput2;
        this.fluidInput3 = fluidInput3;
        return this;
    }

    public AlchemyMachinePage setFluidInputs(FluidStack fluidInput1, FluidStack fluidInput2) {
        this.fluidInput1 = fluidInput1;
        this.fluidInput2 = fluidInput2;
        return this;
    }

    public AlchemyMachinePage setFluidInputs(FluidStack fluidInput1) {
        this.fluidInput1 = fluidInput1;
        return this;
    }

    public AlchemyMachinePage setIsWissen(boolean isWissen) {
        this.isWissen = isWissen;
        return this;
    }

    public AlchemyMachinePage setIsSteam(boolean isSteam) {
        this.isSteam = isSteam;
        return this;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
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

        if (!fluidInput1.isEmpty()) {
            drawItem(book, gui, fluidInput1.getFluid().getBucket().asItem().getDefaultInstance(), x + 38, y + 55, mouseX, mouseY);

            int width = 32;
            double value = ((double) 5000 / (double) fluidInput1.getAmount());
            width /= value;
            if (width <= 1 && value > 0 && !Double.isInfinite(value)) width = 2;
            gui.blit(BACKGROUND, x + 38 + 4, y + 76 + 32 - width, 128, 32 - width, 8, width, 256, 256);

            if (mouseX >= x + 38 + 4 && mouseY >= y + 76 && mouseX <= x + 38 + 4 + 8 && mouseY <= y + 76 + 32) {
                Component component = NumericalUtil.getFluidName(fluidInput1, 5000);
                if (!WizardsRebornClientConfig.NUMERICAL_FLUID.get()) {
                    component = NumericalUtil.getFluidName(fluidInput1);
                }
                gui.renderTooltip(Minecraft.getInstance().font, component, mouseX, mouseY);
            }
        } else {
            gui.blit(BACKGROUND, x + 38, y + 76 + 8, 136, 0, 16, 16, 256, 256);
        }
        if (!fluidInput2.isEmpty()) {
            drawItem(book, gui, fluidInput2.getFluid().getBucket().asItem().getDefaultInstance(), x + 56, y + 55, mouseX, mouseY);

            int width = 32;
            double value = ((double) 5000 / (double) fluidInput2.getAmount());
            width /= value;
            if (width <= 1 && value > 0 && !Double.isInfinite(value)) width = 2;
            gui.blit(BACKGROUND, x + 56 + 4, y + 76 + 32 - width, 128, 32 - width, 8, width, 256, 256);

            if (mouseX >= x + 56 + 4 && mouseY >= y + 76 && mouseX <= x + 56 + 4 + 8 && mouseY <= y + 76 + 32) {
                Component component = NumericalUtil.getFluidName(fluidInput2, 5000);
                if (!WizardsRebornClientConfig.NUMERICAL_FLUID.get()) {
                    component = NumericalUtil.getFluidName(fluidInput2);
                }
                gui.renderTooltip(Minecraft.getInstance().font, component, mouseX, mouseY);
            }
        } else {
            gui.blit(BACKGROUND, x + 56, y + 76 + 8, 136, 0, 16, 16, 256, 256);
        }
        if (!fluidInput3.isEmpty()) {
            drawItem(book, gui, fluidInput3.getFluid().getBucket().asItem().getDefaultInstance(), x + 74, y + 55, mouseX, mouseY);

            int width = 32;
            double value = ((double) 5000 / (double) fluidInput3.getAmount());
            width /= value;
            if (width <= 1 && value > 0 && !Double.isInfinite(value)) width = 2;
            gui.blit(BACKGROUND, x + 74 + 4, y + 76 + 32 - width, 128, 32 - width, 8, width, 256, 256);

            if (mouseX >= x + 74 + 4 && mouseY >= y + 76 && mouseX <= x + 74 + 4 + 8 && mouseY <= y + 76 + 32) {
                Component component = NumericalUtil.getFluidName(fluidInput3, 5000);
                if (!WizardsRebornClientConfig.NUMERICAL_FLUID.get()) {
                    component = NumericalUtil.getFluidName(fluidInput3);
                }
                gui.renderTooltip(Minecraft.getInstance().font, component, mouseX, mouseY);
            }
        } else {
            gui.blit(BACKGROUND, x + 74, y + 76 + 8, 136, 0, 16, 16, 256, 256);
        }

        if (!fluidResult.isEmpty()) {
            drawItem(book, gui, fluidResult.getFluid().getBucket().asItem().getDefaultInstance(), x + 75, y + 128, mouseX, mouseY);

            int width = 32;
            double value = ((double) 5000 / (double) fluidResult.getAmount());
            width /= value;
            if (width <= 1 && value > 0 && !Double.isInfinite(value)) width = 2;
            gui.blit(BACKGROUND, x + 60, y + 120 + 32 - width, 128, 32 - width, 8, width, 256, 256);

            if (mouseX >= x + 60 && mouseY >= y + 120 && mouseX <= x + 60 + 8 && mouseY <= y + 120 + 32) {
                gui.renderTooltip(Minecraft.getInstance().font, NumericalUtil.getFluidName(fluidResult, 5000), mouseX, mouseY);
            }
        } else {
            gui.blit(BACKGROUND, x + 56, y + 120 + 8, 136, 0, 16, 16, 256, 256);
        }

        if (!isWissen) {
            gui.blit(BACKGROUND, x + 14, y + 76 + 8, 136, 0, 16, 16, 256, 256);
        } else {
            if (mouseX >= x + 14 && mouseY >= y + 76 + 8 && mouseX <= x + 14 + 16 && mouseY <= y + 76 + 8 + 16) {
                gui.renderTooltip(Minecraft.getInstance().font, NumericalUtil.getWissenName(), mouseX, mouseY);
            }
        }
        if (!isSteam) {
            gui.blit(BACKGROUND, x + 98, y + 76 + 8, 136, 0, 16, 16, 256, 256);
        } else {
            if (mouseX >= x + 98 && mouseY >= y + 76 + 8 && mouseX <= x + 98 + 16 && mouseY <= y + 76 + 8 + 16) {
                gui.renderTooltip(Minecraft.getInstance().font, NumericalUtil.getSteamName(), mouseX, mouseY);
            }
        }

        renderChanged(book, gui, x, y, mouseX, mouseY);
    }

    protected FluidTank fluidTank1 = new FluidTank(Integer.MAX_VALUE);
    protected FluidTank fluidTank2 = new FluidTank(Integer.MAX_VALUE);
    protected FluidTank fluidTank3 = new FluidTank(Integer.MAX_VALUE);

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isChanged(ArcanemiconScreen book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        ClientLevel level = Minecraft.getInstance().level;

        if (level != null) {
            SimpleContainer inv = new SimpleContainer(7);
            for (int i = 0; i < inputs.length; i++) {
                inv.setItem(i, inputs[i]);
            }
            inv.setItem(6, ItemStack.EMPTY);
            fluidTank1.setFluid(fluidInput1);
            fluidTank2.setFluid(fluidInput2);
            fluidTank3.setFluid(fluidInput3);
            AlchemyMachineContext context = new AlchemyMachineContext(inv, fluidTank1, fluidTank2, fluidTank3);

            List<ItemStack> list = new ArrayList<>();

            Optional<AlchemyMachineRecipe> recipe = level.getRecipeManager().getRecipeFor(WizardsRebornRecipes.ALCHEMY_MACHINE.get(), context, level);
            if (recipe.isPresent()) {
                ItemStack stack = recipe.get().getResultItem(RegistryAccess.EMPTY).copy();

                if (!stack.isEmpty()) {
                    list.add(stack);
                } else {
                    if (!AlchemyPotionUtil.isEmpty(recipe.get().getAlchemyPotion())) {
                        ItemStack bottle = getAlchemyBottle();
                        AlchemyPotionUtil.setPotion(bottle, recipe.get().getAlchemyPotion());
                        list.add(bottle);
                    }
                }

                if (!recipe.get().getFluidResult().isEmpty()) {
                    list.add(recipe.get().getFluidResult().getFluid().getBucket().getDefaultInstance().copy());
                }

                boolean ft1 = false;
                boolean ft2 = false;
                boolean ft3 = false;

                for (int i = 0; i < recipe.get().getFluidInputs().size(); i++) {
                    for (FluidStack fluidStack : recipe.get().getFluidInputs().get(i).getFluids()) {
                        if (fluidInput1.isFluidEqual(fluidStack) & !ft1) ft1 = true;
                        if (fluidInput2.isFluidEqual(fluidStack) & !ft2) ft2 = true;
                        if (fluidInput3.isFluidEqual(fluidStack) & !ft3) ft3 = true;

                    }
                }

                if (!ft1 && !fluidInput1.isEmpty()) return true;
                if (!ft2 && !fluidInput2.isEmpty()) return true;
                if (!ft3 && !fluidInput3.isEmpty()) return true;

                if (!fluidResult.isFluidEqual(recipe.get().getFluidResult())) return true;
            } else {
                return true;
            }

            return list.isEmpty();
        }

        return false;
    }

    public ItemStack getAlchemyBottle() {
        ItemStack stack = ItemStack.EMPTY;

        for (ItemStack input : inputs) {
            if (input.getItem() instanceof AlchemyBottleItem item) {
                stack = item.getPotionItem();
            }
            if (input.getItem() instanceof AlchemyPotionItem item) {
                stack = input.copy();
            }
        }

        return stack;
    }
}