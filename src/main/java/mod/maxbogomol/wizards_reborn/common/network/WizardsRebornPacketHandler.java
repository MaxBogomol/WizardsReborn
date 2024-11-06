package mod.maxbogomol.wizards_reborn.common.network;

import mod.maxbogomol.fluffy_fur.common.network.PacketHandler;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.network.arcaneenchantment.*;
import mod.maxbogomol.wizards_reborn.common.network.block.*;
import mod.maxbogomol.wizards_reborn.common.network.crystalritual.CrystalInfusionBurstPacket;
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
        SpellSetSetSpellPacket.register(HANDLER, id++);
        SpellSetRemovePacket.register(HANDLER, id++);
        SpellSetSetCurrentPacket.register(HANDLER, id++);
        SpellSetSetCurrentSpellPacket.register(HANDLER, id++);
        WissenWandSetModePacket.register(HANDLER, id++);
        BagOpenPacket.register(HANDLER, id++);
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
        ArcanePedestalsBurstPacket.register(HANDLER, id++);
        ExperienceTotemBurstPacket.register(HANDLER, id++);
        TotemOfDisenchantStartPacket.register(HANDLER, id++);
        TotemOfDisenchantBurstPacket.register(HANDLER, id++);

        LightRayBurstPacket.register(HANDLER, id++);

        CrystalInfusionBurstPacket.register(HANDLER, id++);

        ArcanemiconOfferingPacket.register(HANDLER, id++);
        WissenDustBurstPacket.register(HANDLER, id++);
        MortarPacket.register(HANDLER, id++);
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
        BlinkFovPacket.register(HANDLER, id++);
        SnowflakeSpellPacket.register(HANDLER, id++);
        CrossSpellHeartsPacket.register(HANDLER, id++);
        CrossSpellSkullsPacket.register(HANDLER, id++);
        AirImpactSpellPacket.register(HANDLER, id++);
        IcicleSpellTrailPacket.register(HANDLER, id++);

        ChargeSpellTrailPacket.register(HANDLER, id++);
        RaySpellTrailPacket.register(HANDLER, id++);
        WitheringSpellPacket.register(HANDLER, id++);
        IrritationSpellPacket.register(HANDLER, id++);
        MagicSproutSpellPacket.register(HANDLER, id++);
        AuraSpellCastPacket.register(HANDLER, id++);
        AuraSpellBurstPacket.register(HANDLER, id++);
        FrostAuraSpellBurstPacket.register(HANDLER, id++);
        StrikeSpellBurstPacket.register(HANDLER, id++);
        StrikeSpellScreenshakePacket.register(HANDLER, id++);
        NecroticRaySpellTrailPacket.register(HANDLER, id++);
        WisdomSpellBurstPacket.register(HANDLER, id++);
        BlockPlaceSpellPacket.register(HANDLER, id++);
        EarthRaySpellPacket.register(HANDLER, id++);
        WaterRaySpellPacket.register(HANDLER, id++);
        AirRaySpellPacket.register(HANDLER, id++);
        FireRaySpellPacket.register(HANDLER, id++);
        FrostRaySpellPacket.register(HANDLER, id++);
        CrystalCrushingSpellPacket.register(HANDLER, id++);
        CrystalCrushingSpellScreenshakePacket.register(HANDLER, id++);

        KnowledgeUpdatePacket.register(HANDLER, id++);
        KnowledgeToastPacket.register(HANDLER, id++);
        SpellUnlockPacket.register(HANDLER, id++);
        ArcanemiconToastPacket.register(HANDLER, id++);

        MagicBladePacket.register(HANDLER, id++);
        WissenChargeBurstPacket.register(HANDLER, id++);
        EagleShotRayPacket.register(HANDLER, id++);
        EagleShotScreenshakePacket.register(HANDLER, id++);
        SplitArrowBurstPacket.register(HANDLER, id++);
        SplitArrowScreenshakePacket.register(HANDLER, id++);

        ThrowedScytheScreenshakePacket.register(HANDLER, id++);
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