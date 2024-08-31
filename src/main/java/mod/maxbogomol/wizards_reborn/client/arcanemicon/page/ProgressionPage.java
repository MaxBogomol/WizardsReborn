package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProgressionPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/progression_page.png");
    public String text, title;
    List<Knowledge> knowledges;
    public int offset = 0;

    public ProgressionPage(String textKey, List<Knowledge> knowledges) {
        super(BACKGROUND);
        this.text = textKey;
        this.title = textKey + ".title";
        this.knowledges = knowledges;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconGui gui, int x, int y, int mouseX, int mouseY) {
        boolean changed = true;
        boolean click = false;
        if (mouseX >= x + 25 && mouseY >= y + 132 && mouseX <= x + 25 + 12 && mouseY <= y + 132 + 12) {
            offset -= 1;
            click = true;
        }
        if (mouseX >= x + 90 && mouseY >= y + 132 && mouseX <= x + 90 + 12 && mouseY <= y + 132 + 12) {
            offset += 1;
            click = true;
        }
        if (offset < 0) {
            offset = 0;
            changed = false;
        }
        if (offset > knowledges.size() - 1) {
            offset = knowledges.size() - 1;
            changed = false;
        }
        if (changed && click) {
            Minecraft.getInstance().player.playNotifySound(SoundEvents.UI_BUTTON_CLICK.get(), SoundSource.NEUTRAL, 0.1f, 2.0f);
            return true;
        }

        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean mouseScrolled(ArcanemiconGui book, int x, int y, int mouseX, int mouseY, int delta) {
        if (mouseX >= x && mouseX <= x + 128 && mouseY >= y && mouseY <= y + 160) {
            offset += delta;
            boolean changed = true;
            if (offset < 0) {
                offset = 0;
                changed = false;
            }
            if (offset > knowledges.size() - 1) {
                offset = knowledges.size() - 1;
                changed = false;
            }
            if (changed) {
                Minecraft.getInstance().player.playNotifySound(SoundEvents.UI_BUTTON_CLICK.get(), SoundSource.NEUTRAL, 0.1f, 2.0f);
                return true;
            }
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        float step = (float) 360 / 9 / 2;
        List<Component> renderList = new ArrayList<>();

        String title = I18n.get(this.title);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().font.lineHeight);

        int totalKnowledges = 0;
        int currentPoints = 0;
        int totalPoints = 0;
        for (Knowledge knowledge : knowledges) {
            if ((KnowledgeUtil.isKnowledge(Minecraft.getInstance().player, knowledge))) {
                totalKnowledges++;
                currentPoints += knowledge.getPoints();
            }
            totalPoints += knowledge.getPoints();
        }

        String progressionText = String.valueOf(Math.round((float) totalKnowledges / knowledges.size() * 1000) / 10f) + "%";
        String knowledgeText = String.valueOf(totalKnowledges) + "/" + String.valueOf(knowledges.size());
        String pointsText = String.valueOf(currentPoints) + "/" + String.valueOf(totalPoints);

        String text = I18n.get(this.text, progressionText, knowledgeText, pointsText);

        drawWrappingText(book, gui, text, x + 4, y + 24, 124);

        String offsetText = String.valueOf(offset + 1) + "/" + String.valueOf(knowledges.size());
        int offsetTextWidth = Minecraft.getInstance().font.width(offsetText);
        drawText(book, gui, offsetText, x + 64 - offsetTextWidth / 2, y + 144 - Minecraft.getInstance().font.lineHeight);

        if (mouseX >= x + 25 && mouseY >= y + 132 && mouseX <= x + 25 + 12 && mouseY <= y + 132 + 12 && offset > 0) {
            gui.blit(BACKGROUND, x + 25, y + 132, 128, 24, 12, 12);
        } else {
            gui.blit(BACKGROUND, x + 25, y + 132, 128, 12, 12, 12);
        }

        if (mouseX >= x + 90 && mouseY >= y + 132 && mouseX <= x + 90 + 12 && mouseY <= y + 132 + 12 && offset < knowledges.size() - 1) {
            gui.blit(BACKGROUND, x + 90, y + 132, 140, 24, 12, 12);
        } else {
            gui.blit(BACKGROUND, x + 90, y + 132, 140, 12, 12, 12);
        }

        for (int i = 0; i < 9; i ++) {
            int c = offset + i - 4;

            if (c >= 0 && c < knowledges.size()) {
                double dst = Math.toRadians((i * step) + (step / 2));
                int X = (int) (Math.cos(Mth.PI + dst) * (55 * Math.sin(Math.toRadians(90))));
                int Y = (int) (Math.sin(Mth.PI + dst) * (55 * Math.sin(Math.toRadians(90))));
                boolean canKnow = (KnowledgeUtil.isKnowledge(Minecraft.getInstance().player, knowledges.get(c)));
                boolean canKnowPrevious = false;
                List<Component> list = new ArrayList<>();

                if (knowledges.get(c).hasPrevious()) {
                    if ((KnowledgeUtil.isKnowledge(Minecraft.getInstance().player, knowledges.get(c).getPrevious())) && !canKnow) {
                        canKnowPrevious = true;
                    }
                }
                if (canKnow || canKnowPrevious) {
                    if (canKnowPrevious) {
                        gui.blit(BACKGROUND, x + X + 58, y + Y + 130, 140, 0, 12, 12);
                    } else {
                        gui.blit(BACKGROUND, x + X + 58, y + Y + 130, 152, 0, 12, 12);
                        gui.renderItem(knowledges.get(c).getIcon(),x + X + 56, y + Y + 128);
                        gui.renderItemDecorations(Minecraft.getInstance().font, knowledges.get(c).getIcon(),x + X + 56, y + Y + 128);
                    }
                    list.add(Component.empty().append(knowledges.get(c).getName()).withStyle(ChatFormatting.GRAY));
                    int points = knowledges.get(c).getPoints();
                    if (points > 0) {
                        list.add(Component.empty());
                        list.add(Component.empty().append(Component.translatable("wizards_reborn.arcanemicon.knowledge_points")).append(Component.literal(" ")).append(Component.literal(String.valueOf(points))).withStyle(ChatFormatting.GRAY));
                    }
                } else {
                    gui.blit(BACKGROUND, x + X + 58, y + Y + 130, 128, 0, 12, 12);
                    list.add(Component.translatable("wizards_reborn.arcanemicon.unknown").withStyle(ChatFormatting.GRAY));
                }

                if (mouseX >= x + X + 56 && mouseX <= x + X + 56 + 16 && mouseY >= y + Y + 128 && mouseY <= y + Y + 128 + 16) {
                    renderList = list;
                }
            }
        }

        if (!renderList.isEmpty()) gui.renderTooltip(Minecraft.getInstance().font, renderList, Optional.empty(), mouseX, mouseY);
    }
}