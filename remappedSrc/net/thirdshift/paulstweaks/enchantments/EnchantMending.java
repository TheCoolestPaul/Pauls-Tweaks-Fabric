package net.thirdshift.paulstweaks.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class EnchantMending extends Enchantment {
    public EnchantMending() {
        super(Rarity.UNCOMMON, EnchantmentTarget.DIGGER, EquipmentSlot.values());
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}
