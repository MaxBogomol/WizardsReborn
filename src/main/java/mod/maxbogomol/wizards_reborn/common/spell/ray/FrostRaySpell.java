package mod.maxbogomol.wizards_reborn.common.spell.ray;

import mod.maxbogomol.fluffy_fur.common.raycast.RayCast;
import mod.maxbogomol.fluffy_fur.common.raycast.RayCastContext;
import mod.maxbogomol.fluffy_fur.common.raycast.RayHitResult;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.common.entity.SpellEntity;
import mod.maxbogomol.wizards_reborn.common.item.equipment.arcane.ArcaneArmorItem;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.FrostRaySpellPacket;
import mod.maxbogomol.wizards_reborn.config.WizardsRebornConfig;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.level.BlockEvent;

import java.awt.*;

public class FrostRaySpell extends RaySpell {

    public FrostRaySpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.WATER);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.frostSpellColor;
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult, Entity target) {
        super.onImpact(level, entity, hitResult, target);

        if (!entity.level().isClientSide()) {
            if (target.tickCount % 10 == 0) {
                if (entity.getSpellContext().canRemoveWissen(this)) {
                    entity.getSpellContext().removeWissen(this);
                    int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
                    float magicModifier = ArcaneArmorItem.getPlayerMagicModifier(entity.getOwner());
                    float damage = (1.5f + (focusLevel * 0.5f)) + magicModifier + WizardsRebornConfig.SPELL_RAY_DAMAGE.get().floatValue() + WizardsRebornConfig.FROST_RAY_DAMAGE.get().floatValue();

                    target.clearFire();
                    int frost = target.getTicksFrozen() + 75;
                    if (frost > 250) frost = 250;
                    target.setTicksFrozen(frost);
                    target.setTicksFrozen(frost);

                    DamageSource damageSource = getDamage(DamageTypes.FREEZE, entity, entity.getOwner());
                    target.hurt(damageSource, damage);

                    Color color = getColor();
                    float r = color.getRed() / 255f;
                    float g = color.getGreen() / 255f;
                    float b = color.getBlue() / 255f;

                    WizardsRebornPacketHandler.sendToTracking(level, entity.blockPosition(), new FrostRaySpellPacket(hitResult.getPos(), getColor()));
                }
            }
        }
    }

    @Override
    public void onImpact(Level level, SpellEntity entity, RayHitResult hitResult) {
        super.onImpact(level, entity, hitResult);

        if (!entity.level().isClientSide()) {
            if (entity.getSpellContext().getAlternative()) {
                int focusLevel = CrystalUtil.getStatLevel(entity.getStats(), WizardsRebornCrystals.FOCUS);
                if (entity.tickCount % (4 - focusLevel) == 0) {
                    if (entity.getSpellContext().canRemoveWissen(1)) {
                        BlockPos blockPos = hitResult.getBlockPos();
                        BlockState blockState = level.getBlockState(blockPos);
                        BlockState blockStateIce = Blocks.FROSTED_ICE.defaultBlockState();

                        BlockEvent.EntityPlaceEvent placeEv = new BlockEvent.EntityPlaceEvent(BlockSnapshot.create(level.dimension(), level, blockPos), blockStateIce, entity.getOwner());

                        if (blockState == FrostedIceBlock.meltsInto() && blockStateIce.canSurvive(level, blockPos) && level.isUnobstructed(blockStateIce, blockPos, CollisionContext.empty()) && !net.minecraftforge.event.ForgeEventFactory.onBlockPlace(entity.getOwner(), net.minecraftforge.common.util.BlockSnapshot.create(level.dimension(), level, blockPos), net.minecraft.core.Direction.UP)  && !MinecraftForge.EVENT_BUS.post(placeEv)) {
                            level.setBlockAndUpdate(blockPos, blockStateIce);
                            level.scheduleTick(blockPos, Blocks.FROSTED_ICE, random.nextInt(300, 600));
                            entity.getSpellContext().removeWissen(1);
                            WizardsRebornPacketHandler.sendToTracking(level, blockPos, new FrostRaySpellPacket(blockPos.getCenter(), getColor()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public RayHitResult getHit(SpellEntity entity, Vec3 start, Vec3 end) {
        if (entity.getSpellContext().getAlternative()) {
            RayCastContext context = new RayCastContext(start, end).setEntityFilter(getEntityFilter(entity)).setEntityCount(1).setEntityEnd(true).setFluidFilter(RayCastContext.Fluid.WATER);
            return RayCast.getHit(entity.level(), context);
        }
        return super.getHit(entity, start, end);
    }
}