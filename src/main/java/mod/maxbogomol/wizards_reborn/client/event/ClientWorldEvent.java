package mod.maxbogomol.wizards_reborn.client.event;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.light.ILightTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenTileEntity;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.item.equipment.WissenWandItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
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

                        if (canEffect(blockPos, player.level())) {
                            WissenUtils.connectBlockEffect(player.level(), blockPos, new Color(255, 255, 255), 1);
                        }
                    }
                }
            }
        }
    }

    public static boolean canEffect(BlockPos pos, Level world) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof IWissenTileEntity) return true;
        if (blockEntity instanceof ILightTileEntity) return true;

        return false;
    }
}
