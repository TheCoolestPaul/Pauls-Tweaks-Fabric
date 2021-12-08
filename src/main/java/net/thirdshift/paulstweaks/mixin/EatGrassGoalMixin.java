package net.thirdshift.paulstweaks.mixin;

import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SheepEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EatGrassGoal.class)
public class EatGrassGoalMixin {

    @Shadow @Final private MobEntity mob;

    @Inject(method = "canStart", at = @At("RETURN"), cancellable = true)
    public void interceptCanStart(CallbackInfoReturnable<Boolean> cir){
        if (this.mob instanceof SheepEntity && !((SheepEntity)this.mob).isSheared()){
            cir.setReturnValue(false);
        }
    }
}
