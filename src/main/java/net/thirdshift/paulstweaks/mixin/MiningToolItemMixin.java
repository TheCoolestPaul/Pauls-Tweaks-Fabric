package net.thirdshift.paulstweaks.mixin;

import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin {
    @ModifyConstant(method = "postHit", constant = @Constant(intValue = 2))
    public int modifyDamage(int damage){
        return 1;
    }
}
