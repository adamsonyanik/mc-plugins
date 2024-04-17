package io.github.adamsonyanik;

import org.bukkit.Material;

public class ItemPicker {

    public static Material getItem(String name) {
        return itemList[Math.abs(name.hashCode()) % itemList.length];
    }

    private static final Material[] itemList = new Material[]{
            Material.WHITE_WOOL,
            Material.ORANGE_WOOL,
            Material.MAGENTA_WOOL,
            Material.LIGHT_BLUE_WOOL,
            Material.YELLOW_WOOL,
            Material.LIME_WOOL,
            Material.PINK_WOOL,
            Material.GRAY_WOOL,
            Material.LIGHT_GRAY_WOOL,
            Material.CYAN_WOOL,
            Material.PURPLE_WOOL,
            Material.BLUE_WOOL,
            Material.BROWN_WOOL,
            Material.GREEN_WOOL,
            Material.RED_WOOL,
            Material.BLACK_WOOL,

            Material.APPLE,
            Material.MELON_SLICE,
            Material.PUMPKIN,
            Material.SWEET_BERRIES,
            Material.CARROT,
            Material.BAKED_POTATO,
            Material.BEETROOT,
            Material.BEEF,
            Material.COOKED_PORKCHOP,
            Material.COOKED_MUTTON,
            Material.COOKED_CHICKEN,
            Material.COOKED_RABBIT,
            Material.TROPICAL_FISH,
            Material.BREAD,
            Material.COOKIE,
            Material.CAKE,
            Material.WHEAT,

            Material.MILK_BUCKET,
            Material.LAVA_BUCKET,
            Material.WATER_BUCKET,

            Material.WHITE_BANNER,
            Material.ORANGE_BANNER,
            Material.MAGENTA_BANNER,
            Material.LIGHT_BLUE_BANNER,
            Material.YELLOW_BANNER,
            Material.LIME_BANNER,
            Material.PINK_BANNER,
            Material.GRAY_BANNER,
            Material.LIGHT_GRAY_BANNER,
            Material.CYAN_BANNER,
            Material.PURPLE_BANNER,
            Material.BLUE_BANNER,
            Material.BROWN_BANNER,
            Material.GREEN_BANNER,
            Material.RED_BANNER,
            Material.BLACK_BANNER,

            Material.WOODEN_SWORD,
            Material.WOODEN_SHOVEL,
            Material.WOODEN_PICKAXE,
            Material.WOODEN_AXE,
            Material.WOODEN_HOE,
            Material.STONE_SWORD,
            Material.STONE_SHOVEL,
            Material.STONE_PICKAXE,
            Material.STONE_AXE,
            Material.STONE_HOE,
            Material.GOLDEN_SWORD,
            Material.GOLDEN_SHOVEL,
            Material.GOLDEN_PICKAXE,
            Material.GOLDEN_AXE,
            Material.GOLDEN_HOE,
            Material.IRON_SWORD,
            Material.IRON_SHOVEL,
            Material.IRON_PICKAXE,
            Material.IRON_AXE,
            Material.IRON_HOE,
            Material.DIAMOND_SWORD,
            Material.DIAMOND_SHOVEL,
            Material.DIAMOND_PICKAXE,
            Material.DIAMOND_AXE,
            Material.DIAMOND_HOE,
            Material.NETHERITE_SWORD,
            Material.NETHERITE_SHOVEL,
            Material.NETHERITE_PICKAXE,
            Material.NETHERITE_AXE,
            Material.NETHERITE_HOE,
            Material.STICK,

            Material.COAL,
            Material.IRON_INGOT,
            Material.GOLD_INGOT,
            Material.COPPER_INGOT,
            Material.NETHERITE_INGOT,
            Material.DIAMOND,
            Material.LAPIS_LAZULI,
            Material.REDSTONE,
            Material.EMERALD,

            Material.COAL_BLOCK,
            Material.IRON_BLOCK,
            Material.GOLD_BLOCK,
            Material.COPPER_BLOCK,
            Material.NETHERITE_BLOCK,
            Material.DIAMOND_BLOCK,
            Material.LAPIS_BLOCK,
            Material.REDSTONE_BLOCK,
            Material.EMERALD_BLOCK,

            Material.SLIME_BALL,
            Material.FIRE_CHARGE,
            Material.BLAZE_ROD,
            Material.NETHER_STAR,
            Material.ENDER_EYE,

            Material.WHITE_DYE,
            Material.ORANGE_DYE,
            Material.MAGENTA_DYE,
            Material.LIGHT_BLUE_DYE,
            Material.YELLOW_DYE,
            Material.LIME_DYE,
            Material.PINK_DYE,
            Material.GRAY_DYE,
            Material.LIGHT_GRAY_DYE,
            Material.CYAN_DYE,
            Material.PURPLE_DYE,
            Material.BLUE_DYE,
            Material.BROWN_DYE,
            Material.GREEN_DYE,
            Material.RED_DYE,
            Material.BLACK_DYE,
    };

}
