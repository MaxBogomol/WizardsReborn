package mod.maxbogomol.wizards_reborn.client.arcanemicon.page;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.Page;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.index.ConfigIndexEntry;
import mod.maxbogomol.wizards_reborn.client.config.ClientConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec2;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ConfigPage extends Page {
    public static final ResourceLocation BACKGROUND = new ResourceLocation(WizardsReborn.MOD_ID, "textures/gui/arcanemicon/config_page.png");
    ConfigIndexEntry[] entries;

    public ConfigPage(ConfigIndexEntry... pages) {
        super(BACKGROUND);
        this.entries = pages;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean click(ArcanemiconGui gui, int x, int y, int mouseX, int mouseY) {
        for (int i = 0; i < entries.length; i ++) if (entries[i].isUnlocked()) {
            if ((mouseX >= x + 2 && mouseX <= x + 124 && mouseY >= y + 8 + i * 24 && mouseY <= y + 18 + i * 24) ||
                    (mouseX >= x + 2 && mouseX <= x + 96 && mouseY >= y + 8 + i * 24 && mouseY <= y + 28 + i * 24)) {
                if (entries[i].isBoolean()) {
                    entries[i].booleanConfig.set(!entries[i].booleanConfig.get());
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }

            int defaultOffset = 118;

            if (entries[i].isBoolean()) {
                defaultOffset = defaultOffset - 10;
                if (mouseX >= x + 118 && mouseX <= x + 128 && mouseY >= y + 17 + i * 24 && mouseY <= y + 27 + i * 24) {
                    entries[i].booleanConfig.set(!entries[i].booleanConfig.get());
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }

            if (entries[i].isInteger()) {
                defaultOffset = defaultOffset - 20;
                if (mouseX >= x + 108 && mouseX <= x + 118 && mouseY >= y + 17 + i * 24 && mouseY <= y + 27 + i * 24) {
                    entries[i].integerConfig.set(entries[i].integerConfig.get() - 1);
                    normalizeInteger(entries[i].integerConfig, getIntegerRange(entries[i].spec, entries[i].integerConfig));
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
                if (mouseX >= x + 118 && mouseX <= x + 128 && mouseY >= y + 17 + i * 24 && mouseY <= y + 27 + i * 24) {
                    entries[i].integerConfig.set(entries[i].integerConfig.get() + 1);
                    normalizeInteger(entries[i].integerConfig, getIntegerRange(entries[i].spec, entries[i].integerConfig));
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                    return true;
                }
            }

            if (mouseX >= x + defaultOffset && mouseX <= x + defaultOffset + 10 && mouseY >= y + 17 + i * 24 && mouseY <= y + 27 + i * 24) {
                if (entries[i].isBoolean()) {
                    entries[i].booleanConfig.set(entries[i].booleanConfig.getDefault());
                }
                if (entries[i].isInteger()) {
                    entries[i].integerConfig.set(entries[i].integerConfig.getDefault());
                }
                Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
                return true;
            }
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean mouseScrolled(ArcanemiconGui book, int x, int y, int mouseX, int mouseY, int delta) {
        for (int i = 0; i < entries.length; i ++) if (entries[i].isUnlocked()) {
            if (entries[i].isInteger()) {
                if ((mouseX >= x + 2 && mouseX <= x + 124 && mouseY >= y + 8 + i * 24 && mouseY <= y + 18 + i * 24) ||
                        (mouseX >= x + 2 && mouseX <= x + 96 && mouseY >= y + 8 + i * 24 && mouseY <= y + 28 + i * 24)) {
                    entries[i].integerConfig.set(entries[i].integerConfig.get() + delta);
                    normalizeInteger(entries[i].integerConfig, getIntegerRange(entries[i].spec, entries[i].integerConfig));
                    Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 0.2f, 2.0f);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void render(ArcanemiconGui book, GuiGraphics gui, int x, int y, int mouseX, int mouseY) {
        for (int i = 0; i < entries.length; i ++) {
            gui.blit(BACKGROUND, x, y + 7 + (i * 24), 128, 20, 128, 20);
        }

        for (int i = 0; i < entries.length; i ++) {
            if (entries[i].isUnlocked()) {
                List<Component> comments = new ArrayList<>();
                Component name = Component.empty();
                String value = "";
                boolean changed = false;

                int defaultOffset = 118;

                if (entries[i].isBoolean()) {
                    List<String> path = entries[i].booleanConfig.getPath();
                    name = getName(entries[i].modId, path);
                    value = I18n.get("config.number.wizards_reborn.on");
                    if (!entries[i].booleanConfig.get()) value = I18n.get("config.number.wizards_reborn.off");
                    changed = entries[i].booleanConfig.get() != entries[i].booleanConfig.getDefault();
                    if (changed) name = name.copy().withStyle(ChatFormatting.ITALIC);
                    comments.add(name);
                    comments.addAll(getComments(entries[i].spec, entries[i].modId, path));
                    defaultOffset = defaultOffset - 10;

                    String defaultValue = I18n.get("config.number.wizards_reborn.on");
                    if (!entries[i].booleanConfig.getDefault()) defaultValue = I18n.get("config.number.wizards_reborn.off");
                    comments.add(Component.translatable("config.number.wizards_reborn.default").append(Component.literal(" ").append(Component.translatable(defaultValue))).withStyle(ChatFormatting.GRAY));

                    gui.blit(BACKGROUND, x + 118, y + 17 + (i * 24), 138, 40, 10, 10);
                    if (mouseX >= x + 118 && mouseX <= x + 128 && mouseY >= y + 17 + i * 24 && mouseY <= y + 27 + i * 24) {
                        gui.blit(BACKGROUND, x + 118, y + 17 + (i * 24), 138, 50, 10, 10);
                    }
                }

                if (entries[i].isInteger()) {
                    List<String> path = entries[i].integerConfig.getPath();
                    name = getName(entries[i].modId, path);
                    value = String.valueOf(entries[i].integerConfig.get());
                    changed = !Objects.equals(entries[i].integerConfig.get(), entries[i].integerConfig.getDefault());
                    if (changed) name = name.copy().withStyle(ChatFormatting.ITALIC);
                    comments.add(name);
                    comments.addAll(getComments(entries[i].spec, entries[i].modId, path));
                    defaultOffset = defaultOffset - 20;

                    Vec2 range = getIntegerRange(entries[i].spec, entries[i].integerConfig);
                    if (range != null) comments.add(Component.translatable("config.number.wizards_reborn.range")
                            .append(Component.literal(" " + String.valueOf((int) range.x) + " ~ " + String.valueOf((int) range.y))).withStyle(ChatFormatting.GRAY));
                    comments.add(Component.translatable("config.number.wizards_reborn.default").append(Component.literal(" " + String.valueOf((int) entries[i].integerConfig.getDefault()))).withStyle(ChatFormatting.GRAY));

                    gui.blit(BACKGROUND, x + 108, y + 17 + (i * 24), 148, 40, 10, 10);
                    if (mouseX >= x + 108 && mouseX <= x + 118 && mouseY >= y + 17 + i * 24 && mouseY <= y + 27 + i * 24) {
                        gui.blit(BACKGROUND, x + 108, y + 17 + (i * 24), 148, 50, 10, 10);
                    }
                    gui.blit(BACKGROUND, x + 118, y + 17 + (i * 24), 158, 40, 10, 10);
                    if (mouseX >= x + 118 && mouseX <= x + 128 && mouseY >= y + 17 + i * 24 && mouseY <= y + 27 + i * 24) {
                        gui.blit(BACKGROUND, x + 118, y + 17 + (i * 24), 158, 50, 10, 10);
                    }
                }

                gui.blit(BACKGROUND, x + defaultOffset, y + 17 + (i * 24), 128, 40, 10, 10);
                if (mouseX >= x + defaultOffset && mouseX <= x + defaultOffset + 10 && mouseY >= y + 17 + i * 24 && mouseY <= y + 27 + i * 24) {
                    gui.blit(BACKGROUND, x + defaultOffset, y + 17 + (i * 24), 128, 50, 10, 10);
                }

                drawText(book, gui, getFormatText(changed, name), x + 4, y + 16 + i * 24 - Minecraft.getInstance().font.lineHeight);

                if (ClientConfig.CONFIG_CENTER.get()) {
                    String title = value;
                    int titleWidth = Minecraft.getInstance().font.width(title);
                    drawText(book, gui, value, x + 48 - titleWidth / 2, y + 26 + i * 24 - Minecraft.getInstance().font.lineHeight);
                } else {
                    drawText(book, gui, value, x + 2, y + 26 + i * 24 - Minecraft.getInstance().font.lineHeight);
                }

                if ((mouseX >= x + 2 && mouseX <= x + 124 && mouseY >= y + 8 + i * 24 && mouseY < y + 18 + i * 24) ||
                        (mouseX >= x + 2 && mouseX <= x + 96 && mouseY >= y + 8 + i * 24 && mouseY <= y + 28 + i * 24)) {
                    gui.renderTooltip(Minecraft.getInstance().font, comments, Optional.empty(), mouseX, mouseY);
                }
            }
        }
    }

    public static String getNameString(String modId, List<String> path) {
        String string = "";
        for (String str : path) {
            string = string + "." + str;
        }
        return "config."  + modId + string;
    }

    public static String getDescNameString(String modId, List<String> path) {
        return getNameString(modId, path) + ".desc";
    }

    public static Component getName(String modId, List<String> path) {
        if (!I18n.exists(getNameString(modId, path))) {
            return Component.literal(path.get(path.size() - 1));
        }
        return Component.translatable(getNameString(modId, path));
    }

    public static Component getDescName(String modId, List<String> path) {
        return Component.translatable(getDescNameString(modId, path));
    }

    public static List<Component> getComments(Component component) {
        List<Component> comments = new ArrayList<>();
        String text = component.getString();
        String[] words = text.split(" ");
        String line = "";

        for (String s : words) {
            if (s.equals("\n")) {
                comments.add(Component.literal(line).withStyle(ChatFormatting.GRAY));
                line = "";
            }
            else line += s + " ";
        }
        if (!line.isEmpty()) comments.add(Component.literal(line).withStyle(ChatFormatting.GRAY));

        return comments;
    }

    public static List<Component> getComments(ForgeConfigSpec spec, String modId, List<String> path) {
        if (!I18n.exists(getDescNameString(modId, path))) {
            String comment = getComments(spec, path);
            if (comment != null) {
                return getComments(Component.literal(comment));
            }
        }
        return getComments(getDescName(modId, path));
    }

    public static String getFormatText(Boolean changed, Component component) {
        String text = component.getString();
        String formatText = "";
        if (changed) formatText = formatText + ChatFormatting.ITALIC.toString();

        int i = 0;
        for (char s : text.toCharArray()) {
            if (i > 22) break;
            if (i > 19) {
                formatText = formatText + ".";
            } else {
                formatText = formatText + s;
            }
            i++;
        }

        return formatText;
    }

    public static Vec2 getIntegerRange(ForgeConfigSpec spec, ForgeConfigSpec.ConfigValue<Integer> value) {
        if (spec != null) {
            Object range = ObfuscationReflectionHelper.getPrivateValue(ForgeConfigSpec.ValueSpec.class, spec.get(value.getPath()), "range");
            if (range != null) {
                Class rangeClass = null;
                try {
                    rangeClass = Class.forName("net.minecraftforge.common.ForgeConfigSpec$Range");
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                Object min = ObfuscationReflectionHelper.getPrivateValue(rangeClass, range, "min");
                Object max = ObfuscationReflectionHelper.getPrivateValue(rangeClass, range, "max");
                if (min instanceof Integer i && max instanceof Integer j) {
                    return new Vec2(i, j);
                }
            }
        }

        return null;
    }

    public static String getComments(ForgeConfigSpec spec, List<String> path) {
        if (spec != null) {
            Object comment = ObfuscationReflectionHelper.getPrivateValue(ForgeConfigSpec.ValueSpec.class, spec.get(path), "comment");
            if (comment != null) {
                return String.valueOf(comment);
            }
        }

        return null;
    }

    public static void normalizeInteger(ForgeConfigSpec.ConfigValue<Integer> value, Vec2 range) {
        if (range != null) {
            if (value.get() < range.x) value.set((int) range.x);
            if (value.get() > range.y) value.set((int) range.y);
        }
    }
}
