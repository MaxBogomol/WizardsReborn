package mod.maxbogomol.wizards_reborn.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantment;
import mod.maxbogomol.wizards_reborn.api.arcaneenchantment.ArcaneEnchantmentUtils;
import mod.maxbogomol.wizards_reborn.client.gui.container.TotemOfDisenchantContainer;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.tileentity.TotemOfDisenchantStartEffectPacket;
import mod.maxbogomol.wizards_reborn.common.block.totem.disenchant.TotemOfDisenchantBlockEntity;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class TotemOfDisenchantScreen extends AbstractContainerScreen<TotemOfDisenchantContainer> {
    private final ResourceLocation GUI = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/totem_of_disenchant.png");

    public ItemStack currentBook = ItemStack.EMPTY;

    public TotemOfDisenchantScreen(TotemOfDisenchantContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.imageHeight = 194;
        this.inventoryLabelY = this.inventoryLabelY + 28;
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

        if (menu.tileEntity instanceof TotemOfDisenchantBlockEntity totem) {
            int buttonOffset = 0;

            if (x >= i + 141 && y >= j + 73 && x <= i + 141 + 16 && y <= j + 73 + 16 && !totem.isStart) {
                buttonOffset = 18;
            }
            gui.blit(GUI, i + 140, j + 72, 176 + buttonOffset, 0, 18, 18, 256, 256);

            ItemStack stack = totem.itemHandler.getStackInSlot(0);

            if (!stack.isEmpty()) {
                Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
                Map<ArcaneEnchantment, Integer> arcaneEnchantments = new HashMap<>();
                int xx = 0;
                int yy = 0;

                if (ArcaneEnchantmentUtils.isArcaneItem(stack)) {
                    arcaneEnchantments = ArcaneEnchantmentUtils.getAllArcaneEnchantments(stack);
                }

                if (stack.getItem().equals(Items.ENCHANTED_BOOK)) {
                    ListTag listtag = EnchantedBookItem.getEnchantments(stack);
                    for(int ii = 0; ii < listtag.size(); ++ii) {
                        CompoundTag compoundtag = listtag.getCompound(ii);
                        Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(EnchantmentHelper.getEnchantmentId(compoundtag));
                        enchantments.put(enchantment, EnchantmentHelper.getEnchantmentLevel(compoundtag));
                    }
                }

                for (ArcaneEnchantment enchantment : arcaneEnchantments.keySet()) {
                    if (enchantment != null) {
                        ItemStack book = new ItemStack(WizardsReborn.ARCANE_ENCHANTED_BOOK.get());
                        ArcaneEnchantmentUtils.addArcaneEnchantment(book, enchantment, arcaneEnchantments.get(enchantment));

                        int of = 0;
                        if (currentBook.getItem().equals(WizardsReborn.ARCANE_ENCHANTED_BOOK.get())) {
                            if (ArcaneEnchantmentUtils.getArcaneEnchantment(currentBook, enchantment) > 0) {
                                gui.blit(GUI, i + 42 + (xx * 18), j + 16 + (yy * 18), 176, 18, 20, 20, 256, 256);
                                of = -1;
                            }
                        }

                        gui.renderItem(book, i + 44 + (xx * 18), j + 18 + (yy * 18) + of);
                        if (x >= i + 44 + (xx * 18) && y >= j + 18 + of + (yy * 18) && x <= i + 44 + (xx * 18) + 16 && y <= j + 18 + of + (yy * 18) + 16) {
                            gui.renderTooltip(Minecraft.getInstance().font, book, x, y + of);
                        }
                    }
                    xx++;
                    if (xx >= 7 ) {
                        xx = 0;
                        yy++;
                    }
                }

                for (Enchantment enchantment : enchantments.keySet()) {
                    if (enchantment != null) {
                        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                        EnchantedBookItem.addEnchantment(book, new EnchantmentInstance(enchantment, enchantments.get(enchantment)));

                        int of = 0;
                        if (currentBook.getItem().equals(Items.ENCHANTED_BOOK)) {
                            ListTag listtag = EnchantedBookItem.getEnchantments(currentBook);
                            for(int ii = 0; ii < listtag.size(); ++ii) {
                                CompoundTag compoundtag = listtag.getCompound(ii);
                                Enchantment enchantment1 = ForgeRegistries.ENCHANTMENTS.getValue(EnchantmentHelper.getEnchantmentId(compoundtag));
                                if (enchantment.equals(enchantment1)) {
                                    gui.blit(GUI, i + 42 + (xx * 18), j + 16 + (yy * 18), 176, 18, 20, 20, 256, 256);
                                    of = -1;
                                }
                            }
                        }

                        gui.renderItem(book, i + 44 + (xx * 18), j + 18 + (yy * 18) + of);
                        if (x >= i + 44 + (xx * 18) && y >= j + 18 + of + (yy * 18) && x <= i + 44 + (xx * 18) + 16 && y <= j + 18 + of + (yy * 18) + 16) {
                            gui.renderTooltip(Minecraft.getInstance().font, book, x, y + of);
                        }
                    }
                    xx++;
                    if (xx >= 7 ) {
                        xx = 0;
                        yy++;
                    }
                }
            } else {
                currentBook = ItemStack.EMPTY;
            }

            if (!currentBook.isEmpty()) {
                gui.renderItem(currentBook,  i + 55, j + 72);
                if (x >= i + 55 && y >= j + 72 && x <= i + 55 + 16 && y <= j + 72 + 16) {
                    gui.renderTooltip(Minecraft.getInstance().font, currentBook,  x, y);
                }
            }
        }
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        int i = this.leftPos;
        int j = this.topPos;

        if (menu.tileEntity instanceof TotemOfDisenchantBlockEntity totem) {
            ItemStack stack = totem.itemHandler.getStackInSlot(0);

            if (!stack.isEmpty()) {
                Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
                Map<ArcaneEnchantment, Integer> arcaneEnchantments = new HashMap<>();
                int xx = 0;
                int yy = 0;

                if (ArcaneEnchantmentUtils.isArcaneItem(stack)) {
                    arcaneEnchantments = ArcaneEnchantmentUtils.getAllArcaneEnchantments(stack);
                }

                if (stack.getItem().equals(Items.ENCHANTED_BOOK)) {
                    ListTag listtag = EnchantedBookItem.getEnchantments(stack);
                    for(int ii = 0; ii < listtag.size(); ++ii) {
                        CompoundTag compoundtag = listtag.getCompound(ii);
                        Enchantment enchantment = ForgeRegistries.ENCHANTMENTS.getValue(EnchantmentHelper.getEnchantmentId(compoundtag));
                        enchantments.put(enchantment, EnchantmentHelper.getEnchantmentLevel(compoundtag));
                    }
                }

                for (ArcaneEnchantment enchantment : arcaneEnchantments.keySet()) {
                    ItemStack book = new ItemStack(WizardsReborn.ARCANE_ENCHANTED_BOOK.get());
                    ArcaneEnchantmentUtils.addArcaneEnchantment(book, enchantment, arcaneEnchantments.get(enchantment));
                    if (x >= i + 44 + (xx * 18) && y >= j + 18 + (yy * 18) && x <= i + 44 + (xx * 18) + 16 && y <= j + 18 + (yy * 18) + 16 && !totem.isStart && !enchantment.isCurse()) {
                        currentBook = book;
                        Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                        return true;
                    }
                    xx++;
                    if (xx >= 7 ) {
                        xx = 0;
                        yy++;
                    }
                }

                for (Enchantment enchantment : enchantments.keySet()) {
                    ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
                    EnchantedBookItem.addEnchantment(book, new EnchantmentInstance(enchantment, enchantments.get(enchantment)));
                    if (x >= i + 44 + (xx * 18) && y >= j + 18 + (yy * 18) && x <= i + 44 + (xx * 18) + 16 && y <= j + 18 + (yy * 18) + 16 && !totem.isStart && !enchantment.isCurse()) {
                        currentBook = book;
                        Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                        return true;
                    }
                    xx++;
                    if (xx >= 7 ) {
                        xx = 0;
                        yy++;
                    }
                }
            }

            if (x >= i + 141 && y >= j + 73 && x <= i + 141 + 16 && y <= j + 73 + 16) {
                if (!currentBook.isEmpty() && !totem.isStart) {
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.UI_BUTTON_CLICK.get(), SoundSource.NEUTRAL, 0.5f, 1.0f);
                    PacketHandler.sendToServer(new TotemOfDisenchantStartEffectPacket(getMenu().tileEntity.getBlockPos(), currentBook));
                    this.onClose();
                }
                return true;
            }
        }

        return super.mouseClicked(x, y, button);
    }
}
