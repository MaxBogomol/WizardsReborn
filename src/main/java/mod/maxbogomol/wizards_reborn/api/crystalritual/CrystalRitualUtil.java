package mod.maxbogomol.wizards_reborn.api.crystalritual;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystalRituals;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;

public class CrystalRitualUtil {

    public static CrystalRitual deserializeCrystalRitual(JsonObject json) {
        String ritualName = GsonHelper.getAsString(json, "crystal_ritual");
        CrystalRitual ritual = CrystalRituals.getCrystalRitual(ritualName);
        if (ritual == null) {
            throw new JsonSyntaxException("Unknown crystal ritual " + ritualName);
        }
        return ritual;
    }

    public static CrystalRitual crystalRitualFromNetwork(FriendlyByteBuf buffer) {
        return !buffer.readBoolean() ? WizardsRebornCrystalRituals.EMPTY : CrystalRituals.getCrystalRitual(buffer.readComponent().getString());
    }

    public static void crystalRitualToNetwork(CrystalRitual ritual, FriendlyByteBuf buffer) {
        if (ritual == null) {
            buffer.writeBoolean(false);
        } else {
            buffer.writeBoolean(true);
            buffer.writeComponent(Component.literal(ritual.getId()));
        }
    }

    public static CrystalRitual getCrystalRitual(ItemStack stack) {
        CompoundTag nbt = stack.getOrCreateTag();
        if (nbt.contains("crystalRitual")) {
            CrystalRitual ritual = CrystalRituals.getCrystalRitual(nbt.getString("crystalRitual"));
            if (ritual != null) {
                return ritual;
            }
        }

        return WizardsRebornCrystalRituals.EMPTY;
    }

    public static void setCrystalRitual(ItemStack stack, CrystalRitual ritual) {
        CompoundTag nbt = stack.getOrCreateTag();
        nbt.putString("crystalRitual", ritual.getId());
    }

    public static boolean isEmpty(CrystalRitual ritual) {
        return ritual == null;
    }
}
