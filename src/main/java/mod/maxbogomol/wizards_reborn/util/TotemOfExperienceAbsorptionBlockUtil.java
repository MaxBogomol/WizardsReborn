package mod.maxbogomol.wizards_reborn.util;

import mod.maxbogomol.fluffy_fur.client.particle.GenericParticle;
import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;

import mod.maxbogomol.wizards_reborn.common.block.totem.experience_absorption.TotemOfExperienceAbsorptionBlockEntity;

import net.minecraft.core.BlockPos;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class TotemOfExperienceAbsorptionBlockUtil {
    public static void transferExperienceToPlayer(Level level, BlockPos pos, Player player, TotemOfExperienceAbsorptionBlockEntity blockEntity) {
        final Consumer<GenericParticle> blockTarget = p -> {
            Vec3 blockPos = pos.getCenter().add(0, 0.25f, 0);
            Vec3 pPos = p.getPosition();
            float x = 0;
            float y = 0;
            float z = 0;

            if (blockPos.x() < pPos.x()) x = -0.01f;
            if (blockPos.x() > pPos.x()) x = 0.01f;
            if (blockPos.y() < pPos.y()) y = -0.01f;
            if (blockPos.y() > pPos.y()) y = 0.01f;
            if (blockPos.z() < pPos.z()) z = -0.01f;
            if (blockPos.z() > pPos.z()) z = 0.01f;

            p.setSpeed(p.getSpeed().add(x, y, z));
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
                .repeat(level, player.getX(), player.getY() + (player.getEyeHeight() / 2), player.getZ(), 5);
    }
}
