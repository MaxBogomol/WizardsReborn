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

    public static CarpetColor CARPET = CarpetColor.create(WizardsReborn.MOD_ID, "carpet");
    public static CarpetColor WHITE = CarpetColor.create(WizardsReborn.MOD_ID, "white_carpet");
    public static CarpetColor LIGHT_GRAY = CarpetColor.create(WizardsReborn.MOD_ID, "light_gray_carpet");
    public static CarpetColor GRAY = CarpetColor.create(WizardsReborn.MOD_ID, "gray_carpet");
    public static CarpetColor BLACK = CarpetColor.create(WizardsReborn.MOD_ID, "black_carpet");
    public static CarpetColor BROWN = CarpetColor.create(WizardsReborn.MOD_ID, "brown_carpet");
    public static CarpetColor RED = CarpetColor.create(WizardsReborn.MOD_ID, "red_carpet");
    public static CarpetColor ORANGE = CarpetColor.create(WizardsReborn.MOD_ID, "orange_carpet");
    public static CarpetColor YELLOW = CarpetColor.create(WizardsReborn.MOD_ID, "yellow_carpet");
    public static CarpetColor LIME = CarpetColor.create(WizardsReborn.MOD_ID, "lime_carpet");
    public static CarpetColor GREEN = CarpetColor.create(WizardsReborn.MOD_ID, "green_carpet");
    public static CarpetColor CYAN = CarpetColor.create(WizardsReborn.MOD_ID, "cyan_carpet");
    public static CarpetColor LIGHT_BLUE = CarpetColor.create(WizardsReborn.MOD_ID, "light_blue_carpet");
    public static CarpetColor BLUE = CarpetColor.create(WizardsReborn.MOD_ID, "blue_carpet");
    public static CarpetColor PURPLE = CarpetColor.create(WizardsReborn.MOD_ID, "purple_carpet");
    public static CarpetColor MAGENTA = CarpetColor.create(WizardsReborn.MOD_ID, "magenta_carpet");
    public static CarpetColor PINK = CarpetColor.create(WizardsReborn.MOD_ID, "pink_carpet");
    public static CarpetColor RAINBOW = CarpetColor.create(WizardsReborn.MOD_ID, "rainbow_carpet");

    public CarpetColor color;

    public CargoCarpetItem(CarpetColor color, Properties properties) {
        super(properties);
        this.color = color;
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
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

    public static class CarpetColor {
        public ResourceLocation texture;
        
        public CarpetColor(ResourceLocation texture) {
            this.texture = texture;
        }

        @OnlyIn(Dist.CLIENT)
        public ResourceLocation getTexture() {
            return texture;
        }

        public static CarpetColor create(String modId, String carpet) {
            return new CarpetColor(new ResourceLocation(modId, "textures/entity/sniffalo/carpet/" + carpet + ".png"));
        }
    }
}
