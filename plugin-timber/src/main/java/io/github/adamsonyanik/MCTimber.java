package io.github.adamsonyanik;

import org.bukkit.plugin.java.JavaPlugin;

public class MCTimber extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new LogBreakEventListener(), this);
    }
}
