package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.arcanemicon.ArcanemiconGui;
import net.minecraft.client.Minecraft;
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

import java.awt.*;
import java.util.List;
import java.util.Random;

public class ArcanemiconItem extends Item {

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
        Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
        Minecraft.getInstance().setScreen(new ArcanemiconGui());
    }

    @OnlyIn(Dist.CLIENT)
    public void openGui(Level level, Vec3 pos, ItemStack stack) {
        Minecraft.getInstance().player.playNotifySound(SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0f, 1.0f);
        ArcanemiconGui gui = new ArcanemiconGui();
        gui.blockPos = BlockPos.containing(pos);
        gui.isBLock = true;
        Minecraft.getInstance().setScreen(gui);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        list.add(getEditionComponent());
    }

    @OnlyIn(Dist.CLIENT)
    public static Component getEditionComponent() {
        int edition = WizardsReborn.VERSION_NUMBER;
        Random random = new Random(edition);
        Color color1 = new Color(random.nextInt(0, 255), random.nextInt(0, 255), random.nextInt(0, 255));
        Color color2 = new Color(random.nextInt(0, 255), random.nextInt(0, 255), random.nextInt(0, 255));
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
}
