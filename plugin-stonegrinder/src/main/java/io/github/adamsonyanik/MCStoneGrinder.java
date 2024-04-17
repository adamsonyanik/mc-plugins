package io.github.adamsonyanik;

import org.bukkit.plugin.java.JavaPlugin;

public class MCStoneGrinder extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new GrindEventListener(), this);
    }
}
