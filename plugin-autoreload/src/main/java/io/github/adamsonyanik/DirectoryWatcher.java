package io.github.adamsonyanik;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class DirectoryWatcher {
  private final Path directory;
  private final Set<Consumer<Path>> subscribers;
  private WatchService watchService;

  public DirectoryWatcher(Path directory) {
    this.directory = directory;
    this.subscribers = new HashSet<>();
  }

  public void onFileChanged(Consumer<Path> callback) {
    this.subscribers.add(callback);
  }

  public synchronized void stop() {
    if (this.watchService == null) throw new IllegalStateException("watcher not started");
    try {
      this.watchService.close();
      this.watchService = null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public synchronized void start() {
    if (this.watchService != null) throw new IllegalStateException("watcher already started");
    try {
      this.watchService = FileSystems.getDefault().newWatchService();
      this.directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
          StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    new Thread(this::pollUntilStop).start();
  }

  private void pollUntilStop() {
    try {
      while (true) {
        WatchKey key = watchService.take();
        Path path = (Path) key.watchable();
        for (var event : key.pollEvents()) {
          var file = path.resolve((Path) event.context());
          for (var subscriber : this.subscribers) {
            subscriber.accept(file);
          }
        }
        key.reset();
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ClosedWatchServiceException e) {
      // do nothing. expect that the watch service was gracefully closed.
    }
  }
}
