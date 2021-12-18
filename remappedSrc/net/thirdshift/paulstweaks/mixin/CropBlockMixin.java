package net.thirdshift.paulstweaks.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends AbstractBlockMixin{
    @Shadow public abstract boolean isMature(BlockState state);

    @Override
    public void interceptOnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (this.isMature(state)) {
            if (!world.isClient) {
                world.setBlockState(pos, ((CropBlock) (Object) this).withAge(0));
                Block.dropStacks(state, world, pos, null, player, player.getStackInHand(hand));
                player.world.spawnEntity(new ExperienceOrbEntity(player.world, pos.getX(), pos.getY(), pos.getZ(), world.getRandom().nextInt(6) + 1));
            } else {
                player.playSound(SoundEvents.ITEM_CROP_PLANT, 1.0f, 1.0f);
            }

            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}
