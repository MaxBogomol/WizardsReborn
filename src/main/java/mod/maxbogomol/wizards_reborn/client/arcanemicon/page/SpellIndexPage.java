package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.SpellIndexEntry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpellIndexPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/spell_index_page.png");
    SpellIndexEntry[] entries;

    public SpellIndexPage(SpellIndexEntry... pages) {
        super(BACKGROUND);
        this.entries = pages;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconGui gui, int x, int y, int mouseX, int mouseY) {
        for (int i = 0; i < entries.length; i ++) if (entries[i].isUnlocked()) {
            if (mouseX >= x + 2 && mouseX <= x + 124 && mouseY >= y + 8 + i * 20 && mouseY <= y + 26 + i * 20) {
                gui.changeChapter(entries[i].chapter);
                Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                return true;
            }
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        for (int i = 0; i < entries.length; i ++) {
            gui.blit(BACKGROUND, x, y + 7 + (i * 20), 128, 20, 128, 18);
        }

        for (int i = 0; i < entries.length; i ++) {
            if (entries[i].isUnlocked()) {
                gui.blit(entries[i].icon.getIcon(), x + 3, y + 8 + i * 20, 0, 0, 16, 16, 16, 16);
                if (!KnowledgeUtil.isSpell(Minecraft.getInstance().player, entries[i].icon)) {
                    gui.blit(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/research.png"), x + 3, y + 8 + i * 20, 0, 0, 16, 16, 16, 16);
                }
                for (int ii = 0; ii < entries[i].icon.getCrystalTypes().size(); ii++) {
                    gui.blit(entries[i].icon.getCrystalTypes().get(ii).getMiniIcon(), x + 22 + (ii * 4), (y + 8 + i * 20) + 14, 0, 0, 3, 3, 4, 4);
                }
                drawText(book, gui, I18n.get(entries[i].chapter.titleKey), x + 24, y + 20 + i * 20 - Minecraft.getInstance().font.lineHeight);
            } else {
                gui.blit(new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/unknown.png"), x + 3, y + 8 + i * 20, 0, 0, 16, 16, 16, 16);
                drawText(book, gui, I18n.get("wizards_reborn.arcanemicon.unknown"), x + 24, y + 20 + i * 20 - Minecraft.getInstance().font.lineHeight);
                if (mouseX >= x + 2 && mouseX <= x + 124 && mouseY >= y + 8 + i * 20 && mouseY <= y + 26 + i * 20) {
                    if (entries[i].hasKnowledge()) {
                        List<Component> list = new ArrayList<>();
                        list.add(Component.translatable("wizards_reborn.arcanemicon.knowledge_required").withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
                        boolean unknown = false;
                        if (entries[i].getKnowledge().hasPrevious()) {
                            if (!KnowledgeUtil.isKnowledge(Minecraft.getInstance().player, entries[i].getKnowledge().getPrevious())) {
                                list.add(Component.translatable("wizards_reborn.arcanemicon.unknown").withStyle(ChatFormatting.GRAY));
                                unknown = true;
                            }
                        }
                        if (!unknown) list.add(Component.empty().append(entries[i].getKnowledgeName()).withStyle(ChatFormatting.GRAY));
                        gui.renderTooltip(Minecraft.getInstance().font, list, Optional.empty(), mouseX, mouseY);
                    }
                }
            }
        }
    }
}
