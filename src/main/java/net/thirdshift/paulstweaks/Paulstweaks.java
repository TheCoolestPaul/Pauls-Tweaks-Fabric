package net.thirdshift.paulstweaks;

import com.google.gson.JsonObject;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.HayBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.item.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.thirdshift.paulstweaks.enchantments.EnchantMending;
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
    public static final Item SOUL_GEM_CORE = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item COPPER_RING = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item IRON_RING = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item GOLD_RING = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Item NETHERITE_RING = new Item(new FabricItemSettings().group(ItemGroup.MISC));
    public static final Block SUGAR_CANE_BLOCK = new HayBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.PALE_GREEN).strength(0.5f).sounds(BlockSoundGroup.GRASS));
    public static final String MOD_ID = "paulstweaks";
    public static final RecipeJSON recipeJSON = new RecipeJSON();
    public static HashMap<Identifier, JsonObject> ModdedRecipes = new HashMap<>();
    public static final Enchantment STONE_MENDING = new EnchantMending();
    public static final Enchantment NETHER_MENDING = new EnchantMending();
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

    public static void mendTool(World world, ItemStack stack, int level) {
        int random = world.getRandom().nextInt(10);
        if (level==1) {
            if (random<=3) {
                stack.setDamage(stack.getDamage() - 3);
            }
        } else if(level==2) {
            if (random<=6) {
                stack.setDamage(stack.getDamage() - 4);
            }
        } else {
            stack.setDamage(stack.getDamage()-5);
        }
    }

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "copper_soulgem"), COPPER_SOUL_GEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "iron_soulgem"), IRON_SOUL_GEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "gold_soulgem"), GOLD_SOUL_GEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "netherite_soulgem"), NETHERITE_SOUL_GEM);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "copper_soulgem_ring"), COPPER_RING);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "iron_soulgem_ring"), IRON_RING);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "gold_soulgem_ring"), GOLD_RING);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "netherite_soulgem_ring"), NETHERITE_RING);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "soulgem_core"), SOUL_GEM_CORE);

        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "sugar_cane_block"), SUGAR_CANE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "sugar_cane_block"), new BlockItem(SUGAR_CANE_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

        Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "stone_mending"), STONE_MENDING);
        Registry.register(Registry.ENCHANTMENT, new Identifier(MOD_ID, "nether_mending"), NETHER_MENDING);

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
        PlayerBlockBreakEvents.AFTER.register(((world, player, pos, state, blockEntity) -> {
            if (world.getServer()!=null) {
                if (BlockTags.BASE_STONE_OVERWORLD.contains(state.getBlock())) {
                    ItemStack itemStack = player.getMainHandStack();
                    if (itemStack != null && EnchantmentHelper.getLevel(STONE_MENDING, itemStack) > 0) {
                        mendTool(world, itemStack, EnchantmentHelper.getLevel(STONE_MENDING, itemStack));
                    }
                } else if (BlockTags.BASE_STONE_NETHER.contains(state.getBlock())) {
                    ItemStack itemStack = player.getMainHandStack();
                    if (itemStack != null && EnchantmentHelper.getLevel(NETHER_MENDING, itemStack) > 0){
                        mendTool(world, itemStack, EnchantmentHelper.getLevel(NETHER_MENDING, itemStack));
                    }
                }
            }
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
