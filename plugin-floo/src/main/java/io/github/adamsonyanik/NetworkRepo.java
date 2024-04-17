package io.github.adamsonyanik;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class NetworkRepo {

    private static NetworkRepo instance;
    private static Map<String, FurnaceLocation> locations = new HashMap<>();
    private static Gson gson = new Gson();
    private static String filePath;

    public static NetworkRepo get() {
        return instance;
    }

    public static void init(Plugin plugin) {
        try {
            instance = new NetworkRepo(plugin);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private NetworkRepo(Plugin plugin) throws IOException {
        File folder = plugin.getDataFolder();
        if (!folder.exists()) folder.mkdirs();

        filePath = plugin.getDataFolder() + File.separator + "network.json";

        File networkFile = new File(filePath);
        if (!networkFile.exists())
            networkFile.createNewFile();

        JsonReader reader = new JsonReader(new FileReader(filePath));
        Type listType = new TypeToken<ArrayList<FurnaceLocation>>() {
        }.getType();
        ArrayList<FurnaceLocation> furnaceLocations = gson.fromJson(reader, listType);

        if (furnaceLocations != null)
            for (FurnaceLocation loc : furnaceLocations)
                add(loc.id(), loc.name(), loc.location());
    }

    public void add(String name, Location location) {
        FurnaceLocation[] all = all();
        add(all.length > 0 ? all[all.length - 1].id() + 1 : 0, name, location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch());
    }

    private void add(int id, String name, String locationString) {
        locations.put(locationString, new FurnaceLocation(id, name, locationString));
        save();
    }

    public FurnaceLocation[] all() {
        return locations.values().stream().sorted(Comparator.comparing(FurnaceLocation::id)).toArray(FurnaceLocation[]::new);
    }

    public boolean has(Location location) {
        return locations.containsKey(location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch());
    }

    public boolean has(String name) {
        return locations.values().stream().anyMatch(l -> l.name().equals(name));
    }

    public int size() {
        return locations.size();
    }

    public void remove(Location location) {
        locations.remove(location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch());
        save();
    }

    public FurnaceLocation get(String name) {
        return locations.values().stream().filter(l -> l.name().equals(name)).findFirst().orElseThrow();
    }

    private void save() {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(all(), writer);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
