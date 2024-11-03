package mod.maxbogomol.wizards_reborn.common.network.spell;

import mod.maxbogomol.fluffy_fur.common.network.TwoPositionColorClientPacket;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.awt.*;
import java.util.function.Supplier;

public class NecroticRaySpellTrailPacket extends TwoPositionColorClientPacket {

    public NecroticRaySpellTrailPacket(double x1, double y1, double z1, double x2, double y2, double z2, float r, float g, float b, float a) {
        super(x1, y1, z1, x2, y2, z2, r, g, b, a);
    }

    public NecroticRaySpellTrailPacket(Vec3 vec1, Vec3 vec2, Color color) {
        super(vec1, vec2, color);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Level level = WizardsReborn.proxy.getLevel();

        for (int i = 0; i < 10; i++) {
            if (random.nextFloat() < 0.1f) {
                double lerp = random.nextDouble();
                double lx = Mth.lerp(lerp, x1, x2);
                double ly = Mth.lerp(lerp, y1, y2);
                double lz = Mth.lerp(lerp, z1, z2);

                if (random.nextFloat() < 0.1f) {
                    level.addParticle(ParticleTypes.ENTITY_EFFECT, lx, ly, lz, r, g, b);
                }
            }
        }
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, NecroticRaySpellTrailPacket.class, NecroticRaySpellTrailPacket::encode, NecroticRaySpellTrailPacket::decode, NecroticRaySpellTrailPacket::handle);
    }

    public static NecroticRaySpellTrailPacket decode(FriendlyByteBuf buf) {
        return decode(NecroticRaySpellTrailPacket::new, buf);
    }
}