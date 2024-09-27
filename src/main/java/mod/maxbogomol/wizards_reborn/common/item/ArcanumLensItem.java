package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtil;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.network.item.ArcanumLensBurstPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Random;

public class ArcanumLensItem extends ArcanumItem implements IGuiParticleItem {

    private static Random random = new Random();

    public ArcanumLensItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            int wissen = random.nextInt(2000, 3000);

            List<ItemStack> items = WissenUtil.getWissenItems(player);
            List<ItemStack> itemsOff = WissenUtil.getWissenItemsOff(items);
            items.removeAll(itemsOff);

            for (ItemStack item : items) {
                if (item.getItem() instanceof IWissenItem wissenItem) {
                    WissenItemUtil.existWissen(item);
                    int itemWissenRemain = WissenItemUtil.getAddWissenRemain(item, wissen, wissenItem.getMaxWissen());
                    if (wissen - itemWissenRemain > 0) {
                        WissenItemUtil.addWissen(item, wissen - itemWissenRemain, wissenItem.getMaxWissen());
                        wissen = wissen - itemWissenRemain;
                    }
                }
            }

            if (!player.isCreative()) {
                stack.shrink(1);
            }

            PacketHandler.sendToTracking(level, player.getOnPos(), new ArcanumLensBurstPacket(player.position().add(0, player.getBbHeight() / 2, 0)));
            level.playSound(WizardsReborn.proxy.getPlayer(), player.blockPosition(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.PLAYERS, 0.5f, (float) (1.3f + ((random.nextFloat() - 0.5D) / 2)));
            level.playSound(WizardsReborn.proxy.getPlayer(), player.blockPosition(), WizardsRebornSounds.CRYSTAL_BREAK.get(), SoundSource.PLAYERS, 1f, (float) (1.0f + ((random.nextFloat() - 0.5D) / 4)));
        }

        player.getCooldowns().addCooldown(this, 50);

        return InteractionResultHolder.success(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick());

        for (int i = 0; i < 45; i++) {
            poseStack.pushPose();
            float offset = (float) (Math.abs(Math.sin(Math.toRadians(i * 4 + (ticks * 2f)))));
            offset = (offset - 0.25f) * (1 / 0.75f);
            if (offset < 0) offset = 0;
            poseStack.translate(x + 8 + (Math.sin(Math.toRadians(i * 8)) * 8), y + 8 + (Math.cos(Math.toRadians(i * 8)) * 2), 100 + (100 * Math.cos(Math.toRadians(i * 8))));
            poseStack.mulPose(Axis.ZP.rotationDegrees(i * 2 + (ticks * 2f)));
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                    .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/sparkle"))
                    .setColorRaw(Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB()).setAlpha(0.2f)
                    .renderCenteredQuad(poseStack, 3f * offset)
                    .endBatch();
            poseStack.popPose();
        }
    }
}
