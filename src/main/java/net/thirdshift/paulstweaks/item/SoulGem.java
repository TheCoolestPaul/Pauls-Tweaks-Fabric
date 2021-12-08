package net.thirdshift.paulstweaks.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;

public class SoulGem extends Item {
    private final int MAX_LEVELS;
    public SoulGem(int maxLevel) {
        super(new FabricItemSettings().group(ItemGroup.MISC).fireproof().maxCount(1));
        this.MAX_LEVELS = maxLevel;
    }

    public int getMAX_LEVELS() {
        return MAX_LEVELS;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(context.getPlayer()!=null && context.getWorld().getServer()!=null){
            ItemStack itemStack = context.getPlayer().getStackInHand(context.getHand());
            itemStack.addHideFlag(ItemStack.TooltipSection.ENCHANTMENTS);
            if (context.getWorld().getBlockState(context.getBlockPos()).getBlock().equals(Blocks.ENCHANTING_TABLE)) {
                if (itemStack.getNbt() == null || !itemStack.getNbt().getBoolean("hasLevels")) {
                    if (context.getPlayer().isCreative() || context.getPlayer().experienceLevel >= getMAX_LEVELS()) {
                        if (itemStack.getNbt() != null) {
                            itemStack.getNbt().putBoolean("hasLevels", true);
                        } else {
                            NbtCompound nbt = new NbtCompound();
                            nbt.putBoolean("hasLevels", true);
                            itemStack.writeNbt(nbt);
                            itemStack.setNbt(itemStack.writeNbt(nbt));
                        }
                        itemStack.addEnchantment(Enchantments.LUCK_OF_THE_SEA, 1);
                        context.getPlayer().addExperienceLevels(-getMAX_LEVELS());
                        return ActionResult.SUCCESS;
                    }
                }
            } else {
                if (itemStack.getNbt() != null && itemStack.getNbt().getBoolean("hasLevels")) {
                    itemStack.getNbt().remove("hasLevels");
                    itemStack.removeSubNbt("Enchantments");
                    itemStack.removeSubNbt("StoredEnchantments");
                    context.getPlayer().addExperienceLevels(getMAX_LEVELS());
                    return ActionResult.SUCCESS;
                }
            }
        }
        return super.useOnBlock(context);
    }
}
