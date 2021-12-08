package net.thirdshift.paulstweaks.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class StoneMending extends Enchantment {
    public StoneMending() {
        super(Rarity.UNCOMMON, EnchantmentTarget.DIGGER, EquipmentSlot.values());
    }

}
