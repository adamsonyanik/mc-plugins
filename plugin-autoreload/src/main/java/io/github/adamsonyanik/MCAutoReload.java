package io.github.adamsonyanik;

import java.nio.file.Paths;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class MCAutoReload extends JavaPlugin {
    @Override
    public void onEnable() {
        var plugins = Paths.get(this.getDataFolder().getAbsolutePath()).getParent();
        var watcher = new DirectoryWatcher(plugins);
        watcher.onFileChanged(System.out::println);
        watcher.start();

        new Thread(() -> {
          try {
            watcher.start();
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }).start();
    }

    private static void rl() {
        Bukkit.broadcastMessage("reloading...");
        Bukkit.reload();
        Bukkit.broadcastMessage("reload complete");
    }
}
