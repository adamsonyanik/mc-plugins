package io.github.adamsonyanik;

import java.nio.file.Paths;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MCAutoReload extends JavaPlugin {
    private DirectoryWatcher watcher;

    @Override
    public void onEnable() {
        var plugins = Paths.get(this.getDataFolder().getAbsolutePath()).getParent();
        this.watcher = new DirectoryWatcher(plugins);
        watcher.onFileChanged((path) -> {
          var file = path.getFileName();
          if (file.toString().endsWith(".jar")) {
            Bukkit.broadcastMessage("[plugin-autoreload]: detected change in: " + file);
            Bukkit.getScheduler().cancelTasks(this);
            Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
              Bukkit.broadcastMessage("[plugin-autoreload]: performing reload ...");
              Bukkit.reload();
              Bukkit.broadcastMessage("[plugin-autoreload]: reload complete");
            }, 20);
          }
        });
        watcher.start();
    }

    @Override
    public void onDisable() {
        this.watcher.stop();
    }
}
