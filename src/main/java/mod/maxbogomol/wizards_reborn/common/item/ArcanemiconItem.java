package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconScreen;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.item.ArcanemiconSoundPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.TickEvent;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class ArcanemiconItem extends Item {
    public static boolean isLoreShow = false;
    public static int loreTicks = 0;

    public ArcanemiconItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        player.awardStat(Stats.ITEM_USED.get(this));

        if (level.isClientSide()) {
            openGui(level, stack);
        }

        return InteractionResultHolder.success(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(Level level, ItemStack stack) {
        WizardsRebornPacketHandler.sendToServer(new ArcanemiconSoundPacket(WizardsReborn.proxy.getPlayer().position()));
        Minecraft.getInstance().setScreen(new ArcanemiconScreen());
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(Level level, Vec3 pos, ItemStack stack) {
        WizardsRebornPacketHandler.sendToServer(new ArcanemiconSoundPacket(pos));
        ArcanemiconScreen gui = new ArcanemiconScreen();
        gui.blockPos = BlockPos.containing(pos);
        gui.isBLock = true;
        Minecraft.getInstance().setScreen(gui);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        list.add(getLoreComponent());
        list.add(Component.empty());
        list.add(getEditionComponent());
        isLoreShow = true;
    }

    @OnlyIn(Dist.CLIENT)
    public static Component getLoreComponent() {
        MutableComponent component = Component.translatable("lore.wizards_reborn.arcanemicon.name").withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY);
        int ticks = loreTicks - 20;

        if (ticks > 0) {
            String string = " - " + Component.translatable("lore.wizards_reborn.arcanemicon").getString();
            if (ticks > string.length()) ticks = string.length();

            for (int i = 0; i < ticks; i++) {
                char c = string.toCharArray()[i];
                if (i == loreTicks - 21) {
                    component.append(Component.literal(String.valueOf(c)).withStyle(ChatFormatting.STRIKETHROUGH).withStyle(ChatFormatting.DARK_GRAY));
                } else {
                    component.append(Component.literal(String.valueOf(c)).withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY));
                }
            }
        }

        return component;
    }

    @OnlyIn(Dist.CLIENT)
    public static Component getEditionComponent() {
        int edition = WizardsReborn.VERSION_NUMBER;
        Random random = new Random(edition);
        Color color1 = new Color(random.nextInt(0, 256), random.nextInt(0, 256), random.nextInt(0, 256));
        Color color2 = new Color(random.nextInt(0, 256), random.nextInt(0, 256), random.nextInt(0, 256));
        float ticks = ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick() * 5f;
        MutableComponent component = Component.empty();

        int i = 0;
        String string = Component.translatable("wizards_reborn.arcanemicon.edition", edition).getString();
        for (char c : string.toCharArray()) {
            float ii = (float) Math.abs(Math.sin(Math.toRadians(-ticks + i * 10)));
            int red = (int) Mth.lerp(ii, color1.getRed(), color2.getRed());
            int green = (int) Mth.lerp(ii, color1.getGreen(), color2.getGreen());
            int blue = (int) Mth.lerp(ii, color1.getBlue(), color2.getBlue());

            component.append(Component.literal(String.valueOf(c)).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, red, green, blue))));
            i++;
        }

        return component;
    }

    @OnlyIn(Dist.CLIENT)
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!Minecraft.getInstance().isPaused()) {
                if (isLoreShow) {
                    String string = I18n.get("lore.wizards_reborn.arcanemicon");
                    if (loreTicks < string.length() + 40) {
                        loreTicks++;
                        if (Minecraft.getInstance().player != null && loreTicks > 20 && loreTicks - 20 <= string.length() + 3) {
                            Minecraft.getInstance().player.playNotifySound(SoundEvents.UI_BUTTON_CLICK.get(), SoundSource.NEUTRAL, 0.1f, 2.0f);
                        }
                    }
                } else {
                    if (loreTicks > 0) {
                        loreTicks--;
                    }
                }
                isLoreShow = false;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void onTooltipRenderColor(RenderTooltipEvent.Color event) {
        if (event.getItemStack().getItem() instanceof ArcanemiconItem item) {
            int edition = WizardsReborn.VERSION_NUMBER;
            Random random = new Random(edition);
            Color color1 = new Color(random.nextInt(0, 256), random.nextInt(0, 256), random.nextInt(0, 256));
            Color color2 = new Color(random.nextInt(0, 256), random.nextInt(0, 256), random.nextInt(0, 256));
            int packColorStart = ColorUtil.packColor(color1);
            int packColorEnd = ColorUtil.packColor(color2);
            event.setBorderStart(packColorStart);
            event.setBorderEnd(packColorEnd);
        }
    }
}
