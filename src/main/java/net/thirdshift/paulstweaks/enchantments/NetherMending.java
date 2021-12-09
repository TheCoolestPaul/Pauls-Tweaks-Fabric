package net.thirdshift.paulstweaks.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class NetherMending extends Enchantment {
    public NetherMending() {
        super(Rarity.UNCOMMON, EnchantmentTarget.DIGGER, EquipmentSlot.values());
    }
}
