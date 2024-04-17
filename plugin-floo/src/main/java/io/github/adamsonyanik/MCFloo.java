package io.github.adamsonyanik;

import org.bukkit.plugin.java.JavaPlugin;

public class MCFloo extends JavaPlugin {
    @Override
    public void onEnable() {
        NetworkRepo.init(this);

        getServer().getPluginManager().registerEvents(new FurnaceManipulateListener(), this);
        getServer().getPluginManager().registerEvents(new FlooNetworkAttachListener(this), this);
        getServer().getPluginManager().registerEvents(new FlooTeleportListener(this), this);
    }
}
