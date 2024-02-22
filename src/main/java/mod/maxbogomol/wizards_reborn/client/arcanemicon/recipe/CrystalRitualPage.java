package mod.maxbogomol.wizards_reborn.client.arcanemicon.recipe;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitual;
import mod.maxbogomol.wizards_reborn.api.crystalritual.CrystalRitualUtils;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CrystalRitualPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/crystal_ritual_page.png");
    public CrystalRitual crystalRitual;
    public ItemStack[] inputs;

    public CrystalRitualPage(CrystalRitual crystalRitual, ItemStack... inputs) {
        super(BACKGROUND);
        this.crystalRitual = crystalRitual;
        this.inputs = inputs;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        int index = 1;
        double angleBetweenEach = 360.0 / (inputs.length);
        Vec2 point = new Vec2(56, 22), center = new Vec2(56, 57);

        ItemStack item = new ItemStack(WizardsReborn.RUNIC_WISESTONE_PLATE.get());
        CrystalRitualUtils.setCrystalRitual(item, crystalRitual);
        drawItem(book, gui, item,x + 56, y + 56, mouseX, mouseY);

        for (ItemStack o : inputs) {
            drawItem(book, gui, o,(int) point.x + x, (int) point.y + y, mouseX, mouseY);
            index += 1;
            point = rotatePointAbout(point, center, angleBetweenEach);
        }
    }

    public static Vec2 rotatePointAbout(Vec2 in, Vec2 about, double degrees) {
        double rad = degrees * Math.PI / 180.0;
        double newX = Math.cos(rad) * (in.x - about.x) - Math.sin(rad) * (in.y - about.y) + about.x;
        double newY = Math.sin(rad) * (in.x - about.x) + Math.cos(rad) * (in.y - about.y) + about.y;
        return new Vec2((float) newX, (float) newY);
    }
}