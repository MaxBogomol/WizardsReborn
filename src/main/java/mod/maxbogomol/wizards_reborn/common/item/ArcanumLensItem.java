package mod.maxbogomol.wizards_reborn.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.wissen.IWissenItem;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenItemUtil;
import mod.maxbogomol.wizards_reborn.api.wissen.WissenUtils;
import mod.maxbogomol.wizards_reborn.common.config.Config;
import mod.maxbogomol.wizards_reborn.common.network.ArcanumLensBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSounds;
import mod.maxbogomol.wizards_reborn.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
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
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide()) {
            int wissen = random.nextInt(2000, 3000);

            List<ItemStack> items = WissenUtils.getWissenItems(player);
            List<ItemStack> itemsOff = WissenUtils.getWissenItemsOff(items);
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
                stack.setCount(stack.getCount() - 1);
            }

            PacketHandler.sendToTracking(world, player.getOnPos(), new ArcanumLensBurstEffectPacket((float) player.getX(), (float) player.getY() + (player.getBbHeight() / 2), (float) player.getZ()));
            world.playSound(WizardsReborn.proxy.getPlayer(), player.blockPosition(), WizardsRebornSounds.WISSEN_BURST.get(), SoundSource.PLAYERS, 0.5f, (float) (1.3f + ((random.nextFloat() - 0.5D) / 2)));
            world.playSound(WizardsReborn.proxy.getPlayer(), player.blockPosition(), WizardsRebornSounds.CRYSTAL_BREAK.get(), SoundSource.PLAYERS, 1f, (float) (1.0f + ((random.nextFloat() - 0.5D) / 4)));
        }

        player.getCooldowns().addCooldown(this, 50);

        return InteractionResultHolder.success(stack);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack pose, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick());

        RenderUtils.startGuiParticle();
        MultiBufferSource.BufferSource buffersource = Minecraft.getInstance().renderBuffers().bufferSource();

        TextureAtlasSprite sparkle = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(new ResourceLocation(FluffyFur.MOD_ID, "particle/sparkle"));

        for (int i = 0; i < 45; i++) {
            pose.pushPose();
            float offset = (float) (Math.abs(Math.sin(Math.toRadians(i * 4 + (ticks * 2f)))));
            offset = (offset - 0.25f) * (1 / 0.75f);
            if (offset < 0) offset = 0;
            pose.translate(x + 8 + (Math.sin(Math.toRadians(i * 8)) * 8), y + 8 + (Math.cos(Math.toRadians(i * 8)) * 2), 100 + (100 * Math.cos(Math.toRadians(i * 8))));
            pose.mulPose(Axis.ZP.rotationDegrees(i * 2 + (ticks * 2f)));
            RenderUtils.spriteGlowQuadCenter(pose, buffersource, 0, 0, 6f * offset, 6f * offset, sparkle.getU0(), sparkle.getU1(), sparkle.getV0(), sparkle.getV1(), Config.wissenColorR(), Config.wissenColorG(), Config.wissenColorB(), 0.2F);
            buffersource.endBatch();
            pose.popPose();
        }

        RenderUtils.endGuiParticle();
    }
}
