package mod.maxbogomol.wizards_reborn.common.block.salt_lantern;

import mod.maxbogomol.wizards_reborn.WizardsReborn;
import mod.maxbogomol.wizards_reborn.client.particle.Particles;
import mod.maxbogomol.wizards_reborn.common.block.ArcaneLumosBlock;
import mod.maxbogomol.wizards_reborn.common.block.salt_torch.SaltTorchBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class SaltLanternBlockEntity extends SaltTorchBlockEntity {
    public SaltLanternBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SaltLanternBlockEntity(BlockPos pos, BlockState state) {
        this(WizardsReborn.SALT_LANTERN_TILE_ENTITY.get(), pos, state);
    }

    @Override
    public void tick() {
        if (level.isClientSide()) {
            Color colorF = colorFirst;
            Color color = colorSecond;
            Vec3 pos = new Vec3(0.5f, 0.25f, 0.5f);
            boolean isCosmic = false;

            if (getBlockState().getValue(SaltLanternBlock.HANGING)) {
                pos = pos.add(0f, 0.1875f, 0f);
            }

            if (!getItemHandler().getItem(0).isEmpty()) {
                if (getItemHandler().getItem(0).getItem() instanceof BlockItem) {
                    BlockItem blockItem = (BlockItem) getItemHandler().getItem(0).getItem();
                    if (blockItem.getBlock() instanceof ArcaneLumosBlock) {
                        ArcaneLumosBlock lumos = (ArcaneLumosBlock) blockItem.getBlock();
                        color = ArcaneLumosBlock.getColor(lumos.color);

                        if (lumos.color == ArcaneLumosBlock.Colors.COSMIC) {
                            isCosmic = true;
                        }
                    }
                }
            }
            if (!getItemHandler().getItem(1).isEmpty()) {
                if (getItemHandler().getItem(1).getItem() instanceof BlockItem) {
                    BlockItem blockItem = (BlockItem) getItemHandler().getItem(1).getItem();
                    if (blockItem.getBlock() instanceof ArcaneLumosBlock) {
                        ArcaneLumosBlock lumos = (ArcaneLumosBlock) blockItem.getBlock();
                        colorF = ArcaneLumosBlock.getColor(lumos.color);

                        if (lumos.color == ArcaneLumosBlock.Colors.COSMIC) {
                            isCosmic = true;
                        }
                    }
                }
            }

            if (random.nextFloat() < 0.5) {
                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .setAlpha(0.25f, 0).setScale(0.45f, 0)
                        .setColor(colorF.getRed() / 255f, colorF.getGreen()/ 255f, colorF.getBlue() / 255f, color.getRed() / 255f, color.getGreen()/ 255f, color.getBlue() / 255f)
                        .setLifetime(30)
                        .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y(), worldPosition.getZ() + pos.z());
            }
            if (random.nextFloat() < 0.45) {
                Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 80), ((random.nextDouble() - 0.5D) / 80), ((random.nextDouble() - 0.5D) / 80))
                        .setAlpha(0.35f, 0).setScale(0f, 0.35f)
                        .setColor(colorF.getRed() / 255f, colorF.getGreen()/ 255f, colorF.getBlue() / 255f, color.getRed() / 255f, color.getGreen()/ 255f, color.getBlue() / 255f)
                        .setLifetime(60)
                        .setSpin((0.01f * (float) ((random.nextDouble() - 0.5D) * 2)))
                        .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y(), worldPosition.getZ() + pos.z());
            }
            if (random.nextFloat() < 0.45) {
                Particles.create(WizardsReborn.WISP_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 150), ((random.nextDouble() - 0.5D) / 150), ((random.nextDouble() - 0.5D) / 150))
                        .setAlpha(0.35f, 0).setScale(0.15f, 0)
                        .setColor(colorF.getRed() / 255f, colorF.getGreen()/ 255f, colorF.getBlue() / 255f, color.getRed() / 255f, color.getGreen()/ 255f, color.getBlue() / 255f)
                        .setLifetime(30)
                        .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y(), worldPosition.getZ() + pos.z());
            }
            if (random.nextFloat() < 0.3) {
                Particles.create(WizardsReborn.SMOKE_PARTICLE)
                        .addVelocity(((random.nextDouble() - 0.5D) / 150), ((random.nextDouble() - 0.5D) / 150) + 0.02f, ((random.nextDouble() - 0.5D) / 150))
                        .setAlpha(0.4f, 0).setScale(0.15f, 0.35f)
                        .setColor(0, 0, 0)
                        .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                        .setLifetime(60)
                        .spawn(level, worldPosition.getX() + pos.x(), worldPosition.getY() + pos.y(), worldPosition.getZ() + pos.z());
            }

            if (isCosmic) {
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 150), ((random.nextDouble() - 0.5D) / 150) + 0.025f, ((random.nextDouble() - 0.5D) / 150))
                            .setAlpha(0.75f, 0).setScale(0.1f, 0)
                            .setColor((float) color.getRed() / 255, (float) color.getGreen()/ 255, (float) color.getBlue() / 255)
                            .setLifetime(10)
                            .setSpin((0.1f * (float) ((random.nextDouble() - 0.5D) * 2)))
                            .spawn(level, worldPosition.getX() + pos.x() + ((random.nextDouble() - 0.5D) / 3), worldPosition.getY() + pos.y() + ((random.nextDouble() - 0.5D) / 3), worldPosition.getZ() + pos.z() + ((random.nextDouble() - 0.5D) / 3));
                }
                if (random.nextFloat() < 0.1) {
                    Particles.create(WizardsReborn.SPARKLE_PARTICLE)
                            .addVelocity(((random.nextDouble() - 0.5D) / 150), ((random.nextDouble() - 0.5D) / 150) + 0.025f, ((random.nextDouble() - 0.5D) / 150))
                            .setAlpha(0.75f, 0).setScale(0.1f, 0)
                            .setColor(1f, 1f, 1f)
                            .setLifetime(10)
                            .spawn(level, worldPosition.getX() + pos.x() + ((random.nextDouble() - 0.5D) / 3), worldPosition.getY() + pos.y() + ((random.nextDouble() - 0.5D) / 3), worldPosition.getZ() + pos.z() + ((random.nextDouble() - 0.5D) / 3));
                }
            }
        }
    }
}
