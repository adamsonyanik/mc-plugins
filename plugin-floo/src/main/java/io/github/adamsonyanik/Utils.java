package io.github.adamsonyanik;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class Utils {

    public static float getYaw(BlockFace blockFace) {
        switch (blockFace) {
            case UP, DOWN, SOUTH:
                return 0;
            case NORTH:
                return 180;
            case EAST:
                return 270;
            case WEST:
                return 90;
        }
        return 0;
    }

    public static Vector yawToVector(float pitch) {
        switch ((int) pitch) {
            case 0:
                return new Vector(0, 0, 1);
            case 90:
                return new Vector(-1, 0, 0);
            case 180:
                return new Vector(0, 0, -1);
            case 270:
                return new Vector(1, 0, 0);
        }
        return null;
    }

    public static Location getFurnaceLocationFromBlockWithYaw(Block block) {
        Location furnaceLocation = block.getLocation();
        furnaceLocation.setYaw(Utils.getYaw(((Directional) block.getBlockData()).getFacing()));
        return furnaceLocation;
    }


    public static void teleportToFurnace(String name, Player player) {
        boolean couldRemoveItem = false;
        for (int i = 0; i < player.getInventory().getSize(); i++)
            if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType() == Material.GLOWSTONE_DUST) {
                player.getInventory().getItem(i).setAmount(player.getInventory().getItem(i).getAmount() - 1);
                couldRemoveItem = true;
            }

        if (!couldRemoveItem) {
            player.sendMessage("you need some glowstone dust to teleport");
            return;
        }

        Location location = NetworkRepo.get().get(name).toLocation();
        location = location.add(Utils.yawToVector(location.getYaw()));

        Block currentBlock = location.getBlock();
        for (int i = 0; i < 4; i++) {
            currentBlock = currentBlock.getRelative(BlockFace.DOWN);
            if (currentBlock.getType() != Material.AIR)
                break;
            location = location.add(0, -1, 0);
        }
        location = location.add(0.5, 0, 0.5);

        Location start = player.getLocation();
        player.teleport(location);

        start.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, start.add(0, 1, 0), 500);
        start.getWorld().playSound(start, Sound.BLOCK_WOOD_BREAK, 3f, 5f);
        location.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, location.add(0, 1, 0), 500);
        location.getWorld().playSound(location, Sound.BLOCK_WOOD_BREAK, 3f, 5f);
    }
}
