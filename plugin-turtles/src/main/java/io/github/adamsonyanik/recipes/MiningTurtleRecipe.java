package io.github.adamsonyanik.recipes;

import io.github.adamsonyanik.CustomHeads;
import io.github.adamsonyanik.MCTurtles;
import io.github.adamsonyanik.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;

public class MiningTurtleRecipe extends ShapedRecipe {
    public MiningTurtleRecipe() {
        super(new NamespacedKey(MCTurtles.plugin, "mining_turtle"), Utils.createSkull("Mining Turtle", "mining_turtle", CustomHeads.MINING_TURTLE));

        this.shape("R ", "CT", "F ");
        this.setIngredient('R', Material.REDSTONE);
        this.setIngredient('C', Material.CHEST);
        this.setIngredient('F', Material.FURNACE);
        this.setIngredient('T', Material.DIAMOND_PICKAXE);
    }
}
