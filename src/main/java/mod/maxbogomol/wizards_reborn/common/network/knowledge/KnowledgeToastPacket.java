package mod.maxbogomol.wizards_reborn.common.network.knowledge;

import mod.maxbogomol.fluffy_fur.common.network.ClientPacket;
import mod.maxbogomol.wizards_reborn.api.knowledge.Knowledge;
import mod.maxbogomol.wizards_reborn.api.knowledge.KnowledgeHandler;
import mod.maxbogomol.wizards_reborn.client.shader.postprocess.KnowledgePostProcess;
import mod.maxbogomol.wizards_reborn.client.toast.KnowledgeToast;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class KnowledgeToastPacket extends ClientPacket {
    protected final String id;
    protected final boolean all;

    public KnowledgeToastPacket(String id, boolean all) {
        this.id = id;
        this.all = all;
    }

    public KnowledgeToastPacket(Knowledge knowledge, boolean all) {
        this.id = knowledge.getId();
        this.all = all;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        if (KnowledgeToast.instance == null) {
            KnowledgeToast.instance = new KnowledgeToast(id);
        }

        KnowledgeToast.instance.id = id;
        if (Minecraft.getInstance().getToasts().getToast(KnowledgeToast.class, KnowledgeToast.instance.getToken()) == null) {
            if (all) {
                KnowledgeToast.instance.all = true;
                KnowledgeToast.instance.articles = true;
                KnowledgeToast.instance.count = KnowledgeHandler.getKnowledges().size();
            } else {
                KnowledgeToast.instance.all = false;
                Knowledge knowledge = KnowledgeHandler.getKnowledge(id);
                if (knowledge != null) {
                    KnowledgeToast.instance.articles = knowledge.getArticles();
                }
                KnowledgeToast.instance.count = 1;
            }
            Minecraft.getInstance().getToasts().addToast(KnowledgeToast.instance);
        } else {
            if (all) {
                KnowledgeToast.instance.all = true;
                KnowledgeToast.instance.articles = true;
                KnowledgeToast.instance.count = KnowledgeToast.instance.count + KnowledgeHandler.getKnowledges().size();
            } else {
                KnowledgeToast.instance.count = KnowledgeToast.instance.count + 1;
            }
        }
        KnowledgePostProcess.INSTANCE.enable();
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, KnowledgeToastPacket.class, KnowledgeToastPacket::encode, KnowledgeToastPacket::decode, KnowledgeToastPacket::handle);
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(id);
        buf.writeBoolean(all);
    }

    public static KnowledgeToastPacket decode(FriendlyByteBuf buf) {
        return new KnowledgeToastPacket(buf.readUtf(), buf.readBoolean());
    }
}