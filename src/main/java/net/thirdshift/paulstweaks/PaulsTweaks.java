package net.thirdshift.paulstweaks;

import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.thirdshift.paulstweaks.enchantments.NetherMending;
import net.thirdshift.paulstweaks.enchantments.StoneMending;
import net.thirdshift.paulstweaks.recipe.RecipeJSON;

import java.util.HashMap;

public class PaulsTweaks implements ModInitializer {
	public static final RecipeJSON recipeJSON = new RecipeJSON();
	public static HashMap<Identifier, JsonObject> ModdedRecipies = new HashMap<>();
	public static final String[] woodTypes = new String[]{
			"oak",
			"spruce",
			"birch",
			"jungle",
			"acacia",
			"dark_oak",
			"crimson",
			"warped"
	};

	private static Enchantment STONE_MENDING;
	private static Enchantment NETHER_MENDING;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		STONE_MENDING = Registry.register(Registry.ENCHANTMENT, new Identifier("paulstweaks", "stone_mending"), (Enchantment) new StoneMending());
		NETHER_MENDING = Registry.register(Registry.ENCHANTMENT, new Identifier("paulstweaks", "nether_mending"), (Enchantment) new NetherMending());

		for (String woodType : woodTypes){
			for (int i = 0; i < 4; i++) {
				StringBuilder name = new StringBuilder();
				String building;
				int result;
				name.append(woodType).append("_planks");
				if(i==0){
					building = "stairs";
					result = 1;
				}else if(i==1){
					building = "slab";
					result = 2;
				}else if(i==2){
					building = "pressure_plate";
					result = 4;
				}else{
					building = "button";
					result = 10;
				}
				name.append("_").append(building).append("_");
				name.append("from_stonecutting");
				Identifier identifier = new Identifier("paulstweaks", name.toString());
				JsonObject recipe = recipeJSON.createStonecuttingRecipeJson("minecraft:"+woodType+"_planks", "minecraft:"+woodType+"_"+building, result);

				ModdedRecipies.put(identifier, recipe);
			}
		}

		PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
			if (player.getMainHandStack() != ItemStack.EMPTY && player.getMainHandStack().hasEnchantments() && !player.isCreative()){
				if (EnchantmentHelper.getLevel(STONE_MENDING, player.getMainHandStack())>0 || EnchantmentHelper.getLevel(NETHER_MENDING, player.getMainHandStack())>0) {
					ItemStack item = player.getMainHandStack();
					int mendLevel;
					if (EnchantmentHelper.getLevel(STONE_MENDING, item)>0){
						mendLevel = EnchantmentHelper.getLevel(STONE_MENDING, item);
						if (state.getBlock().isIn(BlockTags.BASE_STONE_OVERWORLD))
							mendItem(item, mendLevel);
					} else {
						mendLevel = EnchantmentHelper.getLevel(NETHER_MENDING, item);
						if (state.getBlock().isIn(BlockTags.BASE_STONE_NETHER))
							mendItem(item, mendLevel);
					}
				}
			}
		});
	}

	private static void mendItem(ItemStack item, int mendLevel){
		switch(mendLevel){
			case 1: if (Math.random() > 0.30) return;
			case 2: if (Math.random() > 0.60) return;
			case 3: if (Math.random() > 0.90) return;
		}
		item.setDamage(item.getDamage()-2);
	}

}
