package net.thirdshift.paulstweaks;

import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.thirdshift.paulstweaks.enchantments.NetherMending;
import net.thirdshift.paulstweaks.enchantments.StoneMending;
import net.thirdshift.paulstweaks.item.RemoveTotemItem;
import net.thirdshift.paulstweaks.recipe.RecipeJSON;

import java.util.HashMap;

public class PaulsTweaks implements ModInitializer {
	public static final String MOD_ID = "paulstweaks";
	public static final String TAG_SPAWNER_DATA = "SilkSpawnerData";
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

	public static Enchantment STONE_MENDING;
	public static Enchantment NETHER_MENDING;

	public static RemoveTotemItem TOTEM_OF_REMOVAL;
	public static Tag<Item> TOTEM_REMOVAL_TAG;

	@Override
	public void onInitialize() {
		TOTEM_REMOVAL_TAG = TagRegistry.item(new Identifier( MOD_ID, "totem_removal"));
		TOTEM_OF_REMOVAL = Registry.register(Registry.ITEM, new Identifier( MOD_ID, "totem_of_removal"), new RemoveTotemItem());

		STONE_MENDING = Registry.register(Registry.ENCHANTMENT, new Identifier( MOD_ID, "stone_mending"), (Enchantment) new StoneMending());
		NETHER_MENDING = Registry.register(Registry.ENCHANTMENT, new Identifier( MOD_ID, "nether_mending"), (Enchantment) new NetherMending());

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
				Identifier identifier = new Identifier( MOD_ID, name.toString());
				JsonObject recipe = recipeJSON.createStonecuttingRecipeJson("minecraft:"+woodType+"_planks", "minecraft:"+woodType+"_"+building, result);

				ModdedRecipies.put(identifier, recipe);
			}
		}
	}

}
