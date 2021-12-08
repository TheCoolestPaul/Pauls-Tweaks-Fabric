package net.thirdshift.paulstweaks.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SoulGem extends Item {
    private final int MAX_LEVELS;
    public SoulGem(int maxLevel) {
        super(new FabricItemSettings().group(ItemGroup.MISC).fireproof());
        this.MAX_LEVELS = maxLevel;
    }

    public int getMAX_LEVELS() {
        return MAX_LEVELS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getNbt() != null && itemStack.getNbt().getBoolean("hasLevels")){
            itemStack.getNbt().putBoolean("hasLevel", false);
            user.addExperienceLevels(getMAX_LEVELS());
            return TypedActionResult.success(user.getStackInHand(hand));
        }
        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getWorld().getBlockState(context.getBlockPos()).getBlock().equals(Blocks.ENCHANTING_TABLE) && context.getPlayer()!=null){
            ItemStack itemStack = context.getPlayer().getStackInHand(context.getHand());
            if (itemStack.getNbt() != null && itemStack.getNbt().getBoolean("hasLevels")) {
                if (context.getPlayer().experienceLevel >= getMAX_LEVELS() || context.getPlayer().isCreative()) {
                    itemStack.getNbt().putBoolean("hasLevels", true);
                    context.getPlayer().experienceLevel-=getMAX_LEVELS();
                    return ActionResult.SUCCESS;
                }
            }
        }
        return super.useOnBlock(context);
    }
}
