package io.github.adamsonyanik;

import io.github.adamsonyanik.recipes.MiningTurtleRecipe;
import io.github.adamsonyanik.recipes.TurtleRecipe;
import io.github.adamsonyanik.stackmachine.Instruction;
import io.github.adamsonyanik.stackmachine.OP;
import io.github.adamsonyanik.stackmachine.StackMachine;
import org.bukkit.plugin.java.JavaPlugin;

public class MCTurtles extends JavaPlugin {

    public static MCTurtles plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getServer().addRecipe(new TurtleRecipe());
        getServer().addRecipe(new MiningTurtleRecipe());

        getServer().getPluginManager().registerEvents(new TurtlePlaceListener(), this);
        getServer().getPluginManager().registerEvents(new TurtleClickListener(), this);
        getServer().getPluginManager().registerEvents(new TurtleLoadListener(), this);

        StackMachine sm = new StackMachine(new Instruction[]{new Instruction(OP.push, 1), new Instruction(OP.push, 2), new Instruction(OP.iadd), new Instruction(OP.iprint)}, new int[]{}, 0, 0, 0);
        while (sm.exec()) {
        }
    }

    @Override
    public void onDisable() {
        Turtle.unloadTurtles();
    }
}
