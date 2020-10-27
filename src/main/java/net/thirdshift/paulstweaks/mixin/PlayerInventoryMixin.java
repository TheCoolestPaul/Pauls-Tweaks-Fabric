package net.thirdshift.paulstweaks.mixin;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
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

	@Shadow public abstract int getSlotWithStack(ItemStack stack);

	@Inject(at = @At("INVOKE"), method = "addStack(Lnet/minecraft/item/ItemStack;)I")
	private void addStackInvoke(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
		if (contains(PaulsTweaks.TOTEM_OF_REMOVAL.getDefaultStack())){
			int slotId = getSlotWithStack(PaulsTweaks.TOTEM_OF_REMOVAL.getDefaultStack());
			if (slotId==-1){
				return; // Do nothing if it's in offhand
			}
			if (stack.getItem().isIn(PaulsTweaks.TOTEM_REMOVAL_TAG)){
				stack.setCount(0);
			}
		}
	}

}
