package net.thirdshift.paulstweaks.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.collection.DefaultedList;
import net.thirdshift.paulstweaks.PaulsTweaks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {

	@Shadow @Final private List<DefaultedList<ItemStack>> combinedInventory;

	@Shadow public abstract boolean contains(ItemStack itemStack);

	@Inject(at = @At("INVOKE"), method = "addStack(Lnet/minecraft/item/ItemStack;)I")
	private void addStackInvoke(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
		if (contains(PaulsTweaks.TOTEM_OF_REMOVAL.getDefaultStack())){
			if (
						stack.isItemEqual(Items.COBBLESTONE.getDefaultStack()) ||
						stack.isItemEqual(Items.NETHERRACK.getDefaultStack()) ||
						stack.isItemEqual(Items.ANDESITE.getDefaultStack()) ||
						stack.isItemEqual(Items.DIORITE.getDefaultStack()) ||
						stack.isItemEqual(Items.GRANITE.getDefaultStack()) ||
						stack.isItemEqual(Items.BASALT.getDefaultStack())
				) {

				stack.setCount(0);
			}
		}
	}

}
