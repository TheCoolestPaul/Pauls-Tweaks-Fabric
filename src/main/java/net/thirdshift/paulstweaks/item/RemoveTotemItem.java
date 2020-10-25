package net.thirdshift.paulstweaks.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;

public class RemoveTotemItem extends Item {

	public RemoveTotemItem() {
		super(new FabricItemSettings().group(ItemGroup.MISC).maxCount(1).rarity(Rarity.UNCOMMON));
	}

}
