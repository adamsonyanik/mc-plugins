package io.github.adamsonyanik;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.meta.SkullMeta;

public class TurtlePlaceListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getItemInHand().getType() != Material.PLAYER_HEAD || !((SkullMeta) event.getItemInHand().getItemMeta()).hasOwner())
            return;

        String name = ((SkullMeta) event.getItemInHand().getItemMeta()).getOwnerProfile().getName();

        if (!name.startsWith("mc-turtles-plugin-"))
            return;

        Location location = event.getBlock().getLocation();
        if (event.getBlock().getType() == Material.PLAYER_HEAD)
            switch (((Rotatable) event.getBlock().getBlockData()).getRotation()) {
                case NORTH_NORTH_WEST, NORTH, NORTH_NORTH_EAST, NORTH_EAST -> location.setYaw(0);
                case EAST_NORTH_EAST, EAST, EAST_SOUTH_EAST, SOUTH_EAST -> location.setYaw(90);
                case SOUTH_SOUTH_EAST, SOUTH, SOUTH_SOUTH_WEST, SOUTH_WEST -> location.setYaw(180);
                case WEST_SOUTH_WEST, WEST, WEST_NORTH_WEST, NORTH_WEST -> location.setYaw(270);
            }
        else
            switch (((Directional) event.getBlock().getBlockData()).getFacing()) {
                case SOUTH -> location.setYaw(0);
                case WEST -> location.setYaw(90);
                case NORTH -> location.setYaw(180);
                case EAST -> location.setYaw(270);
            }

        event.getBlock().setType(Material.AIR);

        Turtle.spawn(location);
    }
}
