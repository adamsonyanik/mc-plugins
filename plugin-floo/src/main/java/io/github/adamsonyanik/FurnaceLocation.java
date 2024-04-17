package io.github.adamsonyanik;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public record FurnaceLocation(int id, String name, String location) {

    Location toLocation() {
        String[] args = location().split(",");
        return new Location(Bukkit.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
    }
}

