package io.github.adamsonyanik;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DispenseEventListener implements Listener {

    record GroundPlant(Material plant, List<Material> ground, boolean canFloat) {
    }

    private static final HashMap<Material, GroundPlant> items = new HashMap<>();

    static {
        items.put(Material.POTATO, new GroundPlant(Material.POTATOES, List.of(Material.FARMLAND), false));
        items.put(Material.CARROT, new GroundPlant(Material.CARROTS, List.of(Material.FARMLAND), false));
        items.put(Material.WHEAT_SEEDS, new GroundPlant(Material.WHEAT, List.of(Material.FARMLAND), false));
        items.put(Material.BEETROOT_SEEDS, new GroundPlant(Material.BEETROOTS, List.of(Material.FARMLAND), false));
        items.put(Material.MELON_SEEDS, new GroundPlant(Material.MELON_STEM, List.of(Material.FARMLAND), false));
        items.put(Material.PUMPKIN_SEEDS, new GroundPlant(Material.PUMPKIN_STEM, List.of(Material.FARMLAND), false));

        items.put(Material.ACACIA_SAPLING, new GroundPlant(Material.ACACIA_SAPLING, List.of(Material.DIRT, Material.GRASS_BLOCK), false));
        items.put(Material.BIRCH_SAPLING, new GroundPlant(Material.BIRCH_SAPLING, List.of(Material.DIRT, Material.GRASS_BLOCK), false));
        items.put(Material.OAK_SAPLING, new GroundPlant(Material.OAK_SAPLING, List.of(Material.DIRT, Material.GRASS_BLOCK), false));
        items.put(Material.SPRUCE_SAPLING, new GroundPlant(Material.SPRUCE_SAPLING, List.of(Material.DIRT, Material.GRASS_BLOCK), false));
        items.put(Material.JUNGLE_SAPLING, new GroundPlant(Material.JUNGLE_SAPLING, List.of(Material.DIRT, Material.GRASS_BLOCK), false));
        items.put(Material.DARK_OAK_SAPLING, new GroundPlant(Material.DARK_OAK_SAPLING, List.of(Material.DIRT, Material.GRASS_BLOCK), false));
        items.put(Material.MANGROVE_PROPAGULE, new GroundPlant(Material.MANGROVE_PROPAGULE, List.of(Material.DIRT, Material.GRASS_BLOCK), false));
    }

    @EventHandler
    public void onBlockDispenseEvent(BlockDispenseEvent event) {
        if (event.getBlock().getType() != Material.DISPENSER) return;

        Material m = event.getItem().getType();
        
        if (items.containsKey(m)) {
            handleCrop(event, m);
        } else if (m.isBlock()) {
            Block targetBlock = event.getBlock().getRelative(getDirection(event.getVelocity()));

            if (targetBlock.getType() != Material.AIR) {
                event.setCancelled(true);
                return;
            }

            event.setCancelled(true);
            deleteItem(((Dispenser) event.getBlock().getState()).getInventory(), m);
            targetBlock.setType(m);
        }
    }

    private void handleCrop(BlockDispenseEvent event, Material m) {
        Block targetBlock = event.getBlock().getRelative(getDirection(event.getVelocity()));

        GroundPlant groundPlant = items.get(m);
        Location placeLocation = null;

        Material beneath = targetBlock.getRelative(BlockFace.DOWN).getType();
        Material above = targetBlock.getRelative(BlockFace.UP).getType();
        if ((targetBlock.getType() == Material.AIR || targetBlock.getType() == groundPlant.plant) && groundPlant.ground.contains(beneath)) {
            if (targetBlock.getType() == Material.AIR) {
                placeLocation = targetBlock.getLocation();
            } else {
                event.setCancelled(true);
                return;
            }
        } else if ((above == Material.AIR || above == groundPlant.plant) && groundPlant.ground.contains(targetBlock.getType())) {
            if (above == Material.AIR) {
                placeLocation = targetBlock.getRelative(BlockFace.UP).getLocation();
            } else {
                event.setCancelled(true);
                return;
            }
        } else {
            return;
        }

        event.setCancelled(true);
        deleteItem(((Dispenser) event.getBlock().getState()).getInventory(), m);
        event.getBlock().getWorld().getBlockAt(placeLocation).setType(groundPlant.plant);
    }

    private void deleteItem(Inventory inventory, Material material) {
        Bukkit.getScheduler().runTaskLater(MCAutoPlant.getPlugin(MCAutoPlant.class), () -> {
            inventory.removeItem(new ItemStack(material, 1));
        }, 1);
    }

    record BlockFaceScore(BlockFace face, double score) {
    }

    record BlockFaceVectors(BlockFace face, Vector vector) {
    }

    private static final List<BlockFaceVectors> faces = List.of(new BlockFaceVectors(BlockFace.UP, new Vector(0, 1, 0)),
            new BlockFaceVectors(BlockFace.DOWN, new Vector(0, -1, 0)),
            new BlockFaceVectors(BlockFace.NORTH, new Vector(0, 0, -1)),
            new BlockFaceVectors(BlockFace.EAST, new Vector(1, 0, 0)),
            new BlockFaceVectors(BlockFace.SOUTH, new Vector(0, 0, 1)),
            new BlockFaceVectors(BlockFace.WEST, new Vector(-1, 0, 0)));

    private BlockFace getDirection(Vector vector) {
        vector = vector.subtract(new Vector(0, 0.1f, 0));
        ArrayList<BlockFaceScore> vectors = new ArrayList<>();

        for (BlockFaceVectors bfv : faces) {
            vectors.add(new BlockFaceScore(bfv.face(), bfv.vector().dot(vector)));
        }

        vectors.sort((a, b) -> (int) ((b.score - a.score) * 1024));
        return vectors.get(0).face();
    }
}
