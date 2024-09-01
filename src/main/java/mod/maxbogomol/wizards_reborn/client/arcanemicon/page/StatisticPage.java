package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import com.google.common.collect.Lists;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeUtil;
import mod.maxbogomol.wizards_reborn.api.spell.Spell;
import mod.maxbogomol.wizards_reborn.api.spell.Spells;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class StatisticPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/title_page.png");
    public String text, title;
    public List<List<Knowledge>> knowledges;
    public int offset = 0;

    @SafeVarargs
    public StatisticPage(String textKey, List<Knowledge>... knowledges) {
        super(BACKGROUND);
        this.text = textKey;
        this.title = textKey + ".title";
        this.knowledges = Lists.newArrayList(knowledges);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        String title = I18n.get(this.title);
        int titleWidth = Minecraft.getInstance().font.width(title);
        drawText(book, gui, title, x + 64 - titleWidth / 2, y + 15 - Minecraft.getInstance().font.lineHeight);

        int points = KnowledgeUtil.getKnowledgePoints(Minecraft.getInstance().player) - KnowledgeUtil.getSpellPoints(Minecraft.getInstance().player);
        if (points < 0) points = 0;

        int currentKnowledges = 0;
        int totalKnowledges = 0;
        int currentPoints = 0;
        int totalPoints = 0;
        int currentSpells = 0;
        int totalSpells = 0;
        for (List<Knowledge> list : knowledges) {
            for (Knowledge knowledge : list) {
                if ((KnowledgeUtil.isKnowledge(Minecraft.getInstance().player, knowledge))) {
                    currentKnowledges++;
                    currentPoints += knowledge.getPoints();
                }
                totalKnowledges++;
                totalPoints += knowledge.getPoints();
            }
        }
        for (Spell spell : Spells.getSpells()) {
            if (KnowledgeUtil.isSpell(Minecraft.getInstance().player, spell)) {
                currentSpells++;
            }
            totalSpells++;
        }
        Player player = Minecraft.getInstance().player;

        String progressionText = String.valueOf(Math.round((float) currentKnowledges / totalKnowledges * 1000) / 10f) + "%";
        String knowledgeText = String.valueOf(currentKnowledges) + "/" + String.valueOf(totalKnowledges);
        String pointsText = String.valueOf(currentPoints) + "/" + String.valueOf(totalPoints) + " (" + String.valueOf(points) + ")";
        String spellsText = String.valueOf(currentSpells) + "/" + String.valueOf(totalSpells);
        String healthText = String.valueOf(Math.round((player.getHealth() + player.getAbsorptionAmount()) * 10) / 10f) + "/" + String.valueOf(Math.round(player.getMaxHealth() * 10) / 10f);
        String foodText = String.valueOf(Math.round(player.getFoodData().getFoodLevel() * 10) / 10f) + " (" + String.valueOf(Math.round(player.getFoodData().getSaturationLevel() * 10) / 10f) + ")";
        String armorText = String.valueOf(Math.round(player.getArmorValue() * 10) / 10f);
        String magicArmorText = String.valueOf(Math.round(player.getAttribute(WizardsRebornAttributes.MAGIC_ARMOR.get()).getValue() * 10) / 10f) + "%";
        String wissenDiscountText = String.valueOf(Math.round(WissenUtils.getWissenCostModifierWithDiscount(player) * 1000) / 10f) + "%";
        String experienceText = String.valueOf(player.experienceLevel) + " (" + String.valueOf(getPlayerXP(player)) + ")";

        String text = I18n.get(this.text, progressionText, knowledgeText, pointsText, spellsText, healthText, foodText, armorText, magicArmorText, wissenDiscountText, experienceText);

        drawWrappingText(book, gui, text, x + 4, y + 24, 124);
    }

    public static int getPlayerXP(Player player) {
        return (int)(getExperienceForLevel(player.experienceLevel) + (player.experienceProgress * player.getXpNeededForNextLevel()));
    }

    public static int getExperienceForLevel(int level) {
        if (level == 0) return 0;
        if (level <= 15) return sum(level, 7, 2);
        if (level <= 30) return 315 + sum(level - 15, 37, 5);
        return 1395 + sum(level - 30, 112, 9);
    }

    private static int sum(int n, int a0, int d) {
        return n * (2 * a0 + (n - 1) * d) / 2;
    }
}