package net.thirdshift.paulstweaks.mixin;

import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ComposterBlock.class)
public class ComposterMixin {

	@Shadow
	private static void registerCompostableItem(float levelIncreaseChance, ItemConvertible item) {
	}

	@Inject(at = @At("TAIL"), method = "registerDefaultCompostableItems")
	private static void interceptRegisterDefaultCompostableItems(CallbackInfo info){
		registerCompostableItem(0.60F, Items.ROTTEN_FLESH);
		registerCompostableItem(0.35F, Items.SPIDER_EYE);
		registerCompostableItem(0.45F, Items.COD);
		registerCompostableItem(0.45F, Items.SALMON);
		registerCompostableItem(0.45F, Items.TROPICAL_FISH);
		registerCompostableItem(0.45F, Items.PUFFERFISH);
	}

}
