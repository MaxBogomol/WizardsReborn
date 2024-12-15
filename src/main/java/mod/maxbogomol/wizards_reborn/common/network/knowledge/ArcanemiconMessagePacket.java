package mod.maxbogomol.wizards_reborn.common.network.knowledge;

import mod.maxbogomol.fluffy_fur.common.network.ClientPacket;
import mod.maxbogomol.fluffy_fur.util.ColorUtil;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ArcanemiconMessagePacket extends ClientPacket {

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Player player = WizardsReborn.proxy.getPlayer();
        player.sendSystemMessage(Component.literal("<").append(
                        Component.translatable("message.wizards_reborn.someone").withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 123, 73, 109))))
                .append(Component.literal("> "))
                .append(Component.translatable("message.wizards_reborn.arcanemicon").withStyle(Style.EMPTY.withColor(ColorUtil.packColor(255, 251, 179, 176)))));
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ArcanemiconMessagePacket.class, ArcanemiconMessagePacket::encode, ArcanemiconMessagePacket::decode, ArcanemiconMessagePacket::handle);
    }

    public static ArcanemiconMessagePacket decode(FriendlyByteBuf buf) {
        return new ArcanemiconMessagePacket();
    }
}
