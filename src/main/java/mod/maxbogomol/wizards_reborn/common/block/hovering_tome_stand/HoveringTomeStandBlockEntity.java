package mod.maxbogomol.wizards_reborn.common.block.hovering_tome_stand;

import mod.maxbogomol.fluffy_fur.common.block.entity.TickableBlockEntity;
import mod.maxbogomol.wizards_reborn.WizardsReborn;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class HoveringTomeStandBlockEntity extends BlockEntity implements TickableBlockEntity {
    public int time;
    public float flip;
    public float oFlip;
    public float flipT;
    public float flipA;
    public float open;
    public float oOpen;
    public float rot;
    public float oRot;
    public float tRot;
    private static final RandomSource RANDOM = RandomSource.create();

    public HoveringTomeStandBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public HoveringTomeStandBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.HOVERING_TOME_STAND_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level.isClientSide()) {
            oOpen = open;
            oRot = rot;
            Player player = level.getNearestPlayer((double)getBlockPos().getX() + 0.5D, (double)getBlockPos().getY() + 0.5D, (double)getBlockPos().getZ() + 0.5D, 3.0D, false);
            if (player != null) {
                double d0 = player.getX() - ((double)getBlockPos().getX() + 0.5D);
                double d1 = player.getZ() - ((double)getBlockPos().getZ() + 0.5D);
                tRot = (float) Mth.atan2(d1, d0);
                open += 0.1F;
                if (open < 0.5F || RANDOM.nextInt(40) == 0) {
                    float f1 = flipT;

                    do {
                        flipT += (float)(RANDOM.nextInt(4) - RANDOM.nextInt(4));
                    } while(f1 == flipT);
                }
            } else {
                tRot += 0.02F;
                open -= 0.1F;
            }

            while(rot >= (float)Math.PI) {
                rot -= ((float)Math.PI * 2F);
            }

            while(rot < -(float)Math.PI) {
                rot += ((float)Math.PI * 2F);
            }

            while(tRot >= (float)Math.PI) {
                tRot -= ((float)Math.PI * 2F);
            }

            while(tRot < -(float)Math.PI) {
                tRot += ((float)Math.PI * 2F);
            }

            float f2;
            for(f2 = tRot - rot; f2 >= (float)Math.PI; f2 -= ((float)Math.PI * 2F)) {
            }

            while(f2 < -(float)Math.PI) {
                f2 += ((float)Math.PI * 2F);
            }

            rot += f2 * 0.4F;
            open = Mth.clamp(open, 0.0F, 1.0F);
            ++time;
            oFlip = flip;
            float f = (flipT - flip) * 0.4F;
            float f3 = 0.2F;
            f = Mth.clamp(f, -0.2F, 0.2F);
            flipA += (f - flipA) * 0.9F;
            flip += flipA;
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        return new AABB(pos.getX() - 0.5f, pos.getY() - 0.5f, pos.getZ() - 0.5f, pos.getX() + 1.5f, pos.getY() + 1.5f, pos.getZ() + 1.5f);
    }
}
