package mod.maxbogomol.wizards_reborn.common.echo;

import mod.maxbogomol.fluffy_fur.client.particle.ParticleBuilder;
import mod.maxbogomol.fluffy_fur.client.particle.behavior.SparkParticleBehavior;
import mod.maxbogomol.fluffy_fur.client.particle.data.ColorParticleData;
import mod.maxbogomol.fluffy_fur.client.particle.data.GenericParticleData;
import mod.maxbogomol.fluffy_fur.common.easing.Easing;
import mod.maxbogomol.fluffy_fur.registry.client.FluffyFurParticles;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.api.knowledge.Echo;
import mod.maxbogomol.wizards_reborn.api.knowledge.EchoStack;
import mod.maxbogomol.wizards_reborn.client.shader.postprocess.EchoPostProcess;
import mod.maxbogomol.wizards_reborn.client.shader.postprocess.KnowledgePostProcess;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EggEcho extends Echo {

    public EggEcho(String id) {
        super(id);
    }

    @Override
    public void tick(Player player, EchoStack stack) {
        super.tick(player, stack);
        Level level = player.level();
        if (level.isClientSide()) {
            if (player.equals(WizardsReborn.proxy.getPlayer())) {
                ParticleBuilder.create(FluffyFurParticles.SQUARE)
                        .setBehavior(SparkParticleBehavior.create()
                                .setScaleData(GenericParticleData.create(2, 9, 2).setEasing(Easing.SINE_IN_OUT).build())
                                .build())
                        .setColorData(ColorParticleData.create(WizardsRebornConfig.wissenColor()).build())
                        .setTransparencyData(GenericParticleData.create(0f, 0.5f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setScaleData(GenericParticleData.create(0.025f, 0.05f, 0).setEasing(Easing.QUINTIC_IN_OUT).build())
                        .setLifetime(50)
                        .setVelocity(0, 0.05f, 0)
                        .flatRandomOffset(10f, 7f, 10f)
                        .disablePhysics()
                        .repeat(level, player.position(), 3);
                KnowledgePostProcess.INSTANCE.enable();
                EchoPostProcess.INSTANCE.enable();
            }
        }
    }

    @Override
    public int getMaxTick(Player player) {
        return 2000;
    }
}
