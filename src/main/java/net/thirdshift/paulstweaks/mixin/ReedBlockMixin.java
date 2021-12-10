package net.thirdshift.paulstweaks.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SugarCaneBlock.class)
public class ReedBlockMixin extends AbstractBlockMixin {
    @Override
    public void interceptOnUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (hit.getSide() == Direction.UP) {
            return;
        }

        int count = 1;

        // find the block the sugar cane stands on
        BlockPos bottom = pos.down();
        while (world.getBlockState(bottom).isOf(Blocks.SUGAR_CANE)) {
            count++;
            bottom = bottom.down();
        }
        // when the sugar cane is only 1 tall, do nothing
        if (count == 1 && !world.getBlockState(pos.up()).isOf(Blocks.SUGAR_CANE)) {
            return;
        }
        // else break the 2nd from bottom cane
        if (!world.isClient) {
            world.breakBlock(bottom.up(2), true);
            player.world.spawnEntity(new ExperienceOrbEntity(player.world, pos.getX(), pos.getY(), pos.getZ(), world.getRandom().nextInt(6) + 1));
        } else {
            player.playSound(SoundEvents.ITEM_CROP_PLANT, 1.0f, 1.0f);
        }

        cir.setReturnValue(ActionResult.SUCCESS);
    }
}
