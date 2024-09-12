package mod.maxbogomol.wizards_reborn.common.network;

import com.mojang.datafixers.util.Pair;
import mod.maxbogomol.fluffy_fur.common.network.AddScreenshakePacket;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.network.crystalritual.CrystalInfusionBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.crystalritual.CrystalRitualBurstEffectPacket;
import mod.maxbogomol.wizards_reborn.common.network.spell.*;
import mod.maxbogomol.wizards_reborn.common.network.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public final class PacketHandler {
    private static final String PROTOCOL = "10";
    public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(WizardsReborn.MOD_ID,"network"),
            () -> PROTOCOL,
            PROTOCOL::equals,
            PROTOCOL::equals
    );

    public static void init() {
        int id = 0;

        HANDLER.registerMessage(id++, SetCrystalPacket.class, SetCrystalPacket::encode, SetCrystalPacket::decode, SetCrystalPacket::handle);
        HANDLER.registerMessage(id++, DeleteCrystalPacket.class, DeleteCrystalPacket::encode, DeleteCrystalPacket::decode, DeleteCrystalPacket::handle);
        HANDLER.registerMessage(id++, SetSpellPacket.class, SetSpellPacket::encode, SetSpellPacket::decode, SetSpellPacket::handle);
        HANDLER.registerMessage(id++, SetSpellInSetPacket.class, SetSpellInSetPacket::encode, SetSpellInSetPacket::decode, SetSpellInSetPacket::handle);
        HANDLER.registerMessage(id++, RemoveSpellSetPacket.class, RemoveSpellSetPacket::encode, RemoveSpellSetPacket::decode, RemoveSpellSetPacket::handle);
        HANDLER.registerMessage(id++, SetCurrentSpellSetPacket.class, SetCurrentSpellSetPacket::encode, SetCurrentSpellSetPacket::decode, SetCurrentSpellSetPacket::handle);
        HANDLER.registerMessage(id++, SetCurrentSpellInSetPacket.class, SetCurrentSpellInSetPacket::encode, SetCurrentSpellInSetPacket::decode, SetCurrentSpellInSetPacket::handle);
        HANDLER.registerMessage(id++, SetWissenWandModePacket.class, SetWissenWandModePacket::encode, SetWissenWandModePacket::decode, SetWissenWandModePacket::handle);
        HANDLER.registerMessage(id++, OpenBagPacket.class, OpenBagPacket::encode, OpenBagPacket::decode, OpenBagPacket::handle);
        HANDLER.registerMessage(id++, SetAdditionalFovPacket.class, SetAdditionalFovPacket::encode, SetAdditionalFovPacket::decode, SetAdditionalFovPacket::handle);
        HANDLER.registerMessage(id++, AddScreenshakePacket.class, AddScreenshakePacket::encode, AddScreenshakePacket::decode, AddScreenshakePacket::handle);
        HANDLER.registerMessage(id++, SniffaloScreenPacket.class, SniffaloScreenPacket::encode, SniffaloScreenPacket::decode, SniffaloScreenPacket::handle);

        //HANDLER.registerMessage(id++, WissenAltarBurstEffectPacket.class, WissenAltarBurstEffectPacket::encode, WissenAltarBurstEffectPacket::decode, WissenAltarBurstEffectPacket::handle);
        WissenAltarBurstEffectPacket.register(HANDLER, id++);
        HANDLER.registerMessage(id++, WissenCrystallizerBurstEffectPacket.class, WissenCrystallizerBurstEffectPacket::encode, WissenCrystallizerBurstEffectPacket::decode, WissenCrystallizerBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, ArcaneWorkbenchBurstEffectPacket.class, ArcaneWorkbenchBurstEffectPacket::encode, ArcaneWorkbenchBurstEffectPacket::decode, ArcaneWorkbenchBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, WissenAltarSendEffectPacket.class, WissenAltarSendEffectPacket::encode, WissenAltarSendEffectPacket::decode, WissenAltarSendEffectPacket::handle);
        HANDLER.registerMessage(id++, WissenCellSendEffectPacket.class, WissenCellSendEffectPacket::encode, WissenCellSendEffectPacket::decode, WissenCellSendEffectPacket::handle);
        HANDLER.registerMessage(id++, JewelerTableBurstEffectPacket.class, JewelerTableBurstEffectPacket::encode, JewelerTableBurstEffectPacket::decode, JewelerTableBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, AltarOfDroughtBurstEffectPacket.class, AltarOfDroughtBurstEffectPacket::encode, AltarOfDroughtBurstEffectPacket::decode, AltarOfDroughtBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, AltarOfDroughtSendEffectPacket.class, AltarOfDroughtSendEffectPacket::encode, AltarOfDroughtSendEffectPacket::decode, AltarOfDroughtSendEffectPacket::handle);
        HANDLER.registerMessage(id++, AltarOfDroughtBreakEffectPacket.class, AltarOfDroughtBreakEffectPacket::encode, AltarOfDroughtBreakEffectPacket::decode, AltarOfDroughtBreakEffectPacket::handle);
        HANDLER.registerMessage(id++, ArcaneIteratorBurstEffectPacket.class, ArcaneIteratorBurstEffectPacket::encode, ArcaneIteratorBurstEffectPacket::decode, ArcaneIteratorBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, ExperienceTotemBurstEffectPacket.class, ExperienceTotemBurstEffectPacket::encode, ExperienceTotemBurstEffectPacket::decode, ExperienceTotemBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, TotemOfDisenchantStartEffectPacket.class, TotemOfDisenchantStartEffectPacket::encode, TotemOfDisenchantStartEffectPacket::decode, TotemOfDisenchantStartEffectPacket::handle);
        HANDLER.registerMessage(id++, TotemOfDisenchantBurstEffectPacket.class, TotemOfDisenchantBurstEffectPacket::encode, TotemOfDisenchantBurstEffectPacket::decode, TotemOfDisenchantBurstEffectPacket::handle);

        HANDLER.registerMessage(id++, CrystalRitualBurstEffectPacket.class, CrystalRitualBurstEffectPacket::encode, CrystalRitualBurstEffectPacket::decode, CrystalRitualBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, CrystalInfusionBurstEffectPacket.class, CrystalInfusionBurstEffectPacket::encode, CrystalInfusionBurstEffectPacket::decode, CrystalInfusionBurstEffectPacket::handle);

        HANDLER.registerMessage(id++, WissenTranslatorBurstEffectPacket.class, WissenTranslatorBurstEffectPacket::encode, WissenTranslatorBurstEffectPacket::decode, WissenTranslatorBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, WissenTranslatorSendEffectPacket.class, WissenTranslatorSendEffectPacket::encode, WissenTranslatorSendEffectPacket::decode, WissenTranslatorSendEffectPacket::handle);
        HANDLER.registerMessage(id++, WissenSendEffectPacket.class, WissenSendEffectPacket::encode, WissenSendEffectPacket::decode, WissenSendEffectPacket::handle);

        HANDLER.registerMessage(id++, SpellBurstEffectPacket.class, SpellBurstEffectPacket::encode, SpellBurstEffectPacket::decode, SpellBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, SpellProjectileRayEffectPacket.class, SpellProjectileRayEffectPacket::encode, SpellProjectileRayEffectPacket::decode, SpellProjectileRayEffectPacket::handle);
        HANDLER.registerMessage(id++, SpellProjectileUpdateSpellDataPacket.class, SpellProjectileUpdateSpellDataPacket::encode, SpellProjectileUpdateSpellDataPacket::decode, SpellProjectileUpdateSpellDataPacket::handle);
        HANDLER.registerMessage(id++, RaySpellEffectPacket.class, RaySpellEffectPacket::encode, RaySpellEffectPacket::decode, RaySpellEffectPacket::handle);

        HANDLER.registerMessage(id++, ArcanemiconOfferingEffectPacket.class, ArcanemiconOfferingEffectPacket::encode, ArcanemiconOfferingEffectPacket::decode, ArcanemiconOfferingEffectPacket::handle);
        HANDLER.registerMessage(id++, WissenDustBurstEffectPacket.class, WissenDustBurstEffectPacket::encode, WissenDustBurstEffectPacket::decode, WissenDustBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, ArcanumLensBurstEffectPacket.class, ArcanumLensBurstEffectPacket::encode, ArcanumLensBurstEffectPacket::decode, ArcanumLensBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, SmokeEffectPacket.class, SmokeEffectPacket::encode, SmokeEffectPacket::decode, SmokeEffectPacket::handle);
        HANDLER.registerMessage(id++, InnocentWoodToolsEffectPacket.class, InnocentWoodToolsEffectPacket::encode, InnocentWoodToolsEffectPacket::decode, InnocentWoodToolsEffectPacket::handle);
        HANDLER.registerMessage(id++, FlowerFertilizerEffectPacket.class, FlowerFertilizerEffectPacket::encode, FlowerFertilizerEffectPacket::decode, FlowerFertilizerEffectPacket::handle);

        HANDLER.registerMessage(id++, KnowledgeUpdatePacket.class, KnowledgeUpdatePacket::encode, KnowledgeUpdatePacket::decode, KnowledgeUpdatePacket::handle);
        HANDLER.registerMessage(id++, KnowledgeToastPacket.class, KnowledgeToastPacket::encode, KnowledgeToastPacket::decode, KnowledgeToastPacket::handle);
        HANDLER.registerMessage(id++, UnlockSpellPacket.class, UnlockSpellPacket::encode, UnlockSpellPacket::decode, UnlockSpellPacket::handle);
        HANDLER.registerMessage(id++, ArcanemiconToastPacket.class, ArcanemiconToastPacket::encode, ArcanemiconToastPacket::decode, ArcanemiconToastPacket::handle);

        HANDLER.registerMessage(id++, EarthRaySpellEffectPacket.class, EarthRaySpellEffectPacket::encode, EarthRaySpellEffectPacket::decode, EarthRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, WaterRaySpellEffectPacket.class, WaterRaySpellEffectPacket::encode, WaterRaySpellEffectPacket::decode, WaterRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, AirRaySpellEffectPacket.class, AirRaySpellEffectPacket::encode, AirRaySpellEffectPacket::decode, AirRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, FireRaySpellEffectPacket.class, FireRaySpellEffectPacket::encode, FireRaySpellEffectPacket::decode, FireRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, FrostRaySpellEffectPacket.class, FrostRaySpellEffectPacket::encode, FrostRaySpellEffectPacket::decode, FrostRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, HolyRaySpellEffectPacket.class, HolyRaySpellEffectPacket::encode, HolyRaySpellEffectPacket::decode, HolyRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, ChargeSpellProjectileRayEffectPacket.class, ChargeSpellProjectileRayEffectPacket::encode, ChargeSpellProjectileRayEffectPacket::decode, ChargeSpellProjectileRayEffectPacket::handle);
        HANDLER.registerMessage(id++, HeartOfNatureSpellEffectPacket.class, HeartOfNatureSpellEffectPacket::encode, HeartOfNatureSpellEffectPacket::decode, HeartOfNatureSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, MagicSproutSpellEffectPacket.class, MagicSproutSpellEffectPacket::encode, MagicSproutSpellEffectPacket::decode, MagicSproutSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, WaterBreathingSpellEffectPacket.class, WaterBreathingSpellEffectPacket::encode, WaterBreathingSpellEffectPacket::decode, WaterBreathingSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, AirFlowSpellEffectPacket.class, AirFlowSpellEffectPacket::encode, AirFlowSpellEffectPacket::decode, AirFlowSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, FireShieldSpellEffectPacket.class, FireShieldSpellEffectPacket::encode, FireShieldSpellEffectPacket::decode, FireShieldSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, BlinkSpellEffectPacket.class, BlinkSpellEffectPacket::encode, BlinkSpellEffectPacket::decode, BlinkSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, AuraSpellCastEffectPacket.class, AuraSpellCastEffectPacket::encode, AuraSpellCastEffectPacket::decode, AuraSpellCastEffectPacket::handle);
        HANDLER.registerMessage(id++, AuraSpellBurstEffectPacket.class, AuraSpellBurstEffectPacket::encode, AuraSpellBurstEffectPacket::decode, AuraSpellBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, FrostAuraSpellBurstEffectPacket.class, FrostAuraSpellBurstEffectPacket::encode, FrostAuraSpellBurstEffectPacket::decode, FrostAuraSpellBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, IcicleRayEffectPacket.class, IcicleRayEffectPacket::encode, IcicleRayEffectPacket::decode, IcicleRayEffectPacket::handle);
        HANDLER.registerMessage(id++, StrikeSpellEffectPacket.class, StrikeSpellEffectPacket::encode, StrikeSpellEffectPacket::decode, StrikeSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, PoisonSpellEffectPacket.class, PoisonSpellEffectPacket::encode, PoisonSpellEffectPacket::decode, PoisonSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, WitheringSpellEffectPacket.class, WitheringSpellEffectPacket::encode, WitheringSpellEffectPacket::decode, WitheringSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, HolyCrossSpellEffectPacket.class, HolyCrossSpellEffectPacket::encode, HolyCrossSpellEffectPacket::decode, HolyCrossSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, NecroticRaySpellEffectPacket.class, NecroticRaySpellEffectPacket::encode, NecroticRaySpellEffectPacket::decode, NecroticRaySpellEffectPacket::handle);
        HANDLER.registerMessage(id++, AirImpactSpellEffectPacket.class, AirImpactSpellEffectPacket::encode, AirImpactSpellEffectPacket::decode, AirImpactSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, BlockPlaceSpellEffectPacket.class, BlockPlaceSpellEffectPacket::encode, BlockPlaceSpellEffectPacket::decode, BlockPlaceSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, CrystalCrushingSpellEffectPacket.class, CrystalCrushingSpellEffectPacket::encode, CrystalCrushingSpellEffectPacket::decode, CrystalCrushingSpellEffectPacket::handle);
        HANDLER.registerMessage(id++, IrritationSpellEffectPacket.class, IrritationSpellEffectPacket::encode, IrritationSpellEffectPacket::decode, IrritationSpellEffectPacket::handle);

        HANDLER.registerMessage(id++, MagicBladeEffectPacket.class, MagicBladeEffectPacket::encode, MagicBladeEffectPacket::decode, MagicBladeEffectPacket::handle);
        HANDLER.registerMessage(id++, WissenChargeBurstEffectPacket.class, WissenChargeBurstEffectPacket::encode, WissenChargeBurstEffectPacket::decode, WissenChargeBurstEffectPacket::handle);
        HANDLER.registerMessage(id++, EagleShotRayEffectPacket.class, EagleShotRayEffectPacket::encode, EagleShotRayEffectPacket::decode, EagleShotRayEffectPacket::handle);
        HANDLER.registerMessage(id++, SplitArrowBurstEffectPacket.class, SplitArrowBurstEffectPacket::encode, SplitArrowBurstEffectPacket::decode, SplitArrowBurstEffectPacket::handle);
    }

    private static final PacketDistributor<Pair<Level, BlockPos>> TRACKING_CHUNK_AND_NEAR = new PacketDistributor<>(
            (_d, pairSupplier) -> {
                var pair = pairSupplier.get();
                var level = pair.getFirst();
                var blockpos = pair.getSecond();
                var chunkpos = new ChunkPos(blockpos);
                return packet -> {
                    var players = ((ServerChunkCache) level.getChunkSource()).chunkMap
                            .getPlayers(chunkpos, false);
                    for (var player : players) {
                        if (player.distanceToSqr(blockpos.getX(), blockpos.getY(), blockpos.getZ()) < 64 * 64) {
                            player.connection.send(packet);
                        }
                    }
                };
            },
            NetworkDirection.PLAY_TO_CLIENT
    );

    public static void sendTo(ServerPlayer playerMP, Object toSend) {
        HANDLER.sendTo(toSend, playerMP.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendNonLocal(ServerPlayer playerMP, Object toSend) {
        if (playerMP.server.isDedicatedServer() || !playerMP.getGameProfile().getName().equals(playerMP.server.getLocalIp())) {
            sendTo(playerMP, toSend);
        }
    }

    public static void sendToTracking(Level level, BlockPos pos, Object msg) {
        HANDLER.send(TRACKING_CHUNK_AND_NEAR.with(() -> Pair.of(level, pos)), msg);
    }

    public static void sendTo(Player entity, Object msg) {
        HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer)entity), msg);
    }

    public static void sendToServer(Object msg) {
        HANDLER.sendToServer(msg);
    }
}