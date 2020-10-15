package net.thirdshift.paulstweaks.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.thirdshift.paulstweaks.PaulsTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {

	@Inject(method="onBreak", at = @At("TAIL"))
	public void interceptOnBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo info){
		if(world.isClient())
			return;
		if (!player.getMainHandStack().isEmpty() && player.getMainHandStack().hasEnchantments()){
			if (player.getMainHandStack().hasEnchantments() && !player.isCreative()) {
				if (EnchantmentHelper.getLevel(PaulsTweaks.STONE_MENDING, player.getMainHandStack()) > 0 || EnchantmentHelper.getLevel(PaulsTweaks.NETHER_MENDING, player.getMainHandStack()) > 0) {
					ItemStack item = player.getMainHandStack();
					int mendLevel;
					if (EnchantmentHelper.getLevel(PaulsTweaks.STONE_MENDING, item) > 0) {
						mendLevel = EnchantmentHelper.getLevel(PaulsTweaks.STONE_MENDING, item);
						if (state.getBlock().isIn(BlockTags.BASE_STONE_OVERWORLD))
							mendItem(item, mendLevel);
					} else {
						mendLevel = EnchantmentHelper.getLevel(PaulsTweaks.NETHER_MENDING, item);
						if (state.getBlock().isIn(BlockTags.BASE_STONE_NETHER))
							mendItem(item, mendLevel);
					}
				}
			}
		}
	}

	private static void mendItem(ItemStack item, int mendLevel){
		switch(mendLevel){
			case 1: if (Math.random() > 0.30) return;
			case 2: if (Math.random() > 0.60) return;
			case 3: if (Math.random() > 0.90) return;
		}
		item.setDamage(item.getDamage()-2);
	}
}
