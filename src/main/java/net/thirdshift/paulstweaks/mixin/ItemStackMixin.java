package net.thirdshift.paulstweaks.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.thirdshift.paulstweaks.Paulstweaks.NETHER_MENDING;
import static net.thirdshift.paulstweaks.Paulstweaks.STONE_MENDING;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    private void mendTool(World world, ItemStack stack, int level) {
        int random = world.getRandom().nextInt(10);
        if (level==1) {
            if (random<=3) {
                stack.setDamage(stack.getDamage() - 3);
            }
        } else if(level==2) {
            if (random<=6) {
                stack.setDamage(stack.getDamage() - 6);
            }
        } else {
            stack.setDamage(stack.getDamage() - 10);
        }
    }

    @Inject(method = "postMine", at = @At("HEAD"))
    private void postMineIntercept(World world, BlockState state, BlockPos pos, PlayerEntity player, CallbackInfo ci){
        if (world.getServer()!=null) {
            if (BlockTags.BASE_STONE_OVERWORLD.contains(state.getBlock())) {
                ItemStack itemStack = player.getMainHandStack();
                if (itemStack != null && EnchantmentHelper.getLevel(STONE_MENDING, itemStack) > 0) {
                    mendTool(world, itemStack, EnchantmentHelper.getLevel(STONE_MENDING, itemStack));
                }
            } else if (BlockTags.BASE_STONE_NETHER.contains(state.getBlock())) {
                ItemStack itemStack = player.getMainHandStack();
                if (itemStack != null && EnchantmentHelper.getLevel(NETHER_MENDING, itemStack) > 0){
                    mendTool(world, itemStack, EnchantmentHelper.getLevel(NETHER_MENDING, itemStack));
                }
            }
        }
    }
}
