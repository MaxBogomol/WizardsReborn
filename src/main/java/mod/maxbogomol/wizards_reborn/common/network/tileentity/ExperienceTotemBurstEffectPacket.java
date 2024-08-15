package mod.maxbogomol.wizards_reborn.common.network.tileentity;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.common.block.totem.experience_absorption.TotemOfExperienceAbsorptionBlockEntity;
import mod.maxbogomol.wizards_reborn.common.block.totem.experience_totem.ExperienceTotemBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.Random;
import java.util.function.Supplier;

public class ExperienceTotemBurstEffectPacket {
    private final BlockPos pos;
    private final float X, Y, Z;

    private static final Random random = new Random();

    public ExperienceTotemBurstEffectPacket(BlockPos pos, float X, float Y, float Z) {
        this.pos = pos;

        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    public ExperienceTotemBurstEffectPacket(BlockPos pos, Vec3 end) {
        this.pos = pos;

        this.X = (float) end.x();
        this.Y = (float) end.y();
        this.Z = (float) end.z();
    }

    public static ExperienceTotemBurstEffectPacket decode(FriendlyByteBuf buf) {
        return new ExperienceTotemBurstEffectPacket(buf.readBlockPos(), buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);

        buf.writeFloat(X);
        buf.writeFloat(Y);
        buf.writeFloat(Z);
    }

    public static void handle(ExperienceTotemBurstEffectPacket msg, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().getReceptionSide().isClient()) {
            ctx.get().enqueueWork(new Runnable() {
                @Override
                public void run() {
                    Level world = WizardsReborn.proxy.getWorld();

                    if (world.getBlockEntity(msg.pos) instanceof ExperienceTotemBlockEntity totem) {
                        totem.addBurst(new Vec3(msg.X, msg.Y, msg.Z), msg.pos.getCenter());
                    }
                    if (world.getBlockEntity(msg.pos) instanceof TotemOfExperienceAbsorptionBlockEntity totem) {
                        totem.addBurst(new Vec3(msg.X, msg.Y, msg.Z), msg.pos.getCenter());
                    }

                    ctx.get().setPacketHandled(true);
                }
            });
        }
    }
}