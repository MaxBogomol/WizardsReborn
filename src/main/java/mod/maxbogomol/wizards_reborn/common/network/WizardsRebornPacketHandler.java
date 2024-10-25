package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment.EagleShotRayPacket;
import mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment.MagicBladePacket;
import mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment.SplitArrowBurstPacket;
import mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment.WissenChargeBurstPacket;
import mod.maxbogomol.wizards_reborn.common.network.block.*;
import mod.maxbogomol.wizards_reborn.common.network.crystalritual.CrystalInfusionBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.crystalritual.CrystalRitualBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.item.*;
import mod.maxbogomol.wizards_reborn.common.network.lightray.LightRayBurstPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public final class WizardsRebornPacketHandler extends PacketHandler {
    private static final String PROTOCOL = "10";
    public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(WizardsReborn.MOD_ID,"network"), () -> PROTOCOL, PROTOCOL::equals, PROTOCOL::equals);

    public static void init() {
        int id = 0;

        ArcaneWandSetCrystalPacket.register(HANDLER, id++);
        ArcaneWandRemoveCrystalPacket.register(HANDLER, id++);
        ArcaneWandSpellSetPacket.register(HANDLER, id++);
        SetSpellInSetPacket.register(HANDLER, id++);
        RemoveSpellSetPacket.register(HANDLER, id++);
        SetCurrentSpellSetPacket.register(HANDLER, id++);
        SetCurrentSpellInSetPacket.register(HANDLER, id++);
        WissenWandSetModePacket.register(HANDLER, id++);
        BagOpenPacket.register(HANDLER, id++);
        HANDLER.registerMessage(id++, SetAdditionalFovPacket.class, SetAdditionalFovPacket::encode, SetAdditionalFovPacket::decode, SetAdditionalFovPacket::handle);
        SniffaloScreenPacket.register(HANDLER, id++);

        ArcanumOreBreakPacket.register(HANDLER, id++);
        NetherSaltOreBreakPacket.register(HANDLER, id++);
        ArcaneWoodLeavesBreakPacket.register(HANDLER, id++);
        InnocentWoodLeavesBreakPacket.register(HANDLER, id++);
        CrystalBreakPacket.register(HANDLER, id++);
        CrystalGrowthBreakPacket.register(HANDLER, id++);
        SteamBreakPacket.register(HANDLER, id++);

        WissenAltarBurstPacket.register(HANDLER, id++);
        WissenAltarBurstPacket.register(HANDLER, id++);
        WissenTranslatorBurstPacket.register(HANDLER, id++);
        WissenTranslatorSendPacket.register(HANDLER, id++);
        WissenSendEffectPacket.register(HANDLER, id++);
        WissenCrystallizerBurstPacket.register(HANDLER, id++);
        ArcaneWorkbenchBurstPacket.register(HANDLER, id++);
        WissenCellSendPacket.register(HANDLER, id++);
        JewelerTableBurstPacket.register(HANDLER, id++);
        AltarOfDroughtBurstPacket.register(HANDLER, id++);
        AltarOfDroughtSendPacket.register(HANDLER, id++);
        AltarOfDroughtBreakPacket.register(HANDLER, id++);
        ArcaneIteratorBurstPacket.register(HANDLER, id++);
        ExperienceTotemBurstPacket.register(HANDLER, id++);
        TotemOfDisenchantStartPacket.register(HANDLER, id++);
        TotemOfDisenchantBurstPacket.register(HANDLER, id++);

        HANDLER.registerMessage(id++, CrystalRitualBurstEffectPacket.class, CrystalRitualBurstEffectPacket::encode, CrystalRitualBurstEffectPacket::decode, CrystalRitualBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, CrystalInfusionBurstEffectPacket.class, CrystalInfusionBurstEffectPacket::encode, CrystalInfusionBurstEffectPacket::decode, CrystalInfusionBurstEffectPacket::handle);

        LightRayBurstPacket.register(HANDLER, id++);

        HANDLER.registerMessage(id++, RaySpellEffectPacket.class, RaySpellEffectPacket::encode, RaySpellEffectPacket::decode, RaySpellEffectPacket::handle);

        ArcanemiconOfferingEffectPacket.register(HANDLER, id++);
        WissenDustBurstPacket.register(HANDLER, id++);
        ArcanumLensBurstPacket.register(HANDLER, id++);
        SmokePacket.register(HANDLER, id++);
        InnocentWoodToolsPacket.register(HANDLER, id++);
        FlowerFertilizerPacket.register(HANDLER, id++);

        ProjectileSpellBurstPacket.register(HANDLER, id++);
        ProjectileSpellTrailPacket.register(HANDLER, id++);
        ProjectileSpellHeartsPacket.register(HANDLER, id++);
        ProjectileSpellSkullsPacket.register(HANDLER, id++);
        HeartOfNatureSpellPacket.register(HANDLER, id++);
        WaterBreathingSpellPacket.register(HANDLER, id++);
        AirFlowSpellPacket.register(HANDLER, id++);
        FireShieldSpellPacket.register(HANDLER, id++);
        BlinkSpellPacket.register(HANDLER, id++);
        SnowflakeSpellPacket.register(HANDLER, id++);
        CrossSpellHeartsPacket.register(HANDLER, id++);
        CrossSpellSkullsPacket.register(HANDLER, id++);
        AirImpactSpellPacket.register(HANDLER, id++);
        IcicleSpellTrailPacket.register(HANDLER, id++);

        ChargeSpellTrailPacket.register(HANDLER, id++);

        WitheringSpellPacket.register(HANDLER, id++);
        IrritationSpellPacket.register(HANDLER, id++);

        HANDLER.registerMessage(id++, KnowledgeUpdatePacket.class, KnowledgeUpdatePacket::encode, KnowledgeUpdatePacket::decode, KnowledgeUpdatePacket::handle);
        HANDLER.registerMessage(id++, KnowledgeToastPacket.class, KnowledgeToastPacket::encode, KnowledgeToastPacket::decode, KnowledgeToastPacket::handle);
        HANDLER.registerMessage(id++, SpellUnlockPacket.class, SpellUnlockPacket::encode, SpellUnlockPacket::decode, SpellUnlockPacket::handle);
        HANDLER.registerMessage(id++, ArcanemiconToastPacket.class, ArcanemiconToastPacket::encode, ArcanemiconToastPacket::decode, ArcanemiconToastPacket::handle);

        HANDLER.registerMessage(id++, EarthRaySpellEffectPacket.class, EarthRaySpellEffectPacket::encode, EarthRaySpellEffectPacket::decode, EarthRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, WaterRaySpellEffectPacket.class, WaterRaySpellEffectPacket::encode, WaterRaySpellEffectPacket::decode, WaterRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, AirRaySpellEffectPacket.class, AirRaySpellEffectPacket::encode, AirRaySpellEffectPacket::decode, AirRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, FireRaySpellEffectPacket.class, FireRaySpellEffectPacket::encode, FireRaySpellEffectPacket::decode, FireRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, FrostRaySpellEffectPacket.class, FrostRaySpellEffectPacket::encode, FrostRaySpellEffectPacket::decode, FrostRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, HolyRaySpellEffectPacket.class, HolyRaySpellEffectPacket::encode, HolyRaySpellEffectPacket::decode, HolyRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, MagicSproutSpellEffectPacket.class, MagicSproutSpellEffectPacket::encode, MagicSproutSpellEffectPacket::decode, MagicSproutSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, AuraSpellCastEffectPacket.class, AuraSpellCastEffectPacket::encode, AuraSpellCastEffectPacket::decode, AuraSpellCastEffectPacket::handle);
        HANDLER.registerMessage(id++, AuraSpellBurstEffectPacket.class, AuraSpellBurstEffectPacket::encode, AuraSpellBurstEffectPacket::decode, AuraSpellBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, FrostAuraSpellBurstEffectPacket.class, FrostAuraSpellBurstEffectPacket::encode, FrostAuraSpellBurstEffectPacket::decode, FrostAuraSpellBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, StrikeSpellEffectPacket.class, StrikeSpellEffectPacket::encode, StrikeSpellEffectPacket::decode, StrikeSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, NecroticRaySpellEffectPacket.class, NecroticRaySpellEffectPacket::encode, NecroticRaySpellEffectPacket::decode, NecroticRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, BlockPlaceSpellEffectPacket.class, BlockPlaceSpellEffectPacket::encode, BlockPlaceSpellEffectPacket::decode, BlockPlaceSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, CrystalCrushingSpellEffectPacket.class, CrystalCrushingSpellEffectPacket::encode, CrystalCrushingSpellEffectPacket::decode, CrystalCrushingSpellEffectPacket::handle);

        MagicBladePacket.register(HANDLER, id++);
        WissenChargeBurstPacket.register(HANDLER, id++);
        EagleShotRayPacket.register(HANDLER, id++);
        SplitArrowBurstPacket.register(HANDLER, id++);
    }

    public static SimpleChannel getHandler() {
        return HANDLER;
    }

    public static void sendTo(ServerPlayer playerMP, Object toSend) {
        sendTo(getHandler(), playerMP, toSend);
    }

    public static void sendNonLocal(ServerPlayer playerMP, Object toSend) {
        sendNonLocal(getHandler(), playerMP, toSend);
    }

    public static void sendToTracking(Level level, BlockPos pos, Object msg) {
        sendToTracking(getHandler(), level, pos, msg);
    }

    public static void sendTo(Player entity, Object msg) {
        sendTo(getHandler(), entity, msg);
    }

    public static void sendToServer(Object msg) {
        sendToServer(getHandler(), msg);
    }
}