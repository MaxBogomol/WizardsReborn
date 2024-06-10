package mod.maxbogomol.wizards_reborn.common.item.equipment.curio;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.event.ClientTickHandler;
import mod.maxbogomol.wizards_reborn.utils.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class LeatherCollarItem extends BaseCurioItem implements ICurioItemTexture {

    private static final ResourceLocation COLLAR_TEXTURE = new ResourceLocation(WizardsReborn.MOD_ID,"textures/entity/curio/leather_collar.png");

    public LeatherCollarItem(Properties properties) {
        super(properties);
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

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> list, TooltipFlag flags) {
        super.appendHoverText(stack, world, list, flags);

        String name1 = "lore.wizards_reborn.leather_collar.0";
        String name2 = "lore.wizards_reborn.leather_collar.1";

        double ticks = (ClientTickHandler.ticksInGame + Minecraft.getInstance().getPartialTick()) * 0.05f;
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
            int r = (int) (Math.sin(ticks) * 127 + 128);
            int g = (int) (Math.sin(ticks + Math.PI / 2) * 127 + 128);
            int b = (int) (Math.sin(ticks + Math.PI) * 127 + 128);

            component.append(Component.literal(String.valueOf(c)).withStyle(Style.EMPTY.withColor(ColorUtils.packColor(255, r, g, b))));
        }

        list.add(component);
    }

    @Override
    public ResourceLocation getTexture(ItemStack stack, LivingEntity entity) {
        return COLLAR_TEXTURE;
    }
}
