package mod.maxbogomol.wizards_reborn.client.event;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;

import java.awt.*;
import java.util.Random;

public class ClientWorldEvent {

    private static Random random = new Random();

    @OnlyIn(Dist.CLIENT)
    public static void onTick(TickEvent.LevelTickEvent event) {
        Minecraft mc = Minecraft.getInstance();

        Player player = mc.player;
        if (player != null) {
            ItemStack main = player.getMainHandItem();
            ItemStack offhand = player.getOffhandItem();
            boolean renderWand = false;
            ItemStack stack = main;

            if (!main.isEmpty() && main.getItem() instanceof WissenWandItem) {
                renderWand = true;
            } else {
                if (!offhand.isEmpty() && offhand.getItem() instanceof WissenWandItem) {
                    renderWand = true;
                    stack = offhand;
                }
            }

            CompoundTag nbt = stack.getTag();
            if (nbt == null) {
                nbt = new CompoundTag();
            }

            if (nbt.contains("block") && nbt.contains("mode") && renderWand) {
                if (nbt.getBoolean("block")) {
                    if (nbt.getInt("mode") == 1 || nbt.getInt("mode") == 2) {
                        BlockPos blockPos = new BlockPos(nbt.getInt("blockX"), nbt.getInt("blockY"), nbt.getInt("blockZ"));

                        if (player.level().getBlockEntity(blockPos) instanceof IWissenTileEntity) {
                            connectBlockEffect(blockPos, player.level(), new Color(255, 255, 255));
                        }
                    }
                }
            }
        }
    }

    public static void connectBlockEffect(BlockPos pos, Level world, Color color) {
        float colorR = (color.getRed() / 255f);
        float colorG = (color.getGreen() / 255f);
        float colorB = (color.getBlue() / 255f);

        int particlePerBlock = 1;

        for (int i = 0; i < particlePerBlock; i++) {
            Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                    .addVelocity(((random.nextDouble() - 0.5D) / 100), ((random.nextDouble() - 0.5D) / 100), ((random.nextDouble() - 0.5D) / 100))
                    .setAlpha(0.25f, 0f).setScale(0.1f, 0f)
                    .setColor(colorR, colorG, colorB, colorR, colorG, colorB)
                    .setLifetime(5)
                    .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                    .spawn(world, pos.getX() + 0.5F + (random.nextDouble() - 0.5D), pos.getY() + 0.5F + (random.nextDouble() - 0.5D), pos.getZ() + 0.5F + (random.nextDouble() - 0.5D));
        }
    }
}
