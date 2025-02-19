package mod.maxbogomol.wizards_reborn.common.item.equipment.curio;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import mod.maxbogomol.fluffy_fur.FluffyFur;
import mod.maxbogomol.fluffy_fur.client.event.ClientTickHandler;
import mod.maxbogomol.fluffy_fur.client.render.RenderBuilder;
import mod.maxbogomol.fluffy_fur.common.item.IGuiParticleItem;
import mod.maxbogomol.fluffy_fur.integration.common.curios.BaseCurioItem;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurRenderTypes;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.fluffy_fur.util.RenderUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeatherCollarItem extends BaseCurioItem implements IGuiParticleItem {

    private static final ResourceLocation COLLAR_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID,"textures/entity/curio/leather_collar.png");

    public static Map<String, String> skins = new HashMap<>();
    public static Color yapingColor = new Color(DyeColor.PINK.getMapColor().col);

    public LeatherCollarItem(Properties properties) {
        super(properties);
        skins.put("MaxBogomol", "maxbogomol");
        skins.put("OnixTheCat", "onixthecat");
        skins.put("SammySemicolon", "sammysemicolon");
        skins.put("mlekpi", "mlekpi");
        skins.put("Purplik", "purplik");
        skins.put("Onjerlay", "onjerlay");
        skins.put("Kekqupap", "kekqupap");
        skins.put("unlogicalSamsar", "unlogicalsamsar");
        skins.put("Swaped_meow", "swaped_meow");
    }

    @Nonnull
    @Override
    public ICurio.SoundInfo getEquipSound(SlotContext slotContext, ItemStack stack) {
        return new ICurio.SoundInfo(SoundEvents.ARMOR_EQUIP_LEATHER, 1.0f, 1.0f);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        if (!slotContext.entity().level().isClientSide()) {
            if (slotContext.entity() instanceof Player player) {
                int interval = player.isCrouching() ? 20 : 1000;
                if (player.level().getGameTime() % interval == 0) {
                    SoundEvent soundEvent = player.getRandom().nextInt(8) == 0 ? SoundEvents.CAT_PURREOW : SoundEvents.CAT_PURR;
                    player.level().playSound(null, player.blockPosition(), soundEvent, SoundSource.PLAYERS, 1, 1);
                }
            }
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(stack, level, list, flags);

        String name1 = "lore.wizards_reborn.leather_collar.0";
        String name2 = "lore.wizards_reborn.leather_collar.1";
        String hearts = "lore.wizards_reborn.leather_collar.2";
        String yap = "lore.wizards_reborn.leather_collar.yaping";

        float ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.05f;
        float ticksF = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 2f;
        MutableComponent component = Component.empty();

        List<Character> chars = new ArrayList<>();
        String string1 = Component.translatable(name1).getString();
        String string2 = Component.translatable(name2).getString();

        int max = string1.length();
        if (string2.length() > max) max = string2.length();

        for (int i = 0; i < max; i++) {
            float ii = (float) Math.sin(Math.toRadians(-ticksF + i * 10));
            if (ii < 0) {
                if (string1.length() > i) chars.add(string1.charAt(i));
            } else {
                if (string2.length() > i) chars.add(string2.charAt(i));
            }
        }

        for (char c : chars) {
            component.append(Component.literal(String.valueOf(c)).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(ColorUtil.rainbowColor(ticks)))));
        }

        list.add(component);

        String skin = LeatherCollarItem.getSkin(stack);
        if (skin != null) {
            list.add(Component.literal(skin).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 255, 173, 186)))
                    .append(" ").append(Component.translatable(hearts).withStyle(ChatFormatting.RED)));

            Player player = WizardsReborn.proxy.getPlayer();
            if (player != null) {
                boolean yaping = false;
                String name = player.getGameProfile().getName();
                if (skin.equals("MaxBogomol") && name.equals("OnixTheCat")) {
                    yaping = true;
                } else if (skin.equals("OnixTheCat") && name.equals("MaxBogomol")) {
                    yaping = true;
                }
                if (skin.equals("SammySemicolon") && name.equals("mlekpi")) {
                    yaping = true;
                } else if (skin.equals("mlekpi") && name.equals("SammySemicolon")) {
                    yaping = true;
                }
                if (yaping) list.add(Component.translatable(yap).withStyle(Style.EMPTY.withColor(ColorUtil.packColor(yapingColor))));
            }
        }
    }

    public static String getSkin(ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            if (stack.getDisplayName().getString().contains("Dev")) return "MaxBogomol";
            for (String skin : skins.keySet()) {
                if (stack.getDisplayName().getString().contains(skin)) {
                    return skin;
                }
            }
        }

        return null;
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity) {
        String skin = LeatherCollarItem.getSkin(stack);
        if (skin != null) {
            return new ResourceLocation(WizardsReborn.MOD_ID,"textures/entity/curio/collar/" + skins.get(skin) + ".png");
        }
        return COLLAR_TEXTURE;
    }

    public static boolean isWearCollar(Player player) {
        LazyOptional<ICuriosItemHandler> curiosItemHandler = CuriosApi.getCuriosInventory(player);
        if (curiosItemHandler.isPresent() && curiosItemHandler.resolve().isPresent()) {
            List<SlotResult> curioSlots = curiosItemHandler.resolve().get().findCurios((i) -> i.getItem() instanceof LeatherCollarItem);
            return !curioSlots.isEmpty();
        }
        return false;
    }

    public static class CreeperAvoidPlayerGoal extends AvoidEntityGoal<Player> {
        public CreeperAvoidPlayerGoal(Creeper mob) {
            this(mob, 10.0F, 1.0, 1.2);
        }

        private CreeperAvoidPlayerGoal(Creeper mob, float maxDist, double walkSpeedModifier, double sprintSpeedModifier) {
            super(mob, Player.class, maxDist, walkSpeedModifier, sprintSpeedModifier);
        }

        @Override
        public boolean canUse() {
            if (super.canUse()) {
                if (this.toAvoid != null) {
                    return isWearCollar(this.toAvoid);
                }
            }

            return false;
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void renderParticle(PoseStack poseStack, LivingEntity entity, Level level, ItemStack stack, int x, int y, int seed, int guiOffset) {
        for (int i = 0; i < 5; i++) {
            float ticks = (ClientTickHandler.getTotal() + (i * 20)) * 0.05f;
            float angle = ClientTickHandler.getTotal() * 0.16f + (i * 8f);

            poseStack.pushPose();
            poseStack.translate(x + 8f, y + 8f, 100);
            poseStack.mulPose(Axis.ZP.rotationDegrees(angle));
            RenderBuilder.create().setRenderType(FluffyFurRenderTypes.ADDITIVE_TEXTURE)
                    .setUV(RenderUtil.getSprite(FluffyFur.MOD_ID, "particle/star"))
                    .setColor(ColorUtil.rainbowColor(ticks)).setAlpha(0.25f)
                    .renderCenteredQuad(poseStack, 4f + (8f * (i / 5f)))
                    .endBatch();
            poseStack.popPose();
        }
    }
}
