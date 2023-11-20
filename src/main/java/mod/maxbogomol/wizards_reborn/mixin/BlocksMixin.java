package mod.maxbogomol.wizards_reborn.mixin;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TorchflowerCropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(Blocks.class)
public abstract class BlocksMixin  {
    @ModifyArg(method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/FlowerBlock;<init>(Lnet/minecraft/world/effect/MobEffect;ILnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V",
                    ordinal = 0),
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=torchflower")))
    private static BlockBehaviour.Properties modifyTorchflower(BlockBehaviour.Properties properties) {
        return properties.lightLevel(blockState -> 12);
    }

    @ModifyArg(method = "flowerPot(Lnet/minecraft/world/level/block/Block;[Lnet/minecraft/world/flag/FeatureFlag;)Lnet/minecraft/world/level/block/FlowerPotBlock;",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/FlowerPotBlock;<init>(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V",
                    ordinal = 0))
    private static BlockBehaviour.Properties modifyPottedTorchflower(Block block, BlockBehaviour.Properties properties) {
        if (block.getDescriptionId().equals("block.minecraft.torchflower"))
            return properties.lightLevel(blockState -> 12);
        return properties;
    }

    @ModifyArg(method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/TorchflowerCropBlock;<init>(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V",
                    ordinal = 0),
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=torchflower_crop")))
    private static BlockBehaviour.Properties modifyTorchflowerCrop(BlockBehaviour.Properties properties) {

        return properties.lightLevel(blockState -> switch (blockState.getValue(TorchflowerCropBlock.AGE)) {
            case 0 -> 4;
            case 1 -> 8;
            default -> 12;
        });
    }
}
