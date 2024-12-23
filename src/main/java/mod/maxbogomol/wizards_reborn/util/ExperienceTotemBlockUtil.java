package mod.maxbogomol.wizards_reborn.util;

import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;

import mod.maxbogomol.wizards_reborn.common.block.totem.experience_totem.ExperienceTotemBlockEntity;

import net.minecraft.core.BlockPos;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public class ExperienceTotemBlockUtil {
    public static InteractionResult transferExperienceToPlayer(Level level, BlockPos pos, Player player, ExperienceTotemBlockEntity blockEntity) {
        final Consumer<GenericParticle> blockTarget = p -> {
            Vec3 blockPos = player.position().add(0, player.getEyeHeight() / 2, 0);
            Vec3 pPos = p.getPosition();

            double dX = blockPos.x() - pPos.x();
            double dY = blockPos.y() - pPos.y();
            double dZ = blockPos.z() - pPos.z();

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            float speed = 0.01f;
            double x = Math.sin(pitch) * Math.cos(yaw) * speed;
            double y = Math.cos(pitch) * speed;
            double z = Math.sin(pitch) * Math.sin(yaw) * speed;

            p.setSpeed(p.getSpeed().subtract(x, y, z));
        };
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(0.784f, 1f, 0.560f).build())
                .setTransparencyData(GenericParticleData.create(0.3f, 0.6f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.15f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .addTickActor(blockTarget)
                .setLifetime(50)
                .randomVelocity(0.5f)
                .disablePhysics()
                .setFriction(0.9f)
                .repeat(level, pos.getX() + 0.5f, pos.getY() + 0.75f, pos.getZ() + 0.5f, 5);
        return InteractionResult.SUCCESS;
    }
    public static InteractionResult transferExperienceToTotem(Level level, BlockPos pos, Player player, ExperienceTotemBlockEntity blockEntity) {
        final Consumer<GenericParticle> blockTarget = p -> {
            Vec3 blockPos = pos.getCenter().add(0, 0.25f, 0);
            Vec3 pPos = p.getPosition();

            double dX = blockPos.x() - pPos.x();
            double dY = blockPos.y() - pPos.y();
            double dZ = blockPos.z() - pPos.z();

            double yaw = Math.atan2(dZ, dX);
            double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;

            float speed = 0.01f;
            double x = Math.sin(pitch) * Math.cos(yaw) * speed;
            double y = Math.cos(pitch) * speed;
            double z = Math.sin(pitch) * Math.sin(yaw) * speed;

            p.setSpeed(p.getSpeed().subtract(x, y, z));
        };
        ParticleBuilder.create(FluffyFurParticles.WISP)
                .setColorData(ColorParticleData.create(0.784f, 1f, 0.560f).build())
                .setTransparencyData(GenericParticleData.create(0.3f, 0.6f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .setScaleData(GenericParticleData.create(0.05f, 0.15f, 0).setEasing(Easing.QUARTIC_OUT).build())
                .addTickActor(blockTarget)
                .setLifetime(100)
                .randomVelocity(0.5f)
                .disablePhysics()
                .setFriction(0.9f)
                .repeat(level, player.getX(), player.getY() + (player.getEyeHeight() / 2), player.getZ(), 5);
        return InteractionResult.SUCCESS;
    }
}
