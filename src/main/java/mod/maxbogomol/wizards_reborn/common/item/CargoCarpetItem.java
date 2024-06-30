package mod.maxbogomol.wizards_reborn.common.item;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.entity.SniffaloEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class CargoCarpetItem extends Item {

    public enum Colors {
        CARPET(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/carpet.png")),
        WHITE(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/white_carpet.png")),
        LIGHT_GRAY(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/light_gray_carpet.png")),
        GRAY(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/gray_carpet.png")),
        BLACK(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/black_carpet.png")),
        BROWN(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/brown_carpet.png")),
        RED(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/red_carpet.png")),
        ORANGE(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/orange_carpet.png")),
        YELLOW(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/yellow_carpet.png")),
        LIME(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/lime_carpet.png")),
        GREEN(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/green_carpet.png")),
        CYAN(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/cyan_carpet.png")),
        LIGHT_BLUE(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/light_blue_carpet.png")),
        BLUE(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/blue_carpet.png")),
        PURPLE(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/purple_carpet.png")),
        MAGENTA(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/magenta_carpet.png")),
        PINK(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/pink_carpet.png")),
        RAINBOW(new ResourceLocation(WizardsReborn.MOD_ID, "textures/entity/sniffalo/carpet/rainbow_carpet.png"));

        public final ResourceLocation texture;

        private Colors(ResourceLocation texture) {
            this.texture = texture;
        }

        public ResourceLocation getTexture() {
            return texture;
        }
    }

    public Colors color;

    public CargoCarpetItem(Colors color, Properties properties) {
        super(properties);
        this.color = color;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hnd) {
        if (target instanceof SniffaloEntity sniffalo && target.isAlive()) {
            if (sniffalo.isSaddled()) {
                sniffalo.setCarpet(stack.copy());
                target.level().gameEvent(target, GameEvent.EQUIP, target.position());
                stack.shrink(1);

                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
        }

        return InteractionResult.PASS;
    }

    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getCarpetTexture(ItemStack stack, SniffaloEntity entity) {
        return color.getTexture();
    }
}
