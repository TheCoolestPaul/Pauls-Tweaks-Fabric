package net.thirdshift.paulstweaks.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.thirdshift.paulstweaks.PaulsTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

	@Shadow public abstract ItemStack getEquippedStack(EquipmentSlot slot);

	@ModifyVariable(method = "addExperience(I)V", at = @At("HEAD"), ordinal = 0)
	private int interceptExperience(int experience){
		ItemStack mainStack = getEquippedStack(EquipmentSlot.MAINHAND);
		if (mainStack.isEmpty() || EnchantmentHelper.getLevel(PaulsTweaks.ENLIGHTENMENT, mainStack) <= 0)
			return experience;
		return experience+(experience*EnchantmentHelper.getLevel(PaulsTweaks.ENLIGHTENMENT, mainStack));
	}

}
