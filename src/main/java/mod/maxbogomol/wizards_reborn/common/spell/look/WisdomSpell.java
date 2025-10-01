package mod.maxbogomol.wizards_reborn.common.spell.look;

import mod.maxbogomol.fluffy_fur.client.animation.ItemAnimation;
import mod.maxbogomol.wizards_reborn.api.crystal.CrystalUtil;
import mod.maxbogomol.wizards_reborn.api.spell.SpellContext;
import mod.maxbogomol.wizards_reborn.client.animation.StrikeSpellItemAnimation;
import mod.maxbogomol.wizards_reborn.common.network.WizardsRebornPacketHandler;
import mod.maxbogomol.wizards_reborn.common.network.spell.WisdomSpellBurstPacket;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornCrystals;
import mod.maxbogomol.wizards_reborn.registry.common.WizardsRebornSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

public class WisdomSpell extends LookSpell {
    public static StrikeSpellItemAnimation animation = new StrikeSpellItemAnimation();

    public WisdomSpell(String id, int points) {
        super(id, points);
        addCrystalType(WizardsRebornCrystals.VOID);
    }

    @Override
    public Color getColor() {
        return WizardsRebornSpells.experienceSpellColor;
    }

    @Override
    public int getCooldown() {
        return 50;
    }

    @Override
    public int getWissenCost() {
        return 300;
    }

    @Override
    public int getMinimumPolishingLevel() {
        return 1;
    }

    @Override
    public void useSpell(Level level, SpellContext spellContext) {
        if (!level.isClientSide()) {
            spellContext.startUsing(this);
        }
    }

    @Override
    public void useSpellTick(Level level, SpellContext spellContext, int time) {
        if (!level.isClientSide()) {
            if (!canSpell(level, spellContext)) {
                spellContext.stopUsing(this);
            } else {
                if (time % 20 == 0 && time > 0) {
                    spellContext.removeWissen(this);
                    spellContext.awardStat(this);
                    spellContext.spellSound(this);
                    lookSpell(level, spellContext);

                    Vec3 pos = getHit(level, spellContext).getPos();
                    WizardsRebornPacketHandler.sendToTracking(level, BlockPos.containing(pos), new WisdomSpellBurstPacket(pos, getColor()));
                }
            }
        }
    }

    @Override
    public void stopUseSpell(Level level, SpellContext spellContext, int timeLeft) {
        if (!level.isClientSide()) {
            spellContext.setCooldown(this);
        }
    }

    @Override
    public void lookSpell(Level level, SpellContext spellContext) {
        Vec3 pos = getHit(level, spellContext).getPos();
        int focusLevel = CrystalUtil.getStatLevel(spellContext.getStats(), WizardsRebornCrystals.FOCUS);
        int exp = 5 + focusLevel;
        level.addFreshEntity(new ExperienceOrb(level, pos.x, pos.y, pos.z, exp));
    }

    @Override
    public boolean canLookSpell(Level level, SpellContext spellContext) {
        return spellContext.canRemoveWissen(this);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.CUSTOM;
    }

    @Override
    public boolean hasCustomAnimation(ItemStack stack) {
        return true;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemAnimation getAnimation(ItemStack stack) {
        return animation;
    }
}
