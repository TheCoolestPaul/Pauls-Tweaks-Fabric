package net.thirdshift.paulstweaks;

import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;
import net.thirdshift.paulstweaks.item.SoulGem;
import net.thirdshift.paulstweaks.util.RecipeJSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class Paulstweaks implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Item COPPER_SOUL_GEM = new SoulGem(5);
    public static final Item IRON_SOUL_GEM = new SoulGem(10);
    public static final Item GOLD_SOUL_GEM = new SoulGem(15);
    public static final Item NETHERITE_SOUL_GEM = new SoulGem(30);
    public static final String MOD_ID = "paulstweaks";
    public static final RecipeJSON recipeJSON = new RecipeJSON();
    public static HashMap<Identifier, JsonObject> ModdedRecipes = new HashMap<>();
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

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "copper_soulgem"), COPPER_SOUL_GEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "iron_soulgem"), IRON_SOUL_GEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "gold_soulgem"), GOLD_SOUL_GEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "netherite_soulgem"), NETHERITE_SOUL_GEM);
        UseEntityCallback.EVENT.register(((player, world, hand, entity, hitResult) -> {
            ItemStack itemStack = player.getStackInHand(hand);
            if (entity instanceof FishEntity) {
                if (itemStack.isItemEqualIgnoreDamage(Items.WHEAT_SEEDS.getDefaultStack())){
                    if (!(world instanceof ServerWorld)) {
                        return ActionResult.SUCCESS;
                    } else {
                        player.getStackInHand(hand).decrement(1);
                        if (entity.getType().spawnFromItemStack((ServerWorld) world, itemStack, player, entity.getBlockPos(), SpawnReason.BREEDING, false, false) == null) {
                            return ActionResult.PASS;
                        } else {
                            if (!player.getAbilities().creativeMode) {
                                itemStack.decrement(1);
                            }
                            world.emitGameEvent(GameEvent.ENTITY_PLACE, player);
                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }
            return ActionResult.PASS;
        }));
        for (String woodType : woodTypes){
            for (int i = 0; i < 4; i++) {
                StringBuilder name = new StringBuilder();
                String building;
                int result;
                name.append(woodType).append("_planks");
                if(i==0){
                    building = "stairs";
                    result = 2;
                }else if(i==1){
                    building = "slab";
                    result = 2;
                }else if(i==2){
                    building = "pressure_plate";
                    result = 6;
                }else{
                    building = "button";
                    result = 10;
                }
                name.append("_").append(building).append("_");
                name.append("from_stonecutting");
                Identifier identifier = new Identifier( MOD_ID, name.toString());
                JsonObject recipe = recipeJSON.createStonecuttingRecipeJson("minecraft:"+woodType+"_planks", "minecraft:"+woodType+"_"+building, result);
                ModdedRecipes.put(identifier, recipe);
            }
        }
    }
}
