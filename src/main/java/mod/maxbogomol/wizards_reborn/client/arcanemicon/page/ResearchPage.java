package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.monogram.Monogram;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramMapEntry;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramRecipe;
import mod.maxbogomol.wizards_reborn.api.monogram.MonogramHandler;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconChapters;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Chapter;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornClientConfig;
import mod.maxbogomol.wizards_reborn.common.network.spell.SpellUnlockPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.*;

public class ResearchPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/monogram_page.png");
    public boolean main;
    public Random random = new Random();
    public ArrayList<MonogramMapEntry> map = new ArrayList<MonogramMapEntry>();
    public Map<Monogram, Integer> monograms = new HashMap<Monogram, Integer>();
    public int mapSize = 10;
    public Monogram currentMonogram;
    public MonogramRecipesPage recipeList;
    public Spell spell;
    public Chapter lastChapter;

    public ResearchPage(boolean main) {
        super(BACKGROUND);
        this.main = main;
        if (!main) {
            recipeList = new MonogramRecipesPage(null);
        }
    }

    public void createNap(Spell spell) {
        map.clear();
        this.spell = spell;
        mapSize = spell.getResearch().getMapSize();
        map = (ArrayList<MonogramMapEntry>) spell.getResearch().getMap().clone();
        monograms.clear();
        monograms.putAll(spell.getResearch().getMonograms());
    }

    @OnlyIn(Dist.CLIENT)
    public void tick(ArcanemiconGui book) {
        setActives();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconGui book, int x, int y, int mouseX, int mouseY) {
        if (mouseX >= x && mouseY >= y && mouseX <= x + 128 && mouseY <= y + 160) {
            if (main) {
                if (mouseY < y + 130) {
                    if (currentMonogram != null) {
                        int index = getSelectedMonogramWithShift(x, y, mouseX, mouseY);
                        if (index >= 0) {
                            map.add(getSelectedMonogramWithShift(x, y, mouseX, mouseY), new MonogramMapEntry(currentMonogram));
                            Minecraft.getInstance().player.playNotifySound(SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0f, 1.0f);
                            currentMonogram = null;
                            return true;
                        } else {
                            map.add(new MonogramMapEntry(currentMonogram));
                            Minecraft.getInstance().player.playNotifySound(SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0f, 1.0f);
                            currentMonogram = null;
                            return true;
                        }
                    } else {
                        int index = getSelectedMonogram(x, y, mouseX, mouseY);
                        if (index >= 0) {
                            if (!map.get(getSelectedMonogram(x, y, mouseX, mouseY)).isStart()) {
                                currentMonogram = map.get(getSelectedMonogram(x, y, mouseX, mouseY)).getMonogram();
                                map.remove(getSelectedMonogram(x, y, mouseX, mouseY));
                                Minecraft.getInstance().player.playNotifySound(SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0f, 1.2f);
                                return true;
                            }
                        }
                    }
                } else {
                    int actives = getAllActives();
                    if (actives >= mapSize) {
                        if (mouseX >= x + 95 && mouseY >= y + 132 && mouseX <= x + 95 + 18 && mouseY <= y + 132 + 18) {
                            ArcanemiconGui.currentChapter = lastChapter;
                            WizardsRebornPacketHandler.sendToServer(new SpellUnlockPacket(spell));
                            Minecraft.getInstance().player.playNotifySound(SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.NEUTRAL, 1.0f, 1.0f);
                            Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                            return true;
                        }
                    }
                }
            } else {
                if (ArcanemiconChapters.RESEARCH_MAIN.currentMonogram != null && ArcanemiconChapters.RESEARCH_MAIN.monograms.size() > 0) {
                    Monogram monogram = ArcanemiconChapters.RESEARCH_MAIN.currentMonogram;
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    ArcanemiconChapters.RESEARCH_MAIN.monograms.put(monogram, ArcanemiconChapters.RESEARCH_MAIN.monograms.get(monogram) + 1);
                    ArcanemiconChapters.RESEARCH_MAIN.currentMonogram = null;
                    return true;
                }

                int ii = 0;
                Monogram[] monogramsSet = ArcanemiconChapters.RESEARCH_MAIN.monograms.keySet().toArray(new Monogram[monograms.size()]);
                for (int i = 0; i < monogramsSet.length; i++) {
                    if (i % 5 == 0 && i > 0) {
                        ii++;
                    }
                    int X = ((i - (ii * 5)) * (24));

                    if (mouseX >= x + X + 1 && mouseY >= y + 8 + (ii * 15) && mouseX <= x + X + 9 && mouseY <= y + 8 + (ii * 15) + 8) {
                        Monogram monogram = monogramsSet[i];
                        if (ArcanemiconChapters.RESEARCH_MAIN.monograms.size() > 0 && ArcanemiconChapters.RESEARCH_MAIN.monograms.get(monogram) > 0) {
                            Minecraft.getInstance().player.playNotifySound(SoundEvents.ITEM_PICKUP, SoundSource.NEUTRAL, 1.0f, 1.2f);
                            ArcanemiconChapters.RESEARCH_MAIN.monograms.put(monogram, ArcanemiconChapters.RESEARCH_MAIN.monograms.get(monogram) - 1);
                            ArcanemiconChapters.RESEARCH_MAIN.currentMonogram = monogram;
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        boolean hardmode = WizardsRebornClientConfig.RESEARCH_HARDMODE.get();
        if (Minecraft.getInstance().level.getLevelData().isHardcore()) hardmode = true;
        if (main) {
            setActives();
            gui.blit(BACKGROUND, x + 3, y + 3, 188, 60, 15, 15);
            gui.blit(BACKGROUND, x + 108, y + 3, 188 + 15, 60, 15, 15);
            gui.blit(BACKGROUND, x + 3, y + 108, 188, 60 + 15, 15, 15);
            gui.blit(BACKGROUND, x + 108, y + 108, 188 + 15, 60 + 15, 15, 15);
            gui.blit(BACKGROUND, x + 49, y + 49, 218, 30, 30, 30);
            gui.blit(BACKGROUND, x + 49, y + 126, 128, 50, 30, 30);
            if (spell != null) {
                gui.blit(spell.getIcon(), x + 56, y + 133, 0, 0, 16, 16, 16, 16);
                if (mouseX >= x + 56 && mouseY >= y + 133 && mouseX <= x + 56 + 16 && mouseY <= y + 133 + 16) {
                    gui.renderTooltip(Minecraft.getInstance().font, Component.translatable(spell.getTranslatedName()), mouseX, mouseY);
                }
            }

            int outlineXOffset = 0;
            int outlineYOffset = 14;

            if (WizardsRebornClientConfig.OLD_RESEARCH_MONOGRAM_OUTLINE.get()) {
                outlineYOffset = 0;
            }
            if (WizardsRebornClientConfig.BRIGHT_RESEARCH_MONOGRAM_OUTLINE.get()) {
                outlineXOffset = 14;
                outlineYOffset = 14;
            }

            int actives = getAllActives();

            Font font = Minecraft.getInstance().font;
            String stringSize = String.valueOf(actives) + "/" + String.valueOf(mapSize);
            drawText(book, gui, stringSize, x + 25 - (font.width(stringSize) / 2), y + 138);

            gui.blit(BACKGROUND, x + 95, y + 132, 128, 80, 18, 18);
            if (actives >= mapSize) {
                gui.blit(BACKGROUND, x + 95, y + 132, 146, 80, 18, 18);
                if (mouseX >= x + 95 && mouseY >= y + 132 && mouseX <= x + 95 + 18 && mouseY <= y + 132 + 18) {
                    gui.blit(BACKGROUND, x + 95, y + 132, 164, 80, 18, 18);
                    gui.renderTooltip(Minecraft.getInstance().font, Component.translatable("wizards_reborn.arcanemicon.research"), mouseX, mouseY);
                }
            }

            double angleBetweenEach = 360.0 / (map.size());
            float size = 2 * map.size() + 10;
            if (size > 50) {
                size = 50;
            }
            Vec2 point = new Vec2(size, 0), center = new Vec2(size, size);
            Vec2 pointS = new Vec2(size + 5, 0), centerS = new Vec2(size + 5, size + 5);
            Vec2 pointL = new Vec2(size, 0), centerL = new Vec2(size, size);

            int i = 0;
            int selected = getSelectedMonogram(x, y, mouseX, mouseY);
            if (WizardsRebornClientConfig.RESEARCH_MONOGRAM_CONNECTS.get()) {
                for (MonogramMapEntry monogramMapEntry : map) {
                    for (int ii = 0; ii < 2; ii++) {
                        if (monogramMapEntry.isActive()) {
                            gui.blit(BACKGROUND, x + 59 - (int) size + (int) pointL.x + 3, y + 59 - (int) size + (int) pointL.y + 3, 184, 40, 4, 4);
                        }
                        pointL = rotatePointAbout(pointL, centerL, angleBetweenEach / 2);
                    }
                }
            }
            i = 0;
            for (MonogramMapEntry monogramMapEntry : map) {
                if (i == selected && mouseX >= x + 3  && mouseY >= y + 3 && mouseX <= x + 123 && mouseY <= y + 123) {
                    gui.blit(BACKGROUND, x + 59 - (int) size + (int) pointS.x - 2 - 5, y + 59 - (int) size + (int) pointS.y - 2 - 5, 158, 40, 14, 14);
                }
                if (monogramMapEntry.isActive()) {
                    gui.blit(BACKGROUND, x + 59 - (int) size + (int) point.x - 2, y + 59 - (int) size + (int) point.y - 2, 158 + outlineXOffset, 40 + outlineYOffset, 14, 14);
                }
                if (currentMonogram != null && !hardmode) {
                    if (isCanConnect(monogramMapEntry.getMonogram())) {
                        gui.blit(BACKGROUND, x + 59 - (int) size + (int) point.x - 1, y + 59 - (int) size + (int) point.y - 1, 172, 40, 12, 12);
                    }
                }
                boolean start = monogramMapEntry.isStart();
                gui.blit(BACKGROUND, x + 59 - (int) size + (int) point.x, y + 59 - (int) size + (int) point.y, (start ? 168 : 158), 30, 10, 10);
                gui.blit(BACKGROUND, x + 59 - (int) size + (int) point.x + 1, y + 59 - (int) size + (int) point.y + 1, 178, 30, 8, 8);
                if (monogramMapEntry.getMonogram() != null) {
                    monogramMapEntry.getMonogram().renderMiniIcon(gui, x + 59 - (int) size + (int) point.x + 1, y + 59 - (int) size + (int) point.y + 1);
                }
                if (mouseX >= x + 59 - (int) size + (int) point.x && mouseY >= y + 59 - (int) size + (int) point.y && mouseX <= x + 59 - (int) size + (int) point.x + 8 && mouseY <= y + 59 - (int) size + (int) point.y + 8) {
                    gui.renderTooltip(Minecraft.getInstance().font, monogramMapEntry.getMonogram().getComponentList(), Optional.empty(), mouseX, mouseY);
                }
                point = rotatePointAbout(point, center, angleBetweenEach);
                pointS = rotatePointAbout(pointS, centerS, angleBetweenEach);
                i++;
            }

            if (currentMonogram != null) {
                gui.pose().pushPose();
                gui.pose().translate(0,0,1000);
                gui.blit(BACKGROUND, mouseX - 5, mouseY - 5, 158, 30, 10, 10);
                gui.blit(BACKGROUND, mouseX - 4, mouseY - 4, 178, 30, 8, 8);
                currentMonogram.renderMiniIcon(gui, mouseX - 4, mouseY - 4);
                gui.pose().popPose();
            }
        } else {
            int ii = 0;
            Monogram[] monogramsSet = ArcanemiconChapters.RESEARCH_MAIN.monograms.keySet().toArray(new Monogram[monograms.size()]);
            for (int i = 0; i < monogramsSet.length; i++) {
                if (i % 5 == 0 && i > 0) {
                    ii++;
                }
                int X = ((i - (ii * 5)) * (24));

                if (mouseX >= x + X + 1 && mouseY >= y + 8 + (ii * 15) && mouseX <= x + X + 9 && mouseY <= y + 8 + (ii * 15) + 8) {
                    gui.renderTooltip(Minecraft.getInstance().font, monogramsSet[i].getComponentList(), Optional.empty(), mouseX, mouseY);
                }

                gui.blit(BACKGROUND, x + X, y + 7 + (ii * 15), 158, 30, 10, 10);
                gui.blit(BACKGROUND, x + 1 + X, y + 8 + (ii * 15), 178, 30, 8, 8);
                monogramsSet[i].renderMiniIcon(gui, x + 1 + X, y + 8 + (ii * 15));
                drawText(book, gui, String.valueOf(ArcanemiconChapters.RESEARCH_MAIN.monograms.get(monogramsSet[i])), x + 11 + X, y + 9 + (ii * 15));
            }

            if (hardmode) {
                String title = I18n.get(getHardmodeText());
                int titleWidth = Minecraft.getInstance().font.width(title);
                drawText(book, gui, title, x + 64 - titleWidth / 2, y + 145 - Minecraft.getInstance().font.lineHeight);
            } else {
                if (ArcanemiconChapters.RESEARCH_MAIN.currentMonogram != null) {
                    recipeList.monogram = ArcanemiconChapters.RESEARCH_MAIN.currentMonogram;
                    recipeList.render(book, gui, x, y + (ii * 20) + 10, mouseX, mouseY);
                }
            }
        }
    }

    public static Vec2 rotatePointAbout(Vec2 in, Vec2 about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vec2((float) newX, (float) newY);
    }

    public float getMouseAngle(int x, int y, double mouseX, double mouseY) {
        double X = x + 59;
        double Y = y + 59;

        double angle =  Math.toDegrees(Math.atan2(mouseY-Y,mouseX-X)) + 90 + 25.5F % 360;
        if (angle < 0D) {
            angle += 360D;
        }

        return (float) angle;
    }

    public int getSelectedMonogram(int x, int y, double mouseX, double mouseY) {
        double step = (float) 360 / map.size();

        double angle = getMouseAngle(x, y, mouseX, mouseY) % 360;
        if (angle < 0D) {
            angle += 360D;
        }

        for (int i = 1; i <= map.size(); i += 1) {
            if ((((i-1) * step) <= angle) && (((i * step)) > angle)) {
                return i - 1;
            }
        }
        return -1;
    }

    public int getSelectedMonogramWithShift(int x, int y, double mouseX, double mouseY) {
        double step = (float) 360 / map.size() + 1;

        double angle = getMouseAngle(x, y, mouseX, mouseY) % 360;
        if (angle < 0D) {
            angle += 360D;
        }

        for (int i = 1; i <= map.size() + 1; i += 1) {
            if ((((i - 1) * step) <= angle) && (((i * step)) > angle)) {
                if (i >= map.size()) {
                    return 0;
                }
                return i;
            }
        }
        return -1;
    }

    public void setActives() {
        int i = 0;
        for (MonogramMapEntry monogramMapEntry : map) {
            monogramMapEntry.setActive(false);
            for (MonogramRecipe recipe : MonogramHandler.getRecipes().values()) {
                boolean set = false;
                int ii = (i + 1) % map.size();
                if (ii <= -1) {
                    ii = map.size() - 1;
                }

                if (recipe.getInputs().contains(map.get(i).getMonogram())) {
                    if (recipe.getOutput() == map.get(ii).getMonogram()) {
                        set = true;
                    }
                }
                if (recipe.getInputs().contains(map.get(ii).getMonogram()) && recipe.getInputs().contains(monogramMapEntry.getMonogram())) {
                    set = true;
                }
                MonogramRecipe addRecipe = MonogramHandler.getRecipe(monogramMapEntry.getMonogram().getId());
                if (addRecipe != null) {
                    if (addRecipe.getInputs().contains(map.get(ii).getMonogram())) {
                        set = true;
                    }
                }

                if (monogramMapEntry.getMonogram() == map.get(ii).getMonogram()) {
                    set = false;
                }

                if (set) {
                    monogramMapEntry.setActive(true);
                    break;
                }
            }
            i++;
            i = i % map.size();
        }
    }

    public boolean isCanConnect(Monogram monogram) {
        boolean set = false;
        for (MonogramRecipe recipe : MonogramHandler.getRecipes().values()) {
            set = false;

            if (recipe.getInputs().contains(currentMonogram)) {
                if (recipe.getOutput() == monogram) {
                    set = true;
                }
            }
            if (recipe.getInputs().contains(monogram) && recipe.getInputs().contains(currentMonogram)) {
                set = true;
            }
            MonogramRecipe addRecipe = MonogramHandler.getRecipe(currentMonogram.getId());
            if (addRecipe != null) {
                if (MonogramHandler.getRecipe(currentMonogram.getId()).getInputs().contains(monogram)) {
                    set = true;
                }
            }

            if (currentMonogram == monogram) {
                set = false;
            }

            if (set) {
                break;
            }
        }
        return set;
    }

    public int getAllActives() {
        int i = 0;
        for (MonogramMapEntry monogramMapEntry : map) {
            if (monogramMapEntry.isActive()) {
                i++;
            }
        }

        if (i < map.size()) {
            i = 0;
        }

        return i;
    }

    public String getHardmodeText() {
        return "wizards_reborn.arcanemicon.hardmode_research";
    }
}