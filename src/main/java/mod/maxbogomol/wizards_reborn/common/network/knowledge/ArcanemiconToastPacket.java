package mod.maxbogomol.wizards_reborn.common.network.knowledge;

import mod.maxbogomol.fluffy_fur.common.network.ClientPacket;
import mod.maxbogomol.wizards_reborn.client.toast.ArcanemiconToast;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class ArcanemiconToastPacket extends ClientPacket {

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Minecraft.getInstance().getToasts().addToast(new ArcanemiconToast());
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ArcanemiconToastPacket.class, ArcanemiconToastPacket::encode, ArcanemiconToastPacket::decode, ArcanemiconToastPacket::handle);
    }

    public static ArcanemiconToastPacket decode(FriendlyByteBuf buf) {
        return new ArcanemiconToastPacket();
    }
}